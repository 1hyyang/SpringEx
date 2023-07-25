<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html lang="ko">
<head>
<meta charset="utf-8">
<title>게시판</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
<link href="../resources/css/style.css" rel="stylesheet">
<script>
	function goRead(url, bno){
		searchform.action = url;
		searchform.bno.value = bno;
		searchform.submit();
	}
	
	function goWrite(url){
		searchform.action = url;
		searchform.submit();
	}
</script>
</head>
<body> 
<%@ include file="./header.jsp" %>
<main class="container">
<div class="bg-light p-5 rounded">
  <h1>게시판</h1>
  <p class="lead">부트스트랩을 이용한 게시판 만들기</p>
  <a class="btn btn-lg btn-primary" href="#" onclick="goWrite('/board/write')" role="button" >글쓰기 &raquo;</a>
</div>
<p></p>

<%@ include file="../board/searchform.jsp" %>  
<p></p>

<div class="panel-body">
  <div class="table-responsive">
      <table class="table table-hover">
          <thead>
              <tr>
                  <th>번호</th>
                  <th>제목</th>
                  <th>작성자</th>
                  <th>등록일</th>
              </tr>
          </thead>
          <tbody>
          <c:if test="${empty list}">
          	<tr align="center">
                	<td colspan="4">등록된 게시물이 없습니다.</td>
              </tr>
          </c:if>
          <c:if test="${not empty list}">
          <c:forEach items="${list}" var="board">
              <tr>                  
                  <td>${board.bno}</td>
                  <td><a href="#" onclick="goRead('/board/read', ${board.bno})" style="color: black">${board.title}</a></td>
                  <td>${board.writer}</td>
                  <td>${board.regdate}</td>
              </tr>
          </c:forEach>
          </c:if>
          </tbody>
      </table>
  </div>
</div>
<c:if test="${not empty list}">  
<%@ include file="../board/pagenavi.jsp" %>
</c:if>
</main>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>
</body>
</html>