package databean2;

import org.genericdao.PrimaryKey;

@PrimaryKey("id")
public class Friends {
	private int 	id;
	private String 	userName;
	private String 	friendsUserName;
	private boolean requestAccepted;
	private boolean requestDeclined;
	private boolean requestSend;

	public int getId()					{ return id; 				}
	public String getUserName()			{ return userName; 			}
	public String getFriendsUserName()	{ return friendsUserName;	}
	public boolean isRequestAccepted() 	{ return requestAccepted;	}
	public boolean isRequestDeclined() 	{ return requestDeclined;	}
	public boolean isRequestSend() 		{ return requestSend;	}

	public void setId(int s)								{ id = s;    								}
	public void setUserName(String s)						{ userName = s;    							}
	public void setFriendsUserName(String s)				{ friendsUserName = s;  					}
	public void setRequestAccepted(boolean requestAccepted) { this.requestAccepted = requestAccepted;	}
	public void setRequestDeclined(boolean requestDeclined) { this.requestDeclined = requestDeclined;	}
	public void setRequestSend(boolean requestSend) 		{ this.requestSend = requestSend;	}
}
