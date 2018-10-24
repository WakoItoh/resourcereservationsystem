<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>会議室・備品予約システム</title>
<link rel="stylesheet" href="css/style.css">
</head>
<body>
<div class="small-container">
	<h1>会議室・備品予約システム</h1>
<c:forEach var="msg" items="${errorMessageList}">
	<p class="warning"><c:out value="${msg}" /></p>
</c:forEach>
<c:if test="${not empty message}">
	<p><c:out value="${message}" /></p>
	<c:remove var="message" />
</c:if>
	<form action="login" method="post">
		<div class="field">
			<input class="large" type="text" name="id" value="" placeholder="ID" required>
		</div>
		<div class="field">
			<input class="large" type="password" name="password" value="" placeholder="パスワード" required>
		</div>
		<div class="field">
			<input class="large button-submit" type="submit" value="ログイン">
		</div>
	</form>
</div>
</body>
</html>