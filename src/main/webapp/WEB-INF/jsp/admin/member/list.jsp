<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<%@ include file="/WEB-INF/jspf/head.jspf"%>
<title>매일 만나는 동호회 Todays</title>
</head>
<body>
	<div id="wrapper">
		<!-- Navigation -->
		<nav class="navbar navbar-default navbar-static-top" role="navigation"
			style="margin-bottom: 0">
			<%@ include file="/WEB-INF/jspf/navtop.jspf"%>
		</nav>
		<div id="page-wrapper" class="no-sidenav">
			<div id="member-list" class="container member-list"></div>
		</div>
		<!-- /#page-wrapper -->
	</div>
	<!-- /#wrapper -->
	<!-- 좀 더 좋은 방법으로 변경가능한지 고민 필요. -->
	<!-- member list -->
	<div style="display: none" class="member-component">
		<div class="row">
			<div class="col-xs-12 col-sm-6 col-md-6">
				<div class="well well-sm">
					<div class="row">
						<div class="col-sm-6 col-md-4">
							<!-- profile image -->
							<img id="profileImage" src="http://placehold.it/380x500" alt=""
								class="img-rounded img-responsive" />
						</div>
						<div class="col-sm-6 col-md-8">
							<!-- name -->
							<h4 id="name">임정묵</h4>
							<small><cite title="Seoul, KOREA">Seoul, KOREA <i
									class="glyphicon glyphicon-map-marker"> </i>
							</cite></small>
							<p>
								<i class="glyphicon glyphicon-phone"></i><span id="phoneNumber">010-8791-1883</span>
								<br /> <i class="glyphicon glyphicon-comment"></i><span
									id="description">안녕하세요~!</span> <br /> <i
									class="glyphicon glyphicon-gift"></i><span id="birthday">1986-08-30</span>
								<br /> <i class="glyphicon glyphicon-info-sign"></i><span
									id="joinDate">2015-08-30</span>
							</p>


							<!-- Split button -->
							<div class="btn-group">
								<button type="button" class="btn btn-primary">크게보기</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="/WEB-INF/jspf/footer.jspf"%>
	<script type="text/javascript">
		$(function() {
			//?page=0&size=20;
			var pageable = {
				page: 0,
				size: 10
			};
			// 내보낸 화면의 아이디들은 여기에 보관.
			//보관되어진 id에 해당하는 데이터는 화면에 뿌려지지 않도록 하기 위함.
			var idList = [];
			// ajax 요청을 받는동안 또다른 요청을 하지 않기 위함.
			var requesting = false;

			// 공통화 필요.
			var getMembers = function(callback) {
				requesting = true;
				var url = '/members';
				var $lodingImg = $('#loader');
				//TODO 변경 필요 (hard coding).
				$lodingImg.show().css({
					'top' : $(document).height() - 126 + 'px',
					'left' : '45%'
				});

				$.ajax({
					url : url,
					type : 'GET',
					cache : false,
					data: pageable,
					success : function(data) {
						if (data) {
							if(console) {
								console.log(data);
							}
							//if(data.numberOfElements === data.size) {
							if(!data.last) {
								pageable.page += 1;
							}

							var members = data.content;
							var $memberList = $('#member-list');
							$.each(members, function(index, member) {
								if(idList.indexOf(member.id) == -1){
									idList.push(member.id);
									var $memberComponent = callback(member);
									$memberList.append($memberComponent.html());									
								}
								$("#loader").hide();
							});
							requesting = false;
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
			};

			var memberComponent = function(member) {
				var $memberComponent = $('.member-component .row');
				var $authName = $memberComponent.find('#authName');
				var $name = $memberComponent.find('#name');
				var $description = $memberComponent.find('#description');
				var $phoneNumber = $memberComponent.find('#phoneNumber');
				var $profileImage = $memberComponent.find('#profileImage');
				var $birthday = $memberComponent.find('#birthday');
				var $joinDate = $memberComponent.find('#joinDate');

				$authName.text('');
				$name.text('');
				$description.text('');
				$phoneNumber.text('');
				$birthday.text('');
				$joinDate.text('');

				$authName.text(member.authName);
				$name.text(member.name);
				$description.text(member.description);
				$phoneNumber.text(member.phoneNumber);
				if(member.profileImage != null && member.profileImage.id != null) {
					$profileImage.show();
					$profileImage.attr('src', '/upload/images/'+member.profileImage.id);
				} else {
					 $profileImage.removeAttr('src');
					 $profileImage.hide();
				}
				$birthday.text(member.birthday);
				$joinDate.text(member.joinDate);

				return $memberComponent;
			}
			getMembers(memberComponent);
			$(window).scroll(function() {
				if ($(window).scrollTop() == $(document).height()
						- $(window).height()) {
					if(!requesting) {
						getMembers(memberComponent);
					}
				}
			});
		});
	</script>
</body>
</html>
