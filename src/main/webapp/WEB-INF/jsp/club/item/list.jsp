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
<title>매일 만나는 동호회 Todays</title>
</head>
<body>
	<div id="wrapper">
		<!-- Navigation -->
		<nav class="navbar navbar-default navbar-static-top" role="navigation"
			style="margin-bottom: 0">
			<%@ include file="/WEB-INF/jspf/navtop.jspf"%>
			<%@ include file="/WEB-INF/jspf/navside.jspf"%>
		</nav>
		<div id="page-wrapper" style="position: relative">
			<sec:authorize access="hasPermission(#club,'WRITE')">
			<a class="article-write-btn btn btn-primary btn-lg"
				href="/pages/club/main/${club.id}/menu/${menuId}/item/form"><i class="fa fa-pencil"></i> 글쓰기</a>
			</sec:authorize>
			<div class="panel-body">
				<ul id="article-list" class="article-list">
				</ul>
			</div>
			<!-- /.panel-body -->
			<!-- <div class="panel-footer">
				<div class="input-group">
					<input id="btn-input" type="text" class="form-control input-sm"
						placeholder="검색어를 입력하세요" /> <span
						class="input-group-btn">
						<button class="btn btn-warning btn-sm" id="btn-article-list">
							Search</button>
					</span>
				</div>
			</div> -->
			<!-- /.panel-footer -->
		</div>
		<!-- /.panel .article-list-panel -->
	</div>
	<!-- /#page-wrapper -->
	
	<!-- 좀 더 좋은 방법으로 변경가능한지 고민 필요. -->
	<!-- article list -->
	<div style="display: none" class="article-component">
		<li class="left clearfix article">
			<div class="article-list-body clearfix">
				<div class="body-left">
					<div class="header">
						<strong class="primary-font"><span
							class="article-list-img pull-left"> <img id="profileImage"
								src="http://placehold.it/50/55C1E7/fff" alt="User Avatar"
								class="img-circle" />
						</span><span id="writer">임정묵</span></strong>
					</div>
					<small
						class="text-muted"> <i
						class="fa fa-clock-o fa-fw"></i> <span id="updatedDate">2014-08-22 16:10:33</span>
					</small>
					<br/>
					<div class="title-and-content">
						<sec:authorize access="hasPermission(#club,'READ')">
						<a id="contentPath" href="#">
						</sec:authorize>
							<strong class="title primary-font"><span id="title">안녕하세요</span></strong>
							<p id="content" class="content">Lorem ipsum dolor sit amet, consectetur adipiscing elit.
							Curabitur bibendum ornare dolor, quis ullamcorper ligula
							sodales.</p>
						<sec:authorize access="hasPermission(#club,'READ')">
						</a>
						</sec:authorize>
						
					</div>
				</div>
				<div class="body-right" >
					<span class="pull-right">
					<div>
						<span class="comment-count">9</span>
						<i class="fa fa-comment pull-right"></i>
					</div>
					<img id="articleImage"
						src="http://placehold.it/50/55C1E7/fff"
						class="" />
					</span>
				</div>
			</div>
		</li>
	</div>
	<%@ include file="/WEB-INF/jspf/footer.jspf"%>
	<script type="text/javascript">
		$(function() {
			event.preventDefault();
			//?page=0&size=20;
			var pageable = {
				page: 0,
				size: 10,
				sort: 'id,desc',
				menuId: '${menuId}'
			};
			// 내보낸 화면의 아이디들은 여기에 보관.
			//보관되어진 id에 해당하는 데이터는 화면에 뿌려지지 않도록 하기 위함.
			var idList = [];
			// ajax 요청을 받는동안 또다른 요청을 하지 않기 위함.
			var requesting = false;

			// 공통화 필요.
			var getArticles = function(callback) {
				requesting = true;
				var url = '/articles';
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

							var articles = data.content;
							var $articleList = $('#article-list');
							$.each(articles, function(index, article) {
								if(idList.indexOf(article.id) == -1){
									idList.push(article.id);
									var $articleComponent = callback(article);
									$articleList.append($articleComponent.html());									
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

			
			var articleComponent = function(article) {
				//private Long id;
				//private String title;
				//private Menu parent;
				//private Member writer;
				//private Date createdDate;
				//private Date updatedDate;
				// images
				if(console) {
					console.log(article);
				}
				var $articleComponent = $('.article-component .article');
				var $title = $articleComponent.find('#title');
				var $writer = $articleComponent.find('#writer');
				var $content = $articleComponent.find('#content');
				var $updatedDate = $articleComponent.find('#updatedDate');
				var $articleImage = $articleComponent.find('#articleImage');
				var $profileImage = $articleComponent.find('#profileImage');

				var $contentPath = $articleComponent.find('#contentPath');

				var writer = article.writer;
				$title.text('');
				$writer.text('');
				$content.text('');
				$updatedDate.text('');
				$contentPath.removeAttr('href');

				$title.text(article.title);
				$writer.text(writer.name);
				$content.text(article.content);
				$updatedDate.text(article.updatedDate);

				if($contentPath.size()) {
					$contentPath.attr('href',
							'/pages/club/main/${club.id}/menu/${menuId}/item/'
									+ article.id);
				}
				if (article.articleImage != null
						&& article.articleImage.id != null) {
					$articleImage.show();
					$articleImage.attr('src', '/upload/images/'
							+ article.articleImage.id);
				} else {
					$articleImage.removeAttr('src');
					$articleImage.hide();
				}

				if (writer.profileImage != null
						&& writer.profileImage.id != null) {
					$profileImage.show();
					$profileImage.attr('src', '/upload/images/'
							+ writer.profileImage.id);
				} else {
					$profileImage.removeAttr('src');
					$profileImage.hide();
				}
				//$birthday.text(article.birthday);
				//$joinDate.text(article.joinDate);

				return $articleComponent;
			}
			getArticles(articleComponent);
			$(window).scroll(
					function() {
						if ($(window).scrollTop() == $(document).height()
								- $(window).height()) {
							if (!requesting) {
								getArticles(articleComponent);
							}
						}
					});
		});
	</script>
</body>
</html>
