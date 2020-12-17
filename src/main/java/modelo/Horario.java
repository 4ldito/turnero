package modelo;

public class Horario {

    private final int id;
    private final int id_agenda;
    private final String nombre;
    private final String hs_inicio;
    private final String hs_fin;
    private final int intervalo;
    private final int lunes, martes, miercoles, jueves, viernes, sabado;
    
    public Horario(int id, int id_agenda, String nombre, String hs_inicio, String hs_fin,
            int intervalo, int lunes, int martes, int miercoles, int jueves, int viernes, int sabado) {
        this.id = id;
        this.id_agenda = id_agenda;
        this.nombre = nombre;
        this.hs_inicio = hs_inicio;
        this.hs_fin = hs_fin;
        this.intervalo = intervalo;
        this.lunes = lunes;
        this.martes = martes;
        this.miercoles = miercoles;
        this.jueves = jueves;
        this.viernes = viernes;
        this.sabado = sabado;
    }

    public int getId() {
        return id;
    }

    public int getId_agenda() {
        return id_agenda;
    }

    public String getNombre() {
        return nombre;
    }

    public String getHs_inicio() {
        return hs_inicio;
    }

    public String getHs_fin() {
        return hs_fin;
    }

    public int getIntervalo() {
        return intervalo;
    }

    public int isLunes() {
        return lunes;
    }

    public int isMartes() {
        return martes;
    }

    public int isMiercoles() {
        return miercoles;
    }

    public int isJueves() {
        return jueves;
    }

    public int isViernes() {
        return viernes;
    }

    public int isSabado() {
        return sabado;
    }

}
