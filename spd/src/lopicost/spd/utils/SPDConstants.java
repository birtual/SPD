
package lopicost.spd.utils;
 
import lopicost.config.logger.Logger;
import java.util.ResourceBundle;

public class SPDConstants 
{
	
		public static String LANG_DEFAULT = "es";
		public static int MAX_INSERTS_PER_STATEMENT = 900;
		/**
		 * Constant de conexio IADA
		 */
		public static  String CONNECTION_ID = "spd"; 

	/**
	 * Contexte del servidor web
	 */
	public static String SERVER_CONTEXT="/spd";

	public static String MESSAGE_RESOURCE_PROPERTIES = "SPDMessageResources";
	public static String MESSAGE_LOGS_PROPERTIES = "SPDLog";
	//Id de usuario que se usará para hacer el doDummyLogin()
	//public static String DUMMYLOGIN= "";	

	
	//public static boolean SCOPE_FILTERS_ENABLED = false;
	
	
	//public static String SKIN_CLASS_IMPL= "lopicost.spd.commons.skin.SPDSkinResolver";
	//public static String URLREPORTS= "birt/run?";

	//public static final String ACTION_TO_DO_DEFAULT     		= "DEFAULT"; 		// NO CONFIGURABLE
	
	public static final String SPD_FICHERO_SUBIDO_OK = "";
	public static final String SPD_FICHERO_SUBIDO_CON_ERRORES = "FICHERO SUBIDO CON ERRORES";
	
	public static final String SPD_PROCESO_1_EN_CREACION = "IMPORTANDO_DATOS...";
	public static final String SPD_PROCESO_2_PENDIENTE_VALIDAR = "PENDIENTE_VALIDACION";
	public static final String SPD_PROCESO_3_VALIDADO = "VALIDADO";
	public static final String SPD_PROCESO_3_POR_VALIDACION_MASIVA = "VALIDADO_FORMA_MASIVA";
	public static final String SPD_PROCESO_4_CARGA_ERROR = "CARGA_ERROR";
	//public static final String SPD_PROCESO_4_PENDIENTE_ENVIO_A_PREVISION = "PENDIENTE_ENVIO_A_PREVISION";
	//public static final String SPD_PROCESO_3_CONFIRMADO = "CONFIRMADO";
	//public static final String SPD_PROCESO_GENERANDO_XML = "GENERANDO_XML...";
	//public static final String SPD_PROCESO_XML_GENERADO = "XML_GENERADO";
	//public static final String SPD_PROCESO_DESCARTADO = "DESCARTADO";
	public static final String FORMATO_FECHA_DEFAULT = "dd/MM/yyyy";

	public static final String DIAS_PRODUCCION_PASA_A_HST= "60";
	
	public static final String SPD_DETALLE_ORIGINAL = "ORIGINAL";
	public static final String SPD_DETALLE_PENDIENTE = "PENDIENTE";
	public static final String SPD_DETALLE_BORRADO = "BORRADO";
	public static final String SPD_DETALLE_MODIFICADO = "MODIFICADO";

	public static final String SPD_ACCIONBOLSA_PASTILLERO= "PASTILLERO";
	public static final String SPD_ACCIONBOLSA_SOLO_INFO= "SOLO_INFO";
	public static final String SPD_ACCIONBOLSA_NO_PINTAR= "NO_PINTAR";
	public static final String SPD_ACCIONBOLSA_SI_PRECISA= "SI_PRECISA";
	public static final String LOG_ID= "LOGIN ID";

	public static final String DIAS_DEFECTO_QUINCENA = "1,15";
	public static final String DIAS_DEFECTO_MES = "1";

	public static final String REGISTRO_REUTILIZADO_DE_ANTERIOR_PRODUCCION = "REUTILIZADO";
	public static final String REGISTRO_EDITADO = "EDITADO";
	public static final String REGISTRO_ORIGINAL = "ORIGINAL";
	public static final String REGISTRO_CREADO_AUTOMATICAMENTE = "CREADO_AUTOMATICO";
	public static final String REGISTRO_CREADO_AUTOM_SECUENCIA_GUIDE = "CREADO_AUTO_SECUENCIA_GUIDE";
	public static final String REGISTRO_EDITADO_AUTOMATICAMENTE = "EDITADO_AUTOMATICO";
	public static final String PREFIJO_REGISTRO_SECUENCIA_GUIDE = "SEQ_GUIDE";

	public static final String SPD_PERIODO_DIARIO= "diario";
	public static final String SPD_PERIODO_DIAS_SEMANA_CONCRETOS= "diasSemanaConcretos";
	public static final String SPD_PERIODO_SEMANAL= "semanal";
	public static final String SPD_PERIODO_QUINCENAL= "quincenal";
	public static final String SPD_PERIODO_MENSUAL= "mensual";
	public static final String SPD_PERIODO_SEMESTRAL= "semestral";
	public static final String SPD_PERIODO_ANUAL= "anual";
	public static final String SPD_PERIODO_ESPECIAL= "especial";
	
	public static final String SPD_GUIDE_DIA= "DIA";
	public static final String SPD_GUIDE_DIAS= "DIAS";
	public static final String SPD_GUIDE_DAY= "DAY";
	public static final String SPD_GUIDE_DAYS= "DAYS";

	public static final String SPD_GUIDE_SEMANA= "SEMANA";
	public static final String SPD_GUIDE_SEMANAS= "SEMANAS";
	public static final String SPD_GUIDE_WEEK= "WEEK";
	public static final String SPD_GUIDE_WEEKS= "WEEKS";

	public static final String SPD_GUIDE_MES= "MES";
	public static final String SPD_GUIDE_MESES= "MESES";
	public static final String SPD_GUIDE_MONTH= "MONTH";
	public static final String SPD_GUIDE_MONTHS= "MONTHS";

	public static final String REGISTRO_CABECERA= "CABECERA";
	public static final String REGISTRO_LINEA= "LINEA";
	public static final String REGISTRO_LINEA_SEC_GUIDE= "LINEA_AUTO_GUIDE";
	
	//public static final String NO_RECOGIDA_DATOS_IOFWIN= "NO_RECOGIDA_DATOS_IOFWIN";
	
	//public static final String REGISTRO_LINEA_TRAZODONA= "LINEA_AUTO_TRAZODONA";
	
	public static final String BIRT_URL= "/birt-viewer/frameset?__report=";

	public static String IOSPD_INPATH ="";
	public static String IOSPD_OUTPATH ="";

	/**
	 * Número de registros por página de listado.
	 */
	public static  int PAGE_ROWS= 20;	

	
	public static  int NUM_TOMAS_SPD= 24;	
	public static  int NUM_TOMAS_DEFAULT_RESI= 6;	

	
	/**
	 * Path de logs relativo al contexto 
	 */
	public static String LOGS_DIR= "/WEB-INF/logs";
	
	/**
	 * Constantes que se cargan desde el fichero de propiedades SPDConstants
	 */
	private static  String PROPERTY_BOUNDLE_NAME = "SPDConstants"; 	
	private static ResourceBundle rb = null;	// NO CONFIGURABLE
	private static boolean 					theFirstAccess 			= true;

	//Posibles estados de un dominio
	public static String STATUS_PACIENTE_ALTA= "Alta";
	public static String STATUS_PACIENTE_BAJA= "Baja";
	public static String STATUS_PACIENTE_EXITUS= "Exitus";
	public static String STATUS_PACIENTE_HOSPITAL= "Hospitalizado";
	public static String STATUS_PACIENTE_BAJAVOLUNTARIA= "Baja voluntaria";
	public static String STATUS_PACIENTE_CENTRODEDIA= "Centro de Dia";
	
	/**
	 * Constantes para el upload de ficheros
	 */
	public static String FILEUPLOAD_EXTENSIONS = ".doc,.txt,.zip,.xls,.xlsx,.pdf,.ppt,.pps,.jpg,.gif,.csv,.html,.htm";
	//public static String FILEUPLOAD_REAL_PATH = "c:/projects/spd/desarrollo/webapp";
	public static String PATH_DOCUMENTOS = "C:/eclipse/workspace/spd_files";
	//public static String FILEUPLOAD_REAL_PATH = FileUtil.dameURLBase();// Obtener la ruta de la aplicación web en Tomcat
	 
	public static String FILEUPLOAD_RELATIVE_PATH = "/iospd";
	public static String PLANTILLAS_IO_RELATIVE_PATH = "/plantillas";
	public static String FILEUPLOAD_MAXSIZE = "5000"; //en Kbytes
	public static String SIN_DEFINIR = "SIN_DEFINIR";

	public static String AP_SPD_ADMIN= "---NOCARGADO---";


	// Estos dos se usan para la asignación de alumnos a dominios en campus.
	public static  String USERTYPE_RESPONSABLE= "---NOCARGADO---";	// <DDBB> CAMPUS.CAMPUSUSER.USERTYPEID

	private static SPDConstants instance = null;
	
	/**
	 * Clase de implementación de la seguridad.
	 */
	public static String SECURITYIMP_CLASS="lopicost.spd.security.SecurityImpl";
	
	
	public static String MSG_LEVEL_INFO  = "info";
	public static String MSG_LEVEL_ERROR = "error";
	
	public static String IOSPD_LOG_ENABLED = "0";
	
	public static String PER_DIARIO="DIARIO";
	public static String PER_SEMANAL="SEMANAL";
	public static String PER_QUINCENAL="QUINCENAL";
	public static String PER_MENSUAL="MENSUAL";
	public static String PER_BIMENSUAL="BIMENSUAL";
	public static String PER_TRIMESTRAL="TRIMESTRAL";
	public static String PER_CUATRIMESTRAL="CUATRIMESTRAL";
	public static String PER_SEMESTRAL="SEMESTRAL";
	
	public static final String DIA1 = "lunes";
	public static final String DIA2 = "martes";
	public static final String DIA3 = "miércoles";
	public static final String DIA4 = "jueves";
	public static final String DIA5 = "viernes";
	public static final String DIA6 = "sábado";
	public static final String DIA7 = "domingo";

	public static final String DIA1_HELIUM = "monday";
	public static final String DIA2_HELIUM = "tuesday";
	public static final String DIA3_HELIUM = "wednesday";
	public static final String DIA4_HELIUM = "thursday";
	public static final String DIA5_HELIUM = "friday";
	public static final String DIA6_HELIUM = "saturday";
	public static final String DIA7_HELIUM = "sunday";
	
	public static final String GUIDE_DIAS = "DIAS";
	public static final String GUIDE_SEMANAS = "SEMANAS";
	public static final String GUIDE_MESES = "MESES";
	
	public static final String GUIDE_DIAS_HELIUM = "DAYS";
	public static final String GUIDE_SEMANAS_HELIUM = "WEEKS";
	public static final String GUIDE_MESES_HELIUM = "MONTHS";
	
	public static final String TIPO_0_OTROS_HELIUM = "0";
	public static final String TIPO_1_DIARIO_HELIUM = "1";
	public static final String TIPO_2_DIAS_CONCRETOS_HELIUM = "2";
	public static final String TIPO_3_FRECUENCIA_HELIUM = "3";
	public static final String TIPO_4_GUIDE_HELIUM = "4";

	public static final String INFO_RESI_SIN_CIP_AVISO = "CIP no informado. ";
	public static final String INFO_RESI_SIN_CIP_ARREGLO = "En el listado de la residencia falta añadir el CIP. ";
	public static final String INFO_INTERNA_CIP_SIN_ALTA = " El CIP no está dado de alta en la gestión. ";
	public static final String INFO_INTERNA_CIP_OTRA_RESI = " El CIP está asignado a otra residencia distinta. ";
	public static final String INFO_INTERNA_CIP_SPD_NO = " CIP marcado como SPD=N en la gestión. ";
	public static final String INFO_RESI_CN_NO_INDICADO = "Llega sin código, solicitamos que se incluya uno válido. ";
	
	public static final String INFO_INTERNA_REVISION_SEQGUIDE = " Revisa la SecuenciaGuide, no está bien creada. ";
	public static final String INFO_INTERNA_REVISION_COMPRIMIDOS= " Revisar número total de comprimidos día en pauta. ";
	public static final String INFO_INTERNA_REVISION_SUSTITUCION= " Revisar sustitución o caracteres correctos en pauta. ";
	public static final String INFO_INTERNA_CONFIRMAR_SUSTITUCION_PAUTA= " Confirmar que se ha sustituido bien la pauta. ";
	public static final String INFO_INTERNA_CNFINAL_NOVALIDO= " Revisar CN Final, ha de ser formato numérico con 6 dígitos. ";
	
	
	public static final String INFO_RESIDENCIA_AVISO_CN_RECETA= "Solicitamos que en los listados se indique el tratamiento con el CN que tiene en receta.";
	
	
	public static final String ALERTA_NO_SUSTITUCION = "No se ha encontrado sustitución. ";
	public static final String ALERTA_REVISION_FECHAINICIO = "Revisar fecha inicio. ";
	public static final String ALERTA_NO_CODIGO = " Tratamiento con código vacío, solicitamos arreglo. ";
	public static final String ALERTA_REVISION_FECHAFIN = "FechaFinTratamiento no válida. ";
	public static final String ALERTA_REVISION_TIPOHELIUM = "Tipo de envío Helium no completado. ";
	public static final String ALERTA_INTERNA_CNFINAL_NOVALIDO = " CIP marcado como SPD=N en la gestión. ";

	public static final String ERROR_FALTA_RESIDENCIA = "Falta indicar la residencia. ";

	public static final String AVISO_REVISA_VALOR = " (INTERNO FARMACIA) OJO, revisar valor " ;

	public static final String CTRL_NCOMPRIMIDOS_IGUAL 		 = "OK"; // CASO 1 - No alerta - Nº igual de comprimidos
	public static final String CTRL_NCOMPRIMIDOS_DIFERENTE 	 = "ALERTA"; // CASO 2 - Alerta - Número diferente
	public static final String CTRL_NCOMPRIMIDOS_NOAPLICA = ""; // CASO 3 - No aplica porque no va a robot
	
	
	public static final String CTRL_REGISTRO_ANTERIOR_RI_SI = "RI_SI"; // CASO 1 - No alerta - Reutilizado ok
	public static final String CTRL_REGISTRO_ANTERIOR_RD_SD = "RD_SD"; // CASO 2 - No alerta - Registro nuevo (Revisión si cabe)  
	public static final String CTRL_REGISTRO_ANTERIOR_RI_SD = "RI_SD"; // CASO 3 - Alerta - Confirmar, se envía diferente a la anterior, recibiendo lo mismo
	public static final String CTRL_REGISTRO_ANTERIOR_RD_SI = "RD_SI"; // CASO 4 - Alerta - Confirmar, se envía lo mismo que un tratamiento que se recibe diferente

	public static final String CTRL_ROBOT_SE_ENVIA_A_ROBOT = "SI_ROBOT";
	public static final String CTRL_ROBOT_NO_SE_ENVIA =  "NO_ROBOT";

	public static final String CTRL_PRINCIPIO_ACTIVO_NO_ALERTA = "OK";
	public static final String CTRL_PRINCIPIO_ACTIVO_ALERTA = "ALERTA";

	public static final String CTRL_SUSTITUIBLE_NOALERTA = "OK";
	public static final String CTRL_SUSTITUIBLE_ALERTA = "ALERTA";

	public static final String CTRL_DIFERENTE_GTVMP_OK = "OK";
	public static final String CTRL_DIFERENTE_GTVMP_ALERTA = "ALERTA";
	
	public static final String CTRL_UNICO_GTVM_OK = "OK";
	public static final String CTRL_UNICO_GTVM_ALERTA = "ALERTA";

	public static final String REGISTRO_VALIDAR_SI = "SI";;

	public static final String CTRL_VALIDAR_NO = "OK";;
	public static final String CTRL_VALIDAR_ALERTA = "ALERTA";

	public static final String REGISTRO_CONFIRMADO =  "CONFIRMADO";
	public static final String REGISTRO_CONFIRMADO_AUTO =  "CONFIRMADO_AUTO";
	public static final String REGISTRO_VALIDADO =  "VALIDADO";
	public static final String REGISTRO_VALIDADO_AUTO =  "VALIDADO_AUTO";
	public static final String REGISTRO_VALIDAR = "VALIDAR";
	public static final String REGISTRO_CONFIRMAR = "CONFIRMAR";

	public static final int CTRL_PRINCIPIO_ACTIVO_N_VALIDACIONES = 3; //Número de validaciones necesarias para validarlo completamente

	public static final int CTRL_MAX_COMPRIMIDOS_DIA = 8; //límite de pastillas para mostrar un mensaje y validar pauta mientras sea un registro nuevo

	public static final int MAX_COMPRIMIDOS_POR_BOLSA = 7; //límite de pastillas para mostrar un mensaje y validar pauta mientras sea un registro nuevo
	public static final int MAX_LINEAS_PASTILLERO_POR_BOLSA = 7; //límite de líneas de PASTILLERO por bolsa
	public static final int MAX_LINEAS_SOLO_INFO_POR_BOLSA = 5; //límite de líneas de SOLO_INFO por bolsa


	public static final int DIAS_CONSULTA_LOG = 180;		//Días atrás para consulta de logs

	public static final int DIAS_MAX_PROCESOS_IMPORT = 2;
	public static final int DIAS_MAX_RECOGIDA_IOFWIN= 1;	//Días máximos para dar alerta en caso de que no se recojan datos de iofwin
	public static final int PERCENT_MIN_RECOGIDA_DATOS_IOFWIN= 85;	//% Mínimo de datos recogidos  OK, para que no salte alarma

	//public static final String REGISTRO_ENPROCESO_CONFIRMACION = "EN PROCESO DE CONFIRMACION";;
	
	//Aegerus
	public static final String REGISTRO_AEGERUS_TODO_NO= "TODO_NO";
	public static final String IDPROCESO_AEGERUS= "importAegerus";
	public static final String IDPROCESO_MONTSENY= "importSFAssis";
	public static final String IDPROCESO_STAUROS= "importExcelStauros";


	/**
	 * Path absoluto para localizar el css
	 */
	public static String CSS_URL="/lopicost/admin/dbforms.css";

	/**
	 * Skin por defecto : carpeta por defecto donde estan los css
	 */
	public static String DEFAULT_SKIN="lopicost";
	

	
	
	
	public static synchronized void loadSPDConstants()
	{
		if (rb==null)
		{	
			log("SPDCONSTANTS: Carreguem el SPDConstants.properties",Logger.DEBUG);
			rb = ResourceBundle.getBundle(PROPERTY_BOUNDLE_NAME);
			try
			{
				LOGS_DIR= rb.getString("LOGS_DIR");
			}
			catch (Exception e)
			{
				log("SPDCONSTANTS: No se ha encontrado la propiedad LOGS_DIR, cargado el valor por defecto", Logger.DEBUG);
			}			
			try
			{
				PAGE_ROWS= new Integer(rb.getString("PAGE_ROWS")).intValue();
			}
			catch (Exception e)
			{
				log("SPDCONSTANTS: No se ha encontrado la propiedad PAGE_ROWS, cargado el valor por defecto", Logger.DEBUG);
			}
			try
			{
				SERVER_CONTEXT= rb.getString("SERVER_CONTEXT");
			}
			catch (Exception e)
			{
				log("SPDCONSTANTS: No se ha encontrado la propiedad SERVER_CONTEXT, cargado el valor por defecto", Logger.DEBUG);
			}

			
			try
			{
				SERVER_CONTEXT= rb.getString("SERVER_CONTEXT");
			}
			catch (Exception e)
			{
				log("SPDCONSTANTS: No se ha encontrado la propiedad SERVER_CONTEXTL, cargado el valor por defecto", Logger.DEBUG);
			}		

			try
			{
				MESSAGE_RESOURCE_PROPERTIES= rb.getString("MESSAGE_RESOURCE_PROPERTIES");
			}
			catch (Exception e)
			{
				log("SPDCONSTANTS: No se ha encontrado la propiedad MESSAGE_RESOURCE_PROPERTIESL, cargado el valor por defecto", Logger.DEBUG);
			}		
			try
			{
				MESSAGE_LOGS_PROPERTIES= rb.getString("MESSAGE_LOGS_PROPERTIES");
			}
			catch (Exception e)
			{
				log("SPDCONSTANTS: No se ha encontrado la propiedad MESSAGE_LOGS_PROPERTIES, cargado el valor por defecto", Logger.DEBUG);
			}		
		try
			{
				LOGS_DIR= rb.getString("LOGS_DIR");
			}
			catch (Exception e)
			{
				log("SPDCONSTANTS: No se ha encontrado la propiedad LOGS_DIR, cargado el valor por defecto", Logger.DEBUG);
			}		
			try
			{
				PROPERTY_BOUNDLE_NAME= rb.getString("PROPERTY_BOUNDLE_NAME");
			}
			catch (Exception e)
			{
				log("SPDCONSTANTS: No se ha encontrado la propiedad PROPERTY_BOUNDLE_NAME, cargado el valor por defecto", Logger.DEBUG);
			}		
			
		
			
			try {
				MSG_LEVEL_INFO= rb.getString("MSG_LEVEL_INFO");
			} catch (Exception e) {
				log("SPDCONSTANTS: No se ha encontrado la propiedad MSG_LEVEL_INFO, cargado el valor por defecto", Logger.DEBUG);
			}
			try {
				MSG_LEVEL_ERROR= rb.getString("MSG_LEVEL_ERROR");
			} catch (Exception e) {
				log("SPDCONSTANTS: No se ha encontrado la propiedad MSG_LEVEL_ERROR, cargado el valor por defecto", Logger.DEBUG);
			}
			
			
		}else{
			log("SPDCONSTANTS: NO Carreguem el SPDConstants.properties",Logger.DEBUG);
		}	
	}
	
	/*
	 * Si existe el nombre de la propiedad, devuelve el contenido
	 * Si NO existe, devuelve lo que devuelve el [ ResourceBundle ]
	 */
	public static synchronized String leePropiedad( String idPropiedad ) {
		if (theFirstAccess) {
			loadConstants();
		}
		String returnValue 	= "";
		try {
			returnValue			= rb.getString( idPropiedad );
		} catch (Exception e) 
		{	try
			{
				returnValue= SPDConstants.class.getField(idPropiedad).toString(); 
			}
			catch (NoSuchFieldException nsfe)
			{
				log("SPDCONSTANTS: No se ha encontrado la propiedad ["+idPropiedad+"].", Logger.DEBUG);
			}
		}
		return returnValue;
	}	
	
	private static synchronized void loadConstants() {
		if (rb==null) {	
			log("SPDCONSTANTS: Carreguem el SPDConstants.properties",Logger.INFO);
			rb = ResourceBundle.getBundle(PROPERTY_BOUNDLE_NAME);
		}
	}
	
	public static void log (String message, int level)
	{
		Logger.log("SpdLogger",message,level);	
	}
	
	public static SPDConstants getInstance() {
		if ( instance == null ) {
			instance = new SPDConstants();
		}
		return instance;
	}
	

}