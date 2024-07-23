package lopicost.spd.model;

import java.util.Date;
import java.util.List;

import lopicost.spd.struts.bean.DiasTomaBean;
import lopicost.spd.struts.bean.HorasTomaBean;
import lopicost.spd.utils.StringUtil;

public class SpdProduccionDetalle {
	
	  private int oidProduccionDetalle;
	  private String idProduccionDetalle;
	  private int oidProduccion;
	  private Date fechaCreacion;
	  private String idDivisionResidencia;
	  private String resiCIP;
	  private String resiNombrePaciente;
	  private String resiCn;
	  private String resiMedicamento;
	  private String resiFormaMedicacion;
	  private String resiInicioTratamiento;
	  private String resiFinTratamiento;
	  private String resiObservaciones;
	  private String resiComentarios;
	  private String resiVariante;
	  private String resiSiPrecisa;
	  private String resiViaAdministracion;
	  private String resiPeriodo;
	  private String resiD1;	  
	  private String resiD2;	  
	  private String resiD3;	  
	  private String resiD4;	  
	  private String resiD5;	  
	  private String resiD6;	  
	  private String resiD7;	  
	  private String resiToma1;	  
	  private String resiToma2;	  
	  private String resiToma3;	  
	  private String resiToma4;	  
	  private String resiToma5;	  
	  private String resiToma6;	  
	  private String resiToma7;	  
	  private String resiToma8;	  
	  private String resiToma9;	  
	  private String resiToma10;	  
	  private String resiToma11;	  
	  private String resiToma12;	  
	  private String resiToma13;	  
	  private String resiToma14;	  
	  private String resiToma15;	  
	  private String resiToma16;	  
	  private String resiToma17;	  
	  private String resiToma18;	  
	  private String resiToma19;	  
	  private String resiToma20;	  
	  private String resiToma21;	  
	  private String resiToma22;
	  private String resiToma23;	  
	  private String resiToma24;	
	  private boolean resiDiasAutomaticos=true;
	  private boolean resiEsExcepcion=true;
	  private boolean valido=true;
	  private String estado;
	  private String sustResultLog;
	  private String sustMensajesInfo;
	  private String sustMensajesAlerta;
	  
	  private String spdCnFinal;
	  private String spdNombreBolsa;
	  private String spdNomGtVmp;
	  private String spdNomGtVmpp;
	  private String spdFormaMedicacion;
	  private String spdAccionBolsa;
	  private String spdComentarioLopicost;

	  private String spdPeriodo;
	  private Date spdPeriodoAPartirDeFecha;
	  private String spdPeriodoDíasConcretos;
	  private String spdCIP;
	  
	  private boolean persistir=true;
	  private String resultLog;
	  private int row;

	
	     


	public SpdProduccionDetalle() {
		super();
	}





	public int getOidProduccionDetalle() {
		return oidProduccionDetalle;
	}





	public void setOidProduccionDetalle(int oidProduccionDetalle) {
		this.oidProduccionDetalle = oidProduccionDetalle;
	}





	public String getIdProduccionDetalle() {
		return idProduccionDetalle;
	}





	public void setIdProduccionDetalle(String idProduccionDetalle) {
		this.idProduccionDetalle = idProduccionDetalle;
	}





	public int getOidProduccion() {
		return oidProduccion;
	}





	public void setOidProduccion(int oidProduccion) {
		this.oidProduccion = oidProduccion;
	}





	public Date getFechaCreacion() {
		return fechaCreacion;
	}





	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}





	public String getIdDivisionResidencia() {
		return idDivisionResidencia;
	}





	public void setIdDivisionResidencia(String idDivisionResidencia) {
		this.idDivisionResidencia = idDivisionResidencia;
	}





	public String getResiCIP() {
		return resiCIP;
	}





	public void setResiCIP(String resiCIP) {
		this.resiCIP = resiCIP;
	}





	public String getResiNombrePaciente() {
		return resiNombrePaciente;
	}





	public void setResiNombrePaciente(String resiNombrePaciente) {
		this.resiNombrePaciente = resiNombrePaciente;
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





	public String getResiFormaMedicacion() {
		return resiFormaMedicacion;
	}





	public void setResiFormaMedicacion(String resiFormaMedicacion) {
		this.resiFormaMedicacion = resiFormaMedicacion;
	}





	public String getResiInicioTratamiento() {
		return resiInicioTratamiento;
	}





	public void setResiInicioTratamiento(String resiInicioTratamiento) {
		this.resiInicioTratamiento = resiInicioTratamiento;
	}





	public String getResiFinTratamiento() {
		return resiFinTratamiento;
	}





	public void setResiFinTratamiento(String resiFinTratamiento) {
		this.resiFinTratamiento = resiFinTratamiento;
	}





	public String getResiObservaciones() {
		return resiObservaciones;
	}





	public void setResiObservaciones(String resiObservaciones) {
		this.resiObservaciones = resiObservaciones;
	}





	public String getResiComentarios() {
		return resiComentarios;
	}





	public void setResiComentarios(String resiComentarios) {
		this.resiComentarios = resiComentarios;
	}





	public String getResiVariante() {
		return resiVariante;
	}





	public void setResiVariante(String resiVariante) {
		this.resiVariante = resiVariante;
	}





	public String getResiSiPrecisa() {
		return resiSiPrecisa;
	}





	public void setResiSiPrecisa(String resiSiPrecisa) {
		this.resiSiPrecisa = resiSiPrecisa;
	}





	public String getResiViaAdministracion() {
		return resiViaAdministracion;
	}





	public void setResiViaAdministracion(String resiViaAdministracion) {
		this.resiViaAdministracion = resiViaAdministracion;
	}





	public String getResiPeriodo() {
		return resiPeriodo;
	}





	public void setResiPeriodo(String resiPeriodo) {
		this.resiPeriodo = resiPeriodo;
	}





	public String getResiD1() {
		return resiD1;
	}





	public void setResiD1(String resiD1) {
		this.resiD1 = resiD1;
	}





	public String getResiD2() {
		return resiD2;
	}





	public void setResiD2(String resiD2) {
		this.resiD2 = resiD2;
	}





	public String getResiD3() {
		return resiD3;
	}





	public void setResiD3(String resiD3) {
		this.resiD3 = resiD3;
	}





	public String getResiD4() {
		return resiD4;
	}





	public void setResiD4(String resiD4) {
		this.resiD4 = resiD4;
	}





	public String getResiD5() {
		return resiD5;
	}





	public void setResiD5(String resiD5) {
		this.resiD5 = resiD5;
	}





	public String getResiD6() {
		return resiD6;
	}





	public void setResiD6(String resiD6) {
		this.resiD6 = resiD6;
	}





	public String getResiD7() {
		return resiD7;
	}





	public void setResiD7(String resiD7) {
		this.resiD7 = resiD7;
	}





	public String getResiToma1() {
		return resiToma1;
	}





	public void setResiToma1(String resiToma1) {
		this.resiToma1 = resiToma1;
	}





	public String getResiToma2() {
		return resiToma2;
	}





	public void setResiToma2(String resiToma2) {
		this.resiToma2 = resiToma2;
	}





	public String getResiToma3() {
		return resiToma3;
	}





	public void setResiToma3(String resiToma3) {
		this.resiToma3 = resiToma3;
	}





	public String getResiToma4() {
		return resiToma4;
	}





	public void setResiToma4(String resiToma4) {
		this.resiToma4 = resiToma4;
	}





	public String getResiToma5() {
		return resiToma5;
	}





	public void setResiToma5(String resiToma5) {
		this.resiToma5 = resiToma5;
	}





	public String getResiToma6() {
		return resiToma6;
	}





	public void setResiToma6(String resiToma6) {
		this.resiToma6 = resiToma6;
	}





	public String getResiToma7() {
		return resiToma7;
	}





	public void setResiToma7(String resiToma7) {
		this.resiToma7 = resiToma7;
	}





	public String getResiToma8() {
		return resiToma8;
	}





	public void setResiToma8(String resiToma8) {
		this.resiToma8 = resiToma8;
	}





	public String getResiToma9() {
		return resiToma9;
	}





	public void setResiToma9(String resiToma9) {
		this.resiToma9 = resiToma9;
	}





	public String getResiToma10() {
		return resiToma10;
	}





	public void setResiToma10(String resiToma10) {
		this.resiToma10 = resiToma10;
	}





	public String getResiToma11() {
		return resiToma11;
	}





	public void setResiToma11(String resiToma11) {
		this.resiToma11 = resiToma11;
	}





	public String getResiToma12() {
		return resiToma12;
	}





	public void setResiToma12(String resiToma12) {
		this.resiToma12 = resiToma12;
	}





	public String getResiToma13() {
		return resiToma13;
	}





	public void setResiToma13(String resiToma13) {
		this.resiToma13 = resiToma13;
	}





	public String getResiToma14() {
		return resiToma14;
	}





	public void setResiToma14(String resiToma14) {
		this.resiToma14 = resiToma14;
	}





	public String getResiToma15() {
		return resiToma15;
	}





	public void setResiToma15(String resiToma15) {
		this.resiToma15 = resiToma15;
	}





	public String getResiToma16() {
		return resiToma16;
	}





	public void setResiToma16(String resiToma16) {
		this.resiToma16 = resiToma16;
	}





	public String getResiToma17() {
		return resiToma17;
	}





	public void setResiToma17(String resiToma17) {
		this.resiToma17 = resiToma17;
	}





	public String getResiToma18() {
		return resiToma18;
	}





	public void setResiToma18(String resiToma18) {
		this.resiToma18 = resiToma18;
	}





	public String getResiToma19() {
		return resiToma19;
	}





	public void setResiToma19(String resiToma19) {
		this.resiToma19 = resiToma19;
	}





	public String getResiToma20() {
		return resiToma20;
	}





	public void setResiToma20(String resiToma20) {
		this.resiToma20 = resiToma20;
	}





	public String getResiToma21() {
		return resiToma21;
	}





	public void setResiToma21(String resiToma21) {
		this.resiToma21 = resiToma21;
	}





	public String getResiToma22() {
		return resiToma22;
	}





	public void setResiToma22(String resiToma22) {
		this.resiToma22 = resiToma22;
	}





	public String getResiToma23() {
		return resiToma23;
	}





	public void setResiToma23(String resiToma23) {
		this.resiToma23 = resiToma23;
	}





	public String getResiToma24() {
		return resiToma24;
	}





	public void setResiToma24(String resiToma24) {
		this.resiToma24 = resiToma24;
	}





	public boolean isResiDiasAutomaticos() {
		return resiDiasAutomaticos;
	}





	public void setResiDiasAutomaticos(boolean resiDiasAutomaticos) {
		this.resiDiasAutomaticos = resiDiasAutomaticos;
	}





	public boolean isResiEsExcepcion() {
		return resiEsExcepcion;
	}





	public void setResiEsExcepcion(boolean resiEsExcepcion) {
		this.resiEsExcepcion = resiEsExcepcion;
	}





	public boolean isValido() {
		return valido;
	}





	public void setValido(boolean valido) {
		this.valido = valido;
	}





	public String getEstado() {
		return estado;
	}





	public void setEstado(String estado) {
		this.estado = estado;
	}





	public String getSustResultLog() {
		return sustResultLog;
	}





	public void setSustResultLog(String sustResultLog) {
		this.sustResultLog = sustResultLog;
	}





	public String getSustMensajesInfo() {
		return sustMensajesInfo;
	}





	public void setSustMensajesInfo(String sustMensajesInfo) {
		this.sustMensajesInfo = sustMensajesInfo;
	}





	public String getSustMensajesAlerta() {
		return sustMensajesAlerta;
	}





	public void setSustMensajesAlerta(String sustMensajesAlerta) {
		this.sustMensajesAlerta = sustMensajesAlerta;
	}





	public String getSpdCnFinal() {
		return spdCnFinal;
	}





	public void setSpdCnFinal(String spdCnFinal) {
		this.spdCnFinal = spdCnFinal;
	}





	public String getSpdNombreBolsa() {
		return spdNombreBolsa;
	}





	public void setSpdNombreBolsa(String spdNombreBolsa) {
		this.spdNombreBolsa = spdNombreBolsa;
	}





	public String getSpdNomGtVmp() {
		return spdNomGtVmp;
	}





	public void setSpdNomGtVmp(String spdNomGtVmp) {
		this.spdNomGtVmp = spdNomGtVmp;
	}





	public String getSpdNomGtVmpp() {
		return spdNomGtVmpp;
	}





	public void setSpdNomGtVmpp(String spdNomGtVmpp) {
		this.spdNomGtVmpp = spdNomGtVmpp;
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





	public String getSpdComentarioLopicost() {
		return spdComentarioLopicost;
	}





	public void setSpdComentarioLopicost(String spdComentarioLopicost) {
		this.spdComentarioLopicost = spdComentarioLopicost;
	}





	public String getSpdPeriodo() {
		return spdPeriodo;
	}





	public void setSpdPeriodo(String spdPeriodo) {
		this.spdPeriodo = spdPeriodo;
	}





	public Date getSpdPeriodoAPartirDeFecha() {
		return spdPeriodoAPartirDeFecha;
	}





	public void setSpdPeriodoAPartirDeFecha(Date spdPeriodoAPartirDeFecha) {
		this.spdPeriodoAPartirDeFecha = spdPeriodoAPartirDeFecha;
	}





	public String getSpdPeriodoDíasConcretos() {
		return spdPeriodoDíasConcretos;
	}





	public void setSpdPeriodoDíasConcretos(String spdPeriodoDíasConcretos) {
		this.spdPeriodoDíasConcretos = spdPeriodoDíasConcretos;
	}





	public String getSpdCIP() {
		return spdCIP;
	}





	public void setSpdCIP(String spdCIP) {
		this.spdCIP = spdCIP;
	}





	public boolean isPersistir() {
		return persistir;
	}





	public void setPersistir(boolean persistir) {
		this.persistir = persistir;
	}





	public String getResultLog() {
		return resultLog;
	}





	public void setResultLog(String resultLog) {
		this.resultLog = resultLog;
	}





	public int getRow() {
		return row;
	}





	public void setRow(int row) {
		this.row = row;
	}

	  
	



	

	
}
