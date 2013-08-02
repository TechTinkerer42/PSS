<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="org.ets.ereg.csr.web.form.testtaker.biq.BIQForm"%>
<%@ page import="org.ets.ereg.common.business.vo.biq.*"%>
<%@ page import="org.ets.ereg.profile.vo.*"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="ct" uri="http://ereg.ets.org/commontags" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:base title="ETS - eREG (Update Background Info)">
				<div class="headContainer ">
					<br>
					<h2>Update Background Information!!</h2>
					<span class="required_notification">** Required to be filled
						in By TestTaker.Can be Skipped for now </span>
				</div>
				<!-- Form Canvas starts here -->
				<ct:encode out="/secure/testtaker/background/update?customerId=${customerId}" var="action_url"/>
				
				<form:form method="post"  modelAttribute="biqForm"
					action="${action_url}">
					
					<div class="formContainer">
						<!-- BODY WITHOUT JSP TAGS GOES HERE, IF ANY -->
						<div class="create_form">
							<div class="formRow">								
								<div class="create_form">
									<div class="formRow">
										<div class="questionSection">
											<ol>
												<c:forEach var="demographicQuestion" items="${biqForm.profile.demographicQuestions}" varStatus="i">
										<div  id="q${demographicQuestion.qstnNo}" <c:if test="${!demographicQuestion.displayed}">style="display:none;"</c:if>>
									    	<li>
										 	<!-- CheckBox-->
									    	<c:if test="${demographicQuestion.responseType == 'M'}">
									    	
									    	  <div class="questionRow">
									            	    <c:if test="${demographicQuestion.responseRequired == true}">
																<font color="red">**</font>
														</c:if>
														<c:if test="${demographicQuestion.responseRequired == false}">
																
														</c:if>
									            		<strong>${demographicQuestion.displayText}<strong>
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
																<font color="red">**</font>
														</c:if>
														<c:if test="${demographicQuestion.responseRequired == false}">
																
														</c:if>
									            		<strong>${demographicQuestion.displayText}<strong>
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
																<font color="red">**</font>
														</c:if>
														<c:if test="${demographicQuestion.responseRequired == false}">
														
														</c:if>
									            		<strong>${demographicQuestion.displayText}<strong>
									            		
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
																<span class="Alert">**</span>
														</c:if>
														<c:if test="${demographicQuestion.responseRequired == false}">
																
														</c:if>
									            		<strong>${demographicQuestion.displayText}</strong>
									            	</div>
									            
									            	 <div class="questionRow">	
									            	        			
									        			<form:input path="profile.demographicQuestions[${i.index}].freeFormAnswer" size="10" id=""  maxlength="256"  />       		
														
													</div></li>
													
													
									
										    </c:if>
										  </li>
									     
										  </div>
									     </c:forEach>
												
											</ol>
										</div>
										<div style="clear: both;"></div>
									</div>
								</div>
							</div>
						</div>
					</div>

					<div class="formButtonsContainer">
						<div class="row-fluid">
							<div class="ereg_span_01">
								<a class="submit" href="<ct:encode out='/secure/testtaker/viewBackground?customerId=${customerId}'/>" /> Cancel</a>
								
									<span class="right" style="margin: -6px 0 0 0;">
										<button	class="submit" type="submit">Save</button>
									</span>
							</div>
						</div>
					</div>

				</form:form>
				<!--  Form Canvas ends here -->
				<div id="footer">
					<div class="grayline praxisbar"></div>
					<div id="ets-logo-footer">ETS. Listening. Learning. Leading.</div>
					<div id="corp-links">
						<p class="skip">ETS corporate links:</p>
						<ul>
							<li><a class="popup" href="http://www.ets.org/legal">Legal</a></li>
							<li class="last"><a class="popup"
								href="http://www.ets.org/legal/privacy">Privacy &amp;
									Security</a></li>
						</ul>
					</div>
					<!-- end: corp-links -->
					<div id="cya">
						<p>
							Copyright &copy;
							<script type="text/javascript">document.write(today.getFullYear());</script>
							2012 by Educational Testing Service. All rights reserved.<br>
							The ETS logo is a registered trademark of Educational Testing
							Service.
						</p>
					</div>
					<!-- end: cya -->
				</div>
				<!-- Footer Ends here-->
			</div>
		</div>
	</div>
	<!-- End - CONTAINER -->
	<!-- Start - OTHER JAVASCRIPT LIBRARIES (Placed at the end of the document so the pages load faster) -->
	<script
		src="${pageContext.request.contextPath}/commonweb/js/bootstrap-transition.js"></script>
	<script
		src="${pageContext.request.contextPath}/commonweb/js/bootstrap-alert.js"></script>
	<script
		src="${pageContext.request.contextPath}/commonweb/js/bootstrap-modal.js"></script>
	<script
		src="${pageContext.request.contextPath}/commonweb/js/bootstrap-dropdown.js"></script>
	<!-- End - OTHER JAVASCRIPT LIBRARIES (Placed at the end of the document so the pages load faster) -->
</body>

</html>
</t:base>