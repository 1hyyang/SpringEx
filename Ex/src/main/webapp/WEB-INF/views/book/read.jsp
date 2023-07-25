<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

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
                <h1 class="page-header">도서상세</h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <!-- /.row -->
        <div class="row">
            <div class="col-lg-12">
            	<table width="100%" class="table table-bordered" id="dataTables-example">
	            	<!-- 게시글 정보 -->
				    <tr>
				        <td rowspan="3">
				        	<img alt="${ book.title } 이미지" width="200px" src="../images/bookimg/${ book.sfile }">
				        </td>
				        <td class="well">도서명</td><td>${ book.title }</td>
				        <td class="well">지은이</td> <td>${ book.author }</td>
				    </tr>
				    <tr>
				        <td class="well">대여현황</td>     		
				       	<c:if test="${ empty book.rentno }">       			
				        	<c:if test="${ not empty sessionScope.userid }">
				        		<td colspan="3">대여가능<button type="button" class="btn btn-default btn-xs" onclick="setAction('./rent.book')">대여</button></td>	 
				        	</c:if>   
				        	<c:if test="${ empty sessionScope.userid }">
				        		<td colspan="3">대여가능(로그인 후 이용 가능합니다.)</td>
				        	</c:if>     		        				
				       	</c:if>
				       	<c:if test="${ not empty book.rentno }">
				       		<c:if test="${ book.id eq sessionScope.userid }" var="myRent">
				     			<td>대여중<button type="button" class="btn btn-default btn-xs" onclick="setAction('./return.book')">반납</button></td>
				     			<td class="well">대여기간</td> <td>${ book.startdate } ~ ${ book.enddate }</td> 
				       		</c:if>
				       		<c:if test="${ not myRent }">
				       			<td>대여중</td>
				       			<td class="well">대여기간</td> <td>${ book.startdate } ~ ${ book.enddate }</td>
				       		</c:if>
				       	</c:if>
				    </tr>
				    <tr>
				    	<td class="well" height="200px">상세설명</td><td colspan="3"></td>
				    </tr>
			    </table>
				
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