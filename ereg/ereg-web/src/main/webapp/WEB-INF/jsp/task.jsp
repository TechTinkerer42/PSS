
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@page import="java.util.logging.Logger"%>
<%@page import="org.ets.pss.persistence.model.AsgndTsk"%>
<!doctype html>
<html lang="en">
<head>
<meta charset="utf-8" />
<title>jQuery UI Accordion - Default functionality</title>
<!-- Jquery UI -->
<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
	


<!-- GoogieSpell -->
<%-- <link href="<c:url value="/resources/googiespell/googiespell.css"/>"
	rel="stylesheet" type="text/css" /> --%>

<!-- fine uploader -->
<link
	href="<c:url value="/resources/fineuploader/fineuploader-3.5.0.css"/>"
	rel="stylesheet" type="text/css" />

<!-- Right Click Context Menu 
<link href="<c:url value='/resources/jqcontextmenu/jqcontextmenu.css'/>"
	rel="stylesheet" />	
	-->

<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
<script type="text/javascript" src="http://ajax.cdnjs.com/ajax/libs/json2/20110223/json2.js"></script>

<!-- <script type="text/javascript" src="<c:url value='/resources/jqcontextmenu/jqcontextmenu.js'/>"></script>  -->
<script type="text/javascript"
	src="<c:url value='/resources/contextmenu/jquery.contextmenu.r2.js'/>"></script>


<script type="text/javascript"
	src="<c:url value="/resources/googiespell/AJS.js"/>">
	
</script>
<%-- <script type="text/javascript"
	src="<c:url value="/resources/googiespell/googiespell.js"/>"></script> 
<script type="text/javascript"
	src="<c:url value="/resources/googiespell/cookiesupport.js"/>"></script>--%>

<script type="text/javascript"
	src="<c:url value="/resources/fineuploader/jquery.fineuploader-3.5.0.js"/>"></script>

<script type="text/javascript"
	src="<c:url value='/resources/rangy-1.3/rangy-core.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/resources/rangy-1.3/rangy-cssclassapplier.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/resources/rangy-1.3/rangy-highlighter.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/resources/rangy-1.3/rangy-selectionsaverestore.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/resources/rangy-1.3/rangy-serializer.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/resources/rangy-1.3/rangy-textrange.js'/>"></script>


<script>
	$(document)
			.ready(
					function() {

						rangy.init();

						//create accordian  
						$("#accordion").accordion();

						//editor options
						//1. add spell check 
						/*
						var googie1 = new GoogieSpell(
						 "/portfolio/resources/googiespell/",
						 "https://www.google.com/tbproxy/spell?lang=");  

						$('textarea[id^="editor"]')
								.each(
										function(index) {
											googie1.decorateTextarea($(this));
										}); */

						//creare save Response button functionality						
						$('input[id^="save_response"]')
								.click(
										function() {
											var clicked = $(this);
											var idStr = clicked[0].id;
											var indexOfCount = "save_response".length;
											var index = idStr.substring(
													indexOfCount, idStr.length);

											var myTaskId = $("#taskId" + index)
													.val();
											var myPromptId = $(
													"#promptId" + index).val();

											var text = $('#cteditor' + index)
													.html();//val();

											$
													.ajax({
														url : '/portfolio/spring/web/task/editor/save',
														type : 'post',
														data : {
															taskId : myTaskId,
															promptId : myPromptId,
															editorText : text
														},
														success : function(data) {

														}
													});

										});

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
											//var range = sel.getRangeAt(0);

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
														
													}
													replaceWithOwnChildren(el);
												}
												$('#removehigh' + index).prop(
														'disabled', true);
											}

										});

						var jqxhr = $.ajax({
							url : '/portfolio/spring/web/artifact/getList',
							data : null,
							cache : false,
							contentType : false,
							processData : false,
							type : 'GET',
							success : function(data) {
								console.log("success");
								var documentsDiv = $('#documentsDiv');
								var cList = $('<ul/>').attr('id', 'docList');
								$.each(data, function(i, value) {
									$('<li/>').attr('id', value.id).text(
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
											//$('#highlight')+idstr;
											var indexOfCount = "highlight".length;
											var index = idStr.substring(
													indexOfCount, idStr.length);
											var linkvalue = "http://";
											var grid = '<table id="myTable" cellspacing="0" cellpadding="2" width ="100%" border = "1" align="center"><thead><tr><td align ="center">ArtifactID</td></tr></thead>';
											$("#docList li")
													.each(
															function() {
																var value = $(this)
																		.text();
																grid += '<tbody><tr><td>';
																grid += '<input type="radio" id="'
																		+ $(this).attr('id')
																		+ '" name="FileRadio" value="/portfolio/spring/web/artifact/view/' + $(this).attr('id')																		
																		+ '">' + value + "</input>";
																grid += '</td></tr>';
															});

											grid += '</tbody></table>';

											$("#linkdialog").html(grid);
											$("#linkdialog")
													.dialog(
															{	width:500,
																height:400,
																modal : true,
																dialogClass : "no-close",
																buttons : [
																		{
																			text : "LinkDoc",
																			click : function() {

																				if ($('input:radio[name=FileRadio]:checked')) {
																					var selectectDoc = $('input:radio[name=FileRadio]:checked');
																					var $closestTr = $(
																							'input:radio[name=FileRadio]:checked')
																							.closest(
																									'tr');
																					
																					linkvalue = selectectDoc.val();
																					
																					var docId = selectectDoc.attr('id');
																					var docName = selectectDoc.text();
																					//var docId = selectectDoc[0].id;
																					//var docName = selectectDoc[0].val();
																					
																					$('<li id="'+docId+'">'+docName+'</li>').appendTo($('#docsListForPrmpt'+index));
																					
																					linkApplier = rangy
																							.createCssClassApplier(
																									"highLightLink",
																									{
																										elementTagName : "a",
																										elementProperties : {
																											href : linkvalue,
																											title : "Artifact Page"
																										}
																									});

																					var selection = rangy
																							.getSelection();

																					linkApplier
																							.applyToSelection();

																					$(
																							'#cteditor'
																									+ index
																									+ ' a')
																							.attr(
																									'contenteditable',
																									'false')
																							.attr(
																									'target',
																									'_blank');

																				}//end of radio:checked block
																				$(
																						this)
																						.dialog(
																								'close');
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

										});//end $('#highlight').click

						$('input[id^="cteditorContent"]')
								.each(
										function(index) {

											var clicked = $(this);
											if (clicked) {
												var idStr = clicked[0].id;
												var indexOfCount = "cteditorContent".length;
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
										'mouseup',
										(function(e) {
											 

											var clicked = $(this);
											var idStr = clicked[0].id;

											var indexOfCount = "cteditor".length;
											var index = idStr.substring(indexOfCount, idStr.length);

											$('#highlight' + index).prop(
													'disabled', false);
											var sel = rangy.getSelection();
											var selectedText = rangy.getSelection().toString();
											
											 var nodes = rangy.getSelection().getRangeAt(0).getNodes(false, function (el) {
												 if (el.tagName === 'A')

													{
														// alert('the node is '+el.tagName);
														$('#removehigh'+ index)
																.prop('disabled',false);

													}
											    }
											 
											 );
											 if (selectedText.length <= 0) {
													$('#removehigh' + index)
															.prop('disabled',
																	true);
												}
										

											/*for ( var i = 0, range; i < sel.rangeCount; ++i) {
												
												range = sel.getRangeAt(i);
												range.splitBoundaries();
												

												// Get formatting elements. For this example, we'll count any
												// element with display: inline, except <br>s.
												var formattingEls = range
														.getNodes(
																[1],
																function(el) {
																	console.log('again here 1 mouseup why not '+el.nodeValue);
																	if (el.tagName === 'A')

																	{
																		// alert('the node is '+el.tagName);
																		$('#removehigh'+ index)
																				.prop('disabled',false);

																	}
																	return el.tagName;
																});
												if (selectedText.length <= 0) {
													$('#removehigh' + index)
															.prop('disabled',
																	true);
												}
												
												

											}*/
										}));

											$('[id^=cteditor]')
													.on(
															'mouseout',
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

																	$(
																			'#removehigh'
																					+ index)
																			.prop(
																					'disabled',
																					true);
																	$(
																			'#highlight'
																					+ index)
																			.prop(
																					'disabled',
																					true);

																}

															}));

										
						
						$('#saveDraft').click(function(){
							var promptDocMap = new Object();
							var myTaskId = null;

							$('[id^=docsListForPrmpt]').each(function(){
								var currentPrompt = $(this);
								var idStr = currentPrompt[0].id;
								var indexOfCount = "docsListForPrmpt".length;
								var index = idStr.substring(
										indexOfCount, idStr.length);
								var promptId = $('#promptId'+index).val();
								
								//it is same for all prompts
								myTaskId = $("#taskId" + index).val();
								
								var docArray = new Array();
								
								$('#'+idStr+' li').each(function(){
									
									var docId = $(this).attr('id');
									//var docName = $(this).text();	
									docArray.push(docId);									
									
								});
								
								promptDocMap[promptId] = docArray;
							});
							
							$
							.ajax({
								url : '/portfolio/spring/web/task/draft/save',
								type : 'POST',
								contentType : "application/json",
								data : JSON.stringify({
									taskId : myTaskId,
									promptDocs :promptDocMap
								}),
								dataType: 'json',
								success : function(data) {
									console.log(data);
								}
							});
							
						});
						
						$('#submitTask').click(function(){
							
							
						});

					});
</script>

<style>
#accordion {
	width: 750px;
}

.imagetag {
	float: right;
}

.essay_div {
	height: 500px;
}

.ctclasseditor {
	height: 400px;
	width: 650px;
	resize: none margin-left:   50px;
	margin-right: 50px;
	border: 1px solid #000;
	overflow: scroll;
}

a.highLightLink {
	color: #f66;
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
 
 .uploadVideobutton{
    border:12px;
	padding:8px;
	border-style:"solid";
	margin-left:10px;		
	margin-bottom:15px;	
	background-color:#EFEFEF;	
 }

.right {
	position: absolute;
	top: 20px;
	right: 0px;
	width: 150px;
	
}

div.three
{	
	width:750px;
	height:40px;
	border-style:solid;
/* 	position:relative;
	top:1px;
	left:-1px;	 */
 	border-width:1px;
	border-radius: 3px;
	background-color:#f0f0f0;
	margin-left: auto;
	margin-right: auto;	
	text-align:left;
	
}

div.four
{
	width:750px;	
	border-radius: 3px;
	border-style:solid;	
	border-width:1px;
	margin-left: auto;
	margin-right: auto;	
	margin-bottom:25px;	
	
	
	
}

div.five
{
	padding:10px 10px 10px 10px;
	
	
}

.no-close .ui-dialog-titlebar-close {
	display: none;
}
</style>
</head>
<body>
             
            <div id="video" class="four">
				<div class="three"><h5>Video Submission</h5></div>				
				<div class="five"><p>Teching Video.mp3  <a href="">Video</a></p></div>
				<input type=button id="uploadVideo" class="uploadVideobutton" value="Upload Video" />
			</div>
			
			
	<%
		Logger logger = Logger.getLogger(this.getClass().getName());
	%>
	<div id="documentsDiv" style="display: none"></div>
	<div id="linkdialog" title="Teachers Assesment Artifacts"
		style="display: none"></div>
	<div id="accordion" class="center">
		<!-- Repeat prompts -->
		<c:if test="${!empty prompts}">
			<c:forEach var="prmpt" varStatus="status" items="${prompts}">
				<h3>Essay ${status.count}</h3>
				<div id="essay_div${status.count}" class="essay_div">
					<input type="hidden" id="taskId${status.count}"
						value="${prmpt.taskId}" /> <input type="hidden"
						id="promptId${status.count}" value="${prmpt.promptId}" />
					<div id="essay_instructions${status.count}">
						<c:out value="${prmpt.instructions}" />
					</div>

					<div id="editor_buttons${status.count}">
						<input type="button" value="Save Response" disabled
							id="save_response${status.count}" /> <input type="button" 
							value="Link Text" id="highlight${status.count}" /> <input
							type="button"  value="Remove Link" disabled
							id="removehigh${status.count}" />
					</div>

					<input type="hidden" id="cteditorContent${status.count}"
						value="<c:out value="${prmpt.essayContent}"/>" />
					<div id="cteditor${status.count}" class="ctclasseditor"
						contenteditable="true"></div>
					<div id="docsDivForPrmpt${status.count}" style="display: none">
					<ul id="docsListForPrmpt${status.count}"></ul>
					</div>
				</div>

				
			</c:forEach>
		</c:if>
		<!-- Repeat prompts -->
	</div>
	<div id="taskButtonsDiv" class="taskbuttonsdiv">
		<input type=button id="saveDraft" class="taskbuttons" value="Save Draft" />
		<input type=button id="submitTask" class="taskbuttons" value="Submit Task" />
	</div>
</body>

</html>
