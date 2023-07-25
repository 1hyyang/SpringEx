package com.spring.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.service.AttachService;
import com.spring.vo.Attach;

import lombok.extern.log4j.Log4j;
import net.coobird.thumbnailator.Thumbnails;

@Controller
@Log4j
public class FileuploadController extends CommonRestController{

	/*
	 * 파일 업로드 
	 * 	1. pom.xml에 commons-fileupload 라이브러리 추가 
	 * 	2. root-context.xml에 multipartResolver 빈 등록 
	 * 	3. 메소드의 매개 변수로 MultipartFile 이용
	 */
	
	public static final String ATTACHES_DIRECTORY = "C:/upload/";

	@Autowired
	AttachService service;	
	
	@GetMapping("/file/fileupload")
	public void fileupload() {
		
	}

	// 폼 전송 방식으로 파일 업로드
	@PostMapping("/file/fileupload")
	public String fileupload(List<MultipartFile> files, int bno
								, RedirectAttributes redirectA) throws Exception {
		// message를 쿼리스트링으로 바로 넘기는 경우 한글 깨짐이 발생
		// RedirectAttributes 사용하게 되면 인코딩 처리되어 한글 깨짐 방지
		/*
		 * RedirectAttributes
		 * 	redirect시 속성값을 전달(Spring 제공 기능)
		 * 	Model과 같이 매개 변수로 받아서 사용한다.
		 *	- addAttribute : 파라미터로 전달한 후 페이지 전환 
		 *		(쿼리 스트링으로 전달 -> 값을 받아올 때 ${param.name속성명}을 사용해야 한다.)
		 * 	- addFlashAttribute : 세션에 일회성으로 저장한 후 페이지 전환
		 * 		(새로고침시 유지되지 않는다.)
		 */
		redirectA.addAttribute("message", service.fileupload(files, bno));
		// getMapping (jsp 파일 반환 x)
		return "redirect:/file/fileupload";
	}
	
	// REST 방식으로 파일 업로드
	@PostMapping("/file/fileuploadByFetch")
	public @ResponseBody Map<String, Object> fileuploadByFetch(List<MultipartFile> files
																, int bno) throws Exception {
		return responseMap("success", service.fileupload(files, bno));
	}


	@GetMapping("/file/list/{bno}")
	public @ResponseBody Map<String, Object> getList(@PathVariable("bno") int bno) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", service.getList(bno));
		return map;
	}

	@GetMapping("/file/delete/{uuid}/{bno}")
	public @ResponseBody Map<String, Object> delete(@PathVariable("uuid") String uuid
													, @PathVariable("bno") int bno){
		// 디렉토리에서 삭제
		
		// DB에서 삭제	
		return responseDeleteMap(service.delete(uuid, bno));
	}
	
	/*
	 * 파일 다운로드
	 * 	컨텐츠 타입을 다운로드 할 수 있는 형식으로 지정하여 
	 * 	브라우저에서 파일을 다운로드 할 수 있도록 처리
	 */	
	
	/*
	 * ResponseEntity
	 * 	HttpEntity 클래스를 상속받아 구현한 클래스
	 * 	사용자의 HttpRequest에 대한 응답 데이터를 포함하는 클래스
	 */
	
	@GetMapping("/file/download")
	public ResponseEntity<byte[]> download(String filename){
		File file = new File(ATTACHES_DIRECTORY + filename);		
		if(file.exists()) {
			HttpHeaders headers = new HttpHeaders();
			// 컨텐츠 타입을 지정
			// APPLICATION_OCTET_STREAM : 이진 파일의 콘텐츠 유형
			headers.add("contentType", MediaType.APPLICATION_OCTET_STREAM.toString());
			
			// 컨텐츠에 대한  추가 설명 및 한글 파일 이름에 대한 처리
			try {
				headers.add("Content-Disposition", "attachment; filename=\"" 
							+ new String(filename.getBytes("UTF-8"), "ISO-8859-1") 
							+ "\"");
				return new ResponseEntity<>(FileCopyUtils.copyToByteArray(file)
											, headers, HttpStatus.OK);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			} catch (IOException e) {
				e.printStackTrace();
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
