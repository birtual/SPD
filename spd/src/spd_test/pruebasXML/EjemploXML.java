package spd_test.pruebasXML;

import java.io.File;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element; 


public class EjemploXML {
    
    public Document inicializarDocumento() throws ParserConfigurationException{
        Document documento;
        // Creamos los objectos de creacion de Documentos XML
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder constructor = docFactory.newDocumentBuilder();
                // Convertimos
        documento = constructor.newDocument();
        
        return documento;        
    }
    
    public String convertirString(Document documento) throws TransformerConfigurationException, TransformerException {
        // Creamos el objecto transformador
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        
        // Indicamos que queremos que idente el XML con 2 espacios
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        
        // Creamos el escritor a cadena de texto
        StringWriter writer = new StringWriter();
        // Fuente de datos, en este caso el documento XML
        DOMSource source = new DOMSource(documento);
        // Resultado, el cual se almacenara en el objecto writer
        StreamResult result = new StreamResult(writer);
        // Efectuamos la transformacion a lo que indica el objecto resultado, writer apuntara a el resultado
        transformer.transform(source, result);
        // Convertimos el buffer de writer en cadena de texto
        String output = writer.getBuffer().toString();

        return output;
    }
    
    public void escribirArchivo(Document documento, String fileName) throws TransformerConfigurationException, TransformerException {
        // Creamos el objecto transformador
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        
        // Indicamos que queremos que idente el XML con 2 espacios
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

        // Archivo donde almacenaremos el XML
        File archivo = new File(fileName);

        // Fuente de datos, en este caso el documento XML
        DOMSource source = new DOMSource(documento);
        // Resultado, el cual almacena en el archivo indicado
        StreamResult result = new StreamResult(archivo);
        // Transformamos de �a fuente DOM a el resultado, lo que almacena todo en el archivo
        transformer.transform(source, result);
    }
    
	public Document crearDocumento() throws ParserConfigurationException {
		Document documento = this.inicializarDocumento();
	        
		// Creamos el elemento principal
		Element idCareHome = documento.createElement("careHome");
		idCareHome.setAttribute("idCareHome", "general_cambrils");	//Identificador �nico nacional relacionado con el centro. Puede estar formado por varias claves como un CIF + c�digo interno del centro.
	
		documento.appendChild(idCareHome);
	
	    //RESIDENCIA
		Element resiActive = documento.createElement("active");		// Indica si el centro est� o no activo; toma los valores true o false
		Element resiName = documento.createElement("name");			// Nombre del centro
		Element resiCity = documento.createElement("city");			// Nombre de la ciudad
		Element resiCp = documento.createElement("cp");				// C�digo postal
		Element resiProvince = documento.createElement("province");	// C�digo de provincia
		Element resiPhone = documento.createElement("phone");		// Tel�fono del centro
		Element resiEmail = documento.createElement("email");		// Email del centro
		Element resiCountry = documento.createElement("country");	// Nombre del pais
	    //Element doses = documento.createElement("doses");			// Contenido xml con todas las horas de las tomas existentes en el centro
	    Element sections = documento.createElement("sections");		// N estructuras xml con el contenido de varias secciones
	    Element patients = documento.createElement("patients");				// Contenido xml con los pacientes

		    //DOSES
	    //Element dose = documento.createElement("dose");				// Identificador �nico del centro que referencie a una hora de toma. Su valor puede coincidir con la hora de la toma.
	    //Element doseHour = documento.createElement("hour");			// Hora de la toma del paciente en formato �hh:mm�
	    // Element doseName = documento.createElement("name");			// Descripci�n del periodo del d�a de la toma.
			        
		    //SECTIONS
		    Element section = documento.createElement("section");					// Identificador �nico de la secci�n
		    Element sectionName = documento.createElement("name");					// Nombre de la secci�n
		    Element sectionObservations = documento.createElement("observations>");	// Observaciones
			        
		    //PATIENS
		    Element patient = documento.createElement("patient");								// Identificador �nico del paciente dentro del centro. Se puede coger una clave interna del sistema inform�tico siempre vinculada al paciente.
		    Element patientActive = documento.createElement("active");							// Indicador de si el paciente est� o no activo, valores true y false. Si el paciente est� activo se podr�n generar blisters.
		    Element patientAdmissionDate = documento.createElement("admissionDate");			// Fecha de ingreso en el centro, su formato es dd-mm-yyyy. Ejemplo: 02-01-2019
		    Element patientLastAdmissionDate = documento.createElement("lastAdmissionDate");	// Ultima fecha de ingreso en el centro, su formato es dd-mm-yyyy
		    Element patientHospitalized = documento.createElement("hospitalized");				// Indicador de si el paciente est� o no hospitalizado, valores true y false.
		    Element patientDni = documento.createElement("dni");								// Documento nacional de identidad del paciente
		    Element patientName = documento.createElement("name");     							// Nombre del paciente
		    Element patientSurname1 = documento.createElement("surname1");     					// Primer apellido del paciente
		    Element patientSurname2 = documento.createElement("surname2");    					// Segundo apellido del paciente
		    Element patientBirthday = documento.createElement("birthday");     					// Fecha de nacimiento del paciente en format dd-mm-yyyy
			Element  tratamientos= documento.createElement("treatments");						// Conjunto de tratamientos de un paciente o posolog�a
	               
				//TRATAMIENTOS
				Element  tratamiento= documento.createElement("treatment");							// Identificador �nico del tratamiento en el centro. Un paciente puede tener varios tratamientos. Puede ser una clave compuesta, siempre vinculada al tratamiento del paciente. Si solo existe un tratamiento se puede coger como valor la clave del identificador del paciente.
				Element  tratamientoIdReceipt= documento.createElement("idReceipt");				// Identificador �nico de la receta.
				Element  tratamientoActive= documento.createElement("active");						// Indica si el tratamiento est� o no activo en este momento, puede tomar un valor true o false.
				Element  tratamientoStartTreatment= documento.createElement("startTreatment");		// Fecha de inicio del tratamiento en formato dd-mm-yyyy hh:mm.
				Element  tratamientoEndTreatment= documento.createElement("endTreatment");			// Fecha de finalizaci�n del tratamiento en formato dd-mm-yyyy hh:mm.
				Element  pouches = documento.createElement("pouches");								// Bolsas de medicaci�n que puede tomar un paciente en un d�a en diferentes periodos del d�a.
	        
					//BOLSAS DE MEDICACI�N
					Element  pouch = documento.createElement("pouch");			// Identificador �nico de la bolsa de medicaci�n que toma un paciente en un d�a. Si no se dispone de ella esta clave puede estar compuesta por la clave del paciente, la clave del tratamiento y la clave de la hora de la toma.
					Element  pouchDose = documento.createElement("dose");		// Contenido xml con ela hora de la toma
					Element  pouchHour = documento.createElement("hour");		// Hora de la toma del paciente en formato �hh:mm�
					Element  pouchName = documento.createElement("name");		// Descripci�n del periodo del d�a de la toma.
					Element  doses = documento.createElement("doses");			// Contenido xml con todas las horas de las tomas existentes en el centro
					Element  lines = documento.createElement("lines");			//Listado de medicamentos que tomar� un paciente en diferentes periodo del d�a durante un periodo del tiempo.
				        
					    //DOSES
						Element dose = documento.createElement("dose");				// Identificador �nico del centro que referencie a una hora de toma. Su valor puede coincidir con la hora de la toma.
						Element doseHour = documento.createElement("hour");			// Hora de la toma del paciente en formato �hh:mm�
					    Element doseName = documento.createElement("name");			// Descripci�n del periodo del d�a de la toma.

						//LINEAS BOLSA
						Element  line = documento.createElement("line");							// Identificador �nico del medicamento a tomar por un paciente en una bolsa de medicaci�n correspondiente a un periodo del d�a. Si no se dispone de esta clave se puede coger la clave de la bolsa y el c�digo de medicamento mas un contador incremental (para casos de tener medicaci�n replicada, por ejemplo del tipo si precisa).
						Element  lineActivePeriod = documento.createElement("activePeriod");		// Indicador que se ha de informar siempre; si es true indica que la medicaci�n del paciente es diaria, si es false no lo es.	
						Element  lineFrom = documento.createElement("from");						// Solo se informa si la mediaci�n es diaria. Esta fecha es la fecha de inicio de la toma, tiene formato dd-mm-yyyy hh:mm.
						Element  lineTo = documento.createElement("to");							// Solo se informa si la mediaci�n es diaria. Esta fecha es la fecha de fin de la toma, tiene formato dd-mm-yyyy hh:mm.
						Element  lineDayfromWeek = documento.createElement("dayfromWeek");			// Solo se informa si la medicaci�n se toma ciertos dias de la semana. Esta fecha indica el inicio de la toma, tiene formato dd-mm-yyyy hh:mm.
						Element  lineDaytoWeek = documento.createElement("daytoWeek");				// Solo se informa si la medicaci�n se toma ciertos dias de la semana. Esta fecha indica el fin de la toma, tiene formato dd-mm-yyyy hh:mm.
						Element  lineDayfromMonth = documento.createElement("dayfromMonth");		// Solo se informa si la medicaci�n se toma ciertos dias del mes. Esta fecha indica el inicio de la toma, tiene formato dd-mm-yyyy hh:mm
						Element  lineDaytMonth = documento.createElement("daytoMonth");				// Solo se informa si la medicaci�n se toma ciertos dias del mes. Esta fecha indica el fin de la toma, tiene formato dd-mm-yyyy hh:mm
						Element  lineWeekdays = documento.createElement("weekdays");				// Solo se informa en caso de medicaci�n semanal. Indica los dias de la semana en los cuales se tiene que hacer las tomas.El valor de este campo es una cadena de par�metros no repetidos separados por comas que pueden tomar los siguientes valores: {�monday�,�tuesday�,�wednesday�,�thursday�,�friday�,�saturday�,�sunday�}.
						Element  lineMonthdays = documento.createElement("monthdays");				// Solo se informa en caso de medicaci�n mensual. Indica los dias del mes en los cuales se tienen que hacer las tomas. Este valor es un entero comprendido entre 1 y 31.
						Element  guides = documento.createElement("guides");						// Solo se informa en caso de tener medicaci�n con peculiaridades. En el caso de que la medicaci�n se tome dias,semanas o meses alternos con una determinada frecuencia aqu� se registran dichas peculiaridades. Estas pecularidades tienen un car�cter restrictivo sobre las tomas diarias, semanales o mensuales ya creadas.
						Element  lineNeeded = documento.createElement("needed");					// Si la medicaci�n es de tipo si precisa toma el valor de true, en caso contrario toma el valor de false.
						Element  lineIrreplaceblePill = documento.createElement("irreplaceblePill"); // Si la medicaci�n no es reemplazable toma el valor true, false en caso contrario
						Element  linePill = documento.createElement("pill");						// Aqu� se informa del identificador del medicamento; puede ser el c�digo nacional (C.N) o bien otro c�digo interno del centro correspondiente a otro tipo de medicaci�n
						Element  linePillDesc = documento.createElement("pill_desc");				// Esta es la descripci�n larga del medicamento. Se precisa para casos en los que la medicaci�n se crea de manera manual.
						Element  lineOutOfBlister = documento.createElement("outOfBlister");		// Indicador de si el medicamento va o no fuera de blister; es true si va fuera de blister y false si va en blister. Si no se informa se entiende que va dentro de blister
						Element  lineAmount = documento.createElement("amount");					// Es la cantidad de medicaci�n que se deber� tomar; se acepta medicaci�n fraccionada. Ejemplo: 0.25,0.5,0.75, 1,1.25,2,3�.
	
							//GUIDES	
							Element  guide = documento.createElement("guide");				// Identificador �nico del patr�n a aplicar para esta toma de este paciente. Si no se dispone de esta clave se puede coger la clave de �idLine� y a�adir un contador incremental.
							Element  guideNumber = documento.createElement("number");		// Es un contador entero que indica el orden de la etiqueta; el contador empieza siempre por 1.
							Element  guidePeriods = documento.createElement("periods");		// Este valor es un num�rico entero e indica el n�mero de periodos sobre los que se aplicar� esta restricci�n.
							Element  guidePeriodtype = documento.createElement("periodtype");// Este valor indica el tipo de periodo; este puede tomar los valores �DAYS�, �WEEKS� o �MONTHS�
							Element  guideActive = documento.createElement("active");		// Este valor indica si sobre este periodo de tiempo el paciente ha de tomar o no el medicamento; si es s� toma el valor true, en caso contrario tiene el valor false.
				        
				        
	
			Element  patientRoom = documento.createElement("room");				

    
        
        
		// Establecemos el contenido del titulo
		resiActive.setTextContent("true");
		resiName.setTextContent("Residencia Cambrils");
		resiCity.setTextContent(".");
		resiCp.setTextContent(".");
		resiProvince.setTextContent(".");
		resiEmail.setTextContent(".");
		resiCountry.setTextContent(".");
		//doses
		dose.setTextContent(".");
		doseHour.setTextContent(".");
		doseName.setTextContent(".");
		//sections
		section.setTextContent(".");
		sectionName.setTextContent(".");
		sectionObservations.setTextContent(".");
		//patients
		patient.setTextContent(".");
		patientActive.setTextContent(".");
		patientAdmissionDate.setTextContent(".");
		patientLastAdmissionDate.setTextContent(".");
		patientHospitalized.setTextContent(".");
		patientDni.setTextContent(".");
		patientName.setTextContent(".");
		patientSurname1.setTextContent(".");
		patientSurname2.setTextContent(".");
		patientBirthday.setTextContent(".");
		//treatments
		tratamiento.setTextContent(".");
		tratamientoIdReceipt.setTextContent(".");
		tratamientoActive.setTextContent(".");
		tratamientoStartTreatment.setTextContent(".");
		tratamientoEndTreatment.setTextContent(".");
		//pouchs
		pouch.setTextContent(".");
		pouchDose.setTextContent(".");
		pouchHour.setTextContent(".");
		pouchName.setTextContent(".");
		//lines  
		line.setTextContent(".");
		lineActivePeriod.setTextContent(".");
		lineFrom.setTextContent(".");
		lineTo.setTextContent(".");
		lineDayfromWeek.setTextContent(".");
		lineDaytoWeek.setTextContent(".");
		lineDayfromMonth.setTextContent(".");
		lineDaytMonth.setTextContent(".");
		lineWeekdays.setTextContent(".");
		lineMonthdays.setTextContent(".");
		lineNeeded.setTextContent(".");
		lineIrreplaceblePill.setTextContent(".");
		linePill.setTextContent(".");
		linePillDesc.setTextContent(".");
		lineOutOfBlister.setTextContent(".");
		lineAmount.setTextContent(".");
		//guides  
		guide.setTextContent(".");
		guideNumber.setTextContent(".");
		guidePeriods.setTextContent(".");
		guidePeriodtype.setTextContent(".");
		guideActive.setTextContent(".");
		       
		patientRoom.setTextContent(".");

        
        
        

        // Indicamos que el elemento titulo desciende de entrada
        idCareHome.appendChild(resiActive);
        idCareHome.appendChild(resiName);
        idCareHome.appendChild(resiCity);
        idCareHome.appendChild(resiCp);
        idCareHome.appendChild(resiProvince);
        idCareHome.appendChild(resiEmail);       
        idCareHome.appendChild(resiCountry);  
        //idCareHome.appendChild(doses);  
        //doses.appendChild(dose);
        idCareHome.appendChild(sections);  
        idCareHome.appendChild(patients);  
 
        sections.appendChild(section);
        sections.appendChild(sectionName); 
        sections.appendChild(sectionObservations); 
        
        patients.appendChild(patient); 
        patients.appendChild(patient); 
        patients.appendChild(patient); 
        patients.appendChild(patient); 
        patients.appendChild(patient); 
        
        	//DOSES				
        	//Element dose				
        	//Element doseHour				
        	// Element doseName				
			
        		

        

        
        
        //Elemento fecha
        Element fecha = documento.createElement("fecha");
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendario = Calendar.getInstance();
        Date date = new Date(calendario.getTimeInMillis());

        fecha.setTextContent(formato.format(date));
        idCareHome.appendChild(fecha);
        
        return documento;
    }
    
}