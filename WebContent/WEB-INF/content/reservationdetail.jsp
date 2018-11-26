<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>予約詳細 | 会議室・備品予約システム</title>
<link rel="stylesheet" href="css/style.css">
</head>
<body>
<jsp:include page="common/header.jsp" />
<div class="container">
	<h1>予約詳細</h1>
<c:forEach var="msg" items="${errorMessageList}">
	<p class="warning"><c:out value="${msg}" /></p>
</c:forEach>
<c:if test="${not empty message}">
	<p><c:out value="${message}" /></p>
	<c:remove var="message" />
</c:if>
	<div class="field">
		<label class="title">利用日時</label>
		<div class="normal"><c:out value="${reservation.useStartDate}" />&nbsp;<c:out value="${reservation.useStartTime}" />&ndash;<c:out value="${reservation.useEndTime}" />&nbsp;</div>
	</div>
	<div class="field">
		<label class="title">リソース</label>
		<div class="normal"><c:choose><c:when test="${reservation.resource.deleted == 0}"><a href="resourcedetail?resource_id=<c:out value="${reservation.resource.resourceId}" />"><c:out value="${reservation.resource.resourceName}" /></a></c:when><c:otherwise><c:out value="${reservation.resource.resourceName}" /></c:otherwise></c:choose>&nbsp;</div>
	</div>
	<div class="field">
		<label class="title">会議名</label>
		<div class="normal"><c:out value="${reservation.meetingName}" />&nbsp;</div>
	</div>
	<div class="field">
		<label class="title">予約者</label>
		<div class="normal"><c:out value="${reservation.reservator.lastName}" />&nbsp;<c:out value="${reservation.reservator.firstName}" />&nbsp;</div>
	</div>
	<div class="field">
		<label class="title">共同予約者</label>
		<div class="normal"><c:out value="${reservation.coReservator.lastName}" />&nbsp;<c:out value="${reservation.coReservator.firstName}" />&nbsp;</div>
	</div>
	<div class="field">
		<label class="title">利用人数</label>
		<div class="normal"><c:if test="${reservation.attendanceCount > 0}"><c:out value="${reservation.attendanceCount}" />名</c:if>&nbsp;</div>
	</div>
	<div class="field">
		<label class="title">参加者種別</label>
		<div class="normal"><c:out value="${reservation.attendanceType.attendanceTypeName}" />&nbsp;</div>
	</div>
	<div class="field">
		<label class="title">補足</label>
		<div class="normal"><c:out value="${resource.note}" />&nbsp;</div>
	</div>
	<form action="reservationcopy" method="post">
		<div class="field">
			<input type="hidden" name="reservation_id" value="<c:out value="${reservation.reservationId}" />"><input type="hidden" name="use_start_date" value="<c:out value="${reservation.useStartDate}" />"><input type="hidden" name="resource" value="<c:out value="${reservation.resource.resourceId}" />"><input class="medium button" type="submit" value="コピーして予約">
		</div>
	</form>
<c:if test="${loginUser.id == reservation.reservator.id || loginUser.id == reservation.coReservator.id}">
	<div class="field">
		<a class="medium button" href="reservationeditor?reservation_id=<c:out value="${reservation.reservationId}" />">変更</a><a class="medium button-submit" href="reservationcancelconfirm?reservation_id=<c:out value="${reservation.reservationId}" />">キャンセル</a>
	</div>
</c:if>
</div>
</body>
</html>