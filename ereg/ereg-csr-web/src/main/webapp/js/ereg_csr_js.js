/* Start - COMMON JAVASCRIPT FOR eREG - CSR */
	/* Start - DATA TABLES FOR CANDIDATE SEARCH */
function getMoreResults(integer) {
if(integer==-1){
var count=$("#recordCount").val();
var numberofRecords=$("#numberofRecords").val();
var total=parseInt(count)-parseInt(numberofRecords);
$("#recordCount").val(total);
}else if(integer==1){
var count=$("#recordCount").val();
var numberofRecords=$("#numberofRecords").val();
var total=parseInt(count)+parseInt(numberofRecords);
$("#recordCount").val(total);

}
    ajaxSearch();
}

function showPopup(toggle,id,wid,left,top)
{
if(toggle=="1")
{

$(id).css("display","block");
$(id).animate({"opacity": "1","width":wid,"left": left,"top": top}, "slow");

}
if(toggle=="0")
{
$(id).animate({"opacity": "0","width":"0","left": "0px","top": "0px"}, "slow");
$(id).css("display","none");
}
}

function clearInputFields(updatableObj) {
    $(updatableObj).find(":input").each(function () {
        var type = this.type;
        var tag = this.tagName.toLowerCase();
        if (type == 'text' || type == 'password' || tag == 'textarea') {
            this.value = "";
        } else if (type == 'checkbox' || type == 'radio') {
            this.checked = false;
        } else if (tag == 'select') {
            this.selectedIndex = 0;
        }
    });
}


$(document).ready(function(){


// This is test

$('#nextButton').click(function() {

				var message = "";
				var i = 0;
				var errorField = new Array();

				if(!document.getElementById('<%=ProfileForm.FIRST_NAME%>').validity.valid)
									errorField[i++] = "First or Given Name" ;

				if(!document.getElementById('<%=ProfileForm.LAST_NAME%>').validity.valid)
					errorField[i++] = "Last or Family Name" ;

				if(!document.getElementById('<%=ProfileForm.DATE_OF_BIRTH%>').validity.valid)
					errorField[i++] = "Date of Birth" ;

				if(!document.getElementById('<%=ProfileForm.SOCIAL_SECURITY%>').validity.valid)
					errorField[i++] = "Social Security Number " ;

				if(!document.getElementById('<%=ProfileForm.EMAIL_ADDRESS%>').validity.valid)
					errorField[i++] = "Email" ;

				if(!document.getElementById('<%=ProfileForm.COUNTRY%>').validity.valid)
					errorField[i++] = "Country/Location" ;

				if(!document.getElementById('<%=ProfileForm.ADDRESS_LINE1%>').validity.valid)
					errorField[i++] = "Address Line 1" ;

				if(!document.getElementById('<%=ProfileForm.CITY%>').validity.valid)
					errorField[i++] = "City" ;

				if(!document.getElementById('<%=ProfileForm.STATE%>').validity.valid)
					errorField[i++] = "State" ;

				if(!document.getElementById('<%=ProfileForm.POSTAL_CODE%>').validity.valid)
					errorField[i++] = "ZIP Code" ;

				if(!document.getElementById('primaryPhoneCountryCode').validity.valid)
					errorField[i++] = "Primary Phone Country Code" ;

				if(!document.getElementById('<%=ProfileForm.PRIMARY_PHONE_PHONE_NUMBER%>').validity.valid)
					errorField[i++] = "Primary Phone" ;

				if(errorField.length >0){
					for(i=0; i<errorField.length; i++){
						message = message + "<li>"+ errorField[i]+"</li>";
					}
					message = "<ul>" + message + "</ul>"
					message = "Please check the following fields:" + message;
					$("#frontEndErrors").html(message);
					$("#frontEndErrors").show();
				}
			});
// test ends







$(".submitButton").live("click", function(){
	//alert(this.id);
// $('#recordCount').val(parseInt($('#recordCount').val())+5);
    ajaxSearch(this.id+"Form");
});

$("#nextButton").click(function(){
var startIndex=$('#dataTableCount').val();
var selectedIndex=$("#lengthOfTable").val();
paginateResults(parseInt(startIndex)+parseInt(selectedIndex),parseInt(selectedIndex));
$('#dataTableCount').val(parseInt(startIndex)+parseInt(selectedIndex));
var countOfCachedDT=$('#dataTableCount').val();
var countOfRecs=$('#countOfRecs').val();
if(countOfRecs<200 && parseInt(countOfCachedDT)+parseInt(selectedIndex)>=countOfRecs){
$("#nextButton").attr("disabled", true);
}
//has more results
if(countOfRecs==200){
	getMoreResults(1);
}

$("#prevButton").attr("disabled", false);
});

$("#prevButton").click(function(){
var startIndex=$('#dataTableCount').val();
var selectedIndex=$("#lengthOfTable").val();
var diff=parseInt(startIndex)+parseInt(-selectedIndex);
paginateResults(diff,parseInt(selectedIndex));
$('#dataTableCount').val(parseInt(startIndex)-parseInt(selectedIndex));
var count=$('#dataTableCount').val();
var countOfRecs=$('#countOfRecs').val();
if(count==0){
$("#prevButton").attr("disabled", true);
}
if(countOfRecs==0){
	getMoreResults(-1);
}
$("#nextButton").attr("disabled", false);
});


$("#searchResultsTable_length").change(function(){
var index=$('#searchResultsTable tr.visible:last').index();
var selectedIndex=$("#lengthOfTable").val();
var index=$('#searchResultsTable tr.hidden:first').index();
var idx=0;
$('#searchResultsTable tbody tr').each(function () {
if(idx<selectedIndex){
$(this).removeClass("hidden");
}else{
$(this).addClass("hidden");
}
idx++;
});
var countOfCachedDT=$('#dataTableCount').val();
var countOfRecs=$('#countOfRecs').val();
if( parseInt(countOfCachedDT)+parseInt(selectedIndex)>=countOfRecs){
$("#nextButton").attr("disabled", true);
}else{
$("#nextButton").attr("disabled", false);
}
$("#prevButton").attr("disabled", true);
$('#dataTableCount').val(0);

});
});

function paginateResults(startIndex,numOfRows){
var flag=false;
var idx=0;
var count=1;
$('#searchResultsTable tbody tr').each(function () {
if(idx>=startIndex && count<=numOfRows){
$(this).removeClass("hidden");
count++;

}else{
$(this).addClass("hidden");
}
idx++;
});



}

function ajaxSearch(idSearchForm){

$('.numberofRecords').val(200);
    var form=$('form#'+idSearchForm).serialize();
    var formAction = $("#searchForm").attr("action");
var target = "searchResultsTable";
var $srchFldSet = $('#searchResultsTable').closest('fieldset');
$srchFldSet.addClass('hidden').slideUp(1000);

    $.ajax({
        url: formAction,
        type: 'post',
        dataType: 'json',
        data: form,
        success: function(data) {
         showPopup(0, "#searching", 0, 0, 0);
         processAjaxSearchData(target, data);
                 },
        beforeSend: function (data) {

                     showPopup(1, "#searching", '17%', "521.5px", "400.3px");


        }
    });



}



function showErrorMessages( errorMessages) {
    if (errorMessages.length > 0) {

        var errorDiv = '<div>';
        errorDiv += '<h2>Error Summary</h2>';
        errorDiv += '<div class="info">';
        errorDiv += '<ul>';
        for (var i = 0; i < errorMessages.length; i++) {
            errorDiv += '<li><span>' + errorMessages[i] + '</span></li>';
        }
        errorDiv += '</div>';
        errorDiv += '</div>';
        $("#errorSpan").after(errorDiv);
    }
}


function processAjaxSearchData(target, data) {
var output = "";
$('#dataTableCount').val(0);
$('#countOfRecs').val(data.count);
$("#prevButton").attr("disabled", true);
$("#resultCount-" + target).html("Total Count:"+data.count);
var $srchFldSet = $('#searchResultsTable').closest('fieldset');
var countOfRecs=$('#countOfRecs').val();

var selectedIndex=$("#lengthOfTable").val();

var dataColsArray = [];
$('#searchResultsTable thead th').each(function () {
    var colId = $(this).attr("id");
    dataColsArray.push(colId);
});

if(parseInt(countOfRecs)<=parseInt(selectedIndex)){

$("#nextButton").attr("disabled", false);

}



if(data.count>0){

         $srchFldSet.removeClass('hidden').slideDown(1000);
output = "<tbody>";
var index=1;
$(data.customers).each(function () {
	var oddEvenSel=index%2==0?'even':'odd';
if(index<=10){
output += '<tr class="visible '+oddEvenSel+'">';
}else{
output += '<tr class="hidden '+oddEvenSel+'">';
}
index++;
for (col in dataColsArray) {
var idVal=this[data.idColumnName];
if(dataColsArray[col]=='name'){
var column = this[dataColsArray[col]];
output +='<td><a href="'+data.link +'?customerId='+idVal+'">'+ column + '</td>';
}
else if(dataColsArray[col]!='address' && dataColsArray[col]!='city'
&& dataColsArray[col]!='state' && dataColsArray[col]!='postalCode'){
var column = this[dataColsArray[col]];
output += '<td>' + column + '</td>';

} else if(jQuery.type( this[dataColsArray[col]])=="array"){
	var dataArr=this[dataColsArray[col]];
	$.each(this[dataColsArray[col]], function(index, value) {
		if(index==0){
			output += '<td title="'+dataArr+'">' + value + '</td>';
		}

		});
}

}
output += '</tr>';
});
output += "</tbody>";
if ($("#" + target + " tbody").html()) {
$("#" + target + " tbody").remove();
}

$("#" + target + " thead").after(output);
$( document ).tooltip();


}else if(data.count==0){
output = "<tbody>";
        output += '<tr><td align="center" colspan='+dataColsArray.length+'>No results returned.</td></tr>';
        output += '</tbody>';
        $("#" + target + " tbody").remove();
        $("#prevButton").attr("disabled", true);
        $("#nextButton").attr("disabled", true);
        $("#" + target + " thead").after(output);
        $srchFldSet.removeClass('hidden').slideDown(1000);

}else if(data.errors){
showErrorMessages(data.errorList);

}

}
	/* End - DATA TABLES FOR CANDIDATE SEARCH */
	/* Start - SCRIPT FOR ACCORDION WIDGET */
		$(function() {
				$( "#tabs" ).tabs();
			});

			$(function() {
				$( "#accordion" ).accordion();
			});
		//Start Toggle Function
			$(function() {
			// run the currently selected effect
			function runEffect() {
			// get effect type from
			var selectedEffect = $( "#effectTypes" ).val();

			// most effect types need no options passed by default
			var options = {};
			// some effects have required parameters
			if ( selectedEffect === "scale" ) {
			options = { percent: 0 };
			} else if ( selectedEffect === "size" ) {
			options = { to: { width: 200, height: 60 } };
			}

			// run the effect
			$( "#effect" ).toggle( selectedEffect, options, 500 );
			};

			// set effect from select menu value
			$( "#button" ).click(function() {
			runEffect();
			return false;
			});
			});
		//End Toggle Function
	/* End - SCRIPT FOR ACCORDION WIDGET */




	/* Start - SCRIPT FOR APPOINTMENT INFO */
		var valSubject = document.getElementById("apptinfo_enable");
		valSubject.onchange = function () {
			if (this.value != "" || this.value.length > 0) {
				document.getElementById("apptinfo_disable").disable = false;
			}
		}
	/* End - SCRIPT FOR APPOINTMENT INFO */

	/* Start - DISPLAY TITLE IN ACCORDION UPON DEACTIVATED */
	$(function() {

		$('#accordion').on('hide', function (e) {
			 $(e.target).prev('.accordion-heading').find('.accordion-toggle').addClass('active');
		});

		$('#accordion').on('show', function (e) {
			$(this).find('.accordion-toggle').not($(e.target)).removeClass('active');
		});

	});
	/* End - DISPLAY TITLE IN ACCORDION UPON DEACTIVATED */

	/* Start - VALIDATION SCRIPT FOR RADIO BUTTONS */

	/* Start - VALIDATION SCRIPT FOR RADIO BUTTONS */


/* End - COMMON JAVASCRIPT FOR eREG - CSR */