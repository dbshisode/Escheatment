<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

  <div class="row">
    <div class="col-sm-10" id="logout">
        <div class="comment-tabs">
            <ul class="nav nav-tabs" role="tablist">
                <li class="active"><a href="#comments-logout" role="tab" data-toggle="tab"><h4 class="reviews text-capitalize">Comments</h4></a></li>
                <li><a href="#add-comment" role="tab" data-toggle="tab"><h4 class="reviews text-capitalize">Add comment</h4></a></li>                
            </ul>            
            <div class="tab-content">
                <div class="tab-pane active" id="comments-logout">                
                    <ul class="media-list">
                      <li class="media">
                        <a class="pull-left" href="#">
                          <img class="media-object img-circle" src="<c:url value="/resources/images/operations_bubble.gif" />" alt="profile">
                        </a>
                        <div class="media-body">
                          <div class="well well-lg">
                              <h4 class="media-heading text-uppercase reviews">Janay Marks </h4>
                              <ul class="media-date text-uppercase reviews list-inline">
                                <li class="dd">08</li>
                                <li class="mm">27</li>
                                <li class="aaaa">2018</li>
                              </ul>
                              <p class="media-comment">
                                Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.
                              </p>
                          </div>              
                        </div>
                      </li> 
                      <li class="media">
                        <a class="pull-left" href="#">
                          <img class="media-object img-circle" src="<c:url value="/resources/images/accounting_bubble.gif" />" alt="profile">
                        </a>
                        <div class="media-body">
                          <div class="well well-lg">
                              <h4 class="media-heading text-uppercase reviews">Susan Gnesda</h4>
                              <ul class="media-date text-uppercase reviews list-inline">
                                <li class="dd">08</li>
                                <li class="mm">28</li>
                                <li class="aaaa">2018</li>
                              </ul>
                              <p class="media-comment">
                                Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
                              </p>
                          </div>              
                        </div>
                      </li> 
                      <li class="media">
                        <a class="pull-left" href="#">
                          <img class="media-object img-circle" src="<c:url value="/resources/images/operations_bubble.gif" />" alt="profile">
                        </a>
                        <div class="media-body">
                          <div class="well well-lg">
                              <h4 class="media-heading text-uppercase reviews">Janay Marks </h4>
                              <ul class="media-date text-uppercase reviews list-inline">
                                <li class="dd">08</li>
                                <li class="mm">27</li>
                                <li class="aaaa">2018</li>
                              </ul>
                              <p class="media-comment">
                                Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.
                              </p>
                          </div>              
                        </div>
                      </li> 
                      <li class="media">
                        <a class="pull-left" href="#">
                          <img class="media-object img-circle" src="<c:url value="/resources/images/accounting_bubble.gif" />" alt="profile">
                        </a>
                        <div class="media-body">
                          <div class="well well-lg">
                              <h4 class="media-heading text-uppercase reviews">Susan Gnesda</h4>
                              <ul class="media-date text-uppercase reviews list-inline">
                                <li class="dd">08</li>
                                <li class="mm">28</li>
                                <li class="aaaa">2018</li>
                              </ul>
                              <p class="media-comment">
                                Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
                              </p>
                          </div>              
                        </div>
                      </li>
                    </ul> 
                </div>
                <div class="tab-pane" id="add-comment">
                    <form action="#" method="post" class="form-horizontal" id="commentForm" role="form"> 
                        <div class="form-group">
                            <label for="email" class="col-sm-2 control-label">Comment</label>
                            <div class="col-sm-10">
                              <textarea class="form-control" name="addComment" id="addComment" rows="5"></textarea>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-offset-2 col-sm-10">                    
                                <button class="btn btn-success btn-circle text-uppercase" type="submit" id="submitComment"><span class="glyphicon glyphicon-send"></span> Summit comment</button>
                            </div>
                        </div>            
                    </form>
                </div>
            </div>
        </div>
	</div>
  </div>
