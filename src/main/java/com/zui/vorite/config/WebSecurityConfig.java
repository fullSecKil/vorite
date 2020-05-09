package com.zui.vorite.config;

import com.zui.vorite.details.CustomUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 *  认证配置
 * @author Dusk
 */

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/", "/login", "/register", "/global/**", "/test/**", "/visit/**")
                .permitAll()
                .antMatchers(HttpMethod.POST, "/registerPut", "/checkName")
                .permitAll()
                .antMatchers("/**")
                //leader, admin权限
                .hasAnyRole("LEADER", "SUPER_ADMIN")
                .anyRequest().authenticated().and()
                .formLogin()
                .usernameParameter("username").passwordParameter("password")
                .loginPage("/caricature/manage/login")
                .loginProcessingUrl("/login/form")
                .defaultSuccessUrl("/caricature/manage")
                .failureUrl("/caricature/manage/login?error")
                .permitAll()
                .and().logout()
                // 退出成功之后，浏览器需要重定向
                .logoutSuccessUrl("/caricature/manage/login?logout")
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .permitAll()        // 注销行为任意访问
                .and().rememberMe().tokenValiditySeconds(2419200)
                ;
    }

    @Autowired
    CustomUserService customUserService;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserService).passwordEncoder(passwordEncoder);
    }
}
