package ksmart41.mybatis.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ksmart41.mybatis.dto.Member;
import ksmart41.mybatis.service.MemberService;

@Controller
public class LoginController {
	
	private MemberService memberService;
	
	public LoginController(MemberService memberService) {
		this.memberService = memberService;
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		
		session.invalidate();
		
		return "redirect:/login";
	}
	
	@PostMapping("/login")
	public String login(@RequestParam(value="memberId", required = false) String memberId
			           ,@RequestParam(value="memberPw", required = false) String memberPw
					   ,HttpSession session) {
		
		if( 	memberId != null && !"".equals(memberId)
			&&	memberPw != null && !"".equals(memberPw) ) {
			
			Member member = memberService.getMemberInfoByMemberId(memberId);
			
			if(    member != null && member.getMemberPw() != null 
				&& memberPw.equals(member.getMemberPw())) {
				
				//로그인 비밀번호 일치시
				session.setAttribute("SID", memberId);
				session.setAttribute("SNAME", member.getMemberName());
				session.setAttribute("SLEVEL", member.getMemberLevel());
				
				return "redirect:/";
			}
		}
		//로그인 비밀번호 불일치
		return "redirect:/login";
	}

	@GetMapping("/login")
	public String login(Model model) {
		model.addAttribute("title", "로그인화면");
		return "login/login";
	}
	
	
}
