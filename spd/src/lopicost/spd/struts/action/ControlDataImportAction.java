package lopicost.spd.struts.action;



import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


import lopicost.config.logger.Logger;

import lopicost.spd.struts.form.GenericForm;
import lopicost.spd.struts.helper.ControlDataImportHelper;



public class ControlDataImportAction extends GenericAction  {
	static String className = "ControlDataImportAction";

	   
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List errors = new ArrayList();
		GenericForm formulario =  (GenericForm) form; 
		formulario.setErrors(errors);
		
		List beansResi = ControlDataImportHelper.getDatosTablas(getIdUsuario());
		formulario.setListaBeans(beansResi);
		ActionForward  actionForward =mapping.findForward("list");
		return actionForward;
	}

	
	
	public void log (String message, int level)
	{
		Logger.log("SpdLogger",message,level);	
	}

	
	
}
	