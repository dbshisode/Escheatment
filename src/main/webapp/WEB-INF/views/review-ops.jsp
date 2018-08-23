<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7" lang=""> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8" lang=""> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9" lang=""> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js" lang=""> <!--<![endif]-->
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <title>Escheatment Application - Operations Review</title>
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <link href="<c:url value="/resources/bootstrap/css/bootstrap.css" />" rel="stylesheet">
        <link rel="stylesheet" href="<c:url value="/resources/bootstrap/css/bootstrap-theme.min.css" />">
        <link rel="stylesheet" href="<c:url value="/resources/css/main.css" />">

        <script src="<c:url value="/resources/bootstrap/js/modernizr-2.8.3-respond-1.4.2.min.js" />"></script>
                
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/datatables.min.css" />">
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/fixedHeader.dataTables.min.css" />">       
        
    </head>
    <body id="review-ops" class="dataTableBody">
      
        <%@include file="includes/nav-ops.jsp" %>  
        
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
                                        <th>Case Number1</th> 
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
<%-- 											<form:textarea path="commentText" rows="3" cols="12" /> --%>
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

        <footer class="footer">
            <p id="footer-text">Superior Court of California, County of Orange</p>
        </footer>         


        <!--<script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
        <script>window.jQuery || document.write('<script src="bootstrap/js/jquery-1.11.2.min.js"><\/script>')</script>-->

        
        <script type="text/javascript" src="<c:url value="/resources/js/jquery/jquery-3.3.1.min.js" />"></script>
        <script type="text/javascript" src="<c:url value="/resources/js/datatables.min.js" />"></script>
        <script type="text/javascript" src="<c:url value="/resources/js/dataTables.fixedHeader.min.js" />"></script>
        <script src="<c:url value="/resources/bootstrap/js/bootstrap.js" />"></script>
        
        
        <script type="text/javascript">
        $(document).ready(function() {
            $('#wqTable').DataTable();
        });
        </script>   
        
    </body>
</html>
