<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%--@elvariable id="activeGroups" type="java.util.Map<long, com.yangql.site.chat.ChatServer.ChatGroup>" --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>聊天室</title>
</head>
<body>
	<h1>活跃列表</h1>
	<c:choose>
		<c:when test="${fn:length(activeGroups) == 0}">
			<i>无活跃房间 </i>
		</c:when>
		<c:otherwise>
			<c:forEach items="${activeGroups}" var="e">
				<a href="javascript:void 0;" onclick="joinGroup(${e.key});">房间名称:
					${e.value.chat.groupName}</a>
				<br />
			</c:forEach>
		</c:otherwise>
	</c:choose>
	</br>
	<a href="javascript:void 0;" onclick="createGroup();">创建新房间</a>
	<br />
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
</body>
</html>