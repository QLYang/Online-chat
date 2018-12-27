<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%--@elvariable id="groupId" type="java.lang.Long" --%>
<%--@elvariable id="action" type="java.lang.String" --%>
<%--@elvariable id="userName" type="java.lang.String" --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<link rel="stylesheet"
	href="<c:url value="/resource/stylesheet/chat.css" />" />
<link rel="stylesheet"
	href="http://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/2.3.1/css/bootstrap.min.css" />
<script
	src="http://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/2.3.1/js/bootstrap.min.js"></script>

<title>聊天室</title>
</head>
<body>
	<div id="chatContainer">
		<div id="chatLog"></div>
		<div id="messageContainer">
			<textarea id="messageArea"></textarea>
		</div>
		<div id="buttonContainer">
			<button class="btn btn-primary" onclick="send();">Send</button>
			<button class="btn" onclick="disconnect();">Disconnect</button>
		</div>
	</div>

	<%--错误弹窗--%>
	<div id="modalError" class="modal hide fade">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">&times;</button>
			<h3>Error</h3>
		</div>
		<div class="modal-body" id="modalErrorBody">A blah error
			occurred.</div>
		<div class="modal-footer">
			<button class="btn btn-primary" data-dismiss="modal">OK</button>
		</div>
	</div>
	<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
	<script type="text/javascript" language="javascript">
		$(document)
				.ready(
						function() {
							var modalError = $("#modalError")
							var modalErrorBody = $("#modalErrorBody")
							var chatLog = $('#chatLog')
							var messageArea = $('#messageArea')
							var userName = '${sessionScope.userName}';
							var otherJoined = false;

							var log = console.log.bind(console)
	<%--浏览器是否支持websocket--%>
		if (!("WebSocket" in window)) {
								modalErrorBody
										.text('WebSockets are not supported in this '
												+ 'browser. Try Internet Explorer 10 or the latest '
												+ 'versions of Mozilla Firefox or Google Chrome.')
								modalError.modal('show')
								return

							}
	<%--工具函数--%>
		var infoMessage = function(m) {
								chatLog.append($('<div>').addClass(
										'informational').text(m))
							}

							var objectMessage = function(message) {
								var log = $('<div>')
								var date = message.timeStamp;
								if (message.username != null) {
									var c = message.username == userName ? 'user-me'
											: 'user-you' //样式
									log.append(
											$('<span>').addClass(c).text(
													date + ' '
															+ message.username
															+ ':\xA0')) //添加时间：用户名
									.append($('<span>').text(message.content)) //添加内容
								} else {
									log.addClass(
											message.type == 'ERROR' ? 'error'
													: 'informational').text(
											date + ' ' + message.content)
								}
								chatLog.append(log) //输出文本
							}
	<%--连接服务器--%>
		var server
							try {
								server = new WebSocket(
										'ws://'
												+ window.location.host
												+ '<c:url value="/chat/${groupId}/${userName}" />'
												+ "?action=" + "${action}")
							} catch (error) {
								modalErrorBody.text(error)
								modalError.modal('show')
								return

							}
							server.onopen = function(event) {
								infoMessage("用户 "+userName +'已连接服务器.')
							}

							server.onclose = function(event) {
								if (server != null)
									infoMessage('连接关闭.')
								server = null
								if (!event.wasClean || event.code != 1000) {
									modalErrorBody.text('错误码 ' + event.code
											+ ': ' + event.reason)
									modalError.modal('show')
								}
							}
							server.onerror = function(event) {
								modalErrorBody.text(event.data)
								modalError.modal('show')
							}

							server.onmessage = function(event) {
								var message = JSON.parse(event.data)
								if (message.type == 'JOINED') {
									if (message.username != userName) {
										objectMessage(message);
									}
								} else if (message.type == 'TEXT') {
									objectMessage(message);
								} else if (message.type == 'LEFT') {
									objectMessage(message);
								} else {
									modalErrorBody.text('未知数据类型 ['
											+ typeof (event.data) + '].')
									modalError.modal('show')
								}
							}
	<%--按钮操作--%>
		send = function() {
								if (server == null) {
									modalErrorBody.text('您未连接到服务器!');
									modalError.modal('show');
								} else if (messageArea.get(0).value.trim().length > 0) {
									var message = {
										timeStamp : null, //注意，时间戳在服务器端设置
										type : 'TEXT',
										username : userName,
										content : messageArea.get(0).value
									}
									try {
										var json = JSON.stringify(message);
										server.send(json);
										messageArea.get(0).value = '';
									} catch (error) {
										modalErrorBody.text(error);
										modalError.modal('show');
									}
								}
							}
							disconnect = function() {
								if (server != null) {
									infoMessage('断开连接.'); 
									server.close();
									server = null;
								}
							};

							window.onbeforeunload = disconnect;
						})
	</script>
</body>
</html>