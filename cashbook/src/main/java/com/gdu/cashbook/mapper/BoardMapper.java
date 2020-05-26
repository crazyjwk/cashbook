package com.gdu.cashbook.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.cashbook.vo.Board;

@Mapper
public interface BoardMapper {
	public int selectTotalRow();
	public List<Board> selectBoardList(int beginRow, int rowPerPage);
}
