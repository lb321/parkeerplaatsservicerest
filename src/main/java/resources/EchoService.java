package resources;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("/echo")
public class EchoService {
    @GET
    @Produces("application/json")
    @Path("/{message}")
    public String echoMessage(@PathParam("message") String message){
        JsonArrayBuilder jab = Json.createArrayBuilder();
        jab.add(message);
        return jab.build().toString();
    }
}
