/* Start - Pagination*/

$(document).ready(function() {
	
	// Following functions has been added to toggle the search panel	
	 $("#searchCriteriaPanel").show();
	  $(".toggleMap").click(function () {
		  $(this).toggleClass("toggleMap1");
		  $(this).closest("form").find('.searchCriteriaPanel').slideToggle("slow");
	      $("#searchCriteriaPanel").slideToggle("slow");
	    });
		// Toggle panel script ends here
	  
	$(".submitButton").live("click", function() {
		var form = $("#"+this.id + "Form");
		form.validate();
		if(form.valid()){
			$( "span.errorSpan" ).empty();
			var target = this.id +"FormResultsTable";
			$("#"+this.id + "Form"+"ResultsTable").empty();
			ajaxSearch(this.id + "Form", 1,'criteriaUpdated');
			 $('#'+this.id+"Form").find('.searchCriteriaPanel').slideToggle("slow");
		}
	});

});

function ajaxSearch(idSearchForm, pageNo , action) {
	$('#'+idSearchForm).find('input[name="action"]').val(action);
	$('#'+idSearchForm).find('input[name="pageNo"]').val(pageNo);
	var form = $('form#' + idSearchForm).serialize();
	var formAction = $("#"+idSearchForm).attr("action");
	var target = idSearchForm+"ResultsTable";	

	//null success call back
	//ajaxPost(formAction,form, target,'POST',null);
	
	//Passing user defined successcallback
	ajaxPost(formAction,form, target,'POST',
			function(data) {
				var index=data.indexOf('validationErrors');
				if(index!=-1){
					if($('#'+idSearchForm).find('input[name="formType"]').val()=="candidateSearch") {
						$( "span.errorSpan" ).html(data);
						$('#'+idSearchForm).find('.searchCriteriaPanel').slideToggle("slow");						
					} else {
						//For appointmentSearch form validations
						$( "span.errorSpanAppointmentSearch" ).html(data);
						$('#'+idSearchForm).find('.searchCriteriaPanel').slideToggle("slow");
					}
					
					return;		    	
				}		
				$( "#"+target ).html(data);
			}			
	);
	
//Commenting the old ajax post	
//	$.ajax({
//		url : formAction,
//		type : 'post',
//		dataType : 'html',
//		data : form,
//		success : function(data) {
//			var index=data.indexOf('validationErrors');
//			    if(index!=-1){
//			    	$( "span.errorSpan" ).html(data);
//			    	$('#'+idSearchForm).find('.searchCriteriaPanel').slideToggle("slow");
//			    	 return;
//			    	
//			    }
//			
//			 $( "#"+target ).html(data);
//		},
//	});

}

function nextLink(pageNo) {
	ajaxSearch("searchForm", pageNo,"");

}

function prevLink(pageNo) {
	ajaxSearch("searchForm", pageNo,"");

}

function lastLink(pageNo) {
	ajaxSearch("searchForm", pageNo,"");

}

function firstLink(pageNo) {
	ajaxSearch("searchForm", pageNo,"");

}

function setRecordPerPage(){
	document.getElementById('rowperPage').value = document.getElementById('selRecordPerPage').value;
	ajaxSearch("searchForm", 1,"");
}


function loadFormCode(){
	var form = $("form#searchForm").serialize();
	var formAction = "loadFormCode";

	$.ajax({
		url : formAction,
		type : 'post',
		dataType : 'json',
		data : form,
		success : function(data) {
			processAjaxLoadFormData(data);
		},
		error: function (xhr, ajaxOptions, thrownError) {
			alert(xhr.status);
			alert(thrownError);
			alert(ajaxOptions);
		}	
	});
}


function loadTestCenter(){
	var form = $("form#searchForm").serialize();
	var formAction = "loadTestCenter";
	
	$.ajax({
		url : formAction,
		type : 'post',
		dataType : 'json',
		data : form,
		success : function(data) {
			processAjaxLoadCenterData(data);
		},
		error: function (xhr, ajaxOptions, thrownError) {
			alert(xhr.status);
			alert(thrownError);
			alert(ajaxOptions);
		}	
	});
	
}

function processAjaxLoadCenterData(data){
	for (col in data) {
		$('#testCenterId').append("<option value='" + data[col].id + "'>"+data[col].value+"</option>");
	}
}



function processAjaxLoadFormData(data){
	for (col in data) {
		$('#testFormCode').append("<option value='" + data[col].code + "'>"+data[col].value+"</option>");
	}
}


function pageLink(pageNo){
	ajaxSearch("searchForm", pageNo,"");
}


/* End -Pagination Search */

