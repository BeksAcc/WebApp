package controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.formbeanfactory.FormBeanFactory;
import org.formbeanfactory.FormBeanFactoryException;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;

import databean2.Friends;
import databean2.PostBean;
import databean2.User;
import formbean2.PostForm;
import model2.FriendsDAO;
import model2.Model;
import model2.PostDAO;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

// import org.apache.commons.fileupload.disk.DiskFileItemFactory; 
// import org.apache.commons.fileupload.servlet.ServletFileUpload; 
@MultipartConfig(
  fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
  maxFileSize = 1024 * 1024 * 10,      // 10 MB
  maxRequestSize = 1024 * 1024 * 100   // 100 MB
)
public class PostAddAction extends Action {
    private FormBeanFactory<PostForm> formBean = new FormBeanFactory<>(PostForm.class);

    private PostDAO postDAO;
    private FriendsDAO friendsDAO;

    public PostAddAction(Model model) {
        postDAO = model.getPostDAO();
        friendsDAO = model.getFriendsDAO();
    }

    public String getName() {
        return "posts.do";
    }

    @Override
    public String performGet(HttpServletRequest request) {
        ArrayList<String> categories = new ArrayList<String>();
        categories.add("user_id");
        categories.add("title");
        categories.add("text");

        HttpSession httpSession = request.getSession();
        if(httpSession.getAttribute("searchBy") == null){
            httpSession.setAttribute("searchBy", "title");
        }
        if(httpSession.getAttribute("searchKey") == null){
            httpSession.setAttribute("searchKey" , "");
        }
        request.setAttribute("form", new PostForm());
        request.setAttribute("categories", categories);
        
        try {
            User us = (User) request.getSession().getAttribute("user");
            if(httpSession.getAttribute("items") != null){
                request.setAttribute("items", httpSession.getAttribute("items"));
            }
            else{
                Friends[] requestFrom = friendsDAO.match(MatchArg.equals("requestAccepted", true));
                Friends[] friendsOfUser = new Friends[requestFrom.length];
                int counter = 0;
                for (int i = 0; i<requestFrom.length; i++ ) {
                    if (requestFrom[i].getFriendsUserName().equals(us.getUserName()) || requestFrom[i].getUserName().equals(us.getUserName())) {
                        friendsOfUser[counter]=requestFrom[i];
                        counter++;
                    }
                }
                request.setAttribute("items", postDAO.getItems(friendsOfUser,us.getUserName()));
            }
            
            return ("/pages/index.jsp");
        } catch (RollbackException e) {
            request.setAttribute("error",e.getMessage());
            return "error.jsp";
        }        
    }

    public String performPost(HttpServletRequest request) {
        User u = (User) request.getSession().getAttribute("user");
        try {
            PostForm form = formBean.create(request);            
            if (form.hasValidationErrors()) {
                request.setAttribute("isError", true);
                request.setAttribute("form", form);
                return "/pages/index.jsp";
            }
            
            PostBean bean = new PostBean();
            bean.setImage(form.getImage().getBytes());
            bean.setContentType(form.getImage().getContentType());

            bean.setText(form.getText());
            bean.setTitle(form.getTitle());
            bean.setUser_id(u.getUserName());

            postDAO.create(bean);

            request.setAttribute("form", new PostForm());
            request.setAttribute("posts", postDAO.getItems());

            return "posts.do";
        } catch (FormBeanFactoryException e) {
            request.setAttribute("error",e.getMessage());
            return "error.jsp";
        } catch (RollbackException e) {
            request.setAttribute("error",e.getMessage());
            return "error.jsp";
        }
                
    }
}
