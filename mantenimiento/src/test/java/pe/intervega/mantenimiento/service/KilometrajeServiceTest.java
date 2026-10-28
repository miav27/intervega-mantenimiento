package pe.intervega.mantenimiento.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;
import pe.intervega.mantenimiento.dao.KilometrajeDAO;
import pe.intervega.mantenimiento.dao.VehiculoDAO;
import pe.intervega.mantenimiento.model.RegistroKilometraje;
import pe.intervega.mantenimiento.model.Taller;
import pe.intervega.mantenimiento.model.Vehiculo;

@ExtendWith(MockitoExtension.class)
class KilometrajeServiceTest {

    // Los DAO se simulan: la prueba no toca MySQL
    @Mock
    private KilometrajeDAO kilometrajeDAO;

    @Mock
    private VehiculoDAO vehiculoDAO;

    private KilometrajeService servicio;

    @BeforeEach
    void preparar() {
        // El validador es lógica pura, se usa el real
        servicio = new KilometrajeService(kilometrajeDAO, vehiculoDAO, new KilometrajeValidador());
    }

    // Vehículo de prueba: id 1, placa ABC-123, 45200 km, del taller 10
    private Vehiculo crearVehiculo() {
        Taller taller = new Taller();
        taller.setId(10);
        taller.setNombre("Taller Lima Norte");
        Vehiculo v = new Vehiculo();
        v.setId(1);
        v.setPlaca("ABC-123");
        v.setKilometrajeActual(45200);
        v.setTaller(taller);
        return v;
    }

    @Test
    void datosValidos_guardaRegistroYActualizaVehiculo() {
        when(vehiculoDAO.buscarPorPlaca("ABC-123")).thenReturn(Optional.of(crearVehiculo()));

        // La placa llega con espacios y en minúsculas: el servicio la normaliza
        servicio.registrar(" abc-123 ", 46000, "ingreso", 1, null);

        ArgumentCaptor<RegistroKilometraje> captor = ArgumentCaptor.forClass(RegistroKilometraje.class);
        verify(kilometrajeDAO).guardar(captor.capture());
        assertEquals(10, captor.getValue().getIdTaller().intValue());
        assertEquals(46000, captor.getValue().getKilometraje());
        verify(kilometrajeDAO).actualizarKilometrajeVehiculo(1, 46000);
    }

    @Test
    void placaInexistente_lanzaExcepcionYNoGuarda() {
        when(vehiculoDAO.buscarPorPlaca("ZZZ-999")).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,
            () -> servicio.registrar("ZZZ-999", 50000, null, 1, null));
        verify(kilometrajeDAO, never()).guardar(any());
    }

    @Test
    void jefeDeOtroTaller_lanzaAccesoDenegado() {
        when(vehiculoDAO.buscarPorPlaca("ABC-123")).thenReturn(Optional.of(crearVehiculo()));

        // El usuario pertenece al taller 99, el vehículo es del taller 10
        assertThrows(AccessDeniedException.class,
            () -> servicio.registrar("ABC-123", 46000, null, 2, 99));
        verify(kilometrajeDAO, never()).guardar(any());
    }

    @Test
    void kilometrajeMenorAlActual_lanzaExcepcionYNoGuarda() {
        when(vehiculoDAO.buscarPorPlaca("ABC-123")).thenReturn(Optional.of(crearVehiculo()));

        assertThrows(IllegalArgumentException.class,
            () -> servicio.registrar("ABC-123", 40000, null, 1, null));
        verify(kilometrajeDAO, never()).guardar(any());
    }
}