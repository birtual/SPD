package lopicost.spd.struts.bean;

import java.io.Serializable;

public class DiscrepanciaBean implements Serializable, Cloneable  {
	
	
  private int oidDivisionResidencia;
  private String idDivisionResidencia="";
  private String nombreDivisionResidencia="";
  private String CIP="";
  private String nombrePaciente="";
  
  private String resiCn="";
  private String spdCnSust="";
  private String recetaCn="";

  private String resiMedicamento="";
  private String spdMedicamento="";
  private String recetaMedicamento="";
  
  private String spdNombreBolsa="";
  private String spdFormaMedicacion="";
  private String spdAccionBolsa="";
    
  private String codGtVmp="";	  
  private String nomGtVmp="";	  

  
  private String siPrecisa="";

  private String recetaCantidad="";
  private String recetaPauta="";
  private float spdComprimidosDia;
  private float spdPrevision=0;
  private float recetaComprimidosDia;
  private float recetaPrevision=0;
  private boolean cuadraPrevision=false;

  private String recetaInicioTratamiento="";
  private String recetaFinTratamiento="";
  
  private String recetaCaducidad="";


  private int totalRecetasDispensadas=0;
  private int totalRecetasTratamiento=0;
  private int totalRecetasDisponibles=0;


	
	
		  
		  public DiscrepanciaBean clone() {
		        try {
		            return (DiscrepanciaBean) super.clone();
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


		public String getResiCn() {
			return resiCn;
		}


		public void setResiCn(String resiCn) {
			this.resiCn = resiCn;
		}


		public String getSpdCnSust() {
			return spdCnSust;
		}


		public void setSpdCnSust(String spdFinal) {
			this.spdCnSust = spdFinal;
		}


		public String getRecetaCn() {
			return recetaCn;
		}


		public void setRecetaCn(String recetaCn) {
			this.recetaCn = recetaCn;
		}


		public String getResiMedicamento() {
			return resiMedicamento;
		}


		public void setResiMedicamento(String resiMedicamento) {
			this.resiMedicamento = resiMedicamento;
		}


		public String getSpdMedicamento() {
			return spdMedicamento;
		}


		public void setSpdMedicamento(String spdMedicamento) {
			this.spdMedicamento = spdMedicamento;
		}


		public String getRecetaMedicamento() {
			return recetaMedicamento;
		}


		public void setRecetaMedicamento(String recetaMedicamento) {
			this.recetaMedicamento = recetaMedicamento;
		}


		public String getSpdNombreBolsa() {
			return spdNombreBolsa;
		}


		public void setSpdNombreBolsa(String spdNombreBolsa) {
			this.spdNombreBolsa = spdNombreBolsa;
		}


		public String getSpdFormaMedicacion() {
			return spdFormaMedicacion;
		}


		public void setSpdFormaMedicacion(String spdFformaMedicacion) {
			this.spdFormaMedicacion = spdFformaMedicacion;
		}


		public String getSpdAccionBolsa() {
			return spdAccionBolsa;
		}


		public void setSpdAccionBolsa(String spdAccionBolsa) {
			this.spdAccionBolsa = spdAccionBolsa;
		}


		public String getCodGtVmp() {
			return codGtVmp;
		}


		public void setCodGtVmp(String spdCodGtVmp) {
			this.codGtVmp = spdCodGtVmp;
		}

		public String getNomGtVmp() {
			return nomGtVmp;
		}


		public void setNomGtVmp(String spdNomGtVmp) {
			this.nomGtVmp = spdNomGtVmp;
		}


		public String getSiPrecisa() {
			return siPrecisa;
		}


		public void setSiPrecisa(String siPrecisa) {
			this.siPrecisa = siPrecisa;
		}


		public float getSpdComprimidosDia() {
			return spdComprimidosDia;
		}


		public void setSpdComprimidosDia(float spdComprimidosDia) {
			this.spdComprimidosDia = spdComprimidosDia;
		}


		public float getSpdPrevision() {
			return spdPrevision;
		}


		public void setSpdPrevision(float spdPrevision) {
			this.spdPrevision = spdPrevision;
		}


		public float getRecetaPrevision() {
			return recetaPrevision;
		}


		public void setRecetaPrevision(float recetaPrevision) {
			this.recetaPrevision = recetaPrevision;
		}


		public boolean isCuadraPrevision() {
			return Math.round(this.spdPrevision)==Math.round(this.recetaPrevision);
		}


		public void setCuadraPrevision(boolean cuadraPrevision) {
			this.cuadraPrevision = cuadraPrevision;
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


		public String getRecetaCaducidad() {
			return recetaCaducidad;
		}


		public void setRecetaCaducidad(String recetaCaducidad) {
			this.recetaCaducidad = recetaCaducidad;
		}


		public float getRecetaComprimidosDia() {
			return recetaComprimidosDia;
		}


		public void setRecetaComprimidosDia(float recetaComprimidosDia) {
			this.recetaComprimidosDia = recetaComprimidosDia;
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


	