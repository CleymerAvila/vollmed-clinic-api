package med.voll.api.domain.repository;

import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.model.Consulta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta , Long> {

    boolean existsByMedicoIdAndFecha(Long idMedico, LocalDateTime fecha);

    boolean existsByPacienteIdAndFechaBetween(Long idPaciente, LocalDateTime primerHorario, LocalDateTime segundoHorario);

    Page<Consulta> findByMotivoCancelamientoIsNull(Pageable paginacion);

    boolean existsByPacienteIdAndFechaBetweenAndMotivoCancelamientoIsNotNull(@NotNull Long idPaciente, LocalDateTime primerHorario, LocalDateTime ultimoHorario);
}
