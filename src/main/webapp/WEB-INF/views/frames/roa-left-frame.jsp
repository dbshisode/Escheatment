<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7"/>
    <title>MyCase | Content Frame</title>
    
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/themes/custom-theme/jquery-ui-1.8.17.custom.css" />" />
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/escheatment/tipsy.css" />" />
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/escheatment/toolbar.css" />" />
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/escheatment/fg-button.css" />" />
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/escheatment/layout.css" />" />
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/escheatment/buttons-theme.css" />" />
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/escheatment/datatable.css" />" />
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/escheatment/frame-content.css" />" />
    <!--[if IE]>
      <style>#content-docs, #content-participants, #content-legalworkups, #content-proposedorders, #content-futureevents {float:left; width:95.7% !important; overflow:hidden;}</style>
    <![endif]-->
	
    <script type="text/javascript" src="<c:url value="/resources/js/jquery/add-ons/less-1.2.1.min.js" />"></script>
    <script type="text/javascript" src="<c:url value="/resources/js/jquery/jquery-1.7.1.min.js" />"></script>
    <script type="text/javascript" src="<c:url value="/resources/js/jquery/external/jquery.cookie.js" />"></script>
    <script type="text/javascript" src="<c:url value="/resources/js/jquery/ui/jquery-ui-1.8.17.custom.min.js" />"></script>
    <script type="text/javascript" src="<c:url value="/resources/js/jquery/layout-plugin/jquery.layout-min-1.3.0.js" />"></script>
    <script type="text/javascript" src="<c:url value="/resources/js/jquery/add-ons/jquery.tipsy.js" />"></script>
    <script type="text/javascript" src="<c:url value="/resources/js/jquery/add-ons/jquery.tablesorter.min.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/js/escheatment/frame-content.js" />"></script>	

	<script>
	$(function() {
	  $( "#date1" ).datepicker({
		changeMonth: true,
		changeYear: true
	  });
	});
	$(function() {
	  $( "#date2" ).datepicker({
		changeMonth: true,
		changeYear: true
	  });
	});	
	</script>	
	
	<script type="text/javascript">
	<!--
	 var childWin = new Array();
	 var winCount = 0;
	function openPopup(path,docId,dmsId) {
	 childWin[winCount] = window.open('/Escheatment/' + path + '?F_DocNumber=' + docId + '&dms=' + dmsId + '&ErrorPage=eService/DocPreview', winCount, 'toolbar=0,location=0,menubar=0,resizable=1,scrollbars=1');
	 winCount++;
	 return false;
	 }
	function ClosePopupDocs() {
	 for (winCount = 0; winCount < childWin.length; winCount++) {
	 if (childWin[winCount] != null && !childWin[winCount].closed)
	 childWin[winCount].close();
	 }
	 }
	-->
	</script>		
	
</head>

<body id="frame-content" onunload="ClosePopupDocs();">
    
    <div class="ui-layout-north">
        <!-- top toolbar -->
        <div id="toolbar-top">
			<a id="toolbar-button-close" class="toolbar-button-close" onclick="javascript:window.parent.close();">close</a>
			<!--<a id="toolbar-button-merge" class="toolbar-button-print link-tooltip" title="Merge Documents" href="getAllCaseDocs.cfm?caseId=#session.case_id#" target="_blank">&nbsp;</a>-->
            <span><input type="text" name="keyword" id="keyword" size="25" maxlength="20" title="Enter Search Term" class="link-tooltip" /></span>
        </div>
    </div>
    
    <div class="ui-layout-center">

		<div class="header">
			<span class="notes" title="Case Title">05CC00012</span>			
		</div>
		
		<div class="date-filter">
			<span>
				<form name="dateFilter">	
			    &nbsp;&nbsp;			
				From: <input type="text" id="date1" name="date1" size="8" />&nbsp;&nbsp; 
				To: <input type="text" id="date2" name="date2" size="8" /> 
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input name="button-datefilter" id="button-datefilter" type="button" value="Filter" />
				&nbsp;&nbsp;<input name="button-clear-datefilter" id="button-clear-datefilter" type="button" value="Clear" /> <!-- ajax submit form -->
				</form>
			</span>
		</div>

        
        <!-- content -->		
        <div class="ui-layout-content">
            <div id="content-docs"></div>
        </div>		

        <!-- footer -->
        <div class="footer">
            <div id="footer-toolbar-filters">
                <input type="radio" id="docsShowAll" name="docfilter" /><label title="Show All Documents" for="docsShowAll">&nbsp;&nbsp;&nbsp; All &nbsp;&nbsp;&nbsp;</label>
                <input type="radio" id="docsShowMO" name="docfilter" /><label title="Show Minute Orders" for="docsShowMO">Minute Orders</label>
				<input type="radio" id="docsShowPublic" name="docfilter" /><label title="Show Public Docs" for="docsShowPublic">Public Docs</label>
            </div>            
        </div>
    </div> <!-- end center pane -->

</body>
</html>
