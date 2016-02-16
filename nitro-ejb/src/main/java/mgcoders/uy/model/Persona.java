package mgcoders.uy.model;

import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * Created by raul on 16/02/16.
 */
@Entity
public class Persona {

    @Id
    @GeneratedValue
    private long id;

    @NotNull
    private String nombre;
    @Pattern(regexp = "^(([0-9]){1}.)?([0-9]){3}.([0-9]){3}-([0-9]){1}$")
    private String ci;
    @Pattern(regexp = "^([A-Z]){3}([0-9]){4,5}$")
    private String cc;
    @Past
    private Date fecha_nacimiento;
    @Pattern(regexp = "^([0-9]{3})?([0-9]){8}$")
    private String telefono_1;
    @Pattern(regexp = "^([0-9]{3})?([0-9]){8}$")
    private String telefono_2;
    @Email
    private String email;

    private String direccion;
    @ManyToOne
    private Departamento departamento;
    @ManyToOne
    private Localidad localidad;
    private String barrio;

    private String temas_interes;
    @Digits(integer = 6, fraction = 0)
    private int monto_aporte;
    @Enumerated(EnumType.STRING)
    private Frecuencia frecuencia_aporte;

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
}