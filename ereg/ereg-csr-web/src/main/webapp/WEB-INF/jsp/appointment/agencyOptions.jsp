<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:forEach items="${agencies}" var="agency">
	<option value="${agency.id}">${agency.name}</option>
</c:forEach>
