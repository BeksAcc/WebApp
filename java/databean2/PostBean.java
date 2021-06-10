
package databean2;

import org.genericdao.PrimaryKey;

@PrimaryKey("id")
public class PostBean {
    private int id;
    private String title;
    private String text;
    private byte[] image;
    private String contentType;
    private String user;

    public byte[] getImage() {
        return image;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getUser_id() {
        return user;
    }

    public void setUser_id(String user_id) {
        this.user = user_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
