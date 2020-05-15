package com.gdu.cashbook.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdu.cashbook.mapper.MemberMapper;
import com.gdu.cashbook.mapper.MemberidMapper;
import com.gdu.cashbook.vo.LoginMember;
import com.gdu.cashbook.vo.Member;
import com.gdu.cashbook.vo.Memberid;

@Service
@Transactional
public class MemberService {
	@Autowired
	private MemberMapper memberMapper;
	@Autowired
	private MemberidMapper memberidMapper;
	@Autowired
	private JavaMailSender javaMailSender; //bean생성 -> @Component
	
	public int getMemberPw(Member member) { // id & pw만 들어있다.
		// pw도 추가해주자(UUID 라이브러리 사용)
		UUID uuid = UUID.randomUUID();
	
		String memberPw = uuid.toString().substring(0, 8);
		member.setMemberPw(memberPw);
		int row = memberMapper.updateMemberPw(member);
		
		if(row == 1) {
			// 성공하면 변경된 pw를 이메일로 보내준다.(메일객체) new JavaMailSender();
			System.out.println(memberPw + "<--- update memberPw");
			SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
			simpleMailMessage.setTo(member.getMemberEmail()); // 받는 주소
			simpleMailMessage.setFrom("imdoer0702@gmail.com"); // 보내는 곳
			simpleMailMessage.setSubject("cashbook 사이트 비밀번호 찾기 결과입니다."); // 메일 제목
			simpleMailMessage.setText("변경된 비밀번호 : " + memberPw + "  로그인 후에 꼭 비밀번호를 변경하세요."); // 메일 내용
			javaMailSender.send(simpleMailMessage);
		}
		return row;
	}
	
	public String getMemberIdByMember(Member member) {
		return memberMapper.selectMemberIdBymember(member);
	}
	public void removeMember(LoginMember loginMember) {
		// 1. @Transactional 이 여기에 있어도 괜찮다.
		Memberid memberid = new Memberid();
		memberid.setMemberId(loginMember.getMemberId());
		memberidMapper.insertMemberid(memberid);
		
		// 2.
		memberMapper.deleteMember(loginMember);
	}
	public String modifyPwCk(String memberPw) {
		return memberMapper.updatePwCk(memberPw);
	}
	
	public int modifyMemberInfo(Member member) {
		return memberMapper.updateMemberInfo(member);
	}
	
	public Member getModifyMemberOne(LoginMember loginMember) {
		return memberMapper.updateMemberOne(loginMember);
	}
	
	public Member getMemberOne(LoginMember loginMember) {
		return memberMapper.selectMemberOne(loginMember);
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
