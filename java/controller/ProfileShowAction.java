package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.RollbackException;

import model2.UserInfoDAO;
import model2.UserDAO;
import databean2.User;
import databean2.UserInfo;
//import model.PostDAO;
import model2.Model;

public class ProfileShowAction extends Action {
	private UserInfoDAO userInfoDAO;
	private UserDAO userCredentialDAO;
	//private PostDAO postDAO;
	public ProfileShowAction(Model model) {
		userInfoDAO = model.getUserInfoDAO();
		userCredentialDAO=model.getUserDAO();
		//postDAO = model.getPostDAO();
    }

    public String getName() {
        return "profileshow.do";
    }
    
    public String performGet(HttpServletRequest request) {
    	return performPost(request);
    }
    
    public String performPost(HttpServletRequest request) {
    	HttpSession session = request.getSession();
        if (session.getAttribute("user") == null) {//
            return "login.do";
        }
        User sessionUser = (User)session.getAttribute("user");
        
        try {
        	User userCredential = userCredentialDAO.read(sessionUser.getUserName());
        	UserInfo userInfo = userInfoDAO.read(sessionUser.getUserName());
        	//Posts[] userPosts = PostDAO.match(MatchArg.equals("userName", sessionUser.getUserName()));
        	//request.setAttribute("posts",userPosts);
        	request.setAttribute("userCredential", userCredential);
        	request.setAttribute("userInfo", userInfo);
        	return "/pages/profile.jsp";
        }
        catch(RollbackException e) {
        	System.out.println("Something went wrong on posting");
        }
        return "/pages/profile.jsp";
    }
}
