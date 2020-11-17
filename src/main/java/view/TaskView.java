package view;

import entities.Task;
import entities.TaskPriority;
import entities.UserLoginDetails;
import org.primefaces.PrimeFaces;
import org.primefaces.context.PrimeExternalContext;
import org.primefaces.context.PrimeFacesContext;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;
import service.TaskService;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.SecurityContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Named
@ViewScoped
public class TaskView implements Serializable
{
    private List<TaskPriority> taskPriorities;
    private List<Task> tasks;
    private Task selectedTask;

    @Inject
    TaskService taskService;
    @Inject
    SecurityContext sc;


    @PostConstruct
    public void init()
    {
        tasks = taskService.getAllNonDeletedTasksByUsername(sc.getCallerPrincipal().getName());
        taskPriorities = taskService.getAllPriorities();
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTaskService(TaskService taskService)
    {
        this.taskService = taskService;
    }

    public List<TaskPriority> getTaskPriorities() {
        return taskPriorities;
    }

    public void setTaskPriorities(List<TaskPriority> taskPriorities) {
        this.taskPriorities = taskPriorities;
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
            tasks = taskService.getAllNonDeletedTasksByUsername(currUserName);
            FacesMessage msg = new FacesMessage("Task deleted", selectedTask.getName());
            FacesContext.getCurrentInstance().addMessage(null, msg);
            selectedTask = null;
        }
    }

    public void moveTaskToTrashBin()
    {
        if(selectedTask != null)
        {
            String currUserName = sc.getCallerPrincipal().getName();
            taskService.moveTaskToBin(selectedTask);
            tasks = taskService.getAllNonDeletedTasksByUsername(currUserName);
            FacesMessage msg = new FacesMessage("Task moved to bin", selectedTask.getName());
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
        tasks = taskService.getAllNonDeletedTasksByUsername(currentUser);
        FacesMessage msg = new FacesMessage("New Task added", n.getName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }


    public void onRowEdit(RowEditEvent event)
    {
        String currentUser = sc.getCallerPrincipal().getName();
        Task editedTask = ((Task)event.getObject());
        taskService.updateTask(editedTask);
        tasks = taskService.getAllNonDeletedTasksByUsername(currentUser);
        FacesMessage msg = new FacesMessage("Task Edited", editedTask.getName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void markRowAsDone()
    {
        if(selectedTask != null)
        {
            selectedTask.setDone(selectedTask.isDone() ? false : true);
            taskService.updateTask(selectedTask);
            String currentUser = sc.getCallerPrincipal().getName();
            tasks = taskService.getAllNonDeletedTasksByUsername(currentUser);
        }
    }

    public List<TaskPriority> listAllTaskPriorities()
    {
        return taskService.getAllPriorities();
    }

    public void viewHistory()
    {
        PrimeFaces.current().dialog().openDynamic("taskhistory");
    }
    public void viewTrashBin()
    {
        //username als parameter vorbereiten für die nächste view - primefaces dialog framework
        String username = sc.getCallerPrincipal().getName();
        Map<String, Object> params = PrimeFacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        params.put("username", username);
        PrimeFaces.current().dialog().openDynamic("trashbin", null, null);
    }
}
