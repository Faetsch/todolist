package view;

import entities.Task;
import service.TaskService;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named("tableBasicView")
@ViewScoped
public class TableBasicView implements Serializable
{
    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    private List<Task> tasks;

    @Inject
    private TaskService taskService;

    @PostConstruct
    public void init() {
        tasks = taskService.createTasks();
    }
}
