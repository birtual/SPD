
package lopicost.spd.struts.helper;

import java.util.Iterator;
import java.util.List;
import lopicost.spd.persistence.CabecerasXLSDAO;
import lopicost.spd.persistence.FicheroResiDetalleDAO;
import lopicost.spd.struts.bean.CabecerasXLSBean;
import lopicost.spd.struts.bean.FicheroResiBean;


/**
 
 *Logica de negocio 
 */
public class CabecerasXLSHelper {


	private final static String cLOGGERHEADER = "CabecerasXLSHelper: ";
	private final String cLOGGERHEADER_ERROR = cLOGGERHEADER + "ERROR: CabecerasXLSHelper: ";


	/**
	 * 
	 * @param oidFicheroResiDetalle 
	 * @param spdUsuario
	 * @return
	 * @throws Exception
	 */
	public static boolean nuevaToma(String idUsuario, FicheroResiBean cab, CabecerasXLSBean nuevaToma) throws Exception {
		
		//creamos la toma en la plantilla
		boolean check = CabecerasXLSDAO.addNuevaToma( idUsuario, cab, nuevaToma);
		//si es ok, la actualizamos en la cabecera de la producción
		if(check)
		{
			//actualización de las líneas creadas en la importación 
			check = FicheroResiDetalleDAO.addTomaLineas(idUsuario, cab);		
			//actualización de la cabecera creada en la importación 
			check = FicheroResiDetalleDAO.addTomaCabecera(idUsuario, cab, nuevaToma.getHoraToma(), nuevaToma.getNombreToma());
		}
		return check;
	}


	public static boolean controlEdicion(int oidDivisionResidencia, int oidFicheroResiCabecera) throws Exception {
		
		return FicheroResiDetalleDAO.existeProcesosPosteriores(oidDivisionResidencia, oidFicheroResiCabecera);
		
	
	}


	public static boolean existeTomaPrevia(List tomasCabecera, String resiToma, String resiTomaLiteral) {

		if(resiToma==null || resiTomaLiteral==null || tomasCabecera==null || tomasCabecera.size()==0 )
			return true;
		try
		{
			Iterator it = tomasCabecera.iterator();
			while(it.hasNext())
			{
				CabecerasXLSBean toma = (CabecerasXLSBean) it.next();
				if(toma.getHoraToma().toUpperCase().equalsIgnoreCase(resiToma.toUpperCase()))
					return true;
				if(toma.getNombreToma().toUpperCase().equalsIgnoreCase(resiTomaLiteral.toUpperCase()))
					return true;
			}
		}
		catch(Exception e){
			
		}
		return false;
	}



	

	
		
}
	


	
	
	
	
	
	
	
	
	