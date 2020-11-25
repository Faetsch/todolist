package service;

import entities.TrashbinDeletionConfig;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.*;
import javax.inject.Inject;

@Startup
@Singleton
public class TrashbinScheduler
{
    @Resource
    private TimerService timerService;

    @Inject
    DatabaseService ds;

    @Inject
    TrashbinDeletionService ts;

    @PostConstruct
    private void init()
    {
        updateScheduleConfig();
    }

    public void updateScheduleConfig()
    {
        for(Timer t : timerService.getTimers())
        {
            t.cancel();
        }

        TrashbinDeletionConfig conf = ds.getTrashbinDeletionConfig();
        if(conf != null)
        {
            ScheduleExpression schedule = createSchedule(conf.getSecond(), conf.getMinute(), conf.getHour(), conf.getDayOfWeek(), conf.getDayOfMonth(), conf.getMonth(), conf.getYear());
            timerService.createCalendarTimer(schedule);
        }
        else
        {
            TrashbinDeletionConfig newConfig = ts.defaultConfig();
            ds.updateDeletionConfig(newConfig);
            updateScheduleConfig();
        }
    }

//  @Schedule(second = "*/5", minute = "*", hour = "*", persistent = false)
    @Timeout
    public void atSchedule(Timer timer)
    {
        ts.deleteOverdueTasksInTrashbin();
    }

    private ScheduleExpression createSchedule(String second, String minute, String hour, String dayOfWeek, String dayOfMonth, String month, String year)
    {
        ScheduleExpression expression = new ScheduleExpression();
        expression.second(second);
        expression.minute(minute);
        expression.hour(hour);
        expression.dayOfWeek(dayOfWeek);
        expression.dayOfMonth(dayOfMonth);
        expression.month(month);
        expression.year(year);

        return expression;
    }

    public void setTimerService(TimerService timerService)
    {
        this.timerService = timerService;
    }
}
