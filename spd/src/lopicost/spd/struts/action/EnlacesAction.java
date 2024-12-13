package lopicost.spd.struts.action;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import lopicost.config.logger.Logger;
import lopicost.spd.model.Enlace;
import lopicost.spd.model.Usuario;
import lopicost.spd.persistence.AvisosDAO;
import lopicost.spd.persistence.EnlaceDAO;
import lopicost.spd.persistence.UsuarioDAO;
import lopicost.spd.struts.form.EnlacesForm;
import lopicost.spd.struts.helper.ControlDataImportHelper;
import lopicost.spd.struts.helper.EnlacesHelper;
import lopicost.spd.struts.helper.ExtReHelper;
import lopicost.spd.utils.DateUtilities;
import lopicost.spd.utils.StringUtil;

public class EnlacesAction extends GenericAction  {

	static String className = "EnlacesAction";
	   
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	
		EnlacesForm enlacesForm =  (EnlacesForm) form; 
		List errors = new ArrayList();
		enlacesForm.setErrors(errors);
		
		
		Usuario user = UsuarioDAO.findByIdUser(enlacesForm.getIdUsuario());
		if(user==null) 
		{
	    	errors.add( "Error sesión usuario, es necesario volver a hacer login");
			return mapping.findForward("errorSession");
		}
		
		int nivel = enlacesForm.getNivel();
		
		
		//if(enlacesForm.getACTIONTODO()!=null && enlacesForm.getACTIONTODO().equalsIgnoreCase("INICIO"))
		//	enlacesForm.setListaEnlaces(EnlaceDAO.findByIdUserAndTypeAndLevel(user, null, 1));
		
		List menus = 	EnlaceDAO.findByIdUserAndTypeAndLevel(user, enlacesForm.getACTIONTODO(), nivel);
		List menuArreglado = 	EnlacesHelper.listadoPorApartados(user, menus);
		enlacesForm.setListaEnlaces(menuArreglado);
		
		//20240923 - Comprobamos si la recogida de datos ha sido correcta. En este caso se mira la parte de los datos de las fechas de recogida que afectan a la RE del iofwin. 
		//TO-DO   -  NO se analiza el contenido de la recogida,  
		List beansResi = ExtReHelper.getDatosProcesoCaptacionConErrores(enlacesForm.getIdUsuario());
		enlacesForm.setListaBeans(beansResi);
		

        List avisos = ExtReHelper.getAvisosDeHoy(enlacesForm.getIdUsuario(), null, false, new Date());
        
		enlacesForm.setAvisos(avisos);

		//return mapping.findForward("listHtml");
		 mapping.findForward("list");
		ActionForward  actionForward =mapping.findForward("list");
		return actionForward;
	}
	
	public ActionForward admin(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List errors = new ArrayList();
		EnlacesForm enlacesForm =  (EnlacesForm) form; 
		enlacesForm.setErrors(errors);
		//String idUsuario = (String) request.getSession().getAttribute("idUsuario");
		String idUsuario = enlacesForm.getIdUsuario(); 
		String idUsuario2 = (String) request.getSession().getAttribute("login"); 
		Usuario user = UsuarioDAO.findByIdUser(idUsuario);
		//Usuario user =  getcUser();
		
		
		enlacesForm.setListaEnlaces(EnlaceDAO.findAll(user,  enlacesForm.getCampoGoogle()));
		//return mapping.findForward("listHtml");
		ActionForward  actionForward =mapping.findForward("admin");
		return actionForward;
	}

    
	public ActionForward nuevo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		//inicializamos para que no haya datos de otros módulos
		
		EnlacesForm f =  (EnlacesForm) form;
		Usuario user = UsuarioDAO.findByIdUser(f.getIdUsuario());
		List errors = new ArrayList();
		if(user==null) 
		{
	    	errors.add( "Error sesión usuario, es necesario volver a hacer login");
			return mapping.findForward("errorSession");
		}
		
	
		f.setErrors(errors);
		System.out.println(className + " .nuevo()  "  +f.getACTIONTODO());
		Enlace enlace = new Enlace();
		if(f.getACTIONTODO()!=null && f.getACTIONTODO().equals("NUEVO"))
		{
			f.setEnlace(enlace);

		}
		if(f.getACTIONTODO()!=null && f.getACTIONTODO().equals("COPIA"))
		{
			Enlace enlaceBase = EnlaceDAO.findById(f.getIdEnlace());
			
			enlace.setIdEnlace(enlaceBase.getIdEnlace()+" (modificar o sumar 1)");
		//	enlace.setAliasEnlace(enlaceBase.getAliasEnlace());
			enlace.setIdApartado(enlaceBase.getIdApartado());
		    enlace.setNombreEnlace(enlaceBase.getNombreEnlace());
		    enlace.setPreEnlace(enlaceBase.getPreEnlace());
		    enlace.setLinkEnlace(enlaceBase.getLinkEnlace());
		    enlace.setParamsEnlace(enlaceBase.getParamsEnlace());
		    enlace.setDescripcion(enlaceBase.getDescripcion());
		    enlace.setActivo(enlaceBase.isActivo());
		    enlace.setNuevaVentana(enlaceBase.isNuevaVentana());
		    f.setEnlace(enlace);
		
		}
		else if(f.getACTIONTODO()!=null && f.getACTIONTODO().equals("NUEVO_OK"))
		{
			boolean result =EnlaceDAO.nuevo(f);
			if(result)
			{
				errors.add( "Registro creado correctamente ");
				admin( mapping,  form,  request,  response);
				return mapping.findForward("admin");
			}
			else errors.add( "No se ha podido crear el registro, revisa que el IDENLACE sea único");
	
			
			f.setErrors(errors);
			
				
		}
		return mapping.findForward("nuevo");
	}
	
	
	
	public ActionForward editar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception {
		EnlacesForm f =  (EnlacesForm) form;
		List errors = new ArrayList();
		f.setErrors(errors);
		Usuario user = UsuarioDAO.findByIdUser(f.getIdUsuario());
		if(user==null) 
		{
	    	errors.add( "Error sesión usuario, es necesario volver a hacer login");
			return mapping.findForward("errorSession");
		}

		
		Enlace enlace = EnlaceDAO.findById(f.getIdEnlace());
		boolean result=false;
		if(f.getACTIONTODO()!=null && f.getACTIONTODO().equals("EDITA_OK"))
		{
			//enlace.setAliasEnlace(f.getAliasEnlace());
			
			enlace.setIdApartado(f.getIdApartado());
		    enlace.setNombreEnlace(f.getNombreEnlace());
		    enlace.setPreEnlace(f.getPreEnlace());
		    enlace.setLinkEnlace(f.getLinkEnlace());
		    enlace.setParamsEnlace(f.getParamsEnlace());
		    enlace.setDescripcion(f.getDescripcion());
		    enlace.setActivo(f.isActivo());
		    enlace.setNuevaVentana(f.isNuevaVentana());
			result=EnlaceDAO.edita(enlace);
			

			if(result)
			{
				errors.add( "Registro editado correctamente ");

			}
			else errors.add( "No se ha podido editar el registro");

			admin( mapping,  form,  request,  response);
			f.setErrors(errors);
			return mapping.findForward("admin");
			
		}
		f.setEnlace(enlace);

		return mapping.findForward("editar");
	}

	
	public ActionForward borrar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		ActionForward action = mapping.findForward("borrar");
		EnlacesForm formulari =  (EnlacesForm) form;
		List errors = new ArrayList();
		formulari.setErrors(errors);
		Usuario user = UsuarioDAO.findByIdUser(formulari.getIdUsuario());
		if(user==null) 
		{
	    	errors.add( "Error sesión usuario, es necesario volver a hacer login");
			return mapping.findForward("errorSession");
		}

		Enlace enlace = EnlaceDAO.findById(formulari.getIdEnlace());
		formulari.setEnlace(enlace);;
		
		boolean borrable = EnlacesHelper.checkBorrable(enlace);
		if(!borrable) 
		{
	    	errors.add( "El enlace no es borrable, actualmente está asignado a algún perfil");
			formulari.setIdEnlace("");
			formulari.setACTIONTODO("");
			action=  mapping.findForward("admin");
		}

		
		
		boolean result=false;
		if(formulari.getACTIONTODO()!=null && formulari.getACTIONTODO().equals("CONFIRMADO_OK"))
		{
			result=EnlaceDAO.borrar(enlace.getIdEnlace());
			if(result)
			{
			//	errors.add(SPDConstants.MSG_LEVEL_INFO, new ActionMessage("Registro borrado correctamente Info"));
				errors.add( "Registro borrado correctamente ");
				formulari.setIdEnlace("");
				formulari.setACTIONTODO("");
				action=  mapping.findForward("admin");
			}
			else errors.add( "Error en el borrado del registro");
			admin( mapping,  form,  request,  response);
			formulari.setErrors(errors);
			
		}
			return action;
	}


	
	public void log (String message, int level)
	{
		Logger.log("SgaLogger",message,level);	
	}


	
	
}
	