package edu.thejunglegiant.store.filter;

import edu.thejunglegiant.store.exceptions.JwtAuthenticationException;
import edu.thejunglegiant.store.security.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class JwtTokenFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String token = JwtTokenProvider.resolveToken((HttpServletRequest) servletRequest);
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;

        try {
            if (token != null && !getRequestedUrl(httpRequest).matches("\\/auth\\/.*") && jwtTokenProvider.validateToken(token)) {
                Authentication authentication = jwtTokenProvider.getAuthentication(token);
                if (authentication != null) {
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (JwtAuthenticationException e) {
            SecurityContextHolder.clearContext();
            ((HttpServletResponse) servletResponse).sendError(e.getHttpStatus().value());
            throw new JwtAuthenticationException("JWT token is expired or invalid");
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private String getRequestedUrl(HttpServletRequest httpRequest) {
        return httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
    }
}