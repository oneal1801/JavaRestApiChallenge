package com.api.usersChallenge.security;

import com.api.usersChallenge.helpers.TokenManager;
import com.api.usersChallenge.models.dto.SignInDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collections;

@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        SignInDto signInDto = new SignInDto();
        try {
            signInDto = new ObjectMapper().readValue(request.getReader(), SignInDto.class);
        } catch (IOException e) {
            log.error("Error trying to logIn");

        }

        UsernamePasswordAuthenticationToken usernamePAT = new UsernamePasswordAuthenticationToken(
                signInDto.getEmail(),
                signInDto.getPassword(),
                Collections.emptyList()

        );
        return super.getAuthenticationManager().authenticate(usernamePAT);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        UserDetailsImpl userDetails = (UserDetailsImpl) authResult.getPrincipal();
        String token = TokenManager.createToken(userDetails.getName(), userDetails.getUsername());
        response.addHeader("Authorization", "Bearer " + token);
        response.getWriter().flush();

        super.successfulAuthentication(request, response, chain, authResult);
    }
}
