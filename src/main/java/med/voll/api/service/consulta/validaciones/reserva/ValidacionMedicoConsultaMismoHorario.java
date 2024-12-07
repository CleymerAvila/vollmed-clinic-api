package med.voll.api.service.consulta.validaciones.reserva;

import med.voll.api.application.dto.consulta.DatosReservaConsulta;
import med.voll.api.domain.repository.ConsultaRepository;
import med.voll.api.infrastructure.errors.exception.ValidacionException;
import med.voll.api.service.consulta.validaciones.ValidadorConsultas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidacionMedicoConsultaMismoHorario implements ValidadorConsultas {

    @Autowired
    private ConsultaRepository repository;

    public void validar(DatosReservaConsulta datos){
        var medicoNoDisponibleEnHorario = repository.existsByMedicoIdAndFecha(datos.idMedico(), datos.fecha());

        if (medicoNoDisponibleEnHorario){
            throw new ValidacionException("Medico ya tiene otra consulta en esa misma fecha y hora ");
        }
    }
}
