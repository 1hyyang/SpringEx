package com.healpio.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.healpio.vo.AttachVO;

@Service
public interface AttachService {

	public String fileupload(List<MultipartFile> files, String class_no) throws Exception;
	public String getUploadpath();
	int insert(AttachVO attachVO);
	List<AttachVO> getList(String class_no);
	int delete(String class_no);
	
}