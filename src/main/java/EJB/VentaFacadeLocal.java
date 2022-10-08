/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EJB;

import java.util.List;
import javax.ejb.Local;
import modelo.Venta;

/**
 *
 * @author andre
 */
@Local
public interface VentaFacadeLocal {

    void create(Venta ventas);

    void edit(Venta ventas);

    void remove(Venta ventas);

    Venta find(Object id);

    List<Venta> findAll();

    List<Venta> findRange(int[] range);

    int count();
    
}
