package com.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.spring.service.AttachService;
import com.spring.service.BoardService;
import com.spring.vo.Attach;
import com.spring.vo.Board;
import com.spring.vo.Criteria;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/board/*")
@Log4j
public class BoardController {
	
	@Autowired
	BoardService boardService;
	
	@Autowired
	AttachService attachService;
	
	@GetMapping("reply")
	public void reply(){
		
	}
	
	@GetMapping("message")
	public void message() {
		
	}
	
	/*
	 * 파라미터의 자동 수집
	 * 	기본 생성자로 Criteria 객체를 생성한다.
	 */	
	@GetMapping("list")
	public void getList(Criteria criteria, Model model) {
		StopWatch stopwatch = new StopWatch();
		stopwatch.start();
		
		boardService.getList(criteria, model);
		
		stopwatch.stop();
		log.info("수행시간: " + stopwatch.getTotalTimeMillis() + "(ms)초");
	}
	
	@GetMapping("write")
	public void write() {
		
	}

	@PostMapping("write")
	public String insert(Board board, List<MultipartFile> files, Criteria criteria
							, Model model) {
		try {
			if(boardService.insert(board, files)>0) {
				model.addAttribute("bno", board.getBno());
				model.addAttribute("pageno", criteria.getPageno());
				model.addAttribute("searchfield", criteria.getSearchfield());
				model.addAttribute("searchword", criteria.getSearchword());
				model.addAttribute("message", "등록되었습니다.");
				return "/board/message";
			} else {
				// 사실 여기까지 오지 않는다. 예외 발생시 try-catch문에서 처리한다.
				model.addAttribute("message", "게시글 등록 중 오류가 발생하였습니다.");
				return "/board/message";
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
			return "/board/message";
		}
	}
	
	@GetMapping("read")
	public void read(Board board, Attach attach, Model model) {
		model.addAttribute("board", boardService.read(board.getBno()));
		model.addAttribute("attachList", attachService.getList(board.getBno()));		
	}
	
	@GetMapping("delete")
	public String delete(Board board, Criteria criteria, Model model) {
		if(boardService.delete(board.getBno(), criteria)>0) {
			model.addAttribute("message", "삭제되었습니다.");			
		} else {
			model.addAttribute("message", "삭제 중 오류가 발생하였습니다.");
		}			
		return "/board/message";			
	}	
	
	@GetMapping("edit")
	public String edit(Board board, Model model) {
		model.addAttribute("board", boardService.read(board.getBno()));
		return "/board/write";
	}
	
	@PostMapping("edit")
	public String update(Board board, List<MultipartFile> files, Criteria criteria, Model model) {
		try {
			int boardRes = boardService.update(board, files);
			if(boardRes>0) {
				model.addAttribute("bno", board.getBno());
				model.addAttribute("pageno", criteria.getPageno());
				model.addAttribute("searchfield", criteria.getSearchfield());
				model.addAttribute("searchword", criteria.getSearchword());				
				model.addAttribute("message", "수정되었습니다.");
				return "/board/message";
			} else {
				model.addAttribute("message", "수정 중 오류가 발생하였습니다.");
				return "/board/message";
			}
		} catch (Exception e) {
			log.info(e.getMessage());
			e.printStackTrace();
			if(e.getMessage().indexOf("첨부파일")>-1) {
				model.addAttribute("message", e.getMessage());
			} else {
				model.addAttribute("message", "수정 중 오류가 발생하였습니다.");
			}
			return "/board/message";
		}
	}
	
}
