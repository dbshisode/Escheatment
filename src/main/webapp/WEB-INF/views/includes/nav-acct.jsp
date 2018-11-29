<%--
nav-acct.jsp is the navigation used for pages displayed for users with the Accounting role
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
                <a href="review" class="btn btn-lg navbar-btn ${tab1State}" role="button">Review <span id="acctReviewCount" class="badge">${acctCount.acctReviewCount}</span></a>
                <a href="acct-pending" class="btn btn-lg navbar-btn ${tab2State}" role="button">Pending <span id="acctPendingCount" class="badge">${acctCount.acctPendingCount}</span></a>
                <a href="notice-sent" class="btn btn-lg navbar-btn ${tab3State}" role="button">On Hold <span id="acctOnHoldCount" class="badge">${acctCount.acctOnHoldCount}</span></a>
                <a href="acct-under-review" class="btn btn-lg navbar-btn ${tab4State}" role="button">Claims Under Review <span id="acctUnderReviewCount" class="badge">${acctCount.acctUnderReviewCount}</span></a>
                <a href="notice-sent" class="btn btn-lg navbar-btn ${tab5State}" role="button">&nbsp;&nbsp;Archive&nbsp;&nbsp;</a>
                <ul class="nav navbar-nav navbar-right">                    
                    <c:if test="${user.userRoleAdmin == func_admin_role}"><li><a href="admin"><span class="glyphicon glyphicon-cog"></span> Admin</a></li></c:if>
                    <li><a href="logout"><span class="glyphicon glyphicon-log-out"></span> Logout</a></li>
                </ul>     
            </div>
        </nav>
        
        <br><br>