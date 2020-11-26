package dao;

import entities.APIUserLoginDetails;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

public class APIUserLoginDetailsDAO
{
    @PersistenceContext(name = "todoPU")
    private EntityManager em;

    public APIUserLoginDetails findAPILoginDetailsByUsername(String username)
    {
        try
        {
            TypedQuery<APIUserLoginDetails> detailsQuery = em.createNamedQuery(APIUserLoginDetails.FIND_LOGINDETAILS_BY_USERNAME, APIUserLoginDetails.class);
            detailsQuery.setParameter("username", username);
            return detailsQuery.getSingleResult();
        }
        catch (NoResultException e)
        {
            return null;
        }
    }
}
