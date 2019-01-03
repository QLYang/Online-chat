<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%--@elvariable id="groupNameList" type="java.util.List<String>" --%>
<template:main htmlTitle="历史消息" isLogin='true'>
	<h1>历史消息记录</h1>
	<c:choose>
		<c:when test="${fn:length(groupNameList) == 0}">
			<i>无记录 </i>
		</c:when>
		<c:otherwise>
			<table class="table table-hover">
				<thread>
				<tr>
					<th>房间名称</th>
					<th></th>
				</tr>
				</thread>
				<tbody>
					<c:forEach items="${groupNameList}" var="e">
						<tr>
							<td>${e}</td>
							<td><a href="javascript:void 0;" onclick="showMessage('${e}');">点击查看</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:otherwise>
	</c:choose>
	<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
	<script type="text/javascript" language="javascript">
		var showMessage;
		$(document).ready(function() {
			var url = '<c:url value="/history" />';
			showMessage = function(groupName) {
				post({
					groupName : groupName
				});
			}
			var post = function(fields) {
				var form = $('<form id="mapForm" method="post"></form>').attr({
					action : url,
					style : 'display: none;'
				});
				for ( var key in fields) {
					if (fields.hasOwnProperty(key))
						form.append($('<input type="hidden">').attr({
							name : key,
							value : fields[key]
						}));
				}
				$('body').append(form);
				form.submit();
			};
		});
	</script>
</template:main>