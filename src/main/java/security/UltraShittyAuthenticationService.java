package security;

//vorübergehend, da deklaratives Sichern der resources über web.xml nicht klappt

import entities.APIUserLoginDetails;
import dao.APIUserLoginDetailsDAO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

@ApplicationScoped
@Named
public class UltraShittyAuthenticationService
{
    @Inject
    APIUserLoginDetailsDAO apiuserdao;

    //shitty
    public boolean authenticate(String username, String password)
    {
        APIUserLoginDetails detailsFromDb = apiuserdao.findAPILoginDetailsByUsername(username);

        if(detailsFromDb == null) { return false; }
        else
        {
            //System.out.println(username + " / " + detailsFromDb.getUsername() + " ---- " + password + " / " + detailsFromDb.getPassword());
            String correctPassword = detailsFromDb.getPassword();
            return password.equals(correctPassword);
        }
    }
}
