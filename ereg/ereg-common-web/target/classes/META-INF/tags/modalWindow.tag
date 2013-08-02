<%@tag description="Modal Window" %>
<%@ attribute name="title" required="true" %>
<%@ attribute name="id" required="true" %>
<%@ attribute name="buttonName" required="true" %>
<%@ attribute name="buttonMethod" required="true" %>
<%@ attribute name="cancelButtonName" required="false" %>
<%@ attribute name="cancelButtonMethod" required="false" %>
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
	${buttonMethod}();
},	

<c:choose>
<c:when test="${not empty cancelButtonName}">

"${cancelButtonName}": function() {
	$( this ).dialog( "close" );
}

</c:when>
<c:otherwise>
Cancel: function() {
	$( this ).dialog( "close" );
}
</c:otherwise>
</c:choose>

}
});

});

</script>
<div id="${id}" title="${title}">
<span>
</span>
</div>

