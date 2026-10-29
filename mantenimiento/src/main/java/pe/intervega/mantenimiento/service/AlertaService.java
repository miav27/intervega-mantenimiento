package pe.intervega.mantenimiento.service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pe.intervega.mantenimiento.dao.AlertaDAO;
import pe.intervega.mantenimiento.model.AlertaMantenimiento;
import pe.intervega.mantenimiento.model.TipoMantenimiento;
//S: solo decide alertas
//O:Los intervalos viven en la tabla tipo_mantenimiento
//agregar un mantenimiento nuevo no requiere cambiar AlertaService	
// Lógica del RF03: genera alertas cuando un vehículo alcanza el intervalo de mantenimiento
@Service
public class AlertaService {

    private static final Logger log = LoggerFactory.getLogger(AlertaService.class);

    private final AlertaDAO alertaDAO;

    // Depende de la interfaz, no de la implementación
    public AlertaService(AlertaDAO alertaDAO) {
        this.alertaDAO = alertaDAO;
    }

    // Revisa cada mantenimiento preventivo y crea la alerta si corresponde
    public void evaluar(int idVehiculo, int kilometrajeActual) {
        for (TipoMantenimiento tipo : alertaDAO.listarTiposPreventivos()) {
            int ultimo = alertaDAO.ultimoKilometrajeMantenimiento(idVehiculo, tipo.getId());
            int proximo = ultimo + tipo.getIntervaloKm();

            // Se crea solo si alcanzó el km y no hay ya una alerta pendiente igual
            if (kilometrajeActual >= proximo && !alertaDAO.existeAlertaPendiente(idVehiculo, tipo.getId())) {
                AlertaMantenimiento alerta = new AlertaMantenimiento();
                alerta.setIdVehiculo(idVehiculo);
                alerta.setIdTipo(tipo.getId());
                alerta.setKmProgramado(proximo);
                alertaDAO.crear(alerta);
                log.info("Alerta generada: vehículo {} - {} (programado a los {} km)",
                         idVehiculo, tipo.getNombre(), proximo);
            }
        }
    }

    // Alertas pendientes; null (Administrador General) devuelve las de todos los talleres
    public List<AlertaMantenimiento> listarPendientes(Integer idTallerUsuario) {
        return alertaDAO.listarPendientes(idTallerUsuario);
    }
}