package com.gdu.cashbook.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gdu.cashbook.mapper.CashMapper;
import com.gdu.cashbook.vo.Cash;
import com.gdu.cashbook.vo.Category;

@RestController
public class CashRestController {
	@Autowired
	private CashMapper cashMapper;
	
	@GetMapping("/getCategoryList")
	public List<Category> getCategoryList() {
		List<Category> categoryList = cashMapper.selectCategoryList();
		System.out.println(categoryList.size() + "<-- categoryList size");
		return categoryList;
		
	}
	@PostMapping("/addCash2")
	public void addCash(Cash cash) {
		System.out.println(cash.getCategoryName());
		System.out.println(cash.getCashbookNo());
		System.out.println(cash.getCashKind());
		System.out.println(cash.getCashPrice());
		System.out.println(cash.getMemberId());
		
		cashMapper.insertCash(cash);
	}
}
