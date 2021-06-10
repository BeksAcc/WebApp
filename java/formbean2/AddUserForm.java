package formbean2;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.formbeanfactory.FieldOrder;
import org.formbeanfactory.FormBean;
import org.formbeanfactory.InputType;

@FieldOrder("email,firstName,lastName,gender,birthDate,password,avatar,role")
public class AddUserForm extends FormBean {
	private String email;
	private String firstName;
	private String lastName;
	private String gender;
    private String birthDate;
    private String password;
    private String avatar;
    private String role; 
    private String action;
    
    public String getEmail()  			{ return email; 	}
    public String getFirstName()  		{ return firstName; }
    public String getLastName()  		{ return lastName; 	}
    public String getGender()  			{ return gender; 	}
    public String getBirthDate()  		{ return birthDate; }
    public String getPassword()  		{ return password; 	}
    public String getAvatar()  			{ return avatar; 	}
    public String getRole()  			{ return role; 		}
    public String getAction()  			{ return action; 	}
	
    public void setEmail(String s)  		{ email = s.trim(); 	}
    public void setFirstName(String s)  	{ firstName = s.trim(); }
    public void setLastName(String s)  		{ lastName = s.trim(); 	}
    @InputType("radio")
    public void setGender(String s)  		{ gender = s.trim(); 	}
    @InputType("date")
    public void setBirthDate(String s)  	{ birthDate = s.trim(); }
    @InputType("password")
    public void setPassword(String s)  		{ password = s.trim(); 	}
    public void setRole(String s)			{ role = s.trim(); 		}
    //
    public void setAvatar(String s)  		{ avatar = s.trim(); 	}
    @InputType("button")
    public void setAction(String s)    		{ action   = s;        	}
    
    public void validate() {
        super.validate();
        
        if (hasValidationErrors()) {
            return;
        }
        if (!validateJavaDate(birthDate)) {
        	this.addFieldError("birthDate", "Incorrect date format! Should be - 25.04.2021");
        }
    }
    public static boolean validateJavaDate(String strDate){
	 	if (strDate.trim().equals("")){
	 	    return true;
	 	}
	 	else{
	 	    SimpleDateFormat sdfrmt = new SimpleDateFormat("dd.mm.yyyy");
	 	    sdfrmt.setLenient(false);
	 	    try{
	 	        Date javaDate = sdfrmt.parse(strDate); 
	 	    }
	 	    catch (Exception e){
	 	        return false;
	 	    }
	 	    return true;
	 	}
    }
}
