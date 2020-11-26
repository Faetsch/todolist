package restservice;

import entities.TrashbinDeletionConfig;
import security.UltraShittyAuthenticationService;
import service.TrashbinDeletionService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
public class TrashbinEndpoints
{
    @Inject
    TrashbinDeletionService tds;

    @Inject
    UltraShittyAuthenticationService usas;

    @Path("/trashbinconfig")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTrashbinConfig()
    {
        TrashbinDeletionConfig config = tds.getDeletionConfig();
        return Response.ok().entity(config).build();
    }

    @Path("/trashbinconfig")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response setTrashbinConfig(TrashbinDeletionConfig config)
    {
        tds.updateTrashbinConfig(config);
        return Response.ok().build();
    }

    @Path("/delete")
    @POST
    public Response deleteOverdueTasks()
    {
        tds.deleteOverdueTasksInTrashbin();
        return Response.ok().build();
    }
}
