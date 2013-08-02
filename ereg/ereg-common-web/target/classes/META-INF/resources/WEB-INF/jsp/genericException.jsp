<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
 
<t:base title="ETS - eREG (Exception)">
	<div class="headContainer "></div>
<div id="errorPage" style="visibility: hidden">True</div>	
<!-- Form Canvas starts here -->
<div class="formContainer">
	<h1><spring:message code="technicalerrorpage.heading"/></h1>
	<p><spring:message code="technicalerrorpage.userDebugId"/><b><c:out value="${wrapperException.refNumber}"/></b></p>
	<p><spring:message code="technicalerrorpage.MailTo"/><a href="<c:out value='mailto:${helpDeskEmail}?subject=${wrapperException.refNumber}'/>"><spring:message code="technicalerrorpage.helpDesk"/></a>
	<p><a href="/ereg-web/secure/home" class="submit"><spring:message code="technicalerrorpage.backHomeBtn"/></a></p>
</div>

</t:base>