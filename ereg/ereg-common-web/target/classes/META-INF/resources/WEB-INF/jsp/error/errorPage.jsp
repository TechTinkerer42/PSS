<%@ page isErrorPage="true" %>
<%@page import="java.io.StringWriter"%>
<%@page import="java.io.Writer"%>
<%@page import="java.io.PrintWriter"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>
<pre>
<%
final Writer result = new StringWriter();
final PrintWriter printWriter = new PrintWriter(result);
exception.printStackTrace(printWriter);
//String msg =result.toString().replaceAll("\\r|\\n", "newline");
String msg =result.toString().replaceAll("\\r", "newtab").replaceAll("\\n", "newline");
//out.println(msg);
%>
<c:set var="oldErrorMessage" value="<%=exception.getMessage()%>"/>
<c:set var="oldErrorMessage" value="<%=msg.toString()%>"/>
<c:set var="modifiedErrorMessage" value="${fn:escapeXml(oldErrorMessage)}" />
</pre>
 <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.4/jquery.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            {	
            	var pathURL = window.location.pathname.split( '/' );
            	var errorMessage="${modifiedErrorMessage}";
            	$.ajax({
                	type : "POST",
                    url: window.location.protocol + "//" + window.location.host + "/" + pathURL[1] + "/errorcontroller/error",
                    dataType : 'html',
                    data: "data=" + escape(errorMessage) ,
                    success: function(data){
                    	document.open();
        				document.write(data);
        				document.close();
                    },
                    error: function(xhr, ajaxOptions, thrownError) {
                    	//TODO Styling of the static error page
                    	var msg = "An unepxected error has occured on the server with status code "+ xhr.status +". Please contact administrator"; 
                    	document.open();
        				document.write(msg);
        				document.close();
                    },
        			async : true,
        			cache: false
                });
            }
        });
    </script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Error Page</title>
</head>
<body>
</body>
</html>