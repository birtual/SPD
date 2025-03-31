package lopicost.spd.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Nivel1 implements Serializable {
	private String codGtVm;
	private String nomGtVm;
	private List<Nivel2> listaNivel2 = null;  

	public Nivel1() {
		super();
	}
	
	public String toString() {
		return "SustXComposicion [codGtVm=" + codGtVm + ", nomGtVm=" + nomGtVm+ "" 
				+ ", toString()="
				+ super.toString() + "]";
	}

	/**Para que no devuelva un nulo se inicializa en caso que no tenga ningún elemento asignado
	 * @return
	 */
	public List<Nivel2> getListaNivel2() {
		if(listaNivel2 == null)
			listaNivel2 = new ArrayList<Nivel2>();
		return listaNivel2;
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

	public void setListaNivel2(List<Nivel2> hijos) {
		this.listaNivel2 = hijos;
	}



	

	
}