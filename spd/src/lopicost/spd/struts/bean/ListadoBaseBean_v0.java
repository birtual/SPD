package lopicost.spd.struts.bean;

import java.util.Date;
import java.util.List;

import lopicost.spd.model.BdConsejo;
import lopicost.spd.struts.form.FicheroResiForm;
import lopicost.spd.utils.StringUtil;

public class ListadoBaseBean_v0 {
	
	
	  //Datos de cabecera

	  private String idFicheroBase;
	  private String idEstado="PENDIENTE_VALIDACION";
	  private Date ultimaProduccion;
	  private Date fechaSubidaFichero;
	  private int filasTotales=0;
	  private int cipsFichero=0;
	  private int cipsFicheroNoExistentesBbdd=0;
	  private int cipsActivosBbddNoExistentesEnFichero=0;
	  private String usuarioValidacion="";
	  private String nombreFicheroResi;
	  
	  

	  private int numeroMensajesInfo=0;
	  private int numeroMensajesAlerta=0;
	  
	  private boolean valido=true;
	  private boolean editable=true;
	
	  
	  
	  
	  	public String getIdFicheroBase() {
			return idFicheroBase;
		}
		public void setIdFicheroBase(String idFicheroBase) {
			this.idFicheroBase = idFicheroBase;
		}
		public String getIdEstado() {
			return idEstado;
		}
		public void setIdEstado(String idEstado) {
			this.idEstado = idEstado;
		}
		public Date getUltimaProduccion() {
			return ultimaProduccion;
		}
		public void setUltimaProduccion(Date ultimaProduccion) {
			this.ultimaProduccion = ultimaProduccion;
		}
		public Date getFechaSubidaFichero() {
			return fechaSubidaFichero;
		}
		public void setFechaSubidaFichero(Date fechaSubidaFichero) {
			this.fechaSubidaFichero = fechaSubidaFichero;
		}
		public int getFilasTotales() {
			return filasTotales;
		}
		public void setFilasTotales(int filasTotales) {
			this.filasTotales = filasTotales;
		}
		public int getCipsFichero() {
			return cipsFichero;
		}
		public void setCipsFichero(int cipsFichero) {
			this.cipsFichero = cipsFichero;
		}
		public int getCipsFicheroNoExistentesBbdd() {
			return cipsFicheroNoExistentesBbdd;
		}
		public void setCipsFicheroNoExistentesBbdd(int cipsFicheroNoExistentesBbdd) {
			this.cipsFicheroNoExistentesBbdd = cipsFicheroNoExistentesBbdd;
		}
		public int getCipsActivosBbddNoExistentesEnFichero() {
			return cipsActivosBbddNoExistentesEnFichero;
		}
		public void setCipsActivosBbddNoExistentesEnFichero(int cipsActivosBbddNoExistentesEnFichero) {
			this.cipsActivosBbddNoExistentesEnFichero = cipsActivosBbddNoExistentesEnFichero;
		}
		public String getUsuarioValidacion() {
			return usuarioValidacion;
		}
		public void setUsuarioValidacion(String usuarioValidacion) {
			this.usuarioValidacion = usuarioValidacion;
		}
		public String getNombreFicheroResi() {
			return nombreFicheroResi;
		}
		public void setNombreFicheroResi(String nombreFicheroResi) {
			this.nombreFicheroResi = nombreFicheroResi;
		}
		public int getNumeroMensajesInfo() {
			return numeroMensajesInfo;
		}
		public void setNumeroMensajesInfo(int numeroMensajesInfo) {
			this.numeroMensajesInfo = numeroMensajesInfo;
		}
		public int getNumeroMensajesAlerta() {
			return numeroMensajesAlerta;
		}
		public void setNumeroMensajesAlerta(int numeroMensajesAlerta) {
			this.numeroMensajesAlerta = numeroMensajesAlerta;
		}
		public boolean isValido() {
			return valido;
		}
		public void setValido(boolean valido) {
			this.valido = valido;
		}
		public boolean isEditable() {
			return editable;
		}
		public void setEditable(boolean editable) {
			this.editable = editable;
		}
		
	  
	  
	
}
