package com.annotation.shiro;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShiroConfig {
	@Bean
	public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		// 必须设置 SecurityManager
		shiroFilterFactoryBean.setSecurityManager(securityManager);

		// 设置拦截器
		Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
		// 登陆接口，开发权限
		filterChainDefinitionMap.put("/api/login", "anon");
		// 注册接口，开发权限
		filterChainDefinitionMap.put("/api/register", "anon");
		// 用户，需要角色权限 “user”
		filterChainDefinitionMap.put("/user/**", "roles[user]");
		// 管理员，需要角色权限 “admin”
		filterChainDefinitionMap.put("/admin/**", "roles[admin]");
		// 其余接口一律拦截
		// 主要这行代码必须放在所有权限设置的最后，不然会导致所有 url 都被拦截
		filterChainDefinitionMap.put("/**", "authc");

		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		Map<String, Filter> filterMap = shiroFilterFactoryBean.getFilters();
		filterMap.put("authc", new MyAuthenticationFilter());

		System.out.println("Shiro拦截器工厂类注入成功");
		return shiroFilterFactoryBean;
	}

	/**
	 * 注入 securityManager
	 */
	@Bean
	public SecurityManager securityManager() {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		// 设置realm.
		securityManager.setRealm(customRealm());
		return securityManager;
	}

	/**
	 * 自定义身份认证 realm;
	 * <p>
	 * 必须写这个类，并加上 @Bean 注解，目的是注入 CustomRealm， 否则会影响 CustomRealm类 中其他类的依赖注入
	 */
	@Bean
	public CustomRealm customRealm() {
		return new CustomRealm();
	}
}
