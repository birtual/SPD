
package lopicost.spd.iospd.importdata.process;

import lopicost.spd.controller.SpdLogAPI;
import lopicost.spd.iospd.IOSpdApi;
import lopicost.spd.model.BdConsejo;
import lopicost.spd.model.DivisionResidencia;
import lopicost.spd.model.FicheroResiDetalle;
import lopicost.spd.model.GestSustituciones;
import lopicost.spd.model.GestSustitucionesXResi;
import lopicost.spd.model.farmacia.StockFL;
import lopicost.spd.persistence.BdConsejoDAO;
import lopicost.spd.persistence.DivisionResidenciaDAO;
import lopicost.spd.persistence.FicheroMedResiConPrevisionDAO;
import lopicost.spd.persistence.FicheroMedResiDAO;
import lopicost.spd.persistence.GestSustitucionesDAO;
import lopicost.spd.persistence.StockFL_DAO;
import lopicost.spd.struts.bean.FicheroResiBean;
import lopicost.spd.struts.form.BdConsejoForm;
import lopicost.spd.utils.DataUtil;
import lopicost.spd.utils.DateUtilities;
import lopicost.spd.utils.HelperSPD;
import lopicost.spd.utils.SPDConstants;
import lopicost.spd.utils.StringUtil;
import  lopicost.spd.utils.TextManager;

import java.sql.SQLException;
import java.util.Date;
import java.util.TreeMap;
import java.util.Vector;

/**
 * Método encargado de importar el fichero recibido de la residencia, pero habiendo realizado nuestras sustituciones 
 * La finalidad es persistir los datos de SOLO_INFO y PASTILLERO del SPD junto con los NO_PINTAR, para poder compararlos
 * con las recetas y ver discrepancias.
 * El fichero ya viene "limpio" porque es el paso previo al envío al robot
 * author CARLOS
 *
 */

public class ImportDatosResiSPDPrevision extends ImportProcessImpl
{
	TreeMap CIPSTratados =new TreeMap();
	boolean procesosAnterioresLimpiados = false;
	
	public ImportDatosResiSPDPrevision(){
		super();
	}

	/**los ficheros han de venir con cabecera. Se tendrá en cuenta a partir de la fila 2**/	
    protected boolean beforeProcesarEntrada(Vector row) throws Exception 
    {
    	//pasar a histórico los inactivos
    	//if(!procesosAnterioresLimpiados)ioSpdApi.limpiarCIPsInactivos();
    	//FicheroMedResiConSustitucionDAO.creaHistoricoPacientesInactivos();
    	//FicheroMedResiConSustitucionDAO.borraProcesosPacientesInactivos();
    	
    	return true;
    }
    protected void afterprocesarEntrada(Vector row) throws Exception 
    {
    	
    }
    
    public void procesarCabecera(Vector row) throws Exception 
    {
    }
     
    /**
     * 	resi		  		idproceso 				CIP 			NombrePaciente  	CN_RESI
		MEDICAMENTO_resi	posologia(si_precisa)	Fecha Desde		FechaHasta			Observaciones
		Comentarios			CN						NombreProducto	FORMA				tipoBolsa
		comentario		
	*/
    
   //public void procesarEntrada(String idRobot, String idDivisionResidencia, String idProceso, Vector row, int count) throws Exception 
    public boolean procesarEntrada(String idRobot, DivisionResidencia div, String idProceso, Vector row, int count, boolean cargaAnexa) throws Exception
    {
    	boolean result = false;
       if (row!=null && row.size()>=22)
       {        	
    	   String element = (String) row.elementAt(1);
    	   if(!element.toUpperCase().contains("IDPROCESO"))	//nos aseguramos de saltar la cabecera
    		   result=creaRegistro(row);
       }
       else 
            throw new Exception (TextManager.getMensaje("ImportData.error.ImportDatosResiSPD"));
       return result;
    }
    
    private boolean creaRegistro(Vector row) throws Exception {
 
      	boolean resultPrevision =true;
      	boolean result =true;
    	FicheroResiBean fila = new FicheroResiBean();

    	fila.setIdDivisionResidencia((String) row.elementAt(0));	
    	
    	if(fila.getIdDivisionResidencia()!=null && fila.getIdDivisionResidencia().contains("general"))
    		this.setIdDivisionResidencia((String) row.elementAt(0));
    	
    	fila.setIdProceso((String) row.elementAt(1));	
    	fila.setResiCIP(StringUtil.replaceInvalidChars((String) row.elementAt(2)));	
    	fila.setResiNombrePaciente(StringUtil.replaceInvalidChars((String) row.elementAt(3)));	
    	fila.setResiCn(StringUtil.replaceInvalidCharsInNumeric((String) row.elementAt(4)));	
       	fila.setResiMedicamento(StringUtil.replaceInvalidChars((String) row.elementAt(5)));	
        fila.setResiSiPrecisa(StringUtil.replaceInvalidChars((String) row.elementAt(6)));	//
    	if(fila.getResiSiPrecisa()!=null && fila.getResiSiPrecisa().equalsIgnoreCase("x")) 
    		fila.setResiSiPrecisa("SI_PRECISA");
    	
    	String campoResiInicioTratamiento = (String) row.elementAt(7);
     	if(DataUtil.isNumeroGreatherThanZero(campoResiInicioTratamiento))
    	{
    		double numeroDouble = Double.parseDouble(campoResiInicioTratamiento);
    		int parteEntera = (int) numeroDouble;
    		if(parteEntera>50000) parteEntera=0;//número de una posible fecha 
    		campoResiInicioTratamiento = String.valueOf(parteEntera);
    	}
    	campoResiInicioTratamiento = StringUtil.getStringArregloFecha(campoResiInicioTratamiento, "dd/MM/yyyy"); //conversión a formato DD/MM/YYYY
    	fila.setResiInicioTratamiento(campoResiInicioTratamiento); 
 
    	String campoResiFinTratamiento = (String) row.elementAt(8);
    	
     	if(DataUtil.isNumeroGreatherThanZero(campoResiFinTratamiento))
    	{
    		double numeroDouble = Double.parseDouble(campoResiFinTratamiento);
    		int parteEntera = (int) numeroDouble;
    		if(parteEntera>50000) parteEntera=0;//número de una posible fecha 
    		campoResiFinTratamiento = String.valueOf(parteEntera);
    	}
     	campoResiFinTratamiento = StringUtil.getStringArregloFecha(campoResiFinTratamiento, "dd/MM/yyyy"); //conversión a formato DD/MM/YYYY
    	
    	fila.setResiFinTratamiento(campoResiFinTratamiento); //conversión a formato DD/MM/YYYY
    	//fila.setResiInicioTratamientoParaSPD(fila.getResiInicioTratamiento());
    	//fila.setResiFinTratamientoParaSPD(fila.getResiFinTratamiento());
    	fila.setResiObservaciones(StringUtil.replaceInvalidChars((String) row.elementAt(9)));
    		if(fila.getResiObservaciones()!=null && fila.getResiObservaciones().equalsIgnoreCase("SI PRECISA.")) //Ha de ser EXACTAMENTE "SI PRECISA."
    			fila.setResiSiPrecisa("SI_PRECISA");
    	fila.setResiComentarios(StringUtil.replaceInvalidChars((String) row.elementAt(10)));
    	fila.setSpdCnFinal(StringUtil.replaceInvalidCharsInNumeric((String) row.elementAt(11)));	
    	fila.setSpdNombreBolsa(StringUtil.replaceInvalidChars((String)row.elementAt(12)));
    	fila.setSpdFormaMedicacion((String) row.elementAt(13));	
    	fila.setSpdAccionBolsa((String) row.elementAt(14));	
    	
    	//marca en dias de la semana
    	fila.setResiD1(StringUtil.limpiarTextoyEspacios((String) row.elementAt(15)));
    	fila.setResiD2(StringUtil.limpiarTextoyEspacios((String) row.elementAt(16)));
    	fila.setResiD3(StringUtil.limpiarTextoyEspacios((String) row.elementAt(17)));
    	fila.setResiD4(StringUtil.limpiarTextoyEspacios((String) row.elementAt(18)));
    	fila.setResiD5(StringUtil.limpiarTextoyEspacios((String) row.elementAt(19)));
    	fila.setResiD6(StringUtil.limpiarTextoyEspacios((String) row.elementAt(20)));
    	fila.setResiD7(StringUtil.limpiarTextoyEspacios((String) row.elementAt(21)));
    	
    	//pauta
    	try{
        	fila.setResiToma1(HelperSPD.getPautaStandard(fila, (String) row.elementAt(22))); 
    		fila.setResiToma2(HelperSPD.getPautaStandard(fila, (String) row.elementAt(23))); 
    		fila.setResiToma3(HelperSPD.getPautaStandard(fila, (String) row.elementAt(24))); 
    		fila.setResiToma4(HelperSPD.getPautaStandard(fila, (String) row.elementAt(25)));
    		fila.setResiToma5(HelperSPD.getPautaStandard(fila, (String) row.elementAt(26)));
    		fila.setResiToma6(HelperSPD.getPautaStandard(fila, (String) row.elementAt(27)));
    		fila.setResiToma7(HelperSPD.getPautaStandard(fila, (String) row.elementAt(28)));
        	fila.setResiToma8(HelperSPD.getPautaStandard(fila, (String) row.elementAt(29)));
        	fila.setResiToma9(HelperSPD.getPautaStandard(fila, (String) row.elementAt(30))); 
        	fila.setResiToma10(HelperSPD.getPautaStandard(fila, (String) row.elementAt(31)));
        	fila.setResiToma11(HelperSPD.getPautaStandard(fila, (String) row.elementAt(32))); 
    		fila.setResiToma12(HelperSPD.getPautaStandard(fila, (String) row.elementAt(33)));
    		fila.setResiToma13(HelperSPD.getPautaStandard(fila, (String) row.elementAt(34)));
    		fila.setResiToma14(HelperSPD.getPautaStandard(fila, (String) row.elementAt(35)));
    		fila.setResiToma15(HelperSPD.getPautaStandard(fila, (String) row.elementAt(36))); 
    		fila.setResiToma16(HelperSPD.getPautaStandard(fila, (String) row.elementAt(37)));
    		fila.setResiToma17(HelperSPD.getPautaStandard(fila, (String) row.elementAt(38)));
    		fila.setResiToma18(HelperSPD.getPautaStandard(fila, (String) row.elementAt(39))); 
    		fila.setResiToma19(HelperSPD.getPautaStandard(fila, (String) row.elementAt(40)));
    		fila.setResiToma20(HelperSPD.getPautaStandard(fila, (String) row.elementAt(41))); 
    		fila.setResiToma21(HelperSPD.getPautaStandard(fila, (String) row.elementAt(42)));
    		fila.setResiToma22(HelperSPD.getPautaStandard(fila, (String) row.elementAt(43)));
    		fila.setResiToma23(HelperSPD.getPautaStandard(fila, (String) row.elementAt(44)));
    		fila.setResiToma24(HelperSPD.getPautaStandard(fila, (String) row.elementAt(45)));
    	}
    	catch(Exception e)
    	{
    		
    	}
		
		//conteo de comprimidos
		int diasSemanaMarcados= 0;
		float comprimidosDia= 0;
		float comprimidosSemana=0;


		
		//if(fila.getSpdAccionBolsa()!=null && fila.getSpdAccionBolsa().equalsIgnoreCase("PASTILLERO"))
		{
			//miramos si tiene alguna periodicidad
			HelperSPD.getPeriodicidad(fila);

			
			fila.setDiasSemanaMarcados(HelperSPD.getDiasMarcados(fila));
			comprimidosDia= HelperSPD.getPautaDia(fila)/fila.getResiFrecuencia();
			comprimidosSemana=comprimidosDia*fila.getDiasSemanaMarcados();

			 //por defecto
			fila.setComprimidosDia(comprimidosDia);
			fila.setComprimidosSemana(comprimidosSemana );
			fila.setComprimidosDosSemanas(comprimidosSemana * 2);
			fila.setComprimidosTresSemanas(comprimidosSemana * 3);
			fila.setComprimidosCuatroSemanas(comprimidosSemana * 4);

			 
		}
		
		//Búsqueda de BDConsejo
		BdConsejo bdConsejo=BdConsejoDAO.getByCN(fila.getSpdCnFinal()); 
		if(bdConsejo!=null)
		{
			fila.setBdConsejo(bdConsejo);
			fila.setSpdCodGtVm(bdConsejo.getCodGtVm());
			fila.setSpdNomGtVm(bdConsejo.getNomGtVm());
			fila.setSpdCodGtVmp(bdConsejo.getCodGtVmp());
			fila.setSpdNomGtVmp(bdConsejo.getNomGtVmp());
			fila.setSpdCodGtVmpp(bdConsejo.getCodGtVmpp());
			fila.setSpdNomGtVmpp(bdConsejo.getNomGtVmpp());
			fila.setSpdEmblistable(bdConsejo.getEmblistable());
		}
		
		if(fila.getSpdEmblistable()!=null&&fila.getSpdEmblistable().equalsIgnoreCase("SI") || (fila.getSpdAccionBolsa()!=null && fila.getSpdAccionBolsa().equalsIgnoreCase(SPDConstants.SPD_ACCIONBOLSA_PASTILLERO)))
		{
			fila.setSpdEmblistable("SI");
		}

    	
    	//fila.setSpdComentarioLopicost(StringUtil.replaceInvalidChars((String) row.elementAt(15)));	de momento no lo guardamos
    	
     	if(!CIPSTratados.containsKey(fila.getResiCIP()))
  		{
     		
        	ioSpdApi.limpiarCIPIdprocesoAnterior(fila.getIdProceso(), fila.getResiCIP());
            //Una vez limpiado, se añade como CIP ya tratado para no volver a limpiar datos
            CIPSTratados.put(fila.getResiCIP(), fila.getResiCIP());
            
  		}
		//INICIO importación en ctl_medicacioResi
		//nos vamos al otro proceso de importación que había hasta el 03/2023
		//Lo enlazamos desde este mismo proceso para no tener que importar dos veces
    	
    	/* Ahora lo hacemos CIP a CIP
     	if(!procesosAnterioresLimpiados) {
     		ioSpdApi.limpiarProcesoAnterioresResi(fila.getIdDivisionResidencia());
     		procesosAnterioresLimpiados=true;
     	}
     	*/
     		
     	try{
     		String idProceso = fila.getIdProceso();
     		int fechaInicioInt = new Integer(idProceso.substring(idProceso.length()-8, idProceso.length())).intValue();
     		int fechaFinInt = new Integer(idProceso.substring(idProceso.length()-17, idProceso.length()-9)).intValue();
     		fila.setFechaInicioInt(fechaInicioInt);
     		fila.setFechaFinInt(fechaFinInt);
       	}catch(Exception e)
     	{
     		
     	}
     	ioSpdApi.compruebaTratamiento(getSpdUsuario(), fila);
     	resultPrevision=FicheroMedResiConPrevisionDAO.nuevo(fila);
 		result=FicheroMedResiDAO.nuevo(fila);
 		
		if(!resultPrevision)
		{
			throw new Exception ("No se ha podido crear el registro de previsión  (SPD_resiMedicacion)");
			//errors.add( "Registro sust creado correctamente ");
		}
			if(!result)
		{
			throw new Exception ("No se ha podido crear el registro ImportDatosResiSPD (ctl_medicacioResi)");
			//errors.add( "Registro sust creado correctamente ");
		}
		
		//FIN importación en ctl_medicacioResi


	    
	return resultPrevision&&result;
	}



	protected boolean beforeStart(String filein) throws Exception 
    {
      return true;
    }

    protected void afterStart() throws ClassNotFoundException, SQLException 
    {
    	IOSpdApi.borraErrores(getIdDivisionResidencia());
    }



}



