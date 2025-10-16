package lopicost.spd.struts.helper;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

import lopicost.spd.controller.SpdLogAPI;
import lopicost.spd.model.Proceso;
import lopicost.spd.model.ProcesoEjecucion;
import lopicost.spd.persistence.ProcesoDAO;
import lopicost.spd.persistence.ProcesoEjecucionDAO;
import lopicost.spd.persistence.ProcesoHistoricoDAO;
import lopicost.spd.struts.form.ProcesosForm;
import lopicost.spd.utils.DateUtilities;
import lopicost.spd.utils.SPDConstants;

public class ProcesoHelper3 {
	private final ProcesoDAO procDAO = new ProcesoDAO();
	private final ProcesoEjecucionDAO procEjecDAO = new ProcesoEjecucionDAO();
	
  	public List<Proceso> list(String idUsuario) throws SQLException {
		return procDAO.list(idUsuario);
	}


	public boolean nuevoProceso(String idUsuario, ProcesosForm form, List errors) throws ParseException, SQLException {
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
		proceso.setActivo(form.getActivo());
		proceso.setDescripcion(form.getDescripcion());
		proceso.setParametros(form.getParametros());
		proceso.setTipoEjecucion(form.getTipoEjecucion());
		proceso.setTipoPeriodo(form.getTipoPeriodo());
		proceso.setDiasSemana(form.getDiasSemana());
		proceso.setDiasMes(form.getDiasMes());
		// Campos opcionales con conversión y validación
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

		proceso.setUsuarioCreacion(idUsuario);
		boolean result =  procDAO.nuevo(idUsuario, proceso);
		if(result)
		{
			
			String mensaje = proceso.toString();

			//INICIO creación de historico en BBDD
			boolean historico = creaProcesoHistoricoPorLanzadera(proceso.getLanzadera());
			
			//INICIO creación de log en BBDD
			try{
				SpdLogAPI.addLog(idUsuario, null,  null, null, SpdLogAPI.A_PROCESO, SpdLogAPI.B_CREACION, "", "SpdLog.proceso.creado.general", 
						" Creado histórico --> " + historico  + " / Datos proceso " +  proceso.toString() );
			}catch(Exception e){}	// Cambios--> @@.
			//FIN creación de log en BBDD
		}
		return result;
	}

    public boolean creaProcesoHistoricoPorLanzadera(String lanzadera) throws SQLException {
    	return ProcesoHistoricoDAO.creaProcesoHistoricoPorLanzadera(lanzadera);
    }

	private boolean compruebaSiExisteLanzadera(String idUsuario, ProcesosForm form, List errors) throws SQLException {
		if(form.getLanzadera()==null || form.getLanzadera().equals(""))
		{
			errors.add( "No se ha informado de la lanzadera. ");
			return false;
		}
		List procesos = procDAO.findByFilters(idUsuario, form.getLanzadera(), -1, false, false);
		if(procesos!=null && procesos.size()>0)
		{
			errors.add( "Ya existe una lanzadera con el mismo nombre. ");
			return false;
		}
			
		return true;
	}
	
/**
	-------------------------------------------------------------------------------------------------------------------
	---------------------------- MétodoS DE ejecutó ----------------------------------------------------------------- 
	-------------------------------------------------------------------------------------------------------------------
 */
	
	
	public Proceso obtenerProcesoPorId(String idUsuario, int oidProceso, boolean soloAutomaticos) throws SQLException {
		// TODO Esbozo de método generado automáticamente
		List listado = procDAO.findByFilters(idUsuario, null, oidProceso, false, soloAutomaticos);
		return (listado !=null && listado.size()>0) ? (Proceso) listado.get(0) : null;
	}

/*
	public static String obtenerEstadoProceso(String idUsuario, int oidProceso) throws SQLException {
		// TODO Esbozo de método generado automáticamente
		List listado = procesoDAO.findByFilters(idUsuario, null, null, oidProceso);
		return (listado !=null && listado.size()>0) ? ((Proceso) listado.get(0)).getEstado() : null;

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
        procesoDAO.iniciarProceso(idUsuario, oidProceso);
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
        procesoDAO.actualizarEstadoProceso(oidProceso, "En ejecutó");
        procesoDAO.iniciarProceso(idUsuario, oidProceso);
    }

*/
	public boolean actualizaDatos(String idUsuario, Proceso proceso, ProcesosForm f)  throws SQLException {
		boolean cambios =false;
		String antes = ""; 
		String despues = "";
		
		String querySet="";
		if(proceso!=null)
		{
			/*  INICIO Activo*/
			if (!Objects.equals(proceso.getActivo(), f.getActivo())) 
			{
				antes+=  " | Activo: "+ proceso.getActivo();
				despues+=" | Activo: "+ f.getActivo();

				cambios =true;
				if (!querySet.equals("")) 
					querySet+= ", "; //añadimos la coma en caso que exista uno previo 		
				 
				querySet+= " activo = '"+ f.getActivo() + "'" ;
			}
		
			/*  INICIO Parámetros*/
			if (!Objects.equals(proceso.getParametros(), f.getParametros())) 
			{
				antes+=  " | Parámetros: "+ proceso.getParametros();
				despues+=" | Parámetros: "+ f.getParametros();

				if (!querySet.equals(""))  	querySet+= ", ";
				 
				cambios =true;
				querySet+= " parametros = '"+ f.getParametros() + "'" ;
			}
			/*  INICIO TipoEjecucion*/
			if (!Objects.equals(proceso.getTipoEjecucion(), f.getTipoEjecucion())) 
			{
				antes+=  " | TipoEjecucion: "+ proceso.getTipoEjecucion();
				despues+=" | TipoEjecucion: "+ f.getTipoEjecucion();

				if (!querySet.equals(""))  	querySet+= ", ";
				 
				cambios =true;
				querySet+= " tipoEjecucion = '"+ f.getTipoEjecucion() + "'" ;
			}
			/*  INICIO Frecuencia*/
			if (!Objects.equals(proceso.getTipoPeriodo(), f.getTipoPeriodo())) 
			{
				antes+=  " | Frecuencia: "+ proceso.getTipoPeriodo();
				despues+=" | Frecuencia: "+ f.getTipoPeriodo();

				if (!querySet.equals(""))  	querySet+= ", ";
				 
				cambios =true;
				querySet+= " frecuencia = '"+ f.getTipoPeriodo() + "'" ;
			}
			/*  INICIO DiasSemana*/
			if (!Objects.equals(proceso.getDiasSemana(), f.getDiasSemana())) 
			{
				antes+=  " | Activo: "+ proceso.getDiasSemana();
				despues+=" | Activo: "+ f.getDiasSemana();

				if (!querySet.equals(""))  	querySet+= ", ";
				 
				cambios =true;
				querySet+= " diasSemana = '"+ f.getDiasSemana() + "'" ;
			}
			/*  INICIO DiasMes*/
			if (!Objects.equals(proceso.getDiasMes(), f.getDiasMes())) 
			{
				antes+=  " | Activo: "+ proceso.getDiasMes();
				despues+=" | Activo: "+ f.getDiasMes();

				if (!querySet.equals(""))  	querySet+= ", ";
				 
				cambios =true;
				querySet+= " diasMes = '"+ f.getDiasMes() + "'" ;
			}
			/*  INICIO HoraEjecucion*/
			if (!Objects.equals(proceso.getHoraEjecucion(), f.getHoraEjecucion())) 
			{
				antes+=  " | HoraEjecucion: "+ proceso.getHoraEjecucion();
				despues+=" | HoraEjecucion: "+ f.getHoraEjecucion();

				if (!querySet.equals(""))  	querySet+= ", ";
				 
				cambios =true;
				querySet+= " horaEjecucion = '"+ f.getHoraEjecucion() + "'" ;
			}
			/*  INICIO maxReintentos*/
			if (!Objects.equals(proceso.getMaxReintentos(), f.getMaxReintentos())) 
			{
				antes+=  " | MaxReintentos: "+ proceso.getMaxReintentos();
				despues+=" | MaxReintentos: "+ f.getMaxReintentos();

				if (!querySet.equals(""))  	querySet+= ", ";
				 
				cambios =true;
				querySet+= " maxReintentos = '"+ f.getMaxReintentos() + "'" ;
			}
			/*  INICIO MaxDuracionSegundos*/
			if (!Objects.equals(proceso.getMaxDuracionSegundos(), f.getMaxDuracionSegundos())) 
			{
				antes+=  " | MaxDuracionSegundos: "+ proceso.getMaxDuracionSegundos();
				despues+=" | MaxDuracionSegundos: "+ f.getMaxDuracionSegundos();

				if (!querySet.equals(""))  	querySet+= ", ";
				 
				cambios =true;
				querySet+= " maxDuracionSegundos = '"+ f.getMaxDuracionSegundos() + "'" ;
			}		
			/*  INICIO FechaDesde*/
			if (!Objects.equals(proceso.getFechaDesde(), f.getFechaDesde())) 
			{
				antes+=  " | FechaDesde: "+ proceso.getFechaDesde();
				despues+=" | FechaDesde: "+ f.getFechaDesde();

				if (!querySet.equals(""))  	querySet+= ", ";
				 
				cambios =true;
				querySet+= " fechaDesde = '"+ f.getFechaDesde() + "'" ;
			}
			/*  INICIO FechaHasta*/
			if (!Objects.equals(proceso.getFechaHasta(), f.getFechaHasta())) 
			{
				antes+=  " | FechaHasta: "+ proceso.getFechaHasta();
				despues+=" | FechaHasta: "+ f.getFechaHasta();

				if (!querySet.equals(""))  	querySet+= ", ";
				 
				cambios =true;
				querySet+= " fechaHasta = '"+ f.getFechaHasta() + "'" ;
			}		
			if(cambios)
			{
				String query = " UPDATE SPDAC.dbo.SPD_Procesos SET " + querySet + ", version=version+1 WHERE  OIDPROCESO='"+proceso.getOidProceso()+"'";
				cambios = ProcesoDAO.update(query);
				boolean historico = creaProcesoHistoricoPorLanzadera(proceso.getLanzadera());

				//INICIO creación de log en BBDD
				try{
					SpdLogAPI.addLog(idUsuario, null,  null, null, SpdLogAPI.A_PROCESO, SpdLogAPI.B_EDICION, SpdLogAPI.C_DATOSGENERALES, "SpdLog.proceso.edicion.general", 
							 new String[]{antes, despues} );
				}catch(Exception e){}	// Cambios--> @@.
				//FIN creación de log en BBDD
			}
	}
		return cambios;
}

	/** ok
     * Devuelve una lista de procesos activos
	 * @param idUsuario
	 * @param soloAutomaticos
	 * @return
	 * @throws SQLException
	 */
	public List<Proceso> listaProcesosActivos(String idUsuario, boolean soloAutomaticos) throws SQLException {
		return procDAO.findByFilters(idUsuario, null, -1, true, soloAutomaticos);
	}


	public String consultaEstadoEjecucion(String idUsuario, Proceso proceso) throws SQLException {
		ProcesoEjecucion ejec = procEjecDAO.obtenerUltimaEjecucion(idUsuario, proceso);
		return idUsuario;
		// TODO Esbozo de método generado automáticamente
		
	}


	public static String obtenerEstadoProceso(String idUsuario, int oidProceso) {
		// TODO Esbozo de método generado automáticamente
		return null;
	}


	public boolean actualizarEstadoEjecucion(Proceso proceso, String nuevoEstado) throws SQLException {
		boolean result = procEjecDAO.actualizarEstadoEjecucion(proceso, nuevoEstado);
		return result;
		
	}

/*
    public static void controlEjecucionesEnCurso() {
        List<ProcesoEjecucion> ejecucionesActivas = ProcesoEjecucionDAO.findByFilters(null, -1, null, -1, SPDConstants.PROCESO_EJEC_EJECUTANDO, false);
        
        for (ProcesoEjecucion ejec : ejecucionesActivas) {
            Proceso proceso = ProcesoDAO.findByOidProceso(null, ejec.getOidProceso());
            
        	int maxDuracion = proceso.getMaxDuracionSegundos();
            int transcurrido = calcularSegundosDesde(ejec.getFechaInicioEjecucion());

            if (transcurrido > maxDuracion) {
                ejec.setEstado(SPDConstants.PROCESO_EJEC_ERROR_TIEMPO_EXCEDIDO);
                ejec.setFechaFinEjecucion(obtenerAhora());
                ejec.setDuracionSegundos(transcurrido);
                ejec.setCodigoResultado(SPDConstants.PROCESO_CODE_ERROR_TIEMPO_EXCEDIDO);
                ejec.setMensaje("Duración máxima superada");
                ejec.setTipoError("Timeout");

                ProcesoEjecucionDAO.actualizarEjecucion(ejec);
            }
        }

}

    private static int calcularSegundosDesde(String fechaInicio) {
        LocalDateTime inicio = LocalDateTime.parse(fechaInicio, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        return (int) Duration.between(inicio, LocalDateTime.now()).getSeconds();
    }
    private static String obtenerAhora() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
    }

*/	public void finactualizarEstadosPendientes() {
		// TODO Esbozo de método generado automáticamente
		
	}


    

    
    /** ok
     * Método que se encarga de actualizar la ejecutó en caso de detectar tiempo excedido
     * @param ejec
     * @throws SQLException 
     */
    public boolean marcarComoExcedido(ProcesoEjecucion ejec) throws SQLException {
        
    	ejec.setEstado(SPDConstants.PROCESO_EJEC_ERROR);
        ejec.setFechaFinEjecucion(getAhora());
        ejec.setDuracionSegundos(calcularDuracion(ejec.getFechaInicioEjecucion()));
        ejec.setMensaje("Superó tiempo máximo");
        ejec.setCodigoResultado(SPDConstants.PROCESO_CODE_ERROR_TIEMPO_EXCEDIDO);
        ejec.setTipoError("Timeout");
        return procEjecDAO.actualizarEjecucion(ejec);
    }
    
    private int calcularDuracion(String fechaInicio) {
        LocalDateTime inicio = LocalDateTime.parse(fechaInicio, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        return (int) Duration.between(inicio, LocalDateTime.now()).getSeconds();
    }
    
    private String getAhora() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
    }


	public int contarErroresDesdeUltimoExito(Proceso proceso) throws SQLException {
		return procEjecDAO.contarErroresDesdeUltimoExito(proceso);
	}

	/** ok
	 * Método que se encarga de actualizar el estado de un proceso según el nuevoEstado pasado por parámetro
	 * @param proceso
	 * @param nuevoEstado
	 * @throws SQLException
	 */
	public void actualizarEstado(Proceso proceso, String nuevoEstado) throws SQLException {
		procDAO.actualizarEstadoProceso(proceso, nuevoEstado);
		
	}

	/** ok
	 * Método encargado de realizar un control de la ejecutó del proceso que se está ejecutando, para controlar si el tiempo  
	 * @param proceso
	 * @throws SQLException
	 */
	public void controlTiempoExcedido(Proceso proceso) throws SQLException {
    	ProcesoEjecucion ejec = proceso.getUltimaEjecucion();

        if (ejec != null 
        		&& SPDConstants.PROCESO_EJEC_ESTADO_EJECUTANDO.equalsIgnoreCase(ejec.getEstado())
        		&& duracionExcedida(ejec, proceso.getMaxDuracionSegundos())
        	){
                marcarComoExcedido(ejec);
                proceso.setUltimaEjecucion(null);  //vaciamos la ejecutó del proceso para que se pueda volver a lanzar (si toca)
         }
	}
	
	/** ok
	 * Método encargado de mirar si se ha llegado a los máximos intentos de un mismo proceso, a partir de la fecha del último proceso OK  
	 * @param proceso
	 * @throws SQLException
	 */
	public  void controlMaxIntentos(Proceso proceso) throws SQLException {
    	ProcesoEjecucion ejec = proceso.getUltimaEjecucion();
        if (ejec!=null)
        {
            int erroresSeguidos = contarErroresDesdeUltimoExito(proceso);
            if (erroresSeguidos >= proceso.getMaxReintentos()) {
                //proceso.setActivo(SPDConstants.PROCESO_BLOQUEADO);
                actualizarEstado(proceso, SPDConstants.PROCESO_BLOQUEADO);
                proceso.setUltimaEjecucion(null);
            }
        }
	}

	/** ok
	 * Este método devuelve true en caso que la duración en segundos desde la fecha de inicio de la ejecutó supere el valor máximo definido para este proceso
	 * @param ejec
	 * @param maxSegundos
	 * @return
	 */
    private  boolean duracionExcedida(ProcesoEjecucion ejec, int maxSegundos) {
        LocalDateTime inicio = LocalDateTime.parse(ejec.getFechaInicioEjecucion(), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        return Duration.between(inicio, LocalDateTime.now()).getSeconds() > maxSegundos;
    }

    
    /** ok
     * Se encarga de controlar si ha de lanzarse o no, según la programación.
     * Por defecto es sí, a no ser que la hora y día no 
     * @param proceso
     * @return
     */
    public boolean estaEnHoraDeLanzarse(Proceso proceso) {
        LocalDateTime ahora = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        // 1. Verificar hora de ejecutó
        LocalTime horaProgramada = LocalTime.parse(proceso.getHoraEjecucion()); // asume "HH:mm"
        if (ahora.toLocalTime().isBefore(horaProgramada)) return false;

        // 2. Verificar día de la semana si es semanal
        if (proceso.getTipoPeriodo().equalsIgnoreCase(SPDConstants.PER_SEMANAL)) {
        	// Devuelve el día de la semana como "1" (lunes) a "7" (domingo)
        	String diaHoy = String.valueOf(LocalDate.now().getDayOfWeek().getValue());
            
            if (proceso.getDiasSemana() == null || !proceso.getDiasSemana().toLowerCase().contains(diaHoy)) {
                return false;
            }
        }

        // 3. Verificar día del mes si es mensual
        if (proceso.getTipoPeriodo().equalsIgnoreCase(SPDConstants.PER_MENSUAL)) {
            int diaActual = LocalDate.now().getDayOfMonth(); // 1 a 31

            String diasDefinidos = proceso.getDiasMes(); // Ej: "1,15,30"
            List<Integer> diasPermitidos = Arrays.stream(diasDefinidos.split(","))
                                                 .map(String::trim)
                                                 .map(Integer::parseInt)
                                                 .collect(Collectors.toList());

            if (!diasPermitidos.contains(diaActual)) return false;
        }
        return true;
    }

    
	/** ok
	 * Método que se encarga de mirar si se ha de ejecutar el proceso por la programación realizada en tiempo, frecuencia
	 * Es necesario que el proceso no tenga una ejecutó en curso (solo se tendrá en cuenta si es null) 
	 * @param proceso
	 * @return true en caso que deba ejecutarse, false en caso contrario o que tenga una ejecucion asignada (no nula)
	 * @throws SQLException
	 */
    public boolean debeEjecutarse(Proceso proceso) throws SQLException {
    	//si tiene alguna ejecutó no nula, no debe ejecutarse
    	if(proceso.getUltimaEjecucion()!=null) return false;
    	
    	//miramos si por configuración hay que lanzarse
        if(estaEnHoraDeLanzarse(proceso)) return false;

        return true;
    }

	public Proceso findByOidProceso(String idUsuario, int oidProceso) throws SQLException {
		
		return procDAO.findByOidProceso( idUsuario,  oidProceso);
	}

}