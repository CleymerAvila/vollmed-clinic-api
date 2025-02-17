package med.voll.api.controller;

import med.voll.api.application.dto.DatosDireccion;
import med.voll.api.application.dto.medico.DatosMedicosRegistro;
import med.voll.api.application.dto.medico.DatosRespuestaMedico;
import med.voll.api.domain.model.Especialidad;
import med.voll.api.domain.model.Medico;
import med.voll.api.domain.repository.MedicoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class MedicoControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<DatosMedicosRegistro> datosMedicosRegistroJson;

    @Autowired
    private JacksonTester<DatosRespuestaMedico> datosDetallesMedicoJson;

    @MockBean
    private MedicoRepository repository;

    @Test
    @DisplayName("Debería devolver código http 400 cuando las informaciones son invalidas")
    @WithMockUser
    void registrarMedicoEscenario1() throws Exception {
        var response = mvc.perform(post("/medicos"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Debería devolver código http 200 cuando las informaciones son válidas")
    @WithMockUser
    void registrarMedicoEscenario2() throws Exception {

        var datosRegistro = new DatosMedicosRegistro(
                "Medico",
                "new.medico@voll.med",
                "0239034",
                "293933",
                Especialidad.CARDIOLOGIA,
                datosDireccion()
        );

        when(repository.save(any())).thenReturn(new Medico(datosRegistro));

        var response = mvc.perform(post("/medicos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(datosMedicosRegistroJson.write(datosRegistro).getJson()))
                .andReturn().getResponse();

        var datosDetalles = new DatosRespuestaMedico(
                null,
                datosRegistro.nombre(),
                datosRegistro.email(),
                datosRegistro.telefono(),
                datosRegistro.documento(),
                datosRegistro.especialidad().toString(),
                datosRegistro.direccion()
        );

        var jsonEsperado = datosDetallesMedicoJson.write(datosDetalles).getJson();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }

    private DatosDireccion datosDireccion() {
        return new DatosDireccion(
                "calle ejemplo",
                "distrito",
                "Buenos Aires",
                "092303",
                "a"
        );
    }
}