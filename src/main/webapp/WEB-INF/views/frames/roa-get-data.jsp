<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

		<form name="ROA" id="ROA" action="/roa-get-data" method="post">
		<table id="doc" class="datatable tablesorter">
		<thead>
			<tr id="header">
				<th class="doc-nav-left">&nbsp;</th>
				<th class="doc-name">Case Document</th>
				<th class="doc-roa">ROA</th>
				<th class="doc-filer">&nbsp;</th>				
				<th class="doc-date">Filed</th>
				<th class="hidden"></th>
			</tr>
		</thead>
		<tbody>
					
		<c:forEach var="items" items="${roadata}" varStatus="loop">
			<tr>			
				<!-- document icon left -->				
				<td class="doc-nav-left"><a class="doc-link-icon-left" href="get-document?doc_id=<c:out value="${items.docId}" />&dms=<c:out value="${items.dms}" />">&nbsp;</a></td>
				<!-- document name -->
				<td>
					<span class="doc-link" onclick="javascript:openPopup('get-document','<c:out value="${items.docId}" />','<c:out value="${items.dms}" />');"><c:out value="${items.docName}" /></span>
					<c:if test="${not empty items.otherText}">
						<span class="doc-tooltip" title="<c:out value="${items.otherText}" />">&nbsp;</span>
					</c:if>			
					<c:if test="${items.securityLevel > 1}">
						<span class="doc-sealed" title="Sealed">&nbsp;</span>
					</c:if>
				</td>
			<td><c:out value="${items.lineNum}" /></td>
			
			<!-- filer -->
			<c:choose>
				<c:when test = "${not empty items.filingParty}">
					<td><a class="filer-link" href="#" title="Filed by: <c:out value="${items.filingParty}" />"><span class="doc-filer-icon">&nbsp;</span></a></td>
				</c:when>
				<c:otherwise>
					<td>&nbsp;</td>
				</c:otherwise>
			</c:choose>
			
			<!-- checkbox ( note: do NOT show checkbox if we don't have an event, BUT must show an empty column so we have corrent number of columns for tablesorter ) -->
			<!--<td>			
				<input type="checkbox" name="checkboxDocPrep" id="<c:out value="${items.documentId}" />">									
			</td>-->
			
			<!-- date -->
			<td><fmt:formatDate pattern = "MM/dd/yyyy" value = "${items.externalDt}" /></td>
			
			<!-- hidden column; filer(s) -->
			<td class="hidden"><c:out value="${items.filingParty}" /></td>
			
			</tr>
		</c:forEach>
		
		</tbody>
		</table>
		</form>
		