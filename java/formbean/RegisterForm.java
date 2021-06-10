package formbean;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

public class RegisterForm {
    String username;
    String password;
    private String name;
    private String address;

    public RegisterForm(HttpServletRequest request) {
        this.username = request.getParameter("username");
        this.password = request.getParameter("password");
        this.setName(request.getParameter("name"));
        this.setAddress(request.getParameter("address"));
    }

    /**
     * @return the balance
     */
    public String getPassword() {
        return password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return this.username;
    }
    public ArrayList<String> validateErrors(){
        ArrayList<String> errors = new ArrayList<String>();

        if (this.username == null || this.username.length() == 0) {
            errors.add("User Name is required");
            errors.add(this.username);
        }
        
        if (this.password == null || this.password.length() == 0){
            errors.add("Password is required");
        }

        if (this.name == null || this.name.length() == 0) {
            errors.add("name is required");
        }

        if (this.address == null || this.address.length() == 0) {
            errors.add("address Name is required");
        }

        if (errors.size() > 0) {
            return errors;
        }

        if(!this.username.contains("@")){
            errors.add("Email is invalid");
        }

        if (errors.size() > 0) {
            return errors;
        }

        if (this.username.matches(".*[<>\"].*")) {
            errors.add("User Name may not contain angle brackets or quotes");
        }

        return errors;
    }

}