package dsa;
import dsa.models.*;

import java.util.List;

public interface Covid19Manager {

    //Añadir un Brote
    int añadirBrote(String idBrote, String nombreBrote, List<Caso> listaCasos);
    //Añadir un Brote
    int añadirBrote(Brote brote);
    //Lista de los brotes disponibles
    List<Brote> getListaBrote();
    //Añadir un caso a un brote
    int añadirCasoBrote(String idBrote, Caso caso);
    //Listado de casos de un determinado brote clasificados (confirmado,sospechoso y no caso)
    List<Caso> getListaCasosClasificadoBrote(String IdBrote);
    void liberarRecursos();
    int numBrotes();
    String generateId();
    Brote getBrote(String IdBrote);
}
