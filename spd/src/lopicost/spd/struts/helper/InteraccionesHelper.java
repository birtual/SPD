package lopicost.spd.struts.helper;

import java.util.List;
import java.util.TreeMap;

import lopicost.spd.model.Interaccion;

import lopicost.spd.persistence.InteraccionesDAO;


/**
 
 *Logica de negocio 
 */
public class InteraccionesHelper  {


	private final static String cLOGGERHEADER = "InteraccionesHelper: ";
	private final String cLOGGERHEADER_ERROR = cLOGGERHEADER + "ERROR: ";





	public List<Interaccion> findListInteracciones(String idProceso, String CIP) throws Exception {
		
		return InteraccionesDAO.findListInteracciones(idProceso, CIP);
	}


	public TreeMap<String, Interaccion>  findTreeMapInteracciones(String idProceso, String CIP) throws Exception {
		
		return InteraccionesDAO.findTreeMapInteracciones(idProceso, CIP);
	}

		
}
	


	
	
	
	
	
	
	
	
	