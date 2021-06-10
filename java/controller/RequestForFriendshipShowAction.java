package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.MatchArg;
import org.genericdao.RollbackException;

import databean2.Friends;
import databean2.User;
import model2.FriendsDAO;
import model2.Model;

public class RequestForFriendshipShowAction extends Action {
		private FriendsDAO friendsDAO;
		
		public RequestForFriendshipShowAction(Model model) {
			friendsDAO = model.getFriendsDAO();
	    }
		
		public String getName() {
	        return "viewrequests.do";
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
	        	Friends[] requestFrom = friendsDAO.match(MatchArg.equals("friendsUserName", user.getUserName()));
	        	Friends[] notReactedRequests = new Friends[requestFrom.length];
	        	for (int i = 0; i<requestFrom.length; i++ ) {
	        		if (requestFrom[i].isRequestAccepted()==false && requestFrom[i].isRequestDeclined()==false) {
	        			if(!requestFrom[i].getFriendsUserName().equals(user.getUserName()));
	        				notReactedRequests[i]=requestFrom[i];
	        				request.setAttribute("requestFrom", notReactedRequests);
	        		}
	        	}
	        }
	        catch(RollbackException e) {
	        	System.out.println("Something went wrong on getting");
	        }
	        // Otherwise, just display the login page.
	        return "/pages/viewrequests.jsp";
		}
}
