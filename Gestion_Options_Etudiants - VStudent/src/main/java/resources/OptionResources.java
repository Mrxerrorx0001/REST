package resources;

import entities.Option;
import metiers.OptionBusiness;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.awt.*;
import java.util.List;

@Path("/options")
public class OptionResources {

    public static OptionBusiness optBusiness = new OptionBusiness();

    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes()

    public List<Option> getlist() {
        return optBusiness.getListeOptions();
    }

    @GET
    @Path("/{param}")
    @Produces(MediaType.APPLICATION_JSON)
    public Option getByCode(@PathParam("param") int code) {
        return optBusiness.getOptionByCode(code);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Option> getByDomain(@QueryParam("domain") String domain){
        return optBusiness.getOptionsByDomaine(domain);
    }

    @DELETE
    @Path("/{code}")
    public Response deleteBycode(@PathParam("code") int code) {
        return optBusiness.deleteOption(code) ? Response.status(204).build() : Response.status(404).build();
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateOption(@PathParam("id") int id, Option op) {
        return optBusiness.updateOption(id, op) ? Response.status(200).entity(op).build() : Response.status(404).build();
    }

}
