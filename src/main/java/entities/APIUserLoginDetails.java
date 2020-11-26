package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@NamedQuery(name = APIUserLoginDetails.FIND_LOGINDETAILS_BY_USERNAME, query = "SELECT d FROM APIUserLoginDetails d where d.username = :username")

@XmlRootElement
@Entity
public class APIUserLoginDetails
{
    public static final String FIND_LOGINDETAILS_BY_USERNAME = "APIUserLoginDetails.findLoginDetailsByUsername";

    public APIUserLoginDetails(@NotNull String username, @NotNull String password)
    {
        this.username = username;
        this.password = password;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Column(unique = true, columnDefinition = "VARCHAR(255)")
    private String username;

    @NotNull
    @Column(columnDefinition = "VARCHAR(255)")
    private String password;

    public APIUserLoginDetails() {

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
