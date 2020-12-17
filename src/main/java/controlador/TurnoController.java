package controlador;

import conexion.Conexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.EstadoTurno;
import modelo.Turno;

public class TurnoController {

    Turno turno;
    ArrayList<Turno> listaTurno = new ArrayList();
    EstadoTurno estadoTurno;
    ArrayList<EstadoTurno> listaEstados = new ArrayList();

    public ArrayList cargarTurnos(int idAgenda, int idEspecialidad, int idHorario, int idPaciente, String fecha, int idEstadoTurno, ArrayList<Integer> idPacientes) {
        String condicion = "";
        if (idAgenda != -1) {
            condicion = condicion + " AND`id_agenda` = " + idAgenda;
        }
        if (idEspecialidad != -1) {
            condicion = condicion + " AND `id_especialidad` = " + idEspecialidad;
        }
        if (idHorario != -1) {
            condicion = condicion + " AND `id_horario` = " + idHorario;
        }
        if (idPaciente != -1) {
            condicion = condicion + " AND `id_paciente` = " + idPaciente;
        }
        if (!fecha.equals("")) {
            condicion = condicion + " AND `fecha_turno` = \"" + fecha + "\"";
        }
        if (idEstadoTurno != -1) {
            condicion = condicion + " AND `id_estado` = " + idEstadoTurno;
        }
        if (idPacientes != null) {
            condicion = condicion + " AND `id_paciente` IN (0";
            for (int i = 0; i < idPacientes.size(); i++) {
                condicion = condicion + ", " + idPacientes.get(i);
            }
            condicion = condicion + ")";
        }
        ResultSet rs = Conexion.consultar("SELECT * FROM `turno` WHERE `activo` = 1" + condicion);
        try {
            while (rs.next()) {
                turno = new Turno(
                        rs.getInt("id"),
                        rs.getInt("id_agenda"),
                        rs.getInt("id_especialidad"),
                        rs.getInt("id_horario"),
                        rs.getInt("id_paciente"),
                        rs.getString("fecha_turno"),
                        rs.getString("hs_inicio"),
                        rs.getString("hs_fin"),
                        rs.getInt("num_slot"),
                        rs.getInt("id_estado"));
                listaTurno.add(turno);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PacienteController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaTurno;

    }//Para traer todas las agendas pasar como argumento (1, -1, -1, -1, "", -1)

    public ArrayList cargarEstados() {
        ResultSet rs = Conexion.consultar("SELECT * FROM `estado_turno`");
        try {
            while (rs.next()) {
                estadoTurno = new EstadoTurno(
                        rs.getInt("id"),
                        rs.getString("nombre"));
                listaEstados.add(estadoTurno);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PacienteController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaEstados;
    }

    public EstadoTurno traerEstado(int idEstado) {
        ResultSet rs = Conexion.consultar("SELECT * FROM estado_turno WHERE `id` = " + idEstado);
        try {
            while (rs.next()) {
                estadoTurno = new EstadoTurno(
                        rs.getInt("id"),
                        rs.getString("nombre"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(PacienteController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return estadoTurno;
    }

    public static int agregarTurno(String[] datos) {
        int res = Conexion.agregar("INSERT INTO `turno` (`id_agenda`, `id_especialidad`, `id_horario`, `id_paciente`, `fecha_turno`, `hs_inicio`, `hs_fin`, `num_slot`, `id_estado`, `activo`)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, '2', b'1')", datos);
        return res;
    }

    public static boolean borrarTurno(int idTurno) {
        int res = Conexion.ejecutarUpdate("UPDATE `turno` SET `activo` = b'0' WHERE `id` = " + idTurno);
        return res != 0;
    }

    public static boolean editarEstadoTurno(int idTurno, int idEstadoTurno) {
        int res = Conexion.ejecutarUpdate("UPDATE `turno` SET `id_estado` = " + idEstadoTurno + " WHERE `id` = " + idTurno);
        return res != 0;
    }
}
