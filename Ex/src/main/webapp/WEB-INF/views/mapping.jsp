<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>매핑하기</title>
</head>
<body>
<a href="../mapping/requestMapping">requestMapping 호출</a>
<form action="./requestMapping" method="post">
	<input type="submit" value="requestMapping 호출">	
</form><br>

<a href="../mapping/getMapping?name=콩지&age=20">getMapping 호출</a><br>
name: ${name}<br>
age: ${age}<br><br>

<a href="../mapping/getMappingVo?name=콩지&age=20&duedate=2023/07/03">getMappingVo 호출</a><br>
name: ${member.name}<br>
age: ${member.age}<br>
duedate: ${member.duedate}<br><br>

<a href="../mapping/getMappingArray?names=콩지&names=츠르후">getMappingArray 호출</a><br><br>
<a href="../mapping/getMappingList?names=콩지&names=츠르후">getMappingList 호출</a><br><br>

<script>
	function getMemberList(){
		let url = encodeURI("../mapping/getMappingMemberList?list[0].name=콩지&list[0].age=20&list[1].name=츠르후&list[1].age=18");
		alert(url);
		location.href=url;
	}
</script>
<a href="#" onclick="getMemberList()">getMappingMemberList 호출</a>
</body>
</html>