package lopicost.spd.struts.helper;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime; 
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.stream.Collectors;

import lopicost.spd.model.Proceso;
import lopicost.spd.model.ProcesoEjecucion;
import lopicost.spd.persistence.ProcesoDAO;
import lopicost.spd.persistence.ProcesoEjecucionDAO;
import lopicost.spd.utils.HelperSPD;
import lopicost.spd.utils.SPDConstants;

public class ControladorProcesosHelper {
	private final ProcesoDAO procDAO = new ProcesoDAO();
	private final ProcesoEjecucionDAO procEjecDAO = new ProcesoEjecucionDAO();
//    private  final SimpleDateFormat DATE_FORMAT_TIME = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    //DateTimeFormatter FORMATTER_DATE = DateTimeFormatter.ofPattern("dd/MM/yyyy");
   // DateTimeFormatter FORMATTER_DATETIME =DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

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

    /** ok
     * Método que se encarga de actualizar la ejecutó en caso de detectar tiempo excedido
     * @param ejec
     * @throws SQLException 
     */
    public boolean marcarComoExcedido(ProcesoEjecucion ejec) throws SQLException {
        
       	ejec.setEstado(SPDConstants.PROCESO_EJEC_ESTADO_FINALIZADO);
       	ejec.setResultado(SPDConstants.PROCESO_EJEC_RESULT_ERROR);
        ejec.setFechaFinEjecucion(getAhora());
        ejec.setDuracionSegundos(calcularDuracion(ejec.getFechaInicioEjecucion()));
        ejec.setMensaje("Superó tiempo máximo");
        ejec.setCodigoResultado(SPDConstants.PROCESO_CODE_ERROR_TIEMPO_EXCEDIDO);
        ejec.setTipoError("Timeout");
        ejec.setNumIntentos(ejec.getNumIntentos()+1);
        return procEjecDAO.actualizarEjecucion(ejec);
    }
    
    /** ok
     * Auxiliar que devuelve en segundos transcurridos desde la fecha de inicio de la ejecutó del proceso
     * @param fechaInicio
     * @return
     */
    private int calcularDuracion(String fechaInicio) {
        LocalDateTime inicio = LocalDateTime.parse(fechaInicio, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        return (int) Duration.between(inicio, LocalDateTime.now()).getSeconds();
    }
    /**
     * auxiliar
     * @return
     */
    private String getAhora() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
    }

    /** ok
	 * Método encargado de contar los errores de un mismo proceso, a partir de la fecha del último proceso OK  
     * @param proceso
     * @return
     * @throws SQLException
     */
	public int contarErroresDesdeUltimoExito(Proceso proceso) throws SQLException {
		return procEjecDAO.contarErroresDesdeUltimoExito(proceso);
	}

	/** ok
	 * Método que se encarga de actualizar el estado de un proceso según el nuevoEstado pasado por parámetro
	 * @param proceso
	 * @param nuevoEstado
	 * @throws SQLException
	 */
	public void actualizarEstadoProceso(Proceso proceso, String nuevoEstado) throws SQLException {
		procDAO.actualizarEstadoProceso(proceso, nuevoEstado);
		
	}
	

	/**
	 * Método que se encarga de actualizar el estado de una ejecutó de un proceso según el nuevoEstado pasado por parámetro
	 * @param proceso
	 * @param nuevoEstado
	 * @return
	 * @throws SQLException
	
	public boolean actualizarEstadoEjecucion(Proceso proceso, String nuevoEstado) throws SQLException {
		boolean result = procEjecDAO.actualizarEstadoEjecucion(proceso, nuevoEstado);
		return result;
		
	}
	*/
	

	/** ok
	 * Método encargado de realizar un control de la ejecutó del proceso que se está ejecutando, para controlar si el tiempo  
	 * @param proceso
	 * @throws SQLException
	 */
	public void controlTiempoExcedido(Proceso proceso) throws SQLException {
    	//ProcesoEjecucion ejec = proceso.getUltimaEjecucion();
		ProcesoEjecucion ejec = proceso.getEjecucionActiva();

        if (ejec != null 
        		&& SPDConstants.PROCESO_EJEC_ESTADO_EJECUTANDO.equalsIgnoreCase(ejec.getEstado())
        		&& duracionExcedida(ejec, proceso.getMaxDuracionSegundos().intValue())
        	){
            System.out.println(HelperSPD.dameFechaHora() +  " - evaluarYEjecutarProcesos / SI marcarComoExcedido  ");

                marcarComoExcedido(ejec);
                //proceso.setUltimaEjecucion(null);  //vaciamos la ejecutó del proceso para que se pueda volver a lanzar (si toca)
                proceso.setEjecucionActiva(null);  //vaciamos la ejecutó del proceso para que se pueda volver a lanzar (si toca)
         }
        System.out.println(HelperSPD.dameFechaHora() + " - evaluarYEjecutarProcesos / NO TiempoExcedido  ");
        
	}
	
	/** ok
	 * Método encargado de mirar si se ha llegado a los máximos intentos de un mismo proceso, a partir del número de intentos del proceso anterior  
	 * @param proceso
	 * @throws SQLException
	 */
	public  void controlMaxIntentos(Proceso proceso) throws SQLException {
		//ProcesoEjecucion ejecucion = proceso.getUltimaEjecucion();
    	ProcesoEjecucion ejecucion = proceso.getEjecucionActiva();
    	if (ejecucion!=null)
        {
            int erroresPrevios = ejecucion.getNumIntentos();
            if (erroresPrevios > proceso.getMaxReintentos()) {
                actualizarEstadoProceso(proceso, SPDConstants.PROCESO_BLOQUEADO);
                ejecucion.setCodigoResultado(SPDConstants.PROCESO_CODE_BLOQUEADO);
                ejecucion.setResultado(SPDConstants.PROCESO_BLOQUEADO);
                ejecucion.setEstado(SPDConstants.PROCESO_EJEC_ESTADO_FINALIZADO);
                ejecucion.setNumIntentos(0); //reiniciamos contador
                ejecucion.setFechaFinEjecucion(HelperSPD.dameFechaHora());
                actualizarEstadoProcesoEjecucion(ejecucion);
                //proceso.setUltimaEjecucion(null);
                proceso.setEjecucionActiva(null); 
               // System.out.println(HelperSPD.dameFechaHora() + " - evaluarYEjecutarProcesos / SI MaxIntentos  " + erroresPrevios);
                
            }
        }
      // System.out.println(HelperSPD.dameFechaHora() + " - evaluarYEjecutarProcesos / NO MaxIntentos  ");
       	}
	
	
	private boolean actualizarEstadoProcesoEjecucion(ProcesoEjecucion ejecucion) throws SQLException {
		return procEjecDAO.actualizarEjecucion(ejecucion);
		
	}

	/** ok
	 * Método encargado de mirar si se ha llegado a los máximos intentos de un mismo proceso, a partir de la fecha del último proceso OK  
	 * @param proceso
	 * @throws SQLException
	 */
	public  void controlMaxIntentosPrevios(Proceso proceso) throws SQLException {
    	//ProcesoEjecucion ejec = proceso.getUltimaEjecucion();
		ProcesoEjecucion ejec = proceso.getEjecucionActiva();
      // if (ejec!=null)
        {
            int erroresSeguidos = contarErroresDesdeUltimoExito(proceso);
            if (erroresSeguidos >= proceso.getMaxReintentos()) {
                //proceso.setActivo(SPDConstants.PROCESO_BLOQUEADO);
                actualizarEstadoProceso(proceso, SPDConstants.PROCESO_BLOQUEADO);
                //proceso.setUltimaEjecucion(null);
                proceso.setEjecucionActiva(null); 
               // System.out.println(HelperSPD.dameFechaHora() + " - evaluarYEjecutarProcesos / SI controlMaxIntentosPrevios  " + erroresSeguidos);
                
            }
        }
       System.out.println(HelperSPD.dameFechaHora() + " - evaluarYEjecutarProcesos / NO controlMaxIntentosPrevios  " );
       	}

	/**
	 * Método que se encarga de limpiar procesos anteriores al último y que no han quedado bien cerrados
	 * Precondición - En este punto, si existe un proceso que se está ejecutando que está dentro de los límites de intentos y segundos, no aparecerá en el listado
	 * 				  porque la consulta lo excluirá. En caso que no haya ningún proceso, se descartaran los anteriores, porque si llega nulo es porque ya no está activo
	 * 				  según los controles del método de donde proviene.  
	 * @param proceso
	 * @throws SQLException 
	 */
	public void controlProcesosAnteriores(Proceso proceso) throws SQLException {
		//List<ProcesoEjecucion> list = procEjecDAO.obtenerEjecucionesAnteriores(proceso);
		procEjecDAO.limpiarOtrasEjecucionesNoCerradas(proceso);
        
	}


	
	/** ok
	 * Este método devuelve true en caso que la duración en segundos desde la fecha de inicio de la ejecutó supere el valor máximo definido para este proceso
	 * @param ejec
	 * @param maxSegundos
	 * @return
	 */
    /*private  boolean duracionExcedida(ProcesoEjecucion ejec, int maxSegundos) {
        LocalDateTime inicio = LocalDateTime.parse(ejec.getFechaInicioEjecucion(), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        return Duration.between(inicio, LocalDateTime.now()).getSeconds() > maxSegundos;
    }
    */
    private boolean duracionExcedida(ProcesoEjecucion ejec, int maxSegundos) {
        if (ejec.getFechaInicioEjecucion() == null || ejec.getFechaInicioEjecucion().isEmpty()) {
            return false; 
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        try {
            LocalDateTime inicio = LocalDateTime.parse(ejec.getFechaInicioEjecucion(), formatter);
            long duracion = Duration.between(inicio, LocalDateTime.now()).getSeconds();
            return duracion > maxSegundos;
        } catch (DateTimeParseException e) {
            // Aquí puedes loguear o manejar el error según tu necesidad
            return false; // o true si prefieres ser conservador
        }
    }

	/**
	 * Encargado de descartar los últimos procesos con estado finalizado o error 
	 * @param proceso
	 */
    /*
	public void controlEstadoUltimoProceso(Proceso proceso) {
		if(proceso==null) return;
		//if(proceso.getUltimaEjecucion()==null) return;
		if(proceso.getEjecucionActiva()==null) return;
		/*if(
			proceso.getUltimaEjecucion().getEstado().equalsIgnoreCase(SPDConstants.PROCESO_EJEC_CANCELADO)
			|| proceso.getUltimaEjecucion().getEstado().equalsIgnoreCase(SPDConstants.PROCESO_EJEC_FINALIZADO)
			|| proceso.getUltimaEjecucion().getEstado().equalsIgnoreCase(SPDConstants.PROCESO_EJEC_PENDIENTE)
		) */
		/*if(!proceso.getUltimaEjecucion().getEstado().equalsIgnoreCase(SPDConstants.PROCESO_EJEC_ESTADO_EJECUTANDO))	
			proceso.setUltimaEjecucion(null);
	}
	*/
	
    /** ok
     * Se encarga de controlar si ha de lanzarse o no, según la programación.
     * Por defecto es sí, a no ser que la hora y día no 
     * @param proceso
     * @return
     */
    public boolean estaEnHoraDiaDeLanzarse(Proceso proceso) {
        
    	if(proceso == null || proceso.getTipoPeriodo()==null ) return false;
        System.out.println("** LANZADERA --> " + proceso.getLanzadera());
    	boolean porHora= false;
    	boolean porDiaSemana= false;
    	boolean porDiaMes= false;
    	
    	String[] diasConcretos = ProcesoHelper.obtenerDiasValidosComoArray(proceso.getDiasMes());
    	boolean hayDiasConcretos = diasConcretos!=null && !diasConcretos.equals("");
    	boolean hayDiasSemana = proceso.getDiasSemana()!=null && !proceso.getDiasSemana().equals("");
    	boolean cadaXMinutos = proceso.getTipoPeriodo().equalsIgnoreCase(SPDConstants.PROCESO_FREC_PERIODO_MINUTOS);
    	boolean cadaXHora = proceso.getTipoPeriodo().equalsIgnoreCase(SPDConstants.PROCESO_FREC_PERIODO_HORAS);
    	boolean cadaXDia = proceso.getTipoPeriodo().equalsIgnoreCase(SPDConstants.PROCESO_FREC_PERIODO_DIAS);
    	boolean cadaXSemana = proceso.getTipoPeriodo().equalsIgnoreCase(SPDConstants.PROCESO_FREC_PERIODO_SEMANAS);
    	boolean cadaXMes = proceso.getTipoPeriodo().equalsIgnoreCase(SPDConstants.PROCESO_FREC_PERIODO_MESES);
        LocalDateTime ahora = LocalDateTime.now();
        LocalTime horaProgramada = LocalTime.parse(proceso.getHoraEjecucion()); // asume "HH:mm"
        // System.out.println("** ahora --> " + ahora.toString());
        //  System.out.println("** horaProgramada --> " + horaProgramada.toString());
        ProcesoEjecucion ultimaEjecucion = proceso.getUltimaEjecucion()!=null?proceso.getUltimaEjecucion():null;
        boolean ultimaFinalizadaOk = ultimaEjecucion!=null && ultimaEjecucion.getEstado().equalsIgnoreCase(SPDConstants.PROCESO_EJEC_ESTADO_FINALIZADO);
        
        
        
        			
        //1 - CONTROL POR HORA
        //para saber si se ha de ejecutar, buscaremos la fecha programada justo anterior a la fecha actual,
    	//para comparar ambas. En caso que los minutos sean menor que el intervalo de minutos 
        //de consulta (y mayor que 0) se lanzará el proceso.
        /*if(proceso.getUltimaEjecucion()!=null && proceso.getUltimaEjecucion().getEstado().equalsIgnoreCase(SPDConstants.PROCESO_EJEC_ESTADO_FINALIZADO) )
        {
        	if(proceso.getUltimaEjecucion().getFechaFinEjecucion()==null 
        			|| proceso.getUltimaEjecucion().getFechaFinEjecucion().
        }
        */
        //int cadaCuantosMinutos = cadaCuantosMinutosSeEjecuta(proceso); 
        LocalDateTime ultimaFechaAnterior = fechaProgramadaContigua(proceso, false);
        LocalDateTime primeraFechaPosterior = fechaProgramadaContigua(proceso, true);  
        // System.out.println("** ultimaFechaAnterior --> " + ultimaFechaAnterior.toString());
        // System.out.println("** Duration.between(ultimaFechaAnterior, ahora).toMinutes() --> " + Duration.between(ultimaFechaAnterior, ahora).toMinutes());
        //System.out.println("** SPDConstants.PROCESO_FRECUENCIA_LISTENER/60 --> " + SPDConstants.PROCESO_FRECUENCIA_LISTENER/60);
        // if(Duration.between(ultimaFechaAnterior, ahora).toMinutes()>=0 
        //		&& Duration.between(ultimaFechaAnterior, ahora).toMinutes()< SPDConstants.PROCESO_FRECUENCIA_LISTENER/60
        //		)
        if(Duration.between(ultimaFechaAnterior, ahora).toMinutes()>=SPDConstants.PROCESO_FRECUENCIA_LISTENER/60)
        	porHora=true;

        // System.out.println("** porHora --> " + porHora);

        //2 - CONTROL POR DIA SEMANA
    	// Devuelve el día de la semana como "1" (lunes) a "7" (domingo)
        // 2. Verificar día de la semana si es semanal
       if (proceso.getTipoPeriodo().equalsIgnoreCase(SPDConstants.PROCESO_FREC_PERIODO_SEMANAS)) 
       {
	       	String diaHoy = String.valueOf(LocalDate.now().getDayOfWeek().getValue());
	        if (proceso.getDiasSemana() != null && proceso.getDiasSemana().toLowerCase().contains(diaHoy)) {
	            porDiaSemana=true;
	        }
       }
      
        //3 - CONTROL POR DIA MES
        //Verificar día del mes si es mensual
        if (proceso.getTipoPeriodo().equalsIgnoreCase(SPDConstants.PROCESO_FREC_PERIODO_MESES)) {
            int diaActual = LocalDate.now().getDayOfMonth(); // 1 a 31

            String[] diasMes = ProcesoHelper.obtenerDiasValidosComoArray(proceso.getDiasMes());
            List<Integer> diasPermitidos = Arrays.stream(diasMes)
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
            
            // System.out.println("diasMes String[]      " + Arrays.toString(diasMes));
            // System.out.println("diasMes List<Integer> " + diasPermitidos);
                       		
            /*		
            String diasDefinidos = proceso.getDiasMes(); // Ej: "1,15,30"
            List<Integer> diasPermitidos = Arrays.stream(diasDefinidos.split(","))
                                                 .map(String::trim)
                                                 .map(Integer::parseInt)
                                                 .collect(Collectors.toList());
            */
            if (diasPermitidos.contains(diaActual)) porDiaMes = true;
        }
        // System.out.println("** porHora & porDiaSemana & porDiaMes --> " + porHora +" " + porDiaSemana +" " + porDiaMes);

        return porHora || porDiaSemana  || porDiaMes;
    }

    /** ok
     * Se encarga de controlar si ha de lanzarse o no, según la programación.
     * Por defecto es sí, a no ser que la hora y día no 
     * @param proceso
     * @return
     */
    public boolean estaEnHoraDiaDeLanzarseORI(Proceso proceso) {
        
    	boolean porHora= false;
    	boolean porDiaSemana= false;
    	boolean porDiaMes= false;
    	
        //1 - CONTROL POR HORA
        //para saber si se ha de ejecutar, buscaremos la fecha programada justo anterior a la fecha actual,
    	//para comparar ambas. En caso que los minutos sean menor que el intervalo de minutos 
        //de consulta (y mayor que 0) se lanzará el proceso.
        System.out.println("** LANZADERA --> " + proceso.getLanzadera());
        /*if(proceso.getUltimaEjecucion()!=null && proceso.getUltimaEjecucion().getEstado().equalsIgnoreCase(SPDConstants.PROCESO_EJEC_ESTADO_FINALIZADO) )
        {
        	if(proceso.getUltimaEjecucion().getFechaFinEjecucion()==null 
        			|| proceso.getUltimaEjecucion().getFechaFinEjecucion().
        }
        */
        LocalDateTime ahora = LocalDateTime.now();
        //System.out.println("** ahora --> " + ahora.toString());
        //int cadaCuantosMinutos = cadaCuantosMinutosSeEjecuta(proceso); 
        //LocalDateTime primeraFechaPosterior = fechaProgramadaContigua(proceso, true);  
        LocalDateTime ultimaFechaAnterior = fechaProgramadaContigua(proceso, false);
        //   System.out.println("** ultimaFechaAnterior --> " + ultimaFechaAnterior.toString());
        //  System.out.println("** Duration.between(ultimaFechaAnterior, ahora).toMinutes() --> " + Duration.between(ultimaFechaAnterior, ahora).toMinutes());
        // System.out.println("** SPDConstants.PROCESO_FRECUENCIA_LISTENER/60 --> " + SPDConstants.PROCESO_FRECUENCIA_LISTENER/60);
        if(Duration.between(ultimaFechaAnterior, ahora).toMinutes()>=0 
        		&& Duration.between(ultimaFechaAnterior, ahora).toMinutes()< SPDConstants.PROCESO_FRECUENCIA_LISTENER/60
        		)
        	porHora=true;

      //  System.out.println("** porHora --> " + porHora);

        //2 - CONTROL POR DIA SEMANA
    	// Devuelve el día de la semana como "1" (lunes) a "7" (domingo)
        // 2. Verificar día de la semana si es semanal
       if (proceso.getTipoPeriodo().equalsIgnoreCase(SPDConstants.PROCESO_FREC_PERIODO_SEMANAS)) 
       {
	       	String diaHoy = String.valueOf(LocalDate.now().getDayOfWeek().getValue());
	        if (proceso.getDiasSemana() != null && proceso.getDiasSemana().toLowerCase().contains(diaHoy)) {
	            porDiaSemana=true;
	        }
       }
      
        //3 - CONTROL POR DIA MES
        //Verificar día del mes si es mensual
        if (proceso.getTipoPeriodo().equalsIgnoreCase(SPDConstants.PROCESO_FREC_PERIODO_MESES)) {
            int diaActual = LocalDate.now().getDayOfMonth(); // 1 a 31

            String[] diasMes = ProcesoHelper.obtenerDiasValidosComoArray(proceso.getDiasMes());
            List<Integer> diasPermitidos = Arrays.stream(diasMes)
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
            
            //   System.out.println("diasMes String[]      " + Arrays.toString(diasMes));
            //   System.out.println("diasMes List<Integer> " + diasPermitidos);
                       		
            /*		
            String diasDefinidos = proceso.getDiasMes(); // Ej: "1,15,30"
            List<Integer> diasPermitidos = Arrays.stream(diasDefinidos.split(","))
                                                 .map(String::trim)
                                                 .map(Integer::parseInt)
                                                 .collect(Collectors.toList());
            */
            if (diasPermitidos.contains(diaActual)) porDiaMes = true;
        }
        // System.out.println("** porHora & porDiaSemana & porDiaMes --> " + porHora +" " + porDiaSemana +" " + porDiaMes);

        return porHora || porDiaSemana  || porDiaMes;
    }


	/**
     * Localiza la primera fecha programada para la ejecutó según los saltos de frecuencia que es posterior a la fecha actual
     * @param proceso
     * @return
     */
    private LocalDateTime fechaProgramadaContigua(Proceso proceso, boolean posterior) {
        LocalDateTime ahora = LocalDateTime.now();
        //  System.out.println("**** ahora2 --> " + ahora.toString());

        LocalTime horaProgramada = LocalTime.parse(proceso.getHoraEjecucion()); // asume "HH:mm"
        //  System.out.println("**** horaProgramada --> " + horaProgramada.toString());

        // Punto de partida: hoy a la hora programada
        LocalDateTime inicioHoy = LocalDateTime.of(ahora.toLocalDate(), horaProgramada);
        //  System.out.println("**** inicioHoy --> " + inicioHoy.toString());

        int cadaCuantosMinutos = cadaCuantosMinutosSeEjecuta(proceso);
        //  System.out.println("**** cadaCuantosMinutos --> " + cadaCuantosMinutos);

        // Si ya pasó, empezamos desde ahí e incrementamos
        LocalDateTime candidato = inicioHoy;
        //   System.out.println("**** candidato --> " + candidato);
        while(candidato.isBefore(ahora) && cadaCuantosMinutos>0)
        {
          	candidato=candidato.plusMinutes(cadaCuantosMinutos);
          	//       System.out.println("**** candidato.plusMinutes --> " + candidato);

        }
        if(posterior) // si miramos la primera posterior, añadimos un salto más
        {
           	candidato=candidato.plusMinutes(cadaCuantosMinutos);
        }
        	
        //    System.out.println("**** candidato fin --> " + candidato);
	
		return candidato;
	}

	private int cadaCuantosMinutosSeEjecuta(Proceso proceso) {
		if(proceso ==null) return -1;
		int minutos = -1;
		int frecuenciaPeriodo= proceso.getFrecuenciaPeriodo();
		String tipoPeriodo= proceso.getTipoPeriodo();
		switch (tipoPeriodo) {
		case SPDConstants.PROCESO_FREC_PERIODO_MINUTOS:
			minutos = frecuenciaPeriodo * 1;
			break;
		case SPDConstants.PROCESO_FREC_PERIODO_HORAS:
			minutos = frecuenciaPeriodo * 60;
			break;
		/*En los semanales o mensuales se dejará que la hora y el día marcado devuelvan si se ha de ejecutar o no
		 * case SPDConstants.PROCESO_FREC_PERIODO_DIAS:
			minutos = frecuenciaPeriodo * 60*24;
			break;
		case SPDConstants.PROCESO_FREC_PERIODO_MESES:
			minutos = frecuenciaPeriodo * 60 * 24 * 30;
			break;
		*/	
		default: 
			minutos =  0;
			break;
		};
		return minutos;
	}

	/** ok
     * Se encarga de controlar si ha de lanzarse o no, según la programación.
     * Por defecto es sí, a no ser que la hora y día no 
     * @param proceso
     * @return
     
    public boolean estaEnHoraDeLanzarse(Proceso proceso) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime ahora = LocalDateTime.now();
        
        // 1. Verificar hora de ejecutó
        LocalTime horaProgramada = LocalTime.parse(proceso.getHoraEjecucion()); // asume "HH:mm"
        if (horaProgramada.isAfter(ahora.toLocalTime())) return false; //aún no llega a la hora programada
        else
        {
        	LocalDateTime horaAux = ahora.minusSeconds(new Long(SPDConstants.PROCESO_FRECUENCIA_LISTENER).longValue());
        	LocalTime horaAuxAnterior = LocalTime.parse(proceso.getHoraEjecucion()); // asume "HH:mm"
            if (horaAuxAnterior.isBefore(ahora.toLocalTime())) 
        	{
            	//estamos después de la hora programada, pero si le restamos la frecuencia listener aún no llega, por lo que hay que procesar
        		return true;
        	}
        }
 
        // 2. Verificar día de la semana si es semanal
        if (proceso.getTipoPeriodo().equalsIgnoreCase(SPDConstants.PROCESO_FREC_PERIODO_SEMANAS)) {
        	// Devuelve el día de la semana como "1" (lunes) a "7" (domingo)
        	String diaHoy = String.valueOf(LocalDate.now().getDayOfWeek().getValue());
            
            if (proceso.getDiasSemana() != null && proceso.getDiasSemana().toLowerCase().contains(diaHoy)) {
                return true;
            }
        }

        // 3. Verificar día del mes si es mensual
        if (proceso.getTipoPeriodo().equalsIgnoreCase(SPDConstants.PROCESO_FREC_PERIODO_MESES)) {
            int diaActual = LocalDate.now().getDayOfMonth(); // 1 a 31

            String diasDefinidos = proceso.getDiasMes(); // Ej: "1,15,30"
            List<Integer> diasPermitidos = Arrays.stream(diasDefinidos.split(","))
                                                 .map(String::trim)
                                                 .map(Integer::parseInt)
                                                 .collect(Collectors.toList());

            if (diasPermitidos.contains(diaActual)) return true;
        }
        return false;
    }
  
	/** ok
	 * Método que se encarga de mirar si se ha de ejecutar el proceso por la programación realizada en tiempo, frecuencia
	 * Es necesario que el proceso no tenga una ejecutó en curso (solo se tendrá en cuenta si es null) 
	 * @param proceso
	 * @return true en caso que deba ejecutarse, false en caso contrario o que tenga una ejecucion asignada (no nula)
	 * @throws SQLException
	 */
    public boolean debeEjecutarse(String idUsuario, Proceso proceso) throws SQLException {
    	
    	//si tiene alguna ejecutó no nula, no debe ejecutarse
    	//if(proceso.getUltimaEjecucion()!=null) return false;
    	if(proceso.getEjecucionActiva()!=null) return false;
    	//   System.out.println(HelperSPD.dameFechaHora() + " - evaluarYEjecutarProcesos / debeEjecutarse / SI Activa  ");

    	//miramos si no hay bloqueo de horarios
        if(hayBloqueoHorario(proceso)) return false;
        //  System.out.println(HelperSPD.dameFechaHora() + " - evaluarYEjecutarProcesos / debeEjecutarse / NO  hayBloqueoHorario  ");

     	//miramos si por configuración hay que lanzarse
    	//if(!estaEnHoraDiaDeLanzarse(proceso)) return false;
    	
        if (!estaEntreFechasActivacion(proceso)) return false;
        //  System.out.println(HelperSPD.dameFechaHora() + " - evaluarYEjecutarProcesos / debeEjecutarse / NO  BloqueosFechasActivacion  ");

        if (!esMomentoDeEjecutar(proceso)){
        	//    System.out.println(HelperSPD.dameFechaHora() + " - evaluarYEjecutarProcesos / debeEjecutarse / NO  esMomentoDeEjecutar  ");
            if(!ejecucionesAnterioresCorrectas(proceso)) 
            {
            	//       System.out.println(HelperSPD.dameFechaHora() + " - evaluarYEjecutarProcesos / ejecucionesAnterioresCorrectas / NO  ejecucionesAnterioresCorrectas  ");
            	return true;
            }else
            {
            	//      System.out.println(HelperSPD.dameFechaHora() + " - evaluarYEjecutarProcesos / ejecucionesAnterioresCorrectas / SI  ejecucionesAnterioresCorrectas  ");
                return false;
            }
        }
        //   System.out.println(HelperSPD.dameFechaHora() + " - evaluarYEjecutarProcesos / debeEjecutarse / SI  esMomentoDeEjecutar  ");
        
       

     //	if(comprobar(proceso)) return true;

        return true;
    }
    
    
    /**
     * Método que devuelve true en caso que las anterior ejecutó se haya ejecutado ok o que no haya transcurrido más del tiempo configurado entre una y otra
     * @param proceso
     * @return
     */
    private boolean ejecucionesAnterioresCorrectas(Proceso proceso) {
       	if(proceso==null) return false;
        LocalDateTime ahora = LocalDateTime.now();
        
        boolean tipoEsMinutos = SPDConstants.PROCESO_FREC_PERIODO_MINUTOS.equalsIgnoreCase(proceso.getTipoPeriodo());
       // Si nunca se ha ejecutado
        if (proceso.getUltimaEjecucion() == null) {
        //   System.out.println(HelperSPD.dameFechaHora() + " - evaluarYEjecutarProcesos / debeEjecutarse / esMomentoDeEjecutar  proceso.getUltimaEjecucion() == null " );

            return estaEntreFechasActivacion(proceso) &&
                   (tipoEsMinutos || esEjecutableEnHora(proceso)) &&
                   esEjecutableEnDias(proceso);
        }
 
        LocalDateTime ultimaEjecucion = null;
        if (proceso.getUltimaEjecucion() != null) {
        	ultimaEjecucion  = LocalDateTime.parse(
                proceso.getUltimaEjecucion().getFechaInicioEjecucion(), SPDConstants.FORMAT_DATETIME_24h);
        }
        
        // Calcular fecha base de ejecutó a partir de fechaDesde + horaEjecucion
        LocalDate fechaInicio = HelperSPD.parseFecha(proceso.getFechaDesde()); // formato dd/MM/yyyy
        LocalTime horaEjecucion = HelperSPD.parseHora(proceso.getHoraEjecucion()); // formato HH:mm
        if (fechaInicio == null || horaEjecucion == null) return false;

 
        // Calcular práxima ejecutó teórica desde baseEjecucion hasta ahora
        //anteriorEjecucion = la ejecutó previa a la siguiente teórica
        //proximaEjecucion = la ejecutó siguiente teórica
        LocalDateTime anteriorEjecucion = dameFechaTeorica( proceso, true); 
        LocalDateTime proximaEjecucion =  dameFechaTeorica( proceso, false); 
      
        
        if (ultimaEjecucion == null || (ultimaEjecucion.isBefore(anteriorEjecucion) && anteriorEjecucion.isBefore(ahora))) {
            return false;
        }
        /*if (ultimaEjecucion != null && !(ultimaEjecucion.isBefore(anteriorEjecucion) && anteriorEjecucion.isBefore(ahora))) {
            return false;
        }*/
        //  System.out.println(HelperSPD.dameFechaHora() + " - evaluarYEjecutarProcesos / ultimaEjecucion " +ultimaEjecucion );
        //  System.out.println(HelperSPD.dameFechaHora() + " - evaluarYEjecutarProcesos / anteriorEjecucion " +anteriorEjecucion );
        //  System.out.println(HelperSPD.dameFechaHora() + " - evaluarYEjecutarProcesos / ahora " +ahora );
        // System.out.println(HelperSPD.dameFechaHora() + " - evaluarYEjecutarProcesos / proximaEjecucion " +proximaEjecucion );

        

        return true;
            


    }

    private LocalDateTime dameFechaTeorica(Proceso proceso, boolean anterior) {
       	if(proceso==null) return null;
        LocalDateTime ahora = LocalDateTime.now();
        
         // Calcular fecha base de ejecutó a partir de fechaDesde + horaEjecucion
        LocalDate fechaInicio = HelperSPD.parseFecha(proceso.getFechaDesde()); // formato dd/MM/yyyy
        LocalTime horaEjecucion = HelperSPD.parseHora(proceso.getHoraEjecucion()); // formato HH:mm
        LocalDateTime baseEjecucion = LocalDateTime.of(fechaInicio, horaEjecucion);

        // Calcular práxima ejecutó teórica desde baseEjecucion hasta ahora
        LocalDateTime anteriorEjecucion = baseEjecucion;
        LocalDateTime proximaEjecucion = baseEjecucion;
        while (proximaEjecucion.isBefore(ahora)) {
            anteriorEjecucion = proximaEjecucion;
            switch (proceso.getTipoPeriodo()) {
            case "MINUTOS":
                proximaEjecucion = proximaEjecucion.plusMinutes(proceso.getFrecuenciaPeriodo());
                break;
            case "HORAS":
                proximaEjecucion = proximaEjecucion.plusHours(proceso.getFrecuenciaPeriodo());
                break;
            case "DIAS":
                proximaEjecucion = proximaEjecucion.plusDays(proceso.getFrecuenciaPeriodo());
                break;
            case "SEMANAS":
             	DayOfWeek diaObjetivo = DayOfWeek.of(new Integer(proceso.getDiasSemana()).intValue());
                proximaEjecucion = proximaEjecucion.plusWeeks(proceso.getFrecuenciaPeriodo());
             // Ajusta al día de semana correcto
                proximaEjecucion = proximaEjecucion.with(TemporalAdjusters.nextOrSame(diaObjetivo));
                break;
            case "MESES":
                proximaEjecucion = proximaEjecucion.plusMonths(proceso.getFrecuenciaPeriodo());
                break;
            default:
            	
        }
    }

        
        if(anterior) 
        	return anteriorEjecucion;
    	else 
    		return proximaEjecucion;
            


    }

    

	/** ok
     * ejecutó de un proceso
     * @param proceso
     * @throws SQLException 
     */
    public void ejecutarProceso(String idUsuario, Proceso proceso) throws SQLException {
        System.out.println(HelperSPD.dameFechaHora() + " - Ejecutando: " + idUsuario + " Proceso: " + proceso.getLanzadera());

        if(idUsuario == null || idUsuario.equals(""))
        	idUsuario="AUTO";
        boolean ok = iniciarEjecucionProceso(idUsuario, proceso);


        guardarHistorico(proceso);
    }




    


    private void guardarHistorico(Proceso proceso) {
         // Insertar en tabla de histórico
     }
    
	private boolean esMomentoDeEjecutar(Proceso proceso) {
    	if(proceso==null) return false;
  
        boolean tipoEsMinutos = SPDConstants.PROCESO_FREC_PERIODO_MINUTOS.equalsIgnoreCase(proceso.getTipoPeriodo());

        // Si ya se ejecutó al menos una vez
        if (!estaEntreFechasActivacion(proceso)) 
        {
        	//  System.out.println(HelperSPD.dameFechaHora() + " - evaluarYEjecutarProcesos / debeEjecutarse / NO estaEntreFechasActivacion" );
        	return false;
        }
        if (!tipoEsMinutos &&  !esEjecutableEnHora(proceso)) 
        {
        	//   System.out.println(HelperSPD.dameFechaHora() + " - evaluarYEjecutarProcesos / debeEjecutarse / SI esEjecutableEnHora" );
        	return false;
        }
        if (!esEjecutableEnDias(proceso))
        {
        	//   System.out.println(HelperSPD.dameFechaHora() + " - evaluarYEjecutarProcesos / debeEjecutarse / SI esEjecutableEnDias" );
            return false;
        }
         
        
        return true;

    }

	
	
	/*
	private boolean estamosEnMomentoTeorico(Proceso proceso, LocalDateTime ahora) {
	    try {
	        LocalDate fechaBase = LocalDate.parse(proceso.getFechaDesde(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	        LocalTime horaBase = LocalTime.parse(proceso.getHoraEjecucion(), DateTimeFormatter.ofPattern("HH:mm"));
	        LocalDateTime inicio = LocalDateTime.of(fechaBase, horaBase);

	        LocalDateTime proxima = inicio;
	        while (!proxima.isAfter(ahora)) {
	            switch (proceso.getTipoPeriodo()) {
	                case "MINUTOS": proxima = proxima.plusMinutes(proceso.getFrecuenciaPeriodo()); break;
	                case "HORAS": proxima = proxima.plusHours(proceso.getFrecuenciaPeriodo()); break;
	                case "DIAS": proxima = proxima.plusDays(proceso.getFrecuenciaPeriodo()); break;
	                case "SEMANAS": proxima = proxima.plusWeeks(proceso.getFrecuenciaPeriodo()); break;
	                case "MESES": proxima = proxima.plusMonths(proceso.getFrecuenciaPeriodo()); break;
	            }
	        }

	        LocalDateTime ultimaTeorica = proxima.minus(getChronoUnit(proceso.getTipoPeriodo()).getDuration().multipliedBy(proceso.getFrecuenciaPeriodo()));
	        System.out.println(HelperSPD.dameFechaHora() + " - evaluarYEjecutarProcesos / ultimaTeorica " + ultimaTeorica );
	        System.out.println(HelperSPD.dameFechaHora() + " - evaluarYEjecutarProcesos / ahora.isBefore(ultimaTeorica) " + ahora.isBefore(ultimaTeorica) );
	        System.out.println(HelperSPD.dameFechaHora() + " - evaluarYEjecutarProcesos / Duration.between(ultimaTeorica, ahora).toMinutes() " + Duration.between(ultimaTeorica, ahora).toMinutes() );

	        return !ahora.isBefore(ultimaTeorica) && Duration.between(ultimaTeorica, ahora).toMinutes() <= 5; // margen tolerancia
	    } catch (Exception e) {
	        return false;
	    }
	}
	*/
	private ChronoUnit getChronoUnit(String tipo) {
	    switch (tipo.toUpperCase()) {
	        case "MINUTOS": return ChronoUnit.MINUTES;
	        case "HORAS": return ChronoUnit.HOURS;
	        case "DIAS": return ChronoUnit.DAYS;
	        case "SEMANAS": return ChronoUnit.WEEKS;
	        case "MESES": return ChronoUnit.MONTHS;
	        default: return ChronoUnit.DAYS;
	    }
	}

	private boolean esEjecutableEnHora(Proceso proceso) {
        String horaEjecucionStr = proceso.getHoraEjecucion(); // formato "HH:mm"
       // System.out.println(HelperSPD.dameFechaHora() + " - evaluarYEjecutarProcesos / esEjecutableEnHora / horaEjecucionStr " + horaEjecucionStr );

        if (horaEjecucionStr == null || horaEjecucionStr.trim().isEmpty()) return true;

        try {
            LocalTime horaEjecucion = LocalTime.parse(horaEjecucionStr, DateTimeFormatter.ofPattern("HH:mm"));
            // System.out.println(HelperSPD.dameFechaHora() + " - evaluarYEjecutarProcesos / esEjecutableEnHora / horaEjecucion " + horaEjecucion );
            LocalTime ahora = LocalTime.now();
            //  System.out.println(HelperSPD.dameFechaHora() + " - evaluarYEjecutarProcesos / esEjecutableEnHora / ahora " + ahora );
            //  System.out.println(HelperSPD.dameFechaHora() + " - evaluarYEjecutarProcesos / esEjecutableEnHora / !ahora.isBefore(horaEjecucion) " + !ahora.isBefore(horaEjecucion) );
            return !ahora.isBefore(horaEjecucion); // true si "ahora" ya ha pasado o es la hora
         } catch (DateTimeParseException e) {
            // En caso de error de formato, por seguridad no ejecutamos
            return false;
        }
    }
    private boolean esEjecutableEnDias(Proceso proceso) {
        LocalDate hoy = LocalDate.now();
        boolean result = false;
        if ("SEMANAS".equalsIgnoreCase(proceso.getTipoPeriodo())) {
            String diasSemana = proceso.getDiasSemana(); // valores tipo "1,2,3"
            if (diasSemana == null || diasSemana.isEmpty()) return true;

            int diaSemanaHoy = hoy.getDayOfWeek().getValue(); // 1 (lunes) a 7 (domingo)
            result =  Arrays.stream(diasSemana.split(","))
                         .map(String::trim)
                         .mapToInt(Integer::parseInt)
                         .anyMatch(dia -> dia == diaSemanaHoy);
           
        }
      //  
        if ("MESES".equalsIgnoreCase(proceso.getTipoPeriodo())) {
            String diasMes = proceso.getDiasMes(); // valores tipo "1,15,30"
            if (diasMes == null || diasMes.isEmpty()) return true;

            int diaMesHoy = hoy.getDayOfMonth();
            result =  Arrays.stream(diasMes.split(","))
                         .map(String::trim)
                         .mapToInt(Integer::parseInt)
                         .anyMatch(dia -> dia == diaMesHoy);
        }
        LocalDateTime ultimaEjecucionTeorica=dameFechaTeorica(proceso, true);
        LocalDateTime ultimaEjecucionReal = null;
        if (proceso.getUltimaEjecucion() != null) {
        	ultimaEjecucionReal  = LocalDateTime.parse(
                proceso.getUltimaEjecucion().getFechaInicioEjecucion(), SPDConstants.FORMAT_DATETIME_24h);
        }
        boolean yaProcesado =false;
        if (ultimaEjecucionTeorica == null 
        		|| ultimaEjecucionReal  == null 
        				|| ultimaEjecucionTeorica.isBefore(ultimaEjecucionReal) ) {
        	yaProcesado= true;
        }
        //  System.out.println(HelperSPD.dameFechaHora() + " - ultimaEjecucionTeorica " +ultimaEjecucionTeorica );
        //  System.out.println(HelperSPD.dameFechaHora() + " - ultimaEjecucionReal  " +ultimaEjecucionReal );


        result=result && esEjecutableEnHora(proceso) && !yaProcesado;
        
        // solo se ejecuta si es ejecutable pero el anterior teórico es 
        // Para otros tipos de periodo, no se validan días concretos
        return result ;	//
    }
    private boolean estaEntreFechasActivacion(Proceso proceso) {
        LocalDate hoy = LocalDate.now();

        try {
            if (proceso.getFechaDesde() != null && !proceso.getFechaDesde().isEmpty()) {
                LocalDate desde = LocalDate.parse(proceso.getFechaDesde(), SPDConstants.FORMAT_DATE);
                if (hoy.isBefore(desde)) return false;
            }

            if (proceso.getFechaHasta() != null && !proceso.getFechaHasta().isEmpty()) {
                LocalDate hasta = LocalDate.parse(proceso.getFechaHasta(), SPDConstants.FORMAT_DATE);
                if (hoy.isAfter(hasta)) return false;
            }
        } catch (DateTimeParseException e) {
            System.err.println("Formato de fecha inválido en proceso: " + proceso.getLanzadera());
            return false;
        }

        return true;
    }

	/**
     * Método que mira si hay algún bloqueo por hora, dia o fecha, para poder ejecutar los procesos. Para que se puedan ejecutar siempre han de devolver "false"
     * En caso que exista algún bloqueo devolverá un "true"
     * @param proceso
     * @return
     * @throws SQLException
     */
    public boolean hayBloqueoHorario(Proceso proceso) throws SQLException {
 		ProcesoBloqueoHorarioHelper helper = new ProcesoBloqueoHorarioHelper();
		return helper.hayBloqueoHorario(proceso);
	}

	public boolean iniciarEjecucionProceso(String idUsuario, Proceso proceso) throws SQLException {
       	creaObjetoEjecucion(idUsuario, proceso);

    	return procEjecDAO.iniciarEjecucionProceso(idUsuario, proceso);
    }

	private boolean creaObjetoEjecucion(String idUsuario, Proceso proceso) throws SQLException {
    	ProcesoEjecucion ejec = new ProcesoEjecucion();
    	ProcesoEjecucion ejecAnterior = proceso.getUltimaEjecucion();
    	
    	//ejec.setEstado(SPDConstants.PROCESO_EJEC_ESTADO_EJECUTANDO);
    	//inicializamos en pendiente y al lanzar el procedure se actualizará a ejecutando
    	ejec.setEstado(SPDConstants.PROCESO_EJEC_ESTADO_PENDIENTE);
  		
    	Date dFechaCreacionEjecucion = new Date();
	  	String fechaCreacionEjecucion =  HelperSPD.dameFechaHora();
    	ejec.setFechaCreacionEjecucion(fechaCreacionEjecucion);
    	ejec.setLanzadera(proceso.getLanzadera());
    	int numIntentos=0;
    	//en caso que el proceso ya se haya ejecutado previamente se le suma 1. en caso de bloqueo se reinicializa el contador
    	if(ejecAnterior!=null && !proceso.getActivo().equalsIgnoreCase(SPDConstants.PROCESO_BLOQUEADO)
    			//&& !ejecAnterior.getEstado().equalsIgnoreCase(SPDConstants.PROCESO_CODE_FINALIZADO_OK) )
    			&& !ejecAnterior.getResultado().equalsIgnoreCase(SPDConstants.PROCESO_EJEC_RESULT_OK)) 
    	{
    		numIntentos=ejecAnterior.getNumIntentos()+1;
    	}
    		
    	ejec.setNumIntentos(numIntentos);
    	ejec.setOidProceso(proceso.getOidProceso());
    	ejec.setUsuarioEjecucion(idUsuario);
    	ejec.setVersion(proceso.getVersion());
	  	
    	//int erroresSeguidos = contarErroresDesdeUltimoExito(proceso);
    	//ejec.setNumIntentos(erroresSeguidos+1);
	  	
    	//proceso.setUltimaEjecucion(ejec);
    	proceso.setEjecucionActiva(ejec);
    	boolean crearEjec = procEjecDAO.nuevo(idUsuario, proceso);
    	
    	
		return crearEjec;
	}






}