<%@ tag language="java" pageEncoding="ISO-8859-1"%>

<%@ attribute name="divName" required="true" %>
<%@ attribute name="width" required="true" %>
<%@ attribute name="height" required="true" %>

<div id="${divName}" style="display: none; width: ${width}; height: ${height}; margin:0 auto; text-align: center; ">
	<img src="${pageContext.request.contextPath}/commonweb/img/spinner.gif" alt="Waiting..." style="margin:30% auto;" />
</div>