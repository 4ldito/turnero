package controlador;

import conexion.Conexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Especialidad;

public class EspecialidadController {

    Especialidad especialidad;
    @SuppressWarnings("unchecked")
    ArrayList<Especialidad> listaEspecialidad = new ArrayList();

    public ArrayList cargarEspecialidad() {
        ResultSet rs = Conexion.consultar("SELECT * FROM `especialidad` WHERE `activo` = 1");
        try {
            while (rs.next()) {
                especialidad = new Especialidad(
                        rs.getInt("id"),
                        rs.getString("nombre")
                );
                listaEspecialidad.add(especialidad);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PacienteController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaEspecialidad;
    }

    public Especialidad buscarEspecialidad(int idespecialidad) {
        ResultSet rs = Conexion.consultar("SELECT * FROM `especialidad` WHERE `activo` = 1 AND `id` = " + idespecialidad);
        try {
            while (rs.next()) {
                especialidad = new Especialidad(
                        rs.getInt("id"),
                        rs.getString("nombre")
                );
            }
        } catch (NullPointerException | SQLException ex) {
        }
        return especialidad;
    }

    public int agregarEspecialidad(String[] datos) {
        int res = Conexion.agregar("INSERT INTO especialidad (nombre, activo) VALUES (?, b'1')", datos);
        return res;
    }

    public int editarEspecialidad(String[] datos, int id) {
        int res = Conexion.guardar("UPDATE especialidad SET nombre = ? WHERE `especialidad`.`id` = " + id, datos);
        return res;
    }

    public int borrarEspecialidad(int id) {
        int res = Conexion.ejecutarUpdate("UPDATE `especialidad` SET `activo` = b'0' WHERE `id` = " + id);
        return res;
    }
}
