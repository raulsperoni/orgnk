package mgcoders.uy.rest;

import mgcoders.uy.model.Actividad;
import mgcoders.uy.service.activities.ActivityService;
import mgcoders.uy.service.admin.AdminService;
import mgcoders.uy.service.common.StatsService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by raul on 24/11/15.
 */
@Path("/stats")
@RequestScoped
public class LogRESTService {

    @Inject
    private StatsService statsService;

    @Inject
    private ActivityService activityService;

    @Inject
    private AdminService adminService;

    @Inject
    private Logger log;

    @GET
    @Path("persona/count/{token}")
    @Produces(MediaType.APPLICATION_JSON)
    public long getPersonasCount(@PathParam("token") String token) throws Exception {
        if (adminService.checkIfAdmin(token))
            return statsService.countPersonas();
        else return -1;
    }

    @GET
    @Path("persona/active/count/{token}")
    @Produces(MediaType.APPLICATION_JSON)
    public long getPersonasActiveCount(@PathParam("token") String token) throws Exception {
        if (adminService.checkIfAdmin(token))
            return statsService.countActivadas();
        else return -1;
    }

    @GET
    @Path("actividad/{token}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Actividad> getActividades(@PathParam("token") String token) throws Exception {
        if (adminService.checkIfAdmin(token))
            return activityService.actividadesAbiertas();
        else return new ArrayList<>();
    }

    @GET
    @Path("actividad/cuorum/{id}/{token}")
    @Produces(MediaType.APPLICATION_JSON)
    public long getActividadCuorum(@PathParam("token") String token, @PathParam("id") long id) throws Exception {
        if (adminService.checkIfAdmin(token))
            return activityService.getActividadCuorum(id);
        else return -1;
    }
}
