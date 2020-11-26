package service;

import entities.Task;
import entities.TrashbinDeletionConfig;
import dao.TaskDAO;
import dao.TrashbinDeletionConfigDAO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class TrashbinDeletionService
{
    @Inject
    private TrashbinDeletionConfigDAO trashbinDAO;

    @Inject
    private TaskDAO taskDAO;

    public void deleteOverdueTasksInTrashbin()
    {
        LocalDateTime deletionThreshold = getDeletionThresholdFromConfig();
        if(deletionThreshold == null)
        {
            deletionThreshold = LocalDateTime.now();
        }

        List<Task> tasks = taskDAO.getAllTasks();
        for(Task task : tasks)
        {
            if(task.getDeadline() != null && task.isDeleted())
            {
                Duration d = Duration.between(deletionThreshold, task.getDeadline());
                if(d.isNegative())
                {
                    taskDAO.deleteTask(task);
                }
            }
        }
    }

    public TrashbinDeletionConfig defaultConfig()
    {
        TrashbinDeletionConfig c = new TrashbinDeletionConfig();
        c.setActivated(true);
        c.setSecond("0");
        c.setMinute("*");
        c.setHour("*");
        c.setDayOfWeek("*");
        c.setDayOfMonth("*");
        c.setMonth("*");
        c.setYear("*");
        return c;
    }

    public TrashbinDeletionConfig getDeletionConfig()
    {
        TrashbinDeletionConfig configFromDB = trashbinDAO.getTrashbinDeletionConfig();
        return configFromDB == null ? defaultConfig() : configFromDB;
    }

    public LocalDateTime getDeletionThresholdFromConfig()
    {
        return getDeletionConfig().getDeletionThreshold();
    }

    public void updateTrashbinConfig(TrashbinDeletionConfig config)
    {
        trashbinDAO.updateDeletionConfig(config);
    }
}
