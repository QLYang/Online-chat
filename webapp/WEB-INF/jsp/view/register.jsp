<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%--@elvariable id="userRegister" type="com.yangql.forms.UserRegister" --%>
<%--@elvariable id="userExist" type="java.lang.Boolean" --%>
<template:form htmlTitle="注册" formType="register">
	<h1>注册</h1>
	<c:choose>
		<c:when test="${userExist}">
			<div class="alert alert-block">
			<button type="button" class="close" data-dismiss="alert">&times;</button>
				<h4>警告</h4>
				用户已存在！
			</div>
		</c:when>
	</c:choose>
	<form:form method="post" modelAttribute="userRegister">
		<form:label path="userName">用户名：</form:label>
		<form:input path="userName" class="input-medium search-query"/>
		</br>
		<form:errors path="userName" />
		</br>

		<form:label path="passWord">密码：</form:label>
		<form:password path="passWord" class="input-medium search-query"/>
		</br>
		<form:errors path="passWord" />
		</br>
		<input type="submit" value="保存" />
	</form:form>

</template:form>