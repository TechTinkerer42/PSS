<%@tag description="Header for eReg CSR Web" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<header role="banner">

				<div class="hide">
					<h1><span class="dev-testname">HiSET</span> Web Site</h1>
					<nav>
						<div class="skip-nav"><a href="#main">Skip to main content</a></div>
					</nav>
				</div><!-- /.hide -->

				<div id="ets-product-logo">
					<div id="ets-logo">
						<a href="http://www.ets.org"><img src="${pageContext.request.contextPath}/commonweb/img/ets-logo_64x72.jpg" width="64" height="72" alt="ETS" /></a>
					</div>
					<div id="product-logo">
						<a href="index.html"><img src="${pageContext.request.contextPath}/commonweb/img/testname-logo_156x72.jpg" width="141" height="72" alt="HiSET" /></a>
					</div>
				


   <div id="audience-header">
   <span class="headerLink">
	         
	         <sec:authorize access="isAuthenticated()">

	         <sec:authentication property="principal" var="user" scope="page" />
	         	<c:out value="${user.getFirstName()} ${user.getLastName()}"/> | 
	         	<a href= '<c:url value='/secure/home' />'>My HiSET Home</a> |
	          <c:choose> 
	          <c:when test="${eregUtils.isOAMAuthentication()}">
	           <a  href="<c:url value='/public/logout' />" >Sign Out</a>
	          </c:when>
	          <c:otherwise>
	           <a  href="<c:url value='/public/logout' />" >Sign Out</a>
	          </c:otherwise>
	          
	           </c:choose> 
	         </sec:authorize>
		<sec:authorize access="isAuthenticated()">
			<sec:authorize access="hasRole('ROLE_TEST_CENTER_ADMIN')">
				<h3>FOR TEST CENTER ADMINISTRATORS</h3>
			</sec:authorize>
			<sec:authorize access="!hasRole('ROLE_TEST_CENTER_ADMIN')">
				<h3>FOR ETS REPRESENTATIVES</h3>
			</sec:authorize>
		</sec:authorize>
		<sec:authorize access="!isAuthenticated()">
			<h3>FOR ADMINISTRATORS</h3>
		</sec:authorize>
	
	         </div><!-- end: audience-header -->

</div>

				<!-- nav id="section-navigation"></nav -->
				<!-- div id="audience-header" aria-hidden="true"></div -->

			</header>

			<nav role="navigation" id="colorbar">
			</nav>
			
			
