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
import com.demojsf.jpa.model.Clientes;
import com.demojsf.jpa.model.Propiedad;
import com.demojsf.jpa.model.Detallecontrato;
import java.util.ArrayList;
import java.util.Collection;
import com.demojsf.jpa.model.Factura;
import com.demojsf.jpa.model.Comision;
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
public class ContratoJpaController implements Serializable {

    public ContratoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Contrato contrato) throws RollbackFailureException, Exception {
        if (contrato.getDetallecontratoCollection() == null) {
            contrato.setDetallecontratoCollection(new ArrayList<Detallecontrato>());
        }
        if (contrato.getFacturaCollection() == null) {
            contrato.setFacturaCollection(new ArrayList<Factura>());
        }
        if (contrato.getComisionCollection() == null) {
            contrato.setComisionCollection(new ArrayList<Comision>());
        }
        if (contrato.getRecaudoCollection() == null) {
            contrato.setRecaudoCollection(new ArrayList<Recaudo>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Clientes idclie = contrato.getIdclie();
            if (idclie != null) {
                idclie = em.getReference(idclie.getClass(), idclie.getIdcliente());
                contrato.setIdclie(idclie);
            }
            Propiedad idpropied = contrato.getIdpropied();
            if (idpropied != null) {
                idpropied = em.getReference(idpropied.getClass(), idpropied.getPropiedadPK());
                contrato.setIdpropied(idpropied);
            }
            Collection<Detallecontrato> attachedDetallecontratoCollection = new ArrayList<Detallecontrato>();
            for (Detallecontrato detallecontratoCollectionDetallecontratoToAttach : contrato.getDetallecontratoCollection()) {
                detallecontratoCollectionDetallecontratoToAttach = em.getReference(detallecontratoCollectionDetallecontratoToAttach.getClass(), detallecontratoCollectionDetallecontratoToAttach.getIdDetalleContratoConcep());
                attachedDetallecontratoCollection.add(detallecontratoCollectionDetallecontratoToAttach);
            }
            contrato.setDetallecontratoCollection(attachedDetallecontratoCollection);
            Collection<Factura> attachedFacturaCollection = new ArrayList<Factura>();
            for (Factura facturaCollectionFacturaToAttach : contrato.getFacturaCollection()) {
                facturaCollectionFacturaToAttach = em.getReference(facturaCollectionFacturaToAttach.getClass(), facturaCollectionFacturaToAttach.getIdfactura());
                attachedFacturaCollection.add(facturaCollectionFacturaToAttach);
            }
            contrato.setFacturaCollection(attachedFacturaCollection);
            Collection<Comision> attachedComisionCollection = new ArrayList<Comision>();
            for (Comision comisionCollectionComisionToAttach : contrato.getComisionCollection()) {
                comisionCollectionComisionToAttach = em.getReference(comisionCollectionComisionToAttach.getClass(), comisionCollectionComisionToAttach.getIdcomision());
                attachedComisionCollection.add(comisionCollectionComisionToAttach);
            }
            contrato.setComisionCollection(attachedComisionCollection);
            Collection<Recaudo> attachedRecaudoCollection = new ArrayList<Recaudo>();
            for (Recaudo recaudoCollectionRecaudoToAttach : contrato.getRecaudoCollection()) {
                recaudoCollectionRecaudoToAttach = em.getReference(recaudoCollectionRecaudoToAttach.getClass(), recaudoCollectionRecaudoToAttach.getIdrecaudo());
                attachedRecaudoCollection.add(recaudoCollectionRecaudoToAttach);
            }
            contrato.setRecaudoCollection(attachedRecaudoCollection);
            em.persist(contrato);
            if (idclie != null) {
                idclie.getContratoCollection().add(contrato);
                idclie = em.merge(idclie);
            }
            if (idpropied != null) {
                idpropied.getContratoCollection().add(contrato);
                idpropied = em.merge(idpropied);
            }
            for (Detallecontrato detallecontratoCollectionDetallecontrato : contrato.getDetallecontratoCollection()) {
                Contrato oldIdcontraOfDetallecontratoCollectionDetallecontrato = detallecontratoCollectionDetallecontrato.getIdcontra();
                detallecontratoCollectionDetallecontrato.setIdcontra(contrato);
                detallecontratoCollectionDetallecontrato = em.merge(detallecontratoCollectionDetallecontrato);
                if (oldIdcontraOfDetallecontratoCollectionDetallecontrato != null) {
                    oldIdcontraOfDetallecontratoCollectionDetallecontrato.getDetallecontratoCollection().remove(detallecontratoCollectionDetallecontrato);
                    oldIdcontraOfDetallecontratoCollectionDetallecontrato = em.merge(oldIdcontraOfDetallecontratoCollectionDetallecontrato);
                }
            }
            for (Factura facturaCollectionFactura : contrato.getFacturaCollection()) {
                Contrato oldIdcontraOfFacturaCollectionFactura = facturaCollectionFactura.getIdcontra();
                facturaCollectionFactura.setIdcontra(contrato);
                facturaCollectionFactura = em.merge(facturaCollectionFactura);
                if (oldIdcontraOfFacturaCollectionFactura != null) {
                    oldIdcontraOfFacturaCollectionFactura.getFacturaCollection().remove(facturaCollectionFactura);
                    oldIdcontraOfFacturaCollectionFactura = em.merge(oldIdcontraOfFacturaCollectionFactura);
                }
            }
            for (Comision comisionCollectionComision : contrato.getComisionCollection()) {
                Contrato oldIdcontratoOfComisionCollectionComision = comisionCollectionComision.getIdcontrato();
                comisionCollectionComision.setIdcontrato(contrato);
                comisionCollectionComision = em.merge(comisionCollectionComision);
                if (oldIdcontratoOfComisionCollectionComision != null) {
                    oldIdcontratoOfComisionCollectionComision.getComisionCollection().remove(comisionCollectionComision);
                    oldIdcontratoOfComisionCollectionComision = em.merge(oldIdcontratoOfComisionCollectionComision);
                }
            }
            for (Recaudo recaudoCollectionRecaudo : contrato.getRecaudoCollection()) {
                Contrato oldIdcontratoOfRecaudoCollectionRecaudo = recaudoCollectionRecaudo.getIdcontrato();
                recaudoCollectionRecaudo.setIdcontrato(contrato);
                recaudoCollectionRecaudo = em.merge(recaudoCollectionRecaudo);
                if (oldIdcontratoOfRecaudoCollectionRecaudo != null) {
                    oldIdcontratoOfRecaudoCollectionRecaudo.getRecaudoCollection().remove(recaudoCollectionRecaudo);
                    oldIdcontratoOfRecaudoCollectionRecaudo = em.merge(oldIdcontratoOfRecaudoCollectionRecaudo);
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

    public void edit(Contrato contrato) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Contrato persistentContrato = em.find(Contrato.class, contrato.getNumcontrato());
            Clientes idclieOld = persistentContrato.getIdclie();
            Clientes idclieNew = contrato.getIdclie();
            Propiedad idpropiedOld = persistentContrato.getIdpropied();
            Propiedad idpropiedNew = contrato.getIdpropied();
            Collection<Detallecontrato> detallecontratoCollectionOld = persistentContrato.getDetallecontratoCollection();
            Collection<Detallecontrato> detallecontratoCollectionNew = contrato.getDetallecontratoCollection();
            Collection<Factura> facturaCollectionOld = persistentContrato.getFacturaCollection();
            Collection<Factura> facturaCollectionNew = contrato.getFacturaCollection();
            Collection<Comision> comisionCollectionOld = persistentContrato.getComisionCollection();
            Collection<Comision> comisionCollectionNew = contrato.getComisionCollection();
            Collection<Recaudo> recaudoCollectionOld = persistentContrato.getRecaudoCollection();
            Collection<Recaudo> recaudoCollectionNew = contrato.getRecaudoCollection();
            List<String> illegalOrphanMessages = null;
            for (Detallecontrato detallecontratoCollectionOldDetallecontrato : detallecontratoCollectionOld) {
                if (!detallecontratoCollectionNew.contains(detallecontratoCollectionOldDetallecontrato)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Detallecontrato " + detallecontratoCollectionOldDetallecontrato + " since its idcontra field is not nullable.");
                }
            }
            for (Factura facturaCollectionOldFactura : facturaCollectionOld) {
                if (!facturaCollectionNew.contains(facturaCollectionOldFactura)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Factura " + facturaCollectionOldFactura + " since its idcontra field is not nullable.");
                }
            }
            for (Comision comisionCollectionOldComision : comisionCollectionOld) {
                if (!comisionCollectionNew.contains(comisionCollectionOldComision)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Comision " + comisionCollectionOldComision + " since its idcontrato field is not nullable.");
                }
            }
            for (Recaudo recaudoCollectionOldRecaudo : recaudoCollectionOld) {
                if (!recaudoCollectionNew.contains(recaudoCollectionOldRecaudo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Recaudo " + recaudoCollectionOldRecaudo + " since its idcontrato field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idclieNew != null) {
                idclieNew = em.getReference(idclieNew.getClass(), idclieNew.getIdcliente());
                contrato.setIdclie(idclieNew);
            }
            if (idpropiedNew != null) {
                idpropiedNew = em.getReference(idpropiedNew.getClass(), idpropiedNew.getPropiedadPK());
                contrato.setIdpropied(idpropiedNew);
            }
            Collection<Detallecontrato> attachedDetallecontratoCollectionNew = new ArrayList<Detallecontrato>();
            for (Detallecontrato detallecontratoCollectionNewDetallecontratoToAttach : detallecontratoCollectionNew) {
                detallecontratoCollectionNewDetallecontratoToAttach = em.getReference(detallecontratoCollectionNewDetallecontratoToAttach.getClass(), detallecontratoCollectionNewDetallecontratoToAttach.getIdDetalleContratoConcep());
                attachedDetallecontratoCollectionNew.add(detallecontratoCollectionNewDetallecontratoToAttach);
            }
            detallecontratoCollectionNew = attachedDetallecontratoCollectionNew;
            contrato.setDetallecontratoCollection(detallecontratoCollectionNew);
            Collection<Factura> attachedFacturaCollectionNew = new ArrayList<Factura>();
            for (Factura facturaCollectionNewFacturaToAttach : facturaCollectionNew) {
                facturaCollectionNewFacturaToAttach = em.getReference(facturaCollectionNewFacturaToAttach.getClass(), facturaCollectionNewFacturaToAttach.getIdfactura());
                attachedFacturaCollectionNew.add(facturaCollectionNewFacturaToAttach);
            }
            facturaCollectionNew = attachedFacturaCollectionNew;
            contrato.setFacturaCollection(facturaCollectionNew);
            Collection<Comision> attachedComisionCollectionNew = new ArrayList<Comision>();
            for (Comision comisionCollectionNewComisionToAttach : comisionCollectionNew) {
                comisionCollectionNewComisionToAttach = em.getReference(comisionCollectionNewComisionToAttach.getClass(), comisionCollectionNewComisionToAttach.getIdcomision());
                attachedComisionCollectionNew.add(comisionCollectionNewComisionToAttach);
            }
            comisionCollectionNew = attachedComisionCollectionNew;
            contrato.setComisionCollection(comisionCollectionNew);
            Collection<Recaudo> attachedRecaudoCollectionNew = new ArrayList<Recaudo>();
            for (Recaudo recaudoCollectionNewRecaudoToAttach : recaudoCollectionNew) {
                recaudoCollectionNewRecaudoToAttach = em.getReference(recaudoCollectionNewRecaudoToAttach.getClass(), recaudoCollectionNewRecaudoToAttach.getIdrecaudo());
                attachedRecaudoCollectionNew.add(recaudoCollectionNewRecaudoToAttach);
            }
            recaudoCollectionNew = attachedRecaudoCollectionNew;
            contrato.setRecaudoCollection(recaudoCollectionNew);
            contrato = em.merge(contrato);
            if (idclieOld != null && !idclieOld.equals(idclieNew)) {
                idclieOld.getContratoCollection().remove(contrato);
                idclieOld = em.merge(idclieOld);
            }
            if (idclieNew != null && !idclieNew.equals(idclieOld)) {
                idclieNew.getContratoCollection().add(contrato);
                idclieNew = em.merge(idclieNew);
            }
            if (idpropiedOld != null && !idpropiedOld.equals(idpropiedNew)) {
                idpropiedOld.getContratoCollection().remove(contrato);
                idpropiedOld = em.merge(idpropiedOld);
            }
            if (idpropiedNew != null && !idpropiedNew.equals(idpropiedOld)) {
                idpropiedNew.getContratoCollection().add(contrato);
                idpropiedNew = em.merge(idpropiedNew);
            }
            for (Detallecontrato detallecontratoCollectionNewDetallecontrato : detallecontratoCollectionNew) {
                if (!detallecontratoCollectionOld.contains(detallecontratoCollectionNewDetallecontrato)) {
                    Contrato oldIdcontraOfDetallecontratoCollectionNewDetallecontrato = detallecontratoCollectionNewDetallecontrato.getIdcontra();
                    detallecontratoCollectionNewDetallecontrato.setIdcontra(contrato);
                    detallecontratoCollectionNewDetallecontrato = em.merge(detallecontratoCollectionNewDetallecontrato);
                    if (oldIdcontraOfDetallecontratoCollectionNewDetallecontrato != null && !oldIdcontraOfDetallecontratoCollectionNewDetallecontrato.equals(contrato)) {
                        oldIdcontraOfDetallecontratoCollectionNewDetallecontrato.getDetallecontratoCollection().remove(detallecontratoCollectionNewDetallecontrato);
                        oldIdcontraOfDetallecontratoCollectionNewDetallecontrato = em.merge(oldIdcontraOfDetallecontratoCollectionNewDetallecontrato);
                    }
                }
            }
            for (Factura facturaCollectionNewFactura : facturaCollectionNew) {
                if (!facturaCollectionOld.contains(facturaCollectionNewFactura)) {
                    Contrato oldIdcontraOfFacturaCollectionNewFactura = facturaCollectionNewFactura.getIdcontra();
                    facturaCollectionNewFactura.setIdcontra(contrato);
                    facturaCollectionNewFactura = em.merge(facturaCollectionNewFactura);
                    if (oldIdcontraOfFacturaCollectionNewFactura != null && !oldIdcontraOfFacturaCollectionNewFactura.equals(contrato)) {
                        oldIdcontraOfFacturaCollectionNewFactura.getFacturaCollection().remove(facturaCollectionNewFactura);
                        oldIdcontraOfFacturaCollectionNewFactura = em.merge(oldIdcontraOfFacturaCollectionNewFactura);
                    }
                }
            }
            for (Comision comisionCollectionNewComision : comisionCollectionNew) {
                if (!comisionCollectionOld.contains(comisionCollectionNewComision)) {
                    Contrato oldIdcontratoOfComisionCollectionNewComision = comisionCollectionNewComision.getIdcontrato();
                    comisionCollectionNewComision.setIdcontrato(contrato);
                    comisionCollectionNewComision = em.merge(comisionCollectionNewComision);
                    if (oldIdcontratoOfComisionCollectionNewComision != null && !oldIdcontratoOfComisionCollectionNewComision.equals(contrato)) {
                        oldIdcontratoOfComisionCollectionNewComision.getComisionCollection().remove(comisionCollectionNewComision);
                        oldIdcontratoOfComisionCollectionNewComision = em.merge(oldIdcontratoOfComisionCollectionNewComision);
                    }
                }
            }
            for (Recaudo recaudoCollectionNewRecaudo : recaudoCollectionNew) {
                if (!recaudoCollectionOld.contains(recaudoCollectionNewRecaudo)) {
                    Contrato oldIdcontratoOfRecaudoCollectionNewRecaudo = recaudoCollectionNewRecaudo.getIdcontrato();
                    recaudoCollectionNewRecaudo.setIdcontrato(contrato);
                    recaudoCollectionNewRecaudo = em.merge(recaudoCollectionNewRecaudo);
                    if (oldIdcontratoOfRecaudoCollectionNewRecaudo != null && !oldIdcontratoOfRecaudoCollectionNewRecaudo.equals(contrato)) {
                        oldIdcontratoOfRecaudoCollectionNewRecaudo.getRecaudoCollection().remove(recaudoCollectionNewRecaudo);
                        oldIdcontratoOfRecaudoCollectionNewRecaudo = em.merge(oldIdcontratoOfRecaudoCollectionNewRecaudo);
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
                Integer id = contrato.getNumcontrato();
                if (findContrato(id) == null) {
                    throw new NonexistentEntityException("The contrato with id " + id + " no longer exists.");
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
            Contrato contrato;
            try {
                contrato = em.getReference(Contrato.class, id);
                contrato.getNumcontrato();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The contrato with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Detallecontrato> detallecontratoCollectionOrphanCheck = contrato.getDetallecontratoCollection();
            for (Detallecontrato detallecontratoCollectionOrphanCheckDetallecontrato : detallecontratoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Contrato (" + contrato + ") cannot be destroyed since the Detallecontrato " + detallecontratoCollectionOrphanCheckDetallecontrato + " in its detallecontratoCollection field has a non-nullable idcontra field.");
            }
            Collection<Factura> facturaCollectionOrphanCheck = contrato.getFacturaCollection();
            for (Factura facturaCollectionOrphanCheckFactura : facturaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Contrato (" + contrato + ") cannot be destroyed since the Factura " + facturaCollectionOrphanCheckFactura + " in its facturaCollection field has a non-nullable idcontra field.");
            }
            Collection<Comision> comisionCollectionOrphanCheck = contrato.getComisionCollection();
            for (Comision comisionCollectionOrphanCheckComision : comisionCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Contrato (" + contrato + ") cannot be destroyed since the Comision " + comisionCollectionOrphanCheckComision + " in its comisionCollection field has a non-nullable idcontrato field.");
            }
            Collection<Recaudo> recaudoCollectionOrphanCheck = contrato.getRecaudoCollection();
            for (Recaudo recaudoCollectionOrphanCheckRecaudo : recaudoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Contrato (" + contrato + ") cannot be destroyed since the Recaudo " + recaudoCollectionOrphanCheckRecaudo + " in its recaudoCollection field has a non-nullable idcontrato field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Clientes idclie = contrato.getIdclie();
            if (idclie != null) {
                idclie.getContratoCollection().remove(contrato);
                idclie = em.merge(idclie);
            }
            Propiedad idpropied = contrato.getIdpropied();
            if (idpropied != null) {
                idpropied.getContratoCollection().remove(contrato);
                idpropied = em.merge(idpropied);
            }
            em.remove(contrato);
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

    public List<Contrato> findContratoEntities() {
        return findContratoEntities(true, -1, -1);
    }

    public List<Contrato> findContratoEntities(int maxResults, int firstResult) {
        return findContratoEntities(false, maxResults, firstResult);
    }

    private List<Contrato> findContratoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Contrato.class));
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

    public Contrato findContrato(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Contrato.class, id);
        } finally {
            em.close();
        }
    }

    public int getContratoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Contrato> rt = cq.from(Contrato.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
