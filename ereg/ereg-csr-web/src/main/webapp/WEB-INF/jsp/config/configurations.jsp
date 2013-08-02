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


<c:choose>
<c:when test="${ eregHierconfigs eq null}">

<t:base title="ETS - eREG (Configurations Screen)">


<style>

form {margin: 0; padding: 0;}

.treeview, .treeview ul { 
	padding: 0;
	margin: 0;
	list-style: none;
}

.treeview ul {
	background-color: white;
	margin-top: 4px;
}

.treeview .hitarea {
	background: url(<c:url value="/commonweb/img/treeview-default.gif" />) -64px -25px no-repeat;
	height: 16px;
	width: 16px;
	margin-left: -16px;
	float: left;
	cursor: pointer;
}
/* fix for IE6 */
* html .hitarea {
	display: inline;
	float:none;
}

.treeview li { 
	margin: 0;
	padding: 3px 0pt 3px 16px;
}

.treeview a.selected {
	background-color: #eee;
}

#treecontrol { margin: 1em 0; display: none; }

.treeview .hover { color: red; cursor: pointer; }

.treeview li { background: url(<c:url value="/commonweb/img/treeview-default-line.gif" />) 0 0 no-repeat; }
.treeview li.collapsable, .treeview li.expandable { background-position: 0 -176px; }

.treeview .expandable-hitarea { background-position: -80px -3px; }

.treeview li.last { background-position: 0 -1766px }
.treeview li.lastCollapsable, .treeview li.lastExpandable { background-image: url(<c:url value="/commonweb/img/treeview-default.gif" />); }  
.treeview li.lastCollapsable { background-position: 0 -111px }
.treeview li.lastExpandable { background-position: -32px -67px }

	
	
	

</style>

<span id="ruleSetResponse">








<h1 id="banner">Configurations</h1>
<div id="main">
<div id="sidetree">


  <div class="treeheader">&nbsp;</div>
  <div id="sidetreecontrol"> <a href="?#">Collapse All</a> | <a href="?#">Expand All</a> </div>

 <ul class="treeview" id="tree">
 
 <c:forEach items="${hierarchys}" var="hierarchy" varStatus="item">
 <c:set var="hierListSize" value="${fn:length(hierarchys)}"></c:set>
 <c:set var="countEregHier" value="0"></c:set>

 
  <c:if test="${hierarchy.parentHierarchy.eregHierarchyIdentifier eq null && countEregHier!=0}" >
     </ul>
  </li>
   <li class="expandable"><div class="hitarea expandable-hitarea"></div><span ><strong id="${hierarchy.eregHierarchyIdentifier}" class="hierarchySel">${hierarchy.hierarchyName}</strong></span>
      <ul style="display: none;">
 </c:if>
  <c:if test="${hierarchy.parentHierarchy.eregHierarchyIdentifier eq null && countEregHier==0}" >
   <li class="expandable"><div class="hitarea expandable-hitarea"></div><span ><strong id="${hierarchy.eregHierarchyIdentifier}" class="hierarchySel">${hierarchy.hierarchyName}</strong></span>
      <ul style="display: none;">
       <c:set var="countEregHier" value="${0+1}"></c:set>
 </c:if>
 
   <c:if test="${hierarchy.parentHierarchy.eregHierarchyIdentifier ne null }" >
   <li><span ><strong id="${hierarchy.eregHierarchyIdentifier}" class="hierarchySel">${hierarchy.hierarchyName}</strong></span></li>
 </c:if>
 
 <c:if test="${hierListSize==item.count }">
    </ul>
  </li>
 </c:if>
 
 </c:forEach>
 </ul>
</div>
<br>
<form:form method="post" modelAttribute="configurationsForm" action="configurations/getConfigurationForHierarchy"  onsubmit="return false">
<form:hidden path="hierarchy"/>
</form:form>

<span class="selectedOption">You have Selected:</span>
<span class="defaultSelection">Please select a Hierarchy</span>
<br>
</div>
<br>

<span id="hierarchyConfigurations"></span>
<script src='<spring:url htmlEscape="true" value="/commonweb/js/misc.js"/>'></script>
<script src='<spring:url htmlEscape="true" value="/js/jquery.treeview.js"/>'></script>
<script type="text/javascript">
	$(function() {
		$("#tree").treeview({
			collapsed: true,
			animated: "medium",
			control:"#sidetreecontrol",
			prerendered: true,
			persist: "location"
		});
	})	
	
	$(document).ready(function() {
		
		
		
		$(".selectedOption").hide();
		$("#ruleroleContainer").hide();
		
		$("tr.ruleClass").live("click", function () {
			alert("Clicked");
		    var tr = $(this);
		    var hierarchy=tr.find('input[name="hierarchy"]').val();
		    var roleName=tr.find('input[name="roleName"]').val();
		    var ruleSetType=tr.find('input[name="ruleSetType"]').val();
		    var form=$("form#configurationsForm").serialize()+"&hierarchy="+hierarchy+"&roleName="+roleName+"&ruleSetType="+ruleSetType;    
			var formAction = "${pageContext.request.contextPath}/secure/configurations/getrulesinfo";
			
			var target='ruleSetResponse';
			ajaxPost(formAction,form,target ,'POST',
					function(data) {
				$( "#"+target ).html(data);
				$(".check-now").checked(true);

					}			
			);
	
		});
		
		
		$(".hierarchySel").live("click", function () {
			
			$('#ruleSetType').val('');
			$('#ruleConfigurations').empty();
		    var hierarchySelected =  $(this).attr("id");
		    var hierarchySelText=$(this).text();
		    $('.hierarchySel').css({color:'#333333'});
		    $('#'+hierarchySelected).css({color:'#003082'});
		    $('#hierarchy').val(hierarchySelected);
		    $(".defaultSelection").hide();
		    var selectedOption=$(".selectedOption").text();
		    selectedOption=selectedOption.split(":");
		    selectedOption=selectedOption[0];
		    $(".selectedOption").text(selectedOption+":"+hierarchySelText);
		    $(".selectedOption").show();
		    var form = $('form#configurationsForm').serialize();
		    var formAction=$("#configurationsForm").attr("action");
		    var target='hierarchyConfigurations';
			ajaxPost(formAction,form,target ,'POST',
					function(data) {
				$( "#"+target ).html(data);
					}			
			);

		});
		
		<c:if test="${hierachySelected}">
		
		$('#'+${hierarchyId}).trigger('click');
		
		</c:if>
		
		

 });
	
</script>


  </span>

</t:base>
</c:when>
<c:otherwise>
<c:if test="${!empty eregHierconfigs}">
<h3>Applied Configurations</h3>
<table id="searchResultsTable"  style="width:75%">
		<thead>
			<tr>
				<th  style="text-align:center;">Category</th>
				<th  style="text-align:center;">Role</th>
				<th  style="text-align:center;">Updated By</th>
				<th  style="text-align:center;">Updated Time</th>
				
			</tr>
		</thead>
		<tbody>

			
			<c:forEach items="${eregHierconfigs}"
				var="eregHierconfig" >

			<tr class="ruleClass">
			<form>
			<input type="hidden" name="hierarchy" value="${eregHierconfig.ruleSetConfiguration.ruleSetConfigurationIdentifier }">
			<input type="hidden" name="roleName" value="${eregHierconfig.adminRole.name}"/>
			<input type="hidden" name="ruleSetType" value="${eregHierconfig.ruleSetConfiguration.ruleSetType.code}"/>
			</form>
			<td  style="text-align:center;" >${eregHierconfig.ruleSetConfiguration.ruleSetType.description}</td>
			<td style="text-align:center;">${eregHierconfig.adminRole.description}</td>
			<td style="text-align:center;"><c:choose><c:when test="${eregHierconfig.ruleSetConfiguration.etsAuditable.updatedByAdminUser ne null }"><c:out value="${profileUtils.getETSAdminUser(eregHierconfig.ruleSetConfiguration.etsAuditable.updatedByAdminUser).name }" /></c:when>
			
			<c:otherwise>
			<c:out value="${profileUtils.getETSAdminUser(eregHierconfig.ruleSetConfiguration.etsAuditable.createdByAdminUser).name }"/>
			</c:otherwise>
			</c:choose></td>
			<td>
			<c:choose><c:when test="${eregHierconfig.ruleSetConfiguration.etsAuditable.etsDateUpdated ne null }"><c:out value="${eregHierconfig.ruleSetConfiguration.etsAuditable.etsDateUpdated}" /></c:when><c:otherwise><c:out value="${eregHierconfig.ruleSetConfiguration.etsAuditable.etsDateCreated}"/></c:otherwise></c:choose>
			</td>
			</tr>
			</c:forEach>



		</tbody>
	</table>
	</c:if>
	<br/>
	<c:if test="${!empty availableConfigs}">
	<h3>Avaiable Configurations</h3>
	<table   style="width:75%">
		<thead>
			<tr>
				<th style="text-align:center;">Category</th>
				<th style="text-align:center;">Role</th>
				<th style="text-align:center;">Updated By</th>
				<th style="text-align:center;">Updated Time</th>
				
			</tr>
		</thead>
		<tbody>

			
			<c:forEach items="${availableConfigs}"
				var="eregHierconfig" >

				<tr class="ruleClass">
				<form>
			<input type="hidden" name="hierarchy" value="${eregHierconfig.ruleSetConfiguration.ruleSetConfigurationIdentifier }">
			<input type="hidden" name="roleName" value="${eregHierconfig.adminRole.name}"/>
			<input type="hidden" name="ruleSetType" value="${eregHierconfig.ruleSetConfiguration.ruleSetType.code}"/>
			</form>
			<td  style="text-align:center;" >${eregHierconfig.ruleSetConfiguration.ruleSetType.description}</td>
			<td style="text-align:center;">${eregHierconfig.adminRole.description}</td>
			<td style="text-align:center;"><c:choose><c:when test="${eregHierconfig.ruleSetConfiguration.etsAuditable.updatedByAdminUser ne null }"><c:out value="${profileUtils.getETSAdminUser(eregHierconfig.ruleSetConfiguration.etsAuditable.updatedByAdminUser).name }" /></c:when>
			
			<c:otherwise>
			<c:out value="${profileUtils.getETSAdminUser(eregHierconfig.ruleSetConfiguration.etsAuditable.createdByAdminUser).name }"/>
			</c:otherwise>
			</c:choose></td>
			<td>
			<c:choose><c:when test="${eregHierconfig.ruleSetConfiguration.etsAuditable.etsDateUpdated ne null }"><c:out value="${eregHierconfig.ruleSetConfiguration.etsAuditable.etsDateUpdated}" /></c:when><c:otherwise><c:out value="${eregHierconfig.ruleSetConfiguration.etsAuditable.etsDateCreated}"/></c:otherwise></c:choose>
			</td>
			</tr>
			</c:forEach>



		</tbody>
	</table>
	</c:if>

</c:otherwise>
</c:choose>
