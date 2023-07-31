package com.healpio.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.healpio.vo.ClassVO;

@Service
public interface ClassService {

	public void getExerciseList(Model model);
	public int insert(ClassVO classVO, List<MultipartFile> files) throws Exception;
	public void getOne(String class_no, String member_no, Model model);
	public int update(ClassVO classVO, List<MultipartFile> files) throws Exception;
	public int delete(String class_no, Model model);
	public int scrap(String class_no, String member_no);
	public int cancelScrap(String class_no, String member_no);
	
}
