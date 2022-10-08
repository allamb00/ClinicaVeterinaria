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
 import modelo.Rol;

 /**
  *
  * @author andre
  */
@Stateless
public class RolFacade extends AbstractFacade<Rol> implements RolFacadeLocal {
 
    @PersistenceContext(unitName = "ClinicaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RolFacade() {
        super(Rol.class);
    }
     
    @Override
    public Rol buscar(Rol rol){
        Rol nRol = null;
        try {
            String consultaJPQL = "FROM Rol r WHERE r.rol = :param1";
            
            Query query = em.createQuery(consultaJPQL);
            query.setParameter("param1",rol.getRol());
            
            List<Rol> listaRoles = query.getResultList();
            
            if(!listaRoles.isEmpty())
                nRol = listaRoles.get(0);
            else
                System.out.println("Rol no encontrado");
        } catch(Exception e){
            System.out.println("Error al consultar el rol en base de datos: " + e.getMessage());
        }
        return nRol;
    }
    
 }
