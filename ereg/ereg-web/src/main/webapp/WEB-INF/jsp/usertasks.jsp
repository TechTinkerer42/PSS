
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="javax.servlet.http.HttpSession"%>
<%@page import="java.util.*"%>
<%@page import="java.io.*"%>
<%@page import="java.util.logging.Logger"%>
<!doctype html>
<html lang="en">
<head>
<meta charset="utf-8" />
<title>jQuery UI Accordion - Default functionality</title>
<!-- Jquery UI -->
<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>



<style>
.center {
	margin-left: auto;
	margin-right: auto;
	width: 70%;
}

.right {
	position: absolute;
	top: 20px;
	right: 0px;
	width: 150px;
}

div.one
{	
	width:200px;
	height:40px;
/* 	position:relative;
	top:1px;
	left:-1px; */
	border-style:solid;	
 	border-width:1px;
	border-radius: 4px;
	background-color:#f0f0f0;
	margin-left: auto;
	margin-right: auto;	
	text-align:center;
}

div.two
{
	width:200px;
	height:200px;
	border-radius: 4px;
	border-style:solid;	
	border-width:1px;
	margin-bottom:25px;
}

div.three
{	
	width:700px;
	height:40px;
	border-style:solid;
/* 	position:relative;
	top:1px;
	left:-1px;	 */
 	border-width:1px;
	border-radius: 4px;
	background-color:#f0f0f0;
	margin-left: auto;
	margin-right: auto;	
	text-align:left;
}

div.four
{
	width:700px;	
	border-radius: 4px;
	border-style:solid;	
	border-width:1px;
	margin-bottom:25px;	
	
}

div.five
{
	padding:10px 10px 10px 10px;
	
}

table
{ 
 border-collapse:collapse;
}
table,th, td
{
 padding:0px 5px 0px 5px ;
}

input.remove
{
    float:left;
    display:block;
    margin:5px;
}

input.fieldname
{
    float:left;
    clear:left;
    display:block;
    margin:5px;
}
.inline-form {
}

.inline-form .wrapper {
  float: left;
  width: 100%;
}

.inline-form .wrapper .content {
  margin: 0 75px 0 92px;  /* 0 RC 0 LC */
}

</style>
</head>
<body>
<script>
	$(document)
			.ready(
					function() {
						
						
						
						   var request = $.ajax({
												url : '/portfolio/spring/web/task/myusertasks',
												data : null,
												cache : false,
												contentType : false,
												processData : false,
												type : 'GET',
												success : function(data) {
													console.log("success"+data);												
													$.each(data, function(i, value) {
													console.log("i is "+i+" and taskid is "+value.taskId);
													console.log("i is "+i+" and task dedaline is "+value.deadline);
													console.log("i is "+i+" and task status is "+value.status);
													console.log("i is "+i+" and task name is "+value.name);
													console.log("i is "+i+" and task userId is "+value.userId);
													/* $('body').append( "task id is : " + value.taskId ) 
                                                               .append( "task status is : " + value.status )
													           .append( "task name is : " + value.name )
													           .append( "task dedaline is : " + value.deadline );*/
													

												});
													 realArray = $.makeArray(data );

													
													//<input type="hidden" id="data" value=data />
													//$("#response").html(data);
													//$('div.three').html(data[0]);
													
													
											}});
						   
						   request.done(function(data) {
							  // <input id="url" name="url" type="text" />
							   // <input type="submit" value="submit" />	
							   
							    var fieldWrapper = $("<div class=\"content\" id=\"field"  + "\"/>");
                                var fName = $("<input type=\"text\" size=\"30\" class=\"fieldname\" />");
                                var submitButton = $("<input type=\"button\" class=\"remove\" value=\"SUBMIT\" />");
                                fieldWrapper.append(fName);
                                fieldWrapper.append(submitButton);
                                $("#buildyourform").append($("#wrapper")).append(fieldWrapper);

							   /* $('<button/>', {
							        text: 'submit', 
							        id: 'btn',
							        click: function () { alert('hi'); }
							    });*/
							  /* if(data!=null && data.length>0)
						{
							   var existtingtasks = new Object();
							      for (i=0; i < data.length; i++ ) {
							    	  existtingtasks[data[i].taskId] = data[i];
							   }
							   $.each(existtingtasks,function(taskkey,taskval)
									   {
								        console.log(taskkey +" =  "+ taskval.taskId);
									   });
						}
							else if(data.length<=0)
						{
								 <input type="text" name="t" value="cake"></input>		    
						}*/
							   
							
							   
							   });
							  
							 request.fail(function(jqXHR, textStatus) {
							   alert( "Request failed: " + textStatus );
							 });
						   
							
						


						   });
	</script>
	<!--  
	<div class="four">
  <div id=log class="three">Demonstration Box</div>
   </div>-->
	
	
	<div class="inline-form" id="buildyourform">
	<div class="wrapper" id="wrapper">
    <legend>enter the secret key!</legend>
    </div>
    
   </div>


	
</body>

</html>
