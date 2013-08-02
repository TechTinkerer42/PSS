// JavaScript Document

          /*(function($,W,D)
{
    var JQUERY4U = {};

    JQUERY4U.UTIL =
    {
        setupFormValidation: function()
        {
            //form validation rules
            $("#createProfile-form").validate({
                rules: {
                    firstname: "required",
                    lastname: "required",
                    address1: "required",
					city: "required",
					zip: "required",
					pPhone: "required",
					answer: "required",
                    email: {
                        required: true,
                        email: true
                    },
                    password: {
                        required: true,
                        minlength: 5
                    },
                    agree: "required"
                },
                messages: {
                    firstname: "Please enter firstname",
                    lastname: "Please enter lastname",
                    address1: "Please enter address",
                    city: "Please enter city",
					zip: "Please enter zipcode",
					pPhone: "Please enter valid phone number",
					answer: "Please enter answer",
					password: {
                        required: "Please provide a password",
                        minlength: "Your password must be at least 5 characters long"
                    },
                    email: "Please enter a valid email",
                    agree: "Please accept our policy"
                },
                submitHandler: function(form) {
                    form.submit();
                }


            });
        }
    }

    //when the dom has loaded setup form validation rules
    $(D).ready(function($) {
        JQUERY4U.UTIL.setupFormValidation();
    });

})(jQuery, window, document);*/