/*
 * Copyright 2008-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ets.ereg.domain.audit;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.LoaderClassPath;
import javassist.NotFoundException;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ConstPool;
import javassist.bytecode.FieldInfo;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.ArrayMemberValue;
import javassist.bytecode.annotation.ClassMemberValue;
import javassist.bytecode.annotation.MemberValue;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;

import org.broadleafcommerce.common.extensibility.jpa.convert.BroadleafClassTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ETSAuditableClassTransformer implements BroadleafClassTransformer {

    private static final Logger LOG = LoggerFactory.getLogger(ETSAuditableClassTransformer.class);
    private static final List<String> transformedMethods = new ArrayList<String>();
    
    private String weaveClass;
    private String weaveClassWithoutDates;
    private String auditableListener;

    public ETSAuditableClassTransformer() {
       
    }

    @Override
    public void compileJPAProperties(Properties props, Object key) throws Exception {
        // JPA properties do not need modification
    }

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
            ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {

        String convertedClassName = className.replace('/', '.');

        try {        	
            if (className.startsWith("javassist")){
                return null;
            }
            // Load the destination class and defrost it so it is eligible for modifications
            ClassPool classPool = ClassPool.getDefault();
            CtClass clazz = classPool.makeClass(new ByteArrayInputStream(classfileBuffer), false);
            ConstPool constantPool = clazz.getClassFile().getConstPool();
            clazz.defrost();
            //only perform transformations to Entity classes
            if (clazz.hasAnnotation(Entity.class)){
            	LOG.debug( "Beginning Transformation Process --- ");
                if (weaveClass == null || weaveClassWithoutDates == null || auditableListener == null) {
                	LOG.debug("END Transformation - weaveClass or weaveDatesClass or auditableListener properties are not set.");
                    return null;
                }
                
                //need to skip weaving for the classes that extend BLC entities
                CtClass superClass = clazz.getSuperclass();
                if(superClass != null && superClass.hasAnnotation(Entity.class)) {
                	LOG.debug("END Transformation - No weaving needed in [%s]", clazz.getName());
                    return null;
                }

                if( clazz.getName().contains("org.ets.pss")){
                	return null;
                }
                LOG.debug(String.format("Weaving Annotations into [%s]", clazz.getName()));
                
                AnnotationsAttribute annotationsAttribute = new AnnotationsAttribute(constantPool, AnnotationsAttribute.visibleTag);
                List<?> attributes = clazz.getClassFile().getAttributes();
                Iterator<?> itr = attributes.iterator();
                boolean entityListenerFound = false;
                while(itr.hasNext()) {
                    Object object = itr.next();
                    if (AnnotationsAttribute.class.isAssignableFrom(object.getClass())) {
                        AnnotationsAttribute attr = (AnnotationsAttribute) object;
                        Annotation[] items = attr.getAnnotations();
                        for (Annotation annotation : items) {
                            String typeName = annotation.getTypeName();
                            if (!typeName.equals(EntityListeners.class.getName())) {
                                annotationsAttribute.addAnnotation(annotation);
                            } else {
                                entityListenerFound = true;
                                modifyEntityListenerAnnotation(
										clazz, constantPool,
										annotationsAttribute, annotation);
                            }
                        }
                        itr.remove();
                    }
                }

                if (!entityListenerFound) {
                    addEntityListenerAnnotation(clazz, constantPool,
							annotationsAttribute);
                }

                clazz.getClassFile().addAttribute(annotationsAttribute);
                
                LOG.debug("determining weave class");
                String determinedWeaveClass = determineWeaveClass(clazz);
                
                LOG.debug(String.format("Preparing to weave class [%s]", determinedWeaveClass));
        		classPool.appendClassPath(new LoaderClassPath(Class.forName(determinedWeaveClass).getClassLoader()));
        		CtClass template = classPool.get(determinedWeaveClass);

        		weaveClass(clazz, template);


        		LOG.debug(String.format("Finished weaving [%s] into [%s]", determinedWeaveClass, clazz.getName()));
                
                LOG.debug("Transformation Complete. --- ");
                return clazz.toBytecode();
            }

        } catch(ClassNotFoundException ex) {
        	logAndThrowException(convertedClassName, ex);
        } catch(CannotCompileException ex) {
        	logAndThrowException(convertedClassName, ex);
        } catch(IOException ex) {
        	logAndThrowException(convertedClassName, ex);
        } catch(NotFoundException ex) {
        	logAndThrowException(convertedClassName, ex);
        }

        return null;
    }

	private void logAndThrowException(String convertedClassName,
			Exception ex) throws IllegalClassFormatException {
		String errorText = "Unable to convert " + convertedClassName + " to use ETSAuditable: " + ex.getMessage();
		LOG.error(errorText, ex);
		throw new IllegalClassFormatException(errorText);
	}

	private void weaveClass(CtClass clazz, CtClass template)
			throws NotFoundException, CannotCompileException {
		CtClass[] interfacesToCopy = template.getInterfaces();
		for (CtClass i : interfacesToCopy) {
		    LOG.debug(String.format("Adding interface [%s]", i.getName()));
		    clazz.addInterface(i);
		}

		CtField[] fieldsToCopy = template.getDeclaredFields();
		for (CtField field : fieldsToCopy) {
		    LOG.debug(String.format("Adding field [%s]", field.getName()));
		    CtField copiedField = new CtField(field, clazz);
		    clazz.addField(copiedField);
		}

		// Copy over all declared methods from the template class
		CtMethod[] methodsToCopy = template.getDeclaredMethods();
		for (CtMethod method : methodsToCopy) {
		    try {
		        CtClass[] paramTypes = method.getParameterTypes();
		        CtMethod originalMethod = clazz.getDeclaredMethod(method.getName(), paramTypes);

		        if (transformedMethods.contains(methodDescription(originalMethod))) {
		            throw new RuntimeException("Method already replaced " + methodDescription(originalMethod));
		        } else {
		            LOG.debug(String.format("Marking as replaced [%s]", methodDescription(originalMethod)));
		            transformedMethods.add(methodDescription(originalMethod));
		        }

		        LOG.debug(String.format("Removing method [%s]", method.getName()));
		        clazz.removeMethod(originalMethod);
		    } catch (NotFoundException e) {
		        // Do nothing -- we don't need to remove a method because it doesn't exist
		    }

		    LOG.debug(String.format("Adding method [%s]", method.getName()));
		    CtMethod copiedMethod = new CtMethod(method, clazz, null);
		    clazz.addMethod(copiedMethod);
		}
	}

	private String determineWeaveClass(CtClass clazz) {
		String determinedWeaveClass;
		boolean hasDates = false;
		for(Object obj: clazz.getClassFile().getFields()) {
			FieldInfo fi = (FieldInfo) obj;
			if(fi.getName().equals("auditable")) {
				hasDates = true;
				break;
			}
		}
			
		if(!hasDates){
			determinedWeaveClass = weaveClass;
		} else {
			determinedWeaveClass = weaveClassWithoutDates;
		}
		
		LOG.debug("determined weave class: {}", determinedWeaveClass);
		return determinedWeaveClass;
	}

	private void addEntityListenerAnnotation(CtClass clazz,
			ConstPool constantPool, AnnotationsAttribute annotationsAttribute) {
		LOG.debug(String.format("Adding @EntityListeners to [%s]", clazz.getName()));
		Annotation elAnnotation = new Annotation(EntityListeners.class.getName(), constantPool);
		MemberValue[] newValues = new MemberValue[]{new ClassMemberValue(auditableListener, constantPool)};
		ArrayMemberValue arrayMemberValue = new ArrayMemberValue(constantPool);
		arrayMemberValue.setValue(newValues);
		elAnnotation.addMemberValue("value", arrayMemberValue);
		annotationsAttribute.addAnnotation(elAnnotation);
	}

	private void modifyEntityListenerAnnotation(CtClass clazz,
			ConstPool constantPool, AnnotationsAttribute annotationsAttribute,
			Annotation annotation) {
		LOG.debug(String.format("Found @EntityListeners on class [%s]", clazz.getName()));
		ArrayMemberValue memberValue = (ArrayMemberValue) annotation.getMemberValue("value");
		int newLength = memberValue.getValue().length + 1;
		MemberValue[] newValues = new MemberValue[newLength];
		ClassMemberValue auditableClass =  new ClassMemberValue(auditableListener, constantPool);

		//copy over existing annotations
		for (int i=0; i<memberValue.getValue().length; i++) {
		    newValues[i] = memberValue.getValue()[i];
		}

		LOG.debug("*** Appending Custom AuditableListener ***");
		newValues[memberValue.getValue().length] = auditableClass;

		memberValue.setValue(newValues);
		MemberValue[] memberValues = memberValue.getValue();
		for (MemberValue m : memberValues) {
		    ClassMemberValue classMemberValue = (ClassMemberValue) m;
		    LOG.debug("    **** ClassMemberValue = {} " ,classMemberValue.toString());
		}

		annotationsAttribute.addAnnotation(annotation);
	}

    protected String methodDescription(CtMethod method) {
        return method.getDeclaringClass().getName() + "|" + method.getName() + "|" + method.getSignature();
    }

    public String getWeaveClass() {
        return weaveClass;
    }

    public void setWeaveClass(String weaveClass) {
        this.weaveClass = weaveClass;
    }

	public String getWeaveClassWithoutDates() {
		return weaveClassWithoutDates;
	}

	public void setWeaveClassWithoutDates(String weaveClassWithoutDates) {
		this.weaveClassWithoutDates = weaveClassWithoutDates;
	}

	public String getAuditableListener() {
        return auditableListener;
    }

    public void setAuditableListener(String auditableListener) {
        this.auditableListener = auditableListener;
    }
}
