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
<nav class="text-center">
	<ul class="pagination justify-content-center">
		<c:if test="${pageVo.prev}">
			<li class="page-item" onclick="go(${pageVo.startno-1})">
				<span class="page-link">&lsaquo;</span>
			</li>
		</c:if>
		<c:forEach begin="${pageVo.startno}" end="${pageVo.endno}" var="pageno">
			<li class="page-item ${pageno==criteria.pageno?'active':''}" onclick="go(${pageno})">
				<a class="page-link" href="#">${pageno}</a>
			</li>			
		</c:forEach>
		<c:if test="${pageVo.next}">
			<li class="page-item" onclick="go(${pageVo.endno+1})">
				<a class="page-link" href="#">&rsaquo;</a>
			</li>
		</c:if>
	</ul>
</nav>
</body>
</html>