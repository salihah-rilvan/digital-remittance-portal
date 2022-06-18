package com.example.springboot.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private DataSource dataSource;

    private final String USERS_QUERY = "select email, password, active from sender where email=?";
    private final String ROLES_QUERY = "select u.email, ur.role from sender u inner join sender_role ur on (u.id = ur.user_id) where u.email=?";    

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
          
        // JDBC authentication
        auth.jdbcAuthentication()
            .usersByUsernameQuery(USERS_QUERY)
            .authoritiesByUsernameQuery(ROLES_QUERY)
            .dataSource(dataSource)
            .passwordEncoder(bCryptPasswordEncoder);

        // in-memory authentication
        auth.userDetailsService(createUser());
            
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        // authorize requests for sender and admin
        http.authorizeRequests()
            .antMatchers("/admin/**").hasRole("ADMIN")
            .antMatchers("/admin**").hasRole("ADMIN")
            .antMatchers("/").permitAll()
            .antMatchers("/login").permitAll()
            .antMatchers("/signup").permitAll()
            .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**","/vendor/**","/fonts/**").permitAll()
            .anyRequest().authenticated().and().csrf().disable()
            .formLogin().loginPage("/login").failureUrl("/login?error=true")
            .defaultSuccessUrl("/home", true)
            .usernameParameter("email")
            .passwordParameter("password")
            .and().logout()
            .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
            .logoutSuccessUrl("/login");
    }

    // Create admin user when app start
    @Bean
    public InMemoryUserDetailsManager createUser() {
        var m = new InMemoryUserDetailsManager();
        m.createUser(User.withUsername("admin@smu.com").password(bCryptPasswordEncoder.encode("123")).roles("ADMIN").build());;
        return m;
    }

}