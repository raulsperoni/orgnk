package mgcoders.uy.discourse;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.representation.Form;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import mgcoders.uy.model.Properties;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriBuilder;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by RSperoni on 19/02/2016.
 */
@Stateless
public class DiscourseAPIService {

    private static String DISCOURSE_API_KEY;
    private static String DISCOURSE_API_USER;
    private static String DISCOURSE_API_HOST;

    @Inject
    private Logger log;
    @Inject
    private EntityManager em;
    @Inject
    private Map<String, String> organikaProperties;

    @PostConstruct
    public void init() {
        DISCOURSE_API_KEY = organikaProperties.get(Properties.DISCOURSE_API_KEY);
        DISCOURSE_API_USER = organikaProperties.get(Properties.DISCOURSE_API_USER);
        DISCOURSE_API_HOST = organikaProperties.get(Properties.DISCOURSE_API_HOST);
    }


    public DiscourseUser searchUser(String email) {

        ClientConfig config = new DefaultClientConfig();
        Client client = Client.create(config);
        WebResource service = client.resource(UriBuilder.fromUri(DISCOURSE_API_HOST).build());
        MultivaluedMap<String, String> params = new MultivaluedMapImpl();
        params.add("api_key", DISCOURSE_API_KEY);
        params.add("api_user", DISCOURSE_API_USER);
        params.add("filter", email);
        String output = service.path("admin/users/list/active.json").queryParams(params).accept(MediaType.APPLICATION_JSON).get(String.class);



        log.info("Respuesta buscar usuario: " + output);
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        JsonArray jArray = parser.parse(output).getAsJsonArray();
        DiscourseUser discourseUser = null;
        if (jArray.size() > 0) {
            discourseUser = gson.fromJson(jArray.get(0), DiscourseUser.class);
        } else {
            discourseUser = null;
        }
        return discourseUser;

    }

    public DiscourseCreateUserResponse createUser(DiscourseUser user) {

        ClientConfig config = new DefaultClientConfig();
        Client client = Client.create(config);
        WebResource webResource = client.resource(UriBuilder.fromUri(DISCOURSE_API_HOST).build());
        MultivaluedMap<String, String> params = new MultivaluedMapImpl();
        params.add("api_key", DISCOURSE_API_KEY);
        params.add("api_user", DISCOURSE_API_USER);
        Form formData = new Form();
        formData.add("username", user.getUsername());
        formData.add("name", user.getName());
        formData.add("password", "lalalalala");
        formData.add("notifications", user.getEmail());
        formData.add("active", "true");
        ClientResponse response = webResource.path("users").queryParams(params).type(MediaType.APPLICATION_FORM_URLENCODED).accept(MediaType.APPLICATION_JSON_TYPE).post(ClientResponse.class, formData);
        String output = response.getEntity(String.class);


        log.info("Respuesta crear usuario: " + output);
        Gson gson = new Gson();
        DiscourseCreateUserResponse resp = gson.fromJson(output, DiscourseCreateUserResponse.class);
        if (resp.isSuccess()) log.info(resp.getMessage());
        else log.severe(resp.getMessage());
        return resp;
    }


    public boolean aprobarUsuario(DiscourseUser user) {

        ClientConfig config = new DefaultClientConfig();
        Client client = Client.create(config);
        WebResource service = client.resource(UriBuilder.fromUri(DISCOURSE_API_HOST).build());
        MultivaluedMap<String, String> params = new MultivaluedMapImpl();
        params.add("api_key", DISCOURSE_API_KEY);
        params.add("api_user", DISCOURSE_API_USER);
        ClientResponse response = service.path("admin/users").path(String.valueOf(user.getId())).path("approve").queryParams(params).accept(MediaType.APPLICATION_JSON).put(ClientResponse.class);
        log.info("Respuesta aprobar usuario: " + response.getStatus());
        return response.getStatus() == 200;


    }


}
