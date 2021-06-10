package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.formbeanfactory.FormBeanFactory;
import org.formbeanfactory.FormBeanFactoryException;
import org.genericdao.RollbackException;

import databean2.User;
import databean2.UserInfo;
import formbean2.SearchForm2;
import model2.UserInfoDAO;
import model2.Model;

public class ViewOthersProfileAction extends Action {
	private FormBeanFactory<SearchForm2> formBeanFactory = new FormBeanFactory<>(SearchForm2.class);
	private UserInfoDAO userInfoDAO;
	
	public ViewOthersProfileAction(Model model) {
		userInfoDAO = model.getUserInfoDAO();
    }
	
	public String getName() {
        return "viewothersprofile.do";
    }
	public String performGet(HttpServletRequest request) {
		return performPost(request);
	}
	public String performPost(HttpServletRequest request) {
		HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        if (user == null) {
            return "login.do";
        }
        
        try {
			SearchForm2 form = formBeanFactory.create(request);
        	UserInfo userInfo = userInfoDAO.read(form.getUserName());
			request.setAttribute("userInfo", userInfo);
        	return "/pages/viewothersprofile.jsp";
		}
		catch(FormBeanFactoryException e){
			request.setAttribute("error", e.toString());
			return "error.jsp";
		}
        catch(RollbackException e) {
        	System.out.println("Something went wrong on posting");
        }
        return "/pages/viewothersprofile.jsp";
    }
	
}
