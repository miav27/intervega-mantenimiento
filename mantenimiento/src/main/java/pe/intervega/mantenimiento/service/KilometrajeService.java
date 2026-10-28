package pe.intervega.mantenimiento.service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.intervega.mantenimiento.dao.KilometrajeDAO;
import pe.intervega.mantenimiento.dao.VehiculoDAO;
import pe.intervega.mantenimiento.model.RegistroKilometraje;
import pe.intervega.mantenimiento.model.Vehiculo;

// Lógica del RF02: registrar el kilometraje de un vehículo al ingresar al taller
@Service
public class KilometrajeService {

    private static final Logger log = LoggerFactory.getLogger(KilometrajeService.class);

    private final KilometrajeDAO kilometrajeDAO;
    private final VehiculoDAO vehiculoDAO;
    private final KilometrajeValidador validador;

    // Depende de interfaces, no de implementaciones (inversión de dependencias)
    public KilometrajeService(KilometrajeDAO kilometrajeDAO, VehiculoDAO vehiculoDAO,
                              KilometrajeValidador validador) {
        this.kilometrajeDAO = kilometrajeDAO;
        this.vehiculoDAO = vehiculoDAO;
        this.validador = validador;
    }

    // idTallerUsuario es null para el Administrador General (puede registrar en cualquier taller)
    // @Transactional: si falla la actualización del vehículo, no se guarda el registro
    @Transactional
    public void registrar(String placa, int kilometraje, String observacion,
                          Integer idUsuario, Integer idTallerUsuario) {
        String placaNormalizada = validador.normalizarPlaca(placa);

        Vehiculo vehiculo = vehiculoDAO.buscarPorPlaca(placaNormalizada)
            .orElseThrow(() -> new IllegalArgumentException(
                "No existe un vehículo con la placa " + placaNormalizada));

        // El Jefe de Taller solo puede registrar vehículos de su taller
        if (idTallerUsuario != null && !idTallerUsuario.equals(vehiculo.getTaller().getId())) {
            log.warn("Acceso denegado: usuario {} intentó registrar la placa {}", idUsuario, placaNormalizada);
            throw new AccessDeniedException("El vehículo no pertenece a su taller");
        }

        validador.validar(placaNormalizada, kilometraje, vehiculo.getKilometrajeActual());

        RegistroKilometraje registro = new RegistroKilometraje();
        registro.setIdVehiculo(vehiculo.getId());
        registro.setIdTaller(vehiculo.getTaller().getId());
        registro.setIdUsuario(idUsuario);
        registro.setKilometraje(kilometraje);
        registro.setObservacion(observacion);

        kilometrajeDAO.guardar(registro);
        kilometrajeDAO.actualizarKilometrajeVehiculo(vehiculo.getId(), kilometraje);
        log.info("Kilometraje registrado: placa {} con {} km", placaNormalizada, kilometraje);
    }

    // Lista los registros recientes; null devuelve los de todos los talleres
    public List<RegistroKilometraje> listarRecientes(Integer idTallerUsuario) {
        return kilometrajeDAO.listarRecientes(idTallerUsuario);
    }
}