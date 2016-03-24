<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
		</nav>
		<div id="page-wrapper" class="no-sidenav">
			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header">잘못된 요청입니다.</h1>
				</div>
				<!-- /.col-lg-12 -->
			</div>
			<!-- /.row -->
		</div>
		<!-- /#page-wrapper -->
	</div>

	<%@ include file="/WEB-INF/jspf/footer.jspf"%>
</body>
</html>
