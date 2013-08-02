<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:base title="My HiSET Home">
	<div class="headContainer">
		<h1>My HiSET Home</h1>
		<p style="color:red; margin:10px 0 0 0;">${STATUS_MESSAGE}</p>
	</div>
	<!-- homeContainer Starts -->
	<div id="homeContainer">
		<!-- Column1 Starts -->
		<div class="column1">
			<div class="block">
				<h3>${firstName} ${lastName}</h3> 
				<div class="content">
				 <c:forEach var="roleName" items="${roleNames}">
				 	${roleName}
				 </c:forEach>
				</div>
			</div>
			<jsp:include page="../common/myResources.jsp"></jsp:include>
		</div>
		<!-- Column1 Ends -->
		<!-- Column2 Starts -->
		<div class="column2">
				<div class="block">
					<h3>Manage Test Takers</h3>
					<div class="content">
					<!-- testing starts -->
					<div class="manageTestTaker">
				<div class="manageCol1">
						<span class="homePageh2">Test Takers</span>
						<ul>
								<sec:authorize access="hasRole('PERMISSION_CREATE_CUSTOMER')">
									<li>
										<a href='<spring:url htmlEscape="true" value='/secure/profile' />'>Create New Test Taker Account</a>
									</li>
								</sec:authorize>
								<sec:authorize access="hasRole('PERMISSION_SEARCH_CUSTOMER')">
									<li> 
										<a href="<c:url value='/secure/customersearch/search' />">Search Test Takers</a><br>
									</li>	
								</sec:authorize>
						</ul>
					</div>
					<div class="manageCol2">
						<span class="homePageh2">Appointments</span>
						<ul>
								<sec:authorize access="hasRole('PERMISSION_SEARCH_APPOINTMENT')">
									<li> 
										<a href="<c:url value='#' />">Search Appointments</a><br>
									</li>	
								</sec:authorize>
								<sec:authorize access="hasRole('PERMISSION_GENERATE_ROSTER')">
									<li> 
										<a href="<c:url value='/secure/testTakerRosterSearch/search' />">Roster</a>
									</li>	
								</sec:authorize>
						</ul>
					</div>  </div>
					<!-- testing starts -->
					
					
			 
					</div>	
				</div>
				
				
				
				<div class="block">
					<h3>Test Centers and States</h3>
					<div class="content">
					<div class="manageTestTaker">
					
					
					<div class="manageCol1">
						<span class="homePageh2">Test Centers</span>
						<form id="searchFormHome" method="get" action="${testCenterSearchAction}">
								<input type="text"  name="cityStateOrZipCode" width="150"/> 
								<input type="hidden" name="latitudeDegree" id="latitudeDegree">
								<input type="hidden" name="longitudeDegree" id="longitudeDegree">
								<button class="submit" type="button" id="searchButtonHome">Search</button>
							</form>
						
							<ul>
							
						  	<li> 
								<a href="${testCenterSearchAction}">Search Test Centers</a><br>
							</li>	
							
							</ul>
							
					<div style="clear"></div>	
							
						
					</div>
					<div class="manageCol2">
						<span class="homePageh2">State Users and TCAs</span>
						<ul>
							<sec:authorize access="hasRole('PERMISSION_SEARCH_TCA')">
								<li> 
									<a href="<c:url value='#' />">Search State Users and TCAs</a><br>
								</li>	
							</sec:authorize>
						</ul>
					</div></div>
					</div>	
				</div>
			<div class="block">
				<h3>Manage Reports</h3>
				<div class="content">
					<a href="<c:url value='' />">Generate Report</a>
				</div>
			</div>
		</div>
		<!-- Column2 Ends -->
	</div>
	<!-- homeContainer Ends -->
</t:base>