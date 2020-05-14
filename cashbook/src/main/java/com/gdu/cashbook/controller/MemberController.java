package com.gdu.cashbook.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gdu.cashbook.service.MemberService;
import com.gdu.cashbook.vo.LoginMember;
import com.gdu.cashbook.vo.Member;

@Controller
public class MemberController {
	@Autowired
	private MemberService memberService;
	
	// 회원정보 수정
	@GetMapping("/modifyMemberInfo")
	public String modifyMember(Model model, LoginMember loginMember, HttpSession session) {
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/index";
		}
		Member member = memberService.getModifyMemberOne(loginMember);
		model.addAttribute("loginMember", loginMember);
		model.addAttribute("member", member);
		return "modifyMember";
	}
	@PostMapping("/modifyMemberInfo")
	public String modifyMember(Model model, Member member, HttpSession session) {
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/index";
		}
		System.out.println(member.getMemberAddr());
		System.out.println(member.getMemberName());
		System.out.println(member.getMemberEmail());
		System.out.println(member.getMemberPhone());
		String modifyPwCk = memberService.modifyPwCk(member.getMemberPw()); 
		LoginMember loginMember = (LoginMember)session.getAttribute("loginMember");
		int modifyMember = 0;
		if(modifyPwCk != null) {
			modifyMember = memberService.modifyMemberInfo(member);
		} else {
			model.addAttribute("loginMember", loginMember);
			return "memberInfo";
		}
		if(modifyMember == 1) {
			System.out.println("수정 성공");
		} else {
			System.out.println("수정 실패");
		}
		return "redirect:/memberInfo";
		
	}
	
	// 회원탈퇴
	@GetMapping("/removeMember")
	public String removeMember(HttpSession session, LoginMember loginMember) {
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/index";
		}
		return "removeMember"; 
	}
	@PostMapping("/removeMember")
	public String removeMember(HttpSession session, @RequestParam("memberPw") String memberPw) {
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/index";
		}
		LoginMember loginMember = (LoginMember)session.getAttribute("loginMember"); 
		loginMember.setMemberPw(memberPw); // removeMember.html에서 입력한 비밀번호로 변경
		System.out.println(loginMember.getMemberId());
		System.out.println(loginMember.getMemberPw());
		memberService.removeMember(loginMember); // 삭제 메서드 호출
		session.invalidate();
		return "redirect:/";
	}
	
	// 회원정보
	@GetMapping("/memberInfo")
	public String memberInfo(Model model, HttpSession session) {
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/";
		}
		LoginMember loginMember = (LoginMember)session.getAttribute("loginMember");
		Member member = memberService.getMemberOne(loginMember);
		model.addAttribute("member", member);
		model.addAttribute("loginMember", loginMember);
		return "memberInfo";
	}
	
	@PostMapping("/checkMemberId")
	public String checkMemberId(Model model, @RequestParam("memberIdCk") String memberIdCk, HttpSession session) {
		if(session.getAttribute("loginMember") != null) {
			return "redirect:/";
		}
		String confirmMemberId = memberService.checkmemberId(memberIdCk);
		System.out.println(confirmMemberId);
		if(confirmMemberId == null) {
			//사용
			model.addAttribute("memberIdCk", memberIdCk);
		} else {
			//노사용
			model.addAttribute("msg", "사용할 수 없습니다.");
		}
		return "addMember";
	}
	// login GET, POST
	@GetMapping("/login")
	public String login(HttpSession session) {
		// 세션값이 있으면(=로그인 중) index로 redirect
		if(session.getAttribute("loginMember") != null) {
			return "redirect:/";
		} 
		return "login";
	}
	@PostMapping("/login")
	public String login(Model model, LoginMember loginMember, HttpSession session) {
		System.out.println(loginMember);
		LoginMember returnLoginMember = memberService.login(loginMember);
		if(returnLoginMember == null) {
			model.addAttribute("msg", "아이디와 비밀번호를 확인하세요"); // 왜 이렇게 되는지 생각해보자 ㅎ
			return "login";
		} else {
			session.setAttribute("loginMember", returnLoginMember);
			return "redirect:/home";
		}	
	}
	
	// logout GET
	@GetMapping("logout")
	public String logout(HttpSession session) {
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/";
		}
		session.invalidate();
		return "redirect:/";
	}
	
	// addMember GET, POST
	@GetMapping("/addMember")
	public String addMember(HttpSession session) {
		if(session.getAttribute("loginMember") != null) { // 로그인 중이면  index로 redirect
			return "redirect:/";
		}
		return "addMember";
	}
	@PostMapping("/addMember")
	public String addMember(Member member, HttpSession session) {
		memberService.addMember(member);
		System.out.println(member);
		return "redirect:/index";
	}
}
