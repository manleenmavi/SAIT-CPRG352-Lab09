package services;

import dataaccess.RoleDB;
import java.util.List;
import models.Role;

public class RoleService {
    public Role getRole(int roleId) {
        RoleDB roleDB = new RoleDB();
        return roleDB.getRole(roleId);
    }
    
    public Role getRole(String roleName) {
        RoleDB roleDB = new RoleDB();
        return roleDB.getRole(roleName);
    }
    
    public List<Role> getAll() {
        RoleDB roleDB = new RoleDB();
        return roleDB.getAll();
    }
    
    public boolean isValidRoleID(int roleId) {
        Role role = getRole(roleId);
        
        if (role == null) {
            return false;
        } else {
            return true;
        }
    }
    
    public boolean isValidRoleName(String roleName) {
        List<Role> roleList = getAll();
        boolean isValid = false;
        
        for(Role role : roleList) {
            if(role.getRoleName().equals(roleName)) {
               isValid = true;
               break;
            }
        }
        
         return isValid;
    }
    
    public int getRoleID(String roleName) {
        List<Role> roleList = getAll();
        int roleID = -1;
        
        for(Role role : roleList) {
            if(role.getRoleName().equals(roleName)) {
               roleID = role.getRoleId();
               break;
            }
        }
        
         return roleID;
        
    }
    
    public String getRoleName(int roleId) {
        RoleDB roleDB = new RoleDB();
        Role role = roleDB.getRole(roleId);
        
        if(role == null) {
            return "";
        } else {
            return role.getRoleName();
        }
    }
    
}
