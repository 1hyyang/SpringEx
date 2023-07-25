<%@page import="java.math.BigInteger"%>
<%@page import="java.security.SecureRandom"%>
<%@page import="java.net.URLEncoder"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>  
<!doctype html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="Mark Otto, Jacob Thornton, and Bootstrap contributors">
<meta name="generator" content="Hugo 0.104.2">
<title>Sign In</title>

<link rel="canonical" href="https://getbootstrap.com/docs/5.2/examples/sign-in/">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
<link href="/resources/css/signin.css" rel="stylesheet">

<script src="/resources/js/common.js"></script>
<script type="text/javascript">
	window.addEventListener('load', function(){
    	getSinginformBtn.addEventListener('click', function(){
            signupForm.style.display = 'none';
            signinForm.style.display = '';
          })

        getSignupformBtn.addEventListener('click', function(){
          signupForm.style.display = '';
          signinForm.style.display = 'none';
          // signupId.focus();
        })
        
        // 로그인
		signinBtn.addEventListener('click', function(e){
			// 기본 이벤트(새로고침) 제거
			e.preventDefault();
			
			// 파라미터 수집
			let obj={
				id : document.querySelector('#id').value
				, pw : document.querySelector('#pw').value 
			};
			
			// 서버에 요청
			fetchPost('/loginAction', obj, getResult);
		})
		
		// 회원가입 - 아이디 중복 체크
		// blur 이벤트 : 포커스 상태였다가 포커스 아웃일 때 발생
		signupId.addEventListener('blur', function(){
			if(signupId.value!=''){
				let obj = {id : signupId.value};
				fetchPost('/idCheck', obj, (map) => {
					signupMsg.innerHTML = map.message;
					if(map.result=='fail'){
						signupId.focus();
						signupId.value = '';
					}
				});				
			}
		})
		
		pwCheck.addEventListener('blur', function(){
			if(signupPw.value!='' && pwCheck.value!=''){
				if(signupPw.value==pwCheck.value){
					signupMsg.innerHTML = '';
				} else{
					signupMsg.innerHTML = '비밀번호가 일치하지 않습니다.';
					signupPw.focus();
					signupPw.value= '';
					pwCheck.value = '';
					return;
				}		
			}
		})
		
		signupBtn.addEventListener('click', function(e){
			e.preventDefault();
			
			let id = signupId.value;
			let name = signupName.value;
			let pw = signupPw.value;
			
			if(!id){
				signupMsg.innerHTML = '아이디를 입력하세요.';
				signupId.focus();
				return;
			}			
			if(!name){
				signupMsg.innerHTML = '이름을 입력하세요.';
				signupName.focus();
				return;
			}			
			if(!pw){
				signupMsg.innerHTML = '비밀번호를 입력하세요.';
				signupPw.focus();
				return;
			}
			
			obj = {
				id : id
				, pw : pw
				, name : name
			}
			
			fetchPost('/register', obj, (map) => {
				if(map.result=='success'){
					location.href = '/login';					
				} else {
					signupMsg.innerHTML = map.message;
				}
			});
		})
		
	})

	function getResult(map){
		if(map.result=='success'){
			// location.href = "/board/list";
			location.href = map.url;
		} else {
			signinMsg.innerHTML = map.message;
		}
	}
</script>  
</head>
<body class="text-center">    
<main class="form-signin w-100 m-auto">
  <!-- 로그인 폼 -->
  <form name='signinForm'>
  	<h1 class="h3 mb-3 fw-normal">Sign In</h1>
  	<div id="signinMsg">${param.msg}</div>
    <div class="form-floating">
      <input type="text" class="form-control start" id="id" required="required" placeholder="아이디">
      <label for="id">아이디</label>
    </div>
    <div class="form-floating">
      <input type="password" class="form-control end" id="pw" required="required" placeholder="비밀번호">
      <label for="pw">비밀번호</label>
    </div>
    <div class="checkbox mb-3 text-start">
      <label>
        <input type="checkbox" value="remember-me"> 아이디 저장
      </label>
    </div>
    <button class="w-100 btn btn-lg btn-primary" type="submit" id='signinBtn'>로그인</button>
  </form>
  
  <!-- 회원가입 폼 -->
  <form name='signupForm' style='display:none'>
  	<h1 class="h3 mb-3 fw-normal">Sign Up</h1>
  	<div id="signupMsg"></div>
    <div class="form-floating">
      <input type="text" class="form-control start" id="signupId" placeholder="아이디">
      <label for="signupId">아이디</label>
    </div>
    <div class="form-floating">
      <input type="text" class="form-control middle" id="signupName" placeholder="이름">
      <label for="signupName">이름</label>
    </div>
    <div class="form-floating">
      <input type="password" class="form-control middle" id="signupPw" placeholder="비밀번호">
      <label for="signupPw">비밀번호</label>
    </div>
    <div class="form-floating">
      <input type="password" class="form-control end" id="pwCheck" placeholder="비밀번호 확인">
      <label for="pwCheck">비밀번호 확인</label>
    </div>    
    <button class="w-100 btn btn-lg btn-primary" id="signupBtn" type="submit">회원가입</button>
  </form>
  
  <p></p>
  <button id='getSignupformBtn'>회원가입</button>
  <button id='getSinginformBtn'>로그인</button>
  
  <!-- 네이버 로그인 -->
  <%
    String clientId = "0NtlLkL33aCmf_EpKXin";//애플리케이션 클라이언트 아이디값";
    String redirectURI = URLEncoder.encode("http://localhost:8080/login/naver_callback", "UTF-8");
    SecureRandom random = new SecureRandom();
    String state = new BigInteger(130, random).toString();
    String apiURL = "https://nid.naver.com/oauth2.0/authorize?response_type=code";
    apiURL += "&client_id=" + clientId;
    apiURL += "&redirect_uri=" + redirectURI;
    apiURL += "&state=" + state;
    session.setAttribute("state", state);
 %>
 <a href="<%=apiURL%>"><img height="50" src="http://static.nid.naver.com/oauth/small_g_in.PNG"/></a>
 
  <p class="mt-5 mb-3 text-muted">&copy; 2017–2022</p>
</main>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>  
</body>
</html>
 