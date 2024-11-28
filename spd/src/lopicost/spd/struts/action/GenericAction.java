
package lopicost.spd.struts.action;

//import org.apache.log4j.Logger;

import lopicost.config.logger.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.struts.actions.DispatchAction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

import lopicost.spd.security.form.LoginForm;
import lopicost.spd.utils.TextManager;

public abstract class GenericAction extends DispatchAction
{
	private String idUsuario = null;
	private boolean debug= false;
	public String logDirectory = null;
	List avisos = new ArrayList();

	public ActionForward execute(ActionMapping mapping, ActionForm form,
	        HttpServletRequest request, HttpServletResponse response)
	        throws Exception {
		List errors = new ArrayList();
		HttpSession session = request.getSession();
  	try{
  		
	    // Configura la ubicación del directorio de logs
        // Configura la ubicación del directorio de logs
        //logDirectory = getServlet().getServletContext().getRealPath("/WEB-INF/logs");
  		logDirectory = "c:/logs";
        System.setProperty("log.directory", logDirectory);
        System.out.println(" logDirectory " + logDirectory );
        
        // Inicializa Log4j con la configuración desde el archivo log4j.properties
        String log4jConfigFile = getServlet().getServletContext().getRealPath("/WEB-INF/classes/lopicost/config/pool/dbaccess/log4j.properties");
        PropertyConfigurator.configure(log4jConfigFile);

        try{
  			idUsuario = null;
			lopicost.spd.security.form.LoginForm loginForm = (LoginForm) form;
			idUsuario = loginForm.getIdUsuario();
			loginForm.setErrors(errors);
        }
        	catch(Exception e)
        	{}
		}catch(Exception e) {
	    	Logger.get().error("Error execute : "+e.getMessage(), e);
	    		try{
	    			
				lopicost.spd.struts.form.EnlacesForm enlacesForm = (lopicost.spd.struts.form.EnlacesForm) form;
				idUsuario = enlacesForm.getIdUsuario();
				enlacesForm.setErrors(errors);
			}catch(Exception e2) {
				
			}
		}
		if (idUsuario == null || idUsuario.equals(""))
		{
		    idUsuario = (String) session.getAttribute("idUsuario");
		}
 	    else
 	    {
	    	setIdUsuario(idUsuario);
		    // Almacenar el valor de inicio de sesión en la sesión del usuario
		    session.setAttribute("idUsuario", idUsuario);
 	    }
	    
        // Si el login no es válido, redirigir a la página de inicio de sesión
	    if (idUsuario == null || idUsuario.equals("")) 
	    {
	    	//errors.add( "Error sesión usuario, es necesario volver a hacer login");
	    	session.setAttribute("error", "Error sesión usuario, es necesario volver a hacer login.");
	        return mapping.findForward("errorSession");
	    }

	    // Realizar otras operaciones específicas de la acción madre aquí
	    return super.execute(mapping, form, request, response);

	}
	
	
	public String getIdUsuario() {
		return idUsuario;
	}


	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}


	private Object getText() {
		String text="Error al recuperar la sesión de persistencia del contexto";
		
		if(TextManager.getMensaje("error.persistence")!=null && !TextManager.getMensaje("error.persistence").equals(""))
			text=TextManager.getMensaje("error.persistence");
		return text;
	}
	
	public String getLogDirectory() { 
		return logDirectory;
	}

	public void setLogDirectory(String logDirectory) {
		this.logDirectory = logDirectory;
	}

	public void log (String message, int level)
	{
		Logger.log("SPDLogger",message,level);	
	}


	public List getAvisos() {
		return this.avisos;
	}


	public void setAvisos(List avisos) {
		this.avisos = avisos;
	}
	
	
	
}