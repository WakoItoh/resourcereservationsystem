<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>予約一覧 | 会議室・備品予約システム</title>
<link rel="stylesheet" href="css/style.css">
</head>
<body>
<jsp:include page="common/header.jsp" />
<div class="container">
	<h1>予約一覧</h1>
	<div class="field">
		<a class="medium button" href="reservableresource">予約</a>
	</div>
<c:forEach var="msg" items="${errorMessageList}">
	<p class="warning"><c:out value="${msg}" /></p>
</c:forEach>
<c:if test="${not empty message}">
	<p><c:out value="${message}" /></p>
	<c:remove var="message" />
</c:if>
	<form action="reservation" method="get">
		<div class="field">
			<label class="title" for="use_start">予約日</label>
			<div class="normal">
				<input type="date" id="use_start" name="use_start" value="<c:out value="${paramUseStart}" />" placeholder="yyyy-mm-dd">&ndash;
				<input type="date" id="use_end" name="use_end" value="<c:out value="${paramUseEnd}" />" placeholder="yyyy-mm-dd">
				<span class="note">範囲検索</span>
			</div>
		</div>
		<div class="field">
			<label class="title" for="meeting_name">会議名</label>
			<div class="normal">
				<input type="text" id="meeting_name" name="meeting_name" value="<c:out value="${paramMeetingName}" />">
				<span class="note">部分一致検索</span>
			</div>
		</div>
		<div class="field">
			<label class="title" for="office">事業所</label>
			<div class="normal">
				<select id="office" name="office">
					<option></option><c:forEach var="option" items="${officeList}"><option value="<c:out value="${option.officeId}" />"<c:if test="${String.valueOf(option.officeId) == paramOffice}"> selected</c:if>><c:out value="${option.officeName}" /></option></c:forEach>
				</select>
			</div>
		</div>
		<div class="field">
			<div class="normal">
				<label><input type="checkbox" name="all_reservator" value="on"<c:if test="${'on' == paramAllReservator}"> checked</c:if>>自分以外の予約も表示する</label>
			</div>
		</div>
		<div class="field">
			<div class="normal">
				<label><input type="checkbox" name="ended" value="on"<c:if test="${'on' == paramEnded}"> checked</c:if>>終了した予約も表示する</label>
			</div>
		</div>
		<div class="field">
			<input class="medium button-submit" type="submit" value="検索">
		</div>
	</form>
<c:if test="${not empty reservationList}">
	<table class="large list">
		<thead>
			<tr>
				<th>予約日時</th>
				<th class="col-medium">会議名</th>
				<th class="col-medium">リソース名</th>
				<th class="col-small">事業所</th>
				<th class="col-small">予約者</th>
			</tr>
		</thead>
		<tbody>
	<c:forEach var="reservation" items="${reservationList}">
			<tr>
				<td><c:out value="${reservation.useStartDate}" />&nbsp;<c:out value="${reservation.useStartTime}" />&ndash;<c:out value="${reservation.useEndTime}" /></td>
				<td><a href="reservationdetail?reservation_id=<c:out value="${reservation.reservationId}" />"><c:out value="${reservation.meetingName}" /></a></td>
				<td><c:out value="${reservation.resource.resourceName}" /></td>
				<td><c:out value="${reservation.resource.office.officeName}" /></td>
				<td><c:out value="${reservation.reservator.lastName}" />&nbsp;<c:out value="${reservation.reservator.firstName}" /></td>
			</tr>
	</c:forEach>
		</tbody>
	</table>
</c:if>
</div>
</body>
</html>