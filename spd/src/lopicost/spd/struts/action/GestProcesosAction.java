package lopicost.spd.struts.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import lopicost.config.logger.Logger;
import lopicost.spd.controller.SpdLogAPI;
import lopicost.spd.controller.listener.ControladorProcesos;
import lopicost.spd.model.Proceso;
import lopicost.spd.model.ProcesoEjecucion;
import lopicost.spd.model.ProcesoEjecucionLog;
import lopicost.spd.model.Usuario;
import lopicost.spd.persistence.UsuarioDAO;
import lopicost.spd.struts.form.ProcesosForm;
import lopicost.spd.struts.helper.ProcesoHelper;
import lopicost.spd.utils.SPDConstants;


public class GestProcesosAction extends GenericAction  {

	static String className = "GestProcesosAction";
	ProcesoHelper helper = new ProcesoHelper();
	ControladorProcesos restHelper = new ControladorProcesos();

	/**
	 * Listado de los procesos del sistema, normalmente son PROCEDURE
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
    public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ProcesosForm formulario =  (ProcesosForm) form; 
	//	List errors = new ArrayList();  dejamos sin inicializar errores por si vienen de otras operaciones, como "nuevo"
	//	formulario.setErrors(errors);
		Usuario user = UsuarioDAO.findByIdUser(getIdUsuario());
		if(user==null) 
		{
			List errors = new ArrayList();
	    	errors.add( "Error sesión usuario, es necesario volver a hacer login");
			return mapping.findForward("errorSession");
		}
    	List<Proceso> procesos = helper.list(user.getIdUsuario());
		
		formulario.setProcesos(procesos);

    	return mapping.findForward("list");
    }

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
    public ActionForward nuevo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ProcesosForm f =  (ProcesosForm) form; 
		List<String> errors = new ArrayList<String>();
		f.setErrors(errors);
		Usuario user = UsuarioDAO.findByIdUser(getIdUsuario());
		if(user==null) 
		{
	    	errors.add( "Error sesión usuario, es necesario volver a hacer login");
			return mapping.findForward("errorSession");
		}	
		
		f.setErrors(errors);
		System.out.println(className + " .nuevo()  "  +f.getACTIONTODO());
	
		if(f.getACTIONTODO()!=null && f.getACTIONTODO().equals("NUEVO"))
		{
			//para controlar que solo se realice el reset en el nuevo
			request.setAttribute("action", "nuevo");
			f.reset(mapping, request);	//inicializamos campos
		}
		if(f.getACTIONTODO()!=null && f.getACTIONTODO().equals("NUEVO_OK"))
		{
			helper.validarDatos(getIdUsuario(), f, errors);
			if(errors.size()==0)
			{
				boolean result =helper.nuevoProceso(getIdUsuario(), f, errors);
				if(result)
				{
					errors.add( "Registro creado correctamente ");
					list( mapping,  form,  request,  response);
					return mapping.findForward("list");
				}
				else errors.add( "No se ha podido crear el registro, es necesario que se revisen los datos.");
				
			}

	
			f.setErrors(errors);
		}
		//inicializamos
		request.setAttribute("action", "");

		return mapping.findForward("nuevo");
	}
    
	public ActionForward detalle(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ProcesosForm f =  (ProcesosForm) form;
		Proceso proceso = null;
		proceso=helper.findByOidProceso(getIdUsuario(), f.getOidProceso());
		f.setProceso(proceso);
		return mapping.findForward("detalle");
	}
	
	
	public ActionForward editar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ProcesosForm f =  (ProcesosForm) form;

		Proceso proceso = null;
		//proceso=ProcesoDAO.findByOidProceso(getIdUsuario(), f.getOidProceso());
		proceso=helper.findByOidProceso(getIdUsuario(), f.getOidProceso());
    
		List errors = new ArrayList();
		f.setErrors(errors);
		if(f.getACTIONTODO()!=null && f.getACTIONTODO().equals("EDITAR"))
		{
			//
		}
		if(f.getACTIONTODO()!=null && f.getACTIONTODO().equals("EDITA_OK"))
		{
			helper.validarDatos(getIdUsuario(), f, errors);
			if(errors.size()==0)
			{
				boolean result=helper.actualizaDatos(getIdUsuario(), proceso, f);
				if(result)
				{
					errors.add( "Editado correctamente ");
				}
				else errors.add( "No se ha editado el registro");

				list( mapping,  form,  request,  response);
				return mapping.findForward("list");
			}
			
		}
		f.setProceso(proceso);
	 
		 
		return mapping.findForward("editar");
	}
	
	public ActionForward lanzar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ProcesosForm f =  (ProcesosForm) form;
		final Proceso proceso =helper.findByOidProceso(getIdUsuario(), f.getOidProceso());
		f.setProceso(proceso);
		List errors = new ArrayList();
		f.setErrors(errors);
		if(!proceso.getActivo().equalsIgnoreCase(SPDConstants.PROCESO_ACTIVO)) 
		{
			errors.add("El proceso está " + proceso.getActivo() + " Es necesario activarlo primero.");
			list( mapping,  form,  request,  response);
			return mapping.findForward("list");
		}
			
		
		// Lanza el procedimiento en segundo plano
	    new Thread(() -> {
	        try {
	    		boolean result= restHelper.evaluarYEjecutarProcesos(getIdUsuario(), proceso); // ejecuta el procedure
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }).start();
		
		/*
		if(result)
		{
			errors.add( "Proceso lanzado correctamente ");
			//INICIO creación de log en BBDD
			try{
				SpdLogAPI.addLog(getIdUsuario(), null,  null, null, SpdLogAPI.A_PROCESO, SpdLogAPI.B_LANZAMIENTO, "", "SpdLog.proceso.lanzado.manual", 
						   proceso.getLanzadera() );
			}catch(Exception e){}	// Cambios--> @@.
			//FIN creación de log en BBDD
			
		}
		else errors.add( "No se ha lanzado el proceso");
			
			*/
		//INICIO creación de log en BBDD
		try{
			SpdLogAPI.addLog(getIdUsuario(), null,  null, null, SpdLogAPI.A_PROCESO, SpdLogAPI.B_LANZAMIENTO, "", "SpdLog.proceso.lanzado.manual", 
					   proceso.getLanzadera() );
		}catch(Exception e){}	// Cambios--> @@.
		//FIN creación de log en BBDD
		list( mapping,  form,  request,  response);
		return mapping.findForward("list");
		

	}
	
    
	public ActionForward listadoEjecuciones(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ProcesosForm f =  (ProcesosForm) form;
		Proceso proceso = null;
		proceso=helper.findByOidProceso(getIdUsuario(), f.getOidProceso());
		f.setProceso(proceso);
		List<ProcesoEjecucion> listadoEjecuciones = helper.listarEjecuciones(getIdUsuario(), proceso.getOidProceso());
		proceso.setListadoEjecuciones(listadoEjecuciones);
		return mapping.findForward("listadoEjecuciones");
	}
	
	public ActionForward logEjecucion(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ProcesosForm f =  (ProcesosForm) form;
		Proceso proceso = null;
		proceso=helper.findByOidProceso(getIdUsuario(), f.getOidProceso());
		f.setProceso(proceso);
		ProcesoEjecucion ejec =helper.findEjecucionByOid(getIdUsuario(), f.getOidProcesoEjecucion());
		proceso.setEjecucion(ejec);
		List<ProcesoEjecucionLog> detalleEjecucion = helper.listarLogEjecucion(getIdUsuario(), ejec.getOidProcesoEjecucion());
		ejec.setDetalleEjecucion(detalleEjecucion);

		return mapping.findForward("logEjecucion");
	}
	
	
	
	
	public ActionForward lookUp(ActionMapping mapping,ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		ProcesosForm f =  (ProcesosForm) form;
		f.setProcesos(helper.list(getIdUsuario()));
		f.setFieldName1(request.getParameter("fieldName1"));
		return mapping.findForward("lookUp");
	}

	
	/*
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
	*/	
    
    	
		public void log (String message, int level)
		{
			Logger.log("SpdLogger",message,level);	
		}

	 
		
		
		/*	
    // Este método gestiona la creación y actualización de procesos.
    public ActionForward gestionarProcesos(ActionMapping mapping, ActionForm form, 
                                           HttpServletRequest request, HttpServletResponse response) throws Exception {
        // Obtener los datos del formulario
        ProcesosForm procesosForm = (ProcesosForm) form;
        ProcesoDAO procesosDAO = new ProcesoDAO();

        // Aquí se guardan o actualizan los procesos en la base de datos
        Proceso proceso = new Proceso();
        proceso.setNombreProceso(procesosForm.getNombreProceso());
        proceso.setDescripcion(procesosForm.getDescripcion());
        proceso.setEstado(procesosForm.getEstado());
        proceso.setTipoEjecucion(procesosForm.getTipoEjecucion());
        proceso.setFrecuencia(procesosForm.getFrecuencia());
        
        String horaEjecucion = procesosForm.getHoraEjecucion();
        if (horaEjecucion == null || horaEjecucion.isEmpty()) {
        	horaEjecucion = "03:00";
        }
        proceso.setHoraEjecucion(horaEjecucion);
        
        proceso.setMaxReintentos(procesosForm.getMaxReintentos());

        // Insertar el proceso en la base de datos
     // procesosDAO.insertarProceso(proceso);

        // Redirigir a la página de confirmación o lista de procesos
        return mapping.findForward("procesosGuardados");
    }

    // Este método maneja la visualización del historial de un proceso.
    
    public ActionForward verHistorial(ActionMapping mapping, ActionForm form,
                                      HttpServletRequest request, HttpServletResponse response) throws Exception {
        ProcesosForm procesosForm = (ProcesosForm) form;
        int oidProceso = procesosForm.getOidProceso();
        ProcesoHistoricoDAO historialDAO = new ProcesoHistoricoDAO();
        List<ProcesoHistorico> historial = historialDAO.obtenerPorProceso(oidProceso);

        // Colocar el historial en el contexto para su visualización en la JSP
        request.setAttribute("historial", historial);

        // Redirigir a la página que muestra el historial
        return mapping.findForward("verHistorial");
    }
    

    // Este método maneja las acciones de inicio, parada o reinicio de un proceso.
    public ActionForward accionProceso(ActionMapping mapping, ActionForm form,
                                      HttpServletRequest request, HttpServletResponse response) throws Exception {
        ProcesosForm procesosForm = (ProcesosForm) form;
        int oidProceso = procesosForm.getOidProceso();
        String accion = procesosForm.getAccion();
        
        ProcesoHelper procesoHelper = new ProcesoHelper();
        Proceso proceso = procesoHelper.obtenerProcesoPorId(getIdUsuario(), oidProceso);
        proceso.setOidProceso(procesosForm.getOidProceso());

        switch (accion) {
            case "iniciar":
                // Verificar estado antes de iniciar
                String estado = procesoHelper.obtenerEstadoProceso(getIdUsuario(), proceso.getOidProceso());
                if ("EN_EJECUCION".equals(estado)) {
                    System.out.println("El proceso ya está en ejecución.");
                    break;
                }
                procesoHelper.iniciarProceso(getIdUsuario(), proceso.getOidProceso());
                break;

            case "detener":
                procesoHelper.detenerProceso(getIdUsuario(), proceso.getOidProceso());
                break;

            case "reiniciar":
                procesoHelper.reiniciarProceso(getIdUsuario(), proceso.getOidProceso());
                break;

            default:
                break;
        }

        return mapping.findForward("list"); // O la vista que desees mostrar
    }
   // Método para convertir String a Timestamp
    private Timestamp convertirStringToTimestamp(String fechaStr) {
        try {
            // Suponiendo que el formato de fecha es "yyyy-MM-dd HH:mm:ss"
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            java.util.Date date = sdf.parse(fechaStr);  // Parseamos el String a Date
            return new Timestamp(date.getTime());  // Convertimos el Date a Timestamp
        } catch (Exception e) {
            e.printStackTrace();
            return null;  // Devolver null si hay un error en la conversión
        }
    }
	
    // Método para convertir String a Time
    private Time convertirStringToTime(String horaStr) {
        try {
            // Suponiendo que el formato de fecha es "yyyy-MM-dd HH:mm:ss"
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            java.util.Date date = sdf.parse(horaStr);  // Parseamos el String a Date
            return new Time(date.getTime());  // Convertimos el Date a Timestamp
        } catch (Exception e) {
            e.printStackTrace();
            return null;  // Devolver null si hay un error en la conversión
        }
    }
	*/


}
	