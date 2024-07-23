package lopicost.spd.struts.form;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;


public class BdConsejoForm  extends GenericForm {

	
	
	private String cnConsejo = "";
	private String codiLab = "";
	private String nombreCortoOK = "";
	private String nombreLab = "";
	private String filtroCodiLaboratorio  = "";
	//private String filtroListaPrincipioActivo 	= "";		// se cambia a GtVm -- 
	private String filtroCodGtVm 					= "";
	private String filtroNomGtVm 					= "";
	//private String filtroListaConjuntosHomogeneos ="";		// se cambia a GtVmp -- Conjunto homogéneo
	private String filtroCodGtVmp ="";	
	private String filtroNomGtVmp ="";	
	private String filtroCodGtVmpp  				= "";		//GtVmp + Presentacion
	private String filtroNomGtVmpp  				= "";		//GtVmp + Presentacion
	private String filtroPresentacion  = "";
	//private String filtroCodGtVmp ="";
	public String getCnConsejo() {
		return cnConsejo;
	}
	public void setCnConsejo(String cnConsejo) {
		this.cnConsejo = cnConsejo;
	}
	public String getCodiLab() {
		return codiLab;
	}
	public void setCodiLab(String codiLab) {
		this.codiLab = codiLab;
	}
	public String getNombreCortoOK() {
		return nombreCortoOK;
	}
	public void setNombreCortoOK(String nombreCortoOK) {
		this.nombreCortoOK = nombreCortoOK;
	}
	public String getNombreLab() {
		return nombreLab;
	}
	public void setNombreLab(String nombreLab) {
		this.nombreLab = nombreLab;
	}
	public String getFiltroCodiLaboratorio() {
		return filtroCodiLaboratorio;
	}
	public void setFiltroCodiLaboratorio(String filtroCodiLaboratorio) {
		this.filtroCodiLaboratorio = filtroCodiLaboratorio;
	}

	public String getFiltroPresentacion() {
		return filtroPresentacion;
	}
	public void setFiltroPresentacion(String filtroListaPresentacion) {
		this.filtroPresentacion = filtroListaPresentacion;
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




	
	
}
