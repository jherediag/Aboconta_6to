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
import com.demojsf.jpa.model.DetalleFactura;
import com.demojsf.jpa.model.Factura;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author DESARROLLO
 */
public class DetalleFacturaJpaController implements Serializable {

    public DetalleFacturaJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(DetalleFactura detalleFactura) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Concepto idconc = detalleFactura.getIdconc();
            if (idconc != null) {
                idconc = em.getReference(idconc.getClass(), idconc.getIdconcepto());
                detalleFactura.setIdconc(idconc);
            }
            Factura idfactu = detalleFactura.getIdfactu();
            if (idfactu != null) {
                idfactu = em.getReference(idfactu.getClass(), idfactu.getIdfactura());
                detalleFactura.setIdfactu(idfactu);
            }
            em.persist(detalleFactura);
            if (idconc != null) {
                idconc.getDetalleFacturaCollection().add(detalleFactura);
                idconc = em.merge(idconc);
            }
            if (idfactu != null) {
                idfactu.getDetalleFacturaCollection().add(detalleFactura);
                idfactu = em.merge(idfactu);
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

    public void edit(DetalleFactura detalleFactura) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            DetalleFactura persistentDetalleFactura = em.find(DetalleFactura.class, detalleFactura.getIdDetalle());
            Concepto idconcOld = persistentDetalleFactura.getIdconc();
            Concepto idconcNew = detalleFactura.getIdconc();
            Factura idfactuOld = persistentDetalleFactura.getIdfactu();
            Factura idfactuNew = detalleFactura.getIdfactu();
            if (idconcNew != null) {
                idconcNew = em.getReference(idconcNew.getClass(), idconcNew.getIdconcepto());
                detalleFactura.setIdconc(idconcNew);
            }
            if (idfactuNew != null) {
                idfactuNew = em.getReference(idfactuNew.getClass(), idfactuNew.getIdfactura());
                detalleFactura.setIdfactu(idfactuNew);
            }
            detalleFactura = em.merge(detalleFactura);
            if (idconcOld != null && !idconcOld.equals(idconcNew)) {
                idconcOld.getDetalleFacturaCollection().remove(detalleFactura);
                idconcOld = em.merge(idconcOld);
            }
            if (idconcNew != null && !idconcNew.equals(idconcOld)) {
                idconcNew.getDetalleFacturaCollection().add(detalleFactura);
                idconcNew = em.merge(idconcNew);
            }
            if (idfactuOld != null && !idfactuOld.equals(idfactuNew)) {
                idfactuOld.getDetalleFacturaCollection().remove(detalleFactura);
                idfactuOld = em.merge(idfactuOld);
            }
            if (idfactuNew != null && !idfactuNew.equals(idfactuOld)) {
                idfactuNew.getDetalleFacturaCollection().add(detalleFactura);
                idfactuNew = em.merge(idfactuNew);
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
                Integer id = detalleFactura.getIdDetalle();
                if (findDetalleFactura(id) == null) {
                    throw new NonexistentEntityException("The detalleFactura with id " + id + " no longer exists.");
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
            DetalleFactura detalleFactura;
            try {
                detalleFactura = em.getReference(DetalleFactura.class, id);
                detalleFactura.getIdDetalle();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The detalleFactura with id " + id + " no longer exists.", enfe);
            }
            Concepto idconc = detalleFactura.getIdconc();
            if (idconc != null) {
                idconc.getDetalleFacturaCollection().remove(detalleFactura);
                idconc = em.merge(idconc);
            }
            Factura idfactu = detalleFactura.getIdfactu();
            if (idfactu != null) {
                idfactu.getDetalleFacturaCollection().remove(detalleFactura);
                idfactu = em.merge(idfactu);
            }
            em.remove(detalleFactura);
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

    public List<DetalleFactura> findDetalleFacturaEntities() {
        return findDetalleFacturaEntities(true, -1, -1);
    }

    public List<DetalleFactura> findDetalleFacturaEntities(int maxResults, int firstResult) {
        return findDetalleFacturaEntities(false, maxResults, firstResult);
    }

    private List<DetalleFactura> findDetalleFacturaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(DetalleFactura.class));
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

    public DetalleFactura findDetalleFactura(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DetalleFactura.class, id);
        } finally {
            em.close();
        }
    }

    public int getDetalleFacturaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<DetalleFactura> rt = cq.from(DetalleFactura.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
