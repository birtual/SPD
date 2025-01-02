package lopicost.spd.model;

import java.io.Serializable;
import java.util.Date;

public class SustXGtvmpp implements Serializable {

	private Date fechaCreacion;
	private SustXGtvmp padre; 
	private String idRobot;
	private String nombreRobot;
	private String codGtvmpp;
	private String nomGtvmpp;
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

	public SustXGtvmpp() {
		super();
	}
	
	public String toString() {
		return "SustXComposicion [codGtvmpp=" + codGtvmpp + ", nomGtvmpp= " + presentacion+ ", rentabilidad=" + rentabilidad
				+ ", nota=" + nota + ", ponderacion=" + ponderacion + ", codLaboratorio=" + codLaboratorio
				+ ", tolva=" + tolva + ", sustituible=" + sustituible + ", cn6=" + cn6 + ", cn7=" + cn7
				+ super.toString() + "]";
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public SustXGtvmp getPadre() {
		return padre;
	}

	public void setPadre(SustXGtvmp padre) {
		this.padre = padre;
	}

	public String getIdRobot() {
		return idRobot;
	}

	public void setIdRobot(String idRobot) {
		this.idRobot = idRobot;
	}

	public String getCodGtvmpp() {
		return codGtvmpp;
	}

	public void setCodGtvmpp(String codGtvmpp) {
		this.codGtvmpp = codGtvmpp;
	}

	public String getNomGtvmpp() {
		return nomGtvmpp;
	}

	public void setNomGtvmpp(String nomGtvmpp) {
		this.nomGtvmpp = nomGtvmpp;
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