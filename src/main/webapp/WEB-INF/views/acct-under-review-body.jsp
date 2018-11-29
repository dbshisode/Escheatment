<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<%--
review-acct-body.jsp is the body of the review-acct view.  It contains a table of items for this work queue.
$Revision: 4537 $
$Author: cbarrington $
$Date: 2018-10-16 14:54:27 -0700 (Tue, 16 Oct 2018) $
 --%>   

        <div id="wrap">
            <div id="main" class="container pull-left">
                <!-- Example row of columns -->
                <br><br>
                <div class="row">
                    <div class="col-6 col-sm-6 col-lg-4" style="height:72px;">
                        <h2>Trusts Under Review</h2>                        
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
                                        <th width="34%">Lawful Owner(s)</th>
                                        <th width="3%">&nbsp;</th>                                                                       
                                        <!--<th width="6%">Deposit Date</th>
                                        <th width="7%">Orig. Amt.</th>
                                        <th width="7%">Balance</th>-->
                                        <th width="18%">Comments</th>
                                        <th width="9%">&nbsp;</th>                                       
                                    </tr> 
                                </thead>                                                                    
                            </table>  
                            </form:form>
                            </div>
                        </div>
                        <div id="btnContainer" class="pull-right">
                            <a href="add-to-acct-list" rel="modal:open" role="button"><button type="button" class="btn btn-default" style="margin-right:25px;">Add to List</button></a>
                            <a href="#" onclick="publication();" id="publication" role="button"><button type="button" class="btn btn-default">Ready for Publication</button></a>                         
                        </div>
                    </div>
                </div>   
 

            </div> <!-- /container -->   
        </div>
        
        <script type="text/javascript">
        $(document).ready(function() {    
        	var numFormat = $.fn.dataTable.render.number( ',', '.', 2, '$' ).display;
        	var table = $('#wqTable').DataTable( {
				"ajax": {"url":"get-acct-under-review-table","dataSrc":""},
				"deferRender": true,            	
	        	"order": [[ 3, "desc" ]], //trust number
	        	"columns": [
	       			//{"orderable": false }, //row number
	       			{"orderable": true, data: 'displayCaseNum', render: function (data,type,row,meta) {return '<a href="#" onclick="openROA(\'' + data + '\');">' + data +'</a>'}},
	       			{"orderable": true, data: 'caseTitle'},
	       			{"orderable": true, data: 'trustType'},
	       			{"orderable": true, data: 'trustNum', render: function (data,type,row,meta) {return '<a href="#" role="button" tabindex="0" data-html="true" data-toggle="popover" data-trigger="focus" data-placement="left" title="Trust Detail" data-content="Deposit Date: <b>' + row.depositDate + '</b><br />Original Amount: <b>' + numFormat(row.origAmt) + '</b><br />Current Balance: <b>' + numFormat(row.balance) + '</b>">' + data + '</a>'}},
	       			{"orderable": true, data: 'ownerName', render: function (data,type,row,meta) {return row.underReview != '' ? '<div class="nameAddress">' + data + row.ownerAddress + '</div><div class="firstLawfulOnwerButtons"><a href="#" role="button" onclick="sendAddlInfo(' + row.trustId + ', ' + row.lawfulOwnerId + ');"><button type="button" class="btn btn-default">Approved</button></a>&nbsp;&nbsp;<a href="#" role="button" onclick="sentForReview(' + row.trustId + ', ' + row.lawfulOwnerId + ');"><button type="button" class="btn btn-default ' + row.underReview + '">Denied</button></a></div><div style="clear:both;"></div><div class="addlNames"></div><div class="trustId">' + row.trustId + '</div>' : '<div class="nameAddress">' + data + row.ownerAddress + '</div><div style="clear:both;"></div><div class="addlNames"></div><div class="trustId">' + row.trustId + '</div>'} },
	       			{"orderable": false, data: 'ownerAddress', render: function (data,type,row,meta) {return row.ownerCnt > 1 ? '<span class="showAddlNames glyphicon glyphicon-plus-sign"></span>' : '&nbsp;' } },
	       			//{"orderable": true, data: 'depositDate'},
	       			//{"orderable": true, data: 'origAmt', render: $.fn.dataTable.render.number( ',', '.', 2, '$' )},
	       			//{"orderable": true, data: 'balance', render: $.fn.dataTable.render.number( ',', '.', 2, '$' )},
	       			{"orderable": true, data: 'comments', render: function (data,type,row,meta) {return data != '' ? '<span style="font-size:12px;">' + data + '<a href="comments?trustId=' + row.trustId + '&viewAll=true" rel="modal:open">View All</a> | <a href="comments?trustId=' + row.trustId + '&viewAll=false" rel="modal:open">Add Comment</a></span>' : '<span style="font-size:12px;"><a href="comments?trustId=' + row.trustId + '&viewAll=false" rel="modal:open">Add Comment</a></span>' }},
	       			{"orderable": false, data: 'trustId', render: function (data,type,row,meta) {return '<a href="#" onclick="onHold(' + data + ');" id="on-hold"><button type="button" class="btn btn-default">Place On Hold</button></a>' } } //button	       			
	       			]            		
	        });  
        	        	
            //reload datatable when modal is closed, to show revised data
            $(function() {
    	        $(document).on($.modal.AFTER_CLOSE, refreshPage);
    	        function refreshPage(event, modal) {    	        	
    	        	table.ajax.reload();
    	        }
            });              
            
            $('body').popover({selector: '[data-toggle="popover"]', trigger: 'hover'});
                        
                                    
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
		            		  addlNames += '<div class="addlNamesRow"><div class="nameAddress">' + val.nameLine1 + '<br />';
		            		  
		            		  if (val.nameLine2 != '') {
		            			  addlNames += val.nameLine2 + '<br />';
		            		  }
		            		  addlNames += val.addressLine1 + '<br />';
		            		  
		            		  if (val.addressLine2 != '') {
		            			  addlNames += val.addressLine2 + '<br />';
		            		  }
		            		  addlNames += val.addressLine3 + '</div>';
		            		  
		            		  if (val.underReview != '') {
		            			  addlNames += '<div class="addlLawfulOnwerButtons"><button type="button" class="btn btn-default">Approved</button>&nbsp;&nbsp;<a href="#" onclick="sentForReview(' + trustId + ', ' + val.id + ');"><button type="button" class="btn btn-default ' + val.underReview + '">Denied</button></a></div><div style="clear:both;"></div></div>';
		            		  } else {
		            			  addlNames += '<div class="addlLawfulOnwerButtons" style="width:160px;"></div></div><div style="clear:both;"></div>';
		            		  }
		            		  
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
        
        function sendAddlInfo(trustId,lawfulOwnerId) {
        	window.open('input-request-addl-info?trustId=' + trustId + '&lawfulOwnerId=' + lawfulOwnerId,
        			    'addlInfo', 
        			    'left=50,top=50,width=800,height=785,toolbar=0,location=0,menubar=0,resizable=1,scrollbars=1');
        }          
        	         
		function sentForReview(trustId,lawfulOwnerId) {
			$(this).next().find(".btn").addClass("disabled");
			$
					.ajax({
						url : "sent-for-review?trustId=" + trustId + '&lawfulOwnerId=' + lawfulOwnerId,
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
								//var acctReviewCount = $('#acctReviewCount').text() - 1;
								//$("#acctReviewCount").text(acctReviewCount);
								
								//increment count in pill nav
								var acctUnderReviewCount = +$('#acctUnderReviewCount').text() + 1;
								$("#acctUnderReviewCount").text(acctUnderReviewCount);

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
        
        
			function onHold(trustId) {
				$
						.ajax({
							url : "mark-on-hold?trustId=" + trustId,
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
									var acctReviewCount = $('#acctReviewCount').text() - 1;
									$("#acctReviewCount").text(acctReviewCount);
									
									//increment count in pill nav
									var acctOnHoldCount = +$('#acctOnHoldCount').text() + 1;
									$("#acctOnHoldCount").text(acctOnHoldCount);

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
