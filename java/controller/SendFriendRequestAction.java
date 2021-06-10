package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.formbeanfactory.FormBeanFactory;
import org.genericdao.DuplicateKeyException;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.formbeanfactory.FormBeanFactoryException;
import databean2.User;
import databean2.Friends;
import formbean2.AddFriendForm;
import model2.Model;
import model2.FriendsDAO;

public class SendFriendRequestAction extends Action {
	private FormBeanFactory<AddFriendForm> formBeanFactory = new FormBeanFactory<>(AddFriendForm.class);
	private FriendsDAO friendsDAO;
	
	public SendFriendRequestAction(Model model) {
		friendsDAO = model.getFriendsDAO();
    }
	
	public String getName() {
        return "sendaddrequest.do";
    }
	
	public String performPost(HttpServletRequest request) {
		HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        if (user == null) {
            return "login.do";
        }
        
        try {
			AddFriendForm form = formBeanFactory.create(request);
            if (form.getAction().equals("+ Add Friend")) {
            	Friends newFriend = new Friends();
            	newFriend.setUserName(user.getUserName());
            	newFriend.setFriendsUserName(form.getFriendsUserName());
            	newFriend.setRequestAccepted(false);
            	newFriend.setRequestDeclined(false);
            	newFriend.setRequestSend(true);
            	try {
            		Friends[] friendsOfUser = friendsDAO.match(MatchArg.equals("userName", user.getUserName()));
            		if (friendsOfUser == null){
            			friendsDAO.create(newFriend);
            			return "addfriend.do";
            		}
            		else {
            			for (int i =0; i<friendsOfUser.length;i++) {
            				if (friendsOfUser[i].getFriendsUserName().equals(form.getFriendsUserName())==true && friendsOfUser[i].getUserName().equals(form.getUserName())==true){
            					return "addfriend.do";
            				}
            				else if(friendsOfUser[i].getFriendsUserName().equals(form.getUserName())==true && friendsOfUser[i].getUserName().equals(form.getFriendsUserName())==true) {
            					return "addfriend.do";
            				}
            			}
            			for (int i =0; i<friendsOfUser.length;i++) {
            				if (friendsOfUser[i].getFriendsUserName().equals(form.getFriendsUserName()) && friendsOfUser[i].isRequestSend()==false){
            					friendsDAO.delete(friendsOfUser[i].getId());
            					friendsDAO.create(newFriend);
            					return "addfriend.do";
            				}
            				else if(friendsOfUser[i].getFriendsUserName().equals(form.getFriendsUserName()) && friendsOfUser[i].isRequestSend()==true) {
            					return "addfriend.do";
            				}
            			}
            			friendsDAO.create(newFriend);
            			return "addfriend.do";
            		}
				} 
				catch (DuplicateKeyException e) {
                    form.addFieldError("email", "Something went wrong");
                    return "addfriend.jsp";
                }
            	
            }
            
            return "addfriend.do";
		}
		catch(FormBeanFactoryException e){
			request.setAttribute("error", e.getMessage());
            return "error.jsp";		
		}
        catch (RollbackException e) {
            request.setAttribute("error", e.getMessage());
            return "error.jsp";
        }
    }
	
}
