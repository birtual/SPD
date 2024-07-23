
package lopicost.spd.iospd.importdata.process;

import lopicost.spd.controller.SpdLogAPI;
import lopicost.spd.model.BdConsejo;
import lopicost.spd.model.DivisionResidencia;
import lopicost.spd.model.GestSustituciones;
import lopicost.spd.model.GestSustitucionesXResi;
import lopicost.spd.model.SustXComposicion;
import lopicost.spd.model.farmacia.StockFL;
import lopicost.spd.persistence.BdConsejoDAO;
import lopicost.spd.persistence.DivisionResidenciaDAO;
import lopicost.spd.persistence.GestSustitucionesDAO;
import lopicost.spd.persistence.StockFL_DAO;
import lopicost.spd.persistence.SustXComposicionDAO;
import lopicost.spd.struts.bean.FicheroResiBean;
import lopicost.spd.struts.form.BdConsejoForm;
import lopicost.spd.utils.DataUtil;
import lopicost.spd.utils.DateUtilities;
import lopicost.spd.utils.StringUtil;
import  lopicost.spd.utils.TextManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;
import java.util.Vector;



/**
 * Método encargado de importar el stock de Farmalogic para cruzarlos con los pedidos de farmacia
 * @author CARLOS
 *
 */

public class ImportSustXComposicion extends ImportProcessImpl
{
	
	BdConsejo bdConsejoExcel = null;
	List<BdConsejo> bdConsejoGt = null;
	List<SustXComposicion> sustXCompAInsertar =new ArrayList<SustXComposicion>();	
	String idRobot ="";
	public ImportSustXComposicion(){
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
    private void inicializar() throws ClassNotFoundException {
		BdConsejo bdConsejoExcel = null;
		List<BdConsejo> bdConsejoGt = null;
		List<SustXComposicion> sustXCompAInsertar =new ArrayList<SustXComposicion>();	
		
	}     
    
    /*
      * @see lopicost.spd.iospd.importdata.process.ImportProcessImpl#procesarEntrada(java.lang.String, java.lang.String, java.util.Vector, int)
     */
    public void procesarEntrada(String idRobot, String idDivisionResidencia, String idProceso, Vector row, int count) throws Exception 
    {
    	if(count==1) {
    		this.idRobot=idRobot;
    		inicializar();
    	}
    	if(count>1)
    	{
        	if ( row!=null && row.size()>=1)   //con el count>1 nos saltamos la cabecera
            {
        		//inicialización variables
            	bdConsejoExcel = null;
            	bdConsejoGt = null;
            	sustXCompAInsertar =new ArrayList<SustXComposicion>();

            	SustXComposicion sustX = new SustXComposicion();
            	sustX.setIdRobot(idRobot);
            	this.idRobot=idRobot;

            	
            	
            	boolean result = creaRegistro(sustX, row);
            
            }
            else 
                throw new Exception (TextManager.getMensaje("ImportData.error.ImportSustXComposicion"));
    		
    	}
        
    }
    


	/**
     * Método que importa los datos de sustitución por composición para poder asignar los CN correctos en los pedidos de farmacia o de cara a las produciones del robot

     	Opción 1 - porCN - Si llega solo un CN. Se comprueba si es válido, si existe y se actualizan los datos según el CN recibido. 
    				Si además llega un LAB válido, tendrá prioridad sobre el CN que llega.
    				Si llega GTVMPP se pasa a "Opción 2"
    				Si no llega GTVMPP y llega GTVMP se pasa a "Opción 3"
    				
    	Opción 2 - porGTVMP - no llega GTVMPP (no es la opción 2). 
    							Si llega LAB se buscan todos los GTVMPP del LAB y se actualiza con lo que llega en listado (ponderación, comentarios, etc)
    							Si llega 
    					CN válido, se saca el Si que ha de llegar Se buscan si llega GTVMPP. 
    				Si existe GTVMPP se busca un CN en BdConsejo y se actualizan los datos	

    	Opción 3 - porGTVMPP - si llega CN se extrae Lab del CN
    							- si no llega CN es necesario LAB
    							- si no hay ninguno de los anteriores se descarta
    							- si llega GTMVP se tendrá en cuenta el GTVMPP
    	

    											
			  KEY	  CN GTVMP GTVMPP LAB		
				1000	1	0	0	0	Opción 1	Tenemos CN - Se busca LAB y GTVMPP del CN. Si CN no correcto se descarta
				1001	1	0	0	1	Opción 2	Tenemos CN y LAB - Se busca LAB y GTVMPP del CN. Si CN no correcto se descarta
				0101	0	1	0	1	Opción 3	Tenemos GTVMP y LAB - Se buscan los CN del LAB. Si LAB no correcto se descarta
				1100	1	1	0	0	Opción 4	Tenemos CN y GTVMP - Se buscan los otros CN del LAB que sale del CN. Si CN no correcto se descarta
				1101	1	1	0	1	Opción 5	Tenemos CN, GTVMP y LAB - Se buscan los CN del LAB. Si LAB no coincide se busca el LAB del CN. Si  CN no correcto se descarta
				0011	0	0	1	1	Opción 6	Tenemos GTVMPP y LAB - Se busca un CN del Lab recibido
				0111	0	1	1	1	Opción 7	Tenemos GTVMP, GTVMPP y LAB - Se busca un CN del Lab recibido, se descarta GTVMP. En caso que el LAB no coincida con el CN se busca el LAB del CN
				1010	1	0	1	0	Opción 8	Tenemos CN y GTVMPP - Se busca un LAB del CN recibido
				1110	1	1	1	0	Opción 9	Tenemos CN, GTVMP y GTVMPP - Se busca un LAB del CN recibido, se descarta GTVMP
				1011	1	0	1	1	Opción 10	Tenemos CN, GTVMPP y LAB - Se asigna el LAB recibido, en caso que el LAB no coincida con el CN se busca el LAB del CN
				1111	1	1	1	1	Opción 11	Tenemos CN, GTVMP, GTVMPP y LAB - Se asigna el LAB recibido, se descarta CN en caso que el LAB sea correcto. Si no se busca el LAB del CN. Se descarta GTVMP
				0010	0	0	1	0	Se descarta	Tenemos solo GTVMPP
				0100	0	1	0	0	Se descarta	Tenemos solo GTVMP	
				0110	0	1	1	0	Se descarta	Tenemos solo GTVMP y GTVMPP	
				0001	0	0	0	1	Se descarta	Tenemos solo LAB	


     * @param sustX
     * @param row
     * @return
     * @throws Exception
     */
    
    private boolean creaRegistro(SustXComposicion sustX, Vector row) throws Exception {


    	boolean result =true;
    	
    	sustX.setCn7(StringUtil.limpiarTextoyEspacios((String) row.elementAt(0)));
    	sustX.setCodiLab(StringUtil.limpiarTextoyEspacios((String) row.elementAt(9)));
    	sustX.setNombreLab((String) row.elementAt(10));
    	sustX.setCodGtVmp(StringUtil.limpiarTextoyEspacios((String) row.elementAt(2)));
    	sustX.setNomGtVmp((String) row.elementAt(3));
    	sustX.setCodGtVmpp(StringUtil.limpiarTextoyEspacios((String) row.elementAt(6)));
    	sustX.setNomGtVmpp((String) row.elementAt(7));
	
    //	boolean aplicarNivelGtvmp = false;
    	String aplicarNivelGtvmp ="NO";
    	try	{  aplicarNivelGtvmp = (String) row.elementAt(13);		
   		if( aplicarNivelGtvmp!=null && (aplicarNivelGtvmp.equalsIgnoreCase("X") || aplicarNivelGtvmp.equalsIgnoreCase("S") || aplicarNivelGtvmp.equalsIgnoreCase("SI"))) aplicarNivelGtvmp="SI";
   	}catch ( Exception e){}
 		if( aplicarNivelGtvmp==null || aplicarNivelGtvmp.equals("")) aplicarNivelGtvmp="NO";				
 		sustX.setAplicarNivelGtvmp(aplicarNivelGtvmp)  ;    
       	
    	String ponderacion ="100";
    	String rentabilidad ="0"; 
    	String sustituible ="SI";
    	String tolva ="NO";
    	
     	try	{  ponderacion = (String) row.elementAt(4);       	}catch ( Exception e){}
			if( ponderacion==null || ponderacion.equals("")) ponderacion="100"; 			sustX.setPonderacion(ponderacion)  ;  
			
     	try	{  rentabilidad = (String) row.elementAt(5);		}catch ( Exception e){}
     		if( rentabilidad==null || rentabilidad.equals("")) rentabilidad="0";			sustX.setRentabilidad(rentabilidad)  ;    	

       try	{  tolva = (String) row.elementAt(11);		
       		if( tolva !=null &&  !tolva.equals("") && ( tolva.equalsIgnoreCase("X") || tolva.equalsIgnoreCase("S") || tolva.equalsIgnoreCase("SI") ) ) tolva ="SI";
       }catch ( Exception e){}
     		if( tolva==null || tolva.equals("")) sustituible="NO";				sustX.setTolva(tolva)  ;    	

     	try	{  sustituible = (String) row.elementAt(12);	
     	if( sustituible !=null &&  !sustituible.equals("") && ( sustituible.equalsIgnoreCase("X") || sustituible.equalsIgnoreCase("S") || sustituible.equalsIgnoreCase("NO")  || tolva.equalsIgnoreCase("SI") ) ) sustituible ="NO";}catch ( Exception e){}
     		if( sustituible==null || sustituible.equals("")) sustituible="SI";				sustX.setSustituible(sustituible)  ;    	

	

		sustX.setComentarios((String) row.elementAt(8));
		
	   	String key = buscarKey(sustX, row);
    	sustX.setTipoCarga("carga masiva - KEY: " + key );
	    
	   	
	   	
    	if ("1000".equals(key) || 	//Opción 1 
    		"1001".equals(key) || 	//Opción 2
			"1010".equals(key) ||	//Opción 8
			"1110".equals(key) ||	//Opción 9 
			"1011".equals(key) ||	//Opción 10 
			"1111".equals(key) 		//Opción 11 
    		)
    	{
     		sustRecogerDatosDeConsejo(sustX, bdConsejoExcel);  
    		sustXCompAInsertar.add(sustX);
      	}
    	else if ("0101".equals(key)|| 	//Opción 3 
    			"1100".equals(key) || 	//Opción 4 
    			"1101".equals(key) ||	//Opción 5 
    			"0011".equals(key) ||	//Opción 6 
    			"0111".equals(key) 		//Opción 7 
   			)
    	{ 
        	Iterator it =(Iterator) bdConsejoGt.iterator();
    		while (it!=null && it.hasNext())
    		{
    			BdConsejo bdc = (BdConsejo)it.next();
    			SustXComposicion sustX2 = clonarSustX(sustX);
    			sustRecogerDatosDeConsejo(sustX2, bdc);  
    			sustXCompAInsertar.add(sustX2);
     		}
     		
      	} 	
    	
    	else if ("0010".equals(key)|| 	//Opción 12 
       			"0100".equals(key) || 	//Opción 13 
       			"0110".equals(key) || 	//Opción 14 
    			"0001".equals(key) 		//Opción 15 
    	 	)
    	{
    		errors.add("No se crea el registro de la fila " + getProcessedRows()  + " key >> " + key);
    	}
    
    	Iterator itSust =(Iterator) sustXCompAInsertar.iterator();
    	int i=0;
    	while (itSust!=null && itSust.hasNext())
		{
    		SustXComposicion sustBak = (SustXComposicion)itSust.next();
    		i++;
    		SustXComposicionDAO.borraAnteriores(sustBak);
    		
    		

     		
    		result = SustXComposicionDAO.nuevo(sustBak);
    		if(!result)
    			errors.add("No se crea el registro de la fila " + getProcessedRows()  + " key >> " + key);
 		}
		sustXCompAInsertar.clear();
    	
    	return result;
	}

	private SustXComposicion clonarSustX(SustXComposicion origen) {
		SustXComposicion clonado=new SustXComposicion();
		clonado.setIdRobot(origen.getIdRobot()); 
		clonado.setPonderacion(origen.getPonderacion());
		clonado.setRentabilidad(origen.getRentabilidad());
		clonado.setComentarios(origen.getComentarios());
		clonado.setTipoCarga(origen.getTipoCarga());
		clonado.setSustituible(origen.getSustituible());
		clonado.setSustituible(origen.getSustituible());
		clonado.setTolva("NO"); //a no ser que vengan explícito en el Excel de carga, no es tolva
		clonado.setAplicarNivelGtvmp(origen.getAplicarNivelGtvmp());
		
		
		return clonado;
	}

	private void sustRecogerDatosDeConsejo(SustXComposicion sust, BdConsejo bdc) {
		if(sust!=null && bdc!=null )
		{
			sust.setCn6(bdc.getCnConsejo());
			sust.setCn7((String.valueOf(DataUtil.getCN7(new Integer(bdc.getCnConsejo()).intValue()))));
			sust.setCodiLab(bdc.getCodigoLaboratorio());
			sust.setNombreLab(bdc.getNombreLaboratorio());
			sust.setNombreMedicamento(bdc.getNombreConsejo());
			sust.setCodGtVm(bdc.getCodGtVm());
 			sust.setNomGtVm(bdc.getNomGtVm());
 			sust.setCodGtVmp(bdc.getCodGtVmp());
 			sust.setNomGtVmp(bdc.getNomGtVmp());
 			sust.setCodGtVmpp(bdc.getCodGtVmpp());
			sust.setNomGtVmpp(bdc.getNomGtVmpp());
		}
	}


	private String buscarKey(SustXComposicion sust, Vector row) throws ClassNotFoundException, SQLException {
		String key ="";
		
		String cn = sust.getCn7();
		String codilab = sust.getCodiLab();
		String nombrelab = sust.getNombreLab();
		String codiGTVMP = sust.getCodGtVmp();
		String nomGTVMP =  sust.getNomGtVmp();
		String codiGTVMPP =  sust.getCodGtVmpp();
		String nomGTVMPP = sust.getNomGtVmpp();
		boolean aplicarNivelGtvmp = sust.getAplicarNivelGtvmp().equalsIgnoreCase("SI");
	
		/**
		* Opción 1
		*/
		if(cn!=null && !cn.equals("")) 
		{
			cn=getCN6(cn);
			bdConsejoExcel = BdConsejoDAO.getByCN(cn);
			
		}
		if(bdConsejoExcel!=null){
			key ="1";
			if(aplicarNivelGtvmp) codiGTVMP=bdConsejoExcel.getCodGtVmp();
		}
		else key="0";
			
		
		/**
		* Fin Opción 1
		*/
		//en caso que el CN sea válido, se extrae el LAB del CN
		if((codiGTVMP!=null && !codiGTVMP.equals("")) || (codiGTVMPP!=null && !codiGTVMPP.equals("")) || 
			(nomGTVMP!=null && !nomGTVMP.equals("")) || (nomGTVMPP!=null && !nomGTVMPP.equals("")) || 
			aplicarNivelGtvmp )
		{
			if(bdConsejoExcel!=null)
			{
				codilab=bdConsejoExcel.getCodigoLaboratorio();
				nombrelab=bdConsejoExcel.getNombreLaboratorio();
			}
			if(aplicarNivelGtvmp)
				bdConsejoGt = BdConsejoDAO.getBdConsejoByFilters(null, codilab, nombrelab, 0, 1000, null, codiGTVMP, null, null, nomGTVMP, null);
			else 
				bdConsejoGt = BdConsejoDAO.getBdConsejoByFilters(null, codilab, nombrelab, 0, 1000, null, codiGTVMP, codiGTVMPP, null, nomGTVMP, nomGTVMPP);
			
			
		}
		
		
		//if(bdConsejoGt!=null && bdConsejoGt.size()>0)
		{
			if((codiGTVMP!=null && !codiGTVMP.equals(""))|| (nomGTVMP!=null && !nomGTVMP.equals("")) || aplicarNivelGtvmp )
				key =key.concat("1") ;else key =key.concat("0");
			if((codiGTVMPP!=null && !codiGTVMPP.equals(""))|| (nomGTVMPP!=null && !nomGTVMPP.equals("")))
				key =key.concat("1") ;else key =key.concat("0");
			if((codilab!=null && !codilab.equals(""))|| (nombrelab!=null && !nombrelab.equals("")))
				key =key.concat("1") ;else key =key.concat("0");
		}
				
		System.out.println(" buscarKey --> key =" +key);
		
		return key;
	}

	private String getCN6(String cn) {
		String cn6=null;
		if(cn!=null && !cn.equals(""))
			if (cn.length()==6)
				cn6=cn;
			if (cn.length()>6)
				cn6=cn.substring(0,5);
		return cn6;
	}

	private String getStringFechaArreglada(String elementAt) {
		// TODO Auto-generated method stub
		return null;
	}

	protected boolean beforeStart(String filein) throws Exception 
    {
       // boolean borrado = SustXComposicionDAO.eliminaTodosRegistros(idRobot);
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



