package persistence;
import java.math.BigDecimal;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import entities.Reserva;
/**
 *
 * @author Nahuel
 */
public class ReservaDAO{
    private Connection connection;
    /**
     * Constructor de la clase ReservaDAO que establece la conexion a la DDBB
     *
     * @param connection la conexion a la DDBB que se utilizara para
     * interactuar con ella
     *
     * @throws RuntimeException Si ocurre un error al configurar la conexion
     */
    public ReservaDAO(Connection connection){
        this.connection = connection;
        try{
            connection.setAutoCommit(false);
        }
        catch(SQLException e){
            throw new RuntimeException("Error en la configuracion de conexion: " + e.getMessage());
        }
    }
    /**
     * Guarda una reserva en la DDBB
     *
     * @param Objeto reserva que se va a guardar en la DDBB
     *
     * @throws RuntimeException Si ocurre un error al ejecutar la consulta en la
     * DDBB o al hacer rollback
     */
    public void save(Reserva reserva){
        try(
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO reserva "
                    + "(fechaEntrada, fechaSalida, formaPago, valor)"
                    + "VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS
            ))
        {   
            preparedStatement.setDate(1, Date.valueOf(reserva.getFechaEntrada()));
            preparedStatement.setDate(2, Date.valueOf(reserva.getFechaSalida()));
            preparedStatement.setString(3, reserva.getFormaPago());
            preparedStatement.setBigDecimal(4, reserva.getValor());
            preparedStatement.execute();
                
            try(ResultSet resultSet = preparedStatement.getGeneratedKeys()){
                while(resultSet.next()) {
                    reserva.setId(resultSet.getInt(1));
                }
                System.out.println("Se hizo commit");
                connection.commit();
            }
        }
        catch(SQLException e){
            try{
               connection.rollback();
               System.out.println("Se hizo rollback: "+e.getMessage());
            }
            catch(SQLException ex){
                System.out.println("Error al hacer rollback");
            }
            throw new RuntimeException("Error al ejecutar la consulta en la DDBB: "+e.getMessage());
        }

    }
    /**
     * Actualiza un  huesped en la DDBB
     *
     * @param id = id de la reserva a actualizar
     * @param fechaEntrada nueva fecha de entrada
     * @param fechaSalida nueva fecha de salida
     * @param valor actualizacion del valor de estadía
     * 
     * @throws RuntimeException Si ocurre un error al ejecutar la consulta en la
     * DDBB, al hacer rollback o al cerrar la conexión
     */
    public void updateById(Integer id, LocalDate fechaEntrada, LocalDate fechaSalida, BigDecimal valor){
        try(
            PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE `reserva` SET "
                + "`fechaEntrada` = ?"
                + ", `fechaSalida` = ?"
                + ", `valor` = ?"
                + " WHERE `reserva`.`id` = ?;"
            ))
        {
            preparedStatement.setDate(1, Date.valueOf(fechaEntrada));
            preparedStatement.setDate(2, Date.valueOf(fechaSalida));
            preparedStatement.setBigDecimal(3, valor);
            preparedStatement.setInt(4, id);
            int ent = preparedStatement.executeUpdate();

            if(ent <= 0)
                System.out.println("No se encontro ninguna reserva con ese id");
            else{
                System.out.println(ent + " registros actualizados");
                System.out.println("Se hizo commit");
                connection.commit();
            }
        }
        catch(SQLException e){
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
     * Elimina una reserva de la DDBB
     *
     * @param id = id de la reserva a eliminar
     *
     * @throws RuntimeException Si ocurre un error al ejecutar la consulta en la
     * DDBB, al hacer rollback o al cerrar la conexión
     */
    public void deleteById(Integer id){
        try(
            PreparedStatement preparedStatement = connection.prepareStatement(
            "DELETE FROM reserva WHERE id = ?"))
        {
            preparedStatement.setInt(1, id);
            int ent = preparedStatement.executeUpdate();
            if (ent <= 0)
                System.out.println("No se encontro ninguna reserva con ese id");
            else{
                System.out.println(ent + " reserva eliminada");
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
                System.out.println("Error al hacer rollback: " + ex.getMessage());
            }
            throw new RuntimeException("Error al ejecutar la consulta en la DDBB: " + e.getMessage(), e);
        }
    }
    /**
     * Recupera una lista de todos las reservas en la DDBB
     *
     * @return una lista de objetos Reserva representando a las reservas en la
     * DDBB
     *
     * @throws RuntimeException si ocurre un error al buscar la lista de
     * reservas o al cerrar la conexion
     */
    public List<Reserva> findAll(){
        List<Reserva> all = new ArrayList<>();
        try(
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT id, fechaEntrada, fechaSalida, formaPago, valor FROM reserva"))
        {
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()){
                    LocalDate fechaEntrada=resultSet.getDate("fechaEntrada").toLocalDate();
                    LocalDate fechaSalida=resultSet.getDate("fechaSalida").toLocalDate();
                    all.add(
                        new Reserva(
                                resultSet.getInt("id"),
                                fechaEntrada,
                                fechaSalida,
                                resultSet.getString("formaPago"),
                                resultSet.getBigDecimal("valor")
                    ));
                }
            }
        }
        catch(SQLException e){
            throw new RuntimeException("Error al buscar la lista de huespedes: "+e.getMessage(),e);
        }
        return all;
    }
    /**
     * obtiene una reserva por id
     *
     * @param id = id de la reserva que se desea obtener
     * @return la reserva correspondiente al id, o null si no se encuentra
     * @throws RuntimeException si ocurre un error al ejecutar la consulta en la
     * DDBB
     */
    public Reserva findById(Integer id){
        Reserva reserva=null;
        try(
            PreparedStatement preparedStatement = connection.prepareStatement(
            "SELECT id, fechaEntrada, fechaSalida, formaPago, valor FROM reserva WHERE id=?"
            ))
        {
            preparedStatement.setInt(1, id);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if(resultSet.next()){
                    LocalDate fechaEntrada = resultSet.getDate("fechaEntrada").toLocalDate();
                    LocalDate fechaSalida = resultSet.getDate("fechaSalida").toLocalDate();
                    reserva=new Reserva(
                            resultSet.getInt("id"),
                            fechaEntrada,
                            fechaSalida,
                            resultSet.getString("formaPago"),
                            resultSet.getBigDecimal("valor")
                    );
                }
            }
        }
        catch(SQLException e){
            throw new RuntimeException("error al ejecutar la consulta en la DDBB: "+e.getMessage());
        }
        return reserva;
    }
    /**
     * Obtiene el id de la ultima reserva guardada en la DDBB
     *
     * @return el id de la ultima reserva guardada
     * @throws RuntimeException Si ocurre un error al ejecutar la consulta en la
     * DDBB
     */
    public int getLastId(){
        int lastId=-1;
        try(PreparedStatement preparedStatement=connection.prepareStatement(
        "SELECT MAX(id) FROM reserva")){
            try(ResultSet resultSet=preparedStatement.executeQuery()){
                if(resultSet.next())
                    lastId=resultSet.getInt(1);
            }
        }
        catch(SQLException e){
            throw new RuntimeException("Error al obtener el ultimo id guardado: "+ e.getMessage());
        }
        return lastId;
    }
}