package resources;

import entities.Etudiant;
import entities.EtudiantList;
import entities.Option;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import metiers.EtudiantBusiness;

@Path("etudiants")
public class EtudiantResources {
    public static EtudiantBusiness etuB = new EtudiantBusiness();

    public EtudiantResources() {
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addEtudiant(Etudiant e) {
        if (e != null && e.getOption() != null) {
            Option opt = OptionResources.optBusiness.getOptionByCode(e.getOption().getCodeOption());
            if (opt == null) {
                return Response.status(404).build();
            }
            e.setOption(opt);
            return etuB.addEtudiant(e) ? Response.status(200).build() : Response.status(404).build();
        } else {
            return Response.status(404).build();
        }
    }

    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllEtudiants() {
        return Response.status(200).entity(etuB.getAllEtudiants()).build();
    }

    @GET
    @Path("{identifiant}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEtudiantByIdentifiant(@PathParam("identifiant") String identifiant) {
        Etudiant e = etuB.getEtudiantByIdentifiant(identifiant);
        return e != null ? Response.status(200).entity(e).build() : Response.status(404).build();
    }

    @DELETE
    @Path("{identifiant}")
    public Response deleteEtudiant(@PathParam("identifiant") String identifiant) {
        return etuB.deleteEtudiant(identifiant) ? Response.status(204).build() : Response.status(404).build();
    }

    @PUT
    @Path("{identifiant}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateEtudiant(@PathParam("identifiant") String identifiant, Etudiant e) {
        return etuB.updateEtudiant(identifiant, e) ? Response.status(200).entity(e).build() : Response.status(404).build();
    }

    @GET
    @Path("option")
    @Produces({"application/xml"})
    public Response getEtudiantsByOption(@QueryParam("codeOption") int codeOption) {
        Option option = OptionResources.optBusiness.getOptionByCode(codeOption);
        if (option == null) {
            return Response.status(404).build();
        } else {
            List<Etudiant> copies = new ArrayList();

            for(Etudiant e : etuB.getEtudiantsByOption(option)) {
                copies.add(new Etudiant(e.getIdentifiant(), e.getNom(), e.getPrenom(), (Option)null, e.getAnneeEtude(), e.getEmail()));
            }

            return Response.status(200).entity(new EtudiantList(copies)).build();
        }
    }
}