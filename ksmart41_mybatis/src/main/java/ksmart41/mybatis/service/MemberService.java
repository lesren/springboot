package ksmart41.mybatis.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ksmart41.mybatis.dto.Member;
import ksmart41.mybatis.dto.MemberLevel;
import ksmart41.mybatis.mapper.MemberMapper;

@Service
@Transactional
public class MemberService {
	
	/**
	 * DI (의존성 주입) -> @autowired
	 * 1. 프로퍼티
	 * 2. 세터메서드
	 * 3. 생성자메서드
	 */
	
	/*
	//프로퍼티 DI 주입방식
	@Autowired 
	private MemberMapper memberMapper;
	
	//세터 DI 주입방식
	private MemberMapper memberMapper;
	
	@Autowired
	public void setMemberMapper(MemberMapper memberMapper) {
		this.memberMapper = memberMapper;
	}
	*/
	
	//생성자메소드 주입방식
	private final MemberMapper memberMapper;
	
	public MemberService(MemberMapper memberMapper) {
		this.memberMapper = memberMapper;
	}
	
	public List<Map<String, Object>> getLoginHistory3() {
		return memberMapper.getLoginHistoryList(null);
	}
	
	public Map<String, Object> getLoginHistory2(int currentPage) {
		
		// 보여줄 행의 갯수
		int rowPerPage = 5;
		
		// 로그인 이력 테이블 행의 갯수
		double rowCount = memberMapper.getLoginHistoryCount();
		
		// 마지막 페이지
		int lastPage = (int) Math.ceil((rowCount  / rowPerPage));
		
		// 페이지 구하기
		int startNum = 0;
		startNum = (currentPage - 1) * rowPerPage;
		
		// 보여줄 시작페이지 번호
		int startPageNum = 1;
		
		// 보여줄 마지막페이지 번호
		int endPageNum = 10;
		
		// 동적 페이지구성( 7페이지 부터 )
		if(currentPage >= 7) {
			startPageNum = currentPage - 5;
			endPageNum = currentPage + 4;
			if(endPageNum >= lastPage) {
				startPageNum = lastPage - 9;
				endPageNum = lastPage;
			}
		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("startNum", startNum);
		paramMap.put("rowPerPage", rowPerPage);
		
		List<Map <String, Object>> loginHistoryList = memberMapper.getLoginHistoryList(paramMap);
		
		paramMap.clear();
		
		paramMap.put("lastPage", lastPage);
		paramMap.put("loginHistoryList", loginHistoryList);
		paramMap.put("startPageNum", startPageNum);
		paramMap.put("endPageNum", endPageNum);
		
		return paramMap;
		
	}
	public Map<String, Object> getLoginHistory1(int currentPage) {
		
		// 보여줄 행의 갯수
		int rowPerPage = 10;
		
		// 로그인 이력 테이블 행의 갯수
		double rowCount = memberMapper.getLoginHistoryCount();
		
		// 마지막 페이지
		int lastPage = (int) Math.ceil((rowCount  / rowPerPage));
		
		// 페이지 구하기
		int startNum = 0;
		startNum = (currentPage - 1) * rowPerPage;
		
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("startNum", startNum);
		paramMap.put("rowPerPage", rowPerPage);
		
		List<Map <String, Object>> loginHistoryList = memberMapper.getLoginHistoryList(paramMap);
		
		paramMap.clear();
		paramMap.put("lastPage", lastPage);
		paramMap.put("loginHistoryList", loginHistoryList);
		
		return paramMap;
		
	}
	
	
	
	//회원정보 수정
	public int modifyMember(Member member) {
		return memberMapper.modifyMemberInfo(member);
	}
	
	//회원 정보 조회
	public Member getMemberInfoByMemberId(String memberId) {
		return memberMapper.getMemberInfoByMemberId(memberId);
	}
	
	//회원 여부 체크
	public int getMemberByMemberId(String memberId) {
		int result = 0;
		result += memberMapper.getMemberByMemberId(memberId);
		return result;
	}
	
	//회원 등록
	public int addMember(Member member) {

		int result = memberMapper.addMember(member);
		
		return result;
	}
	
	//전체 회원조회
	public List<Member> getMemberList(){
		List<Member> memberList = memberMapper.getMemberList();
		
		//memberList의 담겨져있는 Member 객체 별로 memberLevel를 확인한 다음 memberLevelName 값을 세팅
		if(memberList != null) {
			
			for(Member member : memberList) {
				String memberLevel = member.getMemberLevel();
	
				if(memberLevel != null) {
					if("1".equals(memberLevel)) {
						member.setMemberLevelName("관리자");
					}else if("2".equals(memberLevel)) {
						member.setMemberLevelName("판매자");
					}else if("3".equals(memberLevel)) {
						member.setMemberLevelName("구매자");
					}else {
						member.setMemberLevelName("회원");						
					}
				}
			}
			/*
			int memberListSize = memberList.size();
			
			for(int i=0; i<memberListSize; i++) {
				String memberLevel = memberList.get(i).getMemberLevel();
				
				if(memberLevel != null) {
					if("1".equals(memberLevel)) {
						memberList.get(i).setMemberLevelName("관리자");
					}else if("2".equals(memberLevel)) {
						memberList.get(i).setMemberLevelName("판매자");
					}else if("3".equals(memberLevel)) {
						memberList.get(i).setMemberLevelName("구매자");
					}else {
						memberList.get(i).setMemberLevelName("회원");						
					}
				}
			}
			*/
		}
		
		
		System.out.println("MemberService : " + memberList);
		return memberList;
		//return memberMapper.getMemberList();
	}

	//회원목록 조회 (검색)
	public List<Member> getMemberListBySearchKey(String searchKey, String searchValue) {
		List<Member> memberList = memberMapper.getMemberListBySearchKey(searchKey, searchValue);
		
		if(memberList != null) {
			
			for(Member member : memberList) {
				String memberLevel = member.getMemberLevel();
	
				if(memberLevel != null) {
					if("1".equals(memberLevel)) {
						member.setMemberLevelName("관리자");
					}else if("2".equals(memberLevel)) {
						member.setMemberLevelName("판매자");
					}else if("3".equals(memberLevel)) {
						member.setMemberLevelName("구매자");
					}else {
						member.setMemberLevelName("회원");						
					}
				}
			}
		}
		
		return memberList;
	}
	
	
	public List<MemberLevel> getMemberLevelList() {
		
		List<MemberLevel> memberLevelList = memberMapper.getMemberLevelList();
		
		return memberLevelList;
	}

	public String removeMember(String memberId, String memberPw) {
		
		String result = "회원탈퇴 실패";
		
		Member member = memberMapper.getMemberInfoByMemberId(memberId);
		
		if(member != null) {
			String memberLevel = member.getMemberLevel();
			String chkMemberPw = member.getMemberPw();
			if(memberPw.equals(chkMemberPw)) {
				//회원레벨에 따라서 삭제 처리
				//판매자일 경우
				if("2".equals(memberLevel)) {
					memberMapper.removeOrderBySellerId(memberId);
					memberMapper.removeGoodsBySellerId(memberId);
				}
				
				if("3".equals(memberLevel)) {
					memberMapper.removeOrderByOrderId(memberId);
				}
				
				memberMapper.removeLoginHistory(memberId);
				memberMapper.removeMemberByMemberId(memberId);
				
				result = "회원탈퇴 성공";
				
			}
		}
		
		return result;
	}
	
	
	
}
