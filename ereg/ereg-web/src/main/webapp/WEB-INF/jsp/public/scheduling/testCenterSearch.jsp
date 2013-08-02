<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="org.ets.ereg.web.scheduling.form.TestCenterSearchForm" %>
<%@ page import="org.ets.ereg.domain.interfaces.model.common.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>



<t:base title="Test Center - Search">
		
		<div class="center">
			<div class="headContainer ">
				<div class="row">
					<div class="span9"><h1><spring:message code="findTestCenters"/></h1></div>
				</div>
				
				
			<span class="required_notification"><spring:message code="requiredField"/></span>
			
				
			</div>
			
         
          
          
          
			<div class="formContainer">
				<div class="create_form">
			
				<div Class="toggleMap"></div> <span style="padding:2px 0; margin:2px; display:inline-block;"><b><spring:message code="searchCriteria"/></b></span>
					
					<!-- TODO toggle show/hide of search criteria -->
					<div class="formRow" id="searchCriteriaPanel">
						<c:url value="/public/testcenter/search/${globalContextCustomer.currentProgramCode}" var="actionURL"/>
						<form:form method="post" modelAttribute="testCenterSearchForm" action="${actionURL}"  id="searchForm" onsubmit="return false">
						<div class="testcenterSearch">
						<ul>
						<li><label for="country"><spring:message code="country"/></label>
							<form:select path="<%=TestCenterSearchForm.COUNTRY%>" required="required" id="country">
								<form:option value=""><spring:message code="select"/></form:option>
								<c:forEach items="${countries}" var="country">
									<form:option value="${country.abbreviation}">${country.name}</form:option>
								</c:forEach>
							</form:select></li>
							
					<li><label for="cityOrZipCode"><spring:message code="cityStateOrZipCode"/></label>
							<form:input path="<%=TestCenterSearchForm.CITY_STATE_OR_ZIPCODE%>" id="cityOrZipCode" required="required" /></li>		
							
					<li><label for="distance"><spring:message code="distance"/></label>
							<form:select path="<%=TestCenterSearchForm.DISTANCE%>">
								<form:option value=""><spring:message code="select"/></form:option>
								<c:forEach items="${distances}" var="distance">
									<form:option value="${distance}">${distance} <spring:message code="miles"/></form:option>
								</c:forEach>
							</form:select></li>		
						
						<li><label for="testCenterName"><spring:message code="testCenterName"/></label>
							<form:input path="<%=TestCenterSearchForm.TEST_CENTER_NAME%>"/></li>
							
					<li><label for="testTypes"><spring:message code="testTypes"/></label>
							<c:forEach items="${testTypes}" var="testType">
								<form:checkbox path="<%=TestCenterSearchForm.TESTTYPES%>" value="${testType.code}"/>${testType.description}
							</c:forEach>
							<form:errors  class="errorMessage" path="<%=TestCenterSearchForm.TESTTYPES%>"/>
							<form:hidden path="<%=TestCenterSearchForm.LATITUDE_DEGREE%>" id="latitudeDegree"/>
							<form:hidden path="<%=TestCenterSearchForm.LONGITUDE_DEGREE%>" id="longitudeDegree"/>
							<form:hidden path="offset" id="offset"/>
							<form:hidden path="count" id="count"/>
					</li>		
							
					
						</ul>
							</div>
							
							
					
							<button type="submit" value="next" id="searchButton" class="submit"><spring:message code="search"/></button>
						</form:form>
					</div>
				</div>
			</div>

 <div id="statusPanel"></div>

			<div id="searchResultPanel">
			<div id="dataTable">
				<form>
					<fieldset style="display:none;">
						<div id="pageSizePanel" class="dataTables_length"><label><spring:message code="resultsPerPage"/> 
						<select name="pageSize" size="1" id="pageSize"  style="width:75px"><option value="5" selected="selected">5</option><option value="10">10</option><option value="20">20</option><option value="50">50</option><option value="100">100</option></select></label>
						</div>
						<table id="searchResultsTable" class="resultsTable">
							<thead>
								<tr>
									<th id="srNo"><spring:message code="srNo"/></th>
									<th id="name"><spring:message code="testCenter"/></th>
									<th id="distance"><spring:message code="distance"/></th>
								</tr>
							</thead>
						</table>
						<div id="paginationBar">
							<a id="firstPage"><spring:message code="firstPage"/></a>
							<a id="previousPage"><spring:message code="previousPage"/></a>
							<span id="pageNumber"></span>
							<a id="nextPage"><spring:message code="nextPage"/></a>
							<a id="lastPage"><spring:message code="lastPage"/></a>
						</div>
						<input type="hidden" id="indexOfFirstRowOfPage" />
						<input type="hidden" id="countOfRecs" />
					</fieldset>
				</form>
				
			
				
				</div>
				
				<div id="wrapper" style="width:600px; position:relative; float:left; margin:2px; display:block; ">
				<div id="map_canvas"  style="width: 600px; height: 500px"></div>
				</div>
				
			</div>
			
			
			
			
			
			
			
		</div>
		<script>
		$(document).ready(function() {
		var searchResult = ${searchResult};
		processSearchResult("searchResultsTable", searchResult);
		reloadMap(searchResult);
		/*xz();
        GUnload();*/
});
		</script>
		
</t:base>
<script src="http://maps.google.com/maps/api/js?file=api&amp;v=3&amp;key=AIzaSyBElXakDy99jw9gMrUQ6Ik5DPvST6dJiwc&amp;sensor=false" type="text/javascript"></script>
<script src='<spring:url htmlEscape="true" value="/commonweb/js/testcenter/search.js"/>'></script>
<script>
var map;
var infoWindow = new google.maps.InfoWindow({maxWidth:400});
var enabledDates;
var markers;

function resetMap(){
	map = null;
	enabledDates = null;
	markers = null;
}

function clearOverlays() {
	  if (markers) {
	    for (var i = 0; i < markers.length; i++ ) {
	      markers[i].setMap(null);
	    }
	  }
	}


function reloadMap(resultData) {
	resetMap();
	//alert(resultData.searchedElements.length);
	//if (resultData != null) {
		var	mapCenterLatitude = $('#latitudeDegree').val();
		var mapCenterLongitude = $('#longitudeDegree').val();
		var radius = $('#distance').val();
		var zoomLevel = 0;
		switch(true){
			case (radius <= 20):
				zoomLevel = 10;
				break;
			case (radius > 20 && radius <= 50):
				zoomLevel = 9;
				break;
			case (radius > 50 && radius <=100):
				zoomLevel = 8;
				break;
			case (radius > 100):
				zoomLevel = 7;
		}
		
		var myOptions = {
		center : new google.maps.LatLng(mapCenterLatitude, mapCenterLongitude),
			zoom : zoomLevel,
			mapTypeId : google.maps.MapTypeId.ROADMAP,
			panControl : true,
			zoomControl : true,
			scaleControl : true,
			streetViewControl : true,
			mapTypeControl : true,
			rotateControl:true,
		// mapTypeId: google.maps.MapTypeId.ROADMAP,
		/*	zoomControlOptions : {
				style : google.maps.ZoomControlStyle.SMALL
			} */

					
			
			
		};

		if(map == null) {
			map = new google.maps.Map(document.getElementById("map_canvas"),
				myOptions);
		} else {
			map.setOptions(myOptions);
		}

		google.maps.event.addListener(map, 'click', function() {
			infoWindow.close();
		});

		var i = 0;
		clearOverlays();
		markers = [];
	   // var image = 'http://i.stack.imgur.com/orZ4x.png';
	   if (resultData != null) {
		for ( var tc in resultData.searchedElements) {
			markers[i] = new google.maps.Marker(
					{
						position : new google.maps.LatLng(
								resultData.searchedElements[tc].latitudeDegree,
								resultData.searchedElements[tc].longitudeDegree),
						map : map,
					//	icon: image,
					//  animation:google.maps.Animation.BOUNCE,
				//	icon: 'http://chart.apis.google.com/chart?chst=d_map_pin_letter&chld=A|FF0000|000000',
					title : resultData.searchedElements[tc].name
					});

		
			(function(i, tcIn) {
				google.maps.event
						.addListener(
								markers[i],
								'click',
								function() {
									infoWindow.setContent(getHtmlInfo(tcIn));
									infoWindow.open(map, markers[i]);

								});
			})(i, tc);

			i++;
			}
	//}

		//}
	}

	function getHtmlInfo(tc) {
		var html = "<div style='width:300px;'><b>" + resultData.searchedElements[tc].name + "</b>";
		var address;
		html += '<br>' + resultData.searchedElements[tc].addressLine1;
		address = resultData.searchedElements[tc].addressLine1 + ',';
		if(null != this.addressLine2){
			html += '<br>' + resultData.searchedElements[tc].addressLine2;
			address += resultData.searchedElements[tc].addressLine2 + ','; 
		}
		if(null != this.addressLine3){
			html += '<br>' + resultData.searchedElements[tc].addressLine3;
			address += resultData.searchedElements[tc].addressLine3 + ',';
		}
		html += '<br>' + resultData.searchedElements[tc].city + ', ' + resultData.searchedElements[tc].stateAbbreviation + ' ' + resultData.searchedElements[tc].postalCode;
		address += resultData.searchedElements[tc].city + ', ' + resultData.searchedElements[tc].stateAbbreviation + ' ' + resultData.searchedElements[tc].postalCode + ',' + resultData.searchedElements[tc].countryAbbreviation;
		html += '<br>' + resultData.searchedElements[tc].countryAbbreviation;
		
		html += '<br><a href="http://maps.google.com/maps?daddr=' + address + '" target="_blank">Directions</a>';

		html += "</div>";
		return html;
	}
}
</script>
