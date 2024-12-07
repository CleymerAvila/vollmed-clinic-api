package med.voll.api.application.dto.medico;

import med.voll.api.application.dto.DatosDireccion;

public record DatosRespuestaMedico(
        Long id,
        String nombre,
        String email,
        String telefono,
        String documento,
        DatosDireccion direccion) {
}
