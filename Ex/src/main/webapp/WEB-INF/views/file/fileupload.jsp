<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>파일 업로드</title>
<script src="https://kit.fontawesome.com/0aadd0de21.js" crossorigin="anonymous"></script>
<script type="text/javascript">
	window.addEventListener('load', function(){		
		// REST 방식으로 파일 업로드
		fileuploadBtn.addEventListener('click', function(){
			// FormData
			// 	웹 개발에서 HTML 폼 데이터를 JS로 쉽게 조작하고 전송하는 방법을 제공하는 API
			// 	FormData(폼이름) : 폼이 가지고 있는 모든 데이터를 수집
			let formData = new FormData(fileuploadForm);			
			console.log("formData: " + formData);
			
			// FormData.entries() : 폼 데이터의 모든 키/값 쌍을 반환
			for(var pair of formData.entries()){
				console.log(pair);
				// 배열의 첫 번째는 폼의 name 속성값, 두 번째는 value 값과 매치
				console.log(pair[0] + ': ' + pair[1]); // 파일의 경우 files: [object File]
				if(typeof(pair[1])=='object'){
					let filename = pair[1].name;
					let filesize = pair[1].size;
					
					console.log('filename: ' + filename);
					console.log('filesize: ' + filesize);
					
					// 파일 확장자, 파일 크기 체크
					// 서버에 전송 가능한 형식인지 
					// 최대 전송 가능한 용량을 초과하지 않는지
					if(!checkValidation(filename, filesize)){
						return false;
					}
				}
			}
			
			// FormData.append(key, value)메소드는 기존 키에 새 값을 추가하거나, 키가 없는 경우 키를 추가한다.
			formData.append('name', 'congg');
			
		 	// fetch()를 통해 서버에 요청할 때 자바스크립트 객체를 생성하지 않고 FormData로부터 가져올 수 있다.
			fetch('/file/fileuploadByFetch', {method:'post', body:formData})
				.then(response => response.json())
				.then(map => {
					if(map.result=='success'){
						fileuploadResDiv.innerHTML = map.message;
					} else {
						alert(map.message);
					}
				});		 	
		});
	});
	
	function checkValidation(filename, filesize){
		// 정규 표현식(Regular Expression) : 특정 규칙을 가진 문자열을 검색하거나 치환할 때 사용
		// (.*?) : 파일명
		// \. : 마침표는 정규식에서 특수 문자로 사용되므로 이스케이프하여 실제 마침표(.)를 찾는다.
		// (exe|sh|zip|alx) : exe, sh, zip, alx 중 하나를 찾는다. 파일의 확장자
		// $ : 문자열의 끝
		let regexp = new RegExp("(.*?)\.(exe|sh|zip|alx)$");
		// RegExp.test(문자열) : 문자열에 정규식 패턴을 만족하는 값이 있으면 true, 없으면 false를 리턴
		if(regexp.test(filename)){
			alert("해당 형식의 파일은 등록할 수 없습니다.");
			return false;
		}
		
		let maxsize = 1024*1024*10;
		if(filesize>=maxsize){
			alert("전송 가능한 크기를 초과하였습니다.");
			return false;
		}
		
		return true;
	}
	
	window.addEventListener('load', function(){	
		// 파일 목록 조회
		getListBtn.addEventListener('click', function(){
			getList();
		});
	});
	
	function getList(){
		let bno = document.querySelector("#bno").value;
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
							 + encodeURIComponent(attach.filepath) + '">'
							 + attach.filename 
							 + '</a>'
							 + ' <i class="fa-solid fa-trash-can "' 
							 + 'onclick="deleteFile(this)" '
							 + 'data-uuid=' + attach.uuid 
							 + ' data-bno=' + attach.bno + '></i>' 
							 + '<br>';
					});
				} else{
					list = '등록된 파일이 없습니다.';
				}
				listDiv.innerHTML = list;
			});	
	}
	
	function deleteFile(e){		
		// 백틱 사용 방법
		// EL로 인식하지 않도록 이스케이프 문자(\) 사용해야 한다.
		// fetch(`/file/delete/\${e.dataset.uuid}/\${e.dataset.bno}`)
		fetch('/file/delete/' + e.dataset.uuid + '/' + e.dataset.bno)
			.then(response => response.json())
			.then(map => {
				if(map.result=='success'){
					getList();
				} else {
					alert(map.message);
				}
			});
	}
</script>
</head>
<body>
<h2>파일 업로드</h2>
<form method="post" enctype="multipart/form-data" name="fileuploadForm"
	action="/file/fileupload">
	<input type="text" id="bno" name="bno" value="159"><br>
	<input type="file" name="files" multiple><br>
	<input type="file" name="files"><br>
	<!-- 폼 전송 방식 -->
	<button type="submit">등록</button>
	<!-- REST 방식 -->
	<button type="button" id="fileuploadBtn">등록(fetch)</button>	
</form>
<div id="fileuploadResDiv">
	${param.message}
</div>

<h2>파일 목록 조회</h2>
<button type="button" id="getListBtn">조회</button>
<div id="listDiv">

</div>
</body>
</html>