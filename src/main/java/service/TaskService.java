package service;

import entities.Task;
import entities.TaskPriority;
import entities.UserLoginDetails;
import dao.TaskDAO;
import dao.UserLoginDetailsDAO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
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
    TaskDAO taskdao;

    @Inject
    UserLoginDetailsDAO userlogindetailsdao;

    public List<Task> getAllNonDeletedTasksByUsername(String username)
    {
        if(username != null)
        {
            return taskdao.getAllNonDeletedTasksByUsername(username);
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
            return taskdao.getAllTasksByUsername(username);
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
            return taskdao.getAllDeletedTasksByUsername(username);
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
            return userlogindetailsdao.findLoginDetailsByUsername(username);
        }
        else throw new IllegalArgumentException(username + " not registered as a user");
    }

    public void deleteTask(Task t)
    {
        if(t != null)
            taskdao.deleteTask(t);
    }

    public void setOverdueTasksToHighestPriority()
    {
        getAllOverdueTasks().stream().forEach(task ->
        {
            task.setPriority(TaskPriority.URGENT);
            taskdao.updateTask(task);
        });
    }

    public List<Task> getAllOverdueTasks()
    {
        List<Task> overdueTasks = new ArrayList<Task>();
        LocalDateTime currentLocalDateTime = LocalDateTime.now();
        List<Task> tasks = taskdao.getAllTasks();
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
            taskdao.updateTask(t);
        }
    }

    public void moveTaskToBin(int id)
    {
        Task taskToDelete = taskdao.findTaskById(id);
        taskToDelete.setDeleted(true);
        taskdao.updateTask(taskToDelete);
    }

    public void createTask(Task t)
    {
        if(t != null)
            taskdao.createTask(t);
    }

    public List<TaskPriority> getAllPriorities()
    {
        return Arrays.asList(TaskPriority.values());
    }

    public void updateTask(Task t)
    {
        taskdao.updateTask(t);
    }

}
