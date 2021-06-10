package formbean2;

import org.formbeanfactory.FieldOrder;
import org.formbeanfactory.FileProperty;
import org.formbeanfactory.FormBean;
import org.formbeanfactory.InputType;

@FieldOrder("image,title,text")
public class PostForm extends FormBean {
    private String title;
    private String text;
    private FileProperty image;

    public String getText() {
        return text;
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
