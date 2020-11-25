package service;

import entities.Task;
import entities.TaskPriority;
import entities.UserLoginDetails;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

    public List<Task> getAllDeletedTasksByUsername(String username)
    {
        if(username != null)
        {
            return ds.getAllDeletedTasksByUsername(username);
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

    public void setOverdueTasksToHighestPriority()
    {
        getAllOverdueTasks().stream().forEach(task ->
        {
            task.setPriority(TaskPriority.URGENT);
            ds.updateTask(task);
        });
    }

    public List<Task> getAllOverdueTasks()
    {
        List<Task> overdueTasks = new ArrayList<Task>();
        LocalDateTime currentLocalDateTime = LocalDateTime.now();
        List<Task> tasks = ds.getAllTasks();
        for(Task task : tasks)
        {
            if(task.getDeadline() != null)
            {
                Duration d = Duration.between(currentLocalDateTime, task.getDeadline());
                if(d.isNegative())
                {
                    overdueTasks.add(task);
                }
            }
        }
        return overdueTasks;
    }

    public void moveTaskToBin(Task t)
    {
        if(t != null)
        {
            t.setDeleted(true);
            t.setDeletedAt(LocalDateTime.now());
            ds.updateTask(t);
        }
    }

    public void moveTaskToBin(int id)
    {
        Task taskToDelete = ds.findTaskById(id);
        taskToDelete.setDeleted(true);
        ds.updateTask(taskToDelete);
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
