<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<%--
acct-add-to-list-body.jsp is the body of the add-to-acct-list view.  It contains a form to allow adding new users with access level.
$Revision: 4502 $
$Author: cbarrington $
$Date: 2018-08-15 07:37:36 -0700 (Wed, 15 Aug 2018) $
 --%>

<div class="modal-body">
	<div class="container-fluid">

		<div class="col-lg-7 col-md-10 col-sm-12" id="modalMessageBox" style="display: none;">
			<div class="row">
				<div class="col-lg-6 col-md-10 col-sm-12" id="modalMessageText"></div>
			</div>
		</div>

		<div style="clear: both;"></div>

		<div class="col-lg-8 col-md-10 col-sm-12">
			<div class="well">
				<div class="container-fluid">
					<div class="row">
						<div class="col-lg-12 col-md-10 col-sm-12">

							<form class="form-horizontal" id="add-to-acct-list" action="add-to-acct-list-trust" method="post">


									<div class="form-group row">
										<label for="trustNumber" class="col-lg-4 col-form-label">Trust Number</label>
										<div class="col-lg-8">
											<input id="trustNumber" name="trustNumber" placeholder="Trust Number" class="form-control" />
											<div class="invalid-feedback">
												
											</div>
										</div>
									</div>

								<div id="trustInfo" style="display:none;">
									<div class="form-group row">
										<label for="trustAmount" class="col-lg-4 col-form-label">Trust Amount</label>
										<div class="col-lg-8">
											<input name="trustAmount" id="trustAmount" placeholder="Trust Amount" class="form-control" />
										</div>
									</div>		
									
									<div class="form-group row">
										<label path="trustType" for="trustType"	class="col-lg-4 col-form-label">Trust Type</label>
										<div class="col-lg-8">
											<input name="trustType" id="trustType" placeholder="Trust Type" class="form-control" />
										</div>
									</div>	
									
									<div class="form-group row">
										<label for="trustDate" class="col-lg-4 col-form-label">Trust Date</label>
										<div class="col-lg-8">
											<input name="trustDate" id="trustDate" placeholder="Trust Date" class="form-control" />
										</div>
									</div>	
									
									<div class="form-group row">
										<label path="caseNumber" for="caseNumber" class="col-lg-4 col-form-label">Case Number</label>
										<div class="col-lg-8">
											<input name="caseNumber" id="caseNumber" placeholder="Case Number" class="form-control" />
										</div>
									</div>	
									
									<div class="row">
										<label for="caseTitle"	class="col-lg-4 col-form-label">Case Title</label>
										<div class="col-lg-8">
											<input name="caseTitle" id="caseTitle" placeholder="Case Title" class="form-control" />
										</div>
									</div>	
									
									<hr />
									
									<div class="form-group row">
										<label for="nameLine1"	class="col-lg-4 col-form-label">Name Line 1</label>
										<div class="col-lg-8">
											<input name="nameLine1" id="nameLine1" placeholder="Lawful Owner Name" class="form-control" />
										</div>
									</div>	
									
									<div class="form-group row">
										<label for="nameLine1"	class="col-lg-4 col-form-label">Name Line 2</label>
										<div class="col-lg-8">
											<input name="nameLine2" id="nameLine2" placeholder="Lawful Owner Name" class="form-control is-invalid" />
										</div>
									</div>	
									
									<div class="form-group row">
										<label for="addressLine1" class="col-lg-4 col-form-label">Address Line 1</label>
										<div class="col-lg-8">
											<input name="addressLine1" id="addressLine1" placeholder="Lawful Owner Address" class="form-control is-invalid" />
										</div>
									</div>		

									<div class="form-group row">
										<label for="addressLine2" class="col-lg-4 col-form-label">Address Line 2</label>
										<div class="col-lg-8">
											<input name="addressLine2" id="addressLine2" placeholder="Lawful Owner Address" class="form-control is-invalid" />
										</div>
									</div>
									
									<div class="form-group row">
										<label for="addressLine3" class="col-lg-4 col-form-label">Address Line 3</label>
										<div class="col-lg-8">
											<input name="addressLine3" id="addressLine3" placeholder="Lawful Owner Address" class="form-control is-invalid" />
										</div>
									</div>																																										
																											
								</div>														

								<div id="addBtn" class="pull-right row">									
									<button id="submitBtn" type="submit" name="submit" class="btn btn-primary">Find</button>
								</div>
								<input type="hidden" id="trustId" name="trustId" value="" />
<!-- 								<input type="hidden" id="hiddenTrustNum" name="hiddenTrustNum" value="" /> -->
							</form>

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
						$("#add-to-acct-list")
								.validate(
										{
											rules : {
												trustNumber : "required",
												nameLine1 : "required",
												addressLine1 : "required",
												addressLine3 : "required"
											},
											messages : {
												trustNumber : "Please enter a trust number",
												nameLine1 : "Please enter a lawful owner name",
												addressLine1 : "Please enter a lawful owner address",
												addressLine3 : "Please enter a lawful owner address"
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
												$(element).parent(".col-lg-8")
														.addClass("has-error")
														.removeClass(
																"has-success");
											},
											unhighlight : function(element,
													errorClass, validClass) {
												$(element)
														.parent(".col-lg-8")
														.addClass("has-success")
														.removeClass(
																"has-error");
											},

											submitHandler : function(form) {
												var post_url = $(form).attr("action");
												$
														.ajax({
															url : post_url,
															type : 'POST',
															//contentType: 'application/json',
															data : $(
																	'#add-to-acct-list')
																	.serialize(),
															success : function(
																	data) {
																var responseJson = $
																		.parseJSON(data);

																if (responseJson.status == "SUCCESS") {

																	$('#modalMessageText').html('');
																	$(
																	'#modalMessageBox')
																			.removeClass(
																			"alert alert-warning");
																	$(
																			'#modalMessageBox')
																			.removeClass(
																					"alert-success");
																	$(
																			'#modalMessageBox')
																			.removeClass(
																					"alert-danger");
																	
																	document.getElementById('trustNumber').readOnly = true;
																	document.getElementById('trustAmount').readOnly = true;
																	document.getElementById('trustType').readOnly = true;
																	document.getElementById('trustDate').readOnly = true;
																	document.getElementById('caseNumber').readOnly = true;
																	document.getElementById('caseTitle').readOnly = true;
																	
																	/* $(
																			'#add-to-acct-list')
																			.find(
																					':input:not(:button):not([type=hidden])')
																			.prop(
																					'disabled',
																					true); */
																	
																	if (post_url == 'add-to-acct-list-trust') {
																		$('#submitBtn').html("Add");																	
																		$('#trustAmount').val(responseJson.trustAmount);
																		$('#trustType').val(responseJson.trustType);
																		$('#trustDate').val(responseJson.trustDate);
																		$('#trustId').val(responseJson.trustId);
																		$('#trustNumber').val(responseJson.trustNumber);
																		//$('#hiddenTrustNum').val(responseJson.trustNumber);
																		$('#caseNumber').val(responseJson.caseNumber);
																		$('#caseTitle').val(responseJson.caseTitle);
																		$("#add-to-acct-list").attr("action", "add-to-acct-list-save");
																		$('#trustInfo').show();																		
																	} else {
																		$(
																		'#modalMessageBox')
																		.addClass(
																				"alert alert-success");																		
																		$('#modalMessageText').html(responseJson.message);
																		$('#addBtn').hide();
																		
																		//increment count in pill nav
																		//var acctReviewCount = +$('#acctReviewCount').text() + 1;
																		//$("#acctReviewCount").text(acctReviewCount);																		
																	}																	
																	
																} else if (responseJson.status == "ERROR") {
																	$(
																			'#modalMessageBox')
																			.addClass(
																					"alert alert-warning");
																	$(
																			'#modalMessageBox')
																			.removeClass(
																					"alert-success");
																	$(
																			'#modalMessageBox')
																			.removeClass(
																					"alert-danger");
																	$(
																			'#modalMessageText')
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
																			'#modalMessageBox')
																			.addClass(
																					"alert alert-danger");
																	$(
																			'#modalMessageBox')
																			.removeClass(
																					"alert-warning");
																	$(
																			'#modalMessageBox')
																			.removeClass(
																					"alert-success");
																	$(
																			'#modalMessageText')
																			.html(
																					errorInfo);
																}

																$('#modalMessageBox')
																		.show();
															}
														});

												return false;
											}
										});
					});
</script>