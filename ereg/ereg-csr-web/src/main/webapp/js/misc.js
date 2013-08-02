/* ===================================================
 *  Written by Irfan Ali

 * ========================================================== */
$(document).ready(function(){

$(function() {
    $(".radiocheck").change(function() {
        if ($("#male").is(":checked")) {
            $(".radiostarcolor").css('backgroundImage', "url('../img/valid.png')");
        }
        if ($("#fmale").is(":checked")) {
            $(".radiostarcolor").css('backgroundImage', "url('../img/valid.png')");
        }

          if ($("#landline").is(":checked")) {
            $(".radiophonecolor").css('backgroundImage', "url('../img/valid.png')");
        }
		 if ($("#mobile").is(":checked")) {
            $(".radiophonecolor").css('backgroundImage', "url('../img/valid.png')");
        }

	      if ($("#lSpanish").is(":checked")) {
            $(".languageRadio").css('backgroundImage', "url('../img/valid.png')");
        }
		 if ($("#lEnglish").is(":checked")) {
            $(".languageRadio").css('backgroundImage', "url('../img/valid.png')");
        }

         if ($("#termsConditions").is(":checked")) {
            $(".checkConditions").css('backgroundImage', "url('../img/valid.png')");
        }
		else {
            $(".checkConditions").css('backgroundImage', "url('../img/red_asterisk.png')");
        }

         if ($("#mmy").is(":checked")) {
            $(".militaryRadio").css('backgroundImage', "url('../img/valid.png')");
        }
		 if ($("#mmn").is(":checked")) {
            $(".militaryRadio").css('backgroundImage', "url('../img/valid.png')");
        }


   });


});






});

