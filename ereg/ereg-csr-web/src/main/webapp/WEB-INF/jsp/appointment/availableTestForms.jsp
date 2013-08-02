<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:forEach items="${availableTestForms}" var="testForm" varStatus="status">
	<option value="${testForm.formID}">Form ${testForm.formCode}</option>
</c:forEach>
