package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.formbeanfactory.FormBeanFactory;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.formbeanfactory.FormBeanFactoryException;
import databean2.Friends;
import databean2.User;
import formbean2.AddFriendForm;
import model2.FriendsDAO;
import model2.Model;

public class DeclineFriendRequesteAction extends Action {
	private FormBeanFactory<AddFriendForm> formBeanFactory = new FormBeanFactory<>(AddFriendForm.class);
	private FriendsDAO friendsDAO;
	
	public DeclineFriendRequesteAction(Model model) {
		friendsDAO = model.getFriendsDAO();
    }
	
	public String getName() {
        return "declinefriend.do";
    }
	
	public String performPost(HttpServletRequest request) {
		HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        if (user == null) {
            return "login.do";
        }
        
        try {
			AddFriendForm form = formBeanFactory.create(request);

        	Friends[] allCameRequests = friendsDAO.match(MatchArg.equals("friendsUserName", user.getUserName()));
        	for (int i = 0; i<allCameRequests.length;i++) {
        		System.out.println("Come to delete");
        		if (allCameRequests[i].getUserName().equals(form.getUserName()) && allCameRequests[i].getFriendsUserName().equals(form.getFriendsUserName())) {
        			try {
        				friendsDAO.delete(allCameRequests[i].getId());
        				return "viewrequests.do";
        			}
        			catch (RollbackException e) {
            		}
        		}
        	}
        	return "viewrequests.do";
		} 
		catch(FormBeanFactoryException e){
			request.setAttribute("error", e.getMessage());
            return "error.jsp";		
		}
		catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            return "error.jsp";
        }
    }
	
}
