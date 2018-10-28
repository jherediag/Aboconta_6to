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
import com.demojsf.jpa.model.Clientes;
import com.demojsf.jpa.model.Contrato;
import com.demojsf.jpa.model.Factura;
import com.demojsf.jpa.model.Propiedad;
import com.demojsf.jpa.model.Recaudo;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author DESARROLLO
 */
public class RecaudoJpaController implements Serializable {

    public RecaudoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Recaudo recaudo) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Clientes idcliente = recaudo.getIdcliente();
            if (idcliente != null) {
                idcliente = em.getReference(idcliente.getClass(), idcliente.getIdcliente());
                recaudo.setIdcliente(idcliente);
            }
            Contrato idcontrato = recaudo.getIdcontrato();
            if (idcontrato != null) {
                idcontrato = em.getReference(idcontrato.getClass(), idcontrato.getNumcontrato());
                recaudo.setIdcontrato(idcontrato);
            }
            Factura idfactura = recaudo.getIdfactura();
            if (idfactura != null) {
                idfactura = em.getReference(idfactura.getClass(), idfactura.getIdfactura());
                recaudo.setIdfactura(idfactura);
            }
            Propiedad idpropiedad = recaudo.getIdpropiedad();
            if (idpropiedad != null) {
                idpropiedad = em.getReference(idpropiedad.getClass(), idpropiedad.getPropiedadPK());
                recaudo.setIdpropiedad(idpropiedad);
            }
            em.persist(recaudo);
            if (idcliente != null) {
                idcliente.getRecaudoCollection().add(recaudo);
                idcliente = em.merge(idcliente);
            }
            if (idcontrato != null) {
                idcontrato.getRecaudoCollection().add(recaudo);
                idcontrato = em.merge(idcontrato);
            }
            if (idfactura != null) {
                idfactura.getRecaudoCollection().add(recaudo);
                idfactura = em.merge(idfactura);
            }
            if (idpropiedad != null) {
                idpropiedad.getRecaudoCollection().add(recaudo);
                idpropiedad = em.merge(idpropiedad);
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

    public void edit(Recaudo recaudo) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Recaudo persistentRecaudo = em.find(Recaudo.class, recaudo.getIdrecaudo());
            Clientes idclienteOld = persistentRecaudo.getIdcliente();
            Clientes idclienteNew = recaudo.getIdcliente();
            Contrato idcontratoOld = persistentRecaudo.getIdcontrato();
            Contrato idcontratoNew = recaudo.getIdcontrato();
            Factura idfacturaOld = persistentRecaudo.getIdfactura();
            Factura idfacturaNew = recaudo.getIdfactura();
            Propiedad idpropiedadOld = persistentRecaudo.getIdpropiedad();
            Propiedad idpropiedadNew = recaudo.getIdpropiedad();
            if (idclienteNew != null) {
                idclienteNew = em.getReference(idclienteNew.getClass(), idclienteNew.getIdcliente());
                recaudo.setIdcliente(idclienteNew);
            }
            if (idcontratoNew != null) {
                idcontratoNew = em.getReference(idcontratoNew.getClass(), idcontratoNew.getNumcontrato());
                recaudo.setIdcontrato(idcontratoNew);
            }
            if (idfacturaNew != null) {
                idfacturaNew = em.getReference(idfacturaNew.getClass(), idfacturaNew.getIdfactura());
                recaudo.setIdfactura(idfacturaNew);
            }
            if (idpropiedadNew != null) {
                idpropiedadNew = em.getReference(idpropiedadNew.getClass(), idpropiedadNew.getPropiedadPK());
                recaudo.setIdpropiedad(idpropiedadNew);
            }
            recaudo = em.merge(recaudo);
            if (idclienteOld != null && !idclienteOld.equals(idclienteNew)) {
                idclienteOld.getRecaudoCollection().remove(recaudo);
                idclienteOld = em.merge(idclienteOld);
            }
            if (idclienteNew != null && !idclienteNew.equals(idclienteOld)) {
                idclienteNew.getRecaudoCollection().add(recaudo);
                idclienteNew = em.merge(idclienteNew);
            }
            if (idcontratoOld != null && !idcontratoOld.equals(idcontratoNew)) {
                idcontratoOld.getRecaudoCollection().remove(recaudo);
                idcontratoOld = em.merge(idcontratoOld);
            }
            if (idcontratoNew != null && !idcontratoNew.equals(idcontratoOld)) {
                idcontratoNew.getRecaudoCollection().add(recaudo);
                idcontratoNew = em.merge(idcontratoNew);
            }
            if (idfacturaOld != null && !idfacturaOld.equals(idfacturaNew)) {
                idfacturaOld.getRecaudoCollection().remove(recaudo);
                idfacturaOld = em.merge(idfacturaOld);
            }
            if (idfacturaNew != null && !idfacturaNew.equals(idfacturaOld)) {
                idfacturaNew.getRecaudoCollection().add(recaudo);
                idfacturaNew = em.merge(idfacturaNew);
            }
            if (idpropiedadOld != null && !idpropiedadOld.equals(idpropiedadNew)) {
                idpropiedadOld.getRecaudoCollection().remove(recaudo);
                idpropiedadOld = em.merge(idpropiedadOld);
            }
            if (idpropiedadNew != null && !idpropiedadNew.equals(idpropiedadOld)) {
                idpropiedadNew.getRecaudoCollection().add(recaudo);
                idpropiedadNew = em.merge(idpropiedadNew);
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
                Integer id = recaudo.getIdrecaudo();
                if (findRecaudo(id) == null) {
                    throw new NonexistentEntityException("The recaudo with id " + id + " no longer exists.");
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
            Recaudo recaudo;
            try {
                recaudo = em.getReference(Recaudo.class, id);
                recaudo.getIdrecaudo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The recaudo with id " + id + " no longer exists.", enfe);
            }
            Clientes idcliente = recaudo.getIdcliente();
            if (idcliente != null) {
                idcliente.getRecaudoCollection().remove(recaudo);
                idcliente = em.merge(idcliente);
            }
            Contrato idcontrato = recaudo.getIdcontrato();
            if (idcontrato != null) {
                idcontrato.getRecaudoCollection().remove(recaudo);
                idcontrato = em.merge(idcontrato);
            }
            Factura idfactura = recaudo.getIdfactura();
            if (idfactura != null) {
                idfactura.getRecaudoCollection().remove(recaudo);
                idfactura = em.merge(idfactura);
            }
            Propiedad idpropiedad = recaudo.getIdpropiedad();
            if (idpropiedad != null) {
                idpropiedad.getRecaudoCollection().remove(recaudo);
                idpropiedad = em.merge(idpropiedad);
            }
            em.remove(recaudo);
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

    public List<Recaudo> findRecaudoEntities() {
        return findRecaudoEntities(true, -1, -1);
    }

    public List<Recaudo> findRecaudoEntities(int maxResults, int firstResult) {
        return findRecaudoEntities(false, maxResults, firstResult);
    }

    private List<Recaudo> findRecaudoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Recaudo.class));
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

    public Recaudo findRecaudo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Recaudo.class, id);
        } finally {
            em.close();
        }
    }

    public int getRecaudoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Recaudo> rt = cq.from(Recaudo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
