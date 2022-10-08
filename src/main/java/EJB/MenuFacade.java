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
import modelo.Menu;
import modelo.Rol;
import modelo.Usuario;

/**
 *
 * @author andre
 */
@Stateless
public class MenuFacade extends AbstractFacade<Menu> implements MenuFacadeLocal {

    @PersistenceContext(unitName = "ClinicaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MenuFacade() {
        super(Menu.class);
    }
    
    @Override
    public List<Menu> obtenerMenusUsuario(Usuario usu) {
        List<Menu> listaMenus = null;
        try {
            
            String consultaJPQL;
            Query query;
            
            consultaJPQL = "FROM Menu m WHERE m.idRol.idRol >= :param1";
            query = em.createQuery(consultaJPQL);
            query.setParameter("param1", usu.getRol().getIdRol());
            
            listaMenus = query.getResultList();
            
        } catch(Exception e){
            System.out.println("Error al consultar el menu en base de datos: " + e.getMessage());
        }
        
        return listaMenus;
    }
    
    public Rol obtenerRolUsuario(Usuario usu) {        
        Rol rol = null;
        try {
            String consultaJPQL = "FROM Rol r WHERE r.rol = :param1";
            
            Query query = em.createQuery(consultaJPQL);
            query.setParameter("param1",usu.getRol());
            
            List<Rol> listaRoles = query.getResultList();
            
            if(!listaRoles.isEmpty()) {
                rol = listaRoles.get(0);
                
            }
        } catch(Exception e){
            System.out.println("Error al consultar el rol en base de datos: " + e.getMessage());
        }
        
        return rol;
    }
}
