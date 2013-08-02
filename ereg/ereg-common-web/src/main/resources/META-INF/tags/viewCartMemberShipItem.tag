<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@ attribute name="currencyCode" required="true"%>
<%@ attribute name="membership" required="true" type="org.ets.ereg.commerce.order.vo.MembershipItemVO" %>



<div class="cartRegistrationfee">
<%--  <c:forEach items="${cartVO.membership}" var="membership" varStatus="status">	 --%>
	${membership.membershipDiscreteOrderitem.agency.name} ${membership.membershipDiscreteOrderitem.name}: <fmt:formatNumber value="${membership.membershipDiscreteOrderitem.price.amount}" type="currency" 	currencyCode="${currencyCode}" />
<%-- 	</c:forEach> --%>
</div>

			<div class="clear"></div>
					