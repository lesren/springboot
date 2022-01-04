package ksmart41.mybatis.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginInterceptor implements HandlerInterceptor{
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		HttpSession session = request.getSession();
		String requestUri = request.getRequestURI();
		
		String sessionId = (String) session.getAttribute("SID");
		String sessionLevel = (String) session.getAttribute("SLEVEL");
		
		if(sessionId == null) {
			response.sendRedirect("/login");
			
			//무조건 false해서 컨트롤러로 진입을 막아야한다.
			return false;
		} else {
			requestUri = requestUri.trim();
			
			
			//판매자인경우
			if("2".equals(sessionLevel)) {
				if(requestUri.indexOf("memberList") > -1) {
					response.sendRedirect("/member/memberList");
				}
			}
				
			//구매자인경우
		}
		
		return true;
	}

}
