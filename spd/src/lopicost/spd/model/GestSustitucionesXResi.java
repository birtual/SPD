package lopicost.spd.model;

import java.io.Serializable;
import java.util.Date;

public class GestSustitucionesXResi implements Serializable {

	private Date fechaSustXResi;
	private int oidUsuario=0;
	
	private int oidGestSustituciones;
	private int oidGestSustitucionesXResi;

	private int oidDivisionResidencia;
	private String idDivisionResidencia;
	private String nombreDivisionResidencia;


	private String cnOkSustXResi;
	private String nombreCortoSustXResi;
	private String accionSustXResi;
	private String comentarioSustXResi;
	private String comentarioInterno="";	  
	
	private String codGtVmppSustXResi;		//codGtVmpp          varchar(30),
	private String nomGtVmppSustXResi;		//codGtVmpp          varchar(250),

	private String nombreMedicamentoOkSustXResi;				//NOMBRE 				varchar(250) NOT NULL,
	private String codGtVmppOkSustXResi;		//codGtVmpp          varchar(30),
	private String nomGtVmppOkSustXResi;		//codGtVmpp          varchar(250),
 
	private BdConsejo bdConsejoSustXResi;
	private BdConsejo bdConsejoSustXResiBiblia;
	private String existeBdConsejoSustXResi="";
	private String existeBdConsejoSustXResiBiblia="";
	

	private boolean gtVmppDiferente=false;
	
	private String sustituibleXResi="1";
	  

	private String mensajesInfo="";
	private String mensajesAlerta="";
	public Date getFechaSustXResi() {
		return fechaSustXResi;
	}
	public void setFechaSustXResi(Date fechaSustXResi) {
		this.fechaSustXResi = fechaSustXResi;
	}
	public int getOidUsuario() {
		return oidUsuario;
	}
	public void setOidUsuario(int oidUsuario) {
		this.oidUsuario = oidUsuario;
	}
	public int getOidGestSustituciones() {
		return oidGestSustituciones;
	}
	public void setOidGestSustituciones(int oidGestSustituciones) {
		this.oidGestSustituciones = oidGestSustituciones;
	}
	public int getOidGestSustitucionesXResi() {
		return oidGestSustitucionesXResi;
	}
	public void setOidGestSustitucionesXResi(int oidGestSustitucionesXResi) {
		this.oidGestSustitucionesXResi = oidGestSustitucionesXResi;
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
	public String getCnOkSustXResi() {
		return cnOkSustXResi;
	}
	public void setCnOkSustXResi(String cnOkSustXResi) {
		this.cnOkSustXResi = cnOkSustXResi;
	}
	public String getNombreCortoSustXResi() {
		return nombreCortoSustXResi;
	}
	public void setNombreCortoSustXResi(String nombreCortoSustXResi) {
		this.nombreCortoSustXResi = nombreCortoSustXResi;
	}
	public String getAccionSustXResi() {
		return accionSustXResi;
	}
	public void setAccionSustXResi(String accionSustXResi) {
		this.accionSustXResi = accionSustXResi;
	}
	public String getComentarioSustXResi() {
		return comentarioSustXResi;
	}
	public void setComentarioSustXResi(String comentarioSustXResi) {
		this.comentarioSustXResi = comentarioSustXResi;
	}
	public String getComentarioInterno() {
		return comentarioInterno;
	}
	public void setComentarioInterno(String comentarioInterno) {
		this.comentarioInterno = comentarioInterno;
	}
	public String getCodGtVmppSustXResi() {
		return codGtVmppSustXResi;
	}
	public void setCodGtVmppSustXResi(String codGtVmppSustXResi) {
		this.codGtVmppSustXResi = codGtVmppSustXResi;
	}
	public String getNomGtVmppSustXResi() {
		return nomGtVmppSustXResi;
	}
	public void setNomGtVmppSustXResi(String nomGtVmppSustXResi) {
		this.nomGtVmppSustXResi = nomGtVmppSustXResi;
	}
	public String getNombreMedicamentoOkSustXResi() {
		return nombreMedicamentoOkSustXResi;
	}
	public void setNombreMedicamentoOkSustXResi(String nombreMedicamentoOkSustXResi) {
		this.nombreMedicamentoOkSustXResi = nombreMedicamentoOkSustXResi;
	}
	public String getCodGtVmppOkSustXResi() {
		return codGtVmppOkSustXResi;
	}
	public void setCodGtVmppOkSustXResi(String codGtVmppOkSustXResi) {
		this.codGtVmppOkSustXResi = codGtVmppOkSustXResi;
	}
	public String getNomGtVmppOkSustXResi() {
		return nomGtVmppOkSustXResi;
	}
	public void setNomGtVmppOkSustXResi(String nomGtVmppOkSustXResi) {
		this.nomGtVmppOkSustXResi = nomGtVmppOkSustXResi;
	}
	public BdConsejo getBdConsejoSustXResi() {
		return bdConsejoSustXResi;
	}
	public void setBdConsejoSustXResi(BdConsejo bdConsejoSustXResi) {
		this.bdConsejoSustXResi = bdConsejoSustXResi;
	}
	public BdConsejo getBdConsejoSustXResiBiblia() {
		return bdConsejoSustXResiBiblia;
	}
	public void setBdConsejoSustXResiBiblia(BdConsejo bdConsejoSustXResiBiblia) {
		this.bdConsejoSustXResiBiblia = bdConsejoSustXResiBiblia;
	}
	public String getExisteBdConsejoSustXResi() {
		return existeBdConsejoSustXResi;
	}
	public void setExisteBdConsejoSustXResi(String existeBdConsejoSustXResi) {
		this.existeBdConsejoSustXResi = existeBdConsejoSustXResi;
	}
	public String getExisteBdConsejoSustXResiBiblia() {
		return existeBdConsejoSustXResiBiblia;
	}
	public void setExisteBdConsejoSustXResiBiblia(String existeBdConsejoSustXResiBiblia) {
		this.existeBdConsejoSustXResiBiblia = existeBdConsejoSustXResiBiblia;
	}
	public boolean isGtVmppDiferente() {
		return gtVmppDiferente;
	}
	public void setGtVmppDiferente(boolean conjHomogDiferente) {
		this.gtVmppDiferente = conjHomogDiferente;
	}
	public String getSustituibleXResi() {
		return sustituibleXResi;
	}
	public void setSustituibleXResi(String sustituibleXResi) {
		this.sustituibleXResi = sustituibleXResi;
	}
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

	//private String codConjHomogOkSustXResi;		//CodConjHomog          varchar(30),
	//private String nomConjHomogOkSustXResi;		//NomConjHomog          varchar(250),
	

	 
}
