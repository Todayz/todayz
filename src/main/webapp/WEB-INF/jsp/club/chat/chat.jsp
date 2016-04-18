<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<%@ include file="/WEB-INF/jspf/head.jspf"%>
</head>
<body>
	<div id="wrapper">
		<!-- Navigation -->
		<nav class="navbar navbar-default navbar-static-top" role="navigation"
			style="margin-bottom: 0">
			<%@ include file="/WEB-INF/jspf/navtop.jspf"%>
			<%@ include file="/WEB-INF/jspf/navside.jspf"%>
		</nav>
		<noscript>
			<h2 style="color: #ff0000">Seems your browser doesn't support
				Javascript! Websocket relies on Javascript being enabled. Please
				enable Javascript and reload this page!</h2>
		</noscript>
		<div id="page-wrapper">
			<div class="chat-panel panel-body">
				<ul class="chat">
				</ul>
				
				<!-- /.panel .comments-panel -->
				<nav class="navbar navbar-default navbar-fixed-bottom">
					<div class="chat-input-panel panel-footer">
						<div class="input-group">
							<input id="chat-input"
								type="text" name="chat-input" class="form-control input-lg"
								placeholder="메세지 입력" /> 
							<span class="input-group-btn">
								<button class="btn btn-warning btn-lg" id="btn-chat">전송</button>
							</span>
						</div>
					</div>
				</nav>
			</div>
			<!-- /.panel-body -->
		</div>
		<!-- /#page-wrapper -->
	</div>
	<!-- /#wrapper -->
	<!-- chat component -->
	<div style="display: none" class="chat-component">
		<div class="right-chat">
			<li id="chat" class="right clearfix"><span
				class="chat-img pull-right"> <img id="profileImage"
					src="http://placehold.it/50/FA6F57/fff" alt="User Avatar"
					class="img-circle" />
			</span>
				<div class="chat-body clearfix">
					<div class="header">
						<small class=" text-muted"> <i class="fa fa-clock-o fa-fw"></i>
							<span id="createdDate">date</span>
						</small> <strong class="pull-right primary-font"> <span
							id="writer">name</span>
						</strong>
					</div>
					<p id="content">content</p>
				</div></li>
		</div>
		<div class="left-chat">
			<li id="chat" class="left clearfix"><span
				class="chat-img pull-left"> <img id="profileImage"
					src="http://placehold.it/50/55C1E7/fff" alt="User Avatar"
					class="img-circle" />
			</span>
				<div class="chat-body clearfix">
					<div class="header">
						<strong class="primary-font"><span id="writer">name</span></strong> <small class="pull-right text-muted"> <i
							class="fa fa-clock-o fa-fw"></i> <span id="createdDate">date</span>
						</small>
					</div>
					<p id="content">content</p>
				</div></li>
		</div>
	</div>
	<%@ include file="/WEB-INF/jspf/footer.jspf"%>
	<script src="/js/sockjs-1.0.3.js"></script>
	<script src="/js/stomp.js"></script>
	<script type="text/javascript">
		
	
	/* 	function setConnected(connected) {
			document.getElementById('connect').disabled = connected;
			document.getElementById('disconnect').disabled = !connected;
			document.getElementById('conversationDiv').style.visibility = connected ? 'visible'
					: 'hidden';
			document.getElementById('response').innerHTML = '';
		}
	 */
	 	$(function() {
	 		var authName = '${userInfo.username}';
	 		var stompClient = null;
		 	var showChatMessage = function(chatMessage) {
				
				var $chatComponent = null;
				var writer = chatMessage.writer;
				if(authName === writer.authName) { 
					$chatComponent = $('.chat-component .right-chat');
				} else {
					$chatComponent = $('.chat-component .left-chat');
				}
				var $writer = $chatComponent.find('#writer');
				var $content = $chatComponent.find('#content');
				var $createdDate = $chatComponent.find('#createdDate');
				var $profileImage = $chatComponent.find('#profileImage');

			
				$writer.text('');
				$content.text('');
				$createdDate.text('');

				$writer.text(writer.name);
				$content.text(chatMessage.content);
				$createdDate.text(chatMessage.createdDate);

				if (writer.profileImage != null
						&& writer.profileImage.id != null) {
					$profileImage.show();
					$profileImage.attr('src', '/upload/images/'
							+ writer.profileImage.id);
				} else {
					$profileImage.removeAttr('src');
					$profileImage.hide();
				} 
				return $chatComponent;
			};

			var connect = function() {
				var socket = new SockJS('/chatSocket');
				stompClient = Stomp.over(socket);
				var $chatList = $('.chat-panel .chat');
				stompClient.connect({}, function(frame) {
					//setConnected(true);
					console.log('Connected: ' + frame);
					stompClient.subscribe('/topic/chatMessages.${club.id}', function(chat) {
	//alert(11);
						//console.log('/topic/chatMessages');
						console.log(JSON.parse(chat.body));
						$chatComponent = showChatMessage(JSON.parse(chat.body));
						$chatList.append($chatComponent.html());
						//console.log($chatList.height());
						//$(document).scrollTop($chatList.height());
					});
				}, function(message) {
					console.log('disconnect message : ' + message);
				    // check message for disconnect
				});
			};
		
			var disconnect = function() {
				if (stompClient != null) {
					stompClient.disconnect();
				}
				//setConnected(false);
				console.log("Disconnected");
			};

			var sendMessage = function(event, elem) {
				//console.log(event);
				var content = elem.val();
				console.log(JSON.stringify({
					'content' : content
				}));
				stompClient.send("/chat/chatTyping.${club.id}", {}, JSON.stringify({
					'content' : content
				}));

				elem.val('');
			};
			disconnect();
			connect();
			$(".chat-panel #btn-chat").on('click', function(event){
				sendMessage(event, $(".chat-panel #chat-input"));
				console.log($(this));
			});
			$(".chat-panel #chat-input").on('keyup', function(event) {
				if (event.keyCode === 13) {
					sendMessage(event, $(this));
				}
			});
		});
	</script>
</body>
</html>