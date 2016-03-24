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

	<fmt:formatDate value="${member.birthday}" var="formattedDate"
		type="date" pattern="yyyy-MM-dd" />

	<!-- TODO validation -->
	<div class="container">
		<div class="row">
			<div class="col-md-4 col-md-offset-4">
				<div class="login-panel panel panel-default">
					<div class="panel-heading">
						<c:choose>
							<c:when test="${club.title == null}">
								<h3 class="panel-title">Club Create page</h3>
							</c:when>
							<c:otherwise>
								<h3 class="panel-title">${club.title}</h3>
							</c:otherwise>
						</c:choose>
					</div>
					<div class="panel-body">
						<form id="club-form" role="form" action="/clubs">
							<input type="hidden" id="_csrf" name="_csrf"
								value="${_csrf.token}"></input>
							<div class="form-group">
								<label>Club title</label>
									<input id="title" class="form-control" name="title"
										value="${club.title}" type="text" autofocus />
							</div>
							<div class="form-group">
								<label>notice</label>
								<textarea class="form-control" id="notice"
									name="notice" rows="3">${club.notice}</textarea>
							</div>
							<div class="form-group">
								<label>Main image</label> <input type="file"
									id="mainImage" name="mainImage" />
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
	<script src="/js/hotclub.js"></script>
	<script type="text/javascript">
		//공통화 필요.
		$('#club-form').submit(function(event) {
			event.preventDefault();
			var form = $(this);

			var id = "${club.id}";
			var action = form.attr('action');
			var url = id === "" ? action : action + "/" + id;
			var type = id === "" ? 'POST' : 'PUT';
			var _csrf = $('#_csrf').val();

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
						// TODO 추후에 개설된 클럽으로 이동할 수 있도록 변경. 
						location.href = "/pages/home";
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