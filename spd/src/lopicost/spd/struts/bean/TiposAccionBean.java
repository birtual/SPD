package lopicost.spd.struts.bean;

import java.util.ArrayList;
import java.util.List;

import lopicost.spd.utils.SPDConstants;


public class TiposAccionBean {

	 String idTipoAccion="";
	 String nombreAccion="";
	 List<String> listaSpdAccionBolsa = new ArrayList();
	 
	public String getIdTipoAccion() {
		return idTipoAccion;
	}
	public void setIdTipoAccion(String idTipoAccion) {
		this.idTipoAccion = idTipoAccion;
	}
	public String getNombreAccion() {
		return nombreAccion;
	}
	public void setNombreAccion(String nombreAccion) {
		this.nombreAccion = nombreAccion;
	}
	public List<String> getDefaultListaSpdAccionBolsa() {
		listaSpdAccionBolsa.add(SPDConstants.SPD_ACCIONBOLSA_PASTILLERO);
		listaSpdAccionBolsa.add(SPDConstants.SPD_ACCIONBOLSA_SOLO_INFO);
		listaSpdAccionBolsa.add(SPDConstants.SPD_ACCIONBOLSA_NO_PINTAR);
		return listaSpdAccionBolsa;
	}
	public void setListaSpdAccionBolsa(List<String> listaSpdAccionBolsa) {
		this.listaSpdAccionBolsa = listaSpdAccionBolsa;
	}
	
	 
}