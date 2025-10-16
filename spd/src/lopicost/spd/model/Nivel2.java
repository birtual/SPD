package lopicost.spd.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Nivel2 implements Serializable {
	private int oidSustXComposicion;
	private String codGtVm;
	private String nomGtVm;
	private String codGtVmp;
	private String nomGtVmp;
	private Nivel1 nivel1; 
	private List<Nivel3> listaNivel3 = null;  
	private String comentarios;
	private String rentabilidad;
	private String nota;
	private String ponderacion;
	private String codLaboratorio;
	private String nomLaboratorio;


	public Nivel2() {
		super();
	}
	
	public String toString() {
		return "SustXComposicion [codGtVmp=" + codGtVmp + ", nomGtVmp=" + nomGtVmp+ "" 
				+ ", toString()="
				+ super.toString() + "]";
	}

	/**Para que no devuelva un nulo se inicializa en caso que no tenga ningún elemento asignado
	 * @return
	 */
	public List<Nivel3> getListaNivel3() {
		if(listaNivel3 == null)
			listaNivel3 = new ArrayList<Nivel3>();
		return listaNivel3;
	}

	public void setListaNivel3(List<Nivel3> hijos) {
		this.listaNivel3 = hijos;
	}

	public String getCodGtVmp() {
		return codGtVmp;
	}

	public void setCodGtVmp(String codGtvmp) {
		this.codGtVmp = codGtvmp;
	}

	public String getNomGtVmp() {
		return nomGtVmp;
	}

	public void setNomGtVmp(String nomGtvmp) {
		this.nomGtVmp = nomGtvmp;
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

	public Nivel1 getNivel1() {
		return nivel1;
	}

	public void setNivel1(Nivel1 padre) {
		this.nivel1 = padre;
	}




	

	
}