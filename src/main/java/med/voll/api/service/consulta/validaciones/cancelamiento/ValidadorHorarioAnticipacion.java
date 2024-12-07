package med.voll.api.service.consulta.validaciones.cancelamiento;

import med.voll.api.application.dto.consulta.DatosCancelamientoConsulta;
import med.voll.api.domain.repository.ConsultaRepository;
import med.voll.api.infrastructure.errors.exception.ValidacionException;
import med.voll.api.service.consulta.validaciones.ValidadorCancelamientoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ValidadorHorarioAnticipacion implements ValidadorCancelamientoConsulta {

    @Autowired
    private ConsultaRepository repository;

    @Override
    public void validar(DatosCancelamientoConsulta datos) {

        var consulta = repository.getReferenceById(datos.idConsulta());

        var ahora = LocalDateTime.now();

        var diferenciaEnHoras = Duration.between(ahora, consulta.getFecha()).toHours();

        if (diferenciaEnHoras < 24){
            throw new ValidacionException("¡La consulta solo puede ser cancelada con anticipacion de 24 horas minimas!");
        }
    }
}
