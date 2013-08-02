
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@page import="java.util.logging.Logger"%>
<%@page import="org.ets.pss.persistence.model.AsgndTsk"%>
<!doctype html>
<html lang="en">
<head>
<meta charset="utf-8" />
<title>jQuery UI Accordion - Default functionality</title>
<!-- Jquery UI -->
<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />

<!-- fine uploader /portfolio/spring/web/artifact/remove?docId=<c:out value="${ob.docId}"/>-->
<link
	href="<c:url value="/resources/fineuploader/fineuploader-3.5.0.css"/>"
	rel="stylesheet" type="text/css" />

<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>

<script type="text/javascript" src="<c:url value="/resources/googiespell/AJS.js"/>"></script>

<script type="text/javascript" src="<c:url value="/resources/fineuploader/jquery.fineuploader-3.5.0.js"/>"></script>

<script>
	$(document)
			.ready(
					function() {						

						$('#uploadArtifact').click(
									function() {
											$("#uploadDialog")
													.load('/portfolio/resources/html/upload.html')
													.dialog(
															{
																modal : true,
																width : 600,
																height : 400,
																buttons : {
																	Cancel : function(event) {
																		event.view.close();																	
																		//$(this).dialog("close");
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
											$
													.ajax({
														url : "/portfolio/spring/web/artifact/remove",
														type : "POST",
														data : {
															docId : vDocId
														},
														success : function(
																response) {
															//alert("success: " + response);
															//location.href = linkTo; //Redirect the user to the new page
															if (response.message == 'success') {
																var tableHtml = '<table><tr><th>Artifact Name</th><th>Action</th><th>Date Uploaded</th></tr>';

																$
																		.each(
																				response.artifacts,
																				function(
																						index) {

																					var obj = response.artifacts[index];
																					tableHtml += '<tr>'
																							+ '<td>'
																							+ obj.filename
																							+ '</td>'
																							+ '<td><a href="" id="removeDoc'+obj.id+'">remove</a></td>'
																							+ '<td>'
																							+ ((obj.dateCreated != null) ? obj.dateCreated
																									: '')
																							+ '</td>'
																							+ '</tr>';
																				});
																tableHtml += '</table>';
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
															alert("xhr status: "
																	+ xhr.statusText);
														},
													});
										});

						$('#goToMyHomePage').click(function() {

							$.ajax({
								url : "/portfolio/spring/web/task/myPage",
								type : "GET"
							});
						});

					});
</script>
<style>

</style>
</head>
<body>
<div id="uploadDialog" title="Upload Artifacts" style="display:none"> </div>
<h1>Pre Service Teacher Assessments</h1>

<h3>My Artifacts</h3>

<div id="message">
These Artifacts can be linked to from any task for this assesments. If you rename an artifact, any tasks
that link to this artifact will be automatically updated. If you remove and artifact, any tasks that link
to the artifact will no longer link to the artifact, but you should check your task to see if you essay
response needs to be modified.<br><br>
Artifacts that been linked to in tasks that have already been submitted cannot be removed, renamed or replaced.
</div>

<input type="button" id="uploadArtifact" value="Upload Artifact"/>

<div id="artifactsDiv">
		<table id="artifactsTable">
		<tr>
		<th>Artifact Name</th>
		<th>Action</th>
		<th>Date Uploaded</th>
		</tr>
		
		<c:if test="${!empty artifacts}">
			<c:forEach var="ob" varStatus="status" items="${artifacts}">
				<tr>
					<td><c:out value="${ob.rspSrcLctnNam}" /></td>
					<td><a href="" id='removeDoc<c:out value="${ob.docId}"/>'>remove</a></td>
					<td>${doc.dateCreated}</td>
				</tr>
			</c:forEach>
		</c:if>
		</table>
</div>

<input type="button" id="goToMyHomePage" value="Go to My Home"/>

</body>

</html>
