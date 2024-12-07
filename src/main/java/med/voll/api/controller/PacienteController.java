package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.application.dto.*;
import med.voll.api.application.dto.paciente.*;
import med.voll.api.domain.model.Paciente;
import med.voll.api.domain.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("pacientes")
@SecurityRequirement(name = "bearer-key")
public class PacienteController {

    @Autowired
    private PacienteRepository repository;


    @PostMapping
    public ResponseEntity<DatosRegistroPaciente> registrarPaciente(@Valid @RequestBody DatosRegistroPaciente datos,
                                                                   UriComponentsBuilder uriComponentsBuilder){
        Paciente paciente = repository.save(new Paciente(datos));

        DatosRegistroPaciente datosRegistroPaciente = new DatosRegistroPaciente(paciente.getNombre(),
                paciente.getEmail(), paciente.getDocumento(), paciente.getTelefono(),paciente.getCodigoPostal()
                , paciente.getProvincia(), new DatosDireccion(paciente.getDireccion().getCalle(),
                paciente.getDireccion().getDistrito(), paciente.getDireccion().getCiudad(), paciente.getDireccion().getNumero(),
                paciente.getDireccion().getComplemento()));

        URI url = uriComponentsBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();

        return ResponseEntity.created(url).body(datosRegistroPaciente);
    }


    @GetMapping
    public ResponseEntity<Page<DatosListaPaciente>> listar(@PageableDefault(page = 0, size = 10, sort = {"nombre"}) Pageable paginacion) {
        return ResponseEntity.ok(repository.findAll(paginacion).map(DatosListaPaciente::new));
    }


    @PutMapping
    @Transactional
    public ResponseEntity<DatosRespuestaPaciente>  actualizarPaciente(@RequestBody @Valid ActualizarPacienteDTO datos){
        Paciente paciente = repository.getReferenceById(datos.id());

        paciente.actualizarInformacion(datos);

        return ResponseEntity.ok(new DatosRespuestaPaciente(paciente.getId(), paciente.getNombre(), paciente.getTelefono(),
                paciente.getEmail(), paciente.getDocumento(), paciente.getProvincia()));
    }

    @DeleteMapping("{id}")
    @Transactional
    public ResponseEntity<Void> eliminarPaciente(@PathVariable Long id){
        Paciente paciente = repository.getReferenceById(id);

        paciente.inactivar();

        return ResponseEntity.noContent().build();
    }

    @GetMapping("{id}")
    public ResponseEntity<DatosDetalladosPaciente> obtenerDetallePaciente(@PathVariable Long id){

        Paciente paciente = repository.getReferenceById(id);

        var datos = new DatosDetalladosPaciente(paciente);

        return ResponseEntity.ok(datos);
    }
}
