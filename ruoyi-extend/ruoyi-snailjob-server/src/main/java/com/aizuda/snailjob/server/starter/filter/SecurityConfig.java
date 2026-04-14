package com.aizuda.snailjob.server.starter.filter;

import org.noear.solon.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.noear.solon.annotation.Bean;
import org.noear.solon.annotation.Configuration;

/**
 * 权限安全配置
 *
 * @author Lion Li
 */
@Configuration
public class SecurityConfig {

    @Value("${spring.boot.admin.client.username}")
    private String username;
    @Value("${spring.boot.admin.client.password}")
    private String password;

    @Bean
    public FilterRegistrationBean<ActuatorAuthFilter> actuatorFilterRegistrationBean() {
        FilterRegistrationBean<ActuatorAuthFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new ActuatorAuthFilter(username, password));
        registrationBean.addUrlPatterns("/actuator", "/actuator/*");
        return registrationBean;
    }

}
