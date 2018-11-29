<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<%--
acct-add-to-list-trust-body.jsp is the body of the add-to-acct-list-trust view.  
It contains a form to allow review of trust information before saving.
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

							<form:form cssClass="form-horizontal" action="add-to-acct-list-trust" modelAttribute="formData" method="post">

								<spring:bind path="trustNumber">
									<div class="form-group row">
										<form:label path="trustNumber" for="trustNumber"
											class="col-lg-4 col-form-label">Trust Number</form:label>
										<div class="col-lg-8">
											<form:input path="trustNumber" id="trustNumber" label="Trust Number"
												placeholder="Trust Number" cssClass="form-control is-invalid" />
											<div class="invalid-feedback">
												<form:errors path="trustNumber" />
											</div>
										</div>
									</div>
								</spring:bind>

								<div id="addBtn" class="pull-right row">									
									<button type="submit" name="submit" cssClass="btn btn-primary">Find</button>
								</div>

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