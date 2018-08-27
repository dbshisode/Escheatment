<%--
bootstrap.jsp contains tag library, css and js for bootstrap
$Revision: 4512 $
$Author: cbarrington $
$Date: 2018-08-24 15:52:51 -0700 (Fri, 24 Aug 2018) $
 --%>
 
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<link href="<c:url value="/resources/bootstrap/css/bootstrap.css" />" rel="stylesheet">
<link rel="stylesheet" href="<c:url value="/resources/bootstrap/css/bootstrap-theme.min.css" />">
<link rel="stylesheet" href="<c:url value="/resources/css/main.css" />">

<script type="text/javascript" src="<c:url value="/resources/js/jquery/jquery-3.3.1.min.js" />"></script>
<script src="<c:url value="/resources/bootstrap/js/bootstrap.js" />"></script>
<script src="<c:url value="/resources/bootstrap/js/modernizr-2.8.3-respond-1.4.2.min.js" />"></script>  
<script src="<c:url value="/resources/js/jquery/external/jquery.validate.min.js" />"></script>       