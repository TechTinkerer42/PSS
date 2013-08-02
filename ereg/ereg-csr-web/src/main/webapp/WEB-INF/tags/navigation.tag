<%@tag description="Navigation for eReg CSR Web" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!-- Start - NAVIGATION -->
<div class="navbar navbar-inverse navbar-fixed-top">
	<div class="navbar-inner">
		<div class="container">
			<a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</a>
			<a class="brand" href="<spring:url value="/secure/home"/>">eREG</a>
			<div class="nav-collapse collapse">
				<ul class="nav">
					<li class="active"><a href="#">First Last&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|</a></li>
					<li><a href="<spring:url htmlEscape="true" value='/logout' />">Sign Out&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|</a></li>
					<li><a href="#contact">Help&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|</a></li>
					<li class="dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown">Preferences <b class="caret"></b></a>
						<ul class="dropdown-menu">
							<li><a href="#">Create Profile</a></li>
							<li><a href="#">Update Profile</a></li>
							<li><a href="#">View Profile</a></li>
							<li class="divider"></li>
							<li class="nav-header">Other Links</li>
							<li><a href="#">Test Taker Details</a></li>
							<li><a href="#">Contact Information</a></li>
						</ul>
					</li>
				</ul>
				<form class="navbar-form pull-right">
					<input class="span2" type="text" placeholder="Search">
					<button type="submit" class="btn">Search</button>
				</form>
			</div><!--/.nav-collapse -->
		</div>
	</div>		
	<div class="ets_brand_palette01">
	</div>
</div>
<!-- End - NAVIGATION -->