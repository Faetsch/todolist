package security;

import entities.UserLoginDetails;
import service.DatabaseService;

import javax.inject.Inject;

public class ShittyAuthenticationService
{
    @Inject
    private DatabaseService ds;

    //shitty code, vorübergehend
    public boolean authenticate(UserLoginDetails requestdetails)
    {
        UserLoginDetails dbdetails = ds.findLoginDetailsByUsername(requestdetails.getUsername());
        if(dbdetails == null)
        {
            return false;
        }
        else
        {
            return dbdetails.getPassword().equals(requestdetails.getPassword());
        }
    }
}
