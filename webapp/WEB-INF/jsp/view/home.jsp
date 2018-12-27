<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%--@elvariable id="isLogin" type="java.lang.Boolean" --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>首页</title>
</head>
<body>
	<h1>多人聊天室</h1>
	</br>
	<c:if test="${isLogin}">
		<h3>你好，"${sessionScope["userName"] }"</h3>
		<a href="<c:url value="list"/>"> 进入聊天室</a>
		<a href="<c:url value="/logout"/>">注销</a>
	</c:if>
	<c:if test="${isLogin ==false}">
		<a href="<c:url value="/login"/>"> 请先登录！</a>
		</br>
		<a href="<c:url value="/register"/>"><h4>还没有帐号？请注册</h4></a>
	</c:if>
</body>
</html>