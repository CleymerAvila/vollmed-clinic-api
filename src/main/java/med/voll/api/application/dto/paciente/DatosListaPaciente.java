package med.voll.api.application.dto.paciente;

import med.voll.api.domain.model.Paciente;

public record DatosListaPaciente(String nombre, String email, String documentoIdentidad) {
    public DatosListaPaciente(Paciente paciente) {
        this(paciente.getNombre(), paciente.getEmail(), paciente.getDocumento());
    }
}
