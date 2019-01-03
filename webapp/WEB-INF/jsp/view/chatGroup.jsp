<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%--@elvariable id="groupId" type="java.lang.Long" --%>
<%--@elvariable id="action" type="java.lang.String" --%>
<%--@elvariable id="userName" type="java.lang.String" --%>
<%--@elvariable id="groupName" type="java.lang.String" --%>

<%--@elvariable id="groupMemberList" type="java.lang.String" --%>
<template:chatGroup htmlTitle="聊天组">
	<div class="span6">
		<div id="chatContainer">
			<div id="chatLog" style="overFlow-y: scroll"></div>
			<div id="messageContainer">
				<textarea id="messageArea"></textarea>
			</div>
			<div id="buttonContainer">
				<button class="btn btn-primary" onclick="send();">Send</button>
				<button class="btn" onclick="disconnect();">Disconnect</button>
			</div>
		</div>
	</div>
	<div class="span4">
		<div id="chatUserContainer">
			<table class="table table-striped">
				<thead>
					<tr>
						<th>用户列表</th>
					</tr>
				</thead>
				<tbody id="userList">
					<!-- <tr><td> -->
				</tbody>
			</table>
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
	
	<input type=hidden name=groupName id=groupName value='<c:out value="${groupName}" />'/>
	<input type=hidden name=userName id=userName value='<c:out value="${userName}" />'>
	<input type=hidden name=groupMemberList id=groupMemberList value='<c:out value="${groupMemberList}" />'>
	<input type=hidden name=groupId id=groupId value='<c:out value="${groupId}" />'>
	<input type=hidden name=action id=action value='<c:out value="${action}" />'>
	
	<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
	<script type="text/javascript" src="resource/js/chatGroup.js"></script>>
</template:chatGroup>