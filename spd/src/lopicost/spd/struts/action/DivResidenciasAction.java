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
import lopicost.spd.struts.form.PacientesForm;


public class DivResidenciasAction extends GenericAction  {

	static String className = "DivisionResidenciasAction";
	   
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
	

    
	public ActionForward nuevo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		DivResidenciasForm f =  (DivResidenciasForm) form; 
		List errors = new ArrayList();
		f.setErrors(errors);
		Usuario user = UsuarioDAO.findByIdUser(f.getIdUsuario());
		if(user==null) 
		{
	    	errors.add( "Error sesión usuario, es necesario volver a hacer login");
			return mapping.findForward("errorSession");
		}	
		return mapping.findForward("nuevo");
	}
	
	
	public ActionForward detalle(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		DivResidenciasForm f =  (DivResidenciasForm) form; 
		f.setDivisionResidencia(DivisionResidenciaDAO.getDivisionResidenciaByOid(getIdUsuario(), f.getOidDivisionResidencia()));
		
		return mapping.findForward("detalle");
	}
	
	public ActionForward editar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception {
		DivResidenciasForm f =  (DivResidenciasForm) form; 
		List errors = new ArrayList();
		f.setErrors(errors);
		Usuario user = UsuarioDAO.findByIdUser(getIdUsuario());
		if(user==null) 
		{
	    	errors.add( "Error sesión usuario, es necesario volver a hacer login");
			return mapping.findForward("errorSession");
		}

		
		DivisionResidencia div = null;
		div=DivisionResidenciaDAO.getDivisionResidenciaByOid(getIdUsuario(), f.getOidDivisionResidencia());
		f.setDivisionResidencia(div);

		f.setListaDivisionResidencias(DivisionResidenciaDAO.getListaDivisionResidencias(getIdUsuario()));
		if(f.getACTIONTODO()!=null && f.getACTIONTODO().equals("EDITAR"))
		{
			f.setIdDivisionResidencia(div.getIdDivisionResidencia()); //para que muestre la resi del paciente en el formulario
		}
		if(f.getACTIONTODO()!=null && f.getACTIONTODO().equals("EDITAR_OK"))
		{
			
			boolean result=false;//DivisionResidenciaHelper.actualizaDatos(getIdUsuario(), div, f);
			if(result)
			{
				errors.add( "Editado correctamente ");
			}
			else errors.add( "No se ha podido editar el registro");

			list( mapping,  form,  request,  response);
			f.setErrors(errors);
			return mapping.findForward("list");
		}
		 
		 
		return mapping.findForward("editar");
	}

	

	
	public void log (String message, int level)
	{
		Logger.log("SgaLogger",message,level);	
	}


	
	
}
	