package lopicost.spd.model;


import java.util.*;


public class ProcesoHistorico {
    private int oidProcesoHistorico;            // Identificador único del procesoHistorico
    private int oidProceso;            			// Identificador único del proceso
    private int version		;                   // Versión del proceso
    private Date fechaCreacionHistorico;        // Fecha de creación del proceso
    private String usuarioCreacion;				// Usuario que crea el proceso
    private String lanzadera;                 	// Nombre de la lanzadera que lo ejecuta
    private String nombreProceso;               // Nombre del procedimiento o proceso
    private String descripcion;                 // Descripción del proceso
    private String activo;                      // SI/NO
    private String parametros;                  // Parámetros del procedimiento
    private String tipoEjecucion;               // "Manual", "Programada"
    private int frecuenciaPeriodo;             	// Frecuencia cada periodo
    private String tipoPeriodo;                 // Frecuencia de ejecutó ("Diaria", "Semanal", etc.)
    private String diasSemana;                  // Días de la semana para ejecutó programada
    private String diasMes;                 	// Días del mes (para procesos mensuales o días de mes puntuales )
    private String horaEjecucion;          		// Hora de ejecutó si es programado
    private int maxReintentos;                  // Máximos reintentos en caso de fallo
    private Integer maxDuracionSegundos; 		// Máxima duración de la ejecutó en segundos
    private String fechaDesde;               	// Fecha de activación del proceso
    private String fechaHasta;                  // Fecha de desactivación del proceso
   

    
	@Override
	public String toString() {
		return "Proceso [oidHistoricoProceso=" + oidProcesoHistorico + ", oidProceso=" + oidProceso + ", version=" + version 
				+ ", fechaCreacionHistorico=" + fechaCreacionHistorico + ", lanzadera=" + lanzadera + ", usuarioCreacion=" + usuarioCreacion 
				+ ", nombreProceso=" + nombreProceso + ", descripcion=" + descripcion + ", activo=" + activo 
				+ ", parametros=" + parametros + ", tipoEjecucion=" + tipoEjecucion 
				+ ", frecuenciaPeriodo=" + frecuenciaPeriodo + ", tipoPeriodo=" + tipoPeriodo + ", diasSemana=" + diasSemana + ", diasMes=" + diasMes + ", horaEjecucion=" + horaEjecucion 
				+ ", maxReintentos=" + maxReintentos + ", maxDuracionSegundos=" + maxDuracionSegundos 
				+ ", fechaDesde=" + fechaDesde + ", fechaHasta=" + fechaHasta + "]";
	}
	public int getOidProceso() {
		return oidProceso;
	}
	public void setOidProceso(int oidProceso) {
		this.oidProceso = oidProceso;
	}
	
	public int getOidProcesoHistorico() {
		return oidProcesoHistorico;
	}
	public void setOidProcesoHistorico(int oidHistoricoProceso) {
		this.oidProcesoHistorico = oidHistoricoProceso;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public Date getFechaCreacionHistorico() {
		return fechaCreacionHistorico;
	}
	public void setFechaCreacionHistorico(Date fechaCreacionHistorico) {
		this.fechaCreacionHistorico = fechaCreacionHistorico;
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
	public String getUsuarioCreacion() {
		return usuarioCreacion;
	}
	public void setUsuarioCreacion(String usuarioCreacion) {
		this.usuarioCreacion = usuarioCreacion;
	}
	
    
    
}
