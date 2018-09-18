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
        var next = 3;
        $("#add-more").click(function(e){
            e.preventDefault();            
            var loopCnt = $('#add-more-cnt').val();
            
            loopCnt = isNaN(parseInt(loopCnt)) ? 1 : parseInt(loopCnt);
			
            for(var i = 0; i < loopCnt; i++) {
	            var addto = "#field" + next;
	            var addRemove = "#field" + (next);
	            next = next + 1;
	            var newIn = '<hr class="dark-hr" /><div id="field' + next + '"> ' +
	            			'	<div class="row top-buffer">' +
	            			'   	<div class="col-xs-6 col-md-4">' +
	            			'			<input type="text" name="owner_name' + next + '" id="owner_name' + next + '" class="form-control" placeholder="Lawful Owner name" value="">' +
	            			'       </div>' +
	            			'   	<div class="col-xs-6 col-md-4">' +
	            			'			<input type="text" name="owner_addr' + next + '" id="owner_addr_line1_' + next + '" class="form-control" placeholder="Lawful Owner address" value="">' +
	            			'			<input type="text" name="owner_addr' + next + '" id="owner_addr_line2_' + next + '" class="form-control" placeholder="Lawful Owner address" value="">' +
	            			'		</div>' +
	            			'	</div>' +
	            			'</div>';
	            var newInput = $(newIn);
	            //var removeBtn = '<button id="remove' + (next - 1) + '" class="btn btn-danger remove-me" >Remove</button></div></div><div id="field">';
	            var removeButton = '';
	            $(addto).after(newInput);
	            $(addRemove).after(removeButton);
	            $("#field" + next).attr('data-source',$(addto).attr('data-source'));
	            $("#count").val(next);  
            }
                /* $('.remove-me').click(function(e){
                    e.preventDefault();
                    var fieldNum = this.id.charAt(this.id.length-1);
                    var fieldID = "#field" + fieldNum;
                    $(this).remove();
                    $(fieldID).remove();
                }); */
        });

    });
    </script>
    
</head>
<body>

<div class="container">
<br>
	<form class="form-inline" method="post" action="review-notice-unclaimed-funds">
	<div class="well">
      <div id="field">
      	<div id="field0">	
		  <div class="row top-buffer">
		    <div class="col-xs-6 col-md-4">
		      <input type="text" id="owner_name0" class="form-control" placeholder="Lawful Owner name" value="">
		    </div>
		    <div class="col-xs-6 col-md-4">
		      <input type="text" id="owner_addr_line1_0" class="form-control" placeholder="Lawful Owner address" value="">
		      <input type="text" id="owner_addr_line2_0" class="form-control" placeholder="Lawful Owner address" value="">
		    </div>
		  </div>
		</div>
		<hr class="dark-hr" />
		<div id="field1">	
		  <div class="row top-buffer">
		    <div class="col-xs-6 col-md-4">
		      <input type="text" id="owner_name1" class="form-control" placeholder="Lawful Owner name" value="">
		    </div>
		    <div class="col-xs-6 col-md-4">
		      <input type="text" id="owner_addr_line1_1" class="form-control" placeholder="Lawful Owner address" value="">
		      <input type="text" id="owner_addr_line2_1" class="form-control" placeholder="Lawful Owner address" value="">
		    </div>
		  </div>
		</div>
		<hr class="dark-hr" />
		<div id="field2">	
		  <div class="row top-buffer">
		    <div class="col-xs-6 col-md-4">
		      <input type="text" id="owner_name2" class="form-control" placeholder="Lawful Owner name" value="">
		    </div>
		    <div class="col-xs-6 col-md-4">
		      <input type="text" id="owner_addr_line1_2" class="form-control" placeholder="Lawful Owner address" value="">
		      <input type="text" id="owner_addr_line2_2" class="form-control" placeholder="Lawful Owner address" value="">
		    </div>
		  </div>
		</div>
		<hr class="dark-hr" />
		<div id="field3">	
		  <div class="row top-buffer">
		    <div class="col-xs-6 col-md-4">
		      <input type="text" id="owner_name3" class="form-control" placeholder="Lawful Owner name" value="">
		    </div>
		    <div class="col-xs-6 col-md-4">
		      <input type="text" id="owner_addr_line1_3" class="form-control" placeholder="Lawful Owner address" value="">
		      <input type="text" id="owner_addr_line2_3" class="form-control" placeholder="Lawful Owner address" value="">
		    </div>
		  </div>
		</div>								
	  </div>	
	  
	  <div class="row top-buffer"></div> 
	  	
	  <div class="row top-buffer">
	    <div class="col-xs-6 col-md-4">	 
	    	<div class="col-xs-3">
	    		<input type="text" name="add-more-cnt" id="add-more-cnt" size="2" class="form-control"/> 
	    	</div>	
  			<button id="add-more" name="add-more" class="btn btn-default add-more" type="button">+</button>
  		</div>
	    <div class="col-xs-6 col-md-4">	  	
	    	<div class="pull-right">
  				<!--<button id="review-form" name="review-form" class="btn btn-primary" type="button">Review Form(s)</button>-->
  				<input type="submit" id="review-form" name="review-form" class="btn btn-primary" value="Review Form(s)" />
  			</div>
  		</div>
  	  </div>	  		
	  		
	  <input type="hidden" name="count" value="1" />
	  </div>
	</form>

</div>

</body>
</html>