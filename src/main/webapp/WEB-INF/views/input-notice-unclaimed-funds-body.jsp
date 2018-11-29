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
    $(document).ready(function () {
        var next = $(".ufields").length;        
        $("#add-more").click(function(e){
            e.preventDefault();            
            var loopCnt = $('#add-more-cnt').val();
            
            loopCnt = isNaN(parseInt(loopCnt)) ? 1 : parseInt(loopCnt);            
            for(var i = 1; i < loopCnt + 1; i++) {            	
	            var addto = "#field" + (next - 1);
	            var addRemove = "#field" + (next - 1);	            
	            var newIn = '<div id="field' + next + '" class="row ufields"><hr class="dark-hr" />' +
            			  		'<div class="row top-buffer">' +
            			    		'<div class="col-xs-6 col-md-4">' +
            			      			'<input name="nameLine1_' + next + '" type="text" class="form-control required nameLine1" placeholder="Lawful Owner Name" value="" />' +
            			      			'<input name="nameLine2_' + next + '" type="text" class="form-control" placeholder="C/O" value="" />' +
            			    		    '<button id="remove_' + next + '" class="btn btn-danger remove-me" style="margin-left:5px;margin-top:10px;">Remove</button>' +
            			      		'</div>' +
            			    		'<div class="col-xs-6 col-md-4">' +
            			      			'<input name="addressLine1_' + next + '" type="text" class="form-control required addressLine1" placeholder="Address Line 1" value="">' +
            			      			'<input name="addressLine2_' + next + '" type="text" class="form-control" placeholder="Address Line 2" value="">' +
            			      			'<input name="addressLine3_' + next + '" type="text" class="form-control required addressLine2" placeholder="City, State ZIP" value="">' +
            			      		'</div>' +
            			  		'</div>' +
	            			  '</div>';
	            var newInput = $(newIn);
	            $(addto).after(newInput);
	            $("#field" + next).attr('data-source',$(addto).attr('data-source'));
	            $("#count").val(next);  
	            $("#remove_" + (next - 1)).hide();
	            next = next + 1;
            }
                 $('.remove-me').click(function(e){
                    e.preventDefault();
                    //var fieldNum = this.id.charAt(this.id.length-1);
                    var fieldNum = this.id.split("_").pop();
                    var fieldID = "#field" + fieldNum;
                    $(this).remove();
                    $(fieldID).removeClass("ufields");
                    $(fieldID).empty();
                    $("#remove_" + (fieldNum - 1)).show();                    
                }); 
        });
        
    });
    
    
    
	function jsonData(){
		
	       var data = $('.row.ufields').map(function(index) {
	           return {
	        	   nameLine1: $(this).find('[name="nameLine1_' + index + '"]').val(),
	        	   nameLine2: $(this).find('[name="nameLine2_' + index + '"]').val(),
	        	   addressLine1: $(this).find('[name="addressLine1_' + index + '"]').val(),
	        	   addressLine2: $(this).find('[name="addressLine2_' + index + '"]').val(),
	        	   addressLine3: $(this).find('[name="addressLine3_' + index + '"]').val(),
	        	   trustId: $(this).find('[name="trustId"]').val()	        	   
	           };
	       }).get();	       
	       document.getElementById('formJSON').value = JSON.stringify(data);
	       
			$("#lawfulOwnerForm")
			.validate(
					{
						/* rules : {
							nameLine1 : "required",
							addressLine1 : "required",
							addressLine3 : "required"
						}, */
						messages : {
							nameLine1: "Please enter a lawful owner name",
							addressLine1 : "Please enter address line 1",
							addressLine3 : "Please enter City, State and Zip"
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
							$('.glyphicon').removeClass("glyphicon-edit");
							$('.glyphicon').addClass("glyphicon-refresh glyphicon-refresh-animate");							
						    form.submit();
						  },
						  
					});  
	       
	}    
    </script>
    
</head>
<body>

<div class="container">
<br>
	<form:form id="lawfulOwnerForm" class="form-inline" modelAttribute="lawfulOwnerForm" method="post" action="review-notice-unclaimed-funds">
	<div class="well">
      <div id="field">
      
        <c:forEach var="items" items="${lawfulOwner}" varStatus="loop">
      	<div id="field${loop.index}" class="row ufields">	
		  <div class="row top-buffer">
		    <div class="col-xs-6 col-md-4">
		      <input name="nameLine1_${loop.index}" type="text" class="form-control required nameLine1" placeholder="Lawful Owner Name" value="${items.nameLine1}" />
		      <input name="nameLine2_${loop.index}" type="text" class="form-control" placeholder="C/O" value="${items.nameLine2}" />
		    </div>
		    <div class="col-xs-6 col-md-4">
		      <input name="addressLine1_${loop.index}" type="text" class="form-control required addressLine1" placeholder="Address Line 1" value="${items.addressLine1}">
		      <input name="addressLine2_${loop.index}" type="text" class="form-control" placeholder="Address Line 2" value="${items.addressLine2}">
		      <input name="addressLine3_${loop.index}" type="text" class="form-control required addressLine2" placeholder="City, State ZIP" value="${items.addressLine3}">
		      <input type="hidden" name="trustId" value="${param.trustId}" />
		    </div>
		  </div>
		</div>
        <c:if test="${!loop.last}"><hr class="dark-hr" /></c:if>
		</c:forEach>							 
	  
	  </div>
	  	
	  <div class="row top-buffer">
	    <div class="col-xs-6 col-md-4">	 
	    	<div class="col-xs-3">
	    		<input type="text" name="add-more-cnt" id="add-more-cnt" size="2" class="form-control"/> 
	    	</div>	
  			<button id="add-more" name="add-more" class="btn btn-default add-more" type="button">+</button>
  		</div>
	    <div class="col-xs-6 col-md-4">	  	
	    	<div class="pull-right">  				
  				<input type="hidden" id="formJSON" name="formJSON" value="" />
  				<!-- <input type="submit" id="review-form" name="review-form" class="btn btn-primary" value="Review Form(s)" onclick="jsonData();" /> -->
  				<br />
  				<button id="review-form" name="review-form" class="btn btn-primary" type="submit" onclick="jsonData();"><span class="glyphicon glyphicon-edit"></span>&nbsp; Review Form(s)</button>
  			</div>
  		</div>
  	  </div>	  		
	  		
	  <input type="hidden" name="count" value="1" />
	  </div>
	</form:form>

</div>

</body>
</html>