package pl.arek.inzynierka.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("classpath:public.pem")
    RSAPublicKey publicKey;
    @Value("${spring.security.oauth2.resourceserver.jwt.privatekey-value}")
    RSAPrivateKey privateKey;

    private UserDetailsServiceImpl userDetailsService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public SecurityConfig(UserDetailsServiceImpl userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests()
//                                .antMatchers("/users/**").hasRole("ADMINISTRATOR")
                                .antMatchers("/login").permitAll()
                                .antMatchers("/users/**").hasRole("ADMINISTRATOR")
                                .antMatchers("/roles/**").hasRole("ADMINISTRATOR")
                                .antMatchers(HttpMethod.GET,"/clients/{id}").access("@userSecurity.hasClientOwnID(authentication,#id) or hasAnyRole('ADMINISTRATOR', 'EMPLOYEE')")
                                .antMatchers(HttpMethod.POST,"/clients/{id}/addresses").access("@userSecurity.hasClientOwnID(authentication,#id) or hasAnyRole('ADMINISTRATOR', 'EMPLOYEE')")
                                .antMatchers("/clients/**").hasAnyRole("ADMINISTRATOR", "EMPLOYEE")
                                .antMatchers(HttpMethod.POST,"/users/**").hasRole("EMPLOYEE")
                                .antMatchers(HttpMethod.GET,"/users/**").hasRole("EMPLOYEE")
                                .anyRequest()
                                .authenticated()
                                .and()
                                .addFilter(new JWTAuthenticationFilter(authenticationManager(),publicKey,privateKey,userDetailsService))
                                .addFilter(new JWTAuthorizationFilter(authenticationManager(),publicKey,privateKey))
                                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }
}
