<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Manage Users</title>
        <link rel="stylesheet" href="<c:url value="users.css" />">
    </head>
    <body>
        <div class="heading">
            <h1>Manage Users</h1>
        </div>
        
        <div class="main_content">
            <div class="add_user side_element">
                <h2>Add User</h2>
                
                <form action="users?action=add_user" method="post">
                    <div>
                        <label>Email:</label>
                        <input type="text" name="n_user_email">
                    </div>
                    
                    <div>
                        <label>First Name:</label>
                        <input type="text" name="n_user_fname">
                    </div>
                    
                    <div>
                        <label>Last Name:</label>
                        <input type="text" name="n_user_lname">
                    </div>
                    
                    <div>
                        <label>Password:</label>
                        <input type="password" name="n_user_pass">
                    </div>
                    
                    <div>
                        <label>Role:</label>
                        <select name="n_user_role">
                            <c:forEach var="role" items="${roleList}">
                                <option value="${role.roleName}">${role.roleName}</option>
                            </c:forEach>
                        </select>
                    </div>
                    
                    <div class="input_div_not_flex">
                        <input type="checkbox" name="n_user_active" id="nuser_active">
                        <label for="nuser_active">Active</label>
                    </div>
                    
                    <div class="form_submit_div">
                        <input type="submit" value="Add User">
                    </div>
                </form>
                
                <c:if test="${addUserMessage != null}">
                    <div class="any_message">${addUserMessage}</div>
                </c:if>
            </div>
            
            <div class="user_list">
                <div>
                    <table>
                        <tr>
                            <th>Email</th>
                            <th>First Name</th>
                            <th>Last Name</th>
                            <th>Role</th>
                            <th>Edit</th>
                            <th>Delete</th>
                        </tr>
                        
                        <c:forEach var="user" items="${userList}">
                            <tr>
                                <td>
                                    ${user.email} <c:if test="${user.active}">(Active)</c:if>
                                </td>
                                <td>${user.firstName}</td>
                                <td>${user.lastName}</td>
                                <td>${user.role.roleName}</td>
                                <td>
                                    <a href="<c:url value="users">
                                           <c:param name="action" value="edit" />
                                           <c:param name="userEmail" value="${user.email}" />
                                    </c:url>">Edit</a>
                                </td>
                                <td>
                                    <a href="<c:url value="users">
                                           <c:param name="action" value="delete" />
                                           <c:param name="userEmail" value="${user.email}" />
                                    </c:url>">Delete</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
                
                <c:if test="${deleteUserMessage != null}">
                    <div class="any_message delete_user_message">${deleteUserMessage}</div>
                </c:if>
            </div>
            
            <div class="edit_user side_element">
                <h2>Edit User</h2>
                
                <c:choose>
                    <c:when test="${selectedUser == null}">
                        <div>Select a user to update.</div>
                    </c:when>
                    
                    <c:otherwise>
                    <form action="users" method="post">
                    <div>${selectedUser.email}</div>
                    
                    <div>
                        <label>First Name:</label>
                        <input type="text" name="e_user_fname" value="${selectedUser.firstName}">
                    </div>
                    
                    <div>
                        <label>Last Name:</label>
                        <input type="text" name="e_user_lname" value="${selectedUser.lastName}">
                    </div>
                    
                    <div>
                        <label>Role:</label>
                        <select name="e_user_role">
                            <c:forEach var="role" items="${roleList}">
                                <option value="${role.roleName}"<c:if test="${role.roleName eq selectedUser.role.roleName}"> selected</c:if>>${role.roleName}</option>
                            </c:forEach>
                        </select>
                    </div>
                    
                    <div class="input_div_not_flex">
                        <input type="checkbox" name="e_user_active" id="euser_active"<c:if test="${selectedUser.active}"> checked</c:if>>
                        <label for="euser_active">Active</label>
                    </div>
                    
                    <div class="input_div_not_flex">
                        <input type="checkbox" name="e_user_changepass" id="euser_changepass" onclick="enableDisableNewPasswordInput()">
                        <label for="euser_changepass">Change Password</label>
                        
                        <div>
                            <input type="password" name="e_user_newpass" id="euser_newpass" style="display: none;" disabled>
                        </div>
                    </div>
                    
                    <div class="form_submit_div">
                        <input type="hidden" name="action" value="update">
                        <input type="hidden" name="userEmail" value="${selectedUser.email}">
                        <input type="submit" value="Update User">
                    </div>
                    </form>
                    </c:otherwise>
                </c:choose>
                
                <c:if test="${updateUserMessage != null}">
                    <div class="any_message">${updateUserMessage}</div>
                </c:if>
                
            </div>
        </div>
    </body>
    
    <script>
        function enableDisableNewPasswordInput() {
            let passCheckbox = document.getElementById("euser_changepass");
            let passInput = document.getElementById("euser_newpass");
            
            if (passCheckbox.checked) {
                passInput.setAttribute("style", "display: initial;");
                passInput.removeAttribute("disabled");
            } else {
                passInput.setAttribute("style", "display: none;");
                passInput.setAttribute("disabled", "");
            }
        }
    </script>
</html>
