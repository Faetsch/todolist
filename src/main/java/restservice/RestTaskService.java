package restservice;

import entities.Task;
import entities.TrashbinDeletionConfig;
import security.UltraShittyAuthentificationService;
import service.TaskService;
import service.TrashbinDeletionService;

import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/")
public class RestTaskService
{
    @Inject
    TaskService ts;

    @Inject
    TrashbinDeletionService tds;

    @Inject
    UltraShittyAuthentificationService usas;

    @Path("{username}/tasks")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTasksForUser(@PathParam("username") String username, @HeaderParam("User") String user, @HeaderParam("Password") String password)
    {
        //hyperultramegashitty
        boolean authenticated = usas.authenticate(user, password);
        if(!authenticated)
        {
            //forbidden
            return Response.status(403).build();
        }
        List<Task> tasks = ts.getAllTasksByUsername(username);
        return Response.ok()
                .entity(tasks)
                .build();
    }

    @Path("{username}/tasks/active")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getActiveTasksForUser(@PathParam("username") String username)
    {
        List<Task> tasks = ts.getAllNonDeletedTasksByUsername(username);
        return Response.ok()
                .entity(tasks)
                .build();
    }

    @Path("{username}/tasks/deleted")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDeletedTasksForUser(@PathParam("username") String username)
    {
        List<Task> tasks = ts.getAllDeletedTasksByUsername(username);
        return Response.ok()
                .entity(tasks)
                .build();
    }

    @Path("{username}/tasks/{id}/delete")
    @POST
    public Response deleteTasksForUser(@PathParam("id") int id)
    {
        try
        {
            ts.moveTaskToBin(id);
            return Response.ok()
                    .build();
        }
        catch (NoResultException e)
        {
            return Response.noContent()
                    .build();
        }
    }

    @Path("/duetasks")
    @POST
    public Response setPriorityOfDueTasksToHigh()
    {
        ts.setOverdueTasksToHighestPriority();
        return Response.ok().build();
    }


    @Path("{username}/tasks/")
    @Consumes(MediaType.APPLICATION_JSON)
    @POST
    public Response createTaskForUser(Task taskToCreate)
    {
        if(taskToCreate != null)
        {
            //ts.createTask(taskToCreate);
            ts.updateTask(taskToCreate);
            return Response.ok()
                    .build();
        }
        else
        {
            return Response.status(406)
                    .build();
        }
    }

    @Path("{username}/tasks/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @PUT
    public Response editTask(Task newTask, @PathParam("id") int id)
    {
        if(newTask != null)
        {
            ts.updateTask(newTask);
            return Response.ok().build();
        }
        else
        {
            return Response.status(406).build();
        }
    }

    @Path("/delete")
    @POST
    public Response deleteOverdueTasks()
    {
        tds.deleteOverdueTasksInTrashbin();
        return Response.ok().build();
    }

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

}
