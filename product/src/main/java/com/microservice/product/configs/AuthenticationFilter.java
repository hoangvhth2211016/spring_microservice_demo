package com.microservice.product.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservice.product.models.users.UserInformation;
import com.microservice.product.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //String authHeader = request.getHeader("Authorization");
        //try{
        //
        //    if(authHeader != null){
        //        String[] headerElements = authHeader.split(" ");
        //
        //        if(headerElements.length == 2 && "Bearer".equals(headerElements[0])){
        //            String token = headerElements[1];
        //
        //            UserInformation userInfo = jwtUtil.extractUserInformation(token);
        //
        //            if(userInfo.getUsername() != null && userInfo.getId() != null && !userInfo.getRoles().isEmpty() && SecurityContextHolder.getContext().getAuthentication() == null){
        //                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
        //                        userInfo, null, userInfo.getRoles().stream().map(r->new SimpleGrantedAuthority("ROLE_"+r)).toList());
        //                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        //                SecurityContextHolder.getContext().setAuthentication(authToken);
        //            }
        //        }
        //    }
        //    filterChain.doFilter(request, response);
        //}catch (Exception e){
        //    resolver.resolveException(request, response, null, e);
        //}
        
        String userInfoJson = request.getHeader("X-User-Info");
        if(userInfoJson !=null){
            try{
                UserInformation userInfo = objectMapper.readValue(userInfoJson, UserInformation.class);
                
                String authHeader = request.getHeader("Authorization");
                String[] headerElements = authHeader.split(" ");
                String token = headerElements[1];
                
                if(userInfo != null && SecurityContextHolder.getContext().getAuthentication() == null){
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userInfo,
                            token,
                            userInfo.getRoles().stream().map(r->new SimpleGrantedAuthority("ROLE_"+r)).toList()
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }else{
                    throw new AccessDeniedException("invalid user information");
                }
            }catch (Exception e){
                resolver.resolveException(request, response, null, e);
            }
        }
        filterChain.doFilter(request, response);
    }
}