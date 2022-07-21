package dataaccess;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;
import models.Role;

public class RoleDB {
    
    public Role getRole(int roleId) {     
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        //Getting role
        Role role = em.find(Role.class, roleId);
        
        //Clossing Entity Manager
        em.close();
        
        return role;
    }
    
    public Role getRole(String roleName) {
        Role role = null;
        
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        //Query
        TypedQuery tQuery = em.createNamedQuery("Role.findByRoleName", Role.class);
        tQuery.setParameter("roleName", roleName);
        
        
        
        try {
            role = (Role) tQuery.getSingleResult();            
        } catch (NoResultException | NonUniqueResultException e) {
        } finally {
            em.close();
        }
        
        return role;
    }
    
    public List<Role> getAll() {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        //Query
        TypedQuery tQuery = em.createNamedQuery("Role.findAll", Role.class);
                
        List<Role> roleList = tQuery.getResultList();
        
        em.close();
        
        return roleList;
    }
}
