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
	location.href = url;
}

function scrap(class_no){
	fetchGet('/class/scrap?class_no=' + class_no, getFullheart);
}

function cancelScrap(class_no){
	fetchGet('/class/cancelScrap?class_no=' + class_no, getEmptyheart);
}

function fetchGet(url, callback){
	try{
		fetch(url)
		// 컨트롤러로부터 JSON 타입의 객체가 반환
		// 객체를 변수명 response에 받아 와서 json() 메소드를 호출
		// json() : JSON 형식의 문자열을 Promise 객체로 반환
		// Promise 객체는 then() 메소드를 사용하여 
		// 비동기 작업의 성공 또는 실패와 관련된 결과를 나타내는 대리자 역할을 수행
		.then(response => response.json())
		// 반환 받은 객체를 매개 변수로 받는 콜백 함수를 호출
		.then(map => callback(map));		
	} catch(e){
		console.log('fetchGet', e)
	}
}

function getFullheart(map){
	if(map.result=='success'){
		scrapDiv.innerHTML = `<i class="fa-solid fa-heart" style="color: #ff6666" onclick="cancelScrap('${classVO.class_no}')"></i><br>`;
	} else {
		alert(map.result);
	}
}

function getEmptyheart(map){
	if(map.result=='success'){
		scrapDiv.innerHTML = `<i class="fa-regular fa-heart" style="color: #ff6666" onclick="scrap('${classVO.class_no}')"></i><br>`;
	} else {
		alert(map.result);
	}
}
</script>
</head>
<body>
<img src='/resources/images/${attachVO.filepath}' alt='${classVO.class_title}' width='300px'><br>
${classVO.class_title}<br>
${classVO.nickname}<br>
${classVO.exercise_name}<br>
${classVO.class_introduce}<br>
<div id="scrapDiv">
	<c:if test="${scrapYN==0}">
	<i class="fa-regular fa-heart" style="color: #ff6666" onclick="scrap('${classVO.class_no}')"></i>
	</c:if>
	<c:if test="${scrapYN>0}">
	<i class="fa-solid fa-heart" style="color: #ff6666" onclick="cancelScrap('${classVO.class_no}')"></i>
	</c:if>
</div>
<button type="button" onclick="go('/class/edit?class_no=${classVO.class_no}')">수정</button>
<button type="button" onclick="go('/class/delete?class_no=${classVO.class_no}')">삭제</button><br>
${classVO.class_content}<br>
${classVO.class_maxcount}<br>
${classVO.class_price}<br>
${classVO.teacher_content}<br>
</body>
</html>