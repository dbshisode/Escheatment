<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<%--
review-ops-body.jsp is the body of the review-ops view.  It contains a table of items for this work queue.
$Revision: 4513 $
$Author: cbarrington $
$Date: 2018-08-28 14:46:18 -0700 (Tue, 28 Aug 2018) $
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
                        <h2>Identified for Escheatment</h2>
                        <%--<p>Some default panel content here. Nulla vitae elit libero, a pharetra augue. Aenean lacinia bibendum nulla sed consectetur. Aenean eu leo quam. Pellentesque ornare sem lacinia quam venenatis vestibulum. Nullam id dolor id nibh ultricies vehicula ut id elit.</p>--%>
                    </div>
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
                                        <th>#</th> 
                                        <th>Case Number</th> 
                                        <th>Case Title</th> 
                                        <th>Trust Type</th> 
                                        <th>Trust Number</th>                                                                         
                                        <th>Deposit Date</th>
                                        <th>Orig. Amt.</th>
                                        <th>Balance</th>
                                        <th>Comments</th>
                                        <th>&nbsp;</th>
                                        <th>&nbsp;</th>
                                    </tr> 
                                </thead>                                     
                                <c:forEach var="items" items="${workqueuedata}">                                                                     
                                    <tr> 
                                        <th scope=row>                                            
											<%-- <s:property value="%{#rowStatus.count}" /> --%>
                                        </th> 
                                        <td><a href="#" onclick="window.open('roa?caseId=10730922', 'getNote', 'toolbar=0,location=0,menubar=0,resizable=1,scrollbars=1');"><c:out value="${items.displayCaseNum}" /></a></td> 
                                        <td><c:out value="${items.caseTitle}" /></td> 
                                        <td><c:out value="${items.trustType}" /></td>
                                        <td><c:out value="${items.trustNum}" /></td>
                                        <td><c:out value="${items.depositDate}" /></td>
                                        <td align="right">$<c:out value="${items.origAmt}" /></td>
                                        <td align="right">$<c:out value="${items.balance}" /></td>
                                        <td>
                                            <c:out value="${items.comments}" escapeXml="false"/>
											<a href="comments" rel="modal:open">add</a>
                                        </td>
                                         <td align="right"><%--<input type="submit" name="trustId" id="${items.trustId}" cssClass="btn-default" value="Mark as Active" />--%></td> 
                                        <td align="right"><button type="button" class="btn-default">Send Notice</button> 
                                            <%--<s:hidden name="trustId" value="%{trustId}" />--%>
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
