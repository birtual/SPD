package lopicost.spd.struts.action;

import org.apache.struts.action.*;
import org.apache.struts.actions.DispatchAction;
import javax.servlet.http.*;
import java.sql.SQLException;
import java.util.*;

import lopicost.spd.model.BdConsejo;
import lopicost.spd.persistence.*;


public class AutocompletarAction extends DispatchAction  
{
	private final String cLOGGERHEADER = "bdConsejoLookUpAction: ";
	private final String cLOGGERHEADER_ERROR = cLOGGERHEADER + "ERROR: bdConsejoLookUpAction";
	private List listaBdConsejo = null;
	BdConsejoDAO dao= new BdConsejoDAO();
   
	  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
	        // Obtener el término de búsqueda y el campo desde la solicitud
	        String term = request.getParameter("query");
	        String campo = request.getParameter("campo");
	        String selectedId = request.getParameter("selectedId");
	        // Lógica para obtener sugerencias basadas en el término de búsqueda y el campo
	        List<BdConsejo> sugerencias = obtenerSugerenciasDesdeBaseDeDatos(term, campo);

	        // Convertir la lista de sugerencias a formato JSON y escribir en la respuesta
	        response.setContentType("application/json");
	        response.getWriter().write(convertirResultadosAJSON(sugerencias));

	        return null; // Evitar el envío a una página de resultados
	    }
	  
    private List<BdConsejo> obtenerSugerenciasDesdeBaseDeDatos(String term, String campo) throws SQLException {
        List<BdConsejo> sugerencias = new ArrayList<BdConsejo>();
        
        boolean a = "filtroNomGtVm".equals(campo);

        if ("filtroNomGtVm".equalsIgnoreCase(campo)) {
            // Lógica para buscar en el filtroGtVm
        	sugerencias = BdConsejoDAO.getAutoGtVm(term);
        	
        }

        return sugerencias;
    }



    private String convertirResultadosAJSON(List<BdConsejo> resultados) {
        StringBuilder jsonResultados = new StringBuilder("[");

        for (int i = 0; i < resultados.size(); i++) {
            jsonResultados.append("{")
                           .append("\"text\":\"").append(resultados.get(i).getNomGtVm())
                           .append("\"}");

            // Agregar una coma si no es el último elemento
            if (i < resultados.size() - 1) {
                jsonResultados.append(",");
            }
        }

        jsonResultados.append("]");

        return jsonResultados.toString();
    }


}