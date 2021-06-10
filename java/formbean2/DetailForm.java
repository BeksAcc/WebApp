package formbean2;

import org.formbeanfactory.FieldOrder;
import org.formbeanfactory.FileProperty;
import org.formbeanfactory.FormBean;
import org.formbeanfactory.InputType;

@FieldOrder("image,title,text,user_id")
public class DetailForm extends FormBean {
    private String title;
    private String text;
    private FileProperty image;
    private String user_id;

    public String getText() {
        return text;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public FileProperty getImage() {
        return image;
    }

    public void setImage(FileProperty image) {
        this.image = image;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }
    @InputType("text")
    public void setTitle(String title) {
        this.title = title;
    }

    public void validate() {
        super.validate();
        if (hasValidationErrors()) {
            return;
        }
        
    }
}
