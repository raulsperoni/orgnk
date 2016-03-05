package mgcoders.uy.rest;

import mgcoders.uy.service.common.StatsService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.logging.Logger;

/**
 * Created by raul on 24/11/15.
 */
@Path("/log")
@RequestScoped
public class LogRESTService {

    @Inject
    StatsService statsService;

    @Inject
    private Logger log;

    @GET
    @Path("persona/count")
    @Produces(MediaType.APPLICATION_JSON)
    public long getPersonasCount() throws Exception {
        return statsService.countPersonas();
    }
}
