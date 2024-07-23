package lopicost.spd.model;

import java.io.Serializable;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class Report implements Serializable{

    /** identifier field */
    private Long oidReport;
    private String idReport;
    private String nombreReport;
    private String linkReport;
    private String paramsReport;
    private String descripcion;
    private boolean activo;

	public String getIdreport() {
		return idReport;
	}

	public void setIdReport(String idReport) {
		this.idReport = idReport;
	}

	public String getNombreReport() {
		return nombreReport;
	}


	public Long getOidReport() {
		return oidReport;
	}

	public void setOidReport(Long oidReport) {
		this.oidReport = oidReport;
	}

	public String getParamsRreport() {
		return paramsReport;
	}

	public void setParamsReport(String paramsReport) {
		this.paramsReport = paramsReport;
	}



	public String getLinkReport() {
		return linkReport;
	}

	public void setLinkReport(String linkReport) {
		this.linkReport = linkReport;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public String getIdReport() {
		return idReport;
	}

	public String getParamsReport() {
		return paramsReport;
	}

	public void setNombreReport(String nombreReport) {
		this.nombreReport = nombreReport;
	}

	public int hashCode() {
        return new HashCodeBuilder()
            .append(getOidReport())
            .toHashCode();
    }	
}
