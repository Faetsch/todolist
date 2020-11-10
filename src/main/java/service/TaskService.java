package service;

import entities.Task;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@Named
@ApplicationScoped
public class TaskService
{
    public List<Task> createTasks()
    {
        List<Task> list = new ArrayList<Task>();
        for(int i = 0 ; i < 5 ; i++)
        {
            list.add(new Task("Name" + i, "Description" + i));
        }
        return list;
    }

}
