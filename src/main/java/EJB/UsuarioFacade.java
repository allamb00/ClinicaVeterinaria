/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EJB;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import modelo.Usuario;

/**
 *
 * @author andre
 */
@Stateless
public class UsuarioFacade extends AbstractFacade<Usuario> implements UsuarioFacadeLocal {

    @PersistenceContext(unitName = "ClinicaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioFacade() {
        super(Usuario.class);
    }
    
    @Override
    public Usuario verificarUsuario (Usuario usuario) {
        Usuario us = null;
        try {
            String consultaJPQL = "FROM Usuario u WHERE u.userName = :param1 and u.password = :param2 and u.eliminado = 0";
            
            Query query = em.createQuery(consultaJPQL);
            query.setParameter("param1",usuario.getUserName());
            query.setParameter("param2",usuario.getPassword());
            
            List<Usuario> listaUsuarios = query.getResultList();
            
            if(!listaUsuarios.isEmpty())
                us = listaUsuarios.get(0);
        } catch(Exception e){
            System.out.println("Error al consultar el usuario en base de datos: " + e.getMessage());
        }
        
        return us;
    }

    @Override
    public boolean userAlreadyExists(String userName) {
        try {
            String consultaJPQL = "FROM Usuario u WHERE u.userName = :param1";
            
            Query query = em.createQuery(consultaJPQL);
            query.setParameter("param1",userName);
            
            List<Usuario> listaUsuarios = query.getResultList();
            
            if(!listaUsuarios.isEmpty())
                return true;
        } catch(Exception e){
            System.out.println("Error al consultar el usuario en base de datos: " + e.getMessage());
        }
        
        return false;
    }
}
