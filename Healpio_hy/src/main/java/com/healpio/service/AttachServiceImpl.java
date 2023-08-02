package com.healpio.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.healpio.mapper.AttachMapper;
import com.healpio.vo.AttachVO;

import lombok.extern.log4j.Log4j;
import net.coobird.thumbnailator.Thumbnails;

@Service
@Log4j
public class AttachServiceImpl implements AttachService {
	
	// public static final String ATTACHES_DIRECTORY = "C:/upload/";
	public static final String ATTACHES_DIRECTORY = "C:/Users/user/git/springEx/Healpio_hy/src/main/webapp/resources/images/";
	
	@Autowired
	AttachMapper attachMapper;
	
	public String fileupload(List<MultipartFile> files, String class_no) throws Exception {
		String message = "";
		
		// 선택된 파일 받아 오기
		for(MultipartFile file:files) {
			// 선택된 파일이 없는 경우 다음 파일로 이동
			if(file.isEmpty()) {
				// continue : 이후의 문장을 실행하지 않고 for문으로 이동하여 반복 수행
				continue;
			}
			log.info("oFilename: " + file.getOriginalFilename());
			log.info("name: " + file.getName());
			log.info("size: " + file.getSize());

			// 파일명 생성
			/*
			 * UUID 
			 * 	소프트웨어 구축에 쓰이는 식별자 표준 
			 * 	파일명이 중복되어 파일이 소실되지 않도록 UUID를 붙여서 저장
			 */
			UUID uuid = UUID.randomUUID();
			String sFilename = uuid + "_" + file.getOriginalFilename();
			// 파일 저장 경로 지정
			String uploadpath = getUploadpath();
			// 특정 경로를 가지는 File 객체를 생성
			File sFile = new File(ATTACHES_DIRECTORY 
									+ uploadpath + sFilename);
			log.info("sFile: " + sFile);
			try {
				// transferTo() : 지정한 경로에 파일 저장 (디렉토리에 저장)
				file.transferTo(sFile);

				// 저장된 파일의 Mime 타입 확인
				String contentType = Files.probeContentType(sFile.toPath());
				// sFile과 sFile.toPath()가 같은데 꼭 toPath() 메소드를 사용해야 하나?
				log.info("sFile.toPath(): " + sFile.toPath());
				log.info("contentType: " + contentType);

				// DB에 저장하기 위해 생성
				AttachVO attachVO = new AttachVO();
				
				// Thumbnailator 라이브러리 추가
				// Mime 타입이 이미지인 경우 썸네일을 생성
				if(contentType!=null && contentType.startsWith("image")) {
					// Thumbnails.of(원본파일).size(저장할크기).toFile(새파일)
					// 디렉토리에 새 파일(썸네일) 저장
					Thumbnails.of(sFile).size(300, 300)
						.toFile(ATTACHES_DIRECTORY 
								+ uploadpath + "thumb_" + sFilename);

					// DB에 파일 타입 저장
					attachVO.setFiletype("I");
				} else {
					attachVO.setFiletype("F");
				}

				// DB에 저장
				attachVO.setUuid(uuid.toString());
				attachVO.setUploadpath(uploadpath);
				attachVO.setFilename(file.getOriginalFilename());
				attachVO.setClass_no(class_no);
				if(insert(attachVO)>0) {
					message = "등록되었습니다.";			
				} 
			} catch (IllegalStateException e) {
				e.printStackTrace();
				// 여기서 던진 Exception을 호출한 메서드가 받는다. 
				throw new Exception("첨부파일 등록 중 오류가 발생하였습니다. (IllegalStateException)");
			} catch (IOException e) {
				e.printStackTrace();
				throw new Exception("첨부파일 등록 중 오류가 발생하였습니다. (IOException)");
			} catch (Exception e) {	
				e.printStackTrace();
				throw new Exception("첨부파일 등록 중 오류가 발생하였습니다. (Exception)");
			}
		}
		
		return message;
	}

	// 업로드 날짜를 폴더명으로 사용하여 파일 관리
	public String getUploadpath() {
		LocalDate currentdate = LocalDate.now();
		// 2023\07\18\ // 마지막 구분자 있어야 한다.
		String uploadpath 
			= currentdate.toString().replace("-", File.separator) + File.separator;
		log.info("오늘날짜: " + currentdate);
		log.info("경로: " + uploadpath);

		// 디렉토리가 없으면 생성
		// 원래 File() 메소드는 디렉토리와 파일명을 포함한 pathname을 파라미터로 받아 File 객체를 생성
		// 다음의 경우는 File() 메소드를 통해 디렉토리를 생성
		// 	디렉토리가 존재하지 않는 상태에서 File 객체를 생성한 후 (디렉토리 생성 x)
		// 	mkdir(), mkdirs() 메소드를 사용하여 디렉토리를 생성한다.
		File uploadDir = new File(ATTACHES_DIRECTORY + uploadpath);
		// 디렉토리가 없으면
		if (!uploadDir.exists()) {
			// mkdirs() : 새로운 디렉토리 생성
			if (uploadDir.mkdirs()) {
				log.info("디렉토리 생성");
			} else {
				log.info("디렉토리 생성 실패");
			}
		}

		return uploadpath;
	}
	
	@Override
	public int insert(AttachVO attachVO) {
		return attachMapper.insert(attachVO);
	}
	
	@Override
	public List<AttachVO> getList(String class_no) {
		return attachMapper.getList(class_no);
	}

	@Override
	public int delete(String class_no) {
		// 디렉토리에서 삭제
		List<AttachVO> attachList = attachMapper.getList(class_no);
		for(AttachVO attachVO:attachList) {
			String filepath = attachVO.getFilepath();
			String thumb_filepath = attachVO.getThumb_filepath();
			
			if(filepath!=null && !"".equals(filepath)) {
				File file = new File(ATTACHES_DIRECTORY + filepath);
				if(file.exists()) {
					// 파일 삭제
					// 메서드 실행 결과가 true가 아니라면
					if(!file.delete()) {
						System.err.println("파일 삭제 실패");
					}
				}
			}
			if(thumb_filepath!=null && !"".equals(thumb_filepath)) {
				File file = new File(ATTACHES_DIRECTORY + thumb_filepath);
				if(file.exists()) {
					if(!file.delete()) {
						System.err.println("파일 삭제 실패");
					}
				}
			}			
		}
		
		// DB에서 삭제
		return attachMapper.delete(class_no);
	}

}

