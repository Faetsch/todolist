package entities;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@NamedQuery(name = Task.FIND_ALL_TASKS_BY_USERNAME, query = "SELECT t FROM Task t WHERE t.owner.username = :username")
@NamedQuery(name = Task.FIND_ALL_DELETED_TASKS_BY_USERNAME, query = "SELECT t FROM Task t WHERE t.owner.username = :username AND t.deleted=true")
@NamedQuery(name = Task.FIND_ALL_NON_DELETED_TASKS_BY_USERNAME, query = "SELECT t FROM Task t WHERE t.owner.username = :username AND t.deleted=false")

@Entity
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class Task implements Serializable
{
    public static final String FIND_ALL_TASKS_BY_USERNAME = "Task.findAllTasksByUserName";
    public static final String FIND_ALL_DELETED_TASKS_BY_USERNAME = "Task.findAllDeletedTasksByUsername";
    public static final String FIND_ALL_NON_DELETED_TASKS_BY_USERNAME = "Task.findAllNonDeletedTasksByUsername";

    private String name;
    private String description;
    @Id
    @GeneratedValue
    private int id;
    @Version
    private int version;

    private boolean done = false;
    private boolean deleted = false;

    @Enumerated(EnumType.STRING)
    private TaskPriority priority = TaskPriority.LOW;

    @Temporal(TemporalType.DATE)
    private Date deadline;

    @ManyToOne
    @JoinColumn(name = "OWNER_ID")
    private UserLoginDetails owner;

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Task(){}

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public UserLoginDetails getOwner() {
        return owner;
    }

    public void setOwner(UserLoginDetails owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
