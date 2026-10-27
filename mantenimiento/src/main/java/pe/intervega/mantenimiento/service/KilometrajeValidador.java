package pe.intervega.mantenimiento.service;

import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import com.google.common.base.Preconditions;

// Reglas de negocio para registrar kilometraje (no depende de la base de datos)
@Component
public class KilometrajeValidador {

    // Formato de placa esperado: tres caracteres, guion, tres caracteres (ej. ABC-123)
    private static final Pattern FORMATO_PLACA = Pattern.compile("^[A-Z0-9]{3}-[A-Z0-9]{3}$");

    // Quita espacios y pasa a mayúsculas (Apache Commons)
    public String normalizarPlaca(String placa) {
        return StringUtils.upperCase(StringUtils.trimToEmpty(placa));
    }

    // Lanza IllegalArgumentException con un mensaje claro si algo no cumple (Guava)
    public void validar(String placa, int kmNuevo, int kmActual) {
        Preconditions.checkArgument(StringUtils.isNotBlank(placa), "La placa es obligatoria");
        Preconditions.checkArgument(FORMATO_PLACA.matcher(placa).matches(),
                "Formato de placa inválido (ejemplo: ABC-123)");
        Preconditions.checkArgument(kmNuevo >= 0, "El kilometraje no puede ser negativo");
        // El odómetro nunca retrocede
        Preconditions.checkArgument(kmNuevo >= kmActual,
                "El kilometraje no puede ser menor al actual");
    }
}