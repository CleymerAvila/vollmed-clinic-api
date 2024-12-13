package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.application.dto.*;
import med.voll.api.application.dto.medico.ActualizarMedicoDTO;
import med.voll.api.application.dto.medico.DatosMedicosRegistro;
import med.voll.api.application.dto.medico.DatosRespuestaMedico;
import med.voll.api.domain.model.Medico;
import med.voll.api.domain.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/medicos")
@SecurityRequirement(name = "bearer-key")
public class MedicoController {

    @Autowired
    private MedicoRepository repository;


    @PostMapping
    public ResponseEntity<DatosRespuestaMedico> registrarMedico(@Valid @RequestBody DatosMedicosRegistro datosMedicosRegistro,
                                                                UriComponentsBuilder uriComponentsBuilder){
        Medico medico = repository.save(new Medico(datosMedicosRegistro));

        var datos = new DatosRespuestaMedico(medico.getId(), medico.getNombre(),
                medico.getEmail(), medico.getTelefono(),medico.getDocumento(), medico.getEspecialidad().toString(), new DatosDireccion(medico.getDireccion().getCalle(),
                medico.getDireccion().getDistrito(), medico.getDireccion().getCiudad(), medico.getDireccion().getNumero(),
                medico.getDireccion().getComplemento()));

        URI url = uriComponentsBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();

        return ResponseEntity.created(url).body(datos);
    }

    @GetMapping
    public ResponseEntity<Page<DatosRespuestaMedico>> listadoMedicos(@PageableDefault(size = 10) Pageable paginacion){
       //  return repository.findAll(paginacion).map(DatosMedicoDTO::new);

        return ResponseEntity.ok(repository.findByActivoTrue(paginacion).map(DatosRespuestaMedico::new));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DatosRespuestaMedico> actualizarMedico(@RequestBody @Valid ActualizarMedicoDTO datos){
        Medico medico = repository.getReferenceById(datos.id());

        medico.actualizarDatos(datos);

        return ResponseEntity.ok(new DatosRespuestaMedico(medico.getId(), medico.getNombre(),
                medico.getEmail(), medico.getTelefono(), medico.getDocumento(), medico.getEspecialidad().toString(),
                new DatosDireccion(medico.getDireccion().getCalle(), medico.getDireccion().getDistrito(),
                        medico.getDireccion().getCiudad(), medico.getDireccion().getNumero(), medico.getDireccion().getComplemento())));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> eliminarMedico(@PathVariable Long id){
        Medico medico = repository.getReferenceById(id);

        medico.desactivarMedico();

        return ResponseEntity.noContent().build();
    }

    @GetMapping("{id}")
    public ResponseEntity<DatosRespuestaMedico> retornaDatos(@PathVariable Long id){
        Medico medico = repository.getReferenceById(id);
        var datosMedico = new DatosRespuestaMedico(medico.getId(), medico.getNombre(),
                medico.getEmail(), medico.getTelefono(), medico.getDocumento(), medico.getEspecialidad().toString(), new DatosDireccion(medico.getDireccion().getCalle(),
                medico.getDireccion().getDistrito(), medico.getDireccion().getCiudad(), medico.getDireccion().getNumero(),
                medico.getDireccion().getComplemento()));

        return ResponseEntity.ok(datosMedico);
    }



}
