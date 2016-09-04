/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal.facade;

import dal.entity.TipoProducto;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Ever
 */
@Stateless
public class TipoProductoFacade extends AbstractFacade<TipoProducto> {

    @PersistenceContext(unitName = "GestorVentasPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TipoProductoFacade() {
        super(TipoProducto.class);
    }
    
}
