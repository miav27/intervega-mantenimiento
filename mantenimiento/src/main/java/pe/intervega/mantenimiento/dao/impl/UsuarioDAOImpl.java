package pe.intervega.mantenimiento.dao.impl;

import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import pe.intervega.mantenimiento.dao.UsuarioDAO;
import pe.intervega.mantenimiento.model.Usuario;

@Repository
public class UsuarioDAOImpl implements UsuarioDAO {

    private final JdbcTemplate jdbc;

    public UsuarioDAOImpl(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    // Convierte cada fila de la consulta en un objeto Usuario
    private static final RowMapper<Usuario> MAPPER = (rs, fila) -> {
        Usuario u = new Usuario();
        u.setId(rs.getInt("id_usuario"));
        u.setUsername(rs.getString("username"));
        u.setPasswordHash(rs.getString("password_hash"));
        u.setNombres(rs.getString("nombres"));
        u.setRol(rs.getString("rol"));
        int taller = rs.getInt("id_taller");
        u.setIdTaller(rs.wasNull() ? null : taller);
        u.setEstado(rs.getBoolean("estado"));
        u.setIntentosFallidos(rs.getInt("intentos_fallidos"));
        return u;
    };

    @Override
    public Optional<Usuario> buscarPorUsername(String username) {
//El signo ? permite pasar el nombre de usuario como parámetro,
// en lugar de concatenarlo directamente al SQL.
        String sql = "SELECT u.id_usuario, u.username, u.password_hash, u.nombres, "
                   + "r.nombre AS rol, u.id_taller, u.estado, u.intentos_fallidos "
                   + "FROM usuario u JOIN rol r ON r.id_rol = u.id_rol "
                   + "WHERE u.username = ?";
        return jdbc.query(sql, MAPPER, username).stream().findFirst();
    }
}
