package lopicost.spd.model;

import java.util.List;

public class ProcesoEjecucion {
    private int oidProcesoEjecucion;            // Identificador �nico de la ejecuci�n de un proceso
    private int oidProceso;            			// Identificador �nico del proceso
    private String lanzadera;                 	// Nombre de la lanzadera que lo ejecuta
    private int version;                   		// Versi�n del proceso
    private String fechaCreacionEjecucion;        // Fecha de creaci�n del procesoEjecucion
    private String fechaInicioEjecucion;        // Fecha y hora de inicio de la ejecuci�n
    private String fechaFinEjecucion;           // Fecha y hora de fin de la ejecuci�n
    private String estado;                      // Estado del proceso: "En ejecuci�n", "Finalizado", etc.
    private int duracionSegundos;               // Duraci�n de la ejecuci�n en segundos
    private String resultado;                   // Resultado de la ejecuci�n
    private String usuarioEjecucion;        	// Usuario que ejecut� el proceso 
    private String mensaje;                     // Mensaje adicional de la ejecuci�n
    private String tipoError;                   // Tipo de error si existi� un fallo
    private String codigoResultado;          	// C�digo de resultado (0 para �xito, 1 para error, etc.)
    private String error;                       // Error ocurrido durante la ejecuci�n
    private int numIntentos;               		// N�mero de intentos realizados
    private List<ProcesoEjecucionLog> detalleEjecucion; 

	@Override
	public String toString() {
		return "Proceso [oidProcesoEjecucion=" + oidProcesoEjecucion + ", oidProceso=" + oidProceso + ", version=" + version + ", lanzadera=" + lanzadera
				+ ", fechaCreacionEjecucion=" + fechaCreacionEjecucion + ", fechaInicioEjecucion=" + fechaInicioEjecucion + ", fechaFinEjecucion=" + fechaFinEjecucion 
				+ ", estado=" + estado	+ ", duracionSegundos=" + duracionSegundos	+ ", resultado=" + resultado + ", usuarioEjecucion=" + usuarioEjecucion 
				+ ", usuarioEjecucion=" + usuarioEjecucion + ", mensaje=" + mensaje + ", tipoError=" + tipoError + ", numIntentos=" + numIntentos
				+ ", codigoResultado=" + codigoResultado + ", error=" + error  + "]";
	}
	
	
	public List<ProcesoEjecucionLog> getDetalleEjecucion() {
		return detalleEjecucion;
	}


	public void setDetalleEjecucion(List<ProcesoEjecucionLog> detalleEjecucion) {
		this.detalleEjecucion = detalleEjecucion;
	}


	public int getOidProceso() {
		return oidProceso;
	}
	public void setOidProceso(int oidProceso) {
		this.oidProceso = oidProceso;
	}
	public String getLanzadera() {
		return lanzadera;
	}
	public void setLanzadera(String lanzadera) {
		this.lanzadera = lanzadera;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getFechaInicioEjecucion() {
		return fechaInicioEjecucion;
	}
	public void setFechaInicioEjecucion(String fechaInicio) {
		this.fechaInicioEjecucion = fechaInicio;
	}
	public String getFechaFinEjecucion() {
		return fechaFinEjecucion;
	}
	public void setFechaFinEjecucion(String fechaFin) {
		this.fechaFinEjecucion = fechaFin;
	}
	public Integer getDuracionSegundos() {
		return duracionSegundos;
	}
	public void setDuracionSegundos(Integer duracionSegundos) {
		this.duracionSegundos = duracionSegundos;
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
	public int getOidProcesoEjecucion() {
		return oidProcesoEjecucion;
	}
	public void setOidProcesoEjecucion(int oidProcesoEjecucion) {
		this.oidProcesoEjecucion = oidProcesoEjecucion;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public String getFechaCreacionEjecucion() {
		return fechaCreacionEjecucion;
	}
	public void setFechaCreacionEjecucion(String fechaCreacionEjecucion) {
		this.fechaCreacionEjecucion = fechaCreacionEjecucion;
	}
	public void setDuracionSegundos(int duracionSegundos) {
		this.duracionSegundos = duracionSegundos;
	}
	public int getNumIntentos() {
		return numIntentos;
	}
	public void setNumIntentos(int numIntentos) {
		this.numIntentos = numIntentos;
	}

    
    
}
