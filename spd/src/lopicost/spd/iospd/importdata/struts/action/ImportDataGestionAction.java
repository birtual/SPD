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
import lopicost.spd.model.Robot;
import lopicost.spd.persistence.DivisionResidenciaDAO;
import lopicost.spd.persistence.RobotDAO;
import lopicost.spd.utils.SPDConstants;
import lopicost.spd.utils.TextManager;
import lopicost.spd.utils.beanUpload;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;



public class ImportDataGestionAction extends DispatchAction 
{
	
	public void killThread(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception  
    {
		/*HttpSession session = request.getSession();
		ImportDataThread proc=new ImportDataThread();
		ImportDataForm formulario=(ImportDataForm) form;
		proc=(ImportDataThread)session.getAttribute(formulario.getIdThread());
		if(proc!=null)
		proc.interrupt();
		formulario.setFechaInicioSpd("");
		formulario.setFechaFinSpd("");
		formulario.setIdThread("");
		formulario.setOperation("FILTER"); 
		*/
		 list(mapping, form, request, response) ;  

    }
      
		
		
		
    public ActionForward list(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception  
    {
        HttpSession session = request.getSession();
        ActionForward actionForward=null;
        String spdUsuario = (String) session.getAttribute("idUsuario");
        
        //TO-DO - Usarlo en un futuro
        List errors = new ArrayList();
        ImportDataForm formulario=(ImportDataForm) form;
        formulario.setErrors(errors);
        formulario.setIdUsuario(spdUsuario);
        String type="IMPORT";
        List lst1=IOSpdProcessDAO.findProcess(type);
        List lst2=IOSpdConnectorDAO.findReaders();
        formulario.setProcess(lst1);
        formulario.setReaders(lst2);
   //     formulario.setFile(file.);
   //     FormFile file = new FormFile();
   //     file.setFileName(request.getContextPath()+"\\" +SPDConstants.PATH_PLANTILLAS_IO, formulario.getFilenameOut()+formulario.getExportType());
        
   //     formulario.setFile(file);
        String exportType= formulario.getExportType();
        String readerType=formulario.getFileType();
        
        formulario.setEnd("WAITING_FILE");
     
        String fechaInicioSpd = formulario.getFechaInicioSpd();
        String fechaFinSpd = formulario.getFechaFinSpd();
        String idProceso= "";
        
        
        //lista de robots
        List listaRobots= RobotDAO.getListaRobots();
        formulario.setListaRobots(listaRobots);

        //resi escogida para la importación
        Robot robotElegido = RobotDAO.getRobotById(formulario.getIdRobot());
        
        
        //lista de resis
        List lstDivisionResidencias= DivisionResidenciaDAO.getListaDivisionResidencias( spdUsuario);
        formulario.setLstDivisionResidencias(lstDivisionResidencias);

        //resi escogida para la importación
        DivisionResidencia resiElegida = DivisionResidenciaDAO.getDivisionResidenciaById(spdUsuario, formulario.getIdDivisionResidencia());

        
        //lista de procesos de mantenimiento
        List procesosGestion= IOSpdProcessDAO.findProcess(type, null,1);  //1 - Procesos de carga de tablas
        
        formulario.setProcess(procesosGestion);

        //proceso escogido para la importación
        IOSpdProcess procSelected = IOSpdProcessDAO.findByFields(type, formulario.getIdProcessIospd(), true);
   
        formulario.setDescProcess(procSelected!=null?procSelected.getDescription():"");
        		
        
        String idreader=null;
         
        //lector importación
        IOSpdConnector readerSelected= IOSpdConnectorDAO.findByFields(idreader, readerType);
        formulario.setDescReader(readerSelected.getDescription());
        
        
        String operation = formulario.getOperation();
        FormFile file = formulario.getFile();
        ImportDataThread proc=new ImportDataThread();
        
        
        //comprobación de existencia de la plantilla de ejemplo para carga
       // String url = FileUtil.dameURLBase();
        
       // String nFile = SPDConstants.FILEUPLOAD_REAL_PATH+SPDConstants.FILEUPLOAD_RELATIVE_PATH+SPDConstants.PLANTILLAS_IO_RELATIVE_PATH + "/" + formulario.getIdProcessIospd() + "." +formulario.getFileType();
        String nFile = SPDConstants.PATH_DOCUMENTOS+SPDConstants.FILEUPLOAD_RELATIVE_PATH+SPDConstants.PLANTILLAS_IO_RELATIVE_PATH + "/" + formulario.getIdProcessIospd() + "." +formulario.getFileType();
    	//"c:/eclipse/workspace/spd/WebContent/tmp/iospd/plantillas/importSustXComposicion.xls";
	    File n =new File(nFile);
	    if(n.exists())
	    	formulario.setExistePlantilla(true);
	    else formulario.setExistePlantilla(false);
	    System.out.println(nFile  + " - " +  n.exists());


      //  String fechasSpd = formulario.getFechasSpd();
     //   if(formulario.getOperation())        

        
        try 
        {    
            if (operation.equals("FILTER")) 
            {
                actionForward = mapping.findForward("list");
            }
    	    if (operation.equals("GENERATE")) 
            {
    	    	//comprobamos que se han introducido fechas para generar el idProceso
    	    	//idProceso=construyeProceso(formulario);
        		
        		System.out.println(" idProceso=construyeProceso(formulario) =" + idProceso);
    	    	
		        if(file!=null)
				{     
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
                            IOSpdProcess p=(IOSpdProcess)IOSpdProcessDAO.findByFields(exportType, formulario.getIdProcessIospd());
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
                             String idRobot=(robotElegido!=null?robotElegido.getIdRobot():null);
                            String idDivisionResidencia=(resiElegida!=null?resiElegida.getIdDivisionResidencia():null);
                            formulario.setIdDivisionResidencia(idDivisionResidencia);
                            if (proc.initialize(spdUsuario, formulario, idProceso,  idRobot, idDivisionResidencia, path, null, classNameProcess,classNameReader, log, false)){
                            	
                                proc.start();
                                formulario.setOperation("THREADING");
                            }
                            else {
                                proc.setStatus(3);
                                formulario.setEnd("FINALIZED_KO");
                            }
                            actionForward = mapping.findForward("inprocess");
                        }
                        catch (Exception e)
                        {
                        	e.printStackTrace();
                            errors.add(e.getMessage());
                            formulario.setErrors(errors);
                            formulario.setEnd("FINALIZED_KO");
                            return mapping.findForward("inprocess");
                        }
					}
					else
                        actionForward = mapping.findForward("error");
                }
                if (operation.equals("THREADING")) {
                    proc=(ImportDataThread)session.getAttribute(formulario.getIdThread());
                    Logger.get().debug("Fila:"+proc.getFilesProcessades()+", Fitxer:"+file.getFileName());
                    formulario.setFilesProcessades(proc.getFilesProcessades());
                    if (proc.checkStatus()==ImportDataThread.FINALIZED_KO)
                    {
                        formulario.setEnd("FINALIZED_KO");
                        errors=proc.getErrors();
                        formulario.setErrors(errors);
                    }
                    else if (((proc.checkStatus()==ImportDataThread.FINALIZED_OK) && (proc.getErrors().size()==0))  || proc.checkStatus()==ImportDataThread.SHUTDOWN)
                        formulario.setEnd("FINALIZED_OK");
                    else if ((proc.checkStatus()==ImportDataThread.FINALIZED_OK) && (proc.getErrors().size()>0))
                    {
                        formulario.setEnd("FINALIZED_OKKO");
                        errors=proc.getErrors();
                        formulario.setErrors(errors);
                    }
                    actionForward = mapping.findForward("inprocess");
                }
    	    }
    	    catch (Exception e) 
    	    {
    	    	e.printStackTrace();
    			request.setAttribute("exception", e );
    			 formulario.setEnd("FINALIZED_KO");
                actionForward = mapping.findForward("error");
    		}
            return actionForward;
    }
    
    

    public void descargaFichero(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, ClassNotFoundException, SQLException  
    {
        HttpSession session = request.getSession();
        ActionForward actionForward=null;
        ImportDataForm formulario=(ImportDataForm) form;
        List errors = new ArrayList();

        try
        {
      		
          //  String url = FileUtil.dameURLBase();
            
            // String nFile = SPDConstants.FILEUPLOAD_REAL_PATH+SPDConstants.FILEUPLOAD_RELATIVE_PATH+SPDConstants.PLANTILLAS_IO_RELATIVE_PATH + "/" + formulario.getIdProcessIospd() + "." +formulario.getFileType();
             String nFile = SPDConstants.PATH_DOCUMENTOS+SPDConstants.FILEUPLOAD_RELATIVE_PATH+SPDConstants.PLANTILLAS_IO_RELATIVE_PATH + "/" + formulario.getIdProcessIospd() + "." +formulario.getFileType();
   		//"c:/eclipse/workspace/spd/WebContent/tmp/iospd/plantillas/importSustXComposicion.xls";
        
	    	    FileInputStream archivo = new FileInputStream(nFile); 

	    	    int longitud = archivo.available();
	    	    byte[] datos = new byte[longitud];
	    	    archivo.read(datos);
	    	    archivo.close();
	    	    
	    	    response.setContentType("application/octet-stream");
	    	response.setHeader("Content-Disposition","attachment;filename="+formulario.getIdProcessIospd()+ "." + formulario.getFileType());    
	    	    
	    	    ServletOutputStream ouputStream = response.getOutputStream();
	    	    ouputStream.write(datos);
	    	    ouputStream.flush();
	    	    ouputStream.close();
	    	    	
      }
        catch (Exception ex)
        {
        	errors.add(" Error en la descarga de la plantilla " +  ex.getMessage());
        	System.out.println("\nExcepción ex \n" + ex);
        }

    } 

/* Este método creo que está de más en las cargas de tablas. Se usa en ImportDataAction, las cargas de producciones*/
 /*   
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
    	}
		//List errors=checkData(form);
  	
    	//2 - Se genera el idProceso en base a las fechas
    	try 
    	{
    		String idProcesoTmp=form.getIdDivisionResidencia().replace("general_", "") + "_" + getFechaYYYYMMDD(form.getFechaInicioSpd()) + "_" + getFechaYYYYMMDD(form.getFechaFinSpd());
    		
    		int procesosExistentes = HelperSPD.countProcesosMismaFecha(idProcesoTmp);
    		idProceso  = idProcesoTmp +"_"+ (procesosExistentes+1);
    		
    		
    	}
    	catch(Exception e)
		{
    		form.getErrors().add(TextManager.getMensaje("ImportData.error.errorGeneracionProceso"));
		}
		//System.out.println(new Date().getTime()/10000);
    	return idProceso;
	}
*/   
/*    

    private String construyeProceso(ImportDataForm  form) {
		//List errors= new ArrayList();
    	String idProceso  = null;
		//1 - miramos si las fechas son correctas
    	if(form.getFechaInicioSpd()!=null && !form.getFechaInicioSpd().equals("") &&  form.getFechaFinSpd()!=null && !form.getFechaFinSpd().equals("") )
    	{
    		Date fechaInicio = DateUtilities.getDate( form.getFechaInicioSpd(), "dd/MM/yyyy");
       		Date fechaFin = DateUtilities.getDate( form.getFechaFinSpd(), "dd/MM/yyyy");
    	}
  	
    	//2 - Se genera el idProceso en base a las fechas
    	try 
    	{
    		idProceso  = form.getIdDivisionResidencia() + "_" + getFechaYYYYMMDD(form.getFechaInicioSpd()) + "_" + getFechaYYYYMMDD(form.getFechaFinSpd()) ; 
    	}
    	catch(Exception e)
		{
		}
    	return idProceso;
	}
*/

	private String getFechaYYYYMMDD(String fechaSpd) {
		String result = null;
		List errors= new ArrayList();
		try{
			result=fechaSpd.substring(6, 10)+fechaSpd.substring(3, 5)+fechaSpd.substring(0, 2);
		}catch(Exception e)
		{
		}
		return result;
	}

    
    
    private boolean isLoggingEnabled()
    {
    	return "1".equals( SPDConstants.IOSPD_LOG_ENABLED );
    }




  

   
    

    
    
    


}
