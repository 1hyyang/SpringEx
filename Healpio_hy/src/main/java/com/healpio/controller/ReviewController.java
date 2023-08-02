package com.healpio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.healpio.service.ClassService;
import com.healpio.service.ReviewService;
import com.healpio.vo.ReviewVO;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/review/*")
@Log4j
public class ReviewController {
	
	@Autowired
	ReviewService reviewService;
	
	@Autowired
	ClassService classService;

	@GetMapping("write")
	public String insert(ReviewVO reviewVO, Model model) {
		if(reviewService.insert(reviewVO)>0) {
			model.addAttribute("review_no", reviewVO.getReview_no());
			model.addAttribute("class_no", reviewVO.getClass_no());			
			model.addAttribute("message", "등록되었습니다.");
			return "/class/message";
		} else {
			model.addAttribute("message", "게시글 등록 중 오류가 발생하였습니다.");
			return "/class/message";
		}
	}
	
//	@GetMapping("read")
//	public void read(String class_no, String member_no, Model model) {
//		System.out.println("----");
//		classService.getOne(class_no, "M000002", model);
//	}
//	
//	@GetMapping("edit")
//	public void edit(String class_no, String member_no, Model model) {
//		classService.getOne(class_no, "M000002", model);
//		classService.getExerciseList(model);
//	}
//	
//	@PostMapping("edit")
//	public String edit(ClassVO classVO, List<MultipartFile> files, Model model) {		
//		try {
//			if(classService.update(classVO, files)>0) {
//				model.addAttribute("class_no", classVO.getClass_no());	
//				model.addAttribute("message", "수정되었습니다.");
//				return "/class/message";
//			} else {
//				model.addAttribute("message", "수정 중 오류가 발생하였습니다.");
//				return "/class/message";
//			}
//		} catch (Exception e) {
//			log.info(e.getMessage());
//			e.printStackTrace();
//			if(e.getMessage().indexOf("첨부파일")>-1) {
//				model.addAttribute("message", e.getMessage());
//			} else {
//				model.addAttribute("message", "수정 중 오류가 발생하였습니다.");
//			}
//			return "/board/message";
//		}
//	}
//	
//	@GetMapping("delete")
//	public String delete(String class_no, Model model) {
//		if(classService.delete(class_no, model)>0) {
//			model.addAttribute("message", "삭제되었습니다.");			
//		} else {
//			model.addAttribute("message", "삭제 중 오류가 발생하였습니다.");
//		}			
//		return "/class/message";
//	}
//	
//	@GetMapping("scrap")
//	@ResponseBody
//	public Map<String, Object> scrap(String class_no, String member_no) {
//		Map<String, Object> map = new HashMap<String, Object>();
//		if(classService.scrap(class_no, "M000002")>0) {
//			map.put("result", "success");
//		} else {
//			map.put("result", "fail");
//		}
//		return map;		
//	}
//	
//	@GetMapping("cancelScrap")
//	@ResponseBody
//	public Map<String, Object> cancelScrap(String class_no, String member_no) {
//		Map<String, Object> map = new HashMap<String, Object>();
//		if(classService.cancelScrap(class_no, "M000002")>0) {
//			map.put("result", "success");
//		} else {
//			map.put("result", "fail");
//		}
//		return map;
//	}
	
}
