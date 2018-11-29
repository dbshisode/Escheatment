<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<%--
review-ops-body.jsp is the body of the review-ops view.  It contains a table of items for this work queue.
$Revision: 4566 $
$Author: cbarrington $
$Date: 2018-11-08 09:28:54 -0800 (Thu, 08 Nov 2018) $
 --%>   

        <div id="wrap">
            <div id="main" class="container pull-left">
                <!-- Example row of columns -->
                <br><br>
                <div class="row">
                    <div class="col-6 col-sm-6 col-lg-4" style="height:72px;">
                        <h2>Identified for Escheatment: V1</h2>                        
                    </div>
                    <div class="col-6 col-sm-6 col-lg-4" id="messageBox"><div id="messageText"></div></div>
                </div>
                <div class="row">
                    <div class="col-lg-12">
                        <div class="panel panel-primary">
                            <!-- Default panel contents -->
                            <div class="panel-heading">Items for Review</div>

                            <div class="panel-body">
                                <form:form modelAttribute="reviewForm" id="reviewForm" action="reviewForm" theme="bootstrap" method="post">
                            <!-- Table -->                               
                            <table class="table table-striped" id="wqTable"> 
                                <thead> 
                                    <tr> 
                                        <!--<th width="3%">#</th>--> 
                                        <th width="10%">Case Number</th> 
                                        <th width="13%">Case Title</th> 
                                        <th width="9%">Trust Type</th> 
                                        <th width="4%">Trust Number</th>  
                                        <th width="11%">Lawful Owner(s)</th>
                                        <th width="3%">&nbsp;</th>                                                                       
                                        <th width="6%">Deposit Date</th>
                                        <th width="7%">Orig. Amt.</th>
                                        <th width="7%">Balance</th>
                                        <th width="18%">Comments</th>
                                        <th width="6%">&nbsp;</th>
                                        <th width="6%">&nbsp;</th>
                                    </tr> 
                                </thead>   
                                <%-- JSP (non-ajax) way of displaying data --%>                                  
                                <%--<c:forEach var="items" items="${workqueuedata}" varStatus="loop">                                                                     
                                    <tr>  
                                        <td><a href="#" onclick="window.open('roa?caseId=' + ${items.caseId}, 'getNote', 'toolbar=0,location=0,menubar=0,resizable=1,scrollbars=1');"><c:out value="${items.displayCaseNum}" /></a></td> 
                                        <td><c:out value="${items.caseTitle}" /></td> 
                                        <td><c:out value="${items.trustType}" /></td>
                                        <td><c:out value="${items.trustNum}" /></td>
                                        <td><c:out value="${items.depositDate}" /></td>
										<td align="right">
											<fmt:setLocale value = "en_US"/>
											<fmt:formatNumber value = "${items.origAmt}" type = "currency"/>
										</td>
                                        <td align="right">
											<fmt:setLocale value = "en_US"/>
											<fmt:formatNumber value = "${items.balance}" type = "currency"/>                                        
                                        <td>
                                            <span style='font-size:12px;'>
                                            	<c:out value="${items.comments}" escapeXml="false"/>
                                            	<c:if test="${items.commentCnt > 1}"><a href="comments" rel="modal:open">View All</a> | </c:if>
												<a href="comments" rel="modal:open">Add Comment</a>
											</span>
                                        </td>
                                         <td align="right"><button type="button" class="btn-default">Mark as Active</button></td> 
                                        <td align="right"><button type="button" class="btn-default">Send Notice</button>                                             
                                        </td>
                                    </tr>                                                                                                             
								</c:forEach>--%>                                                                  
                            </table>  
                            </form:form>
                            </div>
                        </div>  
                        <div id="btnContainer" class="pull-right">
                            <button type="button" class="btn-default">Export to Excel</button>                           
                        </div>
                    </div>
                </div>   
 

            </div> <!-- /container -->   
        </div>
        
        <script type="text/javascript">
        $(document).ready(function() {
        	var table = $('#wqTable').DataTable( {
				"ajax": {"url":"get-ops-review-table","dataSrc":""},
				"deferRender": true,            	
	        	"order": [[ 3, "desc" ]], //trust number
	        	"columns": [
	       			//{"orderable": false }, //row number
	       			{"orderable": true, data: 'displayCaseNum', render: function (data,type,row,meta) {return '<a href="#" onclick="openROA(\'' + data + '\');">' + data +'</a>'}},
	       			{"orderable": true, data: 'caseTitle'},
	       			{"orderable": true, data: 'trustType'},
	       			{"orderable": true, data: 'trustNum'},
	       			{"orderable": true, data: 'ownerName', render: function (data,type,row,meta) {return '<div class="nameAddress">' + data + row.ownerAddress + '</div><div class="addlNames"></div><div class="trustId">' + row.trustId + '</div>'} },	       			
	       			{"orderable": false, data: 'ownerAddress', render: function (data,type,row,meta) {return row.ownerCnt > 1 ? '<span class="showAddlNames glyphicon glyphicon-plus-sign"></span>' : '&nbsp;' } },
	       			{"orderable": true, data: 'depositDate'},
	       			{"orderable": true, data: 'origAmt', render: $.fn.dataTable.render.number( ',', '.', 2, '$' )},
	       			{"orderable": true, data: 'balance', render: $.fn.dataTable.render.number( ',', '.', 2, '$' )},
	       			{"orderable": true, data: 'comments', render: function (data,type,row,meta) {return data != '' ? '<span style="font-size:12px;">' + data + '<a href="comments?trustId=' + row.trustId + '&viewAll=true" rel="modal:open">View All</a> | <a href="comments?trustId=' + row.trustId + '&viewAll=false" rel="modal:open">Add Comment</a></span>' : '<span style="font-size:12px;"><a href="comments?trustId=' + row.trustId + '&viewAll=false" rel="modal:open">Add Comment</a></span>' }},
	       			{"orderable": false, data: 'trustId', render: function (data,type,row,meta) {return '<a href="#" onclick="markActive(' + data + ');" id="mark-active"><button type="button" class="btn btn-default">Mark as Active</button></a>' } }, //button
	       			{"orderable": false, data: 'trustId', render: function (data,type,row,meta) {return '<a href="#" onclick="sendNotice(' + data + ');"><button type="button" class="btn btn-default">Send Notice</button></a>' } } //button
	       			]            		
	        });  
        	
            //reload datatable when modal is closed, to show revised data
            $(function() {
    	        $(document).on($.modal.AFTER_CLOSE, refreshPage);
    	        function refreshPage(event, modal) {    	        	
    	        	table.ajax.reload();
    	        }
            });             
                                    
        });
        
        $('#wqTable').on('click', '.showAddlNames', function(e){
	            e.preventDefault();	      
	            //$('.addlNames').hide();	            
	            var trustId = $(this).closest('td').prev().find(".trustId").html();
	            var addlNamesTd = $(this).closest('td').prev().find(".addlNames");
				var addlNames = '';
				
				
				$.ajax({
					  dataType: "json",
					  url: "get-addl-names?trustId=" + trustId,
					  data: trustId,
					  success: function (data) {
		            	  $.each( data, function( key, val ) {
		            		  addlNames += '<div class="addlNamesRow">' + val.nameLine1 + '<br />';
		            		  
		            		  if (val.nameLine2 != '') {
		            			  addlNames += val.nameLine2 + '<br />';
		            		  }
		            		  addlNames += val.addressLine1 + '<br />';
		            		  
		            		  if (val.addressLine2 != '') {
		            			  addlNames += val.addressLine2 + '<br />';
		            		  }
		            		  addlNames += val.addressLine3 + '</div>';		            		  
		            	  });
		            			            	
		            	addlNamesTd.html(addlNames);	
		            	//addlNamesTd.fadeToggle();		            	
		            	//showAddlNames glyphicon glyphicon-triangle-bottom		            	
					  }
					});
				$(this).closest('td').prev().find(".addlNames").fadeToggle();
				$(this).toggleClass("glyphicon-plus-sign glyphicon-minus-sign");            
        	});        

        function openROA(caseNum) {
        	window.open('roa?caseNum=' + caseNum,
        			    'getNote', 
        			    'toolbar=0,location=0,menubar=0,resizable=1,scrollbars=1');
        }
        
        function sendNotice(trustId) {
        	window.open('input-notice-unclaimed-funds?trustId=' + trustId,
        			    'sendNotice', 
        			    'left=50,top=50,width=800,height=785,toolbar=0,location=0,menubar=0,resizable=1,scrollbars=1');
        }     
        
			function markActive(trustId) {
				$
						.ajax({
							url : "mark-active?trustId=" + trustId,
							type : 'POST',
							//contentType: 'application/json',
							data : $(
									'#reviewForm')
									.serialize(),
							success : function(
									data) {
								var responseJson = $
										.parseJSON(data);

								if (responseJson.status == "SUCCESS") {

									$(
											'#messageBox')
											.addClass(
													"alert alert-success");
									$(
											'#messageBox')
											.removeClass(
													"alert-warning");
									$(
											'#messageBox')
											.removeClass(
													"alert-danger");
									$(
											'#messageText')
											.html(
													responseJson.message);
									
									//decrement count in pill nav
									var opsReviewCount = $('#opsReviewCount').text() - 1;
									$("#opsReviewCount").text(opsReviewCount);
									
									//increment count in pill nav
									var opsActiveCount = +$('#opsActiveCount').text() + 1;
									$("#opsActiveCount").text(opsActiveCount);

								} else if (responseJson.status == "ERROR") {
									$(
											'#messageBox')
											.addClass(
													"alert alert-warning");
									$(
											'#messageBox')
											.removeClass(
													"alert-success");
									$(
											'#messageBox')
											.removeClass(
													"alert-danger");
									$(
											'#messageText')
											.html(
													responseJson.message);
								} else {
									errorInfo = "";
									$
											.each(
													responseJson.errorMsgs,
													function(
															i,
															item) {
														errorInfo = errorInfo
																+ item
																+ "<br />";
													});

									$(
											'#messageBox')
											.addClass(
													"alert alert-danger");
									$(
											'#messageBox')
											.removeClass(
													"alert-warning");
									$(
											'#messageBox')
											.removeClass(
													"alert-success");
									$(
											'#messageText')
											.html(
													errorInfo);
								}

								$('#messageBox')
										.show();
								$('#messageBox').fadeIn().delay(2000).fadeOut();
								$('#wqTable').DataTable().ajax.reload();
							}
						});
			}		    
            
        </script>        
