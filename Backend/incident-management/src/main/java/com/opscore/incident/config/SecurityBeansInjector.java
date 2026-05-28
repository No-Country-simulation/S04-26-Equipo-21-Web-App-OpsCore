//package com.opscore.incident.config;
//
//import com.opscore.incident.exception.UsuarioNoEncontradoException;
//import com.opscore.incident.repository.UsuarioRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Configuration
//@RequiredArgsConstructor
//public class SecurityBeansInjector {
//
//    private final UsuarioRepository usuarioRepository;
//
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) {
//        return authenticationConfiguration.getAuthenticationManager();
//    }
//
//    @Bean
//    public AuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(userDetailsService());
//        authenticationProvider.setPasswordEncoder( passwordEncoder() );
//        return authenticationProvider;
//    }
//
//
//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        Map<String, PasswordEncoder> encoders = new HashMap<>();
//        encoders.put("bcrypt", new BCryptPasswordEncoder(12));
//        return new DelegatingPasswordEncoder("bcrypt", encoders);
//    }
//
//    public UserDetailsService userDetailsService() {
//        return (username -> usuarioRepository.findByUsername(username)
//                .orElseThrow(() -> new UsuarioNoEncontradoException("Usuario no encontrado con el username" + username)));
//    }
//}
//