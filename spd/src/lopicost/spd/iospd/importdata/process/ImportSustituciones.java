
package lopicost.spd.iospd.importdata.process;

import lopicost.spd.controller.SpdLogAPI;
import lopicost.spd.model.BdConsejo;
import lopicost.spd.model.DivisionResidencia;
import lopicost.spd.model.GestSustituciones;
import lopicost.spd.model.GestSustitucionesXResi;
import lopicost.spd.persistence.BdConsejoDAO;
import lopicost.spd.persistence.DivisionResidenciaDAO;
import lopicost.spd.persistence.GestSustitucionesDAO;
import lopicost.spd.struts.bean.FicheroResiBean;
import lopicost.spd.struts.form.BdConsejoForm;
import lopicost.spd.utils.DateUtilities;
import lopicost.spd.utils.StringUtil;
import  lopicost.spd.utils.TextManager;

import java.util.Date;
import java.util.TreeMap;
import java.util.Vector;

/**
 * Método encargado de importar registros de sustituciones y sustXResi, 
 * Todos los registros del listado
 *
 */

public class ImportSustituciones extends ImportProcessImpl
{
	String CIPanterior="";
	TreeMap CIPSTratados =new TreeMap();
	String spdUsuario="";
	
	public ImportSustituciones(){
		super();
	}

	/**los ficheros han de venir con cabecera. Se tendr� en cuenta a partir de la fila 2**/	
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
     * Las Onadas llegan con 5 pautas de tomas--> desayuno / comida / merienda / cena /resop�n
     * (non-Javadoc)
     * @see lopicost.spd.iospd.importdata.process.ImportProcessImpl#procesarEntrada(java.lang.String, java.lang.String, java.util.Vector, int)
     */
    //public void procesarEntrada(String idRobot, String idDivisionResidencia, String idProceso, Vector row, int count) throws Exception 
    
    
    public boolean procesarEntrada(String idRobot, DivisionResidencia div, String idProceso, Vector row, int count, boolean cargaAnexa) throws Exception
    {
    	boolean result = false;
        if (row!=null && row.size()>=8)
        {
    		FicheroResiBean medResi= new FicheroResiBean();
    		result = creaRegistro(row);
        
        }
        else 
            throw new Exception (TextManager.getMensaje("ImportData.error.ImportSustituciones"));
        
        return result;
    }
    
    private boolean creaRegistro(Vector row) throws Exception {

    	GestSustituciones sust = new GestSustituciones();
    	GestSustituciones ultimoSustCreado = new GestSustituciones();
    	boolean resultSust =true;
    	boolean resultSustXResi=true;

	    sust.setCnResi(StringUtil.limpiarTextoyEspacios((String) row.elementAt(0)));		//cnResi
    	sust.setMedicamentoResi(StringUtil.limpiarTexto((String) row.elementAt(1)));		//medicamentoResi
    	sust.setCnOk(StringUtil.limpiarTextoyEspacios((String) row.elementAt(2)));			//cnOk
    	sust.setNombreCorto((String) row.elementAt(3));										//nombreBolsa
       	BdConsejo consejo = BdConsejoDAO.getBdConsejobyCN(sust.getCnOk());
       	sust.setNombreMedicamentoOk(consejo!=null?consejo.getNombreConsejo():(String) row.elementAt(3));						// BdConsejo o nombreBolsa
       	sust.setFormaFarmaceuticaSustitucion(consejo!=null?consejo.getNombreFormaFarmaceutica():(String) row.elementAt(4));		// BdConsejo o FORMA Excel
		sust.setAccion((String) row.elementAt(5));											//acci�n
		sust.setComentario((String) row.elementAt(6));										//comentario
		String sustituible = (String) row.elementAt(7);
		sust.setSustituible(sustituible!=null && sustituible.equals("NO")?"0":"1");
		
		String codigoConjuntoHomogeneo = consejo!=null && consejo.getCodGtVmpp()!=null && !consejo.getCodGtVmpp().equals("")?consejo.getCodGtVmpp():"";
		String nombreConjuntoHomogeneo = consejo!=null && consejo.getNomGtVmpp()!=null && !consejo.getNomGtVmpp().equals("")?consejo.getNomGtVmpp():"";
		sust.setCodGtVmppOk(codigoConjuntoHomogeneo);	
		sust.setNomGtVmppOk(nombreConjuntoHomogeneo);	
		 //creamos en bbdd
    	//primero SustRaiz
	    if(sust!=null 
	    			&& sust.getAccion()!=null 
	    			&& ( sust.getAccion().equalsIgnoreCase("NO_PINTAR") 
	    					|| sust.getAccion().equalsIgnoreCase("SOLO_INFO") 
	    					|| sust.getAccion().equalsIgnoreCase("PASTILLERO"))
	    			)
	    {
	    	resultSust=GestSustitucionesDAO.nuevo(sust);
			ultimoSustCreado = GestSustitucionesDAO.getSustitucionByOid(spdUsuario, GestSustitucionesDAO.getUltimoOid(spdUsuario, sust));
	    }
		
		//Miramos la SustXResi 
    	//si existe idDivisionResidencia
		String idDivResi = (String) row.elementAt(8);
		if(idDivResi!=null && !idDivResi.equals("")&&ultimoSustCreado!=null&&ultimoSustCreado.getOidGestSustituciones()>0)
		{
    		GestSustitucionesXResi sustXResi = new GestSustitucionesXResi();
			DivisionResidencia  divResi = DivisionResidenciaDAO.getDivisionResidenciaById(spdUsuario, idDivResi);
	    	if(divResi!=null && divResi.getIdDivisionResidencia()!=null )
	    	{
	    		sustXResi.setCnOkSustXResi(sust.getCnOk());
	    		sustXResi.setNombreMedicamentoOkSustXResi(sust.getNombreMedicamentoOk());
	        	sustXResi.setNombreCortoSustXResi(sust.getNombreCorto());						//nombreBolsa
	        	sustXResi.setCodGtVmppOkSustXResi(sust.getCodGtVmppOk());
	        	sustXResi.setNomGtVmppOkSustXResi(sust.getNomGtVmppOk());
	    		sustXResi.setOidDivisionResidencia(new Integer(divResi.getOidDivisionResidencia()).intValue());			//acci�nSustXResi
	    		sustXResi.setIdDivisionResidencia(divResi.getIdDivisionResidencia());			//acci�nSustXResi
	    		sustXResi.setAccionSustXResi((String) row.elementAt(9));
	    		sustXResi.setComentarioSustXResi((String) row.elementAt(10));
	    		String sustituibleXResi = (String) row.elementAt(11);
	    		sustXResi.setSustituibleXResi(sustituibleXResi!=null && sustituibleXResi.equals("NO")?"0":"1");
	    	}
	    
	
	 		if(sustXResi!=null 
		    			&& sustXResi.getAccionSustXResi()!=null 
		    			&& ( sustXResi.getAccionSustXResi().equalsIgnoreCase("NO_PINTAR") 
		    					|| sustXResi.getAccionSustXResi().equalsIgnoreCase("SOLO_INFO") 
		    					|| sustXResi.getAccionSustXResi().equalsIgnoreCase("PASTILLERO"))
		    			)
				 {
				 	sustXResi.setOidGestSustituciones(ultimoSustCreado.getOidGestSustituciones());
				 	resultSustXResi=GestSustitucionesDAO.nuevoSustXResi(sustXResi);
					if(!resultSustXResi)
					{
						throw new Exception ("No se ha podido crear el registro sustXResi");
						//errors.add( "Registro sustXResi creado correctamente ");
					}
					//else errors.add( "No se ha podido crear el registro sustXResi");
				 }
		
				if(!resultSust)
				{
					throw new Exception ("No se ha podido crear el registro sust");
					//errors.add( "Registro sust creado correctamente ");
				}
				//else errors.add( "No se ha podido crear el registro sust");
	    }

	return resultSust;
	}

	private String getStringFechaArreglada(String elementAt) {
		// TODO Auto-generated method stub
		return null;
	}

	protected boolean beforeStart(String filein) throws Exception 
    {
 //   	ioSpdApi.eliminaRegistrosCIPIdproceso(this.);
        return true;
    }

    protected void afterStart() 
    {
    }


    
}    


