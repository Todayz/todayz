<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<%@ include file="/WEB-INF/jspf/head.jspf"%>
<title>Login</title>
</head>
<body>
	<div id="wrapper">
		<!-- Navigation -->
		<nav class="navbar navbar-default navbar-static-top" role="navigation"
			style="margin-bottom: 0">
			<%@ include file="/WEB-INF/jspf/navtop.jspf"%>
			<%@ include file="/WEB-INF/jspf/navside.jspf"%>
		</nav>

		<fmt:formatDate value="${article.updatedDate}" var="formattedDate"
			type="date" pattern="yyyy-MM-dd HH:mm:ss" />

		<!-- TODO validation -->
		<div id="page-wrapper">
			<div class="article panel-body">
			<!-- Single button -->
				<sec:authorize
				access="hasPermission(#article,'ADMINISTRATION') or hasPermission(#club,'ADMINISTRATION')">
					<div class="btn-group pull-right">
						<button type="button" class="btn btn-default dropdown-toggle"
							data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
							편집 <span class="caret"></span>
						</button>
						<ul class="dropdown-menu">
							<li><a
								href="/pages/club/main/${club.id}/menu/${menuId}/article/form?itemId=${article.id}">게시글
									수정</a></li>
							<li><a href="#">게시글 삭제</a></li>
						</ul>
					</div>
				</sec:authorize>
				<div class="panel-default">
					<div class="article-list-body clearfix">
						<div class="body-left">
							<div class="header">
								<strong class="primary-font"><span
									class="article-list-img pull-left"> 
									<c:choose>
										<c:when test="${article.writer.profileImage.id != null}">
											<img id="profileImage"
												src="/upload/images/${article.writer.profileImage.id}"
												alt="User Avatar" class="img-circle" />
										</c:when>
										<c:otherwise>
											<img src="http://placehold.it/50/55C1E7/fff"
												alt="User Avatar" class="img-circle" />
										</c:otherwise>
									</c:choose>
								</span><span id="writer">${article.writer.name}</span></strong>
							</div>
							<small class="text-muted"> <i class="fa fa-clock-o fa-fw"></i>
								<span id="updatedDate">${article.updatedDate}</span>
							</small> <br />
						</div>
					</div>
					<div class="title-and-content">
						<strong class="title primary-font"><span id="title">${article.title}</span></strong>
						<p id="content" class="content">${article.content}</p>
						<div class="content-image">
							<!-- 앞으로 여러장이 있을 수 있다. -->
							<img src="/upload/images/${article.articleImage.id}" />
						</div>
					</div>
				</div>
				<nav class="navbar">
				<button type="button" class="btn btn-default navbar-btn">좋아요</button>
				<span> 3 명이 좋아하셨습니다.</span>
				</nav>
				<div class="comments-panel panel-default">
					<ul class="comments">
						<c:forEach var="comment" items="${commentList}">
							<li class="comment left clearfix">
								<span
									class="comments-img pull-left">
									<c:choose>
										<c:when test="${comment.writer.profileImage.id != null}">
											<img alt=""
												src="/upload/images/${comment.writer.profileImage.id}"
												 class="img-circle">
										</c:when>
										<c:otherwise>
											<img src="http://placehold.it/50/55C1E7/fff"
												alt="User Avatar" class="img-circle" />
										</c:otherwise>
									</c:choose>
								</span>
								<div class="comments-body clearfix">
									<div class="header">
										<strong class="primary-font"><span id="writer">${comment.writer.name}</span></strong>
										<sec:authorize access="hasPermission(#club,'ADMINISTRATION') or hasPermission(#comment,'DELETE')">
										<a id="delete-link" class="pull-right text-muted" href="/comments/${comment.id}"><i class="fa fa-times fa-3x"></i></a>
										</sec:authorize>
										<small class="pull-right text-muted"><span
											id="createdDate">${comment.createdDate} </span></small> <strong
											class="primary-font text-primary">모임장</strong>
									</div>
									<p id="content">${comment.content}</p>
								</div></li>
						</c:forEach>
					</ul>
				</div>
				<!-- /.panel .comments-panel -->
				<nav class="navbar navbar-default navbar-fixed-bottom">
					<form id="comment-form" role="form" method="POST"
						action="/comments">
						<div class="comment-input-panel panel-footer">
							<div class="input-group">
								<input type="hidden" id="_csrf" name="_csrf"
									value="${_csrf.token}"></input> <input id="comment-content"
									type="text" name="content" class="form-control input-lg"
									placeholder="댓글을 달아주세요" /> <span class="input-group-btn">
									<button class="btn btn-warning btn-lg" id="btn-chat">전송</button>
								</span>
							</div>
						</div>
					</form>
				</nav>
			</div>
		</div>
	</div>

	<%@ include file="/WEB-INF/jspf/footer.jspf"%>
	<script src="/js/todayz.js"></script>
	<script type="text/javascript">
		//공통화 필요.
		$(function() {
			$('.comment #delete-link').click(function(event) {
				event.preventDefault();
				var _csrf = $('#_csrf').val();
				$.ajax({
					url : $(this).attr("href"),
					type : 'DELETE',
					cache : false,
					contentType : 'application/json',
					beforeSend : function(request) {
						request.setRequestHeader("X-CSRF-TOKEN", _csrf);
					},
					success : function(data) {
						location.reload();
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

			$('#comment-form').submit(function(event) {
				event.preventDefault();
				var form = $(this);
				var action = form.attr('action');
				var url = action;
				var type = 'POST';
				var _csrf = $('#_csrf').val();

				url += '?itemId=${article.id}';
				if (console) {
					console.log(JSON.stringify(form.serializeObject()));
				}

				$.ajax({
					url : url,
					dataType : 'json',
					type : type,
					data : JSON.stringify(form.serializeObject()),
					cache : false,
					contentType : 'application/json',
					beforeSend : function(request) {
						request.setRequestHeader("X-CSRF-TOKEN", _csrf);
					},
					success : function(data) {
						if (data) {
							console.log(data);
							//location.href = "/pages/home";
							location.reload();
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