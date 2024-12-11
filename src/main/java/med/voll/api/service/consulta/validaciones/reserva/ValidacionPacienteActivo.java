package med.voll.api.service.consulta.validaciones.reserva;

import med.voll.api.application.dto.consulta.DatosReservaConsulta;
import med.voll.api.domain.repository.PacienteRepository;
import med.voll.api.infrastructure.errors.exception.ValidacionException;
import med.voll.api.service.consulta.validaciones.ValidadorConsultas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidacionPacienteActivo implements ValidadorConsultas {

    @Autowired
    private PacienteRepository repository;

    public void validar(DatosReservaConsulta datos){
        var pacienteEstaActivo = repository.findActivoById(datos.idPaciente());
        if (!pacienteEstaActivo) {
            throw new ValidacionException("El paciente no esta activo");
        }
    }
}
