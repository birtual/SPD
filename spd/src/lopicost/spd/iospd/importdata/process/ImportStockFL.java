
package lopicost.spd.iospd.importdata.process;

import lopicost.spd.controller.SpdLogAPI;
import lopicost.spd.model.BdConsejo;
import lopicost.spd.model.DivisionResidencia;
import lopicost.spd.model.GestSustituciones;
import lopicost.spd.model.GestSustitucionesXResi;
import lopicost.spd.model.farmacia.StockFL;
import lopicost.spd.persistence.BdConsejoDAO;
import lopicost.spd.persistence.DivisionResidenciaDAO;
import lopicost.spd.persistence.GestSustitucionesDAO;
import lopicost.spd.persistence.StockFL_DAO;
import lopicost.spd.struts.bean.FicheroResiBean;
import lopicost.spd.struts.form.BdConsejoForm;
import lopicost.spd.utils.DateUtilities;
import lopicost.spd.utils.StringUtil;
import  lopicost.spd.utils.TextManager;

import java.util.Date;
import java.util.TreeMap;
import java.util.Vector;

/**
 * Método encargado de importar el stock de Farmalogic para cruzarlos con los pedidos de farmacia
 * @author CARLOS
 *
 */

public class ImportStockFL extends ImportProcessImpl
{
		
	public ImportStockFL(){
		super();
	}

	/**los ficheros han de venir con cabecera. Se tendrá en cuenta a partir de la fila 2**/	
    protected boolean beforeProcesarEntrada(Vector row) throws Exception 
    {
    	return true;
    }
    protected void afterprocesarEntrada(Vector row) throws Exception 
    {
    	
    }
    
    public void procesarCabecera(Vector row) throws Exception 
    {
    }
     
    
    /*
      * @see lopicost.spd.iospd.importdata.process.ImportProcessImpl#procesarEntrada(java.lang.String, java.lang.String, java.util.Vector, int)
     */
    public void procesarEntrada(String idRobot, String idDivisionResidencia, String idProceso, Vector row, int count) throws Exception 
    {
        if (row!=null && row.size()>=11)
        {
     		boolean result = creaRegistro(row);
        
        }
        else 
            throw new Exception (TextManager.getMensaje("ImportData.error.ImportStockFL"));
    }
    
    private boolean creaRegistro(Vector row) throws Exception {

    	boolean result =true;
    	StockFL stock = new StockFL();

    	stock.setCnFL(StringUtil.limpiarTextoyEspacios((String) row.elementAt(0)));	
    	try
    	{
        	stock.setCn6(stock.getCnFL().substring(0, 6));
    	}
    	catch ( Exception e){
    		stock.setCn6(stock.getCnFL());
    	}
    	
    	stock.setNombreMedicamento((String) row.elementAt(1));		
    	stock.setPrincActivo((String) row.elementAt(2));		
     	stock.setProveedor((String) row.elementAt(3));							
     	stock.setExistencias((String) row.elementAt(4));	
     	stock.setLote((String) row.elementAt(5));	
     	stock.setCaducidad((String) row.elementAt(6));
     	stock.setFechaMaximaPedido((String) row.elementAt(7));
     	stock.setPvl_venta((String) row.elementAt(8));
     	stock.setDto_venta((String) row.elementAt(9));
     	stock.setPuv((String) row.elementAt(10));
    	stock.setAlmacen((String) row.elementAt(11));
    	     	
		//Miramos si existe en bbdd consejo 
    	BdConsejo bdConsejo = BdConsejoDAO.getByCN(stock.getCn6());
    	
 		if(bdConsejo!=null)
		{
			stock.setCodGtVm(bdConsejo.getCodGtVm());
			stock.setNomGtVm(bdConsejo.getNomGtVm());
			stock.setCodGtVmp(bdConsejo.getCodGtVmp());
			stock.setNomGtVmp(bdConsejo.getNomGtVmp());
			stock.setCodGtVmpp(bdConsejo.getCodGtVmpp());
			stock.setNomGtVmpp(bdConsejo.getNomGtVmpp());
			
		}
 		result=StockFL_DAO.nuevo(stock);
		if(!result)
		{
			throw new Exception ("No se ha podido crear el registro STOCK_FL");
			//errors.add( "Registro sust creado correctamente ");
		}

	    
	return result;
	}

	private String getStringFechaArreglada(String elementAt) {
		// TODO Auto-generated method stub
		return null;
	}

	protected boolean beforeStart(String filein) throws Exception 
    {
        boolean borrado = StockFL_DAO.eliminaTodosRegistros();
        return true;
    }

    protected void afterStart() 
    {
    }

	@Override
	protected void procesarEntrada(String idRobot, String idDivisionResidencia, String idProceso, Vector row, int count,
			boolean cargaAnexa) throws Exception {
		// TODO Esbozo de método generado automáticamente
		
	}

}



