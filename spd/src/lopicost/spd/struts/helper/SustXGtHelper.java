package lopicost.spd.struts.helper;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.TreeMap;

import org.hsql.util.Tree;

import lopicost.spd.controller.SpdLogAPI;
import lopicost.spd.model.Aviso;
import lopicost.spd.model.SpdLog;
import lopicost.spd.model.SustXGtvmp;
import lopicost.spd.model.SustXGtvmpp;
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
	public static boolean nuevoGtVmp(String idUsuario, SustXGtForm formulari) throws ClassNotFoundException, SQLException {

		//actualizamos en caso que exista algún GTVMPP con el lab seleccionado
		boolean result = SustXGtDAO.updateRentabilidadLab(formulari);
		
		//recuperamos el GtVmp con todos los GTVMPP 
		//SustXGtvmp gtVmpp = SustXGtDAO.getPadreByOid(formulari.getOidSustXComposicion());
		List<SustXGtvmp> listaSustXGtvmp = SustXGtDAO.getSustXGtvmp(formulari, 1, 1000000); //aquí debería llevar el oidSustXComposicion
		SustXGtvmp padre = (listaSustXGtvmp!=null && listaSustXGtvmp.size()>0?listaSustXGtvmp.get(0):null);
		List<SustXGtvmpp> hijos=null; 
		if(padre!=null)
			hijos=padre.getHijos();
		
		TreeMap tree = new TreeMap<String, SustXGtvmpp>();
		Iterator<SustXGtvmpp> it = hijos.iterator();
		String key = "";
		while (it.hasNext())
		{
			SustXGtvmpp hijo = it.next();
			key = hijo.getCodLaboratorio() + "-" + hijo.getRentabilidad(); 
			if(tree.containsKey(key)) //si ya existe uno previo, quiere decir que ya se ha tratado con el update anterior y podemos pasar al siguiente
				continue;
			else  tree.put(key, hijo);	//si  no existe lo ponemos en el treemap y lo tratamos
			
			hijo.setIdRobot("");
			hijo.setNombreRobot("");
			hijo.setRentabilidad(formulari.getRentabilidad());
			hijo.setCodLaboratorio(formulari.getFiltroCodiLaboratorio());
			hijo.setNomLaboratorio(formulari.getFiltroNombreLaboratorio());
			
			result = SustXGtDAO.nuevoSustXGtHijo(hijo);
		}
			
		
		if(result)
		{
			//INICIO creación de log en BBDD
			try{
				SpdLogAPI.addLog(idUsuario, null,  null, null, SpdLogAPI.A_RENTABILIDAD, SpdLogAPI.B_CREACION, "", "SpdLog.rentabilidad.creado.general", 
						padre.getCodGtvmp() );
			}catch(Exception e){}	// Cambios--> @@.
			//FIN creación de log en BBDD
			
		}

		
		return result;
	}
}
