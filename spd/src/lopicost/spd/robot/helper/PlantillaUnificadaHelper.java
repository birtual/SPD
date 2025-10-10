
package lopicost.spd.robot.helper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import java.util.TreeMap;

import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;


import lopicost.spd.model.DivisionResidencia;
import lopicost.spd.persistence.XMLRobotDao;
import lopicost.spd.robot.bean.DetallesTomasBean;
import lopicost.spd.robot.model.Bottle;
import lopicost.spd.robot.model.DrugDM;
import lopicost.spd.robot.model.DrugRX;
import lopicost.spd.robot.model.FiliaDM;
import lopicost.spd.robot.model.FiliaRX;
import lopicost.spd.robot.model.TomasOrdenadas;
import lopicost.spd.robot.model.Print;
import lopicost.spd.struts.bean.FicheroResiBean;
import lopicost.spd.struts.bean.PacienteBean;
import lopicost.spd.utils.SPDConstants;
import lopicost.spd.utils.StringUtil;


/**
 
 *Logica de negocio 
 */
public class PlantillaUnificadaHelper {


	private final String cLOGGERHEADER = "PlantillaUnificadaHelper: ";
	private final String cLOGGERHEADER_ERROR = cLOGGERHEADER + "ERROR: PlantillaUnificadaHelper: ";
	static TreeMap Doses_TreeMap =new TreeMap();

	//Paso1
/*	public static boolean borraProceso(String idUsuario, FicheroResiBean cab) throws SQLException, ParseException, ClassNotFoundException {
		return XMLRobotDao.borraProceso(idUsuario,  cab);
	}
*/	
	public static boolean bloqueaProcesoResidencia(String idUsuario, FicheroResiBean cab) throws ClassNotFoundException, SQLException {
		//limpiamos procesos colgados de más de 30 minutos
		XMLRobotDao.limpiarBloqueosAntiguos(idUsuario);
		//comprobamos que existe registro
		if(!XMLRobotDao.existeRegitroBloqueo(idUsuario,  cab, false))
			XMLRobotDao.crearRegistroBloqueo(idUsuario,  cab);  //creamos
		return XMLRobotDao.bloqueaProcesoResidencia(idUsuario,  cab); //bloqueo
	}
	
	public static String consultaFechaProcesoBloqueado(String idUsuario, FicheroResiBean cab) throws SQLException {
		return XMLRobotDao.consultaFechaProcesoBloqueado(idUsuario,  cab);
	}
	
	public static boolean desBloqueaProcesoResidencia(String idUsuario, FicheroResiBean cab) throws ClassNotFoundException, SQLException {
		return XMLRobotDao.desBloqueaProcesoResidencia(idUsuario,  cab);
	}

	

	public static boolean borraProcesosResidencia(String idUsuario, FicheroResiBean cab, PacienteBean pac) throws SQLException, ParseException, ClassNotFoundException {
		return XMLRobotDao.borraProcesosResidencia(idUsuario,  cab, pac);
	}
	//Paso2
	public static TomasOrdenadas getTomasOrdenadas(String idUsuario, FicheroResiBean cab) throws SQLException {
		return XMLRobotDao.getTomasOrdenadas(idUsuario,  cab);
	}
	
	//Paso3
	public static List<DetallesTomasBean> getDetalleTomasRobot(String idUsuario, FicheroResiBean cabDetalle, TomasOrdenadas tomasGlobal, PacienteBean pac) throws Exception {
		return XMLRobotDao.getDetalleTomasRobot(idUsuario,  cabDetalle, tomasGlobal, pac);
	}
	
	//Paso5
	public static boolean procesarExcepciones(String idUsuario, FicheroResiBean cabGlobal, FicheroResiBean cabDetalle) throws Exception {
		 return  XMLRobotDao.procesarExcepciones(idUsuario,  cabGlobal, cabDetalle);
    }
	
	//Paso7  NO FIABLE porque al eliminar se juntan datos de los procesos concurrentes, con lineasRX iguales
	/*
	 * public static boolean eliminarDuplicadosRX(String idUsuario, FicheroResiBean cab) throws Exception {
	 
		return XMLRobotDao.eliminarDuplicadorRX(idUsuario, cab);
		
	}
	*/
	
	
	//Paso4
	//public static boolean procesarDetalleTomasRobotInsertUnoAUno(String idUsuario, FicheroResiBean cab, DetallesTomasBean  detalleTomas, TomasOrdenadas tomasGlobal) throws SQLException, ClassNotFoundException, ParseException {
	//	return XMLRobotDao.procesarDetalleTomasRobot(idUsuario,  cab, detalleTomas, tomasGlobal);
	//}
	//Paso4
	/*
	public static boolean procesarDetalleTomasRobot(String idUsuario, FicheroResiBean cabDetalle, List<DetallesTomasBean>  listaDetalleTomas, TomasOrdenadas tomasGlobal) throws SQLException, ClassNotFoundException, ParseException {
		
    	if(listaDetalleTomas==null || listaDetalleTomas.size()<1) return false;
    	boolean result =false;
    	Iterator<DetallesTomasBean> _it = listaDetalleTomas.iterator();
    	   // Create a StringJoiner for constructing the SQL statement
    	//     StringJoiner sqlJoiner = new StringJoiner(", ", 
        		//          "INSERT INTO SPD_XML_detallesTomasRobot (idDivisionResidencia, idProceso, CIP, CN, nombreMedicamento, cantidadToma, dispensar, fechaToma, tramoToma, idLineaRX, idToma, nombreToma, planta, habitacion, numBolsa, idBolsa, idFreeInformation, idDetalle) VALUES ", 
            //          ";");
        
    	StringBuilder  queryInsert = new StringBuilder("INSERT INTO SPD_XML_detallesTomasRobot ( idDivisionResidencia, idProceso, CIP, orderNumber, CN, nombreMedicamento "); 
    		queryInsert.append(" , cantidadToma, dispensar, fechaToma,  tramoToma, idLineaRX,  idToma, nombreToma, planta, habitacion " );
    	queryInsert.append(" , numBolsa, idBolsa, idFreeInformation, idDetalle)  VALUES  ");
        
    	//String queryInsert = "INSERT INTO SPD_XML_detallesTomasRobot ( idDivisionResidencia, idProceso, CIP, CN, nombreMedicamento "; 
    	//queryInsert+= " , cantidadToma, dispensar, fechaToma,  tramoToma, idLineaRX,  idToma, nombreToma, planta, habitacion " ;
    	//	queryInsert+= " , numBolsa, idBolsa, idFreeInformation, idDetalle)  VALUES  " ;
        
       // StringBuilder queryInsert = new StringBuilder();
        
        
    	int count=0;
    	String aux="";
    	while (_it.hasNext()) 
        {
    		count++;
    		DetallesTomasBean bean = (DetallesTomasBean)_it.next();
    		aux=XMLRobotDao.procesarDetalleTomasRobot(idUsuario, cabDetalle, bean, tomasGlobal);
    		
    		int rowsInInsert = queryInsert.toString().split("\\), ").length;
    		
    		//if(count>SPDConstants.MAX_INSERTS_PER_STATEMENT)  //ejecutamos por bloques de 150 bolsas
    		if(rowsInInsert>SPDConstants.MAX_INSERTS_PER_STATEMENT)  //ejecutamos por bloques de 150 bolsas
    		{
    	        if (queryInsert.length() > 0) {
    	        	queryInsert.setLength(queryInsert.length() - 2);
    	        }
    			XMLRobotDao.ejecutarSentencia(queryInsert.toString());
    			queryInsert = new StringBuilder("INSERT INTO SPD_XML_detallesTomasRobot ( idDivisionResidencia, idProceso, CIP, orderNumber, CN, nombreMedicamento "); 
    	    		queryInsert.append(" , cantidadToma, dispensar, fechaToma,  tramoToma, idLineaRX,  idToma, nombreToma, planta, habitacion " );
    	    	queryInsert.append(" , numBolsa, idBolsa, idFreeInformation, idDetalle)  VALUES  ");
    			count=1;
    		}

    		if(!aux.equals(""))
    		{
    		//	sqlJoiner.add(aux);
    			queryInsert.append(aux);
    			//queryInsert+=aux;
    		//	if(count<listaDetalleTomas.size())
    		//		queryInsert.append(",");
    		}
         }
        // Remove the last comma and space
        if (queryInsert.length() > 0) {
        	queryInsert.setLength(queryInsert.length() - 2);
        }
    		//if (queryInsert != null && queryInsert.length() > 2 && queryInsert.endsWith(", ")) {
            // Remove the last ", "
    		//	queryInsert = queryInsert.substring(0, queryInsert.length() - 2);
    		//}
        return XMLRobotDao.ejecutarSentencia(queryInsert.toString());
	}
	*/
	
	public static boolean procesarDetalleTomasRobot(String idUsuario, FicheroResiBean cabDetalle, List<DetallesTomasBean>  listaDetalleTomas, TomasOrdenadas tomasGlobal) throws SQLException, ClassNotFoundException, ParseException {
		
    	if(listaDetalleTomas==null || listaDetalleTomas.size()<1) return false;
    	boolean result =false;
    	Iterator<DetallesTomasBean> _it = listaDetalleTomas.iterator();
        
    	StringBuilder  queryInsert = new StringBuilder("INSERT INTO SPD_XML_detallesTomasRobot ( idDivisionResidencia, idProceso, CIP, orderNumber, CN, nombreMedicamento "); 
    		queryInsert.append(" , cantidadToma, dispensar, fechaToma,  tramoToma, idLineaRX,  idToma, nombreToma, planta, habitacion " );
    	queryInsert.append(" , numBolsa, idBolsa, idFreeInformation, idDetalle, pautaResidencia)  VALUES  ");
      
    	String aux="";
    	int rowsInInsert = 0;
    	while (_it.hasNext()) 
        {
     		DetallesTomasBean bean = (DetallesTomasBean)_it.next();
    		aux=XMLRobotDao.procesarDetalleTomasRobot(idUsuario, cabDetalle, bean, tomasGlobal);
    		if (aux==null || aux.equals("")) continue;
    		
    		rowsInInsert+= queryInsert.toString().split("\\) , ").length;
    		if(rowsInInsert<SPDConstants.MAX_INSERTS_PER_STATEMENT && !aux.equals(""))
    		{
      			queryInsert.append(aux);
    		}
    		else {
    	        if (queryInsert.length() > 0) {
    	        	queryInsert.setLength(queryInsert.length() - 2);
    	        }
    			XMLRobotDao.ejecutarSentencia(queryInsert.toString());
    			queryInsert = new StringBuilder("INSERT INTO SPD_XML_detallesTomasRobot ( idDivisionResidencia, idProceso, CIP, orderNumber, CN, nombreMedicamento "); 
    	    		queryInsert.append(" , cantidadToma, dispensar, fechaToma,  tramoToma, idLineaRX,  idToma, nombreToma, planta, habitacion " );
    	    		queryInsert.append(" , numBolsa, idBolsa, idFreeInformation, idDetalle, pautaResidencia)  VALUES  ");
          			queryInsert.append(aux);
          		rowsInInsert = 0;
			}

    		
         }
    	
        // Remove the last comma and space
        if (queryInsert.length() > 0) {
        	queryInsert.setLength(queryInsert.length() - 2);
        }
        
        return XMLRobotDao.ejecutarSentencia(queryInsert.toString());
 		
	}
	

	
	


	public static FiliaDM getMedicamentosProceso(String spdUsuario, FicheroResiBean cab) throws SQLException {
		
		FiliaDM filiaDm = XMLRobotDao.getMedicamentosDeProceso(spdUsuario, cab);
		
		return filiaDm;
	}
	
	public static FiliaRX getTratamientosProceso(String spdUsuario, FicheroResiBean cab, DivisionResidencia div, PacienteBean pac) throws SQLException, ParseException {
		
		FiliaRX filiaRx = XMLRobotDao.getTratamientosDeProceso(spdUsuario, cab, div, pac);
		
		return filiaRx;
	}

	
	
	/**
	 * @param cab
	 * @param medicamentos
	 * @param response
	 * @return
	

	public static String generaFichero(FicheroResiBean cab, FiliaDM medicamentos, HttpServletResponse response) 
	{
		String nomFitxer="";
	    try {
	        medicamentos.setRequestType(10);
	        JAXBContext jaxbContext = JAXBContext.newInstance(FiliaDM.class, DrugRX.class, Bottle.class);
	        // Crear el marshaller
	        Marshaller marshaller = jaxbContext.createMarshaller();
	
	        // Configurar opciones del marshaller
	        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            int count=0;

	        try {
	        	String diaHora = DateUtilities.getDatetoString("ddMMyyyy_Hmm", new Date());
	        	//String filePath = "c://UTILS/"+diaHora+"_UNIFICADA_DM_" + cab.getIdProceso() + ".xml";
	        	String filePath = "c://UTILS/UNIFICADA_DM_" + cab.getIdProceso() + ".xml";
	        	nomFitxer = diaHora+"_UNIFICADA_" + cab.getIdProceso() + ".xml";
	        	File file = new File(filePath);
	
	        	FileOutputStream fos = new FileOutputStream(file);
	        	OutputStreamWriter writer = new OutputStreamWriter(fos, "UTF-8");
	        	marshaller.marshal(medicamentos, writer);
		        writer.close();

		        
		        response.setContentType("application/xml");
		        response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
		        response.setContentLength((int) file.length());
		        
	            System.out.println("XML unificada generado correctamente");
	           
	            if (file == null || !file.exists()) {
	                throw new IllegalArgumentException("El archivo no existe: " + (file != null ? file.getAbsolutePath() : "null"));
	            }

	            if (response == null) {
	                throw new IllegalArgumentException("El objeto HttpServletResponse no puede ser null");
	            }

	            try (InputStream fis = new FileInputStream(file);
	                    OutputStream outputStream = response.getOutputStream()) {

	                   byte[] buffer = new byte[1024];
	                   int bytesRead;
	                   int iteration = 0;

	                   // Leer del InputStream y escribir en el OutputStream
	                   while ((bytesRead = fis.read(buffer)) != -1) {
	                       outputStream.write(buffer, 0, bytesRead);

	                       // Diagnóstico para verificar las iteraciones
	                       iteration++;
	                       System.out.println("Iteración: " + iteration + ", bytesRead: " + bytesRead);
	                   }

	                   // Asegúrate de vaciar el buffer del OutputStream
	                   outputStream.flush();

	            } catch (FileNotFoundException e) {
	                e.printStackTrace();
	                throw new RuntimeException("Error al encontrar el archivo", e);
	            } catch (IOException e) {
	                e.printStackTrace();
	                throw new RuntimeException("Error al leer/escribir el archivo", e);
	            }

	            
	            
	        } catch (Exception e) {
	            System.err.println("Error al generar el XML: " + e.getMessage() + " _ contador:  " + count);
	            e.printStackTrace();
	        }
	    } catch (JAXBException e) {
	       e.printStackTrace();
	    }
	    return nomFitxer;
	}
	*/
	public static String generaFicheroDM(FicheroResiBean cab, FiliaDM medicamentos, HttpServletResponse response) 
	{
		String nomFitxer="";
	    try {
	    	 JAXBContext jaxbContext = JAXBContext.newInstance(FiliaDM.class, DrugDM.class, Bottle.class);
	
	        // Crear el marshaller
	        Marshaller marshaller = jaxbContext.createMarshaller();
	
	        // Configurar opciones del marshaller
	        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
	
	        try {
	        	 // String diaHora = DateUtilities.getDatetoString("ddMMyyyy_Hmm", new Date());
	            //String filePath = "c://UTILS/UNIFICADA_DM_" + cab.getIdProceso() + ".xml";
	            nomFitxer = "FILIA_DM_" + cab.getIdProceso() + ".xml";
	        	String filePath = SPDConstants.PATH_DOCUMENTOS+"/robot/"+ nomFitxer;
	            
	        	File file = new File(filePath);
	
	        	FileOutputStream fos = new FileOutputStream(file);
	        	OutputStreamWriter writer = new OutputStreamWriter(fos, "UTF-8");
	        	marshaller.marshal(medicamentos, writer);

	            System.out.println("DM XML generado correctamente");
	            /* Para enviarlo a descargas 
	            writer.close();
		        response.setContentType("application/xml");
		        response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
		        response.setContentLength((int) file.length());
		        
	            try (InputStream fis = new FileInputStream(file); OutputStream outputStream = response.getOutputStream()) {
	            	    byte[] buffer = new byte[1024];
	            	    int bytesRead;
	            	    while ((bytesRead = fis.read(buffer)) != -1) {
	            	        outputStream.write(buffer, 0, bytesRead);
	            	    }
	            	}
	            */
	            
	            
	            
	        } catch (Exception e) {
	            System.err.println("Error al generar el XML: " + e.getMessage());
	            e.printStackTrace();
	        }
	    } catch (JAXBException e) {
	       e.printStackTrace();
	    }
	    return nomFitxer;
	}
	
	
	/*
	 * 
	public static String generaFicheroDM(FicheroResiBean cab, FiliaDM medicamentos, HttpServletResponse response) 
	{
        String nomFitxer = "";
        try {
            medicamentos.setRequestType(10);
            JAXBContext jaxbContext = JAXBContext.newInstance(FiliaDM.class, DrugRX.class, Bottle.class);
            // Crear el marshaller
            Marshaller marshaller = jaxbContext.createMarshaller();

            // Configurar opciones del marshaller
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");

            String diaHora = DateUtilities.getDatetoString("ddMMyyyy_Hmm", new Date());
            String filePath = "c://UTILS/UNIFICADA_DM_" + cab.getIdProceso() + ".xml";
            nomFitxer = diaHora + "_UNIFICADA_" + cab.getIdProceso() + ".xml";
            File file = new File(filePath);

            // Verificar el archivo
            if (file == null || !file.exists()) {
                throw new IllegalArgumentException("El archivo no existe: " + (file != null ? file.getAbsolutePath() : "null"));
            }

            // Configurar la respuesta HTTP
            if (response == null) {
                throw new IllegalArgumentException("El objeto HttpServletResponse no puede ser null");
            }

            response.setContentType("application/xml");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");
            response.setContentLength((int) file.length());

            try (InputStream fis = new FileInputStream(file);
                // OutputStream outputStream = response.getOutputStream()
            		) 
            		{

                byte[] buffer = new byte[1024];
                int bytesRead;
                int count=0;

                while ((bytesRead = fis.read(buffer)) != -1) {
                	count++;
                  //  outputStream.write(buffer, 0, bytesRead);
                	response.getOutputStream().write(buffer, 0, bytesRead);
                }

                //outputStream.flush(); // Asegúrate de vaciar el buffer
                response.getOutputStream().flush(); 
            	System.out.println("Escritura completada exitosamente.");

            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Error al leer/escribir el archivo en la respuesta", e);
            }

        } catch (JAXBException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al crear el JAXBContext o el Marshaller", e);
        } catch (Exception e) {
            System.err.println("Error al generar el XML: " + e.getMessage());
            e.printStackTrace();
        }
        return nomFitxer;
    }
	*/
	
	
	public static String generaFicheroRX(FicheroResiBean cab, FiliaRX filiaRX, HttpServletResponse response) 
	{
		String nomFitxer="";
	    try {
	    	JAXBContext jaxbContext = JAXBContext.newInstance(FiliaRX.class, DrugRX.class, Print.class);
	
	        // Crear el marshaller
	        Marshaller marshaller = jaxbContext.createMarshaller();
	
	        // Configurar opciones del marshaller
	        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
	
	        try {
	           // String diaHora = DateUtilities.getDatetoString("ddMMyyyy_Hmm", new Date());
	            //String filePath = "c://UTILS/UNIFICADA_RX_" + cab.getIdProceso() + ".xml";
	       	    nomFitxer = "FILIA_RX_" + cab.getIdProceso() +  ".xml";
	       	    String filePath = SPDConstants.PATH_DOCUMENTOS+"/robot/"+nomFitxer;
	            //nomFitxer = "FILIA_RX_" + cab.getIdProceso() + "_hora_"+diaHora + ".xml";
	            File file = new File(filePath);
	
	        	FileOutputStream fos = new FileOutputStream(file);
	        	OutputStreamWriter writer = new OutputStreamWriter(fos, "UTF-8");
	        	marshaller.marshal(filiaRX, writer);
		       // writer.close();

		        
		        //response.setContentType("application/xml");
		        //response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
		        //response.setContentLength((int) file.length());
		        
	            System.out.println("RX XML generado correctamente");
	            /*try (InputStream fis = new FileInputStream(file); OutputStream outputStream = response.getOutputStream()) {
	            	    byte[] buffer = new byte[1024];
	            	    int bytesRead;
	            	    while ((bytesRead = fis.read(buffer)) != -1) {
	            	        outputStream.write(buffer, 0, bytesRead);
	            	    }
	            	}
	            */
	            
	            
	            
	        } catch (Exception e) {
	            System.err.println("Error al generar el XML: " + e.getMessage());
	            e.printStackTrace();
	        }
	    } catch (JAXBException e) {
	       e.printStackTrace();
	    }
	    return nomFitxer;
	}
	
	/*

	public static String generaFicheroRX(FicheroResiBean cab, FiliaRX filiaRX, HttpServletResponse response) 
	{
		String nomFitxer="";
	    try {
	    	filiaRX.setRequestType(10);
	        JAXBContext jaxbContext = JAXBContext.newInstance(FiliaRX.class, DrugRX.class, Print.class);
	        //	  JAXBContext jaxbContext = JAXBContext.newInstance(FiliaRX.class);
	        // Crear el marshaller
	        Marshaller marshaller = jaxbContext.createMarshaller();
	
	        // Configurar opciones del marshaller
	        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
	
	        try {
	        	String diaHora = DateUtilities.getDatetoString("ddMMyyyy_Hmm", new Date());
	        	//String filePath = "c://UTILS/"+diaHora+"_UNIFICADA_RX_" + cab.getIdProceso() + ".xml";
	        	String filePath = "c://UTILS/UNIFICADA_RX_" + cab.getIdProceso() + ".xml";
	        	nomFitxer = diaHora+"_UNIFICADA_" + cab.getIdProceso() + ".xml";
	        	File file = new File(filePath);
	
	        	FileOutputStream fos = new FileOutputStream(file);
	        	OutputStreamWriter writer = new OutputStreamWriter(fos, "UTF-8");
	        	marshaller.marshal(filiaRX, writer);
		        writer.close();

		        
		        response.setContentType("application/xml");
		        response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
		        response.setContentLength((int) file.length());
		        
	            System.out.println("XML unificada generado correctamente");
	            try (InputStream fis = new FileInputStream(file);
	            	     OutputStream outputStream = response.getOutputStream()) {
	            	    byte[] buffer = new byte[1024];
	            	    int bytesRead;
	            	    while ((bytesRead = fis.read(buffer)) != -1) {
	            	        outputStream.write(buffer, 0, bytesRead);
	            	    }
	            	}
	            
	            
	            
	            
	        } catch (Exception e) {
	            System.err.println("Error al generar el XML: " + e.getMessage());
	            e.printStackTrace();
	        }
	    } catch (JAXBException e) {
	       e.printStackTrace();
	    }
	    return nomFitxer;
	}
*/
	
	/**
	 * Método encargardo de contruir el orderNumber, que es un string numérico de 36 dígitos, uno por CIP.
	 * Se compone del año+mes+dia+milis+aleatorio de 18 caracteres 
	 * @return 
	 */
	public static String getOrderNumber() {

        // Obtén la fecha y hora actual
        LocalDateTime now = LocalDateTime.now();
        // Formatea la fecha y los milisegundos a yyyyMMddHHmmssSSS
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        String formattedDateTime = now.format(formatter);
        // Genera un número aleatorio de 18 dígitos
        Random random = new Random();
        long randomNumber = 100000000000000000L + (long)(random.nextDouble() * 900000000000000000L);
        // Convierte el número aleatorio a una cadena con 14 dígitos
        String randomDigits = String.format("%018d", randomNumber);
        // Concatena la fecha formateada con los 18 dígitos aleatorios
        String result = formattedDateTime + randomDigits;
        
		return result;
	}
	
	/**
	 * étodo encargado de generar un id para la producción del paciente. No ha de ser aleatorio como antes del RD porque es posible que el proceso de generar los datos que 
	 * se envían a robot se realice posteriormente, para precisamente generar los datos. 
	 * @param bean
	 * @param cabeceraTop
	 * @return
	 */
	public static String getOrderNumberORI(DetallesTomasBean bean, FicheroResiBean cabeceraTop) {
		ZoneId zoneId = ZoneId.systemDefault(); // o ZoneId.of("Europe/Madrid")
		LocalDateTime lFechaHoraProceso = cabeceraTop.getFechaHoraProceso().toInstant().atZone(zoneId).toLocalDateTime();
		
        // Formatea la fecha y los milisegundos a yyyyMMddHHmmssSSS
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        String ascii= StringUtil.convertTextToAscii(StringUtil.makeFlat(bean.getCIP(), true));

      String parte1 = lFechaHoraProceso.format(formatter);
        String parte2 = StringUtil.left(ascii + ascii + "0000000000000000000000", 18);
        String result = parte1+parte2;

        
		return result;
	}

	public static String getOrderNumber(DetallesTomasBean bean, FicheroResiBean cabeceraTop) {
		
        String ascii= StringUtil.convertTextToAscii(StringUtil.makeFlat(bean.getCIP(), true));
        String parte1 = StringUtil.left(ascii + "0000000000000000000000", 11);	//limitamos a 11
        String parte2 = extraerFechasYRandom(cabeceraTop.getIdProceso());
        String result = parte1+parte2;
        
		return result;
	}
	
	/**
	 * Para no sobrecargar los inserts de generación del fichero, se realiza a posteriori un update cruzado con bd_consejo
	 * @param idUsuario
	 * @param cab
	 * @param cabDetalle
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public static void actualizaOtrosDatos(String idUsuario, FicheroResiBean cab, FicheroResiBean cabDetalle) throws ClassNotFoundException, SQLException {
		XMLRobotDao.actualizaOtrosDatos(idUsuario,  cab, cabDetalle);
		
	}

	/*
	 * Para extraer el random del idProceso 
	 */
	public static String extraerUltimaParte(String cadena) {
	    if (cadena == null || !cadena.contains("_")) {
	        return cadena;
	    }
	    int ultimoGuion = cadena.lastIndexOf('_');
	    return cadena.substring(ultimoGuion + 1);
	}
	
	/* Devolvemos fechaDesde_fechaHasta_random del idProceso 
	 * 
	 */
	public static String extraerFechasYRandom(String cadena) {
	    if (cadena == null || !cadena.contains("_")) { 
	        return cadena;
	    }
	    String parteRandom=extraerUltimaParte(cadena);
	    cadena=quitarFinal(cadena, parteRandom);
	    
	    String fechaHasta=extraerUltimaParte(cadena);
	    cadena=quitarFinal(cadena, fechaHasta);

	    String fechaDesde=extraerUltimaParte(cadena);
	    cadena=quitarFinal(cadena, fechaDesde);

	    String result = "#"+fechaDesde+"_"+fechaHasta+"_"+parteRandom;
	    return result;  
	}

	/**
	 * Eliminamos una cadena final de otra cadena y también un "_" en caso que esté al final
	 * @param original
	 * @param sufijo
	 * @return
	 */
	public static String quitarFinal(String cadena, String quitar) {
	    if (cadena == null || quitar == null) return cadena;
	    if (cadena.endsWith(quitar)) {
	    	cadena = cadena.substring(0, cadena.length() - quitar.length());
	    }
	    // Eliminar guion final si queda
	    if (cadena.endsWith("_")) {
	    	cadena = cadena.substring(0, cadena.length() - 1);
	    }

	    return cadena;
	}





}



	
	
	
	
	
	
	
	