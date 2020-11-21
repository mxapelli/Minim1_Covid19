package dsa;

import dsa.models.*;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Covid19ManagerImplTest {
    // THE QUICK REMINDER: Remember to name the test class public smh
    //Log4j Logger initialization
    private static Logger logger = Logger.getLogger(Covid19ManagerImplTest.class);
    //GameManager
    public Covid19Manager manager = null;
    //Estructura de datos
    Brote brote;
    List<Caso> listaCasos;
    //Metodo SetUp
    @Before
    public void setUp() {
        //Configurando Log4j
        PropertyConfigurator.configure("src/main/resources/log4j.properties");
        logger.debug("Debug Test Message!");
        logger.info("Info Test Message!");
        logger.warn("Warning Test Message!");
        logger.error("Error Test Message!");
        //Implementacion Covid19Manager
        manager = Covid19ManagerImpl.getInstance();
        //Inicializando lista objetos
        listaCasos =  new LinkedList<Caso>();
        //Añadimos casos en Lista
        Date dataNacimiento1 = new Date(1997,7,23,18,55,20);
        Date dataInforme1 = new Date(2020,10,19,20,10,10);
        Date dataNacimiento2 = new Date(1999,6,14,16,22,20);
        Date dataInforme2 = new Date(2020,10,23,05,30,10);
        listaCasos.add(new Caso("001","Marc","Xapelli","M","m.xapelli@gmail.com","Barcelona, Spain", dataNacimiento1,dataInforme1,"medio","confirmado",63848619));
        listaCasos.add(new Caso("002","Nelutu","Avram","M","n.avram@gmail.com","Bukarest, Romania", dataNacimiento2,dataInforme2,"bajo","sospechoso",622456789));
    }
    //Tests
    //Metodo Test para crear un nuevo brote
    @Test
    public void crearBroteTest(){
        //Test inicial, 0 brotes
        Assert.assertEquals(0, this.manager.numBrotes());
        //Test Brote
        brote = new Brote("001","MERS",listaCasos);
        manager.añadirBrote(brote);
        //Ahora hay 1 brote
        Assert.assertEquals(1, this.manager.numBrotes());
    }
    //Tests
    //Metodo Test para añadir un caso sobre brote existente
    @Test
    public void añadirCasoBroteTest(){
        //Añadimos brote de prueba
        brote = new Brote("001","MERS");
        manager.añadirBrote(brote);
        //Initial Test, casos iniciales en brote MERS = 0
        Assert.assertEquals(0, this.manager.getBrote("001").getNumCasos());
        //Añadimos 1 caso
        this.manager.añadirCasoBrote("001",listaCasos.get(0));
        //Esperamos 1 caso
        Assert.assertEquals(1, this.manager.getBrote("001").getNumCasos());
        //Añadimos 2o caso
        this.manager.añadirCasoBrote("001",listaCasos.get(1));
        //Ahora esperamos 2 casos
        Assert.assertEquals(2, this.manager.getBrote("001").getNumCasos());
    }
    @After
    public void tearDown() throws Exception {
        manager.liberarRecursos();
    }
}
