package ksmart41.mybatis.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import ksmart41.mybatis.interceptor.CommonInterceptor;
import ksmart41.mybatis.interceptor.LoginInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer{
	
	private final CommonInterceptor commonInterceptor;
	private final LoginInterceptor loginInterceptor;
	
	
	public WebConfig(CommonInterceptor commonInterceptor, LoginInterceptor loginInterceptor) {
		this.commonInterceptor = commonInterceptor;
		this.loginInterceptor = loginInterceptor;
	}
	
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		registry.addInterceptor(commonInterceptor)
				.addPathPatterns("/**")
				.excludePathPatterns("/css/**")
				.excludePathPatterns("/js/**")
				.excludePathPatterns("/favicon.ico");
		
		registry.addInterceptor(loginInterceptor)
				.addPathPatterns("/**")
				.excludePathPatterns("/css/**")
				.excludePathPatterns("/js/**")
				.excludePathPatterns("/favicon.ico")
				.excludePathPatterns("/member/addMember")
				.excludePathPatterns("/")
				
				//send redirect 에 /login이 들어있으니까 꼭 exclue /login 추가시켜줘야함 안그러면 무한 로그인 리다이렉트로 인해서 뻗어버림
				.excludePathPatterns("/login")
				.excludePathPatterns("/logout");
		
		
		WebMvcConfigurer.super.addInterceptors(registry);
	}
}
