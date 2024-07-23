package lopicost.spd.struts.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import lopicost.spd.utils.StringUtil;

public class ControlDataImportBean {
	
	
	  private String  	nombreTabla;
	  private String  	relaciones;
	  private List<ControlDataImportBean>  	detalleBeans;
	  private int  		UPfarmacia;
	  private String  	idFarmacia;
	  private String  	nombreFarmacia;
	  private String  	idDivisionResidencia;
	  private String  	nombreDivisionResidencia;
	  private String  	nombreResidenciaFarmatic;
	  private String  	CIP;
	  private int  		numeroCIPs;
	  private String 	ultimaFechaRecogida;
	  private String 	ultimaFechaEnOrigen;
	  private String 	alerta;
	  private int  		diasDesdeUltimaFecha;
	  private int  		cuantos;
		  
		  
		  
	public int getCuantos() {
		return cuantos;
	}

	public void setCuantos(int cuantos) {
		this.cuantos = cuantos;
	}

	public String getNombreResidenciaFarmatic() {
		return nombreResidenciaFarmatic;
	}

	public void setNombreResidenciaFarmatic(String nombreResidenciaFarmatic) {
		this.nombreResidenciaFarmatic = nombreResidenciaFarmatic;
	}

	public String getNombreFarmacia() {
		return nombreFarmacia;
	}

	public void setNombreFarmacia(String nombreFarmacia) {
		this.nombreFarmacia = nombreFarmacia;
	}

	public List<ControlDataImportBean> getDetalleBeans() {
		return detalleBeans;
	}

	public void setDetalleBeans(List<ControlDataImportBean> listaBeans) {
		this.detalleBeans = listaBeans;
	}

	public int getUPfarmacia() {
		return UPfarmacia;
	}

	public void setUPfarmacia(int uPfarmacia) {
		UPfarmacia = uPfarmacia;
	}

	public String getIdFarmacia() {
		return idFarmacia;
	}

	public void setIdFarmacia(String idFarmacia) {
		this.idFarmacia = idFarmacia;
	}



	public ControlDataImportBean() {
		super();
	}

	public String getNombreTabla() {
		return nombreTabla;
	}

	public void setNombreTabla(String nombreTabla) {
		this.nombreTabla = nombreTabla;
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

	public String getCIP() {
		return CIP;
	}

	public void setCIP(String cIP) {
		CIP = cIP;
	}

	public String getUltimaFechaRecogida() {
		return ultimaFechaRecogida;
	}

	public void setUltimaFechaRecogida(String fechaUltimoProcesoTrat) {
		this.ultimaFechaRecogida = fechaUltimoProcesoTrat;
	}

	public String getAlerta() {
		return alerta;
	}

	public void setAlerta(String alerta) {
		this.alerta = alerta;
	}

	public int getNumeroCIPs() {
		return numeroCIPs;
	}

	public void setNumeroCIPs(int numeroCIPs) {
		this.numeroCIPs = numeroCIPs;
	}

	public int getDiasDesdeUltimaFecha() {
		return diasDesdeUltimaFecha;
	}

	public void setDiasDesdeUltimaFecha(int diasDesdeUltimaFecha) {
		this.diasDesdeUltimaFecha = diasDesdeUltimaFecha;
	}

	public String getRelaciones() {
		return relaciones;
	}

	public void setRelaciones(String relaciones) {
		this.relaciones = relaciones;
	}

	public String getUltimaFechaEnOrigen() {
		return ultimaFechaEnOrigen;
	}

	public void setUltimaFechaEnOrigen(String ultimaFechaEnOrigen) {
		this.ultimaFechaEnOrigen = ultimaFechaEnOrigen;
	}

	
	
}
