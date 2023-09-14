package entities;
import java.math.BigDecimal;
import java.time.*;
/**
 *
 * @author Nahuel
 */
public class Reserva{
    private Integer id;
    private LocalDate fechaEntrada;
    private LocalDate fechaSalida;
    private BigDecimal valor;
    private String formaPago;
    
    public Reserva(){}
   public Reserva(LocalDate fechaEntrada, LocalDate fechaSalida, String formaPago, BigDecimal valor) {
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        this.formaPago=formaPago;
        this.valor = valor;
    }
    public Reserva(Integer id, LocalDate fechaEntrada, LocalDate fechaSalida, String formaPago, BigDecimal valor) {
        this.id = id;
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        this.formaPago=formaPago;
        this.valor = valor;
    }
    public String getFormaPago() {
        return formaPago;
    }
    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public LocalDate getFechaEntrada() {
        return fechaEntrada;
    }
    public void setFechaEntrada(LocalDate fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }
    public LocalDate getFechaSalida() {
        return fechaSalida;
    }
    public void setFechaSalida(LocalDate fechaSalida) {
        this.fechaSalida = fechaSalida;
    }
    public BigDecimal getValor() {
        return valor;
    }
    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
    @Override
    public String toString() {
        return "Reserva{"
                +", id=" + id
                +", fechaEntrada=" + fechaEntrada
                +", fechaSalida=" + fechaSalida
                +", valor=" + valor
                +", formaPago=" + formaPago
                +'}';
    }
}