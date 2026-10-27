package pe.intervega.mantenimiento.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.intervega.mantenimiento.dao.VehiculoDAO;
import pe.intervega.mantenimiento.model.Vehiculo;

// Contiene la lógica de vehículos; depende de la interfaz VehiculoDAO
@Service
public class VehiculoService {

    private final VehiculoDAO vehiculoDAO;

    public VehiculoService(VehiculoDAO vehiculoDAO) {
        this.vehiculoDAO = vehiculoDAO;
    }

    @Transactional(readOnly = true)
    public List<Vehiculo> listarTodos() {
        return vehiculoDAO.listar();
    }

    @Transactional(readOnly = true)
    public Optional<Vehiculo> buscarPorPlaca(String placa) {
        return vehiculoDAO.buscarPorPlaca(placa);
    }
}
