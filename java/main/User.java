package main;

import java.util.ArrayList;

public class User {
    String name;
    String address;
    String data;
    String comment;
    ArrayList<User> comments;
    public User(String name,String data,String address,String comment,ArrayList<User> comments){
        this.address = address;
        this.name = name;
        this.data = data;
        this.comment = comment;
        this.comments = comments;
    }    
    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }
    /**
     * @return the comments
     */
    public ArrayList<User> getComments() {
        return comments;
    }
    /**
     * @return the comment
     */
    public String getComment() {
        return comment;
    }
    /**
     * @return the data
     */
    public String getData() {
        return data;
    }
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }
    /**
     * @param comments the comments to set
     */
    public void setComments(ArrayList<User> comments) {
        this.comments = comments;
    }
    /**
     * @param comment the comment to set
     */
    public void setComment(String comment) {
        this.comment = comment;
    }
    /**
     * @param data the data to set
     */
    public void setData(String data) {
        this.data = data;
    }
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
}