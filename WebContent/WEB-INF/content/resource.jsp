<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>リソース一覧 | 会議室・備品予約システム</title>
<link rel="stylesheet" href="css/style.css">
</head>
<body>
<jsp:include page="common/header.jsp" />
<div class="container">
	<h1>リソース一覧</h1>
	<div class="field">
		<a class="medium button" href="resourceregistrator">リソース登録</a>
	</div>
<c:forEach var="msg" items="${errorMessageList}">
	<p class="warning"><c:out value="${msg}" /></p>
</c:forEach>
<c:if test="${not empty message}">
	<p><c:out value="${message}" /></p>
	<c:remove var="message" />
</c:if>
<c:if test="${not empty resourceList}">
	<table class="large list">
		<thead>
			<tr>
				<th class="col-medium">リソース名</th>
				<th class="col-small">事業所</th>
				<th class="col-small">カテゴリ</th>
				<th class="col-small">定員</th>
				<th>補足</th>
				<th class="col-small">ステータス</th>
			</tr>
		</thead>
		<tbody>
	<c:forEach var="resource" items="${resourceList}">
			<tr>
				<td><a href="resourcedetail?resource_id=<c:out value="${resource.resourceId}" />"><c:out value="${resource.resourceName}" /></a></td>
				<td><c:out value="${resource.office.officeName}" /></td>
				<td><c:out value="${resource.category.categoryName}" /></td>
				<td><c:if test="${resource.capacity > 0}"><c:out value="${resource.capacity}" />名</c:if></td>
				<td><c:out value="${resource.note}" /></td>
				<td><c:choose><c:when test="${resource.status == 1}">利用可能</c:when><c:when test="${resource.status == 2}">利用停止</c:when><c:otherwise>削除済み</c:otherwise></c:choose></td>
			</tr>
	</c:forEach>
		</tbody>
	</table>
</c:if>
</div>
</body>
</html>