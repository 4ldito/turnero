package conexion;

import com.mysql.cj.jdbc.MysqlDataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * Clase para controlar todas las conexiones.
 * 
 */

public class Conexion extends MysqlDataSource {

    final private static String JDBCMYSQL = "jdbc:mysql://";
    final private static String DATOS_DB = "db.txt";

    private static Conexion instancia = null;
    private static Connection con = null;

    private Conexion() throws IOException, SQLException {
        super();

        Properties props = new Properties();
        FileInputStream fis = new FileInputStream(DATOS_DB);
        props.load(fis);
        setURL(JDBCMYSQL + props.getProperty("HOST") + "/" + props.getProperty("DATABASE") + "?serverTimezone=UTC");
        setUser(props.getProperty("USER"));
        setPassword(props.getProperty("PASSWORD"));
        getConnection();
    }

    public static Conexion getInstancia() {
        if (instancia == null) {
            try {
                instancia = new Conexion();
            } catch (IOException | SQLException ex) {
                instancia = null;
                System.err.println(ex);
            }
        }
        return instancia;
    }

    public static boolean conectar() {
        if (con == null) {
            try {
                if (getInstancia() == null) {
                    return false;
                }
                con = instancia.getConnection();
            } catch (SQLException e) {
                con = null;
                instancia = null;
                System.err.println(e);
            }
        }
        return true;
    }

    public static void desconectar() {
        if (con != null) {
            try {
                con.close();
                con = null;
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
    }

    // Consultas sin prepareds statements
    public static ResultSet consultar(String query) {
        Statement stmt = null;
        ResultSet rs = null;
        //Si no hay conexión, no devuelve nada
        if (!conectar()) {
            return null;
        }
        try {
            //Realiza la consulta
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            //Devuelve el result set
            return rs;
        } catch (SQLException e) {
            System.err.println(e);
        }
        // liberamos recursos y evitamos memory leaks
        try {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
        return null;
    }

    public static int ejecutarUpdate(String query) {
        Statement stmt = null;
        int rs = 0;
        //Si no hay conexión, no devuelve nada
        if (!conectar()) {
            return rs;
        }
        try {
            //Realiza la consulta
            stmt = con.createStatement();
            rs = stmt.executeUpdate(query);
            //Devuelve el result set
            return rs;
        } catch (SQLException e) {
            System.err.println(e);
        }
        return 0;
    }
    
    // Consultas CON prepareds statements
    public static ResultSet consultar(String query, String[] datos) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        //Si no hay conexión, no devuelve nada
        if (!conectar()) {
            return null;
        }
        try {
            //Realiza la consulta con prepared statements
            stmt = con.prepareStatement(query);
            // Por cada fila que haya que revisar, lo asignamos al stmt
            for (int i = 0; i < datos.length; i++) {
                stmt.setString(i + 1, datos[i]);
            }
            // ejecutamos el query
            rs = stmt.executeQuery();
            //Devuelve el result set
            return rs;
        } catch (SQLException e) {
            System.err.println(e);
        }
        // liberamos recursos y evitamos memory leaks
        try {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
        return null;
    }

    public static int agregar(String query, String[] datos) {
        PreparedStatement stmt = null;

        //Si no hay conexión, no devuelve nada
        if (!conectar()) {
            return 0;
        }
        try {
            //Realiza la consulta con prepared statements
            stmt = con.prepareStatement(query);
            // Por cada fila que haya que revisar, lo asignamos al stmt
            for (int i = 0; i < datos.length; i++) {
                stmt.setString(i + 1, datos[i]);
            }
            // ejecutamos el query
            int res = stmt.executeUpdate();
            //Devuelve el result set
            return res;
        } catch (SQLException e) {
            System.out.println(e);
        }
        // liberamos recursos y evitamos memory leaks
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return 0;
    }
    
    public static int guardar(String query, String[] datos) {
        PreparedStatement stmt = null;
        //Si no hay conexión, no devuelve nada
        if (!conectar()) {
            return 0;
        }
        try {
            //Realiza la consulta con prepared statements
            stmt = con.prepareStatement(query);
            // Por cada fila que haya que revisar, lo asignamos al stmt
            for (int i = 0; i < datos.length; i++) {
                stmt.setString(i + 1, datos[i]);
            }
            // ejecutamos el query
            int res = stmt.executeUpdate();
            //Devuelve el result set
            return res;
        } catch (SQLException e) {
            System.out.println(e);
        }
        // liberamos recursos y evitamos memory leaks
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return 0;
    }
}
