package pe.intervega.mantenimiento.dao.impl;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import pe.intervega.mantenimiento.dao.AlertaDAO;
import pe.intervega.mantenimiento.model.AlertaMantenimiento;
import pe.intervega.mantenimiento.model.TipoMantenimiento;

@Repository
public class AlertaDAOImpl implements AlertaDAO {

    private final JdbcTemplate jdbc;

    public AlertaDAOImpl(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private static final RowMapper<TipoMantenimiento> TIPO_MAPPER = (rs, fila) -> {
        TipoMantenimiento t = new TipoMantenimiento();
        t.setId(rs.getInt("id_tipo"));
        t.setNombre(rs.getString("nombre"));
        t.setIntervaloKm(rs.getInt("intervalo_km"));
        return t;
    };

    private static final RowMapper<AlertaMantenimiento> ALERTA_MAPPER = (rs, fila) -> {
        AlertaMantenimiento a = new AlertaMantenimiento();
        a.setId(rs.getInt("id_alerta"));
        a.setIdVehiculo(rs.getInt("id_vehiculo"));
        a.setPlaca(rs.getString("placa"));
        a.setIdTipo(rs.getInt("id_tipo"));
        a.setTipoNombre(rs.getString("tipo_nombre"));
        a.setKmProgramado(rs.getInt("km_programado"));
        a.setFechaGeneracion(rs.getTimestamp("fecha_generacion").toLocalDateTime());
        a.setEstado(rs.getString("estado"));
        return a;
    };

    @Override
    public List<TipoMantenimiento> listarTiposPreventivos() {
        return jdbc.query("SELECT id_tipo, nombre, intervalo_km FROM tipo_mantenimiento "
                        + "WHERE es_preventivo = 1 AND intervalo_km IS NOT NULL", TIPO_MAPPER);
    }

    // COALESCE devuelve 0 si el vehículo nunca tuvo ese mantenimiento
    @Override
    public int ultimoKilometrajeMantenimiento(int idVehiculo, int idTipo) {
        Integer km = jdbc.queryForObject(
            "SELECT COALESCE(MAX(kilometraje_ingreso), 0) FROM mantenimiento "
          + "WHERE id_vehiculo = ? AND id_tipo = ?", Integer.class, idVehiculo, idTipo);
        return km == null ? 0 : km;
    }

    @Override
    public boolean existeAlertaPendiente(int idVehiculo, int idTipo) {
        Integer total = jdbc.queryForObject(
            "SELECT COUNT(*) FROM alerta_mantenimiento "
          + "WHERE id_vehiculo = ? AND id_tipo = ? AND estado = 'PENDIENTE'",
            Integer.class, idVehiculo, idTipo);
        return total != null && total > 0;
    }

    // El estado y la fecha los completa la base de datos (PENDIENTE y fecha actual)
    @Override
    public void crear(AlertaMantenimiento a) {
        jdbc.update("INSERT INTO alerta_mantenimiento (id_vehiculo, id_tipo, km_programado) "
                  + "VALUES (?, ?, ?)", a.getIdVehiculo(), a.getIdTipo(), a.getKmProgramado());
    }

    @Override
    public List<AlertaMantenimiento> listarPendientes(Integer idTaller) {
        String base = "SELECT a.id_alerta, a.id_vehiculo, v.placa, a.id_tipo, t.nombre AS tipo_nombre, "
                    + "a.km_programado, a.fecha_generacion, a.estado "
                    + "FROM alerta_mantenimiento a "
                    + "JOIN vehiculo v ON v.id_vehiculo = a.id_vehiculo "
                    + "JOIN tipo_mantenimiento t ON t.id_tipo = a.id_tipo "
                    + "WHERE a.estado = 'PENDIENTE' ";
        if (idTaller == null) {
            return jdbc.query(base + "ORDER BY a.fecha_generacion DESC", ALERTA_MAPPER);
        }
        return jdbc.query(base + "AND v.id_taller = ? ORDER BY a.fecha_generacion DESC",
                          ALERTA_MAPPER, idTaller);
    }
}