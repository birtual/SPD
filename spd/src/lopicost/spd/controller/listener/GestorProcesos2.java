package lopicost.spd.controller.listener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import lopicost.spd.model.Proceso;
import lopicost.spd.persistence.ProcesoDAO;
import lopicost.spd.struts.action.GenericAction;
import lopicost.spd.struts.helper.ProcesoHelper;
import lopicost.spd.utils.SPDConstants;

public class GestorProcesos2 extends GenericAction implements ServletContextListener {

    private ScheduledExecutorService planificador;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Aplicación iniciada. Iniciando planificación de procesos...");

        // Crear el planificador (temporizador)
        planificador = Executors.newScheduledThreadPool(1);

        // Ejecutar cada 60 segundos
        planificador.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("Revisión periódica de procesos...");

                ejecutarProcesosPendientes();
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

    private void ejecutarProcesosPendientes() {
        List<Proceso> listaProcesos = obtenerProcesosActivos();

        for (Proceso proceso : listaProcesos) {
            if (debeEjecutarse(proceso)) {
                ejecutar(proceso);
            }
        }
    }

    private List<Proceso> obtenerProcesosActivos() {
        // obtener procesos activos y programados para ejecutarse
    	List<Proceso> result = ProcesoHelper.listaProcesosActivos(getIdUsuario(), true);
        return result;
    }

    private boolean debeEjecutarse(Proceso proceso) {
        if (estaEnEjecucion(proceso)) return false;
        if (superaReintentos(proceso)) return false;
        if (excedeDuracionMaxima(proceso)) {
            actualizarEstado(proceso, "ERROR");
            return false;
        }
        if(estaEnHoraDeLanzarse(proceso)) return false;

        return true;
    }


	private boolean estaEnEjecucion(Proceso proceso) {
		String ultimoEstadoEjecucion = ProcesoHelper.obtenerUltimaEjecucion(getIdUsuario(), proceso);
        return SPDConstants.PROCESO_EJEC_EJECUTANDO.equals(ultimoEstadoEjecucion);
    }

    private boolean superaReintentos(Proceso proceso) {
        return proceso.getReintentosActuales() >= proceso.getMaxReintentos();
    }

    private boolean excedeDuracionMaxima(Proceso proceso) {
        return proceso.getDuracionActual() > proceso.getMaxDuracionSegundos();
    }

    private boolean estaEnHoraDeLanzarse(Proceso proceso) {
		// TODO Esbozo de método generado automáticamente
		return false;
	}
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

    private void actualizarEstado(Proceso proceso, String nuevoEstado) {
        // Actualiza en base de datos el estado del proceso
    }

    private void guardarHistorico(Proceso proceso) {
        // Insertar en tabla de histórico
    }
}

    // Métodos auxiliares (pueden ser similares a los que describimos antes, como `obtenerEstadoProceso`, `haFalladoExcesivo`, etc.)
}