package mgcoders.uy.service.discourse;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

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

    public DiscourseAPIService() {

        try {
            properties.load(getClass().getResourceAsStream(getClass().getPackage().getName() + "/" + PROPERTIES_FILE));
        } catch (IOException e) {
            log.severe("Failed to load discourse properties file: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public void searchUser(String email) {
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

        System.out.println("Output from Server .... \n");
        System.out.println(output);

    }

    public void createUser(DiscourseUser user) {

        Client client = Client.create();
        WebResource webResource = client.resource(properties.getProperty("host"));
        webResource.path(properties.getProperty("users"));
        MultivaluedMap<String, String> params = new MultivaluedMapImpl();
        params.add("name", user.getName());
        params.add("username", user.getUsername());
        params.add("email", user.getEmail());
        params.add("active", user.getActive() ? "1" : "0");
        webResource.queryParams(params);
        ClientResponse response = webResource.type("application/json")
                .post(ClientResponse.class);
        if (response.getStatus() != 201) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + response.getStatus());
        }

        System.out.println("Output from Server .... \n");
        String output = response.getEntity(String.class);
        System.out.println(output);
    }


}
