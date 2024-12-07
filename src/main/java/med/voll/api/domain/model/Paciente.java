package med.voll.api.domain.model;

import lombok.*;
import jakarta.persistence.*;
import med.voll.api.application.dto.paciente.ActualizarPacienteDTO;
import med.voll.api.application.dto.paciente.DatosRegistroPaciente;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Paciente")
@Table(name = "pacientes")
public class Paciente {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String email;
    private String documento;
    private String telefono;
    private String codigoPostal;
    private String provincia;
    private Boolean activo;

    @Embedded
    private Direccion direccion;

    public Paciente(DatosRegistroPaciente datos) {
        this.activo = true;
        this.nombre = datos.nombre();
        this.email = datos.email();
        this.telefono = datos.telefono();
        this.codigoPostal = datos.codigo_postal();
        this.provincia = datos.provincia();
        this.documento = datos.documento_identidad();
        this.direccion = new Direccion(datos.direccion());
    }

    public void actualizarInformacion(ActualizarPacienteDTO datos){
        if (datos.nombre() != null){
            this.nombre = datos.nombre();
        }

        if (datos.telefono() != null){
            this.telefono = datos.telefono();
        }

        if (datos.direccion() != null){
            direccion.actualizarDatos(datos.direccion());
        }
    }

    public void inactivar(){
        this.activo = false;
    }

}