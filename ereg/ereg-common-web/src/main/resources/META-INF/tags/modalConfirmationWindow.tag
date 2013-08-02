<%@tag description="Modal Confirmation Window" %>
<%@ attribute name="title" required="true" %>
<%@ attribute name="id" required="true" %>
<%@ attribute name="buttonName" required="true" %>
<%@ attribute name="buttonMethod" required="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script>
$(function() {
	
$( "#"+"${id}" ).dialog({
autoOpen: false,	
modal: true,
resizable: true,
autoResize: true,
buttons: {		
"${buttonName}": function () {
	$( this ).dialog( "close" );
}
}
});

});

</script>

<div id="${id}" title="${title}">
<span>

</span>

</div>
