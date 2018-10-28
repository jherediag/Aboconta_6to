
package com.demojsf.dao;

import java.util.List;

/**
 *
 * @author docente
 * @param <Concepto>
 */
public interface DaoConcepto<Concepto> {
    void save(Concepto c);
    void update(Concepto c);
    void delete(Concepto c);
    List<Concepto> getConcepto();
}
