package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.formbeanfactory.FormBeanFactory;
import org.genericdao.DuplicateKeyException;
import org.formbeanfactory.FormBeanFactoryException;
import org.genericdao.RollbackException;

import databean2.User;
import databean2.UserInfo;
import formbean2.AddUserForm;
import model2.Model;
import model2.UserDAO;
import model2.UserInfoDAO;

public class UpdateUserAction extends Action {
	private FormBeanFactory<AddUserForm> formBeanFactory = new FormBeanFactory<>(AddUserForm.class);
	private UserInfoDAO userInfoDAO;
	private UserDAO userCredentialDAO;
	
	public UpdateUserAction(Model model) {
    	userCredentialDAO = model.getUserDAO();
    	userInfoDAO = model.getUserInfoDAO();
    }
	
	public String getName() {
        return "updateuser.do";
    }
	
	public String performPost(HttpServletRequest request) {
		HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        // if (admin == null) {
        //     return "login.do";
        // }
        
        try {
			AddUserForm form = formBeanFactory.create(request);

            if (form.getAction().equals("Update")) {
            	UserInfo newUserInfo = new UserInfo();
            	User newUser = new User();
            	newUserInfo.setEmail(form.getEmail());
            	System.out.println(form.getEmail());
            	newUserInfo.setFirstName(form.getFirstName());
            	newUserInfo.setLastName(form.getLastName());
            	newUserInfo.setGender(form.getGender());
            	newUserInfo.setBirthDate(form.getBirthDate());
            	newUserInfo.setAvatar(form.getAvatar());
            	newUser.setUserName(form.getEmail());
            	newUser.setPassword(form.getPassword());
            	newUser.setRole(form.getRole());
            	try {
            		userInfoDAO.delete(user.getUserName());
                	userInfoDAO.create(newUserInfo);     
                	return "profileshow.do";
				} 
				
				catch (DuplicateKeyException e) {
                    form.addFieldError("email", "Something went wrong");
                    return "/pages/profile.jsp";
                }
            }
            return "profileshow.do";
		}catch(FormBeanFactoryException e){
			request.setAttribute("error", e.toString());
			return "error.jsp";
		}
        catch (RollbackException e) {
            request.setAttribute("error", e.getMessage());
            return "error.jsp";
		}
		
    }
}
