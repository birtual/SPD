package lopicost.spd.struts.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lopicost.spd.model.BdConsejo;
import lopicost.spd.model.FicheroResiCabecera;
import lopicost.spd.utils.HelperSPD;
import lopicost.spd.utils.SPDConstants;
import lopicost.spd.utils.StringUtil;

public class FicheroResiBean implements Serializable, Cloneable  {
	
	
	  //Datos de cabecera

	  private Date fechaValidacionDatos;
	  private Date fechaCreacionFicheroXML;
	  private String idProcessIospd;
	  private String idResidenciaCarga;
	  private String nombreProduccionRobot;
	  private FicheroResiCabecera ficheroCabecera;
	  
	  private String usuarioValidacion="";
	  private int filasTotales=0;
	  private String nombreFicheroResi="";
	  private String nombreFicheroXML="";
	  private int cipsTotalesProceso=0;
	  private int cipsActivosSPD=0;
	  private int cipsFicheroXML=0;
	  private int cipsFicheroOrigen=0;
	  private String resumenCIPS="";

	  private int cipsNoExistentesBbdd=0;
	  private int cipsSpdResiNoExistentesEnProceso=0;
	  private int numeroMensajesInfo=0;
	  private int numeroMensajesAlerta=0;
	  private int numeroValidacionesPendientes=0;
	  
	  private int oidFicheroResiCabecera;
	  private int oidDivisionResidencia;
	  
	  //Datos de detalle
	  private int oidFicheroResiDetalle;
	  private int oidGestSustituciones;
	  private int oidGestSustitucionesXResi;


	  private String idTratamientoCIP=""; //id que se utiliza para detectar cambios = resiCIP + resiCn + resiMedicamento + resiInicioTratamiento + resiFinTratamiento + resiObservacionesVariante + resiComentarios + resiSiPrecisa + resiPeriodo
	  									//+ resiD1 + resiD2 + resiD3 + resiD4 + resiD5 + resiD6 + resiD7 + resiToma1...resiToma24
	  private String idTratamientoSPD=""; //id utilizado para saber lo que se ha enviado a robot y poder comparar con el anterior
	  private String idProceso="";
	  private String idEstado="PENDIENTE";
	  private String idDivisionResidencia="";
	  private String nombreDivisionResidencia="";
	  private Date fechaHoraProceso;
	  private String fechaCalculoPrevision="";

	  private String oidPaciente="";
	  private String resiCIP="";
	  private String resiCIPMask="";
	  private String resiNombrePaciente="";
	  private String resiNombrePacienteMask="";
	  private String resiNombrePacienteEnFichero;					//nombre del residente que llega en el fichero de la residencia
	  private String resiNombrePacienteEnFicheroMask;
	  private String resiApellidosNombre="";
	  private String resiApellidosNombreMask="";
	  private String resiApellido1="";
	  private String resiApellido1Mask="";
	  private String resiApellido2="";
	  private String resiApellido2Mask="";
	  private String resiApellidos="";
	  private String resiApellidosMask="";
	  private String[] partesNombre;
	  private String[] partesNombreMask;
	  private String resiCodigoPaciente="";
	  private String resiNumIdentidadPaciente="";
	  private String resiNumSegSocialPaciente="";
	  private String resiCn="";
	  private String resiMedicamento="";
	  private String resiFormaMedicacion="";
	  private String resiInicioTratamiento="";
	  private String resiFinTratamiento="";
	  private String resiInicioTratamientoParaSPD="";	//en trat periódicos o especiales se  ha de modificar para enviar a robot dias concretos
	  private String resiFinTratamientoParaSPD="";
	  private String resiObservaciones="";
	  private String resiComentarios="";
	  private String resiSiPrecisa="";
	  private String resiPeriodo="";
	  private int resiFrecuencia=0; //por defecto no marcamos frecuencia
	  private String resiViaAdministracion;
	  private String resiTipoMedicacion="";
	  private String resiVariante="";
	  private String resiPlanta="";
	  private String resiHabitacion="";
	  private String resiIdResidente="";
	    
	  private String spdCIP="";

	  private String spdCnFinal="";
	  private String spdNombreMedicamento="";
	  private String spdNombreBolsa="";
	  private String spdFormaMedicacion="";
	  private String spdAccionBolsa="";
	  private String spdComentarioLopicost="";
	  private String spdCodiLab="";
	  private String spdNombreLab="";
	  private String spdNota="";
	  
	  private String spdCodGtVm="";
	  private String spdCodGtVmp="";	  
	  private String spdCodGtVmpp="";
	  
	  private String spdNomGtVm="";
	  private String spdNomGtVmp="";	  
	  private String spdNomGtVmpp="";
	  
	  private String spdEmblistable="";
	  
	  private float comprimidosDia;
	  private float comprimidosSemana;
	  private float comprimidosDosSemanas;
	  private float comprimidosTresSemanas;
	  private float comprimidosCuatroSemanas;
	  private float previsionResi=0;
	  private float previsionSPD=0;
	  private boolean cuadraPrevision=true;
	  private int diasConToma;
	  private int numeroDeTomasBase;
	  private int numeroDeTomas;
	  private String diasSemanaConcretos="";	  
	  private String diasMesConcretos="";	  
	  
  
	  private String resiD1="";	  
	  private String resiD2="";	  
	  private String resiD3="";	  
	  private String resiD4="";	  
	  private String resiD5="";	  
	  private String resiD6="";	  
	  private String resiD7="";	  
	  private String resiDiasAutomaticos="-";
	  private String sustituible="1";
	  private String mensajesInfo="";
	  private String mensajesResidencia="";
	  private String mensajesAlerta="";
	  private int diasSemanaMarcados;
	  private String secuenciaGuide="";
	  private String tipoRegistro="";

	  private List resiTomas;
	  private List<String> resiTomasOrdenadas;
	  private List<String> nombreTomasOrdenadas;
	  
	  private String resiToma1="";	  
	  private String resiToma2="";	  
	  private String resiToma3="";	  
	  private String resiToma4="";	  
	  private String resiToma5="";	  
	  private String resiToma6="";	  
	  private String resiToma7="";	  
	  private String resiToma8="";	  
	  private String resiToma9="";	  
	  private String resiToma10="";	  
	  private String resiToma11="";	  
	  private String resiToma12="";	  
	  private String resiToma13="";	  
	  private String resiToma14="";	  
	  private String resiToma15="";	  
	  private String resiToma16="";	  
	  private String resiToma17="";	  
	  private String resiToma18="";	  
	  private String resiToma19="";	  
	  private String resiToma20="";	  
	  private String resiToma21="";	  
	  private String resiToma22="";
	  private String resiToma23="";	
	  private String resiToma24="";

	  private String codGtVmpp="";
	  private String nomGtVmpp="";

	  private String incidencia="";
	  private String esExcepcion="";
	  private String validar="";
	  private String confirmar="";
	  private int confirmaciones=0;
	  
	  private boolean persistir=true;
	  private boolean biblia=true;
	  private boolean editable=true;
	  private boolean procesoValido=true;
	  private String margenTerapeuticoEstrecho="NO";
	  private String editado="NO";
	  
	  private int numErrores;
	  private String errores;
	  
		private String comentarioInterno="";
		private String resultLog="";
		private int row;
		private String detalleRow="";
		private String detalleRowKey="";
		private String detalleRowKeyLite="";
		private String detalleRowKeyLiteFechas="";
		private BdConsejo bdConsejo;
		private GestSustitucionesBean gestSustitucionesBean;
		private SustXComposicionBean gestSustXConjHomogBean;
		private String fechaDesde="";
		private String fechaHasta="";
		private int fechaInicioInt;
		private int fechaFinInt;
		
		private String nuevaFechaDesde="";
		private String nuevaFechaHasta="";
		private String nuevaTomaDesde="";
		private String nuevaTomaHasta="";
        
		/**
		 * 0 --> Otros (no_pintar, Si_precisa, pauta 0,1)
		 * 1 --> Diario
		 * 2 --> Semanal
		 * 3 --> Frecuencia
		 * 4 --> Envío guide
		 *  
		 */
		
		private String tipoEnvioHelium ="0"; 
		
		private String asteriscos="NO"; 
		private String free1=""; 
		private String free2=""; 
		private String free3=""; 
		private String usuarioCreacion=""; 
		private String controlNumComprimidos="No detectado";
		private String controlPrincipioActivo="No detectado";
		private String controlRegistroAnterior="No detectado";
		private String controlRegistroRobot="No detectado";
		private String controlDiferentesGtvmp="No detectado";
		private String controlValidacionDatos="";
		private String controlNoSustituible="No detectado";
		private String controlUnicoGtvm="No detectado";
		
		private int contador;
		private int porcentajeCIPS;
		  
		private String usuarioEntregaSPD;
		private String fechaEntregaSPD;
		private String usuarioRecogidaSPD;
		private String fechaRecogidaSPD;
		private String usuarioDesemblistaSPD;
		private String fechaDesemblistaSPD;
		private String usuarioProduccionSPD;
		private String fechaProduccionSPD;
		private int numeroCreacionFicheroXML;
		
		public FicheroResiBean clone() {
		        try {
		            return (FicheroResiBean) super.clone();
		        } catch (CloneNotSupportedException e) {
		            // Manejo de excepciones en caso de que no se pueda clonar
		            return null;
		        }
		    }
		  
		  //importante para limpiar el texto, sin espacios, ni acentos, ni caracteres extraños y en UPPER
			public String getDetalleRow() 				{
				if(detalleRow!=null && detalleRow.length()>900)
					detalleRow=detalleRow.substring(0, 900);
				return detalleRow;		
				}	
			public void setDetalleRow(String detalleRow) 					{		
				//this.detalleRow = StringUtil.quitaEspaciosYAcentos(detalleRow, true);					
				this.detalleRow = detalleRow;
			}
			public String getDetalleRowKey() 			{		
				return detalleRowKey;		
				}	
			public void setDetalleRowKey(String detalleRowKey) 				{		
				this.detalleRowKey = StringUtil.quitaEspaciosYAcentos(detalleRowKey, true);	 			
			}
			
			public String getDetalleRowKeyLite() {
				return detalleRowKeyLite;
			}

			/**
			 * Añadimos también el valor al campo detalleRowKeyLiteFechas
			 * @param detalleRowKeyLite
			 */
			public void setDetalleRowKeyLite(String detalleRowKeyLite) {
				this.detalleRowKeyLite = StringUtil.quitaEspaciosYAcentos(detalleRowKeyLite, true);
				setDetalleRowKeyLiteFechas(this.detalleRowKeyLite);

			}
			  
		  public String getDetalleRowKeyLiteFechas() {
				return detalleRowKeyLiteFechas;
			}

			public void setDetalleRowKeyLiteFechas(String detalleRowKeyLite) {
				String[] detalles = detalleRowKeyLite.split("\\|"); //hay que escaparlo
		        StringBuilder sb = new StringBuilder();
		        for (int i = 0; i < detalles.length; i++) {
		                sb.append(HelperSPD.getDetalleRowLiteFechasOk(detalles[i]));
		                if (i < detalles.length - 1) {
		                    sb.append("|");
		            }
		        }
				
				this.detalleRowKeyLiteFechas = sb.toString();
			}

		public FicheroResiBean() {
		        this.usuarioValidacion = "";
		        this.filasTotales = 0;
		        this.cipsTotalesProceso = 0;
		        this.cipsFicheroXML = 0;
		        this.cipsFicheroOrigen = 0;
		        this.cipsNoExistentesBbdd = 0;
		        this.cipsSpdResiNoExistentesEnProceso = 0;
		        this.numeroMensajesInfo = 0;
		        this.numeroMensajesAlerta = 0;
		        this.idEstado = SPDConstants.SPD_DETALLE_ORIGINAL;  //"PENDIENTE" (antes puesto pendiente)
		        this.resiCodigoPaciente = "";
		        this.resiNumIdentidadPaciente = "";
		        this.resiNumSegSocialPaciente = "";
		        this.resiFormaMedicacion = "";
		        this.resiObservaciones = "";
		        this.resiComentarios = "";
		        this.resiSiPrecisa = "";
		        this.resiPeriodo = "";
		        this.resiViaAdministracion = "";
		        this.resiVariante = "";
		        this.resiPlanta = "";
		        this.resiHabitacion = "";
		        this.spdComentarioLopicost = "";
		        this.spdCodiLab = "";
		        this.spdNombreLab = "";
		        this.spdNota = "";
		        this.spdCodGtVm = "";
		        this.spdCodGtVmp = "";
		        this.spdCodGtVmpp = "";
		        this.spdNomGtVm = "";
		        this.spdNomGtVmp = "";
		        this.spdNomGtVmpp = "";
		        this.spdEmblistable = "";
		        this.resiD1 = "";
		        this.resiD2 = "";
		        this.resiD3 = "";
		        this.resiD4 = "";
		        this.resiD5 = "";
		        this.resiD6 = "";
		        this.resiD7 = "";
		        this.resiDiasAutomaticos = "-";
		        this.sustituible = "1";
		        this.mensajesInfo = "";
		        this.mensajesAlerta = "";
		        this.mensajesResidencia = "";
		        this.resiToma1 = "";
		        this.resiToma2 = "";
		        this.resiToma3 = "";
		        this.resiToma4 = "";
		        this.resiToma5 = "";
		        this.resiToma6 = "";
		        this.resiToma7 = "";
		        this.resiToma8 = "";
		        this.resiToma9 = "";
		        this.resiToma10 = "";
		        this.resiToma11 = "";
		        this.resiToma12 = "";
		        this.resiToma13 = "";
		        this.resiToma14 = "";
		        this.resiToma15 = "";
		        this.resiToma16 = "";
		        this.resiToma17 = "";
		        this.resiToma18 = "";
		        this.resiToma19 = "";
		        this.resiToma20 = "";
		        this.resiToma21 = "";
		        this.resiToma22 = "";
		        this.resiToma23 = "";
		        this.resiToma24 = "";
		        this.codGtVmpp = "";
		        this.nomGtVmpp = "";
		        this.incidencia = "-";
		        this.esExcepcion = "-";
		        this.persistir = true;
		        this.biblia = true;
		        this.editable = true;
		        this.resultLog = "";
		         this.nuevaFechaDesde="";
		         this.nuevaFechaHasta="";
		         this.nuevaTomaDesde="";
		         this.nuevaTomaHasta="";

		    }
		  
		  
	

	public String getIdTratamientoCIP() {			return idTratamientoCIP;		}	public void setIdTratamientoCIP(String idTratamientoCIP) {		this.idTratamientoCIP = idTratamientoCIP;	}
	public String getMensajesInfo() {		return mensajesInfo;	}	public void setMensajesInfo(String mensajesInfo) {		this.mensajesInfo = mensajesInfo;	}
	public String getMensajesAlerta() {		return mensajesAlerta;	}	public void setMensajesAlerta(String mensajesAlerta) {		this.mensajesAlerta = mensajesAlerta;	}
	public String getMensajesResidencia()		 {		return mensajesResidencia;	}	public void setMensajesResidencia(String mens) {	this.mensajesResidencia = mens;	}
	public int getOidDivisionResidencia() {		return oidDivisionResidencia;	}	public void setOidDivisionResidencia(int oidDivisionResidencia) {		this.oidDivisionResidencia = oidDivisionResidencia;}	
	public String getResiDiasAutomaticos() {		return resiDiasAutomaticos;	}	public void setResiDiasAutomaticos(String resiDiasAutomaticos) {		this.resiDiasAutomaticos = resiDiasAutomaticos;	}		
	public String getSustituible() {		return sustituible;	}	public void setSustituible(String sustituible) {		this.sustituible = sustituible;	}
	public int getOidGestSustituciones() {		return oidGestSustituciones;	}	public void setOidGestSustituciones(int oidGestSustituciones) {		this.oidGestSustituciones = oidGestSustituciones;	}
	public int getOidGestSustitucionesXResi() {		return oidGestSustitucionesXResi;	}	public void setOidGestSustitucionesXResi(int oidGestSustitucionesXResi) {		this.oidGestSustitucionesXResi = oidGestSustitucionesXResi;	}
	public String getSpdNombreMedicamento() {		return spdNombreMedicamento;	}	public void setSpdNombreMedicamento(String spdNombreMedicamento) {		this.spdNombreMedicamento = spdNombreMedicamento;	}
	public String getSpdCodiLab() {		return spdCodiLab;	}	public void setSpdCodiLab(String spdCodiLab) {		this.spdCodiLab = spdCodiLab;	}
	public String getSpdNombreLab() {		return spdNombreLab;	}	public void setSpdNombreLab(String spdNombreLab) {		this.spdNombreLab = spdNombreLab;	}
	public String getSpdNota() {		return spdNota;	}	public void setSpdNota(String spdNota) {		this.spdNota = spdNota;	}
	public String getCodGtVmpp() {		return codGtVmpp;	}	public void setCodGtVmpp(String codiConjHomog) {		this.codGtVmpp = codiConjHomog;	}
	public String getNomGtVmpp() {		return nomGtVmpp;	}	public void setNomGtVmpp(String nomConjHomog) {		this.nomGtVmpp = nomConjHomog;	}
	public String getComentarioInterno() {		return comentarioInterno;	}	public void setComentarioInterno(String comentarioInterno) {		this.comentarioInterno = comentarioInterno;	}
	public boolean isBiblia() {	return biblia;	}	public void setBiblia(boolean biblia) {		this.biblia = biblia;	}
	public BdConsejo getBdConsejo() {		return bdConsejo;	}	public void setBdConsejo(BdConsejo bdConsejo) {		this.bdConsejo = bdConsejo;	}
	public GestSustitucionesBean getGestSustitucionesBean() {		return gestSustitucionesBean;	}	public void setGestSustitucionesBean(GestSustitucionesBean gestSustitucionesBean) {		this.gestSustitucionesBean = gestSustitucionesBean;	}
	public SustXComposicionBean getGestSustXConjHomogBean() {		return gestSustXConjHomogBean;	}	public void setGestSustXConjHomogBean(SustXComposicionBean gestSustXConjHomogBean) {		this.gestSustXConjHomogBean = gestSustXConjHomogBean;	}
	public int getRow() {		return row;	}	public void setRow(int row) {		this.row = row;	}
	public int getOidFicheroResiDetalle() {		return oidFicheroResiDetalle;	}	public void setOidFicheroResiDetalle(int oidGestFicheroResiBolsa) {		this.oidFicheroResiDetalle = oidGestFicheroResiBolsa;	}
	public String getIdProceso() {		return idProceso;	}
	public void setIdProceso(String idProceso) {		this.idProceso = idProceso;	}
	public String getIdDivisionResidencia() {		return idDivisionResidencia;	}
	public void setIdDivisionResidencia(String idDivisionResidencia) {		this.idDivisionResidencia = idDivisionResidencia;}
	public Date getFechaHoraProceso() {		return fechaHoraProceso;	}
	public void setFechaHoraProceso(Date fechaHoraProceso) {		this.fechaHoraProceso = fechaHoraProceso;	}
	
	public String getResiCn() {		return resiCn;	}
	public void setResiCn(String resiCn) {		this.resiCn = resiCn;	}
	public String getResiMedicamento() {		return resiMedicamento;	}
	public void setResiMedicamento(String resiMedicamento) {		this.resiMedicamento = resiMedicamento;	}
	public String getResiFormaMedicacion() {		return resiFormaMedicacion;	}
	public void setResiInicioTratamiento(String resiInicioTratamiento) {		this.resiInicioTratamiento = resiInicioTratamiento;	}
	public String getResiInicioTratamiento() {		return resiInicioTratamiento;	}
	public String getResiFinTratamiento() {		return resiFinTratamiento;	}
	public void setResiFinTratamiento(String resiFinTratamiento) {		this.resiFinTratamiento = resiFinTratamiento;	}
	public String getResiObservaciones() {		return resiObservaciones;	}
	public void setResiObservaciones(String resiObservacionesVariante) {		this.resiObservaciones = resiObservacionesVariante;	}
	public String getResiComentarios() {		return resiComentarios;	}
	public void setResiComentarios(String resiComentarios) {		this.resiComentarios = resiComentarios;	}
	public String getResiSiPrecisa() {					return resiSiPrecisa;	}
	public void setResiSiPrecisa(String resiSiPrecisa) {		this.resiSiPrecisa = resiSiPrecisa;	}
	public String getResiViaAdministracion() {			return resiViaAdministracion;	}
	public void setResiViaAdministracion(String resiViaAdministracion) {		this.resiViaAdministracion = resiViaAdministracion;	}
	public String getSpdCnFinal() {						return spdCnFinal;	}
	public void setSpdCnFinal(String spdCnFinal) {		this.spdCnFinal = spdCnFinal;	}
	public String getSpdNombreBolsa() {					return spdNombreBolsa;	}
	public void setSpdNombreBolsa(String spdNombreBolsa) {		this.spdNombreBolsa = spdNombreBolsa;	}
	public String getSpdFormaMedicacion() {				return spdFormaMedicacion;	}
	public void setSpdFormaMedicacion(String spdFormaMedicacion) {		this.spdFormaMedicacion = spdFormaMedicacion;	}
	public String getSpdAccionBolsa() {					return spdAccionBolsa;	}
	public void setSpdAccionBolsa(String spdAccionBolsa) {		this.spdAccionBolsa = spdAccionBolsa;	}
	public String getSpdComentarioLopicost() {			return spdComentarioLopicost;	}
	public void setSpdComentarioLopicost(String spdComentarioLopicost) {		this.spdComentarioLopicost = spdComentarioLopicost;	}
	public String getResiD1() {							return resiD1;							}
	public void setResiD1(String resiD1) 		{				this.resiD1 = resiD1;			}
	public String getResiD2() 					{				return resiD2;					}
	public void setResiD2(String resiD2)		{				this.resiD2 = resiD2;			}
	public String getResiD3() 					{				return resiD3;					}
	public void setResiD3(String resiD3) 		{				this.resiD3 = resiD3;			}
	public String getResiD4() 					{				return resiD4;					}
	public void setResiD4(String resiD4) 		{				this.resiD4 = resiD4;			}
	public String getResiD5() 					{				return resiD5;					}
	public void setResiD5(String resiD5)		{				this.resiD5 = resiD5;			}
	public String getResiD6() 					{				return resiD6;					}
	public void setResiD6(String resiD6)		{				this.resiD6 = resiD6;			}
	public String getResiD7() 					{				return resiD7;					}
	public void setResiD7(String resiD7)		{				this.resiD7 = resiD7;			}
	public boolean getPersistir() 				{				return persistir;				}
	public void setPersistir(boolean persistir) {				this.persistir = persistir;		} 
	public String getIncidencia() 					{			return incidencia;					}
	public void setIncidencia(String incidencia) 		{		this.incidencia = incidencia;			}
	public String getEsExcepcion() 					{			return esExcepcion;					}
	public void setEsExcepcion(String esExcepcion) 		{		this.esExcepcion = esExcepcion;			}
	
	
	
/*	
	public int getValido() 
	{	
		int result=1;
		if(!isValido()) result=0;
		return result;	
	}

*/


	public String getNombreProduccionRobot() {
		return nombreProduccionRobot;
	}

	public void setNombreProduccionRobot(String nombreProduccionRobot) {
		this.nombreProduccionRobot = nombreProduccionRobot;
	}

	public String getResultLog() {		return resultLog;	}	public void setResultLog(String resultLog) {		this.resultLog = resultLog;	}
	public void setResiFormaMedicacion(String resiFormaMedicacion) {		this.resiFormaMedicacion = resiFormaMedicacion;	}  
	public String getNombreDivisionResidencia() {		return nombreDivisionResidencia;	}	public void setNombreDivisionResidencia(String nombreDivisionResidencia) {		this.nombreDivisionResidencia = nombreDivisionResidencia;	}
	public String getResiCodigoPaciente() {		return resiCodigoPaciente;	}	public void setResiCodigoPaciente(String resiCodigoPaciente) {		this.resiCodigoPaciente = resiCodigoPaciente;	}
	public String getResiNumIdentidadPaciente() {		return resiNumIdentidadPaciente;	}	public void setResiNumIdentidadPaciente(String resiNumIdentidadPaciente) {		this.resiNumIdentidadPaciente = resiNumIdentidadPaciente;	}
	public String getResiNumSegSocialPaciente() {		return resiNumSegSocialPaciente;	}	public void setResiNumSegSocialPaciente(String resiNumSegSocialPaciente) {		this.resiNumSegSocialPaciente = resiNumSegSocialPaciente;	}
	public String getResiVariante() {		return resiVariante;	}	public void setResiVariante(String resiVariante) {		this.resiVariante = resiVariante;	}
	public String getSpdNomGtVmpp() {		return spdNomGtVmpp;	}	public void setSpdNomGtVmpp(String spdNomConjHomog) {		this.spdNomGtVmpp = spdNomConjHomog;	}
	public String getIdEstado() {		return idEstado;	}	public void setIdEstado(String idEstado) {		this.idEstado = idEstado;	}
	public String getEditado() 		{		return editado;		}	public void setEditado(String editado) 			{		this.editado = editado;			}
	public boolean isEditable() 	{		return editable;	}	public void setEditable(boolean editable) 		{		this.editable = editable;		}
	public String getResiPeriodo() 	{		return resiPeriodo;	}	public void setResiPeriodo(String resiPeriodo) 	{		this.resiPeriodo = resiPeriodo;	}
	public String getSpdCIP() 		{		return spdCIP;		}	public void setSpdCIP(String spdCIP) 			{		this.spdCIP = spdCIP;			}
	public int getOidFicheroResiCabecera() {		return oidFicheroResiCabecera;	}	public void setOidFicheroResiCabecera(int oidFicheroResiCabecera) {		this.oidFicheroResiCabecera = oidFicheroResiCabecera;	}	
	public Date getFechaValidacionDatos() {		return fechaValidacionDatos;	}	public void setFechaValidacionDatos(Date fechaValidacionDatos) {		this.fechaValidacionDatos = fechaValidacionDatos;	}
	public Date getFechaCreacionFicheroXML() {		return fechaCreacionFicheroXML;	}	public void setFechaCreacionFicheroXML(Date fechaCreaciónFicheroXML) {		this.fechaCreacionFicheroXML = fechaCreaciónFicheroXML;	}
	public String getUsuarioValidacion() {		return usuarioValidacion;	}	public void setUsuarioValidacion(String usuarioValidacion) {		this.usuarioValidacion = usuarioValidacion;	}
	public int getFilasTotales() {		return filasTotales;	}	public void setFilasTotales(int filasTotales) {		this.filasTotales = filasTotales;	}
	public String getNombreFicheroResi() {		return nombreFicheroResi;	}	public void setNombreFicheroResi(String nombreFicheroResi) {		this.nombreFicheroResi = nombreFicheroResi;	}
	public String getNombreFicheroXML() {		return nombreFicheroXML;	}	public void setNombreFicheroXML(String nombreFicheroXML) {		this.nombreFicheroXML = nombreFicheroXML;	}
	public int getCipsTotalesProceso() 			{		return cipsTotalesProceso;	}	public void setCipsTotalesProceso(int cipsFicheroOrigen) 		{		this.cipsTotalesProceso = cipsFicheroOrigen;		}
	public int getCipsFicheroXML() 				{		return cipsFicheroXML;		}	public void setCipsFicheroXML(int cipsFicheroXML) 				{		this.cipsFicheroXML = cipsFicheroXML;				}
	public int getCipsNoExistentesBbdd() 		{		return cipsNoExistentesBbdd;}	public void setCipsNoExistentesBbdd(int cipsNoExistentesBbdd) 	{		this.cipsNoExistentesBbdd = cipsNoExistentesBbdd;	}
	public int getCipsSpdResiNoExistentesEnProceso(){	return cipsSpdResiNoExistentesEnProceso;}public void setCipsSpdResiNoExistentesEnProceso(int cips) {	this.cipsSpdResiNoExistentesEnProceso = cips;		}
	public int getNumeroMensajesInfo() 			{		return numeroMensajesInfo;	}	public void setNumeroMensajesInfo(int numeroMensajesInfo) 		{		this.numeroMensajesInfo = numeroMensajesInfo;		}
	public int getNumeroMensajesAlerta() 		{		return numeroMensajesAlerta;}	public void setNumeroMensajesAlerta(int numeroMensajesAlerta) 	{		this.numeroMensajesAlerta = numeroMensajesAlerta;	}
	public int getCipsFicheroOrigen() 			{		return cipsFicheroOrigen;	}	public void setCipsFicheroOrigen(int cipsFicheroOrigen) 		{		this.cipsFicheroOrigen = cipsFicheroOrigen;			}
	public String getResumenCIPS() 				{		return resumenCIPS;			}	public void setResumenCIPS(String resumenCIPS) 					{		this.resumenCIPS = resumenCIPS;						}
	public String getFechaDesde() 				{		return fechaDesde;			}	public void setFechaDesde(String fechaDesde) 					{		this.fechaDesde = fechaDesde;						}
	public String getFechaHasta() 				{		return fechaHasta;			}	public void setFechaHasta(String fechaHasta) 					{		this.fechaHasta = fechaHasta;						}
	public String getResiToma1() 				{		return resiToma1;			}	public void setResiToma1(String resiToma1) 						{		this.resiToma1 = resiToma1;							}
	public String getResiToma2() 				{		return resiToma2;			}	public void setResiToma2(String resiToma2) 						{		this.resiToma2 = resiToma2;							}
	public String getResiToma3() 				{		return resiToma3;			}	public void setResiToma3(String resiToma3) 						{		this.resiToma3 = resiToma3;							}
	public String getResiToma4() 				{		return resiToma4;			}	public void setResiToma4(String resiToma4) 						{		this.resiToma4 = resiToma4;							}
	public String getResiToma5() 				{		return resiToma5;			}	public void setResiToma5(String resiToma5) 						{		this.resiToma5 = resiToma5;							}
	public String getResiToma6() 				{		return resiToma6;			}	public void setResiToma6(String resiToma6) 						{		this.resiToma6 = resiToma6;							}
	public String getResiToma7() 				{		return resiToma7;			}	public void setResiToma7(String resiToma7) 						{		this.resiToma7 = resiToma7;							}
	public String getResiToma8() 				{		return resiToma8;			}	public void setResiToma8(String resiToma8) 						{		this.resiToma8 = resiToma8;							}
	public String getResiToma9() 				{		return resiToma9;			}	public void setResiToma9(String resiToma9) 						{		this.resiToma9 = resiToma9;							}
	public String getResiToma10() 				{		return resiToma10;			}	public void setResiToma10(String resiToma10) 					{		this.resiToma10 = resiToma10;						}
	public String getResiToma11() 				{		return resiToma11;			}	public void setResiToma11(String resiToma11) 					{		this.resiToma11 = resiToma11;						}
	public String getResiToma12() 				{		return resiToma12;			}	public void setResiToma12(String resiToma12) 					{		this.resiToma12 = resiToma12;						}
	public String getResiToma13() 				{		return resiToma13;			}	public void setResiToma13(String resiToma13) 					{		this.resiToma13 = resiToma13;						}
	public String getResiToma14() 				{		return resiToma14;			}	public void setResiToma14(String resiToma14) 					{		this.resiToma14 = resiToma14;						}
	public String getResiToma15() 				{		return resiToma15;			}	public void setResiToma15(String resiToma15) 					{		this.resiToma15 = resiToma15;						}
	public String getResiToma16() 				{		return resiToma16;			}	public void setResiToma16(String resiToma16) 					{		this.resiToma16 = resiToma16;						}
	public String getResiToma17() 				{		return resiToma17;			}	public void setResiToma17(String resiToma17) 					{		this.resiToma17 = resiToma17;						}
	public String getResiToma18() 				{		return resiToma18;			}	public void setResiToma18(String resiToma18) 					{		this.resiToma18 = resiToma18;						}
	public String getResiToma19() 				{		return resiToma19;			}	public void setResiToma19(String resiToma19) 					{		this.resiToma19 = resiToma19;						}
	public String getResiToma20() 				{		return resiToma20;			}	public void setResiToma20(String resiToma20) 					{		this.resiToma20 = resiToma20;						}
	public String getResiToma21() 				{		return resiToma21;			}	public void setResiToma21(String resiToma21) 					{		this.resiToma21 = resiToma21;						}
	public String getResiToma22() 				{		return resiToma22;			}	public void setResiToma22(String resiToma22) 					{		this.resiToma22 = resiToma22;						}
	public String getResiToma23() 				{		return resiToma23;			}	public void setResiToma23(String resiToma23) 					{		this.resiToma23 = resiToma23;						}
	public String getResiToma24() 				{		return resiToma24;			}	public void setResiToma24(String resiToma24) 					{		this.resiToma24 = resiToma24;						}
	public String getSpdCodGtVm() 				{		return spdCodGtVm;			}	public void setSpdCodGtVm(String spdCodGtVm) 					{		this.spdCodGtVm = spdCodGtVm;						}
	public String getSpdCodGtVmp() 				{		return spdCodGtVmp;			}	public void setSpdCodGtVmp(String spdCodGtVmp) 					{		this.spdCodGtVmp = spdCodGtVmp;						}
	public String getSpdCodGtVmpp() 			{		return spdCodGtVmpp;		}	public void setSpdCodGtVmpp(String spdCodGtVmpp)				{		this.spdCodGtVmpp = spdCodGtVmpp;					}
	public String getSpdNomGtVm() 				{		return spdNomGtVm;			}	public void setSpdNomGtVm(String spdNomGtVm) 					{		this.spdNomGtVm = spdNomGtVm;						}
	public String getSpdNomGtVmp() 				{		return spdNomGtVmp;			}	public void setSpdNomGtVmp(String spdNomGtVmp) 					{		this.spdNomGtVmp = spdNomGtVmp;						}
	public String getSpdEmblistable() 			{		return spdEmblistable;		}	public void setSpdEmblistable(String spdEmblistable) 			{		this.spdEmblistable = spdEmblistable;				}
	public float getComprimidosDia() 			{		return comprimidosDia;		}	public void setComprimidosDia(float comprimidosDia) 			{		this.comprimidosDia = comprimidosDia;				}
	public float getComprimidosSemana() 		{		return comprimidosSemana;	}	public void setComprimidosSemana(float compUnaSemana) 			{		this.comprimidosSemana = compUnaSemana;				}
	public float getComprimidosDosSemanas() 	{		return comprimidosDosSemanas;}	public void setComprimidosDosSemanas(float comp2Semanas) 		{		this.comprimidosDosSemanas = comp2Semanas;			}
	public float getComprimidosTresSemanas() 	{		return comprimidosTresSemanas;}	public void setComprimidosTresSemanas(float comp3Semanas)		{		this.comprimidosTresSemanas = comp3Semanas;			}
	public float getComprimidosCuatroSemanas() 	{		return comprimidosCuatroSemanas;}public void setComprimidosCuatroSemanas(float comp4Semanas)	{		this.comprimidosCuatroSemanas = comp4Semanas;		}
    public int getDiasConToma() 				{       return this.diasConToma;    }	public void setDiasConToma(final int diasConToma) 				{		this.diasConToma = diasConToma;					    }
    public String getResiPlanta() 				{		return this.resiPlanta;    	}	public void setResiPlanta(final String resiPlanta) 				{		this.resiPlanta = resiPlanta;					    }
    public String getResiHabitacion() 			{		return this.resiHabitacion;	}   public void setResiHabitacion(final String resiHabitacion) 		{       this.resiHabitacion = resiHabitacion;			    }
	public int getResiFrecuencia() 				{		return resiFrecuencia;		}	public void setResiFrecuencia(int resiFrecuencia) 				{		this.resiFrecuencia = resiFrecuencia;				}
	public int getDiasSemanaMarcados() 			{		return diasSemanaMarcados;	}	public void setDiasSemanaMarcados(int diasSemanaMarcados)		{		this.diasSemanaMarcados = diasSemanaMarcados;		}
	public int getFechaInicioInt() 				{		return fechaInicioInt;		}	public void setFechaInicioInt(int fechaInicioInt)				{		this.fechaInicioInt = fechaInicioInt;				}
	public int getFechaFinInt() 				{		return fechaFinInt;			}	public void setFechaFinInt(int fechaFinInt) 					{		this.fechaFinInt = fechaFinInt;						}
	public int getNumeroDeTomas() 				{		return numeroDeTomas;		}	public void setNumeroDeTomas(int numeroDeTomas) 				{		this.numeroDeTomas = numeroDeTomas;					}
	public int getNumeroDeTomasBase() 			{		return numeroDeTomasBase;	}	public void setNumeroDeTomasBase(int numeroDeTomasBase) 		{		this.numeroDeTomasBase = numeroDeTomasBase;			}
	public String getDiasMesConcretos() 		{		return diasMesConcretos;	}	public void setDiasMesConcretos(String diasMesConcretos) 		{		this.diasMesConcretos = diasMesConcretos;			}
	public String getDiasSemanaConcretos() 		{		return diasSemanaConcretos;	}	public void setDiasSemanaConcretos(String diasSemanaConcretos) 	{		this.diasSemanaConcretos = diasSemanaConcretos;		}
	public String getValidar() 					{		return validar;				}	public void setValidar(String validar) 							{		this.validar = validar;								}
	public String getConfirmar() 					{		return confirmar;				}	public void setConfirmar(String confirmar) 				{		this.confirmar = confirmar;							}
	public String getTipoEnvioHelium() 			{		return tipoEnvioHelium;		}	public void setTipoEnvioHelium(String tipoEnvioHelium) 			{		this.tipoEnvioHelium = tipoEnvioHelium;				}
	public String getSecuenciaGuide() 			{		return secuenciaGuide;		}	public void setSecuenciaGuide(String secuenciaGuide) 			{		this.secuenciaGuide = secuenciaGuide;				}
	public String getTipoRegistro() 			{		return tipoRegistro;		}	public void setTipoRegistro(String tipoRegistro) 				{		this.tipoRegistro = tipoRegistro;					}
	public String getResiInicioTratamientoParaSPD() {	return resiInicioTratamientoParaSPD;}	public void setResiInicioTratamientoParaSPD(String r) 	{		this.resiInicioTratamientoParaSPD = r;				}	
	public String getResiFinTratamientoParaSPD(){		return resiFinTratamientoParaSPD;}	public void setResiFinTratamientoParaSPD(String resiFin)	{		this.resiFinTratamientoParaSPD = resiFin;			}
	public int getNumErrores() 					{		return numErrores;			}	public void setNumErrores(int numError) 						{		this.numErrores = numError;							}
	public String getErrores() 					{		return errores;				}	public void setErrores(String errores) 							{		this.errores = errores;								}
	public String getResiTipoMedicacion() 		{		return resiTipoMedicacion;	}	public void setResiTipoMedicacion(String t) 					{		this.resiTipoMedicacion = t;						}
	public String getAsteriscos() 				{		return asteriscos;			}	public void setAsteriscos(String ast)							{		this.asteriscos = ast;								}
	public String getFree1() 					{		return free1;				}	public void setFree1(String free1) 								{		this.free1 = free1;									}
	public String getFree2() 					{		return free2;				}	public void setFree2(String free2) 								{		this.free2 = free2;									}
	public String getFree3() 					{		return free3;				}	public void setFree3(String free3) 								{		this.free3 = free3;									}
	public String getUsuarioCreacion() 			{		return usuarioCreacion;		}	public void setUsuarioCreacion(String usuc)						{		this.usuarioCreacion = usuc;						}
	public boolean isProcesoValido() 			{		return procesoValido;		}	public void setProcesoValido(boolean procesoValido) 			{		this.procesoValido = procesoValido;					}
	public int getNumeroValidacionesPendientes(){		return numeroValidacionesPendientes;}	public void setNumeroValidacionesPendientes(int n)		{		this.numeroValidacionesPendientes = n;				}
	public String getFechaCalculoPrevision() 	{		return fechaCalculoPrevision;}	public void setFechaCalculoPrevision(String f)					{		this.fechaCalculoPrevision = f;						}
	public int getCipsActivosSPD() 				{		return cipsActivosSPD;		}	public void setCipsActivosSPD(int cipsActivosSPD) 				{		this.cipsActivosSPD = cipsActivosSPD;				}
	public String getNombrePacienteEnFichero() 	{		return resiNombrePacienteEnFichero;}	public void setNombrePacienteEnFichero(String resiN)	{		this.resiNombrePacienteEnFichero = resiN;			}
	public int getContador() 					{		return contador;			}	public void setContador(int contador) 							{		this.contador = contador;							}
	public String getMargenTerapeuticoEstrecho(){		return margenTerapeuticoEstrecho;}public void setMargenTerapeuticoEstrecho(String margen) 		{		this.margenTerapeuticoEstrecho = margen;			}
	public float getPrevisionResi() 			{		return previsionResi;		}	public void setPrevisionResi(float previsionResi) 				{		this.previsionResi = previsionResi;					}
	public float getPrevisionSPD() 				{		return previsionSPD;		}	public void setPrevisionSPD(float previsionSPD) 				{		this.previsionSPD = previsionSPD;					}
	public boolean isCuadraPrevision() 			{		return cuadraPrevision;		}	public void setCuadraPrevision(boolean cuadraPrevision) 		{		this.cuadraPrevision = cuadraPrevision;				}
	public String getControlNumComprimidos()	{		return controlNumComprimidos;	}	public void setControlNumComprimidos(String alerta) 	{		this.controlNumComprimidos = alerta;				}
	public String getControlPrincipioActivo() 	{		return controlPrincipioActivo;}	public void setControlPrincipioActivo(String a) 				{		this.controlPrincipioActivo = a;					}
	public String getControlRegistroAnterior() 	{		return controlRegistroAnterior;}	public void setControlRegistroAnterior(String a) 			{		this.controlRegistroAnterior = a;					}
	public String getControlRegistroRobot() 	{		return controlRegistroRobot;}	public void setControlRegistroRobot(String a)					{		this.controlRegistroRobot = a;						}
	public String getControlDiferentesGtvmp() 	{		return controlDiferentesGtvmp;}	public void setControlDiferentesGtvmp(String a) 				{		this.controlDiferentesGtvmp = a;					}
	public String getControlValidacionDatos() 	{		return controlValidacionDatos;}	public void setControlValidacionDatos(String a)					{		this.controlValidacionDatos = a;					}
	public String getControlNoSustituible()		{		return controlNoSustituible;}	public void setControlNoSustituible(String alertaNoSustituible) {		this.controlNoSustituible = alertaNoSustituible;	}
	public String getControlUnicoGtvm() 		{		return controlUnicoGtvm;	}	public void setControlUnicoGtvm(String controlVariosGtvm) 		{		this.controlUnicoGtvm = controlVariosGtvm;			}
	public String getIdTratamientoSPD() 		{		return idTratamientoSPD;	}	public void setIdTratamientoSPD(String idTratamientoSPD) 		{		this.idTratamientoSPD = idTratamientoSPD;			}
	public FicheroResiCabecera getFicheroCabecera() {	return ficheroCabecera;		}	public void setFicheroCabecera(FicheroResiCabecera f) 			{		this.ficheroCabecera = f;							}
	public int getPorcentajeCIPS() 				{		return porcentajeCIPS;		}	public void setPorcentajeCIPS(int porcentajeCIPS) 				{		this.porcentajeCIPS = porcentajeCIPS;				}
	public int getConfirmaciones() 				{		return confirmaciones;		}	public void setConfirmaciones(int confirmaciones) 				{		this.confirmaciones = confirmaciones;				}
	public String getResiIdResidente() 			{		return resiIdResidente;		}	public void setResiIdResidente(String resiIdResidente) 			{		this.resiIdResidente = resiIdResidente;				}
	public String getIdProcessIospd() 			{		return idProcessIospd;		}	public void setIdProcessIospd(String idProcessIospd) 			{		this.idProcessIospd = idProcessIospd;				}
	public List getResiTomas() 					{		return resiTomas;			}	public void setResiTomas(List resiTomas) 						{		this.resiTomas = resiTomas;							}
	public List<String> getResiTomasOrdenadas() {		return resiTomasOrdenadas;	}	public void setResiTomasOrdenadas(List<String> resiTomasOrd) 	{		this.resiTomasOrdenadas = resiTomasOrd;				}
	public List<String> getNombreTomasOrdenadas() {		return nombreTomasOrdenadas;}	public void setNombreTomasOrdenadas(List<String> nombre) 		{		this.nombreTomasOrdenadas = nombre;					}

	public String getOidPaciente() {		return oidPaciente;	}	public void setOidPaciente(String oidPaciente) {		this.oidPaciente = oidPaciente;	}	
	public String getNuevaFechaDesde() {		return nuevaFechaDesde;	}	public void setNuevaFechaDesde(String nuevaFechaDesde) {		this.nuevaFechaDesde = nuevaFechaDesde;	}
	public String getNuevaFechaHasta() {		return nuevaFechaHasta;	}	public void setNuevaFechaHasta(String nuevaFechaHasta) {		this.nuevaFechaHasta = nuevaFechaHasta;	}
	public String getNuevaTomaDesde() {		return nuevaTomaDesde;	}	public void setNuevaTomaDesde(String nuevaTomaDesde) {		this.nuevaTomaDesde = nuevaTomaDesde;	}
	public String getNuevaTomaHasta() {		return nuevaTomaHasta;	}	public void setNuevaTomaHasta(String nuevaTomaHasta) {		this.nuevaTomaHasta = nuevaTomaHasta;	}

	//CIP 
	public String getResiCIP() 										{		return resiCIP;					}
	public void setResiCIP(String resiCIP) 							{		
		this.setResiCIPMask(HelperSPD.enmascararCIP(resiCIP));	
		this.resiCIP = resiCIP;			
	}
	public String getResiCIPMask()									{		return resiCIPMask;				}
	public void setResiCIPMask(String resiCIPMask) 					{		this.resiCIPMask = resiCIPMask;	}
	
	//Resi Nombre residente 
	public String getResiNombrePaciente() 							{		return resiNombrePaciente;		}
	public void setResiNombrePaciente(String dato)					{	
		this.setResiNombrePacienteMask(HelperSPD.enmascararNombre(dato));	
		this.resiNombrePaciente = dato;	
	}
	public String getResiNombrePacienteMask() 						{		return this.resiNombrePacienteMask;			}
	public void setResiNombrePacienteMask(String dato) 				{		this.resiNombrePacienteMask = dato;			}

	//Resi Nombre residente 
	public String[] getPartesNombre() 								{		return this.partesNombre;					}	
	public void setPartesNombre(String[] partes)					{		this.partesNombre = partes;					}
	public String[] getPartesNombreMask() 							{		return partesNombreMask;					}
	public void setPartesNombreMask(String[] partesNombreMask) 		{		this.partesNombreMask = partesNombreMask;	}
	
	//Resi Nombre residente 
	public String getResiNombrePacienteEnFichero() 					{		return this.resiNombrePacienteEnFichero;	}	
	public void setResiNombrePacienteEnFichero(String resiN)		{		
		this.setResiNombrePacienteEnFicheroMask(HelperSPD.enmascararNombre(resiN));	
		this.resiNombrePacienteEnFichero = resiN;	
		}
	public String getResiNombrePacienteEnFicheroMask() 				{		return this.resiNombrePacienteEnFicheroMask;}
	public void setResiNombrePacienteEnFicheroMask(String dato) 	{		this.resiNombrePacienteEnFicheroMask = dato;}
	
	//Resi Nombre residente 
	public String getResiApellidosNombre() 							{		return this.resiApellidosNombre;			}	
	public void setResiApellidosNombre(String dato)				 	{		
		this.setResiApellidosNombreMask(HelperSPD.enmascararNombre(dato));	
		this.resiApellidosNombre = dato;			
		}
	public String getResiApellidosNombreMask() 						{		return this.resiApellidosNombreMask;		}
	public void setResiApellidosNombreMask(String dato)				{		this.resiApellidosNombreMask = dato;		}
	
	//Resi Nombre residente 
	public String getResiApellidos() 								{		return this.resiApellidos;					}	
	public void setResiApellidos(String dato) 						{		
		this.setResiApellidosMask(HelperSPD.enmascararNombre(dato));	
		this.resiApellidos = dato;			
		}
	public String getResiApellidosMask() 							{		return this.resiApellidosMask;				}
	public void setResiApellidosMask(String resiApellidosMask) 		{		this.resiApellidosMask = resiApellidosMask;	}
	
	//Resi Nombre residente 
	public String getResiApellido1() 								{		return this.resiApellido1;					}	
	public void setResiApellido1(String dato) 						{		
		this.setResiApellido1Mask(HelperSPD.enmascararNombre(dato));	
		this.resiApellido1 = dato;			
		}
	public String getResiApellido1Mask() 							{		return this.resiApellido1Mask;				}
	public void setResiApellido1Mask(String resiApellido1Mask) 		{		this.resiApellido1Mask = resiApellido1Mask;	}
	
	//Resi Nombre residente 
	public String getResiApellido2() 								{		return this.resiApellido2;					}	
	public void setResiApellido2(String dato) 						{		
		this.setResiApellido2Mask(HelperSPD.enmascararNombre(dato));	
		this.resiApellido2 = dato;			
		}
	public String getResiApellido2Mask() 							{		return this.resiApellido2Mask;				}
	public void setResiApellido2Mask(String resiApellido2Mask) 		{		this.resiApellido2Mask = resiApellido2Mask;	}

	public String getUsuarioEntregaSPD() {
		return usuarioEntregaSPD;
	}

	public void setUsuarioEntregaSPD(String usuarioEntregaSPD) {
		this.usuarioEntregaSPD = usuarioEntregaSPD;
	}

	public String getFechaEntregaSPD() {
		return fechaEntregaSPD;
	}

	public void setFechaEntregaSPD(String fechaEntregaSPD) {
		this.fechaEntregaSPD = fechaEntregaSPD;
	}

	public String getUsuarioRecogidaSPD() {
		return usuarioRecogidaSPD;
	}

	public void setUsuarioRecogidaSPD(String usuarioRecogidaSPD) {
		this.usuarioRecogidaSPD = usuarioRecogidaSPD;
	}

	public String getFechaRecogidaSPD() {
		return fechaRecogidaSPD;
	}

	public void setFechaRecogidaSPD(String fechaRecogidaSPD) {
		this.fechaRecogidaSPD = fechaRecogidaSPD;
	}

	public String getUsuarioDesemblistaSPD() {
		return usuarioDesemblistaSPD;
	}

	public void setUsuarioDesemblistaSPD(String usuarioDesemblistaSPD) {
		this.usuarioDesemblistaSPD = usuarioDesemblistaSPD;
	}

	public String getFechaDesemblistaSPD() {
		return fechaDesemblistaSPD;
	}

	public void setFechaDesemblistaSPD(String fechaDesemblistaSPD) {
		this.fechaDesemblistaSPD = fechaDesemblistaSPD;
	}

	public String getUsuarioProduccionSPD() {
		return usuarioProduccionSPD;
	}

	public void setUsuarioProduccionSPD(String usuarioProduccionSPD) {
		this.usuarioProduccionSPD = usuarioProduccionSPD;
	}

	public String getFechaProduccionSPD() {
		return fechaProduccionSPD;
	}

	public void setFechaProduccionSPD(String fechaProduccionSPD) {
		this.fechaProduccionSPD = fechaProduccionSPD;
	}

	public int getNumeroCreacionFicheroXML() {
		return numeroCreacionFicheroXML;
	}

	public void setNumeroCreacionFicheroXML(int numeroCreacionFicheroXML) {
		this.numeroCreacionFicheroXML = numeroCreacionFicheroXML;
	}

	public String getIdResidenciaCarga() {
		return idResidenciaCarga;
	}

	public void setIdResidenciaCarga(String idResidenciaCarga) {
		this.idResidenciaCarga = idResidenciaCarga;
	}

	














	
	
}
