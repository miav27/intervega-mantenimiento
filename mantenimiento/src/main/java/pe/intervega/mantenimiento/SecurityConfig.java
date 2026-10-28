package pe.intervega.mantenimiento;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// Configuración de seguridad: qué rutas son públicas y cómo se inicia sesión
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Define las reglas de acceso de la aplicación
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                // El login es público 
                // El diseño pueden cargarse sin iniciar sesión 
                .requestMatchers("/login", "/css/**", "/js/**", "/img/**").permitAll()
                // Todo lo demás exige haber iniciado sesión
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")                       // Usa nuestra pantalla de login
                .defaultSuccessUrl("/kilometraje", true)   // Destino tras entrar
                .permitAll()
            )
            .logout(logout -> logout.logoutSuccessUrl("/login?logout"));
        return http.build();
    }

    // Esto proporciona el codificador BCrypt para almacenar y
    // verificar las contraseñas mediante hashes.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}