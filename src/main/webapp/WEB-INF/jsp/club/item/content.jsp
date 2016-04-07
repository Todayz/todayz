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
								href="/pages/club/main/${club.id}/menu/${menuId}/item/form?itemId=${article.id}">게시글
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
					</div>
				</div>
				<nav class="navbar">
				<button type="button" class="btn btn-default navbar-btn">좋아요</button>
				<span> 3 명이 좋아하셨습니다.</span>
				</nav>
				<div class="comments-panel panel-default">
					<ul class="comments">
						<li class="left clearfix"><span class="comments-img pull-left">
								<img src="http://placehold.it/50/55C1E7/fff" alt="User Avatar"
								class="img-circle" />
						</span>
							<div class="comments-body clearfix">
								<div class="header">
									<strong class="primary-font">임정묵</strong> <small
										class="pull-right text-muted"> 1986-08-30 11:22:33
									</small> <strong class="primary-font text-primary">모임장</strong>
								</div>
								<p>안녕하세요</p>
							</div></li>
					</ul>
				</div>
				<!-- /.panel .comments-panel -->
			</div>
		</div>
	</div>
	<%@ include file="/WEB-INF/jspf/footer.jspf"%>
	<script src="/js/hotclub.js"></script>
	<script type="text/javascript">
		//공통화 필요.
		// file upload 관련..참조(아래)
		// http://stackoverflow.com/questions/21329426/spring-mvc-multipart-request-with-json
		/* $('#article-form').submit(function(event) {
			//private Long id;
			//private String title;
			//private Menu parent;
			//private Member writer;
			//private Date createdDate;
			//private Date updatedDate;
			event.preventDefault();
			var form = $(this);
			var formData = new FormData();
			var uploadFile = form[0].articleImage.files[0];

			var id = "${article.id}";
			var action = form.attr('action');
			var url = id === "" ? action : action + "/" + id;
			//var type = id === "" ? 'POST' : 'PUT';
			url += "?menuId=${menuId}";
			var type = 'POST';
			var _csrf = $('#_csrf').val();

			if (console) {
				console.log(JSON.stringify(form.serializeObject()));
				console.log(uploadFile);
			}
			if (uploadFile) {
				formData.append('articleImage', uploadFile);
			}
			formData.append('article', new Blob([JSON.stringify(form.serializeObject())], {
			                type: 'application/json'
			            }));

			$.ajax({
				url : url,
				//dataType : 'json',
				type : type,
				data: formData,
				enctype: 'multipart/form-data',
			    processData: false,
			    contentType: false,
			    cache: false,
				beforeSend : function(request) {
					request.setRequestHeader("X-CSRF-TOKEN", _csrf);
				},
				success : function(data) {
					if (data) {
						console.log(data);
						// TODO 추후에 개설된 클럽으로 이동할 수 있도록 변경. 
						//location.href = "/pages/club/main/" + data.id;
						//location.href = "/pages/club/main/${club.id}/menu/${menuId}/item/list";
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
		}); */
	</script>
</body>
</html>