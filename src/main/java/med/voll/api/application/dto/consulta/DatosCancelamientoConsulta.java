package med.voll.api.application.dto.consulta;

import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.model.MotivoCancelamiento;

public record DatosCancelamientoConsulta(@NotNull Long idConsulta, @NotNull MotivoCancelamiento motivo) {
}
