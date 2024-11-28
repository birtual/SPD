package lopicost.spd.struts.action;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import lopicost.config.logger.Logger;
import lopicost.spd.controller.SpdLogAPI;
import lopicost.spd.helium.helper.HeliumHelper;
import lopicost.spd.helium.model.*;
import lopicost.spd.helper.FicheroResiDetalleHelper;
import lopicost.spd.iospd.IOSpdApi;
import lopicost.spd.iospd.exportdata.process.ExcelFicheroResiDetallePlantUnifLite;
import lopicost.spd.model.*;

import lopicost.spd.persistence.*;
import lopicost.spd.robot.bean.DetallesTomasBean;
import lopicost.spd.robot.helper.PlantillaUnificada;
import lopicost.spd.robot.helper.PlantillaUnificadaHelper;
import lopicost.spd.robot.model.*;
import lopicost.spd.struts.bean.CabecerasXLSBean;
import lopicost.spd.struts.bean.FicheroResiBean;
import lopicost.spd.struts.form.FicheroResiForm;
import lopicost.spd.utils.DateUtilities;
import lopicost.spd.utils.HelperSPD;
import lopicost.spd.utils.SPDConstants;
import lopicost.spd.utils.StringUtil;
public class FicheroResiCabeceraLiteAction extends GenericAction  {

	FicheroResiCabeceraDAO dao= new  FicheroResiCabeceraDAO();
	FicheroResiDetalleDAO daoDetalle= new  FicheroResiDetalleDAO();
	
	   
	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<FicheroResiCabecera> resultList= new ArrayList<FicheroResiCabecera>();
		FicheroResiForm formulari =  (FicheroResiForm) form;
		
		//inicializamos para que no haya datos de otros módulos al venir de un borrado por ejemplo
		formulari.setOidFicheroResiCabecera(0);
		formulari.setIdUsuario(getIdUsuario());
		//paginación
		int currpage = actualizaCurrentPage(formulari, dao.getCountGestFicheroResi(getIdUsuario(), formulari));
		formulari.setListaDivisionResidenciasCargadas(dao.getListaDivisionResidenciasCargadas(getIdUsuario()));
	
		formulari.setListaFicheroResiCabeceraBean(dao.getGestFicheroResi(getIdUsuario(), formulari, currpage*SPDConstants.PAGE_ROWS,(currpage+1)*SPDConstants.PAGE_ROWS, null, false));
		//control y eliminación de procesos colgados
		IOSpdApi.actualizaEstadosSinFinalizar(); 
		
		return mapping.findForward("list");
	}
	

	public ActionForward detalle(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		FicheroResiForm formulari =  (FicheroResiForm) form;
		//creación de log en BBDD
		try{
			SpdLogAPI.addLog(getIdUsuario(), "",  formulari.getIdDivisionResidencia(), formulari.getIdProceso(), SpdLogAPI.A_PRODUCCION, SpdLogAPI.B_CONSULTA, SpdLogAPI.C_DETALLE, "SpdLog.produccion.consulta.detalle", formulari.getIdProceso() );
		}catch(Exception e){}	//SpdLog.produccion.consulta.detalle
		
		return mapping.findForward("detalle");
	}
	

	public ActionForward borrar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		List errors = new ArrayList();

		FicheroResiForm formulari =  (FicheroResiForm) form;
		formulari.setListaFicheroResiCabeceraBean(dao.getGestFicheroResi(getIdUsuario(), formulari, 0, 1, null, false));
		boolean result=false;
		if(errors.isEmpty() && formulari.getACTIONTODO()!=null && formulari.getACTIONTODO().equals("CONFIRMADO_OK"))
		{
			//TO-DO pasar a histórico con estado "BORRADO"
			//borramos detalle
			result=daoDetalle.borrar(getIdUsuario(), formulari.getOidFicheroResiCabecera(), formulari.getOidFicheroResiDetalle(), null);
			if (!result){
				errors.add(new Date() +  " Error en el borrado de las filas del proceso");
				log(" [FicheroResiCabeceraAction_BORRABLE] -borrar()- ERROR borrando filas del proceso: "+formulari.getIdDivisionResidencia() +" - " + formulari.getIdProceso(),Logger.ERROR);
				throw new Exception("Error al eliminar filas del proceso");					
			}
		result=dao.borrar(getIdUsuario(), formulari);
			
		if(result)
		{
			errors.add( "Registro borrado correctamente ");
			//creación de log en BBDD
			try{
				SpdLogAPI.addLog(getIdUsuario(), "", formulari.getIdDivisionResidencia(), formulari.getIdProceso(), SpdLogAPI.A_PRODUCCION, SpdLogAPI.B_BORRADO, "", "SpdLog.produccion.borrado", formulari.getIdProceso() );
			}catch(Exception e){}	//Borrado de la producción.
		}
		else if (!result){
			 errors.add( new Date() +  " Error en el borrado del proceso");
			 log(" [FicheroResiCabeceraAction_BORRABLE] -borrar()- ERROR borrando filas del proceso: "+formulari.getIdDivisionResidencia() +" - " + formulari.getIdProceso(),Logger.ERROR);
			throw new Exception(new Date() +  " Error al eliminar filas del proceso");					
		}
			list( mapping,  form,  request,  response);
			formulari.setErrors(errors);
			return mapping.findForward("list");
		}
		return mapping.findForward("borrar");
	}

	

	public ActionForward editar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		List errors = new ArrayList();
				
		FicheroResiForm formulari =  (FicheroResiForm) form;

	    FicheroResiBean cab = FicheroResiCabeceraDAO.getFicheroResiCabeceraByOid(getIdUsuario(), formulari.getOidFicheroResiCabecera());
		formulari.setFicheroResiDetalleBean(cab);
		
		String fechaDesde=cab.getFechaDesde();
		if(fechaDesde==null || fechaDesde.equals("") || fechaDesde.equalsIgnoreCase("null"))
			fechaDesde = DateUtilities.getDate(HelperSPD.obtenerFechaDesde(cab.getIdProceso()), "yyyyMMdd", "dd/MM/yyyy");  
		
		String fechaHasta=cab.getFechaHasta();			
		if(fechaHasta==null || fechaHasta.equals("") || fechaHasta.equalsIgnoreCase("null"))
			fechaHasta = DateUtilities.getDate(HelperSPD.obtenerFechaHasta(cab.getIdProceso()), "yyyyMMdd", "dd/MM/yyyy");  
		
		//Lógica dedicada a extraer una nueva fecha desde y hasta (siempre dentro del rango de fechas escogido) En caso que no existan se indican los escogidos de la carga
		String nuevaFechaDesde = cab.getNuevaFechaDesde();
		if(nuevaFechaDesde==null || nuevaFechaDesde.equals("") || nuevaFechaDesde.equalsIgnoreCase("null"))
			nuevaFechaDesde= fechaDesde;
		
		String nuevaFechaHasta = cab.getNuevaFechaHasta();
		if(nuevaFechaHasta==null || nuevaFechaHasta.equals("") || nuevaFechaHasta.equalsIgnoreCase("null"))
	    	nuevaFechaHasta= fechaHasta;  

		//recuperamos las tomas
		List<CabecerasXLSBean> tomasCabecera = CabecerasXLSDAO.list(getIdUsuario(), cab.getOidDivisionResidencia(), cab.getOidFicheroResiCabecera());
		formulari.setListaTomasCabecera(tomasCabecera);
		//recuperamos las tomas de inicio/fin, en caso que existan
		String nuevaTomaDesde=cab.getNuevaTomaDesde();
		String nuevaTomaHasta=cab.getNuevaTomaHasta();

		if(nuevaTomaDesde==null || nuevaTomaDesde.equals(""))
		{
			CabecerasXLSBean primerDiaDesdeToma = CabecerasXLSDAO.findByFilters(cab.getOidDivisionResidencia(), -1, -1, null, null, null, true, false);
			if(primerDiaDesdeToma!=null)
				nuevaTomaDesde=primerDiaDesdeToma.getIdToma();
		}
		if(nuevaTomaHasta==null || nuevaTomaHasta.equals(""))
		{
			CabecerasXLSBean ultimoDiaHastaToma = CabecerasXLSDAO.findByFilters(cab.getOidDivisionResidencia(), -1, -1, null, null, null, false, true);
			if(ultimoDiaHastaToma!=null)
			{
				nuevaTomaHasta = ultimoDiaHastaToma.getIdToma();
			}
		}

  	    
		boolean result=false;
		if(errors.isEmpty() && formulari.getACTIONTODO()!=null && formulari.getACTIONTODO().equals("CONFIRMADO_OK"))
		{
			nuevaFechaDesde = formulari.getNuevaFechaDesde();
			nuevaFechaHasta = formulari.getNuevaFechaHasta();
			nuevaTomaDesde = formulari.getNuevaTomaDesde();
			nuevaTomaHasta = formulari.getNuevaTomaHasta();
			
			String antesCab= " | Nueva FechaDesde  " +cab.getNuevaFechaDesde() + " | Nueva FechaHasta " +cab.getNuevaFechaHasta() + " | toma 1er día desde  " +cab.getNuevaTomaDesde() + " | toma último día hasta " +cab.getNuevaTomaHasta() + " | Nota 1 " +cab.getFree1() + " | Nota 2 " +cab.getFree2()+ " | Nota 3 " + cab.getFree3()+ " | " ;
			result=dao.editarCabecera(getIdUsuario(), cab,  formulari);
			String despuesCab = " | Nueva FechaDesde  " +formulari.getNuevaFechaDesde() + " | Nueva FechaHasta " +formulari.getNuevaFechaHasta() + " | toma 1er día desde  " +formulari.getNuevaTomaDesde() + " | toma último día hasta " +formulari.getNuevaTomaHasta() + " | Nota 1 " +formulari.getFree1() + " | Nota 2 " +formulari.getFree2()+ " | Nota 3 " + formulari.getFree3()+ " | " ;
			boolean hayCambios =   ! Objects.equals(StringUtil.limpiarTextoTomas(antesCab), StringUtil.limpiarTextoTomas(despuesCab));
			if(result && hayCambios)
			{
			//	errors.add(SPDConstants.MSG_LEVEL_INFO, new ActionMessage("Registro borrado correctamente Info"));
				errors.add(" Registro modificado correctamente ");
				try{
					SpdLogAPI.addLog(getIdUsuario(), "",  cab.getIdDivisionResidencia(), cab.getIdProceso(),  SpdLogAPI.A_PRODUCCION, SpdLogAPI.B_EDICION, "", "SpdLog.produccion.edicion"
							, new String[]{antesCab, despuesCab} );
				}catch(Exception e){}	//SpdLog.produccion.edicion
			}
			else
			if (!result){
				 errors.add( new Date() +  " Error en la edición del proceso");
				 log("[FicheroResiCabeceraAction_BORRABLE] -editar()- ERROR editando la cabecera del proceso: "+formulari.getIdDivisionResidencia() +" - " + formulari.getIdProceso(),Logger.ERROR);
				throw new Exception("Error al editar la cabecera del proceso");					
			}
			

			
			list( mapping,  form,  request,  response);
			formulari.setErrors(errors);
			return mapping.findForward("list");
		}
		
  	    formulari.setFechaDesde(fechaDesde);
		formulari.setFechaHasta(fechaHasta);
  	    formulari.setNuevaFechaDesde(nuevaFechaDesde);
  	    formulari.setNuevaFechaHasta(nuevaFechaHasta);
  	    formulari.setNuevaTomaDesde(nuevaTomaDesde);
  	    formulari.setNuevaTomaHasta(nuevaTomaHasta);
 
		return mapping.findForward("editar");
	}



	/**
	 * Comparar producciones
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public ActionForward comparar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		FicheroResiForm formulari =  (FicheroResiForm) form;
		ActionForward actionForward = mapping.findForward("compararSeleccion");
		String proceso1= formulari.getIdProceso();
		if(formulari.getACTIONTODO()!=null && formulari.getACTIONTODO().equals("SELECCIONAR"))
		{
			List<FicheroResiBean> listadoProducciones=HelperSPD.recuperaProduccionesResidencia(getIdUsuario(), formulari.getOidDivisionResidencia());
			formulari.setListaProcesosCargados(listadoProducciones);
			
			try{ FicheroResiBean prod =  listadoProducciones.get(0);
			formulari.setIdDivisionResidencia(prod.getIdDivisionResidencia());
			}catch(Exception e){}
			
			
			int currpage = actualizaCurrentPage(formulari, listadoProducciones.size());
			actionForward = mapping.findForward("compararSeleccion");
		}		
		if(formulari.getACTIONTODO()!=null && formulari.getACTIONTODO().equals("COMPARAR"))
		{
			if(formulari.getIdProcesoComparacion()!=null && !formulari.getIdProcesoComparacion().equals(""))
			{
				formulari.setListaFicheroResiDetalleBean(FicheroResiDetalleDAO.getCambiosProcesos(getIdUsuario(), proceso1, formulari.getIdProcesoComparacion()));
				//dejamos log
				try{
					SpdLogAPI.addLog(getIdUsuario(), "",  formulari.getIdDivisionResidencia(), proceso1, SpdLogAPI.A_PRODUCCION, SpdLogAPI.B_COMPARACION, "", "SpdLog.produccion.comparacion"
							, formulari.getIdProcesoComparacion() );
				}catch(Exception e){}	//Comparación de las producciones @@ y @@.
				
				
			}
			actionForward = mapping.findForward("compararResultado");
		}
		return actionForward;
		
		
	}
	
	public ActionForward exportExcel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)	        throws Exception {

			FicheroResiForm formulari =  (FicheroResiForm) form;
			List errors = new ArrayList();
			formulari.setErrors(errors);
		
			//	ActionForward actionForward = mapping.findForward("list");
			
			ActionForward actionForward = mapping.findForward("exportExcel");
			
			if(formulari.getACTIONTODO()!=null && !formulari.getACTIONTODO().equals("EXCEL"))
			{
				return list(mapping, form, request, response);
			}
	   		//Volvemos a poner list porque se quedaría exportExcel en cualquier acción posterior
			formulari.setACTIONTODO("list");
			
			//recuperamos la cabecera del listado
			FicheroResiBean cab =  FicheroResiDetalleHelper.getCabeceraFicheroResi(getIdUsuario(), formulari.getIdDivisionResidencia(), formulari.getIdProceso(), false);
			formulari.setListaTomasCabecera(FicheroResiDetalleHelper.getTomasCabeceraYHora(cab));
			formulari.setOidFicheroResiCabecera(cab.getOidFicheroResiCabecera()); //actualizamos OID

			
		//	inicializamos para que no haya datos de otros módulos
			ExcelFicheroResiDetallePlantUnifLite excelCreator = new ExcelFicheroResiDetallePlantUnifLite();
			//HSSFWorkbook workbook = excelCreator.createWorkbook(formulari, daoDetalle.getGestFicheroResiBolsa(getIdUsuario(), formulari.getOidFicheroResiDetalle(), formulari, 0, 100000, "", false,  " g.resiCIP, g.resiMedicamento ", true, false ));
			HSSFWorkbook workbook = excelCreator.createWorkbook(formulari, daoDetalle.getGestFicheroResiBolsa(getIdUsuario(), formulari.getOidFicheroResiDetalle(), formulari, 0, 100000, "", false, null, true, false ));
		
			String fechaDesde = HelperSPD.obtenerFechaDesde(cab.getIdProceso());  
			String fechaHasta = HelperSPD.obtenerFechaHasta(cab.getIdProceso()); 
			String nombreFichero  = " OK_"+cab.getIdDivisionResidencia().replace("general_",  "")+"_"+fechaDesde+"_" + fechaHasta+"_"+"1.xls";
			
			//if(cab.getNumeroDeTomas()>6)
			//	nombreFichero="OJO_REORDENAR_COLUMNAS_"+nombreFichero;
				
	        response.setHeader("Content-Disposition", "attachment; filename=/"+nombreFichero);
	        ServletOutputStream out = response.getOutputStream();
	        workbook.write(out);
	        out.flush();
	        out.close();
	        
			//dejamos log
			try{
				SpdLogAPI.addLog(getIdUsuario(), "",  formulari.getIdDivisionResidencia(), formulari.getIdProceso(), SpdLogAPI.A_PRODUCCION, SpdLogAPI.B_EXPORTACION, "", "SpdLog.produccion.exportacion"
						,  nombreFichero );
			}catch(Exception e){}	//Exportada a Excel, nombre fichero @@.
			

			return actionForward;
	}



	public void log (String message, int level)
	{
		Logger.log("SgaLogger",message,level);	
	}
	
	public ActionForward detalleNoExistentesBbdd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("detalleNoExistentesBbdd");
	}
	
	public ActionForward detalleNoExistentesFichero(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("detalleNoExistentesFichero");
	}
	
	
	public ActionForward detalleMensajesInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("detalleMensajesInfo");
	}

	
	public ActionForward detalleMensajesAlerta(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("detalleMensajesAlerta");
	}
	
	public ActionForward abrirVentanaErrores(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		FicheroResiForm formulari =  (FicheroResiForm) form;
		FicheroResiBean cab =  FicheroResiCabeceraDAO.getFicheroResiCabeceraByOid(getIdUsuario(), formulari.getOidFicheroResiCabecera());
		formulari.setFicheroResiDetalleBean(cab);
		return mapping.findForward("abrirVentanaErrores");
	}
	
	public ActionForward resumenCIPS(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		FicheroResiForm formulari =  (FicheroResiForm) form;
		FicheroResiBean cab =  FicheroResiCabeceraDAO.getFicheroResiCabeceraByOid(getIdUsuario(), formulari.getOidFicheroResiCabecera());
		formulari.setFicheroResiDetalleBean(cab);
		return mapping.findForward("resumenCIPS");
	}
	
	//public void actualizarPrevision(ActionMapping mapping, ActionForm form,		HttpServletRequest request, HttpServletResponse response)
	public void actualizarPrevision(FicheroResiBean cab, FicheroResiForm formulari)
			throws Exception {
		boolean resultTotal=true;
		List errors = new ArrayList();
		//FicheroResiForm formulari =  (FicheroResiForm) form;
		List<FicheroResiBean> filas=FicheroResiDetalleDAO.getGestFicheroResiBolsa( getIdUsuario(), -1, formulari,  0,100000, null, false, "g.resiCIP, g.SpdNombreBolsa, g.resiInicioTratamiento ", true, false);  
		//List filas = FicheroResiDetalleDAO.getGestFicheroResiBolsa(getIdUsuario(), -1, formulari, 0, 100000, null, true, null, true, false);
		boolean result=false;
		//if(errors.isEmpty() && formulari.getACTIONTODO()!=null && formulari.getACTIONTODO().equals("VALIDADO_OK"))
		if(errors.isEmpty())
		{
			TreeMap CIPSTratados =new TreeMap();
			
			Iterator<FicheroResiBean> it = filas.iterator();
			while (it!=null && it.hasNext())
			{
				FicheroResiBean fila = (FicheroResiBean) it.next();
	    	
		     	if(!CIPSTratados.containsKey(fila.getResiCIP()))
		  		{
		     		
		        	IOSpdApi.limpiarCIPIdprocesoAnterior(fila.getIdProceso(), fila.getResiCIP());
		            //Una vez limpiado, se añade como CIP ya tratado para no volver a limpiar datos
		            CIPSTratados.put(fila.getResiCIP(), fila.getResiCIP());
		  		}
		     	
		     	result=HelperSPD.creaRegistroPrevision(getIdUsuario(), fila);
				if (!result){
					resultTotal=false;
					errors.add( new Date() +  " No se ha podido actualizar la fila " + fila.getRow() + " - " + fila.getResiCIP() + " - " + fila.getSpdCnFinal());
					log("[FicheroResiCabeceraAction_BORRABLE] - actualizarPrevision() - ERROR actualizando previsión de la fila : "+fila.getRow() + " - " + fila.getResiCIP() + " - " + fila.getSpdCnFinal() +" - " + formulari.getIdProceso(),Logger.ERROR);
					throw new Exception("Error al actualizar previsión de la fila");					
				}
			}
			
			//borramos registros antiguos
			IOSpdApi.borraErrores(formulari.getIdDivisionResidencia());
			
			if(result)
			{
			//	errors.add(SPDConstants.MSG_LEVEL_INFO, new ActionMessage("Registro borrado correctamente Info"));
				errors.add( "Previsión de consumo correctamente actualizada ");
		        // Crea un objeto SimpleDateFormat con el patrón de formato deseado
		        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	
		        // Formatea la fecha actual según el patrón
		        Date fechaFormateada = null;
		        try {
		            fechaFormateada = formato.parse(formato.format(new Date()));
		        } catch (ParseException e) {
		            e.printStackTrace();
		        }
		        
		        //actualizamos el cálculo de la previsión solo en caso de que no se haya hecho antes, para no sobrecargar  
		        if(cab.getFechaCalculoPrevision()==null || cab.getFechaCalculoPrevision().equals(""))
		        {
		        	cab.setFechaCalculoPrevision(DateUtilities.getDatetoString("dd/MM/yyyy hh:MM",fechaFormateada));
		        	cab.setIdEstado(SPDConstants.SPD_PROCESO_3_VALIDADO); //cambiamos el estado de la carga
		        	
		    		FicheroResiBean cabeceraGeneral=FicheroResiCabeceraDAO.getFicheroResiCabeceraByOid(getIdUsuario(), cab.getOidFicheroResiCabecera());
		        	if(cabeceraGeneral.getFree3()!=null && cabeceraGeneral.getFree3().equalsIgnoreCase(SPDConstants.SPD_PROCESO_3_POR_VALIDACION_MASIVA))
			        	cab.setIdEstado(SPDConstants.SPD_PROCESO_3_POR_VALIDACION_MASIVA); //cambiamos el estado de la carga
		        		
		        	FicheroResiCabeceraDAO.actualizaFechaCalculoPrevision(cab);
					//dejamos log
					try{
						SpdLogAPI.addLog("AUTO", "",  formulari.getIdDivisionResidencia(), formulari.getIdProceso(), SpdLogAPI.A_PRODUCCION, SpdLogAPI.B_CREACION, SpdLogAPI.C_PREVISION, "SpdLog.produccion.creacion.prevision", formulari.getIdProceso() );
					}catch(Exception e){}	//De forma automática se actualiza la previsión para report de discrepancias de la residencia@@
		        }
			}
			else
				if (!result){
				 errors.add( new Date() +  " Error en la actualización de previsión de consumo");
				 log("[FicheroResiCabeceraAction_BORRABLE] - actualizarPrevision () - actualizando previsión de la fila: "+formulari.getIdDivisionResidencia() +" - " + formulari.getIdProceso(),Logger.ERROR);
			//	throw new Exception("Error en la actualización de previsión de consumo");					
			}
		
			formulari.setErrors(errors);
			
		}
		formulari.setACTIONTODO("list");
		formulari.setParameter("list");
	
	}


	public void generarFicherosHelium(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		FicheroResiForm formulari =  (FicheroResiForm) form;
		FicheroResiBean cab = dao.getCabeceraByFilters(getIdUsuario(), formulari, 0, 1, null, false);
		FicheroResiBean cabDetalle  =  FicheroResiDetalleHelper.getCabeceraFicheroResi(getIdUsuario(), cab.getIdDivisionResidencia(), cab.getIdProceso(), false);

		DivisionResidencia div = DivisionResidenciaDAO.getDivisionResidenciaById(getIdUsuario(), cab.getIdDivisionResidencia());
		
		formulari.setOidFicheroResiCabecera(cab.getOidFicheroResiCabecera()); //actualizamos OID
		formulari.setFicheroResiDetalleBean(cab);
		formulari.setIdDivisionResidencia(cab.getIdDivisionResidencia());
		formulari.setIdProceso(cab.getIdProceso());
		
		boolean result=false;
		
		List<String> errors = new ArrayList<String>();
		formulari.setErrors(errors);
	
		if(cab.isProcesoValido())
		{
			Center careHome = HeliumHelper.getCenterFicherosHelium(getIdUsuario(), formulari);
			String nombreFichero=HeliumHelper.generaFichero(careHome,  response);

			//dejamos log
			try{
				SpdLogAPI.addLog(getIdUsuario(), "",  formulari.getIdDivisionResidencia(), cab.getIdProceso(), SpdLogAPI.A_PRODUCCION, SpdLogAPI.B_CREACION, SpdLogAPI.C_FICHERO_HELIUM
						, "SpdLog.produccion.creacion.ficherohelium", nombreFichero );
			}catch(Exception e){}	//Creación del fichero Helium con nombre @@.
				
			
			//aprovechamos y lanzamos el cálculo de previsión de comprimidos necesarios. En caso que no se haya calculado previamente
	        if(nombreFichero!=null && !nombreFichero.equals("") && (cab.getFechaCalculoPrevision()==null || cab.getFechaCalculoPrevision().equals("")))
	        {
	        	// Crear un hilo para ejecutar el método actualizarPrevision()
	            Thread actualizarPrevisionThread = new Thread(() -> {
	                try {
						actualizarPrevision(cab, formulari);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
               // Aquí se puede enviar algún tipo de señal al cliente, como un mensaje o un código de estado
               // response.getWriter().write("Proceso actualizadoPrevision completado");
	            });

	            // Iniciar el hilo
	            actualizarPrevisionThread.start();
	            try {
                // Esperar a que el hilo termine antes de continuar
	                actualizarPrevisionThread.join();
	            } catch (InterruptedException e) {
	                // Manejar la interrupción si es necesario
	                e.printStackTrace();
	            }
	        }
			}
		else 
			formulari.getErrors().add("Proceso con registros a confirmar o revisar");
	}

	


	public ActionForward generarFicherosDMyRX(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		FicheroResiForm formulari =  (FicheroResiForm) form;
		FicheroResiBean cab = dao.getCabeceraByFilters(getIdUsuario(), formulari, 0, 1, null, false);
		FicheroResiBean cabDetalle  =  FicheroResiDetalleHelper.getCabeceraFicheroResi(getIdUsuario(), cab.getIdDivisionResidencia(), cab.getIdProceso(), false);
		DivisionResidencia div = DivisionResidenciaDAO.getDivisionResidenciaById(getIdUsuario(), cab.getIdDivisionResidencia());
		// Paso1 - Borrado previo de posibles datos del mismo proceso  
    	PlantillaUnificadaHelper.borraProcesosResidencia(getIdUsuario(),  cabDetalle);
    	// Paso2 - Recuperamos el orden y nombre de las tomas del proceso  
    	TomasOrdenadas tomasOrdenadas = PlantillaUnificadaHelper.getTomasOrdenadas(getIdUsuario(),  cabDetalle);
    	// Paso3 - Recuperamos la lista de detalleTomas ya ordenada
    	List<DetallesTomasBean> listaDetallesTomas  = PlantillaUnificadaHelper.getDetalleTomasRobot(getIdUsuario(),  cabDetalle, tomasOrdenadas);
    	// Paso4 - Procesar los detallesBean para insertarlos en BBDD
    	PlantillaUnificadaHelper.procesarDetalleTomasRobot(getIdUsuario(), cabDetalle, listaDetallesTomas, tomasOrdenadas);
    	 // Paso5 - Procesar Excepciones (Falguera) 
    	PlantillaUnificadaHelper.procesarExcepciones(getIdUsuario(), cab,  cabDetalle);
        // Paso6 - Creación del FiliaDM 
   		FiliaDM filiaDM = PlantillaUnificada.creaFicheroDM(getIdUsuario(), cabDetalle);
   		FiliaRX filiaRX = PlantillaUnificada.creaFicheroRX(getIdUsuario(), cabDetalle, div);

   		
   		String nombreFicheroFiliaDM=PlantillaUnificadaHelper.generaFicheroDM(cabDetalle, filiaDM,  response);
   	    boolean fileDMGenerated = (nombreFicheroFiliaDM != null && !nombreFicheroFiliaDM.isEmpty());
   	    String nombreFicheroFiliaRX="";
   	    if(fileDMGenerated)
   	    {
   	        // Paso7 - Creación del FiliaRX
   	   		nombreFicheroFiliaRX=PlantillaUnificadaHelper.generaFicheroRX(cabDetalle, filiaRX,  response);
   	    }
   	    boolean fileRXGenerated = (nombreFicheroFiliaRX != null && !nombreFicheroFiliaRX.isEmpty());
   		
   		try
   		{
   			fileRXGenerated = !filiaRX.getPatients().isEmpty();
   		}catch(Exception e){
   			fileRXGenerated=false;
   		}
   	    
   	    String path = SPDConstants.PATH_DOCUMENTOS+"/robot/";
   	    
  	    request.setAttribute("fileDMGenerated", fileDMGenerated); // Indica si el archivo fue generado
   	    request.setAttribute("fileRXGenerated", fileRXGenerated); // Indica si el archivo fue generado
  	    request.setAttribute("nombreFicheroFiliaDM", nombreFicheroFiliaDM); // Indica si el archivo fue generado
   	    request.setAttribute("nombreFicheroFiliaRX", nombreFicheroFiliaRX); // Indica si el archivo fue generado
   	    request.setAttribute("filePathDM", path + nombreFicheroFiliaDM); // Ruta del archivo generado
   	    request.setAttribute("filePathRX", path + nombreFicheroFiliaRX); // Ruta del archivo generado
   	    
		//aprovechamos y lanzamos el cálculo de previsión de comprimidos necesarios. En caso que no se haya calculado previamente
        if(fileRXGenerated)
        {
        	// Crear un hilo para ejecutar el método actualizarPrevision()
            Thread actualizarPrevisionThread = new Thread(() -> {
                try {
					actualizarPrevision(cab, formulari);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

                // Aquí puedes enviar algún tipo de señal al cliente, como un mensaje o un código de estado
                // (esto dependerá de tu lógica específica y de cómo manejas la respuesta en el cliente)
                // response.getWriter().write("Proceso actualizadoPrevision completado");
            });

            // Iniciar el hilo
            actualizarPrevisionThread.start();

            try {
                // Esperar a que el hilo termine antes de continuar
                actualizarPrevisionThread.join();
            } catch (InterruptedException e) {
                // Manejar la interrupción si es necesario
                e.printStackTrace();
            }
            
			//dejamos log
			try{
				SpdLogAPI.addLog(getIdUsuario(), "",  cab.getIdDivisionResidencia(), cab.getIdProceso(), SpdLogAPI.A_PRODUCCION, SpdLogAPI.B_CREACION, SpdLogAPI.C_FICHERO_ROBOT_UNIFICADA
						, "SpdLog.produccion.creacion.ficheroRobotUnificada", nombreFicheroFiliaDM + " y " +  nombreFicheroFiliaRX);
			}catch(Exception e){}	//Creación del fichero Helium con nombre @@.
        }
        else 
        	return mapping.findForward("sinDatosRX");
          
   		return mapping.findForward("generarFicherosDMyRX");
	}

	public ActionForward confirmacionFicheros(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<String> avisos = new ArrayList<>();
		request.setAttribute("estado", "confirma");
	    FicheroResiForm formulari =  (FicheroResiForm) form;
	    FicheroResiBean cab = FicheroResiCabeceraDAO.getFicheroResiCabeceraByOid(getIdUsuario(), formulari.getOidFicheroResiCabecera());
	    String fechaDesde=cab.getFechaDesde();
	    String fechaHasta=cab.getFechaHasta();
	    String nuevaFechaDesde=cab.getNuevaFechaDesde();
	    String nuevaFechaHasta=cab.getNuevaFechaHasta();
	    
		if(fechaDesde==null || fechaDesde.equals(""))
			fechaDesde = DateUtilities.getDate(HelperSPD.obtenerFechaDesde(cab.getIdProceso()), "yyyyMMdd", "dd/MM/yyyy");  
		if(fechaHasta==null || fechaHasta.equals(""))
			fechaHasta = DateUtilities.getDate(HelperSPD.obtenerFechaHasta(cab.getIdProceso()), "yyyyMMdd", "dd/MM/yyyy");  
		if(nuevaFechaDesde==null || nuevaFechaDesde.equals(""))
			nuevaFechaDesde= fechaDesde;
		if(nuevaFechaHasta==null || nuevaFechaHasta.equals(""))
	    	nuevaFechaHasta= fechaHasta;  

  	    formulari.setFechaDesde(fechaDesde);
		formulari.setFechaHasta(fechaHasta);
  	    formulari.setNuevaFechaDesde(nuevaFechaDesde);
  	    formulari.setNuevaFechaHasta(nuevaFechaHasta);
	    
	    //gestión de las tomas de inicio del primer día y de fñin del último día
  	    String inicioTomaPrimerDia="";
	   	String finTomaUltimoDia="";
  	    
	   	CabecerasXLSBean primerDiaDesdeToma = CabecerasXLSDAO.findByFilters(cab.getOidDivisionResidencia(), -1, -1, null, null, null, true, false);
		CabecerasXLSBean ultimoDiaHastaToma = CabecerasXLSDAO.findByFilters(cab.getOidDivisionResidencia(), -1, -1, null, null, null, false, true);
		if(primerDiaDesdeToma==null || ultimoDiaHastaToma==null)
		{
			TomasOrdenadas tomasOrdenadas = PlantillaUnificadaHelper.getTomasOrdenadas(getIdUsuario(),  cab);	
			try{
				if(primerDiaDesdeToma==null)
				{
	  	   			primerDiaDesdeToma = CabecerasXLSDAO.findByFilters(cab.getOidDivisionResidencia(), -1, -1, tomasOrdenadas.getIdTomas().get(0), null, null, false, false);
				}
				//else inicioTomaPrimerDia = primerDiaDesdeToma.getIdToma();
	  	   	}catch(Exception e){}
			try{
				if(ultimoDiaHastaToma==null)
				{
					ultimoDiaHastaToma = CabecerasXLSDAO.findByFilters(cab.getOidDivisionResidencia(), -1, -1, tomasOrdenadas.getIdTomas().get(tomasOrdenadas.getIdTomas().size()-1), null, null, false, true);
				}
				//else finTomaUltimoDia = ultimoDiaHastaToma.getIdToma();
	  	   	}catch(Exception e){}
		}
 	   	
	    //si alguna de las nuevaFecha son diferentes a las escogidas para la carga se envía aviso informando el nuevo rango para producción
  	    avisos.add("Datos originales en la carga -->  Desde el " + fechaDesde + " ("+(primerDiaDesdeToma!=null?primerDiaDesdeToma.getNombreToma():"") + ") hasta el " +  fechaHasta  +" ("+(ultimoDiaHastaToma!=null?ultimoDiaHastaToma.getNombreToma():"") + ")\n");
  	  
		//una vez tenemos las tomas de inicio/fin estándar, miramos si el gestor ha modificado las tomas en esta producción, que tendrían preferencia.
 	   	if(cab.getNuevaTomaDesde()!=null && !cab.getNuevaTomaDesde().equals("") && inicioTomaPrimerDia!=null && !cab.getNuevaTomaDesde().equals(inicioTomaPrimerDia))
    		primerDiaDesdeToma = CabecerasXLSDAO.findByFilters(cab.getOidDivisionResidencia(), -1, -1, cab.getNuevaTomaDesde(), null, null, false, false);
    	if(cab.getNuevaTomaHasta()!=null && !cab.getNuevaTomaHasta().equals("") && finTomaUltimoDia!=null && !cab.getNuevaTomaHasta().equals(finTomaUltimoDia))
    		ultimoDiaHastaToma = CabecerasXLSDAO.findByFilters(cab.getOidDivisionResidencia(), -1, -1, cab.getNuevaTomaHasta(), null, null, false, false);

    	
    	if(
    		(nuevaFechaDesde!=null && !nuevaFechaDesde.equals("") && fechaDesde!=null && !nuevaFechaDesde.equals(fechaDesde))
	    		||
	    	(nuevaFechaHasta!=null && !nuevaFechaHasta.equals("") && fechaHasta!=null && !nuevaFechaHasta.equals(fechaHasta))	
    			||
        	(cab.getNuevaTomaDesde()!=null && !cab.getNuevaTomaDesde().equals("") && inicioTomaPrimerDia!=null && !cab.getNuevaTomaDesde().equals(inicioTomaPrimerDia))
    			||
	    	(cab.getNuevaTomaHasta()!=null && !cab.getNuevaTomaHasta().equals("") && finTomaUltimoDia!=null && !cab.getNuevaTomaHasta().equals(finTomaUltimoDia))	
	    	)
    	{
    		avisos.clear();
    		avisos.add(" Atención - Las fechas se han modificado en la edición");
    		avisos.add(" Desde el " + nuevaFechaDesde + " ("+(primerDiaDesdeToma!=null?primerDiaDesdeToma.getNombreToma():"") + ") ");
    		avisos.add(" hasta el " + nuevaFechaHasta + " ("+(ultimoDiaHastaToma!=null?ultimoDiaHastaToma.getNombreToma():"") + ") ");
    	}


   	
    	//formulari.setErrors(errors);
    	request.setAttribute("avisos", avisos);
	    return mapping.findForward("generarFicheros");
	}
	public ActionForward generarFicheros(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
	    request.setAttribute("estado", "inicio");
	    return mapping.findForward("generarFicheros");
	}

	
	public ActionForward addTratamientosEnProyectoExistente(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		FicheroResiForm formulari =  (FicheroResiForm) form;
		FicheroResiBean cab = dao.getCabeceraByFilters(getIdUsuario(), formulari, 0, 1, null, false);
		
		formulari.setFicheroResiDetalleBean(cab);
		formulari.setIdDivisionResidencia(cab.getIdDivisionResidencia());
		formulari.setIdProceso(cab.getIdProceso());
		formulari.setFechaDesde(cab.getFechaDesde());
		formulari.setFechaHasta(cab.getFechaHasta());
		

		//dejamos log
		try{
			SpdLogAPI.addLog(getIdUsuario(), "",  formulari.getIdDivisionResidencia(), cab.getIdProceso(), SpdLogAPI.A_IOSPD, SpdLogAPI.B_CARGAFICHERO, SpdLogAPI.C_FICHERO_IOSPD_ANEXO
			, "SpdLog.produccion.cargafichero.anexo", cab.getIdProceso() );
		}catch(Exception e){}	//
		
		
		String url = "/Iospd/Iospd.do?parameter=list&fechaDesde=" + cab.getFechaDesde() + "&fechaHasta=" + cab.getFechaHasta() + "&idProceso=" + cab.getIdProceso() + "&idDivisionResidencia=" + cab.getIdDivisionResidencia();
        // Puedes guardar la URL como un atributo de sesión
        request.getSession().setAttribute("url", url);
        // O puedes guardarla como un atributo en el request
        request.setAttribute("url", url);

        
		return mapping.findForward("addTratamientosEnProyecto");
	}

	
	/**
	 * método de ayuda a la paginación
	 * @param aForm
	 * @param numberObjects
	 * @return
	 */
	private int actualizaCurrentPage(FicheroResiForm aForm, int numberObjects) {
	{
		int numpages= aForm.getNumpages();
		int currpage= aForm.getCurrpage();
	
			if (numberObjects% SPDConstants.PAGE_ROWS != 0)
				numberObjects= numberObjects / SPDConstants.PAGE_ROWS + 1;
			else
				numberObjects = numberObjects/ SPDConstants.PAGE_ROWS;	
			if (numpages!=numberObjects)
			{
				numpages= numberObjects;
				currpage=0;
				aForm.setNumpages(numpages);
				aForm.setCurrpage(currpage);
			}
			return currpage;
		}
}



	
}
	