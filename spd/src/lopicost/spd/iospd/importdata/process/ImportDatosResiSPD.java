
package lopicost.spd.iospd.importdata.process;

import lopicost.spd.iospd.IOSpdApi;
import lopicost.spd.model.DivisionResidencia;
import lopicost.spd.persistence.FicheroMedResiDAO;
import lopicost.spd.struts.bean.FicheroResiBean;
import lopicost.spd.utils.StringUtil;
import  lopicost.spd.utils.TextManager;

import java.sql.SQLException;
import java.util.TreeMap;
import java.util.Vector;

/**
 * Metodo encargado de importar el fichero recibido de la residencia, pero habiendo realizado nuestras sustituciones 
 * La finalidad es persistir los datos de SOLO_INFO y PASTILLERO del SPD junto con los NO_PINTAR, para poder compararlos
 * con las recetas y ver discrepancias.
 * El fichero ya viene "limpio" porque es el paso previo al env�o al robot
 * author CARLOS
 *
 */

public class ImportDatosResiSPD extends ImportProcessImpl
{
	TreeMap CIPSTratados =new TreeMap();
	boolean procesosAnterioresLimpiados = false;
	
	
	public ImportDatosResiSPD(){
		super();
	}

	/**los ficheros han de venir con cabecera. Se tendr� en cuenta a partir de la fila 2**/	
    protected boolean beforeProcesarEntrada(Vector row) throws Exception 
    {
    	//pasar a hist�rico los inactivos
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
    
    public boolean procesarEntrada(String idRobot, DivisionResidencia div, String idProceso, Vector row, int count, boolean cargaAnexa) throws Exception 
    {
		boolean result = false;
     
    	if (row!=null && row.size()>=15)
    	{        	
    		result = false;
    		String element = (String) row.elementAt(1);
    		if(!element.toUpperCase().contains("IDPROCESO"))	//nos aseguramos de saltar la cabecera
    			result=creaRegistro(row);
    	}
       else 
            throw new Exception (TextManager.getMensaje("ImportData.error.ImportDatosResiSPD"));
    	
    	return result;
    }
    
    private boolean creaRegistro(Vector row) throws Exception {
 
      	boolean result =true;
    	FicheroResiBean fila = new FicheroResiBean();

    	fila.setIdDivisionResidencia((String) row.elementAt(0));	
    	fila.setIdProceso((String) row.elementAt(1));	
    	fila.setResiCIP(StringUtil.replaceInvalidChars((String) row.elementAt(2)));	
    	fila.setResiNombrePaciente(StringUtil.replaceInvalidChars((String) row.elementAt(3)));	
    	fila.setResiCn(StringUtil.replaceInvalidChars((String) row.elementAt(4)));	
       	fila.setResiMedicamento(StringUtil.replaceInvalidChars((String) row.elementAt(5)));	
        fila.setResiSiPrecisa(StringUtil.replaceInvalidChars((String) row.elementAt(6)));	//
    	if(fila.getResiSiPrecisa()!=null && fila.getResiSiPrecisa().equalsIgnoreCase("x")) 
    		fila.setResiSiPrecisa("SI_PRECISA");
    	fila.setResiInicioTratamiento(
    			StringUtil.getStringFechaArreglada((String) row.elementAt(7))
    			); //conversi�n a formato DD/MM/YYYY
    	fila.setResiFinTratamiento(
    			StringUtil.getStringFechaArreglada((String) row.elementAt(8))
    			); //conversi�n a formato DD/MM/YYYY
    	fila.setResiObservaciones(StringUtil.replaceInvalidChars((String) row.elementAt(9)));
    	fila.setResiComentarios(StringUtil.replaceInvalidChars((String) row.elementAt(10)));
    	fila.setSpdCnFinal((String) row.elementAt(11));
    	fila.setSpdNombreBolsa((String) row.elementAt(12));	
    	fila.setSpdFormaMedicacion((String) row.elementAt(13));	
    	fila.setSpdAccionBolsa((String) row.elementAt(14));	
    	//fila.setSpdComentarioLopicost(StringUtil.replaceInvalidChars((String) row.elementAt(15)));	de momento no lo guardamos
    	
     	if(!CIPSTratados.containsKey(fila.getResiCIP()))
  		{
     		
        	ioSpdApi.limpiarCIPIdprocesoAnterior(fila.getIdProceso(), fila.getResiCIP());
            //Una vez limpiado, se a�ade como CIP ya tratado para no volver a limpiar datos
            CIPSTratados.put(fila.getResiCIP(), fila.getResiCIP());
            
  		}
    	/* Ahora lo hacemos CIP a CIP
     	if(!procesosAnterioresLimpiados) {
     		ioSpdApi.limpiarProcesoAnterioresResi(fila.getIdDivisionResidencia());
     		procesosAnterioresLimpiados=true;
     	}
     	*/
     		
     	ioSpdApi.compruebaTratamiento(getSpdUsuario(), fila);
     	try{
     		String idProceso = fila.getIdProceso();
     		int fechaInicioInt = new Integer(idProceso.substring(idProceso.length()-8, idProceso.length())).intValue();
     		int fechaFinInt = new Integer(idProceso.substring(idProceso.length()-17, idProceso.length()-9)).intValue();
     		fila.setFechaInicioInt(fechaInicioInt);
     		fila.setFechaFinInt(fechaFinInt);
       	}catch(Exception e)
     	{
     		
     	}
 		result=FicheroMedResiDAO.nuevo(fila);
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

}


