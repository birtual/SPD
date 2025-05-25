package lopicost.spd.model;

public class ProcesoEjecucionLog {
    private int oidLogProcesoEjecucion;           
    private int oidProcesoEjecucion;            		
    private String paso;                   		
    private String fecha;        
    private String mensaje;      
    

	@Override
	public String toString() {
		return "Proceso [oidLogProcesoEjecucion=" + oidLogProcesoEjecucion + ", oidProcesoEjecucion=" + oidProcesoEjecucion 
				+ ", paso=" + paso + ", fecha=" + fecha + ", mensaje=" + mensaje  + "]";
	}


	public int getOidLogProcesoEjecucion() {
		return oidLogProcesoEjecucion;
	}


	public void setOidLogProcesoEjecucion(int oidLogProcesoEjecucion) {
		this.oidLogProcesoEjecucion = oidLogProcesoEjecucion;
	}


	public int getOidProcesoEjecucion() {
		return oidProcesoEjecucion;
	}


	public void setOidProcesoEjecucion(int oidProcesoEjecucion) {
		this.oidProcesoEjecucion = oidProcesoEjecucion;
	}


	public String getPaso() {
		return paso;
	}


	public void setPaso(String paso) {
		this.paso = paso;
	}


	public String getFecha() {
		return fecha;
	}


	public void setFecha(String fecha) {
		this.fecha = fecha;
	}


	public String getMensaje() {
		return mensaje;
	}


	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
    
    
}
