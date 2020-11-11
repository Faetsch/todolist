package restservice;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
public class RestTaskService
{
    @Path("{username}/tasks")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTasksForUser(@PathParam("username") String username)
    {
        //TODO Datenbankanbindung
        return null;
    }
}
