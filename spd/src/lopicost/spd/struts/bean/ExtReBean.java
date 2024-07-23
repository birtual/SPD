package lopicost.spd.struts.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import lopicost.spd.utils.StringUtil;

public class ExtReBean {
	
	
	  private String  idDivisionResidencia;
	  private String  nombreDivisionResidencia;
	  private int  cipsActivos;
	  private int  cipsProcesadosTrat;
	  private int  cipsProcesadosTratNo;
	  private int  cipsProcesadosRecPend;
	  private int  cipsProcesadosRecPendNo;
	  private int  tratamientosProcesadosResi;
	  private String fechaUltimoProcesoTrat;
	  private String fechaUltimoProcesoRecPend;
	  private String  ultimoCIPProcesado;
	  

	public ExtReBean() {
		super();
	}

	public ExtReBean(String idDivisionResidencia, String  nombreDivisionResidencia, int tratamientosProcesadosResi, int  cipsActivos
			, String fechaUltimoProcesoTrat, int  cipsProcesadosTrat, int  cipsProcesadosTratNo
			, String fechaUltimoProcesoRedPend, int  cipsProcesadosRecPend, int  cipsProcesadosRecPendNo
			, String  ultimoCIPProcesado) {
		this.idDivisionResidencia = idDivisionResidencia;
		this.nombreDivisionResidencia = nombreDivisionResidencia;
		this.cipsActivos = cipsActivos;
		this.cipsProcesadosRecPend = cipsProcesadosRecPend;		
		this.cipsProcesadosRecPendNo = cipsProcesadosRecPendNo;		
		this.cipsProcesadosTrat = cipsProcesadosTrat;
		this.cipsProcesadosTratNo = cipsProcesadosTratNo;
		this.tratamientosProcesadosResi = tratamientosProcesadosResi;
		this.fechaUltimoProcesoRecPend = fechaUltimoProcesoRedPend;
		this.fechaUltimoProcesoTrat = fechaUltimoProcesoTrat;
		this.ultimoCIPProcesado = ultimoCIPProcesado;
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

	public int getCipsActivos() {
		return cipsActivos;
	}

	public void setCipsActivos(int cipsActivos) {
		this.cipsActivos = cipsActivos;
	}

	public int getCipsProcesadosRecPend() {
		return cipsProcesadosRecPend;
	}

	public void setCipsProcesadosRecPend(int cipsProcesadosRecPend) {
		this.cipsProcesadosRecPend = cipsProcesadosRecPend;
	}

	public int getCipsProcesadosTrat() {
		return cipsProcesadosTrat;
	}

	public void setCipsProcesadosTrat(int cipsProcesadosTrat) {
		this.cipsProcesadosTrat = cipsProcesadosTrat;
	}

	public int getTratamientosProcesadosResi() {
		return tratamientosProcesadosResi;
	}

	public void setTratamientosProcesadosResi(int tratamientosProcesadosResi) {
		this.tratamientosProcesadosResi = tratamientosProcesadosResi;
	}

	public String getFechaUltimoProcesoTrat() {
		return fechaUltimoProcesoTrat;
	}

	public void setFechaUltimoProcesoTrat(String fechaUltimoProcesoTrat) {
		this.fechaUltimoProcesoTrat = fechaUltimoProcesoTrat;
	}

	public String getFechaUltimoProcesoRecPend() {
		return fechaUltimoProcesoRecPend;
	}

	public void setFechaUltimoProcesoRecPend(String fechaUltimoProcesoRedPend) {
		this.fechaUltimoProcesoRecPend = fechaUltimoProcesoRedPend;
	}

	public String getUltimoCIPProcesado() {
		return ultimoCIPProcesado;
	}

	public void setUltimoCIPProcesado(String ultimoCIPProcesado) {
		this.ultimoCIPProcesado = ultimoCIPProcesado;
	}

	public int getCipsProcesadosTratNo() {
		return cipsProcesadosTratNo;
	}

	public void setCipsProcesadosTratNo(int cipsProcesadosTratNo) {
		this.cipsProcesadosTratNo = cipsProcesadosTratNo;
	}

	public int getCipsProcesadosRecPendNo() {
		return cipsProcesadosRecPendNo;
	}

	public void setCipsProcesadosRecPendNo(int cipsProcesadosRecPendNo) {
		this.cipsProcesadosRecPendNo = cipsProcesadosRecPendNo;
	}

	
	
}
