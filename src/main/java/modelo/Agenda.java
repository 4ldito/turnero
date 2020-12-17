package modelo;

public class Agenda {

    private int id;
    private String nombre;
    private int id_especialidad;
    private int id_medico;
    private String especialidad;
    public Agenda(int id, String nombre, String especialidad) {
        this.id = id;
        this.nombre = nombre;
        this.especialidad = especialidad;
        
       
    }
    public Agenda(int id, String nombre, int id_especialidad, int id_medico) {
        this.id = id;
        this.nombre = nombre;
        this.id_especialidad = id_especialidad;
        this.id_medico = id_medico;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIdEspecialidad() {
        return id_especialidad;
    }

    public void setIdEspecialidad(int id_especialidad) {
        this.id_especialidad = id_especialidad;
    }

    public int getIdMedico() {
        return id_medico;
    }

    public void setIdMedico(int id_medico) {
        this.id_medico = id_medico;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

}
