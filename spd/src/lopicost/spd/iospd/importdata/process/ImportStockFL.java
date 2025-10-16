
package lopicost.spd.iospd.importdata.process;

import lopicost.spd.model.BdConsejo;
import lopicost.spd.model.DivisionResidencia;
import lopicost.spd.model.farmacia.StockFL;
import lopicost.spd.persistence.BdConsejoDAO;
import lopicost.spd.persistence.StockFL_DAO;
import lopicost.spd.utils.StringUtil;
import  lopicost.spd.utils.TextManager;


import java.util.Vector;

/**
 * Método encargado de importar el stock de Farmalogic, con stock mínimo de farmacias) 
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
    public boolean procesarEntrada(String idRobot, DivisionResidencia div, String idProceso, Vector row, int count, boolean cargaAnexa) throws Exception 
    {
    	boolean result = false;
        if (row!=null && row.size()>=2)
        {
     		result = creaRegistro(row);
        }
        else 
            throw new Exception (TextManager.getMensaje("ImportData.error.ImportStockFL"));
        return result;
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
    		stock.setCn6(stock.getCn6());
    	}
		//Miramos si existe en bbdd consejo 
    	BdConsejo bdConsejo = BdConsejoDAO.getByCN(stock.getCn6());
    	
 		if(bdConsejo!=null)
		{
		   	stock.setNombreMedicamento(bdConsejo.getNombreConsejo());
	     	stock.setCodigoLaboratorio(bdConsejo.getCodigoLaboratorio());							
	     	stock.setNombreLaboratorio(bdConsejo.getNombreLaboratorio());							
			stock.setCodGtVmpp(bdConsejo.getCodGtVmpp());
			stock.setNomGtVmpp(bdConsejo.getNomGtVmpp());
	     	try{
	     		stock.setPvl( String.valueOf(bdConsejo.getPvl()));
	     	}
	     	catch(Exception e){}
	     	stock.setStockMinimo((String) row.elementAt(1));	
	     	stock.setExistencias((String) row.elementAt(2));	
	     	try{
	     		stock.setDto_venta((String) row.elementAt(3));
	     	}
	     	catch(Exception e){}
	     	
			StockFL stockPrevio = StockFL_DAO.getbyCN(stock.getCnFL());
			if(stockPrevio!=null) StockFL_DAO.eliminaStockFL(stock.getCnFL());
	 		result=StockFL_DAO.nuevo(stock);

	 		
	 		
		}

 		

		/*stock.setCodGtVm(bdConsejo.getCodGtVm());
	   	//stock.setPrincActivo((String) row.elementAt(2));		
		stock.setNomGtVm(bdConsejo.getNomGtVm());
		stock.setCodGtVmp(bdConsejo.getCodGtVmp());
		stock.setNomGtVmp(bdConsejo.getNomGtVmp());
	   	stock.setCodGtVm(bdConsejo.getCodGtVm());
	   	stock.setCodGtVmp(bdConsejo.getCodGtVmp());
	   	stock.setCodGtVmpp(bdConsejo.getCodGtVmpp());
	   	stock.setNomGtVm(bdConsejo.getNomGtVm());
	   	stock.setNomGtVmp(bdConsejo.getNomGtVmp());
	   	stock.setNomGtVmpp(bdConsejo.getNomGtVmpp());
    	stock.setAlmacen((String) row.elementAt(3));
     	stock.setLote((String) row.elementAt(5));	
     	stock.setCaducidad((String) row.elementAt(6));
     	stock.setFechaMaximaPedido((String) row.elementAt(7));*/


 		
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
        //boolean borrado = StockFL_DAO.eliminaTodosRegistros();
        return true;
    }

    protected void afterStart() 
    {
    }



}



