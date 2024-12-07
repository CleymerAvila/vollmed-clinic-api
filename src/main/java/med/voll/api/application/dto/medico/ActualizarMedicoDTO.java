package med.voll.api.application.dto.medico;

import jakarta.validation.constraints.NotNull;
import med.voll.api.application.dto.DatosDireccion;

public record ActualizarMedicoDTO(
        @NotNull
        Long id,
        String nombre,
        String documento,
        DatosDireccion direccion) {
}
