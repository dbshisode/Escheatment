<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7" lang=""> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8" lang=""> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9" lang=""> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js" lang=""> <!--<![endif]-->
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <title>Escheatment Application - Accounting Review</title>
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <link href="<c:url value="/resources/bootstrap/css/bootstrap.css" />" rel="stylesheet">
        <link rel="stylesheet" href="<c:url value="/resources/bootstrap/css/bootstrap-theme.min.css" />">
        <link rel="stylesheet" href="<c:url value="/resources/css/main.css" />">

        <script src="bootstrap/js/vendor/modernizr-2.8.3-respond-1.4.2.min.js"></script>
                
        <link rel="stylesheet" type="text/css" href="css/datatables.min.css"/>
        <link rel="stylesheet" type="text/css" href="css/fixedHeader.dataTables.min.css"/>       
        
    </head>
    <body>
      
        <%@include file="includes/nav-acct.jsp" %>     
        
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
                        <h2>Escheatment for Publication</h2>
                        <%--<p>Some default panel content here. Nulla vitae elit libero, a pharetra augue. Aenean lacinia bibendum nulla sed consectetur. Aenean eu leo quam. Pellentesque ornare sem lacinia quam venenatis vestibulum. Nullam id dolor id nibh ultricies vehicula ut id elit.</p>--%>
                    </div>
                </div>
                <div class="row">
                    <div class="col-lg-12">
                        <div class="panel panel-primary">
                            <!-- Default panel contents -->
                            <div class="panel-heading">Items for Review</div>

                            <!-- Table -->
                            <table class="table table-striped" id="wqTable"> 
                                <thead> 
                                    <tr> 
                                        <th>#</th> 
                                        <th>Case Number</th> 
                                        <th>Case Title</th> 
                                        <th>Fee Type</th> 
                                        <th>Depositor Name</th>
                                        <th>Balance</th>
                                        <th>&nbsp;</th>
                                    </tr> 
                                </thead> 
                                <tbody>   
                                    <c:forEach var="items" items="${itemsForReview}">
                                    <tr> 
                                        <th scope=row>                                            
											<!-- <s:property value="%{#rowStatus.count}" /> -->
                                        </th> 
                                        <td><c:out value="items.DisplayCaseNum" /></td> 
                                        <td><c:out value="items.CaseTitle" /></td> 
                                        <td><c:out value="items.FeeType" /></td>
                                        <td><c:out value="items.DepositorName" /></td>
                                        <td align="right">$<c:out value="getText('{0,number,#,##0.00}',{Balance})" /></td>
                                        <td align="right"><button type="button" class="btn-default">Remove</button></td>
                                    </tr>                                         
									</c:forEach>                                    
                                </tbody> 
                            </table>
                        </div>  
                        <div id="btnContainer" class="pull-right">
                            <button type="button" class="btn-default">Export to Excel</button>&nbsp;&nbsp;
                            <button type="button" class="btn-default">Mark for Escheatment</button>                            
                        </div>
                    </div>
                </div>   

            </div> <!-- /container -->   
        </div>

		<%@include file="includes/footer.jsp" %>
        

        <script src="js/main.js"></script>
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
