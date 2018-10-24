<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<head>
<meta charset="UTF-8">
<title>リソース削除確認 | 会議室・備品予約システム</title>
<link rel="stylesheet" href="css/style.css">
</head>
<body>
<jsp:include page="common/header.jsp" />
<div class="container">
	<h1>リソース削除確認</h1>
	<div class="field">
		<label class="title">リソース名</label>
		<div class="normal"><c:out value="${resource.resourceName}" />&nbsp;</div>
	</div>
	<div class="field">
		<label class="title">利用停止開始日時</label>
		<div class="normal"><c:out value="${resource.suspendStart}" />&nbsp;</div>
	</div>
	<div class="field">
		<label class="title">利用停止終了日時</label>
		<div class="normal"><c:out value="${resource.suspendEnd}" />&nbsp;</div>
	</div>
	<p>このリソースを削除しようとしています。</p>
	<p>処理を続行してよろしいですか？</p>
	<form action="resourcedelete" method="post">
		<input type="hidden" name="resource_id" value="<c:out value="${resource.resourceId}" />">
		<div class="field">
			<input class="medium button-submit" type="submit" value="削除"><a class="medium button" href="resourcedetail?resource_id=<c:out value="${resource.resourceId}" />">戻る</a>
		</div>
	</form>
</div>
</body>
</html>