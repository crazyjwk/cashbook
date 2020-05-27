package com.gdu.cashbook.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.cashbook.vo.Board;
import com.gdu.cashbook.vo.Comment;

@Mapper
public interface BoardMapper {
	public int deletePost(Comment comment);
	public int deletePostComment(Comment comment);
	public int deleteComment(Comment comment);
	public int insertComment(Comment comment);
	public int updatePost(Board board);
	public Board selectPostOne(Board board);
	public List<Comment> selectComment(int boardNo, int beginRow, int rowPerPage);
	public Board selectDetailView(int boardNo);
	public int insertPost(Board board);
	public int selectTotalRow();
	public List<Board> selectBoardList(int beginRow, int rowPerPage);
}
