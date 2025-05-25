package lopicost.spd.struts.helper;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime; 
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.stream.Collectors;

import lopicost.spd.model.Proceso;
import lopicost.spd.model.ProcesoEjecucion;
import lopicost.spd.persistence.ProcesoDAO;
import lopicost.spd.persistence.ProcesoEjecucionDAO;
import lopicost.spd.utils.SPDConstants;

public class ControladorProcesosHelper {
	private final ProcesoDAO procDAO = new ProcesoDAO();
	private final ProcesoEjecucionDAO procEjecDAO = new ProcesoEjecucionDAO();
    private  final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
    private  final SimpleDateFormat DATE_FORMAT_TIME = new SimpleDateFormat("dd/MM/yyyy HH:MM:SS");

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
     * M�todo que se encarga de actualizar la ejecuci�n en caso de detectar tiempo excedido
     * @param ejec
     * @throws SQLException 
     */
    public boolean marcarComoExcedido(ProcesoEjecucion ejec) throws SQLException {
        
       	ejec.setEstado(SPDConstants.PROCESO_EJEC_ESTADO_FINALIZADO);
       	ejec.setResultado(SPDConstants.PROCESO_EJEC_RESULT_ERROR);
        ejec.setFechaFinEjecucion(getAhora());
        ejec.setDuracionSegundos(calcularDuracion(ejec.getFechaInicioEjecucion()));
        ejec.setMensaje("Super� tiempo m�ximo");
        ejec.setCodigoResultado(SPDConstants.PROCESO_CODE_ERROR_TIEMPO_EXCEDIDO);
        ejec.setTipoError("Timeout");
        return procEjecDAO.actualizarEjecucion(ejec);
    }
    
    /** ok
     * Auxiliar que devuelve en segundos transcurridos desde la fecha de inicio de la ejecuci�n del proceso
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
	 * M�todo encargado de contar los errores de un mismo proceso, a partir de la fecha del �ltimo proceso OK  
     * @param proceso
     * @return
     * @throws SQLException
     */
	public int contarErroresDesdeUltimoExito(Proceso proceso) throws SQLException {
		return procEjecDAO.contarErroresDesdeUltimoExito(proceso);
	}

	/** ok
	 * M�todo que se encarga de actualizar el estado de un proceso seg�n el nuevoEstado pasado por par�metro
	 * @param proceso
	 * @param nuevoEstado
	 * @throws SQLException
	 */
	public void actualizarEstadoProceso(Proceso proceso, String nuevoEstado) throws SQLException {
		procDAO.actualizarEstadoProceso(proceso, nuevoEstado);
		
	}
	

	/**
	 * M�todo que se encarga de actualizar el estado de una ejecuci�n de un proceso seg�n el nuevoEstado pasado por par�metro
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
	 * M�todo encargado de realizar un control de la ejecuci�n del proceso que se est� ejecutando, para controlar si el tiempo  
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
                marcarComoExcedido(ejec);
                //proceso.setUltimaEjecucion(null);  //vaciamos la ejecuci�n del proceso para que se pueda volver a lanzar (si toca)
                proceso.setEjecucionActiva(null);  //vaciamos la ejecuci�n del proceso para que se pueda volver a lanzar (si toca)
         }
	}
	
	/** ok
	 * M�todo encargado de mirar si se ha llegado a los m�ximos intentos de un mismo proceso, a partir de la fecha del �ltimo proceso OK  
	 * @param proceso
	 * @throws SQLException
	 */
	public  void controlMaxIntentos(Proceso proceso) throws SQLException {
    	//ProcesoEjecucion ejec = proceso.getUltimaEjecucion();
		ProcesoEjecucion ejec = proceso.getEjecucionActiva();
       if (ejec!=null)
        {
            int erroresSeguidos = contarErroresDesdeUltimoExito(proceso);
            if (erroresSeguidos >= proceso.getMaxReintentos()) {
                //proceso.setActivo(SPDConstants.PROCESO_BLOQUEADO);
                actualizarEstadoProceso(proceso, SPDConstants.PROCESO_BLOQUEADO);
                //proceso.setUltimaEjecucion(null);
                proceso.setEjecucionActiva(null); 
            }
        }
	}

	/**
	 * M�todo que se encarga de limpiar procesos anteriores al �ltimo y que no han quedado bien cerrados
	 * Precondici�n - En este punto, si existe un proceso que se est� ejecutando que est� dentro de los l�mites de intentos y segundos, no aparecer� en el listado
	 * 				  porque la consulta lo excluir�. En caso que no haya ning�n proceso, se descartaran los anteriores, porque si llega nulo es porque ya no est� activo
	 * 				  seg�n los controles del m�todo de donde proviene.  
	 * @param proceso
	 * @throws SQLException 
	 */
	public void controlProcesosAnteriores(Proceso proceso) throws SQLException {
		//List<ProcesoEjecucion> list = procEjecDAO.obtenerEjecucionesAnteriores(proceso);
		procEjecDAO.limpiarOtrasEjecucionesNoCerradas(proceso);
	}


	
	/** ok
	 * Este m�todo devuelve true en caso que la duraci�n en segundos desde la fecha de inicio de la ejecuci�n supere el valor m�ximo definido para este proceso
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
            // Aqu� puedes loguear o manejar el error seg�n tu necesidad
            return false; // o true si prefieres ser conservador
        }
    }

	/**
	 * Encargado de descartar los �ltimos procesos con estado finalizado o error 
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
     * Se encarga de controlar si ha de lanzarse o no, seg�n la programaci�n.
     * Por defecto es s�, a no ser que la hora y d�a no 
     * @param proceso
     * @return
     */
    public boolean estaEnHoraDiaDeLanzarse(Proceso proceso) {
        
    	boolean porHora= false;
    	boolean porDiaSemana= false;
    	boolean porDiaMes= false;
    	
        //1 - CONTROL POR HORA
        //para saber si se ha de ejecutar, buscaremos la fecha programada justo anterior a la fecha actual, para comparar ambas. En caso que los minutos sean menor 
        //que el intervalo de minutos de consulta (y mayor que 0) se lanzar� el proceso.
        LocalDateTime ahora = LocalDateTime.now();
        //int cadaCuantosMinutos = cadaCuantosMinutosSeEjecuta(proceso); 
        //LocalDateTime primeraFechaPosterior = fechaProgramadaContigua(proceso, true);  
        LocalDateTime ultimaFechaAnterior = fechaProgramadaContigua(proceso, false);
        if(Duration.between(ultimaFechaAnterior, ahora).toMinutes()>=0 
        		&& Duration.between(ultimaFechaAnterior, ahora).toMinutes()< SPDConstants.PROCESO_FRECUENCIA_LISTENER/60
        		)
        	porHora=true;

        //2 - CONTROL POR DIA SEMANA
    	// Devuelve el d�a de la semana como "1" (lunes) a "7" (domingo)
        // 2. Verificar d�a de la semana si es semanal
       if (proceso.getTipoPeriodo().equalsIgnoreCase(SPDConstants.PROCESO_FREC_PERIODO_SEMANAS)) 
       {
	       	String diaHoy = String.valueOf(LocalDate.now().getDayOfWeek().getValue());
	        if (proceso.getDiasSemana() != null && proceso.getDiasSemana().toLowerCase().contains(diaHoy)) {
	            porDiaSemana=true;
	        }
       }
      
        //3 - CONTROL POR DIA MES
        //Verificar d�a del mes si es mensual
        if (proceso.getTipoPeriodo().equalsIgnoreCase(SPDConstants.PROCESO_FREC_PERIODO_MESES)) {
            int diaActual = LocalDate.now().getDayOfMonth(); // 1 a 31

            String[] diasMes = ProcesoHelper.obtenerDiasValidosComoArray(proceso.getDiasMes());
            List<Integer> diasPermitidos = Arrays.stream(diasMes)
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
            
            System.out.println("diasMes String[]      " + Arrays.toString(diasMes));
            System.out.println("diasMes List<Integer> " + diasPermitidos);
                       		
            /*		
            String diasDefinidos = proceso.getDiasMes(); // Ej: "1,15,30"
            List<Integer> diasPermitidos = Arrays.stream(diasDefinidos.split(","))
                                                 .map(String::trim)
                                                 .map(Integer::parseInt)
                                                 .collect(Collectors.toList());
            */
            if (diasPermitidos.contains(diaActual)) porDiaMes = true;
        }
        
        return porHora & porDiaSemana & porDiaMes;
    }



	/**
     * Localiza la primera fecha programada para la ejecuci�n seg�n los saltos de frecuencia que es posterior a la fecha actual
     * @param proceso
     * @return
     */
    private LocalDateTime fechaProgramadaContigua(Proceso proceso, boolean posterior) {
        LocalDateTime ahora = LocalDateTime.now();
        LocalTime horaProgramada = LocalTime.parse(proceso.getHoraEjecucion()); // asume "HH:mm"

        // Punto de partida: hoy a la hora programada
        LocalDateTime inicioHoy = LocalDateTime.of(ahora.toLocalDate(), horaProgramada);
        int cadaCuantosMinutos = cadaCuantosMinutosSeEjecuta(proceso); 
        // Si ya pas�, empezamos desde ah� e incrementamos
        LocalDateTime candidato = inicioHoy;
        while(candidato.isBefore(ahora) && cadaCuantosMinutos>0)
        {
          	candidato=candidato.plusMinutes(cadaCuantosMinutos);
        }
        if(posterior) // si miramos la primera posterior, a�adimos un salto m�s
        {
           	candidato=candidato.plusMinutes(cadaCuantosMinutos);
        }
        	
        	
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
		/*En los semanales o mensuales se dejar� que la hora y el d�a marcado devuelvan si se ha de ejecutar o no
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
     * Se encarga de controlar si ha de lanzarse o no, seg�n la programaci�n.
     * Por defecto es s�, a no ser que la hora y d�a no 
     * @param proceso
     * @return
     
    public boolean estaEnHoraDeLanzarse(Proceso proceso) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime ahora = LocalDateTime.now();
        
        // 1. Verificar hora de ejecuci�n
        LocalTime horaProgramada = LocalTime.parse(proceso.getHoraEjecucion()); // asume "HH:mm"
        if (horaProgramada.isAfter(ahora.toLocalTime())) return false; //a�n no llega a la hora programada
        else
        {
        	LocalDateTime horaAux = ahora.minusSeconds(new Long(SPDConstants.PROCESO_FRECUENCIA_LISTENER).longValue());
        	LocalTime horaAuxAnterior = LocalTime.parse(proceso.getHoraEjecucion()); // asume "HH:mm"
            if (horaAuxAnterior.isBefore(ahora.toLocalTime())) 
        	{
            	//estamos despu�s de la hora programada, pero si le restamos la frecuencia listener a�n no llega, por lo que hay que procesar
        		return true;
        	}
        }
 
        // 2. Verificar d�a de la semana si es semanal
        if (proceso.getTipoPeriodo().equalsIgnoreCase(SPDConstants.PROCESO_FREC_PERIODO_SEMANAS)) {
        	// Devuelve el d�a de la semana como "1" (lunes) a "7" (domingo)
        	String diaHoy = String.valueOf(LocalDate.now().getDayOfWeek().getValue());
            
            if (proceso.getDiasSemana() != null && proceso.getDiasSemana().toLowerCase().contains(diaHoy)) {
                return true;
            }
        }

        // 3. Verificar d�a del mes si es mensual
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
	 * M�todo que se encarga de mirar si se ha de ejecutar el proceso por la programaci�n realizada en tiempo, frecuencia
	 * Es necesario que el proceso no tenga una ejecuci�n en curso (solo se tendr� en cuenta si es null) 
	 * @param proceso
	 * @return true en caso que deba ejecutarse, false en caso contrario o que tenga una ejecucion asignada (no nula)
	 * @throws SQLException
	 */
    public boolean debeEjecutarse(Proceso proceso) throws SQLException {
    	
    	//si tiene alguna ejecuci�n no nula, no debe ejecutarse
    	//if(proceso.getUltimaEjecucion()!=null) return false;
    	if(proceso.getEjecucionActiva()!=null) return false;

    	//miramos si no hay restricci�n de horarios
        if(!hayRestriccionHorario(proceso)) return true;

    	
    	//miramos si por configuraci�n hay que lanzarse
    	if(estaEnHoraDiaDeLanzarse(proceso)) return true;

        return false;
    }
    
    /**
     * M�todo que mira si hay alguna restricci�n por hora, dia o fecha, para poder ejecutar los procesos. Para que se puedan ejecutar siempre han de devolver "false"
     * En caso que exista alguna restricci�n devolver� un "true"
     * @param proceso
     * @return
     * @throws SQLException
     */
    public boolean hayRestriccionHorario(Proceso proceso) throws SQLException {
 		ProcesoRestriccionesHelper helper = new ProcesoRestriccionesHelper();
		return helper.hayRestriccionHorario(proceso);
	}

	public boolean iniciarEjecucionProceso(String idUsuario, Proceso proceso) throws SQLException {
       	creaObjetoEjecucion(idUsuario, proceso);

    	return procEjecDAO.iniciarEjecucionProceso(idUsuario, proceso);
    }

	private boolean creaObjetoEjecucion(String idUsuario, Proceso proceso) throws SQLException {
    	ProcesoEjecucion ejec = new ProcesoEjecucion();
    	//ejec.setEstado(SPDConstants.PROCESO_EJEC_ESTADO_EJECUTANDO);
    	//inicializamos en pendiente y al lanzar el procedure se actualizar� a ejecutando
    	ejec.setEstado(SPDConstants.PROCESO_EJEC_ESTADO_PENDIENTE);
  		
    	Date dFechaCreacionEjecucion = new Date();
	  	String fechaCreacionEjecucion =  DATE_FORMAT_TIME.format(dFechaCreacionEjecucion);
    	ejec.setFechaCreacionEjecucion(fechaCreacionEjecucion);
    	ejec.setLanzadera(proceso.getLanzadera());
    	ejec.setNumIntentos(1);
    	ejec.setOidProceso(proceso.getOidProceso());
    	ejec.setUsuarioEjecucion(idUsuario);
    	ejec.setVersion(proceso.getVersion());
	  	
    	int erroresSeguidos = contarErroresDesdeUltimoExito(proceso);
    	ejec.setNumIntentos(erroresSeguidos+1);
	  	
    	//proceso.setUltimaEjecucion(ejec);
    	proceso.setEjecucionActiva(ejec);
    	boolean crearEjec = procEjecDAO.nuevo(idUsuario, proceso);
    	
    	
		return crearEjec;
	}






}