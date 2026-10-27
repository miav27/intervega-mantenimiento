package pe.intervega.mantenimiento.dao;

import java.util.Optional;
import pe.intervega.mantenimiento.model.Usuario;

public interface UsuarioDAO {
    Optional<Usuario> buscarPorUsername(String username);
}