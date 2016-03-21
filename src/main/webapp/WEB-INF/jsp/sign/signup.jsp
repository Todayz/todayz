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
	<div class="container">
		<div class="row">
			<div class="col-md-4 col-md-offset-4">
				<div class="login-panel panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">Sign Up</h3>
					</div>
					<div class="panel-body">
						<form id="signup-form" role="form" action="/members" method="POST">
							<input type="hidden" id="_csrf" name="_csrf"
								value="${_csrf.token}"></input>
							<div class="form-group">
								<label>username</label> <input id="authName"
									class="form-control" name="authName" type="text" autofocus />
							</div>
							<div class="form-group">
								<label>password</label> <input class="form-control"
									id="password" name="password" type="password" />
							</div>
							<div class="form-group">
								<label>name</label> <input class="form-control" id="name"
									name="name" type="text" />
							</div>
							<div class="form-group">
								<label>phone number</label> <input class="form-control"
									id="phoneNumber" name="phoneNumber" type="text" />
							</div>
							<div class="form-group">
								<label>birthday</label> <input class="form-control"
									id="birthday" name="birthday" type="text" />
							</div>
							<div class="form-group">
								<label>description</label>
								<textarea class="form-control" id="description"
									name="description" rows="3"></textarea>
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
	<!-- jQuery -->
	<script src="/bower_components/jquery/dist/jquery.min.js"></script>
	<!-- Bootstrap Core JavaScript -->
	<script src="/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

	<script src="/js/hotclub.js"></script>
	<script type="text/javascript">
		$('#signup-form').submit(function(event) {
			event.preventDefault();
			var form = $(this);

			if (console) {
				console.log(JSON.stringify(form.serializeObject()));
			}
			$.ajax({
				url : form.attr('action'),
				dataType : 'json',
				type : form.attr('method'),
				data : JSON.stringify(form.serializeObject()),
				cache : false,
				contentType : 'application/json',
				beforeSend : function(request) {
					request.setRequestHeader("X-CSRF-TOKEN", form_data._csrf);
				},
				success : function(data) {
					if (data) {
						console.log(data);
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

	<%@ include file="/WEB-INF/jspf/footer.jspf"%>
</body>
</html>