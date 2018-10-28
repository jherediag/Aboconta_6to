/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.demojsf.dao;

import java.util.List;

/**
 *
 * @author docente
 * @param <Contrato>
 */
public interface DaoContrato<Contrato> {
    void save(Contrato c);
    void update(Contrato c);
    void delete(Contrato c);
    List<Contrato> getContrato();
}
