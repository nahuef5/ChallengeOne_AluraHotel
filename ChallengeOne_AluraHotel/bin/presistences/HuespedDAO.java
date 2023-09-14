package com.alura.hotelalura.presistences;
import com.alura.hotelalura.models.entities.Huesped;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
/**
 *
 * @author Nahuel
 */
public class HuespedDAO{
    //ATTRIBUTE
    private Connection connection;
    /**
     * constructor de la clase HuespedDAO que establece la conexion a la base de
     * datos
     *
     * @param connection la conexion a la DDBB que se utilizara para
     * interactuar con ella
     *
     * @throws RuntimeException si ocurre un error al configurar la conexion
     */
    public HuespedDAO(Connection connection) {
        this.connection = connection;
        try {
            connection.setAutoCommit(false);
        }
        catch(SQLException e){
            System.out.println("error conexion");
            throw new RuntimeException("error en la configuracion de conexion: "+e.getMessage());
        }
    }
    /**
     * guarda un huesped en la DDBB
     *
     * @param huesped el objeto Huesped que se va a guardar en la DDBB
     *
     * @throws RuntimeException si ocurre un error al ejecutar la consulta en la
     * DDBB o al hacer rollback
     */
    public void save(Huesped huesped){
        try(
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO huesped "
                    + "(nombre, apellido, fechaNacimiento, nacionalidad, telefono, numeroReserva)"
                    + " VALUES (?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS))
        {
            preparedStatement.setString(1, huesped.getNombre());
            preparedStatement.setString(2, huesped.getApellido());
            preparedStatement.setDate(3, Date.valueOf(huesped.getFechaNacimiento()));
            preparedStatement.setString(4, huesped.getNacionalidad());
            preparedStatement.setString(5, huesped.getTelefono());
            preparedStatement.setInt(6, huesped.getNumeroReserva());
            preparedStatement.execute();

            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()){
                while(resultSet.next()){
                    huesped.setId(resultSet.getInt(1));
                }
                System.out.println("Se hizo commit");
                connection.commit();
            }
        }
        catch(Exception e){
            try{
               connection.rollback();
               System.out.println("Se hizo rollback: "+e.getMessage());
            }
            catch(SQLException ex){
                System.out.println("error al hacer rollback");
            }
            throw new RuntimeException("error al ejecutar la consulta en la DDBB: "+e.getMessage());
        }

    }
    /**
     * actualiza un  huesped en la DDBB
     *
     * @param id = id del huesped a actualizar
     * @param fechaNacimiento corregir la fecha de nacimiento del huesped
     * @param nacionalidad la nueva nacionalidad del huesped
     * @param telefono el nuevo telefono del huesped
     
     */
    public void update(Integer id, LocalDate fechaNacimiento, String nacionalidad ,String telefono){
        try(
            PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE `huesped` SET "
                + "`fechaNacimiento` = ?, "
                + "`nacionalidad` = ?, "
                + "`telefono` = ? "
                + "WHERE `huesped`.`id` = ?;"))
        {
            preparedStatement.setDate(1, Date.valueOf(fechaNacimiento));
            preparedStatement.setString(2, nacionalidad);
            preparedStatement.setString(3, telefono);
            preparedStatement.setInt(4, id);
            int rowsUpdated = preparedStatement.executeUpdate();
            if(rowsUpdated <= 0)
                System.out.println("No se encontró ningún huesped con ese id.");
            else{
                System.out.println(rowsUpdated + " registro actualizado correctamente.");
                System.out.println("Se hizo commit");
                connection.commit();
            }
        }
        catch(SQLException e){
            try{
                connection.rollback();
                System.out.println("Se hizo rollback debido a un error: " + e.getMessage());
            }
            catch(SQLException ex){
                System.out.println("error al hacer rollback: " + ex.getMessage());
            }
            throw new RuntimeException("error al ejecutar la consulta en la DDBB: " + e.getMessage(), e);
        }

    }
    /**
     * elimina un huesped de la DDBB
     *
     * @param id = id del huesped a eliminar
     *
     * @throws RuntimeException Si ocurre un error al ejecutar la consulta en la
     * DDBB, al hacer rollback o al cerrar la conexión
     */
    public void delete(Integer id){
        try(
            PreparedStatement preparedStatement = connection.prepareStatement(
            "DELETE FROM huesped WHERE id = ?"))
        {
            preparedStatement.setInt(1, id);
            int ent = preparedStatement.executeUpdate();
            if(ent <= 0)
                System.out.println("No se encontro ningun huesped con ese id");
            else{
                System.out.println(ent + " Huesped eliminado");
                System.out.println("Se hizo commit");
                connection.commit();
            }
        }
        catch(SQLException e){
            try{
                connection.rollback();
                System.out.println("Se hizo rollback debido a un error: " + e.getMessage());
            }
            catch(SQLException ex){
                System.out.println("error al hacer rollback: " + ex.getMessage());
            }
            throw new RuntimeException("error al ejecutar la consulta en la DDBB: " + e.getMessage(), e);
        }

    }
    /**
     * recupera una lista de todos los huespedes en la DDBB
     *
     * @return una lista de objetos Huesped representando a los huespedes en la
     * DDBB
     *
     * @throws RuntimeException si ocurre un error al buscar la lista de
     * huespedes o al cerrar la conexion
     */
    public List<Huesped> findAll(){
        List<Huesped> all = new ArrayList<>();
        try(
            PreparedStatement preparedStatement = connection.prepareStatement(
            "SELECT id, nombre, apellido, fechaNacimiento, nacionalidad, telefono, numeroReserva FROM huesped"))
        {

            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while(resultSet.next()){
                    LocalDate fechaNacimiento=resultSet.getDate("fechaNacimiento").toLocalDate();
                    all.add(
                    	new Huesped(
                            resultSet.getInt("id"),
                            resultSet.getString("nombre"),
                            resultSet.getString("apellido"),
                            fechaNacimiento,
                            resultSet.getString("nacionalidad"),
                            resultSet.getString("telefono"),
                            resultSet.getInt("numeroReserva")
                    ));
                }
            }
        }
        catch(SQLException e){
            throw new RuntimeException("error al buscar la lista de huespedes: "+e.getMessage(),e);
        }

        return all;
    }
    /**
     * obtiene un huesped por id
     *
     * @param id = id del huesped que se desea obtener
     * @return el huesped correspondiente al id, o null si no se encuentra
     * @throws RuntimeException si ocurre un error al ejecutar la consulta en la
     * DDBB
     */
    public Huesped findById(Integer id){
        Huesped huesped=null;
        try(
            PreparedStatement preparedStatement = connection.prepareStatement(
            "SELECT id, nombre, apellido, fechaNacimiento, nacionalidad, telefono, numeroReserva FROM huesped WHERE id=?"
            ))
        {
            preparedStatement.setInt(1, id);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if(resultSet.next()){
                    LocalDate fechaNacimiento = resultSet.getDate("fechaNacimiento").toLocalDate();
                    huesped=new Huesped(
                            resultSet.getInt("id"),
                            resultSet.getString("nombre"),
                            resultSet.getString("apellido"),
                            fechaNacimiento,
                            resultSet.getString("nacionalidad"),
                            resultSet.getString("telefono"),
                            resultSet.getInt("numeroReserva")
                    );
                }
            }
        }
        catch(SQLException e){
            throw new RuntimeException("error al ejecutar la consulta en la DDBB: "+e.getMessage());
        }
        return huesped;
    }
    /**
     * recupera una lista de los huespedes que tengan cierto nombre y apellido
     * en la DDBB
     * 
     * @param nombre = nombre del huesped que se desea guardar en la lista
     * @param apellido = apellido del huesped que se desea guardar en la lista
     * @return una lista de objetos Huesped representando a esos huespedes en la
     * DDBB
     *
     * @throws RuntimeException si ocurre un error al buscar la lista de
     * huespedes o al cerrar la conexion
     */
    public List<Huesped> findByNombreAndApellido(String nombre, String apellido){
        List<Huesped> byNombreAndApellido=new ArrayList<>();
        try(
            PreparedStatement preparedStatement = connection.prepareStatement(
            "SELECT id, nombre, apellido, fechaNacimiento, nacionalidad, telefono, numeroReserva FROM huesped WHERE nombre=? AND apellido=?"
            ))
        {
            preparedStatement.setString(1, nombre);
            preparedStatement.setString(2, apellido);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while(resultSet.next()){
                    LocalDate fechaNacimiento=resultSet.getDate("fechaNacimiento").toLocalDate();
                    byNombreAndApellido.add(
                    	new Huesped(
                            resultSet.getInt("id"),
                            resultSet.getString("nombre"),
                            resultSet.getString("apellido"),
                            fechaNacimiento,
                            resultSet.getString("nacionalidad"),
                            resultSet.getString("telefono"),
                            resultSet.getInt("numeroReserva")
                    ));
                }
            }
        }
        catch(SQLException e){
            throw new RuntimeException("error al ejecutar la consulta en la DDBB: "+e.getMessage());
        }

        return byNombreAndApellido;
    }
    /**
     * obtiene un huesped por id
     *
     * @param numeroReserva = numeroReserva del huesped que se desea obtener
     * @return el huesped correspondiente al numeroReserva, o null si no se encuentra
     * @throws RuntimeException si ocurre un error al ejecutar la consulta en la
     * DDBB
     */
    public Huesped findByNumeroReserva(Integer numeroReserva){
        Huesped huesped=null;
        try(
            PreparedStatement preparedStatement = connection.prepareStatement(
            "SELECT id, nombre, apellido, fechaNacimiento, nacionalidad, telefono, numeroReserva FROM huesped WHERE numeroReserva=?"
            ))
        {
            preparedStatement.setInt(1, numeroReserva);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if(resultSet.next()){
                    LocalDate fechaNacimiento = resultSet.getDate("fechaNacimiento").toLocalDate();
                    huesped=new Huesped(
                            resultSet.getInt("id"),
                            resultSet.getString("nombre"),
                            resultSet.getString("apellido"),
                            fechaNacimiento,
                            resultSet.getString("nacionalidad"),
                            resultSet.getString("telefono"),
                            resultSet.getInt("numeroReserva")
                    );
                }
            }
        }
        catch(SQLException e){
            throw new RuntimeException("error al ejecutar la consulta en la DDBB: "+e.getMessage());
        }

        return huesped;
    }
    /**
     * elimina un huesped de la DDBB
     *
     * @param numeroReserva = numeroReserva del objeto de la entidad Huesped a eliminar
     *
     * @throws RuntimeException Si ocurre un error al ejecutar la consulta en la
     * DDBB, al hacer rollback o al cerrar la conexión
     */
    public void deleteByNumeroReserva(Integer numeroReserva){
        try(
            PreparedStatement preparedStatement = connection.prepareStatement(
            "DELETE FROM huesped WHERE numeroReserva = ?"))
        {
            preparedStatement.setInt(1, numeroReserva);
            int ent = preparedStatement.executeUpdate();
            if(ent <= 0)
                System.out.println("No se encontro ningun huesped con ese numero de reserva");
            else{
                System.out.println(ent + " Huesped eliminado");
                System.out.println("Se hizo commit");
                connection.commit();
            }
        }
        catch(SQLException e){
            try{
                connection.rollback();
                System.out.println("Se hizo rollback debido a un error: " + e.getMessage());
            }
            catch(SQLException ex){
                System.out.println("error al hacer rollback: " + ex.getMessage());
            }
            throw new RuntimeException("error al ejecutar la consulta en la DDBB: " + e.getMessage(), e);
        }
    }
}