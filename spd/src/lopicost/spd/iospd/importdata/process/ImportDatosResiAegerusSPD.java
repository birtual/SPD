
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
import lopicost.spd.persistence.GestSustitucionesDAO;
import lopicost.spd.persistence.StockFL_DAO;
import lopicost.spd.struts.bean.FicheroResiBean;
import lopicost.spd.struts.form.BdConsejoForm;
import lopicost.spd.utils.DateUtilities;
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

public class ImportDatosResiAegerusSPD extends ImportProcessImpl
{
	TreeMap CIPSTratados =new TreeMap();
	boolean procesosAnterioresLimpiados = false;
	
	
	public ImportDatosResiAegerusSPD(){
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
	
		resi			idproceso			CN_OK	NOMBRE_CORTO	PRESENTACION	TIPO_DISP
		MEDICAMENTO		CODIGO NACIONAL		CIP		NOMBRE			HABITACION		UNIDAD ADMINISTRACION
	*/
    
    public void procesarEntrada(String idRobot, String idDivisionResidencia, String idProceso, Vector row, int count) throws Exception 
    {
       if (row!=null && row.size()>=12)
       {        	
    	   boolean result = false;
    	   String element = (String) row.elementAt(1);
    	   if(!element.toUpperCase().contains("IDPROCESO"))	//nos aseguramos de saltar la cabecera
    		   result=creaRegistro(row);
       }
       else 
            throw new Exception (TextManager.getMensaje("ImportData.error.ImportDatosResiSPD"));
    }
    
    private boolean creaRegistro(Vector row) throws Exception {
 
      	boolean result =true;
    	FicheroResiBean fila = new FicheroResiBean();

    	fila.setIdDivisionResidencia((String) row.elementAt(0));	
    	fila.setIdProceso((String) row.elementAt(1));	
       	fila.setSpdCnFinal((String) row.elementAt(2));
    	fila.setSpdNombreBolsa((String) row.elementAt(3));	
     	fila.setSpdFormaMedicacion((String) row.elementAt(4));	
    	fila.setSpdAccionBolsa((String) row.elementAt(5));	
       	fila.setResiMedicamento(StringUtil.replaceInvalidChars((String) row.elementAt(6)));	
    	fila.setResiCn(StringUtil.replaceInvalidChars((String) row.elementAt(7)));	
     	fila.setResiCIP(StringUtil.replaceInvalidChars((String) row.elementAt(8)));	
    	fila.setResiNombrePaciente(StringUtil.replaceInvalidChars((String) row.elementAt(9)));	
    	fila.setResiObservaciones(StringUtil.replaceInvalidChars((String) row.elementAt(10)));	
    	fila.setResiInicioTratamiento((String) row.elementAt(11));
    	fila.setResiFinTratamiento((String) row.elementAt(12));
        fila.setResiSiPrecisa("");
    	fila.setResiComentarios("");
     	
     	if(!CIPSTratados.containsKey(fila.getResiCIP()))
  		{
     		
        	ioSpdApi.limpiarCIPIdprocesoAnterior(fila.getIdProceso(), fila.getResiCIP());
            //Una vez limpiado, se añade como CIP ya tratado para no volver a limpiar datos
            CIPSTratados.put(fila.getResiCIP(), fila.getResiCIP());
            
  		}
     	
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
     	
 		result=FicheroMedResiConPrevisionDAO.nuevo(fila);
		if(!result)
		{
			throw new Exception ("No se ha podido crear el registro ImportDatosResiSPD (ctl_medicacioResi)");
			//errors.add( "Registro sust creado correctamente ");
		}

	    
	return result;
	}



	protected boolean beforeStart(String filein) throws Exception 
    {
      return true;
    }

    protected void afterStart() throws ClassNotFoundException, SQLException 
    {
    	IOSpdApi.borraErrores(getIdDivisionResidencia());
    }

	@Override
	protected void procesarEntrada(String idRobot, String idDivisionResidencia, String idProceso, Vector row, int count,
			boolean cargaAnexa) throws Exception {
		// TODO Esbozo de método generado automáticamente
		
	}

}



