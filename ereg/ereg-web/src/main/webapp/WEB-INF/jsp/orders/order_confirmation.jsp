<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:base title="Order Confirmation">

    <div class="headContainer">

        <div class="row">
            <div class="span9">
                <h1>Order Success!</h1>
                <h2>Order Number: ${order.orderNumber}</h2>
            </div>
        </div>

        <div class="row">
            <ul>
                <c:forEach items="${order.discreteOrderItems}" var="item">
                    <li>${item.product.name} | qty: ${item.quantity}</li>
                </c:forEach>
            </ul>

            Total:$ ${order.total}
        </div>

        <c:forEach items="${order.paymentInfos}" var="paymentInfo">
            <div class="payment_method">
                <h3>Billing Address</h3>
                <address>
                    ${paymentInfo.address.firstName}&nbsp;${paymentInfo.address.lastName}<br/>
                    ${paymentInfo.address.addressLine1}<br/>
                    ${paymentInfo.address.city},&nbsp;${paymentInfo.address.state.abbreviation}&nbsp;${paymentInfo.address.postalCode}<br/>
                </address>
                <h3>Payment Information</h3>
                <b>${paymentInfo.additionalFields['CARD_TYPE']}</b><br/>
                ****<span>${paymentInfo.additionalFields['LAST_FOUR']}</span><br/>
                exp. <span>${paymentInfo.additionalFields['EXP_MONTH']}/${paymentInfo.additionalFields['EXP_YEAR']}</span><br/>
            </div>
        </c:forEach>

    </div>

</t:base>