package formbean2;

import org.formbeanfactory.FieldOrder;
import org.formbeanfactory.FormBean;
import org.formbeanfactory.InputType;

@FieldOrder("userName,password")
public class LoginForm extends FormBean {
    private String userName;
    private String password;
    private String action;
    
    public String getUserName()  { return userName; }
    public String getPassword()  { return password; }
    public String getAction()    { return action; }
	
    public void setUserName(String s)  { userName = s.trim(); }
    @InputType("password")
    public void setPassword(String s)  { password = s.trim(); }
    @InputType("button")
    public void setAction(String s)    { action   = s;        }

    public void validate() {
        super.validate();
        if (hasValidationErrors()) {
            return;
        }
        
        if (!action.equals("Login") && !action.equals("Register")) {
            this.addFormError("Invalid button");
        }
        
        if (userName.matches(".*[<>\"].*")) {
            this.addFieldError("userName", "May not contain angle brackets or quotes");
        }
    }
}
