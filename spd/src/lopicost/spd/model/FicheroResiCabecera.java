package lopicost.spd.model;

import java.util.Date;


public class FicheroResiCabecera {
	
	  private int oidDivisionResidencia;
	  private int oidFicheroResiCabecera;
	  private String usuarioCreacion;
	  private String usuarioValidacion;
	  private String usuarioEntregaSPD;
	  private String fechaEntregaSPD;
	  private String usuarioRecogidaSPD;
	  private String fechaRecogidaSPD;
	  private String usuarioDesemblistaSPD;
	  private String fechaDesemblistaSPD;
	  private String usuarioProduccionSPD;
	  private String fechaProduccionSPD;

	  private Date fechaCreacion;
	  private Date fechaCreacionFicheroXML;
	  private Date fechaCalculoPrevision;
	  private Date fechaValidacionDatos;
	  private String idDivisionResidencia;
	  private String nombreDivisionResidencia;
	  private String idProceso;
	  private String nombreFicheroResi;
	  private String nombreFicheroXML;
	  private String free1;
	  private String free2;
	  private String free3;
	  private int filasTotales;
	  private int cipsFicheroXML;
	  private int cipsActivosSPD;
	  private int porcentajeCIPS;
	  private String idEstado;
	  private String resultLog;
	  private int numErrores;
	  private String errores;
	  private int mensajesInfo;
	  private int mensajesAlerta;
	  private int numeroRevisionesPendientes;
	  private boolean procesoValido;
	  
	  
	public FicheroResiCabecera() {
		super();
	}


	public int getOidDivisionResidencia() {
		return oidDivisionResidencia;
	}


	public void setOidDivisionResidencia(int oidDivisionResidencia) {
		this.oidDivisionResidencia = oidDivisionResidencia;
	}


	public int getOidFicheroResiCabecera() {
		return oidFicheroResiCabecera;
	}


	public void setOidFicheroResiCabecera(int oidFicheroResiCabecera) {
		this.oidFicheroResiCabecera = oidFicheroResiCabecera;
	}


	public String getUsuarioCreacion() {
		return usuarioCreacion;
	}


	public void setUsuarioCreacion(String usuarioCreacion) {
		this.usuarioCreacion = usuarioCreacion;
	}


	public String getUsuarioValidacion() {
		return usuarioValidacion;
	}


	public void setUsuarioValidacion(String usuarioValidacion) {
		this.usuarioValidacion = usuarioValidacion;
	}


	public String getUsuarioEntregaSPD() {
		return usuarioEntregaSPD;
	}


	public void setUsuarioEntregaSPD(String usuarioEntregaSPD) {
		this.usuarioEntregaSPD = usuarioEntregaSPD;
	}


	public String getFechaEntregaSPD() {
		return fechaEntregaSPD;
	}


	public void setFechaEntregaSPD(String fechaEntregaSPD) {
		this.fechaEntregaSPD = fechaEntregaSPD;
	}


	public String getUsuarioRecogidaSPD() {
		return usuarioRecogidaSPD;
	}


	public void setUsuarioRecogidaSPD(String usuarioRecogidaSPD) {
		this.usuarioRecogidaSPD = usuarioRecogidaSPD;
	}


	public String getFechaRecogidaSPD() {
		return fechaRecogidaSPD;
	}


	public void setFechaRecogidaSPD(String fechaRecogidaSPD) {
		this.fechaRecogidaSPD = fechaRecogidaSPD;
	}


	public String getUsuarioDesemblistaSPD() {
		return usuarioDesemblistaSPD;
	}


	public void setUsuarioDesemblistaSPD(String usuarioDesemblistaSPD) {
		this.usuarioDesemblistaSPD = usuarioDesemblistaSPD;
	}


	public String getFechaDesemblistaSPD() {
		return fechaDesemblistaSPD;
	}


	public void setFechaDesemblistaSPD(String fechaDesemblistaSPD) {
		this.fechaDesemblistaSPD = fechaDesemblistaSPD;
	}


	public String getUsuarioProduccionSPD() {
		return usuarioProduccionSPD;
	}


	public void setUsuarioProduccionSPD(String usuarioProduccionSPD) {
		this.usuarioProduccionSPD = usuarioProduccionSPD;
	}


	public String getFechaProduccionSPD() {
		return fechaProduccionSPD;
	}


	public void setFechaProduccionSPD(String fechaProduccionSPD) {
		this.fechaProduccionSPD = fechaProduccionSPD;
	}


	public Date getFechaCreacion() {
		return fechaCreacion;
	}


	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}


	public Date getFechaCreacionFicheroXML() {
		return fechaCreacionFicheroXML;
	}


	public void setFechaCreacionFicheroXML(Date fechaCreacionFicheroXML) {
		this.fechaCreacionFicheroXML = fechaCreacionFicheroXML;
	}


	public Date getFechaCalculoPrevision() {
		return fechaCalculoPrevision;
	}


	public void setFechaCalculoPrevision(Date fechaCalculoPrevision) {
		this.fechaCalculoPrevision = fechaCalculoPrevision;
	}


	public Date getFechaValidacionDatos() {
		return fechaValidacionDatos;
	}


	public void setFechaValidacionDatos(Date fechaValidacionDatos) {
		this.fechaValidacionDatos = fechaValidacionDatos;
	}


	public String getIdDivisionResidencia() {
		return idDivisionResidencia;
	}


	public void setIdDivisionResidencia(String idDivisionResidencia) {
		this.idDivisionResidencia = idDivisionResidencia;
	}


	public String getNombreDivisionResidencia() {
		return nombreDivisionResidencia;
	}


	public void setNombreDivisionResidencia(String nombreDivisionResidencia) {
		this.nombreDivisionResidencia = nombreDivisionResidencia;
	}


	public String getIdProceso() {
		return idProceso;
	}


	public void setIdProceso(String idProceso) {
		this.idProceso = idProceso;
	}


	public String getNombreFicheroResi() {
		return nombreFicheroResi;
	}


	public void setNombreFicheroResi(String nombreFicheroResi) {
		this.nombreFicheroResi = nombreFicheroResi;
	}


	public String getNombreFicheroXML() {
		return nombreFicheroXML;
	}


	public void setNombreFicheroXML(String nombreFicheroXML) {
		this.nombreFicheroXML = nombreFicheroXML;
	}


	public String getFree1() {
		return free1;
	}


	public void setFree1(String free1) {
		this.free1 = free1;
	}


	public String getFree2() {
		return free2;
	}


	public void setFree2(String free2) {
		this.free2 = free2;
	}


	public String getFree3() {
		return free3;
	}


	public void setFree3(String free3) {
		this.free3 = free3;
	}


	public int getFilasTotales() {
		return filasTotales;
	}


	public void setFilasTotales(int filasTotales) {
		this.filasTotales = filasTotales;
	}


	public int getCipsFicheroXML() {
		return cipsFicheroXML;
	}


	public void setCipsFicheroXML(int cipsFicheroXML) {
		this.cipsFicheroXML = cipsFicheroXML;
	}


	public int getCipsActivosSPD() {
		return cipsActivosSPD;
	}


	public void setCipsActivosSPD(int cipsActivosSPD) {
		this.cipsActivosSPD = cipsActivosSPD;
	}


	public int getPorcentajeCIPS() {
		return porcentajeCIPS;
	}


	public void setPorcentajeCIPS(int porcentajeCIPS) {
		this.porcentajeCIPS = porcentajeCIPS;
	}


	public String getIdEstado() {
		return idEstado;
	}


	public void setIdEstado(String idEstado) {
		this.idEstado = idEstado;
	}


	public String getResultLog() {
		return resultLog;
	}


	public void setResultLog(String resultLog) {
		this.resultLog = resultLog;
	}


	public int getNumErrores() {
		return numErrores;
	}


	public void setNumErrores(int numErrores) {
		this.numErrores = numErrores;
	}


	public String getErrores() {
		return errores;
	}


	public void setErrores(String errores) {
		this.errores = errores;
	}


	public int getMensajesInfo() {
		return mensajesInfo;
	}


	public void setMensajesInfo(int mensajesInfo) {
		this.mensajesInfo = mensajesInfo;
	}


	public int getMensajesAlerta() {
		return mensajesAlerta;
	}


	public void setMensajesAlerta(int mensajesAlerta) {
		this.mensajesAlerta = mensajesAlerta;
	}


	public int getNumeroRevisionesPendientes() {
		return numeroRevisionesPendientes;
	}


	public void setNumeroRevisionesPendientes(int numeroRevisionesPendientes) {
		this.numeroRevisionesPendientes = numeroRevisionesPendientes;
	}


	public boolean isProcesoValido() {
		return procesoValido;
	}


	public void setProcesoValido(boolean procesoValido) {
		this.procesoValido = procesoValido;
	}


	  
}
