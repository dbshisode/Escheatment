<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<%--
acct-pending-body.jsp is the body of the acct-pending view.  It contains a table of items for this work queue.
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
                   <h2>Trusts Ready for Publication</h2>                        
               </div>
               <div class="col-6 col-sm-6 col-lg-4" id="messageBox"><div id="messageText"></div></div>
           </div>                
           
           <div class="row" style="padding-left:15px;">

				<ul class="nav nav-tabs">				 
				  <c:forEach begin="1" end="${pubTabCount}" varStatus="loop">				  
				  	<c:choose>
				  	  <c:when test="${loop.index == 1}">
				  	  	<li class="active"><a data-toggle="tab" href="#pending">Pending</a></li>
				  	  </c:when>
				  	  <c:otherwise>
				  	  	<li><a data-toggle="tab" href="#menu${loop.index - 1}" onclick="loadTab(${loop.index - 1})">Batch ${loop.index - 1}</a></li>
				  	  </c:otherwise>
				  	</c:choose>				  	
				  </c:forEach>
				</ul> 
				        
				<div class="tab-content">
				  <div id="pending" class="tab-pane fade in active">

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
                                  <th width="20%">Lawful Owner(s)</th>
                                  <th width="3%">&nbsp;</th>                                                                       
                                  <th width="6%">Deposit Date</th>
                                  <th width="7%">Orig. Amt.</th>
                                  <th width="7%">Balance</th>
                                  <th width="21%">Comments</th>                                                                               
                              </tr> 
                          </thead>                                                                    
                      </table>  
                      </form:form>
                      
                      <br/>

						<div id="btnContainer" class="pull-right">
						  <a href="#" rel="modal:open" onclick="reviseList();" id="reviseList" role="button"><button type="button" class="btn btn-default" style="margin-right:25px;">Revise List</button></a>
						  <a href="#" role="button" onclick="finalizeList();" id="finalizeList"><button type="button" class="btn btn-default">Finalize List</button></a>                       
						</div>
                  </div>
                  
                  
                  
                  
                  
                  
                  
                  
                  
                  
                  
                  
                  
                  
                  
                  
                  
                  
                  				  				  
		  	
				  
                  
                  
                  
                  <c:forEach begin="1" end="${pubTabCount}" varStatus="loop">
				  <div id="menu${loop.index}" class="tab-pane fade in">
                      <form:form modelAttribute="reviewForm" id="reviewForm${loop.index}" action="reviewForm" theme="bootstrap" method="post">
                      <!-- Table -->                               
                      <table class="table table-striped" width="80%" id="batchTable${loop.index}"> 
                          <thead> 
                              <tr> 
                                  <th width="25%">Case Number</th> 
                                  <th width="30%">Case Title</th> 
                                  <th width="15%">Trust Type</th> 
                                  <th width="15%">Trust Number</th>  
                                  <th width="15%">Balance</th>
                              </tr> 
                          </thead>                                                                    
                      </table>  
                      </form:form>               
                  </div>                  
                  </c:forEach>
                  
                  
                  
                  
                  
                  
                  
                  
                  
                  
                  
                  
                
                </div>                    
            </div>   
            
         </div>
</div>
 


        
        <script type="text/javascript">
        $(document).ready(function() {    
        	var numFormat = $.fn.dataTable.render.number( ',', '.', 2, '$' ).display;
        	var table = $('#wqTable').DataTable( {
				"ajax": {"url":"get-pending-acct-table","dataSrc":""},
				"deferRender": true,            	
	        	"order": [[ 3, "desc" ]], //trust number
	        	"language": {
	        	      "emptyTable": "No trusts currently pending publication"
	        	    },
	        	"columns": [
	       			//{"orderable": false }, //row number
	       			{"orderable": true, data: 'displayCaseNum', render: function (data,type,row,meta) {return '<a href="#" onclick="openROA(\'' + data + '\');">' + data +'</a>'}},
	       			{"orderable": true, data: 'caseTitle'},
	       			{"orderable": true, data: 'trustType'},
	       			{"orderable": true, data: 'trustNum', render: function (data,type,row,meta) {return '<a href="#" role="button" tabindex="0" data-html="true" data-toggle="popover" data-trigger="focus" data-placement="left" title="Trust Detail" data-content="Deposit Date: <b>' + row.depositDate + '</b><br />Original Amount: <b>' + numFormat(row.origAmt) + '</b><br />Current Balance: <b>' + numFormat(row.balance) + '</b>">' + data + '</a>'}},
	       			{"orderable": true, data: 'ownerName', render: function (data,type,row,meta) {return '<div class="nameAddress">' + data + row.ownerAddress + '</div><div style="clear:both;"></div><div class="addlNames"></div><div class="trustId">' + row.trustId + '</div>'} },
	       			{"orderable": false, data: 'ownerAddress', render: function (data,type,row,meta) {return row.ownerCnt > 1 ? '<span class="showAddlNames glyphicon glyphicon-plus-sign"></span>' : '&nbsp;' } },
	       			{"orderable": true, data: 'depositDate'},
	       			{"orderable": true, data: 'origAmt', render: $.fn.dataTable.render.number( ',', '.', 2, '$' )},
	       			{"orderable": true, data: 'balance', render: $.fn.dataTable.render.number( ',', '.', 2, '$' )},
	       			{"orderable": true, data: 'comments', render: function (data,type,row,meta) {return data != '' ? '<span style="font-size:12px;">' + data + '<a href="comments?trustId=' + row.trustId + '&viewAll=true" rel="modal:open">View All</a> | <a href="comments?trustId=' + row.trustId + '&viewAll=false" rel="modal:open">Add Comment</a></span>' : '<span style="font-size:12px;"><a href="comments?trustId=' + row.trustId + '&viewAll=false" rel="modal:open">Add Comment</a></span>' }}	       				       			
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
        
        function loadTab(index) {
          if ( $.fn.dataTable.isDataTable( '#batchTable' + index ) == false ) {
        	 var numFormat = $.fn.dataTable.render.number( ',', '.', 2, '$' ).display;
        	 var table = $('#batchTable' + index).DataTable( {
        		 dom: '<"toolbar">frtip',
        		        fnInitComplete: function(){
        		        	$(this).closest(".dataTables_wrapper").find("div.toolbar").html('<div class="col-xs-3 col-md-2 input-group date" id="datepicker' + index + '"><input type="text" name="pubDate" class="form-control" placeholder="Publication Date" /><span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span></div><div id="savePubDate"><a href="#" role="button" onclick="savePubDate();"><button type="button" class="btn btn-primary">Save</button></a></div>');
        				    $('#datepicker' + index).datetimepicker({format: 'MM/DD/YYYY'});
        		        },     		 
				"ajax": {"url":"get-pending-acct-batch-table?index=" + parseInt(index - 1),"dataSrc":""},
				"deferRender": true,            	
	        	"order": [[ 3, "desc" ]], //trust number
	        	"language": {
	        	      "emptyTable": "No trusts currently pending publication"
	        	    },
	        	"columns": [
	       			//{"orderable": false }, //row number
	       			{"orderable": true, data: 'displayCaseNum', render: function (data,type,row,meta) {return '<a href="#" onclick="openROA(\'' + data + '\');">' + data +'</a>'}},
	       			{"orderable": true, data: 'caseTitle'},
	       			{"orderable": true, data: 'trustType'},
	       			{"orderable": true, data: 'trustNum', render: function (data,type,row,meta) {return '<a href="#" role="button" tabindex="0" data-html="true" data-toggle="popover" data-trigger="focus" data-placement="left" title="Trust Detail" data-content="Deposit Date: <b>' + row.depositDate + '</b><br />Original Amount: <b>' + numFormat(row.origAmt) + '</b><br />Current Balance: <b>' + numFormat(row.balance) + '</b>">' + data + '</a>'}},
	       			{"orderable": true, data: 'balance', render: $.fn.dataTable.render.number( ',', '.', 2, '$' )}
	       		]            		
	        });         	 
          }
        }
        
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
		            		  addlNames += '<div style="clear:both;"></div></div>';
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
        			
		function reviseList() {
			$.ajax({
				url : "revise-list",
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
						
						//set values in pills
						var acctPendingCount = $('#acctPendingCount').text();
						var acctReviewCount = $('#acctReviewCount').text();
						$("#acctReviewCount").text(parseInt(acctPendingCount) + parseInt(acctReviewCount));
						
						//increment count in pill nav
						$("#acctPendingCount").text("0");						

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
		
		function finalizeList() {
			$.ajax({
				url : "finalize-list",
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
