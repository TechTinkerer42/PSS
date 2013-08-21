<%@tag description="The header tag fragment for eReg Web" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="ets" uri="/WEB-INF/tld/ets.tld"%> 
 
<link href="<c:url value="/resources/css/pssscreen.css"/>" rel="stylesheet" type="text/css"/>

<footer id="footer" role="contentinfo" aria-labelledby="desc-footer" >
					<h1 id="desc-footer" class="hide">Footer Navigation</h1>

					<div class="corp-bar"></div>

					<nav id="corp-links" role="navigation" aria-labelledby="corp-links-desc">
						<p class="hide" id="corp-links-desc">State Links</p>
						<ul>
							<li><a href="http://dese.mo.gov/" target="_blank"><spring:message code="footer.moDeptSecondaryEdu"/></a></li>
							<li><a href="http://mega.ets.org" target="_blank"><spring:message code="footer.moPerfAssmtSite"/></a></li>
							<li><a href="http://www.ets.org/legal" target="_blank"><spring:message code="footer.legalPrivacyPolicy"/></a></li>
						</ul>
						<div class="clearboth"></div>
					</nav><!-- /corp-links -->

					<div class="clearboth"></div>

					<div id="cya">
						<p>
							Copyright <span aria-hidden="true">&copy;</span> YYYY. All rights reserved.<br />
							All trademarks are the property of their respective owners.
						</p>
					</div> <!-- /cya -->

					<div id="ets-logo-3l"><a href="http://www.ets.org">ETS. Listening. Learning. Leading.</a></div>
				</footer>