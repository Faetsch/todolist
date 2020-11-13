package view;

import entities.Task;
import entities.UserLoginDetails;
import org.primefaces.event.CellEditEvent;
import service.TaskService;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.SecurityContext;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class TaskView implements Serializable
{
    private List<Task> tasks;
    private Task selectedTask;

    @Inject
    TaskService taskService;
    @Inject
    SecurityContext sc;

    @PostConstruct
    public void init()
    {
        tasks = taskService.getAllTasksByUsername(sc.getCallerPrincipal().getName());
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTaskService(TaskService taskService)
    {
        this.taskService = taskService;
    }

    public Task getSelectedTask() {
        return selectedTask;
    }

    public void setSelectedTask(Task selectedTask) {
        this.selectedTask = selectedTask;
    }

    public void onDeleteTask()
    {
        if(selectedTask != null)
        {
            String currUserName = sc.getCallerPrincipal().getName();
            taskService.deleteTask(selectedTask);
            tasks = taskService.getAllTasksByUsername(currUserName);
            FacesMessage msg = new FacesMessage("Task deleted", selectedTask.getName());
            FacesContext.getCurrentInstance().addMessage(null, msg);
            selectedTask = null;
        }
    }
    public void onAddNew()
    {
        String currentUser = sc.getCallerPrincipal().getName();
        UserLoginDetails currUserDetails = taskService.getDetailsByUsername(currentUser);
        Task n = new Task("Empty", "Empty");

        n.setOwner(currUserDetails);
        taskService.createTask(n);
        tasks = taskService.getAllTasksByUsername(currentUser);
        FacesMessage msg = new FacesMessage("New Task added", n.getName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onCellEdit(CellEditEvent event)
    {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();

        if(newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Task edited", "From: " + oldValue + ", To:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void markRowAsDone()
    {
        if(selectedTask != null)
        {
            selectedTask.setDone(selectedTask.isDone() ? false : true);
            taskService.updateTask(selectedTask);
        }
    }
}
