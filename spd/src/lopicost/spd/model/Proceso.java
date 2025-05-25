package lopicost.spd.model;


import java.util.*;


public class Proceso {
    private int oidProceso;                     // Identificador �nico del proceso
    private int version		;                   // Versi�n del proceso
    private String fechaCreacion;              	// Fecha de creaci�n del proceso
    private String usuarioCreacion;				// Usuario que crea el proceso
    private String lanzadera;                 	// Nombre de la lanzadera que lo ejecuta
    private String nombreProceso;               // Nombre del procedimiento o proceso
    private String descripcion;                 // Descripci�n del proceso
    private String activo;                      // SI/NO
    private String parametros;                  // Par�metros del procedimiento
    private String tipoEjecucion;               // "Manual", "Programada"
    private int frecuenciaPeriodo;             	    // Frecuencia cada periodo
    private String tipoPeriodo;                 // Frecuencia de ejecuci�n ("Diaria", "Semanal", etc.)
    private String diasSemana;                  // D�as de la semana para ejecuci�n programada
    private String diasMes;                 	// D�as del mes (para procesos mensuales o d�as de mes puntuales )
    private String horaEjecucion;          		// Hora de ejecuci�n si es programado
    private int maxReintentos;                  // M�ximos reintentos en caso de fallo
    private Integer maxDuracionSegundos; 		// M�xima duraci�n de la ejecuci�n en segundos
    private String fechaDesde;               	// Fecha de activaci�n del proceso
    private String fechaHasta;                  // Fecha de desactivaci�n del proceso
    private ProcesoEjecucion ultimaEjecucion;	// �ltima ejecuci�n del proceso
    private ProcesoEjecucion ejecucionActiva;	// ejecuci�n activa del proceso
    private ProcesoEjecucion ejecucion;			// ejecuci�n 
    private List<ProcesoEjecucion> listadoEjecuciones;	// Listado de las �ltimas ejecuciones
    
   private String[] diasSemanaArray;

	@Override
	public String toString() {
		return "Proceso [oidProceso=" + oidProceso + ", version=" + version + ", fechaCreacion=" + fechaCreacion
				+ ", lanzadera=" + lanzadera + ", nombreProceso=" + nombreProceso + ", descripcion=" + descripcion + ", usuarioCreacion=" + usuarioCreacion 
				+ ", activo=" + activo + ", parametros=" + parametros + ", tipoEjecucion=" + tipoEjecucion 
				+ ", tipoPeriodo =" + tipoPeriodo + ", frecuenciaPeriodo =" + frecuenciaPeriodo + ", diasSemana=" + diasSemana + ", diasMes=" + diasMes 
				+ ", horaEjecucion=" + horaEjecucion 
				+ ", maxReintentos=" + maxReintentos + ", maxDuracionSegundos=" + maxDuracionSegundos 
				+ ", fechaDesde=" + fechaDesde + ", fechaHasta=" + fechaHasta + "]";
	}
	
	
    public ProcesoEjecucion getEjecucion() {
		return ejecucion;
	}


	public void setEjecucion(ProcesoEjecucion ejecucion) {
		this.ejecucion = ejecucion;
	}


	public List<ProcesoEjecucion> getListadoEjecuciones() {
		return listadoEjecuciones;
	}


	public void setListadoEjecuciones(List<ProcesoEjecucion> listadoEjecuciones) {
		this.listadoEjecuciones = listadoEjecuciones;
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
    
	public String getDiasSemana() {
		return diasSemana;
	}
	public void setDiasSemana(String diasSemana) {
		this.diasSemana = diasSemana;
        if (this.diasSemana != null && !this.diasSemana.isEmpty()) {
        	this.diasSemanaArray = this.diasSemana.split(",");
        }
	}
    
	
 	public int getFrecuenciaPeriodo() {
		return frecuenciaPeriodo;
	}

	public void setFrecuenciaPeriodo(int frecuenciaPeriodo) {
		this.frecuenciaPeriodo = frecuenciaPeriodo;
	}

	public String getDiasMes() {
		return diasMes;
	}

	public void setDiasMes(String diasMes) {
		this.diasMes = diasMes;
	}

	public ProcesoEjecucion getUltimaEjecucion() {
		return ultimaEjecucion;
	}

	public void setUltimaEjecucion(ProcesoEjecucion ultimaEjecucion) {
		this.ultimaEjecucion = ultimaEjecucion;
	}

	public ProcesoEjecucion getEjecucionActiva() {
		return ejecucionActiva;
	}

	public void setEjecucionActiva(ProcesoEjecucion ejecucionActiva) {
		this.ejecucionActiva = ejecucionActiva;
	}

	public int getOidProceso() {
		return oidProceso;
	}
	public void setOidProceso(int oidProceso) {
		this.oidProceso = oidProceso;
	}
	public String getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(String fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	public String getNombreProceso() {
		return nombreProceso;
	}
	public void setNombreProceso(String nombreProceso) {
		this.nombreProceso = nombreProceso;
	}
	
	public String getLanzadera() {
		return lanzadera;
	}
	public void setLanzadera(String lanzadera) {
		this.lanzadera = lanzadera;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getParametros() {
		return parametros;
	}
	public void setParametros(String parametros) {
		this.parametros = parametros;
	}
	public String getTipoEjecucion() {
		return tipoEjecucion;
	}
	public void setTipoEjecucion(String tipoEjecucion) {
		this.tipoEjecucion = tipoEjecucion;
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
	public Integer getMaxDuracionSegundos() {
		return maxDuracionSegundos;
	}
	public void setMaxDuracionSegundos(Integer maxDuracionSegundos) {
		this.maxDuracionSegundos = maxDuracionSegundos;
	}
	public String getFechaDesde() {
		return fechaDesde;
	}
	public void setFechaDesde(String fechaDesde) {
		this.fechaDesde = fechaDesde;
	}
	public String getFechaHasta() {
		return fechaHasta;
	}
	public void setFechaHasta(String fechaHasta) {
		this.fechaHasta = fechaHasta;
	}
	public String getActivo() {
		return activo;
	}
	public void setActivo(String activo) {
		this.activo = activo;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public String getUsuarioCreacion() {
		return usuarioCreacion;
	}
	public void setUsuarioCreacion(String usuarioCreacion) {
		this.usuarioCreacion = usuarioCreacion;
	}
	
    
    
}
