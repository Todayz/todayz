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
			event.preventDefault();
			//?page=0&size=20
			var url = '/members';
			$.ajax({
				url : url,
				type : 'GET',
				cache : false,
				success : function(data) {
					if (data) {
						var members = data.content;
						var $memberList = $('#member-list');
						var $memberComponent = $('.member-component .row');
						$.each(members, function(index, member) {
							console.log(member);
							console.log($memberComponent);
							var $memberComponent = memberComponent(member);
							$memberList.append($memberComponent.html());
						});
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

			var memberComponent = function(member) {
				var $memberComponent = $('.member-component .row');
				var $authName = $memberComponent.find('#authName');
				var $name = $memberComponent.find('#name');
				var $description = $memberComponent.find('#description');
				var $phoneNumber = $memberComponent.find('#phoneNumber');
				var $birthday = $memberComponent.find('#birthday');
				var $joinDate = $memberComponent.find('#joinDate');

				$authName.text(member.authName);
				$name.text(member.name);
				$description.text(member.description);
				$phoneNumber.text(member.phoneNumber);
				$birthday.text(member.birthday);
				$joinDate.text(member.joinDate);
				
				return $memberComponent;
			}
		});
	</script>
</body>
</html>
