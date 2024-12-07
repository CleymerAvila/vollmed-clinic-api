package med.voll.api.application.dto.medico;

import med.voll.api.application.dto.DatosDireccion;
import med.voll.api.domain.model.Medico;

public record DatosMedicoDTO(
        String nombre,
        String email,
        String telefono,
        String documento,
        String especialidad,
        DatosDireccion direccion) {

    public DatosMedicoDTO(Medico medico){
        this(medico.getNombre(), medico.getEmail()
                , medico.getTelefono(), medico.getDocumento(),
                        medico.getEspecialidad().toString(), new DatosDireccion(medico.getDireccion().getCalle(), medico.getDireccion().
                        getDistrito(), medico.getDireccion().getCiudad(), medico.getDireccion().getNumero(),
                        medico.getDireccion().getComplemento()));
    }
}
