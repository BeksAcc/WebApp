package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import formbean2.LoginForm;
import model2.Model;

public class LogoutAction extends Action {
	public LogoutAction(Model model) {
    }

    public String getName() {
        return "logout.do";
    }


    public String performPost(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session.getAttribute("admin")!=null) {
        	session.setAttribute("admin", null);
        }
        if (session.getAttribute("user")!=null) {
        	session.setAttribute("user", null);
        }
        request.setAttribute("form", new LoginForm());
        return "login.do";
    }
}
