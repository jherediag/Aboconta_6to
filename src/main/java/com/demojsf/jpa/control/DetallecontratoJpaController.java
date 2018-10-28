/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.demojsf.jpa.control;

import com.demojsf.jpa.control.exceptions.NonexistentEntityException;
import com.demojsf.jpa.control.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.demojsf.jpa.model.Concepto;
import com.demojsf.jpa.model.Contrato;
import com.demojsf.jpa.model.Detallecontrato;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author DESARROLLO
 */
public class DetallecontratoJpaController implements Serializable {

    public DetallecontratoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Detallecontrato detallecontrato) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Concepto idconc = detallecontrato.getIdconc();
            if (idconc != null) {
                idconc = em.getReference(idconc.getClass(), idconc.getIdconcepto());
                detallecontrato.setIdconc(idconc);
            }
            Contrato idcontra = detallecontrato.getIdcontra();
            if (idcontra != null) {
                idcontra = em.getReference(idcontra.getClass(), idcontra.getNumcontrato());
                detallecontrato.setIdcontra(idcontra);
            }
            em.persist(detallecontrato);
            if (idconc != null) {
                idconc.getDetallecontratoCollection().add(detallecontrato);
                idconc = em.merge(idconc);
            }
            if (idcontra != null) {
                idcontra.getDetallecontratoCollection().add(detallecontrato);
                idcontra = em.merge(idcontra);
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

    public void edit(Detallecontrato detallecontrato) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Detallecontrato persistentDetallecontrato = em.find(Detallecontrato.class, detallecontrato.getIdDetalleContratoConcep());
            Concepto idconcOld = persistentDetallecontrato.getIdconc();
            Concepto idconcNew = detallecontrato.getIdconc();
            Contrato idcontraOld = persistentDetallecontrato.getIdcontra();
            Contrato idcontraNew = detallecontrato.getIdcontra();
            if (idconcNew != null) {
                idconcNew = em.getReference(idconcNew.getClass(), idconcNew.getIdconcepto());
                detallecontrato.setIdconc(idconcNew);
            }
            if (idcontraNew != null) {
                idcontraNew = em.getReference(idcontraNew.getClass(), idcontraNew.getNumcontrato());
                detallecontrato.setIdcontra(idcontraNew);
            }
            detallecontrato = em.merge(detallecontrato);
            if (idconcOld != null && !idconcOld.equals(idconcNew)) {
                idconcOld.getDetallecontratoCollection().remove(detallecontrato);
                idconcOld = em.merge(idconcOld);
            }
            if (idconcNew != null && !idconcNew.equals(idconcOld)) {
                idconcNew.getDetallecontratoCollection().add(detallecontrato);
                idconcNew = em.merge(idconcNew);
            }
            if (idcontraOld != null && !idcontraOld.equals(idcontraNew)) {
                idcontraOld.getDetallecontratoCollection().remove(detallecontrato);
                idcontraOld = em.merge(idcontraOld);
            }
            if (idcontraNew != null && !idcontraNew.equals(idcontraOld)) {
                idcontraNew.getDetallecontratoCollection().add(detallecontrato);
                idcontraNew = em.merge(idcontraNew);
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
                Integer id = detallecontrato.getIdDetalleContratoConcep();
                if (findDetallecontrato(id) == null) {
                    throw new NonexistentEntityException("The detallecontrato with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Detallecontrato detallecontrato;
            try {
                detallecontrato = em.getReference(Detallecontrato.class, id);
                detallecontrato.getIdDetalleContratoConcep();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The detallecontrato with id " + id + " no longer exists.", enfe);
            }
            Concepto idconc = detallecontrato.getIdconc();
            if (idconc != null) {
                idconc.getDetallecontratoCollection().remove(detallecontrato);
                idconc = em.merge(idconc);
            }
            Contrato idcontra = detallecontrato.getIdcontra();
            if (idcontra != null) {
                idcontra.getDetallecontratoCollection().remove(detallecontrato);
                idcontra = em.merge(idcontra);
            }
            em.remove(detallecontrato);
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

    public List<Detallecontrato> findDetallecontratoEntities() {
        return findDetallecontratoEntities(true, -1, -1);
    }

    public List<Detallecontrato> findDetallecontratoEntities(int maxResults, int firstResult) {
        return findDetallecontratoEntities(false, maxResults, firstResult);
    }

    private List<Detallecontrato> findDetallecontratoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Detallecontrato.class));
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

    public Detallecontrato findDetallecontrato(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Detallecontrato.class, id);
        } finally {
            em.close();
        }
    }

    public int getDetallecontratoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Detallecontrato> rt = cq.from(Detallecontrato.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
