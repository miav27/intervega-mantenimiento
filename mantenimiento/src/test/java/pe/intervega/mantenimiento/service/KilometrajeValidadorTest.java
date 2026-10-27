package pe.intervega.mantenimiento.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class KilometrajeValidadorTest {

    private final KilometrajeValidador validador = new KilometrajeValidador();

    // La placa se limpia: sin espacios y en mayúsculas
    @Test
    void normalizaLaPlaca_quitaEspaciosYUsaMayusculas() {
        assertEquals("ABC-123", validador.normalizarPlaca("  abc-123 "));
    }

    @Test
    void placaVacia_lanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> validador.validar("", 50000, 45000));
    }

    @Test
    void formatoDePlacaInvalido_lanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> validador.validar("12", 50000, 45000));
    }

    // El odómetro no puede retroceder
    @Test
    void kilometrajeMenorAlActual_lanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> validador.validar("ABC-123", 40000, 45000));
    }

    @Test
    void datosValidos_noLanzaExcepcion() {
        assertDoesNotThrow(() -> validador.validar("ABC-123", 46000, 45000));
    }
}