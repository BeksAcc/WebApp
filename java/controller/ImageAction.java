package controller;

import javax.servlet.http.HttpServletRequest;

import org.formbeanfactory.FormBeanFactory;
import org.formbeanfactory.FormBeanFactoryException;
import org.genericdao.RollbackException;

import databean2.PostBean;
import formbean2.IdForm;
import formbean2.PostForm;
import model2.PostDAO;
import model2.Model;

public class ImageAction extends Action {
    private FormBeanFactory<IdForm> formBeanFactory = new FormBeanFactory<>(IdForm.class);

    private PostDAO itemDAO;

	public ImageAction(Model model) {
		itemDAO = model.getPostDAO();
	}

	public String getName() {
		return "image.do";
	}

	public String performGet(HttpServletRequest request) {
		try {
	        IdForm form = formBeanFactory.create(request);
	        if (form.hasValidationErrors()) {
	            request.setAttribute("form", form);
	            return "error.jsp";
	        }
	        
	        PostBean b = itemDAO.read(form.getIdAsInt());
	        if (b == null) {
	            form.addFormError("There is no image with id="+form.getIdAsInt());
                request.setAttribute("form", form);
                return "error.jsp";
	        }

	        request.setAttribute("image", b);
			return "ImageView";

        } catch (FormBeanFactoryException e) {
            request.setAttribute("error",e.getMessage());
            return "error.jsp";
        } catch (RollbackException e) {
            request.setAttribute("error",e.getMessage());
            return "error.jsp";
        }
	}
}
