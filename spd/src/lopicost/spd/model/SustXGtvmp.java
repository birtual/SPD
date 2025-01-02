package lopicost.spd.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SustXGtvmp implements Serializable {
	private int oidSustXComposicion;
	private String codGtvmp;
	private String nomGtvmp;
	private String codGtVm;
	private String nomGtVm;
	private List<SustXGtvmpp> hijos = null;  
	private String comentarios;
	private String rentabilidad;
	private String nota;
	private String ponderacion;
	private String codLaboratorio;
	private String nomLaboratorio;




	public SustXGtvmp() {
		super();
	}
	
	public String toString() {
		return "SustXComposicion [codGtvmp=" + codGtvmp + ", nomGtvmp=" + nomGtvmp+ "" 
				+ ", toString()="
				+ super.toString() + "]";
	}

	/**Para que no devuelva un nulo se inicializa en caso que no tenga ningún elemento asignado
	 * @return
	 */
	public List<SustXGtvmpp> getHijos() {
		if(hijos == null)
			hijos = new ArrayList<SustXGtvmpp>();
		return hijos;
	}

	public void setHijos(List<SustXGtvmpp> hijos) {
		this.hijos = hijos;
	}

	public String getCodGtvmp() {
		return codGtvmp;
	}

	public void setCodGtvmp(String codGtvmp) {
		this.codGtvmp = codGtvmp;
	}

	public String getNomGtvmp() {
		return nomGtvmp;
	}

	public void setNomGtvmp(String nomGtvmp) {
		this.nomGtvmp = nomGtvmp;
	}

	public String getComentarios() {
		return comentarios;
	}

	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
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

	public int getOidSustXComposicion() {
		return oidSustXComposicion;
	}

	public void setOidSustXComposicion(int oid) {
		this.oidSustXComposicion = oid;
	}

	public String getRentabilidad() {
		return rentabilidad;
	}

	public void setRentabilidad(String rentabilidad) {
		this.rentabilidad = rentabilidad;
	}

	public String getNota() {
		return nota;
	}

	public void setNota(String nota) {
		this.nota = nota;
	}

	public String getPonderacion() {
		return ponderacion;
	}

	public void setPonderacion(String ponderacion) {
		this.ponderacion = ponderacion;
	}

	public String getCodLaboratorio() {
		return codLaboratorio;
	}

	public void setCodLaboratorio(String codLaboratorio) {
		this.codLaboratorio = codLaboratorio;
	}

	public String getNomLaboratorio() {
		return nomLaboratorio;
	}

	public void setNomLaboratorio(String nomLaboratorio) {
		this.nomLaboratorio = nomLaboratorio;
	}




	

	
}