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
<title>Escheatment Application - Register of Actions</title>

</head>

<frameset id="mainFrameset" cols="400,*" border="1" frameborder="1" framespacing="2" bordercolor="#94AAC7">

    <!-- left pane -->
    <frame src="roa-left-frame" name="leftframe" id="leftframe" scrolling="auto" />

    <!-- right pane -->
    <frame src="roa-right-frame" name="rightframe" id="rightframe" scrolling="auto" />
	
	<noframes><h3>Your browser must support frames.</h3></noframes>

</html>	

    