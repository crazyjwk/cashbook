package com.gdu.cashbook.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdu.cashbook.mapper.CashMapper;
import com.gdu.cashbook.vo.Cash;
import com.gdu.cashbook.vo.Cashbook;
import com.gdu.cashbook.vo.Category;
import com.gdu.cashbook.vo.DayAndPrice;
import com.gdu.cashbook.vo.MonthAndPrice;

@Service
@Transactional
public class CashService {
	@Autowired
	private CashMapper cashMapper;
	
	public List<MonthAndPrice> getMonthSumList(String memberId, LocalDate day) {
		String day2 = day.toString();
		System.out.println(day2 +" <--asdasdasddddddd");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberId", memberId);
		map.put("day", day2);
		return cashMapper.selectMonthSumList(map);
	}
	
	public int addCashbook(Cashbook cashbook) {
		return cashMapper.insertCashbook(cashbook);
	}
	
	public List<Cashbook> getCashbookList(String memberId, int currentPage, int rowPerPage) {
		int beginRow = (currentPage - 1) * rowPerPage;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberId", memberId);
		map.put("rowPerPage", rowPerPage);
		map.put("beginRow", beginRow);
		return cashMapper.selectCashbookList(map);
	}
	public int getLastPage(String memberId, int rowPerPage) {
		int totalRow = cashMapper.cashbookTotalRow(memberId);
		int lastPage = totalRow / rowPerPage;
		if(totalRow % rowPerPage != 0) {
			lastPage+=1;
		}
		return lastPage;
	}
	
	public Integer getCashMonthSum(String memberId, int year, int month) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberId", memberId);
		map.put("year", year);
		map.put("month", month);
		Integer monthSum = cashMapper.selectCashMonthSum(map);
		if(monthSum == null) {
			monthSum = 0;
		}
		return monthSum;
	}
	public int modifyCash(Cash cash) {
		return cashMapper.updateCash(cash);
	}
	
	public Cash getCashOne(int cashNo) {
		return cashMapper.selectCashOne(cashNo);
	}
	
	public List<Category> getCategoryList() {
		return cashMapper.selectCategoryList();
	}
	public List<DayAndPrice> getCashAndPriceList(String memberId, int year, int month) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberId", memberId);
		map.put("year", year);
		map.put("month", month);
		return cashMapper.selectDayAndPrice(map);
	}
	
	public int addCash(Cash cash) {
		return cashMapper.insertCash(cash);
	}
	
	public void removeCash(int cashNo) {
		cashMapper.deleteCash(cashNo);
	}
	
	public Map<String, Object> getCashListByDate(Cash cash) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Cash> cashList = cashMapper.selectCashListByDate(cash);
		Integer sumCash = cashMapper.selectSumCashByDate(cash);
		if(sumCash == null) {
			sumCash = 0;
		}
		map.put("cashList", cashList);
		map.put("sumCash", sumCash);
		return map;
	}
}
