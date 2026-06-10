package com.fatec.glab.config.security;

import com.fatec.glab.model.User;
import com.fatec.glab.repository.UserRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AccessTokenFilter extends OncePerRequestFilter {

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String token = retrieveRequestToken(request);

        if (token != null) {

            try {

                FirebaseToken decodedToken =
                        FirebaseAuth.getInstance()
                                .verifyIdToken(token);

                String email = decodedToken.getEmail();

                User user = userRepository
                        .findByEmailIgnoreCaseAndActiveTrue(email)
                        .orElseGet(() -> {

                            User newUser = new User();

                            newUser.setName(decodedToken.getName());
                            newUser.setEmail(email);
                            newUser.setPassword(null);
                            newUser.setRole("USER");
                            newUser.setActive(true);

                            return userRepository.save(newUser);
                        });

                Authentication authentication =
                        new UsernamePasswordAuthenticationToken(
                                user,
                                null,
                                user.getAuthorities()
                        );

                SecurityContextHolder
                        .getContext()
                        .setAuthentication(authentication);

            } catch (FirebaseAuthException e) {

                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private String retrieveRequestToken(HttpServletRequest request) {

        String authorizationHeader =
                request.getHeader("Authorization");

        if (authorizationHeader != null &&
                authorizationHeader.startsWith("Bearer ")) {

            return authorizationHeader.replace("Bearer ", "");
        }

        return null;
    }
}