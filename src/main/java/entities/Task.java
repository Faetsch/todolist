package entities;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;


@NamedQuery(name = Task.FIND_ALL_TASKS_BY_USERNAME, query = "SELECT t FROM Task t WHERE t.owner.username = :username")
@NamedQuery(name = Task.FIND_ALL_DELETED_TASKS_BY_USERNAME, query = "SELECT t FROM Task t WHERE t.owner.username = :username AND t.deleted=true")
@NamedQuery(name = Task.FIND_ALL_NON_DELETED_TASKS_BY_USERNAME, query = "SELECT t FROM Task t WHERE t.owner.username = :username AND t.deleted=false")
@NamedQuery(name = Task.FIND_TASK_BY_ID, query = "SELECT t FROM Task t WHERE t.id = :id")
@NamedQuery(name = Task.FIND_ALL_TASKS, query = "SELECT t FROM Task t")

@XmlRootElement
@Entity
public class Task implements Serializable
{
    public static final String FIND_ALL_TASKS_BY_USERNAME = "Task.findAllTasksByUserName";
    public static final String FIND_ALL_DELETED_TASKS_BY_USERNAME = "Task.findAllDeletedTasksByUsername";
    public static final String FIND_ALL_NON_DELETED_TASKS_BY_USERNAME = "Task.findAllNonDeletedTasksByUsername";
    public static final String FIND_TASK_BY_ID = "Task.findTaskById";
    public static final String FIND_ALL_TASKS = "Task.findOverdueTasks";

    private String name;
    private String description;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Version
    private int version;

    private boolean done = false;
    private boolean deleted = false;

    @Enumerated(EnumType.STRING)
    private TaskPriority priority = TaskPriority.LOW;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime deadline;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime deletedAt;

    @ManyToOne
    @JoinColumn(name = "OWNER_ID")
    private UserLoginDetails owner;

    public Task(){}

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

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


    public Task(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
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
