package pe.intervega.mantenimiento.dao.impl;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import pe.intervega.mantenimiento.dao.KilometrajeDAO;
import pe.intervega.mantenimiento.model.RegistroKilometraje;

//S:solo hace SQL
@Repository
public class KilometrajeDAOImpl implements KilometrajeDAO {

    private final JdbcTemplate jdbc;

    // Spring inyecta JdbcTemplate por constructor
    public KilometrajeDAOImpl(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    // Convierte cada fila en un RegistroKilometraje (la placa viene del JOIN)
    private static final RowMapper<RegistroKilometraje> MAPPER = (rs, fila) -> {
        RegistroKilometraje r = new RegistroKilometraje();
        r.setId(rs.getInt("id_registro"));
        r.setIdVehiculo(rs.getInt("id_vehiculo"));
        r.setIdTaller(rs.getInt("id_taller"));
        r.setIdUsuario(rs.getInt("id_usuario"));
        r.setPlaca(rs.getString("placa"));
        r.setKilometraje(rs.getInt("kilometraje"));
        r.setFechaRegistro(rs.getTimestamp("fecha_registro").toLocalDateTime());
        r.setObservacion(rs.getString("observacion"));
        return r;
    };

    // Los ? son parámetros: los valores nunca se concatenan al SQL (evita inyección SQL)
    @Override
    public void guardar(RegistroKilometraje r) {
        String sql = "INSERT INTO registro_kilometraje "
                   + "(id_vehiculo, id_taller, id_usuario, kilometraje, observacion) "
                   + "VALUES (?, ?, ?, ?, ?)";
        jdbc.update(sql, r.getIdVehiculo(), r.getIdTaller(), r.getIdUsuario(),
                    r.getKilometraje(), r.getObservacion());
    }

    @Override
    public void actualizarKilometrajeVehiculo(int idVehiculo, int kilometraje) {
        jdbc.update("UPDATE vehiculo SET kilometraje_actual = ? WHERE id_vehiculo = ?",
                    kilometraje, idVehiculo);
    }

    // Últimos 20 registros; con idTaller null (Administrador General) trae todos los talleres
    @Override
    public List<RegistroKilometraje> listarRecientes(Integer idTaller) {
        String base = "SELECT r.id_registro, r.id_vehiculo, r.id_taller, r.id_usuario, "
                    + "v.placa, r.kilometraje, r.fecha_registro, r.observacion "
                    + "FROM registro_kilometraje r JOIN vehiculo v ON v.id_vehiculo = r.id_vehiculo ";
        if (idTaller == null) {
            return jdbc.query(base + "ORDER BY r.fecha_registro DESC LIMIT 20", MAPPER);
        }
        return jdbc.query(base + "WHERE r.id_taller = ? ORDER BY r.fecha_registro DESC LIMIT 20",
                          MAPPER, idTaller);
    }
}