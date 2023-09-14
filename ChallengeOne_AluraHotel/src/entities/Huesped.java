package entities;
import java.time.LocalDate;
/**
 *
 * @author Nahuel
 */
public class Huesped{
    
    private Integer id;
    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;
    private String nacionalidad;
    private String telefono;
    private Integer numeroReserva;
    
    public Huesped(){}
    public Huesped(String nombre, String apellido, LocalDate fechaNacimiento, String nacionalidad, String telefono) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.nacionalidad = nacionalidad;
        this.telefono = telefono;
    }
    public Huesped
        (Integer id, String nombre, String apellido, LocalDate fechaNacimiento, String nacionalidad, String telefono, Integer numeroReserva) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.nacionalidad = nacionalidad;
        this.telefono = telefono;
        this.numeroReserva = numeroReserva;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getApellido() {
        return apellido;
    }
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }
    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
    public String getNacionalidad() {
        return nacionalidad;
    }
    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }
    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public Integer getNumeroReserva() {
        return numeroReserva;
    }
    public void setNumeroReserva(Integer numeroReserva) {
        this.numeroReserva = numeroReserva;
    }

    @Override
    public String toString() {
        return "Huesped{" + "id=" + id +
                ", nombre=" + nombre +
                ", apellido=" + apellido +
                ", fechaNacimiento=" + fechaNacimiento +
                ", nacionalidad=" + nacionalidad +
                ", telefono=" + telefono +
                ", numeroReserva=" + numeroReserva +
                '}';
    }
}