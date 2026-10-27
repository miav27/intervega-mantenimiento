package pe.intervega.mantenimiento.dao.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pe.intervega.mantenimiento.dao.VehiculoDAO;
import pe.intervega.mantenimiento.model.Vehiculo;

@Repository
public class VehiculoDAOImpl implements VehiculoDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Vehiculo> listar() {
        return em.createQuery(
                "SELECT v FROM Vehiculo v JOIN FETCH v.taller ORDER BY v.placa", Vehiculo.class)
            .getResultList();
    }

    @Override
    public Optional<Vehiculo> buscarPorPlaca(String placa) {
        return em.createQuery(
                "SELECT v FROM Vehiculo v JOIN FETCH v.taller WHERE v.placa = :placa", Vehiculo.class)
            .setParameter("placa", placa)
            .getResultStream()
            .findFirst();
    }
}
