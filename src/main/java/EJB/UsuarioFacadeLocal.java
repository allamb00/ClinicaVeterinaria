/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EJB;

import java.util.List;
import javax.ejb.Local;
import modelo.Usuario;

/**
 *
 * @author andre
 */
@Local
public interface UsuarioFacadeLocal {

    void create(Usuario usuarios);

    void edit(Usuario usuarios);

    void remove(Usuario usuarios);

    Usuario find(Object id);

    List<Usuario> findAll();

    List<Usuario> findRange(int[] range);

    int count();
    
    Usuario verificarUsuario(Usuario usuario);
    
    boolean userAlreadyExists(String userName);
    
}
