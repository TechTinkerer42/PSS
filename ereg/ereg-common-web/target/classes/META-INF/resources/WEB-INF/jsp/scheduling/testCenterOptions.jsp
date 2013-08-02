<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:forEach items="${testCenters}" var="testCenter" varStatus="status">
	<option value="${testCenter.id}">${testCenter.name}</option>
</c:forEach>