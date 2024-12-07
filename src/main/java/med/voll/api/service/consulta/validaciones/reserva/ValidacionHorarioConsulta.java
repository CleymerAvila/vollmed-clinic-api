package med.voll.api.service.consulta.validaciones.reserva;

import med.voll.api.application.dto.consulta.DatosReservaConsulta;
import med.voll.api.infrastructure.errors.exception.ValidacionException;
import med.voll.api.service.consulta.validaciones.ValidadorConsultas;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class ValidacionHorarioConsulta implements ValidadorConsultas {

    public void validar(DatosReservaConsulta datos){
        var fechaConsulta = datos.fecha();

        var domingo = fechaConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);

        var horarioAntesAperturaClinica = fechaConsulta.getHour()<7;

        var horarioDespuesAperturaClinica = fechaConsulta.getHour() > 18;

        if (domingo || horarioAntesAperturaClinica || horarioDespuesAperturaClinica){
            throw new ValidacionException("Horario selecionado fuera del horario de atencion");
        }


    }
}
