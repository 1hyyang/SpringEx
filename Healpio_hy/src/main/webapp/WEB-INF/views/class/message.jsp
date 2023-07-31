<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<!-- 
	부트스트랩을 이용한 모달창 띄우기
		1. CSS, JS 링크 추가
		2. 모달 요소 정의
			타이틀 및 메시지 수정
		3. 모달창 열기
			자바 스크립트를 이용하여 모달 객체 생성 후 show()
		4. 모달창 닫기(닫기 버튼 클릭, 배경 클릭) 후 뒤로가기
			자바 스크립트를 이용하여 이벤트 발생시 history.go(-1)		
 -->
 
<script>
	let message = '${message}';
	window.addEventListener('load', function(){
		if(message!=''){
			// 모달창 열기
			document.querySelector(".modal-body").innerHTML = message;
			const myModal = new bootstrap.Modal('#myModal', {
				keyboard: false
			})
			myModal.show();
			
			// 모달창 닫기 후 뒤로가기
			const myModalEl = document.getElementById('myModal')
			myModalEl.addEventListener('hidden.bs.modal', event=>{
				if(message.indexOf("오류")>-1){
					history.go(-1);					
				} else if(message.indexOf("삭제되었습니다.")>-1){
					location.href='../class/#';
				} else {
					//location.href='../class/read?class_no=${class_no}&pageno=${pageno}&searchfield=${searchfield}&searchword=${searchword}';
					location.href='../class/read?class_no=${class_no}';
				}
			})
		}
	})
</script>

<!-- CSS, JS 링크 추가 -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
</head>

<body>
<!-- Modal -->
<!-- 모달 요소 정의 -->
<div id="myModal" class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h1 class="modal-title fs-5" id="exampleModalLabel">알림</h1>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
      	<!-- message -->
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
        <!-- <button type="button" class="btn btn-primary">Save changes</button> -->
      </div>
    </div>
  </div>
</div>

<!-- CSS, JS 링크 추가 -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>
</body>
</html>