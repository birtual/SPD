package lopicost.spd.struts.action;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import lopicost.config.logger.Logger;
import lopicost.spd.model.DivisionResidencia;
import lopicost.spd.model.Usuario;
import lopicost.spd.persistence.DivisionResidenciaDAO;
import lopicost.spd.persistence.PacienteDAO;
import lopicost.spd.persistence.UsuarioDAO;
import lopicost.spd.struts.form.DivResidenciasForm;
import lopicost.spd.struts.form.FarmaciasForm;
import lopicost.spd.struts.form.PacientesForm;
import lopicost.spd.struts.form.ProcesosForm;
import lopicost.spd.struts.helper.FarmaciasHelper;
import lopicost.spd.struts.helper.ProcesoHelper;


public class FarmaciasAction extends GenericAction  {

	static String className = "FarmaciasAction";
	FarmaciasHelper helper = new FarmaciasHelper();
	   
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	
		DivResidenciasForm formulario =  (DivResidenciasForm) form; 
		List errors = new ArrayList();
		formulario.setErrors(errors);
		
		
		Usuario user = UsuarioDAO.findByIdUser(getIdUsuario());
		if(user==null) 
		{
	    	errors.add( "Error sesión usuario, es necesario volver a hacer login");
			return mapping.findForward("errorSession");
		}
		
		List divResidencias = 	DivisionResidenciaDAO.getListaDivisionResidencias(user.getIdUsuario());
		formulario.setListaDivisionResidencia(divResidencias);
		

		return mapping.findForward("list");
	}
	

	
	public ActionForward lookUp(ActionMapping mapping,ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		FarmaciasForm f =  (FarmaciasForm) form;
		f.setListaFarmacias(helper.getFarmaciasPorUser(getIdUsuario()));
		f.setFieldName1(request.getParameter("fieldName1"));
		return mapping.findForward("lookUp");
	}

	
	
	public void log (String message, int level)
	{
		Logger.log("SpdLogger",message,level);	
	}


	
	
}
	