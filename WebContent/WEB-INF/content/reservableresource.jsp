<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>空きリソース検索 | 会議室・備品予約システム</title>
<link rel="stylesheet" href="css/style.css">
</head>
<body>
<jsp:include page="common/header.jsp" />
<div class="container">
	<h1>空きリソース検索</h1>
<c:forEach var="msg" items="${errorMessageList}">
	<p class="warning"><c:out value="${msg}" /></p>
</c:forEach>
	<form action="reservableresource" method="get">
		<div class="field">
			<label class="title" for="start_date">利用日時</label><span class="warning note">必須</span>
			<div class="normal">
				<input type="date" id="start_date" name="start_date" value="<c:out value="${paramStartDate}" />" placeholder="yyyy-mm-dd" required>&nbsp;
				<select id="start_time" name="start_time" required>
					<c:forEach begin="0" end="95" var="option" items="${timeList}"><option value="${option}"<c:if test="${option == paramStartTime}"> selected</c:if>>${option}</option></c:forEach>
				</select>&ndash;
				<select id="end_time" name="end_time" required>
					<c:forEach begin="1" end="96" var="option" items="${timeList}"><option value="${option}"<c:if test="${option == paramEndTime}"> selected</c:if>>${option}</option></c:forEach>
				</select>
			</div>
		</div>
		<div class="field">
			<label class="title" for="hours">利用時間数</label>
			<div class="normal">
				<select id="hours" name="hours" required>
					<c:forEach begin="1" end="96" var="option" items="${timeList}"><option value="${option}"<c:if test="${option == paramHours}"> selected</c:if>>${option}</option></c:forEach>
				</select>
			</div>
		</div>
		<div class="field">
			<label class="title" for="resource_name">リソース名</label>
			<div class="normal">
				<input type="text" id="resource_name" name="resource_name" value="<c:out value="${paramResourceName}" />">
				<span class="note">部分一致検索</span>
			</div>
		</div>
		<div class="field">
			<label class="title" for="category">カテゴリ</label>
			<div class="normal">
				<select id="category" name="category">
					<option></option><c:forEach var="option" items="${categoryList}"><option value="<c:out value="${option.categoryId}" />"<c:if test="${String.valueOf(option.categoryId) == paramCategory}"> selected</c:if>><c:out value="${option.categoryName}" /></option></c:forEach>
				</select>
			</div>
		</div>
		<div class="field">
			<label class="title" for="capacity">定員</label>
			<div class="normal">
				<input type="number" id="capacity" name="capacity" value="<c:out value="${paramCapacity}" />" min="0" max="999">名以上
				<span class="warning note">最大999名</span>
			</div>
			<div class="normal">
				<label><input type="checkbox" id="no_capacity" name="no_capacity" value="on"<c:if test="${'on' == paramNoCapacity}"> checked</c:if>>定員0の備品のみ検索する</label>
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
			<input class="medium button-submit" type="submit" value="検索">
		</div>
	</form>
<c:if test="${not empty availableResourceList}">
	<table class="large list">
		<thead>
			<tr>
				<th class="col-small"></th>
				<th>利用可能日時</th>
				<th class="col-medium">リソース名</th>
				<th class="col-small">事業所</th>
				<th class="col-small">カテゴリ</th>
				<th class="col-small">定員</th>
			</tr>
		</thead>
		<tbody>
	<c:forEach var="availableResource" items="${availableResourceList}">
			<tr>
				<td><form action="reservationregistrator" method="post"><input type="hidden" name="use_start_date" value="<c:out value="${availableResource.availableStartDate}" />"><input type="hidden" name="use_start_time" value="<c:out value="${availableResource.availableStartTime}" />"><input type="hidden" name="use_end_time" value="<c:out value="${availableResource.availableEndTime}" />"><input type="hidden" name="resource" value="<c:out value="${availableResource.resource.resourceId}" />"><input class="large button" type="submit" value="予約"></form></td>
				<td><c:out value="${availableResource.availableStartDate}" />&nbsp;<c:out value="${availableResource.availableStartTime}" />&ndash;<c:out value="${availableResource.availableEndTime}" /></td>
				<td><c:out value="${availableResource.resource.resourceName}" /></td>
				<td><c:out value="${availableResource.resource.office.officeName}" /></td>
				<td><c:out value="${availableResource.resource.category.categoryName}" /></td>
				<td><c:out value="${availableResource.resource.capacity}" /></td>
			</tr>
	</c:forEach>
		</tbody>
	</table>
</c:if>
</div>
</body>
</html>