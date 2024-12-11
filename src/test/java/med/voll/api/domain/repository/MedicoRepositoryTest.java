package med.voll.api.domain.repository;

import jakarta.persistence.EntityManager;
import med.voll.api.application.dto.DatosDireccion;
import med.voll.api.application.dto.medico.DatosMedicosRegistro;
import med.voll.api.application.dto.paciente.DatosRegistroPaciente;
import med.voll.api.domain.model.Consulta;
import med.voll.api.domain.model.Especialidad;
import med.voll.api.domain.model.Medico;
import med.voll.api.domain.model.Paciente;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class MedicoRepositoryTest {

    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private EntityManager  em;

    @Test
    @DisplayName("Debería devolver null cuando el medico buscado existe pero no esta disponible en esa fecha")
    void elegirMedicoAleatorioDisponibleEscenario1() {

        // GIVEN O ARRANGE

        var lunesSiguienteAlas10 = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(10, 0);


        var medico = registrarMedico("Medico 1", "medico1@gmail.com", "293034", Especialidad.CARDIOLOGIA );
        var paciente = registrarPaciente("Paciente 1", "paciente1@gmail.com", "123567");

        // WHEN O ACT
        registrarConsulta(medico, paciente, lunesSiguienteAlas10);

        var medicoLibre = medicoRepository.elegirMedicoAleatorioDisponible(Especialidad.CARDIOLOGIA, lunesSiguienteAlas10);

        // THEN O ASSERT
        assertThat(medicoLibre).isNull();
    }

    @Test
    @DisplayName("Debería devolver medico cuando el medico buscado esta disponible en esa fecha")
    void elegirMedicoAleatorioDisponibleEscenario2() {

        // GIVEN O ARRANGE
        var lunesSiguienteAlas10 = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(10, 0);


        var medico = registrarMedico("Medico 1", "medico1@gmail.com", "293034", Especialidad.CARDIOLOGIA );

        // WHEN O ACT
        var medicoLibre = medicoRepository.elegirMedicoAleatorioDisponible(Especialidad.CARDIOLOGIA, lunesSiguienteAlas10);

        // THEN O ASSERT
        assertThat(medicoLibre).isEqualTo(medico);
    }

    private void registrarConsulta(Medico medico, Paciente paciente, LocalDateTime fecha){
        em.persist(new Consulta(null, medico, paciente, fecha, null));
    }

    private Medico registrarMedico(String nombre, String email, String documento, Especialidad especialidad){
        var medico = new Medico(datosMedico(nombre, email, documento, especialidad));
        em.persist(medico);

        return medico;
    }

    private Paciente registrarPaciente(String nombre, String email, String documento){
        var paciente = new Paciente(datosPaciente(nombre, email, documento));
        em.persist(paciente);
        return paciente;
    }

    private DatosMedicosRegistro datosMedico(String nombre, String email, String documento, Especialidad especialidad){
        return new DatosMedicosRegistro(
                nombre,
                email,
                "2390239",
                documento,
                especialidad,
                datosDireccion()
        );
    }

    private DatosRegistroPaciente datosPaciente(String nombre, String email, String documento){
        return new DatosRegistroPaciente(
                nombre,
                email,
                "909509",
                documento,
                "2039293",
                "provincia z",
                datosDireccion()
        );
    }

    private DatosDireccion datosDireccion(){
        return new DatosDireccion(
                "calle x",
                "distrito",
                "ciudad z",
                "129",
                "a"
        );
    }


}