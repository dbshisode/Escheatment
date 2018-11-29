<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<%--
admin-add-body.jsp is the body of the admin-add view.  It contains a form to allow adding new users with access level.
$Revision: 4502 $
$Author: cbarrington $
$Date: 2018-08-15 07:37:36 -0700 (Wed, 15 Aug 2018) $
 --%>

<div class="modal-body">
	<div class="container-fluid">

		<div class="col-lg-7 col-md-10 col-sm-12" id="messageBox" style="display: none;">
			<div class="row">
				<div class="col-lg-6 col-md-10 col-sm-12" id="messageText"></div>
			</div>
		</div>

		<div style="clear: both;"></div>

		<div class="col-lg-7 col-md-10 col-sm-12">
			<div class="well">
				<div class="container-fluid">
					<div class="row">
						<div class="col-lg-12 col-md-10 col-sm-12">

							<form:form cssClass="form-horizontal" action="admin-add-save"
								id="admin-add-form" modelAttribute="user" method="post">

								<spring:bind path="userName">
									<div class="form-group row">
										<form:label path="userName" for="userName"
											class="col-lg-4 col-form-label">User Name</form:label>
										<div class="col-lg-8">
											<form:input path="userName" id="userName" label="User Name"
												placeholder="User Name" cssClass="form-control is-invalid" />
											<div class="invalid-feedback">
												<form:errors path="userName" />
											</div>
										</div>
									</div>
								</spring:bind>

								<spring:bind path="firstName">
									<div class="form-group">
										<form:label path="firstName" for="firstName"
											class="col-lg-4 col-form-label">First Name</form:label>
										<div class="col-lg-8">
											<form:input path="firstName" id="firstName"
												label="First Name" placeholder="First Name"
												cssClass="form-control is-invalid align-middle" />
											<div class="invalid-feedback">
												<form:errors path="firstName" />
											</div>
										</div>
									</div>
								</spring:bind>

								<div class="form-group">
									<form:label path="middleName" for="middleName"
										class="col-lg-4 col-form-label">Mid. Name</form:label>
									<div class="col-lg-8">
										<form:input path="middleName" id="middleName"
											label="Middle Name" placeholder="Middle Name"
											cssClass="form-control" />
									</div>
								</div>

								<spring:bind path="lastName">
									<div class="form-group">
										<form:label path="lastName" for="lastName"
											class="col-lg-4 col-form-label">Last Name</form:label>
										<div class="col-lg-8">
											<form:input path="lastName" id="lastName" label="Last Name"
												placeholder="Last Name" cssClass="form-control" />
											<div class="invalid-feedback">
												<form:errors path="lastName" />
											</div>
										</div>
									</div>
								</spring:bind>

								<div class="form-check row">
									<form:radiobutton path="userFunctionalArea"
										value="${opsRoleValue}" class="form-check-input"
										checked="checked" />
									<label for="userFunctionalArea" class="col-lg-4 col-form-label">
										Operations </label>
								</div>

								<div class="form-check row">
									<form:radiobutton path="userFunctionalArea"
										value="${acctRoleValue}" class="form-check-input" />
									<label for="userFunctionalArea" class="col-lg-4 col-form-label">
										Accounting </label>
								</div>

								<div class="form-check row">
									<form:checkbox path="userRoleAdmin" class="form-check-input"
										value="${funcAdminRoleValue}" id="adminCheckbox" />
									<form:label path="userRoleAdmin" for="adminCheckbox"
										class="col-lg-4 col-form-label">
								    Admin
								  </form:label>
								</div>

								<div id="addBtn" class="pull-right row">
									<button type="submit" name="submit" class="btn btn-primary">Add</button>
								</div>

								<form:hidden path="active" value="Y" />
								<form:hidden path="password" name="password" value="p" />
							</form:form>

						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>



<script type="text/javascript">
	$(document)
			.ready(
					function() {
						$("#admin-add-form")
								.validate(
										{
											rules : {
												firstName : "required",
												lastName : "required",
												userName : {
													required : true,
													minlength : 3
												}
											},
											messages : {
												firstName : "Please enter a first name",
												lastName : "Please enter a last name",
												userName : {
													required : "Please enter a user name",
													minlength : "User name must consist of at least 3 characters"
												}
											},
											errorElement : "em",
											errorPlacement : function(error,
													element) {
												// Add the 'help-block' class to the error element
												error.addClass("help-block");

												if (element.prop("type") === "checkbox") {
													error.insertAfter(element
															.parent("label"));
												} else {
													error.insertAfter(element);
												}
											},
											highlight : function(element,
													errorClass, validClass) {
												$(element).parents(".col-lg-8")
														.addClass("has-error")
														.removeClass(
																"has-success");
											},
											unhighlight : function(element,
													errorClass, validClass) {
												$(element)
														.parents(".col-lg-8")
														.addClass("has-success")
														.removeClass(
																"has-error");
											},

											submitHandler : function(form) {
												$
														.ajax({
															url : "admin-add-save",
															type : 'POST',
															//contentType: 'application/json',
															data : $(
																	'#admin-add-form')
																	.serialize(),
															success : function(
																	data) {
																var responseJson = $
																		.parseJSON(data);

																if (responseJson.status == "SUCCESS") {

																	$(
																			'#admin-add-form')
																			.find(
																					':input:not(:disabled)')
																			.prop(
																					'disabled',
																					true);
																	$(
																			'#messageBox')
																			.addClass(
																					"alert alert-success");
																	$(
																			'#messageBox')
																			.removeClass(
																					"alert-warning");
																	$(
																			'#messageBox')
																			.removeClass(
																					"alert-danger");
																	$(
																			'#messageText')
																			.html(
																					responseJson.message);
																	$('#addBtn')
																			.hide();

																} else if (responseJson.status == "ERROR") {
																	$(
																			'#messageBox')
																			.addClass(
																					"alert alert-warning");
																	$(
																			'#messageBox')
																			.removeClass(
																					"alert-success");
																	$(
																			'#messageBox')
																			.removeClass(
																					"alert-danger");
																	$(
																			'#messageText')
																			.html(
																					responseJson.message);
																} else {
																	errorInfo = "";
																	$
																			.each(
																					responseJson.errorMsgs,
																					function(
																							i,
																							item) {
																						errorInfo = errorInfo
																								+ item
																								+ "<br />";
																					});

																	$(
																			'#messageBox')
																			.addClass(
																					"alert alert-danger");
																	$(
																			'#messageBox')
																			.removeClass(
																					"alert-warning");
																	$(
																			'#messageBox')
																			.removeClass(
																					"alert-success");
																	$(
																			'#messageText')
																			.html(
																					errorInfo);
																}

																$('#messageBox')
																		.show();
															}
														});

												return false;
											}
										});
					});
</script>