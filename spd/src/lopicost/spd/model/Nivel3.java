package lopicost.spd.model;

import java.io.Serializable;
import java.util.Date;

public class Nivel3 implements Serializable {

	private int oidSustXComposicion;
	private Date fechaCreacion;
	private Nivel1 nivel1; 
	private Nivel2 nivel2; 
	private String idRobot;
	private String nombreRobot;
	private String codGtVmpp;
	private String nomGtVmpp;
	private String codGtVmp;
	private String nomGtVmp;
	private String presentacion;
	private float rentabilidad;
	private float nota;
	private float ponderacion;
	private String codLaboratorio;
	private String nomLaboratorio;
	private String comentarios;
	private String tolva;
	private String sustituible;
	private String cn6;
	private String cn7;
	private String nombreMedicamento;
	private boolean excepcion;

	public Nivel3() {
		super();
	}
	
	public String toString() {
		return "SustXComposicion [oidSustXComposicion = " + oidSustXComposicion + ", codGtVmpp=" + codGtVmpp 
				+ ", nomGtVmpp= " + presentacion+ ", rentabilidad=" + rentabilidad
				+ ", nota=" + nota + ", ponderacion=" + ponderacion + ", codLaboratorio=" + codLaboratorio
				+ ", tolva=" + tolva + ", sustituible=" + sustituible + ", cn6=" + cn6 + ", cn7=" + cn7
				+ super.toString() + "]";
	}
	

	public int getOidSustXComposicion() {
		return oidSustXComposicion;
	}

	public void setOidSustXComposicion(int oid) {
		this.oidSustXComposicion = oid;
	}


	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Nivel1 getNivel1() {
		return nivel1;
	}

	public void setNivel1(Nivel1 abuelo) {
		this.nivel1 = abuelo;
	}

	public Nivel2 getNivel2() {
		return nivel2;
	}

	public void setNivel2(Nivel2 padre) {
		this.nivel2 = padre;
	}

	public String getIdRobot() {
		return idRobot;
	}

	public void setIdRobot(String idRobot) {
		this.idRobot = idRobot;
	}

	public String getCodGtVmp() {
		return codGtVmp;
	}

	public void setCodGtVmp(String codGtVmp) {
		this.codGtVmp = codGtVmp;
	}

	public String getNomGtVmp() {
		return nomGtVmp;
	}

	public void setNomGtVmp(String nomGtVmp) {
		this.nomGtVmp = nomGtVmp;
	}

	public String getCodGtVmpp() {
		return codGtVmpp;
	}

	public void setCodGtVmpp(String codGtvmpp) {
		this.codGtVmpp = codGtvmpp;
	}

	public String getNomGtVmpp() {
		return nomGtVmpp;
	}

	public void setNomGtVmpp(String nomGtvmpp) {
		this.nomGtVmpp = nomGtvmpp;
	}

	public float getRentabilidad() {
		return rentabilidad;
	}

	public void setRentabilidad(float rentabilidad) {
		this.rentabilidad = rentabilidad;
	}

	public float getNota() {
		return nota;
	}

	public void setNota(float nota) {
		this.nota = nota;
	}

	public float getPonderacion() {
		return ponderacion;
	}

	public void setPonderacion(float ponderacion) {
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

	public String getComentarios() {
		return comentarios;
	}

	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}

	public String getTolva() {
		return tolva;
	}

	public void setTolva(String tolva) {
		this.tolva = tolva;
	}

	public String getSustituible() {
		return sustituible;
	}

	public void setSustituible(String sustituible) {
		this.sustituible = sustituible;
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

	public String getNombreMedicamento() {
		return nombreMedicamento;
	}

	public void setNombreMedicamento(String nombreMedicamento) {
		this.nombreMedicamento = nombreMedicamento;
	}

	public String getPresentacion() {
		return presentacion;
	}

	public void setPresentacion(String presentacion) {
		this.presentacion = presentacion;
	}

	public boolean isExcepcion() {
		return excepcion;
	}

	public void setExcepcion(boolean excepcion) {
		this.excepcion = excepcion;
	}

	public String getNombreRobot() {
		return nombreRobot;
	}

	public void setNombreRobot(String nombreRobot) {
		this.nombreRobot = nombreRobot;
	}



	
}