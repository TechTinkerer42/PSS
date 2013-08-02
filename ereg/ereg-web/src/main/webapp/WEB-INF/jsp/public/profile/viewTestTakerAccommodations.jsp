
<%@ page import="org.ets.ereg.common.web.form.CustomerAccommodationsForm" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="ct" uri="http://ereg.ets.org/commontags" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:base title="ETS - eREG (View Accommodation)">

<!-- Form Canvas starts here -->
	<div class="accomodationFilter">
	<div class="row">
			<div class="span9">
				<h1>
					Accommodations for <c:out value="${customer.firstName} ${customer.lastName}" />
				</h1>
			</div>
		</div>
		<c:url value="/secure/home" var="back_url"/>
	<ct:testTakerAccommodation testId="<%=CustomerAccommodationsForm.TEST_ID%>"  formDeliveryMode="<%=CustomerAccommodationsForm.DELIVERY_MODE%>" formStatus="<%=CustomerAccommodationsForm.ACCOMMODATION_STATUS%>" backURL="${back_url}"> </ct:testTakerAccommodation>
	</div>

</t:base>
<script type="text/javascript">

function callSubmit(form, url,isSubmit){


	form.action=url;	
	 if(isSubmit){	
		form.submit();
	}
	return isSubmit; 
	 
}

</script>