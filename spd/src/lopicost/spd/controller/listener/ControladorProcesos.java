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
 				try {
					evaluarYEjecutarProcesos();
				} catch (SQLException e) {
					// TODO Bloque catch generado autom�ticamente
					e.printStackTrace();
				}

                //ejecutarProcesosPendientes();
            }
        }, 0, 
        		//SPDConstants.PROCESO_FRECUENCIA_LISTENER+6000000
        		SPDConstants.PROCESO_FRECUENCIA_LISTENER
        		, TimeUnit.SECONDS); // Ajusta el intervalo seg�n sea necesario
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
         	if(proceso==null)
    			return;
        	evaluarYEjecutarProcesos("AUTO",  proceso);

        }
    }

    public boolean evaluarYEjecutarProcesos(String idUsuario, Proceso proceso) throws SQLException {
        boolean result = false;	
        boolean automatico = true; 
        if(idUsuario!=null && !idUsuario.equalsIgnoreCase("AUTO")) automatico=false;
        	
    	if(proceso==null) return result;

        // 1. Comprobar si hay ejecuci�n activa y excede duraci�n
        helper.controlTiempoExcedido(proceso);
    	// 2. Comprobamos errores  seguidos o intentos de ejecuciones desde el �ltimo ok
        helper.controlMaxIntentos(proceso);
        // 3. (limpieza) Control de otros procesos que no se han cerrado ok
        helper.controlProcesosAnteriores(proceso);
         // 3. Comprobaci�n de si debe ejecutarse seg�n frecuencia, hora y d�a 
        result = helper.debeEjecutarse(idUsuario, proceso);
        if (result || !automatico) 
        {
        	//4. Si llegamos aqu� es que no hay otra ejecuci�n lanzada del proceso (�ltima ejecuci�n es null) 
            helper.ejecutarProceso(idUsuario, proceso);
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

}

