package service;

import entities.Task;
import entities.TrashbinDeletionConfig;
import entities.UserLoginDetails;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class DatabaseService
{
    @PersistenceContext(name = "todoPU")
    private EntityManager em;

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

    public void createUserLoginDetails(UserLoginDetails details)
    {
        if(findLoginDetailsByUsername(details.getUsername()) != null)
        {
            throw new IllegalArgumentException("User " + details.getUsername() + " already exists in database.");
        }

        em.persist(details);
        em.flush();
    }

    public void createTask(Task task)
    {
        //em.getTransaction().begin();
        em.persist(task);
        //em.getTransaction().commit();
    }

    public List<Task> getAllTasksByUsername(String username)
    {
        TypedQuery<Task> tasksQuery = em.createNamedQuery(Task.FIND_ALL_TASKS_BY_USERNAME, Task.class);
        tasksQuery.setParameter("username", username);
        return tasksQuery.getResultList();
    }

    public List<Task> getAllDeletedTasksByUsername(String username)
    {
        TypedQuery<Task> tasksQuery = em.createNamedQuery(Task.FIND_ALL_DELETED_TASKS_BY_USERNAME, Task.class);
        tasksQuery.setParameter("username", username);
        return tasksQuery.getResultList();
    }

    public List<Task> getAllNonDeletedTasksByUsername(String username)
    {
        TypedQuery<Task> tasksQuery = em.createNamedQuery(Task.FIND_ALL_NON_DELETED_TASKS_BY_USERNAME, Task.class);
        tasksQuery.setParameter("username", username);
        return tasksQuery.getResultList();
    }

    public List<Task> getAllTasks()
    {
        TypedQuery<Task> taskQuery = em.createNamedQuery(Task.FIND_ALL_TASKS, Task.class);
        return taskQuery.getResultList();
    }

    public Task findTaskById(int id) throws NoResultException
    {
        TypedQuery<Task> taskQuery = em.createNamedQuery(Task.FIND_TASK_BY_ID, Task.class);
        taskQuery.setParameter("id", id);
        return taskQuery.getSingleResult();
    }

    public TrashbinDeletionConfig getTrashbinDeletionConfig() throws NoResultException
    {
        TypedQuery<TrashbinDeletionConfig> trashbinDeletionConfigQuery = em.createNamedQuery(TrashbinDeletionConfig.FIND_TRASHBIN_DELETION_CONFIG, TrashbinDeletionConfig.class);
        try
        {
            TrashbinDeletionConfig result = trashbinDeletionConfigQuery.getSingleResult();
            return result;
        }
        catch (NoResultException e)
        {
            return null;
        }
    }

    public void createDeletionConfig(TrashbinDeletionConfig config)
    {
        em.persist(config);
    }

    public void deleteDeletionConfig()
    {
        TrashbinDeletionConfig config = getTrashbinDeletionConfig();
        if(config != null)
        {
            em.remove(config);
        }
    }

    public void updateDeletionConfig(TrashbinDeletionConfig config)
    {
        deleteDeletionConfig();
        createDeletionConfig(config);
    }

    public void deleteTask(Task t)
    {
        Task task = em.find(Task.class, t.getId());
        //em.getTransaction().begin();
        em.remove(task);
        //em.getTransaction().commit();
    }

    public void updateTask(Task t)
    {
        Task task = em.find(Task.class, t.getId());
        //em.getTransaction().begin();
        em.merge(t);
        //em.getTransaction().commit();
    }

}
