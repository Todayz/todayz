<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
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
					<h1 class="page-header"><c:out value="${club.title}"/></h1>
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

					<div class="members-panel panel panel-default">
						<div class="panel-heading">
							<i class="fa fa-comments fa-fw"></i> 가입한 멤버 <strong
								class="primary-font text-primary">${club.joiningMembers.size()}명</strong>
						</div>
						<!-- /.panel-heading -->
						<div class="panel-body">
							<ul class="members">
								<c:forEach var="joiningMember" items="${club.joiningMembers}">
									<li class="left clearfix"><span class="members-img pull-left"> 
									<fmt:formatDate
												value="${joiningMember.birthday}" var="formattedDate" type="date"
												pattern="yyyy-MM-dd" /> 
									<c:choose>
										<c:when test="${joiningMember.profileImage.id != null}">
											<img alt=""
												src="/upload/images/${joiningMember.profileImage.id}"
												style="width: 50px; height: 50px;" class="img-circle">
										</c:when>
										<c:otherwise>
											<img src="http://placehold.it/50/55C1E7/fff"
												alt="User Avatar" class="img-circle" />
										</c:otherwise>
									</c:choose>
									</span>
										<div class="members-body clearfix">
											<div class="header">
												<strong class="primary-font">${joiningMember.name}</strong> 
												<small
													class="pull-right text-muted"> <i
													class="	fa fa-birthday-cake fa-fw"></i> ${formattedDate}
												</small>
												<c:if test="${joiningMember.authName == club.owner.authName}">
													<strong class="primary-font text-primary">모임장</strong> 
												</c:if>
											</div>
											<p><c:out value="${joiningMember.description}"/></p>
										</div></li>
								</c:forEach>
							</ul>
						</div>
						<!-- /.panel-body -->
					</div>
					<!-- /.panel .members-panel -->
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
