<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>システムエラー | 会議室・備品予約システム</title>
<link rel="stylesheet" href="css/style.css">
</head>
<body>
<div class="small-container">
	<h1>システムエラー</h1>
<c:forEach var="msg" items="${errorMessageList}">
	<p class="warning"><c:out value="${msg}" /></p>
</c:forEach>
	<a class="large button" href="index.jsp">戻る</a>
</div>
</body>
</html>