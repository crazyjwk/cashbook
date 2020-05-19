package com.gdu.cashbook.controller;

import java.time.LocalDate;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gdu.cashbook.service.CashService;
import com.gdu.cashbook.vo.Cash;
import com.gdu.cashbook.vo.LoginMember;

@Controller
public class CashController {
	@Autowired
	private CashService cashService;
	
	@GetMapping("/addCash")
	public String addCash(Model model, HttpSession session) {
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/index";
		}
		String memberId = ((LoginMember)(session.getAttribute("loginMember"))).getMemberId();
		model.addAttribute("memberId", memberId);
		return "addCash";
	}
	@PostMapping("/addCash")
	public String addCash(HttpSession session, Cash cash) {
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/index";
		}
		System.out.println(cash.getMemberId());
		System.out.println(cash.getCategoryName());
		System.out.println(cash.getCashKind());
		System.out.println(cash.getCashPrice());
		System.out.println(cash.getCashPlace());
		System.out.println(cash.getCashMemo());
		return "redirect:/";
	}
	// 수입, 지출 
	@GetMapping("/removeCash")
	public String removeCash(HttpSession session, Cash cash) {
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/index";
		}
		System.out.println(cash.getCashNo() + "<-- cashNo");
		System.out.println(cash.getMemberId() + "<-- cashMemberId");
		cashService.removeCash(cash.getCashNo());
		return "redirect:/getCashListByDate?"+cash.getMemberId();
	}
	Integer test = null;
	@GetMapping("/getCashListByDate")
	public String getCashListByDate(Model model, HttpSession session, 
			@RequestParam(value="day", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate day) {
			// RequestParam -> DateTimeFormat으로 자동 형변환
		if(day == null) {
			day = LocalDate.now();
		}
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/index";
		}
		// memberId
		String memberId = ((LoginMember)session.getAttribute("loginMember")).getMemberId();
		
		// 오늘 날짜를 받아오는 다른 방법
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		String strToday = sdf.format(day);
//		
//		System.out.println(sdf.format(day) + " <-- today");
		
		Cash cash = new Cash();
		cash.setMemberId(memberId);
		cash.setCashDate(day);
		
		Map<String, Object> list = cashService.getCashListByDate(cash);
		
		Integer sumCash = (Integer)list.get("sumCash");
		if(sumCash == null) {
			sumCash = 0;
		}
		model.addAttribute("day", day);
		model.addAttribute("cashList", list.get("cashList"));
		model.addAttribute("sumCash", sumCash);
			
		return "getCashListByDate";
	}
}
