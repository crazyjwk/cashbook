package com.gdu.cashbook.controller;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
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
import com.gdu.cashbook.vo.Cashbook;
import com.gdu.cashbook.vo.Category;
import com.gdu.cashbook.vo.LoginMember;
import com.gdu.cashbook.vo.dayAndMonthAndYearAndPrice;

@Controller
public class CashController {
	@Autowired
	private CashService cashService;
	
	
	@GetMapping("/compareToMonth")
	public String compareToMonth (Model model, HttpSession session, 
			@RequestParam(value="day", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate day) {
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/index";
		}
		String memberId = ((LoginMember)session.getAttribute("loginMember")).getMemberId();
		Map<String, Object> map = cashService.getTotalDaySumAndTotalMonthSum(memberId, day);
		
		model.addAttribute("list", map.get("list"));
		model.addAttribute("totalMonthSum", map.get("totalMonthSum"));
		System.out.println(map.get("totalMonthSum") + "<--- totalMonthSum");
		return "compareToMonth";
	}
	// 가계부 생성
	@GetMapping("/addCashbook")
	public String addCashbook(Model model, HttpSession session) {
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/index";
		}
		
		String memberId = ((LoginMember)session.getAttribute("loginMember")).getMemberId();
		model.addAttribute("memberId", memberId);
		return "addCashbook";
	}
	
	@PostMapping("/addCashbook")
	public String addCashbook(HttpSession session, Cashbook cashbook) {
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/index";
		}
		int result = cashService.addCashbook(cashbook);
		System.out.println(result + "<-- 생성확인");
		return "redirect:/cashbookList?memberId=" + cashbook.getMemberId();
	}
	// 가계부 리스트
	@GetMapping("/cashbookList")
	public String cashbookList(Model model, HttpSession session, @RequestParam(value="currentPage", defaultValue="1" ) int currentPage) {
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/index";
		}
		int rowPerPage = 5;
		String memberId = ((LoginMember)session.getAttribute("loginMember")).getMemberId();
		int lastPage = cashService.getLastPage(memberId, rowPerPage); 
		List<Cashbook> list = cashService.getCashbookList(memberId, currentPage, rowPerPage);
		
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("cashbookList", list);
		model.addAttribute("lastPage", lastPage);
		return "cashbookList";
	}
	@GetMapping("/modifyCash")
	public String modifyCash(Model model, HttpSession session, @RequestParam(value="cashNo") int cashNo,
			@RequestParam(value="day", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate day) {
		
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/index";
		}
		List<Category> categoryList = cashService.getCategoryList();
		Cash cashOne = cashService.getCashOne(cashNo);
		
		model.addAttribute("cashNo", cashNo);
		model.addAttribute("cashOne", cashOne);
		model.addAttribute("day", day);
		model.addAttribute("categoryList", categoryList);
		return "modifyCash";
	}
	@PostMapping("/modifyCash")
	public String modifyCash(Model model, HttpSession session, Cash cash,
			@RequestParam(value="day", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate day) {
		cash.setCashDate(day);
		cashService.modifyCash(cash);
		return "redirect:/getCashListByDate?" + cash.getMemberId() + "&day=" + day;
	}
	
	@GetMapping("/getCashListByMonth")
	public String getCashListByMonth(Model model, HttpSession session, 
			@RequestParam(value="day", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate day) {
		
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/index";
		}
		
		Calendar calendarDay = Calendar.getInstance(); // Calendar 타입
		// System.out.println(calendarDay.get(Calendar.MONTH)+1);
		
		
		if(day == null) {
			day = LocalDate.now();	// LocalDate 타입	
		} else {
			/*
			 * LocalDate -> Date -> Calendar
			 * LocalDate -> String -> Calendar
			 * LocalDate -> Calendar
			 */
			calendarDay.set(day.getYear(), day.getMonthValue()-1, day.getDayOfMonth()); // 오늘 날짜에서 day값과 동일하게 변경
			
		}
		/*
		 * 0. 오늘 LocalDate 타입
		 * 1. 오늘 Calendar 타입
		 * 2. 이번 달의 마지막 일
		 * 3. 이번 달 1일의 요일
		 */
		// 일별 지출 총액
		String memberId = ((LoginMember)session.getAttribute("loginMember")).getMemberId();
		int year = calendarDay.get(Calendar.YEAR);
		int month = calendarDay.get(Calendar.MONTH)+1;
		System.out.println(year + "<--- year     "  + month + "<--- month");
		
		List<dayAndMonthAndYearAndPrice> dayAndPriceList = cashService.getCashAndPriceList(memberId, year, month);
		
		int monthSum = cashService.getCashMonthSum(memberId, year, month);
		System.out.println(monthSum + "<-- monthSum");
		
		
		model.addAttribute("day", day); 
		model.addAttribute("year", calendarDay.get(Calendar.YEAR));
		model.addAttribute("month", calendarDay.get(Calendar.MONTH)+1); // 현재 월
		model.addAttribute("lastDay", calendarDay.getActualMaximum(Calendar.DATE)); // 현재 월의 마지막 일
		model.addAttribute("dayAndPriceList", dayAndPriceList);
		model.addAttribute("monthSum", monthSum);
		
		
		System.out.println(dayAndPriceList);
		for(dayAndMonthAndYearAndPrice dp : dayAndPriceList) {
			System.out.println(dp);
		}
		
		Calendar firstDay = calendarDay;// 오늘 날짜를 구하기 위한 firstDay변수
		firstDay.set(Calendar.DATE, 1); // calendarDay DATE만 1로 변경
		System.out.println(firstDay.get(Calendar.YEAR)+","+(firstDay.get(Calendar.MONTH)+1) +"," + firstDay.get(Calendar.DATE));
		
		model.addAttribute("firstDayOfWeek", firstDay.get(Calendar.DAY_OF_WEEK));
		return "getCashListByMonth";
	}
	
	// 수입/지출 입력
	@GetMapping("/addCash")
	public String addCash(Model model, HttpSession session,
			@RequestParam(value="day", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate day) {
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/index";
		}
		List<Category> categoryList = cashService.getCategoryList();
		String memberId = ((LoginMember)(session.getAttribute("loginMember"))).getMemberId();
		System.out.println(memberId + "<--- addCash memberId");
		model.addAttribute("day", day);
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("memberId", memberId);
		return "addCash";
	}
	@PostMapping("/addCash")
	public String addCash(HttpSession session, Cash cash,
			@RequestParam(value="day", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate day) {
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/index";
		}
		System.out.println(day);
		System.out.println(cash.getMemberId());
		System.out.println(cash.getCategoryName());
		System.out.println(cash.getCashKind());
		System.out.println(cash.getCashPrice());
		System.out.println(cash.getCashPlace());
		System.out.println(cash.getCashMemo());
		System.out.println(cash.getCashDate());
		
		cash.setCashDate(day);
		cashService.addCash(cash);
		return "redirect:/getCashListByDate?" + cash.getMemberId() + "&day=" + day;
	}
	
	// 수입/지출 삭제
	@GetMapping("/removeCash")
	public String removeCash(HttpSession session, Cash cash,
			@RequestParam(value="day", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate day) {
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/index";
		}
		System.out.println(cash.getCashNo() + "<-- cashNo");
		System.out.println(cash.getMemberId() + "<-- cashMemberId");
		cashService.removeCash(cash.getCashNo());
		return "redirect:/getCashListByDate?"+ cash.getMemberId() + "&day=" + day;
	}
	@GetMapping("/getCashListByDate")
	public String getCashListByDate(Model model, HttpSession session, 
			@RequestParam(value="day", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate day,
			@RequestParam(value="year", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate year) {
			// RequestParam -> DateTimeFormat으로 자동 형변환
		
		System.out.println(year + " <-- year asdasdasdasd");
		if(day == null) {
			day = LocalDate.now();
		}
		if(year != null) {
			day= LocalDate.of(year.getYear(), day.getMonth(), day.getDayOfMonth());
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
		int sumCash = (int)list.get("sumCash");
		
		model.addAttribute("year", day.getYear());
		model.addAttribute("day", day);
		model.addAttribute("cashList", list.get("cashList"));
		model.addAttribute("sumCash", sumCash);
			
		return "getCashListByDate";
	}
}
