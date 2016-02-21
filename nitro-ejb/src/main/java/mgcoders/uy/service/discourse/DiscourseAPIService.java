package mgcoders.uy.service.discourse;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.LoggingFilter;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
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
        webResource.path(properties.getProperty("user_search"));
        MultivaluedMap<String, String> params = new MultivaluedMapImpl();
        params.add("api_key", properties.get("api_key").toString());
        params.add("filter", email);
        webResource.queryParams(params);
        ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);

        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + response.getStatus());
        }

        String output = response.getEntity(String.class);

        Gson gson = new Gson();
        DiscourseUser discourseUser = gson.fromJson(output, DiscourseUser.class);

        return discourseUser;

    }

    public long createUser(DiscourseUser user) {

        Client client = Client.create();
        client.addFilter(new LoggingFilter(System.out));
        WebResource webResource = client.resource(properties.getProperty("host"));
        webResource.path(properties.getProperty("users"));
        MultivaluedMap<String, String> params = new MultivaluedMapImpl();
        params.add("api_key", "6275dd6d39023d1ff4ed4bffb5bfd0f47a3a04aec92f020072d8048c2093eb7e");
        params.add("name", user.getName());
        params.add("username", user.getUsername());
        params.add("email", user.getEmail());
        params.add("active", "1");
        params.add("password", "valenzo1");

        ClientResponse response =
                webResource.type(MediaType.
                        APPLICATION_FORM_URLENCODED_TYPE)
                        .post(ClientResponse.class, params);

        if (response.getStatus() != 201 || response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + response.getStatus());
        }

        System.out.println("Output from Server .... \n");
        String output = response.getEntity(String.class);
        Gson gson = new Gson();
        DiscourseCreateUserResponse resp = gson.fromJson(output, DiscourseCreateUserResponse.class);
        if (resp.isSuccess()) log.info(resp.getMessage());
        else log.severe(resp.getMessage());
        return resp.getUser_id();
    }


}
