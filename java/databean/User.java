package databean;

public class User {
    private String userName;
    private String password;

    public User(String userName, String password) {
        this.setPassword(password);
        this.setUserName(userName);
    }

    public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


}
