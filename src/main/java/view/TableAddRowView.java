package view;

import entities.Task;
import org.primefaces.event.CellEditEvent;
import service.TaskService;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class TableAddRowView implements Serializable
{
    private List<Task> tasks;
    private Task selectedTask;

    @Inject
    TaskService taskService;

    @PostConstruct
    public void init()
    {
        tasks = taskService.createTasks();
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
            tasks.remove(selectedTask);
            FacesMessage msg = new FacesMessage("Task deleted", selectedTask.getName());
            FacesContext.getCurrentInstance().addMessage(null, msg);
            selectedTask = null;
        }
    }
    public void onAddNew()
    {
        Task n = new Task("Empty", "Empty");
        tasks.add(n);
        FacesMessage msg = new FacesMessage("New Task added", n.getName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onCellEdit(CellEditEvent event) {
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
        }
    }
}
