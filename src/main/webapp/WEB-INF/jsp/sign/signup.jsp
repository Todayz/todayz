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
						<h3 class="panel-title">Sign Up</h3>
					</div>
					<div class="panel-body">
						<form id="signup-form" role="form" action="/members">
							<input type="hidden" id="_csrf" name="_csrf"
								value="${_csrf.token}"></input>
							<div class="form-group">
								<label>username</label>
								<c:choose>
									<c:when test="${member.id == null}">
										<input id="authName" class="form-control" name="authName"
											value="${member.authName}" type="text" autofocus />
									</c:when>
									<c:otherwise>
										<input id="authName" class="form-control" name="authName"
											value="${member.authName}" type="text" autofocus disabled />
									</c:otherwise>
								</c:choose>
							</div>
							<div class="form-group">
								<label>password</label> <input class="form-control"
									id="password" name="password" type="password" />
							</div>
							<div class="form-group">
								<label>name</label> <input class="form-control" id="name"
									name="name" value="${member.name}" type="text" />
							</div>
							<div class="form-group">
								<label>phone number</label> <input class="form-control"
									id="phoneNumber" name="phoneNumber"
									value="${member.phoneNumber}" type="text" />
							</div>
							<div class="form-group">
								<label>birthday</label> <input class="form-control"
									id="birthday" name="birthday" value="${formattedDate}"
									type="text" />
							</div>
							<div class="form-group">
								<label>description</label>
								<textarea class="form-control" id="description"
									name="description" rows="3">${member.description}</textarea>
							</div>
							<div class="form-group">
								<label>profile image</label> <input type="file"
									id="profileImage" name="profileImage" />
							</div>
							<button type="submit" class="btn btn-default">Submit
								Button</button>
							<button type="reset" class="btn btn-default">Reset
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
		$('#signup-form').submit(function(event) {
			event.preventDefault();
			var form = $(this);

			var id = "${member.id}";
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