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
	</nav>

	<fmt:formatDate value="${club.updatedDate}" var="formattedDate"
		type="date" pattern="yyyy-MM-dd" />

	<!-- TODO validation -->
	<div class="container">
		<div class="row">
			<div class="col-md-4 col-md-offset-4">
				<div class="form-panel panel panel-default">
					<div class="panel-heading">
						<c:choose>
							<c:when test="${club.title == null}">
								<h3 class="panel-title">클럽 생성 페이지</h3>
							</c:when>
							<c:otherwise>
								<h3 class="panel-title"><c:out value="${club.title}"/></h3>
							</c:otherwise>
						</c:choose>
					</div>
					<div class="panel-body">
						<form id="club-form" role="form" action="/clubs">
							<input type="hidden" id="_csrf" name="_csrf"
								value="${_csrf.token}"></input>
							<div class="form-group">
								<label>클럽명</label>
									<input id="title" class="form-control" name="title"
										value="${club.title}" type="text" autofocus />
									<div class="error-message title"></div>
							</div>
							<div class="form-group">
								<label>설명</label>
								<textarea class="form-control" id="notice"
									name="notice" rows="3">${club.notice}</textarea>
							</div>
							<div class="form-group">
								<label>메인 이미지</label> <input type="file"
									id="mainImage" name="mainImage" />
							</div>
							<button type="submit" class="btn btn-default">생성</button>
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
		$('#club-form').submit(function(event) {
			event.preventDefault();
			var form = $(this);
			var formData = new FormData();
			var uploadFile = form[0].mainImage.files[0];

			var id = "${club.id}";
			var action = form.attr('action');
			var url = id === "" ? action : action + "/" + id;
			//var type = id === "" ? 'POST' : 'PUT';
			var type = 'POST';
			var _csrf = $('#_csrf').val();

			if (console) {
				console.log(JSON.stringify(form.serializeObject()));
				console.log(uploadFile);
			}
			if (uploadFile) {
				formData.append('mainImage', uploadFile);
			}
			formData.append('club', new Blob([JSON.stringify(form.serializeObject())], {
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
						location.href = "/pages/club/main/" + data.id;
					}
				}.bind(this),
				error : function(xhr, status, err) {
					var errors = xhr.responseJSON.errors;
					errors.forEach(function(obj) {
						var field = obj.field;
						$(".error-message." + field).text(obj.defaultMessage);
					});
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