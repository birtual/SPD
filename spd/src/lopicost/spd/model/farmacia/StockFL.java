package lopicost.spd.model.farmacia;

import java.io.Serializable;

import java.util.Date;


public class StockFL implements Serializable {

	  private Date fechaInsert;

	  private String cnFL;
	  private String cn6;
	  private String nombreMedicamento;			//NOMBRE 						varchar(255) NOT NULL,
	  private String princActivoPresentacion;	//PRINC_ACTIVO_PRESENTACION 	varchar(255),
	  private String princActivo;				//PRINC_ACTIVO     	 			varchar(255),
	  private String proveedor;					//PROVEEDOR 					varchar(255),
	  private String existencias;				//EXISTENCIAS 		 		    int,
	  private String lote;						//LOTE		 					varchar(50),
	  private String caducidad;					//CADUCIDAD		 				varchar(50),
	  private String FechaMaximaPedido;			//CADUCIDAD		- 75 días		varchar(50),
	  private String pvl_venta;					//PVL_COMPRA		 			float,
	  private String dto_venta;					//DTO_COMPRA		 			float,
	  private String puv;						//PUV 							float,
	  private String almacen;					//ALMACEN 	   					varchar(50),
	  private String CodGtVm;                   // = PRINCIPIO ACTIVO 
	  private String NomGtVm;                   // 
	  private String CodGtVmp;                  // = CONJUNTO HOMOGENEO
	  private String NomGtVmp;                  // 
	  private String CodGtVmpp;                 // = con presentacion
	  private String NomGtVmpp;                 //

	  
	  
	public Date getFechaInsert() {
		return fechaInsert;
	}
	public void setFechaInsert(Date fechaInsert) {
		this.fechaInsert = fechaInsert;
	}
	public String getCn6() {
		return cn6;
	}
	public void setCn6(String cn6) {
		this.cn6 = cn6;
	}
	public String getCnFL() {
		return cnFL;
	}
	public void setCnFL(String cnFL) {
		this.cnFL = cnFL;
	}
	public String getNombreMedicamento() {
		return nombreMedicamento;
	}
	public void setNombreMedicamento(String nombreMedicamento) {
		this.nombreMedicamento = nombreMedicamento;
	}
	public String getPrincActivoPresentacion() {
		return princActivoPresentacion;
	}
	public void setPrincActivoPresentacion(String princActivoPresentacion) {
		this.princActivoPresentacion = princActivoPresentacion;
	}
	public String getPrincActivo() {
		return princActivo;
	}
	public void setPrincActivo(String princActivo) {
		this.princActivo = princActivo;
	}
	public String getProveedor() {
		return proveedor;
	}
	public void setProveedor(String proveedor) {
		this.proveedor = proveedor;
	}
	public String getExistencias() {
		return existencias;
	}
	public void setExistencias(String existencias) {
		this.existencias = existencias;
	}
	public String getLote() {
		return lote;
	}
	public void setLote(String lote) {
		this.lote = lote;
	}
	public String getCaducidad() {
		return caducidad;
	}
	public void setCaducidad(String caducidad) {
		this.caducidad = caducidad;
	}
	public String getPvl_venta() {
		return pvl_venta;
	}
	public void setPvl_venta(String pvl_venta) {
		this.pvl_venta = pvl_venta;
	}
	public String getDto_venta() {
		return dto_venta;
	}
	public void setDto_venta(String dto_venta) {
		this.dto_venta = dto_venta;
	}
	public String getPuv() {
		return puv;
	}
	public void setPuv(String puv) {
		this.puv = puv;
	}
	public String getAlmacen() {
		return almacen;
	}
	public void setAlmacen(String almacen) {
		this.almacen = almacen;
	}
	public String getCodGtVm() {
		return CodGtVm;
	}
	public void setCodGtVm(String codGtVm) {
		CodGtVm = codGtVm;
	}
	public String getNomGtVm() {
		return NomGtVm;
	}
	public void setNomGtVm(String nomGtVm) {
		NomGtVm = nomGtVm;
	}
	public String getCodGtVmp() {
		return CodGtVmp;
	}
	public void setCodGtVmp(String codGtVmp) {
		CodGtVmp = codGtVmp;
	}
	public String getNomGtVmp() {
		return NomGtVmp;
	}
	public void setNomGtVmp(String nomGtVmp) {
		NomGtVmp = nomGtVmp;
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
	public String getFechaMaximaPedido() {
		return FechaMaximaPedido;
	}
	public void setFechaMaximaPedido(String fechaMaximaPedido) {
		FechaMaximaPedido = fechaMaximaPedido;
	}

	  
	  
}
