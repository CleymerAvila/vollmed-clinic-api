package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import med.voll.api.application.dto.consulta.DatosCancelamientoConsulta;
import med.voll.api.application.dto.consulta.DatosDetallesConsulta;
import med.voll.api.application.dto.consulta.DatosReservaConsulta;
import med.voll.api.domain.repository.ConsultaRepository;
import med.voll.api.service.consulta.ConsultaReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/consultas")
@SecurityRequirement(name = "bearer-key")
public class ConsultaController {

    @Autowired
    private ConsultaReservaService consultaReserva;
    @Autowired
    private ConsultaRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity<DatosDetallesConsulta> reservar(@RequestBody @Valid DatosReservaConsulta datos){
        var detallesConsulta  = consultaReserva.reservar(datos);
        return ResponseEntity.ok(detallesConsulta);
    }

    @GetMapping
    public ResponseEntity<Page<DatosDetallesConsulta>> listadoConsultas(@PageableDefault(size = 10) Pageable paginacion){
        //  return repository.findAll(paginacion).map(DatosMedicoDTO::new);

        return ResponseEntity.ok(repository.findByMotivoCancelamientoIsNull(paginacion).map(DatosDetallesConsulta::new));
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity<Void> cancelar(@RequestBody @Valid DatosCancelamientoConsulta datos){
        consultaReserva.cancelar(datos);
        return ResponseEntity.noContent().build();
    }

}
