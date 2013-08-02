<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:base title="Cart">

    <div class="headContainer">

        <div class="row">
            <div class="span9">
                <h1>Cart</h1>
            </div>
        </div>

    </div>


    <div id="reviewformContainer">

        <ul>
            <c:forEach items="${cart.orderItems}" var="item">
                <li>
                    ${item.product.name} |
                    qty: ${item.quantity} |
                        <!--<a href="<c:url value='/cart/remove?orderItemId=${item.id}'/>">(Remove)</a>-->
                </li>
            </c:forEach>
        </ul>

        Total:$ ${cart.total}

        <hr/>
        
        <form action="${hopFields['cybersourceServerUrl']}" method="POST">

            <fieldset>
                <legend>ETS Required Cybersource Shipping Fields</legend>
                <b>TODO: per J. Jones - This should be taken from the user's Profile (3/29/13)</b><br/>
                First Name: <input type="text" name="shipTo_firstName" value="John"/> <br/>
                Last Name: <input type="text" name="shipTo_lastName" value="Smith"/> <br/>
                Street: <input type="text" name="shipTo_street1" value="1295 Charleston Rd"/> <br/>
                City: <input type="text" name="shipTo_city" value="Mountain View"/> <br/>
                State: <input type="text" name="shipTo_state" value="CA"/> <br/>
                Postal Code: <input type="text" name="shipTo_postalCode" value="94043"/> <br/>
                Country: <input type="text" name="shipTo_country" value="US"/>
            </fieldset>

            *ETS Required Cybersource Fields are Hidden Inputs<br/>
            <input type="hidden" name="originatingSystem_code" value="${hopFields['originatingSystem_code']}"/>
            <input type="hidden" name="originatingSystem_sessionId" value="${hopFields['originatingSystem_sessionId']}"/>
            <input type="hidden" name="modeOfReceipt" value="${hopFields['modeOfReceipt']}"/>
            <input type="hidden" name="context" value="${hopFields['context']}"/>
            <input type="hidden" name="project" value="${hopFields['project']}"/>
            <input type="hidden" name="invoiceHeader_merchantDescriptor" value="${hopFields['invoiceHeader_merchantDescriptor']}"/>
            <input type="hidden" name="amount" value="${hopFields['amount']}"/>
            <input type="hidden" name="currency" value="${hopFields['currency']}"/>
            <input type="hidden" name="orderPage_timestamp" value="${hopFields['orderPage_timestamp']}"/>
            <input type="hidden" name="merchantID" value="${hopFields['merchantID']}"/>
            <input type="hidden" name="merchant_successURL" value="${hopFields['merchant_successURL']}"/>
            <input type="hidden" name="merchant_rejectURL" value="${hopFields['merchant_rejectURL']}"/>
            <input type="hidden" name="merchant_errorURL" value="${hopFields['merchant_errorURL']}"/>
            <input type="hidden" name="merchant_timeoutURL" value="${hopFields['merchant_timeoutURL']}"/>
            <input type="hidden" name="orderPage_signaturePublic" value="${hopFields['orderPage_signaturePublic']}"/>
            <input type="hidden" name="orderPage_version" value="${hopFields['orderPage_version']}"/>
            <input type="hidden" name="orderPage_serialNumber" value="${hopFields['orderPage_serialNumber']}"/>
            <input type="hidden" name="orderPage_signedFields" value="${hopFields['orderPage_signedFields']}">
            <input type="submit" value="Continue"/>
        </form>

        <form action="http://localhost:8080/ereg-web/cybersource/zerocheckout"  method="POST">
            <input type="submit" value="Zero Checkout"/>
        </form>

    </div>

</t:base>