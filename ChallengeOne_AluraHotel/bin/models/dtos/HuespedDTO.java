package com.alura.hotelalura.models.dtos;
import java.time.LocalDate;
/**
 *
 * @author Nahuel
 */
public class HuespedDTO {
    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;
    private String nacionalidad;
    private String telefono;
    //CONSTRUCTORS
    public HuespedDTO(){}
    //create
    public HuespedDTO(String nombre, String apellido, LocalDate fechaNacimiento, String nacionalidad, String telefono){
        if(isValidName(nombre))this.nombre=nombre;
        if(isValidName(apellido))this.apellido=apellido;
        if(isValidFechaNacimiento(fechaNacimiento))this.fechaNacimiento = fechaNacimiento;
        this.nacionalidad = nacionalidad;
        if(isValidTelefono(telefono)) this.telefono = telefono;
    }
    //update
    public HuespedDTO(LocalDate fechaNacimiento, String nacionalidad, String telefono) {
        this.fechaNacimiento = fechaNacimiento;
        this.nacionalidad = nacionalidad;
        this.telefono = telefono;
    }
    //METHODS
    //VALIDATIONS
    private boolean isValidFechaNacimiento(LocalDate fechaNacimiento){
        LocalDate now=LocalDate.now();
        var minDate=now.minusYears(18);
        var maxDate=now.minusYears(99);
        if(
            fechaNacimiento==null
                ||
            (fechaNacimiento.isBefore(maxDate) || fechaNacimiento.isAfter(minDate)))
            throw new IllegalArgumentException("fecha de nacimiento no valida. Debe ser mayor de edad y no puede superar los 99 años."
            );
        else return true;
    }
    private boolean isValidTelefono(String telefono){
        String regex="^[0-9]{3}-[0-9]{7}$";
        if(telefono==null || !telefono.matches(regex))
            throw new IllegalArgumentException("formato de telefono no valido. Luego de los primeros 3 numeros no se olvide colocar el guion.");
        else return true;
    }
    private boolean isValidName(String name){
        String regex="^[A-Z][a-z]{2,14}$";
        if(name==null || !name.matches(regex)) 
            throw new IllegalArgumentException(
                    "debe contener minimo 3 caracteres y maximo 15. Debe no debe tener caracteres especiales ni numeros.");
        else return true;
    }
    //GETTERS AND SETTERS
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        if(isValidName(nombre))this.nombre=nombre;
    }
    public String getApellido() {
        return apellido;
    }
    public void setApellido(String apellido) {
        if(isValidName(apellido))this.apellido=apellido;
    }
    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }
    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        if(isValidFechaNacimiento(fechaNacimiento))this.fechaNacimiento = fechaNacimiento;
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
        if(isValidTelefono(telefono)) this.telefono = telefono;
    }
}