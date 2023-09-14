package com.alura.hotelalura.models.dtos;
import com.alura.hotelalura.controllers.services.utils.Calculadora;
import java.math.BigDecimal;
import java.time.LocalDate;
/**
 *
 * @author Nahuel
 */
public class ReservaDTO{
    //ATTRIBUTES
    private LocalDate fechaEntrada;
    private LocalDate fechaSalida;
    private String formaPago;
    private BigDecimal valor;
    //CONSTRUCTORS
    public ReservaDTO(){}
    //create
    public ReservaDTO(LocalDate fechaEntrada, LocalDate fechaSalida, String formaPago) {
        if(isValidFechaEntrada(fechaEntrada))
            this.fechaEntrada = fechaEntrada;
        if(isValidFechaSalida(fechaSalida, fechaEntrada))
            this.fechaSalida = fechaSalida;
        this.formaPago=formaPago;
        this.valor=Calculadora.valueOfDays(fechaEntrada, fechaSalida);
    }
    //update
    public ReservaDTO(LocalDate fechaEntrada, LocalDate fechaSalida) {
        if(isValidFechaEntrada(fechaEntrada))
            this.fechaEntrada = fechaEntrada;
        if(isValidFechaSalida(fechaSalida, fechaEntrada))
            this.fechaSalida = fechaSalida;
    }
    //METHODS
    //VALIDATIONS
    private boolean isValidFechaEntrada(LocalDate fechaEntrada){
        LocalDate entradaMaxima=LocalDate.now().plusYears(5);
        if( fechaEntrada==null
                ||
            fechaEntrada.isBefore(LocalDate.now()) || fechaEntrada.isAfter(entradaMaxima))
            throw new IllegalArgumentException("Fecha de entrada debe ser mayor o igual a la actual y no puede exceder los 5 años.");
        else return true;
    }
    private boolean isValidFechaSalida(LocalDate fechaSalida, LocalDate fechaEntrada){
        if(
            fechaSalida==null
            ||
            fechaSalida.isBefore(fechaEntrada)
            ||
            fechaSalida.equals(fechaEntrada)
            ||
            fechaSalida.isAfter(fechaEntrada.plusDays(30)))
            throw new IllegalArgumentException(
            "Fecha de salida debe ser posterior a la fecha de entrada y no puede exceder los 30 dias de la fecha de entrada."
            );
        else return true;
    }
    //GETTERS AND SETTERS
    public BigDecimal getValor() {
        return valor;
    }
    public LocalDate getFechaEntrada() {
        return fechaEntrada;
    }
    public void setFechaEntrada(LocalDate fechaEntrada) {
        if(isValidFechaEntrada(fechaEntrada))
            this.fechaEntrada = fechaEntrada;
    }
    public LocalDate getFechaSalida() {
        return fechaSalida;
    }
    public void setFechaSalida(LocalDate fechaSalida) {
        if(isValidFechaSalida(fechaSalida, this.fechaEntrada))
            this.fechaSalida = fechaSalida;
    }
    public String getFormaPago() {
        return formaPago;
    }
    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }
}