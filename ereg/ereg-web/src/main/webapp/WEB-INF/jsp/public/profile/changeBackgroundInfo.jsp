<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="org.ets.ereg.web.profile.form.ProfileForm" %>
<%@ page import="org.ets.ereg.common.business.vo.biq.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>


<t:base title="Update Background Info">

<div class="headContainer ">
<div class="row">
<div class="span9"><h1>${headText}</h1></div>
</div>
             <h2></h2>
             <span class="required_notification"><spring:message code="requiredInformation"/></span>

</div>
<div class="headContainer"><spring:message code="background.message"/></div>

<!-- Form Canvas starts here -->
<c:url value="/secure/home" var="prev_url"/>
<form:form method="post" modelAttribute="profileForm" >

<p >
	<c:set var="STATUS_OK" value="<%=ProfileForm.STATUS_OK%>" />
	<c:set var="STATUS_KO" value="<%=ProfileForm.STATUS_KO%>" />
  	<c:if test="${profileForm.statusCode==STATUS_KO}">
		<div style="margin:20px;">
			<div class="errorblock">
  				<i class="icon-exclamation-sign"></i>
  				<c:out value="${profileForm.statusMessage}" escapeXml="false" />
  				<!-- 	<form:errors/>  -->
  			</div>
		</div>
 	</c:if>
	</p>


<div class="formContainer">


<style type="text/css">
	label:after {
			content:" " !important;
	}
	
</style>
<div class="create_form">


<div class="formRow">






	

	


<div class="questionSection">
	<ol>
<c:forEach var="demographicQuestion" items="${profileForm.profile.demographicQuestions}" varStatus="i">
    	<li>
    	<div  id="q${demographicQuestion.qstnNo}" <c:if test="${!demographicQuestion.displayed}">style="display:none;"</c:if>>
	 	<!-- CheckBox-->
    	<c:if test="${demographicQuestion.responseType == 'M'}">
    	
    	  <div class="questionRow">
            	    <c:if test="${demographicQuestion.responseRequired == true}">
							<font color="red">*</font>
					</c:if>
					<c:if test="${demographicQuestion.responseRequired == false}">
							
					</c:if>
            		<strong>${demographicQuestion.displayText}</strong>
            	<!-- 	${demographicQuestion.triggerArray}  -->
            		
            	</div>
       
    <div class="questionRow">	
					
					<form:checkboxes items="${demographicQuestion.demographicResponses}" path="profile.demographicQuestions[${i.index}].selectedResponseIds" itemLabel="possibleResponse" itemValue="respNo" />
	
	</div>
	    </c:if>
	        <!-- Radio-->
    	<c:if test="${demographicQuestion.responseType == 'R'}">
    	
    	  <div class="questionRow">
            	    <c:if test="${demographicQuestion.responseRequired == true}">
							<font color="red">*</font>
					</c:if>
					<c:if test="${demographicQuestion.responseRequired == false}">
							
					</c:if>
            		<strong>${demographicQuestion.displayText}</strong>
            	<!-- 	${demographicQuestion.triggerArray}  -->
            		
            	</div>
       
   	
   
					 <c:forEach var="validResponseItem" items="${demographicQuestion.demographicResponses}">
					  <div class="questionRow">
     <form:radiobutton path="profile.demographicQuestions[${i.index}].selectedResponseIds"  value="${validResponseItem.respNo}" label="${validResponseItem.possibleResponse}" />
     </div>
   </c:forEach>
				
	
	
	    </c:if>
	    
       	<!-- ListBox-->
	    <c:if test="${demographicQuestion.responseType == 'S'}">
	      
	      <div class="questionRow">
	      
            		<c:if test="${demographicQuestion.responseRequired == true}">
							<font color="red">*</font>
					</c:if>
					<c:if test="${demographicQuestion.responseRequired == false}">
					
					</c:if>
            		<strong>${demographicQuestion.displayText}</strong>
            		
            	</div>
            	
           
           
            <div class="questionRow">	
		<form:select path="profile.demographicQuestions[${i.index}].selectedResponseIds" size="1" multiple="false" onChange="javascript:toggleDisplay(this, ${demographicQuestion.triggerArray})">
		
		<form:option value="">Select</form:option>
		<c:forEach items="${demographicQuestion.demographicResponses}" var="validResponseItem">
			<form:option  value="${validResponseItem.respNo}" name="${validResponseItem.respNo}" >${validResponseItem.possibleResponse}</form:option>
		</c:forEach>
	</form:select>
	
	
				</div>
	    </c:if>
	 
	 <!--TextBox-->
	    <c:if test="${demographicQuestion.responseType == 'F'}">
	   
	       <div class="questionRow">	
            		<c:if test="${demographicQuestion.responseRequired == true}">
							<span class="Alert">*</span>
					</c:if>
					<c:if test="${demographicQuestion.responseRequired == false}">
							
					</c:if>
            		<strong>${demographicQuestion.displayText}</strong>
            	</div>
            
            	 <div class="questionRow">	
            	        			
        			<form:input path="profile.demographicQuestions[${i.index}].freeFormAnswer" size="10" id=""  maxlength="256"  />       		
					
				</div>
				
				

	    </c:if>
	     </div>
	  </li>
     
	 
     </c:forEach>
	  </ol>
     
     
    
       
       
       
    
</div>








<div style="clear:both;"></div>

 <div class="row-fluid">
    <div class="span6">&nbsp; &nbsp; <a class="submit" href="<c:out value='${prev_url}'/>"><spring:message code="cancel"/></a>
</div>
    <div class="span6"><span class="right"><button class="submit" type="submit"><spring:message code="save"/></button></span> &nbsp; &nbsp;</div>
    </div>




</form:form>

</div>

</div> <!--  Form Canvas ends here -->

</div>

</t:base>