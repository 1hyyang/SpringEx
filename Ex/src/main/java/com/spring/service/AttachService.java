package com.spring.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.spring.vo.Attach;

@Service
public interface AttachService {

	public String fileupload(List<MultipartFile> files, int bno) throws Exception;
	public String getUploadpath();
	int insert(Attach attach);
	List<Attach> getList(int bno);
	int delete(String uuid, int bno);
	
}
