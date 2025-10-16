package lopicost.spd.struts.helper;

import java.util.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import lopicost.spd.controller.SpdLogAPI;
import lopicost.spd.model.Proceso;
import lopicost.spd.persistence.ProcesoDAO;
import lopicost.spd.struts.form.ProcesosForm;
import lopicost.spd.utils.DateUtilities;

public class ProcesoHelper2 {
	private static ProcesoDAO procesoDAO = new ProcesoDAO();
	
	public Proceso obtenerProcesoPorId(String idUsuario, int oidProceso) throws SQLException {
		// TODO Esbozo de método generado automáticamente
    	//antipete List listado = procesoDAO.findByFilters(idUsuario, null, null, oidProceso);
		List listado = null;
		return (listado !=null && listado.size()>0) ? (Proceso) listado.get(0) : null;
	}


	public static String obtenerEstadoProceso(String idUsuario, int oidProceso) throws SQLException {
		// TODO Esbozo de método generado automáticamente
    	//antipete List listado = procesoDAO.findByFilters(idUsuario, null, null, oidProceso);
		List listado = null;
    	//antipete 	return (listado !=null && listado.size()>0) ? ((Proceso) listado.get(0)).getEstado() : null;
		return null;

	}
	
	// lógica para iniciar el proceso
    public void iniciarProceso(String idUsuario, int oidProceso) {
        // Verificar si el proceso ya está en ejecutó
        if (procesoDAO.estaEnEjecucion(oidProceso)) {
            System.out.println("El proceso ya está en ejecutó.");
            return;
        }
        
        // Si no está en ejecutó, iniciar el proceso
        System.out.println("Lanzando proceso con OID: " + oidProceso);
    	//antipete procesoDAO.iniciarProceso(idUsuario, oidProceso);
    }

    // lógica para detener el proceso
    public void detenerProceso(String idUsuario, int oidProceso) {
        System.out.println("Deteniendo proceso con OID: " + oidProceso);
        procesoDAO.detenerProceso(idUsuario, oidProceso);
    }

    // lógica para reiniciar un proceso
    public void reiniciarProceso(String idUsuario, int oidProceso) {
        // lógica para reiniciar el proceso
        System.out.println("Reiniciando proceso con OID: " + oidProceso);
    	//antipete  procesoDAO.actualizarEstadoProceso(oidProceso, "En ejecutó");
    	//antipete  procesoDAO.iniciarProceso(idUsuario, oidProceso);
    }
    
    public void copiarProcesoAHistoricoPorOid(int oidProceso) {
        String sql = "INSERT INTO ProcesosHistorico (" +
            "oidProceso, nombreProceso, descripcion, estado, parametros, tipoEjecucion, " +
            "frecuencia, horaEjecucion, diasSemana, maxReintentos, fechaDesde, fechaHasta, fechaInicioEjecucion, fechaFinEjecucion, " +
            "duracionSegundos, resultado, codigoResultado, error, usuarioEjecucion, mensaje, tipoError" +
            ") SELECT " +
            "oidProceso, nombreProceso, descripcion, estado, parametros, tipoEjecucion, " +
            "frecuencia, horaEjecucion, diasSemana, maxReintentos, fechaInicio, fechaFin, " +
            "duracionSegundos, resultado, codigoResultado, error, usuarioEjecucion, mensaje, tipoError " +
            "FROM Procesos WHERE oidProceso = ?";

    }


	public List<Proceso> list(String idUsuario) throws SQLException {
		return procesoDAO.list(idUsuario);
	}


	public static boolean nuevo(String idUsuario, ProcesosForm form, List errors) throws ParseException, SQLException {
		boolean check = compruebaSiExisteLanzadera(idUsuario, form, errors);
		if(!check) 
			return false;
		
		// Conversores
		SimpleDateFormat timestampFormat = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
		Proceso proceso = new Proceso();

		// Asignaciones simples
		proceso.setNombreProceso(form.getNombreProceso()); 
		proceso.setLanzadera(form.getLanzadera());
		proceso.setDescripcion(form.getDescripcion());
    	//antipete proceso.setEstado(form.getEstado());
		proceso.setParametros(form.getParametros());
		proceso.setTipoEjecucion(form.getTipoEjecucion());
		proceso.setTipoPeriodo(form.getTipoPeriodo());
		proceso.setDiasSemana(form.getDiasSemana());
    	//antipete proceso.setResultado(form.getResultado());
    	//antipete proceso.setUsuarioEjecucion(form.getUsuarioEjecucion());
    	//antipete proceso.setMensaje(form.getMensaje());
    	//antipete proceso.setTipoError(form.getTipoError());
    	//antipete proceso.setCodigoResultado(form.getCodigoResultado());
    	//antipete proceso.setError(form.getError());
		// Campos opcionales con conversión y validación
		if (form.getFechaCreacion() != null && !form.getFechaCreacion().isEmpty()) {
	    	Date parsedDate = (Date) timestampFormat.parse(form.getFechaCreacion());
	    	//antipete proceso.setFechaCreacion(parsedDate);
		}
		
		if (form.getHoraEjecucion() != null && !form.getHoraEjecucion().isEmpty()) {
		    proceso.setHoraEjecucion(form.getHoraEjecucion());
		}

		if (form.getMaxReintentos()>0) {
		    proceso.setMaxReintentos(form.getMaxReintentos());
		}

		if (form.getMaxDuracionSegundos()>0) {
		    proceso.setMaxDuracionSegundos(form.getMaxDuracionSegundos());
		}

		
		if (form.getFechaDesde() != null && !form.getFechaDesde().isEmpty()) {
			Date fechaDesde = DateUtilities.getDate( form.getFechaDesde(), "dd/MM/yyyy");
			String fechaDesdeFormateada =  timestampFormat.format(fechaDesde);
		    proceso.setFechaDesde(fechaDesdeFormateada);
		}

		if (form.getFechaHasta() != null && !form.getFechaHasta().isEmpty()) {
			Date fechaHasta = DateUtilities.getDate( form.getFechaHasta(), "dd/MM/yyyy");
			String fechaHastaFormateada =  timestampFormat.format(fechaHasta);
		    proceso.setFechaHasta(fechaHastaFormateada);
		}

    	//antipete 	if (form.getDuracionSegundos()>0) {
	    	//antipete 	    proceso.setDuracionSegundos(form.getDuracionSegundos());
    	//antipete 	}
		

		boolean result =  procesoDAO.nuevo(idUsuario, proceso);
		if(result)
		{
			
			String mensaje = proceso.toString();

			//INICIO creación de log en BBDD
			try{
				SpdLogAPI.addLog(idUsuario, null,  null, null, SpdLogAPI.A_PROCESO, SpdLogAPI.B_CREACION, "", "SpdLog.proceso.creado.general", 
						mensaje );
			}catch(Exception e){}	// Cambios--> @@.
			//FIN creación de log en BBDD
			
		}

		
		return result;
	}


	private static boolean compruebaSiExisteLanzadera(String idUsuario, ProcesosForm form, List errors) throws SQLException {
		if(form.getLanzadera()==null || form.getLanzadera().equals(""))
		{
			errors.add( "No se ha informado de la lanzadera. ");
			return false;
		}
    	//antipete List procesos = ProcesoDAO.findByFilters(idUsuario, null, form.getLanzadera(), -1);
		List procesos=null;
		if(procesos!=null && procesos.size()>0)
		{
			errors.add( "Ya existe una lanzadera con el mismo nombre. ");
			return false;
		}
			
		return true;
	}
	
}
