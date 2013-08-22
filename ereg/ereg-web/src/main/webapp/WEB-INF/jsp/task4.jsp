
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@page import="java.util.logging.Logger"%>
<%@page import="org.ets.pss.persistence.model.AsgndTsk"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="ct" uri="http://ereg.ets.org/commontags"%>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<!doctype html>
<!--[if lt IE 7]> <html class="no-js lt-ie9 lt-ie8 lt-ie7" lang="en"> <![endif]-->
<!--[if IE 7]>    <html class="no-js lt-ie9 lt-ie8" lang="en"> <![endif]-->
<!--[if IE 8]>    <html class="no-js lt-ie9" lang="en"> <![endif]-->
<!--[if IE 9]>    <html class="no-js lt-ie10" lang="en"> <![endif]-->
<!--[if gt IE 9]><!--> <html class="no-js" lang="en"> <!--<![endif]-->
<html lang="en">
<head>
<meta charset="utf-8" />

<title>Missouri Performance Assessments Application</title>

<!-- fine uploader -->
<link href="<c:url value="/resources/fineuploader/fineuploader-3.5.0.css"/>" rel="stylesheet" type="text/css" />

<script type="text/javascript" 	src="<c:url value='/resources/jquery-ui-1.10.3/jquery-1.9.1.js'/>"></script>
<script type="text/javascript" 	src="<c:url value='/resources/jquery-ui-1.10.3/ui/jquery-ui.js'/>"></script>
<script type="text/javascript" src="<c:url value="/resources/jquery-ui-1.10.3/themes/base/jquery-ui.css"/>"></script>
<script type="text/javascript" 	src="<c:url value='/resources/json/json2.js'/>"></script> 
<script type="text/javascript" src="<c:url value="/resources/fineuploader/jquery.fineuploader-3.5.0.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/dateformat/jquery.dateFormat-1.0.js"/>"></script>

<script type="text/javascript" 	src="<c:url value='/resources/rangy-1.3/rangy-core.js'/>"></script>
<script type="text/javascript" 	src="<c:url value='/resources/rangy-1.3/rangy-cssclassapplier.js'/>"></script>


<script>

function setdiv( divid ){
	if(document.getElementById(divid).height == 100 ){
		document.getElementById(divid).height = 500;
	}else{
		document.getElementById(divid).height = 100;
	}
}

function strip_tags(str){
	t = str.replace(/<(\/)?(html|head|title|body|h1|h2|h3|h4|h5|h6|p|br|hr|pre|em|strong|code|b|i|ul|li|ol|dl|dd|table|tr|th|td)([^>]*)>/gi, "");
	t = t.replace(/<(\/)?(iframe|frameset|form|input|select|option|textarea|blackquote|address|object)([^>]*)>/gi, "");
    return t;
	//$("#t").html(t);
	}


//jQuery.fn.stripTags = function() { return this.replaceWith( this.html().replace(/<\/?[^>]+>/gi, '') ); };

jQuery.fn.stripTags = function(str) { return str.replace(/<\/?[^>]+>/gi, '');  };

$.extend({ alert: function (message, title) {
	  $("<div></div>").dialog( {
		  closeOnEscape: false,
		  dialogClass: 'no-close-dialog',		     
	    buttons: { "Ok": function () { $(this).dialog("close"); } },
	   close: function (event, ui) { $(this).remove(); }, 
	    title: title,
	    resizable: false,
	    position:['center',120],
	    //height:195,
	    modal: true
	  }).text(message);
	}
	});

	$(document)
			.ready(
					
					function() {
						jQuery.noConflict();
						rangy.init();
						
						var accordicons = {
							      header: "ui-icon-circle-arrow-e",
							      activeHeader: "ui-icon-circle-arrow-s"
							    };

						//create accordian  
						
						$("#accordion-1").accordion({heightStyle: "content",collapsible: true,active: false,icons: accordicons});
						$("#accordion").accordion({heightStyle: "content",collapsible: true,active: false,icons: accordicons});
						   
						

						function surroundSelectedText(templateElement) {

							var range, sel = rangy.getSelection();
							var ranges = sel.getAllRanges();
							var textNodes, textNode, el, i, len, j, jLen;
							for (i = 0, len = ranges.length; i < len; ++i) {
								range = ranges[i];
								// If one or both of the range boundaries falls in the middle
								// of a text node, the following line splits the text node at the
								// boundary
								range.splitBoundaries();

								// The first parameter below is an array of valid nodeTypes
								// (in this case, text nodes only)
								textNodes = range.getNodes([ 3 ]);
								for (j = 0, jLen = textNodes.length; j < jLen; ++j) {
									textNode = textNodes[j];
									el = templateElement.clone();
									textNode.parentNode.insertBefore(el,
											textNode);
									el.appendChild(textNode);
								}
							}
						}

						/*var span = document.createElement("span");
						span.style.color = "green";
						span.style.fontWeight = "bold";

						surroundSelectedText(span);*/

						function getCaretCharacterOffsetWithin(element) {
							var caretOffset = 0;
							if (typeof window.getSelection != "undefined") {
								var range = window.getSelection().getRangeAt(0);
								var preCaretRange = range.cloneRange();
								preCaretRange.selectNodeContents(element);
								preCaretRange.setEnd(range.endContainer,
										range.endOffset);
								caretOffset = preCaretRange.toString().length;
							} else if (typeof document.selection != "undefined"
									&& document.selection.type != "Control") {
								var textRange = document.selection
										.createRange();
								var preCaretTextRange = document.body
										.createTextRange();
								preCaretTextRange.moveToElementText(element);
								preCaretTextRange.setEndPoint("EndToEnd",
										textRange);
								caretOffset = preCaretTextRange.text.length;
							}
							return caretOffset;
						}

						function showCaretPos() {
							var el = $("#cteditor");

							alert(getCaretCharacterOffsetWithin(el));
						}

						function replaceWithOwnChildren(el) {
							var parent = el.parentNode;
							while (el.hasChildNodes()) {
								parent.insertBefore(el.firstChild, el);
							}
							parent.removeChild(el);
						}

						$('input[id^="removehigh"]')
								.click(
										function() {
											//linkApplier.undoToSelection(this);
											var clicked = $(this);
											var idStr = clicked[0].id;
											var indexOfCount = "removehigh".length;
											var index = idStr.substring(indexOfCount, idStr.length);
											var sel = rangy.getSelection();
										
											
											var range = sel.getRangeAt(0);

											for ( var i = 0, range; i < sel.rangeCount; ++i) {
												range = sel.getRangeAt(i);												
												// Split partially selected nodes 
												range.splitBoundaries();

												// Get formatting elements. For this example, we'll count any
												// element with display: inline, except <br>s.
												var formattingEls = range
														.getNodes(
																[ 1 ],
																function(el) {
																	//alert('the node is '+el.tagName);
																	return el.tagName;
																});

												for ( var i = 0, el; el = formattingEls[i++];) {
													if(el.tagName == 'A')
													{
														var link = el.href;
														var docId = link.substring(link.lastIndexOf("/")+1);
														
														$('#docsListForPrmpt'+index+' li').each(function(){
															
															var currentNode = $(this);
															var liId = currentNode.attr('id');
															
															if(liId == docId)
															{
																currentNode.remove();
																
															}
															
														});
														replaceWithOwnChildren(el);
														//ashwin added to remove which docs
														$('<li id="'+docId+'">'+'</li>').appendTo($('#removedocsListForPrmpt'+index));
														
													}
													
													
												}
												//$('#removehigh' + index).prop('disabled', true);//can be removed
											}

										});

						var jqxhr = $.ajax({
							url : '/ereg-web/pss/artifact/getList',
							data : null,
							cache : false,
							contentType : false,
							processData : false,
							type : 'GET',
							success : function(data) {
								//console.log("success");
								var documentsDiv = $('#documentsDiv');
								var cList = $('<ul/>').attr('id', 'docList');
								$.each(data, function(i, value) {
								
									if(value.dateCreated==null)
										{value.dateCreated="";}
									//console.log(value.dateCreated);
									$('<li/>').attr('id', value.id).attr('date',$.format.date(value.dateCreated, "MM/dd/yyyy")).text(
											value.filename).appendTo(cList);
								});
								cList.appendTo(documentsDiv);

							}

						});
						
						
					

						$('input[id^="highlight"]')
								.click(
										function() {
											
										
											var clicked = $(this);
											var idStr = clicked[0].id;
											var indexOfCount = "highlight".length;
											var index = idStr.substring(
													indexOfCount, idStr.length);
											var linkvalue = "http://";
											var grid = '<table id="myTable" cellspacing="0" cellpadding="2" width ="100%" border = "1" align="center"><thead><tr><td align ="center"></td><td align ="center">Artifact Name</td><td align ="center">Date Uploaded</td></tr></thead>';
											$("#docList li")
													.each(
															function() {
																var value = $(this).text();
																/* $.ajax({
																	url : '/ereg-web/pss/artifact/getList',
																	type : 'get',
																	success : function(response) {
																		console.log("success");
																	
																			$.each(response,function(index,ajaxvalue) {
																				if(value==ajaxvalue.filename)
																					{
																					  value.prototype.dateCreated=$.format.date(ajaxvalue.dateCreated, "dd/MM/yyyy");
																					}

																						//var obj = response.artifacts[index];
																				console.log("now the filename is "+ajaxvalue.filename);
																				
																				//console.log("now the date is "+ajaxvalue.dateCreated);
																				console.log("now the formatted date is "+$.format.date(ajaxvalue.dateCreated, "dd/MM/yyyy"));
																				
																		});
																
																	}
																}); */
																//console.log("now the value date created is "+$(this).attr('date'));
																
																grid += '<tbody><tr><td>';
																grid += '<input type="radio" id="'
																		+ $(this).attr('id')
																		+ '" name="FileRadio" value="/ereg-web/pss/artifact/view/' + $(this).attr('id')																		
																		+ '">' +  "</input>";
																grid += '</td>'+'<td>'+value+'</td>'+'<td>'+$(this).attr('date')+'</td>'+'</tr>';
																
																
																
																
															});

											grid += '</tbody></table>';
											grid = "<h3>Pre-Service Teacher Exit Assessment</h3><h3>My Library of Artifacts</h3>" + grid;
											$("#linkdialog").html(grid);
											$("#linkdialog")
													.dialog(
															{	width:500,
																height:400,
																modal : true,
																dialogClass : "no-close",
																position:['middle',80],
																buttons : [
																		{
																			text : "LinkDoc",
																			click : function() {

																				if ($('input:radio[name=FileRadio]:checked')) {
																					var selectectDoc = $('input:radio[name=FileRadio]:checked');
																					var $closestTr = $(
																							'input:radio[name=FileRadio]:checked')
																							.closest('tr');
																					
																					linkvalue = selectectDoc.val();
																					
																					var docId = selectectDoc.attr('id');
																					var docName = selectectDoc.text();
																					
																					
																					$('<li id="'+docId+'">'+docName+'</li>').appendTo($('#docsListForPrmpt'+index));
																					
																					
																					linkApplier = rangy
																					.createCssClassApplier(
																							"highLightLink",
																							{
																								ignoreWhiteSpace: true,
																								elementTagName : "a",
																								ignoreWhiteSpace: true,
																								elementProperties : {
																									href : linkvalue,
																									title : "Artifact Page"
																								}
																							});

																			linkApplier.applyToSelection();
																			
																			

																			$('#cteditor'+ index+ ' a').attr('contenteditable','false').attr('target','_blank');
																			/*var sel = rangy.getSelection();
																			var node="";
																			var selRange = sel.getRangeAt(0);
																			var currentElement = selRange.endContainer;*/
																		  
																		    	//node=selRange.getNodes([3]);
																		    
																		   // console.log(node);
																			
																			//console.log(selRange.endOffset);
																			//console.log(selRange.endContainer);
																			
																			//var selectedNodes = [];
																		   // var sel = rangy.getSelection();
																		   /*  for (var i = 0; i < sel.rangeCount; ++i) {
																		        selectedNodes = selectedNodes.concat( sel.getRangeAt(i).getNodes() );
																		    } */
																		   //console.log(selectedNodes);
																		   
																		
																		    //selRange.setStartAfter(node[0],selRange.endOffset+1);
																			//selRange.setStartAfter(selectedNodes[0],selRange.endOffset+1);
																			//selRange.setStartAfter(selectedNodes[0],selRange.endOffset+1);
																			//sel.removeAllRanges();
																			//sel.addRange(selRange);
																		
																			       // .attr('color','red');
																			       // .attr('font-size', '1.2em');
																				}//end of radio:checked block
																				$(this).dialog('close');
																			}
																		},
																		{
																			text : "Cancel",
																			click : function() {
																				$(
																						this)
																						.dialog(
																								'close');
																			}
																		} ]
															}, 'open');
											
											$(this).focus();

										});//end $('#highlight').click

						$('input[id^="existingEssayContent"]')
								.each(
										function(index) {
											var clicked = $(this);
											if (clicked) {
												var idStr = clicked[0].id;
												var indexOfCount = "existingEssayContent".length;
												var index = idStr.substring(
														indexOfCount,
														idStr.length);
												var x = clicked[0].value;
												$('#cteditor' + index)[0].innerHTML = x;

											}
										});
										
					    				
										
						function GetAllCreatedElements(selection) {
						    var nodes = selection.getRangeAt(0).getNodes(false, function (el) {
						        return el.parentNode && el.parentNode.className == "highlight";
						    });

						    var spans = [];

						    for (var i = 0; i < nodes.length; i++) {
						        spans.push(nodes[i].parentNode);
						    }
						    return spans;
						}

					 	$('[id^=cteditor]')
								.on(
										'mouseup mouseover',
										(function(e) {
											var clicked = $(this);
											var idStr = clicked[0].id;
											var indexOfCount = "cteditor".length;
											
											var index = idStr.substring(indexOfCount, idStr.length);
											var selectedText = rangy.getSelection().toString();	
											
											if (selectedText.length > 0) {
												//alert('the selected text is here');
												$('#highlight' + index).prop('disabled', false);
												$('#removehigh' + index).prop('disabled',false);//can be removed
											}
											
											var nodes = rangy.getSelection().getRangeAt(0).getNodes(false, function (el) {
												 if (el.tagName === 'A')

													{//alert('the node is '+el.tagName);
														$('#highlight'+ index)
																.prop('disabled',true);
													}
											}
											    
											 
											 );
											
											 if (selectedText.length <= 0) {
													$('#removehigh' + index).prop('disabled',true);//can be removed
													$('#highlight' + index).prop('disabled', true);//can be removed
												} 
											
											/*
											var formattingEls = range
														.getNodes(
																[1],
																function(el) {
																	//alert('the node is '+el.tagName);
																	if(el.tagName == 'A')
																	{
																		$('#removehigh'+index).prop('disabled',false);

																	}
																	
																});*/

												/*for ( var i = 0, el; el = formattingEls[i++];) {
													//alert('the node is '+el.tagName);
													if(el.tagName == 'A')
													{
														$('#removehigh'+ index)
														.prop('disabled',false);

													}
												}*/
											
											
											/* var nodes = rangy.getSelection().getRangeAt(0).getNodes(false, function (el) {
											
												 //alert(el.tagName);
												 if (el.tagName === 'A')

													{
														//alert('the node is '+el.tagName);
														$('#removehigh'+ index)
																.prop('disabled',false);

													}
											    }
											 
											 );*/
											 
											
											 
										}));
					 	
					
					 	
						$('[id^=cteditor]')
						.on(
								'keyup keypress', //'keyup',//keypress
								(function(e) {
									var clicked = $(this);
									var idStr = clicked[0].id;
									var indexOfCount = "cteditor".length;
									var index = idStr.substring(indexOfCount, idStr.length);
									var selectedText = rangy.getSelection().toString();								
									if (selectedText.length > 0) {
										$('#highlight' + index).prop('disabled', false);
										$('#removehigh' + index).prop('disabled',false);//can be removed
									}
									 
									 if (selectedText.length <= 0) {
											$('#removehigh' + index).prop('disabled',true);//can be removed
											$('#highlight' + index).prop('disabled', true);//can be removed
										}
									 
										var nodes = rangy.getSelection().getRangeAt(0).getNodes(false, function (el) {
											
											 if (el.tagName === 'A')

												{
												 //alert(el.tagName);
													//alert('the node is '+el.tagName);
													$('#highlight'+ index)
															.prop('disabled',true);

												}
										}
										 );
									 
									 
									/*  setTimeout(function(){ 
									    //var text = $('#cteditor' + index).html();
									 //   console.log('the id on keypress is '+idStr);
									  //  console.log('now the index is '+index);
										var currentEditor = $('#cteditor' + index);
										var value=currentEditor.html();
										
										 value= value.replace(/<\/?([a-z]+)[^>]*>/gi, function(match, tag) { 
										
                                             return (tag.toLowerCase() === "a") ? match : "";
                                         }); 
										
										   //var el = document.getElementById('cteditor' + index);										
											//el.innerHTML = el.innerHTML.replace(/(<([^>]+)>)/ig,"");
										 
							           
									 
									 },100); */
									 
								}));
						function convertHtmlToText( input) {
						    var inputText =input;// document.getElementById("input").value;
						    var returnText = "" + inputText;

						    //-- remove BR tags and replace them with line break
						    returnText=returnText.replace(/<br>/gi, "\n");
						    returnText=returnText.replace(/<br\s\/>/gi, "\n");
						    returnText=returnText.replace(/<br\/>/gi, "\n");

						    //-- remove P and A tags but preserve what's inside of them
						    returnText=returnText.replace(/<p.*>/gi, "\n");
						    returnText=returnText.replace(/<a.*href="(.*?)".*>(.*?)<\/a>/gi, " $2");

						    //-- remove all inside SCRIPT and STYLE tags
						    returnText=returnText.replace(/<script.*>[\w\W]{1,}(.*?)[\w\W]{1,}<\/script>/gi, "");
						    returnText=returnText.replace(/<style.*>[\w\W]{1,}(.*?)[\w\W]{1,}<\/style>/gi, "");
						    //-- remove all else
						    returnText=returnText.replace(/<(?:.|\s)*?>/g, "");

						    //-- get rid of more than 2 multiple line breaks:
						    returnText=returnText.replace(/(?:(?:\r\n|\r|\n)\s*){2,}/gim, "\n\n");

						    //-- get rid of more than 2 spaces:
						    returnText = returnText.replace(/ +(?= )/g,'');

						    //-- get rid of html-encoded characters:
						    returnText=returnText.replace(/&nbsp;/gi," ");
						    returnText=returnText.replace(/&amp;/gi,"&");
						    returnText=returnText.replace(/&quot;/gi,'"');
						    returnText=returnText.replace(/&lt;/gi,'<');
						    returnText=returnText.replace(/&gt;/gi,'>');
						    return returnText;

						    //-- return
						    //document.getElementById("output").value = returnText;
						}

						
						
						$('[id^=cteditor]')
						.on(
								'paste', //'keyup',
								(function(e) {
									//console.log($(this));
									var clicked = $(this);
									var idStr = clicked[0].id;
									//console.log('the id on paste is '+idStr);
									var indexOfCount = "cteditor".length;
									var index = idStr.substring(indexOfCount, idStr.length);
									
									 //console.log('now the index is '+index);
									var currentEditor = $('#cteditor' + index);
									  setTimeout(function(){ 
									
										//var currentEditor = $(this);
										 //var text = $('#cteditor' + index).html();
										
										//var html = $('#cteditor' + index).html();
										
										//var value=currentEditor.html();
										//console.log('the value on paste before is '+value);
										/*  value= value.replace(/<\/?([a-z]+)[^>]*>[^\s\S]+?/gi, function(match, tag) {
											
                                             return (tag.toLowerCase() === "a") ? match : "";
                                         });  */
										 //console.log('the value on paste after is '+value);
										// $(this).innerText=value;
										// $(this)[0].innerHTML=$(this)[0].innerHTML.replace(value);
										 //$(this)[0].outerHTML=value;
										// $(this)[0].text=value;
										//var value=currentEditor.html();
										//console.log("the text value is "+value);
										//var found = value.match(/(<([^/S/n/r]+[^>]+)>)/ig);
										//console.log(found);
									/* 	$.each(found, function(i, v) {
										    console.log(i+" = "+v);
										}); */
									
										/* value= value.replace(/<\/?([a-z]+[^\S\r\n])[^>]*>/ig, function(match, tag) {
											 console.log("tag is "+tag);
											if(tag.toLowerCase() === "<div><br> ")
												{
												 console.log("its a newline gotcha!");
												}
                                             return (tag.toLowerCase() === "/n") ? match : "";
                                         });
										console.log("value is "+value); */
										
										//returnValue=convertHtmlToText(value);
										//console.log('the new reutnr value is '+returnValue);
										//var el = document.getElementById('cteditor' + index);
										 //el.innerHTML = el.innerHTML.replace(/(<([^>]+)>)/ig,"");
										//el.innerHTML= el.innerHTML.replace(/<\/?([a-z]+[^\S\r\n])[^>]*>/ig,"");
										//el.innerHTML = el.innerHTML.replace(/(<([^>]+)>)/ig,"");
										//el.innerHTML =el.innerHTML.replace(/(<([^>]+)>)/ig,returnValue);
										 //el.innerHTML= el.innerHTML.replace(/<\/?([a-z]+[^\S\r\n])[^>]*>/ig,"");
										//el.innerHTML = el.innerHTML.replace(/(<([^>]+)>)/ig,"");								
										//el.innerHTML= el.innerHTML.replace(/<\/?([a-z]+[^\S\r\n])[^>]*>/ig,"").replace((/<script.*>[\w\W]{1,}(.*?)[\w\W]{1,}<\/script>/gi, ""),"").replace(/<style.*>[\w\W]{1,}(.*?)[\w\W]{1,}<\/style>/gi, "").replace(/<(?:.|\s)*?>/g, "");
                                       									
										var el = document.getElementById('cteditor' + index);
										el.innerHTML = el.innerHTML.replace(/(<([^>]+)>)/ig,"");		
									  },100); }));
					  
										$('[id^=cteditor]')
													.on('mouseout',
															(function(e) {
																var selectedText = rangy
																		.getSelection()
																		.toString();
																if (selectedText.length <= 0) {
																	var clicked = $(this);
																	var idStr = clicked[0].id;

																	var indexOfCount = "cteditor".length;
																	var index = idStr
																			.substring(
																					indexOfCount,
																					idStr.length);

																	$('#removehigh'+ index).prop('disabled',true);//can be removed
																	$('#highlight'+ index).prop('disabled',true);

																}

															}));  
											
											$('[id^=cteditor]')
											.on('keydown',
													(function(e) {
														var selectedText = rangy
																.getSelection()
																.toString();
														
														if (selectedText.length <= 0) {
															var clicked = $(this);
															var idStr = clicked[0].id;

															var indexOfCount = "cteditor".length;
															var index = idStr.substring(indexOfCount,idStr.length);

															$('#removehigh'+ index).prop('disabled',true);//can be removed
															$('#highlight'+ index).prop('disabled',true);

														}

													})); 
                       
											
						//creare save response button functionality						
					$('input[id^="save_response"]').click(function() {
																var promptDocMap1 = new Object();
																var removePromptDocMap1=new Object();
																var essayContentMap1 = new Object();
																var clicked = $(this);
																var idStr = clicked[0].id;
																var indexOfCount = "save_response".length;
																var index = idStr.substring(
																		indexOfCount, idStr.length);

																
																var myPromptId = $("#promptId" + index).val();
																var text = $('#cteditor' + index).html();//val();																
																		
															    essayContentMap1[myPromptId] = text;
													
												               
												                var myTaskId = $("#taskId").val();
												             
												                
												                var linkMap1=false;																
																var linksArray1 = new Array();
													
													    $('[id^=cteditor]').each(function(){
														var currentEditor = $(this);
														var ideditor = currentEditor[0].id;								    	
														 $('#'+ideditor +'.ctclasseditor > a.highLightLink').each(function(){
														    linksArray1.push(true);
														      //console.log($(this).attr('href'));
														      //console.log('linksArray pushed true');
															});
														 
															
													});
													
														
													
													$('[id^=docsListForPrmpt]').each(function(){
														var currentPrompt = $(this);
														var idStr = currentPrompt[0].id;
														var indexOfCount = "docsListForPrmpt".length;
														var index = idStr.substring(indexOfCount, idStr.length);
														var promptId = $('#promptId'+index).val();													    											
														var docArray1 = new Array();
														$('#'+idStr+' li').each(function(){
																								
															var docId = $(this).attr('id');	
															//console.log('the doc id is '+docId);
															docArray1.push(docId);									
															
														});
														
														promptDocMap1[promptId] = docArray1;
													});
													

													$('[id^=removedocsListForPrmpt]').each(function(){
														var currentPrompt = $(this);
														var idStr = currentPrompt[0].id;
														var indexOfCount = "removedocsListForPrmpt".length;
														var index = idStr.substring(indexOfCount, idStr.length);
														var promptId = $('#promptId'+index).val();
														if(linksArray1.length>0)
														{
														//console.log('haslink');
														linkMap1 = true;
														}
														
														var removeArray1 = new Array();
														$('#'+idStr+' li').each(function(){
																								
															var removeDocId = $(this).attr('id');		
															//console.log('the remove doc id is '+removeDocId);
															removeArray1.push(removeDocId);									
															
														});
														
														removePromptDocMap1[promptId] = removeArray1;
													});
													
													
													
																$.ajax({   url : '/ereg-web/pss/task/editor/save',
																		    type : 'post',
																			contentType : "application/json",
																			data :  JSON.stringify({
																			    taskId : myTaskId,	
																			    essayMap:essayContentMap1,
																				promptDocs:promptDocMap1,
																				removeDocs:removePromptDocMap1,
																				hasLinks:linkMap1
																				
																				
																			}), 
																			success : function(data) {
																				$.alert("Response Saved Successfully", "Task Response Save Status");

																			},
																error: function(XMLHttpRequest, textStatus, errorThrown) { 
																	   alert("Status: " + textStatus); alert("Error: " + errorThrown);
																	}
																		});

															});							
						
					$('input[id^="wordCount"]').click(function(){
					
						var clicked = $(this);
						var idStr = clicked[0].id;
						var indexOfCount = "wordCount".length;
						var index = idStr.substring(
								indexOfCount, idStr.length);					
						//var value = $('#cteditor' + index).text();
						//var html = $('#cteditor' + index).html();
						var taskID = $("#taskId").val();	
						 var wordCountTotal=0;
						//console.log("before wordcount for task "+taskID);
						
						 /* $('#cteditor'+index +'.ctclasseditor > div h3').each(function(){
						 console.log('right here at criss cross apple sauce'+this);
						 }); */
						
						/*  html= html.replace(/<\/?([a-z]+)[^>]*>/gi, function(match, tag) { 							
							
							
							 return (tag.toLowerCase() === "a") ? match : "";
							});   */
					
						
						//console.log("after "+html);

						// console.log("the word count value is "+value);	
						
						  $('[id^=cteditor]').each(function(){
                        	   
								var currentEditor = $(this);
								var value=currentEditor.text();
								
								var charCount = value.trim().length;
							
								wordCountTotal+=charCount;
								
							});
						  var messagePart1 = "The total character count for all responses to this task thus far is ";
						  var messagePart2 = ". The maximum character count permitted for this Task is ";
						  var messagePart3 = ". You will not be able to submit a task that exceeds the maximum character count.";
						  var messageTitle = "Character Count Status";
						 //var charCount = value.trim().length;
						         if(taskID==1)
							{
						      
						   // $.alert('Your Character Count for all Response\'s is '+wordCountTotal +'. The maximum Character count allowed for all Response\'s for this Task is '+18000+'.','CharacterCount');
						     $.alert(messagePart1 + wordCountTotal + messagePart2 + 18000 + messagePart3, messageTitle);
							}
						   if(taskID==2)
							{
								      
								//$.alert('Your Character Count for all Response\'s is '+wordCountTotal +'. The maximum Character count allowed for all Response\'s for this Task is '+23000+'.','CharacterCount');
							   $.alert(messagePart1 + wordCountTotal + messagePart2 + 23000 + messagePart3, messageTitle);
							}
						     if(taskID==3)
							{
								      
								//$.alert('Your Character Count for all Response\'s is '+wordCountTotal +'. The maximum Character count allowed for all Response\'s for this Task is '+18000+'.','CharacterCount');
						    	 $.alert(messagePart1 + wordCountTotal + messagePart2 + 18000 + messagePart3, messageTitle);
						      }
						    if(taskID==4)
						    {
								      
							//$.alert('Your Character Count for all Response\'s is '+wordCountTotal +'. The maximum Character Count allowed for all Response\'s for this Task is '+20000+'.','CharacterCount');
						    	 $.alert(messagePart1 + wordCountTotal + messagePart2 + 20000 + messagePart3, messageTitle);
						    }
						         
					});
						
						$('#saveDraft').click(function(){
							var promptDocMap = new Object();
							var removePromptDocMap = new Object();
							var essayContentMap = new Object();
							var linkMap=false;
							var myTaskId = $("#taskId").val();
							var linksArray = new Array();
							
							
							$('[id^=cteditor]').each(function(){
								
								var currentEditor = $(this);
								var ideditor = currentEditor[0].id;								    	
								 $('#'+ideditor +'.ctclasseditor > a.highLightLink').each(function(){
								linksArray.push(true);
								      //console.log($(this).attr('href'));
								      //console.log('linksArray pushed true');
									});
								var indexOfCount = "cteditor".length;
								var index = ideditor.substring(
										indexOfCount, ideditor.length);
								var promptId = $('#promptId'+index).val();
								
								var text = currentEditor.html();
								
								essayContentMap[promptId] = text;
								
								
							});
							
							
						

							$('[id^=docsListForPrmpt]').each(function(){
								var currentPrompt = $(this);
								var idStr = currentPrompt[0].id;
								var indexOfCount = "docsListForPrmpt".length;
								var index = idStr.substring(indexOfCount, idStr.length);
								var promptId = $('#promptId'+index).val();
								
							
								
									if(linksArray.length>0)
									{
									
									linkMap = true;
									/* for (i = 0; i <linksArray.length; i++) {
										  if (!linksArray[i]) return false;
									}
									 return true; */
									
									
									}
								var docArray = new Array();
								$('#'+idStr+' li').each(function(){
																		
									var docId = $(this).attr('id');									
									docArray.push(docId);									
									
								});
								
								promptDocMap[promptId] = docArray;
							});
							

							$('[id^=removedocsListForPrmpt]').each(function(){
								var currentPrompt = $(this);
								var idStr = currentPrompt[0].id;
								var indexOfCount = "removedocsListForPrmpt".length;
								var index = idStr.substring(indexOfCount, idStr.length);
								var promptId = $('#promptId'+index).val();
								
								
								var removeArray = new Array();
								$('#'+idStr+' li').each(function(){
																		
									var removeDocId = $(this).attr('id');									
									removeArray.push(removeDocId);									
									
								});
								
								removePromptDocMap[promptId] = removeArray;
							});
							
							
							$
							.ajax({
								url : '/ereg-web/pss/task/draft/save',
								type : 'POST',
								contentType : "application/json",
								 data :  JSON.stringify({
								    taskId : myTaskId,
									essayMap:essayContentMap,
									promptDocs:promptDocMap,
									removeDocs:removePromptDocMap,
									hasLinks:linkMap
								}), 
								//dataType: 'json',
								success : function(data) {
									//console.log(data);
									$.alert("Task Draft is Saved Successfully", "Task Draft Save Status");
									//$('#submitTask').prop('disabled', false);
								},
								error: function(XMLHttpRequest, textStatus, errorThrown) { 
								   alert("Status: " + textStatus); alert("Error: " + errorThrown);
								}
							});
							
						});
						
						$('#submitTask').click(function(){
							 var wordCountTotal=0;
							 var taskID = $("#taskId").val();		
                           $('[id^=cteditor]').each(function(){
                        	   
								var currentEditor = $(this);
								var value=currentEditor.text();
								//var regex = /\s+/gi;
								//var wordCount = value.trim().replace(regex, ' ').split(' ').length;
								//var totalChars = value.length;
								var charCount = value.trim().length;
								//var charCountNoSpace = value.replace(regex, '').length;
								wordCountTotal+=charCount;
								
								
								
							});
 						  var messagePart1 = "The total character count for all responses to this task thus far is ";
						  var messagePart2 = ". The maximum character count permitted for this Task is ";
						  var messagePart3 = ". You will not be able to submit a task that exceeds the maximum character count.";
						  var messageTitle = "Character Count Status";

                                  if(wordCountTotal<=0)
                        	   {
                                	  $.alert("You have not provided written commentary for the guiding prompts for this Task. Tasks with no response cannot be submitted.","Submission Error");
                                	  return;
                        	   }
                           
							//console.log("the wordcount is "+wordCountTotal);
							     if(taskID==1)
								{
							       if(wordCountTotal>18000)
								{
									
							     $.alert(messagePart1 + wordCountTotal + messagePart2 + 18000 + messagePart3, messageTitle);

									return;
								}
								}
							      if(taskID==2)
									{
								       if(wordCountTotal>23000)
									{
									
									    $.alert(messagePart1 + wordCountTotal + messagePart2 + 23000 + messagePart3, messageTitle);
										return;
									  }
								    }
							     
							       if(taskID==3)
									{
								       if(wordCountTotal>18000)
									{
										
								    $.alert(messagePart1 + wordCountTotal + messagePart2 + 18000 + messagePart3, messageTitle);
										return;
									}
								      }
							        
							        if(taskID==4)
									{
								       if(wordCountTotal>20000)
									{
										
										$.alert(messagePart1 + wordCountTotal + messagePart2 + 20000 + messagePart3, messageTitle);								         
										return;
									}
									}
								
							       
							
						    $( "#submit-confirm" ).dialog({
						    	closeOnEscapeType:false,
						       /*  show: {
						            effect: "blind",
						            duration: 1000
						          },
						          hide: {
						            effect: "explode",
						            duration: 1000
						          }, */
						    	dialogClass:"no-close",
						        resizable: false,
						        width:500,
						        height:450,
						        modal: true,
						        buttons: {
						          "Yes, Submit Now": function() {						        	
						            if($('#confirmSubmit').is(":checked"))
						            {
						            	$( this ).dialog( "close" );
										var myTaskId = $("#taskId").val();							
										$.ajax({
											url : '/ereg-web/pss/task/submit/save',
											type : 'POST',								
											data : {
												taskId : myTaskId,
											},
											success : function(data) {
												//console.log(data);
												$('div[id^="editor_buttons"]').each(function(index){										
													$(this).empty();										
												});
												
												$('div[id^="cteditor"]').each(function(index){										
													$(this).prop('contenteditable',false);
												});
												
												$('#taskButtonsDiv').empty();
												window.location = '/ereg-web/secure/home';
												
											},
											error : function(data)
											{
												alert('Problem with submitting Task');
											}
											
										});
						            }
						            else
						            {
						            	//change the color of checkbox. style it.
						            	$('#submitConfirmLabel').css('color','red');
						            }
						          },
						          Cancel: function() {
						            $( this ).dialog( "close" );
						          }
						        }
						      }).siblings('.ui-dialog-titlebar').remove();

						});
						
						
						 $('#artifactTask').click(function(){
					        	window.location = '/ereg-web/pss/artifact/';
					        });
						
						$('#uploadVideo').click(function(){
							//console.log('upload video clicked');
							//console.log("the prompt keys is "+$("#promptVideoKey").val());
							//console.log("the task keys is "+$("#taskVideoKey").val());
							//var $j = jQuery.noConflict();
						
							var SplitTable='<p> Each video must be in avi, divx, flv, mov, mp4, mpeg, mpg or wmv format and cannot exceed XX MB </p>';
						
						    $("#videodialog").dialog({modal:true,width:600,height:300,position:['middle',20], dialogClass: 'no-close-dialog',buttons:[{text:"Close",click: function() { $(this).dialog('close'); }}]}) 
							.each(
									function(index) {
										
										$(this)
												.fineUploader(
														{
															//element: $('fineUploader')[0],
															request : {
																endpoint : '/ereg-web/pss/task/upload/video',
																forceMultipart : true,
																inputName : "file",
																/* enctype : "multipart/form-data", */
																params: {
																    
																       'promptId':$("#promptVideoKey").val(),
																       'taskId':$("#taskVideoKey").val(),
																      
														             },   
																// text: $('#videodialog')[0].append('<input type="text" name="name">'),
															},
															 
															
														             

															text : {
																//uploadButton:'<div>Select a file</div>'
																uploadButton : '<i class="icon-plus icon-white"></i> <font size=2>Select and Upload Video</font>'
																
															},
															
															
															validation: {
														        allowedExtensions: ['wmv','mp4','mpg','jpeg', 'jpg','flv'],
														        //sizeLimit: 5120000 // 50 kB = 50 * 1024 bytes
														       // sizeLimit: 2147483648 //2GB
														      },
														       showMessage: function(message) {
														          // Using Bootstrap's classes
														          $('#videodialog').append('<div class="alert alert-error">' + message + '</div>');
														        },
														        
														        
															
															formatFileName : function(
																	fileName) {
																if (fileName.length > 33) {
																	fileName = fileName
																			.slice(
																					0,
																					19)
																			+ '...'
																			+ fileName
																					.slice(-14);
																}
																//console.log('the filename is '+fileName);
																return fileName;
															}

														})
														
														
														
														/* .on("submitted", function(event, id, name) {
                                                                filenames.push(name);    
                                                          }) */
														
														.on('error', function(event, id, name, reason) {
															//console.log('coming here to error');
															 $(".alert-error").closest('div').remove();
															 
                                                          })
                                                          
                                                         /*  .on("validate", function(event, fileData) {
                                                             return $.inArray(filenames, fileData.name) < 0;
                                                             } */
                                                          
										                .on('complete', function(event, id, name, responseJSON) {
										                	//console.log('coming here to oncomplete '+id);
										                	var element= $('#fine-uploader')[0];
										                	var fileItemContainer = $(this).fineUploader('getItemByFileId', id);
										                	 $(fileItemContainer);
										                	 //console.log('the filename is '+name);
										                	 $(".alert-error").closest('div').remove();
										                	 if (responseJSON.success) {
										                		 //$(this).fineUploader('setParams', {'param1': 'val1'});
										                		 //console.log('coming here succes'+ name+ element);
										                		 location.reload();
										                	 return name;
										                	 }
										                     //.prepend(SplitTable);
										                     //.append('<input type="text" name="description">');
										             

									       /* var elem = $('.qq-upload-list li')[id];

									        $(elem).prepend('<div class="previewContainer"></div>').addClass('cf');
									        $(elem)
									            .children('.previewContainer')
									            .append($('<img src="/admin/model/media/image/view/tmp/'+response.result[0].name+'" class="previewItem"/>'));*/
										                	
									   });
										
										
										
										
									}); 
						    	
							  
						    	 $("#videodialog").append(SplitTable);
						    	 

						    	
							
						});//end $('#uploadVideo').click function
						
						$('#reviewVideo').click(function(){
							//console.log('review video clicked');
							var $j = jQuery.noConflict();
							var filenames = [];
							/* $.ajax({
								url : '/ereg-web/pss/task/review/video',
								type : 'GET',
								success : function(data) {
									//console.log(data);
									console.log("Reviwed video Successfully");
									
								},
								
								
							}); */
							var windowHeight = $(window).height();
							var windowWidth = $(window).width();
							var dHeight = $('#dialogBox').height();
							var dWidth = $('#dialogBox').width();
							$("#reviewhidden").css("display", "block");
							$("#reviewhidden").css({position:'absolute',
							  top:'45%',
							  left:'50%',
							  width:'400px',                 
							  height:'200px',            
							  zIndex:1000,
							  marginTop:'-150px',           
							  marginLeft:'-300px' }); 
							//$("#reviewhidden").css({top:windowHeight/2 - dHeight/2, left:windowWidth/2 - dWidth/2}).show();
						});
						
						$('#closeVideo').click(function(){
							//'scriptAccess' : 'always'
							//System.security.allowDomain("https://cdnapisec.kaltura.com");
							//console.log('close video clicked');
							//document.getElementById("kaltura_player_1372798520").sendNotification('doStop');
							 $("#kaltura_player_1372798520").get(0).sendNotification('doStop');
							$("#reviewhidden").css("display", "none");
							 
						});
						
					
						$("#expandAccordions").click(function(e) {
						    //console.log('expand all clicked');
						   
						     $('#accordion h3').removeClass('ui-state-default')
					        .addClass('ui-state-active')
					        .removeClass('ui-corner-all')
					        .addClass('ui-corner-top')
					        .attr('aria-expanded', 'true')
					        .attr('aria-selected', 'true')
					        .attr('tabIndex', 0)
					        .find('span.ui-icon')
					        .removeClass('ui-icon-circle-arrow-e')
					        .addClass('ui-icon-circle-arrow-s')
					     .closest('h3').next('div').show();
						     
						     $('#accordion-1 h3').removeClass('ui-state-default')
						        .addClass('ui-state-active')
						        .removeClass('ui-corner-all')
						        .addClass('ui-corner-top')
						        .attr('aria-expanded', 'true')
						        .attr('aria-selected', 'true')
						        .attr('tabIndex', 0)
						   .find('span.ui-icon')
						        .removeClass('ui-icon-circle-arrow-e')
						        .addClass('ui-icon-circle-arrow-s')
						     .closest('h3').next('div').show();
						});
						   
						    
							
					    
						
						
						$('#collapseAccordions').click(function() {
						    //console.log('collpase all clicked');
						  /*  $('#accordion .ui-accordion-content').slideToggle(); */
						   
						    $('#accordion h3').removeClass('ui-state-active')
						            .addClass('ui-state-default')
						            .removeClass('ui-corner-top')
						            .addClass('ui-corner-all')
						            .attr('aria-expanded', 'false')
						            .attr('aria-selected', 'false')
						            .attr('tabIndex', -1)
						        .find('span.ui-icon')
						            .removeClass('ui-icon-circle-arrow-e')
						            .addClass('ui-icon-circle-arrow-s')
						        .closest('h3').next('div').hide();
						   /*  $('#accordion').accordion('destroy').accordion(); */
						   
						    $('#accordion-1 h3').removeClass('ui-state-active')
				            .addClass('ui-state-default')
				            .removeClass('ui-corner-top')
				            .addClass('ui-corner-all')
				            .attr('aria-expanded', 'false')
				            .attr('aria-selected', 'false')
				            .attr('tabIndex', -1)
				        .find('span.ui-icon')
				            .removeClass('ui-icon-circle-arrow-e')
				            .addClass('ui-icon-circle-arrow-s')
				        .closest('h3').next('div').hide();
						 
					    });
						
						
						 $("#accordion").accordion({heightStyle: "content",collapsible: true,active:false,icons: accordicons,beforeActivate: function(event, ui) {
								{
							//console.log('right here');
							  // The accordion believes a panel is being opened
				            if (ui.newHeader[0]) {
				                var currHeader  = ui.newHeader;
				                var currContent = currHeader.next('.ui-accordion-content');
				             // The accordion believes a panel is being closed
				            } else {
				                var currHeader  = ui.oldHeader;
				                var currContent = currHeader.next('.ui-accordion-content');
				            }
				             // Since we've changed the default behavior, this detects the actual status
				            var isPanelSelected = currHeader.attr('aria-selected') == 'true';
				            
				             // Toggle the panel's header
				            currHeader.toggleClass('ui-corner-all',isPanelSelected).toggleClass('accordion-header-active ui-state-active ui-corner-top',!isPanelSelected).attr('aria-selected',((!isPanelSelected).toString()));
				            
				            // Toggle the panel's icon
				            currHeader.children('.ui-icon').toggleClass('ui-icon-circle-arrow-e',isPanelSelected).toggleClass('ui-icon-circle-arrow-s',!isPanelSelected);
				            
				             // Toggle the panel's content
				            currContent.toggleClass('accordion-content-active',!isPanelSelected)    
				            if (isPanelSelected) { currContent.slideUp(); }  else { currContent.slideDown(); }

				          return false; // Cancels the default action
							    
								}}});
					});
					
							 
						
					
</script>

<style>
#accordion {
	
	width: 98.5%;
    
}

.imagetag {
	float: right;
}

.essay_div {
	height: 400px;
}

.ctclasseditor {
	height:280px;
	width: 98.5%;
	resize: none margin-left:50px;
	margin:auto;
	/* margin-right: 0px;
	margin-left: 0px; */
	/* border: 1.0px solid #000; */
	overflow: auto;
	border: 1px solid #ccc;
	/* border-right: 2px solid #ccc; */
    /* border-bottom: 2px solid #ccc; */
    -webkit-border-radius: 5px;
    -moz-border-radius: 5px;
    border-radius: 5px;
    border: 1px solid #999; margin-bottom: 1em; height:275px; overflow:auto;
	/* -webkit-user-select: auto; */
}

a.highLightLink {
	 /* color: red; */
	font-size: 1.2em;
}

.editor {
	height: 400px;
	width: 650px;
	resize: none margin-left:    50px;
	margin-right: 50px;
}

.center {
	margin-left: auto;
	margin-right: auto;
	width: 100%;
	
}

.taskbuttonsdiv {
	margin-left: auto;
	margin-right: auto;
	width: 100%;
	height:80px; 
	margin-top	:25px;	
	margin-bottom:25px;	
	text-align:center;
	
}

.taskbuttons{
    border:12px;
	padding:8px;
	border-style:"solid";
	background-color:#EFEFEF;	
 }
 
 .uploadVideo-oldbutton{
    border:12px;
	padding:8px;
	border-style:"solid";
	margin-left:10px;		
	margin-bottom:15px;	
	background-color:#EFEFEF;	
 }
 
 
 
 .uploadVideobutton {
	background-color: #1982d3;length:10px; width:10px; padding-left: 5px; padding-right: 5px;
	background: -webkit-gradient(linear, left top, left bottom, from(#1982d3), to(#1f95f0));
	background: -webkit-linear-gradient(top, #1982d3, #1f95f0);
	background: -moz-linear-gradient(top, #1982d3, #1f95f0);
	background: -ms-linear-gradient(top, #1982d3, #1f95f0);
	background: -o-linear-gradient(top, #1982d3, #1f95f0);
	background: linear-gradient(top, #1982d3, #1f95f0);
	border: 1px solid #1f74b6;
	border-bottom: 1px solid #1868a6;
	border-radius: 3px;
	-webkit-border-radius: 3px;
	-moz-border-radius: 3px;
	-ms-border-radius: 3px;
	-o-border-radius: 3px;
	box-shadow: inset 0 1px 0 0 #55a0da;
	-webkit-box-shadow: 0 1px 0 0 #55a0da inset ;
	-moz-box-shadow: 0 1px 0 0 #55a0da inset;
	-ms-box-shadow: 0 1px 0 0 #55a0da inset;
	-o-box-shadow: 0 1px 0 0 #55a0da inset;
	 color: white;
	font-weight: regular;
	padding: 4px 18px;
	text-align: center;
	text-shadow: 0 -1px 0 #124e7c;
	border-top-right-radius: 5px;
    border-top-left-radius: 5px;
    border-bottom-right-radius: 5px;
    border-bottom-left-radius: 5px;
    
   filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#003333CC', endColorstr='#696969C', GradientType=0);
   border-color: #006666C #006666C #000000;
   border-color: rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.25);

   filter: progid:DXImageTransform.Microsoft.gradient(enabled = false);
  -webkit-box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.1), 0 1px 0 rgba(255, 255, 255, 0.075);
  -moz-box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.1), 0 1px 0 rgba(255, 255, 255, 0.075);
  box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.1), 0 1px 0 rgba(255, 255, 255, 0.075);
    
   /*   filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#696969', endColorstr='#696969'); 
    filter: progid:DXImageTransform.Microsoft.Blur(PixelRadius=3,MakeShadow=true,ShadowOpacity=0.30);
	-ms-filter: "progid:DXImageTransform.Microsoft.Blur(PixelRadius=3,MakeShadow=true,ShadowOpacity=0.30)";
	zoom: 1;  */
	
	margin-left:10px;		
	margin-bottom:15px;	
 
 
 
}

.uploadVideobutton:hover {
	opacity:.85; text-decoration:none;
	cursor: pointer; 
}

.uploadVideobutton:active {
	border: 1px solid #175e95;
	box-shadow: 0 0 10px 5px #14629f inset; 
	-webkit-box-shadow:0 0 10px 5px #14629f inset ;
	-moz-box-shadow: 0 0 10px 5px #14629f inset;
	-ms-box-shadow: 0 0 10px 5px #14629f inset;
	-o-box-shadow: 0 0 10px 5px #14629f inset;
	text-shadow: 0 -1px 0 gray;
	
}

.uploadVideobutton:disabled {
    background: #696969 ;
   -moz-user-select: -moz-none;
   -khtml-user-select: none;
   -webkit-user-select: none;
   /*
     Introduced in IE 10.
     See http://ie.microsoft.com/testdrive/HTML5/msUserSelect/
   */
   -ms-user-select: none;
    -o-user-select: none;
   user-select: none;
   
    background: -webkit-gradient(linear, left top, left bottom, from(#696969), to(#696969));
	background: -webkit-linear-gradient(top, #696969, #696969);
	background: -moz-linear-gradient(top, #696969, #696969);
	background: -ms-linear-gradient(top, #696969, #696969);
	background: -o-linear-gradient(top, #696969, #696969);
	background: linear-gradient(top, #696969, #696969);
	border: 1px solid #696969;
	border-bottom: 1px solid #696969;
	border-radius: 3px;
	-webkit-border-radius: 3px;
	-moz-border-radius: 3px;
	-ms-border-radius: 3px;
	-o-border-radius: 3px;
	box-shadow: inset 0 1px 0 0 #696969;
	-webkit-box-shadow: 0 1px 0 0 #696969 inset ;
	-moz-box-shadow: 0 1px 0 0 #696969 inset;
	-ms-box-shadow: 0 1px 0 0 #696969 inset;
	-o-box-shadow: 0 1px 0 0 #696969 inset;
	color: white;
	font-weight: regular;
	padding: 4px 18px;
	text-align: center;
	text-shadow: 0 -1px 0 #696969;
	border-top-right-radius: 5px;/* for IE */
    border-top-left-radius: 5px;
    border-bottom-right-radius: 5px;
    border-bottom-left-radius: 5px;
    /*  filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#696969', endColorstr='#696969'); 
    filter: progid:DXImageTransform.Microsoft.Blur(PixelRadius=3,MakeShadow=true,ShadowOpacity=0.30);
	-ms-filter: "progid:DXImageTransform.Microsoft.Blur(PixelRadius=3,MakeShadow=true,ShadowOpacity=0.30)";
	zoom: 1; */
	
	filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='    #003333CC', endColorstr='#696969C', GradientType=0);
  border-color: #006666C #006666C #000000;
  border-color: rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.25);
  
  filter: progid:DXImageTransform.Microsoft.gradient(enabled = false);
  -webkit-box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.1), 0 1px 0 rgba(255, 255, 255, 0.075);
  -moz-box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.1), 0 1px 0 rgba(255, 255, 255, 0.075);
  box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.1), 0 1px 0 rgba(255, 255, 255, 0.075); 
	
}
 

.right {
	position: absolute;
	top: 20px;
	right: 0px;
	width: 150px;
	
}

div.videoinner
{	
	width:600px;
	height:33x;
	border-style:solid;

 	/* border-width:1px;
	border-radius: 3px;
	background-color:#f0f0f0;
	margin-left: auto;
	margin-right: auto;	
	text-align:left; */
	
	border: 1px solid #ccc;
	/* border-right: 2px solid #ccc; */
    /* border-bottom: 2px solid #ccc; */
    -webkit-border-radius: 5px;
    -moz-border-radius: 5px;
    border-radius: 5px;
    text-align:left;
    text-align: center;
    background-color: #f2f2f2;/* #888888; *//* #004B8E; */ /* #fa9300;  */
    font-size: 14px;
    font-style: italic;
    
    /* font-size: 15px;
    color: #FFF; */
   /*  font-style: oblique; */
    /* font-style: oblique;
     
    text-align: center; */
   /*  margin-top: 20px;
    margin-left: 5px; */
    /* line-height: 90px; */
    
	
	
}

div.videoouter
{
	width:600px;	
	border-radius: 3px;
	border-style:solid;	
	border-width:1px;
	margin-left: auto;
	margin-right: auto;	
	margin-bottom:25px;	
	
	border: 1px solid #ccc;
	/* border-right: 2px solid #ccc; */
    /* border-bottom: 2px solid #ccc; */
    -webkit-border-radius: 5px;
    -moz-border-radius: 5px;
    border-radius: 5px;
}

div.video
{
	padding:10px 10px 10px 10px;
}

 .no-close .ui-dialog-titlebar-close {
	display: none;
	} 

.no-close-dialog .ui-dialog-titlebar-close {
	display: none;
	/* top:'20%';
	left:'50%'; */
} 


.promptbutton {
	background-color: #1982d3;length:10px; width:10px; padding-left: 5px; padding-right: 5px;
	background: -webkit-gradient(linear, left top, left bottom, from(#1982d3), to(#1f95f0));
	background: -webkit-linear-gradient(top, #1982d3, #1f95f0);
	background: -moz-linear-gradient(top, #1982d3, #1f95f0);
	background: -ms-linear-gradient(top, #1982d3, #1f95f0);
	background: -o-linear-gradient(top, #1982d3, #1f95f0);
	background: linear-gradient(top, #1982d3, #1f95f0);
	border: 1px solid #1f74b6;
	border-bottom: 1px solid #1868a6;
	border-radius: 3px;
	-webkit-border-radius: 3px;
	-moz-border-radius: 3px;
	-ms-border-radius: 3px;
	-o-border-radius: 3px;
	box-shadow: inset 0 1px 0 0 #55a0da;
	-webkit-box-shadow: 0 1px 0 0 #55a0da inset ;
	-moz-box-shadow: 0 1px 0 0 #55a0da inset;
	-ms-box-shadow: 0 1px 0 0 #55a0da inset;
	-o-box-shadow: 0 1px 0 0 #55a0da inset;
	color: white;
	font-weight: regular;
	padding: 4px 18px;
	text-align: center;
	text-shadow: 0 -1px 0 #124e7c;
	border-top-right-radius: 5px;
    border-top-left-radius: 5px;
    border-bottom-right-radius: 5px;
    border-bottom-left-radius: 5px;
    
    /* filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#696969', endColorstr='#696969'); 
    filter: progid:DXImageTransform.Microsoft.Blur(PixelRadius=3,MakeShadow=true,ShadowOpacity=0.30);
	-ms-filter: "progid:DXImageTransform.Microsoft.Blur(PixelRadius=3,MakeShadow=true,ShadowOpacity=0.30)";
	zoom: 1; */
	
 filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#003333CC', endColorstr='#696969C', GradientType=0);
  border-color: #006666C #006666C #000000;
  border-color: rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.25);

filter: progid:DXImageTransform.Microsoft.gradient(enabled = false);
  -webkit-box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.1), 0 1px 0 rgba(255, 255, 255, 0.075);
  -moz-box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.1), 0 1px 0 rgba(255, 255, 255, 0.075);
  box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.1), 0 1px 0 rgba(255, 255, 255, 0.075);
 
 
 
}

.promptbutton:hover {
	opacity:.85; text-decoration:none;
	cursor: pointer; 
}
.promptbutton:active {
	border: 1px solid #175e95;
	box-shadow: 0 0 10px 5px #14629f inset; 
	-webkit-box-shadow:0 0 10px 5px #14629f inset ;
	-moz-box-shadow: 0 0 10px 5px #14629f inset;
	-ms-box-shadow: 0 0 10px 5px #14629f inset;
	-o-box-shadow: 0 0 10px 5px #14629f inset;
	text-shadow: 0 -1px 0 gray;
	
}


 .promptbutton:disabled {
    background: #696969 ;
   -moz-user-select: -moz-none;
   -khtml-user-select: none;
   -webkit-user-select: none;
   /*
     Introduced in IE 10.
     See http://ie.microsoft.com/testdrive/HTML5/msUserSelect/
   */
   -ms-user-select: none;
    -o-user-select: none;
   user-select: none;
   
    background: -webkit-gradient(linear, left top, left bottom, from(#696969), to(#696969));
	background: -webkit-linear-gradient(top, #696969, #696969);
	background: -moz-linear-gradient(top, #696969, #696969);
	background: -ms-linear-gradient(top, #696969, #696969);
	background: -o-linear-gradient(top, #696969, #696969);
	background: linear-gradient(top, #696969, #696969);
	border: 1px solid #696969;
	border-bottom: 1px solid #696969;
	border-radius: 3px;
	-webkit-border-radius: 3px;
	-moz-border-radius: 3px;
	-ms-border-radius: 3px;
	-o-border-radius: 3px;
	box-shadow: inset 0 1px 0 0 #696969;
	-webkit-box-shadow: 0 1px 0 0 #696969 inset ;
	-moz-box-shadow: 0 1px 0 0 #696969 inset;
	-ms-box-shadow: 0 1px 0 0 #696969 inset;
	-o-box-shadow: 0 1px 0 0 #696969 inset;
	color: white;
	font-weight: regular;
	padding: 4px 18px;
	text-align: center;
	text-shadow: 0 -1px 0 #696969;
	border-top-right-radius: 5px;/* for IE */
    border-top-left-radius: 5px;
    border-bottom-right-radius: 5px;
    border-bottom-left-radius: 5px;
    /* filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#696969', endColorstr='#696969'); 
     filter: progid:DXImageTransform.Microsoft.Blur(PixelRadius=3,MakeShadow=true,ShadowOpacity=0.30);
	-ms-filter: "progid:DXImageTransform.Microsoft.Blur(PixelRadius=3,MakeShadow=true,ShadowOpacity=0.30)";
	zoom: 1; */
  filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='    #003333CC', endColorstr='#696969C', GradientType=0);
  border-color: #006666C #006666C #000000;
  border-color: rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.25);
  
  filter: progid:DXImageTransform.Microsoft.gradient(enabled = false);
  -webkit-box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.1), 0 1px 0 rgba(255, 255, 255, 0.075);
  -moz-box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.1), 0 1px 0 rgba(255, 255, 255, 0.075);
  box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.1), 0 1px 0 rgba(255, 255, 255, 0.075); 
}


 .ui-accordion-header{
   background:#888888;
} 
 
 

#accordion {
	width: 100%;
	margin: 0px 0px auto;
	
}

/* .ui-accordion-header {
margin-bottom:25px;
}
.ui-accordion-header.ui-state-active {
margin-bottom:25px
}
.ui-accordion-content-active {
margin-bottom:25px;
} */

/* ui-accordion-header ui-helper-reset ui-state-default ui-corner-all ui-accordion-icons */
 
  /* #accordion .ui-accordion-header> h3 {
  margin-top: 50px;
  }
  
  #accordion .ui-accordion-header > h3:first-child {
    margin-top: 0;
  } */
  
  

/* #accordion .ui-accordion-content:active {background: #FFCC00; text-decoration: none} */
/* #accordion .ui-accordion-content :visited {background: #FFCC00; text-decoration: none} */
#accordion .ui-accordion-content a {
	 background-color: #B2FF99;
	 text-decoration: underline;
	/* color:  #777;   */
}

#accordion .ui-accordion-content a:hover {
	background-color:#B2FF99;font-weight:bold; color: red;
}

#accordion .ui-accordion-content {
	
	/* background-color: #f3f3f3; */
	/* color: #777; */
	font-size: 10pt; */
	line-height: 16pt;	
	/* border: 1.0px solid #000;  */
}

#accordion .ui-accordion-header a {
	text-shadow: 1px 1px 0px rgba(0,0,0,0.2);
	text-shadow: 1px 1px 0px rgba(0,0,0,0.2);
	border-right: 1px solid rgba(0, 0, 0, .2);
	border-left: 1px solid rgba(0, 0, 0, .2);
	border-bottom: 1px solid rgba(0, 0, 0, .2);
	border-top: 1px solid rgba(250, 250, 250, .2);
}

#accordion .ui-accordion-header a {
	color: #fff;
	line-height: 42px;
	display: block;
	font-size: 12pt;
	width: 100%;
	text-indent: 10px;
	/* -webkit-border-radius: 5px;
    -moz-border-radius: 5px;
    border-radius: 5px;  */
}

#accordion .ui-accordion-content > * {
	margin: 0;
	padding: 20px;
	margin-right: 1px;
	margin-left: 3px;
   /*  border: 1px solid #ccc; */
	/* border: 1px solid #000;  */
	/*  -webkit-border-radius: 5px;
    -moz-border-radius: 5px;
    border-radius: 5px; */
	
}

   #accordion .ui-accordion-content {
	box-shadow: inset 0px -1px 0px 0px rgba(0, 0, 0, .4),
				inset 0px 1px 1px 0px rgba(0, 0, 0, .2);
}
   #accordion .ui-accordion-content:last-of-type {
	box-shadow: inset 0px 1px 1px 0px rgba(0, 0, 0, .2),
				inset 0px 0 0px 0px rgba(0, 0, 0, .5);
}  
 
#accordion .ui-accordion-header {
	background-color: #f2f2f2;/* #888888; */
	/* margin: 2px; */
	/* border: 1px solid #000; */  
	/* -webkit-border-radius: 5px;
    -moz-border-radius: 5px;
    border-radius: 5px;  */
}



 
 
 div.rounded {
		border: 1px solid #999;
		border-radius: 10px;
		padding: 0.5em 1em;
		padding: 0.5em 1em 0.5em 1.4em; /* indent all for the show-hide trigger */
		margin-bottom: 1em;
		overflow: hidden;
		}

	div.rounded > .shaded {
		margin: -0.5em -1em 1em -1em;
		padding: 0.8em 1em;
		margin: -0.5em -1em 1em -2em; /* indent all for the show-hide trigger */
		padding: 0.8em 1em 0.8em 2em;
		}

	div.shaded {
		background: #f2f2f2;
		margin-bottom: 1em;
		}

	.shaded,
	p.shaded {
		background: #f2f2f2;
		}

	div.rounded.show-hide {
		padding-bottom: 0;
		}
		
	div.rounded .trigger {
		margin-left: -3em; /* indent all for the show-hide trigger */
		cursor: pointer;
		color: #003082;
		}
		
	div.rounded .trigger .icon {
		display: inline-block;
		width: 1em;
		text-align: center;
		}

	div.rounded .trigger.closed {
		margin-bottom: 0;
		}
	.toggle {
		margin-top: 1em;
		}
	
	div.rounded ul {
		list-style: disc;
		margin-left: 0;
		padding-left: 1.3em;
		}
		
		
	/* -- -- -- -- -- -- -- -- -- -- -- -- -- -- */
	/* start: anchor rules */

		a {
			color: #003082;
			white-space: normal;
			
			}
		a:link {text-decoration: underline;}
		a:visited { color: #003082; }
		a:active {}
		a:hover {
			}

		a img {
			
			}

		h1 a, h2 a, h3 a, h4 a, h5 a, h6 a {
			color: #003082;
			text-decoration: none;
			}
		h1 a:hover, h2 a:hover, h3 a:hover, h4 a:hover, h5 a:hover, h6 a:hover {
			text-decoration: underline;
			}

	/* end: anchor rules */
	/* -- -- -- -- -- -- -- -- -- -- -- -- -- -- */
	
	
		
	/* -- -- -- -- -- -- -- -- -- -- -- -- -- -- */
	/* breadcrumb */

		#breadcrumb {
			font-size: 80%;
			margin-bottom: 3em;
			color: #999;
			}

		#breadcrumb a {
			color: #999;
			}

	/* end: breadcrumb */
	/* -- -- -- -- -- -- -- -- -- -- -- -- -- -- */

     /* expand all accordion and collapse all*/
	/* -- -- -- -- -- -- -- -- -- -- -- -- -- -- */
       .expand_all {
			font-size: 100%;			
			color: #003082;
			text-decoration:none;
			}

		.expand_all a {
			color: #003082;
			display:inline;
			cursor:pointer;
			text-decoration:none;
			
			}
			
			.expand_all a:hover {
               cursor:pointer;
               text-decoration:none;
               
            }
			
		 .collapse_all {
			font-size: 100%;			
			color: #003082;
			text-decoration:none;
			
			
			}

		.collpase_all a {
			color: #003082;
			display:inline;
			cursor:pointer;
			text-decoration:none;
			
			
			}
			
			.collpase_all a:hover {
               cursor:pointer;
               text-decoration:none;
                }
                
                 /* end if expand all accordion and collapse all*/
			
</style>
</head>

            
           
			
	<%
		Logger logger = Logger.getLogger(this.getClass().getName());
	%>
	
	<t:base title="Home Page">
   <div id="breadcrumb">
   	<p class="hide">Site Path:</p>
	<a href="/ereg-web/secure/home">Home</a> <span aria-hidden="true">&gt;</span>
	<a href="/ereg-web/secure/home">My Tasks</a> <span aria-hidden="true">&gt;</span>
	<a href="/ereg-web/pss/task/goto?taskId=${task.taskId}">CurrentTask</a> <span aria-hidden="true"></span>
    </div>
    
   <!-- <div id="expandocollpase" align="right"> 
	            <a id ='expandAccordions' href="#" class="expand_all">[Expand All]</a>&nbsp;&nbsp;|&nbsp;<a id ='collapseAccordions'  href="#" class="collapse_all" >[Collapse All]</a>
    </div>  -->
            
    
	<div class="headContainer">
	
		<div class="row">
			<div class="span9">
				<%-- <h1><center><spring:message code="home.welcome.heading" arguments="${globalContextCustomer.currentProgramShortDescription}"/></center></h1> --%>
				<h1><left><spring:message code="myassessments.tasks.preserviceTeacher" /></left></h1> <h3>${task.title}</h3><br>
				
 				<div id="message"><spring:message code="tasks.currentTask.description" /> </div>				
				<p style="color:red; margin:10px 0 0 0;">${STATUS_MESSAGE}</p>
			</div>
		</div>
	</div>
	<!-- homeContainer Starts -->
	<div> <!-- id="homeContainer" -->
		<!-- Column1 Starts -->
		
		<%-- <div class="column1">
			<div class="block">
				<h3>${customer.firstName} ${customer.lastName}</h3>
				<div class="content">
					<spring:message code="home.testtakerId.label"/> # ${testTakerId}
				</div>
			</div>
			<div class="block">
				<h3><spring:message code="home.profile.header"/></h3>
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
		</div>  --%>
		<!-- Column1 Ends -->
		<!-- Column2 Starts -->
		<div class="column2">

		<div>
		<div id="rightSide" >
            <div id="jquery-wrapped-video-uploader"></div>
           
          <!--  <div id="video" class="four">
				<div class="three"><h5></h5></div>				
				<div class="five"><p>Teching Video.mp3  <a href="">Video</a></p></div>
				<input type=button id="uploadVideo" class="uploadVideobutton" value="Upload Video" />
				
			</div> -->
	
    							
   <div id="reviewhidden" style="display: none">
  
  <c:if test="${not empty videoResponses}">
  <c:forEach var="entry" items="${videoResponses}">
  

<%-- <script src="http://cdnapi.kaltura.com/p/958691/sp/95869100/embedIframeJs/uiconf_id/15210901/partner_id/958691?autoembed=true&entry_id=${entry.value}&playerId=kaltura_player_1372798520&cache_st=1372798520&width=400&height=333&flashvars[akamaiHD.loadingPolicy]=preInitialize&flashvars[akamaiHD.asyncInit]=true&flashvars[streamerType]=hdnetwork"></script>   --%>
<script src="https://cdnapisec.kaltura.com/p/958691/sp/95869100/embedIframeJs/uiconf_id/15210901/partner_id/958691?autoembed=true&entry_id=${entry.value}&playerId=kaltura_player_1372798520&cache_st=1372798520&width=560&height=395&flashvars[akamaiHD.loadingPolicy]=preInitialize&flashvars[akamaiHD.asyncInit]=true&flashvars[streamerType]=hdnetwork"></script>
  </c:forEach> 
  </c:if>
   </div>
  
	   <%--  video: ${entry.key} - value = ${entry.value} --%>
	<div id="expandocollpase" align="right"> 
	           <a id ='artifactTask' href="#" class="expand_all">[Upload/Manage My Artifacts]</a>&nbsp;&nbsp;|&nbsp; <a id ='expandAccordions' href="#" class="expand_all">[Expand All]</a>&nbsp;&nbsp;|&nbsp;<a id ='collapseAccordions'  href="#" class="collapse_all" >[Collapse All]</a>
    </div> 
	<div id="documentsDiv" style="display: none"></div>
	<div id="linkdialog" title="Link to Artifact"
		style="display: none">
	</div>
	<div id="submit-confirm" title="Submit" style="display:none;background-color:#FCE7E4;">
   		<p style="font-size:9pt;font-weight:bold;color:red"><span class="ui-icon ui-icon-alert" style="float: left; margin: 0 7px 20px 0;"></span>Are you Sure you want to submit your task including all essay responses and artifacts associated with this task?</p>
   		<p style="font-size:11pt;font-weight:bold;">Once you submit your task, you will not be able to make any modifications to the essay responses or the artifacts associated with task</p>
   		<p><input type="checkbox" id="confirmSubmit" value="true"/><label for="confirmSubmit" id="submitConfirmLabel" style="font-size:8pt;white-space: wrap;width:450;">By Checking this box, I understand that I am submitting my responses and artifacts associated with this task. I certify that the submissions represents the work that I completed. I understand that the responses andartifacts that I submit will be evaluated by educators, raters or other appropriate individuals and I understand that I will not be able to make any modifications once I click Submit.</label></p>
	</div>
	<input type="hidden" id="taskId" value="${task.taskId}" />	
	<%-- <c:out value="taskid is: ${task.taskId}" /> --%>
	
	<!--  new  -->
	 
			    <div id="accordion-1" class="center"  >
			    <h3>${task.title2}</h3>
			    <div>
			     <div class="rounded">				
				<!-- <div class="taskstatus" style="padding: 10px 0 0 0;"><span>Status: New</span></div> -->
				<div class="taskinstr" style="padding: 20px 0px 0px 0px;"><span>${task.instructions}</span></div>
		          </div>
                </div>
                </div>
				<%-- <c:out value="TASKSTATUS is: ${customerTask.docStsTyp.docStsTypCde}" /> --%>
				 <div id="accordion" class="center"  >	
					<c:forEach var="step" varStatus="status" items="${task.stepDTOs}">
					
								<c:forEach var="prompt" varStatus="promtStatus" items="${step.promptDTOs}">
								<%-- <c:out value="stepid is: ${step.stepID}" />-<c:out value="prompt id is: ${prompt.promptId}" /> --%>
									<h3>${step.title} ${prompt.title}</h3>
									<div >
										
									    <c:if test="${prompt.media=='video'}">  
									                
										         <input type="hidden" id="videoKey" value="<c:out value="${prompt.media}"/>" />	
										          <input type="hidden" id="promptVideoKey" value="<c:out value="${prompt.promptId}"/>" />
										           <input type="hidden" id="taskVideoKey" value="<c:out value="${task.taskId}"/>" />												   
											    <div id="video" class="videoouter">
												<div class="videoinner"><h5>VIDEO UPLOAD</h5></div>				
												<div class="video"><p><h5>Teaching Video.mp3</h5> <!-- <a href="">Video</a> --></p></div>
												
												<c:if test="${customerTask.docStsTyp.docStsTypCde!='CMPLD'}">												
												<input type=button id="uploadVideo" class="uploadVideobutton" value="Upload Video" />
												</c:if>
												<input type=button id="reviewVideo" class="uploadVideobutton" value="Review Video" />
												<input type=button id="closeVideo" class="uploadVideobutton" value="Close Video" />
												<div id="videodialog" title="Upload Video" style="display: none"></div>				
											    </div>											
											</c:if> 
											
											
										 <c:if test="${prompt.media!='video'}"> 
										
									    <div class="step${status.count}" id="step${status.count}" style=" padding: 5px 0px 0px 0px;  ">
									     
									     <div class="rounded">
										<div class="instructions"><span>${step.instructions}</span></div>
										
										<div class="prompts" style="padding: 5px 0px 0px 0px;  margin-right: 4px; margin-top:1px; margin-left: 4px; margin-bottom: 1px; /* border: 1px solid #ccc;-webkit-border-radius: 5px;-moz-border-radius: 5px;border-radius: 5px; */">
										<div class="activity" style="padding: 5px 0px 0px 0px;"><span style="font-size: 1.1em"></span><span>${prompt.instructions }</span></div>
										<%-- <div class="activity" style="padding: 5px 0px 0px 0px;"><span style="font-size: 1.1em">Activity:</span><span>${prompt.activity }</span></div> --%>
										<%-- <div class="prompttitle" style="padding: 20px 0 0 0"><span style="font-size: 1.2em">${prompt.title}</span></div> --%>								
										<div class="prompt" style="padding: 4px 0 0 0 "><span style="font-size: 1.2em"></span><br></div> 
										
										<div class="prompt ">
											<li>
												<c:forEach var="guide" varStatus="guideCnt" items="${prompt.guides}">
													<li>${guide.value}</li>
												</c:forEach>
											</li>
										</div>
										
										
										
										<!--  old -->		
										
										<div id="essay_div${prompt.promptId}" class="essay_div">
											<input type="hidden" id="taskId${prompt.promptId}"
												value="${task.taskId}" /> <input type="hidden"
												id="promptId${prompt.promptId}" value="${prompt.promptId}" />
											<div id="essay_instructions${prompt.promptId}">
												<%-- <c:out value="${prompt.instructions}" /> --%>
											</div>
											<c:if test="${customerTask.docStsTyp.docStsTypCde!='CMPLD'}">
											<c:if test="${prompt.media!='video'}">  	
												<div id="editor_buttons${prompt.promptId}">	
												       	<input type="button"  class="promptbutton" style="margin-left: 2.2px;" value="Character Count"  id="wordCount${prompt.promptId}" />										
													    <input type="button" class="promptbutton"  value="Save Response" id="save_response${prompt.promptId}" />    
														<input type="button" class="promptbutton" value="Link Text" disabled id="highlight${prompt.promptId}" /> 														
														<input type="button"  class="promptbutton" value="Remove Link" disabled id="removehigh${prompt.promptId}" /> 														 
												</div>
												</c:if>
											</c:if>
											
										  
												<input type="hidden" id="existingEssayContent${prompt.promptId}"
												value="<c:out value="${promptResponses[prompt.promptId]}"/>" /> 
											   <div id="cteditor${prompt.promptId}" spellcheck="true" class="ctclasseditor" contenteditable='<c:choose><c:when test="${customerTask.docStsTyp.docStsTypCde!='CMPLD'}">true</c:when><c:when test="${customerTask.docStsTyp.docStsTypCde=='CMPLD'}">false</c:when></c:choose>'>
											    </div>
																					
											<div id="docsDivForPrmpt${prompt.promptId}" style="display: none">
											<ul id="docsListForPrmpt${prompt.promptId}"></ul>
											<ul id="removedocsListForPrmpt${prompt.promptId}"></ul>
											</div>
										</div>			
							<!-- old end -->
							</div>	
							</div>	
							</div><!-- end if div class='rounded' -->
							        </c:if><%-- <c:if test="${prompt.media!='video'}"> --%>
									</div><!-- end of outer div -->
								</c:forEach>

							

					</c:forEach>
				</div>	

			
	<c:if test="${customerTask.docStsTyp.docStsTypCde!='CMPLD'}">  
		<div id="taskButtonsDiv" class="taskbuttonsdiv">
			<a class="submit" id="saveDraft" >Save Draft</a>
			<!-- <input type=button id="saveDraft"  value="Save Draft" /> -->
			<c:choose>
				<c:when test="${customerTask.docStsTyp.docStsTypCde == 'ACTIV'}">
					<a class="submit" id="submitTask">Submit Task</a>
				<!-- <input type=button id="submitTask" value="Submit Task" disabled /> -->
				</c:when>
				<c:when test="${customerTask.docStsTyp.docStsTypCde != 'ACTIV'}">				
					<a class="submit" id="submitTask" >Submit Task</a>
					<!-- <input type=button id="submitTask" value="Submit Task" /> -->
				</c:when>
			</c:choose>
			<!--  &nbsp<a class="submit"  href="/ereg-web/secure/home" id="goHome" >MyTask Page</a> -->
		</div>
	</c:if>
	
	
	
	<!--<c:out value="${customerTask.docStsTyp.docStsTypCde}" />-->
	
	</div>
	</div>
	</div>
	</div>
	</t:base>
	


</html>
