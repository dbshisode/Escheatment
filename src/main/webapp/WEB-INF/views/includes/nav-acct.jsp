<%--
nav-acct.jsp is the navigation used for pages displayed for users with the Accounting role
$Revision: 4502 $
$Author: cbarrington $
$Date: 2018-08-15 07:37:36 -0700 (Wed, 15 Aug 2018) $
 --%>

        <nav class="navbar navbar-inverse navbar-fixed-top">
            <div class="container-fluid">
                <div class="navbar-header">             
                    <a class="navbar-brand" href="#"><img src="assets/img/seal.gif" /></a>             
                </div>
                <a href="review" class="btn btn-lg navbar-btn btn-primary" role="button">Review <span class="badge"><s:property value="itemsForReview.size" /></span></a>
                <a href="#" class="btn btn-lg navbar-btn btn-default" role="button">Pending <span class="badge"><s:property value="pendingCount" /></span></a>
                <a href="#" class="btn btn-lg navbar-btn btn-default" role="button">&nbsp;&nbsp;&nbsp;Archive&nbsp;&nbsp;&nbsp;</a>
                <ul class="nav navbar-nav navbar-right">                    
                    <li><a href="logout"><span class="glyphicon glyphicon-log-out"></span> Logout</a></li>
                </ul>     
            </div>
        </nav>