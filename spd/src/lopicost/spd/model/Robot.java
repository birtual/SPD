package lopicost.spd.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class Robot implements Serializable{

    /** identifier field */

    private String idRobot;
    private String nombreRobot;
    private String alias;
    private String contacto;
    private String direccion;
    private String poblacion;
    private String carpetaFilia;
    private boolean activo;
	private Date fechaAlta;	
	private Date fechaBaja;
	
	
	public String getIdRobot() {
		return idRobot;
	}
	public void setIdRobot(String idRobot) {
		this.idRobot = idRobot;
	}
	public String getNombreRobot() {
		return nombreRobot;
	}
	public void setNombreRobot(String nombreRobot) {
		this.nombreRobot = nombreRobot;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getContacto() {
		return contacto;
	}
	public void setContacto(String contacto) {
		this.contacto = contacto;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getPoblacion() {
		return poblacion;
	}
	public void setPoblacion(String poblacion) {
		this.poblacion = poblacion;
	}
	public String getCarpetaFilia() {
		return carpetaFilia;
	}
	public void setCarpetaFilia(String carpetaFilia) {
		this.carpetaFilia = carpetaFilia;
	}
	public boolean isActivo() {
		return activo;
	}
	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	public Date getFechaAlta() {
		return fechaAlta;
	}
	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	public Date getFechaBaja() {
		return fechaBaja;
	}
	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}	
	


}
