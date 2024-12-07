package med.voll.api.service.consulta;

import med.voll.api.application.dto.consulta.DatosCancelamientoConsulta;
import med.voll.api.application.dto.consulta.DatosDetallesConsulta;
import med.voll.api.application.dto.consulta.DatosReservaConsulta;
import med.voll.api.domain.model.Consulta;
import med.voll.api.domain.model.Medico;
import med.voll.api.domain.model.Paciente;
import med.voll.api.domain.repository.ConsultaRepository;
import med.voll.api.domain.repository.MedicoRepository;
import med.voll.api.domain.repository.PacienteRepository;
import med.voll.api.infrastructure.errors.exception.ValidacionException;
import med.voll.api.service.consulta.validaciones.ValidadorCancelamientoConsulta;
import med.voll.api.service.consulta.validaciones.ValidadorConsultas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConsultaReservaService {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private List<ValidadorConsultas> validadores;

    @Autowired
    private List<ValidadorCancelamientoConsulta> validadoresCancelConsulta;


    public DatosDetallesConsulta reservar(DatosReservaConsulta datos){


        Optional<Paciente> pacienteBuscado = pacienteRepository.findById(datos.idPaciente());

        if (!pacienteBuscado.isPresent()){
            throw new ValidacionException("No existe un paciente con el id informado");
        }

        // VALIDACIONES
        validadores.forEach(validador -> validador.validar(datos));

        var medico = elegirMedico(datos);
        var paciente = pacienteRepository.findById(datos.idPaciente()).get();
        var consulta = new Consulta(null, medico, paciente, datos.fecha(), null);
        consultaRepository.save(consulta);

        return new DatosDetallesConsulta(consulta);
    }

    public void cancelar(DatosCancelamientoConsulta datos){
        if (!consultaRepository.existsById(datos.idConsulta())){
            throw new ValidacionException("Id de la consulta informado no existe!");
        }

        validadoresCancelConsulta.forEach(v -> v.validar(datos));

        var consulta = consultaRepository.getReferenceById(datos.idConsulta());
        consulta.cancelar(datos.motivo());
    }

    private Medico elegirMedico(DatosReservaConsulta datos){
        if (datos.idMedico() != null){
            return medicoRepository.getReferenceById(datos.idMedico());
        }

        if (datos.especialidad()==null){
            throw new ValidacionException("Es necesario elegir la especialidad cuando no se elige un medico");
        }

        return medicoRepository.elegirMedicoAleatorioDisponible(datos.especialidad(), datos.fecha());
    }


}
