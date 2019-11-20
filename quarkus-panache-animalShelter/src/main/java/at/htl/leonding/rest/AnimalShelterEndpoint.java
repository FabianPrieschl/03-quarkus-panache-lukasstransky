package at.htl.leonding.rest;

import at.htl.leonding.business.AnimalShelterPanacheRepository;
import at.htl.leonding.model.AnimalShelter;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.jboss.resteasy.annotations.Query;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

//http://localhost:8080/animalShelter
@Path("animalShelter")
@Produces(MediaType.APPLICATION_JSON)
public class AnimalShelterEndpoint {

    @Inject
    AnimalShelterPanacheRepository animalShelterPanacheRepository;

    @GET
    public Response getAllAnimalShelters(){
        return Response.ok().entity(animalShelterPanacheRepository.listAll()).build();
    }

    @GET
    @Path("count")
    public long count(){
        return animalShelterPanacheRepository.count();
    }

    @GET
    @Path("id")
    public Response getById(@QueryParam("id") Long id){
        return Response.ok().entity(animalShelterPanacheRepository.findById(id)).build();
    }

    @GET
    @Path("town")
    public Response getByTown(@QueryParam("town") String town){
        return Response.ok().entity(animalShelterPanacheRepository.findByTown(town)).build();
    }


    //{"post_code": 2222, "street": "Teststraße 11", "town": "Luftenberg"}
    @POST
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    public Response save(AnimalShelter animalShelter) {
        try{
            animalShelter = animalShelterPanacheRepository.save(animalShelter);
            return Response.ok().entity(animalShelter).build();
        }catch(Exception ex){
            return Response.serverError().build();
        }
    }

    //http://localhost:8080/animalShelter?id=3
    //{"post_code": 1234, "street": "Teststrasse 11", "town": "Luftenberg"}
    @PUT
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@QueryParam("id") Long id, AnimalShelter animalShelter){
        try{
            AnimalShelter toUpdate = animalShelterPanacheRepository.findById(id);
            toUpdate.setPost_code(animalShelter.getPost_code());
            toUpdate.setStreet(animalShelter.getStreet());
            toUpdate.setTown(animalShelter.getTown());
            animalShelter = animalShelterPanacheRepository.update(toUpdate);
            return Response.ok().entity(animalShelter).build();
        }catch(Exception ex){
            return Response.serverError().build();
        }
    }

    //http://localhost:8080/animalShelter?id=5
    @DELETE
    @Transactional
    public Response deleteAnimalShelter(@QueryParam("id") long id) {
        try{
            AnimalShelter animalShelter = animalShelterPanacheRepository.findById(id);
            animalShelterPanacheRepository.delete(animalShelter);
            return Response.ok().entity(animalShelter).build();
        }catch(Exception ex){
            return Response.serverError().build();
        }
    }
}