package formbean2;

import org.formbeanfactory.FormBean;

public class AddFriendForm extends FormBean {
	private String userName;
	private String friendsUserName;
	private String action;
	
	public String getUserName() {
		return userName;
	}
	public String getFriendsUserName() {
		return friendsUserName;
	}
	public String getAction() {
		return action;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setFriendsUserName(String friendsUserName) {
		this.friendsUserName = friendsUserName;
	}
	public void setAction(String action) {
		this.action = action;
	}
	}
