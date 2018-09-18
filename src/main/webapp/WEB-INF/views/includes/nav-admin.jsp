<%--
nav-admin.jsp is the navigation used for the admin page
$Revision: 4502 $
$Author: cbarrington $
$Date: 2018-08-15 07:37:36 -0700 (Wed, 15 Aug 2018) $
 --%>
 
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

        <nav class="navbar navbar-inverse navbar-fixed-top">
            <div class="container-fluid">
                <div class="navbar-header">             
                    <a class="navbar-brand" href="<c:url value="/review" />"><img src="<c:url value="/resources/images/seal.gif" />" /></a>             
                </div>
                <a href="<c:url value="/review" />" class="btn btn-lg navbar-btn btn-primary" role="button"><span class="glyphicon glyphicon-arrow-left"></span> Go Back</a>
<!--                 <a href="#" class="btn btn-lg navbar-btn btn-default" role="button" onclick="window.open('admin-add', 'addUserWindow', 'toolbar=0,location=0,menubar=0,resizable=1,scrollbars=1,width=400,height=605');"><span class="glyphicon glyphicon-plus"></span> Add User</a> -->
 				<a href="admin-add" id="admin-add" rel="modal:open" class="btn btn-lg navbar-btn btn-default" role="button"><span class="glyphicon glyphicon-plus"></span> Add User</a>
                <ul class="nav navbar-nav navbar-right">                    
                    <li><a href="admin"><span class="glyphicon glyphicon-cog"></span> Admin</a></li>
                    <li><a href="logout"><span class="glyphicon glyphicon-log-out"></span> Logout</a></li>
                </ul>     
            </div>
            
        </nav>
        
        <br><br>  