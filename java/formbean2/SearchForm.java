package formbean2;

import org.formbeanfactory.FormBean;

public class SearchForm extends FormBean {
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void validate() {
	    super.validate();

	    if (hasValidationErrors()) {
	        return;
	    }
	}
}
