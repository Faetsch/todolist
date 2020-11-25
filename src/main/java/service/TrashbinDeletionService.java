package service;

import entities.Task;
import entities.TrashbinDeletionConfig;

import javax.ejb.Singleton;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class TrashbinDeletionService
{
    @Inject
    private DatabaseService ds;

    public void deleteOverdueTasksInTrashbin()
    {
        LocalDateTime deletionThreshold = getDeletionThresholdFromConfig();
        if(deletionThreshold == null)
        {
            deletionThreshold = LocalDateTime.now();
        }

        List<Task> tasks = ds.getAllTasks();
        for(Task task : tasks)
        {
            if(task.getDeadline() != null && task.isDeleted())
            {
                Duration d = Duration.between(deletionThreshold, task.getDeadline());
                if(d.isNegative())
                {
                    ds.deleteTask(task);
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
        TrashbinDeletionConfig configFromDB = ds.getTrashbinDeletionConfig();
        return configFromDB == null ? defaultConfig() : configFromDB;
    }

    public LocalDateTime getDeletionThresholdFromConfig()
    {
        return getDeletionConfig().getDeletionThreshold();
    }

    public void updateTrashbinConfig(TrashbinDeletionConfig config)
    {
        ds.updateDeletionConfig(config);
    }
}
