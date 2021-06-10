package formbean;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

public class FundForm {
    String name;
    String price;

    public FundForm(HttpServletRequest request) {
        this.name = request.getParameter("name");
        this.price = request.getParameter("price");
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the price
     */
    public String getPrice() {
        return price;
    }
    
    public ArrayList<String> validateErrors(){
        ArrayList<String> errors = new ArrayList<String>();

        if (this.name == null || this.name.length() == 0) {
            errors.add("Name is required");
        }
        
        if (this.price == null || this.price.length() == 0) {
            errors.add("Price is required");
        }


        if (errors.size() > 0) {
            return errors;
        }

        return errors;
    }
}