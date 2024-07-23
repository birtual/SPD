package lopicost.spd.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GestSustitucionesLite implements Serializable,   Cloneable {

	  private int oidGestSustitucionesLite;
	  private Date fecha;
	  private String idDivisionResidencia;
	  private int oidDivisionResidencia;
	  private String resiCn;
	  private String resiMedicamento;
	  private String spdCn;
	  private String spdNombreBolsa;
	  private String spdNombreMedicamento;
	  private String spdFormaMedicacion;		
	  private String spdAccionBolsa;		
	  private String excepciones;
	  private String aux1;
	  private String aux2;
	  private String codGtVm; //principio activo
	  private String nomGtVm; //principio activo
	  private String codGtVmp; //principio activo + DOSIS
	  private String nomGtVmp; //principio activo + DOSIS
	  private String nomLABO; //principio activo + DOSIS
	  
	  public GestSustitucionesLite clone() {
	        try {
	            return (GestSustitucionesLite) super.clone();
	        } catch (CloneNotSupportedException e) {
	            // Manejo de excepciones en caso de que no se pueda clonar
	            return null;
	        }
	    }
	  
	  
	  
	public int getOidGestSustitucionesLite() {
		return oidGestSustitucionesLite;
	}
	public void setOidGestSustitucionesLite(int oidGestSustitucionesLite) {
		this.oidGestSustitucionesLite = oidGestSustitucionesLite;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getIdDivisionResidencia() {
		return idDivisionResidencia;
	}
	public void setIdDivisionResidencia(String idDivisionResidencia) {
		this.idDivisionResidencia = idDivisionResidencia;
	}
	public String getResiCn() {
		return resiCn;
	}
	public void setResiCn(String resiCn) {
		this.resiCn = resiCn;
	}
	public String getResiMedicamento() {
		return resiMedicamento;
	}
	public void setResiMedicamento(String resiMedicamento) {
		this.resiMedicamento = resiMedicamento;
	}
	public String getSpdCn() {
		return spdCn;
	}
	public void setSpdCn(String spdCn) {
		this.spdCn = spdCn;
	}
	public String getSpdNombreBolsa() {
		return spdNombreBolsa;
	}
	public void setSpdNombreBolsa(String spdNombreBolsa) {
		this.spdNombreBolsa = spdNombreBolsa;
	}
	public String getSpdFormaMedicacion() {
		return spdFormaMedicacion;
	}
	public void setSpdFormaMedicacion(String spdFormaMedicacion) {
		this.spdFormaMedicacion = spdFormaMedicacion;
	}
	public String getSpdAccionBolsa() {
		return spdAccionBolsa;
	}
	public void setSpdAccionBolsa(String spdAccionBolsa) {
		this.spdAccionBolsa = spdAccionBolsa;
	}
	public String getExcepciones() {
		return excepciones;
	}
	public void setExcepciones(String excepciones) {
		this.excepciones = excepciones;
	}
	public String getAux1() {
		return aux1;
	}
	public void setAux1(String aux1) {
		this.aux1 = aux1;
	}
	public String getAux2() {
		return aux2;
	}
	public void setAux2(String aux2) {
		this.aux2 = aux2;
	}
	public int getOidDivisionResidencia() {
		return oidDivisionResidencia;
	}
	public void setOidDivisionResidencia(int oidDivisionResidencia) {
		this.oidDivisionResidencia = oidDivisionResidencia;
	}
	public String getSpdNombreMedicamento() {
		return spdNombreMedicamento;
	}
	public void setSpdNombreMedicamento(String spdNombreMedicamento) {
		this.spdNombreMedicamento = spdNombreMedicamento;
	}
	public String getNomGtVm() {
		return nomGtVm;
	}
	public void setNomGtVm(String nomGtVm) {
		this.nomGtVm = nomGtVm;
	}
	public String getCodGtVm() {
		return codGtVm;
	}
	public void setCodGtVm(String codGtVm) {
		this.codGtVm = codGtVm;
	}
	public String getCodGtVmp() {
		return codGtVmp;
	}
	public void setCodGtVmp(String codGtVmp) {
		this.codGtVmp = codGtVmp;
	}
	public String getNomGtVmp() {
		return nomGtVmp;
	}
	public void setNomGtVmp(String nomGtVmp) {
		this.nomGtVmp = nomGtVmp;
	}
	public String getNomLABO() {
		return nomLABO;
	}
	public void setNomLABO(String nomLABO) {
		this.nomLABO = nomLABO;
	}
	 

	  
	

}
