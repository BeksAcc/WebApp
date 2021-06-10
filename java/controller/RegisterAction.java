package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.formbeanfactory.FormBeanFactory;
import org.formbeanfactory.FormBeanFactoryException;
import org.genericdao.DuplicateKeyException;
import org.genericdao.RollbackException;

import databean2.UserInfo;
import databean2.User;
import formbean2.RegisterForm;
import model2.Model;
import model2.UserDAO;
import model2.UserInfoDAO;

public class RegisterAction extends Action{
	private FormBeanFactory<RegisterForm> formBeanFactory = new FormBeanFactory<>(RegisterForm.class);

    private UserDAO userCredentialDAO;
    private UserInfoDAO userInfoDAO;
    
    public RegisterAction(Model model) {
    	userCredentialDAO = model.getUserDAO();
    	userInfoDAO = model.getUserInfoDAO();
    }
    public String getName() {
        return "register.do";
    }
    public String performGet(HttpServletRequest request) {
    	HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        User admin = (User)session.getAttribute("admin");
        if (user != null) {
            return "posts.do";
        }
        if (admin != null) {
            return "adduser.do";
        }

        // Otherwise, just display the login page.
        request.setAttribute("form", new RegisterForm());
        return "/pages/register.jsp";
    }
    public String performPost(HttpServletRequest request) {
    	HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        User admin = (User)session.getAttribute("admin");
        if (user != null) {
            return "posts.do";
        }
        if (admin != null) {
            return "adduser.do";
        }
        try {
        	RegisterForm form = formBeanFactory.create(request);
            request.setAttribute("form", form);
            if (form.hasValidationErrors()) {
                return "/pages/register.jsp";
            }

            if (form.getAction().equals("Register")) {
            	UserInfo newUserInfo = new UserInfo();
            	User newUser = new User();
            	newUserInfo.setEmail(form.getEmail());
            	newUserInfo.setFirstName(form.getFirstName());
            	newUserInfo.setLastName(form.getLastName());
            	newUserInfo.setGender(form.getGender());
            	newUserInfo.setBirthDate(form.getBirthDate());
            	newUserInfo.setAvatar(form.getAvatar());
            	newUser.setUserName(form.getEmail());
            	newUser.setPassword(form.getPassword());
            	newUser.setRole("user");
            	try {
                	userCredentialDAO.create(newUser);
                	userInfoDAO.create(newUserInfo);
                	session.setAttribute("user", newUser);
                	
                } catch (DuplicateKeyException e) {
                    form.addFieldError("email", "This email already exist");
                    return "/pages/register.jsp";
                }
            	
            }
            
            return "profileshow.do";
        }
        catch (FormBeanFactoryException e) {
            request.setAttribute("error",e.getMessage());
            return "error.jsp";
        } 
        catch (RollbackException e) {
            request.setAttribute("error", e.getMessage());
            return "error.jsp";
        }
    }
}
