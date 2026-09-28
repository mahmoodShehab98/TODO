package com.ga.todo.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {


    private static final Logger logger =
            LoggerFactory.getLogger(JwtRequestFilter.class);

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        System.out.println();
        System.out.println("========== JWT FILTER ==========");
        System.out.println(
                "REQUEST: "
                        + request.getMethod()
                        + " "
                        + request.getRequestURI()
        );

        try {

            // 1. Get Authorization header
            String authHeader =
                    request.getHeader("Authorization");

            System.out.println(
                    "AUTHORIZATION HEADER ==> "
                            + authHeader
            );

            // 2. Extract JWT
            String jwt = parseJwt(request);

            System.out.println(
                    "JWT ==> "
                            + jwt
            );

            // 3. Check JWT
            if (jwt != null) {

                System.out.println("JWT RECEIVED");

                // 4. Validate JWT
                boolean valid =
                        jwtUtils.validateJwtToken(jwt);

                System.out.println(
                        "JWT VALID ==> "
                                + valid
                );

                if (valid) {

                    // 5. Get username from JWT
                    String username =
                            jwtUtils.getUserNameFromJwtToken(jwt);

                    System.out.println(
                            "USERNAME ==> "
                                    + username
                    );

                    // 6. Load user
                    UserDetails userDetails =
                            myUserDetailsService
                                    .loadUserByUsername(username);

                    System.out.println(
                            "USER ==> "
                                    + userDetails.getUsername()
                    );

                    System.out.println(
                            "AUTHORITIES ==> "
                                    + userDetails.getAuthorities()
                    );

                    // 7. Create authentication
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );

                    // 8. Add request details
                    authentication.setDetails(
                            new WebAuthenticationDetailsSource()
                                    .buildDetails(request)
                    );

                    // 9. Set authentication
                    SecurityContextHolder
                            .getContext()
                            .setAuthentication(authentication);

                    System.out.println(
                            "AUTHENTICATION SET ==> "
                                    + SecurityContextHolder
                                    .getContext()
                                    .getAuthentication()
                    );
                }
            }

        } catch (Exception e) {

            logger.error(
                    "Cannot set user authentication",
                    e
            );

            e.printStackTrace();
        }

        System.out.println(
                "AUTH BEFORE FILTER CHAIN ==> "
                        + SecurityContextHolder
                        .getContext()
                        .getAuthentication()
        );

        System.out.println(
                "========== END JWT FILTER =========="
        );
        System.out.println();

        // Continue to Spring Security
        filterChain.doFilter(request, response);
    }

    private String parseJwt(
            HttpServletRequest request) {

        String headerAuth =
                request.getHeader("Authorization");

        if (headerAuth != null
                && headerAuth.startsWith("Bearer ")) {

            return headerAuth.substring(7);
        }

        return null;
    }
}