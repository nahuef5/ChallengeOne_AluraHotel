package services;
import java.sql.Connection;
import java.util.List;
import dtos.ReservaDTO;
import entities.Reserva;
import persistence.ReservaDAO;
/**
*
* @author Nahuel
*/

/**
 * Proporciona métodos para interactuar con objetos de tipo Reserva en la DDBB.
 * Permite la creación, actualización, eliminación y recuperación de reservas.
 */
public class ReservaService {
    private final ReservaDAO reservaDAO;
    /**
     * Crea una instancia de ReservaService que utiliza la conexión 
     * especificada para interactuar con la DDBB.
     *
     * @param connection La conexión a la base de datos.
     */
    public ReservaService(Connection connection){
        this.reservaDAO=new ReservaDAO(connection);
    }
    /**
     * Guarda una nueva reserva en la DDBB utilizando la información 
     * proporcionada en un objeto ReservaDTO.
     *
     * @param dto El objeto ReservaDTO que contiene los datos de la reserva.
     */
    public void save(ReservaDTO dto) {
        Reserva reserva = new Reserva(
                dto.getFechaEntrada(),
                dto.getFechaSalida(),
                dto.getFormaPago(),
                dto.getValor()
        );
        reservaDAO.save(reserva);
    }
    /**
     * Elimina una reserva de la DDBB utilizando su id.
     *
     * @param id: id de la reserva que se va a eliminar.
     */
    public void deleteById(Integer id){
        reservaDAO.deleteById(id);
    }
    /**
     * Actualiza una reserva existente en la DDBB utilizando su id y la información de un objeto ReservaDTO.
     *
     * @param id: id de la reserva que se va a actualizar.
     * @param dto: objeto ReservaDTO que contiene los nuevos datos de la reserva.
     */
    public void updateById(Integer id, ReservaDTO dto) {
        var fechaEntrada = dto.getFechaEntrada();
        var fechaSalida = dto.getFechaSalida();
        var valor=Calculadora.valueOfDays(fechaEntrada, fechaSalida);
        
        reservaDAO.updateById(id, fechaEntrada, fechaSalida, valor);
    }
    /**
     * Recupera todas las reservas almacenadas en la DDBB.
     *
     * @return lista de objetos Reserva que representan todas las reservas almacenadas.
     */
    public List<Reserva> findAll() {
        return reservaDAO.findAll();
    }
    public Reserva findById(Integer id){
        return reservaDAO.findById(id);
    }
    public int getLastIdReserva(){
        return reservaDAO.getLastId();
    }
}