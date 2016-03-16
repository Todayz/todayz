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
	<div class="container">
		<div class="row">
			<div class="col-md-4 col-md-offset-4">
				<div class="login-panel panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">Please Sign In</h3>
						<a href="/pages/signup">move signup</a>
					</div>
					<div class="panel-body">
						<form role="form" method="POST" action="/loginProcess">
							<fieldset>
								<input type="hidden" name="_csrf" value="${_csrf.token}"></input>
								<div class="form-group">
									<input class="form-control" placeholder="authName"
										name="username" type="text" />
								</div>
								<div class="form-group">
									<input class="form-control" placeholder="Password"
										name="password" type="password" />
								</div>
								<div class="checkbox">
									<label> <input name="remember" type="checkbox"
										value="Remember Me" />Remember Me
									</label>
								</div>
								<div class="form-group">
									<input name="submit" type="submit" value="Login" />
								</div>
							</fieldset>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="/WEB-INF/jspf/footer.jspf"%>
</body>
</html>