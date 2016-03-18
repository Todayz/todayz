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
						<form role="form" action="/members" method="POST">
							<div class="form-group">
								<label>username</label> <input class="form-control"
									name="authName" type="text" autofocus />
							</div>
							<div class="form-group">
								<label>password</label> <input class="form-control"
									name="password" type="password" />
							</div>
							<div class="form-group">
								<label>name</label> <input class="form-control" name="name"
									type="text" />
							</div>
							<div class="form-group">
								<label>phone number</label> <input class="form-control"
									name="phoneNumber" type="text" />
							</div>
							<div class="form-group">
								<label>birthday</label> <input class="form-control"
									name="birthday" type="text" />
							</div>
							<div class="form-group">
								<label>description</label>
								<textarea class="form-control" name="description" rows="3"></textarea>
							</div>
							<div class="form-group">
								<label>profile image</label> <input type="file"
									name="profileImage" />
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
</body>
</html>