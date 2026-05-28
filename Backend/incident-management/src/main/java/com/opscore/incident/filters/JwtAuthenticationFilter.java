//package com.opscore.incident.filters;
//
//import com.opscore.incident.model.Usuario;
//import com.opscore.incident.service.JwtService;
//import com.opscore.incident.service.UsuarioService;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.Cookie;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import org.jspecify.annotations.NonNull;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//
//@Component
//@RequiredArgsConstructor
//public class JwtAuthenticationFilter extends OncePerRequestFilter {
//
//    private final JwtService jwtService;
//    private final UsuarioService usuarioService;
//
//    @Override
//    protected void doFilterInternal(
//            @NonNull HttpServletRequest request,
//            @NonNull HttpServletResponse response,
//            @NonNull FilterChain filterChain) throws ServletException, IOException {
//        String token = extractAccessTokenFromCookies(request);
//
//        if (token == null) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        try {
//            if (jwtService.isTokenValid(token) || !jwtService.isAccessToken(token)) {
//                filterChain.doFilter(request, response);
//                return;
//            }
//
//            String username = jwtService.extractUsername(token);
//
//            Usuario usuario = usuarioService.findByUsername(username);
//
//            if (usuario == null || !usuario.isEnabled()) {
//                filterChain.doFilter(request, response);
//                return;
//            }
//
//            UsernamePasswordAuthenticationToken auth =
//                    new UsernamePasswordAuthenticationToken(
//                            usuario,
//                            null,
//                            usuario.getAuthorities()
//                    );
//
//            SecurityContextHolder.getContext().setAuthentication(auth);
//
//        } catch (Exception e) {
//            SecurityContextHolder.clearContext();
//        }
//
//        filterChain.doFilter(request, response);
//    }
//
//    private String extractAccessTokenFromCookies(HttpServletRequest request) {
//
//        if (request.getCookies() == null) {
//            return null;
//        }
//
//        for (Cookie cookie : request.getCookies()) {
//            if ("access_token".equals(cookie.getName())) {
//                return cookie.getValue();
//            }
//        }
//
//        return null;
//    }
//}
//