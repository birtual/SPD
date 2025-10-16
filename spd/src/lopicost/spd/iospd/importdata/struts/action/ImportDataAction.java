package lopicost.spd.iospd.importdata.struts.action;


import lopicost.config.logger.Logger;
import lopicost.spd.iospd.ProcessLogging;
import lopicost.spd.iospd.importdata.process.ImportDataThread;
import lopicost.spd.iospd.importdata.struts.form.ImportDataForm;
import lopicost.spd.iospd.model.IOSpdConnector;
import lopicost.spd.iospd.model.IOSpdProcess;
import lopicost.spd.iospd.persistence.IOSpdConnectorDAO;
import lopicost.spd.iospd.persistence.IOSpdProcessDAO;
import lopicost.spd.model.DivisionResidencia;
import lopicost.spd.persistence.DivisionResidenciaDAO;
import lopicost.spd.utils.DateUtilities;
import lopicost.spd.utils.SPDConstants;
import lopicost.spd.utils.TextManager;
import lopicost.spd.utils.beanUpload;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;



public class ImportDataAction extends DispatchAction 
{
    public ActionForward procesar(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception  
    {
    	
        HttpSession session = request.getSession();
        ActionForward actionForward=null;

        String spdUsuario = (String) session.getAttribute("idUsuario");
        //TO-DO - Usarlo en un futuro
  
        String goMapping="list";
        List errors = new ArrayList();
        ImportDataForm formulario=(ImportDataForm) form;
        formulario.setErrors(errors);
        formulario.setIdUsuario(spdUsuario);
        String type="IMPORT";
        formulario.setEnd("WAITING_FILE");
        String operation = formulario.getOperation();

        
        List lst1=IOSpdProcessDAO.findProcess(type);
        List lst2=IOSpdConnectorDAO.findReaders();
        formulario.setProcess(lst1);
        formulario.setReaders(lst2);
        
        String exportType= formulario.getExportType();
        String readerType=formulario.getFileType();
         
        //lista de resis
        List lstDivisionResidencias= DivisionResidenciaDAO.getListaDivisionResidencias(spdUsuario);
        formulario.setLstDivisionResidencias(lstDivisionResidencias);

        //resi escogida para la importación
        DivisionResidencia resiElegida = DivisionResidenciaDAO.getDivisionResidenciaById(spdUsuario, formulario.getIdDivisionResidencia());
        String idDivisionResidencia= resiElegida.getIdDivisionResidencia();
        //proceso asociado
        String idProcessIospd=formulario.getIdProcessIospd(); 
        
        if (operation.equals("FILTER")) 
        	idProcessIospd=resiElegida.getIdProcessIospd();
        	
        //String idProcessIospd=formulario.getIdProcessIospd(); 
        //if(idProcessIospd==null || !(idProcessIospd!=null && idProcessIospd.equalsIgnoreCase("importPlantillaXLS") ))  
        //	idProcessIospd=resiElegida.getIdProcessIospd();
        
        FormFile file = formulario.getFile();
        ImportDataThread proc=new ImportDataThread();
        
        Long milis = new Date().getTime();
        String fechaInicioSpd = formulario.getFechaInicioSpd();
        String fechaFinSpd = formulario.getFechaFinSpd();
        String idProceso= "";
       // String idProcessIospd
        
        //INICIO miramos si es una carga anexa en un proyecto ya existente --> cambio de variables 
        boolean cargaExtra = formulario.isCargaExtra();
        
         
        //boolean cargaAnexa = cargaAnexaEnProyectoExistente(request.getParameter("idDivisionResidencia"), request.getParameter("idProceso"));
        //boolean cargaExtra = formulario.isCargaExtra();
        //if(cargaExtra) cargaAnexa=true; 
        if(cargaExtra)
        {
        //	formulario.setCargaExtra(true);
        	formulario.setIdProceso(idProceso);
        	idProceso= request.getParameter("idProceso");
        	formulario.setIdProceso(idProceso);
        	idDivisionResidencia= request.getParameter("idDivisionResidencia");
        	DivisionResidencia div = DivisionResidenciaDAO.getDivisionResidenciaById(spdUsuario, idDivisionResidencia);
        	formulario.setIdDivisionResidencia(idDivisionResidencia);
        	idProcessIospd=div.getIdProcessIospd();
        	formulario.setFechaInicioSpd(getFechaDesdeDelProceso(idDivisionResidencia, idProceso));
        	formulario.setFechaFinSpd(getFechaHastaDelProceso(idDivisionResidencia, idProceso));
        	
        	goMapping="addInProcess";
        }

        //FIN carga anexa

        formulario.setIdDivisionResidencia(idDivisionResidencia);
        formulario.setIdProcessIospd(idProcessIospd);
        IOSpdProcess procSelected= IOSpdProcessDAO.findByFields(type, idProcessIospd);
        formulario.setDescProcess(procSelected!=null?procSelected.getDescription():"");
        //buscamos diferentes tipos
        String idreader=null;
       
        //lector importación
        IOSpdConnector readerSelected= IOSpdConnectorDAO.findByFields(idreader, readerType);
        formulario.setDescReader(readerSelected.getDescription());
        
        try 
        {    
        	/*
        	 * if (operation.equals("EXPORT_ERRORES"))
        	{
    	        errors=(List) request.getAttribute("listaErrores");
    	        
    	        // Crear un libro de trabajo de Excel (XSSFWorkbook)
    	        Workbook workbook = new XSSFWorkbook();
    	        Sheet sheet = workbook.createSheet("Errores de Importación");

    	        // Crea el encabezado de la tabla en Excel
    	        Row headerRow = sheet.createRow(0);
    	        headerRow.createCell(0).setCellValue("Mensaje de Error");

    	        // Llenar la tabla con los datos de la lista de errores
    	        int rowNum = 1;
    	        for (int i = 0; i < errors.size(); i++) {
    	            Row row = sheet.createRow(rowNum++);
    	            row.createCell(0).setCellValue((double) errors.get(i));
    				
    			}
    	        // Configurar el tipo de contenido y el encabezado de respuesta para el archivo Excel
    	        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    	        response.setHeader("Content-Disposition", "attachment; filename=errores_importacion.xlsx");

    	        // Enviar el archivo Excel al cliente
    	        try (OutputStream outputStream = response.getOutputStream()) {
    	            workbook.write(outputStream);
    	        }
       	}*/
            if (operation.equals("PLANT_UNIF")) 
            {
            	formulario.setIdProcessIospd("importPlantillaXLS");
                actionForward = mapping.findForward(goMapping);
                
            }          
            if (operation.equals("FILTER")) 
            {
                actionForward = mapping.findForward(goMapping);
                
            }
    	    if (operation.equals("GENERATE")) 
            {
    	    	
	        	//comprobamos que se han introducido fechas para generar el idProceso
        		if(!cargaExtra)
        			idProceso=construyeProceso(formulario);
        		if(idProceso == null || errors.size()>0)
        		{
        		
                    return mapping.findForward(goMapping);
        		}
        		else if(file!=null )
				{     
        			//formulario.setIdProceso("idProceso");
        			formulario.setIdProceso(idProceso);

	        				
		        		StringBuffer sbAux= new StringBuffer();
						beanUpload upload = new beanUpload();
						int uploaded = upload.UploadImage(file,sbAux,true, resiElegida);
						
						switch(uploaded)
						{
							case 1: break;
							case 2: errors.add(TextManager.getMensaje("ImportData.error.extension"));
                                    formulario.setErrors(errors);
                                    return mapping.findForward("inprocess");
							case 3: errors.add(TextManager.getMensaje("ImportData.error.size")); 
                                    formulario.setErrors(errors);   
                                    return mapping.findForward("inprocess");
							case 4: errors.add(TextManager.getMensaje("ImportData.error.nofound"));
                                    formulario.setErrors(errors);
                                    return mapping.findForward("inprocess");
							default: errors.add(TextManager.getMensaje("ImportData.error.error"));
							        formulario.setErrors(errors);
                                    return mapping.findForward("inprocess");
						}
                        
                        try
                        {
                            // Obtenim el nom de la classe que implementarà el procès d'importació
                            IOSpdProcess p=(IOSpdProcess)IOSpdProcessDAO.findByFields(exportType, idProcessIospd);
                            String classNameProcess=p.getClassname();
                            //Obtenim el nom de la classe que implementarà el connector de lectura.
                            IOSpdConnector r=(IOSpdConnector)IOSpdConnectorDAO.findById(readerType);
                            String classNameReader=r.getClassname();
                            
                            String path = SPDConstants.PATH_DOCUMENTOS+"/"+sbAux.toString();                           
                            String idThread= "thread"+exportType+ new Date().getTime();
                            session.setAttribute(idThread,proc);
                            formulario.setIdThread(idThread);
                            
                            // si el sistema esta configurado para hacer log del iospd 
                            // creamos un objeto ProcessLoging que se encargara de ello
                            ProcessLogging log = null;
                            
                            if ( isLoggingEnabled() )
                            {
                             	log = new ProcessLogging(p.getIdprocess(), file.getFileName());                          
                            }
                            
                            // Inicialitzem el thread indicant-li quin serà el procés d'importació
                            // i quin serà el format de lectura.
                            if (proc.initialize(spdUsuario,  formulario, idProceso, null, idDivisionResidencia, path, null,classNameProcess,classNameReader, log, cargaExtra)){
                                 proc.start();
                                formulario.setOperation("THREADING");
                            }
                            else {
                                proc.setStatus(3);
                                formulario.setEnd("FINALIZED_KO");
                            }
                            actionForward = mapping.findForward("inprocess");
                            if(cargaExtra) actionForward =  mapping.findForward("addExists"); //en caso de carga anexa
                        }
                        catch (Exception e)
                        {
                        	e.printStackTrace();
                            errors.add(e.getMessage());
                            formulario.setErrors(errors);
                            actionForward = mapping.findForward("inprocess");
                            if(cargaExtra) actionForward =  mapping.findForward("addExists"); //en caso de carga anexa
                            return actionForward;
                        }
					}
					else
                        actionForward = mapping.findForward("error");
                }
                if (operation.equals("THREADING")) {
                    proc=(ImportDataThread)session.getAttribute(formulario.getIdThread());
                    Logger.get().debug("Fila:"+proc.getFilesProcessades()+", Fitxer:"+file.getFileName());
                    formulario.setFilenameIn(file.getFileName());
                    formulario.setFilesProcessades(proc.getFilesProcessades());
                    if (proc.checkStatus()==ImportDataThread.FINALIZED_KO)
                    {
                        formulario.setEnd("FINALIZED_KO");
                        errors=proc.getErrors();
                        formulario.setErrors(errors);
                    }
                    else if ((proc.checkStatus()==ImportDataThread.FINALIZED_OK) && (proc.getErrors().size()==0))
                        formulario.setEnd("FINALIZED_OK");
                    else if ((proc.checkStatus()==ImportDataThread.FINALIZED_OK) && (proc.getErrors().size()>0))
                    {
                        formulario.setEnd("FINALIZED_OKKO");
                        errors=proc.getErrors();
                        formulario.setErrors(errors);
                    }
                    actionForward = mapping.findForward("inprocess");
                    if(cargaExtra) actionForward =  mapping.findForward("addExists");
                    return actionForward;
                }
    	    }
    	    catch (Exception e) 
    	    {
    	    	e.printStackTrace();
    			request.setAttribute("exception", e );
                actionForward = mapping.findForward("error");
    		}
            return actionForward;
    }

    public ActionForward list(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception  
    {
        ImportDataForm formulario=(ImportDataForm) form;

    	formulario.setCargaExtra(false);
    	return procesar(mapping, formulario, request, response);
      }
    
    
    public ActionForward listAux(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception  
    {
        ImportDataForm formulario=(ImportDataForm) form;

    	formulario.setCargaExtra(true);
    	return procesar(mapping, formulario, request, response);
      }
  /*  
    private boolean cargaAnexaEnProyectoExistente(String idDivisionResidencia, String idProceso) {
		if(idDivisionResidencia!=null && !idDivisionResidencia.equals("") && idProceso!=null && !idProceso.equals(""))
		return true;
		else
			return false;
	}
*/
	
  /*  
    private List checkData(ImportDataForm form) {
    	List errors= new ArrayList();
    	String result =null;
    	//fechas no nulas
    	if(form.getFechaInicioSpd()==null || form.getFechaInicioSpd().equals("") ||  form.getFechaFinSpd()==null || form.getFechaFinSpd().equals("") )
    		errors.add(TextManager.getMensaje("ImportData.error.fechas"));
    	else
    	{
    		Date fechaInicio = DateUtilities.getDate( form.getFechaInicioSpd(), "dd/MM/yyyy");
       		Date fechaFin = DateUtilities.getDate( form.getFechaFinSpd(), "dd/MM/yyyy");
    		if(DateUtilities.getLengthInDays(DateUtilities.actualDate(), fechaInicio)<0)   //Si es anterior a hoy
    		{
        		errors.add(TextManager.getMensaje("ImportData.error.fechaInicioAnteriorAHoy"));
    			
    		}
    		if(DateUtilities.getLengthInDays(DateUtilities.actualDate(), fechaFin)<0)   //Si es anterior a hoy
    		{
        		errors.add(TextManager.getMensaje("ImportData.error.fechaFinAnteriorAHoy"));
    			
    		}
    	}
	
    	
    	//fechas posteriores a ayer
     	
     	form.setErrors(errors);
     	return errors;
    }
*/

	private String construyeProceso(ImportDataForm form) {
		//List errors= new ArrayList();
    	String idProceso  = null;
		//1 - miramos si las fechas son correctas
    	if(form.getFechaInicioSpd()==null || form.getFechaInicioSpd().equals("") ||  form.getFechaFinSpd()==null || form.getFechaFinSpd().equals("") )
    		form.getErrors().add(TextManager.getMensaje("ImportData.error.fechas"));
    	else
    	{
    		Date fechaInicio = DateUtilities.getDate( form.getFechaInicioSpd(), "dd/MM/yyyy");
       		Date fechaFin = DateUtilities.getDate( form.getFechaFinSpd(), "dd/MM/yyyy");
    	/*	if(DateUtilities.getLengthInDays(DateUtilities.actualDate(), fechaInicio)<0)   //Si es anterior a hoy
    		{
    			form.getErrors().add(TextManager.getMensaje("ImportData.error.fechaInicioAnteriorAHoy"));
    			
    		}
    		if(DateUtilities.getLengthInDays(DateUtilities.actualDate(), fechaFin)<0)   //Si es anterior a hoy
    		{
    			form.getErrors().add(TextManager.getMensaje("ImportData.error.fechaFinAnteriorAHoy"));
    			
    		}
    	*/	
    	}
		//List errors=checkData(form);
  	
    	//2 - Se genera el idProceso en base a las fechas
    	try 
    	{
    		//String idProcesoTmp=form.getIdDivisionResidencia().replace("general_", "") + "_" + getFechaYYYYMMDD(form.getFechaInicioSpd()) + "_" + getFechaYYYYMMDD(form.getFechaFinSpd());
    		//System.out.println(" idProcesoTmp " + idProcesoTmp );
    		//int procesosExistentes = HelperSPD.countProcesosMismaFecha(idProcesoTmp);
    		//System.out.println(" procesosExistentes " + procesosExistentes );
    		
    		//idProceso  = idProcesoTmp +"_"+ (procesosExistentes+1);
    		//System.out.println(" idProceso " + idProceso );
    		long currentTimeMillis = System.currentTimeMillis();
    		String digits = String.valueOf(currentTimeMillis).substring(4, 10);
    		System.out.println(digits);
    		
    		idProceso=form.getIdDivisionResidencia().replace("general_", "") + "_" + getFechaYYYYMMDD(form.getFechaInicioSpd()) + "_" + getFechaYYYYMMDD(form.getFechaFinSpd())  + "_" + digits;
    		
    	}
    	catch(Exception e)
		{
    		form.getErrors().add(TextManager.getMensaje("ImportData.error.errorGeneracionProceso"));
		}
		//System.out.println(new Date().getTime()/10000);
    	return idProceso;
	}


	private String getFechaHastaDelProceso(String idDivisionResidencia, String idProceso) {
		String result = null;
		List errors= new ArrayList();
		try{
			result=idProceso.replace(idDivisionResidencia.replace("general_", "")+"_", "");
			result=result.substring(15, 17)+"/"+result.substring(13, 15)+"/"+result.substring(9, 13);
		}catch(Exception e)
		{
			errors.add(TextManager.getMensaje("ImportData.error.getFechaHastaDelProceso"));
		}
		return result;
	}

	
	private String getFechaDesdeDelProceso(String idDivisionResidencia, String idProceso) {
		String result = null;
		List errors= new ArrayList();
		try{
			result=idProceso.replace(idDivisionResidencia.replace("general_", "")+"_", "");
			result=result.substring(6, 8)+"/"+result.substring(4, 6)+"/"+result.substring(0, 4);
		}catch(Exception e)
		{
			errors.add(TextManager.getMensaje("ImportData.error.getFechaDesdeDelProceso"));
		}
		return result;
	}

	/*
	 * Retorna la fecha en formato DDMMYYYYY
	 * 
	 */
	private String getFechaYYYYMMDD(String fechaSpd) {
		String result = null;
		List errors= new ArrayList();
		try{
			result=fechaSpd.substring(6, 10)+fechaSpd.substring(3, 5)+fechaSpd.substring(0, 2);
		}catch(Exception e)
		{
			errors.add(TextManager.getMensaje("ImportData.error.fechasErrorGetFechaYYYYMMDD"));
		}
		return result;
	}

	private boolean isLoggingEnabled()
    {
    	return "1".equals( SPDConstants.IOSPD_LOG_ENABLED );
    }
 
	public class ExportarExcelServlet extends HttpServlet {
	    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        // Obtener la lista de errores del atributo de solicitud (assumiendo que la lista se llama "listaErrores")
	        //List<ErrorImportacion> listaErrores = (List<ErrorImportacion>) request.getAttribute("listaErrores");
	        List errors = new ArrayList();
	        errors=(List) request.getAttribute("listaErrores");
	        
	        // Crear un libro de trabajo de Excel (XSSFWorkbook)
	        Workbook workbook = new XSSFWorkbook();
	        Sheet sheet = workbook.createSheet("Errores de Importación");

	        // Crea el encabezado de la tabla en Excel
	        Row headerRow = sheet.createRow(0);
	        headerRow.createCell(0).setCellValue("Mensaje de Error");

	        // Llenar la tabla con los datos de la lista de errores
	        int rowNum = 1;
	        for (int i = 0; i < errors.size(); i++) {
	            Row row = sheet.createRow(rowNum++);
	            row.createCell(0).setCellValue((double) errors.get(i));
				
			}
	        // Configurar el tipo de contenido y el encabezado de respuesta para el archivo Excel
	        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
	        response.setHeader("Content-Disposition", "attachment; filename=errores_importacion.xlsx");

	        // Enviar el archivo Excel al cliente
	        try (OutputStream outputStream = response.getOutputStream()) {
	            workbook.write(outputStream);
	        }
	    }
	}
	
}
