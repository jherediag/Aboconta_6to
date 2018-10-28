/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.demojsf.jpa.control;

import com.demojsf.jpa.control.exceptions.IllegalOrphanException;
import com.demojsf.jpa.control.exceptions.NonexistentEntityException;
import com.demojsf.jpa.control.exceptions.PreexistingEntityException;
import com.demojsf.jpa.control.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.demojsf.jpa.model.Propietarios;
import com.demojsf.jpa.model.Factura;
import java.util.ArrayList;
import java.util.Collection;
import com.demojsf.jpa.model.Comision;
import com.demojsf.jpa.model.Contrato;
import com.demojsf.jpa.model.Propiedad;
import com.demojsf.jpa.model.PropiedadPK;
import com.demojsf.jpa.model.Recaudo;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author DESARROLLO
 */
public class PropiedadJpaController implements Serializable {

    public PropiedadJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Propiedad propiedad) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (propiedad.getPropiedadPK() == null) {
            propiedad.setPropiedadPK(new PropiedadPK());
        }
        if (propiedad.getFacturaCollection() == null) {
            propiedad.setFacturaCollection(new ArrayList<Factura>());
        }
        if (propiedad.getComisionCollection() == null) {
            propiedad.setComisionCollection(new ArrayList<Comision>());
        }
        if (propiedad.getContratoCollection() == null) {
            propiedad.setContratoCollection(new ArrayList<Contrato>());
        }
        if (propiedad.getRecaudoCollection() == null) {
            propiedad.setRecaudoCollection(new ArrayList<Recaudo>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Propietarios idpropietario = propiedad.getIdpropietario();
            if (idpropietario != null) {
                idpropietario = em.getReference(idpropietario.getClass(), idpropietario.getIdpropietario());
                propiedad.setIdpropietario(idpropietario);
            }
            Collection<Factura> attachedFacturaCollection = new ArrayList<Factura>();
            for (Factura facturaCollectionFacturaToAttach : propiedad.getFacturaCollection()) {
                facturaCollectionFacturaToAttach = em.getReference(facturaCollectionFacturaToAttach.getClass(), facturaCollectionFacturaToAttach.getIdfactura());
                attachedFacturaCollection.add(facturaCollectionFacturaToAttach);
            }
            propiedad.setFacturaCollection(attachedFacturaCollection);
            Collection<Comision> attachedComisionCollection = new ArrayList<Comision>();
            for (Comision comisionCollectionComisionToAttach : propiedad.getComisionCollection()) {
                comisionCollectionComisionToAttach = em.getReference(comisionCollectionComisionToAttach.getClass(), comisionCollectionComisionToAttach.getIdcomision());
                attachedComisionCollection.add(comisionCollectionComisionToAttach);
            }
            propiedad.setComisionCollection(attachedComisionCollection);
            Collection<Contrato> attachedContratoCollection = new ArrayList<Contrato>();
            for (Contrato contratoCollectionContratoToAttach : propiedad.getContratoCollection()) {
                contratoCollectionContratoToAttach = em.getReference(contratoCollectionContratoToAttach.getClass(), contratoCollectionContratoToAttach.getNumcontrato());
                attachedContratoCollection.add(contratoCollectionContratoToAttach);
            }
            propiedad.setContratoCollection(attachedContratoCollection);
            Collection<Recaudo> attachedRecaudoCollection = new ArrayList<Recaudo>();
            for (Recaudo recaudoCollectionRecaudoToAttach : propiedad.getRecaudoCollection()) {
                recaudoCollectionRecaudoToAttach = em.getReference(recaudoCollectionRecaudoToAttach.getClass(), recaudoCollectionRecaudoToAttach.getIdrecaudo());
                attachedRecaudoCollection.add(recaudoCollectionRecaudoToAttach);
            }
            propiedad.setRecaudoCollection(attachedRecaudoCollection);
            em.persist(propiedad);
            if (idpropietario != null) {
                idpropietario.getPropiedadCollection().add(propiedad);
                idpropietario = em.merge(idpropietario);
            }
            for (Factura facturaCollectionFactura : propiedad.getFacturaCollection()) {
                Propiedad oldIdpropiedOfFacturaCollectionFactura = facturaCollectionFactura.getIdpropied();
                facturaCollectionFactura.setIdpropied(propiedad);
                facturaCollectionFactura = em.merge(facturaCollectionFactura);
                if (oldIdpropiedOfFacturaCollectionFactura != null) {
                    oldIdpropiedOfFacturaCollectionFactura.getFacturaCollection().remove(facturaCollectionFactura);
                    oldIdpropiedOfFacturaCollectionFactura = em.merge(oldIdpropiedOfFacturaCollectionFactura);
                }
            }
            for (Comision comisionCollectionComision : propiedad.getComisionCollection()) {
                Propiedad oldIdpropiedadOfComisionCollectionComision = comisionCollectionComision.getIdpropiedad();
                comisionCollectionComision.setIdpropiedad(propiedad);
                comisionCollectionComision = em.merge(comisionCollectionComision);
                if (oldIdpropiedadOfComisionCollectionComision != null) {
                    oldIdpropiedadOfComisionCollectionComision.getComisionCollection().remove(comisionCollectionComision);
                    oldIdpropiedadOfComisionCollectionComision = em.merge(oldIdpropiedadOfComisionCollectionComision);
                }
            }
            for (Contrato contratoCollectionContrato : propiedad.getContratoCollection()) {
                Propiedad oldIdpropiedOfContratoCollectionContrato = contratoCollectionContrato.getIdpropied();
                contratoCollectionContrato.setIdpropied(propiedad);
                contratoCollectionContrato = em.merge(contratoCollectionContrato);
                if (oldIdpropiedOfContratoCollectionContrato != null) {
                    oldIdpropiedOfContratoCollectionContrato.getContratoCollection().remove(contratoCollectionContrato);
                    oldIdpropiedOfContratoCollectionContrato = em.merge(oldIdpropiedOfContratoCollectionContrato);
                }
            }
            for (Recaudo recaudoCollectionRecaudo : propiedad.getRecaudoCollection()) {
                Propiedad oldIdpropiedadOfRecaudoCollectionRecaudo = recaudoCollectionRecaudo.getIdpropiedad();
                recaudoCollectionRecaudo.setIdpropiedad(propiedad);
                recaudoCollectionRecaudo = em.merge(recaudoCollectionRecaudo);
                if (oldIdpropiedadOfRecaudoCollectionRecaudo != null) {
                    oldIdpropiedadOfRecaudoCollectionRecaudo.getRecaudoCollection().remove(recaudoCollectionRecaudo);
                    oldIdpropiedadOfRecaudoCollectionRecaudo = em.merge(oldIdpropiedadOfRecaudoCollectionRecaudo);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findPropiedad(propiedad.getPropiedadPK()) != null) {
                throw new PreexistingEntityException("Propiedad " + propiedad + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Propiedad propiedad) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Propiedad persistentPropiedad = em.find(Propiedad.class, propiedad.getPropiedadPK());
            Propietarios idpropietarioOld = persistentPropiedad.getIdpropietario();
            Propietarios idpropietarioNew = propiedad.getIdpropietario();
            Collection<Factura> facturaCollectionOld = persistentPropiedad.getFacturaCollection();
            Collection<Factura> facturaCollectionNew = propiedad.getFacturaCollection();
            Collection<Comision> comisionCollectionOld = persistentPropiedad.getComisionCollection();
            Collection<Comision> comisionCollectionNew = propiedad.getComisionCollection();
            Collection<Contrato> contratoCollectionOld = persistentPropiedad.getContratoCollection();
            Collection<Contrato> contratoCollectionNew = propiedad.getContratoCollection();
            Collection<Recaudo> recaudoCollectionOld = persistentPropiedad.getRecaudoCollection();
            Collection<Recaudo> recaudoCollectionNew = propiedad.getRecaudoCollection();
            List<String> illegalOrphanMessages = null;
            for (Factura facturaCollectionOldFactura : facturaCollectionOld) {
                if (!facturaCollectionNew.contains(facturaCollectionOldFactura)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Factura " + facturaCollectionOldFactura + " since its idpropied field is not nullable.");
                }
            }
            for (Comision comisionCollectionOldComision : comisionCollectionOld) {
                if (!comisionCollectionNew.contains(comisionCollectionOldComision)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Comision " + comisionCollectionOldComision + " since its idpropiedad field is not nullable.");
                }
            }
            for (Contrato contratoCollectionOldContrato : contratoCollectionOld) {
                if (!contratoCollectionNew.contains(contratoCollectionOldContrato)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Contrato " + contratoCollectionOldContrato + " since its idpropied field is not nullable.");
                }
            }
            for (Recaudo recaudoCollectionOldRecaudo : recaudoCollectionOld) {
                if (!recaudoCollectionNew.contains(recaudoCollectionOldRecaudo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Recaudo " + recaudoCollectionOldRecaudo + " since its idpropiedad field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idpropietarioNew != null) {
                idpropietarioNew = em.getReference(idpropietarioNew.getClass(), idpropietarioNew.getIdpropietario());
                propiedad.setIdpropietario(idpropietarioNew);
            }
            Collection<Factura> attachedFacturaCollectionNew = new ArrayList<Factura>();
            for (Factura facturaCollectionNewFacturaToAttach : facturaCollectionNew) {
                facturaCollectionNewFacturaToAttach = em.getReference(facturaCollectionNewFacturaToAttach.getClass(), facturaCollectionNewFacturaToAttach.getIdfactura());
                attachedFacturaCollectionNew.add(facturaCollectionNewFacturaToAttach);
            }
            facturaCollectionNew = attachedFacturaCollectionNew;
            propiedad.setFacturaCollection(facturaCollectionNew);
            Collection<Comision> attachedComisionCollectionNew = new ArrayList<Comision>();
            for (Comision comisionCollectionNewComisionToAttach : comisionCollectionNew) {
                comisionCollectionNewComisionToAttach = em.getReference(comisionCollectionNewComisionToAttach.getClass(), comisionCollectionNewComisionToAttach.getIdcomision());
                attachedComisionCollectionNew.add(comisionCollectionNewComisionToAttach);
            }
            comisionCollectionNew = attachedComisionCollectionNew;
            propiedad.setComisionCollection(comisionCollectionNew);
            Collection<Contrato> attachedContratoCollectionNew = new ArrayList<Contrato>();
            for (Contrato contratoCollectionNewContratoToAttach : contratoCollectionNew) {
                contratoCollectionNewContratoToAttach = em.getReference(contratoCollectionNewContratoToAttach.getClass(), contratoCollectionNewContratoToAttach.getNumcontrato());
                attachedContratoCollectionNew.add(contratoCollectionNewContratoToAttach);
            }
            contratoCollectionNew = attachedContratoCollectionNew;
            propiedad.setContratoCollection(contratoCollectionNew);
            Collection<Recaudo> attachedRecaudoCollectionNew = new ArrayList<Recaudo>();
            for (Recaudo recaudoCollectionNewRecaudoToAttach : recaudoCollectionNew) {
                recaudoCollectionNewRecaudoToAttach = em.getReference(recaudoCollectionNewRecaudoToAttach.getClass(), recaudoCollectionNewRecaudoToAttach.getIdrecaudo());
                attachedRecaudoCollectionNew.add(recaudoCollectionNewRecaudoToAttach);
            }
            recaudoCollectionNew = attachedRecaudoCollectionNew;
            propiedad.setRecaudoCollection(recaudoCollectionNew);
            propiedad = em.merge(propiedad);
            if (idpropietarioOld != null && !idpropietarioOld.equals(idpropietarioNew)) {
                idpropietarioOld.getPropiedadCollection().remove(propiedad);
                idpropietarioOld = em.merge(idpropietarioOld);
            }
            if (idpropietarioNew != null && !idpropietarioNew.equals(idpropietarioOld)) {
                idpropietarioNew.getPropiedadCollection().add(propiedad);
                idpropietarioNew = em.merge(idpropietarioNew);
            }
            for (Factura facturaCollectionNewFactura : facturaCollectionNew) {
                if (!facturaCollectionOld.contains(facturaCollectionNewFactura)) {
                    Propiedad oldIdpropiedOfFacturaCollectionNewFactura = facturaCollectionNewFactura.getIdpropied();
                    facturaCollectionNewFactura.setIdpropied(propiedad);
                    facturaCollectionNewFactura = em.merge(facturaCollectionNewFactura);
                    if (oldIdpropiedOfFacturaCollectionNewFactura != null && !oldIdpropiedOfFacturaCollectionNewFactura.equals(propiedad)) {
                        oldIdpropiedOfFacturaCollectionNewFactura.getFacturaCollection().remove(facturaCollectionNewFactura);
                        oldIdpropiedOfFacturaCollectionNewFactura = em.merge(oldIdpropiedOfFacturaCollectionNewFactura);
                    }
                }
            }
            for (Comision comisionCollectionNewComision : comisionCollectionNew) {
                if (!comisionCollectionOld.contains(comisionCollectionNewComision)) {
                    Propiedad oldIdpropiedadOfComisionCollectionNewComision = comisionCollectionNewComision.getIdpropiedad();
                    comisionCollectionNewComision.setIdpropiedad(propiedad);
                    comisionCollectionNewComision = em.merge(comisionCollectionNewComision);
                    if (oldIdpropiedadOfComisionCollectionNewComision != null && !oldIdpropiedadOfComisionCollectionNewComision.equals(propiedad)) {
                        oldIdpropiedadOfComisionCollectionNewComision.getComisionCollection().remove(comisionCollectionNewComision);
                        oldIdpropiedadOfComisionCollectionNewComision = em.merge(oldIdpropiedadOfComisionCollectionNewComision);
                    }
                }
            }
            for (Contrato contratoCollectionNewContrato : contratoCollectionNew) {
                if (!contratoCollectionOld.contains(contratoCollectionNewContrato)) {
                    Propiedad oldIdpropiedOfContratoCollectionNewContrato = contratoCollectionNewContrato.getIdpropied();
                    contratoCollectionNewContrato.setIdpropied(propiedad);
                    contratoCollectionNewContrato = em.merge(contratoCollectionNewContrato);
                    if (oldIdpropiedOfContratoCollectionNewContrato != null && !oldIdpropiedOfContratoCollectionNewContrato.equals(propiedad)) {
                        oldIdpropiedOfContratoCollectionNewContrato.getContratoCollection().remove(contratoCollectionNewContrato);
                        oldIdpropiedOfContratoCollectionNewContrato = em.merge(oldIdpropiedOfContratoCollectionNewContrato);
                    }
                }
            }
            for (Recaudo recaudoCollectionNewRecaudo : recaudoCollectionNew) {
                if (!recaudoCollectionOld.contains(recaudoCollectionNewRecaudo)) {
                    Propiedad oldIdpropiedadOfRecaudoCollectionNewRecaudo = recaudoCollectionNewRecaudo.getIdpropiedad();
                    recaudoCollectionNewRecaudo.setIdpropiedad(propiedad);
                    recaudoCollectionNewRecaudo = em.merge(recaudoCollectionNewRecaudo);
                    if (oldIdpropiedadOfRecaudoCollectionNewRecaudo != null && !oldIdpropiedadOfRecaudoCollectionNewRecaudo.equals(propiedad)) {
                        oldIdpropiedadOfRecaudoCollectionNewRecaudo.getRecaudoCollection().remove(recaudoCollectionNewRecaudo);
                        oldIdpropiedadOfRecaudoCollectionNewRecaudo = em.merge(oldIdpropiedadOfRecaudoCollectionNewRecaudo);
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
                PropiedadPK id = propiedad.getPropiedadPK();
                if (findPropiedad(id) == null) {
                    throw new NonexistentEntityException("The propiedad with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(PropiedadPK id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Propiedad propiedad;
            try {
                propiedad = em.getReference(Propiedad.class, id);
                propiedad.getPropiedadPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The propiedad with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Factura> facturaCollectionOrphanCheck = propiedad.getFacturaCollection();
            for (Factura facturaCollectionOrphanCheckFactura : facturaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Propiedad (" + propiedad + ") cannot be destroyed since the Factura " + facturaCollectionOrphanCheckFactura + " in its facturaCollection field has a non-nullable idpropied field.");
            }
            Collection<Comision> comisionCollectionOrphanCheck = propiedad.getComisionCollection();
            for (Comision comisionCollectionOrphanCheckComision : comisionCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Propiedad (" + propiedad + ") cannot be destroyed since the Comision " + comisionCollectionOrphanCheckComision + " in its comisionCollection field has a non-nullable idpropiedad field.");
            }
            Collection<Contrato> contratoCollectionOrphanCheck = propiedad.getContratoCollection();
            for (Contrato contratoCollectionOrphanCheckContrato : contratoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Propiedad (" + propiedad + ") cannot be destroyed since the Contrato " + contratoCollectionOrphanCheckContrato + " in its contratoCollection field has a non-nullable idpropied field.");
            }
            Collection<Recaudo> recaudoCollectionOrphanCheck = propiedad.getRecaudoCollection();
            for (Recaudo recaudoCollectionOrphanCheckRecaudo : recaudoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Propiedad (" + propiedad + ") cannot be destroyed since the Recaudo " + recaudoCollectionOrphanCheckRecaudo + " in its recaudoCollection field has a non-nullable idpropiedad field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Propietarios idpropietario = propiedad.getIdpropietario();
            if (idpropietario != null) {
                idpropietario.getPropiedadCollection().remove(propiedad);
                idpropietario = em.merge(idpropietario);
            }
            em.remove(propiedad);
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

    public List<Propiedad> findPropiedadEntities() {
        return findPropiedadEntities(true, -1, -1);
    }

    public List<Propiedad> findPropiedadEntities(int maxResults, int firstResult) {
        return findPropiedadEntities(false, maxResults, firstResult);
    }

    private List<Propiedad> findPropiedadEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Propiedad.class));
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

    public Propiedad findPropiedad(PropiedadPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Propiedad.class, id);
        } finally {
            em.close();
        }
    }

    public int getPropiedadCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Propiedad> rt = cq.from(Propiedad.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
