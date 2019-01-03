<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%--@elvariable id="listOfChatMessage" type="java.util.List<com.yangql.site.entities.ChatMessage>" --%>    
<template:main htmlTitle="历史消息" isLogin='true'>
	<h1>历史消息记录</h1>
	<table class="table table-hover">
				<thread>
				<tr>
					<th>时间</th>
					<th>房间名称</th>
					<th>发送者</th>
					<th>内容</th>
				</tr>
				</thread>
				<tbody>
					<c:forEach items="${listOfChatMessage}" var="e">
						<tr>
							<td>${e.timeStamp}</td>
							<td>${e.groupName}</td>
							<td>${e.username}</td>
							<td>${e.content}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
	</template:main>