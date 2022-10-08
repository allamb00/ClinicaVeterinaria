/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EJB;

import java.util.List;
import javax.ejb.Local;
import modelo.Cliente;

/**
 *
 * @author andre
 */
@Local
public interface ClienteFacadeLocal {

    void create(Cliente clientes);

    void edit(Cliente clientes);

    void remove(Cliente clientes);

    Cliente find(Object id);

    List<Cliente> findAll();

    List<Cliente> findRange(int[] range);

    int count();
    
    
}
