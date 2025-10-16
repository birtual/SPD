package lopicost.spd.model.farmacia;

import java.io.Serializable;

import java.util.Date;


public class StockFL implements Serializable {

	  private Date fechaInsert;

	  private String cnFL;
	  private String cn6;
	  private String nombreMedicamento;			//NOMBRE 						varchar(255) NOT NULL,
	  private String codigoLaboratorio;			//CODILAB,
	  private String nombreLaboratorio;			//NOMLAB,
	  private String existencias;				//EXISTENCIAS 		 		    int,
	  private String stockMinimo;				//EXISTENCIAS 		 		    int,
	  private String pvl;						//PVL				 			float,
	  private String dto_venta;					//DTO_COMPRA		 			float,
//	  private String almacen;					//ALMACEN 	   					varchar(50),
//	  private String CodGtVm;                   // = PRINCIPIO ACTIVO 
//	  private String NomGtVm;                   // 
//	  private String CodGtVmp;                  // = CONJUNTO HOMOGENEO
//	  private String NomGtVmp;                  // 
	  private String CodGtVmpp;                 // = con presentacion
	  private String NomGtVmpp;                 //
//	  private String princActivoPresentacion;	//PRINC_ACTIVO_PRESENTACION 	varchar(255),
//	  private String princActivo;				//PRINC_ACTIVO     	 			varchar(255),
//	  private String proveedor;					//PROVEEDOR 					varchar(255),
//	  private String lote;						//LOTE		 					varchar(50),
//	  private String caducidad;					//CADUCIDAD		 				varchar(50),
//	  private String FechaMaximaPedido;			//CADUCIDAD		- 75 días		varchar(50),
//	  private String puv;						//PUV 							float,
	public Date getFechaInsert() {
		return fechaInsert;
	}
	public void setFechaInsert(Date fechaInsert) {
		this.fechaInsert = fechaInsert;
	}
	public String getCnFL() {
		return cnFL;
	}
	public void setCnFL(String cnFL) {
		this.cnFL = cnFL;
	}
	public String getCn6() {
		return cn6;
	}
	public void setCn6(String cn6) {
		this.cn6 = cn6;
	}
	public String getNombreMedicamento() {
		return nombreMedicamento;
	}
	public void setNombreMedicamento(String nombreMedicamento) {
		this.nombreMedicamento = nombreMedicamento;
	}
	public String getCodigoLaboratorio() {
		return codigoLaboratorio;
	}
	public void setCodigoLaboratorio(String codigoLaboratorio) {
		this.codigoLaboratorio = codigoLaboratorio;
	}
	public String getNombreLaboratorio() {
		return nombreLaboratorio;
	}
	public void setNombreLaboratorio(String nombreLaboratorio) {
		this.nombreLaboratorio = nombreLaboratorio;
	}
	public String getExistencias() {
		return existencias;
	}
	public void setExistencias(String existencias) {
		this.existencias = existencias;
	}
	public String getStockMinimo() {
		return stockMinimo;
	}
	public void setStockMinimo(String stockMinimo) {
		this.stockMinimo = stockMinimo;
	}
	public String getPvl() {
		return pvl;
	}
	public void setPvl(String pvl) {
		this.pvl = pvl;
	}
	public String getDto_venta() {
		return dto_venta;
	}
	public void setDto_venta(String dto_venta) {
		this.dto_venta = dto_venta;
	}
	public String getCodGtVmpp() {
		return CodGtVmpp;
	}
	public void setCodGtVmpp(String codGtVmpp) {
		CodGtVmpp = codGtVmpp;
	}
	public String getNomGtVmpp() {
		return NomGtVmpp;
	}
	public void setNomGtVmpp(String nomGtVmpp) {
		NomGtVmpp = nomGtVmpp;
	}

	  


	  
	  
}
