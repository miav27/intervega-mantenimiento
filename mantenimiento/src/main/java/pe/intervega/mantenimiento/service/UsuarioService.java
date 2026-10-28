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

// Este archivo comprueba la identidad del usuario

@Service
//Implementaste la interfaz de Spring Security
public class UsuarioService implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(UsuarioService.class);

    private final UsuarioDAO usuarioDAO;

    // Depende de la interfaz, no de la implementación (inversión de dependencias)
    public UsuarioService(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        //buscarPorUsername(username): busca al usuario en MySQL.
        Usuario u = usuarioDAO.buscarPorUsername(username)
            .orElseThrow(() -> {
                log.warn("Intento de inicio de sesión con usuario inexistente: {}", username);
                return new UsernameNotFoundException("Usuario no encontrado");
            });
        //getUsername(): obtiene su nombre de usuario.
        log.info("Usuario cargado para autenticación: {}", u.getUsername());

        return User.withUsername(u.getUsername())
        //getPasswordHash(): obtiene la contraseña cifrada
            .password(u.getPasswordHash())
        //roles(u.getRol()): asigna su rol.    
            .roles(u.getRol())
        //disabled(!u.isEstado()): impide el acceso si la cuenta está deshabilitada.    
            .disabled(!u.isEstado())
            .build();
    }
        // Devuelve el usuario completo (id, taller, rol) a partir del nombre de usuario logueado
    public Usuario obtenerPorUsername(String username) {
        return usuarioDAO.buscarPorUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
    }
}