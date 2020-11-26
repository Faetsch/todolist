package view;

import entities.UserLoginDetails;
import dao.UserLoginDetailsDAO;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class UserCreateView
{
    private String username;
    private String password;

    @Inject
    private UserLoginDetailsDAO userlogindetailsDAO;

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
            userlogindetailsDAO.createUserLoginDetails(new UserLoginDetails(username, password));
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
