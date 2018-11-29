<%--
nav-ops.jsp is the navigation used for pages displayed for users with the Operations role
$Revision: 4565 $
$Author: cbarrington $
$Date: 2018-11-07 15:59:56 -0800 (Wed, 07 Nov 2018) $
 --%>
 
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

        <nav class="navbar navbar-inverse navbar-fixed-top">
            <div class="container-fluid">
                <div class="navbar-header">             
                    <a class="navbar-brand" href="<c:url value="/review" />"><img src="<c:url value="/resources/images/seal.gif" />" /></a>             
                </div>
                <a href="review" class="btn btn-lg navbar-btn ${tab1State}" role="button">Review <span id="opsReviewCount" class="badge">${opsCount.opsReviewCount}</span></a>
                <a href="active" class="btn btn-lg navbar-btn ${tab2State}" role="button">Marked as Active <span id="opsActiveCount" class="badge">${opsCount.opsActiveCount}</span></a>
                <a href="notice-sent" class="btn btn-lg navbar-btn ${tab3State}" role="button">&nbsp;&nbsp;&nbsp;Notice Sent <span id="opsSentCount" class="badge">${opsCount.opsSentCount}</span>&nbsp;&nbsp;&nbsp;</a>
                <ul class="nav navbar-nav navbar-right">                    
                    <c:if test="${user.userRoleAdmin == func_admin_role}"><li><a href="admin"><span class="glyphicon glyphicon-cog"></span> Admin</a></li></c:if>
                    <li><a href="logout"><span class="glyphicon glyphicon-log-out"></span> Logout</a></li>
                </ul>     
            </div>
        </nav>
        
        <br><br>