package services;
import java.math.BigDecimal;
import java.time.*;
/**
*
* @author Nahuel
*/
public class Calculadora{
	//Precio estadía
    public static final BigDecimal PRICE=BigDecimal.valueOf(20000);
    /**
     * Calcula el valor total basado en el número de días entre dos fechas y el precio por día definido en la constante PRICE.
     *
     * @param date1 La primera fecha.
     * @param date2 La segunda fecha.
     * @return El valor total calculado.
     */
    public static BigDecimal valueOfDays(LocalDate date1, LocalDate date2){
        Duration duration = Duration.between(
                date1.atStartOfDay(), date2.atStartOfDay()
        );
        var days = duration.toDays();
        return PRICE.multiply(BigDecimal.valueOf(days));
    }
}