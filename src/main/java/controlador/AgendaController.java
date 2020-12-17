package controlador;

import conexion.Conexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Agenda;

public class AgendaController {

    Agenda agenda;
    @SuppressWarnings("unchecked")
    ArrayList<Agenda> listaAgenda = new ArrayList();

    public int conseguirIdMedico(int idUsuario) {
        ResultSet rs = Conexion.consultar("SELECT id FROM medico WHERE id_usuario=" + idUsuario);
        int idMedico = -1;

        try {
            while (rs.next()) {
                idMedico = rs.getInt("id");

            }
        } catch (SQLException ex) {
            Logger.getLogger(PacienteController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return idMedico;
    }

    //Carga las agendas en la clase AgendaMedico.
    public ArrayList cargarAgendasByIdMedico(int idMedico) {

        ResultSet rs = Conexion.consultar("SELECT agenda.id,agenda.nombre , especialidad.nombre FROM agenda INNER JOIN especialidad ON agenda.id_especialidad = especialidad.id WHERE agenda.activo=1 AND agenda.id_medico =" + idMedico);
        try {
            while (rs.next()) {
                agenda = new Agenda(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("especialidad.nombre")
                );

                listaAgenda.add(agenda);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PacienteController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaAgenda;
    }

    public ArrayList cargarAgendas(String nombreAgenda, int idMedico, int idespecialidad) {
        String condicion = "";
        if (idMedico != -1) {
            condicion = " AND `id_medico` = '" + idMedico + "'";
        }
        if (idespecialidad != -1) {
            condicion = condicion + " AND `id_especialidad` = '" + idespecialidad + "'";
        }
        ResultSet rs = Conexion.consultar("SELECT * FROM `agenda` WHERE `activo` = 1 AND `nombre` LIKE \"%" + nombreAgenda + "%\"" + condicion);
        try {
            while (rs.next()) {
                agenda = new Agenda(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getInt("id_especialidad"),
                        rs.getInt("id_medico")
                );
                listaAgenda.add(agenda);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PacienteController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaAgenda;

    }//Para traer todas las agendas pasar como argumento ("",-1,-1)

    public Agenda traerAgenda(int idAgenda) {
        ResultSet rs = Conexion.consultar("SELECT * FROM agenda WHERE `id` = " + idAgenda);
        try {
            while (rs.next()) {
                agenda = new Agenda(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getInt("id_especialidad"),
                        rs.getInt("id_medico")
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(PacienteController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return agenda;
    }

    public static int agregarAgenda(String[] datos) {
        int res = Conexion.agregar("INSERT INTO `agenda` (`nombre`, `id_especialidad`, `id_medico`, `activo`) VALUES (?, ?, ?, b'1')", datos);
        return res;
    }

    public static boolean borrarAgenda(int id) {
        int res = Conexion.ejecutarUpdate("UPDATE `agenda` SET `activo` = b'0' WHERE `id` = " + id);
        return res != 0;
    }

    public static int editarAgenda(int id, String nombre, int idespecialidad, int idmedico) {
        int res = Conexion.ejecutarUpdate("UPDATE `agenda` SET `nombre` = '" + nombre + "' , `id_especialidad` = " + idespecialidad + " , `id_medico` = " + idmedico + " WHERE `agenda`.`id` =" + id);
        return res;
    }

    public static int buscarIdAgenda(String nombre, int idmedico, int idespecialidad) {
        int idagenda = -1;
        ResultSet rs = Conexion.consultar("SELECT * FROM `agenda` WHERE `activo` = 1 AND `nombre` = \"" + nombre + "\""
                + " AND `id_medico` = " + idmedico
                + " AND `id_especialidad` = " + idespecialidad);
        try {
            while (rs.next()) {
                idagenda = rs.getInt("id");
            }
        } catch (NullPointerException | SQLException ex) {
        }
        return idagenda;
    } //devuelve -1 si no encontró la agenda
}
