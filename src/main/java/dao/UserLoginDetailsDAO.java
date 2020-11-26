package dao;

import entities.UserLoginDetails;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

public class UserLoginDetailsDAO
{
    @PersistenceContext(name = "todoPU")
    private EntityManager em;

    @Transactional
    public void createUserLoginDetails(UserLoginDetails details)
    {
        if(findLoginDetailsByUsername(details.getUsername()) != null)
        {
            throw new IllegalArgumentException("User " + details.getUsername() + " already exists in database.");
        }
        em.persist(details);
        em.flush();
    }

    @Transactional
    public UserLoginDetails findLoginDetailsByUsername(String username)
    {
        try
        {
            TypedQuery<UserLoginDetails> detailsQuery = em.createNamedQuery(UserLoginDetails.FIND_LOGINDETAILS_BY_USERNAME, UserLoginDetails.class);
            detailsQuery.setParameter("username", username);
            return detailsQuery.getSingleResult();
        }
        catch (NoResultException e)
        {
            return null;
        }
    }
}
