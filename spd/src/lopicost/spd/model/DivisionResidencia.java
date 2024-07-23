package lopicost.spd.model;

import java.io.Serializable;
import java.util.Date;

public class DivisionResidencia implements Serializable {
	  private int oidDivisionResidencia;
	  private String idDivisionResidencia;
	  private String nombreDivisionResidencia;
	  private String idResidencia;
	  private String idFarmacia;
	  private String idRobot;
	  private String idLayout;
	  private String nombreBolsa;
	  private String idProcessIospd;
	  private Date fechaAlta;
	  private int extRE;
	  private int extRE_diaSemana;
	  private String extRE_diaSemanaLiteral;

	  
	public String getNombreBolsa() {		return nombreBolsa;	}	public void setNombreBolsa(String nombreBolsa) {		this.nombreBolsa = nombreBolsa;	}
	public String getIdProcessIospd() {		return idProcessIospd;	}	public void setIdProcessIospd(String idProcessIospd) {		this.idProcessIospd = idProcessIospd;	}
	public DivisionResidencia() {
		super();
	}


	public String getIdDivisionResidencia() {
		return idDivisionResidencia;
	}


	public void setIdDivisionResidencia(String idDivisionResidencia) {
		this.idDivisionResidencia = idDivisionResidencia;
	}


	public String getNombreDivisionResidencia() {
		return nombreDivisionResidencia;
	}


	public void setNombreDivisionResidencia(String nombreDivisionResidencia) {
		this.nombreDivisionResidencia = nombreDivisionResidencia;
	}


	public String getIdResidencia() {
		return idResidencia;
	}


	public void setIdResidencia(String idResidencia) {
		this.idResidencia = idResidencia;
	}


	public String getIdFarmacia() {
		return idFarmacia;
	}


	public void setIdFarmacia(String idFarmacia) {
		this.idFarmacia = idFarmacia;
	}


	public String getIdRobot() {
		return idRobot;
	}


	public void setIdRobot(String idRobot) {
		this.idRobot = idRobot;
	}


	public Date getFechaAlta() {
		return fechaAlta;
	}


	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}


	public int getOidDivisionResidencia() {
		return oidDivisionResidencia;
	}


	public void setOidDivisionResidencia(int oidDivisionResidencia) {
		this.oidDivisionResidencia = oidDivisionResidencia;
	}


	public int getExtRE() {
		return extRE;
	}


	public void setExtRE(int extRE) {
		this.extRE = extRE;
	}


	public int getExtRE_diaSemana() {
		return extRE_diaSemana;
	}


	public void setExtRE_diaSemana(int extRE_diaSemana) {
		this.extRE_diaSemana = extRE_diaSemana;
	}


	public String getExtRE_diaSemanaLiteral() {
		return extRE_diaSemanaLiteral;
	}


	public void setExtRE_diaSemanaLiteral(String extRE_diaSemanaLiteral) {
		this.extRE_diaSemanaLiteral = extRE_diaSemanaLiteral;
	}
	public String getIdLayout() {
		return idLayout;
	}
	public void setIdLayout(String idLayout) {
		this.idLayout = idLayout;
	}

	
	
}
