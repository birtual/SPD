
package lopicost.spd.security.action;

import java.io.IOException;


import lopicost.spd.security.form.LoginForm;
import lopicost.spd.security.helper.LoginHelper;
import lopicost.spd.struts.action.GenericAction;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class LoginAction extends GenericAction {

	// --------------------------------------------------------- Instance Variables

	// --------------------------------------------------------- Methods

	/** 
	 * Method execute
	 * @param ActionMapping mapping
	 * @param ActionForm form
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward list(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
	
		LoginForm loginForm = (LoginForm) form;
		ActionErrors errors = new ActionErrors();
		
		ActionForward actionForward = mapping.findForward("inicio");
		
		// Verifica que el usuario y password sean correctos
		try {
		// Obligamos a que los logins sean en minúsculas.
		loginForm.setIdUsuario(loginForm.getIdUsuario().toLowerCase());
	
		LoginHelper lh = new LoginHelper();
		if (!lh.chkUser(loginForm.getIdUsuario(), loginForm.getPassword())){
			errors.add("userNoOk", new ActionError("errors.userNoOk"));
			actionForward = mapping.findForward("error");
		} else {
			
			// Si el usuario y password son correctos
		    request.getSession().setAttribute("login", loginForm.getIdUsuario());
		}
		if(loginForm.getIdUsuario()!=null && loginForm.getIdUsuario().equals("carlos"))
			return mapping.findForward("success");

		} catch (Exception e) {
			request.setAttribute("error", e.getMessage());
			actionForward = mapping.findForward("global_error");
		}

		// Si hay errores, los pasa al formulario
		if (errors.size()>0){
			saveErrors(request, errors);
		}
		
		return actionForward;
	}

}
