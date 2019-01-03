$(document).ready(function() {
    var log = console.log.bind(console)

    var modalError = $("#modalError")
    var modalErrorBody = $("#modalErrorBody")
    var chatLog = $('#chatLog')
    var messageArea = $('#messageArea')
    var otherJoined = false;
    var userContainer = $('#userList')

    var memberList = jQuery.parseJSON(document.getElementById('groupMemberList').value)
    var groupName=document.getElementById('groupName').value
    var userName = document.getElementById('userName').value
    var groupId=document.getElementById('groupId').value
    var action=document.getElementById('action').value
    
    // 浏览器是否支持websocket
    if (!("WebSocket" in window)) {
        modalErrorBody.text('WebSockets are not supported in this ' + 'browser. Try Internet Explorer 10 or the latest ' + 'versions of Mozilla Firefox or Google Chrome.')
        modalError.modal('show')
        return
    }
    // 工具函数
    // 打印控制类消息
    var infoMessage = function(m) {
        chatLog.append($('<div>').addClass('informational').text(m))
        $("#chatLog").scrollTop($("#chatLog")[0].scrollHeight) // 滚动条自动到底部
    }
    // 显示到达消息
    var objectMessage = function(message) {
        var log = $('<div>')
        var date = message.timeStamp;
        if (message.username != null) {
            var c = message.username == userName ? 'user-me' : 'user-you' // 样式
            log.append($('<span>').addClass(c).text(date + ' ' + message.username + ':\xA0')) // 添加时间：用户名
                .append($('<span>').text(message.content)) // 添加内容
        } else {
            log.addClass(message.type == 'ERROR' ? 'error' : 'informational').text(date + ' ' + message.content)
        }
        chatLog.append(log) // 输出文本
        $("#chatLog").scrollTop($("#chatLog")[0].scrollHeight) // 滚动条自动到底部
    }
    // 在userContainer表格中添加一行，不直接使用
    var appendUser = function(username) { //
        var user = $('<tr>')
        user.append($('<td>').text(username))
        userContainer.append(user)
    }
    // 清除userContainer中的内容，不直接使用
    var clearForm = function() {
        userContainer.empty()
    }
    // 处理到达的userList中的用户名，不直接使用
    var appendUserOfUserList = function() {
        var list = memberList
        for (var i of list) {
            appendUser(i)
        }
    }
    // 刷新用户列表，直接使用
    var flushUserList = function() { // 直接使用
        clearForm()
        appendUserOfUserList()
    }
    // 删除userlist中的指定元素username
    var deleteUserFromUserList = function(e) { // e为字符串
        var listLen = memberList.length
        var index = 0
        for (var i of memberList) { // 找到该元素的index
            if (i == e) {
                break
            }
            index++
        }
        var restNums = listLen - index - 1 // 剩余元素的数量
        while (restNums) { // 将该元素后的所有元素向前移动一位
            memberList[index] = memberList[index + 1]
            index++
            restNums--
        }
        memberList.pop() // 删除最后一个元素
    }
    // 连接服务器
    var server
    try {
        server = new WebSocket('ws://' + window.location.host + '/ChatServer/chat/'+groupId+'/'+userName + "/?action=" + action)
    } catch (error) {
        modalErrorBody.text(error)
        modalError.modal('show')
        return
    }
    // websocket连接开启后
    server.onopen = function(event) {
        infoMessage("用户 " + userName + '已连接服务器.');
        flushUserList()
    }
    // websocket连接关闭
    server.onclose = function(event) {
        if (server != null) infoMessage('连接关闭.')
        server = null
        if (!event.wasClean || event.code != 1000) {
            modalErrorBody.text('错误码 ' + event.code + ': ' + event.reason)
            modalError.modal('show')
        }
    }
    // 发生错误
    server.onerror = function(event) {
        modalErrorBody.text(event.data)
        modalError.modal('show')
    }
    // 从ws连接中得到消息
    server.onmessage = function(event) {
        var message = JSON.parse(event.data)
        if (message.type == 'JOINED') {
            if (message.username != userName) {
                objectMessage(message)
                memberList.push(message.username)
                flushUserList()
            }
        } else if (message.type == 'TEXT') {
            objectMessage(message);
        } else if (message.type == 'LEFT') {
            objectMessage(message);
            deleteUserFromUserList(message.username)
            flushUserList()
        } else {
            modalErrorBody.text('未知数据类型 [' + typeof(event.data) + '].')
            modalError.modal('show')
        }
    }
    // 按钮操作
    send = function() {
        if (server == null) {
            modalErrorBody.text('您未连接到服务器!');
            modalError.modal('show');
        } else if (messageArea.get(0).value.trim().length > 0) {
            var message = {
                timeStamp: null, // 注意，时间戳在服务器端设置
                type: 'TEXT',
                username: userName,
                content: messageArea.get(0).value,
                groupName: groupName
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
    // 断开连接
    disconnect = function() {
        if (server != null) {
            infoMessage('断开连接.');
            server.close();
            server = null;
        }
    };
    window.onbeforeunload = disconnect;
	}) 