package lopicost.spd.helper;

import lopicost.spd.persistence.CabecerasXLSDAO;
import lopicost.spd.struts.bean.CabecerasXLSBean;


/**
  * Logica de negocio 
 */
public class FicheroResiCabeceraHelper {

	private final String cLOGGERHEADER = "FicheroResiCabeceraHelper: ";
	private final String cLOGGERHEADER_ERROR = cLOGGERHEADER + "ERROR: ";

	public static CabecerasXLSBean getDesdeTomaPrimerDia(String idDivisionResidencia) throws Exception {
		return CabecerasXLSDAO.getDesdeTomaPrimerDia(idDivisionResidencia);
	}

	public static CabecerasXLSBean getHastaTomaUltimoDia(String idDivisionResidencia) throws Exception {
		return CabecerasXLSDAO.getHastaTomaUltimoDia(idDivisionResidencia);
	}

		
}
	


	
	
	
	
	
	
	
	
	