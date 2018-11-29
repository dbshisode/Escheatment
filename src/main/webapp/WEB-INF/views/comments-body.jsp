<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

  <div id="comments" class="row">
    <div class="col-sm-10" id="logout">
        <div class="comment-tabs">
            <ul class="nav nav-tabs" role="tablist">
                <c:choose>
                   <c:when test="${fn:length(comments) == 0}">
                   	  <li class="active"><a href="#add-comment" role="tab" data-toggle="tab"><h4 class="reviews text-capitalize">Add comment</h4></a></li>                   	  
                      <c:set var="active" scope="page" value="active"/>                 
                   </c:when>
                   <c:when test="${fn:length(comments) > 0 && param.viewAll == false}">
					  <li><a href="#comments-logout" role="tab" data-toggle="tab"><h4 class="reviews text-capitalize">Comments</h4></a></li>
                   	  <li class="active"><a href="#add-comment" role="tab" data-toggle="tab"><h4 class="reviews text-capitalize">Add comment</h4></a></li>
                      <c:set var="active" scope="page" value=""/>
                   </c:when>                   
			       <c:otherwise>
                   	  <li class="active"><a href="#comments-logout" role="tab" data-toggle="tab"><h4 class="reviews text-capitalize">Comments</h4></a></li>
                   	  <li><a href="#add-comment" role="tab" data-toggle="tab"><h4 class="reviews text-capitalize">Add comment</h4></a></li>			          
			          <c:set var="active" scope="page" value="active"/>
			        </c:otherwise>
			    </c:choose>                                 
            </ul>                        
            <div class="tab-content">
                <c:if test="${fn:length(comments) > 0}">
	                <div class="tab-pane <c:out value = "${active}"/>" id="comments-logout">                
	                    <ul class="media-list">
	                      <c:forEach var="items" items="${comments}" varStatus="loop">
		                      <li class="media">
		                        <a class="pull-left" href="#">
									<c:choose>
									    <c:when test="${items.userCommentRole.intValue() == opsRoleValue}">
									    	<img class="media-object img-circle" src="<c:url value="/resources/images/operations_bubble.gif" />" alt="profile">
									    </c:when>
									    <c:otherwise>
									        <img class="media-object img-circle" src="<c:url value="/resources/images/accounting_bubble.gif" />" alt="profile">
									    </c:otherwise>
									</c:choose>    	                          
		                        </a>
		                        <div class="media-body">
		                          <div class="well well-lg">
		                              <h4 class="media-heading text-uppercase reviews"><c:out value="${items.userName}" /></h4>
		                              <ul class="media-date text-uppercase reviews list-inline">
		                                <li class="dd"><fmt:formatDate pattern="M/d/yy, kk:mm aa" value="${items.commentDate}" /></li>
		                              </ul>
		                              <p class="media-comment">
		                                <c:out value="${items.commentText}" />
		                              </p>
		                          </div>              
		                        </div>
		                      </li> 
	                      </c:forEach>
	                    </ul> 
	                </div>
                </c:if>
                
                <c:choose>
                   <c:when test="${fn:length(comments) == 0 || param.viewAll == false}">
                   	  <div class="tab-pane active" id="add-comment">
                   </c:when>
			       <c:otherwise>
			          <div class="tab-pane" id="add-comment">
			        </c:otherwise>
			    </c:choose> 
  
                   <form:form modelAttribute="comment" action="comments-save" method="post" class="form-horizontal" name="commentForm" id="commentForm" role="form"> 
                       <div class="form-group">
                           <label for="commentText" class="col-sm-2 control-label">Comment</label>
                           <div class="col-sm-10">
                             <textarea path="commentText" class="form-control" name="commentText" id="commentText" rows="5"></textarea>
                           </div>
                       </div>
                       <div class="form-group">
                           <div class="col-sm-offset-2 col-sm-10">                    
                               <form:hidden path="trustId" name="trustId" id="trustId" value="${param.trustId}" />
                               <button class="btn btn-success btn-circle text-uppercase" type="submit" id="submitComment"><span class="glyphicon glyphicon-send"></span> Summit comment</button>
                           </div>
                       </div>            
                   </form:form>
                </div>
            </div>
        </div>
	</div>
  </div>

  <script type="text/javascript">
	$(document)
			.ready(
					function() {
						$("#commentForm")
								.validate(
										{
											rules : {
												commentText : "required"
											},
											messages : {
												commentText : "Please enter a comment"
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
															url : "comments-save",
															type : 'POST',
															//contentType: 'application/json',
															data : $(
																	'#commentForm')
																	.serialize(),
															success : function(
																	data) {
																
																var trustId = $('#trustId').val();

																$.get('comments?trustId=' + trustId, function(html) { 
																	    $('#comments').html(html).modal(); 
																		//$(html).appendTo('comments').modal(); 
																	    }); 
															}
														});

												return false;
											}
										});
					});
</script>
