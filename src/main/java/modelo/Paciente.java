package modelo;

/**
 *
 * Esta clase es utilizada para crear un nuevo paciente. Se registraran sus
 * datos para poseteriormente guardarlo en la base de datos.
 */
public class Paciente {
    private String genero;
    private int id;
    private int dni;
    private String nombre;
    private String apellido;
    private String direccion;
    private String celular;
    private int id_genero;

    public Paciente(int id, int dni, String nombre, String apellido, String direccion, String celular, int id_genero) {
        this.id = id;
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.direccion = direccion;
        this.celular = celular;
        this.id_genero = id_genero;
        

    }
    //Este constructor es para cargar la lista.
    public Paciente( int id, int dni, String nombre, String apellido, String direccion, String celular,String genero) {
        this.genero = genero;
        this.id = id;
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.direccion = direccion;
        this.celular = celular;
    }
    
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public int getId_genero() {
        return id_genero;
    }

    public void setId_genero(int id_genero) {
        this.id_genero = id_genero;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

}
