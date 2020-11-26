package dao;

import entities.Task;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

public class TaskDAO
{
    @PersistenceContext(name = "todoPU")
    private EntityManager em;

    @Transactional
    public void createTask(Task task)
    {
        //em.getTransaction().begin();
        em.persist(task);
        em.flush();
        //em.getTransaction().commit();
    }

    @Transactional
    public void deleteTask(Task t)
    {
        Task task = em.find(Task.class, t.getId());
        //em.getTransaction().begin();
        em.remove(task);
        //em.getTransaction().commit();
    }

    @Transactional
    public void updateTask(Task t)
    {
        Task task = em.find(Task.class, t.getId());
        //em.getTransaction().begin();
        em.merge(t);
        //em.getTransaction().commit();
    }

    @Transactional
    public List<Task> getAllTasksByUsername(String username)
    {
        TypedQuery<Task> tasksQuery = em.createNamedQuery(Task.FIND_ALL_TASKS_BY_USERNAME, Task.class);
        tasksQuery.setParameter("username", username);
        return tasksQuery.getResultList();
    }

    @Transactional
    public List<Task> getAllDeletedTasksByUsername(String username)
    {
        TypedQuery<Task> tasksQuery = em.createNamedQuery(Task.FIND_ALL_DELETED_TASKS_BY_USERNAME, Task.class);
        tasksQuery.setParameter("username", username);
        return tasksQuery.getResultList();
    }

    @Transactional
    public List<Task> getAllNonDeletedTasksByUsername(String username)
    {
        TypedQuery<Task> tasksQuery = em.createNamedQuery(Task.FIND_ALL_NON_DELETED_TASKS_BY_USERNAME, Task.class);
        tasksQuery.setParameter("username", username);
        return tasksQuery.getResultList();
    }

    @Transactional
    public List<Task> getAllTasks()
    {
        TypedQuery<Task> taskQuery = em.createNamedQuery(Task.FIND_ALL_TASKS, Task.class);
        return taskQuery.getResultList();
    }

    @Transactional
    public Task findTaskById(int id) throws NoResultException
    {
        TypedQuery<Task> taskQuery = em.createNamedQuery(Task.FIND_TASK_BY_ID, Task.class);
        taskQuery.setParameter("id", id);
        return taskQuery.getSingleResult();
    }
}
