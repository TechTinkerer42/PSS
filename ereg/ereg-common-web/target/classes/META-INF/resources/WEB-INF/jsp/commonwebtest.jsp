<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="ct" uri="http://ereg.ets.org/commontags" %>
<t:base title="test page">

<div class="headContainer ">

<p>This is commonwebtest.jspasdasdasdasdas</p>

  <h2>???????Message from properties:<spring:message code="test.msg"/></h2>
<a href="/ereg-web/secure/home">Go home</a>

</div>

<!-- Form Canvas starts here -->
<div class="formContainer1">
This content goes in the base tag body
<img  src='<spring:url value="/commonweb/img/commonimg.jpg" htmlEscape="true"/>' alt="Image from Common">
<div><a href="javascript:commonTest();">click to invoke function from common js</a></div>
</div> <!--  Form Canvas ends here -->

</t:base>
<ct:test>This content goes in the Common tag body</ct:test>