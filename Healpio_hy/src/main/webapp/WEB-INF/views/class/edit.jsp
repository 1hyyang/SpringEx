<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://kit.fontawesome.com/0aadd0de21.js" crossorigin="anonymous"></script>
<script>
function go(url){
	editForm.action = url;
	editForm.submit();
}

function changeAttach(){
	attachDiv.innerHTML = "";
}
</script>
</head>
<body>
<form name="editForm" method="post" enctype="multipart/form-data">
	강사 이름 <input type="text" name="member_no" id="member_no" value="${classVO.nickname}">
	강의 지역 <br>
	강의 이름 <input type="text" name="class_title" id="class_title" value="${classVO.class_title}">
	<select name="exercise_no" id="exercise_no">
	<c:forEach items="${exerciseList}" var="exercise">
		<c:if test="${exercise.exercise_name eq classVO.exercise_name}">
		<option value="${exercise.exercise_no}" selected>${exercise.exercise_name}</option>
		</c:if>
		<c:if test="${exercise.exercise_name ne classVO.exercise_name}">
		<option value="${exercise.exercise_no}">${exercise.exercise_name}</option>
		</c:if>
	</c:forEach>
	</select><br>
	대표 사진 <input type="file" name="files" id="files" multiple onchange="changeAttach()"><br>
	<div id="attachDiv">
	<c:forEach items="${attachList}" var="attachVO">
		${attachVO.filename}
	</c:forEach>
	</div>
	최대 수강 인원 <input type="number" name="class_maxcount" id="class_maxcount" value="${classVO.class_maxcount}"><br>
	한 줄 소개 <input type="text" name="class_introduce" id="class_introduce" value="${classVO.class_introduce}"><br>
	상세 소개 <input type="text" name="class_content" id="class_content" value="${classVO.class_content}"><br>
	수강료 <input type="text" name="class_price" id="class_price" value="${classVO.class_price}"><br>
	강사 소개 <input type="text" name="teacher_content" id="teacher_content" value="${classVO.teacher_content}"><br>
	<button type="submit" onclick="go('/class/edit?class_no=${classVO.class_no}')">수정하기</button>
	<button type="button" onclick="#">취소</button>
</form>
</body>
</html>