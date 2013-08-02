<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="ct" uri="http://ereg.ets.org/commontags"%>

<input type="hidden" id="holdSeatSuccess" value="${holdSeatSuccess}" />
<c:if test="${accommodations.size() > 0}">
	<span id="accommodationDiv">
		<label><spring:message code="scheduling.label.accommodations" />:</label>
		<ct:accommodationsTable accommodations="${accommodations}" deliveryMode="${deliveryMode}" />
		<sec:authorize access="!hasRole('PERMISSION_VIEW_ACCOMMODATION')">
			<div class="acdn">
				<img src="${pageContext.request.contextPath}/commonweb/img/important.png" style="margin: 10px 0 -27px 9px;">
				<li><spring:message code="scheduling.message.approvedAccommodations" /></li>
				<li><spring:message code="scheduling.message.acknowledgementMessage" /></li>
				<li>
					<img src="${pageContext.request.contextPath}/commonweb/img/red_asterisk_accommodation.png" style="margin:-27px -7px -22px 0;" />
					<input type="checkbox" name="nonaccommodation" value="true" style="margin:-5px 0;">
					<spring:message code="scheduling.message.schedulingWithoutAccommodations" />
				</li>
			</div>
		</sec:authorize>
	</span>
</c:if>