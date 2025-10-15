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
        System.out.println("Aplicaci�n iniciada. Iniciando planificaci�n de procesos...");

        // Crear el planificador (temporizador)
        planificador = Executors.newScheduledThreadPool(1);

        // Ejecutar cada X segundos
        planificador.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("Revisi�n peri�dica de procesos...");
                //ProcesoHelper.controlEjecucionesEnCurso();
                try {
					evaluarYEjecutarProcesos();
				} catch (SQLException e) {
					// TODO Bloque catch generado autom�ticamente
					e.printStackTrace();
				}
                //ejecutarProcesosPendientes();
            }
        }, 0, 600000, TimeUnit.SECONDS); // Ajusta el intervalo seg�n sea necesario
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Aplicaci�n detenida. Deteniendo planificaci�n de procesos...");
        if (planificador != null) {
            planificador.shutdown();
        }
    }

    /** ok
     * M�todo encargado de realizar los controles previos y ejecutar en su caso el proceso que sale del bucle
     * @throws SQLException
     */
    private void evaluarYEjecutarProcesos() throws SQLException {
        List<Proceso> listaProcesos = obtenerProcesosActivos();

        for (Proceso proceso : listaProcesos) {
        	//controles(proceso);
        	if(proceso==null || proceso.getUltimaEjecucion()==null)
    			return;

        	// 1. Comprobar si hay ejecuci�n activa y excede duraci�n
        	helper.controlTiempoExcedido(proceso);
    		// 2. Comprobamos errores  seguidos o intentos de ejecuciones desde el �ltimo ok
        	helper.controlMaxIntentos(proceso);
     
        	// 3. Comprobaci�n de si debe ejecutarse seg�n frecuencia, hora y d�a 
             if (helper.debeEjecutarse(proceso)) {
                ejecutar(proceso);
            }
        }
    }


    /** ok
     * Devuelve una lista de procesos activos y que se procesan de forma autom�tica
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
	 * M�todo que controla si hemos alcanzado el m�ximo de intentos permitidos
	 * @param proceso
	 * @return true si ha llegado al m�ximo, false en caso contrario (por defecto) 
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
     * M�todo que controla si hemos alcanzado el m�ximo de tiempo permitido para su ejecuci�n
     * @param proceso
     * @return true si ha llegado al m�ximo, false en caso contrario (por defecto) 
     */
    /*
    private boolean excedeDuracionMaxima(Proceso proceso) {
    	
		if(proceso==null || proceso.getUltimaEjecucion()==null)
			return false;
		String fechaFin =proceso.getUltimaEjecucion().getFechaFinEjecucion(); 
		//si hay fecha de finalizaci�n el proceso ya ha acabado
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
		// TODO Esbozo de m�todo generado autom�ticamente
		return false;
	}
    */
    
    /** ok
     * Ejecuci�n de un proceso
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
        // Simular ejecuci�n del proceso o invocar la l�gica real
        return true;
    }
/*
    private void actualizarEstado(Proceso proceso, String nuevoEstado) throws SQLException {
    	helper.actualizarEstadoEjecucion( proceso,  nuevoEstado);
    }
*/
    private void guardarHistorico(Proceso proceso) {
        // Insertar en tabla de hist�rico
    }
}

    // M�todos auxiliares (pueden ser similares a los que describimos antes, como `obtenerEstadoProceso`, `haFalladoExcesivo`, etc.)
