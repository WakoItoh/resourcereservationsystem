<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>予約入力 | 会議室・備品予約システム</title>
<link rel="stylesheet" href="css/style.css">
</head>
<body>
<jsp:include page="common/header.jsp" />
<div class="container">
	<h1>予約入力</h1>
<c:forEach var="msg" items="${errorMessageList}">
	<p class="warning"><c:out value="${msg}" /></p>
</c:forEach>
	<form action="reservationedit" method="post">
		<div class="field">
			<label class="title" for="use_start_date">利用日時</label><span class="warning note">必須</span>
			<div class="normal">
				<input type="date" id="use_start_date" name="use_start_date" value="<c:out value="${paramUseStartDate}" />" placeholder="yyyy-mm-dd" required>&nbsp;
				<select id="use_start_time" name="use_start_time" required>
					<option>--:--</option><c:forEach begin="0" end="95" var="useStartTime" items="${timeList}"><option value="${useStartTime}"<c:if test="${useStartTime == paramUseStartTime}"> selected</c:if>>${useStartTime}</option></c:forEach>
				</select>&ndash;
				<select id="use_end_time" name="use_end_time" required>
					<option>--:--</option><c:forEach begin="1" end="96" var="useEndTime" items="${timeList}"><option value="${useEndTime}"<c:if test="${useEndTime == paramUseEndTime}"> selected</c:if>>${useEndTime}</option></c:forEach>
				</select>
			</div>
			<div class="normal">
				<table class="time">
					<thead>
						<tr>
							<th colspan="4">00</th>
							<th colspan="4">01</th>
							<th colspan="4">02</th>
							<th colspan="4">03</th>
							<th colspan="4">04</th>
							<th colspan="4">05</th>
							<th colspan="4">06</th>
							<th colspan="4">07</th>
							<th colspan="4">08</th>
							<th colspan="4">09</th>
							<th colspan="4">10</th>
							<th colspan="4">11</th>
							<th colspan="4">12</th>
							<th colspan="4">13</th>
							<th colspan="4">14</th>
							<th colspan="4">15</th>
							<th colspan="4">16</th>
							<th colspan="4">17</th>
							<th colspan="4">18</th>
							<th colspan="4">19</th>
							<th colspan="4">20</th>
							<th colspan="4">21</th>
							<th colspan="4">22</th>
							<th colspan="4">23</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<c:forEach begin="0" end="95" var="time" items="${timeList}"><td<c:forEach var="availableResource" items="${availableResourceList}"><c:if test="${availableResource.availableStartTime <= time && time < availableResource.availableEndTime}"> class="available"</c:if></c:forEach><c:if test="${paramUseStartDate == reservation.useStartDate && reservation.useStartTime <= time && time < reservation.useEndTime}"> class="use"</c:if>></td></c:forEach>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		<div class="field">
			<label class="title" for="resource">リソース</label><span class="warning note">変更不可</span>
			<div class="normal">
				<input type="hidden" id="resource" name="resource" value="<c:out value="${reservation.resource.resourceId}" />">
				<c:out value="${reservation.resource.resourceName}" />
			</div>
		</div>
		<div class="field">
			<label class="title" for="meeting_name">会議名</label><span class="warning note">必須</span>
			<div class="normal">
				<input type="text" id="meeting_name" name="meeting_name" value="<c:out value="${paramMeetingName}" />" required>
			</div>
		</div>
		<div class="field">
			<label class="title" for="reservator">予約者</label><span class="warning note">変更不可</span>
			<div class="normal">
				<input type="hidden" id="reservator" name="reservator" value="<c:out value="${reservation.reservator.id}" />">
				<c:out value="${reservation.reservator.lastName}" />&nbsp;<c:out value="${reservation.reservator.firstName}" />
			</div>
		</div>
		<div class="field">
			<label class="title" for="co_reservator">共同予約者</label>
			<div class="normal">
				<select id="co_reservator" name="co_reservator">
					<option></option><c:forEach var="user" items="${userList}"><option value="<c:out value="${user.id}" />"<c:if test="${user.id == paramCoReservator}"> selected</c:if>><c:out value="${user.lastName}" />&nbsp;<c:out value="${user.firstName}" /></option></c:forEach>
				</select>
			</div>
		</div>
		<div class="field">
			<label class="title" for="attendance_count">利用人数</label><span class="warning note">必須</span>
			<div class="normal">
				<input type="number" id="attendance_count" name="attendance_count" value="<c:out value="${paramAttendanceCount}" />" min="0" max="999" required>名
				<span class="warning note">最大999名</span>
			</div>
		</div>
		<div class="field">
			<label class="title" for="attendance_type">参加者種別</label>
			<div class="normal">
				<select id="attendance_type" name="attendance_type">
					<option></option><c:forEach var="attendanceType" items="${attendanceTypeList}"><option value="<c:out value="${attendanceType.attendanceTypeId}" />"<c:if test="${String.valueOf(attendanceType.attendanceTypeId) == paramAttendanceType}"> selected</c:if>><c:out value="${attendanceType.attendanceTypeName}" /></option></c:forEach>
				</select>
			</div>
		</div>
		<div class="field">
			<label class="title" for="note">補足</label>
			<div class="normal">
				<textarea id="note" name="note" rows="" cols=""><c:out value="${paramNote}" /></textarea>
				<span class="warning note">最大500文字</span>
			</div>
		</div>
		<div class="field">
			<input type="hidden" name="reservation_id" value="<c:out value="${reservation.reservationId}" />">
			<input class="medium button-submit" type="submit" value="変更"><a class="medium button" href="reservationdetail?reservation_id=<c:out value="${reservation.reservationId}" />">戻る</a>
		</div>
	</form>
</div>
</body>
</html>