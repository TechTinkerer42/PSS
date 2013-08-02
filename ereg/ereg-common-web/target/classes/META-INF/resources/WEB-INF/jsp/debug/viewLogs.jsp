<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src='<spring:url htmlEscape="true" value="/commonweb/js/jquery-1.8.3.min.js"/>'></script>
<script src='<spring:url htmlEscape="true" value="/commonweb/js/misc.js"/>'></script>
<title>Debug: View Logs</title>
<script type="text/javascript">
function sub(file) {
	var target = "logtext";
	$( "#"+target ).html("Loading....");
	$('#viewLogsForm').find('input[name="selectedfile"]').val(file);
	var form = $('form#viewLogsForm').serialize();
	var formAction = "${pageContext.request.contextPath}/secure/debug/viewlogfile";
	ajaxPost(formAction,form, target,'POST',
			function(data) {
				$.trim(data);
				data=data.length <=0 ?"No Content for "+file:data;		
				$( "#"+target ).text(data);
			}			
	);
}
function getLog(file) {
	var target = "logtext";
	$( "#fn" ).text("");
	$( "#"+target ).html("Loading....");
	$('#viewLogsForm').find('input[name="selectedfile"]').val(file);
	var form = $('form#viewLogsForm').serialize();
	var formAction = "${pageContext.request.contextPath}/secure/debug/viewlogfile";
    $.ajax({
      type : 'post',
      url: formAction,
      data: form,
      async : true,
      success:function(data) {
          $.trim(data);
          data=data.length <=0 ?"No Content":data;
		  $( "#fn" ).html("Output for \"<b>"+file+"</b>\"");		
		  $( "#"+target ).text(data);
		},
      error: function(e){
          alert('Error: ' + e);
      }
    });
  }
</script>
<style>
.logtext {
	font: bold 13px/24px arial,helvetica,sans-serif;
	width: 1600px;
	height: 550px;
	background: #fff;
	border: 1px solid #b7babc;
	overflow: auto;
	padding: 5px;
	white-space: -moz-pre-wrap !important;
	white-space: -pre-wrap; 
	white-space: -o-pre-wrap; 
	white-space: pre-wrap; 
	word-wrap: break-word;
}
</style>
</head>
<body>
<div>
<p><a href=<c:url value='/secure/debug' />>Back to debug home</a></p>
<p><b>Log File Directory:</b>&#160;<c:out value="${logsDirectoryPath}" /></p>
<c:choose>
    <c:when test="${filesList.isEmpty()}">No files to view</c:when>
    <c:otherwise>
	    <p><b>Click on a file to view contents:</b></p>
	    <form:form  id="viewLogsForm" method="post" action="${pageContext.request.contextPath}/secure/debug/viewlogfile">
	    	<input id="logsdir" type="hidden" value="${logsDirectoryPath}"/>
	    	<input id="selectedfile" name="selectedfile" type="hidden"/>
			<c:forEach items="${filesList}" var="logFile">
			<p><a href="javascript:getLog('${logFile.getName()}');"><c:out value="${logFile.getName()}" /></a></p>
			</c:forEach>
		</form:form>
    </c:otherwise>
</c:choose>
</div>
<div id="fn"></div>
<div id="logtext" name="logtext" class="logtext"></div>
</body>
</html>