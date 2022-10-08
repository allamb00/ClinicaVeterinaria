/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EJB;

import java.util.List;
import javax.ejb.Local;
import modelo.Tarifa;

/**
 *
 * @author andre
 */
@Local
public interface TarifaFacadeLocal {

    void create(Tarifa tarifa);

    void edit(Tarifa tarifa);

    void remove(Tarifa tarifa);

    Tarifa find(Object id);

    List<Tarifa> findAll();

    List<Tarifa> findRange(int[] range);

    int count();
    
    Tarifa getHospitalizacion();
    
}
