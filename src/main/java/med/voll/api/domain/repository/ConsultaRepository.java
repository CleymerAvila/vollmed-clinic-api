package med.voll.api.domain.repository;

import jakarta.validation.constraints.NotNull;
import med.voll.api.application.dto.consulta.DatosRelatorioConsultaMensual;
import med.voll.api.domain.model.Consulta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta , Long> {

    boolean existsByMedicoIdAndFecha(Long idMedico, LocalDateTime fecqha);

    boolean existsByPacienteIdAndFechaBetween(Long idPaciente, LocalDateTime primerHorario, LocalDateTime segundoHorario);

    Page<Consulta> findByMotivoCancelamientoIsNull(Pageable paginacion);

    boolean existsByPacienteIdAndFechaBetweenAndMotivoCancelamientoIsNotNull(@NotNull Long idPaciente,
                                                                             LocalDateTime primerHorario,
                                                                             LocalDateTime ultimoHorario);

    @Query("""
            SELECT new med.voll.api.application.dto.consulta.DatosRelatorioConsultaMensual(c.medico.nombre, COUNT(c))
            FROM Consulta c
            WHERE YEAR(c.fecha) = :year AND MONTH(c.fecha) = :month
            GROUP BY c.medico.nombre
            """)
    List<DatosRelatorioConsultaMensual> findConsultasPorMes(int year,int month);
}
