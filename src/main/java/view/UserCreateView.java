package view;

import entities.UserLoginDetails;
import service.DatabaseService;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.SecurityContext;
import javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;
import java.io.IOException;

@Named
@RequestScoped
public class UserCreateView
{
    private String username;
    private String password;

    @Inject
    DatabaseService ds;

    @Inject
    private FacesContext facesContext;

    public UserCreateView(String username, String password)
    {
        this.username = username;
        this.password = password;
    }

    public UserCreateView() {
    }

    public String createUser()
    {
        try
        {
            ds.createUserLoginDetails(new UserLoginDetails(username, password));
            return "login.xhtml";
        }
        catch (IllegalArgumentException e)
        {
            FacesMessage msg = new FacesMessage(e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return "";
        }
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
