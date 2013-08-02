<!-- Start - JSP Import Statements -->
<%@ page import="org.ets.ereg.csr.web.form.profile.ProfileForm"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="org.ets.ereg.csr.web.form.profile.ProfileForm"%>
<%@ page import="org.ets.ereg.common.business.vo.biq.*"%>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:base title="ETS - eREG (Background Info)">
	<div class="headContainer ">
		<div class="row">
			<div class="span9">
				<h1>Create Profile</h1>
			</div>
		</div>
		<c:choose> 
			<c:when test="${eregUtils.isOAMAuthentication()}">
				<img src="${pageContext.request.contextPath}/commonweb/img/step3OAM.jpg">
			</c:when>
			<c:otherwise>
				<img src="${pageContext.request.contextPath}/commonweb/img/step3.jpg">
			</c:otherwise>
		</c:choose>
		<br>
		<h2>Background Information</h2>
		<span class="required_notification">** Required to be filled
			in By TestTaker.Can be Skipped for now </span>
	</div>
	<!-- Form Canvas starts here -->
	<form:form method="post" modelAttribute="profileForm"
		action="background">
		<div class="formContainer">
			<!-- BODY WITHOUT JSP TAGS GOES HERE, IF ANY -->
			<div class="create_form">
				<div class="formRow">
					<c:url value="/secure/profile/additional" var="prev_url" />
					<div class="create_form">
						<div class="formRow">
							<div class="questionSection">
								<ol>
									<c:forEach var="demographicQuestion"
										items="${profileForm.profile.demographicQuestions}"
										varStatus="i">
										<div id="q${demographicQuestion.qstnNo}"
											<c:if test="${!demographicQuestion.displayed}">style="display:none;"</c:if>>
											<li>
												<!-- CheckBox--> <c:if
													test="${demographicQuestion.responseType == 'M'}">

													<div class="questionRow">
														<c:if
															test="${demographicQuestion.responseRequired == true}">
															<font color="red">**</font>
														</c:if>
														<c:if
															test="${demographicQuestion.responseRequired == false}">

														</c:if>
														<strong>${demographicQuestion.displayText}<strong>
																<!-- 	${demographicQuestion.triggerArray}  -->
													</div>

													<div class="questionRow">

														<form:checkboxes
															items="${demographicQuestion.demographicResponses}"
															path="profile.demographicQuestions[${i.index}].selectedResponseIds"
															itemLabel="possibleResponse" itemValue="respNo" />

													</div>
												</c:if> <!-- Radio--> <c:if
													test="${demographicQuestion.responseType == 'R'}">

													<div class="questionRow">
														<c:if
															test="${demographicQuestion.responseRequired == true}">
															<font color="red">*</font>
														</c:if>
														<c:if
															test="${demographicQuestion.responseRequired == false}">

														</c:if>
														<strong>${demographicQuestion.displayText}<strong>
																<!-- 	${demographicQuestion.triggerArray}  -->
													</div>



													<c:forEach var="validResponseItem"
														items="${demographicQuestion.demographicResponses}">
														<div class="questionRow">
															<form:radiobutton
																path="profile.demographicQuestions[${i.index}].selectedResponseIds"
																value="${validResponseItem.respNo}"
																label="${validResponseItem.possibleResponse}" />
														</div>
													</c:forEach>



												</c:if> <!-- ListBox--> <c:if
													test="${demographicQuestion.responseType == 'S'}">

													<div class="questionRow">

														<c:if
															test="${demographicQuestion.responseRequired == true}">
															<font color="red">**</font>
														</c:if>
														<c:if
															test="${demographicQuestion.responseRequired == false}">

														</c:if>
														<strong>${demographicQuestion.displayText}<strong>
													</div>



													<div class="questionRow">
														<form:select
															path="profile.demographicQuestions[${i.index}].selectedResponseIds[0]"
															onChange="javascript:toggleDisplay(this, ${demographicQuestion.triggerArray})">
															<form:option value="">Select</form:option>
															<c:forEach
																items="${demographicQuestion.demographicResponses}"
																var="validResponseItem">
																<form:option value="${validResponseItem.respNo}"
																	name="${validResponseItem.respNo}">${validResponseItem.possibleResponse}</form:option>
															</c:forEach>
														</form:select>


													</div>
												</c:if> <!--TextBox--> <c:if
													test="${demographicQuestion.responseType == 'F'}">

													<div class="questionRow">
														<c:if
															test="${demographicQuestion.responseRequired == true}">
															<span class="Alert">**</span>
														</c:if>
														<c:if
															test="${demographicQuestion.responseRequired == false}">

														</c:if>
														<strong>${demographicQuestion.displayText}</strong>
													</div>

													<div class="questionRow">

														<form:input
															path="profile.demographicQuestions[${i.index}].freeFormAnswer"
															size="10" id="" maxlength="256" />

													</div>
											</li>



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
				<div class="ereg_span_01"">
					<a class="submit" href="<c:out value='${prev_url}'/>">Back</a> <span
						class="right" style="margin: -6px 0 0 0;"><button
							class="submit" type="submit">Next</button></span>
				</div>
			</div>
		</div>

	</form:form>
	<!--  Form Canvas ends here -->
</t:base>