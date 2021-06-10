package databean2;

import org.genericdao.MaxSize;
import org.genericdao.PrimaryKey;

@PrimaryKey("userName")
public class User {
    private String userName;
    private String password;
    private String role;

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUserName() {
        return userName;
    }

    public void setPassword(String s)  { password = s;    }
    @MaxSize(255)
    public void setUserName(String s)  { userName = s;    }
}
