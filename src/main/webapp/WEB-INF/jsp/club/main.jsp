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
				<c:forEach var="meeting" items="${meetingList}">
					<div class="col-lg-6 col-md-6">
						<div class="meeting-panel panel panel-default">
							<input id="meeting-id" type="hidden" value="${meeting.id}" />
							<div class="panel-body">
								<div class="row">
									<!-- <i class="fa fa-comments fa-5x"></i> -->
									<div id="title-panel" class="text-primary">
										<strong><span id="title"><c:out value="${meeting.title}"/></span><span id="quota">(${meeting.quota})</span></strong>
									</div>
									<div class="col-xs-3 meeting-panel-left">
										<div class="meeting-date">
											<div class="day-of-the-week">목요일</div>
											<div class="day huge">21</div>
										</div>
									</div>
									<div class="col-xs-6 meeting-panel-center">
										<time datetime="2016-04-21" class="meeting-date-time">
											<div class="date-wrap"><i class="fa fa-clock-o fa-fw"></i> <span id="meetingDate">${meeting.meetingDate}</span></div>
											<div class="place-wrap"><i class="fa fa-map-marker fa-fw"></i><span id="place"><c:out value="${meeting.place}"/></span></div>
											<div class="costs-wrap"><i class="fa fa-usd fa-fw"></i><span id="attendCosts"><c:out value="${meeting.attendCosts}"/></span></div>
										</time>
									</div>
									<div class="col-xs-3 text-right">
										<div>
											<sec:authorize access="hasPermission(#club,'READ') and !hasPermission(#meeting,'READ')">
												<button id="attach-meeting"
													class="btn btn-primary attach-meeting meeting-btn">참석</button>
											</sec:authorize>
											<sec:authorize access="hasPermission(#club,'READ') and hasPermission(#meeting,'READ')">
												<button id="detach-meeting"
													class="btn btn-default detach-meeting meeting-btn">참석취소</button>
											</sec:authorize>
										</div>
									</div>
								</div>
							</div>
							<a id="meeting-view-detail" class="meeting-view-detail">
								<div class="panel-footer">
									<span class="pull-left">자세히 보기</span> <span
										class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
									<div class="clearfix"></div>
								</div>
							</a>
							<!-- /.panel-heading -->
							<div class="panel-body">
								<ul class="members">
								</ul>
							</div>
						</div>
					</div>
					<!-- /.col-lg-6 .col-md-6-->
				</c:forEach>
			</div>
			<!-- /.row-->
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
							<div id="notice"><c:out value="${club.notice}"/></div>
						</div>
						<!-- /.panel-body -->
					</div>
					<!-- /.panel -->
				</div>
				<!-- /.col-lg-8 -->
				<div class="col-lg-12">
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
												<strong class="primary-font"><c:out value="${joiningMember.name}"/></strong> 
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
	<div style="display: none" class="attach-member-component">
		<li class="left clearfix"><span class="members-img pull-left">
				<img id="profileImage" src="http://placehold.it/50/55C1E7/fff"
				alt="User Avatar" class="img-circle" />
		</span>
			<div class="members-body clearfix">
				<div class="header">
					<strong class="primary-font"><span id="name"></span></strong> <small
						class="pull-right text-muted"> <i
						class="	fa fa-birthday-cake fa-fw"></i> <span id="birthday">${formattedDate}</span>
					</small> <strong class="primary-font text-primary">모임장</strong>
				</div>
				<p>
					<span id="description"></span>
				</p>
			</div></li>
	</div>

	<%@ include file="/WEB-INF/jspf/footer.jspf"%>
	 <!-- Script to Activate the Carousel -->
    <script type="text/javascript">
	    $(function() {
	        $('.carousel').carousel({
	            interval: 5000 //changes the speed
	        });
	        
	        $('.meeting-btn').on('click', function(event) {
	        	event.preventDefault();
	        	var button = $(event.target);
	        	var meetingId = button.closest('.meeting-panel').find('#meeting-id').val();
				var url = null;
				
				if (button.hasClass('attach-meeting')) {
					url = '/meetings/' + meetingId
							+ '/attach';
				} else {
					url = '/meetings/' + meetingId
							+ '/detach';
				}
				$.ajax({
					url : url,
					type : 'GET',
					cache : false,
					success : function(data) {
						//TODO
						if (button.hasClass('attach-meeting')) {
							button.removeClass('attach-meeting');
							button.removeClass('btn-primary');
							button.addClass('detach-meeting');
							button.addClass('btn-default');
							button.text('참석취소');
						} else {
							button.removeClass('detach-meeting');
							button.removeClass('btn-default');
							button.addClass('attach-meeting');
							button.addClass('btn-primary');
							button.text('참석');
						}
						button.closest('.meeting-panel').find('.meeting-view-detail').trigger('click');
					}.bind(this),
					error : function(xhr, status, err) {
						if (console) {
							console.log(xhr);
							console.log(status);
							console.log(err);
						}
					}.bind(this)
				});
			});
	
			$('.meeting-view-detail').on('click', function(event) {
				event.preventDefault();
				//TODO 회원목록 출력
				var view = $(event.target);
	        	var meetingId = view.closest('.meeting-panel').find('#meeting-id').val();
				var url = '/meetings/' + meetingId + '/attachMembers';
				$.ajax({
					url : url,
					type : 'GET',
					cache : false,
					success : function(data) {
						if (data) {
							if(console) {
								console.log(data);
							}

							var attachMembers = data;
							var $memberList = view.closest('.meeting-panel').find('.members');
							$memberList.empty();
							var $attachMemberComponent = $('.attach-member-component');
							attachMembers.forEach(function(member) {
								var $name = $attachMemberComponent.find('#name');
								var $description = $attachMemberComponent.find('#description');
								var $profileImage = $attachMemberComponent.find('#profileImage');
								var $birthday = $attachMemberComponent.find('#birthday');

								$name.text('');
								$description.text('');
								$birthday.text('');

								$name.text(member.name);
								$description.text(member.description);
								if(member.profileImage != null && member.profileImage.id != null) {
									$profileImage.show();
									$profileImage.attr('src', '/upload/images/'+member.profileImage.id);
								} else {
									 $profileImage.removeAttr('src');
									 $profileImage.hide();
								}
								$birthday.text(member.birthday);
								
								$memberList.append($attachMemberComponent.html());
							});
							//$.each(attachMembers, function(index, member) {
								//alert(member);
								/* if(idList.indexOf(album.id) == -1){
									idList.push(album.id);
									var $albumComponent = callback(album);
									$albumList.append($albumComponent.html());									
								} */
							//});
						}
					}.bind(this),
					error : function(xhr, status, err) {
						if (console) {
							console.log(xhr);
							console.log(status);
							console.log(err);
						}
					}.bind(this)
				});
			});
		});
	</script>
</body>
</html>
