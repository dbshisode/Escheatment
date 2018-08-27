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
                <!--<div class="panel panel-primary">
                    <div class="panel-heading">Escheatment for Publication</div>
                    <div class="panel-body">Some default panel content here. Nulla vitae elit libero, a pharetra augue. Aenean lacinia bibendum nulla sed consectetur. Aenean eu leo quam. Pellentesque ornare sem lacinia quam venenatis vestibulum. Nullam id dolor id nibh ultricies vehicula ut id elit.</div>
                </div>-->     
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
                                <form:form modelAttribute="reviewForm" id="reviewForm" action="reviewForm" theme="bootstrap" method="post">
                            <!-- Table -->                               
                            <table class="table table-striped" id="wqTable"> 
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
                                <c:forEach var="items" items="${userdata}">                                                                     
                                    <tr> 
                                        <td align="center"><a onclick="window.open('admin-edit?userId=' + ${items.userId}, 'editUserWindow', 'toolbar=0,location=0,menubar=0,resizable=1,scrollbars=1,width=400,height=650');"><button type="button" class="btn-default">Edit</button></a></td> 
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
								</c:forEach>                                                                    
                            </table>  
                            </form:form>
                            </div>
                        </div>  
                        <!--<div id="btnContainer" class="pull-right">
                            <button type="button" class="btn-default">Export to Excel</button>&nbsp;&nbsp;
                            <button type="button" class="btn-default">Mark for Escheatment</button>                            
                        </div>-->
                    </div>
                </div>   
 

            </div> <!-- /container -->   
        </div>
