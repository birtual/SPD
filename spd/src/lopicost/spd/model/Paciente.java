package lopicost.spd.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lopicost.spd.robot.bean.rd.BolsaSPD;

public class Paciente implements Serializable {
	  
	private int oidPaciente;						//oidPaciente					int,
	private String CIP; 							//CIP					varchar(20),
	private String idPacienteResidencia;			//idPacienteResidencia	varchar(50) DEFAULT '',
	private String nombre;						//nom         			varchar(100) DEFAULT '',,
	private String apellido1;						//apellido1             varchar(100),
	private String apellido2;						//apellido2             varchar(100),
	private String nombreApellidos;				//nomCognoms	 	 	nombre + '' + apellido1 + ' ' +apellido2,
	private String apellidosNombre;				//cognomsNom            apellido1 + ' ' +apellido2 + ', ' + nombre,
	private String numIdentidad;					//nIdentidad            varchar(25) DEFAULT '',
	private String segSocial	;					//segSocial             varchar(25) DEFAULT '',
	private String planta;						//planta                varchar(25) DEFAULT '',
	private String habitacion;					//habitacion            varchar(25) DEFAULT '',
	private String idDivisionResidencia;			//idDivisionResidencia  varchar(50) DEFAULT '',
	private String spd;							//spd                   varchar(20) DEFAULT '',
	private Date fechaProceso;					//fechaProceso          datetime DEFAULT getdate(),
	private int exitus;							//exitus                bit DEFAULT 0,
	private String estatus;						//estatus               varchar(255) DEFAULT '',
	private String bolquers;						//bolquers              varchar(20) DEFAULT 'S',
	private String comentarios;					//comentarios           varchar(255) DEFAULT '',
	private String fechaAltaPaciente;				//fechaAltaPaciente     varchar(30) DEFAULT getdate(),
	private String CipFicheroResi;        		//CipFicheroResi        varchar(50) 
	private String activo;        				
	private List<BolsaSPD> produccionSPD;        				
	

	public Paciente() {
		super();
	}

	  public String getNombreApellidos() {
			return this.nombre + ' ' + this.apellido1 + ' ' + this.apellido2;
	}
	

	  public String getApellidosNombre() {
			return this.apellido1 + " " + this.apellido2 + ", " + this.nombre;
	}


// GETTERS y SETTERS estandars
		

	public String getCIP() {
		return CIP;
	}


	public int getOidPaciente() {
		return oidPaciente;
	}

	public void setOidPaciente(int oidPaciente) {
		this.oidPaciente = oidPaciente;
	}

	public void setCIP(String cIP) {
		CIP = cIP;
	}


	public String getIdPacienteResidencia() {
		return idPacienteResidencia;
	}


	public void setIdPacienteResidencia(String idPacienteResidencia) {
		this.idPacienteResidencia = idPacienteResidencia;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getApellido1() {
		return apellido1;
	}


	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}


	public String getApellido2() {
		return apellido2;
	}


	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}





	public String getNIdentidad() {
		return numIdentidad;
	}


	public void setNIdentidad(String nIdentidad) {
		this.numIdentidad = nIdentidad;
	}


	public String getSegSocial() {
		return segSocial;
	}


	public void setSegSocial(String segSocial) {
		this.segSocial = segSocial;
	}


	public String getPlanta() {
		return planta;
	}


	public void setPlanta(String planta) {
		this.planta = planta;
	}


	public String getHabitacion() {
		return habitacion;
	}


	public void setHabitacion(String habitacion) {
		this.habitacion = habitacion;
	}


	public String getIdDivisionResidencia() {
		return idDivisionResidencia;
	}


	public void setIdDivisionResidencia(String idDivisionResidencia) {
		this.idDivisionResidencia = idDivisionResidencia;
	}


	public String getSpd() {
		return spd;
	}


	public void setSpd(String spd) {
		this.spd = spd;
	}


	public Date getFechaProceso() {
		return fechaProceso;
	}


	public void setFechaProceso(Date fechaProceso) {
		this.fechaProceso = fechaProceso;
	}


	public int getExitus() {
		return exitus;
	}


	public void setExitus(int exitus) {
		this.exitus = exitus;
	}


	public String getEstatus() {
		return estatus;
	}


	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}


	public String getBolquers() {
		return bolquers;
	}


	public void setBolquers(String bolquers) {
		this.bolquers = bolquers;
	}


	public String getComentarios() {
		return comentarios;
	}


	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}


	public String getFechaAltaPaciente() {
		return fechaAltaPaciente;
	}


	public void setFechaAltaPaciente(String fechaAltaPaciente) {
		this.fechaAltaPaciente = fechaAltaPaciente;
	}


	public String getCipFicheroResi() {
		return CipFicheroResi;
	}


	public void setCipFicheroResi(String cipFicheroResi) {
		CipFicheroResi = cipFicheroResi;
	}


	public void setNombreApellidos(String nombreApellidos) {
		this.nombreApellidos = nombreApellidos;
	}

	public void setApellidosNombre(String apellidosNombre) {
		this.apellidosNombre = apellidosNombre;
	}

	public String getActivo() {
		return activo;
	}

	public void setActivo(String activo) {
		this.activo = activo;
	}

	public String getNumIdentidad() {
		return numIdentidad;
	}

	public void setNumIdentidad(String numIdentidad) {
		this.numIdentidad = numIdentidad;
	}

	public List<BolsaSPD> getProduccionSPD() {
		return produccionSPD;
	}

	public void setProduccionSPD(List<BolsaSPD> produccionSPD) {
		this.produccionSPD = produccionSPD;
	}
	


	  
}
