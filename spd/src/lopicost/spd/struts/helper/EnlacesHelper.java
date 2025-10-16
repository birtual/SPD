package lopicost.spd.struts.helper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import lopicost.spd.model.Enlace;
import lopicost.spd.model.Usuario;
import lopicost.spd.persistence.MenuDAO;


/**
 
 *Logica de negocio 
 */
public class EnlacesHelper {


	private final static String cLOGGERHEADER = "EnlacesHelper: ";
	private final String cLOGGERHEADER_ERROR = cLOGGERHEADER + "ERROR: EnlacesHelper: ";



	public static List listadoPorApartados(Usuario user, List menu)throws Exception {
		List result=new ArrayList();
		String apartadoAnterior=null;
		Iterator it =menu.iterator();
		List seccion = new ArrayList<Enlace>();
		result.add(seccion);
		while(it.hasNext())
		{
			Enlace enlace = (Enlace) it.next();
			String apartado = enlace.getIdApartado();
			if(apartadoAnterior!=null && !apartado.equals(apartadoAnterior))
			{
				seccion = new ArrayList<Enlace>();
				result.add(seccion);
			}

				seccion.add(enlace);
				apartadoAnterior=apartado;

				
		}
		
		return result;
		}



	public static boolean checkBorrable(Enlace enlace) throws ClassNotFoundException, SQLException {
		int cont = MenuDAO.countByIdEnlace(enlace.getIdEnlace());
		if(cont>0) return false;
		return true;
	}


/*
	public static String remplazaTexto(String idApartado) {
		  String resultado = "-";
    	
		  if (idApartado.equalsIgnoreCase("1_pacientes")) {
    		resultado = "Pacientes";
	    } else if (idApartado.equalsIgnoreCase("2_bolquers")) {
	    	resultado = "Pañales";
	    } else if (idApartado.equalsIgnoreCase("3_recetas")) {
	    	resultado = "Tratamientos";
	    } else if (idApartado.equalsIgnoreCase("4_pedidos")) {
	    	resultado = "Pedidos";
	    } else if (idApartado.equalsIgnoreCase("5_producciones")) {
	    	resultado = "Producciones";
	    } else if (idApartado.equalsIgnoreCase("7_admin")) {
	    	resultado = "Admin";
	    } else if (idApartado.equalsIgnoreCase("8_admin_test")) {
	    	resultado = "AdminTest";
	    } else  if (idApartado.equalsIgnoreCase("9_cajon")) {
	    	resultado = "Otros";
	    }

    return resultado;
		
	}
	*/



	
		
}
	


	
	
	
	
	
	
	
	
	