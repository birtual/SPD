package lopicost.spd.struts.form;

import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import lopicost.spd.model.Proceso;

public class ProcesosForm extends GenericForm {
    private String nombreProceso;
    private String nombreOriginal;
    private String lanzadera;
    private String descripcion;
    private String estado;
    private String activo;
    private String tipoEjecucion;
    private int frecuenciaPeriodo;
    private String tipoPeriodo ;
    private String horaEjecucion;
    private int maxReintentos = 3;
    private int oidProceso;
    private int oidProcesoEjecucion;
    private String accion;
    private List procesos;
    private Proceso proceso;
    
    private String fechaCreacion;               // Fecha de creación del proceso
    private String parametros;                  // Parámetros del procedimiento
    private String diasSemana;                  // Días de la semana para ejecución programada
    private String diasMes;                 	// Días del mes (para procesos mensuales o días de mes puntuales )
    private String[] diasSemanaArray;
    
    private int maxDuracionSegundos=120; 		// Máxima duración de la ejecución en segundos
    private String fechaDesde;               	// Fecha de activación del proceso
    private String fechaHasta;                  // Fecha de desactivación del proceso
    private String fechaInicioEjecucion;        // Fecha de inicio de la ejecución (si está en ejecución)
    private String fechaFinEjecucion;           // Fecha de fin de la ejecución (si está finalizado)
    private int duracionSegundos=0; 			// Duración de la última ejecución en segundos
    private String resultado;                   // Resultado de la ejecución
    private String usuarioEjecucion;        	// Usuario que ejecutó el proceso 
    private String mensaje;                     // Mensaje adicional de la ejecución
    private String tipoError;                   // Tipo de error si existió un fallo
    private String codigoResultado;          	// Código de resultado (0 para éxito, 1 para error, etc.)
    private String error;                       // Error ocurrido durante la ejecución
    private String apartado;                 	// Nombre del apartado o entidad para la que se ha creado, a efectos de agruparlo en la vista
    private int orden;	 		                 // Orden que ocupa en el apartado
    private int prioridad;						// 1 'Alta', 2 'Media', 3 'Baja',

    
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();

        try {
            int valor = getMaxReintentos();
            if (valor < 0) {
                errors.add("maxReintentos", new ActionMessage("error.maxReintentos.positivo"));
            }
        } catch (NumberFormatException e) {
            errors.add("maxReintentos", new ActionMessage("error.maxReintentos.numero"));
        }

        return errors;
    }
    
   
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
    	
    //	String action = request.getParameter("action");
    	String action = request.getParameter("ACTIONTODO");

        if ("nuevo".equalsIgnoreCase(action)) {
            // Solo restablecer si estamos en la acción "nuevo"
            // Si no hay datos existentes, se marcan todos
            if (this.diasSemana == null || this.diasSemana.isEmpty()) {
                this.diasSemanaArray = new String[] {
                    "1", "2", "3", "4", "5", "6", "7"
                };
                this.diasSemana = String.join(",", this.diasSemanaArray);
            }
        }

    }

    
	public int getPrioridad() {
		return prioridad;
	}


	public void setPrioridad(int prioridad) {
		this.prioridad = prioridad;
	}


	public String getApartado() {
		return apartado;
	}


	public void setApartado(String apartado) {
		this.apartado = apartado;
	}


	public int getOrden() {
		return orden;
	}


	public void setOrden(int orden) {
		this.orden = orden;
	}


	public String getNombreOriginal() {
		return nombreOriginal;
	}


	public void setNombreOriginal(String nombreOriginal) {
		this.nombreOriginal = nombreOriginal;
	}


	public int getOidProcesoEjecucion() {
		return oidProcesoEjecucion;
	}


	public void setOidProcesoEjecucion(int oidProcesoEjecucion) {
		this.oidProcesoEjecucion = oidProcesoEjecucion;
	}


	public String getDiasSemana() {
		return diasSemana;
	}

	public void setDiasSemana(String diasSemana) {
		this.diasSemana = diasSemana;
	}
    
    public String getDiasMes() {
		return diasMes;
	}


	public void setDiasMes(String diasMes) {
		this.diasMes = diasMes;
	}


	public String[] getDiasSemanaArray() {
        // Cuando la vista necesita mostrar los checkboxes
        if (diasSemanaArray == null && diasSemana != null && !diasSemana.isEmpty()) {
            diasSemanaArray = diasSemana.split(",");
        }
        return diasSemanaArray;
    }

    public void setDiasSemanaArray(String[] diasSemanaArray) {
        this.diasSemanaArray = diasSemanaArray;
        // Convertimos a String con comas al guardar
        if (diasSemanaArray != null) {
            this.diasSemana = String.join(",", diasSemanaArray);
        } else {
            this.diasSemana = "";
        }
    }
    
    
	public int getMaxDuracionSegundos() {
		return maxDuracionSegundos;
	}


	public int getDuracionSegundos() {
		return duracionSegundos;
	}


	public void setMaxDuracionSegundos(int maxDuracionSegundos) {
		this.maxDuracionSegundos = maxDuracionSegundos;
	}


	public void setDuracionSegundos(int duracionSegundos) {
		this.duracionSegundos = duracionSegundos;
	}


	public Proceso getProceso() {
		return proceso;
	}

	public void setProceso(Proceso proceso) {
		this.proceso = proceso;
	}

	
	public String getLanzadera() {
		return lanzadera;
	}


	public void setLanzadera(String lanzadera) {
		this.lanzadera = lanzadera;
	}


	public String getActivo() {
		return activo;
	}

	public void setActivo(String activo) {
		this.activo = activo;
	}

	// Getters y setters
    public String getNombreProceso() {
        return nombreProceso;
    }

    public void setNombreProceso(String nombreProceso) {
        this.nombreProceso = nombreProceso;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTipoEjecucion() {
        return tipoEjecucion;
    }

    public void setTipoEjecucion(String tipoEjecucion) {
        this.tipoEjecucion = tipoEjecucion;
    }

    
    public int getFrecuenciaPeriodo() {
		return frecuenciaPeriodo;
	}


	public void setFrecuenciaPeriodo(int frecuenciaPeriodo) {
		this.frecuenciaPeriodo = frecuenciaPeriodo;
	}


	public String getTipoPeriodo() {
        return tipoPeriodo;
    }

    public void setTipoPeriodo(String tipoPeriodo) {
        this.tipoPeriodo = tipoPeriodo;
    }

    public String getHoraEjecucion() {
        return horaEjecucion;
    }

    public void setHoraEjecucion(String horaEjecucion) {
        this.horaEjecucion = horaEjecucion;
    }

    public int getMaxReintentos() {
        return maxReintentos;
    }

    public void setMaxReintentos(int maxReintentos) {
        this.maxReintentos = maxReintentos;
    }

    public int getOidProceso() {
        return oidProceso;
    }

    public void setOidProceso(int oidProceso) {
        this.oidProceso = oidProceso;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

	public List getProcesos() {
		return procesos;
	}

	public void setProcesos(List procesos) {
		this.procesos = procesos;
	}

	public String getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(String fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public String getParametros() {
		return parametros;
	}

	public void setParametros(String parametros) {
		this.parametros = parametros;
	}

	public String getFechaDesde() {
		return fechaDesde;
	}

	public void setFechaDesde(String fechaInicio) {
		this.fechaDesde = fechaInicio;
	}

	public String getFechaHasta() {
		return fechaHasta;
	}

	public void setFechaHasta(String fechaFin) {
		this.fechaHasta = fechaFin;
	}

	public String getFechaInicioEjecucion() {
		return fechaInicioEjecucion;
	}


	public void setFechaInicioEjecucion(String fechaInicioEjecucion) {
		this.fechaInicioEjecucion = fechaInicioEjecucion;
	}


	public String getFechaFinEjecucion() {
		return fechaFinEjecucion;
	}


	public void setFechaFinEjecucion(String fechaFinEjecucion) {
		this.fechaFinEjecucion = fechaFinEjecucion;
	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

	public String getUsuarioEjecucion() {
		return usuarioEjecucion;
	}

	public void setUsuarioEjecucion(String usuarioEjecucion) {
		this.usuarioEjecucion = usuarioEjecucion;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getTipoError() {
		return tipoError;
	}

	public void setTipoError(String tipoError) {
		this.tipoError = tipoError;
	}

	public String getCodigoResultado() {
		return codigoResultado;
	}

	public void setCodigoResultado(String codigoResultado) {
		this.codigoResultado = codigoResultado;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
    
    
}
