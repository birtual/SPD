package lopicost.spd.model;

import java.io.Serializable;
import java.util.Date;

public class Residencia implements Serializable {
	  private int oidResidencia;
	  private String idResidencia;
	  private String nombreResidencia;
	  private String idFarmacia;
	  private String codPostal;
	  private String poblacion;
	  private String provincia;
	  private String telefono;
	  private String nif;
	  private String email;
	  private String fax;
	  private String contacto;
	  private Date fechaAlta;
	  private String aliasResidencia="";
	  private String aplicativo;
	  private String lenguajeBolsa="";
	  private String status="";
	  private String layoutBolsa="";
	  private String locationID;
	  private String servirSiPrecisa;
	  
	public Residencia() {
		super();
	}

	public int getOidResidencia() {
		return oidResidencia;
	}

	public void setOidResidencia(int oidResidencia) {
		this.oidResidencia = oidResidencia;
	}

	public String getIdResidencia() {
		return idResidencia;
	}

	public void setIdResidencia(String idResidencia) {
		this.idResidencia = idResidencia;
	}

	public String getNombreResidencia() {
		return nombreResidencia;
	}

	public void setNombreResidencia(String nombreResidencia) {
		this.nombreResidencia = nombreResidencia;
	}

	public String getIdFarmacia() {
		return idFarmacia;
	}

	public void setIdFarmacia(String idFarmacia) {
		this.idFarmacia = idFarmacia;
	}

	public String getCodPostal() {
		return codPostal;
	}

	public void setCodPostal(String codPostal) {
		this.codPostal = codPostal;
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

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getContacto() {
		return contacto;
	}

	public void setContacto(String contacto) {
		this.contacto = contacto;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public String getAliasResidencia() {
		return aliasResidencia;
	}

	public void setAliasResidencia(String aliasResidencia) {
		this.aliasResidencia = aliasResidencia;
	}

	public String getAplicativo() {
		return aplicativo;
	}

	public void setAplicativo(String aplicativo) {
		this.aplicativo = aplicativo;
	}

	public String getLenguajeBolsa() {
		return lenguajeBolsa;
	}

	public void setLenguajeBolsa(String lenguajeBolsa) {
		this.lenguajeBolsa = lenguajeBolsa;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLayoutBolsa() {
		return layoutBolsa;
	}

	public void setLayoutBolsa(String layoutBolsa) {
		this.layoutBolsa = layoutBolsa;
	}

	public String getLocationID() {
		return locationID;
	}

	public void setLocationID(String locationID) {
		this.locationID = locationID;
	}

	public String getServirSiPrecisa() {
		return servirSiPrecisa;
	}

	public void setServirSiPrecisa(String servirSiPrecisa) {
		this.servirSiPrecisa = servirSiPrecisa;
	}


}
