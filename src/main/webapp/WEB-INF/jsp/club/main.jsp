<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<%@ include file="/WEB-INF/jspf/head.jspf"%>
</head>
<body>
	<div id="wrapper">
		<!-- Navigation -->
		<nav class="navbar navbar-default navbar-static-top" role="navigation"
			style="margin-bottom: 0">
		<%@ include file="/WEB-INF/jspf/navtop.jspf"%>
		<%@ include file="/WEB-INF/jspf/navside.jspf"%>
		</nav>
		<div id="page-wrapper">
			<c:if test="${club.mainImage.id != null}">
				<!-- Half Page Image Background Carousel Header -->
				<div id="main-carousel" class="carousel slide">
					<!-- Indicators -->
					<ol class="carousel-indicators">
						<li data-target="#main-carousel" data-slide-to="0" class="active"></li>
						<li data-target="#main-carousel" data-slide-to="1"></li>
						<li data-target="#main-carousel" data-slide-to="2"></li>
					</ol>

					<!-- Wrapper for Slides -->
					<div class="carousel-inner">
						<div class="item active">
							<!-- Set the first background image using inline CSS below. -->
							<div class="fill"
								style="background-image: url('/upload/images/${club.mainImage.id}');"></div>
							<div class="carousel-caption">
								<h2>Caption 1</h2>
							</div>
						</div>
						<!-- <div class="item">
						Set the second background image using inline CSS below.
						<div class="fill"
							style="background-image: url('http://placehold.it/1900x1080&amp;text=Slide Two');"></div>
						<div class="carousel-caption">
							<h2>Caption 2</h2>
						</div>
					</div>
					<div class="item">
						Set the third background image using inline CSS below.
						<div class="fill"
							style="background-image: url('http://placehold.it/1900x1080&amp;text=Slide Three');"></div>
						<div class="carousel-caption">
							<h2>Caption 3</h2>
						</div>
					</div> -->
					</div>

					<!-- Controls -->
					<a class="left carousel-control" href="#main-carousel"
						data-slide="prev"> <span class="icon-prev"></span>
					</a> <a class="right carousel-control" href="#main-carousel"
						data-slide="next"> <span class="icon-next"></span>
					</a>
				</div>
			</c:if>
			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header">${club.title}</h1>
				</div>
				<!-- /.col-lg-12 -->
			</div>
			<!-- /.row -->
			
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default notice-panel">
						<div class="panel-heading">
							<i class="fa fa-bar-chart-o fa-fw"></i> 공지사항
							<div class="pull-right">
								<div class="btn-group">
									<button type="button"
										class="btn btn-default btn-xs dropdown-toggle"
										data-toggle="dropdown">
										Actions <span class="caret"></span>
									</button>
									<ul class="dropdown-menu pull-right" role="menu">
										<li><a href="#">Action</a></li>
										<li><a href="#">Another action</a></li>
										<li><a href="#">Something else here</a></li>
										<li class="divider"></li>
										<li><a href="#">Separated link</a></li>
									</ul>
								</div>
							</div>
						</div>
						<!-- /.panel-heading -->
						<div class="panel-body">
							<div id="notice">${club.notice}</div>
						</div>
						<!-- /.panel-body -->
					</div>
					<!-- /.panel -->
				</div>
				<!-- /.col-lg-8 -->
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading">
							<i class="fa fa-bell fa-fw"></i> Notifications Panel
						</div>
						<!-- /.panel-heading -->
						<div class="panel-body">
							<div class="list-group">
								<a href="#" class="list-group-item"> <i
									class="fa fa-comment fa-fw"></i> New Comment <span
									class="pull-right text-muted small"><em>4 minutes
											ago</em> </span>
								</a> <a href="#" class="list-group-item"> <i
									class="fa fa-twitter fa-fw"></i> 3 New Followers <span
									class="pull-right text-muted small"><em>12 minutes
											ago</em> </span>
								</a> <a href="#" class="list-group-item"> <i
									class="fa fa-envelope fa-fw"></i> Message Sent <span
									class="pull-right text-muted small"><em>27 minutes
											ago</em> </span>
								</a> <a href="#" class="list-group-item"> <i
									class="fa fa-tasks fa-fw"></i> New Task <span
									class="pull-right text-muted small"><em>43 minutes
											ago</em> </span>
								</a> <a href="#" class="list-group-item"> <i
									class="fa fa-upload fa-fw"></i> Server Rebooted <span
									class="pull-right text-muted small"><em>11:32 AM</em> </span>
								</a> <a href="#" class="list-group-item"> <i
									class="fa fa-bolt fa-fw"></i> Server Crashed! <span
									class="pull-right text-muted small"><em>11:13 AM</em> </span>
								</a> <a href="#" class="list-group-item"> <i
									class="fa fa-warning fa-fw"></i> Server Not Responding <span
									class="pull-right text-muted small"><em>10:57 AM</em> </span>
								</a> <a href="#" class="list-group-item"> <i
									class="fa fa-shopping-cart fa-fw"></i> New Order Placed <span
									class="pull-right text-muted small"><em>9:49 AM</em> </span>
								</a> <a href="#" class="list-group-item"> <i
									class="fa fa-money fa-fw"></i> Payment Received <span
									class="pull-right text-muted small"><em>Yesterday</em> </span>
								</a>
							</div>
							<!-- /.list-group -->
							<a href="#" class="btn btn-default btn-block">View All Alerts</a>
						</div>
						<!-- /.panel-body -->
					</div>
					<!-- /.panel -->
					<div class="chat-panel panel panel-default">
						<div class="panel-heading">
							<i class="fa fa-comments fa-fw"></i> Chat
							<div class="btn-group pull-right">
								<button type="button"
									class="btn btn-default btn-xs dropdown-toggle"
									data-toggle="dropdown">
									<i class="fa fa-chevron-down"></i>
								</button>
								<ul class="dropdown-menu slidedown">
									<li><a href="#"> <i class="fa fa-refresh fa-fw"></i>
											Refresh
									</a></li>
									<li><a href="#"> <i class="fa fa-check-circle fa-fw"></i>
											Available
									</a></li>
									<li><a href="#"> <i class="fa fa-times fa-fw"></i>
											Busy
									</a></li>
									<li><a href="#"> <i class="fa fa-clock-o fa-fw"></i>
											Away
									</a></li>
									<li class="divider"></li>
									<li><a href="#"> <i class="fa fa-sign-out fa-fw"></i>
											Sign Out
									</a></li>
								</ul>
							</div>
						</div>
						<!-- /.panel-heading -->
						<div class="panel-body">
							<ul class="chat">
								<li class="left clearfix"><span class="chat-img pull-left">
										<img src="http://placehold.it/50/55C1E7/fff" alt="User Avatar"
										class="img-circle" />
								</span>
									<div class="chat-body clearfix">
										<div class="header">
											<strong class="primary-font">Jack Sparrow</strong> <small
												class="pull-right text-muted"> <i
												class="fa fa-clock-o fa-fw"></i> 12 mins ago
											</small>
										</div>
										<p>Lorem ipsum dolor sit amet, consectetur adipiscing
											elit. Curabitur bibendum ornare dolor, quis ullamcorper
											ligula sodales.</p>
									</div></li>
								<li class="right clearfix"><span
									class="chat-img pull-right"> <img
										src="http://placehold.it/50/FA6F57/fff" alt="User Avatar"
										class="img-circle" />
								</span>
									<div class="chat-body clearfix">
										<div class="header">
											<small class=" text-muted"> <i
												class="fa fa-clock-o fa-fw"></i> 13 mins ago
											</small> <strong class="pull-right primary-font">Bhaumik
												Patel</strong>
										</div>
										<p>Lorem ipsum dolor sit amet, consectetur adipiscing
											elit. Curabitur bibendum ornare dolor, quis ullamcorper
											ligula sodales.</p>
									</div></li>
								<li class="left clearfix"><span class="chat-img pull-left">
										<img src="http://placehold.it/50/55C1E7/fff" alt="User Avatar"
										class="img-circle" />
								</span>
									<div class="chat-body clearfix">
										<div class="header">
											<strong class="primary-font">Jack Sparrow</strong> <small
												class="pull-right text-muted"> <i
												class="fa fa-clock-o fa-fw"></i> 14 mins ago
											</small>
										</div>
										<p>Lorem ipsum dolor sit amet, consectetur adipiscing
											elit. Curabitur bibendum ornare dolor, quis ullamcorper
											ligula sodales.</p>
									</div></li>
								<li class="right clearfix"><span
									class="chat-img pull-right"> <img
										src="http://placehold.it/50/FA6F57/fff" alt="User Avatar"
										class="img-circle" />
								</span>
									<div class="chat-body clearfix">
										<div class="header">
											<small class=" text-muted"> <i
												class="fa fa-clock-o fa-fw"></i> 15 mins ago
											</small> <strong class="pull-right primary-font">Bhaumik
												Patel</strong>
										</div>
										<p>Lorem ipsum dolor sit amet, consectetur adipiscing
											elit. Curabitur bibendum ornare dolor, quis ullamcorper
											ligula sodales.</p>
									</div></li>
							</ul>
						</div>
						<!-- /.panel-body -->
						<div class="panel-footer">
							<div class="input-group">
								<input id="btn-input" type="text" class="form-control input-sm"
									placeholder="Type your message here..." /> <span
									class="input-group-btn">
									<button class="btn btn-warning btn-sm" id="btn-chat">
										Send</button>
								</span>
							</div>
						</div>
						<!-- /.panel-footer -->
					</div>
					<!-- /.panel .chat-panel -->
				</div>
				<!-- /.col-lg-4 -->
			</div>
			<!-- /.row -->
		</div>
		<!-- /#page-wrapper -->
	</div>
	<!-- /#wrapper -->

	<%@ include file="/WEB-INF/jspf/footer.jspf"%>
	 <!-- Script to Activate the Carousel -->
    <script>
    $('.carousel').carousel({
        interval: 5000 //changes the speed
    })
    </script>
</body>
</html>
