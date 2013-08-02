<!-- Start - JSP Import Statements -->
<%@ page import="org.ets.ereg.csr.web.form.profile.ProfileForm"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="org.ets.ereg.csr.web.form.profile.ProfileForm"%>
<%@ page import="org.ets.ereg.common.business.vo.biq.*"%>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="ct" uri="http://ereg.ets.org/commontags" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:base title="ETS - eREG (Sign In)">
	<!-- <div class="headContainer ">
		<div class="row">
			<div class="span9">
				<h1></h1>
			</div>
		</div> -->					
		<br>
		<h2>Background Information</h2>
		
	</div>
	<!-- Form Canvas starts here -->
	<form:form method="get"
		action="update"   >
		<div class="formContainer">
			<!-- BODY WITHOUT JSP TAGS GOES HERE, IF ANY -->
			<div class="create_form">
				<div class="formRow">								
					<div class="create_form">
						<div class="formRow">
							<div class="questionSection">
								<ol>
									<c:forEach var="demographicQuestion" items="${biqs}" varStatus="i">

										<!-- CheckBox-->
										<c:if test="${demographicQuestion.answered}">
											<li>											            	    
														${demographicQuestion.displayText}</br>${demographicQuestion.getResponse()}					            		
									   
											</li>
										</c:if>						 
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
				
					<a class="submit" href="<ct:encode out="/secure/testtaker/view/?customerId=${customerIdStr}" />" />Cancel</a>
					<sec:authorize
												access="hasRole('PERMISSION_UPDATE_BIQ')"> 
					<span class="right" style="margin: -6px 0 0 0;"><a class="submit" href="<ct:encode out="/secure/testtaker/viewUpdateBackground?customerId=${customerIdStr}" />" />Update</a></span>
					</sec:authorize>
				</div>
			</div>
		
		<%-- <input id="customerId" type="hidden" value="<c:out value="${customerIdStr}" />" /> --%>
		

	</form:form>
	<!--  Form Canvas ends here -->
</t:base>