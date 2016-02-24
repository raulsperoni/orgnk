package mgcoders.uy.model;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by raul on 16/02/16.
 */
@Entity
public class Persona implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    private boolean enabled = false;
    private String password;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;
    @Pattern(regexp = "^(([0-9]){1}.)?([0-9]){3}.([0-9]){3}-([0-9]){1}$", message = "El formato de la cédula debe ser: 1.111.111-1")
    @Column(unique = true)
    private String ci;
    @Pattern(regexp = "^(([A-Z]){3}([0-9]){4,5})?$", message = "El formato de la credencial debe ser: AAA11111")
    private String cc;
    @NotNull(message = "La fecha de nacimiento no puede estar vacía")
    @Past(message = "La fecha debe ser anterior a hoy")
    private Date fecha_nacimiento;
    @Pattern(regexp = "^([0-9]{3})?([0-9]){8}$", message = "El formato del teléfono debe ser 59899111111")
    private String telefono_1;
    @Pattern(regexp = "^(([0-9]{3})?([0-9]){9})?$", message = "El formato del teléfono debe ser 59899111111")
    private String telefono_2;
    @NotBlank(message = "Necesitamos un notifications para contactarte")
    @Email(message = "El mail es incorrecto")
    @Column(unique = true)
    private String email;

    private String direccion;
    @NotNull(message = "El departamento no puede estar vacío")
    @ManyToOne
    private Departamento departamento;
    @ManyToOne
    private Localidad localidad;
    private String barrio;

    private String temas_interes;

    @NotNull(message = "Debe elegir un monto")
    private int monto_aporte;
    @Enumerated(EnumType.STRING)
    private Frecuencia frecuencia_aporte;


    public long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public Date getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(Date fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public String getTelefono_1() {
        return telefono_1;
    }

    public void setTelefono_1(String telefono_1) {
        this.telefono_1 = telefono_1;
    }

    public String getTelefono_2() {
        return telefono_2;
    }

    public void setTelefono_2(String telefono_2) {
        this.telefono_2 = telefono_2;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public Localidad getLocalidad() {
        return localidad;
    }

    public void setLocalidad(Localidad localidad) {
        this.localidad = localidad;
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public String getTemas_interes() {
        return temas_interes;
    }

    public void setTemas_interes(String temas_interes) {
        this.temas_interes = temas_interes;
    }

    public int getMonto_aporte() {
        return monto_aporte;
    }

    public void setMonto_aporte(int monto_aporte) {
        this.monto_aporte = monto_aporte;
    }

    public Frecuencia getFrecuencia_aporte() {
        return frecuencia_aporte;
    }

    public void setFrecuencia_aporte(Frecuencia frecuencia_aporte) {
        this.frecuencia_aporte = frecuencia_aporte;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Persona{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", ci='" + ci + '\'' +
                ", cc='" + cc + '\'' +
                ", fecha_nacimiento=" + fecha_nacimiento +
                ", telefono_1='" + telefono_1 + '\'' +
                ", telefono_2='" + telefono_2 + '\'' +
                ", notifications='" + email + '\'' +
                ", direccion='" + direccion + '\'' +
                ", departamento=" + departamento +
                ", localidad=" + localidad +
                ", barrio='" + barrio + '\'' +
                ", temas_interes='" + temas_interes + '\'' +
                ", monto_aporte=" + monto_aporte +
                ", frecuencia_aporte=" + frecuencia_aporte +
                '}';
    }
}
