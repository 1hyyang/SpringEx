function getReplyList(page){
	let bno = document.querySelector('#bno').value;
	// false : false, 0, "", NaN, undefined, null
	if(!page){
		page = 1;
	}
	document.querySelector("#page").value = page;
	// 서버에 요청
	fetchGet(`/reply/list/${bno}/${page}`, readReply);
}

function fetchGet(url, callback){
	try{
		fetch(url)
		// 컨트롤러로부터 JSON 타입의 객체가 반환
		// 객체를 변수명 response에 받아 와서 json() 메소드를 호출
		// json() : JSON 형식의 문자열을 Promise 객체로 반환
		// Promise 객체는 then() 메소드를 사용하여 
		// 비동기 작업의 성공 또는 실패와 관련된 결과를 나타내는 대리자 역할을 수행
		.then(response => response.json())
		// 반환 받은 객체를 매개 변수로 받는 콜백 함수를 호출
		.then(map => callback(map));		
	} catch(e){
		console.log('fetchGet', e)
	}
}

function readReply(map){
	let list = map.list;
	let pageVo = map.pageVo;
	
	// innerHTML에 태그를 붙이고 닫는 것을 반복할 경우 오류 발생하므로 새로운 변수를 생성
	let replyDivStr = ``
		+ `<table class="table text-break text-center">`
		+ `  <thead>                                `
		+ `    <tr>                                 `
		+ `      <th scope="col" colspan="4"></th>             `
		+ `    </tr>                                `
		+ `  </thead>                               `;
	if(list.length==0){
		replyDivStr += ``
			+ `  <tbody>                                `
			+ `    <tr>                                 `
			+ `      <td>등록된 답글이 없습니다.</td>           `
			+ `    </tr>                                `
			+ `  </tbody>                               `
			+ `</table>                                 `;	
		replyDiv.innerHTML = replyDivStr;
	} else {
		replyDivStr += ``
			+ `  <tbody>                                `
			+ `    <tr>                                 `
			+ `      <th scope="col" class="table-light" colspan="4">답글</th>             `
			+ `    </tr>                                `;
		list.forEach(reply => {
			replyDivStr += ``
				+ `    <tr id="reply${reply.rno}" data-reply="${reply.reply}" data-replyer="${reply.replyer}" data-replydate="${reply.replydate}">`
				+ `      <td class="col-1">${reply.rno}</td>                      `
				+ `      <td class="text-start col-7">${reply.reply} `;
			// replyer.value : 로그인한 아이디
			// reply.replyer : 답글 작성자
			if(replyer.value==reply.replyer){
				replyDivStr += ``
					+ `<i class="fa-solid fa-pen-to-square" onclick="editReply(${reply.rno})"></i> `
					+ `<i class="fa-solid fa-trash-can" onclick="deleteReply(${reply.rno})"></i></td>`;
			}
			replyDivStr += ``	
				+ `      <td class="col-1">${reply.replydate}</td>                      `
				+ `      <td class="col-1">${reply.replyer}</td>					`
				+ `    </tr>                                `;	
		})
		replyDivStr += ``
			+ `  </tbody>                               `
			+ `</table>                                 `;
		
		replyDiv.innerHTML = replyDivStr;
		
		// 페이지 블록 생성
		// innerHTML에 태그를 붙이고 닫는 것을 반복할 경우 오류 발생하므로 새로운 변수를 생성
		let pageblock = ``
				+ `<nav aria-label="...">`
				+ `  <ul class="pagination pagination-sm justify-content-end">`;
		if(pageVo.prev){
			pageblock += ``
				+ `    <li class="page-item" onclick="getReplyList(${pageVo.startno-1})">`
				+ `      <span class="page-link">&lsaquo;</span>`
				+ `    </li>`;
		}
		for(var i=pageVo.startno; i<=pageVo.endno; i++){
			let activeYN = (pageVo.criteria.pageno == i)?'active':''; 
			// 페이지 번호 생성	
			pageblock += ``		
				+ `    <li class="page-item ${activeYN}" onclick="getReplyList(${i})">`
				+ `		 <a class="page-link" href="#">${i}</a></li>`;				
		}
		if(pageVo.next){
			pageblock += ``
				+ `    <li class="page-item" onclick="getReplyList(${pageVo.endno+1})">`
				+ `      <a class="page-link" href="#">&rsaquo;</a>`
				+ `    </li>`;	
		}
		pageblock += ``
				+ `  </ul>`
				+ `</nav>`;
		
		replyDiv.innerHTML += pageblock;
	}	
}

function writeReply(){
	// 1. 파라미터 수집 - Reply 객체를 생성하기 위해 필요한 필드 값을 수집한다.
	let bno = document.querySelector('#bno').value;
	let reply = document.querySelector('#reply').value;
	let replyer = document.querySelector('#replyer').value;
	
	// 2. 전송할 데이터를 JS object로 생성 {속성명 : 속성}
	let replyObj = {bno : bno
			, reply : reply
			, replyer : replyer}
	
	// 3. 서버에 요청
	fetchPost('/reply/write', replyObj, getResult);
}
	
function fetchPost(url, obj, callback){
	// fetch(url, {method, headers, body})
	// 지정한 URL에 요청 정보를 파라미터로 넘긴다.
	fetch(url, {method:'post'
				, headers:{'Content-Type':'application/json'}
				// JS object를 JSON 타입의 문자열로 변환
				, body:JSON.stringify(obj)})
	    // 컨트롤러로부터 JSON 타입의 객체가 반환
		// 객체를 변수명 response에 받아 와서 json() 메소드를 호출
	    // json() : JSON 형식의 문자열을 Promise 객체로 반환
	    // Promise 객체는 then() 메소드를 사용하여 
	    // 비동기 작업의 성공 또는 실패와 관련된 결과를 나타내는 대리자 역할을 수행
		.then(response => response.json())
		// 반환 받은 객체를 매개 변수로 받는 콜백 함수를 호출
		.then(map => callback(map));
}

function getResult(map){
	if(map.result=='success'){
		getReplyList();
		document.querySelector('#replyer').value = "";
		document.querySelector('#reply').value = "";
	} else {
		alert(map.message);
	}
}

function deleteReply(rno){
	fetchGet('/reply/delete/' + rno, getResult);
}

function editReply(rno){
	let editTr = document.querySelector('#reply' + rno);
	let page = document.querySelector('#page').value;
	let reply = editTr.dataset.reply;
	let replyer = editTr.dataset.replyer;
	let replydate = editTr.dataset.replydate;
	editTr.innerHTML = ''
		+ `		 <td class="col-1">${rno}</td>                      `
		+ `      <td class="text-start col-7"><input type="text" id="editedReply${rno}" class="form-control" value="${reply}"><i class="fa-solid fa-circle-check" onclick="editAction(${rno})"></i> <i class="fa-solid fa-circle-xmark" onclick="getReplyList(${page})"></i></td>                      `
		+ `      <td class="col-1"><input type="text" id="editedReplyer${rno}" class="form-control" value="${replyer}"></td>					`;
}

function editAction(rno){
	// 1. 파라미터 수집 - Reply 객체를 생성하기 위해 필요한 필드 값을 수집한다.
	let editedReply = document.querySelector('#editedReply' + rno).value;
	let editedReplyer = document.querySelector('#editedReplyer' + rno).value;
	
	// 2. 전송할 데이터를 JS object로 생성 (객체명 = {속성명:속성})
	let replyObj = {
		rno : rno
		, reply : editedReply
		, replyer : editedReplyer
	}

	// 3. 서버에 요청
	fetchPost('/reply/edit', replyObj, getResult);
}