package dao;

import entities.TrashbinDeletionConfig;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

public class TrashbinDeletionConfigDAO
{
    @PersistenceContext(name = "todoPU")
    private EntityManager em;

    @Transactional
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

    @Transactional
    public void createDeletionConfig(TrashbinDeletionConfig config)
    {
        em.persist(config);
    }

    @Transactional
    public void deleteDeletionConfig()
    {
        TrashbinDeletionConfig config = getTrashbinDeletionConfig();
        if(config != null)
        {
            em.remove(config);
        }
    }

    @Transactional
    public void updateDeletionConfig(TrashbinDeletionConfig config)
    {
        deleteDeletionConfig();
        createDeletionConfig(config);
    }
}
