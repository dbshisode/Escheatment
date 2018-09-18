<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<%--
roa.jsp is the roa view
$Revision: 4502 $
$Author: cbarrington $
$Date: 2018-08-15 07:37:36 -0700 (Wed, 15 Aug 2018) $
 --%> 

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7"/>
<title>Escheatment Application - Review Notice of Unclaimed Funds</title>

<link href="<c:url value="/resources/bootstrap/css/bootstrap.css" />" rel="stylesheet">
<link rel="stylesheet" href="<c:url value="/resources/bootstrap/css/bootstrap-theme.min.css" />">
<link rel="stylesheet" href="<c:url value="/resources/css/main.css" />">

<script type="text/javascript" src="<c:url value="/resources/js/jquery/jquery-3.3.1.min.js" />"></script>
<script src="<c:url value="/resources/bootstrap/js/bootstrap.js" />"></script>
<script src="<c:url value="/resources/bootstrap/js/modernizr-2.8.3-respond-1.4.2.min.js" />"></script>  
<script src="<c:url value="/resources/js/jquery/external/jquery.validate.min.js" />"></script>

</head>
<body style="width:100%; height:98%; background-color:#2F2F2F;">

<div style="margin:10px;">
	<a href="<c:url value="/send-notice-unclaimed-funds" />" class="btn btn-lg navbar-btn btn-primary" role="button"><span class="glyphicon glyphicon-arrow-left"></span> Go Back</a>
	<a href="#" class="btn btn-lg navbar-btn btn-default pull-right" role="button"><span class="glyphicon glyphicon-envelope"></span> Send</a>
</div>

    <object data="http://labcfdev2/temp/NoticeofUnclaimedFunds.pdf" type="application/pdf" width="100%" height="700">
        <embed src="http://labcfdev2/temp/NoticeofUnclaimedFunds.pdf" type="application/pdf" />
    </object>



</body>
</html>	

    