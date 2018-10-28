/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.demojsf.jpa.control;

import com.demojsf.jpa.control.exceptions.IllegalOrphanException;
import com.demojsf.jpa.control.exceptions.NonexistentEntityException;
import com.demojsf.jpa.control.exceptions.RollbackFailureException;
import com.demojsf.jpa.model.Concepto;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.demojsf.jpa.model.Detallecontrato;
import java.util.ArrayList;
import java.util.Collection;
import com.demojsf.jpa.model.DetalleFactura;
import java.util.List;
import javax.annotation.Resource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;
import javax.transaction.UserTransaction;

/**
 *
 * @author DESARROLLO
 */
public class ConceptoJpaController implements Serializable {

    public ConceptoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    /*@Resource
    private UserTransaction utx = null;
    
    @PersistenceUnit (unitName = "org.primefaces_sentinel_war_2.1.2PU")
    private EntityManagerFactory emf = null;
    
     public EntityManager getEntityManager() {
        if(emf == null){
            emf = Persistence.createEntityManagerFactory("org.primefaces_sentinel_war_2.1.2PU");
        }
        return emf.createEntityManager();
    }
    
    private void setUserTransaction() throws NamingException{
        Context context = new InitialContext ();
        
        if(utx == null){
            utx = (UserTransaction) context.lookup ("java:app/Aboconta");
         }  
    }*/

    public void create(Concepto concepto) throws RollbackFailureException, Exception {
        if (concepto.getDetallecontratoCollection() == null) {
            concepto.setDetallecontratoCollection(new ArrayList<Detallecontrato>());
        }
        if (concepto.getDetalleFacturaCollection() == null) {
            concepto.setDetalleFacturaCollection(new ArrayList<DetalleFactura>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<Detallecontrato> attachedDetallecontratoCollection = new ArrayList<Detallecontrato>();
            for (Detallecontrato detallecontratoCollectionDetallecontratoToAttach : concepto.getDetallecontratoCollection()) {
                detallecontratoCollectionDetallecontratoToAttach = em.getReference(detallecontratoCollectionDetallecontratoToAttach.getClass(), detallecontratoCollectionDetallecontratoToAttach.getIdDetalleContratoConcep());
                attachedDetallecontratoCollection.add(detallecontratoCollectionDetallecontratoToAttach);
            }
            concepto.setDetallecontratoCollection(attachedDetallecontratoCollection);
            Collection<DetalleFactura> attachedDetalleFacturaCollection = new ArrayList<DetalleFactura>();
            for (DetalleFactura detalleFacturaCollectionDetalleFacturaToAttach : concepto.getDetalleFacturaCollection()) {
                detalleFacturaCollectionDetalleFacturaToAttach = em.getReference(detalleFacturaCollectionDetalleFacturaToAttach.getClass(), detalleFacturaCollectionDetalleFacturaToAttach.getIdDetalle());
                attachedDetalleFacturaCollection.add(detalleFacturaCollectionDetalleFacturaToAttach);
            }
            concepto.setDetalleFacturaCollection(attachedDetalleFacturaCollection);
            em.persist(concepto);
            for (Detallecontrato detallecontratoCollectionDetallecontrato : concepto.getDetallecontratoCollection()) {
                Concepto oldIdconcOfDetallecontratoCollectionDetallecontrato = detallecontratoCollectionDetallecontrato.getIdconc();
                detallecontratoCollectionDetallecontrato.setIdconc(concepto);
                detallecontratoCollectionDetallecontrato = em.merge(detallecontratoCollectionDetallecontrato);
                if (oldIdconcOfDetallecontratoCollectionDetallecontrato != null) {
                    oldIdconcOfDetallecontratoCollectionDetallecontrato.getDetallecontratoCollection().remove(detallecontratoCollectionDetallecontrato);
                    oldIdconcOfDetallecontratoCollectionDetallecontrato = em.merge(oldIdconcOfDetallecontratoCollectionDetallecontrato);
                }
            }
            for (DetalleFactura detalleFacturaCollectionDetalleFactura : concepto.getDetalleFacturaCollection()) {
                Concepto oldIdconcOfDetalleFacturaCollectionDetalleFactura = detalleFacturaCollectionDetalleFactura.getIdconc();
                detalleFacturaCollectionDetalleFactura.setIdconc(concepto);
                detalleFacturaCollectionDetalleFactura = em.merge(detalleFacturaCollectionDetalleFactura);
                if (oldIdconcOfDetalleFacturaCollectionDetalleFactura != null) {
                    oldIdconcOfDetalleFacturaCollectionDetalleFactura.getDetalleFacturaCollection().remove(detalleFacturaCollectionDetalleFactura);
                    oldIdconcOfDetalleFacturaCollectionDetalleFactura = em.merge(oldIdconcOfDetalleFacturaCollectionDetalleFactura);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Concepto concepto) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Concepto persistentConcepto = em.find(Concepto.class, concepto.getIdconcepto());
            Collection<Detallecontrato> detallecontratoCollectionOld = persistentConcepto.getDetallecontratoCollection();
            Collection<Detallecontrato> detallecontratoCollectionNew = concepto.getDetallecontratoCollection();
            Collection<DetalleFactura> detalleFacturaCollectionOld = persistentConcepto.getDetalleFacturaCollection();
            Collection<DetalleFactura> detalleFacturaCollectionNew = concepto.getDetalleFacturaCollection();
            List<String> illegalOrphanMessages = null;
            for (Detallecontrato detallecontratoCollectionOldDetallecontrato : detallecontratoCollectionOld) {
                if (!detallecontratoCollectionNew.contains(detallecontratoCollectionOldDetallecontrato)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Detallecontrato " + detallecontratoCollectionOldDetallecontrato + " since its idconc field is not nullable.");
                }
            }
            for (DetalleFactura detalleFacturaCollectionOldDetalleFactura : detalleFacturaCollectionOld) {
                if (!detalleFacturaCollectionNew.contains(detalleFacturaCollectionOldDetalleFactura)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain DetalleFactura " + detalleFacturaCollectionOldDetalleFactura + " since its idconc field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Detallecontrato> attachedDetallecontratoCollectionNew = new ArrayList<Detallecontrato>();
            for (Detallecontrato detallecontratoCollectionNewDetallecontratoToAttach : detallecontratoCollectionNew) {
                detallecontratoCollectionNewDetallecontratoToAttach = em.getReference(detallecontratoCollectionNewDetallecontratoToAttach.getClass(), detallecontratoCollectionNewDetallecontratoToAttach.getIdDetalleContratoConcep());
                attachedDetallecontratoCollectionNew.add(detallecontratoCollectionNewDetallecontratoToAttach);
            }
            detallecontratoCollectionNew = attachedDetallecontratoCollectionNew;
            concepto.setDetallecontratoCollection(detallecontratoCollectionNew);
            Collection<DetalleFactura> attachedDetalleFacturaCollectionNew = new ArrayList<DetalleFactura>();
            for (DetalleFactura detalleFacturaCollectionNewDetalleFacturaToAttach : detalleFacturaCollectionNew) {
                detalleFacturaCollectionNewDetalleFacturaToAttach = em.getReference(detalleFacturaCollectionNewDetalleFacturaToAttach.getClass(), detalleFacturaCollectionNewDetalleFacturaToAttach.getIdDetalle());
                attachedDetalleFacturaCollectionNew.add(detalleFacturaCollectionNewDetalleFacturaToAttach);
            }
            detalleFacturaCollectionNew = attachedDetalleFacturaCollectionNew;
            concepto.setDetalleFacturaCollection(detalleFacturaCollectionNew);
            concepto = em.merge(concepto);
            for (Detallecontrato detallecontratoCollectionNewDetallecontrato : detallecontratoCollectionNew) {
                if (!detallecontratoCollectionOld.contains(detallecontratoCollectionNewDetallecontrato)) {
                    Concepto oldIdconcOfDetallecontratoCollectionNewDetallecontrato = detallecontratoCollectionNewDetallecontrato.getIdconc();
                    detallecontratoCollectionNewDetallecontrato.setIdconc(concepto);
                    detallecontratoCollectionNewDetallecontrato = em.merge(detallecontratoCollectionNewDetallecontrato);
                    if (oldIdconcOfDetallecontratoCollectionNewDetallecontrato != null && !oldIdconcOfDetallecontratoCollectionNewDetallecontrato.equals(concepto)) {
                        oldIdconcOfDetallecontratoCollectionNewDetallecontrato.getDetallecontratoCollection().remove(detallecontratoCollectionNewDetallecontrato);
                        oldIdconcOfDetallecontratoCollectionNewDetallecontrato = em.merge(oldIdconcOfDetallecontratoCollectionNewDetallecontrato);
                    }
                }
            }
            for (DetalleFactura detalleFacturaCollectionNewDetalleFactura : detalleFacturaCollectionNew) {
                if (!detalleFacturaCollectionOld.contains(detalleFacturaCollectionNewDetalleFactura)) {
                    Concepto oldIdconcOfDetalleFacturaCollectionNewDetalleFactura = detalleFacturaCollectionNewDetalleFactura.getIdconc();
                    detalleFacturaCollectionNewDetalleFactura.setIdconc(concepto);
                    detalleFacturaCollectionNewDetalleFactura = em.merge(detalleFacturaCollectionNewDetalleFactura);
                    if (oldIdconcOfDetalleFacturaCollectionNewDetalleFactura != null && !oldIdconcOfDetalleFacturaCollectionNewDetalleFactura.equals(concepto)) {
                        oldIdconcOfDetalleFacturaCollectionNewDetalleFactura.getDetalleFacturaCollection().remove(detalleFacturaCollectionNewDetalleFactura);
                        oldIdconcOfDetalleFacturaCollectionNewDetalleFactura = em.merge(oldIdconcOfDetalleFacturaCollectionNewDetalleFactura);
                    }
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = concepto.getIdconcepto();
                if (findConcepto(id) == null) {
                    throw new NonexistentEntityException("The concepto with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Concepto concepto;
            try {
                concepto = em.getReference(Concepto.class, id);
                concepto.getIdconcepto();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The concepto with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Detallecontrato> detallecontratoCollectionOrphanCheck = concepto.getDetallecontratoCollection();
            for (Detallecontrato detallecontratoCollectionOrphanCheckDetallecontrato : detallecontratoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Concepto (" + concepto + ") cannot be destroyed since the Detallecontrato " + detallecontratoCollectionOrphanCheckDetallecontrato + " in its detallecontratoCollection field has a non-nullable idconc field.");
            }
            Collection<DetalleFactura> detalleFacturaCollectionOrphanCheck = concepto.getDetalleFacturaCollection();
            for (DetalleFactura detalleFacturaCollectionOrphanCheckDetalleFactura : detalleFacturaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Concepto (" + concepto + ") cannot be destroyed since the DetalleFactura " + detalleFacturaCollectionOrphanCheckDetalleFactura + " in its detalleFacturaCollection field has a non-nullable idconc field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(concepto);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Concepto> findConceptoEntities() {
        return findConceptoEntities(true, -1, -1);
    }

    public List<Concepto> findConceptoEntities(int maxResults, int firstResult) {
        return findConceptoEntities(false, maxResults, firstResult);
    }

    private List<Concepto> findConceptoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Concepto.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Concepto findConcepto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Concepto.class, id);
        } finally {
            em.close();
        }
    }

    public int getConceptoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Concepto> rt = cq.from(Concepto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
