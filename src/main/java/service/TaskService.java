package service;

import entities.Task;
import entities.TaskPriority;
import entities.UserLoginDetails;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Named
@ApplicationScoped
public class TaskService
{
    @Inject
    DatabaseService ds;

    public List<Task> getAllNonDeletedTasksByUsername(String username)
    {
        if(username != null)
        {
            return ds.getAllNonDeletedTasksByUsername(username);
        }
        else
        {
            return Collections.emptyList();
        }
    }

    public List<Task> getAllDeletedTasksByUsername(String username)
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

    public void moveTaskToBin(Task t)
    {
        if(t != null)
        {
            t.setDeleted(true);
            ds.updateTask(t);
        }
    }

    public void createTask(Task t)
    {
        if(t != null)
            ds.createTask(t);
    }

    public List<TaskPriority> getAllPriorities()
    {
        return Arrays.asList(TaskPriority.values());
    }

    public void updateTask(Task t)
    {
        ds.updateTask(t);
    }

}
