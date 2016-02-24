package mgcoders.uy.rest;

import mgcoders.uy.model.Votacion;
import mgcoders.uy.service.voting.VotacionService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.logging.Logger;

/**
 * Created by raul on 24/11/15.
 */
@Path("/votacion")
@RequestScoped
public class VotacionRESTService {

    @Inject
    VotacionService votacionService;

    @Inject
    private Logger log;

    @GET
    @Path("{token}/{chatId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Votacion getVotacionPorChatIdToken(@PathParam("chatId") String chatId, @PathParam("token") String token) throws Exception {
        return votacionService.getVotacionPorChatIdToken(chatId, token);
    }
}
