<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

        <nav class="navbar navbar-inverse navbar-fixed-top">
            <div class="container-fluid">
                <div class="navbar-header">             
                    <a class="navbar-brand" href="<c:url value="/review" />"><img src="<c:url value="/resources/images/seal.gif" />" /></a>             
                </div>
                <a href="review" class="btn btn-lg navbar-btn ${tab1State}" role="button">Review <span class="badge">${opsCount.opsReviewCount}</span></a>
                <a href="active" class="btn btn-lg navbar-btn ${tab2State}" role="button">Marked as Active <span class="badge">${opsCount.opsActiveCount}</span></a>
                <a href="sent" class="btn btn-lg navbar-btn ${tab3State}" role="button">&nbsp;&nbsp;&nbsp;Notice Sent <span class="badge">${opsCount.opsSentCount}</span>&nbsp;&nbsp;&nbsp;</a>
                <ul class="nav navbar-nav navbar-right">                    
                    <li><a href="admin"><span class="glyphicon glyphicon-cog"></span> Admin</a></li>
                    <li><a href="logout"><span class="glyphicon glyphicon-log-out"></span> Logout</a></li>
                </ul>     
            </div>
        </nav>
        
        <br><br>