package med.voll.api.application.dto.paciente;

import med.voll.api.application.dto.DatosDireccion;
import med.voll.api.domain.model.Paciente;

public record DatosDetalladosPaciente(
        Long id,
        String nombre,
        String email,
        String telefono,
        String documento,
        String provincia,
        String codigoPostal,
        DatosDireccion direccion
        ) {

    public DatosDetalladosPaciente(Paciente paciente){
        this(paciente.getId(), paciente.getNombre(), paciente.getEmail(), paciente.getDocumento(), paciente.getProvincia(),
                paciente.getCodigoPostal(), paciente.getTelefono(), new DatosDireccion(paciente.getDireccion().getCalle(), paciente.getDireccion().getDistrito(),
                        paciente.getDireccion().getCiudad(), paciente.getDireccion().getNumero(), paciente.getDireccion().getComplemento()));
    }
}
