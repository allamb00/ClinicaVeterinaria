/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EJB;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import modelo.Tarifa;

/**
 *
 * @author andre
 */
@Stateless
public class TarifaFacade extends AbstractFacade<Tarifa> implements TarifaFacadeLocal {

    @PersistenceContext(unitName = "ClinicaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TarifaFacade() {
        super(Tarifa.class);
    }

    @Override
    public Tarifa getHospitalizacion() {
        Tarifa hos = null;
        try {
            String consultaJPQL = "FROM Tarifa t WHERE t.campo.nombre = 'HOSPITALIZACIÓN'";
//            String consultaJPQL = "FROM Tarifa t WHERE t.campo.nombre = :param1";
            
            Query query = em.createQuery(consultaJPQL);
//            query.setParameter("param1","HOSPITALIZACIÓN");
            
            List<Tarifa> listaTarifas = query.getResultList();
            
            if(!listaTarifas.isEmpty())
                hos = listaTarifas.get(0);
        } catch(Exception e){
            System.out.println("Error al buscar la tarifa en base de datos: " + e.getMessage());
        }
        
        return hos;
    }
    
}
