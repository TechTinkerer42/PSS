<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="ct" uri="http://ereg.ets.org/commontags"%>
<%@page import="java.util.logging.Logger"%>
<%@page import="org.ets.pss.persistence.model.AsgndTsk"%>

<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<!doctype html>
<!--[if lt IE 7]> <html class="no-js lt-ie9 lt-ie8 lt-ie7" lang="en"> <![endif]-->
<!--[if IE 7]>    <html class="no-js lt-ie9 lt-ie8" lang="en"> <![endif]-->
<!--[if IE 8]>    <html class="no-js lt-ie9" lang="en"> <![endif]-->
<!--[if IE 9]>    <html class="no-js lt-ie10" lang="en"> <![endif]-->
<!--[if gt IE 9]><!--> <html class="no-js" lang="en"> <!--<![endif]-->

<link href="<c:url value="/resources/css/pssscreen.css"/>" rel="stylesheet" type="text/css"/>
<head>
<meta charset="utf-8" />
<title>Upload Artifact</title>
<!-- Jquery UI -->
<script type="text/javascript" src="<c:url value="/resources/jquery-ui-1.10.3/themes/base/jquery-ui.css"/>"></script>
<script type="text/javascript" 	src="<c:url value='/resources/jquery-ui-1.10.3/jquery-1.9.1.js'/>"></script>
<script type="text/javascript" 	src="<c:url value='/resources/jquery-ui-1.10.3/ui/jquery-ui.js'/>"></script>
<script type="text/javascript" src="<c:url value="/resources/fineuploader/jquery.fineuploader-3.5.0.js"/>"></script>
<link href="<c:url value="/resources/fineuploader/fineuploader-3.5.0.css"/>" rel="stylesheet" type="text/css" />

<script>
	$(document)
			.ready(
					function() {						
						jQuery.noConflict();
						
						$('#uploadArtifact').click(
									function() {
										
										$("#uploadDialog")
													.load('/ereg-web/resources/html/upload.html')
													.dialog(
															{
																modal : true,
																dialogClass : "no-close",
																width : 575,
																height : 400,
																buttons : {
																	Close : function(event) {
																		location.reload();
																	}
																}
															});
										});

						$('a[id^="removeDoc"]')
								.click(
										function(event) {
											//$('#do_something').click(function(event) {							
											event.preventDefault();

											var clicked = $(this);
											var idStr = clicked[0].id;
											//$('#highlight')+idstr;
											var indexOfCount = "removeDoc".length;
											var vDocId = idStr.substring(
													indexOfCount, idStr.length);
											$.ajax({
														url : "/ereg-web/pss/artifact/remove",
														type : "POST",
														data : {
															docId : vDocId
														},
														success : function(
																response) {
															//alert("success: " + response);
															//location.href = linkTo; //Redirect the user to the new page
															if (response.message == 'success') {
																var tableHtml = '<div class="clearboth spacer-height1em"></div><table><tr><th style=text-align: left; height="30">Artifact Name</th><th>Action</th><th>Date Uploaded</th></tr><tbody>';

																$
																		.each(
																				response.artifacts,
																				function(
																						index) {

																					var obj = response.artifacts[index];
																					tableHtml += '<tr>'
																							+ '<td height="25">'
																							+ obj.filename
																							+ '</td>'
																							+ '<td><a href="" id="removeDoc'+obj.id+'">remove</a></td>'
																							+ '<td>'
																							+ ((obj.dateCreated != null) ? obj.dateCreated
																									: '')
																							+ '</td>'
																							+ '</tr>';
																				});
																tableHtml += '</tbody></table>';
																$(
																		'#artifactsDiv')
																		.replaceWith(
																				tableHtml);
															} else {
																alert(response.message);
															}
														},
														error : function(xhr,
																textStatus,
																thrownError) {
															//console.log("Error xhr status: "+ xhr.statusText);
														},
													});
										});

						$('#goToMyHomePage').click(function() {
							
							$.ajax({
								url : "/ereg-web/secure/home",
								type : "GET"
							});
						});

					});
</script>
<style>
.no-close .ui-dialog-titlebar-close {
	display: none;
}
</style>
</head>
<t:base title="Artifacts">
<div id="main" role="main" aria-labelledby="desc-main">
       <div id="center-content">

<div id="breadcrumb">
	<p class="hide">Site Path:</p>
	<a href="/ereg-web/secure/home">Home</a> <span aria-hidden="true">&gt;</span>
	<a href="/ereg-web/secure/home">MyTasks</a> <span aria-hidden="true">&gt;</span>
	<a href="">MyArtifacts</a>
</div>

<div id="uploadDialog" title="Upload Artifacts" style="display:none"> </div>
	 <!-- the FIRST h1 on the page MUST get the id="desc-main" attribute -->
<h1><spring:message code="view.artifacts.teacher.assessments"/></h1>

<h3><spring:message code="view.artifacts.libraryOfArtifacts"/></h3>

<div id="message">
<spring:message code="view.artifacts.description.text"/> <br><br>

<spring:message code="view.artifacts.description.text1"/>

</div>
					
					<!-- <div style="width: 100%; margin: 10px 0px 20px 0px;">&nbsp;
					<div class="span6">
						<a class="submit" id="uploadArtifact" href="javascript: return 0;">Upload Artifact</a>
					 </div>
					
					</div>  -->
					<br>
					<p><a class="submit" id="uploadArtifact" >Upload Artifact</a></p>
					
			       
					<div id="artifactsDiv" style="margin: 20px 5px 10px 5px;">
							<table id="artifactsTable" style="border: 1px solid black; width: 100%; text-align:center;">
							<thead>
							<tr>
							<th style=text-align: left; height="30">Artifact Name</th>
							<th>Action</th>
							<th>Date Uploaded</th>
							</tr>
							</thead>
							<tbody>
							<c:if test="${!empty artifacts}">
								<c:forEach var="ob" varStatus="status" items="${artifacts}">
									<tr>
										<td height="25"><c:out value="${ob.rspSrcLctnNam}" /></td>
										<td><a href="" id='removeDoc<c:out value="${ob.docId}"/>'>remove</a></td>
										<td><fmt:formatDate  dateStyle="long" timeStyle="short"  value="${ob.dateCreated}" /></td>
										<%-- String S = new SimpleDateFormat("MM/dd/yyyy").format(myTimestamp);
										<td>${ob.dateCreated}</td> --%>
									</tr>
								</c:forEach>
							</c:if>
							</tbody>
							</table>
					</div>
					
					<div class="clearboth spacer-height1em"></div>
					
					<!-- pro tip: if it looks like a button, it needs to be a button -->

			
			<p><a class="submit" href="/ereg-web/secure/home">Go to My Home</a></p>
			<!-- <div class="span6">
				<a class="submit" href="/ereg-web/secure/home">Go to My Home</a>
			</div> -->
<div class="clearboth"></div>
	</div><!-- /center-content -->
	<div class="clearboth"></div>
	</div><!-- /main -->
	
	
</t:base>	

