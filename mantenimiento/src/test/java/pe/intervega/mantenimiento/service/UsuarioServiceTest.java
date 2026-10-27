package pe.intervega.mantenimiento.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pe.intervega.mantenimiento.dao.UsuarioDAO;
import pe.intervega.mantenimiento.model.Usuario;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioDAO usuarioDAO;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario crearUsuario(boolean activo) {
        Usuario u = new Usuario();
        u.setUsername("jefe1");
        u.setPasswordHash("$2a$10$hashdeprueba");
        u.setRol("JEFE_TALLER");
        u.setEstado(activo);
        return u;
    }

    @Test
    void usuarioInexistente_lanzaExcepcion() {
        when(usuarioDAO.buscarPorUsername("noexiste")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class,
            () -> usuarioService.loadUserByUsername("noexiste"));
    }

    @Test
    void usuarioExistente_devuelveHashYRol() {
        when(usuarioDAO.buscarPorUsername("jefe1")).thenReturn(Optional.of(crearUsuario(true)));

        UserDetails detalles = usuarioService.loadUserByUsername("jefe1");

        assertEquals("jefe1", detalles.getUsername());
        assertEquals("$2a$10$hashdeprueba", detalles.getPassword());
        assertTrue(detalles.getAuthorities().stream()
            .anyMatch(a -> a.getAuthority().equals("ROLE_JEFE_TALLER")));
    }

    @Test
    void usuarioInactivo_quedaDeshabilitado() {
        when(usuarioDAO.buscarPorUsername("jefe1")).thenReturn(Optional.of(crearUsuario(false)));

        assertFalse(usuarioService.loadUserByUsername("jefe1").isEnabled());
    }
}