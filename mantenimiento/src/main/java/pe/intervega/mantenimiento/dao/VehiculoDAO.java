package pe.intervega.mantenimiento.dao;

import java.util.List;
import java.util.Optional;
import pe.intervega.mantenimiento.model.Vehiculo;

public interface VehiculoDAO {
    List<Vehiculo> listar();
    Optional<Vehiculo> buscarPorPlaca(String placa);
}
