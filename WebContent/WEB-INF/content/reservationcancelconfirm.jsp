<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>予約キャンセル確認 | 会議室・備品予約システム</title>
<link rel="stylesheet" href="css/style.css">
</head>
<body>
<jsp:include page="common/header.jsp" />
<div class="container">
	<h1>予約キャンセル確認</h1>
	<div class="field">
		<label class="title">利用日時</label>
		<div class="normal"><c:out value="${reservation.useStartDate}" />&nbsp;<c:out value="${reservation.useStartTime}" />&ndash;<c:out value="${reservation.useEndTime}" />&nbsp;</div>
	</div>
	<div class="field">
		<label class="title">リソース</label>
		<div class="normal"><c:out value="${reservation.resource.resourceName}" />&nbsp;</div>
	</div>
	<div class="field">
		<label class="title">会議名</label>
		<div class="normal"><c:out value="${reservation.meetingName}" />&nbsp;</div>
	</div>
	<div class="field">
		<label class="title">利用人数</label>
		<div class="normal"><c:if test="${reservation.attendanceCount > 0}"><c:out value="${reservation.attendanceCount}" />名</c:if>&nbsp;</div>
	</div>
	<p>この予約をキャンセルしようとしています。</p>
	<p>処理を続行してよろしいですか？</p>
	<form action="reservationcancel" method="post">
		<input type="hidden" name="reservation_id" value="<c:out value="${reservation.reservationId}" />">
		<div class="field">
			<input class="medium button-submit" type="submit" value="キャンセル"><a class="medium button" href="reservationdetail?reservation_id=<c:out value="${reservation.reservationId}" />">戻る</a>
		</div>
	</form>
</div>
</body>
</html>