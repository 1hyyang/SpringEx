<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script>
function go(url){
	location.href = url;
}
</script>
</head>
<body>
${classVO.class_title}<br>
${classVO.nickname}<br>
${classVO.exercise_name}<br>
${classVO.class_introduce}<br>
${classVO.class_maxcount}<br>
${classVO.class_content}<br>
${classVO.class_price}<br>
${classVO.teacher_content}<br>
<button type="button" onclick="go('/class/edit?class_no=${classVO.class_no}')">수정</button>
</body>
</html>