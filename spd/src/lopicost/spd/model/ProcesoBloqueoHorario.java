package lopicost.spd.model;


public class ProcesoBloqueoHorario {
    private int oidBloqueoHorario;
    private Integer oidProceso;
    private String idFarmacia;
    private String nombreFarmacia;
    private String lanzadera;
    private String parametros;
    private String tipoBloqueoHorario;
    private String horasDesde;
    private String horasHasta;
    private Integer valorDia;
    private String valorFecha;
    private String descripcion;
    private boolean usarBloqueoHorario;
    private String bloqueaAhora;

    // Getters y Setters
    public int getOidBloqueoHorario() { return oidBloqueoHorario; }
    public void setOidBloqueoHorario(int oidBloqueoHorario) { this.oidBloqueoHorario = oidBloqueoHorario; }
    public String getIdFarmacia() {		return idFarmacia;	}
    public void setIdFarmacia(String idFarmacia) {		this.idFarmacia = idFarmacia;	}
	public String getNombreFarmacia() {		return nombreFarmacia;	}
	public void setNombreFarmacia(String nombreFarmacia) {		this.nombreFarmacia = nombreFarmacia;	}
	public String getLanzadera() {		return lanzadera;	}	public void setLanzadera(String lanzadera) {		this.lanzadera = lanzadera;	}
	public String getParametros() {		return parametros;	}	public void setParametros(String parametros) {		this.parametros = parametros;	}
	public Integer getOidProceso() { return oidProceso; }
    public void setOidProceso(Integer oidProceso) { this.oidProceso = oidProceso; }
    public String getTipoBloqueoHorario() { return tipoBloqueoHorario; }
    public void setTipoBloqueoHorario(String tipoBloqueoHorario) { this.tipoBloqueoHorario = tipoBloqueoHorario; }
    public String getHorasDesde() { return horasDesde; }
    public void setHorasDesde(String horasDesde) { this.horasDesde = horasDesde; }
    public String getHorasHasta() { return horasHasta; }
    public void setHorasHasta(String horasHasta) { this.horasHasta = horasHasta; }
    public Integer getValorDia() { return valorDia; }
    public void setValorDia(Integer valorDia) { this.valorDia = valorDia; }
    public String getValorFecha() { return valorFecha; }
    public void setValorFecha(String valorFecha) { this.valorFecha = valorFecha; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public boolean isUsarBloqueoHorario() { return usarBloqueoHorario; }
    public void setUsarBloqueoHorario(boolean activo) { this.usarBloqueoHorario = activo; }
	public String getBloqueaAhora() {		return bloqueaAhora;	}
	public void setBloqueaAhora(String bloqueaAhora) {		this.bloqueaAhora = bloqueaAhora;	}
    
}
