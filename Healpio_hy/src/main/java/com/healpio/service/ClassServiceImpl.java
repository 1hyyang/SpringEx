package com.healpio.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.healpio.mapper.AttachMapper;
import com.healpio.mapper.ClassMapper;
import com.healpio.vo.ClassVO;
import com.healpio.service.AttachService;

@Service
public class ClassServiceImpl implements ClassService {

	@Autowired
	ClassMapper classMapper;
	
	@Autowired
	AttachService attachService;
	
	@Autowired
	AttachMapper attachMapper;

	@Override
	public void getExerciseList(Model model) {
		model.addAttribute("exerciseList", classMapper.getExerciseList());
	}
	
	@Override
	/*
	 * @Transactional은 기본적으로 RuntimeException과 Error에 대해서만 롤백하고
	 * Exception에 대해서는 롤백하지 않는다. 
	 * 즉 checked exception이 발생하면 커밋한다.
	 * 
	 * 어떠한 예외가 발생하더라도 롤백하고 싶다면 rollbackFor = Exception.class를 지정한다.
	 * Exception.class는 모든 예외의 상단에 위치하기 때문이다.
	 */
	@Transactional(rollbackFor = Exception.class)
	// throws Exception : 해당 메서드를 호출한 메서드(컨트롤러의 메서드)가 예외를 처리하도록 던진다.
	public int insert(ClassVO classVO, List<MultipartFile> files) throws Exception {
		int res = classMapper.insert(classVO);
		if(files!=null) {
			attachService.fileupload(files, classVO.getClass_no());				
		}		
		return res;
	}

	@Override
	public void getOne(String class_no, String member_no, Model model) {
		model.addAttribute("classVO", classMapper.getOne(class_no));
		model.addAttribute("attachList", attachMapper.getList(class_no));
		model.addAttribute("scrapYN", classMapper.scrapYN(class_no, member_no));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int update(ClassVO classVO, List<MultipartFile> files) throws Exception {
		int res = classMapper.update(classVO);
		
		String msg = attachService.fileupload(files, classVO.getClass_no());
		if("".equals(msg)) {
			return res;
		} else {
			// (수정 전, 수정하면서) 등록된 파일 삭제 후 새로운 파일 등록
			attachService.delete(classVO.getClass_no());
			attachService.fileupload(files, classVO.getClass_no());
		}
		return res;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int delete(String class_no, Model model) {
		// 첨부파일 삭제
		attachService.delete(class_no);
		
		// 스크랩 삭제
		classMapper.deleteScrap(class_no);
		
		// 글 삭제
		return classMapper.delete(class_no);
	}

	@Override
	public int scrap(String class_no, String member_no) {
		return classMapper.scrap(class_no, member_no);
	}

	@Override
	public int cancelScrap(String class_no, String member_no) {
		return classMapper.cancelScrap(class_no, member_no);
	}
	
}
