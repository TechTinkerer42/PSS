<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:base title="Catalog">

    <div class="headContainer">

        <div class="row">
            <div class="span9">
                <h1>Catalog</h1>
            </div>
        </div>

    </div>


    <div id="formContainer">

        <ul>
            <c:forEach items="${products}" var="product">
                <c:if test = "${product.id < 6}">
                    <li>${product.name} - <a href="<c:url value='/cart/add?productId=${product.id}&amp;quantity=1&amp;itemAttributes[\'TEST_TYPE\']=Paper'/>">Add To Cart (Paper)</a> | <a href="<c:url value='/cart/add?productId=${product.id}&amp;quantity=1&amp;itemAttributes[\'TEST_TYPE\']=Computer'/>">Add To Cart (Computer)</a></li>
                </c:if>
            </c:forEach>
        </ul>

    </div>

</t:base>