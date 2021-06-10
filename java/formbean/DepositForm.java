package formbean;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

public class DepositForm {
    String amount;
    String email;

    public DepositForm(HttpServletRequest request) {
        this.amount = request.getParameter("amount");
        this.email = request.getParameter("email");
    }

    /**
     * @return the amount
     */
    public String getAmount() {
        return amount;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    public ArrayList<String> validateErrors(){
        ArrayList<String> errors = new ArrayList<String>();

        for(int x = 0;x < this.amount.length();x++){
            if(Character.isLetter(this.amount.charAt(x))){
                errors.add("Amount is invalid");
                return errors;
            }
        }
 
        if (this.amount == null || this.amount.length() == 0) {
            errors.add("Amount is required");
        }
        
        if (this.email == null || this.email.length() == 0) {
            errors.add("Email is required");
        }


        if(!this.email.contains("@")){
            errors.add("Email is invalid");
        }

        if (errors.size() > 0) {
            return errors;
        }

        return errors;
    }
}