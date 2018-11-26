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
	<p>このリソースを削除しようとしています。</p>
<c:if test="${not empty reservationList}">
	<c:if test="${not empty message}">
	<p><c:out value="${message}" /></p>
	</c:if>
	<table class="large list">
		<thead>
			<tr>
				<th>予約日時</th>
				<th class="col-medium">会議名</th>
				<th class="col-medium">予約者</th>
				<th class="col-medium">共同予約者</th>
			</tr>
		</thead>
		<tbody>
	<c:forEach var="reservation" items="${reservationList}">
			<tr>
				<td><c:out value="${reservation.useStartDate}" />&nbsp;<c:out value="${reservation.useStartTime}" />&ndash;<c:out value="${reservation.useEndTime}" /></td>
				<td><a href="reservationdetail?reservation_id=<c:out value="${reservation.reservationId}" />"><c:out value="${reservation.meetingName}" /></a></td>
				<td><c:out value="${reservation.reservator.lastName}" />&nbsp;<c:out value="${reservation.reservator.firstName}" /></td>
				<td><c:out value="${reservation.coReservator.lastName}" />&nbsp;<c:out value="${reservation.coReservator.firstName}" /></td>
			</tr>
	</c:forEach>
		</tbody>
	</table>
</c:if>
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