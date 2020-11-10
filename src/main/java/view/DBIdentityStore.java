package view;

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

        if(login.getCaller().equals("larry") && login.getPasswordAsString().equals("berry"))
        {
            return new CredentialValidationResult("larry", new HashSet<>(Arrays.asList("LARRY")));
        }
        else return CredentialValidationResult.NOT_VALIDATED_RESULT;
    }
}
