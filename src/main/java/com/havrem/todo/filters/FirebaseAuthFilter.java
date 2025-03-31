package com.havrem.todo.filters;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.havrem.todo.models.FirebaseUser;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class FirebaseAuthFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (request.getRequestURI().startsWith("/h2-console")) {
            filterChain.doFilter(request, response);
            return;
        }

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String idToken = authHeader.substring(7);

            try {
                FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);

                FirebaseUser user = new FirebaseUser(decodedToken.getUid());

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());

                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (FirebaseAuthException e) {
                throw new RuntimeException(e);
            }
        }

        filterChain.doFilter(request, response);
    }
}
