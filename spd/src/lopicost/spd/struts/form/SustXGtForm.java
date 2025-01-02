package lopicost.spd.struts.form;

import lopicost.spd.model.SustXGtvmp;

public class SustXGtForm   extends GenericForm {

	private int oidSustXComposicion;
	private boolean verSoloGestionados;
	
	private String codGtVmp;
	private String codGtVmpp;
	private String campoGoogle;
	private String filtroLogico;
	private String filtroCodGtVm;
	private String filtroNomGtVm;
	private String filtroCodGtVmp;
	private String filtroNomGtVmp;
	private String filtroCodGtVmpp;
	private String filtroNomGtVmpp;	
	private String filtroCodiLaboratorio  = "";
	private String filtroNombreLaboratorio  = "";
	private String comentarios = "";
	private float rentabilidad = 0;
	private float ponderacion= 0;
	private String sustituible = "";
	private String tolva = "";
	private SustXGtvmp sustXGtPadre;
	
	public String getFiltroCodiLaboratorio() {
		return filtroCodiLaboratorio;
	}
	public void setFiltroCodiLaboratorio(String filtroCodiLaboratorio) {
		this.filtroCodiLaboratorio = filtroCodiLaboratorio;
	}
	
	public String getFiltroNombreLaboratorio() {
		return filtroNombreLaboratorio;
	}
	public void setFiltroNombreLaboratorio(String filtroNombreLaboratorio) {
		this.filtroNombreLaboratorio = filtroNombreLaboratorio;
	}
	public boolean isVerSoloGestionados() {
		return verSoloGestionados;
	}
	public void setVerSoloGestionados(boolean verSoloGestionados) {
		this.verSoloGestionados = verSoloGestionados;
	}
	public int getOidSustXComposicion() {
		return oidSustXComposicion;
	}
	public void setOidSustXComposicion(int oidSustXComposicion) {
		this.oidSustXComposicion = oidSustXComposicion;
	}
	public String getCodGtVmp() {
		return codGtVmp;
	}
	public void setCodGtVmp(String codGtGvmp) {
		this.codGtVmp = codGtGvmp;
	}
	public String getCodGtVmpp() {
		return codGtVmpp;
	}
	public void setCodGtVmpp(String codGtGvmpp) {
		this.codGtVmpp = codGtGvmpp;
	}

	public String getCampoGoogle() {
		return campoGoogle;
	}
	public void setCampoGoogle(String campoGoogle) {
		this.campoGoogle = campoGoogle;
	}
	
	public String getFiltroCodGtVm() {
		return filtroCodGtVm;
	}
	public void setFiltroCodGtVm(String filtroCodGtVm) {
		this.filtroCodGtVm = filtroCodGtVm;
	}
	public String getFiltroNomGtVm() {
		return filtroNomGtVm;
	}
	public void setFiltroNomGtVm(String filtroNomGtVm) {
		this.filtroNomGtVm = filtroNomGtVm;
	}
	public String getFiltroCodGtVmp() {
		return filtroCodGtVmp;
	}
	public void setFiltroCodGtVmp(String filtroCodGtVmp) {
		this.filtroCodGtVmp = filtroCodGtVmp;
	}
	public String getFiltroNomGtVmp() {
		return filtroNomGtVmp;
	}
	public void setFiltroNomGtVmp(String filtroNomGtVmp) {
		this.filtroNomGtVmp = filtroNomGtVmp;
	}
	public String getFiltroCodGtVmpp() {
		return filtroCodGtVmpp;
	}
	public void setFiltroCodGtVmpp(String filtroCodGtVmpp) {
		this.filtroCodGtVmpp = filtroCodGtVmpp;
	}
	public String getFiltroNomGtVmpp() {
		return filtroNomGtVmpp;
	}
	public void setFiltroNomGtVmpp(String filtroNomGtVmpp) {
		this.filtroNomGtVmpp = filtroNomGtVmpp;
	}
	public String getComentarios() {
		return comentarios;
	}
	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}
	public float getRentabilidad() {
		return rentabilidad;
	}
	public void setRentabilidad(float rentabilidad) {
		this.rentabilidad = rentabilidad;
	}
	public float getPonderacion() {
		return ponderacion;
	}
	public void setPonderacion(float ponderacion) {
		this.ponderacion = ponderacion;
	}
	public String getSustituible() {
		return sustituible;
	}
	public void setSustituible(String sustituible) {
		this.sustituible = sustituible;
	}
	public String getTolva() {
		return tolva;
	}
	public void setTolva(String tolva) {
		this.tolva = tolva;
	}
	public SustXGtvmp getSustXGtPadre() {
		return sustXGtPadre;
	}
	public void setSustXGtPadre(SustXGtvmp padre) {
		this.sustXGtPadre = padre;
	}
	public String getFiltroLogico() {
		return filtroLogico;
	}
	public void setFiltroLogico(String filtroLogico) {
		this.filtroLogico = filtroLogico;
	}




	
	
	
}
