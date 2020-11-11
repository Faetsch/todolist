package security;

import javax.enterprise.context.ApplicationScoped;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;
import javax.security.enterprise.identitystore.IdentityStore;
import java.util.Arrays;
import java.util.HashSet;

@ApplicationScoped
//DatabaseIdentityStoreDefinition
public class DBIdentityStore implements IdentityStore
{
    @Override
    public CredentialValidationResult validate(Credential credential)
    {
        UsernamePasswordCredential login = (UsernamePasswordCredential) credential;

        if(login.getCaller().equals("admin") && login.getPasswordAsString().equals("test"))
        {
            return new CredentialValidationResult("admin", new HashSet<>(Arrays.asList("ADMIN")));
        }
        else if(login.getCaller().equals("user") && login.getPasswordAsString().equals("test"))
        {
            return new CredentialValidationResult("user", new HashSet<>(Arrays.asList("USER")));
        }
        else return CredentialValidationResult.NOT_VALIDATED_RESULT;
    }
}
