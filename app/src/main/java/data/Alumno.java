package data;


public class Alumno {
    private String nombre;
    private String apellido;
    private int dni;
    private int legajo;
    private int id;

    public Alumno(){};

    public Alumno(String nombre, String apellido, int dni, int legajo){
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.legajo = legajo;

    }
    public int getId(){
        return id;

    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public String getNombre(){
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public int getDni(){
        return dni;
    }

    public int getLegajo(){
        return legajo;
    }
}
