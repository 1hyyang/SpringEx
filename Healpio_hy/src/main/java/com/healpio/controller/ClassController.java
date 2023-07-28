package com.healpio.controller;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.healpio.service.ClassService;
import com.healpio.vo.ClassVO;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/class/*")
@Log4j
public class ClassController {
	
	@Autowired
	ClassService classService;
	
	@GetMapping("write")
	public void write(Model model) {
		classService.getExerciseList(model);
	}
	
	@PostMapping("write")
	public String insert(ClassVO classVO, List<MultipartFile> files, Model model) {
		try {
			if(classService.insert(classVO, files)>0) {
				model.addAttribute("class_no", classVO.getClass_no());
				model.addAttribute("message", "등록되었습니다.");
				return "/class/message";
			} else {
				// 사실 여기까지 오지 않는다. 예외 발생시 try-catch문에서 처리한다.
				model.addAttribute("message", "게시글 등록 중 오류가 발생하였습니다.");
				return "/class/message";
			}
		} catch (Exception e) {
			e.printStackTrace();
			// Exception Message에 첨부파일이라는 단어가 들어 있으면 해당 Exception Message를 저장
			if(e.getMessage().indexOf("첨부파일")>-1) {
				model.addAttribute("message", e.getMessage());
			} else {
				// 들어 있지 않으면 글 등록과 관련된 예외이다.
				model.addAttribute("message", "게시글 등록 중 오류가 발생하였습니다.");
			}
			return "/class/message";
		}
	}
	
	@GetMapping("read")
	public void read(String class_no, Model model) {
		classService.getOne("C000012", model);
	}
	
	@GetMapping("edit")
	public void edit(String class_no, Model model) {
		classService.getOne(class_no, model);
		classService.getExerciseList(model);
	}
	
	@PostMapping("edit")
	public void edit(String class_no, List<MultipartFile> files, Model model) {
		
	}
	
}
