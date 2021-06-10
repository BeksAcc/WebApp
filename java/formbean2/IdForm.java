package formbean2;

import org.formbeanfactory.FormBean;
import org.formbeanfactory.InputType;

public class IdForm extends FormBean {
	private String id;

	public String getId() { return id;    }
	
	public int getIdAsInt() {
		// The call validate() to ensures that errors will be detected before
		//  NullPointerException or NumberFormatException are thrown!
		return Integer.parseInt(id);
	}
	
	@InputType("hidden")
	public void setId(String id) { this.id = id; }

	public void validate() {
	    super.validate();

	    if (hasValidationErrors()) {
	        return;
	    }

		try {
			Integer.parseInt(id);
		} catch (NumberFormatException e) {
			this.addFormError("Id is not an integer");
		}
	}
}
