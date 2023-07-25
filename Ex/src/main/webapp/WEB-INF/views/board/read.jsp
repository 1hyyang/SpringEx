<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 - 상세보기</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
<link href="../resources/css/style.css" rel="stylesheet">
<script src="../resources/js/reply.js"></script>
<script>
	window.addEventListener('load', function(){
		backToListBtn.addEventListener('click', function(){
			readform.action = '/board/list';
			readform.submit();
		});
		
		// undefined 오류를 방지하기 위해 if문 필요
		if(${userid eq board.writer}){
			editBtn.addEventListener('click', function(){
				readform.action = '/board/edit';
				readform.submit();
			});
			
			deleteBtn.addEventListener('click', function(){
				readform.action = '/board/delete';
				readform.submit();
			});		
		}
		
		getFileList();
		
		getReplyList();
		
		writeReplyBtn.addEventListener('click', function(){
			writeReply();
		});
	});
	
	function getFileList(){
		let bno = '${board.bno}';
		if(bno){			
			fetch('/file/list/' + bno)
				.then(response => response.json())
				.then(map => {
					let list = '';
					if(map.list.length>0){
						map.list.forEach(attach => {
							/*
							encodeURIComponent()
								URI로 데이터를 전달하기 위해 문자열을 인코딩
								
								웹을 통해서 데이터를 전송할 때 특정 문자들은 특수한 기능으로 사용되는데 
								파라미터에 이러한 문자들이 포함되면 값을 제대로 인식할 수 없으므로 인코딩한다.
							*/
							list += ''
								 + '<a href="/file/download?filename=' 
								 + encodeURIComponent(attach.filepath) + '" style="color:black">'
								 + attach.filename 
								 + '</a>'
								 + '<br>';
						});
					} else{
						list = '첨부된 파일이 없습니다.';
					}
					filelistDiv.innerHTML = list;
				});	
		}
	}
</script>
</head>
<body>
<%@ include file="./header.jsp" %>

<!-- 게시글 영역 -->
<form name="readform">
  <!-- 
	폼 태그를 통해 넘어온 요소 (쿼리스트링 ?pageno= 으로 전달)
	-> ${param.pageno}로 받는다.
	
	model.setAttribute("pageno")를 통해 model 객체에 저장된 요소
	-> ${pageno}로 받는다.
   -->
  <!-- 목록으로 돌아가기시 필요 (검색, 페이징 유지)-->
  <input type="hidden" name="pageno" value="${param.pageno}">
  <input type="hidden" name="searchfield" value="${param.searchfield}">
  <input type="hidden" name="searchword" value="${param.searchword}">
  <!-- 수정 또는 삭제시 필요 --><!-- 답글 목록 출력 또는 등록 또는 수정시 필요 -->
  <input type="hidden" id="bno" name="bno" value="${board.bno}">
  <main class="container">
  <div class="bg-light p-5 rounded">
    <h1>게시판</h1>
    <p class="lead">부트스트랩을 이용한 게시판 만들기</p>
    <a class="btn btn-lg btn-primary" id="backToListBtn" href="#" role="button">목록으로 &raquo;</a>
  </div>
  <p></p>
  <div class="mb-3">
	<label for="title" class="form-label">제목</label>
	<input type="text" name="title" id="title" class="form-control" value="${board.title}" readonly>
  </div>
  <div class="mb-3">
	<label for="writer" class="form-label">작성자</label>
	<input type="text" name="writer" id="writer" class="form-control" value="${board.writer}" readonly>
  </div>
  <div class="mb-3">
  	<label for="fileMultiple" class="form-label">첨부파일</label>
  	<div id="filelistDiv" class="form-control">
  	
  	</div>
  </div> 
  <div class="mb-3">
	<label for="content" class="form-label">내용</label>
	<textarea name="content" id="content" class="form-control" rows="3" readonly>${board.content}</textarea>
  </div>
  
  <!-- 로그인한 사용자의 아이디와 게시글의 작성자가 일치하면 수정, 삭제 버튼을 노출 -->
  <c:if test="${userid eq board.writer}">
  <div class="d-grid gap-2 d-md-flex justify-content-md-end">	
	<button type="button" id="editBtn" class="btn btn-secondary">수정</button>
	<button type="button" id="deleteBtn" class="btn btn-secondary">삭제</button>
  </div>
  </c:if>
</form>
<p></p>

<!-- 답글 영역 -->
<!-- 답글 페이징 유지시 필요 -->
<input type="hidden" id="page" name="page">

<!-- 답글 목록 -->
<div id="replyDiv">

</div>

<!-- 답글 달기 -->
<div class="input-group">
  <input type="text" id="replyer" name="replyer" value="${userid}" class="form-control" placeholder="작성자">
  <input type="text" id="reply" name="reply" class="form-control" placeholder="답글">
  <buttontype="button" id="writeReplyBtn" class="btn btn-primary">등록</button>
</div>
<p></p>
</main>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>
</body>
</html>