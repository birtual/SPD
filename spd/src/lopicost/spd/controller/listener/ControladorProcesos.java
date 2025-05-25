package lopicost.spd.controller.listener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import lopicost.spd.model.Proceso;
import lopicost.spd.struts.action.GenericAction;
import lopicost.spd.struts.helper.ControladorProcesosHelper;
import lopicost.spd.utils.SPDConstants;

public class ControladorProcesos extends GenericAction implements ServletContextListener {

    private ScheduledExecutorService planificador;
    private SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
    private final ControladorProcesosHelper helper = new ControladorProcesosHelper();

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
/*				try {
					evaluarYEjecutarProcesos();
				} catch (SQLException e) {
					// TODO Bloque catch generado autom�ticamente
					e.printStackTrace();
				}
*/
                //ejecutarProcesosPendientes();
            }
        }, 0, SPDConstants.PROCESO_FRECUENCIA_LISTENER+600000, TimeUnit.SECONDS); // Ajusta el intervalo seg�n sea necesario
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
        	//if(proceso==null || proceso.getUltimaEjecucion()==null)
        	if(proceso==null)
    			return;
        	evaluarYEjecutarProcesos("AUTO",  proceso);

        }
    }

    public boolean evaluarYEjecutarProcesos(String idUsuario, Proceso proceso) throws SQLException {
        boolean result = false;	
    	if(proceso==null) return result;

        // 0. Control estados (Creo que no hace falta porque ahora hay un campo ejecucionActiva que indica si hay una en curso.
        //helper.controlEstadoUltimoProceso(proceso);
        // 1. Comprobar si hay ejecuci�n activa y excede duraci�n
        helper.controlTiempoExcedido(proceso);
    	// 2. Comprobamos errores  seguidos o intentos de ejecuciones desde el �ltimo ok
        helper.controlMaxIntentos(proceso);
        // 3. (limpieza) Control de otros procesos que no se han cerrado ok
        helper.controlProcesosAnteriores(proceso);
        result = helper.debeEjecutarse(proceso);
        // 3. Comprobaci�n de si debe ejecutarse seg�n frecuencia, hora y d�a 
        //if (result) 
        {
        	//4. Si llegamos aqu� es que no hay otra ejecuci�n lanzada del proceso (�ltima ejecuci�n es null) 
            ejecutarProceso(idUsuario, proceso);
        }
       return result;
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



    /** ok
     * Ejecuci�n de un proceso
     * @param proceso
     * @throws SQLException 
     */
    private void ejecutarProceso(String idUsuario, Proceso proceso) throws SQLException {
        System.out.println("Ejecutando: " + proceso.getLanzadera());
        
        //helper.actualizarEstadoProceso(proceso,  SPDConstants.PROCESO_EJEC_PENDIENTE);
        //helper.actualizarEstadoEjecucion(proceso,  SPDConstants.PROCESO_EJEC_ESTADO_PENDIENTE); //no tiene sentido porque es null

       // if(proceso.getTipoEjecucion().equalsIgnoreCase(SPDConstants.PROCESO_TIPOEJEC_AUTO))
        if(idUsuario == null || idUsuario.equals(""))
        	idUsuario="AUTO";
        boolean ok = helper.iniciarEjecucionProceso(idUsuario, proceso);

 /* lO DEBER�A ACTUALIZAR EL PROCEDURE
  *        if (ok) {
        	helper.actualizarEstadoEjecucion(proceso,  SPDConstants.PROCESO_EJEC_ESTADO_FINALIZADO);
        	
        } else {
        	helper.actualizarEstadoEjecucion(proceso,  SPDConstants.PROCESO_EJEC_ERROR);
        }
*/
        guardarHistorico(proceso);
    }


   private void guardarHistorico(Proceso proceso) {
        // Insertar en tabla de hist�rico
    }
}

