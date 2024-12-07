package med.voll.api.application.dto.paciente;

public record DatosRespuestaPaciente(
        Long id,
        String nombre,
        String email,
        String telefono,
        String documento,
        String provincia) {
}
