package dataaccess;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import models.User;

public class UserDB {
    
    public User getUser(String email) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        User user = em.find(User.class, email);
        
        em.close();
        
        return user;
    }
    
    public List<User> getUsers() {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        //Query
        TypedQuery tQuery = em.createNamedQuery("User.findAll", User.class);
        
        List<User> userList = tQuery.getResultList();
        
        em.close();
        
        return userList;
    }
    
    public User updateUser(User updatedUser) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction etrans = em.getTransaction();
        
        User afterUpdateUser = null;
        
        try {
            etrans.begin();
            afterUpdateUser = em.merge(updatedUser);
            etrans.commit();
        } catch (Exception e) {
            etrans.rollback();
        } finally {
            em.close();
        }
        
        return afterUpdateUser;
    }
    
    public void addUser(User newUser) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction etrans = em.getTransaction();
        
        try {
            etrans.begin();
            em.persist(newUser);
            etrans.commit();
        } catch (Exception e) {
            etrans.rollback();
        } finally {
            em.close();
        }
    }
    
    public void deleteUser(String userEmail) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction etrans = em.getTransaction();
        
        try {
            etrans.begin();
            em.remove(em.find(User.class, userEmail));
            etrans.commit();
        } catch (Exception e) {
            etrans.rollback();
        } finally {
            em.close();
        }
    }
}
