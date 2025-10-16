package lopicost.spd.struts.action;

import org.apache.struts.action.*;
import org.apache.struts.actions.DispatchAction;
import javax.servlet.http.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.*;

import lopicost.spd.controller.SpdLogAPI;
import lopicost.spd.persistence.*;
import lopicost.spd.utils.SPDConstants;


public class DescargarArchivosAction extends GenericAction  
{
	private final String cLOGGERHEADER = "DescargarArchivosAction: ";
	private final String cLOGGERHEADER_ERROR = cLOGGERHEADER + "ERROR: DescargarArchivosAction";
   

	    public ActionForward descarga(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

	        // Definir los nombres de los archivos
	    	String fileName = request.getParameter("fileName");
	    	


	        // Definir la ruta donde se encuentran los archivos
	        String filePath = SPDConstants.PATH_DOCUMENTOS+"/robot/"; // Ruta completa en el servidor

	        // Crear las instancias de los archivos
	        File file = new File(filePath + File.separator + fileName);
	   
	        // Verificar que exista

	        if (file.exists()) {
	            // Configurar el tipo de contenido y las cabeceras para la descarga
	            response.setContentType("application/octet-stream");
	            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
	            response.setContentLength((int) file.length());

	            // Leer el archivo y enviarlo al flujo de salida
	            FileInputStream fileInputStream = new FileInputStream(file);
	            OutputStream  out = response.getOutputStream();

	            byte[] buffer = new byte[1024];
	            int bytesRead;
	            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
	                out.write(buffer, 0, bytesRead);
	            }

	            fileInputStream.close();
	            out.flush();
	            out.close();

	            /*
				//dejamos log
				try{
					SpdLogAPI.addLog(getIdUsuario(), "",  form.getIdDivisionResidencia(), cab.getIdProceso(), SpdLogAPI.A_PRODUCCION, SpdLogAPI.B_DESCARGAFICHERO, SpdLogAPI.C_FICHERO_ROBOT_UNIFICADA
							, "SpdLog.produccion.descarga.ficheroRobotUnificada", fileName );
				}catch(Exception e){}	//Creación del fichero Helium con nombre @@.
				*/
				
				
	            // Finalizar sin hacer un forward
	            return null;
	        } else {
	            // En caso de que el archivo no exista, redirigir a una página de error
	            return mapping.findForward("error");
	        }
	    }




}