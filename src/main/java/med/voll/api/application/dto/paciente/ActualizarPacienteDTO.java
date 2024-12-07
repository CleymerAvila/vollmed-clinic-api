package med.voll.api.application.dto.paciente;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import med.voll.api.application.dto.DatosDireccion;

public record ActualizarPacienteDTO(
        @NotNull
        Long id,
        String nombre,
        String telefono,
        @Valid
        DatosDireccion direccion) {
}
