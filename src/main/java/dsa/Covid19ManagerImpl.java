package dsa;

import dsa.models.*;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Covid19ManagerImpl implements Covid19Manager {
    private static Covid19Manager instance;
    //HashMap is same as Dictionary
    private HashMap<String , Brote> diccionarioBrote;
    private List<Caso> listaCasos;
    private static Logger log = Logger.getLogger(Covid19Manager.class);
    //Singleton fachada
    public static Covid19Manager getInstance(){
        if(instance == null) {
            instance = new Covid19ManagerImpl();
        }
        return instance;
    }
    //Private Constructor
    private Covid19ManagerImpl(){
        //Singleton Private Constructor
        this.diccionarioBrote = new HashMap<>();
        this.listaCasos = new LinkedList<>();
    }
    @Override
    public int añadirBrote(String idBrote,String nombreBrote, List<Caso> listaCasos) {

        try{
            diccionarioBrote.put(idBrote,new Brote(idBrote,nombreBrote,listaCasos));
            log.info("Brote Añadido: " + nombreBrote );
            return 201; //OK CREATED
        }
        catch (IndexOutOfBoundsException e){
            log.error("UserMap Full Error");
            return 507; //INSUFFICIENT STORAGE
        }
        catch (IllegalArgumentException e){
            log.error("Incorrect format exception");
            return 400; //BAD REQUEST
        }
    }
    //Añadir Brote con Brote
    @Override
    public int añadirBrote(Brote brote) {

        try{
            if(brote.getIdBrote() == null || brote.getIdBrote().isEmpty()){
                throw new IllegalArgumentException();
            }
            diccionarioBrote.put(brote.getIdBrote(),brote);
            log.info("Brote Añadido: " + brote.getBroteNombre() );
            return 201; //OK CREATED
        }
        catch (IndexOutOfBoundsException e){
            log.error("UserMap Full Error");
            return 507; //INSUFFICIENT STORAGE
        }
        catch (IllegalArgumentException e){
            log.error("Brote is Empty");
            return 400; //BAD REQUEST
        }
    }
    @Override
    public List<Brote> getListaBrote() {
        List<Brote> result = null;
        if(this.diccionarioBrote.size() !=0){
            result = new LinkedList<>(this.diccionarioBrote.values());
            log.info("Brote List: "+result.toString());
        }
        return result; //Null: 404 Empty User HashMap
    }

    @Override
    public int añadirCasoBrote(String idBrote, Caso caso) {
        if(diccionarioBrote.size() ==0){
            log.info("204: Diccionario de Brote Vacio ");
            return 204;//204 No Content
        }
            Brote temp_brote = diccionarioBrote.get(idBrote);
            if(temp_brote != null){
                int err = temp_brote.añadirCaso(caso);
                if(err == 201){
                    diccionarioBrote.put(idBrote,temp_brote);
                    log.info("201: Caso añadido al brote " + temp_brote.getBroteNombre());
                    return 201; //OK CREATED
                }
                else if(err == 400){
                    log.error("400: Bad Format");
                    return 400; //BAD REQUEST
                }
                else{
                    log.error("507:Insufficient storage "+ temp_brote.getBroteNombre());
                    return 507; //204 No Content
                }
            }
            else{
                log.error("Brote Not found");
                return 404; //Brote NOT FOUND
            }
    }

    @Override
    public List<Caso> getListaCasosClasificadoBrote(String IdBrote) {
        List<Caso> listaClasificadaCasos;
        Brote brote = getBrote(IdBrote);
        if(brote ==null){return null;}
        //We have found the brote but it doesn't contain casos
        int nums = brote.getNumCasos();
        if(nums==0){return null;}
        listaClasificadaCasos = brote.getListaCasos();
        //Now we can classify and order it!
        //TODO: ACTUALLY CLASSIFY THE ABOVE CASE LIST! confirmado > sospechoso > no caso
        return listaClasificadaCasos;
    }
    ////////////////////EXTRAS///////////
    @Override
    public Brote getBrote(String IdBrote){
        return diccionarioBrote.get(IdBrote);
    }
    @Override
    public int numBrotes(){return this.diccionarioBrote.size();
    }
    //Generate Id
    @Override
    public String generateId(){
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 3) { // length of the random generated ID
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        return salt.toString();
    }
    //Liberar Recursos
    @Override
    public void liberarRecursos() {
        this.listaCasos.clear();
        this.diccionarioBrote.clear();
    }
}
