package med.voll.api.service.consulta.validaciones.reserva;

import med.voll.api.application.dto.consulta.DatosReservaConsulta;
import med.voll.api.infrastructure.errors.exception.ValidacionException;
import med.voll.api.service.consulta.validaciones.ValidadorConsultas;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ValidacionConsultaAnticipacion implements ValidadorConsultas {

    public void validar(DatosReservaConsulta datos){
        var fechaConsulta = datos.fecha();

        var ahora = LocalDateTime.now();

        var diferenciaEnMinutos = Duration.between(ahora, fechaConsulta).toMinutes();

        if (diferenciaEnMinutos <30){
            throw new ValidacionException("Horario seleccionado con menos de 30 minutos de anticipación");
        }
    }
}
