/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.demojsf.jpa.control;

import com.demojsf.jpa.control.exceptions.IllegalOrphanException;
import com.demojsf.jpa.control.exceptions.NonexistentEntityException;
import com.demojsf.jpa.control.exceptions.RollbackFailureException;
import com.demojsf.jpa.model.Clientes;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.demojsf.jpa.model.Factura;
import java.util.ArrayList;
import java.util.Collection;
import com.demojsf.jpa.model.Contrato;
import com.demojsf.jpa.model.Recaudo;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author DESARROLLO
 */
public class ClientesJpaController implements Serializable {

    public ClientesJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Clientes clientes) throws RollbackFailureException, Exception {
        if (clientes.getFacturaCollection() == null) {
            clientes.setFacturaCollection(new ArrayList<Factura>());
        }
        if (clientes.getContratoCollection() == null) {
            clientes.setContratoCollection(new ArrayList<Contrato>());
        }
        if (clientes.getRecaudoCollection() == null) {
            clientes.setRecaudoCollection(new ArrayList<Recaudo>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<Factura> attachedFacturaCollection = new ArrayList<Factura>();
            for (Factura facturaCollectionFacturaToAttach : clientes.getFacturaCollection()) {
                facturaCollectionFacturaToAttach = em.getReference(facturaCollectionFacturaToAttach.getClass(), facturaCollectionFacturaToAttach.getIdfactura());
                attachedFacturaCollection.add(facturaCollectionFacturaToAttach);
            }
            clientes.setFacturaCollection(attachedFacturaCollection);
            Collection<Contrato> attachedContratoCollection = new ArrayList<Contrato>();
            for (Contrato contratoCollectionContratoToAttach : clientes.getContratoCollection()) {
                contratoCollectionContratoToAttach = em.getReference(contratoCollectionContratoToAttach.getClass(), contratoCollectionContratoToAttach.getNumcontrato());
                attachedContratoCollection.add(contratoCollectionContratoToAttach);
            }
            clientes.setContratoCollection(attachedContratoCollection);
            Collection<Recaudo> attachedRecaudoCollection = new ArrayList<Recaudo>();
            for (Recaudo recaudoCollectionRecaudoToAttach : clientes.getRecaudoCollection()) {
                recaudoCollectionRecaudoToAttach = em.getReference(recaudoCollectionRecaudoToAttach.getClass(), recaudoCollectionRecaudoToAttach.getIdrecaudo());
                attachedRecaudoCollection.add(recaudoCollectionRecaudoToAttach);
            }
            clientes.setRecaudoCollection(attachedRecaudoCollection);
            em.persist(clientes);
            for (Factura facturaCollectionFactura : clientes.getFacturaCollection()) {
                Clientes oldIdclieOfFacturaCollectionFactura = facturaCollectionFactura.getIdclie();
                facturaCollectionFactura.setIdclie(clientes);
                facturaCollectionFactura = em.merge(facturaCollectionFactura);
                if (oldIdclieOfFacturaCollectionFactura != null) {
                    oldIdclieOfFacturaCollectionFactura.getFacturaCollection().remove(facturaCollectionFactura);
                    oldIdclieOfFacturaCollectionFactura = em.merge(oldIdclieOfFacturaCollectionFactura);
                }
            }
            for (Contrato contratoCollectionContrato : clientes.getContratoCollection()) {
                Clientes oldIdclieOfContratoCollectionContrato = contratoCollectionContrato.getIdclie();
                contratoCollectionContrato.setIdclie(clientes);
                contratoCollectionContrato = em.merge(contratoCollectionContrato);
                if (oldIdclieOfContratoCollectionContrato != null) {
                    oldIdclieOfContratoCollectionContrato.getContratoCollection().remove(contratoCollectionContrato);
                    oldIdclieOfContratoCollectionContrato = em.merge(oldIdclieOfContratoCollectionContrato);
                }
            }
            for (Recaudo recaudoCollectionRecaudo : clientes.getRecaudoCollection()) {
                Clientes oldIdclienteOfRecaudoCollectionRecaudo = recaudoCollectionRecaudo.getIdcliente();
                recaudoCollectionRecaudo.setIdcliente(clientes);
                recaudoCollectionRecaudo = em.merge(recaudoCollectionRecaudo);
                if (oldIdclienteOfRecaudoCollectionRecaudo != null) {
                    oldIdclienteOfRecaudoCollectionRecaudo.getRecaudoCollection().remove(recaudoCollectionRecaudo);
                    oldIdclienteOfRecaudoCollectionRecaudo = em.merge(oldIdclienteOfRecaudoCollectionRecaudo);
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

    public void edit(Clientes clientes) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Clientes persistentClientes = em.find(Clientes.class, clientes.getIdcliente());
            Collection<Factura> facturaCollectionOld = persistentClientes.getFacturaCollection();
            Collection<Factura> facturaCollectionNew = clientes.getFacturaCollection();
            Collection<Contrato> contratoCollectionOld = persistentClientes.getContratoCollection();
            Collection<Contrato> contratoCollectionNew = clientes.getContratoCollection();
            Collection<Recaudo> recaudoCollectionOld = persistentClientes.getRecaudoCollection();
            Collection<Recaudo> recaudoCollectionNew = clientes.getRecaudoCollection();
            List<String> illegalOrphanMessages = null;
            for (Factura facturaCollectionOldFactura : facturaCollectionOld) {
                if (!facturaCollectionNew.contains(facturaCollectionOldFactura)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Factura " + facturaCollectionOldFactura + " since its idclie field is not nullable.");
                }
            }
            for (Contrato contratoCollectionOldContrato : contratoCollectionOld) {
                if (!contratoCollectionNew.contains(contratoCollectionOldContrato)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Contrato " + contratoCollectionOldContrato + " since its idclie field is not nullable.");
                }
            }
            for (Recaudo recaudoCollectionOldRecaudo : recaudoCollectionOld) {
                if (!recaudoCollectionNew.contains(recaudoCollectionOldRecaudo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Recaudo " + recaudoCollectionOldRecaudo + " since its idcliente field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Factura> attachedFacturaCollectionNew = new ArrayList<Factura>();
            for (Factura facturaCollectionNewFacturaToAttach : facturaCollectionNew) {
                facturaCollectionNewFacturaToAttach = em.getReference(facturaCollectionNewFacturaToAttach.getClass(), facturaCollectionNewFacturaToAttach.getIdfactura());
                attachedFacturaCollectionNew.add(facturaCollectionNewFacturaToAttach);
            }
            facturaCollectionNew = attachedFacturaCollectionNew;
            clientes.setFacturaCollection(facturaCollectionNew);
            Collection<Contrato> attachedContratoCollectionNew = new ArrayList<Contrato>();
            for (Contrato contratoCollectionNewContratoToAttach : contratoCollectionNew) {
                contratoCollectionNewContratoToAttach = em.getReference(contratoCollectionNewContratoToAttach.getClass(), contratoCollectionNewContratoToAttach.getNumcontrato());
                attachedContratoCollectionNew.add(contratoCollectionNewContratoToAttach);
            }
            contratoCollectionNew = attachedContratoCollectionNew;
            clientes.setContratoCollection(contratoCollectionNew);
            Collection<Recaudo> attachedRecaudoCollectionNew = new ArrayList<Recaudo>();
            for (Recaudo recaudoCollectionNewRecaudoToAttach : recaudoCollectionNew) {
                recaudoCollectionNewRecaudoToAttach = em.getReference(recaudoCollectionNewRecaudoToAttach.getClass(), recaudoCollectionNewRecaudoToAttach.getIdrecaudo());
                attachedRecaudoCollectionNew.add(recaudoCollectionNewRecaudoToAttach);
            }
            recaudoCollectionNew = attachedRecaudoCollectionNew;
            clientes.setRecaudoCollection(recaudoCollectionNew);
            clientes = em.merge(clientes);
            for (Factura facturaCollectionNewFactura : facturaCollectionNew) {
                if (!facturaCollectionOld.contains(facturaCollectionNewFactura)) {
                    Clientes oldIdclieOfFacturaCollectionNewFactura = facturaCollectionNewFactura.getIdclie();
                    facturaCollectionNewFactura.setIdclie(clientes);
                    facturaCollectionNewFactura = em.merge(facturaCollectionNewFactura);
                    if (oldIdclieOfFacturaCollectionNewFactura != null && !oldIdclieOfFacturaCollectionNewFactura.equals(clientes)) {
                        oldIdclieOfFacturaCollectionNewFactura.getFacturaCollection().remove(facturaCollectionNewFactura);
                        oldIdclieOfFacturaCollectionNewFactura = em.merge(oldIdclieOfFacturaCollectionNewFactura);
                    }
                }
            }
            for (Contrato contratoCollectionNewContrato : contratoCollectionNew) {
                if (!contratoCollectionOld.contains(contratoCollectionNewContrato)) {
                    Clientes oldIdclieOfContratoCollectionNewContrato = contratoCollectionNewContrato.getIdclie();
                    contratoCollectionNewContrato.setIdclie(clientes);
                    contratoCollectionNewContrato = em.merge(contratoCollectionNewContrato);
                    if (oldIdclieOfContratoCollectionNewContrato != null && !oldIdclieOfContratoCollectionNewContrato.equals(clientes)) {
                        oldIdclieOfContratoCollectionNewContrato.getContratoCollection().remove(contratoCollectionNewContrato);
                        oldIdclieOfContratoCollectionNewContrato = em.merge(oldIdclieOfContratoCollectionNewContrato);
                    }
                }
            }
            for (Recaudo recaudoCollectionNewRecaudo : recaudoCollectionNew) {
                if (!recaudoCollectionOld.contains(recaudoCollectionNewRecaudo)) {
                    Clientes oldIdclienteOfRecaudoCollectionNewRecaudo = recaudoCollectionNewRecaudo.getIdcliente();
                    recaudoCollectionNewRecaudo.setIdcliente(clientes);
                    recaudoCollectionNewRecaudo = em.merge(recaudoCollectionNewRecaudo);
                    if (oldIdclienteOfRecaudoCollectionNewRecaudo != null && !oldIdclienteOfRecaudoCollectionNewRecaudo.equals(clientes)) {
                        oldIdclienteOfRecaudoCollectionNewRecaudo.getRecaudoCollection().remove(recaudoCollectionNewRecaudo);
                        oldIdclienteOfRecaudoCollectionNewRecaudo = em.merge(oldIdclienteOfRecaudoCollectionNewRecaudo);
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
                Integer id = clientes.getIdcliente();
                if (findClientes(id) == null) {
                    throw new NonexistentEntityException("The clientes with id " + id + " no longer exists.");
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
            Clientes clientes;
            try {
                clientes = em.getReference(Clientes.class, id);
                clientes.getIdcliente();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The clientes with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Factura> facturaCollectionOrphanCheck = clientes.getFacturaCollection();
            for (Factura facturaCollectionOrphanCheckFactura : facturaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Clientes (" + clientes + ") cannot be destroyed since the Factura " + facturaCollectionOrphanCheckFactura + " in its facturaCollection field has a non-nullable idclie field.");
            }
            Collection<Contrato> contratoCollectionOrphanCheck = clientes.getContratoCollection();
            for (Contrato contratoCollectionOrphanCheckContrato : contratoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Clientes (" + clientes + ") cannot be destroyed since the Contrato " + contratoCollectionOrphanCheckContrato + " in its contratoCollection field has a non-nullable idclie field.");
            }
            Collection<Recaudo> recaudoCollectionOrphanCheck = clientes.getRecaudoCollection();
            for (Recaudo recaudoCollectionOrphanCheckRecaudo : recaudoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Clientes (" + clientes + ") cannot be destroyed since the Recaudo " + recaudoCollectionOrphanCheckRecaudo + " in its recaudoCollection field has a non-nullable idcliente field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(clientes);
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

    public List<Clientes> findClientesEntities() {
        return findClientesEntities(true, -1, -1);
    }

    public List<Clientes> findClientesEntities(int maxResults, int firstResult) {
        return findClientesEntities(false, maxResults, firstResult);
    }

    private List<Clientes> findClientesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Clientes.class));
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

    public Clientes findClientes(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Clientes.class, id);
        } finally {
            em.close();
        }
    }

    public int getClientesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Clientes> rt = cq.from(Clientes.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
