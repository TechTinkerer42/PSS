/* Start - view Ordes Search */

$(document).ready(function() {
	ajaxSearch("searchForm", 1 ,'criteriaUpdated');

});


function loadSearchResults(){
	ajaxSearch("searchForm", 1 ,'criteriaUpdated');
}

function ajaxSearch(idSearchForm, pageNo , action) {
	$('#'+idSearchForm).find('input[name="action"]').val(action);
	$('#'+idSearchForm).find('input[name="pageNo"]').val(pageNo);
	var form = $('form#' + idSearchForm).serialize();
	var formAction = $("#"+idSearchForm).attr("action");
	var target = idSearchForm+"ResultsTable";	
	ajaxPost(formAction,form, target,'POST',
			function(data) {
				$( "#"+target ).html(data);
			}			
	);
	


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




