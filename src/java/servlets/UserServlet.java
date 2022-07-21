package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.Role;
import models.User;
import services.RoleService;
import services.UserService;

public class UserServlet extends HttpServlet {
    //Action values
    private final String ACTION_ADD_USER = "add_user";
    private final String ACTION_EDIT_USER = "edit";
    private final String ACTION_DELETE_USER = "delete";
    private final String ACTION_UPDATE_USER = "update";
    

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RoleService roleService = new RoleService();
        UserService userService = new UserService();
        
        request.setAttribute("roleList", roleService.getAll());
        request.setAttribute("userList", userService.getUsers());
        
        if(request.getParameter("action") != null && request.getParameter("userEmail") != null) {
            String action = request.getParameter("action");
            String userEmail = request.getParameter("userEmail");
            
            if(action.equals(ACTION_DELETE_USER)) {
                userService.deleteUser(userEmail);
                request.setAttribute("deleteUserMessage", "User deleted.");
            }
            
            if(action.equals(ACTION_EDIT_USER)) {
                request.getSession().setAttribute("selectedUser", userService.getUser(userEmail));
            }
        }
        
        getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
    
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if(action == null) {
            response.sendRedirect("users");
            return;
        }
        
        // Services
        RoleService roleService = new RoleService();
        UserService userService = new UserService();
        
        if(action.equals(ACTION_ADD_USER)) {
            //Getting parameter values
            String email = request.getParameter("n_user_email");
            String firstName = request.getParameter("n_user_fname");
            String lastName = request.getParameter("n_user_lname");
            String password = request.getParameter("n_user_pass");
            String roleName = request.getParameter("n_user_role");
            
            boolean active = false;
            if(request.getParameter("n_user_active") != null) {
                active = true;
            }
            
            String outPutMessage = "";
            
            // Checking if all parameter are available
            if(email == null || firstName == null || lastName == null || password == null || roleName == null) {
                outPutMessage = "Invalid data entered.";
            } else {
                //Checking if role exist
                if(roleService.isValidRoleName(roleName)) {
                    Role role = roleService.getRole(roleName);
                    User newUser = new User(email, active, firstName, lastName, password);
                    newUser.setRole(role);
                    
                    //Adding and checking if input is valid
                    if(!userService.checkUserEntry(newUser, true)) {
                        outPutMessage = "Invalid data entered.";
                    } else {
                        userService.addUser(newUser);
                        outPutMessage = "User added.";
                    }
                } else {
                    outPutMessage = "Invalid data entered.";
                }
            }
            
            request.setAttribute("addUserMessage", outPutMessage);
            
        } else if (action.equals(ACTION_UPDATE_USER)) {
            //Getting parameter values
            String email = request.getParameter("userEmail");
            String firstName = request.getParameter("e_user_fname");
            String lastName = request.getParameter("e_user_lname");
            String roleName = request.getParameter("e_user_role");
       
            boolean active = false;
            if(request.getParameter("e_user_active") != null) {
                active = true;
            }
            
            String outputMessage = "";
            
            //Checking if password change requested
            boolean isPasswordChanged = false;
            String password = request.getParameter("e_user_newpass");
            
            if(request.getParameter("e_user_changepass") != null) {
                isPasswordChanged = true;
            }
            
            // Checking if all parameter are available
            if(email == null || firstName == null || lastName == null || (isPasswordChanged && password == null) || roleName == null) {
                outputMessage = "Invalid data entered.";
            } else {
                //Checking if role exist
                if(roleService.isValidRoleName(roleName)) {
                    Role role = roleService.getRole(roleName);
                    User updatedUser = userService.getUser(email);
                    updatedUser.setActive(active);
                    updatedUser.setFirstName(firstName);
                    updatedUser.setLastName(lastName);
                    updatedUser.setRole(role);
                    
                    //Adding and checking if input is valid
                    if(!userService.checkUserEntry(updatedUser, isPasswordChanged)) {
                        outputMessage = "Invalid data entered.";
                    } else {
                        if(isPasswordChanged) {
                            updatedUser.setPassword(password);
                        }
                        
                        userService.updateUser(updatedUser);
                        
                        outputMessage = "User updated.";
                    }
                } else {
                    outputMessage = "Invalid data entered.";
                }
            }
            
            request.setAttribute("updateUserMessage", outputMessage);
            request.getSession().setAttribute("selectedUser", userService.getUser(email));
        }
        
        request.setAttribute("roleList", roleService.getAll());
        request.setAttribute("userList", userService.getUsers());
        getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
    }

}
