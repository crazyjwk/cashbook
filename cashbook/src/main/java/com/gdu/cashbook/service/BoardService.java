package com.gdu.cashbook.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdu.cashbook.mapper.BoardMapper;
import com.gdu.cashbook.vo.Board;

@Service
@Transactional
public class BoardService {
	@Autowired
	private BoardMapper boardMapper;
	
	public Map<String, Object> getBoardList(int currentPage) {
		int rowPerPage = 10;
		int beginRow = (currentPage - 1) * rowPerPage;
		int lastPage = getLastPage(rowPerPage);
		System.out.println(lastPage + "<-- boardService lastPage");
		List<Board> list = boardMapper.selectBoardList(beginRow, rowPerPage);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lastPage", lastPage);
		map.put("list", list);
		return map;
	}
	// LastPage 구하는 메서드
	public int getLastPage(int rowPerPage) {
		int totalRow = boardMapper.selectTotalRow();
		int lastPage = totalRow / rowPerPage;
		if(totalRow % rowPerPage != 0) {
			lastPage += 1;
		}
		return lastPage;
	}
}
