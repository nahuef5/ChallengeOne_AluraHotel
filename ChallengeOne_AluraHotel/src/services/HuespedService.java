package services;
import java.sql.Connection;
import java.util.List;
import dtos.HuespedDTO;
import entities.Huesped;
import persistence.*;
/**
 *
 * @author Nahuel
 */

/**
 * Proporciona métodos para interactuar con objetos de tipo Huesped en la DDBB.
 * Permite la creación, actualización, eliminación y recuperación de reservas.
 */
public class HuespedService {
    private final HuespedDAO huespedDAO;
    private final ReservaDAO reservaDAO;
    /**
     * Crea una instancia de la clase que utiliza la conexión 
     * especificada para interactuar con la DDBB.
     *
     * @param connection: la conexión a la DDBB.
     */
    public HuespedService(Connection connection){
        this.huespedDAO = new HuespedDAO(connection);
        this.reservaDAO = new ReservaDAO(connection);
    }
    /**
     * Obtiene el último id de reserva almacenado en la DDBB.
     *
     * @return el último id de reserva almacenado.
     */
    public int getIdReserva(){
        return reservaDAO.getLastId();
    }
    /**
     * Guarda un nuevo huésped en la DDBB utilizando la información proporcionada en un objeto HuespedDTO.
     *
     * @param dto el objeto HuespedDTO que contiene los datos del huésped.
     */
    public void save(HuespedDTO dto){
        int lastIdReserva=getIdReserva();
        Huesped huesped = new Huesped(
                dto.getNombre(),
                dto.getApellido(),
                dto.getFechaNacimiento(),
                dto.getNacionalidad(),
                dto.getTelefono()
        );
        huesped.setNumeroReserva(lastIdReserva);
        huespedDAO.save(huesped);
    }
    /**
     * Elimina un huésped de la DDBB utilizando su id . 
     * También elimina la reserva asociada a ese huésped.
     *
     * @param id el id del huésped que se va a eliminar.
     */
    public void deleteById(Integer id){
        int numReserva=findById(id).getNumeroReserva();
        reservaDAO.deleteById(numReserva);
        huespedDAO.delete(id);
    }
    /**
     * Actualiza un huésped existente en la DDBB utilizando su 
     * id  y la información proporcionada en un objeto HuespedDTO.
     *
     * @param id el id del huésped que se va a actualizar.
     * @param dto el objeto HuespedDTO que contiene los nuevos datos del huésped.
     */
    public void updateById(Integer id, HuespedDTO dto){
        var fechaNacimiento=dto.getFechaNacimiento();
        var nacionalidad = dto.getNacionalidad();
        var telefono = dto.getTelefono();
        huespedDAO.update(id, fechaNacimiento, nacionalidad, telefono);
    }
    /**
     * Recupera todas los huespedes almacenadas en la DDBB.
     *
     * @return lista de objetos Huesped que representan todas los huespedes almacenadas.
     */
    public List<Huesped> findAll(){
        return huespedDAO.findAll();
    }
    public Huesped findById(Integer id){
        return huespedDAO.findById(id);
    }
    public Huesped findByNumeroReserva(Integer numeroReserva){
        return huespedDAO.findByNumeroReserva(numeroReserva);
    }
    /**
     * Genera una lista de huespedes que tengan cierto nombre y apellido
     * 
     * @param nombre = nombre del huesped
     * @param apellido = apellido del huesped
     * @return una lista de objetos Huesped
     *
     * @throws IllegalArgumentException si el formato de esas cadenas no es correcto
     */
    public List<Huesped>findByNombreAndApellido(String nombre, String apellido){
        if(!isValidNombreApellido(nombre) || !isValidNombreApellido(apellido))
            throw new IllegalArgumentException("formato de nombre y apellido no valido: ");
        return huespedDAO.findByNombreAndApellido(nombre, apellido);
    }
    /**
     * Corrobora el cumplimiento de patron de string
     * 
     * @param string = nombre del huesped
     * @return boolean si el string no es nulo y cumple con la expresion regular
     */
    private boolean isValidNombreApellido(String string){
        var regex ="^[A-Z][a-z]{2,14}$";
        return string != null && string.matches(regex);
    }
}