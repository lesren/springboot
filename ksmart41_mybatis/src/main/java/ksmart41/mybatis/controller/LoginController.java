package ksmart41.mybatis.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ksmart41.mybatis.dto.Member;
import ksmart41.mybatis.service.MemberService;

@Controller
public class LoginController {
	
	private final MemberService memberService;
	
	public LoginController(MemberService memberService) {
		this.memberService = memberService;
	}
	
	@GetMapping("/loginHistory3")
	public String loginHistory3(Model model) {
		
		model.addAttribute("title", "로그인 이력조회");
		
		return "login/loginHistory3";
	}
	
	@PostMapping("/loginHistory3")
	@ResponseBody
	public List<Map<String, Object>> loginHistory3() {
		List<Map<String, Object>> loginHistoryList = memberService.getLoginHistory3();
		
		return loginHistoryList;
	}
	
	@GetMapping("/loginHistory2")
	public String loginHistory2(@RequestParam(value="currentPage", required=false, defaultValue = "1") int currentPage
			,Model model) {
		
		//model currentPage, lastPage, loginHistoryList
		
		Map<String, Object> resultMap = memberService.getLoginHistory2(currentPage);
		
		model.addAttribute("currentPage",currentPage);
		model.addAttribute("lastPage", resultMap.get("lastPage"));
		model.addAttribute("loginHistoryList",resultMap.get("loginHistoryList"));
		model.addAttribute("startPageNum",resultMap.get("startPageNum"));
		model.addAttribute("endPageNum",resultMap.get("endPageNum"));
		
		return "login/loginHistory2";
	}
	
	@GetMapping("/loginHistory1")
	public String loginHistory1(@RequestParam(value="currentPage", required=false, defaultValue = "1") int currentPage
			,Model model) {
	
		//model currentPage, lastPage, loginHistoryList
		
		Map<String, Object> resultMap = memberService.getLoginHistory1(currentPage);
		
		model.addAttribute("currentPage",currentPage);
		model.addAttribute("lastPage", resultMap.get("lastPage"));
		model.addAttribute("loginHistoryList",resultMap.get("loginHistoryList"));
		
		
		return "login/loginHistory1";
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
