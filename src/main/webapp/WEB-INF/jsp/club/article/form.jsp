<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<%@ include file="/WEB-INF/jspf/head.jspf"%>
<title>Login</title>
</head>
<body>
	<!-- Navigation -->
	<nav class="navbar navbar-default navbar-static-top" role="navigation"
		style="margin-bottom: 0">
		<%@ include file="/WEB-INF/jspf/navtop.jspf"%>
		<%@ include file="/WEB-INF/jspf/navside.jspf"%>
	</nav>

	<fmt:formatDate value="${article.updatedDate}" var="formattedDate"
		type="date" pattern="yyyy-MM-dd" />

	<!-- TODO validation -->
	<div class="container">
		<div class="row">
			<div class="col-md-4 col-md-offset-4">
				<div class="form-panel panel panel-default">
					<div class="panel-heading">
						<c:choose>
							<c:when test="${article.title == null}">
								<h3 class="panel-title">게시글 생성</h3>
							</c:when>
							<c:otherwise>
								<h3 class="panel-title"><c:out value="${article.title}"/></h3>
							</c:otherwise>
						</c:choose>
					</div>
					<div class="panel-body">
						<form id="article-form" role="form" action="/articles">
							<input type="hidden" id="_csrf" name="_csrf"
								value="${_csrf.token}"></input>
							<div class="form-group">
								<label>제목</label>
									<input id="title" class="form-control" name="title"
										value="${article.title}" type="text" autofocus />
							</div>
							<div class="form-group">
								<label>내용</label>
								<textarea class="form-control" id="content"
									name="content" rows="3"><c:out value="${article.content}"/></textarea>
							</div>
							<div class="form-group">
								<label>게시판 이미지</label> <input type="file"
									id="articleImage" name="articleImage" />
							</div>
							<button type="submit" class="btn btn-default">Submit
								Button</button>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="/WEB-INF/jspf/footer.jspf"%>
	<script type="text/javascript">
		//공통화 필요.
		// file upload 관련..참조(아래)
		// http://stackoverflow.com/questions/21329426/spring-mvc-multipart-request-with-json
		$('#article-form').submit(function(event) {
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
						//location.href = "/pages/club/main/${club.id}/menu/${menuId}/article/list";
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
	</script>
</body>
</html>