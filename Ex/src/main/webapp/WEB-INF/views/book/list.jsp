<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>숲속도서관</title>
<!-- Bootstrap Core CSS -->
<link href="../resources/css/bootstrap.min.css" rel="stylesheet">
<!-- Custom CSS -->
<link href="../resources/css/sb-admin-2.css" rel="stylesheet">
<!-- Custom Fonts -->
<link href="../resources/css/font-awesome.min.css" rel="stylesheet" type="text/css">
</head>

<body>
<div id="wrapper">

	<%@include file="./header.jsp" %>

    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">도서조회</h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <!-- /.row -->
        <div class="row">
            <div class="col-lg-12">
            
				 <form name="searchform" action="../book/list">
				 <input type="hidden" name="pageno" value="${pageVo.criteria.pageno}"><!-- 페이징 또는 상세보기 후 목록으로 복귀시 필요 -->                
					<div class="form-inline text-center">
						<div class="form-group">
	                        <select name="searchfield" class="form-control">
	                            <option value="title" ${"title" eq pageVo.criteria.searchfield?"selected":""}>도서명</option>
	                            <option value="author" ${"author" eq pageVo.criteria.searchfield?"selected":""}>지은이</option>
	                            <option value="no" ${"no" eq pageVo.criteria.searchfield?"selected":""}>번호</option>
	                        </select>
	                    </div>
	                    
	                    <div class="form-group input-group">
                            <input type="text" name="searchword" class="form-control" value="${pageVo.criteria.searchword}" placeholder="검색">
                            <span class="input-group-btn">
                                <button type="submit" class="btn btn-default" onclick="go(1)"><i class="fa fa-search"></i></button>
                            </span>
                        </div>

                    </div>
				 </form>
				
                 <p></p>	
                                     
                    <table width="100%" class="table table-bordered" id="dataTables-example">
                        <thead class="well">
                            <tr>
                                <th>번호</th>
                                <th>도서명</th>
                                <th>지은이</th>
                                <th>대여현황</th>
                            </tr>
                        </thead>
                        <tbody>
                        <c:if test="${empty list}">
            				<tr align="center">
                  				<td colspan="5">등록된 게시물이 없습니다.</td>
                			</tr>
            			</c:if>
            			<c:if test="${not empty list}">
        				<c:forEach items="${list}" var="book">
                            <tr class="odd gradeA">
                                <td>${book.no}</td>
                                <td><a href="../book/read?no=${book.no}">${book.title}</a></td>
                                <td>${book.author}</td>
                                <td>${book.rentyn}</td>
                            </tr>
                        </c:forEach>
        				</c:if>                          
                        </tbody>
                    </table>
                    <!-- /.table-responsive -->
                        
		<c:if test="${not empty list}">  
		<%@ include file="../book/pagenavi.jsp" %>
		</c:if>

                 <div class="well">
                     <h4>DataTables Usage Information</h4>
                     <p>DataTables is a very flexible, advanced tables plugin for jQuery. In SB Admin, we are using a specialized version of DataTables built for Bootstrap 3. We have also customized the table headings to use Font Awesome icons in place of images. For complete documentation on DataTables, visit their website at <a target="_blank" href="https://datatables.net/">https://datatables.net/</a>.</p>
                     <a class="btn btn-default btn-lg btn-block" target="_blank" href="https://datatables.net/">View DataTables Documentation</a>
                 </div>
                    
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <!-- /.row -->
    </div>
    <!-- /#page-wrapper -->
    
</div>
<!-- /#wrapper -->

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>
</body>
</html>