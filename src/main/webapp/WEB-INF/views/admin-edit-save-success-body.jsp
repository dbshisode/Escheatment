<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<%--
admin-edit-save-success-body.jsp is the body of the admin-edit-save view.  Display success message and button to close window.
$Revision: 4502 $
$Author: cbarrington $
$Date: 2018-08-15 07:37:36 -0700 (Wed, 15 Aug 2018) $
 --%>

<br>    
<div class="col-lg-6">
        <div class="well">
            <div class="container">
				<div class="row">
                	<div class="col-md-1"></div>
		            <div class="col-lg-4 alert alert-success">			    
						User edited successfully!
		            </div>
                    <div class="col-md-1"></div>
                </div> 
				<div class="row">
                	<div class="col-md-6">
                		<button type="button" class="btn btn-default" onclick="window.close();">Close</button>
                	</div>                
			    </div>			                 
            </div>
        </div>
    </div>    
    
<script>
    window.onunload = refreshParent;
    function refreshParent() {
        window.opener.location.reload();
    }
</script>