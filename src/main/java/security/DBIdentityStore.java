package security;

import entities.UserLoginDetails;
import service.DatabaseService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;
import javax.security.enterprise.identitystore.IdentityStore;
import java.util.Arrays;
import java.util.HashSet;

@ApplicationScoped
@DatabaseIdentityStoreDefinition(
        dataSourceLookup = "java:/DemoPostgresDB",
        callerQuery = "select password from userlogindetails where username = ?",
        useFor = IdentityStore.ValidationType.VALIDATE,
        hashAlgorithm = ShittyPasswordHash.class
)
public class DBIdentityStore implements IdentityStore
{
    @Inject
    DatabaseService ds;

    //wird nicht aufgerufen mit @DBIdentityStoreDefintion gesetzt
//    @Override
//    public CredentialValidationResult validate(Credential credential)
//    {
//        UsernamePasswordCredential login = (UsernamePasswordCredential) credential;
//        UserLoginDetails details = ds.findLoginDetailsByUsername(login.getCaller());
//        if(details != null && details.getPassword().equals(login.getPasswordAsString()))
//        {
//            return new CredentialValidationResult(login.getCaller(), new HashSet<>(Arrays.asList("ADMIN")));
//        }
//        else return CredentialValidationResult.NOT_VALIDATED_RESULT;
//    }
}
