package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.formbeanfactory.FormBeanFactory;
import org.formbeanfactory.FormBeanFactoryException;
import org.genericdao.MatchArg;

import databean2.Friends;
import databean2.User;
import formbean2.SearchForm2;
import model2.FriendsDAO;
import model2.Model;

public class DeleteFriendAction extends Action {
	private final FormBeanFactory<SearchForm2> formBeanFactory = new FormBeanFactory<>(SearchForm2.class);
	private final FriendsDAO friendsDAO;
	
	public DeleteFriendAction(final Model model) {
		friendsDAO = model.getFriendsDAO();
    }
	
	public String getName() {
        return "deletefriend.do";
    }
	
	public String performPost(final HttpServletRequest request) {
		final HttpSession session = request.getSession();
        final User user = (User)session.getAttribute("user");
        if (user == null) {
            return "login.do";
        }
        
        try {
			final SearchForm2 form = formBeanFactory.create(request);
	
        	final Friends[] allAcceptedRequests = friendsDAO.match(MatchArg.equals("requestAccepted", true));
        	for (int i = 0; i<allAcceptedRequests.length;i++ ) {
        		if (allAcceptedRequests[i].getFriendsUserName().equals(form.getUserName()) && allAcceptedRequests[i].getUserName().equals(user.getUserName())) {
        			friendsDAO.delete(allAcceptedRequests[i].getId());
        			return "viewfriends.do";
        		}
        		if (allAcceptedRequests[i].getFriendsUserName().equals(user.getUserName()) && allAcceptedRequests[i].getUserName().equals(form.getUserName())) {
        			friendsDAO.delete(allAcceptedRequests[i].getId());
        			return "viewfriends.do";
        		}
        	}
        	return "viewfriends.do";
        } catch (FormBeanFactoryException e) {
            request.setAttribute("error",e.getMessage());
            return "error.jsp";
        }catch (final Exception e) {
            request.setAttribute("error", e.getMessage());
            return "error.jsp";
        }
    }

}
