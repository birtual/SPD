package lopicost.spd.struts.form;

import java.util.List;
import lopicost.spd.model.SustXComposicion;
import lopicost.spd.model.BdConsejo;
import lopicost.spd.model.GestSustituciones;
import lopicost.spd.model.GestSustitucionesXResi;

public class GestSustitucionesForm  extends GenericForm {

	private int oidDivisionResidenciaFiltro = 0;
	private int oidGestSustitucionesXResi = 0;
	
	
	private String gtVm = "";  //gtVm

	private String cnOk = "";

	private String medicamentoResi = "";
	private String accion = "";
	private String accionSustXResi = "";
	private String cnOkSustXResi = "";
	private String tipoAccionSustXResi= "";
	private String nombreCortoSustXResi = "";

	private String comentarioSustXResi = "";
	private String formaFarmaceuticaSustXResi = "";
	private String sustituibleXResi ="1";
	
	private String nombreConsejo = "";
	private String nombreCorto = "";
	private String cn6 = "";
	private String cnResi = "";
	private String comentario = "";
	private String formaFarmaceutica = "";
	private String sustituible ="1";
	
	
	private List<?> listaHoras;

	private List<?> listaTiposAccion;

	private GestSustituciones sustitucion = new GestSustituciones();
	private GestSustitucionesXResi sustitucionXResi = new GestSustitucionesXResi();
	private SustXComposicion  sustXConjHomog = new SustXComposicion();



	private String filtroCn  = "";
	private String filtroMedicamentoResi  = "";
	private String filtroNombreCortoOK  = "";
	private String filtroGtVm  = "";		//filtroGtVm
	private String filtroGtVmp  = "";		//filtroGtVmp
	private String filtroGtVmpp  = "";		//filtroGtVmpp
	private String filtroPresentacion  = "";
	private String filtroNombreCorto = "";
	private String filtroFormaFarmaceutica= "";
	private String filtroAccion= "";
	private String filtroExisteBdConsejo ="0";
	private String filtroCheckeadoExisteBdConsejo ="false";
	private BdConsejo bdConsejoResi ;
	
	
	public int getOidDivisionResidenciaFiltro() {
		return oidDivisionResidenciaFiltro;
	}
	public void setOidDivisionResidenciaFiltro(int oidDivisionResidenciaFiltro) {
		this.oidDivisionResidenciaFiltro = oidDivisionResidenciaFiltro;
	}
	public int getOidGestSustitucionesXResi() {
		return oidGestSustitucionesXResi;
	}
	public void setOidGestSustitucionesXResi(int oidGestSustitucionesXResi) {
		this.oidGestSustitucionesXResi = oidGestSustitucionesXResi;
	}
	public String getGtVm() {
		return gtVm;
	}
	public void setGtVm(String gtVm) {
		this.gtVm = gtVm;
	}
	public String getCnOk() {
		return cnOk;
	}
	public void setCnOk(String cnOk) {
		this.cnOk = cnOk;
	}
	public String getMedicamentoResi() {
		return medicamentoResi;
	}
	public void setMedicamentoResi(String medicamentoResi) {
		this.medicamentoResi = medicamentoResi;
	}
	public String getAccion() {
		return accion;
	}
	public void setAccion(String accion) {
		this.accion = accion;
	}
	public String getAccionSustXResi() {
		return accionSustXResi;
	}
	public void setAccionSustXResi(String accionSustXResi) {
		this.accionSustXResi = accionSustXResi;
	}
	public String getCnOkSustXResi() {
		return cnOkSustXResi;
	}
	public void setCnOkSustXResi(String cnOkSustXResi) {
		this.cnOkSustXResi = cnOkSustXResi;
	}
	public String getTipoAccionSustXResi() {
		return tipoAccionSustXResi;
	}
	public void setTipoAccionSustXResi(String tipoAccionSustXResi) {
		this.tipoAccionSustXResi = tipoAccionSustXResi;
	}
	public String getNombreCortoSustXResi() {
		return nombreCortoSustXResi;
	}
	public void setNombreCortoSustXResi(String nombreCortoSustXResi) {
		this.nombreCortoSustXResi = nombreCortoSustXResi;
	}
	public String getComentarioSustXResi() {
		return comentarioSustXResi;
	}
	public void setComentarioSustXResi(String comentarioSustXResi) {
		this.comentarioSustXResi = comentarioSustXResi;
	}
	public String getFormaFarmaceuticaSustXResi() {
		return formaFarmaceuticaSustXResi;
	}
	public void setFormaFarmaceuticaSustXResi(String formaFarmaceuticaSustXResi) {
		this.formaFarmaceuticaSustXResi = formaFarmaceuticaSustXResi;
	}
	public String getSustituibleXResi() {
		return sustituibleXResi;
	}
	public void setSustituibleXResi(String sustituibleXResi) {
		this.sustituibleXResi = sustituibleXResi;
	}
	public String getNombreConsejo() {
		return nombreConsejo;
	}
	public void setNombreConsejo(String nombreConsejo) {
		this.nombreConsejo = nombreConsejo;
	}
	public String getNombreCorto() {
		return nombreCorto;
	}
	public void setNombreCorto(String nombreCorto) {
		this.nombreCorto = nombreCorto;
	}
	public String getCn6() {
		return cn6;
	}
	public void setCn6(String cn6) {
		this.cn6 = cn6;
	}
	public String getCnResi() {
		return cnResi;
	}
	public void setCnResi(String cnResi) {
		this.cnResi = cnResi;
	}
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	public String getFormaFarmaceutica() {
		return formaFarmaceutica;
	}
	public void setFormaFarmaceutica(String formaFarmaceutica) {
		this.formaFarmaceutica = formaFarmaceutica;
	}
	public String getSustituible() {
		return sustituible;
	}
	public void setSustituible(String sustituible) {
		this.sustituible = sustituible;
	}
	public List<?> getListaHoras() {
		return listaHoras;
	}
	public void setListaHoras(List<?> listaHoras) {
		this.listaHoras = listaHoras;
	}
	public List<?> getListaTiposAccion() {
		return listaTiposAccion;
	}
	public void setListaTiposAccion(List<?> listaTiposAccion) {
		this.listaTiposAccion = listaTiposAccion;
	}
	public GestSustituciones getSustitucion() {
		return sustitucion;
	}
	public void setSustitucion(GestSustituciones sustitucion) {
		this.sustitucion = sustitucion;
	}
	public GestSustitucionesXResi getSustitucionXResi() {
		return sustitucionXResi;
	}
	public void setSustitucionXResi(GestSustitucionesXResi sustitucionXResi) {
		this.sustitucionXResi = sustitucionXResi;
	}
	public SustXComposicion getSustXConjHomog() {
		return sustXConjHomog;
	}
	public void setSustXConjHomog(SustXComposicion sustXConjHomog) {
		this.sustXConjHomog = sustXConjHomog;
	}
	public String getFiltroCn() {
		return filtroCn;
	}
	public void setFiltroCn(String filtroCn) {
		this.filtroCn = filtroCn;
	}
	public String getFiltroMedicamentoResi() {
		return filtroMedicamentoResi;
	}
	public void setFiltroMedicamentoResi(String filtroMedicamentoResi) {
		this.filtroMedicamentoResi = filtroMedicamentoResi;
	}
	public String getFiltroNombreCortoOK() {
		return filtroNombreCortoOK;
	}
	public void setFiltroNombreCortoOK(String filtroNombreCortoOK) {
		this.filtroNombreCortoOK = filtroNombreCortoOK;
	}
	public String getFiltroGtVm() {
		return filtroGtVm;
	}
	public void setFiltroGtVm(String filtroGtVm) {
		this.filtroGtVm = filtroGtVm;
	}
	public String getFiltroGtVmp() {
		return filtroGtVmp;
	}
	public void setFiltroGtVmp(String filtroGtVmp) {
		this.filtroGtVmp = filtroGtVmp;
	}
	public String getFiltroGtVmpp() {
		return filtroGtVmpp;
	}
	public void setFiltroGtVmpp(String filtroGtVmpp) {
		this.filtroGtVmpp = filtroGtVmpp;
	}
	public String getFiltroPresentacion() {
		return filtroPresentacion;
	}
	public void setFiltroPresentacion(String filtroPresentacion) {
		this.filtroPresentacion = filtroPresentacion;
	}
	public String getFiltroNombreCorto() {
		return filtroNombreCorto;
	}
	public void setFiltroNombreCorto(String filtroNombreCorto) {
		this.filtroNombreCorto = filtroNombreCorto;
	}
	public String getFiltroFormaFarmaceutica() {
		return filtroFormaFarmaceutica;
	}
	public void setFiltroFormaFarmaceutica(String filtroFormaFarmaceutica) {
		this.filtroFormaFarmaceutica = filtroFormaFarmaceutica;
	}
	public String getFiltroAccion() {
		return filtroAccion;
	}
	public void setFiltroAccion(String filtroAccion) {
		this.filtroAccion = filtroAccion;
	}
	public String getFiltroExisteBdConsejo() {
		return filtroExisteBdConsejo;
	}
	public void setFiltroExisteBdConsejo(String filtroExisteBdConsejo) {
		this.filtroExisteBdConsejo = filtroExisteBdConsejo;
	}
	public String getFiltroCheckeadoExisteBdConsejo() {
		return filtroCheckeadoExisteBdConsejo;
	}
	public void setFiltroCheckeadoExisteBdConsejo(String filtroCheckeadoExisteBdConsejo) {
		this.filtroCheckeadoExisteBdConsejo = filtroCheckeadoExisteBdConsejo;
	}
	public BdConsejo getBdConsejoResi() {
		return bdConsejoResi;
	}
	public void setBdConsejoResi(BdConsejo bdConsejoResi) {
		this.bdConsejoResi = bdConsejoResi;
	}


	
	
}
