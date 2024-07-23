package lopicost.spd.model;

import org.apache.commons.lang.builder.HashCodeBuilder;
import java.io.Serializable;
import java.util.Date;

public class Farmacia implements Serializable
{
    private String idFarmacia;
    private String nombreFarmacia;
    private String contacto;
    private String codigoUP;
    private Date fechaAlta;
    private Date fechaBaja;
    private String direccion;
    private String poblacion;
    private String provincia;
    private String codPostal;
    private String telefono;
    private String fax;
    private String email;
    private int orden;
    private int nivel;
	public String getIdFarmacia() {
		return idFarmacia;
	}
	public void setIdFarmacia(String idFarmacia) {
		this.idFarmacia = idFarmacia;
	}
	public String getNombreFarmacia() {
		return nombreFarmacia;
	}
	public void setNombreFarmacia(String nombreFarmacia) {
		this.nombreFarmacia = nombreFarmacia;
	}
	public String getContacto() {
		return contacto;
	}
	public void setContacto(String contacto) {
		this.contacto = contacto;
	}
	public String getCodigoUP() {
		return codigoUP;
	}
	public void setCodigoUP(String codigoUP) {
		this.codigoUP = codigoUP;
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
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	public String getCodPostal() {
		return codPostal;
	}
	public void setCodPostal(String codPostal) {
		this.codPostal = codPostal;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getOrden() {
		return orden;
	}
	public void setOrden(int orden) {
		this.orden = orden;
	}
	public int getNivel() {
		return nivel;
	}
	public void setNivel(int nivel) {
		this.nivel = nivel;
	}

    



	

	
}