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
<!doctype html>
<!--[if lt IE 7]> <html class="no-js lt-ie9 lt-ie8 lt-ie7" lang="en"> <![endif]-->
<!--[if IE 7]>    <html class="no-js lt-ie9 lt-ie8" lang="en"> <![endif]-->
<!--[if IE 8]>    <html class="no-js lt-ie9" lang="en"> <![endif]-->
<!--[if IE 9]>    <html class="no-js lt-ie10" lang="en"> <![endif]-->
<!--[if gt IE 9]><!--> <html class="no-js" lang="en"> <!--<![endif]-->




<head>
<meta charset="utf-8" />
<title>Upload Artifact</title>
<!-- Jquery UI -->
<script type="text/javascript" src="<c:url value="/resources/jquery-ui-1.10.3/themes/base/jquery-ui.css"/>"></script>
<script type="text/javascript" 	src="<c:url value='/resources/jquery-ui-1.10.3/jquery-1.9.1.js'/>"></script>
<script type="text/javascript" 	src="<c:url value='/resources/jquery-ui-1.10.3/ui/jquery-ui.js'/>"></script>
<script type="text/javascript" src="<c:url value="/resources/fineuploader/jquery.fineuploader-3.5.0.js"/>"></script>
<link href="<c:url value="/resources/fineuploader/fineuploader-3.5.0.css"/>" rel="stylesheet" type="text/css" />
<link href="<c:url value="/resources/css/pssscreen.css"/>" rel="stylesheet" type="text/css" />
<body>

<script>
	$(document)
			.ready(
					function() {						
						
						$('#uploadArtifact').click(
									function() {
											$("#uploadDialog")
													.load('/ereg-web/resources/html/upload.html')
													.dialog(
															{
																modal : true,
																dialogClass : "no-close",
																width : 600,
																height : 400,
																position:['middle',80],
																
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
											$
													.ajax({
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

<body>

		<div id="access-links" class="hide" role="navigation" aria-labelledby="desc-access-links">
			<span id="desc-access-links">Quick Links</span>
			<ul>
				<li><a href="#main">Skip to main content</a></li>
				<li><a href="#footer">Skip to Footer</a></li>
			</ul>
		</div><!-- /access-links -->

		<div id="wrapper-center">
			<div id="wrapper-content">

				<div id="top-navigation">

					<nav id="user-nav" role="navigation" aria-labelledby="desc-user-nav">
						<p class="hide" id="desc-user-nav">User navigation</p>
						<ul>
							<li><a href="">First Last</a></li>
							<li><a href="">My Home</a></li>
							<li><a href="">Sign Out</a></li>
						</ul>
						<div class="clearboth"></div>
					</nav><!-- /user-nav -->

					<div class="graydottedbar"></div>

				</div><!-- /top-navigation -->

				<div id="header" class="header-image ets-home" role="banner">

					<div id="product-logo">
						<div id="mo-logo">
							<a href="http://mega.ets.org/" target="_blank"><img src="/ereg-web/resources/img/logo-mo-dese.423x61.jpg" width="423" height="61" alt="Missouri Educator Gateway Assessments"></a>
						</div>
					</div>

					<nav id="audience-nav" role="navigation" aria-labelledby="desc-audience-nav">
						<p class="hide" id="desc-audience-nav">Audience links</p>
						<ul>
							<li><a href="http://mega.ets.org/rsc/pdf/faq.pdf" target="_blank" title="Frequently Asked Questions" class="lsa-trans donottransform">FAQs</a></li>
							<li><a href="http://mega.ets.org/contact/index.html" target="_blank">Contact Us</a></li>
						</ul>
						<div class="clearboth"></div>
					</nav>

					<div id="audience-header" aria-hidden="true">
						<!-- that 'ephox-wrap' is needed for the ets cms, if you are not using it you can skip the div-wrapper -->
						<div class="ephox-wrap">For Candidates</div>
					</div>

					<div class="clearboth"></div>

				</div> <!-- /header -->

				<div id="corp-bar" class="corp-bar">
					<!--
							<p class="hide">Site Path:</p>
							<a href="">Home</a> <span aria-hidden="true">&gt;</span>
							<a href="">Some Page</a> <span aria-hidden="true">&gt;</span>
							<a href="">A Topic</a>
					-->
				</div>

				<div id="main" role="main" aria-labelledby="desc-main">
					<div id="center-content">

<!-- ============= -->
<!-- CONTENT START -->
<!-- ============= -->

<div id="breadcrumb">
	<p class="hide">Site Path:</p>
	<a href="">Home</a> <span aria-hidden="true">&gt;</span>
	<a href="">Some Page</a> <span aria-hidden="true">&gt;</span>
	<a href="">A Topic</a>
</div>

<!-- the FIRST h1 on the page MUST get the id="desc-main" attribute -->
<h1 id="desc-main">My Artifacts</h1>

<h2>My Assessments</h2>

<p>These artifacts Aliquam ligula mauris, tempor nec libero auctor, commodo laoreet leo. Curabitur viverra turpis viverra ante vulputate, a semper enim hendrerit. Mauris dapibus orci id diam vehicula, nec dignissim orci euismod. Interdum et malesuada fames ac ante ipsum primis in faucibus. Mauris nisl arcu, feugiat quis orci a, adipiscing blandit dui.</p>
<p>Artifacts that have been eu tristique mi. Integer faucibus mauris nulla. Sed auctor augue turpis, nec vehicula lectus euismod eget.</p>

<div id="uploadDialog" title="Upload Artifacts" style="display:none"> </div>
<p><button id="uploadArtifact">Upload Artifact</button></p>

<table>
	<thead>
		<tr>
			<th>Artifact Name</th>
			<th>Action</th>
			<th>Date Uploaded</th>
		</tr>
	</thead>
	<tbody>

<!-- yes, the left-most cell in any TR needs to be TH -->
		<tr>
			<th class="as-td"><a href="">Classroom portfolio with spaces in the title that wraps on to two lines.pdf</a></th>
			<td><a href="">Rename</a> | <a href="">Remove</a></td>
			<td>Sept 18, 2013 &ndash; 9:01 AM</td>
		</tr>
		<tr>
			<th class="as-td"><a href="">MyStudentTeaching.ppt</a></th>
			<td><a href="">Rename</a> | <a href="">Remove</a></td>
			<td>Sept 18, 2013 &ndash; 9:01 AM</td>
		</tr>
		<tr>
			<th class="as-td"><a href="">TeachingExample.doc</a></th>
			<td>Cannot be modified</td>
			<td>Sept 18, 2013 &ndash; 9:01 AM</td>
		</tr>
	</tbody>
</table>

<div class="clearboth spacer-height1em"></div>

<!-- pro tip: if it looks like a button, it needs to be a button -->
<p><button class="btn">Go to My Home</button></p>

<div class="clearboth"></div>

<!-- ============= -->
<!-- CONTENT END   -->
<!-- ============= -->
						<div class="clearboth"> </div>


					</div><!-- /center-content -->
					<div class="clearboth"></div>
				</div><!-- /main -->

				<footer id="footer" role="contentinfo" aria-labelledby="desc-footer" >
					<h1 id="desc-footer" class="hide">Footer Navigation</h1>

					<div class="corp-bar"></div>

					<nav id="corp-links" role="navigation" aria-labelledby="corp-links-desc">
						<p class="hide" id="corp-links-desc">State Links</p>
						<ul>
							<li><a href="http://dese.mo.gov/">Missouri Department of Elementry and Secondary Education</a></li>
							<li><a href="">Missouri Performance Assessments Website</a></li>
							<li><a href="">Legal/Privacy Policy</a></li>
						</ul>
						<div class="clearboth"></div>
					</nav><!-- /corp-links -->

					<div class="clearboth"></div>

					<div id="cya">
						<p>
							Copyright <span aria-hidden="true">&copy;</span> 2013 by Educational Testing Service. All rights reserved.<br />
							All trademarks are the property of their respective owners.
						</p>
					</div> <!-- /cya -->

					<div id="ets-logo-3l"><a href="http://www.ets.org">ETS. Listening. Learning. Leading.</a></div>
				</footer>

			</div><!-- /wrapper-content -->
		</div><!-- /wrapper-center -->

	</body>
</html>
