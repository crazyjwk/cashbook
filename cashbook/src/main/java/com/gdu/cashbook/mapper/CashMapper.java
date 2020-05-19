package com.gdu.cashbook.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.cashbook.vo.Cash;

@Mapper
public interface CashMapper {
	public int insertCash(Cash cash);
	public void deleteCash(int cashNo);
	public Integer selectSumCashByDate(Cash cash);
	public List<Cash> selectCashListByDate(Cash cash);
}
