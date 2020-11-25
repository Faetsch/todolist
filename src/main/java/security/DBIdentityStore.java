package security;

import javax.enterprise.context.ApplicationScoped;
import javax.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;
import javax.security.enterprise.identitystore.IdentityStore;


@ApplicationScoped
@DatabaseIdentityStoreDefinition(
        dataSourceLookup = "java:/DemoPostgresDB",
        callerQuery = "select password from userlogindetails where username = ?",
        useFor = IdentityStore.ValidationType.VALIDATE,
        hashAlgorithm = ShittyPasswordHash.class
)

public class DBIdentityStore implements IdentityStore
{
}
