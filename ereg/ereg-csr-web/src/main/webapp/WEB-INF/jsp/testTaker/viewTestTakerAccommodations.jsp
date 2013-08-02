
<%@ page
	import="org.ets.ereg.common.web.form.CustomerAccommodationsForm"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="ct" uri="http://ereg.ets.org/commontags" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>

<t:base title="ETS - eREG (View Accommodation)">





	<ct:modalWindow id="dialog-confirm" buttonName="Continue"
		buttonMethod="addAccommodation" title="Add Accommodations"></ct:modalWindow>

	<ct:modalWindow id="dialog-remove" buttonName="OK"
		buttonMethod="removeAccommodation" title="Remove Accommodation"></ct:modalWindow>
	<ct:modalWindow id="dialog-edit" buttonName="OK"
		buttonMethod="editAccommodation" title="Edit Accommodation"></ct:modalWindow>

	<!-- Form Canvas starts here -->
	<div class="accomodationFilter">
		<div class="row">
			<div class="span9">
				<h1>
					<spring:message code="accommodations.view.heading1"/>
					<c:out value="${firstName} ${lastName}" />
				</h1>
			</div>
		</div>
		<c:out value="${operationMsg}"/>
		<c:url value="${backAction}" var="back_url"/>
		
		<ct:testTakerAccommodation
			testId="<%=CustomerAccommodationsForm.TEST_ID%>"
			formDeliveryMode="<%=CustomerAccommodationsForm.DELIVERY_MODE%>"
			formStatus="<%=CustomerAccommodationsForm.ACCOMMODATION_STATUS%>"
			backURL="${back_url}">
		</ct:testTakerAccommodation>
	</div>

</t:base>
<script type="text/javascript">
	function callSubmit(form, url, isSubmit) {

		form.action = url;
		if (isSubmit) {
			form.submit();
		}
		return isSubmit;

	}

	$(function() {
		$('button.modalWindow').on('click', function() {

			var tr = $(this).closest('tr');
			var formAction = $("#accommodationForm").attr("action");

			var form = $('form#accommodationForm').serialize();

			$.ajax({
				url : formAction,
				type : "post",
				data : form,
				success : function(data) {
					$("#dialog-confirm").html(data);
					$("#dialog-confirm").dialog("open");
					$("#dialog-confirm").dialog('option', 'width', 450);
				},
				error : function() {

					$("#result").html('there is error while submit');
				}
			});

		});

	});
	$(function() {
		$('a.removeModalWindow').on('click', function(e) {

			e.preventDefault();

			$('#dialog-remove').load(this.href).dialog('open');
			$("#dialog-remove").dialog('option', 'width', 450);

			return false;

		});

	});

	$(function() {
		$('a.editModalWindow').on('click', function(e) {
			e.preventDefault();

			$('#dialog-edit').load(this.href).dialog('open');
			$("#dialog-edit").dialog('option', 'width', 450);

			return false;

		});

	});
</script>