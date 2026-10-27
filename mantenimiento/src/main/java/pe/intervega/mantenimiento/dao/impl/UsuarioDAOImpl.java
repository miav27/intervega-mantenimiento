package pe.intervega.mantenimiento.dao.impl;

import java.util.Optional;
import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pe.intervega.mantenimiento.dao.UsuarioDAO;
import pe.intervega.mantenimiento.model.Usuario;

@Repository
public class UsuarioDAOImpl implements UsuarioDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<Usuario> buscarPorUsername(String username) {
        return em.createQuery(
                "SELECT u FROM Usuario u WHERE u.username = :username", Usuario.class)
            .setParameter("username", username)
            .getResultStream()
            .findFirst();
    }
}