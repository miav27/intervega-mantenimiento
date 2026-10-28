package pe.intervega.mantenimiento.dao;

import java.util.List;
import pe.intervega.mantenimiento.model.RegistroKilometraje;

// Contrato de acceso a datos del kilometraje: el servicio solo conoce esta interfaz
public interface KilometrajeDAO {

    // Inserta un registro en registro_kilometraje
    void guardar(RegistroKilometraje registro);

    // Actualiza el kilometraje_actual del vehículo
    void actualizarKilometrajeVehiculo(int idVehiculo, int kilometraje);

    // Últimos registros; si idTaller es null devuelve los de todos los talleres
    List<RegistroKilometraje> listarRecientes(Integer idTaller);
}