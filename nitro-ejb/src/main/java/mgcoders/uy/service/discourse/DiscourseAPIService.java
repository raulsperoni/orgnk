package mgcoders.uy.service.discourse;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.MultivaluedMap;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Created by RSperoni on 19/02/2016.
 */
@Stateless
public class DiscourseAPIService {

    private static final String PROPERTIES_FILE = "discourse.properties";
    private static Properties properties = new Properties();
    @Inject
    private Logger log;


    @PostConstruct
    public void init() {
        try {
            properties.load(DiscourseAPIService.class.getResourceAsStream(PROPERTIES_FILE));
        } catch (IOException e) {
            log.severe("Failed to load discourse properties file: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public DiscourseUser searchUser(String email) {
        Client client = Client.create();
        WebResource webResource = client.resource(properties.getProperty("host"));
        MultivaluedMap<String, String> params = new MultivaluedMapImpl();
        params.add("api_key", properties.get("api_key").toString());
        params.add("filter", email);
        ClientResponse response = webResource
                .path(properties.getProperty("user_search"))
                .queryParams(params)
                .accept("application/json")
                .get(ClientResponse.class);

        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + response.getStatus());
        }

        String output = response.getEntity(String.class);
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

    public long createUser(DiscourseUser user) {

        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        Client client = Client.create(clientConfig);

        WebResource webResource = client.resource(properties.getProperty("host"));

        MultivaluedMap<String, String> params = new MultivaluedMapImpl();
        params.add("username", user.getUsername());
        params.add("name", user.getName());
        params.add("password", "eeeeeeeeee");
        params.add("email", user.getEmail());
        params.add("active", "true");
        params.add("api-key", properties.getProperty("api-key"));
        ClientResponse response = webResource.path(properties.getProperty("users")).queryParams(params).accept("application/json")
                .type("application/json").post(ClientResponse.class);

        if (response.getClientResponseStatus().getStatusCode() > 201) {
            log.severe("NAAAA: " + response.getClientResponseStatus().getStatusCode());
        }

        String output = response.getEntity(String.class);
        log.info("Respuesta crear usuario: " + output);
        Gson gson = new Gson();
        DiscourseCreateUserResponse resp = gson.fromJson(output, DiscourseCreateUserResponse.class);
        if (resp.isSuccess()) log.info(resp.getMessage());
        else log.severe(resp.getMessage());
        return resp.getUser_id();
    }


}
