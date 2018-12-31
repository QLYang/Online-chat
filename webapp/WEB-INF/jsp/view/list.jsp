<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%--@elvariable id="activeGroups" type="java.util.Map<long, com.yangql.site.chat.ChatServer.ChatGroup>" --%>
<c:set var="isLogin" value="true" />
<template:main htmlTitle="列表" isLogin="${isLogin }">
	<h1>活跃列表</h1>
	<c:choose>
		<c:when test="${fn:length(activeGroups) == 0}">
			<i>无活跃房间 </i>
		</c:when>
		<c:otherwise>
			<table class="table table-hover">
				<thread>
				<tr>
					<th>房间Id</th>
					<th>房间名称</th>
					<th>活跃人数</th>
					<th></th>
				</tr>
				</thread>
				<tbody>
					<c:forEach items="${activeGroups}" var="e">
						<tr>
							<td>${e.value.groupId }</td>
							<td>${e.value.chat.groupName}</td>
							<td>${e.value.memNums }</td>
							<td><a href="javascript:void 0;" onclick="joinGroup(${e.key});">点击进入</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:otherwise>
	</c:choose>
	<br></br>
	<br></br>
	<button class="btn btn-large btn-block" type="button" onclick="createGroup();">创建新房间</button>
	
	<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
	<script type="text/javascript" language="javascript">
            var createGroup, joinGroup;
            $(document).ready(function() {
                var url = '<c:url value="/list" />';
                
                createGroup = function() {
                    var groupName = prompt('输入房间名称（仅限英文字母）', '');
                    if(groupName != null && groupName.trim().length > 0 &&
                    		validateGroupName(groupName))
                        post({action: 'create', groupName: groupName});
                };

                joinGroup = function(groupId) {        
                        post({action: 'join', groupId: groupId});
                };

                var validateGroupName = function(groupName) {
                    var valid = groupName.match(/^[a-zA-Z0-9_]+$/) != null;
                    if(!valid)
                        alert('Group names can only contain letters, numbers ' +
                                'and underscores.');
                    return valid;
                };

                var post = function(fields) {
                    var form = $('<form id="mapForm" method="post"></form>')
                            .attr({ action: url, style: 'display: none;' });
                    for(var key in fields) {
                        if(fields.hasOwnProperty(key))
                            form.append($('<input type="hidden">').attr({
                                name: key, value: fields[key]
                            }));
                    }
                    $('body').append(form);
                    form.submit();
                };
            });
        </script>
</template:main>