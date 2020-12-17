package controlador;

import conexion.Conexion;
import modelo.Usuario;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Rol;

public class UsuarioController {

    Usuario usuario = null;
    Usuario medico;
    Usuario administrativo;
    Rol rol;
    ArrayList<Usuario> listaUsuarios = new ArrayList<>();
    ArrayList<Rol> listaRol = new ArrayList<>();
    ArrayList<Usuario> listaMedicos = new ArrayList<>();
    ArrayList<Usuario> listaAdministrativos = new ArrayList<>();

    public ArrayList<Usuario> cargarUsuarios(String dni) {
        String condicion = "";
        if (!"".equals(dni)) {
            condicion = condicion + " AND usuario.dni LIKE \"%" + dni + "%\"";
        }
        ResultSet rs = Conexion.consultar("SELECT * ,rol.tipo FROM usuario INNER JOIN rol ON usuario.rol = rol.id WHERE `activo` = 1" + condicion);
        try {
            while (rs.next()) {
                usuario = new Usuario(
                        rs.getInt("id"),
                        rs.getInt("dni"),
                        rs.getInt("celular"),
                        rs.getInt("id_genero"),
                        rs.getInt("id_especialidad"),
                        rs.getInt("matricula"),
                        rs.getString("tipo"),
                        rs.getString("nombre_usuario"),
                        rs.getString("clave"),
                        rs.getString("nombre"),
                        rs.getString("apellido")
                );
                listaUsuarios.add(usuario);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PacienteController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaUsuarios;
    }

    public ArrayList<Usuario> cargarMedicos(String dniMedico) {
        String condicion = "";
        if (!"".equals(dniMedico)) {
            condicion = condicion + "AND usuario.dni LIKE \"%" + dniMedico + "%\"";
        }
        ResultSet rs = Conexion.consultar("SELECT * ,rol.tipo FROM usuario INNER JOIN rol ON usuario.rol = rol.id WHERE `activo` = 1 AND `rol` IN (2,3)" + condicion);
        try {
            while (rs.next()) {
                medico = new Usuario(
                        rs.getInt("id"),
                        rs.getInt("dni"),
                        rs.getInt("celular"),
                        rs.getInt("id_genero"),
                        rs.getInt("id_especialidad"),
                        rs.getInt("matricula"),
                        rs.getString("tipo"),
                        rs.getString("nombre_usuario"),
                        rs.getString("clave"),
                        rs.getString("nombre"),
                        rs.getString("apellido")
                );
                listaMedicos.add(medico);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PacienteController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaMedicos;
    }

    public ArrayList cargarAdministrativos() {
        ResultSet rs = Conexion.consultar("SELECT * ,rol.tipo FROM usuario INNER JOIN rol ON usuario.rol = rol.id WHERE `activo` = 1 AND `rol` = 1");
        try {
            while (rs.next()) {
                administrativo = new Usuario(
                        rs.getInt("id"),
                        rs.getInt("dni"),
                        rs.getInt("celular"),
                        rs.getInt("id_genero"),
                        rs.getInt("id_especialidad"),
                        rs.getInt("matricula"),
                        rs.getString("tipo"),
                        rs.getString("nombre_usuario"),
                        rs.getString("clave"),
                        rs.getString("nombre"),
                        rs.getString("apellido")
                );
                listaAdministrativos.add(administrativo);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PacienteController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaAdministrativos;
    }

    public Usuario buscarMedico(int idMedico, ArrayList<Usuario> listaMedico) {
        for (int i = 0; i < listaMedico.size(); i++) {
            medico = listaMedico.get(i);
            if (medico.getId() == idMedico) {
                return medico;
            }
        }
        return null;
    }

    public ArrayList<Usuario> buscarMedicosPorEspecialidad(int idEspecialidad, ArrayList<Usuario> listaMedico) {
        listaMedicos = new ArrayList<>();
        for (int i = 0; i < listaMedico.size(); i++) {
            medico = listaMedico.get(i);
            if (medico.getId_especialidad() == idEspecialidad) {
                listaMedicos.add(medico);
            }
        }
        return listaMedicos;
    }

    public ArrayList cargarMedicosMatricula(String[] matricula) {
        ResultSet rs = Conexion.consultar("SELECT * FROM `medico` WHERE `activo` = 1 AND matricula LIKE CONCAT('%',?,'%')", matricula);
        try {
            while (rs.next()) {
                medico = new Usuario(
                        rs.getInt("id"),
                        rs.getInt("dni"),
                        rs.getInt("celular"),
                        rs.getInt("id_genero"),
                        rs.getInt("id_especialidad"),
                        rs.getInt("matricula"),
                        rs.getString("tipo"),
                        rs.getString("nombre_usuario"),
                        rs.getString("clave"),
                        rs.getString("nombre"),
                        rs.getString("apellido")
                );
                listaMedicos.add(medico);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PacienteController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaMedicos;
    }

    public String nombreSexo(int id_genero) {
        ResultSet rs = Conexion.consultar("SELECT * FROM `genero` WHERE `id` = " + id_genero);
        try {
            while (rs.next()) {
                String genero = rs.getString("sexo");
                return genero;
            }
        } catch (SQLException ex) {
            Logger.getLogger(PacienteController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<Rol> cargarRoles() {
        listaRol.clear();
        ResultSet rs = Conexion.consultar("SELECT * FROM `rol`");
        try {
            while (rs.next()) {
                rol = new Rol(
                        rs.getInt("id"),
                        rs.getString("tipo"));
                listaRol.add(rol);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PacienteController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaRol;
    }

    public static int agregarUsuario(String[] datos) {
        int res = Conexion.agregar("INSERT INTO usuario (`rol`, `dni`, `nombre_usuario`, `clave`, `nombre`, `apellido`, `celular`, `id_genero`, `id_especialidad`, `matricula`, `activo`)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, b'1')", datos);
        return res;
    }

    public static int borrarUsuario(int id) {
        int res = Conexion.ejecutarUpdate("UPDATE `usuario` SET `activo` = b'0' WHERE `id` = " + id);
        return res;
    }

    public static int editarUsuario(String[] datos, int id) {
        int res = Conexion.guardar("UPDATE `usuario` SET `rol` = ?, `dni` = ?, `nombre_usuario` = ?, `clave` = ?, `nombre` = ?, `apellido` = ?, `celular` = ?, `id_genero` = ?,"
                + " `id_especialidad` = ?, `matricula` = ? WHERE `usuario`.`id` =" + id, datos);
        return res;
    }

}
