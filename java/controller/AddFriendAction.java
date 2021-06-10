package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.formbeanfactory.FormBeanFactory;
import org.genericdao.DuplicateKeyException;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;

import databean2.User;
import databean2.UserInfo;

import formbean2.SearchForm;

import model2.Model;
import model2.UserInfoDAO;

public class AddFriendAction extends Action {
	private FormBeanFactory<SearchForm> formBeanFactory = new FormBeanFactory<>(SearchForm.class);
	private UserInfoDAO userInfoDAO;
	
	public AddFriendAction(Model model) {
    	userInfoDAO = model.getUserInfoDAO();
    }
	
	public String getName() {
        return "addfriend.do";
    }
	public String performGet(HttpServletRequest request) {
		HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        if (user == null) {
            return "login.do";
        }
        try {
        	request.setAttribute("sessionUser",user);
        	UserInfo[] usersInfo = userInfoDAO.match();
        	request.setAttribute("usersInfo", usersInfo);
        }
        catch(RollbackException e) {
        	System.out.println("Something went wrong on getting");
        }
        // Otherwise, just display the login page.
        request.setAttribute("form", new SearchForm());
        return "/pages/addfriend.jsp";
    }

	public String performPost(HttpServletRequest request) {
		HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        if (user == null) {
            return "login.do";
        }
        return "";
	}
}
