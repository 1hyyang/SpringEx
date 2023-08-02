<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
<script src="https://cdn.jsdelivr.net/gh/hiphop5782/score@latest/score.js"></script>
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

$(function(){
    $('.score').score({
        starColor: "gold", //별 색상
        backgroundColor: "transparent", //배경 색상
        editable: true, //점수 변경 가능 여부
        integerOnly: true, //정수만 설정 가능 여부
        zeroAvailable:false,//0 설정 가능 여부
        send:{
            sendable:true,//전송 가능 여부
            name:"review_star",//전송 가능할 경우 전송될 이름
        },
        display: {
            showNumber: true, //설정된 숫자 표시 가능 여부
            placeLimit: 1, //소수점 자리수 표시 길이
            textColor:"black",//텍스트 색상
        },
        point: {
            max: 5,//최대 점수(data-max로 대체 가능)
            rate: 3,//실제 점수(data-rate로 대체 가능)
        }
    })
});
</script>
</head>
<body>
<c:forEach items="${attachList}" var="attachVO">
<img src='/resources/images/${attachVO.filepath}' alt='${classVO.class_title}' width='300px'><br>
</c:forEach>
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

<!-- 강의 소개 -->
${classVO.class_content}<br>
${classVO.class_maxcount}<br>
${classVO.class_price}<br>

<br><br><br><br>

<!-- 강사 소개 -->
${classVO.teacher_content}<br>

<br><br><br><br>

<!-- 리뷰 -->
리뷰 작성
<form name="reviewForm" method="get" action="/review/write">
	<div class='score' data-max='5'></div><br>
	<input type="text" name="member_no" id="member_no" value="M000002">
	<input type="text" name="class_no" id="class_no" value="${classVO.class_no}">
	<input type="text" name="review_content" id="review_content">
	<button type="submit">등록</button>
</form>
</body>
</html>