package lopicost.spd.model;


import java.io.Serializable;
import java.util.Date;

public class SpdLog  implements Serializable {

	private Long oidlog;
	private String idUsuario;
	private String CIP;
	private String idDivisionResidencia;
	private String idProceso;
	private String idApartado;
	private String idAccion;
	private String idSubAccion;
	private String descripcion;
	private String fecha;	
	
	

    /** default constructor */
    public SpdLog() {
    }

	/** full constructor */
	public SpdLog(String idUsuario, String CIP, String idDivisionResidencia, String idProceso	,  String idApartado, String idAccion, String idSubAccion, String descripcion) 
	{
		this.idUsuario=idUsuario;
		this.CIP=CIP;
		this.idDivisionResidencia=idDivisionResidencia;
		this.idProceso=idProceso;
		this.idApartado=idApartado;
		this.idAccion=idAccion;
		this.idSubAccion=idSubAccion;
		this.descripcion=descripcion;	
	}

	public Long getOidlog() {
		return oidlog;
	}

	public void setOidlog(Long oidlog) {
		this.oidlog = oidlog;
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getCIP() {
		return CIP;
	}

	public void setCIP(String cIP) {
		CIP = cIP;
	}

	public String getIdDivisionResidencia() {
		return idDivisionResidencia;
	}

	public void setIdDivisionResidencia(String idDivisionResidencia) {
		this.idDivisionResidencia = idDivisionResidencia;
	}

	public String getIdApartado() {
		return idApartado;
	}

	public void setIdApartado(String idApartado) {
		this.idApartado = idApartado;
	}

	public String getIdAccion() {
		return idAccion;
	}

	public void setIdAccion(String idAccion) {
		this.idAccion = idAccion;
	}

	public String getIdSubAccion() {
		return idSubAccion;
	}

	public void setIdSubAccion(String idSubAccion) {
		this.idSubAccion = idSubAccion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getIdProceso() {
		return idProceso;
	}

	public void setIdProceso(String idProceso) {
		this.idProceso = idProceso;
	}



}
