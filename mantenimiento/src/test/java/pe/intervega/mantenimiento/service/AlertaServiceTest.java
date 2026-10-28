package pe.intervega.mantenimiento.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.intervega.mantenimiento.dao.AlertaDAO;
import pe.intervega.mantenimiento.model.AlertaMantenimiento;
import pe.intervega.mantenimiento.model.TipoMantenimiento;

@ExtendWith(MockitoExtension.class)
class AlertaServiceTest {

    @Mock
    private AlertaDAO alertaDAO;

    @InjectMocks
    private AlertaService servicio;

    // Cambio de aceite cada 10 000 km
    private TipoMantenimiento aceite() {
        TipoMantenimiento t = new TipoMantenimiento();
        t.setId(1);
        t.setNombre("Cambio de aceite");
        t.setIntervaloKm(10000);
        return t;
    }

    @Test
    void alcanzaElIntervalo_creaLaAlerta() {
        when(alertaDAO.listarTiposPreventivos()).thenReturn(List.of(aceite()));
        // Último cambio a los 40 000 km, el siguiente toca a los 50 000
        when(alertaDAO.ultimoKilometrajeMantenimiento(1, 1)).thenReturn(40000);
        when(alertaDAO.existeAlertaPendiente(1, 1)).thenReturn(false);

        servicio.evaluar(1, 50000);

        ArgumentCaptor<AlertaMantenimiento> captor = ArgumentCaptor.forClass(AlertaMantenimiento.class);
        verify(alertaDAO).crear(captor.capture());
        assertEquals(50000, captor.getValue().getKmProgramado());
    }

    @Test
    void aunNoAlcanzaElIntervalo_noCreaAlerta() {
        when(alertaDAO.listarTiposPreventivos()).thenReturn(List.of(aceite()));
        when(alertaDAO.ultimoKilometrajeMantenimiento(1, 1)).thenReturn(40000);

        servicio.evaluar(1, 49999);

        verify(alertaDAO, never()).crear(any());
    }

    @Test
    void yaExisteAlertaPendiente_noLaDuplica() {
        when(alertaDAO.listarTiposPreventivos()).thenReturn(List.of(aceite()));
        when(alertaDAO.ultimoKilometrajeMantenimiento(1, 1)).thenReturn(40000);
        when(alertaDAO.existeAlertaPendiente(1, 1)).thenReturn(true);

        servicio.evaluar(1, 50000);

        verify(alertaDAO, never()).crear(any());
    }
}