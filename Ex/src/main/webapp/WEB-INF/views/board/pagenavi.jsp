<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script>
	function go(pageno){
		document.searchform.pageno.value = pageno;
		document.searchform.submit();
	}
</script>
</head>
<body>
<nav class="text-center" aria-label="Page navigation example">
  <ul class="pagination justify-content-center">
    <li class="page-item ${1!=criteria.pageno?'':'disabled'}">
      <a class="page-link" onclick="go(1)">&laquo;</a>
    </li>
    <li class="page-item ${pageVo.prev?'':'disabled'}">
      <a class="page-link" onclick="go(${pageVo.startno-1})">&lsaquo;</a>
    </li>
  <c:forEach begin="${pageVo.startno}" end="${pageVo.endno}" var="pageno">
    <li class="page-item ${pageno==criteria.pageno?'active':''}"><a class="page-link" onclick="go(${pageno})">${pageno}</a></li>
  </c:forEach>
    <li class="page-item ${pageVo.next?'':'disabled'}">
      <a class="page-link" onclick="go(${pageVo.endno+1})">&rsaquo;</a>
    </li>
    <li class="page-item ${pageVo.realendno!=criteria.pageno?'':'disabled'}">
      <a class="page-link" onclick="go(${pageVo.realendno})">&raquo;</a>
    </li>
  </ul>
</nav>
</body>
</html>