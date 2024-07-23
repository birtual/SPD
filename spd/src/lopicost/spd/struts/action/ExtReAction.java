package lopicost.spd.struts.action;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

import lopicost.config.logger.Logger;
import lopicost.spd.model.DivisionResidencia;
import lopicost.spd.model.Usuario;
import lopicost.spd.persistence.DivisionResidenciaDAO;
import lopicost.spd.persistence.UsuarioDAO;
import lopicost.spd.struts.form.GenericForm;
import lopicost.spd.struts.helper.ExtReHelper;


public class ExtReAction extends GenericAction  {

	static String className = "ExtReAction";

	   
	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		List errors = new ArrayList();
		GenericForm formulario =  (GenericForm) form; 
		formulario.setErrors(errors);
		String idUsuario = (String) request.getSession().getAttribute("login");
		//String idUsuario = formulario.getIdUsuario(); 
		//List<DivisionResidencia> listaDivisionResidencia = DivisionResidenciaDAO.getSecurityListaDivisionResidencias(idUsuario);
		//formulario.setListaDivisionResidencia(listaDivisionResidencia);
		List beansResi = ExtReHelper.getDatosProcesoCaptacion(getIdUsuario());
		formulario.setListaBeans(beansResi);
		ActionForward  actionForward =mapping.findForward("list");
		return actionForward;
	}
	
	public ActionForward preparaProceso(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		List errors = new ArrayList();
		GenericForm formulario =  (GenericForm) form; 
		formulario.setErrors(errors);
		String idUsuario = (String) request.getSession().getAttribute("idUsuario");
		//String idUsuario = (String) request.getSession().getAttribute("login");
		Usuario user = UsuarioDAO.findByIdUser(idUsuario);
		//Usuario user =  getcUddser();
		String[] oidVariasDivisionResidencia=formulario.getOidVariasDivisionResidencia();
		if(oidVariasDivisionResidencia==null || oidVariasDivisionResidencia.length==0 || (oidVariasDivisionResidencia.length==1 && oidVariasDivisionResidencia[0].equals("") ))
			errors.add( "Es necesario seleccionar al menos una residencia ");	
		else
		{
			//boolean result = DivisionResidenciaDAO.updateACeroResisUser(user.getIdUsuario());
			boolean result = DivisionResidenciaDAO.updateACeroResisUser(getIdUsuario());
			result = DivisionResidenciaDAO.updateAUnoSeleccionadas(getIdUsuario(), formulario);
			if(!result) errors.add( "Ha ocurrido un error en la petición de actualización de datos");	
			else 		errors.add( "Datos enviados. Por la noche se lanzará el proceso y por la mañana estarán disponibles los datos.");
			
			formulario.setOidVariasDivisionResidencia(null);

		}

		//return mapping.findForward("listHtml");
		ActionForward  actionForward =mapping.findForward("list");
		return actionForward;
	}

    
	   
	public ActionForward sinProcesar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List errors = new ArrayList();
		GenericForm formulario =  (GenericForm) form; 
		formulario.setErrors(errors);
		String idUsuario = (String) request.getSession().getAttribute("login");
		//String idUsuario = formulario.getIdUsuario(); 
		
		List listaPacientesBean=new ArrayList();
		if(formulario.getACTIONTODO()!=null && formulario.getACTIONTODO().equalsIgnoreCase("TRATAMIENTOS"))
			listaPacientesBean=ExtReHelper.getCipsSinProcesarTrat(getIdUsuario(), formulario.getIdDivisionResidencia());
		else if(formulario.getACTIONTODO()!=null && formulario.getACTIONTODO().equalsIgnoreCase("PENDIENTES"))
			listaPacientesBean=ExtReHelper.getCipsSinProcesarPendientes(getIdUsuario(), formulario.getIdDivisionResidencia());
		formulario.setListaBdPacientes(listaPacientesBean);
		ActionForward  actionForward =mapping.findForward("sinProcesar");
		return actionForward;
	}
	
	
	
	
	public void log (String message, int level)
	{
		Logger.log("SgaLogger",message,level);	
	}

	
	
}
	