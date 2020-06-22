package com.gdu.cashbook.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
	@GetMapping("/cashbook/index")
	public String index() {
		return "index";
	}
	
	// homeController를 따로 만들어도 되지만 IndexController와 비슷한 역할, 내용이 없어보여서 IndexController에 만듦
	@GetMapping("/home")
	public String home(HttpSession session) {
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/login";
		}
		return "home";
	}
}
