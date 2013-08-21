<%@tag description="The header tag fragment for eReg Web" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="ets" uri="/WEB-INF/tld/ets.tld"%> 
 
<link href="<c:url value="/resources/css/pssscreen.css"/>" rel="stylesheet" type="text/css"/>


				<div class="hide">
					<h1><span class="dev-testname">HiSET</span> Web Site</h1>
					<nav>
						<div class="skip-nav"><a href="#main">Skip to main content</a></div>
					</nav>
				</div><!-- /.hide -->
				
				<div id="access-links" class="hide" role="navigation" aria-labelledby="desc-access-links">
			<span id="desc-access-links">Quick Links</span>
			<ul>
				<li><a href="#main">Skip to main content</a></li>
				<li><a href="#footer">Skip to Footer</a></li>
			</ul>
		</div><!-- /access-links -->
				
				
		
		
				
				<div id="top-navigation">

					<nav id="user-nav" role="navigation" aria-labelledby="desc-user-nav">
						<p class="hide" id="desc-user-nav">User navigation</p>
						<ul>
							<li><a href="#">${customer.firstName} ${customer.lastName}</a></li>
							<li><a href="/ereg-web/secure/home">My Home</a></li>
							 <c:choose> 
	          <c:when test="${eregUtils.isOAMAuthentication()}">
	           <li><a  href="<c:url value='/logout.jsp' />" >Sign Out</a></li>
	          	 </c:when>
	          <c:otherwise>
	           <li><a  href="<c:url value='/public/logout' />" >Sign Out</a></li>
	           
	          </c:otherwise></c:choose>
						</ul>
					</nav><!-- /user-nav -->

					<div class="graydottedbar"></div>

				</div><!-- /top-navigation -->
                   
			 <div class="header-image ets-home" id="ets-product-logo" role="banner"> 
			 <div id="product-logo">
						<div class="parent" id="mo-logo">
							<a href=""><img src="/ereg-web/resources/img/logo-mo-dese.423x61.jpg" width="100%" height="100%" alt="Missouri Educator Gateway Assessments"></a>
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
						<div class="ephox-wrap">FOR CANDIDATES</div>
					</div>

					<div class="clearboth"></div>
						</div> <!-- /header -->

				<div id="corp-bar" class="corp-bar">
					
				</div>
			
				
				

			







