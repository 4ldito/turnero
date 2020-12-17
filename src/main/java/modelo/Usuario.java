package modelo;

/**
 *
 * Esta clase es utilizada para crear un nuevo usuario.
 *
 *
 */
public class Usuario {

    private int id, celular, id_genero, id_especialidad, matricula, dni;
    private String rol, nombre_usuario, clave, nombre, apellido;

    public Usuario(int id, int dni, int celular, int id_genero, int id_especialidad, int matricula, String rol, String nombre_usuario, String clave, String nombre, String apellido) {
        this.id = id;
        this.dni = dni;
        this.celular = celular;
        this.id_genero = id_genero;
        this.id_especialidad = id_especialidad;
        this.matricula = matricula;
        this.rol = rol;
        this.nombre_usuario = nombre_usuario;
        this.clave = clave;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDNI() {
        return dni;
    }

    public void setDNI(int dni) {
        this.dni = dni;
    }

    public int getCelular() {
        return celular;
    }

    public void setCelular(int celular) {
        this.celular = celular;
    }

    public int getId_genero() {
        return id_genero;
    }

    public void setId_genero(int id_genero) {
        this.id_genero = id_genero;
    }

    public int getId_especialidad() {
        return id_especialidad;
    }

    public void setId_especialidad(int id_especialidad) {
        this.id_especialidad = id_especialidad;
    }

    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getNombre_usuario() {
        return nombre_usuario;
    }

    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

}
