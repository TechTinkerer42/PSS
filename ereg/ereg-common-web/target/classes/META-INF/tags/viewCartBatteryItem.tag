<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@ attribute name="currencyCode" required="true"%>
<%@ attribute name="battery" required="true" type="org.ets.ereg.commerce.order.vo.BatteryItemVO" %>



<div class="cartRegistrationfee">
    ${battery.agency.name} ${battery.batteryDiscreteOrderitem.name}: <fmt:formatNumber value="${battery.batteryDiscreteOrderitem.price.amount}" type="currency" 	currencyCode="${currencyCode}" />
</div>

<div class="clear"></div>
