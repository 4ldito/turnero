package controlador;

import conexion.Conexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Horario;

public class HorarioController {

    Horario horario;
    @SuppressWarnings("unchecked")
    ArrayList<Horario> listaHorario = new ArrayList();

    public ArrayList cargarHorario(int id_agenda) {
        ResultSet rs = Conexion.consultar("SELECT * FROM `horario` WHERE `activo` = 1 AND `id_agenda` = " + id_agenda);
        try {
            while (rs.next()) {
                horario = new Horario(
                        rs.getInt("id"),
                        rs.getInt("id_agenda"),
                        rs.getString("nombre"),
                        rs.getString("hs_inicio"),
                        rs.getString("hs_fin"),
                        rs.getInt("intervalo"),
                        rs.getInt("lunes"),
                        rs.getInt("martes"),
                        rs.getInt("miercoles"),
                        rs.getInt("jueves"),
                        rs.getInt("viernes"),
                        rs.getInt("sabado")
                );
                listaHorario.add(horario);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PacienteController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaHorario;
    }
    
    public Horario traerHorario(int id_Horario){
        ResultSet rs = Conexion.consultar("SELECT * FROM `horario` WHERE `activo` = 1 AND `id` = " + id_Horario);
        try {
            while (rs.next()) {
                horario = new Horario(
                        rs.getInt("id"),
                        rs.getInt("id_agenda"),
                        rs.getString("nombre"),
                        rs.getString("hs_inicio"),
                        rs.getString("hs_fin"),
                        rs.getInt("intervalo"),
                        rs.getInt("lunes"),
                        rs.getInt("martes"),
                        rs.getInt("miercoles"),
                        rs.getInt("jueves"),
                        rs.getInt("viernes"),
                        rs.getInt("sabado")
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(PacienteController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return horario;
    }

    public int agregarHorario(String[] datos) {
        int res = Conexion.agregar("INSERT INTO `horario` (`id_agenda`, `nombre`, `hs_inicio`, `hs_fin`, `intervalo`,"
                + " `lunes`, `martes`, `miercoles`, `jueves`, `viernes`, `sabado`, `activo`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, b'1')", datos);
        return res;
    }

    public boolean borrarHorario(int id) {
        int res = Conexion.ejecutarUpdate("UPDATE `horario` SET `activo` = b'0' WHERE `id` = " + id);
        return res != 0;
    }

    public int editarHorario(int id, String[] datos) {
        int res = Conexion.guardar("UPDATE `horario` SET `id_agenda` = ?, `nombre` = ?, `hs_inicio` = ?, `hs_fin` = ?, `intervalo` = ?, `lunes` = ?,"
                + " `martes` = ?, `miercoles` = ?, `jueves` = ?, `viernes` = ?, `sabado` = ? WHERE `horario`.`id` =" + id, datos);
        return res;
    }
}
