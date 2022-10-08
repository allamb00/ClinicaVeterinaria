/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EJB;

import java.util.List;
import javax.ejb.Local;
import modelo.Mascota;

/**
 *
 * @author andre
 */
@Local
public interface MascotaFacadeLocal {

    void create(Mascota mascotas);

    void edit(Mascota mascotas);

    void remove(Mascota mascotas);

    Mascota find(Object id);

    List<Mascota> findAll();

    List<Mascota> findRange(int[] range);

    int count();
    
}
