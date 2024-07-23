package lopicost.spd.struts.form;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.struts.action.ActionForm;

import lopicost.spd.model.GestSustitucionesLite;



public class GestSustitucionesLiteForm  extends GestSustitucionesForm {

	private int oidGestSustitucionesLite = 0;
	private String spdCn;
	private String spdNombreBolsa;
	private String resiCn;
	private String resiMedicamento;
	  private String excepciones;
	  private String aux1;
	  private String aux2;
		private String filtroCn  = "";
		private String filtroGtVmp = "";
	private GestSustitucionesLite sustitucionLite = new GestSustitucionesLite();

	public List<GestSustitucionesLite> listGestSustitucionesLite;


	public String getFiltroCn() {
		return filtroCn;
	}


	public void setFiltroCn(String filtroCn) {
		this.filtroCn = filtroCn;
	}


	public String getFiltroGtVmp() {
		return filtroGtVmp;
	}


	public void setFiltroGtVmp(String filtroGtVmp) {
		this.filtroGtVmp = filtroGtVmp;
	}


	public int getOidGestSustitucionesLite() {
		return oidGestSustitucionesLite;
	}


	public void setOidGestSustitucionesLite(int oidGestSustitucionesLite) {
		this.oidGestSustitucionesLite = oidGestSustitucionesLite;
	}


	public List<GestSustitucionesLite> getListGestSustitucionesLite() {
		return listGestSustitucionesLite;
	}


	public void setListGestSustitucionesLite(List<GestSustitucionesLite> listGestSustitucionesLite) {
		this.listGestSustitucionesLite = listGestSustitucionesLite;
	}


	public GestSustitucionesLite getSustitucionLite() {
		return sustitucionLite;
	}


	public void setSustitucionLite(GestSustitucionesLite sustitucionLite) {
		this.sustitucionLite = sustitucionLite;
	}


	public String getExcepciones() {
		return excepciones;
	}


	public void setExcepciones(String excepciones) {
		this.excepciones = excepciones;
	}


	public String getAux1() {
		return aux1;
	}


	public void setAux1(String aux1) {
		this.aux1 = aux1;
	}


	public String getAux2() {
		return aux2;
	}


	public void setAux2(String aux2) {
		this.aux2 = aux2;
	}


	public String getSpdCn() {
		return spdCn;
	}


	public void setSpdCn(String spdCn) {
		this.spdCn = spdCn;
	}


	public String getSpdNombreBolsa() {
		return spdNombreBolsa;
	}


	public void setSpdNombreBolsa(String spdNombreBolsa) {
		this.spdNombreBolsa = spdNombreBolsa;
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





}
