package pe.intervega.mantenimiento.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pe.intervega.mantenimiento.dao.UsuarioDAO;
import pe.intervega.mantenimiento.model.Usuario;

// Lógica de usuarios: Spring Security la usa para autenticar contra MySQL
@Service
public class UsuarioService implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(UsuarioService.class);

    private final UsuarioDAO usuarioDAO;

    // Depende de la interfaz, no de la implementación (inversión de dependencias)
    public UsuarioService(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Usuario u = usuarioDAO.buscarPorUsername(username)
            .orElseThrow(() -> {
                log.warn("Intento de inicio de sesión con usuario inexistente: {}", username);
                return new UsernameNotFoundException("Usuario no encontrado");
            });

        log.info("Usuario cargado para autenticación: {}", u.getUsername());

        return User.withUsername(u.getUsername())
            .password(u.getPasswordHash())
            .roles(u.getRol())
            .disabled(!u.isEstado())
            .build();
    }
}