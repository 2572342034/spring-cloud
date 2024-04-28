//package com.store.commen.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Lazy;
//import org.springframework.context.annotation.Primary;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.AuthenticationEntryPoint;
//import org.springframework.security.web.access.AccessDeniedHandler;
//
//import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
//import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
//
//import org.springframework.web.servlet.DispatcherServlet;
//
//@EnableWebSecurity
//@Configuration
//@EnableGlobalMethodSecurity(prePostEnabled = true )
//@ConditionalOnClass(DispatcherServlet.class)
//public class SecurityConfig extends WebSecurityConfigurerAdapter   {
//
//    @Autowired
//    private AuthenticationEntryPoint authenticationEntryPoint;
//
//    @Autowired
//    private AccessDeniedHandler accessDeniedHandler;
//    @Autowired
//    private LogoutSuccessHandler logoutSuccessHandler;
//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
////                .formLogin()
////                .loginProcessingUrl("https//:localhost:8091/user/login")
////                .permitAll()
////                .and()
//                .requestCache()
//                .requestCache(new HttpSessionRequestCache())
//                .and()
//                .authorizeRequests()
//                //对登录的接口 允许匿名访问，就是登不登陆都可以访问
//                .antMatchers("/user/login","/user/hello")
//                .permitAll()
//                .antMatchers("/captcha","/verify-captcha")
//                .permitAll()
//                .antMatchers("/user/register")
//                .permitAll()
//                .anyRequest().authenticated();
//
//
//        //把token效验过滤器添加到过滤器链中
//
//        //加入自定义异常
//        http.exceptionHandling()
//                .authenticationEntryPoint(authenticationEntryPoint).
//                accessDeniedHandler(accessDeniedHandler);
//        http.logout()
//                //配置注销成功处理器
//                .logoutSuccessHandler(logoutSuccessHandler);
//
//        //开启跨越
//        http.cors();
//    }
//
//
//
//
//    @Bean
//    @Override
//    @Primary
//    @Lazy
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }
//
//}
