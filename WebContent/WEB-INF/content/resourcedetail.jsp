<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>リソース詳細 | 会議室・備品予約システム</title>
<link rel="stylesheet" href="css/style.css">
</head>
<body>
<jsp:include page="common/header.jsp" />
<div class="container">
	<h1>リソース詳細</h1>
<c:forEach var="msg" items="${errorMessageList}">
	<p class="warning"><c:out value="${msg}" /></p>
</c:forEach>
<c:if test="${not empty message}">
	<p><c:out value="${message}" /></p>
	<c:remove var="message" />
</c:if>
	<div class="field">
		<label class="title">リソース名</label>
		<div class="normal"><c:out value="${resource.resourceName}" />&nbsp;</div>
	</div>
	<div class="field">
		<label class="title">カテゴリ</label>
		<div class="normal"><c:out value="${resource.category.categoryName}" />&nbsp;</div>
	</div>
	<div class="field">
		<label class="title">定員</label>
		<div class="normal"><c:if test="${resource.capacity > 0}"><c:out value="${resource.capacity}" />名</c:if>&nbsp;</div>
	</div>
	<div class="field">
		<label class="title">事業所</label>
		<div class="normal"><c:out value="${resource.office.officeName}" />&nbsp;</div>
	</div>
	<div class="field">
		<label class="title">特性</label>
		<div class="normal"><c:forEach var="property" items="${resource.propertyList}"><c:out value="${property.propertyName}" />&nbsp;</c:forEach>&nbsp;</div>
	</div>
	<div class="field">
		<label class="title">補足</label>
		<div class="normal"><c:out value="${resource.note}" />&nbsp;</div>
	</div>
	<div class="field">
		<label class="title">利用停止開始日時</label>
		<div class="normal"><c:out value="${resource.suspendStartDate}" />&nbsp;<c:out value="${resource.suspendStartTime}" />&nbsp;</div>
	</div>
	<div class="field">
		<label class="title">利用停止終了日時</label>
		<div class="normal"><c:out value="${resource.suspendEndDate}" />&nbsp;<c:out value="${resource.suspendEndTime}" />&nbsp;</div>
	</div>
<c:if test="${loginUser.userLevel == 2 && resource.deleted == 0}">
	<div class="field">
		<a class="medium button" href="resourceeditor?resource_id=<c:out value="${resource.resourceId}" />">変更</a><a class="medium button-submit" href="resourcedeleteconfirm?resource_id=<c:out value="${resource.resourceId}" />">削除</a>
	</div>
</c:if>
</div>
</body>
</html>