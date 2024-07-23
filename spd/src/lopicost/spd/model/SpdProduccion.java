package lopicost.spd.model;

import java.util.Date;


public class SpdProduccion {
	
	  private int oidProduccion;
	  private int oidListadoBase;
	  private String idProduccion;
	  private String idDivisionResidencia;
	  private Date fechaCreacion;
	  private Date fechaInicioProduccion;
	  private Date fechaFinProduccion;
	  private Date fechaValidacionDatos;
	  private String nombreFicheroXML;
	  private Date fechaCreacionFicheroXML;
	  private String usuarioCreacionFicheroXML;
	  private boolean activo=true;
	  private int row;
	  private String idEstado;
		  
	  
	  public SpdProduccion() {
		super();
	}


	public int getOidProduccion() {
		return oidProduccion;
	}


	public void setOidProduccion(int oidProduccion) {
		this.oidProduccion = oidProduccion;
	}


	public int getOidListadoBase() {
		return oidListadoBase;
	}


	public void setOidListadoBase(int oidListadoBase) {
		this.oidListadoBase = oidListadoBase;
	}


	public String getIdProduccion() {
		return idProduccion;
	}


	public void setIdProduccion(String idProduccion) {
		this.idProduccion = idProduccion;
	}


	public String getIdDivisionResidencia() {
		return idDivisionResidencia;
	}


	public void setIdDivisionResidencia(String idDivisionResidencia) {
		this.idDivisionResidencia = idDivisionResidencia;
	}


	public Date getFechaCreacion() {
		return fechaCreacion;
	}


	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}


	public Date getFechaInicioProduccion() {
		return fechaInicioProduccion;
	}


	public void setFechaInicioProduccion(Date fechaInicioProduccion) {
		this.fechaInicioProduccion = fechaInicioProduccion;
	}


	public Date getFechaFinProduccion() {
		return fechaFinProduccion;
	}


	public void setFechaFinProduccion(Date fechaFinProduccion) {
		this.fechaFinProduccion = fechaFinProduccion;
	}


	public Date getFechaValidacionDatos() {
		return fechaValidacionDatos;
	}


	public void setFechaValidacionDatos(Date fechaValidacionDatos) {
		this.fechaValidacionDatos = fechaValidacionDatos;
	}


	public String getNombreFicheroXML() {
		return nombreFicheroXML;
	}


	public void setNombreFicheroXML(String nombreFicheroXML) {
		this.nombreFicheroXML = nombreFicheroXML;
	}


	public Date getFechaCreacionFicheroXML() {
		return fechaCreacionFicheroXML;
	}


	public void setFechaCreacionFicheroXML(Date fechaCreacionFicheroXML) {
		this.fechaCreacionFicheroXML = fechaCreacionFicheroXML;
	}


	public String getUsuarioCreacionFicheroXML() {
		return usuarioCreacionFicheroXML;
	}


	public void setUsuarioCreacionFicheroXML(String usuarioCreacionFicheroXML) {
		this.usuarioCreacionFicheroXML = usuarioCreacionFicheroXML;
	}


	public boolean isActivo() {
		return activo;
	}


	public void setActivo(boolean activo) {
		this.activo = activo;
	}


	public int getRow() {
		return row;
	}


	public void setRow(int row) {
		this.row = row;
	}


	public String getIdEstado() {
		return idEstado;
	}


	public void setIdEstado(String idEstado) {
		this.idEstado = idEstado;
	}

	 







	

	
}
