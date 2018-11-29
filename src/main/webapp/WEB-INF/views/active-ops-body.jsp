<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<%--
active-ops-body.jsp is the body of the "/active" view.  It contains a table of items for this work queue.
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
                        <h2>Marked as Active</h2>                        
                    </div>
                    <div class="col-6 col-sm-6 col-lg-4" id="messageBox"><div id="messageText"></div></div>
                </div>
                <div class="row">
                    <div class="col-lg-12">
                        <div class="panel panel-primary">
                            <!-- Default panel contents -->
                            <div class="panel-heading">Trusts Returned to Active Status</div>

                            <div class="panel-body">
                                <form:form modelAttribute="reviewForm" id="reviewForm" action="reviewForm" theme="bootstrap" method="post">
                            <!-- Table -->                               
                            <table class="table table-striped" id="wqTable"> 
                                <thead> 
                                    <tr> 
                                        <!--<th width="3%">#</th>--> 
                                        <th width="10%">Case Number</th> 
                                        <th width="16%">Case Title</th> 
                                        <th width="10%">Trust Type</th> 
                                        <th width="4%">Trust Number</th>  
                                        <th width="12%">Lawful Owner(s)</th>
                                        <th width="3%">&nbsp;</th>                                                                       
                                        <th width="6%">Deposit Date</th>
                                        <th width="7%">Orig. Amt.</th>
                                        <th width="7%">Balance</th>
                                        <th width="19%">Comments</th>
                                        <th width="6%">&nbsp;</th>                                        
                                    </tr> 
                                </thead>                                                                    
                            </table>  
                            </form:form>
                            </div>
                        </div>  
                    </div>
                </div>   
 

            </div> <!-- /container -->   
        </div>
        
        <script type="text/javascript">
        $(document).ready(function() {
        	var table = $('#wqTable').DataTable( {
				"ajax": {"url":"get-ops-active-table","dataSrc":""},
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
	       			{"orderable": false, data: 'trustId', render: function (data,type,row,meta) {return '<a href="#" onclick="markIdentForEscheatment(' + data + ');" id="return"><button type="button" class="btn btn-default">Return</button></a>' } } //button	       			
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
        
		function markIdentForEscheatment(trustId) {
			$
					.ajax({
						url : "mark-identified-for-escheatment?trustId=" + trustId,
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
								
								//increment count in pill nav
								var opsReviewCount = +$('#opsReviewCount').text() + 1;
								$("#opsReviewCount").text(opsReviewCount);
								
								//decrement count in pill nav
								var opsActiveCount = $('#opsActiveCount').text() - 1;
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
