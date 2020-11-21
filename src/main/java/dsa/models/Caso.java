package dsa.models;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Caso {
    //Formato
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    String nombre ;
    String apellidos;
    String idCaso;
    String genero;
    String correo;
    String direccion;
    Date fechaNacimiento, fechaInforme;
    String nivelRiesgo, classificacion;
    int telefono;
    //Constructor sin inicializacion
    public Caso(){

    }

    //Creando un caso con todos los campos
    public Caso( String idCaso,String nombre, String apellidos, String genero, String correo, String direccion, Date fechaNacimiento,Date fechaInforme, String nivelRiesgo, String classificacion, int telefono) {
        this.fechaInforme = fechaInforme;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.idCaso = idCaso;
        this.genero = genero;
        this.correo = correo;
        this.direccion = direccion;
        this.fechaNacimiento = fechaNacimiento;
        this.nivelRiesgo = nivelRiesgo;
        this.classificacion = classificacion;
        this.telefono = telefono;
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String name) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getIdCaso() {
        return idCaso;
    }

    public void setIdCaso(String idCaso) {
        this.idCaso = idCaso;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Date getFechaInforme() {
        return fechaInforme;
    }

    public void setFechaInforme(Date fechaInforme) {
        this.fechaInforme = fechaInforme;
    }

    public String getNivelRiesgo() {
        return nivelRiesgo;
    }

    public void setNivelRiesgo(String nivelRiesgo) {
        this.nivelRiesgo = nivelRiesgo;
    }

    public String getClassificacion() {
        return classificacion;
    }

    public void setClassificacion(String classificacion) {
        this.classificacion = classificacion;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

}
