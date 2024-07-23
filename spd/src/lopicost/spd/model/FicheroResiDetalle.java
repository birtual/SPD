package lopicost.spd.model;

import java.util.Date;
import java.util.List;

import lopicost.spd.struts.bean.DiasTomaBean;
import lopicost.spd.struts.bean.HorasTomaBean;
import lopicost.spd.utils.StringUtil;

public class FicheroResiDetalle {
	
	  private int oidGestFicheroResiBolsa;
	  private String idProceso;
	  private String idDivisionResidencia;
	  private Date fechaHoraProceso;
	  private String resiCIP;
	  private String resiNombrePaciente;
	  private String resiCn;
	  private String resiMedicamento;
	  private String resiFormaMedicacion;
	  private String resiInicioTratamiento;
	  private String resiFinTratamiento;
	  private String resiObservaciones;
	  private String resiComentarios;
	  private String resiSiPrecisa;
	  private String resiViaAdministracion;
	  private String spdCnFinal;
	  private String spdNombreBolsa;
	  private String spdFormaMedicacion;
	  private String spdAccionBolsa;
	  private String spdComentarioLopicost;

	  private DiasTomaBean diasToma;
	  private HorasTomaBean horasToma;
	  
	  private boolean valido=true;
	  private boolean persistir=true;
	  private String resultLog;
	  private int row;

	  
	  
	  public int getRow() {
		return row;
	}


	public void setRow(int row) {
		this.row = row;
	}


	public FicheroResiDetalle() {
		super();
	}

	  
	

	public int getOidGestFicheroResiBolsa() {
		return oidGestFicheroResiBolsa;
	}


	public void setOidGestFicheroResiBolsa(int oidGestFicheroResiBolsa) {
		this.oidGestFicheroResiBolsa = oidGestFicheroResiBolsa;
	}


	

	
	public String getIdProceso() {		return idProceso;	}
	public void setIdProceso(String idProceso) {		this.idProceso = idProceso;	}
	public String getIdDivisionResidencia() {		return idDivisionResidencia;	}
	public void setIdDivisionResidencia(String idDivisionResidencia) {		this.idDivisionResidencia = idDivisionResidencia;}
	public Date getFechaHoraProceso() {		return fechaHoraProceso;	}
	public void setFechaHoraProceso(Date fechaHoraProceso) {		this.fechaHoraProceso = fechaHoraProceso;	}
	public String getResiCIP() {		return resiCIP;	}
	public void setResiCIP(String resiCIP) {		this.resiCIP = resiCIP;	}
	public String getResiNombrePaciente() {		return resiNombrePaciente;	}
	public void setResiNombrePaciente(String resiNombrePaciente) {		this.resiNombrePaciente = resiNombrePaciente;	}
	public String getResiCn() {		return resiCn;	}
	public void setResiCn(String resiCn) {		this.resiCn = resiCn;	}
	public String getResiMedicamento() {		return resiMedicamento;	}
	public void setResiMedicamento(String resiMedicamento) {		this.resiMedicamento = resiMedicamento;	}
	public String getResiFormaMedicacion() {		return resiFormaMedicacion;	}
	public String getResiInicioTratamiento() {		return resiInicioTratamiento;	}
	public void setResiInicioTratamiento(String resiInicioTratamiento) {		this.resiInicioTratamiento = resiInicioTratamiento;	}
	public String getResiFinTratamiento() {		return resiFinTratamiento;	}
	public void setResiFinTratamiento(String resiFinTratamiento) {		this.resiFinTratamiento = resiFinTratamiento;	}
	public String getResiObservaciones() {		return resiObservaciones;	}
	public void setResiObservaciones(String resiObservaciones) {		this.resiObservaciones = resiObservaciones;	}
	public String getResiComentarios() {		return resiComentarios;	}
	public void setResiComentarios(String resiComentarios) {		this.resiComentarios = resiComentarios;	}
	public String getResiSiPrecisa() {					return resiSiPrecisa;	}
	public void setResiSiPrecisa(String resiSiPrecisa) {		this.resiSiPrecisa = resiSiPrecisa;	}
	public String getResiViaAdministracion() {			return resiViaAdministracion;	}
	public void setResiViaAdministracion(String resiViaAdministracion) {		this.resiViaAdministracion = resiViaAdministracion;	}
	public String getSpdCnFinal() {						return spdCnFinal;	}
	public void setSpdCnFinal(String spdCnFinal) {		this.spdCnFinal = spdCnFinal;	}
	public String getSpdNombreBolsa() {					return spdNombreBolsa;	}
	public void setSpdNombreBolsa(String spdNombreBolsa) {		this.spdNombreBolsa = spdNombreBolsa;	}
	public String getSpdFormaMedicacion() {				return spdFormaMedicacion;	}
	public void setSpdFormaMedicacion(String spdFormaMedicacion) {		this.spdFormaMedicacion = spdFormaMedicacion;	}
	public String getSpdAccionBolsa() {					return spdAccionBolsa;	}
	public void setSpdAccionBolsa(String spdAccionBolsa) {		this.spdAccionBolsa = spdAccionBolsa;	}
	public String getSpdComentarioLopicost() {			return spdComentarioLopicost;	}
	public void setSpdComentarioLopicost(String spdComentarioLopicost) {		this.spdComentarioLopicost = spdComentarioLopicost;	}
	
	public boolean getPersistir() {						return persistir;	}
	public void setPersistir(boolean persistir) {		this.persistir = persistir;	} 
	public boolean isValido() {							return valido;	}
	public void setValido(boolean valido) {				this.valido = valido;	}
	
	public int getValido() 
	{	
		int result=1;
		if(!isValido()) result=0;
		return result;	
	}


	public String getResultLog() {
		return resultLog;
	}


	public void setResultLog(String r) {
		this.resultLog = r;
	}


	public void setResiFormaMedicacion(String resiFormaMedicacion) {
		this.resiFormaMedicacion = resiFormaMedicacion;
	}



	

	
}
