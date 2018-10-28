
package com.demojsf.dao;

import java.util.List;

/**
 *
 * @author docente
 * @param <Comision>
 */
public interface DaoComision<Comision> {
    void save(Comision c);
    void update(Comision c);
    void delete(Comision c);
    List<Comision> getComision();
}
