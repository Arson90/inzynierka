package pl.arek.inzynierka.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;

import static pl.arek.inzynierka.config.JWTAuthenticationFilter.AUTHORIZATION;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    RSAPublicKey publicKey;
    RSAPrivateKey privateKey;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, RSAPublicKey publicKey, RSAPrivateKey privateKey) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(AUTHORIZATION);
        if(header==null){
            chain.doFilter(request,response);
            return;
        }

        String userName = JWT.require(Algorithm.RSA256(publicKey, privateKey))
                .build()
                .verify(header)
                .getSubject();

        if(userName!=null){
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userName, null, new ArrayList<>());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(request,response);
    }

}
