package lopicost.spd.struts.form;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;


import lopicost.spd.model.SustXComposicion;


public class SustXComposicionForm   extends GenericForm {


	
	private List<SustXComposicion> listaSustXComposicion 		= new ArrayList();

	private int oidSustXComposicion 							= 0;
	private SustXComposicion sustXComposicion 					= new SustXComposicion();
	private String filtroTextoABuscar 							= "";

	//private String filtroListaConjuntosHomogeneos  				= ""; Cambiamos a GTVmpp (GtVmpp)
	private String filtroPresentacion  = "";
	private String filtroCodiLaboratorio  = "";
	private String filtroNombreLaboratorio  = "";
//	private String filtroCodiLaboratorioBiblia  = "";
//	private String filtroNombreLaboratorioBiblia  = "";
	private String filtroCodigoMedicamento  = "";
	private String filtroCodGtVm 	= "";						//filtroListaPrincipioActivo
	private String filtroNomGtVm 	= "";						//filtroListaPrincipioActivo
	private String filtroCodGtVmp 	="";
	private String filtroNomGtVmp 	="";
	private String filtroCodGtVmpp 	="";						//Conj Homog
	private String filtroNomGtVmpp 	="";						//Conj Homog
	private String cnOk  = "";
	private String cn6  = "";
	private String cn7  = "";
	private String nombreConsejo  = "";
	private boolean filtroLabsSoloAsignados=false;
	private boolean filtroCheckedLabsSoloAsignados=false;
	private boolean filtroComposicionSinLabs=false;
	private boolean filtroCheckedComposicionSinLabs=false;
	
	//private String codConjHomog = "";
	//private String nomConjHomog = "";
	private float rentabilidad = 0;
	private float ponderacion= 0;
	private String codiLab = "";
	
	private String codigoGT ="";
	private String grupoTerapeutico ="";
	private String codGtAtcNivel3 =""; 
	private String nomGtAtcNivel3 ="";		// nivel de grupo terapéutico de la ATC (mismo subgrupo farmacológico/terapéutico).
	private String codGtAtcNivel4 ="";
	private String nomGtAtcNivel4 ="";		// nivel de grupo terapéutico de la ATC (mismo subgrupo farmacológico/terapéutico).
	private String codGtAtcNivel5 ="";
	private String nomGtAtcNivel5 ="";		// nivel de grupo terapéutico de la ATC (mismo principio activo)
	private String codGtVm ="";					// grupo VM (igual principio activo).
	private String nomGtVm =""; 
	private String codGtVmp ="";
	private String nomGtVmp ="";				// grupo VMP (igual principio activo, dosis y forma farmacéutica).
	private String codGtVmpp =""; 
	private String nomGtVmpp ="";				// grupo VMMP (igual principio activo, dosis, forma farmacéutica y número de unidades de dosificación).
	private String comentarios = "";
	private String sustituible = "";
	private String tolva = "";

	
	public int getOidSustXComposicion() {												return oidSustXComposicion;		}
	public void setOidSustXComposicion(int oidSustXComposicion) {						this.oidSustXComposicion = oidSustXComposicion;		}
	public String getFiltroCodigoMedicamento() {										return filtroCodigoMedicamento;		}
	public void setFiltroCodigoMedicamento(String filtroCodigoMedicamento) {			this.filtroCodigoMedicamento = filtroCodigoMedicamento;		}
	public String getCnOk() {															return cnOk;		}
	public void setCnOk(String cnOk) {													this.cnOk = cnOk;		}
	public String getNombreConsejo() {													return nombreConsejo;	}
	public void setNombreConsejo(String nombreConsejo) {								this.nombreConsejo = nombreConsejo;}	
	public float getRentabilidad() {													return rentabilidad;		}
	public void setRentabilidad(float rentabilidad) {									this.rentabilidad = rentabilidad;		}
	public float getPonderacion() {														return ponderacion;		}
	public void setPonderacion(float ponderacion) {										this.ponderacion = ponderacion;		}
	public String getCodiLab() {														return codiLab;		}
	public void setCodiLab(String codiLab) {											this.codiLab = codiLab;		}
	public String getComentarios() {													return comentarios;		}
	public void setComentarios(String comentarios) {									this.comentarios = comentarios;		}
	public List<SustXComposicion> getListaSustXComposicion() {							return listaSustXComposicion;	}
	public void setListaSustXComposicion(List<SustXComposicion> listaSustXComposicion) {this.listaSustXComposicion = listaSustXComposicion;	}
	public SustXComposicion getSustXComposicion() {										return sustXComposicion;	}
	public void setSustXComposicion(SustXComposicion sustXComposicion) {				this.sustXComposicion = sustXComposicion;	}
	public String getFiltroTextoABuscar() {												return filtroTextoABuscar;	}
	public void setFiltroTextoABuscar(String filtroTextoABuscar) {						this.filtroTextoABuscar = filtroTextoABuscar;	}
//	public String getFiltroListaConjuntosHomogeneos() {									return filtroListaConjuntosHomogeneos;	}
//	public void setFiltroListaConjuntosHomogeneos(String filtroListaConjuntosHomogeneos) {	this.filtroListaConjuntosHomogeneos = filtroListaConjuntosHomogeneos;	}
	public String getFiltroPresentacion() {										return filtroPresentacion;	}
	public void setFiltroPresentacion(String filtroListaPresentacion) {			this.filtroPresentacion = filtroListaPresentacion;	}
	public String getCodigoGT() {														return codigoGT;	}	
	public void setCodigoGT(String codigoGT) {											this.codigoGT = codigoGT;	}
	public String getGrupoTerapeutico() {												return grupoTerapeutico;	}
	public void setGrupoTerapeutico(String grupoTerapeutico) {							this.grupoTerapeutico = grupoTerapeutico;	}
	public String getCodGtAtcNivel3() {													return codGtAtcNivel3;	}
	public void setCodGtAtcNivel3(String codGtAtcNivel3) {								this.codGtAtcNivel3 = codGtAtcNivel3;	}
	public String getNomGtAtcNivel3() {													return nomGtAtcNivel3;	}
	public void setNomGtAtcNivel3(String nomGtAtcNivel3) {								this.nomGtAtcNivel3 = nomGtAtcNivel3;	}
	public String getCodGtAtcNivel4() {													return codGtAtcNivel4;	}
	public void setCodGtAtcNivel4(String codGtAtcNivel4) {								this.codGtAtcNivel4 = codGtAtcNivel4;	}
	public String getNomGtAtcNivel4() {													return nomGtAtcNivel4;	}
	public void setNomGtAtcNivel4(String nomGtAtcNivel4) {								this.nomGtAtcNivel4 = nomGtAtcNivel4;	}
	public String getCodGtAtcNivel5() {													return codGtAtcNivel5;	}
	public void setCodGtAtcNivel5(String codGtAtcNivel5) {								this.codGtAtcNivel5 = codGtAtcNivel5;	}
	public String getNomGtAtcNivel5() {													return nomGtAtcNivel5;	}
	public void setNomGtAtcNivel5(String nomGtAtcNivel5) {								this.nomGtAtcNivel5 = nomGtAtcNivel5;	}
	public String getCodGtVmp() {														return codGtVmp;	}
	public void setCodGtVmp(String codGtVmp) {										this.codGtVmp = codGtVmp;	}
	public String getNomGtVmp() {														return nomGtVmp;	}
	public void setNomGtVmp(String nomGtVmp) {										this.nomGtVmp = nomGtVmp;	}
	public String getCodGtVmpp() {															return codGtVmpp;	}
	public void setCodGtVmpp(String codGtVmpp) {												this.codGtVmpp = codGtVmpp;	}
	public String getNomGtVmpp() {															return nomGtVmpp;	}
	public void setNomGtVmpp(String nomGtVmpp) {												this.nomGtVmpp = nomGtVmpp;	}
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
/*
	public String getFiltroCodiLaboratorioBiblia() {
		return filtroCodiLaboratorioBiblia;
	}
	public void setFiltroCodiLaboratorioBiblia(String filtroCodiLaboratorioBiblia) {
		this.filtroCodiLaboratorioBiblia = filtroCodiLaboratorioBiblia;
	}
	public String getFiltroNombreLaboratorioBiblia() {
		return filtroNombreLaboratorioBiblia;
	}
	public void setFiltroNombreLaboratorioBiblia(String filtroNombreLaboratorioBiblia) {
		this.filtroNombreLaboratorioBiblia = filtroNombreLaboratorioBiblia;
	}
	public List<BdConsejo> getListaLabsBiblia() {
		return listaLabsBiblia;
	}
	public void setListaLabsBiblia(List<BdConsejo> listaLabsBiblia) {
		this.listaLabsBiblia = listaLabsBiblia;
	}
*/
	public boolean isFiltroLabsSoloAsignados() {
		return filtroLabsSoloAsignados;
	}
	public void setFiltroLabsSoloAsignados(boolean filtroLabsSoloAsignados) {
		this.filtroLabsSoloAsignados = filtroLabsSoloAsignados;
	}
	public boolean isFiltroCheckedLabsSoloAsignados() {
		return filtroCheckedLabsSoloAsignados;
	}
	public void setFiltroCheckedLabsSoloAsignados(boolean filtroCheckedLabsSoloAsignados) {
		this.filtroCheckedLabsSoloAsignados = filtroCheckedLabsSoloAsignados;
	}
	public boolean isFiltroComposicionSinLabs() {
		return filtroComposicionSinLabs;
	}
	public void setFiltroComposicionSinLabs(boolean filtroComposicionSinLabs) {
		this.filtroComposicionSinLabs = filtroComposicionSinLabs;
	}
	public boolean isFiltroCheckedComposicionSinLabs() {
		return filtroCheckedComposicionSinLabs;
	}
	public void setFiltroCheckedComposicionSinLabs(boolean filtroCheckedComposicionSinLabs) {
		this.filtroCheckedComposicionSinLabs = filtroCheckedComposicionSinLabs;
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
	public String getCn6() {
		return cn6;
	}
	public void setCn6(String cn6) {
		this.cn6 = cn6;
	}
	public String getCn7() {
		return cn7;
	}
	public void setCn7(String cn7) {
		this.cn7 = cn7;
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
