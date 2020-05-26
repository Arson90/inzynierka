package pl.arek.inzynierka.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import pl.arek.inzynierka.data.UserInternal;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public static final String AUTHORIZATION = "Authorization";
    private AuthenticationManager authenticationManager;
    public static final long EXPIRATION_TIME = 864_000_000; // 10 days

    RSAPublicKey publicKey;
    RSAPrivateKey privateKey;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, RSAPublicKey publicKey, RSAPrivateKey privateKey){
        this.authenticationManager = authenticationManager;
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            UserInternal creds = new ObjectMapper()
                    .readValue(request.getInputStream(), UserInternal.class);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getUserName(),
                            creds.getPassword(),
                            new ArrayList<>())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        Date expiresDate = Date.from(Instant.now().plus(360L, ChronoUnit.DAYS));
        String token = JWT.create()
                .withSubject(((User) authResult.getPrincipal()).getUsername())
                .withExpiresAt(expiresDate)
                .sign(Algorithm.RSA256(publicKey, privateKey));
        response.addHeader(AUTHORIZATION, token);
    }
}
