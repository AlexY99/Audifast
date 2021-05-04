
package utilidades;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ConexionBD {
    Connection con;
    
    public ConexionBD(){}
    
    public Connection obtenerConexion() {
        String user = "root";
        String pwd = "root";
        String url = "jdbc:mysql://localhost:3306/?serverTimezone=America/Mexico_City";
        String mySqlDriver = "com.mysql.cj.jdbc.Driver";
        try {
            Class.forName(mySqlDriver);
            con = DriverManager.getConnection(url, user, pwd);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return con;
    }
}
