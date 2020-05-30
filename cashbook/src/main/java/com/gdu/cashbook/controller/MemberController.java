package com.gdu.cashbook.controller;

import java.util.Map;

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
import com.gdu.cashbook.vo.MemberForm;

@Controller
public class MemberController {
	@Autowired
	private MemberService memberService;
	
	
	// 회원 비밀번호 찾기
	@GetMapping("/findMemberPw")
	public String findMemberPw(HttpSession session) {
		if(session.getAttribute("loginMember") != null) {
			return "redirect:/";
		}
		return "findMemberPw";
	}
	
	@PostMapping("/findMemberPw")
	public String findMemberPw(Model model, Member member, HttpSession session) {
		if(session.getAttribute("loginMember") != null) {
			return "redirect:/";
		}
		int row = memberService.getMemberPw(member);
		String msg = "아이디와 이메일을 확인하세요.";
		if(row == 1) {
			msg = "비밀번호를 입력한 이메일로 전송했습니다."; 
		}
		model.addAttribute("msg", msg);
		return "findMemberPwResult";
	}
	// 회원 아이디 찾기
	@GetMapping("/findMemberId")
	public String findMemberId(HttpSession session) {
		if(session.getAttribute("loginMember") != null) {
			return "redirect:/";
		}
		return "findMemberId";
	}
	
	@PostMapping("/findMemberId")
	public String findMemberId(Model model, Member member, HttpSession session) {
		if(session.getAttribute("loginMember") != null) {
			return "redirect:/";
		}
		String findMemberId = memberService.getMemberIdByMember(member);
		
		model.addAttribute("findMemberId", findMemberId);
		return "findMemberIdResult";
	}
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
	public String modifyMember(Model model, MemberForm memberForm, HttpSession session) {
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/index";
		}
		System.out.println(memberForm.getMemberAddr());
		System.out.println(memberForm.getMemberName());
		System.out.println(memberForm.getMemberEmail());
		System.out.println(memberForm.getMemberPhone());
		System.out.println(memberForm.getMemberPic());
		LoginMember loginMember = (LoginMember)session.getAttribute("loginMember");
		
		
			if(memberForm.getMemberPic().getContentType().equals("image/png") || 
					memberForm.getMemberPic().getContentType().equals("image/jpeg") ||
					memberForm.getMemberPic().getContentType().equals("image/gif")) 
				{ // 입력과 저장타입의 불일치로 service에서 memberForm -> member + 폴더에 파일 저장
					int result = memberService.modifyMemberInfo(memberForm, loginMember);
					System.out.println(result + "<-- result");
					model.addAttribute("loginMember", loginMember);
					return "redirect:/memberInfo";
				}
			memberService.modifyMemberInfo(memberForm, loginMember);
			model.addAttribute("loginMember", loginMember);
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
	public String memberInfo(Model model, HttpSession session,
			@RequestParam(value="currentPage", defaultValue="1") int currentPage) {
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/";
		}
		LoginMember loginMember = (LoginMember)session.getAttribute("loginMember");
		String admin = loginMember.getMemberId();
		if(admin.equals("admin")) {
			Map<String, Object> adminMemberInfo = memberService.getAdminMemberInfoList(currentPage);
			model.addAttribute("memberInfoList", adminMemberInfo.get("list"));
			model.addAttribute("lastPage", adminMemberInfo.get("lastPage"));
			model.addAttribute("loginMember", loginMember);
			return "adminMemberInfo";
		}
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
	public String addMember(Model model, MemberForm memberForm, HttpSession session) {
		if(session.getAttribute("loginMember") != null) {
			return "redirect:/";
		}
		// 이미지 파일만 업로드 할 수 있음
		if(memberForm.getMemberPic() != null) {
			if(memberForm.getMemberPic().getContentType().equals("image/png") || 
				memberForm.getMemberPic().getContentType().equals("image/jpeg") ||
				memberForm.getMemberPic().getContentType().equals("image/gif")) 
			{ // 입력과 저장타입의 불일치로 service에서 memberForm -> member + 폴더에 파일 저장
				memberService.addMember(memberForm);
				return "redirect:/index";
			}
		}
		System.out.println(memberForm + "<-- addMember memberForm");
		return "redirect:/addMember";
	}
}