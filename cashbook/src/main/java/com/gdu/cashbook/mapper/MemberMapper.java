package com.gdu.cashbook.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.cashbook.vo.LoginMember;
import com.gdu.cashbook.vo.Member;

@Mapper
public interface MemberMapper {
	public String updatePwCk(String memberPw);
	public int updateMemberInfo(Member member);
	public Member updateMemberOne(LoginMember loginMember);
	public void deleteMember(LoginMember loginMember);
	public Member selectMemberOne(LoginMember loginMember); // 회원정보 
	public String selectCheckMemberId(String memberIdCk); // 아이디 중복 체크
	public LoginMember selectLoginMember(LoginMember loginMember); // 로그인 아이디 비밀번호
	public void insertMember(Member member); // 회원가입
}