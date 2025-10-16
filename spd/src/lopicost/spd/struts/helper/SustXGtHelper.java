package lopicost.spd.struts.helper;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.TreeMap;

import org.hsql.util.Tree;

import lopicost.spd.controller.SpdLogAPI;
import lopicost.spd.model.Aviso;
import lopicost.spd.model.Nivel1;
import lopicost.spd.model.SpdLog;
import lopicost.spd.model.Nivel2;
import lopicost.spd.model.Nivel3;
import lopicost.spd.persistence.AvisosDAO;
import lopicost.spd.persistence.PacienteDAO;
import lopicost.spd.persistence.SustXGtDAO;
import lopicost.spd.struts.bean.PacienteBean;
import lopicost.spd.struts.form.AvisosForm;
import lopicost.spd.struts.form.PacientesForm;
import lopicost.spd.struts.form.SustXGtForm;
import lopicost.spd.utils.StringUtil;

public class SustXGtHelper {


/**
 * Método que crea o actualiza los GTVMPP de un GTVMP determinado.
 * Se recuperan todos los GTVMPP hijos  
 * En caso que existan se actualiza la rentabilidad de cada uno
 * En caso que no existan, se crearán con la rentabilidad del form 
 * @param idUsuario
 * @param f
 * @return
 * @throws ClassNotFoundException
 * @throws SQLException
 */
	public static boolean gestionarNivel(String idUsuario, SustXGtForm formulari) throws ClassNotFoundException, SQLException {

		boolean result = false;
		String filtroCodigoLaboratorio=formulari.getFiltroCodiLaboratorio();
		String labNuevo = "";
		
		//recuperamos los GtVm con todos los GTVMP y sus GTVMPP 
		List<Nivel1> listaNivel1 = SustXGtDAO.getDesdeNivel(formulari, 0, 1000000); 
		for (Nivel1 nivel1 : listaNivel1) {
			List<Nivel2> listaNivel2 = nivel1.getListaNivel2();

			//recuperamos los GTVMP y sus GTVMPP 
			for (Nivel2 nivel2 : listaNivel2) {
				List<Nivel3> listaNivel3 = nivel2.getListaNivel3();
	
				//recuperamos sus GTVMPP 
				for (Nivel3 nivel3 : listaNivel3) {
					labNuevo = nivel3.getCodLaboratorio(); 
					if(nivel3.getCodLaboratorio()==null || nivel3.getCodLaboratorio().equals("") )
					{
						boolean existeConLab = SustXGtDAO.existeHermanoConLab(nivel3, formulari);
						if(!existeConLab)
						{					
							//SustXGtDAO.creaHijo(hijo, formulari);
							nivel3.setIdRobot("");
							nivel3.setNombreRobot("");
							nivel3.setRentabilidad(formulari.getRentabilidad());
							nivel3.setCodLaboratorio(formulari.getFiltroCodiLaboratorio());
							nivel3.setNomLaboratorio(formulari.getFiltroNombreLaboratorio());
							result=SustXGtDAO.nuevoSustXGtHijo(nivel2, nivel3);
						}
						
					}
					else if(nivel3.getCodLaboratorio()!=null && nivel3.getCodLaboratorio().equals(filtroCodigoLaboratorio) )
					{
						result=SustXGtDAO.actualizaRentabilidadHijoPorOid(nivel3, formulari);
					}
						
				}
				
			}
			
		}
		

			if(result)
		{
			//INICIO creación de log en BBDD
			try{
				SpdLogAPI.addLog(idUsuario, null,  null, null, SpdLogAPI.A_RENTABILIDAD, SpdLogAPI.B_CREACION, "", "SpdLog.rentabilidad.creado.general", 
						formulari.getFiltroCodGtVm() + "_" + formulari.getFiltroCodGtVmp() );
			}catch(Exception e){}	// Cambios--> @@.
			//FIN creación de log en BBDD
			
		}

		
		return result;
	}


}
