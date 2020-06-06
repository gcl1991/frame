package tacos.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web
        .configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web
        .configuration.WebSecurityConfigurerAdapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation
        .authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web
        .builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests() // 授权请求(允许根据HttpServletRequest使用限制访问)
                .antMatchers("/design", "/orders").access("hasRole('ROLE_USER')") // 访问
                .antMatchers("/", "/**").access("permitAll") // 允许全部 /**匹配路径中的零个或多个目录
                .and()
                .formLogin() // 配置自定义登录表单
                .loginPage("/login") // 当断定用户没有认证并且需要登录的时候，它就会将用户重定向到该路径。
                .and() // 添加配置
                .logout() // 搭建安全过滤器，拦截对/logout的请求
                .logoutSuccessUrl("/") // 用户退出后session会被清除，同时默认被重定向到登录页面，此处自定义未根路径
                .and()
                .csrf() // 添加了CSRF支持
                .ignoringAntMatchers("/h2-console/**") // 指定HttpServletRequest不使用CSRF保护
                .and()
                .headers() // 将Security标头添加到响应中
                .frameOptions() // 允许自定义XFrameOptionsHeaderWriter。X-Frame-Options是一个HTTP标头（header用来告诉浏览器这个网页是否可以放在iFrame内
                .sameOrigin() //表示该页面可以在相同域名页面的frame中展示,告诉浏览器只有当架设iFrame的网站与发出X-Frame-Options的网站相同，才能显示发出X-Frame-Options网页的内容。
        ;
    }

    @Bean
    public PasswordEncoder encoder() {
        return new StandardPasswordEncoder("53cr3t");
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
    }

    //  @Override
//  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//    auth.userDetailsService(userDetailsService);
//  }

// IN MEMORY AUTHENTICATION EXAMPLE
//  @Override
//  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//    auth
//      .inMemoryAuthentication()
//        .withUser("buzz")
//          .password("infinity")
//          .authorities("ROLE_USER")
//        .and()
//        .withUser("woody")
//          .password("bullseye")
//          .authorities("ROLE_USER");
//
//  }

// JDBC Authentication example
/*
  @Autowired
  DataSource dataSource;
  
  @Override
  protected void configure(AuthenticationManagerBuilder auth)
      throws Exception {
    
    auth
      .jdbcAuthentication()
        .dataSource(dataSource);
    
  }
*/

/*
  @Override
  protected void configure(AuthenticationManagerBuilder auth)
      throws Exception {
    
    auth
      .jdbcAuthentication()
        .dataSource(dataSource)
        .usersByUsernameQuery(
            "select username, password, enabled from Users " +
            "where username=?")
        .authoritiesByUsernameQuery(
            "select username, authority from UserAuthorities " +
            "where username=?");
    
  }
*/

/*
  @Override
  protected void configure(AuthenticationManagerBuilder auth)
      throws Exception {
    
    auth
      .jdbcAuthentication()
        .dataSource(dataSource)
        .usersByUsernameQuery(
            "select username, password, enabled from Users " +
            "where username=?")
        .authoritiesByUsernameQuery(
            "select username, authority from UserAuthorities " +
            "where username=?")
        .passwordEncoder(new StandardPasswordEncoder("53cr3t");
    
  }
*/


// LDAP Authentication example
/*
  @Override
  protected void configure(AuthenticationManagerBuilder auth)
      throws Exception {
    auth
      .ldapAuthentication()
        .userSearchFilter("(uid={0})")
        .groupSearchFilter("member={0}");
  }
*/
}
