package med.voll.api.service.consulta.validaciones.reserva;

import med.voll.api.application.dto.consulta.DatosReservaConsulta;
import med.voll.api.domain.repository.ConsultaRepository;
import med.voll.api.infrastructure.errors.exception.ValidacionException;
import med.voll.api.service.consulta.validaciones.ValidadorConsultas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidarPacienteSinConsultaMismoDia implements ValidadorConsultas {

    @Autowired
    private ConsultaRepository repository;

    public void validar(DatosReservaConsulta datos){
        var primerHorario = datos.fecha().withHour(7);
        var ultimoHorario = datos.fecha().withHour(18);
        var pacienteTieneOtraConsultaEnElDia = repository.existsByPacienteIdAndFechaBetweenAndMotivoCancelamientoIsNotNull(
                datos.idPaciente(), primerHorario, ultimoHorario);


        if (pacienteTieneOtraConsultaEnElDia){
            throw new ValidacionException("Paciente ya tiene una consulta reservada para ese dia");
        }

    }
}
