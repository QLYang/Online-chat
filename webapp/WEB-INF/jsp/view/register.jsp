<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%--@elvariable id="userRegister" type="com.yangql.forms.UserRegister" --%>
<%--@elvariable id="userExist" type="java.lang.Boolean" --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>注册</title>
</head>
<body>
	<h1>注册</h1>
	<c:choose>
		<c:when test="${userExist}">
		用户已存在！
		</c:when>
	</c:choose>
	<form:form method="post" modelAttribute="userRegister">
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
		<input type="submit" value="保存" />
	</form:form>

</body>
</html>