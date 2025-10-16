package lopicost.spd.struts.action;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import lopicost.config.logger.Logger;
import lopicost.spd.model.Aviso;
import lopicost.spd.model.Usuario;
import lopicost.spd.persistence.AvisosDAO;
import lopicost.spd.persistence.FarmaciaDAO;
import lopicost.spd.persistence.UsuarioDAO;
import lopicost.spd.struts.form.AvisosForm;
import lopicost.spd.struts.helper.AvisoHelper;


public class AvisosAction extends GenericAction  {

	static String className = "AvisosAction";
	   
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	
		AvisosForm formulario =  (AvisosForm) form; 
		List errors = new ArrayList();
		formulario.setErrors(errors);
		
		
		Usuario user = UsuarioDAO.findByIdUser(getIdUsuario());
		if(user==null) 
		{
	    	errors.add( "Error sesión usuario, es necesario volver a hacer login");
			return mapping.findForward("errorSession");
		}
		
		List avisos = 	AvisosDAO.findByFilters(user.getIdUsuario(), -1, null, false, null);
		formulario.setAvisos(avisos);
		

		return mapping.findForward("list");
	}
	
	public ActionForward editar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		AvisosForm f =  (AvisosForm) form;

		Aviso aviso = null;
		aviso=AvisosDAO.findByOid(getIdUsuario(), f.getOidAviso());
		f.setAviso(aviso);

		List errors = new ArrayList();
		//f.setListaEstatusResidente(PacienteDAO.getEstatusResidente(getIdUsuario()));
		f.setListaFarmacias(FarmaciaDAO.getFarmaciasPorUser(getIdUsuario()));
		if(f.getACTIONTODO()!=null && f.getACTIONTODO().equals("EDITAR"))
		{
			//
		}
		if(f.getACTIONTODO()!=null && f.getACTIONTODO().equals("EDITA_OK"))
		{
			
			boolean result=AvisoHelper.actualizaDatos(getIdUsuario(), aviso, f);
			if(result)
			{
				errors.add( "Editado correctamente ");
			}
			else errors.add( "No se ha editado el registro");

			list( mapping,  form,  request,  response);
			f.setErrors(errors);
			return mapping.findForward("list");
		}
		 
		 
		return mapping.findForward("editar");
	}
	
	
	public ActionForward borrar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		ActionForward action = mapping.findForward("borrar");
		AvisosForm formulari =  (AvisosForm) form;
		List errors = new ArrayList();
		formulari.setErrors(errors);
		Usuario user = UsuarioDAO.findByIdUser(getIdUsuario());
		if(user==null) 
		{
	    	errors.add( "Error sesión usuario, es necesario volver a hacer login");
			return mapping.findForward("errorSession");
		}
		Aviso aviso=AvisosDAO.findByOid(getIdUsuario(), formulari.getOidAviso());
		formulari.setAviso(aviso);;
		
		boolean result=false;

			result=AvisoHelper.borrar(user.getIdUsuario(), aviso);
			if(result)
			{
			//	errors.add(SPDConstants.MSG_LEVEL_INFO, new ActionMessage("Registro borrado correctamente Info"));
				errors.add( "Registro borrado correctamente ");
				formulari.setOidAviso(-1);
				formulari.setACTIONTODO("");
				action=  mapping.findForward("list");
			}
			else errors.add( "Error en el borrado del registro");
			list( mapping,  form,  request,  response);
			formulari.setErrors(errors);
			

			return action;
	}
		


	public ActionForward nuevo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		AvisosForm f =  (AvisosForm) form; 
		List errors = new ArrayList();
		f.setErrors(errors);
		Usuario user = UsuarioDAO.findByIdUser(getIdUsuario());
		if(user==null) 
		{
	    	errors.add( "Error sesión usuario, es necesario volver a hacer login");
			return mapping.findForward("errorSession");
		}	
		
		
		f.setListaFarmacias(FarmaciaDAO.getFarmaciasPorUser(getIdUsuario()));
		f.setErrors(errors);
		System.out.println(className + " .nuevo()  "  +f.getACTIONTODO());
/*		Aviso aviso = new Aviso();
		if(f.getACTIONTODO()!=null && f.getACTIONTODO().equals("NUEVO"))
		{
			f.setAviso(aviso);

		}*/
		if(f.getACTIONTODO()!=null && f.getACTIONTODO().equals("NUEVO_OK"))
		{
			boolean result =AvisoHelper.nuevo(getIdUsuario(), f);
			if(result)
			{
				errors.add( "Registro creado correctamente ");
				list( mapping,  form,  request,  response);
				return mapping.findForward("list");
			}
			else errors.add( "No se ha podido crear el registro, revisa que el IDENLACE sea único");
	
			
			f.setErrors(errors);
			
				
		}
		return mapping.findForward("nuevo");
	}


	
	public void log (String message, int level)
	{
		Logger.log("SgaLogger",message,level);	
	}


	
	
}
	