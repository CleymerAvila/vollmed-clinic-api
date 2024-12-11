package med.voll.api.controller;

import med.voll.api.application.dto.consulta.DatosDetallesConsulta;
import med.voll.api.application.dto.consulta.DatosReservaConsulta;
import med.voll.api.domain.model.Especialidad;
import med.voll.api.service.consulta.ConsultaReservaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ConsultaControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JacksonTester<DatosReservaConsulta> datosReservaConsultaJson;
    @Autowired
    private JacksonTester<DatosDetallesConsulta> datosDetallesConsultaJson;



    @Mock
    private ConsultaReservaService reservaService;

    @Test
    @DisplayName("Debería devolver http 400 cuando la requests no tenga datos")
    @WithMockUser(username = "cleymer.avila" , roles = {"USER"})
    void reservar_escenario1() throws Exception {
        var response = mockMvc.perform(post("/consultas"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Debería devolver http 200 cuando la requests reciba un json valido")
    @WithMockUser(username = "cleymer.avila" , roles = {"USER"})
    void reservar_escenario2() throws Exception {
        var fecha = LocalDateTime.now().plusHours(1);
        var datosDetalles = new DatosDetallesConsulta(null, 2L, 4L , fecha);


        when(reservaService.reservar(any())).thenReturn(datosDetalles);

        var response = mockMvc.perform(post("/consultas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(datosReservaConsultaJson.write(
                                new DatosReservaConsulta(2L, 4L, fecha, Especialidad.CARDIOLOGIA)
                        ).getJson()))
                .andReturn().getResponse();

        var jsonEsperado = datosDetallesConsultaJson.write(
                datosDetalles
        ).getJson();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }
}