
package org.ets.ereg.external.service.eias.client.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ExtendedResponseObject complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ExtendedResponseObject">
 *   &lt;complexContent>
 *     &lt;extension base="{http://webservice.eias.ets.org}ResponseObject">
 *       &lt;sequence>
 *         &lt;element name="userArray" type="{http://webservice.eias.ets.org}ArrayOfUser"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ExtendedResponseObject", propOrder = {
    "userArray"
})
public class ExtendedResponseObject
    extends ResponseObject
{

    @XmlElement(required = true, nillable = true)
    protected ArrayOfUser userArray;

    /**
     * Gets the value of the userArray property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfUser }
     *     
     */
    public ArrayOfUser getUserArray() {
        return userArray;
    }

    /**
     * Sets the value of the userArray property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfUser }
     *     
     */
    public void setUserArray(ArrayOfUser value) {
        this.userArray = value;
    }

}
