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

import lopicost.spd.model.ProcesoRestricciones;
import lopicost.spd.struts.form.ProcesosRestriccionesForm;
import lopicost.spd.struts.helper.ProcesoRestriccionesHelper;

public class GestProcesosRestriccionesAction extends GenericAction  {

	 private ProcesoRestriccionesHelper restriccionService = new ProcesoRestriccionesHelper();
	 static String className = "GestProcesosRestriccionesAction";
		
		
	    public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws SQLException 
	    {
	        List<ProcesoRestricciones> restricciones = restriccionService.listarTodas();
	        request.setAttribute("listaRestricciones", restricciones);
	        return mapping.findForward("list");
	    }

	    public ActionForward nueva(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) 
	    {
	    	ProcesosRestriccionesForm f = (ProcesosRestriccionesForm) form;
	        f.resetear(); // limpia el formulario
	        return mapping.findForward("editar");
	    }

	    public ActionForward editar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws SQLException, IllegalAccessException, InvocationTargetException 
	    {
	    	ProcesosRestriccionesForm f = (ProcesosRestriccionesForm) form;
	        //int id = Integer.parseInt(request.getParameter("oidRestriccion"));
	        ProcesoRestricciones restriccion = restriccionService.obtenerPorOid(f.getOidRestriccion());

	        if(restriccion!=null)
	        {
		        BeanUtils.copyProperties(f, restriccion);
		        f.setRestriccion(restriccion);
	        }
	        return mapping.findForward("editar");
	    }

	    public ActionForward guardar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	    {
	    	ProcesosRestriccionesForm f = (ProcesosRestriccionesForm) form;
	   	 	List errors = new ArrayList();
	    	
	    	f.setErrors(errors);
	    	ProcesoRestricciones restriccionOriginal = restriccionService.obtenerPorOid(f.getOidRestriccion());
	    	ProcesoRestricciones restriccionEditada = new ProcesoRestricciones();
	        BeanUtils.copyProperties(restriccionEditada, f);
	        boolean result = restriccionService.guardar(getIdUsuario(), restriccionOriginal, restriccionEditada, errors);
	        if(!result) 
	        {
	        	return editar(mapping, form, request, response) ;
	        
	        }
	        
	        return mapping.findForward("success");
	    }

	    public ActionForward borrar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws SQLException 
	    {
	    	ProcesosRestriccionesForm f = (ProcesosRestriccionesForm) form;
	        ProcesoRestricciones restriccion = restriccionService.obtenerPorOid(f.getOidRestriccion());

	       // int id = Integer.parseInt(request.getParameter("id"));
	        restriccionService.borrar(getIdUsuario(), restriccion);
	        return mapping.findForward("success");
	    }

}
	