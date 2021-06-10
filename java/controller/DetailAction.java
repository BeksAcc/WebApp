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
import org.genericdao.RollbackException;

import databean2.PostBean;
import databean2.User;
import formbean2.DetailForm;
import formbean2.PostForm;
import formbean2.SearchForm;
import model2.Model;
import model2.PostDAO;
public class DetailAction extends Action {

    private PostDAO postDAO;

    public DetailAction(Model model) {
        postDAO = model.getPostDAO();
    }

    public String getName() {
        return "detail.do";
    }

    @Override
    public String performGet(HttpServletRequest request) {
        return performPost(request);
    }

    @Override
    public String performPost(HttpServletRequest request) {
        User u = (User) request.getSession().getAttribute("user");
        HttpSession session = request.getSession();
        try {
            
            request.setAttribute("form", postDAO.read(Integer.parseInt(request.getParameter("id"))));
            return "/pages/detail.jsp";
        } catch (RollbackException e) {
            request.setAttribute("error",e.getMessage());
            return "error.jsp";
        }
                
    }
}
