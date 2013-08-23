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
<script type="text/javascript" 	src="<c:url value='/resources/kendoui/underscore-min.js'/>"></script>
<script type="text/javascript" 	src="<c:url value='/resources/kendoui/backbone-min.js'/>"></script>

<script type="text/javascript" 	src="<c:url value='/resources/kendoui/kendo.backbone.js'/>"></script>

<%-- <script type="text/javascript"  src="<c:url value='/resources/kendoui/people.js'/>"></script>  --%>

</head>

<style>

.k-header .k-link{
   text-align: center;
}
 .k-grid tbody tr{height:38px;} 

 /* .k-grid td
{
    padding: 0.1em 0.1em 
    
}  */
/* .k-grid td {
border-style: solid;
border-width: 0 0 0 1px;
padding: .4em .6em;
overflow: hidden;
line-height: 3.0em;
vertical-align: middle;
text-overflow: ellipsis;
} */

/* k-grid-header .k-header {
   overflow: visible;
   white-space: normal;
} */
</style>
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
							<a href="http://mega.ets.org/" target="_blank"><img src="/ereg-web/resources/img/logo-mo-dese.423x61.jpg" width="423" height="61" alt="Missouri Educator Gateway Assessments"></a>
						</div>
					</div>

					<nav id="audience-nav" role="navigation" aria-labelledby="desc-audience-nav">
						<p class="hide" id="desc-audience-nav">Audience links</p>
						<ul>
							<li><a href="http://mega.ets.org/rsc/pdf/faq.pdf" target="_blank" title="Frequently Asked Questions" class="lsa-trans donottransform">FAQs</a></li>
							<li><a href="http://mega.ets.org/contact/index.html" target="_blank">Contact Us</a></li>
						</ul>
						<div class="clearboth"></div>
					</nav>

					<div id="audience-header" aria-hidden="true">
						<!-- that 'ephox-wrap' is needed for the ets cms, if you are not using it you can skip the div-wrapper -->
						<div class="ephox-wrap">For Admin</div>
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
<!-- <h1 id="desc-main">Missouri Performance Assessments Home</h1> -->

<h1 id="desc-main">Missouri Performance Assessments Admin View</h1>

			
			  
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
			<!-- <h2 class="shaded like-h3">Missouri Pre-Service Teacher Assessments : Admin View</h2> -->
			<h2 class="shaded like-h3"><center>Candidate Submitted Tasks</center></h2>
         <!--    <h3 class="shaded like-h3">Submitted Assesments</h3> -->
            
		
           <div id="example" class="k-content">
            <div id="grid"></div>
           
            <script>
           
            
                $(document).ready(function() {
                	

                	
                	
                	// add a backbone namespace if we need it
              /*   	kendo.Backbone = kendo.Backbone || {};
                	
                	 // BackboneTransport
                	  // -----------------
                	  //
                	  // Define a transport that will move data between
                	  // the kendo DataSource and the Backbone Collection
                	  var BackboneTransport = function(collection){
                	    this._collection = collection;
                	  };
                	  
                	  _.extend(BackboneTransport.prototype, {

                		    create: function(options) {
                		      // increment the id
                		      if (!this._currentId) { this._currentId = this._collection.length; }
                		      this._currentId += 1;

                		      // set the id on the data provided
                		      var data = options.data;
                		      data.id = this._currentId;

                		      // create the model in the collection
                		      this._collection.add(data);

                		      // tell the DataSource we're done
                		      options.success(data);
                		    },

                		    read: function(options) {
                		    	console.log(this._collection.toJSON());
                		      options.success(this._collection.toJSON());
                		    },

                		    update: function(options) {
                		      // find the model
                		      var model = this._collection.get(options.data.id);

                		      // update the model
                		      model.set(options.data);

                		      // tell the DataSource we're done
                		      options.success(options.data);
                		    },

                		    destroy: function(options) {
                		      // find the model
                		      var model = this._collection.get(options.data.id);

                		      // remove the model
                		      this._collection.remove(model);

                		      // tell the DataSource we're done
                		      options.success(options.data);
                		    }
                		  });
                	  
                	// kendo.backbone.BackboneDataSource
                	  // -----------------------------------

                	  // Define the custom data source that uses a Backbone.Collection
                	  // as the underlying data store / transport
                	// Define the custom data source that uses a Backbone.Collection
                	  // as the underlying data store / transport
                	  kendo.Backbone.DataSource = kendo.data.DataSource.extend({
                	    init: function(options) {
                	      var bbtrans = new BackboneTransport(options.collection);
                	      _.defaults(options, {transport: bbtrans, autoSync: true});

                	      kendo.data.DataSource.fn.init.call(this, options);
                	    }
                	  }); 

               
                	
                	   var TodoModel = Backbone.Model.extend({
                		   
                                  initialize : function() {
                          	    },
                   	    parse: function(response){
                   	    	console.log(response);
                   	        return JSON.stringify(response);
                   	    }
                		   
                	   });
                	  var TodoCollection = Backbone.Collection.extend({
                	    model: TodoModel,
                	   // idAttribute: "myId",                	   
                	    url: "/ereg-web/pss/task/draft/admin",
                	    initialize : function() {
                	    	this.fetch({update:true});  
                	        // jabadaba whatever you need here
                	    }
                	    
                	    
                	  }); 
                	  
                	  // A hard coded list of items to render
                	/*  var tasks = [
                	    { task_name: "Task 4: Designing and Implementing Instruction to Promote Student Learning", cust_name: "ravi ramaswamy", customer_id: "3906",task_id: "2",task_date: "2016-06-14"},
                	    { task_name: "Task 1: Knowledge of Students and the Learning Environment", cust_name: "harish gadre", customer_id: "13935",task_id: "2",task_date: "2016-06-14"}
                	  
                	  ]; */
                	  
                	// A backbone collection for the todo items
                /* 	 var MyCollection = new TodoCollection();
                     
                	   MyCollection.on("change remove add", function(model){
                		   console.log(model.attributes);
                		  });
                	 
                	  var adminDataSource = new kendo.Backbone.DataSource({ 
                	    collection: MyCollection,
                	    
                	   
                	    schema: {
                	    	
                	      model: {
                	    	  id: "id",
                	    	  fields: {
                              	task_name: { type: "string"},
                              	cust_name: { type: "string"},
                              	customer_id: { type: "string"},
                              	task_id: { type: "string"},
                              	task_date: { type: "string"}
                              	
                               }
                	      
                	      },
                	    
                     
                	    }, 
                	  });    */

                
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
                                	cust_name: { type: "string"},
                                	customer_id: { type: "string"},
                                	task_id: { type: "string"},
                                	task_date: { type: "string"}
                                	
                                 }
                                 
                             },
                          
 
                          	 data: function(rawData) { 
                                  //return rawData[0].SearchResult.assets;
                                  //console.log(rawData);
                                  return rawData;
                                }  
                         
                      }
                     }); 
                  
                     var grid= $("#grid").kendoGrid({
                        
                        dataSource: adminDataSource,    
                      
                        height: 380,
                        columns: [

                                  {
                                  	
                                      title: "CandidateID",
                                      field:"customer_id",
                                      width: '18%',  
                                  },
                                  
                                  {
                                  	
                                      title: "Candidate Name",
                                      field:"cust_name",
                                      width: '25%', 
                                      filterable: false
                                      //template: "#=first_name# #=last_name#",
                                  },
                                  
                                 /*  { field: "cust_name", title: "Done", width: '10%', template: "<input type='checkbox' # if (cust_name){ # checked='checked' #}# disabled='disabled'>" },  */
                            {
                            	
                                title: "Task Name",
                                field:"task_name",
                                template:"<a href='/ereg-web/pss/task/gotoAdmin?taskId=#=task_id#&userId=#=customer_id#'>#=task_name#</a>",
                                width: '35%',  
                                 /* filterable: {
                                	ui: taskFilter
                                },  */
                               
                               
                            },
                           /*  { command: ["edit", "destroy"], width: '20%' }, */
                             
                           
                            {
                                
                                title: "Submitted Date",
                                field: "task_date",                                
                                width: '20%',  
                                filterable: false,
                               /*  template:'#= kendo.toString(task_date, "dd/MM/yyyy") #' */
                                	 
                                	              
                               /* format: "{0:MM/dd/yyyy HH:mm tt}",
                                 filterable: {
                                    ui: "datetimepicker"
                                }  */
                            } 
                       ],
                      /*  editable: {
                    	      mode: "inline"
                    	    }, */
                       toolbar: kendo.template( $( "#template" ).html()),
                       sortable: true,
                       navigatable: true,
                       filterable: true,
                       
                       /*  filterable: {
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
                      	//console.log("in search here");
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
                      Search Candidates: <input type="search" class="k-textbox" placeholder="" id="search" style="width: 200px"></input>
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
							<li><a href="http://dese.mo.gov/" target="_blank"><spring:message code="footer.moDeptSecondaryEdu"/></a></li>
 							<li><a href="http://mega.ets.org" target="_blank"><spring:message code="footer.moPerfAssmtSite"/></a></li>
 							<li><a href="http://www.ets.org/legal" target="_blank"><spring:message code="footer.legalPrivacyPolicy"/></a></li>
						</ul>
						<div class="clearboth"></div>
					</nav><!-- /corp-links -->

					<div class="clearboth"></div>

					<div id="cya">
						<p>
							Copyright <span aria-hidden="true">&copy;</span> 2013 by Educational Testing Service. All rights reserved.<br />
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