package security;

import javax.security.enterprise.SecurityContext;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.HttpHeaders;
import java.io.IOException;

@PreMatching
public class CustomAuthentication implements ContainerRequestFilter
{
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException
    {
        System.out.println("hi");
        String authHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
    }
}
