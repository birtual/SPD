package lopicost.spd.iospd;

import lopicost.config.pool.dbaccess.Conexion;

import lopicost.spd.model.*;

import lopicost.spd.persistence.*;
import lopicost.spd.struts.bean.FicheroResiBean;
import lopicost.spd.struts.form.SustXComposicionForm;
import lopicost.spd.utils.DateUtilities;
import lopicost.spd.utils.MessageManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;
import java.util.Vector;


public class IOSpdApi 
{
    private String caracteresValidos = "[A-Z|a-z|º|ª|ñ|Ñ|á|é|í|ó|ä|ë|ö|ü|ç|à|è|ì|ò|ù|,]";
    
    public static final String ERRORCODE_REGISTRATION_GENERIC			= "E01";
	

	private Hashtable cache= new Hashtable();
	
	private static final int CACHE_STATUS =1;

	private  static int n=1;	
	private static MessageManager mm = MessageManager.instance();


    
 
    private void addItemCache(int cacheType,String key ,Object value){
    	((Hashtable)cache.get(new Integer(cacheType))).put(key, value);
    }
    
    public void clearCache(){
    	cache= new Hashtable();
    }



	
    /**
     * 20211029_NO USADO porque conservamos los datos de ficheros por procesos
     * Método encargado de realizar un borrado de los datos idproceso-CIP-CN de la tabla principal y guardarlos en 
     * la tabla de histórico  
     * @param medResi
     * @throws Exception 
	public void limpiarCIPIdprocesoAnterior(String spdUsuario, FicheroResiBean medResi) throws Exception {
		//GUARDAMOS HISTÓRICO 
		FicheroResiDetalleDAO.generarHistoricoProcesoAnterior(spdUsuario,  medResi, true);
		//BORRAMOS
		FicheroResiDetalleDAO.limpiarCIPIdproceso(spdUsuario,  medResi, true);
		
	}
*/	
	/**
	 * Método encargardo de chequear si los datos que se reciben son correctos y es
	 * un tratamiento válido
	 * @param medResi
	 * @param row 
	 * @return
	 * @throws Exception 
	 */
	public static FicheroResiBean compruebaTratamiento(String spdUsuario, FicheroResiBean medResi) throws Exception {
		medResi=checkTratamientoValido(spdUsuario, medResi);
		
		return medResi;
	}

	
	/**
	 * Método que retorna si una linea de tratamiento recibida por iospd es válido. 
	 * Se tendrá en cuenta únicamente si: 
	 *  - Se recibe alguna de las tomas con datos, 
	 *  - Se recibe  CIP y nombre de paciente (con uno de los dos sería suficiente)
	 *  - Se recibe un CN o un nmbre de medicamento
	 * @param medResi
	 * @return 
	 * @throws Exception 
	 */
public static FicheroResiBean checkTratamientoValido(String spdUsuario, FicheroResiBean b) throws Exception {
		
		String mensaje="";
		
		if(DivisionResidenciaDAO.getDivisionResidenciaById(spdUsuario, b.getIdDivisionResidencia())==null)
			mensaje += " Falta residencia. ";
		if(b.getResiCIP()==null || b.getResiCIP().equals(""))
			mensaje += " <br> Falta CIP. ";
		if(b.getResiNombrePaciente()==null || b.getResiNombrePaciente().equals(""))
			mensaje += "  <br> Falta Nombre residente. ";
		if(DateUtilities.isDateValid(b.getResiInicioTratamiento(), "dd/MM/yyyy"))
			
		
		if(b.getResiInicioTratamiento()==null || b.getResiInicioTratamiento().equals("") )
			mensaje += "  <br> Falta fecha inicio.  ";
		if(b.getResiCn()==null || b.getResiCn().equals("") )
			mensaje += "  <br> Falta CN residencia.  ";
		if(b.getResiMedicamento()==null || b.getResiMedicamento().equals("") )
			mensaje += "  <br> Falta nombre medicamento. ";
		
		if(b.getSpdCnFinal()==null || b.getSpdCnFinal().equals("") )
			mensaje += "  <br> Falta CN Robot. ";
		if(b.getSpdNombreBolsa()==null || b.getSpdNombreBolsa().equals("") )
			mensaje += "  <br> Falta Nombre medicamento Robot. ";
		if(b.getSpdAccionBolsa()==null || b.getSpdAccionBolsa().equals("") )
			mensaje += "  <br> Falta Acción en bolsa. ";
		
		if(mensaje!=null && !mensaje.equals(""))
		{
			b.setIncidencia("SI");
			b.setResultLog(" <br> Registro con incidencias el día " + DateUtilities.actualDate());
			if(!b.getMensajesAlerta().contains(mensaje))
				b.setMensajesAlerta(b.getMensajesAlerta() + " - " +  mensaje);
		}
		else
		{
			b.setIncidencia("");
			b.setResultLog("");
			b.setMensajesAlerta("");
		}
		

		return b;
	}
	
/*
	private FicheroResiBean checkTratamientoValido(FicheroResiBean medResi) {
		//pautas, que ya vienen "limpias" de espacios y demás
		if(
			(
					//viene sin CIP y sin nombre
					(medResi.getResiCIP()==null || medResi.getResiCIP().equals(""))&&(medResi.getResiNombrePaciente()==null || medResi.getResiNombrePaciente().equals(""))
				
			)
			//si no hay fechaDesde
			|| (medResi.getResiInicioTratamiento()==null || medResi.getResiInicioTratamiento().equals("")  )
			//cn / medicamento
			|| ((medResi.getResiCn()==null || medResi.getResiCn().equals("")) && (medResi.getResiMedicamento()==null || medResi.getResiMedicamento().equals("")) )
			//cip / nombre paciente
			|| ((medResi.getResiCIP()==null || medResi.getResiCIP().equals("")) && (medResi.getResiNombrePaciente()==null || medResi.getResiNombrePaciente().equals("")) )
			)
			
		{
			medResi.setIncidencia("SI");
			medResi.setPersistir(true);
			medResi.setResultLog(" * Registro sin datos válidos. ");
		}
		
		return medResi;
	}

	*/

	public boolean addCtlMedicacionResidenciaOriginal(String idDivisionResidencia, String idProceso, FicheroResiBean medResi) throws ClassNotFoundException, SQLException {
		boolean result = FicheroResiDetalleDAO.nuevo(idDivisionResidencia, idProceso, medResi);
		return  result ;
		
	}
	
/*
	public boolean addCtlMedicacionResidencia(FicheroResiBean medResi) throws ClassNotFoundException {
		//control del CIP
		if (medResi.getResiCIP()==null || medResi.getResiCIP().equals("")) 
			//medResi.setResultLog(medResi.getResultLog() + " / * Falta CIP paciente. ");
			medResi.setMensajesAlerta(medResi.getMensajesAlerta() + " / * Falta CIP paciente. ");
		boolean result = FicheroResiDetalleDAO.nuevo(medResi.getIdDivisionResidencia(), medResi.getIdProceso(), medResi);
		return  result ;
		
	}
	*/
	
	public static boolean validarFecha(String fecha) {
        try {
            SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
            formatoFecha.setLenient(false);
            formatoFecha.parse(fecha);
            System.out.println();
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    public Vector getSustXConjHomog (TreeMap params) throws ClassNotFoundException, SQLException
    {
        Vector p=new Vector();
        Vector s=null;
        String st="";
       	SustXComposicionForm form = new SustXComposicionForm();

  	    form.setFiltroTextoABuscar((String)params.get("filtroTextoABuscar"));
   		//form.setFiltroListaConjuntosHomogeneos((String)params.get("filtroListaConjuntosHomogeneos"));
    	form.setFiltroPresentacion((String)params.get("filtroListaPresentacion"));
    	form.setFiltroCodigoMedicamento((String)params.get("filtroCodigoMedicamento"));
    	form.setCodigoGT((String)params.get("codigoGT"));
    	form.setGrupoTerapeutico((String)params.get("grupoTerapeutico"));
    	form.setFiltroCodiLaboratorio((String)params.get("filtroCodiLaboratorio"));
    	form.setCodGtAtcNivel3((String)params.get("codGtAtcNivel3"));
    	form.setNomGtAtcNivel3((String)params.get("nomGtAtcNivel3"));
    	form.setCodGtAtcNivel4((String)params.get("codGtAtcNivel4"));
    	form.setNomGtAtcNivel4((String)params.get("nomGtAtcNivel4"));
    	form.setCodGtAtcNivel5((String)params.get("codGtAtcNivel5"));
    	form.setNomGtAtcNivel5((String)params.get("nomGtAtcNivel5"));
    	form.setFiltroCodGtVmp((String)params.get("filtroCodGtVmp"));
    	form.setFiltroCodGtVm((String)params.get("filtroListaGtVm"));
    	form.setNomGtVm((String)params.get("nomGtVm"));
    	form.setNomGtVmp((String)params.get("nomGtVmp"));
    	form.setCodGtVmpp((String)params.get("codGtVmpp"));
    	form.setNomGtVmpp((String)params.get("nomGtVmpp"));
    	form.setCodiLab((String)params.get("codiLab"));
    	form.setNombreLab((String)params.get("nombreLab"));
    	form.setFiltroCheckedLabsSoloAsignados((String)params.get("filtroCheckedLabsSoloAsignados")!=null  && params.get("filtroCheckedLabsSoloAsignados").equals("true"));
    	form.setFiltroCheckedComposicionSinLabs((String)params.get("filtroCheckedConjHomogSinLabs")!=null && params.get("filtroCheckedConjHomogSinLabs").equals("true"));

       
       Iterator it= SustXComposicionDAO.getSustXComposicion(form, 0, 100000).iterator();
 
       while (it!=null && it.hasNext())
       {
    	   SustXComposicion g = (SustXComposicion)it.next();
    	   p.add(g.toStringForExportCSV());
         //  p.add(s);
        }
   
       return s;
       
    
    }

	public boolean addGestFicheroResi(String  spdUsuario, String idDivisionResidencia, String idProceso, String filein) throws Exception {
		return FicheroResiCabeceraDAO.nuevo(spdUsuario, idDivisionResidencia, idProceso,  filein);
		
	}

	public int getCipsTotalesCargaFichero(String spdUsuario, String idDivisionResidencia, String idProceso) throws Exception {
		return FicheroResiDetalleDAO.getCipsTotalesCargaFichero(spdUsuario, idDivisionResidencia, idProceso);
		
	}

	public int getCipsNoExistentesBbdd(String spdUsuario, String idDivisionResidencia, String idProceso)  throws Exception {
		return FicheroResiDetalleDAO.getCipsNoExistentesBbdd(spdUsuario, idDivisionResidencia, idProceso);
		
	}
	

	public int getCipsActivosSPD(String spdUsuario, String idDivisionResidencia) throws Exception {
		return FicheroResiDetalleDAO.getCipsActivosSPD(spdUsuario, idDivisionResidencia);
	}
	
	public int getLineasProceso(String spdUsuario, String idDivisionResidencia, String idProceso)  throws Exception {
		return FicheroResiDetalleDAO.getLineasProceso(spdUsuario, idDivisionResidencia, idProceso);
	}
	
/*
	public boolean editaFinCargaFicheroResi(String idDivisionResidencia, String idProceso, int processedRows, int cipsTotales, int cipsNoExistentesBbdd, int cipsSpdResiNoExistentesEnFichero, int numeroMensajesInfo, int numeroMensajesAlerta, List errors)  throws ClassNotFoundException {
		return FicheroResiCabeceraDAO.editaFinCargaFicheroResi(idDivisionResidencia, idProceso, processedRows, cipsTotales, cipsNoExistentesBbdd,  cipsSpdResiNoExistentesEnFichero,  numeroMensajesInfo,  numeroMensajesAlerta, errors);
	}

	public boolean editaFinCargaFicheroResi(String idDivisionResidencia, String idProceso, int processedRows,  int cipsTotales, int cipsActivosSPD, int porcent, List errors)  throws ClassNotFoundException, SQLException {
		return FicheroResiCabeceraDAO.editaFinCargaFicheroResi(idDivisionResidencia, idProceso, processedRows,  cipsTotales, cipsActivosSPD, porcent, errors);
	}
*/	
	public int getOidFicheroResiCabecera(String spdUsuario, String idDivisionResidencia, String idProceso) throws Exception {
		return FicheroResiCabeceraDAO.getOidFicheroResiCabecera(spdUsuario, idDivisionResidencia, idProceso);
	}

	public int getCipsSpdResiNoExistentesEnFichero(String spdUsuario, String idDivisionResidencia, String idProceso) throws Exception {
		return FicheroResiDetalleDAO.getCipsSpdResiNoExistentesEnFichero( spdUsuario, idDivisionResidencia, idProceso);
	}

	public int getNumeroMensajesInfo(String spdUsuario, String idDivisionResidencia, String idProceso)  throws Exception {
		return FicheroResiDetalleDAO.getNumeroMensajesInfo( spdUsuario, idDivisionResidencia, idProceso);
	}

	public int getNumeroMensajesAlerta(String spdUsuario, String idDivisionResidencia, String idProceso)  throws Exception {
		return FicheroResiDetalleDAO.getNumeroMensajesAlerta( spdUsuario, idDivisionResidencia, idProceso);
	}


	public void limpiarCIPsInactivos() throws ClassNotFoundException, SQLException {
		//GUARDAMOS HISTÓRICO 
		FicheroMedResiDAO.creaHistoricoPacientesInactivos();
    	FicheroMedResiConPrevisionDAO.creaHistoricoPacientesInactivos();
		FicheroMedResiDAO.borraProcesosPacientesInactivos();
    	FicheroMedResiConPrevisionDAO.borraProcesosPacientesInactivos();
		
	}

	public static void limpiarCIPIdprocesoAnterior(String idProceso, String resiCIP) throws ClassNotFoundException, SQLException {
		FicheroMedResiDAO.limpiarDatosHistoricoProcesosAnterioresCIP( idProceso, resiCIP);		//limpiamos datos del hst para no redundar CIP-idProcesos-CN ya que se necesita consultar producciones
		FicheroMedResiDAO.creaHistoricoProcesosAnterioresCIP(resiCIP);				//creamos el hst del CIP-idProceso anteriores
		FicheroMedResiDAO.limpiarDatosCIP(resiCIP);			//borramos el CIP-idProceso que ya está en hst
		FicheroMedResiConPrevisionDAO.limpiarDatosHistoricoProcesosAnterioresCIP( idProceso, resiCIP);
		FicheroMedResiConPrevisionDAO.creaHistoricoProcesosAnterioresCIP( resiCIP);
		FicheroMedResiConPrevisionDAO.limpiarDatosCIP(resiCIP);
		
	}
/*
 * 20230328 - De momento no se usa, ahora lo hacemos a nivel de CIP resi
	public void limpiarProcesoAnterioresResi(String idDivisionResidencia) throws ClassNotFoundException {
		FicheroMedResiDAO.creaHistoricoProcesosResi(idDivisionResidencia);
		FicheroMedResiConPrevisionDAO.creaHistoricoProcesosResi(idDivisionResidencia);
		FicheroMedResiDAO.limpiarDatosResi(idDivisionResidencia);
		FicheroMedResiConPrevisionDAO.limpiarDatosResi(idDivisionResidencia);
	}
*/
	public static void borraErrores(String idDivisionResidencia)  throws ClassNotFoundException, SQLException {
		FicheroMedResiDAO.borraErrores();
		FicheroMedResiConPrevisionDAO.borraErrores();
		FicheroMedResiDAO.borraErrores_hst();
		FicheroMedResiConPrevisionDAO.borraErrores_hst();
		FicheroMedResiDAO.creaHistoricoProcesosResi(idDivisionResidencia);
		FicheroMedResiConPrevisionDAO.creaHistoricoProcesosResi(idDivisionResidencia);
		FicheroMedResiDAO.limpiarDatosResi(idDivisionResidencia);
		FicheroMedResiConPrevisionDAO.limpiarDatosResi(idDivisionResidencia);

	}

	public boolean compruebaValidez(FicheroResiDetalle fila) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Se encarga de eliminar filas huérfanas de detalle sin que tengan oidFicheroResiCabecera asociadas
	 * @return
	 * @throws Exception
	 */
	public boolean borraDetalleSinCabecera() throws Exception {
		
		return FicheroResiDetalleDAO.borraDetalleSinCabecera();
	}

	/**
	 * Si quedan procesos que no suben correctamente y que no pasan a estado validado, se actualizará a descartado si han pasado dos dias desde la subida
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public static boolean actualizaEstadosSinFinalizar() throws ClassNotFoundException, SQLException {
		return FicheroResiCabeceraDAO.actualizaEstadosSinFinalizar();		
	
	}

	
	/**
	 * Método encargado de vaciar un poco la gestión de producciones para enviar a histórico las que llevan un tiempo determinado.
	 * @return
	 * @throws Exception
	 */
	public boolean buscarParaPasarAHistorico() throws Exception {
		
		Connection conn = null;
	    boolean result = false;
	    try {
	        conn = Conexion.conectar(); // Obtener la conexión a la base de datos
	        conn.setAutoCommit(false); // Desactivar el modo de autocommit para iniciar una transacción

	        List aHistorico = FicheroResiCabeceraDAO.getCabecerasProcesosAnterioresAHistorico();

	        if(aHistorico!=null && aHistorico.size()>0)
	        {
		        // Realizar las operaciones de borrado dentro de la transacción
		        result = FicheroResiCabeceraDAO.cabecerasProcesosAnterioresAHistorico(conn, aHistorico);
		        result &= FicheroResiDetalleDAO.detalleProcesosAnterioresAHistorico(conn, aHistorico);
		        result &= FicheroResiDetalleDAO.borrarDetalleYaEnHistorico(conn, aHistorico);
		        result &= FicheroResiCabeceraDAO.borrarCabecerasYaEnHistorico(conn, aHistorico);
	        	
	        }

	        // Si todas las operaciones fueron exitosas, realizar commit
	        if (result) {
	            conn.commit();
	        } else {
	            // Si alguna operación falla, realizar rollback
	            conn.rollback();
	        }
	    } catch (SQLException ex) {
	        // Manejar la excepción
	        //ex.printStackTrace();
	        // Realizar rollback en caso de excepción
	        if (conn != null) {
	            conn.rollback();
	        }
	    } finally {
	        // Cerrar la conexión
	        if (conn != null) {
	            conn.setAutoCommit(true); // Restaurar el modo de autocommit
	            conn.close();
	        }
	    }
	    return result;
	}
        
        
		



}