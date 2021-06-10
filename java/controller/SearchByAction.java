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
import formbean2.PostForm;
import formbean2.SearchForm;
import model2.Model;
import model2.PostDAO;
public class SearchByAction extends Action {
    private FormBeanFactory<SearchForm> formBean = new FormBeanFactory<>(SearchForm.class);

    private PostDAO postDAO;

    public SearchByAction(Model model) {
        postDAO = model.getPostDAO();
    }

    public String getName() {
        return "searchBy.do";
    }

    @Override
    public String performGet(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("searchBy", request.getParameter("searchBy"));
        return "posts.do"; 
    }
}
