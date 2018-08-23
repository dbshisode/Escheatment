<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">

        <link href="<c:url value="/resources/bootstrap/css/bootstrap.css" />" rel="stylesheet">
        <link rel="stylesheet" href="<c:url value="/resources/bootstrap/css/bootstrap-theme.min.css" />">
        <link rel="stylesheet" href="<c:url value="/resources/css/main.css" />">

        <script type="text/javascript" src="<c:url value="/resources/js/jquery/jquery-3.3.1.min.js" />"></script>
        <script src="<c:url value="/resources/bootstrap/js/bootstrap.js" />"></script>

        <title>Escheatment Application Login</title>

    </head>

    <body>	

	<%@include file="includes/nav-home.jsp" %>

    <div class="col-lg-6 col-lg-offset-3">
        <div class="well">
            <div class="container">
                <div class="row">
                    <div class="col-lg-6">
                    	<form:form action="review" modelAttribute="user" method="post" cssClass="bs-example form-horizontal">
                            <fieldset>
                                <legend>Escheatment Application Login</legend>

                                <form:input id="userName"  path="userName" label="User Name" cssClass="col-lg-10" placeholder="User Name" />                                
								
								<br /><br />
								
                                <form:password path="password" label="Password" cssClass="col-lg-10" placeholder="Password" />
								
								<div style="clear:both;"><br/></div>	                                
                                <div class="col-lg-10"> 
                                	<div class="pull-right">                                                                  
                                    	<button type="submit" name="submit" cssClass="btn btn-primary pull-right">Login</button>
                                    </div>
                                </div>                                
                                <%-- <form:hidden path="pageName" name="pageName" value="login" /> --%>
								
                            </fieldset>
                        </form:form>
                    </div>
                </div>
                <br/>
			    <spring:hasBindErrors name="user">
				<div class="row">
                	<div class="col-md-1"></div>
		            <div class="col-lg-4 alert alert-danger">			    
						<c:forEach var="error" items="${errors.allErrors}">
							<spring:message message="${error}" /><br />
						</c:forEach> 
		            </div>
                    <div class="col-md-1"></div>
                </div>        
			    </spring:hasBindErrors>  
			                  
            </div>
        </div>
    </div>


</body>
</html>
