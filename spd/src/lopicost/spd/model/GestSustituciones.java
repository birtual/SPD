package lopicost.spd.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GestSustituciones implements Serializable {
	  private int oidGestSustituciones;
	  private Date fecha;
	  private String idDivisionResidencia;
	  private String nombreDivisionResidencia;


	  private int oidDivisionResidencia;
	  private String cnResi;
	  private String medicamentoResi;
	  private String cnOk;
	  private String nombreCorto;
	  private String formaFarmaceuticaSustitucion;	 //por si no existe en el consejo

	  private String nombreMedicamentoOk;			//NOMBRE 				varchar(250) NOT NULL,
//	  private String codConjHomogOk;				//CodConjHomog     	 	varchar(30),
//	  private String nomConjHomogOk;				//NomConjHomog     	 	varchar(250),
	  private String codGtVmppOk;					//codGtVmpp = ConjHomog  varchar(30),
	  private String nomGtVmppOk;					//codGtVmpp = 		    varchar(250),

	
	  private String accion;
	  private String comentario;
	  private int oidUsuario=0;
	  private String comentarioInterno="";
	  private List<GestSustitucionesXResi> listaSustitucionesXResi =new ArrayList<GestSustitucionesXResi>();
	  private int sustXResiAsociados=0;

	  private BdConsejo bdConsejo;
	  private BdConsejo bdConsejoResi;//cnResi que existe en bdConsejo

	  private BdConsejo bdConsejoBiblia;

	  private String existeBdConsejo="";
	  private String existeBdConsejoResi="";   //cnResi que existe en bdConsejo

	  private String existeBdConsejoBiblia="";
	  private boolean gtVmppDiferente=false;

	  private String mensajesInfo="";
	
	  public String getCodGtVmppOk() {
			return codGtVmppOk;
		}


		public void setCodGtVmppOk(String codGtVmpOk) {
			this.codGtVmppOk = codGtVmpOk;
		}


		public String getNomGtVmppOk() {
			return nomGtVmppOk;
		}


		public void setNomGtVmppOk(String nomGtVmpOk) {
			this.nomGtVmppOk = nomGtVmpOk;
		}



		 private String mensajesAlerta="";

	  private String sustituible="1";
	  
	  public String getSustituible() {
		return sustituible;
	}


	public void setSustituible(String sustituible) {
		this.sustituible = sustituible;
	}

	  public String getNombreMedicamentoOk() {
		return nombreMedicamentoOk;
	}


	public void setNombreMedicamentoOk(String nombreMedicamentoOk) {
		this.nombreMedicamentoOk = nombreMedicamentoOk;
	}

/*
	public String getCodConjHomogOk() {
		return codConjHomogOk;
	}


	public void setCodConjHomogOk(String codConjHomogOk) {
		this.codConjHomogOk = codConjHomogOk;
	}


	public String getNomConjHomogOk() {
		return nomConjHomogOk;
	}


	public void setNomConjHomogOk(String nomConjHomogOk) {
		this.nomConjHomogOk = nomConjHomogOk;
	}
	*/
	
	  public String getMensajesInfo() {
		return mensajesInfo;
	}


	public void setMensajesInfo(String mensajesInfo) {
		this.mensajesInfo = mensajesInfo;
	}


	public String getMensajesAlerta() {
		return mensajesAlerta;
	}


	public void setMensajesAlerta(String mensajesAlerta) {
		this.mensajesAlerta = mensajesAlerta;
	}


	public boolean isGtVmppDiferente() {
		return gtVmppDiferente;
	}


	public void setGtVmppDiferente(boolean conjHomogDiferente) {
		this.gtVmppDiferente = conjHomogDiferente;
	}


	public String getExisteBdConsejoBiblia() {
		return existeBdConsejoBiblia;
	}


	public void setExisteBdConsejoBiblia(String existeBdConsejoBiblia) {
		this.existeBdConsejoBiblia = existeBdConsejoBiblia;
	}

	  
	public String getNombreDivisionResidencia() {
		return nombreDivisionResidencia;
	}


	public void setNombreDivisionResidencia(String nombreDivisionResidencia) {
		this.nombreDivisionResidencia = nombreDivisionResidencia;
	}

	public int getSustXResiAsociados() {
		return sustXResiAsociados;
	}


	public void setSustXResiAsociados(int sustXResiAsociados) {
		this.sustXResiAsociados = sustXResiAsociados;
	}


	public List<GestSustitucionesXResi> getListaSustitucionesXResi() {
		return listaSustitucionesXResi;
	}


	public void setListaSustitucionesXResi(List<GestSustitucionesXResi> listaSustitucionesXResi) {
		this.listaSustitucionesXResi = listaSustitucionesXResi;
	}


	public String getExisteBdConsejo() {
		return existeBdConsejo;
	}


	public void setExisteBdConsejo(String existeBdConsejo) {
		this.existeBdConsejo = existeBdConsejo;
	}


	public GestSustituciones() {
		  super();
		  }

	  
	public BdConsejo getBdConsejo() {
		return bdConsejo;
	}


	public void setBdConsejo(BdConsejo bdConsejo) {
		this.bdConsejo = bdConsejo;
	}
	
	public String getFormaFarmaceuticaSustitucion() {
		return formaFarmaceuticaSustitucion;
	}

	public void setFormaFarmaceuticaSustitucion(String formaFarmaceuticaSustitucion) {
		this.formaFarmaceuticaSustitucion = formaFarmaceuticaSustitucion;
	}

	public int getOidGestSustituciones() {
		return oidGestSustituciones;
	}

	public void setOidGestSustituciones(int oidGestSustituciones) {
		this.oidGestSustituciones = oidGestSustituciones;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getIdDivisionResidencia() {
		return idDivisionResidencia;
	}

	public void setIdDivisionResidencia(String idDivisionResidencia) {
		this.idDivisionResidencia = idDivisionResidencia;
	}

	public int getOidDivisionResidencia() {
		return oidDivisionResidencia;
	}

	public void setOidDivisionResidencia(int oidDivisionResidencia) {
		this.oidDivisionResidencia = oidDivisionResidencia;
	}

	public String getCnResi() {
		return cnResi;
	}


	public void setCnResi(String cnResi) {
		this.cnResi = cnResi;
	}


	public String getMedicamentoResi() {
		return medicamentoResi;
	}


	public void setMedicamentoResi(String medicamentoResi) {
		this.medicamentoResi = medicamentoResi;
	}


	public String getCnOk() {
		return cnOk;
	}


	public void setCnOk(String cn7_ok) {
		this.cnOk = cn7_ok;
	}


	public String getNombreCorto() {
		return nombreCorto;
	}


	public void setNombreCorto(String nombreCorto) {
		this.nombreCorto = nombreCorto;
	}



	public String getAccion() {
		return accion;
	}


	public void setAccion(String accion) {
		this.accion = accion;
	}


	public String getComentario() {
		return comentario;
	}


	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
		  
	  public int getOidUsuario() {
		return oidUsuario;
	}


	public void setOidUsuario(int oidUsuario) {
		this.oidUsuario = oidUsuario;
	}


	public String getComentarioInterno() {
		return comentarioInterno;
	}


	public void setComentarioInterno(String comentarioInterno) {
		this.comentarioInterno = comentarioInterno;
	}


	public BdConsejo getBdConsejoBiblia() {
		return bdConsejoBiblia;
	}


	public void setBdConsejoBiblia(BdConsejo bdConsejoBiblia) {
		this.bdConsejoBiblia = bdConsejoBiblia;
	}
	  public BdConsejo getBdConsejoResi() {
		return bdConsejoResi;
	}


	public void setBdConsejoResi(BdConsejo bdConsejoResi) {
		this.bdConsejoResi = bdConsejoResi;
	}
	  public String getExisteBdConsejoResi() {
		return existeBdConsejoResi;
	}


	public void setExisteBdConsejoResi(String existeBdConsejoResi) {
		this.existeBdConsejoResi = existeBdConsejoResi;
	}


}
