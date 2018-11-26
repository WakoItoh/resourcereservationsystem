<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>今すぐ予約 | 会議室・備品予約システム</title>
<link rel="stylesheet" href="css/style.css">
</head>
<body>
<jsp:include page="common/header.jsp" />
<div class="container">
	<h1>今すぐ予約</h1>
<c:forEach var="msg" items="${errorMessageList}">
	<p class="warning"><c:out value="${msg}" /></p>
</c:forEach>
	<h2>空きリソース検索</h2>
	<form action="reservationnow" method="post">
		<div class="field">
			<label class="title" for="capacity">利用人数</label><span class="warning note">必須</span>
			<div class="normal">
				<input type="number" id="capacity" name=capacity value="<c:out value="${paramCapacity}" />" min="0" max="999" required>名
				<span class="warning note">最大999名</span>
				<span class="note">定員が利用人数以上のリソースを予約する</span>
			</div>
			<div class="normal">
				<label><input type="checkbox" id="no_capacity" name="no_capacity" value="on"<c:if test="${'on' == paramNoCapacity}"> checked</c:if>>定員0の備品を予約する</label>
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
			<input class="small button-submit" type="submit" value="検索">
		</div>
	</form>
<c:if test="${not empty availableResourceList}">
	<h2>予約入力</h2>
	<form action="reservationregisternow" method="post">
		<div class="field">
			<label class="title" for="use_start_date">利用日時</label><span class="warning note">変更不可</span>
			<div class="normal">
				<input type="hidden" id="use_start_date" name="use_start_date" value="<c:out value="${availableResource.availableStartDate}" />"><c:out value="${availableResource.availableStartDate}" />&nbsp;
				<input type="hidden" id="use_start_time" name="use_start_time" value="<c:out value="${availableResource.availableStartTime}" />"><c:out value="${availableResource.availableStartTime}" />&ndash;
				<input type="hidden" id="use_end_time" name="use_end_time" value="<c:out value="${availableResource.availableEndTime}" />"><c:out value="${availableResource.availableEndTime}" />
			</div>
		</div>
		<div class="field">
			<label class="title" for="resource">リソース</label><span class="warning note">必須</span>
			<div class="normal">
	<c:forEach var="availableResource" items="${availableResourceList}">
				<label><input type="radio" id="resource" name="resource" value="<c:out value="${availableResource.resource.resourceId}" />"<c:if test="${String.valueOf(availableResource.resource.resourceId) == paramResource}"> checked</c:if>><c:out value="${availableResource.resource.resourceName}" /></label>
	</c:forEach>
			</div>
		</div>
		<div class="field">
			<label class="title" for="meeting_name">会議名</label><span class="warning note">変更不可</span>
			<div class="normal">
				<input type="hidden" id="meeting_name" name="meeting_name" value="<c:out value="${loginUser.lastName}" /><c:out value="${loginUser.firstName}" />の今すぐ会議">
				<c:out value="${loginUser.lastName}" /><c:out value="${loginUser.firstName}" />の今すぐ会議
			</div>
		</div>
		<div class="field">
			<label class="title" for="reservator">予約者</label><span class="warning note">変更不可</span>
			<div class="normal">
				<input type="hidden" id="reservator" name="reservator" value="<c:out value="${loginUser.id}" />">
				<c:out value="${loginUser.lastName}" />&nbsp;<c:out value="${loginUser.firstName}" />
			</div>
		</div>
		<div class="field">
			<label class="title" for="attendance_count">利用人数</label><span class="warning note">変更不可</span>
			<div class="normal">
				<input type="hidden" id="attendance_count" name="attendance_count" value="<c:out value="${paramAttendanceCount}" />"><c:out value="${paramAttendanceCount}" />名
			</div>
		</div>
		<div class="field">
			<input type="hidden" id="capacity" name="capacity" value="<c:out value="${paramCapacity}" />">
			<c:if test="${'on' == paramNoCapacity}"><input type="hidden" id="no_capacity" name="no_capacity" value="on"></c:if>
			<input type="hidden" id="office" name="office" value="<c:out value="${paramOffice}" />">
			<input class="medium button-submit" type="submit" value="予約">
		</div>
	</form>
</c:if>
</div>
</body>
</html>