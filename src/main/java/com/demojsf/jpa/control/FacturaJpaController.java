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
import com.demojsf.jpa.model.Contrato;
import com.demojsf.jpa.model.Propiedad;
import com.demojsf.jpa.model.DetalleFactura;
import com.demojsf.jpa.model.Factura;
import java.util.ArrayList;
import java.util.Collection;
import com.demojsf.jpa.model.Recaudo;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author DESARROLLO
 */
public class FacturaJpaController implements Serializable {

    public FacturaJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Factura factura) throws RollbackFailureException, Exception {
        if (factura.getDetalleFacturaCollection() == null) {
            factura.setDetalleFacturaCollection(new ArrayList<DetalleFactura>());
        }
        if (factura.getRecaudoCollection() == null) {
            factura.setRecaudoCollection(new ArrayList<Recaudo>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Clientes idclie = factura.getIdclie();
            if (idclie != null) {
                idclie = em.getReference(idclie.getClass(), idclie.getIdcliente());
                factura.setIdclie(idclie);
            }
            Contrato idcontra = factura.getIdcontra();
            if (idcontra != null) {
                idcontra = em.getReference(idcontra.getClass(), idcontra.getNumcontrato());
                factura.setIdcontra(idcontra);
            }
            Propiedad idpropied = factura.getIdpropied();
            if (idpropied != null) {
                idpropied = em.getReference(idpropied.getClass(), idpropied.getPropiedadPK());
                factura.setIdpropied(idpropied);
            }
            Collection<DetalleFactura> attachedDetalleFacturaCollection = new ArrayList<DetalleFactura>();
            for (DetalleFactura detalleFacturaCollectionDetalleFacturaToAttach : factura.getDetalleFacturaCollection()) {
                detalleFacturaCollectionDetalleFacturaToAttach = em.getReference(detalleFacturaCollectionDetalleFacturaToAttach.getClass(), detalleFacturaCollectionDetalleFacturaToAttach.getIdDetalle());
                attachedDetalleFacturaCollection.add(detalleFacturaCollectionDetalleFacturaToAttach);
            }
            factura.setDetalleFacturaCollection(attachedDetalleFacturaCollection);
            Collection<Recaudo> attachedRecaudoCollection = new ArrayList<Recaudo>();
            for (Recaudo recaudoCollectionRecaudoToAttach : factura.getRecaudoCollection()) {
                recaudoCollectionRecaudoToAttach = em.getReference(recaudoCollectionRecaudoToAttach.getClass(), recaudoCollectionRecaudoToAttach.getIdrecaudo());
                attachedRecaudoCollection.add(recaudoCollectionRecaudoToAttach);
            }
            factura.setRecaudoCollection(attachedRecaudoCollection);
            em.persist(factura);
            if (idclie != null) {
                idclie.getFacturaCollection().add(factura);
                idclie = em.merge(idclie);
            }
            if (idcontra != null) {
                idcontra.getFacturaCollection().add(factura);
                idcontra = em.merge(idcontra);
            }
            if (idpropied != null) {
                idpropied.getFacturaCollection().add(factura);
                idpropied = em.merge(idpropied);
            }
            for (DetalleFactura detalleFacturaCollectionDetalleFactura : factura.getDetalleFacturaCollection()) {
                Factura oldIdfactuOfDetalleFacturaCollectionDetalleFactura = detalleFacturaCollectionDetalleFactura.getIdfactu();
                detalleFacturaCollectionDetalleFactura.setIdfactu(factura);
                detalleFacturaCollectionDetalleFactura = em.merge(detalleFacturaCollectionDetalleFactura);
                if (oldIdfactuOfDetalleFacturaCollectionDetalleFactura != null) {
                    oldIdfactuOfDetalleFacturaCollectionDetalleFactura.getDetalleFacturaCollection().remove(detalleFacturaCollectionDetalleFactura);
                    oldIdfactuOfDetalleFacturaCollectionDetalleFactura = em.merge(oldIdfactuOfDetalleFacturaCollectionDetalleFactura);
                }
            }
            for (Recaudo recaudoCollectionRecaudo : factura.getRecaudoCollection()) {
                Factura oldIdfacturaOfRecaudoCollectionRecaudo = recaudoCollectionRecaudo.getIdfactura();
                recaudoCollectionRecaudo.setIdfactura(factura);
                recaudoCollectionRecaudo = em.merge(recaudoCollectionRecaudo);
                if (oldIdfacturaOfRecaudoCollectionRecaudo != null) {
                    oldIdfacturaOfRecaudoCollectionRecaudo.getRecaudoCollection().remove(recaudoCollectionRecaudo);
                    oldIdfacturaOfRecaudoCollectionRecaudo = em.merge(oldIdfacturaOfRecaudoCollectionRecaudo);
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

    public void edit(Factura factura) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Factura persistentFactura = em.find(Factura.class, factura.getIdfactura());
            Clientes idclieOld = persistentFactura.getIdclie();
            Clientes idclieNew = factura.getIdclie();
            Contrato idcontraOld = persistentFactura.getIdcontra();
            Contrato idcontraNew = factura.getIdcontra();
            Propiedad idpropiedOld = persistentFactura.getIdpropied();
            Propiedad idpropiedNew = factura.getIdpropied();
            Collection<DetalleFactura> detalleFacturaCollectionOld = persistentFactura.getDetalleFacturaCollection();
            Collection<DetalleFactura> detalleFacturaCollectionNew = factura.getDetalleFacturaCollection();
            Collection<Recaudo> recaudoCollectionOld = persistentFactura.getRecaudoCollection();
            Collection<Recaudo> recaudoCollectionNew = factura.getRecaudoCollection();
            List<String> illegalOrphanMessages = null;
            for (DetalleFactura detalleFacturaCollectionOldDetalleFactura : detalleFacturaCollectionOld) {
                if (!detalleFacturaCollectionNew.contains(detalleFacturaCollectionOldDetalleFactura)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain DetalleFactura " + detalleFacturaCollectionOldDetalleFactura + " since its idfactu field is not nullable.");
                }
            }
            for (Recaudo recaudoCollectionOldRecaudo : recaudoCollectionOld) {
                if (!recaudoCollectionNew.contains(recaudoCollectionOldRecaudo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Recaudo " + recaudoCollectionOldRecaudo + " since its idfactura field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idclieNew != null) {
                idclieNew = em.getReference(idclieNew.getClass(), idclieNew.getIdcliente());
                factura.setIdclie(idclieNew);
            }
            if (idcontraNew != null) {
                idcontraNew = em.getReference(idcontraNew.getClass(), idcontraNew.getNumcontrato());
                factura.setIdcontra(idcontraNew);
            }
            if (idpropiedNew != null) {
                idpropiedNew = em.getReference(idpropiedNew.getClass(), idpropiedNew.getPropiedadPK());
                factura.setIdpropied(idpropiedNew);
            }
            Collection<DetalleFactura> attachedDetalleFacturaCollectionNew = new ArrayList<DetalleFactura>();
            for (DetalleFactura detalleFacturaCollectionNewDetalleFacturaToAttach : detalleFacturaCollectionNew) {
                detalleFacturaCollectionNewDetalleFacturaToAttach = em.getReference(detalleFacturaCollectionNewDetalleFacturaToAttach.getClass(), detalleFacturaCollectionNewDetalleFacturaToAttach.getIdDetalle());
                attachedDetalleFacturaCollectionNew.add(detalleFacturaCollectionNewDetalleFacturaToAttach);
            }
            detalleFacturaCollectionNew = attachedDetalleFacturaCollectionNew;
            factura.setDetalleFacturaCollection(detalleFacturaCollectionNew);
            Collection<Recaudo> attachedRecaudoCollectionNew = new ArrayList<Recaudo>();
            for (Recaudo recaudoCollectionNewRecaudoToAttach : recaudoCollectionNew) {
                recaudoCollectionNewRecaudoToAttach = em.getReference(recaudoCollectionNewRecaudoToAttach.getClass(), recaudoCollectionNewRecaudoToAttach.getIdrecaudo());
                attachedRecaudoCollectionNew.add(recaudoCollectionNewRecaudoToAttach);
            }
            recaudoCollectionNew = attachedRecaudoCollectionNew;
            factura.setRecaudoCollection(recaudoCollectionNew);
            factura = em.merge(factura);
            if (idclieOld != null && !idclieOld.equals(idclieNew)) {
                idclieOld.getFacturaCollection().remove(factura);
                idclieOld = em.merge(idclieOld);
            }
            if (idclieNew != null && !idclieNew.equals(idclieOld)) {
                idclieNew.getFacturaCollection().add(factura);
                idclieNew = em.merge(idclieNew);
            }
            if (idcontraOld != null && !idcontraOld.equals(idcontraNew)) {
                idcontraOld.getFacturaCollection().remove(factura);
                idcontraOld = em.merge(idcontraOld);
            }
            if (idcontraNew != null && !idcontraNew.equals(idcontraOld)) {
                idcontraNew.getFacturaCollection().add(factura);
                idcontraNew = em.merge(idcontraNew);
            }
            if (idpropiedOld != null && !idpropiedOld.equals(idpropiedNew)) {
                idpropiedOld.getFacturaCollection().remove(factura);
                idpropiedOld = em.merge(idpropiedOld);
            }
            if (idpropiedNew != null && !idpropiedNew.equals(idpropiedOld)) {
                idpropiedNew.getFacturaCollection().add(factura);
                idpropiedNew = em.merge(idpropiedNew);
            }
            for (DetalleFactura detalleFacturaCollectionNewDetalleFactura : detalleFacturaCollectionNew) {
                if (!detalleFacturaCollectionOld.contains(detalleFacturaCollectionNewDetalleFactura)) {
                    Factura oldIdfactuOfDetalleFacturaCollectionNewDetalleFactura = detalleFacturaCollectionNewDetalleFactura.getIdfactu();
                    detalleFacturaCollectionNewDetalleFactura.setIdfactu(factura);
                    detalleFacturaCollectionNewDetalleFactura = em.merge(detalleFacturaCollectionNewDetalleFactura);
                    if (oldIdfactuOfDetalleFacturaCollectionNewDetalleFactura != null && !oldIdfactuOfDetalleFacturaCollectionNewDetalleFactura.equals(factura)) {
                        oldIdfactuOfDetalleFacturaCollectionNewDetalleFactura.getDetalleFacturaCollection().remove(detalleFacturaCollectionNewDetalleFactura);
                        oldIdfactuOfDetalleFacturaCollectionNewDetalleFactura = em.merge(oldIdfactuOfDetalleFacturaCollectionNewDetalleFactura);
                    }
                }
            }
            for (Recaudo recaudoCollectionNewRecaudo : recaudoCollectionNew) {
                if (!recaudoCollectionOld.contains(recaudoCollectionNewRecaudo)) {
                    Factura oldIdfacturaOfRecaudoCollectionNewRecaudo = recaudoCollectionNewRecaudo.getIdfactura();
                    recaudoCollectionNewRecaudo.setIdfactura(factura);
                    recaudoCollectionNewRecaudo = em.merge(recaudoCollectionNewRecaudo);
                    if (oldIdfacturaOfRecaudoCollectionNewRecaudo != null && !oldIdfacturaOfRecaudoCollectionNewRecaudo.equals(factura)) {
                        oldIdfacturaOfRecaudoCollectionNewRecaudo.getRecaudoCollection().remove(recaudoCollectionNewRecaudo);
                        oldIdfacturaOfRecaudoCollectionNewRecaudo = em.merge(oldIdfacturaOfRecaudoCollectionNewRecaudo);
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
                Integer id = factura.getIdfactura();
                if (findFactura(id) == null) {
                    throw new NonexistentEntityException("The factura with id " + id + " no longer exists.");
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
            Factura factura;
            try {
                factura = em.getReference(Factura.class, id);
                factura.getIdfactura();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The factura with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<DetalleFactura> detalleFacturaCollectionOrphanCheck = factura.getDetalleFacturaCollection();
            for (DetalleFactura detalleFacturaCollectionOrphanCheckDetalleFactura : detalleFacturaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Factura (" + factura + ") cannot be destroyed since the DetalleFactura " + detalleFacturaCollectionOrphanCheckDetalleFactura + " in its detalleFacturaCollection field has a non-nullable idfactu field.");
            }
            Collection<Recaudo> recaudoCollectionOrphanCheck = factura.getRecaudoCollection();
            for (Recaudo recaudoCollectionOrphanCheckRecaudo : recaudoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Factura (" + factura + ") cannot be destroyed since the Recaudo " + recaudoCollectionOrphanCheckRecaudo + " in its recaudoCollection field has a non-nullable idfactura field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Clientes idclie = factura.getIdclie();
            if (idclie != null) {
                idclie.getFacturaCollection().remove(factura);
                idclie = em.merge(idclie);
            }
            Contrato idcontra = factura.getIdcontra();
            if (idcontra != null) {
                idcontra.getFacturaCollection().remove(factura);
                idcontra = em.merge(idcontra);
            }
            Propiedad idpropied = factura.getIdpropied();
            if (idpropied != null) {
                idpropied.getFacturaCollection().remove(factura);
                idpropied = em.merge(idpropied);
            }
            em.remove(factura);
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

    public List<Factura> findFacturaEntities() {
        return findFacturaEntities(true, -1, -1);
    }

    public List<Factura> findFacturaEntities(int maxResults, int firstResult) {
        return findFacturaEntities(false, maxResults, firstResult);
    }

    private List<Factura> findFacturaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Factura.class));
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

    public Factura findFactura(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Factura.class, id);
        } finally {
            em.close();
        }
    }

    public int getFacturaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Factura> rt = cq.from(Factura.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
