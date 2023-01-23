package com.fiteprojects.fitegis.Security;

import com.fiteprojects.fitegis.Models.User;
import com.fiteprojects.fitegis.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class TokenFilter extends OncePerRequestFilter {

    @Autowired
    TokenHandler tokenHandler;
    @Autowired
    UserService applicationUserService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        SecurityContextHolder.clearContext();
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            String username = "";
            try {
                username = tokenHandler.getUsername(token);
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    if (tokenHandler.isTokenExpired(token)) {
                        logger.error("Token is Expired");
                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token is Expired");
                        return;
                    }
                }
            } catch (Exception exception) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token is Expired");
                return;
            }
            try {
                User applicationUser = applicationUserService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                applicationUser, null, applicationUser.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            logger.info("No Token is Provided");
        }
        filterChain.doFilter(request, response);
    }
}
