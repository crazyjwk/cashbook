package com.gdu.cashbook.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdu.cashbook.mapper.CashMapper;
import com.gdu.cashbook.vo.Cash;
import com.gdu.cashbook.vo.Category;
import com.gdu.cashbook.vo.DayAndPrice;

@Service
@Transactional
public class CashService {
	@Autowired
	private CashMapper cashMapper;
	
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
		map.put("cashList", cashList);
		map.put("sumCash", sumCash);
		return map;
	}
}
