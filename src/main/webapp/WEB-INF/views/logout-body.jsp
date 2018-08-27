<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<%--
logout-body.jsp is the body of the logout view.  It contains a form to allow users to provide user name and password,
so the user may log back in if necessary
$Revision: 4501 $
$Author: cbarrington $
$Date: 2018-08-15 07:14:20 -0700 (Wed, 15 Aug 2018) $
 --%>

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
                                <form:hidden path="firstName" name="firstName" value="firstName" />
                                <form:hidden path="lastName" name="lastName" value="lastName" />
								
                            </fieldset>
                        </form:form>
                    </div>
                </div>
                <br/>
				<div class="row">
                    <div class="col-md-1"></div>
                    <div class="col-lg-4 alert alert-warning">
                        You have successfully logged out.
                    </div>
                    <div class="col-md-1"></div>
                </div>  
			                  
            </div>
        </div>
    </div>