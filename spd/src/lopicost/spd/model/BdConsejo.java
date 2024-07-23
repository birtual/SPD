package lopicost.spd.model;

import java.io.Serializable;
import java.util.Date;

public class BdConsejo implements Serializable {
	  
	  private Date fechaProcesoSpdac;				//dataHoraProces        datetime,
	  private Date fechaActualizacionConsejo;		//ultActualizConsejo    varchar(128),
	  private String cnConsejo; 					//CODIGO 				char(6) NOT NULL,

	  private String nombreMedicamento;				//NOMBRE 				varchar(512) NOT NULL,
	  private String presentacion;					//PRESENTACION          varchar(256),
	  private String codigoGrupoTerapeutico;		//CodigoGT              char(12),
	  private String grupoTerapeutico;				//GrupoTerapeutico      varchar(255),
	  private String codigoLaboratorio;				//codiLABO              char(5),
	  private String nombreLaboratorio;				//nomLABO               varchar(50),
	  private float pvl;							//PVL                   float,
	  private float pvp;							//PVP                   float,
	  private int unidades;							//UNIDADES              int,
	  private int unidadesEnvase;					//UdsEnvase             int,
//	  private int codigoPrincipioActivo;			//codiPactivo           smallint,		//TODO 20201001  - Se debería sustituir por codGtVm. a día de hoy no existe aún la tabla GTVM en Consejo
//	  private String nombrePrincipioActivo;			//nomPactivo            varchar(255),	//TODO 20201001  - Se debería sustituir por nomGtVm. a día de hoy no existe aún la tabla GTVM en Consejo
	  private Date fechaAlta;						//FechaALTA             datetime,
	  private Date fechaBaja;						//FechaBAJA             datetime,

	  private String aportacion;					//APORTACION            char(4),
	  private int composicionPor;					//ComposicionPOR        smallint,
	  private int codigoFormaFarmaceutica;			//FormaFarmaceutica     smallint,
	  private String financiado;					//Financiado            varchar(10),
	  private String emblistable;					//Emblistable           varchar(10),
	  private String sustituible;					//Sustituible           varchar(10),
	  private String nombreFormaFarmaceutica;		//nomFormaFarmaceutica  varchar(30),
	  private String marcaEFG;						//MarcaEFG              char(3),
	//  private String codigoConjuntoHomogeneo;		//CodConjHomog          varchar(30),    ahora es GTVMPP
	 // private String nombreConjuntoHomogeneo;		//NomConjHomog          varchar(250),
	  private String descripCorta;					//DescripCorta          varchar(250),
	  private String descripBolsa;					//DescripBolsa          varchar(250)
	  private String nombreConsejo;					//nombreConsejo  + PRESENTACION

	  private String codGtAtcNivel3 =""; 			// nivel de grupo terapéutico de la ATC (mismo subgrupo farmacológico/terapéutico).
	  private String nomGtAtcNivel3 ="";		
	  private String codGtAtcNivel4 ="";			// nivel de grupo terapéutico de la ATC (mismo subgrupo farmacológico/terapéutico).
	  private String nomGtAtcNivel4 ="";		
	  private String codGtAtcNivel5 ="";			// nivel de grupo terapéutico de la ATC (mismo principio activo)
	  private String nomGtAtcNivel5 ="";			

	  private String codGtVm ="";					// grupo VM - Virtual Medicinal  (igual principio activo).  
	  private String nomGtVm ="";				
	  private String codGtVmp ="";					// grupo VMP - Virtual Medicinal Product (igual principio activo, dosis y forma farmacéutica).  
	  private String nomGtVmp ="";				
	  private String codGtVmpp =""; 				// grupo VMPP - Virtual Medicinal Product Package (igual principio activo, dosis, forma farmacéutica y número de unidades de dosificación).
	  private String nomGtVmpp ="";					
	  private int nota;					//campo auxiliar para mostrar la ponderación+rentabilidad de la biblia,
				
	  public int getNota() {
			return nota;
	  }

	  public void setNota(int nota) {
			this.nota = nota;
	  }

	  public BdConsejo() {
		super();
	  }

	  public String getNombreConsejo() {
		return nombreConsejo ;
	  }

	  public void setCnConsejo(String cnConsejo) {
		this.cnConsejo = cnConsejo;
	  }

	  public void setNombreConsejo(String nombreConsejo) {
		this.nombreConsejo = nombreConsejo;
	  }	
	  
	  public Date getFechaProcesoSpdac() {
		return fechaProcesoSpdac;
	  }

	  public void setFechaProcesoSpdac(Date fechaProcesoSpdac) {
		this.fechaProcesoSpdac = fechaProcesoSpdac;
	  }

	  public Date getFechaActualizacionConsejo() {
		return fechaActualizacionConsejo;
	  }

	  public void setFechaActualizacionConsejo(Date fechaActualizacionConsejo) {
		this.fechaActualizacionConsejo = fechaActualizacionConsejo;
	  }

	  public String getCnConsejo() {
		return cnConsejo;
	  }

	  public void setConsejo(String cnConsejo) {
		 this.cnConsejo = cnConsejo;
	  }

	  public String getNombreMedicamento() {
		return nombreMedicamento;
	  }

	  public void setNombreMedicamento(String nombreMedicamento) {
		this.nombreMedicamento = nombreMedicamento;
	  }

	  public String getPresentacion() {
		return presentacion;
	  }

	  public void setPresentacion(String presentacion) {
		this.presentacion = presentacion;
	  }

	  public String getCodigoGrupoTerapeutico() {
		return codigoGrupoTerapeutico;
	  }

	  public void setCodigoGrupoTerapeutico(String codigoGrupoTerapeutico) {
		this.codigoGrupoTerapeutico = codigoGrupoTerapeutico;
	  }

	  public String getGrupoTerapeutico() {
		return grupoTerapeutico;
	  }

	  public void setGrupoTerapeutico(String grupoTerapeutico) {
		this.grupoTerapeutico = grupoTerapeutico;
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

	  public float getPvl() {
		return pvl;
	  }

	  public void setPvl(float pvl) {
		this.pvl = pvl;
	  }

	  public float getPvp() {
		return pvp;
	  }

	  public void setPvp(float pvp) {
		this.pvp = pvp;
	  }

	  public int getUnidades() {
		return unidades;
	  }

	  public void setUnidades(int unidades) {
		this.unidades = unidades;
	  }

	  public int getUnidadesEnvase() {
		return unidadesEnvase;
	  }

	  public void setUnidadesEnvase(int unidadesEnvase) {
		this.unidadesEnvase = unidadesEnvase;
	  }
/*
	  public int getCodigoPrincipioActivo() {
		return codigoPrincipioActivo;
	  }

	  public void setCodigoPrincipioActivo(int codigoPrincipioActivo) {
		this.codigoPrincipioActivo = codigoPrincipioActivo;
	  }

	  public String getNombrePrincipioActivo() {
		return nombrePrincipioActivo;
	  }

	  public void setNombrePrincipioActivo(String nombrePrincipioActivo) {
		this.nombrePrincipioActivo = nombrePrincipioActivo;
	  }
*/
	  public Date getFechaAlta() {
		return fechaAlta;
	  }

	  public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	  }

	  public Date getFechaBaja() {
		return fechaBaja;
	  }

	  public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	  }

	  public String getAportacion() {
		return aportacion;
	  }

	  public void setAportacion(String aportacion) {
		this.aportacion = aportacion;
	  }

	  public int getComposicionPor() {
		return composicionPor;
	  }

	  public void setComposicionPor(int composicionPor) {
		this.composicionPor = composicionPor;
	  }

	  public int getCodigoFormaFarmaceutica() {
		return codigoFormaFarmaceutica;
	  }

	  public void setCodigoFormaFarmaceutica(int codigoFormaFarmaceutica) {
		this.codigoFormaFarmaceutica = codigoFormaFarmaceutica;
	  }

	  public String getFinanciado() {
		return financiado;
	  }	

	  public void setFinanciado(String financiado) {
		this.financiado = financiado;
	  }

	  public String getEmblistable() {
		return emblistable;
	  }

	  public void setEmblistable(String emblistable) {
		this.emblistable = emblistable;
	  }

	  public String getNombreFormaFarmaceutica() {
		return nombreFormaFarmaceutica;
	  }

	  public void setNombreFormaFarmaceutica(String nombreFormaFarmaceutica) {
		this.nombreFormaFarmaceutica = nombreFormaFarmaceutica;
	  }

	  public String getMarcaEFG() {
		return marcaEFG;
	  }

	  public void setMarcaEFG(String marcaEFG) {
		this.marcaEFG = marcaEFG;
	  }

/*	  public String getCodigoConjuntoHomogeneo() {
		return codigoConjuntoHomogeneo;
	  }

	  public void setCodigoConjuntoHomogeneo(String codigoConjuntoHomogeneo) {
		this.codigoConjuntoHomogeneo = codigoConjuntoHomogeneo;
	  }

	  public String getNombreConjuntoHomogeneo() {
		return nombreConjuntoHomogeneo;
	  }

	  public void setNombreConjuntoHomogeneo(String nombreConjuntoHomogeneo) {
		this.nombreConjuntoHomogeneo = nombreConjuntoHomogeneo;
	  }
*/
	  public String getDescripCorta() {
		return descripCorta;
	  }

	  public void setDescripCorta(String descripCorta) {
		this.descripCorta = descripCorta;
	  }

	  public String getDescripBolsa() {
		return descripBolsa;
	  }

	  public void setDescripBolsa(String descripBolsa) {
		this.descripBolsa = descripBolsa;
	  }

	  public String getCodGtAtcNivel3() {
		 return codGtAtcNivel3;
	  }

	  public void setCodGtAtcNivel3(String codGtAtcNivel3) {
		this.codGtAtcNivel3 = codGtAtcNivel3;
	  }

	  public String getNomGtAtcNivel3() {
		return nomGtAtcNivel3;
	  }

	  public void setNomGtAtcNivel3(String nomGtAtcNivel3) {
		this.nomGtAtcNivel3 = nomGtAtcNivel3;
	  }

	  public String getCodGtAtcNivel4() {
		return codGtAtcNivel4;
	  }

	  public void setCodGtAtcNivel4(String codGtAtcNivel4) {
		this.codGtAtcNivel4 = codGtAtcNivel4;
	  }

	  public String getNomGtAtcNivel4() {
		return nomGtAtcNivel4;
	  }

	  public void setNomGtAtcNivel4(String nomGtAtcNivel4) {
		this.nomGtAtcNivel4 = nomGtAtcNivel4;
	  }

	  public String getCodGtAtcNivel5() {
		return codGtAtcNivel5;
	  }

	  public void setCodGtAtcNivel5(String codGtAtcNivel5) {
		this.codGtAtcNivel5 = codGtAtcNivel5;
	  }

	  public String getNomGtAtcNivel5() {
		return nomGtAtcNivel5;
	  }

	  public void setNomGtAtcNivel5(String nomGtAtcNivel5) {
		this.nomGtAtcNivel5 = nomGtAtcNivel5;
	  }

	  public String getCodGtVm() {
		return codGtVm;
	  }

	  public void setCodGtVm(String codGtVm) {
		this.codGtVm = codGtVm;
	  }

	  public String getNomGtVm() {
		return nomGtVm;
	  }

	  public void setNomGtVm(String nomGtVm) {
		this.nomGtVm = nomGtVm;
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

	  public String getCodGtVmpp() {
		return codGtVmpp;
	  }

	  public void setCodGtVmpp(String codGtVmpp) {
		this.codGtVmpp = codGtVmpp;
	  }

	  public String getNomGtVmpp() {
		return nomGtVmpp;
	  }

	  public void setNomGtVmpp(String nomGtVmpp) {
		this.nomGtVmpp = nomGtVmpp;
	  }

	  public String getSustituible() {
		return sustituible;
	  }

	  public void setSustituible(String sustituible) {
		this.sustituible = sustituible;
	  }

}
