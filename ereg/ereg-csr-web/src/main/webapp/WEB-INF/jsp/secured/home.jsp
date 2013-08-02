<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="ct" uri="http://ereg.ets.org/commontags"%>

<t:base title="ETS - eREG (Home)">
	<div class="headContainer">
		<h1>Home of ${name}</h1>
		<p style="color:red; margin:10px 0 0 0;">${STATUS_MESSAGE}</p>
	</div>
	<!-- homeContainer Starts -->
	<div id="homeContainer">
		<!-- Column1 Starts -->
		<div class="column1">
			<div class="block">
				<h3>Welcome ${name}</h3>
				<div class="content">
					<b>State:</b>New Jersey<br>
					<b>Test Center:</b> Test Center Name
				</div>
			</div>
			<div class="block">
				<h3>My Profile</h3>
				<div class="content">
					<ul>
						<li><a href=<c:url value='/secure/tca/profile/' />>Personal Information</a></li>
						<li><a href='<c:url value='/secure/tca/profile/changePassword' />'>Change Password</a></li>
						<li><a href='<c:url value='/secure/tca/profile/changeSecurityQuestion' />'>Security Question</a></li>
					</ul>
				</div>
			</div>
			<div class="block">
				<h3>My Resources</h3>
				<div class="content">
					<a href="?">State Requirements</a>
				</div>
			</div>
		</div>
		<!-- Column1 Ends -->
		<!-- Column2 Starts -->
		<div class="column2">
			<sec:authorize access="hasRole('PERMISSION_CREATE_CUSTOMER')">
				<div class="block">
				<h3>Create New Test Taker Account</h3>
				<div class="content">
				<a href='<spring:url htmlEscape="true" value='/secure/profile' />'>Create New Test Taker Account</a>
				</div>	
				</div>
			</sec:authorize> 
			<sec:authorize access="hasRole('PERMISSION_SEARCH_CUSTOMER')">
				<div class="block">
					<h3>Search/Manage Existing Test Takers</h3>
					<div class="content">
						<strong>Quick Search</strong> <br>
						<input type="text"  class="smalltext"> <input type="text"  class="smalltext"> <button class="submit" type="submit">Search</button><br><br>
						<strong>Advanced Search</strong> <br><br>
						<a href="<c:url value='/secure/customersearch/search' />">Search Test Takers</a><br>
						<a href="<c:url value='/secure/testTakerRosterSearch/search' />">Test Taker Roster</a>
					</div>
				</div>
			</sec:authorize>
			<div class="block">
				<h3>My Next Steps</h3>
				<div class="content">
				</div>
			</div>
		</div>
		<!-- Column2 Ends -->
			<!-- Column3 Starts -->
		<div class="column3">
			<div class="block">
				<div class="content">
					<strong>Help desk?</strong><br>
					Display Personal Information here
				</div>
			</div>
			<div class="block">
				<div class="content">
					<strong>News</strong><br>
				</div>
			</div>
			<div class="block">
				<div class="content">
					<strong>Other Promos</strong><br>
				</div>
			</div>
		</div>
		<!-- Column3 Ends -->
	</div>
	<!-- homeContainer Ends -->
</t:base>