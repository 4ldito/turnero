package modelo;

public class Turno {

    private int id, id_agenda, id_especialidad, id_horario, id_paciente, num_slot, id_estado;
    private String fecha, horaInicio, horaFin;

    public Turno(int id, int id_agenda, int id_especialidad, int id_horario, int id_paciente, String fecha, String horaInicio, String horaFin,  int num_slot, int id_estado) {
        this.id = id;
        this.id_agenda = id_agenda;
        this.id_especialidad = id_especialidad;
        this.id_horario = id_horario;
        this.id_paciente = id_paciente;
        this.num_slot = num_slot;
        this.id_estado = id_estado;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_agenda() {
        return id_agenda;
    }

    public void setId_agenda(int id_agenda) {
        this.id_agenda = id_agenda;
    }
    
    public int getId_especialidad(){
        return id_especialidad;
    }
    
    public void setId_especialidad(int id_especialidad){
        this.id_especialidad = id_especialidad;
    }

    public int getId_horario() {
        return id_horario;
    }

    public void setId_horario(int id_horario) {
        this.id_horario = id_horario;
    }

    public int getId_paciente() {
        return id_paciente;
    }

    public void setId_paciente(int id_paciente) {
        this.id_paciente = id_paciente;
    }

    public int getNum_slot() {
        return num_slot;
    }

    public void setNum_slot(int num_slot) {
        this.num_slot = num_slot;
    }

    public int getId_estado() {
        return id_estado;
    }

    public void setId_estado(int id_estado) {
        this.id_estado = id_estado;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

}
