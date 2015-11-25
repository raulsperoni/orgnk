package mgcoders.uy.rest;

import mgcoders.uy.service.VotacionService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by raul on 24/11/15.
 */
@Path("/votacion")
@RequestScoped
public class VotacionRESTService {

    @Inject
    VotacionService votacionService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getVotacionPorChatIdToken(@PathParam("chatId") String chatId, @PathParam("token") String token) {
        try {
            return Response.ok().entity(votacionService.getVotacionPorChatIdToken(chatId, token)).build();
        } catch (Exception e) {
            return Response.noContent().build();
        }
    }
}
