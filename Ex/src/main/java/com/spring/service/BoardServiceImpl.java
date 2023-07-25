package com.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.spring.mapper.BoardMapper;
import com.spring.vo.Attach;
import com.spring.vo.Board;
import com.spring.vo.Criteria;
import com.spring.vo.PageVo;
import com.spring.vo.Reply;

/*
 * 각 계층 간의 연결은 인터페이스를 활용하여 느슨한 결합을 한다.
 * 느슨한 결합 : 하나의 컴포넌트의 변경이 다른 컴포넌트들의 변경을 요구하는 위험을 줄이는 것을 목적으로 하는 시스템에서
 * 			 컴포넌트 간의 내부 의존성을 줄이는 것을 추구하는 디자인 목표 
 * 
 * Service
 * 	계층 구조상 비즈니스 영역을 담당하는 객체임을 표시
 * 
 * root-context.xml
 * 	component-scan 속성에 패키지를 등록
 * 
 * 서비스를 인터페이스로 생성하는 까닭
 * 	1. 내부 로직의 분리
 * 		인터페이스를 사용함으로써 내부 로직의 변경, 수정시 유연하게 대처할 수 있다.
 * 	2. 구현체의 전환이 용이
 * 		구현체의 변경, 교체가 용이하다.
 * 	3. 테스트가 용이
 * 		단위 테스트시 테스트용 구현체를 이용하여 테스트를 수행할 수 있다.
 */

@Service
public class BoardServiceImpl implements BoardService{
	
	@Autowired
	private BoardMapper boardMapper;
	
	@Autowired
	private AttachService attachService;
	
	@Autowired
	private ReplyService replyService;

	@Override
	public void getList(Criteria criteria, Model model) {		
		/*
		 * 1. 목록 조회 - 검색, 페이지당 게시물 수(startnum~endnum)
		 * 2. 총 게시물 수 조회
		 * 3. pageVo 객체 생성 - 페이지 블록(startno~endno)
		 */		
		List<Board> list = boardMapper.getList(criteria);
		int total = boardMapper.getTotal(criteria);
		PageVo pageVo = new PageVo(criteria, total);
		
		model.addAttribute("list", list);
		model.addAttribute("pageVo", pageVo);
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
	public int insert(Board board, List<MultipartFile> files) throws Exception {
		int res = boardMapper.insert(board);
		if(files!=null) {
			attachService.fileupload(files, board.getBno());				
		}		
		return res;
	}

	@Override
	public Board read(int bno) {
		return boardMapper.read(bno);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int delete(int bno, Criteria criteria) {
		// 답글 삭제
		List<Reply> replyList = replyService.getList(bno, criteria);
		if(replyList!=null) {
			for(Reply reply:replyList) {
				replyService.delete(reply.getRno());
			}
		}
		
		// 첨부파일 삭제
		List<Attach> attachList = attachService.getList(bno);
		if(attachList!=null) {
			for(Attach attach:attachList) {
				attachService.delete(attach.getUuid(), bno);
			}
		}
		
		// 글 삭제
		return boardMapper.delete(bno);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int update(Board board, List<MultipartFile> files) throws Exception {
		int res = boardMapper.update(board);
		if(files!=null) {
			attachService.fileupload(files, board.getBno());				
		}		
		return res;
	}

	@Override
	public int updateReplyCnt(int bno, int amount) {
		return boardMapper.updateReplyCnt(bno, amount);
	}

}
