package formbean2;

import org.formbeanfactory.FieldOrder;
import org.formbeanfactory.FormBean;
import org.formbeanfactory.InputType;

@FieldOrder("userName,action")
public class SearchForm2 extends FormBean {
	private String userName;
    private String action;
    
    public String getUserName()  { return userName; }
    public String getAction()    { return action; }
	
    public void setUserName(String s)  { userName = s.trim(); }
    @InputType("button")
    public void setAction(String s)    { action   = s;        }
    
    public void validate() {
        super.validate();

        if (hasValidationErrors()) {
            return;
        }
    }
}
