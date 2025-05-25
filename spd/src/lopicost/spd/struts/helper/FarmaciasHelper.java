package lopicost.spd.struts.helper;

import java.util.List;

import lopicost.spd.model.Farmacia;
import lopicost.spd.persistence.FarmaciaDAO;


/**
 
 *Logica de negocio 
 */
public class FarmaciasHelper  {


	private final static String cLOGGERHEADER = "FarmaciasHelper: ";
	private final String cLOGGERHEADER_ERROR = cLOGGERHEADER + "ERROR: ";





	public List<Farmacia> getFarmaciasPorUser(String idUsuario) throws Exception {
		
		return FarmaciaDAO.getFarmaciasPorUser(idUsuario);
	}

	public Farmacia getFarmaciaPorUserAndId(String idUsuario, String id) throws Exception {
		
		return FarmaciaDAO.getFarmaciaPorUserAndId(idUsuario, id);
	}
	


		


		
}
	


	
	
	
	
	
	
	
	
	