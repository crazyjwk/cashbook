package com.gdu.cashbook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdu.cashbook.mapper.MemberMapper;
import com.gdu.cashbook.vo.LoginMember;
import com.gdu.cashbook.vo.Member;

@Service
@Transactional
public class MemberService {
	@Autowired
	private MemberMapper memberMapper;
	
	public Member getMemberOne(LoginMember loginMember) {
		return memberMapper.selectMemberOnd(loginMember);
	}
	
	public String checkmemberId(String memberIdCk) {
		return memberMapper.selectCheckMemberId(memberIdCk);
	}
	public LoginMember login(LoginMember loginMember) {
		return memberMapper.selectLoginMember(loginMember);
	}
	public void addMember(Member member) {
		memberMapper.insertMember(member);
	}
}
