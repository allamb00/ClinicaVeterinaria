/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EJB;

import java.util.List;
import javax.ejb.Local;
import modelo.Constantes;

/**
 *
 * @author andre
 */
@Local
public interface ConstantesFacadeLocal {

    void create(Constantes constantes);

    void edit(Constantes constantes);

    void remove(Constantes constantes);

    Constantes find(Object id);

    List<Constantes> findAll();

    List<Constantes> findRange(int[] range);

    int count();
    
}
