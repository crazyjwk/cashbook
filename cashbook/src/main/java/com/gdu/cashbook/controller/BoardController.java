package com.gdu.cashbook.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gdu.cashbook.service.BoardService;
import com.gdu.cashbook.vo.Board;
import com.gdu.cashbook.vo.Comment;
import com.gdu.cashbook.vo.LoginMember;

@Controller
public class BoardController {
	@Autowired
	private BoardService boardService;
	
	@GetMapping("/removePost")
	public String removePost(HttpSession session, Comment comment,
			@RequestParam(value="boardId", required = false) String boardId) {
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/";
		}
		
		String adminCheck = ((LoginMember)session.getAttribute("loginMember")).getMemberId();
		if(adminCheck.equals("admin")) {
			comment.setMemberId(boardId);
			int result = boardService.removePost(comment);
			if(result == 0) {
				System.out.println("admin 게시글 삭제 실패");
			} else {
				System.out.println("admin 게시글 삭제 성공");
			}
			return "redirect:/boardList";
		}
		
		int result = boardService.removePost(comment);
		if(result == 0) {
			System.out.println("게시글 삭제 실패");
		} else {
			System.out.println("게시글 삭제 성공");
		}
		return "redirect:/boardList";
	}
	
	@GetMapping("/removeComment")
	public String removeCommen(HttpSession session, Comment comment,
			@RequestParam(value="commentId", required = false) String commentId) {
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/";
		}
		String adminCheck = ((LoginMember)session.getAttribute("loginMember")).getMemberId();
		if(adminCheck.equals("admin")) {
			comment.setMemberId(commentId);
			int result = boardService.removeComment(comment);
			if(result == 0) {
				System.out.println("admin 댓글 삭제 실패");
			} else {
				System.out.println("admin 댓글 삭제 성공");
			}
			return "redirect:/detailView?boardNo=" + comment.getBoardNo();
		}
		int result = boardService.removeComment(comment);
		
		if(result == 0) {
			System.out.println("댓글 삭제 실패");
		} else {
			System.out.println("댓글 삭제 성공");
		}
		
		return "redirect:/detailView?boardNo=" + comment.getBoardNo();
	}
	
	// 댓글 달기
	@PostMapping("/addComment")
	public String addComment(HttpSession session, Comment comment) {
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/";
		}
		int result = boardService.addComment(comment);
		
		if(result == 0) {
			System.out.println("댓글 작성 실패");
		} else {
			System.out.println("댓글 작성 성공");
		}
		
		return "redirect:/detailView?boardNo=" + comment.getBoardNo();
	}
	
	// 게시글 수정
	@GetMapping("/modifyPost")
	public String modifyPost(Model model, HttpSession session, @RequestParam(value="boardNo") int boardNo) {
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/";
		}
		
		String memberId = ((LoginMember)session.getAttribute("loginMember")).getMemberId();
		Board board = new Board();
		board.setMemberId(memberId);
		board.setBoardNo(boardNo);
		
		Board postOne = boardService.getPostOne(board);
		
		model.addAttribute("postOne", postOne);
		model.addAttribute("memberId", memberId);
		model.addAttribute("boardNo", boardNo);
		return "modifyPost";
	}
	@PostMapping("/modifyPost")
	public String modifyPost(Model model, HttpSession session, Board board) {
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/";
		}
		
		System.out.println(board.getBoardTitle() + "<-- boardTitle");
		System.out.println(board.getBoardContent() + "<-- boardContent");
	
		int result = boardService.modifyPost(board);
		
		if(result == 0) {
			System.out.println("수정 실패");
		} else {
			System.out.println("수정 성공");
		}
		return "redirect:/detailView?boardNo=" + board.getBoardNo();
	}
	
	// 상세보기
	@GetMapping("/detailView")
	public String detailView(Model model, HttpSession session, @RequestParam(value="boardNo") int boardNo,
			@RequestParam(value="currentPage", defaultValue="1") int currentPage) {
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/";
		}
		String memberId = ((LoginMember)session.getAttribute("loginMember")).getMemberId();
		Map<String, Object> detailView = boardService.getDetailView(boardNo, currentPage);
		model.addAttribute("memberId", memberId);
		model.addAttribute("boardOne", detailView.get("boardOne"));
		model.addAttribute("lastPage", detailView.get("lastPage"));
		model.addAttribute("commentList", detailView.get("commentList"));
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("boardNo", boardNo);
		return "/detailView";
	}
	
	// 글쓰기
	@GetMapping("/addPost")
	public String addPost(Model model, HttpSession session) {
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/";
		}
		
		String memberId = ((LoginMember)session.getAttribute("loginMember")).getMemberId();
		model.addAttribute("memberId", memberId);
		return "addPost";
	}
	@PostMapping("/addPost")
	public String addPost(Model model, HttpSession session, Board board) {
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/";
		}
	
		int result = boardService.addPost(board);
	
		if(result == 1) {
			System.out.println("작성 성공");
		} else {
			System.out.println("작성 실패");
		}
		return "redirect:/boardList?memberId="+board.getMemberId();
	}
	// 게시판 목록
	@GetMapping("/boardList")
	public String boardList(Model model, HttpSession session,
			@RequestParam(value="currentPage", defaultValue="1" ) int currentPage) {
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/";
		}
		
		String memberId = ((LoginMember)session.getAttribute("loginMember")).getMemberId();
		
		Map<String, Object> boardList = boardService.getBoardList(currentPage);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("memberId", memberId);
		model.addAttribute("boardList", boardList.get("list"));
		model.addAttribute("lastPage", boardList.get("lastPage"));
		
		System.out.println(boardList.get("list"));
		return "boardList";
	}
}
