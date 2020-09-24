package pl.arek.inzynierka.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import pl.arek.inzynierka.repository.UserRepository;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static pl.arek.inzynierka.config.JWTAuthenticationFilter.AUTHORIZATION;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    public static final String AUTHORITIES = "AUTHORITIES";
    RSAPublicKey publicKey;
    RSAPrivateKey privateKey;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, RSAPublicKey publicKey, RSAPrivateKey privateKey) {
        super(authenticationManager);
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(AUTHORIZATION);
        if(header==null){
            chain.doFilter(request,response);
            return;
        }

        DecodedJWT decodedJWT = JWT.require(Algorithm.RSA256(publicKey, privateKey))
                .build()
                .verify(header);

        if(decodedJWT!=null){
            List<SimpleGrantedAuthority> authorities = Arrays.stream(decodedJWT.getClaim(AUTHORITIES).asString().split(","))
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(decodedJWT.getSubject(), null, authorities);

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(request,response);
    }

}
