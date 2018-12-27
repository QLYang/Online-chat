<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%--@elvariable id="pwdIncorrect" type="java.lang.Boolean" --%>
<%--@elvariable id="userDontExist" type="java.lang.Boolean" --%>
<%--@elvariable id="userLogin" type="com.yangql.forms.UserLogin" --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录</title>
</head>
<body>
	<c:choose>
		<c:when test="${pwdIncorrect}">
		密码输入错误！请检查
		</c:when>
		<c:when test="${userDontExist}">
		用户不存在！
		</c:when>
	</c:choose>

	<h2>登录账户</h2>
	<form:form method="post" modelAttribute="userLogin">
		<form:label path="userName">用户名：</form:label>
		<form:input path="userName" />
		</br>
		<form:errors path="userName" />
		</br>

		<form:label path="passWord">密码：</form:label>
		<form:password path="passWord" />
		</br>
		<form:errors path="passWord" />
		</br>
		<input type="submit" value="登录" />
	</form:form>

	<a href="<c:url value="/register"/>"><h4>还没有帐号？请注册</h4></a>
</body>
</html>