package controlador;

import conexion.Conexion;
import modelo.Paciente;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * En esta clase se controlaran todas las diferentes acciones que se pueden
 * realizar con un paciente. Cada acción tiene su respectivo método.
 *
 */
public class PacienteController {

    ArrayList<Paciente> listaPacientes = new ArrayList<>();
    Paciente paciente = null;

    // obitene a todos los pacientes y los guarda en un ArrayList
    public ArrayList cargarPacientes(String idPaciente, String dni) {
        String condicion = "";
        if (!idPaciente.equals("")) {
            condicion = " AND paciente.id = " + idPaciente;
        } else if(!dni.equals("")){
            condicion = " AND paciente.dni LIKE \"%" + dni + "%\"";
        }
        ResultSet rs = Conexion.consultar("SELECT *,genero.sexo FROM paciente INNER JOIN genero ON paciente.id_genero=genero.id WHERE paciente.activo = 1" + condicion);
        try {
            while (rs.next()) {
                paciente = new Paciente(
                        rs.getInt("id"),
                        rs.getInt("dni"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("direccion"),
                        rs.getString("celular"),
                        rs.getString("sexo")
                );
                listaPacientes.add(paciente);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PacienteController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaPacientes;
    }//Carga la lista de pacientes, recibe como argumento, una id o un dni, pasar "","" si se requiere la lista completa.

    public Paciente traerPaciente(int idPaciente){
        ResultSet rs = Conexion.consultar("SELECT *,genero.sexo FROM paciente INNER JOIN genero ON paciente.id_genero=genero.id WHERE paciente.activo = 1 AND paciente.id =" + idPaciente);
        try {
            while (rs.next()) {
                paciente = new Paciente(
                        rs.getInt("id"),
                        rs.getInt("dni"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("direccion"),
                        rs.getString("celular"),
                        rs.getString("sexo")
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(PacienteController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return paciente;
    }
    
    public static int agregarPaciente(String[] datos) {
        int res = Conexion.agregar("INSERT INTO paciente (dni, nombre, apellido, direccion, celular, id_genero, activo) VALUES (?,?,?,?,?,?,1)", datos);
        return res;
    }
    
    public static boolean borrarPaciente(int id) {
        int res = Conexion.ejecutarUpdate("UPDATE `paciente` SET `activo` = b'0' WHERE `id` = " + id);
        return res != 0;
    }
    
    public static int editarPaciente(int id, String[] datos){
        int res = Conexion.guardar("UPDATE `paciente` SET `dni` = ?, `nombre` = ?, `apellido` = ?, `direccion` = ?, `celular` = ?, `id_genero` = ? WHERE id = "+id, datos);
        return res;
    }
    
    //    public static int eliminarPaciente(String[] filas){

    //int res = Conn.agregar("INSERT INTO paciente (dni, nombre, apellido, direccion, celular, id_genero) VALUES (?,?,?,?,?,?)",filas);
    //return res;
    //}
}
