package view;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import databean2.PostBean;

/**
 * This servlet is the "view" for images. It looks at the "image" request
 * attribute and sends it's image bytes to the client browser.
 * 
 * We need to use a servlet instead of a JSP for the "view" of the image because
 * we need to send back the image bits and not HTML.
 */
@WebServlet("/ViewImageServlet")
public class ImageServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        PostBean b = (PostBean) request.getAttribute("image");

        if (b == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        response.setContentType(b.getContentType());

        ServletOutputStream out = response.getOutputStream();
        out.write(b.getImage());
    }
}
