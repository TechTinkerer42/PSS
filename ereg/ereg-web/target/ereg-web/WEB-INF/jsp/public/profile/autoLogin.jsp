<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:base title="Create Profile - Account Created">



<div class="center">
			


<p></p>

<br><br>

<spring:message code="accountcreated.successmessage" />

Next Step:<br><br>


<b>.Prepare for the test</b><br>
Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat. <br><br>


<b>.Schedule your test</b><br>
Lorem ipsum dolor sit amet, <a href="?">find the nearest test center</a> to schedule an appointment.



</p>

<form name='autoLogin' method='POST' action=${actionValue}  > 

 
<input type='hidden' name=${username} value=${userid} id="username">
<input type='hidden' name=${password}  value=${userpassword} id="password"/>

<input type="hidden" name=${lockpage} value=${lockpageValue}/>
<input type="hidden" name=${expiredpwdpage} value=${expiredpwdpageValue}/>
<button type="submit" class="submit">Go to My Homepage</button>
</form>







		</div>


</body></t:base>
