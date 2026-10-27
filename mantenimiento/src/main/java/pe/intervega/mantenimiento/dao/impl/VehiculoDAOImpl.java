package pe.intervega.mantenimiento.dao.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import pe.intervega.mantenimiento.dao.VehiculoDAO;
import pe.intervega.mantenimiento.model.Taller;
import pe.intervega.mantenimiento.model.Vehiculo;

@Repository
public class VehiculoDAOImpl implements VehiculoDAO {

    private final JdbcTemplate jdbc;

    // Spring inyecta JdbcTemplate por constructor
    public VehiculoDAOImpl(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    // Convierte cada fila de la consulta en un Vehiculo con su Taller
    private static final RowMapper<Vehiculo> MAPPER = (rs, fila) -> {
        Taller t = new Taller();
        t.setId(rs.getInt("id_taller"));
        t.setNombre(rs.getString("taller_nombre"));

        Vehiculo v = new Vehiculo();
        v.setId(rs.getInt("id_vehiculo"));
        v.setPlaca(rs.getString("placa"));
        v.setMarca(rs.getString("marca"));
        v.setModelo(rs.getString("modelo"));
        int anio = rs.getInt("anio");
        v.setAnio(rs.wasNull() ? null : anio);   // respeta el NULL de la base de datos
        v.setKilometrajeActual(rs.getInt("kilometraje_actual"));
        v.setEstado(rs.getString("estado"));
        v.setTaller(t);
        return v;
    };

    // Columnas compartidas por las dos consultas
    private static final String BASE_SQL =
        "SELECT v.id_vehiculo, v.placa, v.marca, v.modelo, v.anio, "
      + "v.kilometraje_actual, v.estado, t.id_taller, t.nombre AS taller_nombre "
      + "FROM vehiculo v JOIN taller t ON t.id_taller = v.id_taller ";

    // Lista todos los vehículos ordenados por placa
    @Override
    public List<Vehiculo> listar() {
        return jdbc.query(BASE_SQL + "ORDER BY v.placa", MAPPER);
    }

    // Busca un vehículo por placa; el ? evita la inyección SQL
    @Override
    public Optional<Vehiculo> buscarPorPlaca(String placa) {
        return jdbc.query(BASE_SQL + "WHERE v.placa = ?", MAPPER, placa)
                   .stream().findFirst();
    }
}
