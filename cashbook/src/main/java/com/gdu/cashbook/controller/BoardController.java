package com.gdu.cashbook.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gdu.cashbook.service.BoardService;
import com.gdu.cashbook.vo.LoginMember;

@Controller
public class BoardController {
	@Autowired
	private BoardService boardService;
	
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
