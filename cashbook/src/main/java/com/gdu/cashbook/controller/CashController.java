package com.gdu.cashbook.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.gdu.cashbook.service.CashService;
import com.gdu.cashbook.vo.Cash;
import com.gdu.cashbook.vo.LoginMember;

@Controller
public class CashController {
	@Autowired
	private CashService cashService;
	
	// 수입, 지출 
	@GetMapping("/getCashListByDate")
	public String getCashListByDate(Model model, HttpSession session) {
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/index";
		}
		// memberId
		String memberId = ((LoginMember)session.getAttribute("loginMember")).getMemberId();
		
		// 오늘 날짜를 받아오는 다른 방법
		Date today = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String strToday = sdf.format(today);
		
		System.out.println(sdf.format(today) + " <-- today");
		// 오늘 날짜를 받아오는  Calendar 클래스
		//Calendar today2 = Calendar.getInstance();
		//String strToday = "";
		
		Cash cash = new Cash();
		cash.setMemberId(memberId);
		cash.setCashDate(strToday); // sdf.format(today);
		List<Cash> cashList = cashService.getCashListByDate(cash);
		
		model.addAttribute("today", strToday);
		model.addAttribute("cashList", cashList);
		
		for(Cash c : cashList) {
			System.out.println(c);
		}
		
		return "getCashListByDate";
	}
}
