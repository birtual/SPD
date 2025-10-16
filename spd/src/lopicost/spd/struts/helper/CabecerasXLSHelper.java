
package lopicost.spd.struts.helper;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import lopicost.spd.persistence.CabecerasXLSDAO;
import lopicost.spd.persistence.FicheroResiDetalleDAO;
import lopicost.spd.struts.bean.CabecerasXLSBean;
import lopicost.spd.struts.bean.FicheroResiBean;
import lopicost.spd.struts.form.FicheroResiForm;
import lopicost.spd.utils.StringUtil;


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


	public static void marcarEdicionNombreToma(List<CabecerasXLSBean> tomasCabecera, String idToma) {
    	for  (CabecerasXLSBean toma : tomasCabecera) {
    	    if (toma.getIdToma().equals(idToma)) {  
    	        toma.setEdicionNombreToma(true);
    	        break; 
    	    }
    	}
	}


	public static void marcarEdicionHoraToma(List<CabecerasXLSBean> tomasCabecera, String idToma) {
    	for  (CabecerasXLSBean toma : tomasCabecera) {
    	    if (toma.getIdToma().equals(idToma)) {  
    	        toma.setEdicionHoraToma(true);
    	        break; 
    	    }
    	}
	}

	public static CabecerasXLSBean getByIdToma(List<CabecerasXLSBean> tomasCabecera, String idToma) {
    	for  (CabecerasXLSBean toma : tomasCabecera) {
    	    if (toma.getIdToma().equals(idToma)) {  
    	        return toma;
    	    }
    	}
		return null;
	}


	public static CabecerasXLSBean editarToma(String idUsuario, CabecerasXLSBean tomaAntigua, CabecerasXLSBean tomaNueva, FicheroResiForm formulari, FicheroResiBean cab) throws Exception {
		
		String horaToma=formulari.getHoraToma(); 
		
		//String nombreToma=StringUtil.limpiarTextoEspaciosYAcentos(formulari.getNombreToma(), false	);
		String nombreToma=StringUtil.limpiarTexto(formulari.getNombreToma());
		
			 
		boolean result = CabecerasXLSDAO.editarToma( idUsuario, tomaAntigua, tomaNueva, horaToma, nombreToma);

		if(result)
		{
			if(horaToma!=null && !horaToma.equals(""))
			{
				tomaNueva.setHoraToma(horaToma);
				tomaNueva.setIdToma(getIdToma(horaToma, tomaNueva.getPosicionEnBBDD()));
			}
			if(nombreToma!=null && !nombreToma.equals(""))
				tomaNueva.setNombreToma(nombreToma);
			
			//actualizamos el proceso en curso
			if(tomaNueva!=null)
			{
		   		//actualización de la cabecera creada en la importación del proceso en el que hemos modificado la toma, que es el último
		  		boolean resultEdicion = FicheroResiDetalleDAO.modificaCabeceraTomaDelProceso(idUsuario, tomaAntigua, tomaNueva, cab.getIdProceso());
		  		//si no se realiza ok, se ha de invertir el proceso para dejarlo como antes de la edición y devolver null
		  		if(!resultEdicion)
		 		{
		  			CabecerasXLSDAO.editarToma( idUsuario, tomaNueva, tomaAntigua, horaToma, nombreToma);
		  			return null;
		  		}
		  	}
				
			
			return tomaNueva;
		}
		else return null;

	}

	/**
	 * Devuelve el ID de la toma, que acostumbra ser 4 dígitos (la hora sin los :)
	 * @param cadena
	 * @param posicion
	 * @return
	 */
	public static String getIdToma(String cadena, int posicion) {
		String result = getHoraToma(cadena, posicion);
		if(result!=null && !result.equals("")) 
			result=result.replace(":", "");
		
		return result;
	}

	
	


	/**
	 * Por defecto devuelve que es una toma BASE (de las que hay en Excel de la resi. En caso de superar 6 se devuelve como si fuera Extra
	 * @param i
	 * @return
	 */
	public static String getBase(int i) {
		String result = "BASE";
		if(i>6) result="EXTRA";
		return result;
	}


	/**
	 * A partir de lo que se devuelva en la select, se construye el resultado. En caso de ser una hora, se devuelve tal cual. En caso de ser un texto que no sigue 
	 * formato XX:XX se construye una hora a partir de la posición que ocupa, devolviendo formato XX:XX  
	 * @param cadena
	 * @param posicion
	 * @return
	 */
	public static String getHoraToma(String cadena, int posicion) {
		String result="";
		String formatoExpresionRegular = "\\d{2}:\\d{2}";
		System.out.println(cadena.matches(formatoExpresionRegular));
		 if (cadena.matches(formatoExpresionRegular)) {
			 result =cadena;
	        } else {
	        	 if (posicion < 1 || posicion > 99) {
	        		 throw new IllegalArgumentException("El número debe estar en el rango de 1 a 99.");
	             }
	        	 result = String.format("%02d:00", posicion, 0);
	        }
		return result;
		
	}


	
		
}
	


	
	
	
	
	
	
	
	
	