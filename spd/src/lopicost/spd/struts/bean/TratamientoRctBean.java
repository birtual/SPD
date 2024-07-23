package lopicost.spd.struts.bean;

import java.io.Serializable;

public class TratamientoRctBean implements Serializable, Cloneable  {
	
	
  private int oidDivisionResidencia;
  private String idDivisionResidencia="";
  private String nombreDivisionResidencia="";
  private String CIP="";
  private String nombrePaciente="";
  

  private String recetaCn="";
  private String recetaMedicamento="";
  private String codGtVmp="";	  
  private String nomGtVmp="";	  
  private String siPrecisa="";
  private String recetaCantidad="";
  private String recetaPauta="";
  private String recetaInicioTratamiento="";
  private String recetaFinTratamiento="";
  private String recetaCaducidad="";
  private int totalRecetasDispensadas=0;
  private int totalRecetasTratamiento=0;
  private int totalRecetasDisponibles=0;


	
	
		  
		  public TratamientoRctBean clone() {
		        try {
		            return (TratamientoRctBean) super.clone();
		        } catch (CloneNotSupportedException e) {
		            // Manejo de excepciones en caso de que no se pueda clonar
		            return null;
		        }
		    }


		public int getOidDivisionResidencia() {
			return oidDivisionResidencia;
		}


		public void setOidDivisionResidencia(int oidDivisionResidencia) {
			this.oidDivisionResidencia = oidDivisionResidencia;
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


		public String getNombrePaciente() {
			return nombrePaciente;
		}


		public void setNombrePaciente(String nombrePaciente) {
			this.nombrePaciente = nombrePaciente;
		}


		public String getRecetaCn() {
			return recetaCn;
		}


		public void setRecetaCn(String recetaCn) {
			this.recetaCn = recetaCn;
		}


		public String getRecetaMedicamento() {
			return recetaMedicamento;
		}


		public void setRecetaMedicamento(String recetaMedicamento) {
			this.recetaMedicamento = recetaMedicamento;
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


		public String getSiPrecisa() {
			return siPrecisa;
		}


		public void setSiPrecisa(String siPrecisa) {
			this.siPrecisa = siPrecisa;
		}


		public String getRecetaCantidad() {
			return recetaCantidad;
		}


		public void setRecetaCantidad(String recetaCantidad) {
			this.recetaCantidad = recetaCantidad;
		}


		public String getRecetaPauta() {
			return recetaPauta;
		}


		public void setRecetaPauta(String recetaPauta) {
			this.recetaPauta = recetaPauta;
		}


		public String getRecetaInicioTratamiento() {
			return recetaInicioTratamiento;
		}


		public void setRecetaInicioTratamiento(String recetaInicioTratamiento) {
			this.recetaInicioTratamiento = recetaInicioTratamiento;
		}


		public String getRecetaFinTratamiento() {
			return recetaFinTratamiento;
		}


		public void setRecetaFinTratamiento(String recetaFinTratamiento) {
			this.recetaFinTratamiento = recetaFinTratamiento;
		}


		public String getRecetaCaducidad() {
			return recetaCaducidad;
		}


		public void setRecetaCaducidad(String recetaCaducidad) {
			this.recetaCaducidad = recetaCaducidad;
		}


		public int getTotalRecetasDispensadas() {
			return totalRecetasDispensadas;
		}


		public void setTotalRecetasDispensadas(int totalRecetasDispensadas) {
			this.totalRecetasDispensadas = totalRecetasDispensadas;
		}


		public int getTotalRecetasTratamiento() {
			return totalRecetasTratamiento;
		}


		public void setTotalRecetasTratamiento(int totalRecetasTratamiento) {
			this.totalRecetasTratamiento = totalRecetasTratamiento;
		}


		public int getTotalRecetasDisponibles() {
			return totalRecetasDisponibles;
		}


		public void setTotalRecetasDisponibles(int totalRecetasDisponibles) {
			this.totalRecetasDisponibles = totalRecetasDisponibles;
		}


}


	