package formbean;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

public class LoginForm {
    String username;
    String password;
    String role;

    public LoginForm(HttpServletRequest request) {
        this.username = request.getParameter("username");
        this.password = request.getParameter("password");
        this.role     = request.getParameter("role");
    }

    public String getPassword() {
        return this.password;
    }

    public String getUsername() {
        return this.username;
    }
    public ArrayList<String> validateErrors(){
        ArrayList<String> errors = new ArrayList<String>();

        if (this.username == null || this.username.length() == 0) {
            errors.add("User Name is required");
        }
        
        if (this.password == null || this.password.length() == 0) {
            errors.add("Password is required");
        }

        if (errors.size() > 0) {
            return errors;
        }

        if(!this.username.contains("@") && this.role.equals("customer")){
            errors.add("User Name is invalid");
        }

        if (errors.size() > 0) {
            return errors;
        }
        
        if (this.username.matches(".*[<>\"].*") && this.role.equals("customer")) {
            errors.add("User Name may not contain angle brackets or quotes");
        }

        return errors;
    }

}