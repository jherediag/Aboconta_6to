/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.demojsf.jpa.control;

import com.demojsf.jpa.control.exceptions.NonexistentEntityException;
import com.demojsf.jpa.control.exceptions.RollbackFailureException;
import com.demojsf.jpa.model.Comision;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.demojsf.jpa.model.Contrato;
import com.demojsf.jpa.model.Propiedad;
import com.demojsf.jpa.model.Propietarios;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author DESARROLLO
 */
public class ComisionJpaController implements Serializable {

    public ComisionJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Comision comision) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Contrato idcontrato = comision.getIdcontrato();
            if (idcontrato != null) {
                idcontrato = em.getReference(idcontrato.getClass(), idcontrato.getNumcontrato());
                comision.setIdcontrato(idcontrato);
            }
            Propiedad idpropiedad = comision.getIdpropiedad();
            if (idpropiedad != null) {
                idpropiedad = em.getReference(idpropiedad.getClass(), idpropiedad.getPropiedadPK());
                comision.setIdpropiedad(idpropiedad);
            }
            Propietarios idpropietario = comision.getIdpropietario();
            if (idpropietario != null) {
                idpropietario = em.getReference(idpropietario.getClass(), idpropietario.getIdpropietario());
                comision.setIdpropietario(idpropietario);
            }
            em.persist(comision);
            if (idcontrato != null) {
                idcontrato.getComisionCollection().add(comision);
                idcontrato = em.merge(idcontrato);
            }
            if (idpropiedad != null) {
                idpropiedad.getComisionCollection().add(comision);
                idpropiedad = em.merge(idpropiedad);
            }
            if (idpropietario != null) {
                idpropietario.getComisionCollection().add(comision);
                idpropietario = em.merge(idpropietario);
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

    public void edit(Comision comision) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Comision persistentComision = em.find(Comision.class, comision.getIdcomision());
            Contrato idcontratoOld = persistentComision.getIdcontrato();
            Contrato idcontratoNew = comision.getIdcontrato();
            Propiedad idpropiedadOld = persistentComision.getIdpropiedad();
            Propiedad idpropiedadNew = comision.getIdpropiedad();
            Propietarios idpropietarioOld = persistentComision.getIdpropietario();
            Propietarios idpropietarioNew = comision.getIdpropietario();
            if (idcontratoNew != null) {
                idcontratoNew = em.getReference(idcontratoNew.getClass(), idcontratoNew.getNumcontrato());
                comision.setIdcontrato(idcontratoNew);
            }
            if (idpropiedadNew != null) {
                idpropiedadNew = em.getReference(idpropiedadNew.getClass(), idpropiedadNew.getPropiedadPK());
                comision.setIdpropiedad(idpropiedadNew);
            }
            if (idpropietarioNew != null) {
                idpropietarioNew = em.getReference(idpropietarioNew.getClass(), idpropietarioNew.getIdpropietario());
                comision.setIdpropietario(idpropietarioNew);
            }
            comision = em.merge(comision);
            if (idcontratoOld != null && !idcontratoOld.equals(idcontratoNew)) {
                idcontratoOld.getComisionCollection().remove(comision);
                idcontratoOld = em.merge(idcontratoOld);
            }
            if (idcontratoNew != null && !idcontratoNew.equals(idcontratoOld)) {
                idcontratoNew.getComisionCollection().add(comision);
                idcontratoNew = em.merge(idcontratoNew);
            }
            if (idpropiedadOld != null && !idpropiedadOld.equals(idpropiedadNew)) {
                idpropiedadOld.getComisionCollection().remove(comision);
                idpropiedadOld = em.merge(idpropiedadOld);
            }
            if (idpropiedadNew != null && !idpropiedadNew.equals(idpropiedadOld)) {
                idpropiedadNew.getComisionCollection().add(comision);
                idpropiedadNew = em.merge(idpropiedadNew);
            }
            if (idpropietarioOld != null && !idpropietarioOld.equals(idpropietarioNew)) {
                idpropietarioOld.getComisionCollection().remove(comision);
                idpropietarioOld = em.merge(idpropietarioOld);
            }
            if (idpropietarioNew != null && !idpropietarioNew.equals(idpropietarioOld)) {
                idpropietarioNew.getComisionCollection().add(comision);
                idpropietarioNew = em.merge(idpropietarioNew);
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
                Integer id = comision.getIdcomision();
                if (findComision(id) == null) {
                    throw new NonexistentEntityException("The comision with id " + id + " no longer exists.");
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
            Comision comision;
            try {
                comision = em.getReference(Comision.class, id);
                comision.getIdcomision();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The comision with id " + id + " no longer exists.", enfe);
            }
            Contrato idcontrato = comision.getIdcontrato();
            if (idcontrato != null) {
                idcontrato.getComisionCollection().remove(comision);
                idcontrato = em.merge(idcontrato);
            }
            Propiedad idpropiedad = comision.getIdpropiedad();
            if (idpropiedad != null) {
                idpropiedad.getComisionCollection().remove(comision);
                idpropiedad = em.merge(idpropiedad);
            }
            Propietarios idpropietario = comision.getIdpropietario();
            if (idpropietario != null) {
                idpropietario.getComisionCollection().remove(comision);
                idpropietario = em.merge(idpropietario);
            }
            em.remove(comision);
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

    public List<Comision> findComisionEntities() {
        return findComisionEntities(true, -1, -1);
    }

    public List<Comision> findComisionEntities(int maxResults, int firstResult) {
        return findComisionEntities(false, maxResults, firstResult);
    }

    private List<Comision> findComisionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Comision.class));
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

    public Comision findComision(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Comision.class, id);
        } finally {
            em.close();
        }
    }

    public int getComisionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Comision> rt = cq.from(Comision.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
