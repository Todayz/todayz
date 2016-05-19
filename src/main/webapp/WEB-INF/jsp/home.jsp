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
		<!-- /#page-wrapper -->
		<div id="page-wrapper" class="no-sidenav">
			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header">가입한 모임</h1>
				</div>
				<!-- /.col-lg-12 -->
			</div>
			<!-- /.row -->
			<div id="joined-club-list" class="row joined-member-list"></div>

			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header">개설된 모임</h1>
				</div>
				<!-- /.col-lg-12 -->
			</div>
			<!-- /.row -->
			<div id="club-list" class="row member-list"></div>
		</div>
		<!-- /#page-wrapper -->
	</div>
	<!-- /#wrapper -->
	<!--  club list -->
	<div style="display: none" class="club-component">
		<div class="col-lg-3 col-md-6 club">
			<div class="panel panel-green">
				<div class="panel-heading">
					<div class="row">
						<div class="col-xs-3">
							<i class="fa fa-tasks fa-5x"></i>
						</div>
						<div class="col-xs-9 text-right">
							<div class="huge"><span id="joinMemberCount"></span></div>
							<div id="title">title</div>
						</div>
					</div>
				</div>
				<a id="enter-club" href="#">
					<div class="panel-footer">
						<span class="pull-left">입장하기</span><span
							class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
						<div class="clearfix"></div>
					</div>
				</a>
			</div>
		</div>
	</div>

	<%@ include file="/WEB-INF/jspf/footer.jspf"%>
	<script type="text/javascript">
		$(function() {
			//?page=0&size=20;
			var pageable = {
				page: 0,
				size: 12
			};
			// 내보낸 화면의 아이디들은 여기에 보관.
			//보관되어진 id에 해당하는 데이터는 화면에 뿌려지지 않도록 하기 위함.
			var idList = [];
			// ajax 요청을 받는동안 또다른 요청을 하지 않기 위함.
			var requesting = false;

			// 공통화 필요. 클럽 전체
			var getClubs = function(callback) {
				requesting = true;
				var url = '/clubs';
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

							var clubs = data.content;
							var $clubList = $('#club-list');
							$.each(clubs, function(index, club) {
								if(idList.indexOf(club.id) == -1){
									idList.push(club.id);
									var $clubComponent = callback(club);
									$clubList.append($clubComponent.html());									
								}
							});
							$lodingImg.hide();
							requesting = false;
						}
					}.bind(this),
					error : function(xhr, status, err) {
						if (console) {
							console.log(xhr);
							console.log(status);
							console.log(err);
						}
						alert("error");
					}.bind(this)
				});
			};

			//가입한 클럽
			var getJoinedClubs = function(callback) {
				var url = '/clubs/member/${userInfo.id}';

				$.ajax({
					url : url,
					type : 'GET',
					cache : false,
					success : function(data) {
						if (data) {
							if(console) {
								console.log(data);
							}
							var $clubList = $('#joined-club-list');
							var clubs = data;
							clubs.forEach(function(club) {
								var $clubComponent = callback(club);
								$clubList.append($clubComponent.html());
							});
						}
					}.bind(this),
					error : function(xhr, status, err) {
						if (console) {
							console.log(xhr);
							console.log(status);
							console.log(err);
						}
						alert("error");
					}.bind(this)
				});
			}

			var clubComponent = function(club) {
			/* private Long id;
			  private String title;
			  private Image mainImage;
			  private String notice;
			  private List<Member> joiningMembers = new ArrayList<>();
			  private Date createdDate;
			  private Date updatedDate; */
			 	var url = '/pages/club/main/' + club.id;
				var $clubComponent = $('.club-component');
				var $title = $clubComponent.find('#title');
				var $enterClub = $clubComponent.find('#enter-club');
				//var $mainImage = $clubComponent.find('#mainImage');
				//var $joiningMembers = $clubComponent.find('#joiningMembers');	
				var $notice = $clubComponent.find('#notice');
				var $createdDate = $clubComponent.find('#createdDate');

				$title.text('');
				$notice.text('');
				$createdDate.text('');
				$enterClub.removeAttr('href')

				$title.text(club.title);
				$notice.text(club.notice);
				$createdDate.text(club.createdDate);
				$enterClub.attr('href',url)

				return $clubComponent;
			}
			//ready 시 실행.
			getClubs(clubComponent);
			getJoinedClubs(clubComponent);
			$(window).scroll(
					function() {
						if ($(window).scrollTop() == $(document).height()
								- $(window).height()) {
							if (!requesting) {
								getClubs(clubComponent);
							}
						}
					});
		});
	</script>
</body>
</html>
