$(document).ready(function(){

	// Folllowing functions has been added to toggle the search panel	
	 $("#searchCriteriaPanel").show();
	  $(".toggleMap").click(function () {
		  $(this).toggleClass("toggleMap1");
	      $("#searchCriteriaPanel").slideToggle("slow");
	    });
		// Toggle panel script ends here
	  
	  
	$('button[id=searchButton]').click(function() {
		
		var geocoder = new google.maps.Geocoder(); 
	//	alert ($('input[name=cityStateOrZipCode]').val() + ',' + $('select[id=country]').val());
		geocoder.geocode({
				//address : $('input[name=cityStateOrZipCode]').val(), 
			    address : $('input[name=cityStateOrZipCode]').val() + ',' + $('select[id=country]').val(),
				region: 'no' 
			},
		    function(results, status) {
		    	if (status.toLowerCase() == 'ok') {
					// Get center
					var coords = new google.maps.LatLng(
						results[0]['geometry']['location'].lat(),
						results[0]['geometry']['location'].lng()
					);
					$('#coords').html('Latitute: ' + coords.lat() + '    Longitude: ' + coords.lng() );
					
					$('#latitudeDegree').val(coords.lat());
					$('#longitudeDegree').val(coords.lng());
				//	alert (coords.lat()+','+coords.lng());
					map.setCenter(coords);
					map.setZoom(18);
 					// Set marker also
					marker = new google.maps.Marker({
						position: coords, 
						map: map, 
						title: $('input[name=cityStateOrZipCode]').val()
						
					});
					doSearch();
		    	}
		    	
			}
			
		);
	});
	
	
	$('button[id=searchButtonHome]').click(function() {
		
		var geocoder = new google.maps.Geocoder(); 
	//	alert ($('input[name=cityStateOrZipCode]').val() + ',' + $('select[id=country]').val());
		
		
		geocoder.geocode({
			
		//	address : $('input[name=cityStateOrZipCode]').val(),
		    address : $('input[name=cityStateOrZipCode]').val() ,
				region: 'no' 
			},
		    function(results, status) {
		    	if (status.toLowerCase() == 'ok') {
					// Get center
					var coords = new google.maps.LatLng(
						results[0]['geometry']['location'].lat(),
						results[0]['geometry']['location'].lng()
					);
					$('#coords').html('Latitute: ' + coords.lat() + '    Longitude: ' + coords.lng() );
					$('#latitudeDegree').val(coords.lat());
					$('#longitudeDegree').val(coords.lng());
		    	}
		    	//submit the form
		    	$('#searchFormHome').submit();
			}
			
		);
	});
	
	
	$("#firstPage").live("click", function(){
		var countOfRecs = parseInt($('#countOfRecs').val());
		var pageSize = parseInt($("#pageSize").val());
		var totalPages = Math.ceil(countOfRecs/pageSize);
		var indexOfFirstRowOfPage = 0;
		var pageNo = Math.floor(indexOfFirstRowOfPage/pageSize)+1;
		paginateResults(indexOfFirstRowOfPage, pageSize);
		$('#indexOfFirstRowOfPage').val(indexOfFirstRowOfPage);
		$("#firstPage").attr("disabled", pageNo==1);
		$("#previousPage").attr("disabled", pageNo==1);
		$("#pageNumber").html("Page " + pageNo + " of " + totalPages);
		$("#nextPage").attr("disabled", countOfRecs<=pageNo*pageSize);
		$("#lastPage").attr("disabled", countOfRecs<=pageNo*pageSize);
	});
	
	$("#previousPage").live("click", function(){
		var countOfRecs = parseInt($('#countOfRecs').val());
		var pageSize = parseInt($("#pageSize").val());
		var totalPages = Math.ceil(countOfRecs/pageSize);
		var indexOfFirstRowOfPage = parseInt($('#indexOfFirstRowOfPage').val()) - pageSize;
		var pageNo = Math.floor(indexOfFirstRowOfPage/pageSize)+1;
		if(pageNo > 0){
			paginateResults(indexOfFirstRowOfPage, pageSize);
			$('#indexOfFirstRowOfPage').val(indexOfFirstRowOfPage);
			$("#firstPage").attr("disabled", pageNo==1);
			$("#previousPage").attr("disabled", pageNo==1);
			$("#pageNumber").html("Page " + pageNo + " of " + totalPages);
			$("#nextPage").attr("disabled", countOfRecs<=pageNo*pageSize);
			$("#lastPage").attr("disabled", countOfRecs<=pageNo*pageSize);
		}
	});
	
	$("#nextPage").live("click", function(){
		var countOfRecs = parseInt($('#countOfRecs').val());
		var pageSize = parseInt($("#pageSize").val());
		var totalPages = Math.ceil(countOfRecs/pageSize);
		var indexOfFirstRowOfPage = parseInt($('#indexOfFirstRowOfPage').val()) + pageSize;
		var pageNo = Math.floor(indexOfFirstRowOfPage/pageSize)+1;
		if(pageNo <= totalPages){
			paginateResults(indexOfFirstRowOfPage, pageSize);
			$('#indexOfFirstRowOfPage').val(indexOfFirstRowOfPage);
			$("#firstPage").attr("disabled", pageNo==1);
			$("#previousPage").attr("disabled", pageNo==1);
			$("#pageNumber").html("Page " + pageNo + " of " + totalPages);
			$("#nextPage").attr("disabled", countOfRecs<=pageNo*pageSize);
			$("#lastPage").attr("disabled", countOfRecs<=pageNo*pageSize);
		}
	});
	
	$("#lastPage").live("click", function(){
		var countOfRecs = parseInt($('#countOfRecs').val());
		var pageSize = parseInt($("#pageSize").val());
		var totalPages = Math.ceil(countOfRecs/pageSize);
		var indexOfFirstRowOfPage = (totalPages-1)*pageSize;
		var pageNo = Math.floor(indexOfFirstRowOfPage/pageSize)+1;
		paginateResults(indexOfFirstRowOfPage, pageSize);
		$('#indexOfFirstRowOfPage').val(indexOfFirstRowOfPage);
		$("#firstPage").attr("disabled", pageNo==1);
		$("#previousPage").attr("disabled", pageNo==1);
		$("#pageNumber").html("Page " + pageNo + " of " + totalPages);
		$("#nextPage").attr("disabled", countOfRecs<=pageNo*pageSize);
		$("#lastPage").attr("disabled", countOfRecs<=pageNo*pageSize);
	});
	
	$("#pageSize").change(function(){
		var pageSize=parseInt($("#pageSize").val());
		var countOfRecs=parseInt($('#countOfRecs').val());
		var index=0;
		$('#searchResultsTable tbody tr').each(function () {
			if(index < pageSize){
				$(this).removeClass("hidden");
			}else{
				$(this).addClass("hidden");
			}	
			index++;
		});
		$('#indexOfFirstRowOfPage').val(0);
		$("#firstPage").attr("disabled", true);
		$("#previousPage").attr("disabled", true);
		$("#pageNumber").html("Page 1 of " + Math.ceil(countOfRecs/pageSize));
		if(countOfRecs <= pageSize){
			$("#nextPage").attr("disabled", true);
			$("#lastPage").attr("disabled", true);
		}
		else{
			$("#nextPage").attr("disabled", false);
			$("#lastPage").attr("disabled", false);
		}
	});
});

function doSearch(){
	$('#count').val(-1);
	$('#offset').val(0);
	
 	var form=$('form#searchForm').serialize();
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
	        			processSearchResult(target, data);
	        			reloadMap(data);
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
        $("#statusPanel").html(errorDiv);
    }
}

function paginateResults(startIndex, numOfRows){
	var index=0;
	var count=1;
	$('#searchResultsTable tbody tr').each(function () {
		if(index >= startIndex && count <= numOfRows){
			$(this).removeClass("hidden");
			count++;
		}else{
			$(this).addClass("hidden");
		}
		index++;
	});
}

function processSearchResult(target, data) {
	if(null == data){
		$("#searchResultPanel").attr('hidden', true);
		$("#statusPanel").html('');
	}
	else{
		if(data.hasErrors){
			showErrorMessages(data.errors);
		}
		else if(0 < data.count){
			$("#searchResultPanel").attr('hidden', false);
			var $srchFldSet = $('#searchResultsTable').closest('fieldset');
			var latitudeDegree = $('#latitudeDegree').val();
			var longitudeDegree = $('#longitudeDegree').val();
			var dataColsArray = [];
			$('#indexOfFirstRowOfPage').val(0);
			$('#countOfRecs').val(data.count);
			$('#searchResultsTable thead th').each(function () {
				var colId = $(this).attr("id");
				dataColsArray.push(colId);
			});
			$srchFldSet.removeClass('hidden').slideDown(1000);
			var rowNumber = 1;
			var pageSize = parseInt($("#pageSize").val());
			var output = "<tbody>";
			$(data.searchedElements).each(function () {
				if(rowNumber <= pageSize){
					output += '<tr class="visible">';
				}else{
					output += '<tr class="hidden">';
				}
				for (col in dataColsArray) {
					if(dataColsArray[col] == 'srNo' ){
						output +='<td>' + rowNumber +'</td>';
					}
					else if(dataColsArray[col] == 'name'){
						output += '<td>';
						output += '<a href="' + data.link + this.id +'/?distance=' + this.distanceMile + '&latitudeDegree=' + latitudeDegree + '&longitudeDegree=' + longitudeDegree + '">'+ this.name + '</a>';
						output += '<br>' + this.addressLine1;
						if(null != this.addressLine2){
							output += '<br>' + this.addressLine2;
						}
						if(null != this.addressLine3){
							output += '<br>' + this.addressLine3;
						}
						output += '<br>' + this.city + ', ' + this.stateAbbreviation + ' ' + this.postalCode;
						output += '<br>' + this.countryAbbreviation;
						output += '<br>Phone: ' + ((null != this.phoneNumber && this.phoneNumber.length == 10)?('(' + this.phoneNumber.substr(0,3) + ') ' + this.phoneNumber.substr(3,3) + '-' + this.phoneNumber.substr(6)):'') + ((null != this.phoneExtension && this.phoneExtension.length > 0)?(' x' + this.phoneExtension):'');
						if(null != this.deliveryModes && this.deliveryModes.length > 0){
							output += '<br>' + this.deliveryModes[0];
							for(var i = 1; i < this.deliveryModes.length; i++){
								output += ', ' + this.deliveryModes[i];
							}
						}
						else{
							output += '<br> Unknown Test Type';
						}
						if (this.schedulingType == "TCAM") {
							output += '<br><span class="callTestCenter">Please call the test center to schedule appointment(s).</span><br><br>';
						} else if (this.schedulingType == "CANDM") {
							output += '<br>' + '<a class="linkButton" href="../../appointment/before?testCenterId=' + this.id + '">Schedule Appointment</a>' + '<br><br>';
						}
						output += '</td>';
					}
					else if(dataColsArray[col]=='distance'){
						output +='<td>' + this.distanceMile.toFixed(1) + ' mi' + '</td>';
					}
				}
				output += '</tr>';
				rowNumber++;
			});
			output += "</tbody>";
			if ($("#" + target + " tbody").html()) {
				$("#" + target + " tbody").remove();
			}
			$("#" + target + " thead").after(output);
			
			//initialize page navigation bar
			$("#firstPage").attr("disabled", true);
			$("#previousPage").attr("disabled", true);
			$("#pageNumber").html("Page 1 of " + Math.ceil(data.count/pageSize));
			if(data.count <= pageSize){
				$("#nextPage").attr("disabled", true);
				$("#lastPage").attr("disabled", true);
			}
			else{
				$("#nextPage").attr("disabled", false);
				$("#lastPage").attr("disabled", false);
			}
			
			//remove status message
			$("#statusPanel").html('');
		}else if(data.count==0){
			var statusMessage = '<ul><li><span>' + 'No test centers found near the location you provided. Change search criteria and try again.' + '</span></li></ul>';
	        $("#statusPanel").html(statusMessage);
			$("#searchResultPanel").attr('hidden', true);
		}
	}
}