/* ===================================================
 *  Written by Irfan Ali
 
 * ========================================================== */


	

$(function() {
    $(".radiocheck").change(function() {
        if ($("#male").is(":checked")) {
            $(".radiostarcolor").css('backgroundImage', "url('../../commonweb/img/valid.png')");
        }
        if ($("#fmale").is(":checked")) {
            $(".radiostarcolor").css('backgroundImage', "url('../../commonweb/img/valid.png')");
        }
       
          if ($("#landline").is(":checked")) {
            $(".radiophonecolor").css('backgroundImage', "url('../../commonweb/img/valid.png')");
        }
		 if ($("#mobile").is(":checked")) {
            $(".radiophonecolor").css('backgroundImage', "url('../../commonweb/img/valid.png')");
        }
       
	      if ($("#lSpanish").is(":checked")) {
            $(".languageRadio").css('backgroundImage', "url('../../commonweb/img/valid.png')");
        }
		 if ($("#lEnglish").is(":checked")) {
            $(".languageRadio").css('backgroundImage', "url('../../commonweb/img/valid.png')");
        }

         if ($("#termsConditions").is(":checked")) {
            $(".checkConditions").css('backgroundImage', "url('../../commonweb/img/valid.png')");
        }
		else {
            $(".checkConditions").css('backgroundImage', "url('../../commonweb/img/red_asterisk.png')");
        }

         if ($("#mmy").is(":checked")) {
            $(".militaryRadio").css('backgroundImage', "url('../../commonweb/img/valid.png')");
        }
		 if ($("#mmn").is(":checked")) {
            $(".militaryRadio").css('backgroundImage', "url('../../commonweb/img/valid.png')");
        }
		if ($("#csr_RM").is(":checked")) {
			$(".radiostarcolor").css('backgroundImage', "url('../../commonweb/img/valid.png')");
		}
		if ($("#csr_RF").is(":checked")) {
			$(".radiostarcolor").css('backgroundImage', "url('../../commonweb/img/valid.png')");
		}
   });


});




$(document).ready(function(){


$('input:radio[id="mmy"]').click(function(){
		$("#militaryStatus").attr("disabled", false);
});

$('input:radio[id="mmn"]').click(function(){
//	alert ('Disabled');
	$("#militaryStatus").attr("disabled", true);
});


if ($('input:radio[id="mmn"]').is(":checked")) {
	$("#militaryStatus").attr("disabled", true);
}





$('input:radio[id="profile.customer.militaryMemberShip1"]').click(function(){
//	alert ('Disabled');
	$("#militaryStatus").attr("disabled", false);
});

$('input:radio[id="profile.customer.militaryMemberShip2"]').click(function(){
$("#militaryStatus").attr("disabled", true);
});


if ($('input:radio[id="profile.customer.militaryMemberShip2"]').is(":checked")) {
	$("#militaryStatus").attr("disabled", true);
}


});

function ajaxPost(serviceURL, form, target, method, funcInSuccessCallback, errorCallback, datatype,
		funcInCallback, funcInCallbackParam, timeoutValue) {
		
		//Default success 		
		successCallback = function(data, textStatus, jqXHR) {
			var $response=$(data);
			var result = $("#errorPage", data);
			if(result.length!=0) {
				document.open();
				document.write(data);
				document.close();
				} else {				
					if (funcInSuccessCallback) {
						funcInSuccessCallback(data);
					} else {			
						$("#" + target).html(data);
					}
				}			
			};		
	
	//Default errorCallback
	if (!errorCallback) {
		errorCallback = function(xhr, ajaxOptions, thrownError) {
		var pathURL = window.location.pathname.split( '/' );
		$.ajax({
			url: window.location.protocol + "//" + window.location.host + "/" + pathURL[1] + "/errorcontroller/error",
			type : "POST",
			dataType : 'html',
			data :  "data=" + xhr.responseText, 		
			success : function(data) {
				document.open();
				document.write(data);
				document.close();
			},			
			async : true,
			cache: false
		});
		
		};
	}
	if (!timeoutValue) {
		/*
		 * default timeout to 3 minutes
		 */
		timeoutValue = 300000;
	}
	
	if (!method) {
		/*
		 * default post
		 */
		method = 'post';
	}

	if (!datatype) {
		/*
		 * default html
		 */
		datatype = 'html';
	}
	
	$.ajax({
		url : serviceURL,
		type : method,
		dataType : datatype,
		data : form, 
		success : successCallback,
		error : errorCallback,
		timeout : timeoutValue,
		async : true
	});

}



