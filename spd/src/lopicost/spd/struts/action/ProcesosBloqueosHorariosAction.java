package lopicost.spd.struts.action;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import lopicost.spd.model.ProcesoBloqueoHorario;
import lopicost.spd.struts.form.ProcesosBloqueosHorariosForm;
import lopicost.spd.struts.helper.ProcesoBloqueoHorarioHelper;

public class ProcesosBloqueosHorariosAction extends GenericAction  {

	 private ProcesoBloqueoHorarioHelper bloqueoHorarioService = new ProcesoBloqueoHorarioHelper();
	 static String className = "GestProcesosBloqueosHorariosAction";
		
		
	    public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws SQLException 
	    {
	        List<ProcesoBloqueoHorario> bloqueosHorarios = bloqueoHorarioService.listarTodas();
	        request.setAttribute("listaBloqueos", bloqueosHorarios);
	        return mapping.findForward("list");
	    }

	    public ActionForward nueva(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) 
	    {
	    	ProcesosBloqueosHorariosForm f = (ProcesosBloqueosHorariosForm) form;
	        f.resetear(); // limpia el formulario
	        return mapping.findForward("editar");
	    }

	    public ActionForward editar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws SQLException, IllegalAccessException, InvocationTargetException 
	    {
	    	ProcesosBloqueosHorariosForm f = (ProcesosBloqueosHorariosForm) form;
	        //int id = Integer.parseInt(request.getParameter("oidBloqueoHorario"));
	        ProcesoBloqueoHorario bloqueoHorario = bloqueoHorarioService.obtenerPorOid(f.getOidBloqueoHorario());

	        if(bloqueoHorario!=null)
	        {
		        BeanUtils.copyProperties(f, bloqueoHorario);
		        f.setBloqueoHorario(bloqueoHorario);
	        }
	        return mapping.findForward("editar");
	    }

	    public ActionForward guardar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	    {
	    	ProcesosBloqueosHorariosForm f = (ProcesosBloqueosHorariosForm) form;
	   	 	List errors = new ArrayList();
	    	
	    	f.setErrors(errors);
	    	ProcesoBloqueoHorario bloqueoOriginal = bloqueoHorarioService.obtenerPorOid(f.getOidBloqueoHorario());
	    	ProcesoBloqueoHorario bloqueoEditado = new ProcesoBloqueoHorario();
	        BeanUtils.copyProperties(bloqueoEditado, f);
	        boolean result = bloqueoHorarioService.guardar(getIdUsuario(), bloqueoOriginal, bloqueoEditado, errors);
	        if(!result) 
	        {
	        	return editar(mapping, form, request, response) ;
	        
	        }
	        
	        return mapping.findForward("success");
	    }

	    public ActionForward borrar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws SQLException 
	    {
	    	ProcesosBloqueosHorariosForm f = (ProcesosBloqueosHorariosForm) form;
	        ProcesoBloqueoHorario bloqueoHorario = bloqueoHorarioService.obtenerPorOid(f.getOidBloqueoHorario());

	       // int id = Integer.parseInt(request.getParameter("id"));
	        bloqueoHorarioService.borrar(getIdUsuario(), bloqueoHorario);
	        return mapping.findForward("success");
	    }

}
	