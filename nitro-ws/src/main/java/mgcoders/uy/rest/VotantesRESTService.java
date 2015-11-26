package mgcoders.uy.rest;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import mgcoders.uy.service.VotantesService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.logging.Logger;

/**
 * Created by RSperoni on 24/11/2015.
 */
@Path("/votantes")
@RequestScoped
public class VotantesRESTService {

    @Inject
    VotantesService votantesService;

    @Inject
    private Logger log;


    @GET
    @Path("{token}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNombreUsuarioAsociado(@PathParam("token") String token) {
        try {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("nombre", votantesService.getVotantePorToken(token).getNombreUsuario());
            Gson gson = new Gson();
            return Response.ok().entity(gson.toJson(jsonObject)).build();
        } catch (Exception e) {
            log.info("No hay usuario asociado a token: " + token);
            return Response.noContent().build();
        }
    }

    @POST
    @Path("{token}/{chatId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response setAsociacionUsuarioChatId(@PathParam("token") String token, @PathParam("chatId") String chatId) {
        try {
            votantesService.setVotanteChatIdPorToken(token, chatId);
            return Response.ok().build();
        } catch (Exception e) {
            log.info("No se logro asociar chatId a token: " + token + " Ex: " + e.getMessage());
            return Response.notModified().build();
        }
    }
}
