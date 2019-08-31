package cn.tedu.store.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import cn.tedu.store.interceptor.LoginInterceptor;

/**
 * 拦截器的配置类
 */
@Configuration
public class LoginInterceptorConfigurer 
	implements WebMvcConfigurer {
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		HandlerInterceptor interceptor
			= new LoginInterceptor();
		
		List<String> paths = new ArrayList<>();
		paths.add("/js/**");
		paths.add("/css/**");
		paths.add("/images/**");
		paths.add("/bootstrap3/**");
		
		paths.add("/web/register.html");
		paths.add("/web/login.html");
		paths.add("/web/index.html");
		paths.add("/web/product.html");
		
		paths.add("/users/reg");
		paths.add("/users/login");
		paths.add("/districts/**");
		paths.add("/products/**");
		
		registry.addInterceptor(interceptor)
			.addPathPatterns("/**")
			.excludePathPatterns(paths);
	}

}







