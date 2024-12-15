package lopicost.spd.struts.helper;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.*;

import lopicost.spd.persistence.AvisosDAO;
import lopicost.spd.persistence.ExtReDAO;
import lopicost.spd.struts.bean.ExtReBean;

/**
 
 *Logica de negocio 
 */
public class ExtReHelper {


	private final static String cLOGGERHEADER = "ExtReHelper: ";
	private final String cLOGGERHEADER_ERROR = cLOGGERHEADER + "ERROR: ExtReHelper: ";


	/**
	 * 
	 * @param spdUsuario
	 * @return
	 * @throws Exception
	 */
	
	public static List getDatosProcesoCaptacion(String spdUsuario)throws Exception {
		return ExtReDAO.getDatosProcesoCaptacion(spdUsuario);
		}

	public static List getDatosProcesoCaptacionConErrores(String spdUsuario)throws Exception {
		List totales = ExtReDAO.getDatosProcesoCaptacion(spdUsuario);
		Iterator it = totales.iterator();
		List<ExtReBean> result = new ArrayList<ExtReBean>();
		
		while(it.hasNext())
		{
			ExtReBean bean = (ExtReBean) it.next();
			if(bean!=null && ( bean.isErrorDatosProcesadosRecPend() || bean.isErrorDatosProcesadosTrat() || bean.isErrorFechaRecogidaRecPend() || bean.isErrorFechaRecogidaTrat()))
				result.add(bean);
		}
		return result;
		
		}

	
	
	public static List getCipsSinProcesarTrat(String spdUsuario, String idDivisionResidencia)throws Exception {
		return ExtReDAO.getCipsSinProcesarTrat(spdUsuario, idDivisionResidencia);
		}
	
	public static List getCipsSinProcesarPendientes(String spdUsuario, String idDivisionResidencia)throws Exception {
		return ExtReDAO.getCipsSinProcesarPendientes(spdUsuario, idDivisionResidencia);
		}

	public static List getAvisosDeHoy(String idUsuario, int oidAviso, String idFarmacia, boolean actuales, Date fecha) throws Exception {
		return AvisosDAO.getAvisosDeHoy(idUsuario, oidAviso, idFarmacia, actuales, fecha );
	}


	
		
}
	


	
	
	
	
	
	
	
	
	