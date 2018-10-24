<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>リソース入力 | 会議室・備品予約システム</title>
<link rel="stylesheet" href="css/style.css">
</head>
<body>
<jsp:include page="common/header.jsp" />
<div class="container">
<h1>リソース入力</h1>
<c:forEach var="msg" items="${errorMessageList}">
	<p class="warning"><c:out value="${msg}" /></p>
</c:forEach>
	<form action="resourceedit" method="post">
		<div class="field">
			<label class="title" for="resource_name">リソース名</label><span class="warning note">必須</span>
			<div class="normal">
				<input type="text" id="resource_name" name="resource_name" value="<c:out value="${paramResourceName}" />" required>
				<span class="warning note">最大30文字</span>
			</div>
		</div>
		<div class="field">
			<label class="title" for="category">カテゴリ</label><span class="warning note">必須</span>
			<div class="normal">
				<select id="category" name="category" required>
					<option></option><c:forEach var="option" items="${categoryList}"><option value="<c:out value="${option.categoryId}" />"<c:if test="${String.valueOf(option.categoryId) == paramCategory}"> selected</c:if>><c:out value="${option.categoryName}" /></option></c:forEach>
				</select>
			</div>
		</div>
		<div class="field">
			<label class="title" for="capacity">定員</label><span class="warning note">必須</span>
			<div class="normal">
				<input type="number" id="capacity" name="capacity" value="<c:out value="${paramCapacity}" />" min="0" max="999" required>名
				<span class="warning note">最大999名、備品の場合は0で入力してください</span>
			</div>
		</div>
		<div class="field">
			<label class="title" for="office">事業所</label><span class="warning note">必須</span>
			<div class="normal">
				<select id="office" name="office" required>
					<option></option><c:forEach var="option" items="${officeList}"><option value="<c:out value="${option.officeId}" />"<c:if test="${String.valueOf(option.officeId) == paramOffice}"> selected</c:if>><c:out value="${option.officeName}" /></option></c:forEach>
				</select>
			</div>
		</div>
		<div class="field">
			<label class="title" for="property">特性</label>
			<div class="normal">
				<c:forEach var="option" items="${propertyList}"><label><input id="property" type="checkbox" name="property" value="<c:out value="${option.propertyId}" />"<c:forEach var="paramProperty" items="${paramProperties}"><c:if test="${String.valueOf(option.propertyId) == paramProperty}"> checked</c:if></c:forEach>><c:out value="${option.propertyName}" /></label></c:forEach>
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
			<label class="title" for="suspend_start_date">利用停止開始日時</label>
			<div class="normal">
				<input type="date" id="suspend_start_date" name="suspend_start_date" value="<c:out value="${paramSuspendStartDate}" />" placeholder="yyyy-mm-dd">
				<input type="time" id="suspend_start_time" name="suspend_start_time" value="<c:out value="${paramSuspendStartTime}" />" placeholder="hh:mm">
				<span class="warning note">終了日時と合わせて入力してください</span>
			</div>
		</div>
		<div class="field">
			<label class="title" for="suspend_end_date">利用停止終了日時</label>
			<div class="normal">
				<input type="date" id="suspend_end_date" name="suspend_end_date" value="<c:out value="${paramSuspendEndDate}" />" placeholder="yyyy-mm-dd">
				<input type="time" id="suspend_end_time" name="suspend_end_time" value="<c:out value="${paramSuspendEndTime}" />" placeholder="hh:mm">
				<span class="warning note">開始日時と合わせて入力してください</span>
			</div>
		</div>
		<div class="field">
			<input type="hidden" name="resource_id" value="<c:out value="${resourceId}" />">
			<input class="medium button-submit" type="submit" value="変更"><a class="medium button" href="resourcedetail?resource_id=<c:out value="${resourceId}" />">戻る</a>
		</div>
	</form>
</div>
</body>
</html>