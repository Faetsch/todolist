package entities;

//import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;


@NamedQuery(name = Task.FIND_ALL_TASKS_BY_USERNAME, query = "SELECT t FROM Task t WHERE t.owner.username = :username")

@Entity
//@Audited
public class Task implements Serializable
{
    public static final String FIND_ALL_TASKS_BY_USERNAME = "Task.findAllTasksByUserName";

    private String name;
    private String description;
    @Id
    @GeneratedValue
    private int id;
    @Version
    private int version;

    private boolean done = false;

    @ManyToOne
    @JoinColumn(name = "OWNER_ID")
    private UserLoginDetails owner;

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
