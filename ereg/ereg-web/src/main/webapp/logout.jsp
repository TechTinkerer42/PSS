<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="ct" uri="http://ereg.ets.org/commontags" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>


<t:base title="ETS - eREG (Candidate Search)">
<link href='<spring:url htmlEscape="true" value="/commonweb/css/jquery-ui.css"/>' rel="stylesheet">

	
<!--
    ___
   |   |
   |   |
   |   |  LOOK!
   |   |
   |   |
 __!   !__
 \       /
  \     /
   \   /
    \ /

	any eias in-page scripting and style happen here.
	not in the body. here!
 -->
		<script type="text/javascript">
		/* <![CDATA[ */

			$(document).ready(function(){
			}); // end: document.ready

		/* ]]> */
		
				function delCookie(name,path,domain) {
			var today = new Date();
			var deleteDate = new Date(today.getTime() - 48 * 60 * 60 * 1000); // minus 2 days
			var cookie = name + "="
				+ ((path == null) ? "" : "; path=" + path)
				+ ((domain == null) ? "" : "; domain=" + domain)
				+ "; expires=" + deleteDate;
			document.cookie = cookie;
		
		}

		function delOblixCookie() {
			// set focus to ok button
			//var isNetscape = (document.layers);
			//if (isNetscape == false || navigator.appVersion.charAt(0) >= 5) {
				//for (var i=0; i<document.links.length; i++) {
					//if (document.links[i].href == "javascript:top.close()") {
						//document.links[i].focus();
						//break;
					//}
				//}
			//}
			delCookie('ObTEMC', '/');
			delCookie('ObSSOCookie', '/');
			// in case cookieDomain is configured
			// delete same cookie to all of subdomain
			var subdomain;
			var domain = new String(document.domain);
			var index = domain.indexOf(".");
			while (index > 0) {
				subdomain = domain.substring(index, domain.length);
				if (subdomain.indexOf(".", 1) > 0) {
					delCookie('ObTEMC', '/', subdomain);
					delCookie('ObSSOCookie', '/', subdomain);
				}
				domain = subdomain;
				index = domain.indexOf(".", 1);
			}
			
			//Uncomment this if you want redirect to login page after successful log out.
			//provide your application relative path to login page below.
			window.location.href = "${pageContext.request.contextPath}/public/logout";				
		}
		
		function backToSignIn()
		{
			delOblixCookie();
			//redirect to login page.
			//change this value as per your application login page location.
			window.location.href = "${pageContext.request.contextPath}/public/logout";	
		}	
		
		</script>
		<style type="text/css">

		</style>

<body class="eias" onload="delOblixCookie();">







		<div><!-- start: container for 'header' -->


<!--
    ___
   |   |
   |   |
   |   |  LOOK!
   |   |
   |   |
 __!   !__
 \       /
  \     /
   \   /
    \ /

	it is a snap to change the color of the breadcrumb bar.
	just replace the 'etscorp-bkgrnd' with a new brand name.
	refer to ets.css for a list of the brand names/colors

	do not forget to update the line in the footer at the same time.

 -->
			<div id="breadcrumbs" class="etscorp-bkgrnd">
<!--
    ___
   |   |
   |   |
   |   |  LOOK!
   |   |
   |   |
 __!   !__
 \       /
  \     /
   \   /
    \ /

	if you have a need to add links into the breadcrumb area
	the coding needs to look like this
-->
<!--
				<a href="#">ETS Home</a> &gt;
				<a href="#">[Brand]</a> &gt;
				<a href="#">For Audience</a> &gt;
				Some Page
-->
			</div><!-- end: breadcrumbs -->

		</div><!-- end: container for 'header' -->

		<div id="main-content">
			<div id="page-contents">
				<div id="center-content" class="container-560">
<!-- ==================== -->
<!-- start: main contents -->
<!-- ==================== -->

<!--
    ___
   |   |
   |   |
   |   |  LOOK!
   |   |
   |   |
 __!   !__
 \       /
  \     /
   \   /
    \ /

	huzzah!
	now three pages out of the thousands that use this
	look and feel have heading with puncutation and sentence-case
	we should be proud!
-->
<h1>You are being signed out of the system. Please click on Back to Sign in if you are not redirected in the next 60 secs</h1>

<!--
    ___
   |   |
   |   |
   |   |  LOOK!
   |   |
   |   |
 __!   !__
 \       /
  \     /
   \   /
    \ /

	you need a spacer here
-->
<div class="spacer-height1em"></div>


<p>
	<input type="button" value="Back to Sign in" class="make-pretty" onclick="backToSignIn();"/>
</p>



<!-- ==================== -->
<!-- end: main contents   -->
<!-- ==================== -->
					<div class="clearboth"></div>
				</div><!-- end: container-560 -->



			</div><!-- end: page-contents" -->



		</div><!-- end: main-content -->
	
		</t:base>

