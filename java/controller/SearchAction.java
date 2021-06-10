package controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
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
import databean2.User;
import formbean2.SearchForm;
import model2.FriendsDAO;
import model2.Model;
import model2.PostDAO;
public class SearchAction extends Action {
    private FormBeanFactory<SearchForm> formBean = new FormBeanFactory<>(SearchForm.class);

    private PostDAO postDAO;
    private FriendsDAO friendsDAO;

    public SearchAction(Model model) {
        postDAO = model.getPostDAO();
        friendsDAO = model.getFriendsDAO();
    }

    public String getName() {
        return "search.do";
    }

    @Override
    public String performPost(HttpServletRequest request) {
        User u = (User) request.getSession().getAttribute("user");
        HttpSession session = request.getSession();
        try {
            SearchForm form = formBean.create(request);   
            
            String sortBy;
            if((String) session.getAttribute("searchBy") == null) sortBy = "title";
            else sortBy = (String) session.getAttribute("searchBy");
            // request.setAttribute("form", new PostForm());
            Friends[] requestFrom = friendsDAO.match(MatchArg.equals("requestAccepted", true));
            Friends[] friendsOfUser = new Friends[requestFrom.length];
            int counter = 0;
            for (int i = 0; i<requestFrom.length; i++ ) {
                if (requestFrom[i].getFriendsUserName().equals(u.getUserName()) || requestFrom[i].getUserName().equals(u.getUserName())) {
                    friendsOfUser[counter]=requestFrom[i];
                    counter++;
                }
            }
            session.setAttribute("items", postDAO.getItemsBy(form.getKey(), sortBy , friendsOfUser, u.getUserName()));
            session.setAttribute("searchKey", form.getKey());
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
