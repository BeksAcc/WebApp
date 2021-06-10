package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.MatchArg;
import org.genericdao.RollbackException;

import databean2.Friends;
import databean2.User;
import model2.FriendsDAO;
import model2.Model;

public class ViewFriendsAction extends Action {
	private FriendsDAO friendsDAO;
	
	public ViewFriendsAction(Model model) {
		friendsDAO = model.getFriendsDAO();
    }
	
	public String getName() {
        return "viewfriends.do";
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
        	request.setAttribute("sessionUser",user);
        	Friends[] allAcceptedRequests = friendsDAO.match(MatchArg.equals("requestAccepted", true));
        	Friends[] friendsOfUser = new Friends[allAcceptedRequests.length];
        	int counter = 0;
        	for (int i = 0; i<allAcceptedRequests.length; i++ ) {
        		if (allAcceptedRequests[i].getFriendsUserName().equals(user.getUserName()) || allAcceptedRequests[i].getUserName().equals(user.getUserName())) {
        			friendsOfUser[counter]=allAcceptedRequests[i];
        			counter++;
        		}
        	}

        	request.setAttribute("allFriends", friendsOfUser);
            return "/pages/viewfriends.jsp";
        }
        catch(RollbackException e) {
        	System.out.println("Something went wrong on getting");
        }

        return "/pages/viewfriends.jsp";
	}
}
