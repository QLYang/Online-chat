<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%--@elvariable id="isLogin" type="java.lang.Boolean" --%>
<template:main htmlTitle="主页" isLogin="${isLogin }">
	<h1>多人聊天室</h1>
	</br>
	<c:if test="${isLogin}">
		<h3>你好，"${sessionScope["userName"] }"</h3>
		<a href="<c:url value="/list"/>"> <h4>进入聊天室</h4></a>
	</c:if>
	<c:if test="${isLogin ==false}">
		<h2>请先登录！</h2>
		</br>
		<a href="<c:url value="/register"/>"><h4>还没有帐号？请注册</h4></a>
	</c:if>
</template:main>