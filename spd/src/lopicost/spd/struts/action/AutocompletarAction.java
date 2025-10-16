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
	private final String cLOGGERHEADER = "AutocompletarAction: ";
	private final String cLOGGERHEADER_ERROR = cLOGGERHEADER + "ERROR: AutocompletarAction";
	private List listaBdConsejo = null;
	BdConsejoDAO dao= new BdConsejoDAO();
   
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        // Obtener los parámetros desde la solicitud
        String term = request.getParameter("query");
        String campo = request.getParameter("campo");
        String id = request.getParameter("id"); // El id de cada campo
        String paramAdicional = request.getParameter("paramAdicional");
        
        try {
            // lógica para obtener sugerencias basadas en el término de búsqueda y el campo
            List<BdConsejo> sugerencias = obtenerSugerenciasDesdeBaseDeDatos(term, campo, id, paramAdicional);

            // Convertir la lista de sugerencias a formato JSON y escribir en la respuesta
            response.setContentType("application/json");
            response.getWriter().write(convertirResultadosAJSON(sugerencias, campo, id));
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"Error al procesar la solicitud\"}");
        }

        return null; // Evitar el envío a una página de resultados
    }

	
    private List<BdConsejo> obtenerSugerenciasDesdeBaseDeDatos(String term, String campo, String id, String codGtVm) throws SQLException {
        List<BdConsejo> sugerencias = new ArrayList<>();

        // lógica para buscar en el campo correspondiente y usar el ID para determinar la búsqueda
        if ("filtroNomGtVm".equalsIgnoreCase(campo)) {
            sugerencias = BdConsejoDAO.getAutoGtVm(term);
        } else if ("filtroNombreLaboratorio".equalsIgnoreCase(campo)) {
            sugerencias = BdConsejoDAO.getAutoLabs(term, codGtVm);
        }

        return sugerencias;
    }



    private String convertirResultadosAJSON(List<BdConsejo> resultados, String campo, String id) {
        StringBuilder jsonResultados = new StringBuilder("[");

        for (int i = 0; i < resultados.size(); i++) {
            BdConsejo consejo = resultados.get(i);

            // Personalizar el texto según el campo y el id
            String ide = obtenerTextoPorCampoYId(campo, "id", consejo);
            String text = obtenerTextoPorCampoYId(campo, "nombre", consejo);
 
            jsonResultados.append("{")
                          .append("\"text\":\"").append(text)
                          .append("\",\"id\":\"").append(ide)
                          .append("\"}");

            if (i < resultados.size() - 1) {
                jsonResultados.append(",");
            }
        }

        jsonResultados.append("]");
        return jsonResultados.toString();
    }
	
    private String obtenerTextoPorCampoYId(String campo, String id, BdConsejo consejo) {
        // Dependiendo del campo y del id, retornamos el valor adecuado
        if ("filtroNomGtVm".equalsIgnoreCase(campo) && id.equalsIgnoreCase("id")) {
            return consejo.getCodGtVm(); // Usamos el nombre del GtVm
        } else if ("filtroNombreLaboratorio".equalsIgnoreCase(campo) && id.equalsIgnoreCase("id")) {
            return consejo.getCodigoLaboratorio(); // Usamos el nombre del laboratorio
        } else if  ("filtroNomGtVm".equalsIgnoreCase(campo) && id.equalsIgnoreCase("nombre")) {
            return consejo.getNomGtVm(); // Usamos el nombre del GtVm
        } else if ("filtroNombreLaboratorio".equalsIgnoreCase(campo) && id.equalsIgnoreCase("nombre")) {
            return consejo.getNombreLaboratorio(); // Usamos el nombre del laboratorio
        }
        return ""; // Valor por defecto si no se encuentra el campo o id
    }
}