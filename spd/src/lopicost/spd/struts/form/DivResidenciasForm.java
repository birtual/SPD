package lopicost.spd.struts.form;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lopicost.spd.model.DivisionResidencia;
import lopicost.spd.model.Farmacia;
import lopicost.spd.model.Residencia;
import lopicost.spd.model.Robot;

public class DivResidenciasForm   extends GenericForm {

	  private int oidResidencia;
	  private int oidDivisionResidencia;
	  private String idDivisionResidencia;
	  private String nombreDivisionResidencia;
	  private String idResidencia;
	  private DivisionResidencia divisionResidencia;
	  private Residencia residencia;
	  private Farmacia farmacia;
	  private Robot robot;
	  private String idFarmacia;
	  private String idRobot;
	  private String idLayout;
	  private String nombreBolsa;
	  private String idProcessIospd;
	  private Date fechaAlta;
	  private int extRE;
	  private int extRE_diaSemana;
	  private String extRE_diaSemanaLiteral;
	  private String tipoCLIfarmatic;
	  private String locationId;
	  
	  private List<DivisionResidencia> listaDivisionResidencias= new ArrayList();

		
		
	public int getOidResidencia() {
		return oidResidencia;
	}
	public void setOidResidencia(int oidResidencia) {
		this.oidResidencia = oidResidencia;
	}
	public int getOidDivisionResidencia() {
		return oidDivisionResidencia;
	}
	public void setOidDivisionResidencia(int oidDivisionResidencia) {
		this.oidDivisionResidencia = oidDivisionResidencia;
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
	public Residencia getResidencia() {
		return residencia;
	}
	public void setResidencia(Residencia residencia) {
		this.residencia = residencia;
	}
	public Farmacia getFarmacia() {
		return farmacia;
	}
	public void setFarmacia(Farmacia farmacia) {
		this.farmacia = farmacia;
	}
	public Robot getRobot() {
		return robot;
	}
	public void setRobot(Robot robot) {
		this.robot = robot;
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
	public String getIdLayout() {
		return idLayout;
	}
	public void setIdLayout(String idLayout) {
		this.idLayout = idLayout;
	}
	public String getNombreBolsa() {
		return nombreBolsa;
	}
	public void setNombreBolsa(String nombreBolsa) {
		this.nombreBolsa = nombreBolsa;
	}
	public String getIdProcessIospd() {
		return idProcessIospd;
	}
	public void setIdProcessIospd(String idProcessIospd) {
		this.idProcessIospd = idProcessIospd;
	}
	public Date getFechaAlta() {
		return fechaAlta;
	}
	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
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
	public String getTipoCLIfarmatic() {
		return tipoCLIfarmatic;
	}
	public void setTipoCLIfarmatic(String tipoCLIfarmatic) {
		this.tipoCLIfarmatic = tipoCLIfarmatic;
	}
	public String getLocationId() {
		return locationId;
	}
	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}
	public DivisionResidencia getDivisionResidencia() {
		return divisionResidencia;
	}
	public void setDivisionResidencia(DivisionResidencia divisionResidencia) {
		this.divisionResidencia = divisionResidencia;
	}
	public List<DivisionResidencia> getListaDivisionResidencias() {
		return listaDivisionResidencias;
	}
	public void setListaDivisionResidencias(List<DivisionResidencia> listaDivisionResidencias) {
		this.listaDivisionResidencias = listaDivisionResidencias;
	}
		

		    


	


	
	
}
