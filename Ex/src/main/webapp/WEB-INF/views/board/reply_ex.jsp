<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>답글달기</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
<script src="https://kit.fontawesome.com/0aadd0de21.js" crossorigin="anonymous"></script>
<script>
	window.addEventListener('load', function(){
		getList();
	});
	
	// 목록 불러오기	
	function getList(){
		let bno = document.querySelector('#bno').value;
		let page = document.querySelector('#page').value;
		
		// 서버에 요청
		fetchGet('/reply/list/' + bno + '/' + page, readReply);
	}
	
	function fetchGet(url, callback){
		fetch(url)
		    // 컨트롤러로부터 JSON 타입의 객체가 반환
			// 객체를 변수명 response에 받아 와서 json() 메소드를 호출
		    // json() : JSON 형식의 문자열을 Promise 객체로 반환
		    // Promise 객체는 then() 메소드를 사용하여 
		    // 비동기 작업의 성공 또는 실패와 관련된 결과를 나타내는 대리자 역할을 수행
			.then(response => response.json())
			// 반환 받은 객체를 매개 변수로 받는 콜백 함수를 호출
			.then(map => callback(map));
	}
	
	// 리스트를 화면에 출력
	function readReply(map){
		let list = map.list;
		let pageVo = map.pageVo;
		
		// div 초기화
		replyDiv.innerHTML = '';
		// 리스트로부터 답글을 하나씩 읽어 와서 div에 출력
		// forEach() : 파라미터로 주어진 콜백 함수를 배열 요소 각각에 대해 실행하는 메소드
		// forEach((요소, 인덱스, 배열)=>{})
		list.forEach((reply) => {
			// 답글을 div에 출력
			replyDiv.innerHTML += ''
				// reply0 형태로 id 값을 부여 (답글 수정시 필요)
				+ '<figure id="reply' + reply.rno + '" data-reply="' + reply.reply + '" data-replyer="' + reply.replyer + '">'
				+  	'<blockquote class="blockquote">'
				+    	'<p>' + reply.reply
				+			' <i class="fa-solid fa-pen-to-square" onclick="editReply(' + reply.rno + ')"></i>'
				+			' <i class="fa-solid fa-trash-can" onclick="deleteReply(' + reply.rno + ')"></i>'
				+ 		'</p>'
				+  	'</blockquote>'
				+  	'<figcaption class="blockquote-footer">'
				+    	reply.replyer + ' <cite title="Source Title">' + reply.replydate + '</cite>'
				+  	'</figcaption>'
				+ '</figure>';
		});
		
		// 페이지 블록 생성
		// innerHTML에 태그를 붙이고 닫는 것을 반복할 경우 오류 발생하므로 새로운 변수를 생성
		let pageblock = ''
				+ '<nav aria-label="...">'
				+ '  <ul class="pagination justify-content-center">';
		if(pageVo.prev){
			pageblock += ''
				+ '    <li class="page-item" ' 
				+ '			onclick="go('+ (pageVo.startno-1) +')">'
				+ '      <span class="page-link">&lsaquo;</span>'
				+ '    </li>';
		}
		for(var i=pageVo.startno; i<=pageVo.endno; i++){
			let activeYN = (pageVo.criteria.pageno == i)?'active':''; 
			// 페이지 번호 생성	
			pageblock += ''		
				+ '    <li class="page-item ' + activeYN + '" '
				+ '			onclick="go('+ i +')">'
				+ '		<a class="page-link" href="#">'+ i +'</a></li>';				
		}
		if(pageVo.next){
			pageblock += ''
				+ '    <li class="page-item"'
				+ ' 		onclick="go('+ (pageVo.endno+1) +')">'
				+ '      <a class="page-link" href="#">&rsaquo;</a>'
				+ '    </li>';	
		}
		pageblock += ''
				+ '  </ul>'
				+ '</nav>';
				
		replyDiv.innerHTML += pageblock;
	}
	
	function go(page){
		document.querySelector("#page").value = page;
		getList();
	}
	
	window.addEventListener('load', function(){		
		// 답글 등록
		writeBtn.addEventListener('click', function(){
			// 1. 파라미터 수집 - Reply 객체를 생성하기 위해 필요한 필드 값을 수집한다.
			let bno = document.querySelector('#bno').value;
			let reply = document.querySelector('#reply').value;
			let replyer = document.querySelector('#replyer').value;
			
			// 2. 전송할 데이터를 JS object로 생성 (객체명 = {속성명:속성})
			let replyObj = {
				bno : bno
				, reply : reply
				, replyer : replyer
			}
			
			// 3. 서버에 요청
			fetchPost('/reply/write', replyObj, getResult);
		});		
	});
	
	function fetchPost(url, obj, callback){
		// fetch(url, {method, headers, body})
		// 지정한 URL에 요청 정보를 파라미터로 넘긴다.
		fetch(url, {method:'post'
					, headers:{'Content-Type':'application/json'}
					// JS object를 JSON 타입의 문자열로 변환
					, body:JSON.stringify(obj)})
		    // 컨트롤러로부터 JSON 타입의 객체가 반환
			// 객체를 변수명 response에 받아 와서 json() 메소드를 호출
		    // json() : JSON 형식의 문자열을 Promise 객체로 반환
		    // Promise 객체는 then() 메소드를 사용하여 
		    // 비동기 작업의 성공 또는 실패와 관련된 결과를 나타내는 대리자 역할을 수행
			.then(response => response.json())
			// 반환 받은 객체를 매개 변수로 받는 콜백 함수를 호출
			.then(map => callback(map));
	}
	
	function getResult(map){
		if(map.result=='success'){
			getList();
		} else {
			alert(map.message);
		}
	}
	
	// 수정 화면 보여주기
	function editReply(rno){
		let editFigure = document.querySelector('#reply' + rno);
		let reply = editFigure.dataset.reply;
		let replyer = editFigure.dataset.replyer;
		editFigure.innerHTML = ''
			+ '<div class="input-group mb-3">'
			+  	'<input type="text" id="editedReplyer' + rno + '" name="replyer" class="form-control" value="' + replyer + '">'
			+  	'<input type="text" id="editedReply' + rno + '" name="reply" class="form-control" value="' + reply + '">'
			+  	'<button class="btn btn-outline-secondary" type="button" id="replyBtn" onclick="editAction(' + rno + ')">수정</button>'
			+  '</div>';
	}
	
	function editAction(rno){
		// 1. 파라미터 수집 - Reply 객체를 생성하기 위해 필요한 필드 값을 수집한다.
		let editedReply = document.querySelector('#editedReply' + rno).value;
		let editedReplyer = document.querySelector('#editedReplyer' + rno).value;
		
		// 2. 전송할 데이터를 JS object로 생성 (객체명 = {속성명:속성})
		let replyObj = {
			rno : rno
			, reply : editedReply
			, replyer : editedReplyer
		}

		// 3. 서버에 요청
		fetchPost('/reply/edit', replyObj, getResult);
	}
	
	function deleteReply(rno){
		fetch('/reply/delete/' + rno)
			// 컨트롤러로부터 Map 객체가 반환된다.
			.then(response => response.json())
			// 반환 받은 오브젝트를 매개 변수로 받는 함수를 호출
			.then(map => getResult(map));
	}
</script>
</head>
<body>
<h3>답글</h3>
<input type="hidden" id="page" name="page" value="1">
<div class="input-group mb-3">
  <input type="text" id="replyer" name="replyer" class="form-control" placeholder="작성자">
  <input type="text" id="reply" name="reply" class="form-control" placeholder="답글">
  <button class="btn btn-outline-secondary" type="button" id="writeBtn">등록</button>
</div>
<div id="replyDiv">

</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>
</body>
</html>