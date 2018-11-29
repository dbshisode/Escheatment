<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>

    <script type='text/javascript'> 
    $(document).ready(function() {
	    $(function () {
	    	$('#datepicker').datetimepicker({
	    		format: 'MM/DD/YYYY'
	    	});
	    });	
	    
		$("#requestAddlInfoForm")
		.validate(
				{
					 rules : {
						nameLine1 : "required",
						addressLine1 : "required",
						addressLine3 : "required",
						claimAmount : "required",
						claimDate : "required",
						reason : "required"
					}, 
					messages : {
						nameLine1: "Please enter a lawful owner name",
						addressLine1 : "Please enter address line 1",
						addressLine3 : "Please enter City, State and Zip",
						claimAmount : "Please enter a claim amount",
						claimDate : "Please enter a claim date",
						reason : "Please select at least one reason"
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
					submitHandler: function(form) {
						$('#review-form').prop("disabled","disabled");
						$('.review-form-btn').removeClass("glyphicon-edit");
						$('.review-form-btn').addClass("glyphicon-refresh glyphicon-refresh-animate");						
					    form.submit();
					  },
					  
				});	    
	    
    });
       
    </script>
    
</head>
<body>

<div class="container">
<br>
	<form id="requestAddlInfoForm" class="form-inline" method="post" action="review-request-addl-info">
	<div class="well">
      <div id="field">
      

      	<div id="lawfulOwner" class="row ufields">	
		  <div class="row top-buffer">
		    <div class="col-xs-6 col-md-4">
		      <input name="nameLine1" type="text" class="form-control required nameLine1" placeholder="Lawful Owner Name" value="${owner.nameLine1}" />
		      <input name="nameLine2" type="text" class="form-control" placeholder="C/O" value="${owner.nameLine2}" />
		    </div>
		    <div class="col-xs-6 col-md-4">
		      <input name="addressLine1" type="text" class="form-control required addressLine1" placeholder="Address Line 1" value="${owner.addressLine1}">
		      <input name="addressLine2" type="text" class="form-control" placeholder="Address Line 2" value="${owner.addressLine2}">
		      <input name="addressLine3" type="text" class="form-control required addressLine2" placeholder="City, State ZIP" value="${owner.addressLine3}">
		      <input type="hidden" name="trustId" value="${owner.trustId}" />
		      <input type="hidden" name="lawfulOwnerId" value="${owner.id}" />
		    </div>
		  </div>
		</div>
        <hr class="dark-hr" />							 
      	<div id="formInfo" class="row ufields">	
      	
		  <div class="row top-buffer">	  
		    <div class="col-xs-4 col-md-3">
			  <input type="text" name="claimAmount" class="form-control" placeholder="Claim Amount" value="${formData.trustAmount}" />			  	  
			</div>
		  </div>
		  <div class="row top-buffer">	  
		    <div class="col-xs-4 col-md-3 input-group date" id="datepicker">
			  <input type="text" name="claimDate" class="form-control" placeholder="Claim Date" value="${formData.claimSubmitDate}" />	
			  <span class="input-group-addon">
                <span class="glyphicon glyphicon-calendar"></span>
              </span>		  		  	  
			</div>
		  </div>		        	
		  <div class="row top-buffer">	  
		    <div class="col-xs-8 col-md-6">
			  <input type="radio" name="reason" id="signatureMissing" class="form-check-input" value="${sigMissing}" ${formData.rejectReason == sigMissing ? 'checked="checked"' : ''}/>
			  <label for="signatureMissing" class="col-lg-4 col-form-label">Affirmation signature is missing</label>	  
			</div>
		  </div>
		  <div class="row top-buffer">	  
		    <div class="col-xs-8 col-md-6">
			  <input type="radio" name="reason" id="notarizationMissing" class="form-check-input" value="${notarizationMissing}" ${formData.rejectReason == notarizationMissing ? 'checked="checked"' : ''} />
			  <label for="notarizationMissing" class="col-lg-4 col-form-label">Notarization is missing</label>	  
			</div>
		  </div>		  
		  <div class="row top-buffer">	  
		    <div class="col-xs-8 col-md-6">
			  <input type="radio" name="reason" id="validIdMissing" class="form-check-input" value="${photoIdMissing}" ${formData.rejectReason == photoIdMissing ? 'checked="checked"' : ''} />
			  <label for="validIdMissing" class="col-lg-4 col-form-label">Photocopy of current valid photo ID is missing</label>	  
			</div>
		  </div>		  
		  <div class="row top-buffer">	  
		    <div class="col-xs-8 col-md-6">
			  <input type="radio" name="reason" id="documentationMissing" class="form-check-input" value="${insufficientDocumentation}" ${formData.rejectReason == insufficientDocumentation ? 'checked="checked"' : ''} />
			  <label for="documentationMissing" class="col-lg-4 col-form-label">Missing or insufficient documentation</label>	  
			</div>
		  </div>		  
		  <div class="row top-buffer">	  
		    <div class="col-xs-8 col-md-6">
			  <input type="radio" name="reason" id="other" class="form-check-input" value="${other}" ${formData.rejectReason == other ? 'checked="checked"' : ''} />
			  <label for="other" class="col-lg-4 col-form-label">Other:</label>	 
			  <input type="text" name="otherReason" class="form-control" placeholder="Other Reason" value="${formData.otherRejectReason}" /> 
			</div>
		  </div>		  
		</div>
	  </div>
	  	
	  <div class="row top-buffer">
	    <div class="col-lg-6">	  	
	    	<div class="pull-right">  				
  				<!-- <input type="submit" id="review-form" name="review-form" class="btn btn-primary" value="Review Form" /> -->
  				<button id="review-form" name="review-form" class="btn btn-primary" type="submit""><span class="glyphicon glyphicon-edit review-form-btn"></span>&nbsp; Review Form</button>
  			</div>
  		</div>
  	  </div>	  		

	</div>
	</form>

</div>

</body>
</html>