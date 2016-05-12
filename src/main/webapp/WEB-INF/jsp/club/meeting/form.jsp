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

	<fmt:formatDate value="${meeting.meetingDate}" var="formattedDate"
		type="date" pattern="yyyy-MM-dd" />

	<!-- TODO validation -->
	<div class="container">
		<div class="row">
			<div class="col-md-4 col-md-offset-4">
				<div class="form-panel panel panel-default">
					<div class="panel-heading">
						<c:choose>
							<c:when test="${meeting.title == null}">
								<h3 class="panel-title">미팅 생성</h3>
							</c:when>
							<c:otherwise>
								<h3 class="panel-title"><c:out value="${meeting.title}"/></h3>
							</c:otherwise>
						</c:choose>
					</div>
					<div class="panel-body">
						<form id="meeting-form" role="form" action="/meetings">
							<input type="hidden" id="_csrf" name="_csrf"
								value="${_csrf.token}"></input>
							<div class="form-group">
								<label>제목</label>
									<input id="title" class="form-control" name="title"
										value="${meeting.title}" type="text" autofocus />
									<div class="error-message title"></div>
							</div>
							<div class="form-group">
								<label>모임날짜</label>
									<input id="meetingDate" class="form-control" name="meetingDate"
										value="${meeting.meetingDate}" type="text" />
									<div class="error-message meetingDate"></div>
							</div>
							<div class="form-group">
								<label>정모장소</label>
									<input id="place" class="form-control" name="place"
										value="${meeting.place}" type="text" />
									<div class="error-message place"></div>
							</div>
							<div class="form-group">
								<label>만남비용</label>
									<input id="attendCosts" class="form-control" name="attendCosts"
										value="${meeting.attendCosts}" type="text" />
									<div class="error-message attendCosts"></div>
							</div>
							<div class="form-group">
								<label>정원</label>
									<input id="quota" class="form-control" name="quota"
										value="${meeting.quota}" type="text" />
									<div class="error-message quota"></div>
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
		$('#meeting-form').submit(function(event) {
			/* 	private String title;
				@Temporal(TemporalType.TIMESTAMP)
				private Date meetingDate;
				private String place;
				private String attendCosts;
				private Long quota; */
			event.preventDefault();
			var form = $(this);
			var action = form.attr('action');
			var url = action;
			var type = 'POST';
			var _csrf = $('#_csrf').val();
			var clubId = '${club.id}';

			url += '?clubId='+clubId;
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
						location.href = "/pages/club/main/" + clubId;
						//location.href = "/pages/home";
						//location.reload();
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