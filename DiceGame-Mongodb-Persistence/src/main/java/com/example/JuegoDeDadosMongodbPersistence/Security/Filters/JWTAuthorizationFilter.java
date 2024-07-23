package com.example.JuegoDeDadosMongodbPersistence.Security.Filters;

import com.example.JuegoDeDadosMongodbPersistence.Security.JWT.JWTUtils;
import com.example.JuegoDeDadosMongodbPersistence.Services.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTAuthorizationFilter extends OncePerRequestFilter {

     @Autowired
     private JWTUtils jwtUtils;

     @Autowired
     private UserDetailsServiceImpl userDetailsService;


    @Override
    protected void doFilterInternal(@NonNull  HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {


        String authHeader = request.getHeader("Authorization");
        System.out.println("Token:" + authHeader);


        if (authHeader != null && authHeader.startsWith("Bearer")) {

            String token = authHeader.substring(7);

            if (jwtUtils.isTokenValid(token)){
                String userEmail = jwtUtils.getUsername(token);
                UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);

                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }

        }

        filterChain.doFilter(request, response);





    }
}
