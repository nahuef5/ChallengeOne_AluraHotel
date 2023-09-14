package factory;
import java.sql.*;
import javax.sql.DataSource;
import com.mchange.v2.c3p0.ComboPooledDataSource;
/**
 *
 * @author Nahuel
 */

/**
 * La clase proporciona una forma de obtener conexiones a una DDBB utilizando un DataSource.
 * Implementa la interfaz AutoCloseable para gestionar la liberación de recursos.
 */
public class ConnectionFactory implements AutoCloseable{
    
    private final String url = "jdbc:mysql://localhost:3306/hotelAlura?useTimeZone=true&serverTimeZone=UTC";
    private final String root = "username";
    private final String password = "password";
    private final DataSource dataSource;
    /**
     * Constructor que inicializa el DataSource con la configuración predeterminada.
     */
    public ConnectionFactory() {
    	ComboPooledDataSource pooledDataSource = new ComboPooledDataSource();
        pooledDataSource.setJdbcUrl(url);
        pooledDataSource.setUser(root);
        pooledDataSource.setPassword(password);
        pooledDataSource.setMaxPoolSize(5);
        this.dataSource = pooledDataSource;
    }
    /**
     * Obtiene una conexión a la DDBB.
     *
     * @return Una conexión a la DDBB.
     * @throws RuntimeException Si ocurre un error al obtener la conexión.
     */
    public Connection connection(){
        try{
            return this.dataSource.getConnection();
        }
        catch(SQLException e){
            throw new RuntimeException(e.getMessage());
        }
    }
    /**
     * Implementación del método close de la interfaz AutoCloseable.
     * Cierra el DataSource si es una instancia de ComboPooledDataSource.
     *
     * @throws Exception Si ocurre un error al cerrar el DataSource.
     */
    @Override
    public void close() throws Exception{
        if(dataSource instanceof ComboPooledDataSource comboPooledDataSource){
            comboPooledDataSource.close();
        }
    }
}