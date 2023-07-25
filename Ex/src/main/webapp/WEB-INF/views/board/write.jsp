<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>게시판 - 글쓰기</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
<link href="../resources/css/style.css" rel="stylesheet">
<script type="text/javascript">   
    window.addEventListener('load', function(){
    	getFileList();
    	
    	backToListBtn.addEventListener('click', function(){
    		writeform.action = '/board/list';
    		writeform.method = 'get';
    		writeform.submit();
    	})    	
    })
    
    function go(url){
		writeform.action = url;
		writeform.submit();
    }
	
	function getFileList(){
		let bno = '${board.bno}';
		if(bno){			
			fetch('/file/list/' + bno)
				.then(response => response.json())
				.then(map => {
					let list = '';
					if(map.list.length>0){
						list += '<div class="form-control">';
						map.list.forEach(attach => {
							list += ''
								 + attach.filename 
								 + ' <i class="fa-solid fa-trash-can "' 
								 + 'onclick="deleteFile(this)" '
								 + 'data-uuid=' + attach.uuid 
								 + ' data-bno=' + attach.bno + '></i>' 
								 + '<br>';
						});
						list += '</div>';
					}
					filelistDiv.innerHTML = list;
				});	
		}
	}
	
	function deleteFile(e){		
		// 백틱 사용 방법
		// EL로 인식하지 않도록 이스케이프 문자(\) 사용해야 한다.
		// fetch(`/file/delete/\${e.dataset.uuid}/\${e.dataset.bno}`)
		fetch('/file/delete/' + e.dataset.uuid + '/' + e.dataset.bno)
			.then(response => response.json())
			.then(map => {
				if(map.result=='success'){
					getFileList();
				} else {
					alert(map.message);
				}
			});
	}
</script>
</head>
<body>
<%@ include file="./header.jsp" %>
<main class="container">
  <div class="bg-light p-5 rounded">
    <h1>게시판</h1>
    <p class="lead">부트스트랩을 이용한 게시판 만들기</p>
    <a class="btn btn-lg btn-primary" id="backToListBtn" href="#" role="button">목록으로 &raquo;</a>
  </div>
  <p></p>
<form name="writeform" method="post" enctype="multipart/form-data">
  <!-- 목록으로 돌아가기시 필요 (검색, 페이징 유지)-->
  <input type="hidden" name="pageno" value="${param.pageno}">
  <input type="hidden" name="searchfield" value="${param.searchfield}">
  <input type="hidden" name="searchword" value="${param.searchword}">
  
  <div class="mb-3">
	<label for="title" class="form-label">제목</label>
	<input type="text" name="title" id="title" class="form-control" value="${board.title}">
  </div>
  
  <div class="mb-3">
	<label for="writer" class="form-label">작성자</label>
	<!-- 글쓰기 -->
	<c:if test="${empty board.writer}">
		<input type="text" name="writer" id="writer" class="form-control" value="${userid}">
	</c:if>
	<!-- 수정하기 -->
	<c:if test="${not empty board.writer}">
		<input type="text" name="writer" id="writer" class="form-control" value="${board.writer}">
	</c:if>
  </div>
  
  <div class="mb-3">
  	<label for="fileMultiple" class="form-label">첨부파일</label>
  	<input class="form-control" type="file" name="files" id="fileMultiple" multiple>
  </div>
  <div id="filelistDiv">
  	
  </div>
  <p></p>
  
  <div class="mb-3">
	<label for="content" class="form-label">내용</label>
	<textarea class="form-control" name="content" id="content" rows="3">${board.content}</textarea>
  </div>
  
  <div class="d-grid gap-2 d-md-flex justify-content-md-end">
  <!-- 글쓰기 -->
  <c:if test="${empty board.bno}">
	<button type="submit" class="btn btn-primary" onclick="go('/board/write')">등록</button>
  </c:if>
  <!-- 수정하기 -->
  <c:if test="${not empty board.bno}">
	<input type="hidden" name="bno" value="${board.bno}">
	<button type="submit" class="btn btn-primary" onclick="go('/board/edit')">등록</button>
  </c:if>
	<button type="reset" class="btn btn-secondary">초기화</button>
  </div>
</form>
</main>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>
</body>
</html>