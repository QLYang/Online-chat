<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%--@elvariable id="pwdIncorrect" type="java.lang.Boolean" --%>
<%--@elvariable id="userDontExist" type="java.lang.Boolean" --%>
<%--@elvariable id="userLogin" type="com.yangql.forms.UserLogin" --%>
<template:form htmlTitle="登录" formType="login">
	<c:choose>
		<c:when test="${pwdIncorrect}">
			<div class="alert alert-block">
			<button type="button" class="close" data-dismiss="alert">&times;</button>
				<h4>警告</h4>
				密码输入错误！请检查
			</div>

		</c:when>
		<c:when test="${userDontExist}">
			<div class="alert alert-block">
			<button type="button" class="close" data-dismiss="alert">&times;</button>
				<h4>警告</h4>
				用户不存在！
			</div>
		</c:when>
	</c:choose>

	<h2>登录账户</h2>
	<form:form method="post" modelAttribute="userLogin">
		<form:label path="userName" >用户名：</form:label>
		<form:input path="userName" class="input-medium search-query" />
		</br>
		<form:errors path="userName" />
		</br>
		<form:label path="passWord">密码：</form:label>
		<form:password path="passWord" class="input-medium search-query" />
		</br>
		<form:errors path="passWord" />
		</br>
		<input type="submit" value="登录" />
		</br>
		<a href="<c:url value="/register"/>"><h4>还没有帐号？请注册</h4></a>
	</form:form>
</template:form>