<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<form name="searchform" action="/board/list">
  <input type="hidden" name="pageno" value="${pageVo.criteria.pageno}"><!-- 페이징 또는 상세보기 후 목록으로 복귀시 필요 -->
  <input type="hidden" name="bno"><!-- 상세보기시 필요 -->
	<div class="row g-3 justify-content-center">
	  <div class="col-auto">
	  	<!-- 페이징 또는 상세보기 후 목록으로 복귀시 이용 -->
	    <select name="searchfield" class="form-select" aria-label="Default select example">
		  <option value="title" ${"title" eq pageVo.criteria.searchfield?"selected":""}>제목</option>
		  <option value="writer" ${"writer" eq pageVo.criteria.searchfield?"selected":""}>작성자</option>
		  <option value="content" ${"content" eq pageVo.criteria.searchfield?"selected":""}>내용</option>
		</select>
	  </div>
	  <div class="col-sm-5">
	    <!-- 페이징 또는 상세보기 후 목록으로 복귀시 이용 -->
	    <input type="text" name="searchword" class="form-control" id="searchword" value="${pageVo.criteria.searchword}" placeholder="검색">
	  </div>
	  <div class="col-auto">
	    <button type="submit" class="btn btn-primary mb-3" onclick="go(1)"><i class="fa-solid fa-magnifying-glass"></i></button>
	  </div>
	</div>
</form>
</body>
</html>