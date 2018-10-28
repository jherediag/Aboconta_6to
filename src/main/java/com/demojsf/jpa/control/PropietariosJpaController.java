/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.demojsf.jpa.control;

import com.demojsf.jpa.control.exceptions.IllegalOrphanException;
import com.demojsf.jpa.control.exceptions.NonexistentEntityException;
import com.demojsf.jpa.control.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.demojsf.jpa.model.Comision;
import java.util.ArrayList;
import java.util.Collection;
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
public class PropietariosJpaController implements Serializable {

    public PropietariosJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Propietarios propietarios) throws RollbackFailureException, Exception {
        if (propietarios.getComisionCollection() == null) {
            propietarios.setComisionCollection(new ArrayList<Comision>());
        }
        if (propietarios.getPropiedadCollection() == null) {
            propietarios.setPropiedadCollection(new ArrayList<Propiedad>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<Comision> attachedComisionCollection = new ArrayList<Comision>();
            for (Comision comisionCollectionComisionToAttach : propietarios.getComisionCollection()) {
                comisionCollectionComisionToAttach = em.getReference(comisionCollectionComisionToAttach.getClass(), comisionCollectionComisionToAttach.getIdcomision());
                attachedComisionCollection.add(comisionCollectionComisionToAttach);
            }
            propietarios.setComisionCollection(attachedComisionCollection);
            Collection<Propiedad> attachedPropiedadCollection = new ArrayList<Propiedad>();
            for (Propiedad propiedadCollectionPropiedadToAttach : propietarios.getPropiedadCollection()) {
                propiedadCollectionPropiedadToAttach = em.getReference(propiedadCollectionPropiedadToAttach.getClass(), propiedadCollectionPropiedadToAttach.getPropiedadPK());
                attachedPropiedadCollection.add(propiedadCollectionPropiedadToAttach);
            }
            propietarios.setPropiedadCollection(attachedPropiedadCollection);
            em.persist(propietarios);
            for (Comision comisionCollectionComision : propietarios.getComisionCollection()) {
                Propietarios oldIdpropietarioOfComisionCollectionComision = comisionCollectionComision.getIdpropietario();
                comisionCollectionComision.setIdpropietario(propietarios);
                comisionCollectionComision = em.merge(comisionCollectionComision);
                if (oldIdpropietarioOfComisionCollectionComision != null) {
                    oldIdpropietarioOfComisionCollectionComision.getComisionCollection().remove(comisionCollectionComision);
                    oldIdpropietarioOfComisionCollectionComision = em.merge(oldIdpropietarioOfComisionCollectionComision);
                }
            }
            for (Propiedad propiedadCollectionPropiedad : propietarios.getPropiedadCollection()) {
                Propietarios oldIdpropietarioOfPropiedadCollectionPropiedad = propiedadCollectionPropiedad.getIdpropietario();
                propiedadCollectionPropiedad.setIdpropietario(propietarios);
                propiedadCollectionPropiedad = em.merge(propiedadCollectionPropiedad);
                if (oldIdpropietarioOfPropiedadCollectionPropiedad != null) {
                    oldIdpropietarioOfPropiedadCollectionPropiedad.getPropiedadCollection().remove(propiedadCollectionPropiedad);
                    oldIdpropietarioOfPropiedadCollectionPropiedad = em.merge(oldIdpropietarioOfPropiedadCollectionPropiedad);
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

    public void edit(Propietarios propietarios) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Propietarios persistentPropietarios = em.find(Propietarios.class, propietarios.getIdpropietario());
            Collection<Comision> comisionCollectionOld = persistentPropietarios.getComisionCollection();
            Collection<Comision> comisionCollectionNew = propietarios.getComisionCollection();
            Collection<Propiedad> propiedadCollectionOld = persistentPropietarios.getPropiedadCollection();
            Collection<Propiedad> propiedadCollectionNew = propietarios.getPropiedadCollection();
            List<String> illegalOrphanMessages = null;
            for (Comision comisionCollectionOldComision : comisionCollectionOld) {
                if (!comisionCollectionNew.contains(comisionCollectionOldComision)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Comision " + comisionCollectionOldComision + " since its idpropietario field is not nullable.");
                }
            }
            for (Propiedad propiedadCollectionOldPropiedad : propiedadCollectionOld) {
                if (!propiedadCollectionNew.contains(propiedadCollectionOldPropiedad)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Propiedad " + propiedadCollectionOldPropiedad + " since its idpropietario field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Comision> attachedComisionCollectionNew = new ArrayList<Comision>();
            for (Comision comisionCollectionNewComisionToAttach : comisionCollectionNew) {
                comisionCollectionNewComisionToAttach = em.getReference(comisionCollectionNewComisionToAttach.getClass(), comisionCollectionNewComisionToAttach.getIdcomision());
                attachedComisionCollectionNew.add(comisionCollectionNewComisionToAttach);
            }
            comisionCollectionNew = attachedComisionCollectionNew;
            propietarios.setComisionCollection(comisionCollectionNew);
            Collection<Propiedad> attachedPropiedadCollectionNew = new ArrayList<Propiedad>();
            for (Propiedad propiedadCollectionNewPropiedadToAttach : propiedadCollectionNew) {
                propiedadCollectionNewPropiedadToAttach = em.getReference(propiedadCollectionNewPropiedadToAttach.getClass(), propiedadCollectionNewPropiedadToAttach.getPropiedadPK());
                attachedPropiedadCollectionNew.add(propiedadCollectionNewPropiedadToAttach);
            }
            propiedadCollectionNew = attachedPropiedadCollectionNew;
            propietarios.setPropiedadCollection(propiedadCollectionNew);
            propietarios = em.merge(propietarios);
            for (Comision comisionCollectionNewComision : comisionCollectionNew) {
                if (!comisionCollectionOld.contains(comisionCollectionNewComision)) {
                    Propietarios oldIdpropietarioOfComisionCollectionNewComision = comisionCollectionNewComision.getIdpropietario();
                    comisionCollectionNewComision.setIdpropietario(propietarios);
                    comisionCollectionNewComision = em.merge(comisionCollectionNewComision);
                    if (oldIdpropietarioOfComisionCollectionNewComision != null && !oldIdpropietarioOfComisionCollectionNewComision.equals(propietarios)) {
                        oldIdpropietarioOfComisionCollectionNewComision.getComisionCollection().remove(comisionCollectionNewComision);
                        oldIdpropietarioOfComisionCollectionNewComision = em.merge(oldIdpropietarioOfComisionCollectionNewComision);
                    }
                }
            }
            for (Propiedad propiedadCollectionNewPropiedad : propiedadCollectionNew) {
                if (!propiedadCollectionOld.contains(propiedadCollectionNewPropiedad)) {
                    Propietarios oldIdpropietarioOfPropiedadCollectionNewPropiedad = propiedadCollectionNewPropiedad.getIdpropietario();
                    propiedadCollectionNewPropiedad.setIdpropietario(propietarios);
                    propiedadCollectionNewPropiedad = em.merge(propiedadCollectionNewPropiedad);
                    if (oldIdpropietarioOfPropiedadCollectionNewPropiedad != null && !oldIdpropietarioOfPropiedadCollectionNewPropiedad.equals(propietarios)) {
                        oldIdpropietarioOfPropiedadCollectionNewPropiedad.getPropiedadCollection().remove(propiedadCollectionNewPropiedad);
                        oldIdpropietarioOfPropiedadCollectionNewPropiedad = em.merge(oldIdpropietarioOfPropiedadCollectionNewPropiedad);
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
                Integer id = propietarios.getIdpropietario();
                if (findPropietarios(id) == null) {
                    throw new NonexistentEntityException("The propietarios with id " + id + " no longer exists.");
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
            Propietarios propietarios;
            try {
                propietarios = em.getReference(Propietarios.class, id);
                propietarios.getIdpropietario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The propietarios with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Comision> comisionCollectionOrphanCheck = propietarios.getComisionCollection();
            for (Comision comisionCollectionOrphanCheckComision : comisionCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Propietarios (" + propietarios + ") cannot be destroyed since the Comision " + comisionCollectionOrphanCheckComision + " in its comisionCollection field has a non-nullable idpropietario field.");
            }
            Collection<Propiedad> propiedadCollectionOrphanCheck = propietarios.getPropiedadCollection();
            for (Propiedad propiedadCollectionOrphanCheckPropiedad : propiedadCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Propietarios (" + propietarios + ") cannot be destroyed since the Propiedad " + propiedadCollectionOrphanCheckPropiedad + " in its propiedadCollection field has a non-nullable idpropietario field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(propietarios);
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

    public List<Propietarios> findPropietariosEntities() {
        return findPropietariosEntities(true, -1, -1);
    }

    public List<Propietarios> findPropietariosEntities(int maxResults, int firstResult) {
        return findPropietariosEntities(false, maxResults, firstResult);
    }

    private List<Propietarios> findPropietariosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Propietarios.class));
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

    public Propietarios findPropietarios(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Propietarios.class, id);
        } finally {
            em.close();
        }
    }

    public int getPropietariosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Propietarios> rt = cq.from(Propietarios.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
