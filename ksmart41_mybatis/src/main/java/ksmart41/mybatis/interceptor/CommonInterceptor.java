package ksmart41.mybatis.interceptor;

import java.util.Set;
import java.util.StringJoiner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class CommonInterceptor implements HandlerInterceptor{
	
	//logger + 자동완성
	private static final Logger log = LoggerFactory.getLogger(CommonInterceptor.class);

	
	/**
	 * 매개변수: HttpServletRequest, HttpServletResponse, Object handler  
	 * HandlerMapping이 핸들러 객체를 결정한 후 HandlerAdapter가 핸들러를 
	 * 호출하기 전에 호출되는 메소드
	 * return true(cotroller 진입), false(cotroller 진입 차단)
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
			
			
			Set<String> paramKeySet = request.getParameterMap().keySet();
			
			//memberId=id001, memberPw=pw001
			//memberId, memberPw
			StringJoiner param = new StringJoiner(", ");
			
			for(String key : paramKeySet) {
				param.add(key + "=" + request.getParameter(key));
			}
			
			
			request.getParameter("memberId");
			
			
			log.info("CommonInterceptor=============================start");
			log.info("Access Info		=============================start");
			log.info("PORT 			 :::: {}", request.getLocalPort());
			log.info("SERVERNAME 			 :::: {}", request.getServerName());
			log.info("HTTP METHOD 		 :::: {}", request.getMethod());
			log.info("URI 			 :::: {}", request.getRequestURI());
			log.info("PARAMETER 			 :::: {}", param);
			log.info("Access Info		=============================END");
			
		//return true;  로 줄여쓸수도있다
		return HandlerInterceptor.super.preHandle(request, response, handler);
	}

	/**
	 * 매개변수: HttpServletRequest, HttpServletResponse, Object handler, ModelAndView  
	 * HandlerAdapter가 실제로 핸들러를 호출한 후 DispatcherServlet이 뷰를 
	 * 렌더링하기 전에 호출되는 메소드 
	 * ModelAndView를 통해 뷰에 추가 모델 객체를 노출 (View 렌더링 전)  
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		log.info("CommonInterceptor=============================post handle");
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}
	
	
	/**
	 * 매개변수: HttpServletRequest, HttpServletResponse, Object handler
	 * DispatcherServlet이 뷰를 렌더링하기 후에 호출되는 메소드
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		log.info("CommonInterceptor=============================after handle");
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}
}
