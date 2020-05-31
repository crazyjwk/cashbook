package com.gdu.cashbook.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdu.cashbook.mapper.BoardMapper;
import com.gdu.cashbook.vo.Board;
import com.gdu.cashbook.vo.Comment;

@Service
@Transactional
public class BoardService {
	@Autowired
	private BoardMapper boardMapper;
	
	public int removePost(Comment comment) {
		boardMapper.deletePostComment(comment);
		return boardMapper.deletePost(comment);
	}
	
	public int removeComment(Comment comment) {
		return boardMapper.deleteComment(comment);
	}
	
	public int addComment(Comment comment) {
		return boardMapper.insertComment(comment);
	}
	
	public int modifyPost(Board board) {
		return boardMapper.updatePost(board);
	}
	
	public Board getPostOne(Board board) {
		return boardMapper.selectPostOne(board);
	}
	
	public Map<String, Object> getDetailView(int boardNo, int currentPage) {
		int rowPerPage = 5;
		int beginRow = (currentPage - 1) * rowPerPage;
		int totalRow = boardMapper.selectCommentTotalRowByPost(boardNo);
		int lastPage = totalRow / rowPerPage;
		if(totalRow % rowPerPage != 0) {
			lastPage += 1;
		}
		
		List<Comment> commentList = boardMapper.selectComment(boardNo, beginRow, rowPerPage);
		Board boardOne = boardMapper.selectDetailView(boardNo);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lastPage", lastPage);
		map.put("boardOne", boardOne);
		map.put("commentList", commentList);
		
		return map; 
	}
	
	public int addPost(Board board) {
	
		int row = boardMapper.insertPost(board);

		return row;
	}
	
	public Map<String, Object> getBoardList(int currentPage) {
		int rowPerPage = 10;
		int beginRow = (currentPage - 1) * rowPerPage;
		int totalRow = boardMapper.selectPostTotalRow();
		int lastPage = totalRow / rowPerPage;
		if(totalRow % rowPerPage != 0) {
			lastPage += 1;
		}
		
		System.out.println(lastPage + "<-- boardService lastPage");
		List<Board> list = boardMapper.selectBoardList(beginRow, rowPerPage);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lastPage", lastPage);
		map.put("list", list);
		return map;
	}
}
