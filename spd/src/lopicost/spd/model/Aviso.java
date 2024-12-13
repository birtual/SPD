package lopicost.spd.model;

import java.io.Serializable;
import java.util.Date;

public class Aviso implements Serializable
{
	private int oidAviso;
	private Date fechaInsert;
	private String aviso;
	private Date fechaInicio;
	private Date fechaFin;
	private String activo;
	private String idFarmacia;
	private String usuarioCreador;
	private int orden;
	private String tipo;
	
	public int getOidAviso() {
		return oidAviso;
	}
	public void setOidAviso(int oidAviso) {
		this.oidAviso = oidAviso;
	}
	public Date getFechaInsert() {
		return fechaInsert;
	}
	public void setFechaInsert(Date fechaInsert) {
		this.fechaInsert = fechaInsert;
	}
	public String getAviso() {
		return aviso;
	}
	public void setAviso(String aviso) {
		this.aviso = aviso;
	}
	public Date getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public Date getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	public String getActivo() {
		return activo;
	}
	public void setActivo(String activo) {
		this.activo = activo;
	}
	public String getIdFarmacia() {
		return idFarmacia;
	}
	public void setIdFarmacia(String idFarmacia) {
		this.idFarmacia = idFarmacia;
	}
	public String getUsuarioCreador() {
		return usuarioCreador;
	}
	public void setUsuarioCreador(String usuarioCreador) {
		this.usuarioCreador = usuarioCreador;
	}
	public int getOrden() {
		return orden;
	}
	public void setOrden(int orden) {
		this.orden = orden;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
    
   
}