package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@NamedQuery(name = UserLoginDetails.FIND_LOGINDETAILS_BY_USERNAME, query = "SELECT d FROM UserLoginDetails d where d.username = :username")

@Entity
public class UserLoginDetails
{
    public static final String FIND_LOGINDETAILS_BY_USERNAME = "UserLoginDetails.findLoginDetailsByUsername";

    public UserLoginDetails(@NotNull String username, @NotNull String password)
    {
        this.username = username;
        this.password = password;
    }

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Column(unique = true, columnDefinition = "VARCHAR(255)")
    private String username;

    @NotNull
    @Column(columnDefinition = "VARCHAR(255)")
    private String password;

    @OneToMany(mappedBy = "owner")
    private List<Task> tasks;

    public UserLoginDetails() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
