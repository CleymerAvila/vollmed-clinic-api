package med.voll.api.service.consulta.validaciones.reserva;

import med.voll.api.application.dto.consulta.DatosReservaConsulta;
import med.voll.api.domain.repository.MedicoRepository;
import med.voll.api.infrastructure.errors.exception.ValidacionException;
import med.voll.api.service.consulta.validaciones.ValidadorConsultas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidorMedicoActivo implements ValidadorConsultas {

    @Autowired
    private MedicoRepository repository;

    public void validar(DatosReservaConsulta datos){

        if (datos.idMedico() == null){
            return;
        }

        var medicoEstaActivo = repository.findActivoById(datos.idMedico());
        if (medicoEstaActivo){
            throw new ValidacionException("Consulta no puede ser reservada con medico excluisdo");
        }
    }
}
