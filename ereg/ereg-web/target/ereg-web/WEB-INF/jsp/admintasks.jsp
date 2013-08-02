<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<!doctype html>
<!--[if lt IE 7]> <html class="no-js lt-ie9 lt-ie8 lt-ie7" lang="en"> <![endif]-->
<!--[if IE 7]>    <html class="no-js lt-ie9 lt-ie8" lang="en"> <![endif]-->
<!--[if IE 8]>    <html class="no-js lt-ie9" lang="en"> <![endif]-->
<!--[if IE 9]>    <html class="no-js lt-ie10" lang="en"> <![endif]-->
<!--[if gt IE 9]><!--> <html class="no-js" lang="en"> <!--<![endif]-->
<html>
<head>

 
 <link href="<c:url value='/resources/kendoui/styles/kendo.common.min.css'/>" rel="stylesheet" type="text/css"/>
 <link href="<c:url value='/resources/kendoui/styles/kendo.default.min.css'/>" rel="stylesheet" type="text/css"/>
<script type="text/javascript" 	src="<c:url value='/resources/jquery-ui-1.10.3/jquery-1.9.1.js'/>"></script>
<script type="text/javascript" 	src="<c:url value='/resources/kendoui/kendo.all.min.js'/>"></script>
<%-- <script type="text/javascript"  src="<c:url value='/resources/kendoui/people.js'/>"></script>  --%>

</head>


<body>
<div id="wrapper-center">
			<div id="wrapper-content">

				<div id="top-navigation">

					<nav id="user-nav" role="navigation" aria-labelledby="desc-user-nav">
						<p class="hide" id="desc-user-nav">User navigation</p>
						 <sec:authorize access="isAuthenticated()">
						<ul>
							<li><a href="/ereg-web/secure/home">${customer.firstName}${customer.lastName} </a></li>
							<li><a href="/ereg-web/secure/home">My Home</a></li>
							  <c:choose> 
	          <c:when test="${eregUtils.isOAMAuthentication()}">
	          <li> <a  href="<c:url value='/logout.jsp' />" >Sign Out</a></li>
	          </c:when>
	          <c:otherwise>
	          <li> <a  href="<c:url value='/public/logout' />" >Sign Out</a></li>
	          </c:otherwise></c:choose> 
						</ul>
						</sec:authorize>
						<div class="clearboth"></div>
					</nav><!-- /user-nav -->

					<div class="graydottedbar"></div>

				</div><!-- /top-navigation -->

				<div id="header" class="header-image ets-home" role="banner">

					<div id="product-logo">
						<div id="mo-logo">
							<a href=""><img src="/ereg-web/resources/img/logo-mo-dese.423x61.jpg" width="423" height="61" alt="Missouri Educator Gateway Assessments"></a>
						</div>
					</div>

					<nav id="audience-nav" role="navigation" aria-labelledby="desc-audience-nav">
						<p class="hide" id="desc-audience-nav">Audience links</p>
						<ul>
							<li><a href="/ereg-web/secure/home" title="Frequently Asked Questions" class="lsa-trans donottransform">FAQs</a></li>
							<li><a href="/ereg-web/secure/home">Contact Us</a></li>
						</ul>
						<div class="clearboth"></div>
					</nav>

					<div id="audience-header" aria-hidden="true">
						<!-- that 'ephox-wrap' is needed for the ets cms, if you are not using it you can skip the div-wrapper -->
						<div class="ephox-wrap">For Candidates</div>
					</div>

					<div class="clearboth"></div>

				</div> <!-- /header -->

				<div id="corp-bar" class="corp-bar">
					<!--
							<p class="hide">Site Path:</p>
							<a href="">Home</a> <span aria-hidden="true">&gt;</span>
							<a href="">Some Page</a> <span aria-hidden="true">&gt;</span>
							<a href="">A Topic</a>
					-->
				</div>

				<div id="main" role="main" aria-labelledby="desc-main">
					<div id="center-content">

<!-- ============= -->
<!-- CONTENT START -->
<!-- ============= -->

<div id="breadcrumb">
	<p class="hide">Site Path:</p>
	<a href="/ereg-web/secure/home">Home</a> <span aria-hidden="true">&gt;</span>
	<a href="/ereg-web/secure/home">My Assesments</a> <span aria-hidden="true">&gt;</span>
	<a href="/ereg-web/secure/home">Tasks</a>
</div>

<!-- the FIRST h1 on the page MUST get the id="desc-main" attribute -->
<h1 id="desc-main">Admin Missouri Performance Assessments Home</h1>

			
			  
				<%-- <h1><spring:message code="home.welcome.heading" arguments="${globalContextCustomer.currentProgramShortDescription}"/> </h1> --%>
				<p style="color:red; margin:10px 0 0 0;">${STATUS_MESSAGE}</p>
			<!-- </div>
		</div>
	</div> -->
	<!-- homeContainer Starts -->
	<!-- <div id="homeContainer"> -->
		<!-- Column1 Starts -->
		<div class="content-left">
			<%-- <div class="block">
				<h3>${customer.firstName} ${customer.lastName}</h3>
				<div class="content">
					<spring:message code="home.testtakerId.label"/> # ${testTakerId}
				</div>
			</div>  --%>
			<div class="rounded">
				<%-- <h3><spring:message code="home.profile.header"/></h3> --%>
				<p class="like-h3 shaded">${customer.firstName} ${customer.lastName}</p>
				<div class="content">
					ID: ${testTakerId}
					<br>
				</div>
				<div class="content">
					<ul>
						<li><a href=<c:url value='/secure/profile/' />><spring:message code="home.personal.link.display"/></a></li>
						<li><a href='<c:url value='${changePassword}' />'><spring:message code="home.changepassword.link.display"/></a></li>
						<li><a href='<c:url value='${changeSecurityQuestion}' />'><spring:message code="home.securityq.link.display"/></a></li>
						<li><a href='<c:url value='/secure/profile/background/update' />'><spring:message code="home.backgroundinfo.display"/></a></li>
						<li><a href="<c:url value='/secure/testtaker/accommodations/view' />"><spring:message code="home.accommodations"/></a></li>
					</ul>
				</div>
			</div>
			<jsp:include page="../common/myResources.jsp"></jsp:include>
		</div>
		
		<div class="content-right"> 
			<%-- <div  class="block" <c:if test="${all_biq_answered}">style="display:none;"</c:if>>
				<h3><spring:message code="home.notifocation.heading"/></h3>
				<c:if test="${!all_biq_answered}">
					<div style="width:100%; margin:0 auto; border:0px dotted #ccc; padding:0; background:#FFEEE2">
						<div class="content">
							<spring:message code="home.background.message.part1"/> <a href='<c:url value='/secure/profile/background/update' />'><spring:message code="home.background.message.part2"/></a> <spring:message code="home.background.message.part3"/> 
						</div>
					</div>
				</c:if>
			</div> --%>
			<div class="rounded">
			<h2 class="shaded like-h3">Missouri Pre-Service Teacher Assessments : Admin View</h2>
            <h3>Adview for : Submitted Assessments </h3>
		
           <div id="example" class="k-content">
            <div id="grid"></div>
           
            <script>
           
            
                $(document).ready(function() {
                
                	 var adminDataSource = new kendo.data.DataSource({
                         transport: {
                             read: {
                            	
                                 // the remote service url
                                 url: "/ereg-web/pss/task/draft/admin",

                                 // JSONP is required for cross-domain AJAX
                                 //dataType: "application/json;charset=utf-8",
                                 
                                 dataType: "json",
                                 

                                 // additional parameters sent to the remote service
                                 data: {
                                     q:"adminTasks"
                                 }
                             }
                         },
                         pageSize: 15,
                       
                         schema: {                            
                             data: "data",
                             model: {
                            	 id: 'id',
                                 fields: {
                                	task_name: { type: "string"},
                                	customer_id: { type: "string"},
                                	task_id: { type: "string"}
                                 }
                                 
                             },
                            /*   parse: function(response) {
                                console.log($.parseJSON(response));
                                return $.parseJSON(response);
                             }   */
 
                          	 data: function(rawData) { 
                                  //return rawData[0].SearchResult.assets;
                                  console.log(rawData);
                                  return rawData;
                                }  
                         
                      }
                     });
                   var grid= $("#grid").kendoGrid({
                        
                        dataSource: adminDataSource,    
                        //height: 430,
                        columns: [
                            {
                                title: "TaskName",
                                field:"task_name",
                                //width: 160,
                                /* filterable: {
                                	ui: taskFilter
                                }, */
                                //template: "#=first_name# #=last_name#",
                               
                            },
                             {
                            	 title: "Customer",                            	 
                                field: "customer_id",
                               // width: 130,
                               /*  filterable: {
                                    ui: customerFilter
                               } */
                              },
                            {
                            	 title: "Task_Id",
                                template:"<a href='/ereg-web/pss/task/goto?taskId=#=task_id#'>#=task_name#</a>",
                                /* filterable: {
                                    ui: "taskIDFilter"
                                } */
                               
                            }
                           /*  {
                                field: "date",
                                title: "Birth Date",
                               // format: "{0:MM/dd/yyyy HH:mm tt}",
                                 filterable: {
                                    ui: "datetimepicker"
                                } 
                            } */
                       ], toolbar: kendo.template( $( "#template" ).html()),
                       sortable: true,
                       navigatable: true,
                       filterable: true,
                       
                       /* filterable: {
                           extra: false,
                           operators: {
                               string: {
                                   startswith: "Starts with",
                                   eq: "Is equal to",
                                   neq: "Is not equal to"
                               }
                           }
                       },  */
                       pageable: true,
                       scrollable: true
                      
                      
                       
                    });
                    
                   grid.find("#search").on("keyup focus", function (e) {
                      	console.log("in search here");
                          var searchTerm = e.currentTarget.value;
                          var ds = grid.data('kendoGrid').dataSource;
                  		if (searchTerm.length === 0) {
                  			ds.filter( {} );
                  			return false;
                  		}
                          var fields = ds.options.schema.model.fields;
                          var innerFilters = [];
                          
                          for ( var key in fields ) {
                  			if ( !fields.hasOwnProperty( key ) /* || fields[key].type != 'String' */ ) continue;
                  			innerFilters.push({
                  				field: key,
                  				operator: 'contains',
                  				value: searchTerm
                  			});
                          }
                          var filters = { logic: 'or', filters: innerFilters };
                          ds.filter( filters );
                  		return false;
                        }).on("mouseup", function() { 
                  		var that = this;
                  		setTimeout(function() {that.blur(); that.focus();}, 10); 
                  	  });
                    
                    function taskFilter(element) {
                        element.kendoAutoComplete({
                        
								transport: {
									read: "/draft/admintasks"
								}
							
                        });
                    }

                    function customerFilter(element) {
                        element.kendoDropDownList({
                            dataSource: customer_id,
                            optionLabel: "--Select Value--"
                        });
                    }
                    
                    function taskIDFilter(element) {
                        element.kendoDropDownList({
                            dataSource: task_id,
                            optionLabel: "--Select Value--"
                        });
                    }
                });

               

            </script>
            
            <script type="text/x-kendo-template" id="template">
                         <div class="toolbar" style="float: right;">
                          <input type="search" class="k-textbox" placeholder="search grid" id="search" style="width: 200px"></input>
                            </div>
             </script>  
        </div>

        </div>



</div>
</div>


<div class="clearboth"></div>

<!-- ============= -->
<!-- CONTENT END   -->
<!-- ============= -->
						<div class="clearboth"> </div>


					</div><!-- /center-content -->
					<div class="clearboth"></div>
				</div><!-- /main -->
				<footer id="footer" role="contentinfo" aria-labelledby="desc-footer" >
					<h1 id="desc-footer" class="hide">Footer Navigation</h1>

					<div class="corp-bar"></div>

					<nav id="corp-links" role="navigation" aria-labelledby="corp-links-desc">
						<p class="hide" id="corp-links-desc">State Links</p>
						<ul>
							<li><a href="http://dese.mo.gov/">Missouri Department of Elementry and Secondary Education</a></li>
							<li><a href="">Missouri Performance Assessments Website</a></li>
							<li><a href="">Legal/Privacy Policy</a></li>
						</ul>
						<div class="clearboth"></div>
					</nav><!-- /corp-links -->

					<div class="clearboth"></div>

					<div id="cya">
						<p>
							Copyright <span aria-hidden="true">&copy;</span> YYYY. All rights reserved.<br />
							All trademarks are the property of their respective owners.
						</p>
					</div> <!-- /cya -->

					<div id="ets-logo-3l"><a href="http://www.ets.org">ETS. Listening. Learning. Leading.</a></div>
				</footer>

					<!-- <div style="margin-top:10px">
						<p>
							<a href="">View All My Assessments</a>
						</p>
					</div> -->
				<!-- </div>
			</div>

		</div>

				
			</div>
		</div> -->

</div>
</div>
		
	

</body>
</html>