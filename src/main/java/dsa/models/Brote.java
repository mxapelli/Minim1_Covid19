package dsa.models;

import java.util.LinkedList;
import java.util.List;

public class Brote {
    private String broteNombre;
    private String idBrote;
    List<Caso> listaCasos;
    public Brote() {
        this.broteNombre ="";
        this.listaCasos = new LinkedList<>();
    }
    public Brote(String idBrote,String nombreBrote,List<Caso> listaCasos){
        this.idBrote = idBrote;
        this.broteNombre = nombreBrote;
        this.listaCasos = listaCasos;
    }
    public Brote(String idBrote,String nombreBrote){
        this.idBrote = idBrote;
        this.broteNombre = nombreBrote;
        this.listaCasos = new LinkedList<>();
    }
    public Brote(String idBrote,String nombreBrote,Caso caso){
        this.idBrote = idBrote;
        this.broteNombre = nombreBrote;
        this.listaCasos = new LinkedList<>();
        this.listaCasos.add(caso);
    }
    public int añadirCaso(Caso caso){
        try{
            this.listaCasos.add(caso);
        }
        catch (ExceptionInInitializerError e)
        {
            return 400;//400 Bad Request
        }
        catch (IndexOutOfBoundsException e){
            return 507;//Insufficient storage
        }
        return 201;//201 Created
    }
    public String getBroteNombre() {
        return broteNombre;
    }

    public void setBroteNombre(String broteNombre) {
        this.broteNombre = broteNombre;
    }

    public List<Caso> getListaCasos() {
        return listaCasos;
    }

    public void setListaCasos(List<Caso> listaCasos) {
        this.listaCasos = listaCasos;
    }

    public int getNumCasos(){
        return this.listaCasos.size();
    }
    public String getIdBrote() {
        return idBrote;
    }
    public void setIdBrote(String idBrote) {
        this.idBrote = idBrote;
    }
    @Override
    public String toString(){
        return "ID Brote: " + this.getIdBrote() + " | Brote Nombre: " + this.getBroteNombre() ;
    }

}
