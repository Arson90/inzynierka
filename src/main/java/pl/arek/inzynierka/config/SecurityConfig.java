package pl.arek.inzynierka.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import java.security.interfaces.RSAPublicKey;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${spring.security.oauth2.resourceserver.jwt.key-value}")
    RSAPublicKey key;
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/clients/**")
                                .permitAll()
                                .anyRequest()
                                .authenticated()
                                .and()
                                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                                .addFilter(new JWTAuthorizationFilter(authenticationManager()))
                                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    }

    @Bean
    JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(key).build();
    }
}
