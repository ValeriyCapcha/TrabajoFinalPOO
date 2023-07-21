package inventario;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

public class Conexion {
    Connection conectar = null;
    String usuario = "root";
    String contrasenha = "#MySQL135";
    String bd = "RYNPSAC";
    String ip = "localhost";
    String puerto = "3306";
    
    String cadena = "jdbc:mysql://"+ip+":"+puerto+"/"+bd;
    
    public Connection conectar(){
        try{            
            Class.forName("com.mysql.cj.jdbc.Driver");
            conectar = DriverManager.getConnection(cadena,usuario,contrasenha);
            ///JOptionPane.showMessageDialog(null, "Conecto");
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, "Error al Conectar: "+e.toString());
        }
        return conectar;
    }
}
