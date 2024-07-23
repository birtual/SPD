package lopicost.spd.model;

import java.util.Date;
import java.util.List;

import lopicost.spd.utils.StringUtil;

public class SpdListadoBase_v0 {
	
	  private int oidFicheroBase;
	  private String idFicheroBase;
	  private String idDivisionResidencia;
	  private Date fechaCreacion;
	  private int filasTotales;
	  private String nombreFicheroBase;
	  private String ultimaProduccionRealizada;
	  private Date fechaValidacionDatos;
	  private String idUsuarioCreacion;
	  private String idUsuarioValidacion;
	  private String idEstado;
	  private String resultLog;
	  private boolean activo=true;
	  private int row;
	  
	  public SpdListadoBase_v0() {
		super();
	}

	  
	public int getOidFicheroBase() {
		return oidFicheroBase;
	}
	public void setOidFicheroBase(int oidFicheroBase) {
		this.oidFicheroBase = oidFicheroBase;
	}
	public String getIdFicheroBase() {
		return idFicheroBase;
	}
	public void setIdFicheroBase(String idFicheroBase) {
		this.idFicheroBase = idFicheroBase;
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
	public int getFilasTotales() {
		return filasTotales;
	}
	public void setFilasTotales(int filasTotales) {
		this.filasTotales = filasTotales;
	}
	public String getNombreFicheroBase() {
		return nombreFicheroBase;
	}
	public void setNombreFicheroBase(String nombreFicheroBase) {
		this.nombreFicheroBase = nombreFicheroBase;
	}
	public String getUltimaProduccionRealizada() {
		return ultimaProduccionRealizada;
	}
	public void setUltimaProduccionRealizada(String ultimaProduccionRealizada) {
		this.ultimaProduccionRealizada = ultimaProduccionRealizada;
	}
	public Date getFechaValidacionDatos() {
		return fechaValidacionDatos;
	}
	public void setFechaValidacionDatos(Date fechaValidacionDatos) {
		this.fechaValidacionDatos = fechaValidacionDatos;
	}
	public String getIdUsuarioCreacion() {
		return idUsuarioCreacion;
	}
	public void setIdUsuarioCreacion(String idUsuarioCreacion) {
		this.idUsuarioCreacion = idUsuarioCreacion;
	}
	public String getIdUsuarioValidacion() {
		return idUsuarioValidacion;
	}
	public void setIdUsuarioValidacion(String idUsuarioValidacion) {
		this.idUsuarioValidacion = idUsuarioValidacion;
	}
	public String getIdEstado() {
		return idEstado;
	}
	public void setIdEstado(String idEstado) {
		this.idEstado = idEstado;
	}
	public String getResultLog() {
		return resultLog;
	}
	public void setResultLog(String resultLog) {
		this.resultLog = resultLog;
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







	

	
}
