package lopicost.spd.controller.listener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import lopicost.spd.model.Proceso;
import lopicost.spd.model.ProcesoEjecucion;
import lopicost.spd.struts.action.GenericAction;
import lopicost.spd.struts.helper.ProcesoHelper;
import lopicost.spd.utils.DateUtilities;
import lopicost.spd.utils.SPDConstants;

public class GestorProcesos3 extends GenericAction implements ServletContextListener {

    private ScheduledExecutorService planificador;
    private SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
    private final ProcesoHelper helper = new ProcesoHelper();

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Aplicación iniciada. Iniciando planificación de procesos...");

        // Crear el planificador (temporizador)
        planificador = Executors.newScheduledThreadPool(1);

        // Ejecutar cada X segundos
        planificador.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("Revisión periódica de procesos...");
                //ProcesoHelper.controlEjecucionesEnCurso();
                try {
					evaluarYEjecutarProcesos();
				} catch (SQLException e) {
					// TODO Bloque catch generado automáticamente
					e.printStackTrace();
				}
                //ejecutarProcesosPendientes();
            }
        }, 0, 600000, TimeUnit.SECONDS); // Ajusta el intervalo según sea necesario
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Aplicación detenida. Deteniendo planificación de procesos...");
        if (planificador != null) {
            planificador.shutdown();
        }
    }

    /** ok
     * Método encargado de realizar los controles previos y ejecutar en su caso el proceso que sale del bucle
     * @throws SQLException
     */
    private void evaluarYEjecutarProcesos() throws SQLException {
        List<Proceso> listaProcesos = obtenerProcesosActivos();

        for (Proceso proceso : listaProcesos) {
        	//controles(proceso);
        	if(proceso==null || proceso.getUltimaEjecucion()==null)
    			return;

        	// 1. Comprobar si hay ejecución activa y excede duración
        	helper.controlTiempoExcedido(proceso);
    		// 2. Comprobamos errores  seguidos o intentos de ejecuciones desde el último ok
        	helper.controlMaxIntentos(proceso);
     
        	// 3. Comprobación de si debe ejecutarse según frecuencia, hora y día 
             if (helper.debeEjecutarse(proceso)) {
                ejecutar(proceso);
            }
        }
    }


    /** ok
     * Devuelve una lista de procesos activos y que se procesan de forma automática
     * @return
     * @throws SQLException
     */
	private List<Proceso> obtenerProcesosActivos() throws SQLException {
        // obtener procesos activos y programados para ejecutarse
    	List<Proceso> result = helper.listaProcesosActivos(getIdUsuario(), true);
        return result;
    }



/*
	private boolean estaEnEjecucion(Proceso proceso) {

		if(proceso==null || proceso.getUltimaEjecucion()==null)
			return false;
		
		String estadoEjecucion = proceso.getUltimaEjecucion().getEstado();
		if(estadoEjecucion!=null || estadoEjecucion.equalsIgnoreCase(SPDConstants.PROCESO_EJEC_EJECUTANDO))
			return true;

		return false;
    }
*/
	/**
	 * Método que controla si hemos alcanzado el máximo de intentos permitidos
	 * @param proceso
	 * @return true si ha llegado al máximo, false en caso contrario (por defecto) 
	 */
/*    private boolean superaReintentos(Proceso proceso) {
		if(proceso==null || proceso.getUltimaEjecucion()==null)
			return false;
		int maxIntentosPermitidos=proceso.getMaxReintentos();
		int intentosRealizados=proceso.getUltimaEjecucion().getNumIntentos();
		if(maxIntentosPermitidos>=intentosRealizados)
			return true;
        
		return false;
    }
*/
    /**
     * Método que controla si hemos alcanzado el máximo de tiempo permitido para su ejecución
     * @param proceso
     * @return true si ha llegado al máximo, false en caso contrario (por defecto) 
     */
    /*
    private boolean excedeDuracionMaxima(Proceso proceso) {
    	
		if(proceso==null || proceso.getUltimaEjecucion()==null)
			return false;
		String fechaFin =proceso.getUltimaEjecucion().getFechaFinEjecucion(); 
		//si hay fecha de finalización el proceso ya ha acabado
		if(DateUtilities.isDateValid(fechaFin, DATE_FORMAT.toString()))
			return false;
		
		
		int maxDuracionSegundos=proceso.getMaxDuracionSegundos();
		Date fechaInicio = DateUtilities.getDate(proceso.getUltimaEjecucion().getFechaInicioEjecucion(), DATE_FORMAT.toString());
		Date fechaActual = new Date();
		
		long duracionSegundos = DateUtilities.getLengthInSeconds(fechaInicio, fechaActual);
		if(duracionSegundos> new Long(maxDuracionSegundos).longValue())
			return true;
        
		return false;
    }

*/
    
    /* 
    private boolean estaEnHoraDeLanzarse(Proceso proceso) {
		// TODO Esbozo de método generado automáticamente
		return false;
	}
    */
    
    /** ok
     * Ejecución de un proceso
     * @param proceso
     */
    private void ejecutar(Proceso proceso) {
        System.out.println("Ejecutando: " + proceso.getNombre());
        actualizarEstado(proceso, "EJECUTANDO");

        boolean ok = ejecutarLanzadera(proceso);

        if (ok) {
            actualizarEstado(proceso, "FINALIZADO");
        } else {
            actualizarEstado(proceso, "ERROR");
        }

        guardarHistorico(proceso);
    }

    private boolean ejecutarLanzadera(Proceso proceso) {
        // Simular ejecución del proceso o invocar la lógica real
        return true;
    }
/*
    private void actualizarEstado(Proceso proceso, String nuevoEstado) throws SQLException {
    	helper.actualizarEstadoEjecucion( proceso,  nuevoEstado);
    }
*/
    private void guardarHistorico(Proceso proceso) {
        // Insertar en tabla de histórico
    }
}

    // Métodos auxiliares (pueden ser similares a los que describimos antes, como `obtenerEstadoProceso`, `haFalladoExcesivo`, etc.)
