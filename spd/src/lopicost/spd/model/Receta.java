package lopicost.spd.model;

import java.io.Serializable;
import java.util.Date;

public class Receta implements Serializable {
	  
	  private Date fechaHoraProceso;		
	  private String CIP; 			
	  private String Codigo;				
	  private String Descripcion;					
	  private String Cantidad;		
	  private String Pauta;			
	  private String FechaInicio;			
	  private String FechaFinal;			
	  private String Cronico;						
	  private String dosiEsmorzar;						
	  private String dosiDinar;						
	  private String dosiBerenar;			
	  private String dosiSopar; 			
	  private String dosiRessopo;				
	  private String idDivisionResidencia; 			


	  public Receta() {
		super();
	  }


	public Date getFechaHoraProceso() {
		return fechaHoraProceso;
	}


	public void setFechaHoraProceso(Date fechaHoraProceso) {
		this.fechaHoraProceso = fechaHoraProceso;
	}


	public String getCIP() {
		return CIP;
	}


	public void setCIP(String cIP) {
		CIP = cIP;
	}


	public String getCodigo() {
		return Codigo;
	}


	public void setCodigo(String codigo) {
		Codigo = codigo;
	}


	public String getDescripcion() {
		return Descripcion;
	}


	public void setDescripcion(String descripcion) {
		Descripcion = descripcion;
	}


	public String getCantidad() {
		return Cantidad;
	}


	public void setCantidad(String cantidad) {
		Cantidad = cantidad;
	}


	public String getPauta() {
		return Pauta;
	}


	public void setPauta(String pauta) {
		Pauta = pauta;
	}


	public String getFechaInicio() {
		return FechaInicio;
	}


	public void setFechaInicio(String fechaInicio) {
		FechaInicio = fechaInicio;
	}


	public String getFechaFinal() {
		return FechaFinal;
	}


	public void setFechaFinal(String fechaFinal) {
		FechaFinal = fechaFinal;
	}


	public String getCronico() {
		return Cronico;
	}


	public void setCronico(String cronico) {
		Cronico = cronico;
	}


	public String getDosiEsmorzar() {
		return dosiEsmorzar;
	}


	public void setDosiEsmorzar(String dosiEsmorzar) {
		this.dosiEsmorzar = dosiEsmorzar;
	}


	public String getDosiDinar() {
		return dosiDinar;
	}


	public void setDosiDinar(String dosiDinar) {
		this.dosiDinar = dosiDinar;
	}


	public String getDosiBerenar() {
		return dosiBerenar;
	}


	public void setDosiBerenar(String dosiBerenar) {
		this.dosiBerenar = dosiBerenar;
	}


	public String getDosiSopar() {
		return dosiSopar;
	}


	public void setDosiSopar(String dosiSopar) {
		this.dosiSopar = dosiSopar;
	}


	public String getDosiRessopo() {
		return dosiRessopo;
	}


	public void setDosiRessopo(String dosiRessopo) {
		this.dosiRessopo = dosiRessopo;
	}


	public String getIdDivisionResidencia() {
		return idDivisionResidencia;
	}


	public void setIdDivisionResidencia(String idDivisionResidencia) {
		this.idDivisionResidencia = idDivisionResidencia;
	}


}
