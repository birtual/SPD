
package lopicost.spd.helium.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;


import lopicost.spd.helium.model.Center;
import lopicost.spd.helium.model.Dose;
import lopicost.spd.helium.model.Guide;
import lopicost.spd.helium.model.Line;
import lopicost.spd.helium.model.Patient;
import lopicost.spd.helium.model.Patients;
import lopicost.spd.helium.model.Pouch;
import lopicost.spd.helium.model.Section;
import lopicost.spd.helper.FicheroResiDetalleHelper;
import lopicost.spd.model.DivisionResidencia;
import lopicost.spd.persistence.DivisionResidenciaDAO;
import lopicost.spd.persistence.DoseDAO;
import lopicost.spd.persistence.FicheroResiDetalleDAO;
import lopicost.spd.persistence.PacienteDAO;
import lopicost.spd.struts.bean.FicheroResiBean;
import lopicost.spd.struts.bean.PacienteBean;
import lopicost.spd.struts.form.FicheroResiForm;
import lopicost.spd.utils.DataUtil;
import lopicost.spd.utils.DateUtilities;
import lopicost.spd.utils.HelperSPD;
import lopicost.spd.utils.SPDConstants;
import lopicost.spd.utils.StringUtil;

/**
 
 *Logica de negocio 
 */
public class HeliumHelper {


	private final String cLOGGERHEADER = "PlantillaUnificadaHelper: ";
	private final String cLOGGERHEADER_ERROR = cLOGGERHEADER + "ERROR: PlantillaUnificadaHelper: ";
	static TreeMap Doses_TreeMap =new TreeMap();

	

	/**
	 * Método principal encargado de englobar toda la lógica de creación
	 * @param formulari
	 * @return
	 * @throws Exception
	 */
	public static Center getCenterFicherosHelium(String spdUsuario, FicheroResiForm formulari) throws Exception {
		
		List<String> errors = new ArrayList<String>();
		formulari.setErrors(errors);

		TreeMap<String, Patient> CIPS_TreeMap =new TreeMap<String, Patient>();

	    //recuperamos cabecera para saber los campos y tomas de inicio
	    //FicheroResiBean cab =  FicheroResiDetalleDAO.getFicheroResiCabecera(formulari);
		FicheroResiBean cab =  FicheroResiDetalleHelper.getCabeceraFicheroResi( spdUsuario, formulari.getIdDivisionResidencia(), formulari.getIdProceso(), false);
		formulari.setListaTomasCabecera(getTomasCabecera(cab));	
		Doses_TreeMap = getDoses(cab);

		//creación del centro
		Center careHome = new Center();
	    Patients patients = new Patients();
		creaCenter(spdUsuario, careHome, formulari);

		//recuperación de los registros totales
		//List<FicheroResiBean> registros=FicheroResiDetalleDAO.getGestFicheroResiBolsa(-1, formulari,  0,10000, null, false, null, false, true);  
		List<FicheroResiBean> registros=FicheroResiDetalleDAO.getGestFicheroResiBolsa( spdUsuario, -1, formulari,  0,100000, null, false, "g.resiCIP, g.SpdNombreBolsa, g.resiInicioTratamiento ", true, false);  
		Iterator<FicheroResiBean> it= registros.iterator();
		
		Patient patient = null; 
		//RECORRIDO POR LOS REGISTROS
		int j=0;
		while (it!=null && it.hasNext())
		{
			j++;
			
	      	FicheroResiBean registro = (FicheroResiBean)it.next();
	      	registro.setContador(j); //usado para crear posteriormente el id y que no tenga duplicados, si solo usamos row, se utiliza el mismo en caso de la trazadona
	      	
	      	//si no es tipo LINEA saltamos al siguiente 
	      	if(registro.getTipoRegistro()!=null && !registro.getTipoRegistro().equalsIgnoreCase(SPDConstants.REGISTRO_LINEA)) 
	      		continue;
	      	//int tipoRegistro=ExtReHelper.checkTipoRegistro(registro);
	      	String CIP = StringUtil.replaceInvalidCharsForRobot(registro.getResiCIP());
	      	if (!CIPS_TreeMap.containsKey(CIP)) 
	      	{
	      		// se crea el Patient si no se ha tratado préviamente 
	      		patient = new Patient(Doses_TreeMap);
	      		creaPatient(patient, registro);
	      		//añadimos en treemap
		        CIPS_TreeMap.put(CIP, patient);
			    patients.add(patient);
	      	} 
	      	//recuperamos Patient
	      	patient = (Patient) CIPS_TreeMap.get(CIP);
	
	      	//Un treatment por residente, con todas los medicamentos en el treatment
		    //recorrido por las tomas y creación de los tratamientos
		    boolean hayDose =false;
		    String[] resiTomas = new String[24];
		    
		    System.out.println(" registro.getSpdNombreBolsa() -->  " + registro.getSpdNombreBolsa());
		    
	      	if(!registro.getSpdAccionBolsa().equalsIgnoreCase(SPDConstants.SPD_ACCIONBOLSA_NO_PINTAR) && !registro.getSpdAccionBolsa().equalsIgnoreCase(SPDConstants.SPD_ACCIONBOLSA_SI_PRECISA) )
	      	{
	  	        for (int i = 0; i < 24; i++) 
	  	        {
	  			    
	  			    String fieldName = "getResiToma" + (i + 1);
	  			    //System.out.println(" resiTomas[i] -->  " + i + " " + resiTomas[i]);

	  			    resiTomas[i] = evaluarCampo(fieldName, registro); // Llama a la función evaluarCampo con el nombre del campo y el objeto registro
	  	        	if(checkTomaRegistroNoVacia(resiTomas[i]))
	  	        	{
		  			    //System.out.println(" checkTomaRegistroNoVacia resiTomas[i]-->  " + i + " " + resiTomas[i]);
	  	        		
		  			    //si pertenece a la bolsa OTROS hay que ponerlo en el último registro
		  				/*if(registro.getTipoEnvioHelium().equals("0")) //0 --> Otros (no_pintar, Si_precisa, pauta 0,1)
		  				{
		  					ponerloEnDoseOtros(patient, registro);
		  				}
		  			    */
	  	        		//Line line = creaLine(resiTomas[i], registro);
	  	        		Line line = creaLinePorTipo(patient, resiTomas[i].replace(',', '.'), registro);
	  	        		
	  		        	patient.getTreatments().getList().get(0).getPouches().getList().get(i).getLines().add(line);
	  		        	//idPouch
	  		        	//patient.getTreatments().getList().get(0).getPouches().getList().get(i).setIdPouch(patient.getTreatments().getList().get(0).getCodeTreatment() + "-"+patient.getTreatments().getList().get(0).getPouches().getList().get(i).getDose().getCodeDose());
	  		        	String idPouch= StringUtil.left(patient.getTreatments().getList().get(0).getCodeTreatment(), 25) + "-"+ StringUtil.left(patient.getTreatments().getList().get(0).getPouches().getList().get(i).getDose().getCodeDose(), 25);
	  		        	patient.getTreatments().getList().get(0).getPouches().getList().get(i).setIdPouch(StringUtil.right(idPouch, 50));
	  		        	//IdLine
	  		        	//line.setIdLine(patient.getTreatments().getList().get(0).getCodeTreatment() + "-"+patient.getTreatments().getList().get(0).getPouches().getList().get(i).getDose().getCodeDose()+"-"+registro.getRow()+"_"+registro.getContador());
	  		        	String idLine = StringUtil.left(patient.getTreatments().getList().get(0).getCodeTreatment(), 25) + "-"+patient.getTreatments().getList().get(0).getPouches().getList().get(i).getDose().getCodeDose()+"-"+registro.getRow()+"_"+registro.getContador();
	  		        	line.setIdLine(StringUtil.right(idLine, 50));	//aseguramos que no pase de 50 (máximo Helium)

	  		        	
	  		        	patient.getTreatments().getList().get(0).getPouches().getList().get(i).setActiva(true);
	  		        	hayDose=true;
	    	        	}
	  	        }
	      	}
	
		        	
	      	//Asignamos el NO_PINTAR a la última dose reservada para los tratamientos sin pauta
	      	if(!hayDose)
	      	{
	      		creaDoseOtros(patient, registro, new Line());
	       		hayDose=true;
	      	}
	  	}        
	      
      descartarPouchesInactivas(patients.getList());
	    
      careHome.setPatients(patients);
      return careHome;
  	}

	public static String generaFichero(Center careHome, HttpServletResponse response) 
	{
		String nomFitxer="";
	    try {
	        JAXBContext jaxbContext = JAXBContext.newInstance(Center.class);
	
	        // Crear el marshaller
	        Marshaller marshaller = jaxbContext.createMarshaller();
	
	        // Configurar opciones del marshaller
	        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
	
	        try {
	        	String diaHora = DateUtilities.getDatetoString("ddMMyyyy_Hmm", new Date());
	        	
	        	
	        	String filePath = SPDConstants.PATH_DOCUMENTOS+"/helium/"+diaHora+"_HELIUM_" + careHome.getIdProceso() + ".xml";      
	        	//String filePath = "c://UTILS/"+diaHora+"_HELIUM_" + careHome.getIdProceso() + ".xml";
	        	nomFitxer = diaHora+"_HELIUM_" + careHome.getIdProceso() + ".xml";
	        	File file = new File(filePath);
	
	        	FileOutputStream fos = new FileOutputStream(file);
	        	OutputStreamWriter writer = new OutputStreamWriter(fos, "UTF-8");
	        	marshaller.marshal(careHome, writer);
		        writer.close();

		        
		        response.setContentType("application/xml");
		        response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
		        response.setContentLength((int) file.length());
		        
	            System.out.println("XML generado correctamente");
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
	
	
	
	
	/**
	 * Creación del centro
	 * @param careHome
	 * @param formulari
	 * @throws Exception 
	 */
	private static void creaCenter(String spdUsuario, Center careHome, FicheroResiForm formulari) throws Exception {
		try {
			DivisionResidencia dr = DivisionResidenciaDAO.getDivisionResidenciaById(spdUsuario, formulari.getIdDivisionResidencia());
			String name = "DEFAULT";
			if(dr!=null)
			{
				name = dr.getNombreBolsa();
				if(name==null || name.equals(""))
					name= dr.getLocationId();
			} 
			careHome.setName(name); 

			
		} catch (ClassNotFoundException e) {
			//e.printStackTrace();
			formulari.getErrors().add("Error al poner el nombre de la residencia");
		}
		// careHome.setName(cab.getIdDivisionResidencia());
		careHome.setIdCareHome(formulari.getIdDivisionResidencia());
		careHome.setIdProceso(formulari.getIdProceso());
		//careHome.setCountry("España");
		careHome.setActive(true);
		careHome.setDoses(getDosesTreemapToList(Doses_TreeMap));
	        
		//careHome.setProvince(province);
		careHome.setSections(new ArrayList<Section>());
	}

	/**
	 * Creación de la instancia Patient
	 * @param patient
	 * @param registro
	 * @throws Exception 
	 */
	private static void creaPatient(Patient patient, FicheroResiBean registro) throws Exception 
	{
	 	patient.setActive(true);
	 	//patient.setAdmissionDate(admissionDate);
	 	//patient.setBirthday(birthday);
	 	//patient.setDni(dni);
	 	patient.setHospitalized(false);
	 	patient.setIdPatient(StringUtil.replaceInvalidCharsForRobot(registro.getResiCIP()));
	 	patient.setCip(StringUtil.replaceInvalidCharsForRobot(registro.getResiCIP()));
	 	//patient.setSurname1(surname1);
	 	//patient.setSurname2(surname2);
	 	//patient.setTreatments(new Treatments());
	    //Treatments treatments = new Treatments();
	    //patient.setTreatments(treatments);
	    //Treatment treatment = new Treatment();
	    patient.getTreatments().getList().get(0).setActive(true);
	    patient.getTreatments().getList().get(0).setIdTreatment(registro.getResiCIP()+"-T1");
	    //patient.getTreatments().getList().get(0).setIdTreatment(registro.getResiCIP() + "_" + registro.getSpdCnFinal() + "_" + registro.getRow());
		//trat.setCodeTreatment(registro.getResiCIP() + "_" + registro.getSpdCnFinal() + "_" + registro.getRow());
	    //patient.getTreatments().getList().get(0).setStartTreatment(DateUtilities.convertFormatDateString(registro.getResiInicioTratamiento(), "dd/MM/yyyy", "dd-MM-yyyy HH:mm"));
	    //patient.getTreatments().getList().get(0).setEndTreatment(DateUtilities.convertFormatDateString(registro.getResiFinTratamiento(), "dd/MM/yyyy", "dd-MM-yyyy HH:mm"));
	    patient.getTreatments().getList().get(0).setStartTreatment(DateUtilities.getDateOrDateDefault(registro.getResiInicioTratamiento(), "dd/MM/yyyy", "dd-MM-yyyy HH:mm"));
	    patient.getTreatments().getList().get(0).setEndTreatment(DateUtilities.getDateOrDateDefault(registro.getResiFinTratamiento(), "dd/MM/yyyy", "dd-MM-yyyy HH:mm"));
	 
	    //patient.getTreatments().add(treatment);
	    //patient.getTreatments().getList().get(0).gsetPouches(pouches);
    	//patient.setLastAdmissionDate(lastAdmissionDate);	
	    if(patient!=null && (patient.getName()==null || patient.getName().equals("")))
        	patient.setName(StringUtil.replaceInvalidCharsForRobot(registro.getResiNombrePaciente())); 
        if(patient!=null && (patient.getSurname1()==null || patient.getSurname1().equals("")))
        	patient.setSurname1(StringUtil.replaceInvalidCharsForRobot(registro.getResiApellido1()));
        if(patient!=null && (patient.getSurname2()==null || patient.getSurname2().equals("")))
        	patient.setSurname2(StringUtil.replaceInvalidCharsForRobot(registro.getResiApellido2()));
        
		try {
			PacienteBean pac = PacienteDAO.getPacientePorCIP(registro.getResiCIP());
		 	if(pac!=null)
		 	{
		        patient.setIdPharmacy(pac.getIdPharmacy()!=null?pac.getIdPharmacy().trim():"");
		 		patient.setFloor(pac.getPlanta() !=null?StringUtil.replaceInvalidCharsForRobot(pac.getPlanta()):"");
		 		patient.setRoom(StringUtil.replaceInvalidCharsForRobot(pac.getHabitacion())!=null?pac.getHabitacion():"");
			 	patient.setActive(pac.getSpd().equalsIgnoreCase("S"));
			    patient.setName(StringUtil.replaceInvalidCharsForRobot(pac.getNombre()));
		        patient.setSurname1(StringUtil.replaceInvalidCharsForRobot(pac.getApellido1()));
		        patient.setSurname2(StringUtil.replaceInvalidCharsForRobot(pac.getApellido2()));
		        }
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	
	

	

	/**
	 * Dose encargada de englobar tratamientos SI_PRECISA, NO_PINTAR o sin pauta
	 * @param patient
	 * @param registro
	 * @param line
	 */
	private static void creaDoseOtros(Patient patient, FicheroResiBean registro, Line line) 
	{
		int i = patient.getTreatments().getList().get(0).getPouches().getList().size();
		//line.setIdLine();
		String idLine = StringUtil.left(registro.getResiCIP(), 14) +"-T1-OTROS-" + registro.getRow()+"_"+registro.getContador();
		line.setIdLine(StringUtil.right(idLine, 50));	//aseguramos que no pase de 50 (máximo Helium)
		if(registro.getResiSiPrecisa()!=null && registro.getResiSiPrecisa().equalsIgnoreCase("X"))
			line.setNeeded(true);
		
		line.setActivePeriod(false); 
				
		line.setAmount(new Float("0.01"));	//cantidad //restricción porque no puede ser 0 en Helium
		line.setFrom(DateUtilities.getDate(registro.getResiInicioTratamiento(),  "dd/MM/yyyy", "dd-MM-yyyy HH:mm"));
		line.setTo(DateUtilities.getDateOrDateDefault(registro.getResiFinTratamiento(),  "dd/MM/yyyy", "dd-MM-yyyy HH:mm"));
		controlaFechaBaseTratamiento(patient, registro);
		line.setOutOfBlister(true);
		line.setPill(StringUtil.limpiarTextoTomas(registro.getSpdCnFinal()));
		line.setPill_desc(StringUtil.left(StringUtil.replaceInvalidCharsForRobot(registro.getSpdNombreBolsa()), 120)) ;
  		patient.getTreatments().getList().get(0).getPouches().getList().get(i-1).getLines().add(line);
  		String idPouch= StringUtil.left(patient.getTreatments().getList().get(0).getCodeTreatment(), 25) + "-"+patient.getTreatments().getList().get(0).getPouches().getList().get(i-1).getDose().getCodeDose();
  		patient.getTreatments().getList().get(0).getPouches().getList().get(i-1).setIdPouch(StringUtil.right(idPouch, 50));
  		//patient.getTreatments().getList().get(0).getPouches().getList().get(i-1).setIdPouch(patient.getTreatments().getList().get(0).getCodeTreatment() + "-"+patient.getTreatments().getList().get(0).getPouches().getList().get(i-1).getDose().getCodeDose());
		//line.setIdLine(patient.getTreatments().getList().get(0).getCodeTreatment() + "-"+patient.getTreatments().getList().get(0).getPouches().getList().get(24).getDose().getCodeDose()+"-"+registro.getRow());
		patient.getTreatments().getList().get(0).getPouches().getList().get(i-1).setActiva(true);
	}	
	
	
			/**
		 *   
	     * Detectamos el tipo de envío Helium	
			0 --> Otros (no_pintar, Si_precisa, pauta 0,1)
			1 --> Diario
			2 --> Días concretos
			3 --> Frecuencia
			4 --> Envío guide
		 * @param patient 
		 * @param resiToma
		 * @param registro
		 * @return
		 */

		private static Line creaLinePorTipo(Patient patient, String resiToma, FicheroResiBean registro ) {
			Line line = new Line();
			String idLine = StringUtil.left(registro.getResiCIP(), 14)+"-T1-" + "_" + StringUtil.left(registro.getSpdCnFinal(), 6) + "_" + registro.getRow()+"_"+registro.getContador()+"_"+registro.getContador(); 
			line.setIdLine(StringUtil.right(idLine, 50));	//aseguramos que no pase de 50 (máximo Helium)
			line.setActivePeriod(false); //por defecto NO diaria 

			if(registro.getTipoEnvioHelium().equals("0")) //0 --> Otros (no_pintar, Si_precisa, pauta 0,1)
			{
				creaDoseOtros(patient, registro, line);
			}
			else
			{
				line.setAmount(new Float(resiToma));	//cantidad
				if(registro.getTipoEnvioHelium().equals("1")) //1 --> Diario
				{
			   	    line.setActivePeriod(true); //diaria TO-DO
					line.setFrom(DateUtilities.getDate(registro.getResiInicioTratamiento(),  "dd/MM/yyyy", "dd-MM-yyyy HH:mm"));
					line.setTo(DateUtilities.getDateOrDateDefault(registro.getResiFinTratamiento(),  "dd/MM/yyyy", "dd-MM-yyyy HH:mm"));
					controlaFechaBaseTratamiento(patient, registro);
				}
				if(registro.getTipoEnvioHelium().equals("2") || (registro.getDiasSemanaConcretos()!=null && !registro.getDiasSemanaConcretos().equals(""))) //2 --> Días concretos
				{
	    			//Solo se informa si la medicación se toma ciertos dias de la semana.Esta fecha indica el inicio de la toma, tiene formato dd-mm-yyyy hh:mm.
	    			line.setDayfromWeek(DateUtilities.getDate(registro.getResiInicioTratamiento(),  "dd/MM/yyyy", "dd-MM-yyyy HH:mm"));
	    			//Solo se informa si la medicación se toma ciertos dias de la semana. Esta fecha indica el fin de la toma, tiene formato dd-mm-yyyy hh:mm.
	    			line.setDaytoWeek(DateUtilities.getDateOrDateDefault(registro.getResiFinTratamiento(),  "dd/MM/yyyy", "dd-MM-yyyy HH:mm"));
	    			controlaFechaBaseTratamiento(patient, registro);
	    			//Solo se informa en caso de medicación semanal.
	    			// Indica los dias de la semana en los cuales se tiene que hacer las tomas.El valor de este campo es una cadena de parámetros no repetidos separados 
	    			//por comas que pueden tomar los siguientes valores: {“monday”,”tuesday”,”wednesday”,”thursday”,”friday”,”saturday”,”sunday”}.
	    			line.setWeekdays(registro.getDiasSemanaConcretos());
	    			line.setActivePeriod(false); //diaria TO-DO
				}
				if(registro.getTipoEnvioHelium().equals("3") || (registro.getDiasMesConcretos()!=null && !registro.getDiasMesConcretos().equals("")))//3 --> Frecuencia
				{
		  			//Solo se informa si la medicación se toma ciertos dias del mes. Esta fecha indica el fin de la toma, tiene formato dd-mm-yyyy hh:mm
	    			line.setDayfromMonth(DateUtilities.getDate(registro.getResiInicioTratamiento(),  "dd/MM/yyyy", "dd-MM-yyyy HH:mm"));
	    			//Solo se informa si la medicación se toma ciertos dias del mes. Esta fecha indica el inicio de la toma, tiene formato dd-mm-yyyy hh:mm
	    			line.setDaytoMonth(DateUtilities.getDateOrDateDefault(registro.getResiFinTratamiento(),  "dd/MM/yyyy", "dd-MM-yyyy HH:mm"));
	    			controlaFechaBaseTratamiento(patient, registro);
	    			//buscamos día mensual y en caso que no exista ponemos e1 "1" por defecto.
	    			String diasMesDefecto =registro.getDiasMesConcretos();
	    			if(registro.getDiasMesConcretos()==null || registro.getDiasMesConcretos().equals(""))
	    				diasMesDefecto ="1";
	    			//if(registro.getResiFrecuencia()==15) diasMesDefecto ="1, 15";
	    			if(registro.getResiFrecuencia()==15 && (diasMesDefecto==null || diasMesDefecto.equals(""))) diasMesDefecto ="1, 15";
	    			//Solo se informa en caso de medicación mensual.
	    			line.setMonthdays(String.valueOf(diasMesDefecto));
				}
				if(registro.getTipoEnvioHelium().equals("4"))	//4 --> Envío guide
				{
					line.setActivePeriod(true); //diaria TO-DO
					line.setFrom(DateUtilities.getDate(registro.getResiInicioTratamiento(),  "dd/MM/yyyy", "dd-MM-yyyy HH:mm"));
					line.setTo(DateUtilities.getDateOrDateDefault(registro.getResiFinTratamiento(),  "dd/MM/yyyy", "dd-MM-yyyy HH:mm"));
					controlaFechaBaseTratamiento(patient, registro);
			   	    String secuenciaGuide=registro.getSecuenciaGuide();
			   	    
			   	    String[] partesSecuencia =secuenciaGuide.split("-");
	
			   	    for (int i = 0; i < partesSecuencia.length-1; i+=2) {
				   	    String elemento = partesSecuencia[i];
				   	    if(elemento!=null && !elemento.equals("")&& !elemento.equals("null"))
				   	    {
					   	    Guide guideStart = new Guide();
						   	guideStart.setIdGuide(line.getId() + "-" + i+1);
						   	guideStart.setNumber(i+1);
						   	guideStart.setPeriods(new Integer(elemento).intValue());
						   	guideStart.setPeriodtype(partesSecuencia[i+2]);
						   	guideStart.setActive(true);
					   	    Guide guideStop = new Guide();
					   	    guideStop.setIdGuide(line.getId() + "-" + i+2);
					   	    guideStop.setNumber(i+2);
					   	    guideStop.setPeriods(new Integer(partesSecuencia[i+1]).intValue());
					   	    guideStop.setPeriodtype(partesSecuencia[i+2]);
					   	    guideStop.setActive(false);
					   	    line.getGuides().add(guideStart);
					   		line.getGuides().add(guideStop);		   	    	
				   	    }
				   	}
					
				}
	    		line.setIrreplaceblePill(false);
	    		//line.setNeeded(registro.getResiSiPrecisa()!=null && registro.getResiSiPrecisa().equalsIgnoreCase("X"));
	    		line.setOutOfBlister(registro.getSpdAccionBolsa()!=null && !registro.getSpdAccionBolsa().equalsIgnoreCase(SPDConstants.SPD_ACCIONBOLSA_PASTILLERO));
	    		//line.setPill(registro.getSpdCnFinal());
	    		line.setPill(StringUtil.limpiarTextoTomas(registro.getSpdCnFinal()));

	    		line.setPill_desc(StringUtil.left(StringUtil.replaceInvalidCharsForRobot(registro.getSpdNombreBolsa()), 120));
			}

			return line;
		}	
	
		private static Line creaLinePorTipoORI(Patient patient, String resiToma, FicheroResiBean registro ) {
			Line line = new Line();
			line.setIdLine(registro.getResiCIP()+"-T1-" + "_" + registro.getSpdCnFinal() + "_" + registro.getRow());
			line.setActivePeriod(true); //diaria TO-DO

			if(registro.getTipoEnvioHelium().equals("0")) //0 --> Otros (no_pintar, Si_precisa, pauta 0,1)
			{
				creaDoseOtros(patient, registro, line);
			}
			else if(registro.getTipoEnvioHelium().equals("4"))	//4 --> Envío guide
			{
		   	    line.setActivePeriod(true); //diaria TO-DO
				line.setFrom(DateUtilities.getDate(registro.getResiInicioTratamiento(),  "dd/MM/yyyy", "dd-MM-yyyy HH:mm"));
				line.setTo(DateUtilities.getDateOrDateDefault(registro.getResiFinTratamiento(),  "dd/MM/yyyy", "dd-MM-yyyy HH:mm"));
				controlaFechaBaseTratamiento(patient, registro);
		   	    String secuenciaGuide=registro.getSecuenciaGuide();
		   	    
		   	    String[] partesSecuencia =secuenciaGuide.split("-");

		   	    for (int i = 0; i < partesSecuencia.length; i+=3) {
			   	    String elemento = partesSecuencia[i];
			   	    if(elemento!=null && !elemento.equals("")&& !elemento.equals("null"))
			   	    {
				   	    Guide guideStart = new Guide();
					   	guideStart.setIdGuide(line.getId() + "-" + i+1);
					   	guideStart.setNumber(i+1);
					   	guideStart.setPeriods(new Integer(elemento).intValue());
					   	guideStart.setPeriodtype(partesSecuencia[i+2]);
					   	guideStart.setActive(true);
				   	    Guide guideStop = new Guide();
				   	    guideStop.setIdGuide(line.getId() + "-" + i+2);
				   	    guideStop.setNumber(i+2);
				   	    guideStop.setPeriods(new Integer(partesSecuencia[i+1]).intValue());
				   	    guideStop.setPeriodtype(partesSecuencia[i+2]);
				   	    guideStop.setActive(false);
				   	    line.getGuides().add(guideStart);
				   		line.getGuides().add(guideStop);		   	    	
			   	    }
				   	    

			   	}
				
			}
			else 
			{
				line.setAmount(new Float(resiToma));	//cantidad
				
				if(registro.getTipoEnvioHelium().equals("1")) //1 --> Diario
				{
					line.setFrom(DateUtilities.getDate(registro.getResiInicioTratamiento(),  "dd/MM/yyyy", "dd-MM-yyyy HH:mm"));
					line.setTo(DateUtilities.getDateOrDateDefault(registro.getResiFinTratamiento(),  "dd/MM/yyyy", "dd-MM-yyyy HH:mm"));
					controlaFechaBaseTratamiento(patient, registro);
				}
				else if(registro.getTipoEnvioHelium().equals("2")) //2 --> Días concretos
				{
	    			//Solo se informa si la medicación se toma ciertos dias de la semana.Esta fecha indica el inicio de la toma, tiene formato dd-mm-yyyy hh:mm.
	    			line.setDayfromWeek(DateUtilities.getDate(registro.getResiInicioTratamiento(),  "dd/MM/yyyy", "dd-MM-yyyy HH:mm"));
	    			//Solo se informa si la medicación se toma ciertos dias de la semana. Esta fecha indica el fin de la toma, tiene formato dd-mm-yyyy hh:mm.
	    			line.setDaytoWeek(DateUtilities.getDateOrDateDefault(registro.getResiFinTratamiento(),  "dd/MM/yyyy", "dd-MM-yyyy HH:mm"));
	    			controlaFechaBaseTratamiento(patient, registro);
	    			//Solo se informa en caso de medicación semanal.
	    			// Indica los dias de la semana en los cuales se tiene que hacer las tomas.El valor de este campo es una cadena de parámetros no repetidos separados 
	    			//por comas que pueden tomar los siguientes valores: {“monday”,”tuesday”,”wednesday”,”thursday”,”friday”,”saturday”,”sunday”}.
	    			line.setWeekdays(registro.getDiasSemanaConcretos());
	    			line.setActivePeriod(false); //diaria TO-DO

				}
				else if(registro.getTipoEnvioHelium().equals("3")) //3 --> Frecuencia
				{
		  			//Solo se informa si la medicación se toma ciertos dias del mes. Esta fecha indica el fin de la toma, tiene formato dd-mm-yyyy hh:mm
	    			line.setDayfromMonth(DateUtilities.getDate(registro.getResiInicioTratamiento(),  "dd/MM/yyyy", "dd-MM-yyyy HH:mm"));
	    			//Solo se informa si la medicación se toma ciertos dias del mes. Esta fecha indica el inicio de la toma, tiene formato dd-mm-yyyy hh:mm
	    			line.setDaytoMonth(DateUtilities.getDateOrDateDefault(registro.getResiFinTratamiento(),  "dd/MM/yyyy", "dd-MM-yyyy HH:mm"));
	    			controlaFechaBaseTratamiento(patient, registro);
	    			//buscamos día mensual y en caso que no exista ponemos e1 "1" por defecto.
	    			String diasMesDefecto =registro.getDiasMesConcretos();
	    			if(registro.getDiasMesConcretos()==null || registro.getDiasMesConcretos().equals(""))
	    				diasMesDefecto ="1";
	    			if(registro.getResiFrecuencia()==15) diasMesDefecto ="1, 15";
	    			//Solo se informa en caso de medicación mensual.
	    			line.setActivePeriod(false); //diaria TO-DO
	    			line.setMonthdays(String.valueOf(diasMesDefecto));
				}
		
	    		//line.setGuides(guides);
	    		line.setIrreplaceblePill(false);
	    		
	    		//line.setNeeded(registro.getResiSiPrecisa()!=null && registro.getResiSiPrecisa().equalsIgnoreCase("X"));
	    		line.setOutOfBlister(registro.getSpdAccionBolsa()!=null && !registro.getSpdAccionBolsa().equalsIgnoreCase(SPDConstants.SPD_ACCIONBOLSA_PASTILLERO));
	    		//line.setPill(registro.getSpdCnFinal());
	    		line.setPill(StringUtil.limpiarTextoTomas(registro.getSpdCnFinal()));
	    		
	    		line.setPill_desc(StringUtil.left(registro.getSpdNombreBolsa(), 120));

			}
			
			return line;
		}	
	

	public static List getTomasCabecera(FicheroResiBean medResi) {
		
		List<String> doses =new ArrayList<String>();
		int numeroDoses=0;
		
		try{
			//numeroDoses=new Integer(medResi.getResultLog()).intValue();
			numeroDoses=medResi.getNumeroDeTomas();
			if(numeroDoses>=1)  doses.add(medResi.getResiToma1()); // 
			if(numeroDoses>=2)  doses.add(medResi.getResiToma2()); //
			if(numeroDoses>=3)  doses.add(medResi.getResiToma3()); // 
			if(numeroDoses>=4)  doses.add(medResi.getResiToma4()); // 
			if(numeroDoses>=5)  doses.add(medResi.getResiToma5()); // 
			if(numeroDoses>=6)  doses.add(medResi.getResiToma6()); // 
			if(numeroDoses>=7)  doses.add(medResi.getResiToma7()); // 
			if(numeroDoses>=8)  doses.add(medResi.getResiToma8()); // 
			if(numeroDoses>=9)  doses.add(medResi.getResiToma9()); // 
			if(numeroDoses>=10)  doses.add(medResi.getResiToma10()); //		
			if(numeroDoses>=11)  doses.add(medResi.getResiToma11()); // 
			if(numeroDoses>=12)  doses.add(medResi.getResiToma12()); //
			if(numeroDoses>=13)  doses.add(medResi.getResiToma13()); // 
			if(numeroDoses>=14)  doses.add(medResi.getResiToma14()); // 
			if(numeroDoses>=15)  doses.add(medResi.getResiToma15()); // 
			if(numeroDoses>=16)  doses.add(medResi.getResiToma16()); // 
			if(numeroDoses>=17)  doses.add(medResi.getResiToma17()); // 
			if(numeroDoses>=18)  doses.add(medResi.getResiToma18()); // 
			if(numeroDoses>=19)  doses.add(medResi.getResiToma19()); // 
			if(numeroDoses>=20)  doses.add(medResi.getResiToma20()); //		
			if(numeroDoses>=21)  doses.add(medResi.getResiToma21()); // 
			if(numeroDoses>=22)  doses.add(medResi.getResiToma22()); //
			if(numeroDoses>=23)  doses.add(medResi.getResiToma23()); // 
			if(numeroDoses>=24)  doses.add(medResi.getResiToma24()); // 
		}catch(Exception e){
			
		}
		
		return doses;
	}


	public static TreeMap<Integer, Dose> getDoses(FicheroResiBean medResi) {
		  
		TreeMap<Integer, Dose> doses =new TreeMap<Integer, Dose>();
		int numeroDoses=0;
		
		try{
			//numeroDoses=new Integer(medResi.getResultLog()).intValue();
			numeroDoses=medResi.getNumeroDeTomas();
			
		
			if(numeroDoses>=1)  doses.put(1, DoseDAO.getTomaCabecera(medResi.getIdDivisionResidencia(), medResi.getResiToma1(), 0, 1)); //
			if(numeroDoses>=2)  doses.put(2, DoseDAO.getTomaCabecera(medResi.getIdDivisionResidencia(), medResi.getResiToma2(), 0, 2)); //
			if(numeroDoses>=3)  doses.put(3, DoseDAO.getTomaCabecera(medResi.getIdDivisionResidencia(), medResi.getResiToma3(), 0, 3)); //
			if(numeroDoses>=4)  doses.put(4, DoseDAO.getTomaCabecera(medResi.getIdDivisionResidencia(), medResi.getResiToma4(), 0, 4)); //
			if(numeroDoses>=5)  doses.put(5, DoseDAO.getTomaCabecera(medResi.getIdDivisionResidencia(), medResi.getResiToma5(), 0, 5)); //
			if(numeroDoses>=6)  doses.put(6, DoseDAO.getTomaCabecera(medResi.getIdDivisionResidencia(), medResi.getResiToma6(), 0, 6)); //
			if(numeroDoses>=7)  doses.put(7, DoseDAO.getTomaCabecera(medResi.getIdDivisionResidencia(), medResi.getResiToma7(), 0, 7)); //
			if(numeroDoses>=8)  doses.put(8, DoseDAO.getTomaCabecera(medResi.getIdDivisionResidencia(), medResi.getResiToma8(), 0, 8)); //
			if(numeroDoses>=9)  doses.put(9, DoseDAO.getTomaCabecera(medResi.getIdDivisionResidencia(), medResi.getResiToma9(), 0, 9)); //
			if(numeroDoses>=10)  doses.put(10, DoseDAO.getTomaCabecera(medResi.getIdDivisionResidencia(), medResi.getResiToma10(), 0, 10)); //
			if(numeroDoses>=11)  doses.put(11, DoseDAO.getTomaCabecera(medResi.getIdDivisionResidencia(), medResi.getResiToma11(), 0, 11)); //
			if(numeroDoses>=12)  doses.put(12, DoseDAO.getTomaCabecera(medResi.getIdDivisionResidencia(), medResi.getResiToma12(), 0, 12)); //
			if(numeroDoses>=13)  doses.put(13, DoseDAO.getTomaCabecera(medResi.getIdDivisionResidencia(), medResi.getResiToma13(), 0, 13)); //
			if(numeroDoses>=14)  doses.put(14, DoseDAO.getTomaCabecera(medResi.getIdDivisionResidencia(), medResi.getResiToma14(), 0, 14)); //
			if(numeroDoses>=15)  doses.put(15, DoseDAO.getTomaCabecera(medResi.getIdDivisionResidencia(), medResi.getResiToma15(), 0, 15)); //
			if(numeroDoses>=16)  doses.put(16, DoseDAO.getTomaCabecera(medResi.getIdDivisionResidencia(), medResi.getResiToma16(), 0, 16)); //
			if(numeroDoses>=17)  doses.put(17, DoseDAO.getTomaCabecera(medResi.getIdDivisionResidencia(), medResi.getResiToma17(), 0, 17)); //
			if(numeroDoses>=18)  doses.put(18, DoseDAO.getTomaCabecera(medResi.getIdDivisionResidencia(), medResi.getResiToma18(), 0, 18)); //
			if(numeroDoses>=19)  doses.put(19, DoseDAO.getTomaCabecera(medResi.getIdDivisionResidencia(), medResi.getResiToma19(), 0, 19)); //
			if(numeroDoses>=20)  doses.put(20, DoseDAO.getTomaCabecera(medResi.getIdDivisionResidencia(), medResi.getResiToma20(), 0, 20)); //
			if(numeroDoses>=21)  doses.put(21, DoseDAO.getTomaCabecera(medResi.getIdDivisionResidencia(), medResi.getResiToma21(), 0, 21)); //
			if(numeroDoses>=22)  doses.put(22, DoseDAO.getTomaCabecera(medResi.getIdDivisionResidencia(), medResi.getResiToma22(), 0, 22)); //
			if(numeroDoses>=23)  doses.put(23, DoseDAO.getTomaCabecera(medResi.getIdDivisionResidencia(), medResi.getResiToma23(), 0, 23)); //
			if(numeroDoses>=24)  doses.put(24, DoseDAO.getTomaCabecera(medResi.getIdDivisionResidencia(), medResi.getResiToma24(), 0, 24)); //
			
			//if(numeroDoses>=1)  doses.put("1", new Dose(medResi.getResiToma1(), medResi.getResiToma1(), medResi.getResiToma1()) ); // 
			doses.put(99, DoseDAO.generaDoseNoPintar(medResi.getIdDivisionResidencia(), 0)); //dose fantasma para albergar NO_PINTAR
		}catch(Exception e){
			
		}
		return doses;
	}
	
	
	public static List<Dose> getDosesTreemapToList(TreeMap<?, ?> tDoses) {
	    // Convertimos el TreeMap a List
	    Collection<?> c = tDoses.values();
	    Iterator<?> itr = c.iterator();
	    List<Dose> list = new ArrayList<Dose>();
	    while (itr.hasNext()) {
	    	Dose dose = (Dose)itr.next();
	    	list.add(dose);
	    }
	
		return list;
	}
	
	
	public static boolean checkTomaRegistroNoVacia(String resiToma) {
		boolean result = false;
		if(resiToma!=null &&  DataUtil.isNumeroGreatherThanZero(resiToma))
			result=true;
		
		return result;
	}
	
	
	public static void descartarPouchesInactivas(List<Patient> list) {
		Iterator<Patient> it =list.iterator();
		while (it.hasNext())
		{
			Patient patient = (Patient)it.next();
			List<?> pouches = patient.getTreatments().getList().get(0).getPouches().getList();
			Iterator<?> ip =pouches.iterator();
			while (ip.hasNext())
			{
				Pouch pouch = (Pouch) ip.next(); 
				if(!pouch.isActiva()) 
					//patient.getTreatments().getList().get(0).getPouches().getList().remove(pouch);
					ip.remove();
			}
		}
		
	}
	

	
	/**
	 * Método para saber qué tipo de tratamiento es: Diario, Quincenal, Mensual,....
	 * @param registro
	 * @return 0 Diario
	 */
	
	public static List checkTipoRegistro(FicheroResiBean registro) 
	{
		int nDias=0;
		ArrayList<String> dias = new ArrayList<>();
		if(registro.getResiD1()!=null&& registro.getResiD1().equalsIgnoreCase("X")) dias.add("monday"); 
		if(registro.getResiD2()!=null&& registro.getResiD2().equalsIgnoreCase("X")) dias.add("tuesday"); 
		if(registro.getResiD3()!=null&& registro.getResiD3().equalsIgnoreCase("X")) dias.add("wednesday");
		if(registro.getResiD4()!=null&& registro.getResiD4().equalsIgnoreCase("X")) dias.add("thursday");
		if(registro.getResiD5()!=null&& registro.getResiD5().equalsIgnoreCase("X")) dias.add("friday");
		if(registro.getResiD6()!=null&& registro.getResiD6().equalsIgnoreCase("X")) dias.add("saturday");
		if(registro.getResiD7()!=null&& registro.getResiD7().equalsIgnoreCase("X")) dias.add("sunday");
		
	
				
		return dias;
	
		}
	
	/*
	public static FicheroResiBean recuperaDatosAnteriores(FicheroResiBean medResiActual) throws ClassNotFoundException, SQLException {

		return HelperSPD.recuperaDatosAnteriores(getSpdUsuario(), medResiActual);
	}
	*/
	public static String evaluarCampo(String fieldName, Object object) {
	    try {
	        Method method = object.getClass().getMethod(fieldName);
	        Object result = method.invoke(object);
	        if (result != null) {
	            return result.toString();
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return null;
	}
	
	
	private static void controlaFechaBaseTratamiento(Patient patient, FicheroResiBean registro) {
		String iniTrat = patient.getTreatments().getList().get(0).getStartTreat();
		String finTrat = patient.getTreatments().getList().get(0).getEndTreat();
		String iniTratLine = DateUtilities.getDateOrDateDefault(registro.getResiInicioTratamiento(),  "dd/MM/yyyy", "dd-MM-yyyy HH:mm");
		String finTratLine = DateUtilities.getDateOrDateDefault(registro.getResiFinTratamiento(),   "dd/MM/yyyy", "dd-MM-yyyy HH:mm");
	
		
		if(DateUtilities.getLengthInDays(DateUtilities.getDate(iniTratLine, "dd-MM-yyyy HH:mm"), DateUtilities.getDate(iniTrat,  "dd-MM-yyyy HH:mm"))>0)
			patient.getTreatments().getList().get(0).setStartTreatment(iniTratLine);
		
		if(finTrat!= null && !finTrat.equals("")) //en caso que si que exista fecha fin. en caso contrario se deja tal cual
		{
			if(DateUtilities.getLengthInDays(DateUtilities.getDate(finTrat, "dd-MM-yyyy HH:mm"), DateUtilities.getDate(finTratLine,  "dd-MM-yyyy HH:mm"))>0)
				patient.getTreatments().getList().get(0).setEndTreatment(finTratLine);
		}
			
		
	}

	

	/**
	 ** Detectamos el tipo de envío Helium	
			0 --> Otros (no_pintar, Si_precisa, pauta 0,1)
			1 --> Diario
			2 --> Días concretos
			3 --> Frecuencia
			4 --> Envío guide
	 * @param b
	 * @return
	 */
	public static String getTipoEnvioHelium(FicheroResiForm b) {
	
			FicheroResiBean beanActual = null;
			String  tipoEnvioHelium = ""; 
			try 
			{
				beanActual = b.getFicheroResiDetalleBean();
				tipoEnvioHelium = beanActual.getTipoEnvioHelium();
				
				
				if(HeliumHelper.detectarDiasMarcados(b)!=null && !HeliumHelper.detectarDiasMarcados(b).equals("") && !HeliumHelper.detectarDiasMarcados(b).equals("null"))
					tipoEnvioHelium ="2";
				if(b.getDiasMesConcretos()!=null && !b.getDiasMesConcretos().equals("")&& !b.getDiasMesConcretos().equals("null"))
					tipoEnvioHelium ="3";
				if(b.getSecuenciaGuide()!=null && !b.getSecuenciaGuide().equals("")&& !b.getSecuenciaGuide().equals("null"))
					tipoEnvioHelium ="4";
				if(b.getSpdAccionBolsa().contentEquals(SPDConstants.SPD_ACCIONBOLSA_NO_PINTAR) || b.getSpdAccionBolsa().equalsIgnoreCase(SPDConstants.SPD_ACCIONBOLSA_SI_PRECISA) )
					tipoEnvioHelium ="0";
		}
			catch(Exception e)
			{
			}
			return tipoEnvioHelium;
			
				
			
		}
	

	public static String detectarDiasMarcados(FicheroResiForm form) {
		String result="";
		List<String> dias=new ArrayList<String>();
		if(HelperSPD.diaMarcado(form.getResiD1())) dias.add("monday");
		if(HelperSPD.diaMarcado(form.getResiD2())) dias.add("tuesday");
		if(HelperSPD.diaMarcado(form.getResiD3())) dias.add("wednesday");
		if(HelperSPD.diaMarcado(form.getResiD4())) dias.add("thursday");
		if(HelperSPD.diaMarcado(form.getResiD5())) dias.add("friday");
		if(HelperSPD.diaMarcado(form.getResiD6())) dias.add("saturday");
		if(HelperSPD.diaMarcado(form.getResiD7())) dias.add("sunday");
		if(dias!=null&&dias.size()>0) 
			result= String.join(",", dias);
		return result;
	}
	
	
}



	
	
	
	
	
	
	
	