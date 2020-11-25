package view;

import entities.Task;
import org.primefaces.PrimeFaces;
import org.primefaces.context.PrimeFacesContext;
import service.DatabaseService;
import service.TaskService;

import javax.annotation.PostConstruct;
import javax.faces.context.SessionMap;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Named
@ViewScoped
public class TrashbinView implements Serializable
{
    private List<Task> deletedTasks;
    private String username;

    @Inject
    private TaskService taskService;
    private Task selectedTask;

    @PostConstruct
    public void init()
    {
        Map<String, Object> sessionMap =  PrimeFacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        String username = (String) sessionMap.get("username");
        if(username != null)
        {
            this.username = username;
            deletedTasks = taskService.getAllDeletedTasksByUsername(username);
        }
        sessionMap.remove("username");
    }

    public List<Task> getDeletedTasks() {
        return deletedTasks;
    }

    public void setDeletedTasks(List<Task> deletedTasks) {
        this.deletedTasks = deletedTasks;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Task getSelectedTask() {
        return selectedTask;
    }

    public void setSelectedTask(Task selectedTask) {
        this.selectedTask = selectedTask;
    }

    public void restoreTask()
    {
        System.out.println("Task restored");
        selectedTask.setDeleted(false);
        taskService.updateTask(selectedTask);
    }
}
