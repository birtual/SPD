package lopicost.spd.struts.form;

import lopicost.spd.model.Nivel1;
import lopicost.spd.model.Nivel2;
import lopicost.spd.model.Nivel3;

public class SustXGtForm   extends GenericForm {

	private int oidSustXComposicion;
	private boolean filtroVerTodoConsejo;
	private boolean filtroVerFarmacias;
	
	
	private String codGtVmp;
	private String codGtVmpp;
	private String campoGoogle;
	//private String filtroLogico;
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
	private Nivel1 nivel1;
	private Nivel2 nivel2;
	private Nivel3 nivel3;
	
	public int getOidSustXComposicion() {
		return oidSustXComposicion;
	}
	public void setOidSustXComposicion(int oidSustXComposicion) {
		this.oidSustXComposicion = oidSustXComposicion;
	}
	public boolean isFiltroVerTodoConsejo() {
		return filtroVerTodoConsejo;
	}
	public void setFiltroVerTodoConsejo(boolean filtroVerGestionados) {
		this.filtroVerTodoConsejo = filtroVerGestionados;
	}
	public boolean isFiltroVerFarmacias() {
		return filtroVerFarmacias;
	}
	public void setFiltroVerFarmacias(boolean filtroVerFarmacias) {
		this.filtroVerFarmacias = filtroVerFarmacias;
	}
	public String getCodGtVmp() {
		return codGtVmp;
	}
	public void setCodGtVmp(String codGtVmp) {
		this.codGtVmp = codGtVmp;
	}
	public String getCodGtVmpp() {
		return codGtVmpp;
	}
	public void setCodGtVmpp(String codGtVmpp) {
		this.codGtVmpp = codGtVmpp;
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
	public Nivel1 getNivel1() {
		return nivel1;
	}
	public void setNivel1(Nivel1 nivel1) {
		this.nivel1 = nivel1;
	}
	public Nivel2 getNivel2() {
		return nivel2;
	}
	public void setNivel2(Nivel2 nivel2) {
		this.nivel2 = nivel2;
	}
	public Nivel3 getNivel3() {
		return nivel3;
	}
	public void setNivel3(Nivel3 nivel3) {
		this.nivel3 = nivel3;
	}
	

	
}
