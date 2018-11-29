<%--
datatables.jsp contains tag library, css and js for datatables
$Revision: 4547 $
$Author: cbarrington $
$Date: 2018-10-25 15:53:00 -0700 (Thu, 25 Oct 2018) $
 --%>

<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<style>
.addlNames {
	display:none;
	}
.addlNamesRow {
	float:left;
	padding:5px 0px 10px 0px;
	overflow:hidden;
	border-top:1px solid #999;	
}
.nameAddress {
	padding-bottom:5px;
	float:left;
	min-width:170px;
	}
.trustId {display:none;}

.firstLawfulOnwerButtons {
	float:left;
	margin:15px 0 0 10px;
}
.addlLawfulOnwerButtons {
	float:left;
	margin:20px 0 0 10px;	
}

</style>

<link rel="stylesheet" type="text/css" href="<c:url value="/resources/datatables/datatables.min.css" />">
<script type="text/javascript" src="<c:url value="/resources/datatables/datatables.min.js" />"></script>