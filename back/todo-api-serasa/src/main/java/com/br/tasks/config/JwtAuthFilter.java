package com.br.tasks.config;

import com.br.tasks.application.port.repository.AccountProfileRepository;
import com.br.tasks.application.service.AccountService;
import com.br.tasks.application.service.JwtService;
import com.br.tasks.domain.model.AccountProfileDO;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.security.auth.login.AccountNotFoundException;
import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    @Autowired
    JwtService jwtService;
    @Autowired
    AccountService accountService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);
        String email = jwtService.extractSubject(token);
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null && jwtService.validateToken(token)) {
            try {
                AccountProfileDO foundProfile = accountService.fetchAccountByEmail(email);
                // Authorities
                List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(foundProfile.getRoleEnum().name()));
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(foundProfile, null, authorities);

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authToken);
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Unauthorized: " + e.getMessage());
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}
