package com.gdu.cashbook.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.cashbook.vo.LoginMember;
import com.gdu.cashbook.vo.Member;

@Mapper
public interface MemberMapper {
	public int deleteMemberOut(String memberId);
	public List<Member> selectAdminMemberInfoList(int beginRow, int rowPerPage); // admin전용 회원정보 리스트
	public int selectAdminMemberInfoTotalRow(); // admin전용 회원정보 총 개수
	public String selectMemberPic(String memberId); // 이미지 삭제를 위해서 이미지 값을 받아 옴
	public int updateMemberPw(Member member); // 찾은 비밀번호 수정
	public Member selectMemberByIdAndEmail(Member member); // 비밀번호 찾기
	public String selectMemberIdBymember(Member member); // 아이디 찾기
	public String updatePwCk(String memberPw); // 미완성
	public int updateMemberInfo(Member member); // 회원정보 수정
	public Member updateMemberOne(LoginMember loginMember); // 특정 member의 db를 가지고 옴
	public void deleteMember(LoginMember loginMember); // 회원탈퇴
	public Member selectMemberOne(LoginMember loginMember); // 회원정보 
	public String selectCheckMemberId(String memberIdCk); // 아이디 중복 체크
	public LoginMember selectLoginMember(LoginMember loginMember); // 로그인 아이디 비밀번호
	public int insertMember(Member member); // 회원가입
}