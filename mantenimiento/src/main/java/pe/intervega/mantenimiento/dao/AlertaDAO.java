package pe.intervega.mantenimiento.dao;

import java.util.List;
import pe.intervega.mantenimiento.model.AlertaMantenimiento;
import pe.intervega.mantenimiento.model.TipoMantenimiento;

// Contrato de acceso a datos de las alertas de mantenimiento
public interface AlertaDAO {

    // Tipos preventivos que tienen un intervalo definido
    List<TipoMantenimiento> listarTiposPreventivos();

    // Km en que se hizo por última vez ese mantenimiento (0 si nunca se hizo)
    int ultimoKilometrajeMantenimiento(int idVehiculo, int idTipo);

    boolean existeAlertaPendiente(int idVehiculo, int idTipo);

    void crear(AlertaMantenimiento alerta);

    // Alertas pendientes; si idTaller es null devuelve las de todos los talleres
    List<AlertaMantenimiento> listarPendientes(Integer idTaller);
}