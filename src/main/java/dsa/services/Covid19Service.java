package dsa.services;

import dsa.Covid19Manager;
import dsa.Covid19ManagerImpl;
import dsa.models.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

//Models or Element Entity
//Swagger Imports
@Api(value = "/Covid19", description = "Endpoint to User Service")
@Path("/Covid19Service")
public class Covid19Service {
    static final Logger logger = Logger.getLogger(Covid19Service.class);
    private Covid19Manager manager;
    //Estructura de datos
    Brote brote;
    List<Caso> listaCasos;
    public Covid19Service(){
        //Configuring Log4j, location of the log4j.properties file and must always be inside the src folder
        PropertyConfigurator.configure("src/main/resources/log4j.properties");
        this.manager = Covid19ManagerImpl.getInstance();
        if (this.manager.numBrotes() == 0) {
            //Adding Brotes
            manager = Covid19ManagerImpl.getInstance();
            //Initializing Object List
            listaCasos =  new LinkedList<Caso>();
            //Appending Caso en Lista
            //Example Date firstDate3 = new Date(int year, int month, int date, int hrs, int min, int sec);
            Date dataNacimiento = new Date(1997,9,23,16,55,20);
            Date dataInforme = new Date(2020,04,12,05,10,10);
            //Caso( String idCaso,String nombre, String apellidos, String genero, String correo, String direccion, Date fechaNacimiento,Date fechaInforme, String nivelRiesgo, String classificacion, int telefono)
            listaCasos.add(new Caso("001","Marc","Xapelli","M","m.xapelli@gmail.com","Barcelona, Spain", dataNacimiento,dataInforme,"medio","confirmado",63848619));
            listaCasos.add(new Caso("002","Nelutu","Avram","M","n.avram@gmail.com","Bukarest, Romania", dataNacimiento,dataInforme,"bajo","no caso",622456789));
            listaCasos.add(new Caso("003","Linus","Chinchilla","M","l.chinchilla@gmail.com","Andes, Xile", dataNacimiento,dataInforme,"medio","sospechoso",666666666));
            listaCasos.add(new Caso("004","Lana","Rhoades","F","lana@gmail.com","Chicago, United States", dataNacimiento,dataInforme,"bajo","no caso",657432997));
            brote = new Brote("001","MERS",listaCasos);
            manager.añadirBrote(brote);
            brote = new Brote("002","SARS",listaCasos.get(2));
            manager.añadirBrote(brote);
            brote = new Brote("003","EBOLA",listaCasos.get(3));
            manager.añadirBrote(brote);
        }
    }
    //When multiple GET, PUT, POSTS & DELETE EXIST on the same SERVICE, path must be aggregated
    //Lista de Brotes
    @GET
    @ApiOperation(value = "Get all brotes", notes = "Retrieves the list of brotes")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Brote.class, responseContainer="List"),
    })
    @Path("/listBrotes")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers() {
        List<Brote> user = this.manager.getListaBrote();
        GenericEntity<List<Brote>> entity = new GenericEntity<List<Brote>>(user) {};
        return Response.status(201).entity(entity).build()  ;
    }
    //When multiple GET, PUT, POSTS & DELETE EXIST on the same SERVICE, path must be aggregated
    //Lista de Brotes Clasificado
    @GET
    @ApiOperation(value = "Get all brotes clasificados", notes = "Retrieves the list of casos clasificados de un brote")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Caso.class, responseContainer="List"),
    })
    @Path("/listCasoClasificado/{BroteId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getListaClassificada(@PathParam("BroteId") String BroteId) {
        List<Caso> listacasos = new LinkedList<>();
        if (BroteId.isEmpty()) {return Response.status(500).entity(null).build();}
        Brote brote = manager.getBrote(BroteId);
        if(brote == null) {return Response.status(500).entity(null).build();}
        listacasos = manager.getListaCasosClasificadoBrote(BroteId);
        GenericEntity<List<Caso>> entity = new GenericEntity<List<Caso>>(listacasos) {};
        return Response.status(201).entity(entity).build();
    }


    //Añadir un Brote
    @POST
    @ApiOperation(value = "Create a new brote", notes = "Adds a new brote")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response=Brote.class),
            @ApiResponse(code = 500, message = "Validation Error")
    })
    @Path("/addBrote/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response newUser(@PathParam("name") String nameBrote ) {
        if (nameBrote.isEmpty())  return Response.status(500).entity(new Brote()).build();
        String idBrote = manager.generateId();
        this.manager.añadirBrote(idBrote,nameBrote,new LinkedList<Caso>());
        return Response.status(201).entity(manager.getBrote(idBrote)).build();
    }

    //Añadir un caso sobre un brote
    @PUT
    @ApiOperation(value = "Añade un caso sobre brote", notes = "Caso en un brote existente: Fecha: yyyy-MM-dd HH:mm:ss")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response=Brote.class),
            @ApiResponse(code = 500, message = "Validation Error"),
            @ApiResponse(code = 404, message = "User/GameObject Not found Error")
    })
    @Path("/addCaso/{BroteId}/{nombre}/{apellido}/{fechaNacimiento}/{fechaInforme}/{nivelRiesgo}/{genero}/{correo}/{telefono}/{direccion}/{classificacion}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addObject(@PathParam("BroteId") String BroteId,@PathParam("nombre") String nombre,@PathParam("apellido") String apellido
            ,@PathParam("fechaNacimiento") String fechaNacimiento,@PathParam("fechaInforme") String fechaInforme
            ,@PathParam("nivelRiesgo") String nivelRiesgo,@PathParam("genero") String genero,@PathParam("correo") String correo
            ,@PathParam("telefono") String telefono,@PathParam("direccion") String direccion,@PathParam("classificacion") String classificacion)
    {
        if (BroteId.isEmpty() || nombre.isEmpty()|| apellido.isEmpty()|| fechaNacimiento.isEmpty()|| fechaInforme.isEmpty()|| nivelRiesgo.isEmpty()|| genero.isEmpty()|| correo.isEmpty()|| telefono.isEmpty()|| direccion.isEmpty()|| classificacion.isEmpty())
        {  return Response.status(500).entity(new Brote()).build();}

        //Check the format is right
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Brote brote = manager.getBrote(BroteId);
        //Caso caso = listaCasos.contains(new Caso())
        if(brote==null )  return Response.status(404).entity(new Brote()).build();
        Date dateNacimiento = null;Date dateInforme = null;
        try {
            dateNacimiento = formatter.parse(fechaNacimiento);
            dateInforme = formatter.parse(fechaInforme);
            int telephone = Integer.parseInt(telefono);
            String idCaso = manager.generateId();
            Caso caso = new Caso(idCaso,nombre,apellido,genero,correo,direccion,dateNacimiento,dateInforme,nivelRiesgo, classificacion,telephone );
            manager.añadirCasoBrote(BroteId,caso);

        } catch (Exception ex) {
            return Response.status(500).entity(new Brote()).build();
        }

        return Response.status(201).entity(manager.getBrote(BroteId)).build();
    }
}
