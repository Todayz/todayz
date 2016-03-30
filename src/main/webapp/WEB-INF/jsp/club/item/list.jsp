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
			<%@ include file="/WEB-INF/jspf/navside.jspf"%>
		</nav>
		<div id="page-wrapper">
			<!-- /.panel-heading -->
			<div class="panel-body">
				<ul class="chat">
					<li class="left clearfix"><span class="chat-img pull-left">
							<img src="http://placehold.it/50/55C1E7/fff" alt="User Avatar"
							class="img-circle" />
					</span>
						<div class="chat-body clearfix">
							<div class="header">
								<strong class="primary-font">Jack Sparrow</strong> <small
									class="pull-right text-muted"> <i
									class="fa fa-clock-o fa-fw"></i> 12 mins ago
								</small>
							</div>
							<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit.
								Curabitur bibendum ornare dolor, quis ullamcorper ligula
								sodales.</p>
						</div></li>
					<li class="right clearfix"><span class="chat-img pull-right">
							<img src="http://placehold.it/50/FA6F57/fff" alt="User Avatar"
							class="img-circle" />
					</span>
						<div class="chat-body clearfix">
							<div class="header">
								<small class=" text-muted"> <i
									class="fa fa-clock-o fa-fw"></i> 13 mins ago
								</small> <strong class="pull-right primary-font">Bhaumik Patel</strong>
							</div>
							<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit.
								Curabitur bibendum ornare dolor, quis ullamcorper ligula
								sodales.</p>
						</div></li>
					<li class="left clearfix"><span class="chat-img pull-left">
							<img src="http://placehold.it/50/55C1E7/fff" alt="User Avatar"
							class="img-circle" />
					</span>
						<div class="chat-body clearfix">
							<div class="header">
								<strong class="primary-font">Jack Sparrow</strong> <small
									class="pull-right text-muted"> <i
									class="fa fa-clock-o fa-fw"></i> 14 mins ago
								</small>
							</div>
							<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit.
								Curabitur bibendum ornare dolor, quis ullamcorper ligula
								sodales.</p>
						</div></li>
					<li class="right clearfix"><span class="chat-img pull-right">
							<img src="http://placehold.it/50/FA6F57/fff" alt="User Avatar"
							class="img-circle" />
					</span>
						
				</ul>
			</div>
			<!-- /.panel-body -->
			<div class="panel-footer">
				<div class="input-group">
					<input id="btn-input" type="text" class="form-control input-sm"
						placeholder="검색어를 입력하세요" /> <span
						class="input-group-btn">
						<button class="btn btn-warning btn-sm" id="btn-chat">
							Search</button>
					</span>
				</div>
			</div>
			<!-- /.panel-footer -->
		</div>
		<!-- /.panel .chat-panel -->
	</div>
	<!-- /#page-wrapper -->
	</div>
</body>
</html>
