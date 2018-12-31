<%@ tag body-content="scriptless" pageEncoding="UTF-8"
	trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/jsp/base.jspf"%>
<%@ attribute name="htmlTitle" type="java.lang.String"
	rtexprvalue="true" required="true"%>
<%@ attribute name="formType" type="java.lang.String" rtexprvalue="true"
	required="true"%>
<!DOCTYPE html>
<html>

<head>
<link rel="stylesheet"
	href="resource/css/bootstrap.min.css" />
<title><c:out value="${fn:trim(htmlTitle) }" /></title>
</head>
<body>
	<div class="navbar">
		<div class="navbar-inner">
			<a class="brand" href="#">聊天室</a>
		</div>
	</div>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span2">
				<!--边栏内容-->
				<!--导航-->
				<ul class="nav nav-list">
					<li><a href="<c:url value="/"/>">首页</a></li>
					<c:if test="${formType=='login'}">
						<li class="active"><a href="<c:url value="/login"/>">登录</a></li>
						<li><a href="<c:url value="/register"/>">注册</a></li>
					</c:if>
					<c:if test="${formType=='register'}">
						<li><a href="<c:url value="/login"/>">登录</a></li>
						<li class="active"><a href="<c:url value="/register"/>">注册</a></li>
					</c:if>
				</ul>
			</div>

			<div class="span10">
				<!--主体内容-->
				<jsp:doBody></jsp:doBody>
			</div>
		</div>
	</div>

	<script src="http://code.jquery.com/jquery.js"></script>
	<script src="resource/js/bootstrap.min.js"></script>
</body>
</html>