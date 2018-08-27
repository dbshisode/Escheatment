<%--
datatables.jsp contains tag library, css and js for datatables
$Revision: 4502 $
$Author: cbarrington $
$Date: 2018-08-15 07:37:36 -0700 (Wed, 15 Aug 2018) $
 --%>

<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/datatables.min.css" />">
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/fixedHeader.dataTables.min.css" />">
<script type="text/javascript" src="<c:url value="/resources/js/datatables.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/js/dataTables.fixedHeader.min.js" />"></script>