package lopicost.spd.struts.form;


import lopicost.spd.model.ProcesoRestricciones;

public class ProcesosRestriccionesForm extends GenericForm {
	 /**
	 * 
	 */
	private int oidRestriccion;
	    private String oidProceso;
	    private String idFarmacia;
	    private String nombreFarmacia;
	    private String lanzadera;
	    private String tipoRestriccion;
	    private String horasDesde;
	    private String horasHasta;
	    private String valorDia;
	    private String valorFecha;
	    private String descripcion;
	    private boolean usarRestriccion;
	    private String bloqueaAhora;
	    private ProcesoRestricciones restriccion;
	    
	    
	    public ProcesoRestricciones getRestriccion() {
			return restriccion;
		}
		public void setRestriccion(ProcesoRestricciones restriccion) {
			this.restriccion = restriccion;
		}
		public int getOidRestriccion() { return oidRestriccion; }
	    public void setOidRestriccion(int oidRestriccion) { this.oidRestriccion = oidRestriccion; }
	    public String getIdFarmacia() {			return idFarmacia;		}
		public void setIdFarmacia(String idFarmacia) {			this.idFarmacia = idFarmacia;		}
		public String getNombreFarmacia() {
			return nombreFarmacia;
		}
		public void setNombreFarmacia(String nombreFarmacia) {
			this.nombreFarmacia = nombreFarmacia;
		}
		public String getOidProceso() { return oidProceso; }
	    public void setOidProceso(String oidProceso) { this.oidProceso = oidProceso; }
	    public String getLanzadera() {		return lanzadera;	}	
	    public void setLanzadera(String lanzadera) {		this.lanzadera = lanzadera;	}
	    public String getTipoRestriccion() { return tipoRestriccion; }
	    public void setTipoRestriccion(String tipoRestriccion) { this.tipoRestriccion = tipoRestriccion; }
	    public String getHorasDesde() { return horasDesde; }
	    public void setHorasDesde(String horasDesde) { this.horasDesde = horasDesde; }
	    public String getHorasHasta() { return horasHasta; }
	    public void setHorasHasta(String horasHasta) { this.horasHasta = horasHasta; }
	    public String getValorDia() { return valorDia; }
	    public void setValorDia(String valorDia) { this.valorDia = valorDia; }
	    public String getValorFecha() { return valorFecha; }
	    public void setValorFecha(String valorFecha) { this.valorFecha = valorFecha; }
	    public String getDescripcion() { return descripcion; }
	    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
	    public boolean isUsarRestriccion() { return usarRestriccion; }
	    public void setUsarRestriccion(boolean activo) { this.usarRestriccion = activo; }

	    public String getBloqueaAhora() {
			return bloqueaAhora;
		}
		public void setBloqueaAhora(String bloqueaAhora) {
			this.bloqueaAhora = bloqueaAhora;
		}
		public void resetear() {
	        oidProceso = null;
	        tipoRestriccion = null;
	        horasDesde = null;
	        horasHasta = null;
	        valorDia = null;
	        valorFecha = null;
	        descripcion = null;
	        usarRestriccion = true;
	    }

	
	}
