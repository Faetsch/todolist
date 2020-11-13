package service;

import entities.Task;
import entities.UserLoginDetails;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Named
@ApplicationScoped
public class TaskService
{
    @Inject
    DatabaseService ds;

    public List<Task> getAllTasksByUsername(String username)
    {
        if(username != null)
        {
            return ds.getAllTasksByUsername(username);
        }
        else
        {
            return Collections.emptyList();
        }
    }

    public UserLoginDetails getDetailsByUsername(String username)
    {
        if(username != null)
        {
            return ds.findLoginDetailsByUsername(username);
        }
        else throw new IllegalArgumentException(username + " not registered as a user");
    }

    public void deleteTask(Task t)
    {
        if(t != null)
            ds.deleteTask(t);
    }

    public void createTask(Task t)
    {
        if(t != null)
            ds.createTask(t);
    }

    public void updateTask(Task t)
    {
        ds.updateTask(t);
    }

}
