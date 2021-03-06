package view;

import security.DBIdentityStore;

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
import java.io.IOException;

@Named
@RequestScoped
public class UserLoginView
{
    private String username;
    private String password;

    @Inject
    private SecurityContext securityContext;

    @Inject
    private ExternalContext externalContext;

    @Inject
    private FacesContext facesContext;

    @Inject
    private DBIdentityStore is;

    public UserLoginView(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public UserLoginView() {
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

    public void login() throws IOException
    {
        switch (continueAuthentication())
        {
            case SEND_CONTINUE:
                facesContext.responseComplete();
                break;
            case SEND_FAILURE:
                FacesMessage succMsg = new FacesMessage("Login Failed");
                facesContext.addMessage("messages", succMsg);
                break;
            case SUCCESS:
                FacesMessage errMsg = new FacesMessage("Login Succeeded");
                facesContext.addMessage("messages", errMsg);
                externalContext.redirect(externalContext.getRequestContextPath() + "/tasks/tasks.xhtml");
                break;
            case NOT_DONE:
        }
    }

    private AuthenticationStatus continueAuthentication() {
        return securityContext.authenticate(
                (HttpServletRequest) externalContext.getRequest(),
                (HttpServletResponse) externalContext.getResponse(),
                AuthenticationParameters.withParams()
                        .credential(new UsernamePasswordCredential(username, password))
        );
    }
}
