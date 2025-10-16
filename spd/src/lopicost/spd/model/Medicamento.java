package lopicost.spd.model;

import lopicost.spd.model.rd.AspectoMedicamento;

public abstract class Medicamento {
    private String cn;
    private String nombreConsejoCn;
	private String nombreMedicamentoBolsa;
    
	private String codGtVm ="";					// grupo VM - Virtual Medicinal  (igual principio activo).  
	private String nomGtVm ="";				
	private String codGtVmp ="";					// grupo VMP - Virtual Medicinal Product (igual principio activo, dosis y forma farmacéutica).  
	private String nomGtVmp ="";				
	private String codGtVmpp =""; 				// grupo VMPP - Virtual Medicinal Product Package (igual principio activo, dosis, forma farmacéutica y número de unidades de dosificación).
	private String nomGtVmpp ="";					

    private String lab;
    private String lote;
    private String caducidad;
    private String numeroSerie;
    private AspectoMedicamento aspectoMedicamento;
    
    private String  idProceso;
    private String  fechaCorte;
    private String  idRobot;
    private String  idDivisionResidencia;
    private String  diaInicioSPD;
    private String  CIP;

    
	public String getNombreMedicamentoBolsa() {
		return nombreMedicamentoBolsa;
	}
	public void setNombreMedicamentoBolsa(String nombreMedicamentoBolsa) {
		this.nombreMedicamentoBolsa = nombreMedicamentoBolsa;
	}
	public String getNombreConsejoCn() {
		return nombreConsejoCn;
	}
	public void setNombreConsejoCn(String nombreConsejoCn) {
		this.nombreConsejoCn = nombreConsejoCn;
	}
	public String getLab() {
		return lab;
	}
	public void setLab(String lab) {
		this.lab = lab;
	}
	public AspectoMedicamento getAspectoMedicamento() {
		return aspectoMedicamento;
	}
	public void setAspectoMedicamento(AspectoMedicamento identificacion) {
		this.aspectoMedicamento = identificacion;
	}
	public String getCn() {
		return cn;
	}
	public void setCn(String cn) {
		this.cn = cn;
	}
	public String getLote() {
		return lote;
	}
	public void setLote(String lote) {
		this.lote = lote;
	}
	public String getCaducidad() {
		return caducidad;
	}
	public void setCaducidad(String caducidad) {
		this.caducidad = caducidad;
	}
	public String getNumeroSerie() {
		return numeroSerie;
	}
	public void setNumeroSerie(String numeroSerie) {
		this.numeroSerie = numeroSerie;
	}
	public String getCodGtVm() {
		return codGtVm;
	}
	public void setCodGtVm(String codGtVm) {
		this.codGtVm = codGtVm;
	}
	public String getNomGtVm() {
		return nomGtVm;
	}
	public void setNomGtVm(String nomGtVm) {
		this.nomGtVm = nomGtVm;
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
	public String getCodGtVmpp() {
		return codGtVmpp;
	}
	public void setCodGtVmpp(String codGtVmpp) {
		this.codGtVmpp = codGtVmpp;
	}
	public String getNomGtVmpp() {
		return nomGtVmpp;
	}
	public void setNomGtVmpp(String nomGtVmpp) {
		this.nomGtVmpp = nomGtVmpp;
	}
	public String getIdProceso() {
		return idProceso;
	}
	public void setIdProceso(String idProceso) {
		this.idProceso = idProceso;
	}
	public String getFechaCorte() {
		return fechaCorte;
	}
	public void setFechaCorte(String fechaCorte) {
		this.fechaCorte = fechaCorte;
	}
	public String getIdRobot() {
		return idRobot;
	}
	public void setIdRobot(String idRobot) {
		this.idRobot = idRobot;
	}
	public String getIdDivisionResidencia() {
		return idDivisionResidencia;
	}
	public void setIdDivisionResidencia(String idDivisionResidencia) {
		this.idDivisionResidencia = idDivisionResidencia;
	}
	public String getDiaInicioSPD() {
		return diaInicioSPD;
	}
	public void setDiaInicioSPD(String diaInicioSPD) {
		this.diaInicioSPD = diaInicioSPD;
	}
	public String getCIP() {
		return CIP;
	}
	public void setCIP(String cIP) {
		CIP = cIP;
	}

    
}
