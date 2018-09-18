<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<%--
admin-body.jsp is the body of the admin view.  It contains a table of users who have access to the system.
$Revision: 4502 $
$Author: cbarrington $
$Date: 2018-08-15 07:37:36 -0700 (Wed, 15 Aug 2018) $
 --%>
    
        <div id="wrap">
            <div id="main" class="container pull-left">
                <!-- Example row of columns -->
                <br><br>    
                <div class="row">
                    <div class="col-6 col-sm-6 col-lg-4">
                        <h2>Escheatment Users</h2>
                        <%--<p>Some default panel content here. Nulla vitae elit libero, a pharetra augue. Aenean lacinia bibendum nulla sed consectetur. Aenean eu leo quam. Pellentesque ornare sem lacinia quam venenatis vestibulum. Nullam id dolor id nibh ultricies vehicula ut id elit.</p>--%>
                    </div>
                </div>
                <div class="row">
                    <div class="col-lg-6">
                        <div class="panel panel-primary">
                            <!-- Default panel contents -->
                            <div class="panel-heading">Current Users</div>

                            <div class="panel-body">
                                <form:form id="adminForm" action="adminForm" theme="bootstrap" method="post">
                            <!-- Table -->                               
                            <table class="table table-striped" id="currUsersTable"> 
                                <thead> 
                                    <tr> 
                                        <th>&nbsp;</th> 
                                        <th>User Name</th>  
                                        <th>Last Name</th>                                      
                                        <th>First Name</th>
                                        <th>Functional Area</th> 
                                        <th>Admin</th>    
                                        <th>Active</th>                                                                                                                                                      
                                    </tr> 
                                </thead>           
                                
                                <%-- JSP (non-ajax) way of displaying data --%>                                                          
                                <%--<c:forEach var="items" items="${userdata}">                                                                     
                                    <tr> 
                                        <!--  ?userId=' + ${items.userId} -->
                                        <td align="center"><a href="admin-edit?userId=<c:out value="${items.userId}" />" id="admin-edit" rel="modal:open"><button type="button" class="btn-default">Edit</button></a></td> 
                                        <td><c:out value="${items.userName}" /></td> 
                                        <td><c:out value="${items.lastName}" /></td>
                                        <td><c:out value="${items.firstName}" /> <c:out value="${items.middleName}" /></td>
                                        <td>
                                        	<c:choose>
                                        		<c:when test="${items.userFunctionalArea == 1}">
                                        			Operations
                                        		</c:when>
                                        		
                                        		<c:when test="${items.userFunctionalArea == 2}">
                                        			Accounting
                                        		</c:when>
                                        		
                                        		<c:when test="${items.userRoleAdmin > 0}">
                                        			Admin
                                        		</c:when>
                                        	
                                        		<c:otherwise>
                                        			&nbsp;
                                        		</c:otherwise>
                                        	</c:choose>
                                        		                                        		                                        		
                                        </td>
                                        <td>
                                        	<c:choose>
                                        		<c:when test="${items.userRoleAdmin > 0}">
                                        			Yes
                                        		</c:when>                                                                        
                                        	
                                        		<c:otherwise>
                                        			&nbsp;
                                        		</c:otherwise>
                                        	</c:choose>                                        
                                        </td> 
                                        <td align="center">
                                        	<c:choose>
                                        		<c:when test="${items.active == 'Y'}">
                                        			Yes
                                        		</c:when>
                                        		
                                        		<c:when test="${items.active != 'Y'}">
                                        			&nbsp;
                                        		</c:when>                                        	
                                        	
                                        		<c:otherwise>
                                        			&nbsp;
                                        		</c:otherwise>
                                        	</c:choose>                                         
                                        </td> 
                                    </tr>                                                                                                             
								</c:forEach>--%>                                                                    
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
            var table = $('#currUsersTable').DataTable( {
    			"ajax": {"url":"get-user-table","dataSrc":""},
    			"deferRender": true,
            	"order": [[ 2, "asc" ]], //last name
            	"columns": [
           			{"orderable": false, data: 'userId', render: function (data,type,row,meta) {return '<a href="admin-edit?userId=' + data + '" id="admin-edit" rel="modal:open"><button type="button" class="btn-default">Edit</button></a>' }}, //button
           			{"orderable": true, data: 'userName' },
           			{"orderable": true, data: 'lastName' },
           			{"orderable": true, data: 'firstName' },
           			{"orderable": true, 
           				data: 'userFunctionalArea', 
           				render: function (data,type,row,meta) {
           					return data == 1 ? 'Operations' : 'Accounting'         					
           				}
           			},           				
           			{"orderable": true, data: 'userRoleAdmin', render: function (data,type,row,meta) {return data > 0 ? 'Yes' : '&nbsp;'}},
           			{"orderable": true, data: 'active', render: function (data,type,row,meta) {return data == 'Y' ? 'Yes' : '&nbsp;'}}
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
    	</script>