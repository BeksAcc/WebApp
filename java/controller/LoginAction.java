package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.formbeanfactory.FormBeanFactory;
import org.formbeanfactory.FormBeanFactoryException;
import org.genericdao.DuplicateKeyException;
import org.genericdao.RollbackException;

import databean2.User;
import formbean2.LoginForm;
import model2.Model;
import model2.UserDAO;

public class LoginAction extends Action {
    private FormBeanFactory<LoginForm> formBeanFactory = new FormBeanFactory<>(LoginForm.class);
    
    private UserDAO userDAO;

    public LoginAction(Model model) {
        userDAO = model.getUserDAO();
    }

    public String getName() {
        return "login.do";
    }
    
    public String performGet(HttpServletRequest request) {
        // If user is already logged in, redirect to todolist.do
        HttpSession session = request.getSession();
        if (session.getAttribute("user") != null) {
            return "posts.do";
        }

        // Otherwise, just display the login page.
        request.setAttribute("form", new LoginForm());
        return "/pages/login.jsp";
    }
        
    public String performPost(HttpServletRequest request) {
        // If user is already logged in, redirect to todolist.do
        HttpSession session = request.getSession();
        if (session.getAttribute("user") != null) {
            return "posts.do";
        }

        try {
            LoginForm form = formBeanFactory.create(request);
            request.setAttribute("form", form);

            // Any validation errors?
            if (form.hasValidationErrors()) {
                return "/pages/login.jsp";
            }

            if (form.getAction().equals("Register")) {
                User newUser = new User();
                newUser.setUserName(form.getUserName());
                newUser.setPassword(form.getPassword());
                try {
                    userDAO.create(newUser);
                    session.setAttribute("user", newUser);
                    return ("posts.do");
                } catch (DuplicateKeyException e) {
                    form.addFieldError("userName",
                            "A user with this name already exists");
                    return "/pages/login.jsp";
                }
            }

            // Look up the user
            User user = userDAO.read(form.getUserName());

            if (user == null) {
                form.addFieldError("userName", "User Name not found");
                return "/pages/login.jsp";
            }

            // Check the password
            if (!user.getPassword().equals(form.getPassword())) {
                form.addFieldError("password", "Incorrect password");
                return "/pages/login.jsp";
            }

            // Attach (this copy of) the user bean to the session
            session.setAttribute("user", user);

            // Redirect to the "todolist" action
            return "posts.do";
        }catch(FormBeanFactoryException e){
            request.setAttribute("error", e.getMessage());
            return "error.jsp";
        } catch (RollbackException e) {
            request.setAttribute("error",e.getMessage());
            return "error.jsp";
        }
    }
}
