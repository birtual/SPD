package lopicost.spd.struts.action;


import java.util.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import lopicost.spd.controller.SpdLogAPI;
import lopicost.spd.helper.FicheroResiDetalleHelper;
import lopicost.spd.iospd.exportdata.process.ExcelFicheroResiDetallePlantUnifLite;
import lopicost.spd.model.DivisionResidencia;

import lopicost.spd.persistence.DivisionResidenciaDAO;
import lopicost.spd.persistence.FicheroResiDetalleDAO;
import lopicost.spd.struts.bean.CamposPantallaBean;
import lopicost.spd.struts.bean.FicheroResiBean;
import lopicost.spd.struts.bean.InfoAlertasBean;
import lopicost.spd.struts.form.FicheroResiForm;
import lopicost.spd.utils.HelperSPD;
import lopicost.spd.utils.SPDConstants;


public class FicheroResiDetalleLiteHstAction extends GenericAction  {

	
	FicheroResiDetalleDAO dao= new  FicheroResiDetalleDAO();
	
	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)			throws Exception {

		FicheroResiForm formulari =  (FicheroResiForm) form;
		List errors = new ArrayList();
		formulari.setErrors(errors);
	
		DivisionResidencia dr = DivisionResidenciaDAO.getDivisionResidenciaByOid(getIdUsuario(), formulari.getOidDivisionResidencia());
		if(dr==null) 
		{
			errors.add(SPDConstants.ERROR_FALTA_RESIDENCIA);	
			return mapping.findForward("listarTipoVista");
		}
		formulari.setIdDivisionResidencia(dr.getIdDivisionResidencia());
		formulari.setIdProcessIospd(dr.getIdProcessIospd());
		
		//recuperamos la cabecera del listado
		FicheroResiBean cab =  FicheroResiDetalleHelper.getCabeceraFicheroResi(getIdUsuario(), dr.getIdDivisionResidencia(), formulari.getIdProceso(), false, true );
		formulari.setListaTomasCabecera(FicheroResiDetalleHelper.getTomasCabeceraYHora(cab));	


		//String ordenacion="mensajesAlerta desc, mensajesResidencia desc, g.mensajesInfo desc, g.resiCIP, g.SpdNombreBolsa, g.resiInicioTratamiento ";
		String ordenacion="  g.resiCIP, g.SpdNombreBolsa, g.resiInicioTratamiento ";
		if(formulari.getCampoOrder()!=null && !formulari.getCampoOrder().equalsIgnoreCase(""))
			ordenacion=formulari.getCampoOrder();
		
		boolean excluirNoPintar=false;
		if(formulari.getExcluirNoPintar()!=null && formulari.getExcluirNoPintar().equalsIgnoreCase("SI"))
			excluirNoPintar=true;
		
		formulari.setListaFicheroResiDetalleBean(dao.getGestFicheroResiBolsa(getIdUsuario(), -1, formulari,  0,100000, null, false, ordenacion, true, excluirNoPintar, true));
		
		CamposPantallaBean camposPantallaBean = new CamposPantallaBean();
		HelperSPD.gestionVisibilidadCampos(camposPantallaBean, cab);
		formulari.setCamposPantallaBean(camposPantallaBean);
		
		FicheroResiDetalleHelper.rellenaListados(formulari);
		
		//INICIO creación de log en BBDD
		try{
			SpdLogAPI.addLog(getIdUsuario(), "",  formulari.getIdDivisionResidencia(), formulari.getIdProceso(),  SpdLogAPI.A_TRATAMIENTO, SpdLogAPI.B_CONSULTA, SpdLogAPI.C_LISTADO_HIST, "SpdLog.tratamiento.consulta.listado_hist", formulari.getIdProceso());
		}catch(Exception e){}	 //Consulta del listado de tratamientos de la producción
		//FIN creación de log en BBDD
		
		return mapping.findForward("listarTipoVista");
	
	}
	
	
	public ActionForward infoAlertas(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		FicheroResiForm formulari =  (FicheroResiForm) form;
		FicheroResiBean  frbean = dao.getFicheroResiDetalleByIdOid(getIdUsuario(), formulari.getOidFicheroResiDetalle(), true);
		
		List listInfoAlertas = new ArrayList();
		if(frbean!=null)
		{
			// C - (Número comprimidos)
			InfoAlertasBean infoAlertas = new InfoAlertasBean();
			infoAlertas.setTituloAlerta("C - (Número comprimidos) ");
			if(frbean.getControlNumComprimidos()!=null && frbean.getControlNumComprimidos().equalsIgnoreCase(SPDConstants.CTRL_NCOMPRIMIDOS_IGUAL))
			{
				infoAlertas.setCssAlerta("verde");
				infoAlertas.setTextoAlerta("Coincide  la previsión de comprimidos según fichero de la residencia (Previsión --> "+frbean.getPrevisionResi()+ ") y lo que se envía a robot (Previsión --> "+frbean.getPrevisionSPD()+ ") ");
			}
			else if(frbean.getControlNumComprimidos()!=null && frbean.getControlNumComprimidos().equalsIgnoreCase(SPDConstants.CTRL_NCOMPRIMIDOS_DIFERENTE))
			{
				infoAlertas.setCssAlerta("rojo");
				infoAlertas.setTextoAlerta("ALERTA - Comprobar comprimidos fichero de la residencia (Previsión --> "+frbean.getPrevisionResi()+ ") y lo que se envía a robot (Previsión --> "+frbean.getPrevisionSPD()+ ") ");
			}
			else
			{
				infoAlertas.setCssAlerta("naranja");
				infoAlertas.setAlertaNumComprimidos("No se detecta el número de comprimidos según fichero o es un tratamiento que no afecta a SPD ");
			}
			listInfoAlertas.add(infoAlertas);
			
			// I - (Registro anterior) 
			infoAlertas = new InfoAlertasBean();
			infoAlertas.setTituloAlerta("I - (Registro anterior) ");
			if(frbean.getControlRegistroAnterior()!=null && frbean.getControlRegistroAnterior().equalsIgnoreCase(SPDConstants.CTRL_REGISTRO_ANTERIOR_RI_SI))
			{
				infoAlertas.setCssAlerta("verde");
				infoAlertas.setTextoAlerta(" Registro reutilizado que coincide 100% con el anterior");
			}
			else if(frbean.getControlRegistroAnterior()!=null && frbean.getControlRegistroAnterior().equalsIgnoreCase(SPDConstants.CTRL_REGISTRO_ANTERIOR_RD_SI))
			{
				FicheroResiBean medResiAnterior = HelperSPD.recuperaDatosAnteriores(getIdUsuario(), frbean, true);
				
				
				infoAlertas.setCssAlerta("naranja");
				infoAlertas.setTextoAlerta("ALERTA.- Revisar registro, la salida es igual que la anterior, pero los datos de la resi son diferentes. <br>" 
				+ "<br>  ANTERIOR --> " + medResiAnterior.getDetalleRow() 
				+ "<br>  ACTUAL------> " + frbean.getDetalleRow());
			}
			else if(frbean.getControlRegistroAnterior()!=null && frbean.getControlRegistroAnterior().equalsIgnoreCase(SPDConstants.CTRL_REGISTRO_ANTERIOR_RI_SD))
			{
				FicheroResiBean medResiAnterior = HelperSPD.recuperaDatosAnteriores(getIdUsuario(), frbean, true);
				
				infoAlertas.setCssAlerta("rojo");
				if(medResiAnterior!=null)
				{
					infoAlertas.setTextoAlerta("ALERTA.-  REVISAR bien el tratamiento. Se envía diferente a la anterior producción. <br>"
							+ "<br>  ANTERIOR --> " + medResiAnterior.getIdTratamientoSPD() 
							+ "<br>  ACTUAL------> " + frbean.getIdTratamientoSPD());
				}
					
			}
			else if(frbean.getControlRegistroAnterior()!=null && frbean.getControlRegistroAnterior().equalsIgnoreCase(SPDConstants.CTRL_REGISTRO_ANTERIOR_RD_SD))
			{
				infoAlertas.setCssAlerta("azul");
				infoAlertas.setTextoAlerta("Registro nuevo");
			}
			listInfoAlertas.add(infoAlertas);
			
			// R - (envío a robot) 
			infoAlertas = new InfoAlertasBean();
			infoAlertas.setTituloAlerta("R - (envío a robot) ");
			if(frbean.getControlRegistroRobot()!=null && frbean.getControlRegistroRobot().equalsIgnoreCase(SPDConstants.CTRL_ROBOT_SE_ENVIA_A_ROBOT))
			{
				infoAlertas.setCssAlerta("verde");
				infoAlertas.setTextoAlerta("Se envía a robot como '" + frbean.getSpdAccionBolsa()+"'");
			}
			else if(frbean.getControlRegistroRobot()!=null && frbean.getControlRegistroRobot().equalsIgnoreCase(SPDConstants.CTRL_ROBOT_NO_SE_ENVIA))
			{
				infoAlertas.setCssAlerta("gris");
				infoAlertas.setTextoAlerta("NO se envía a robot porque es '" + frbean.getSpdAccionBolsa()+"'");
			}
			else 
			{
				infoAlertas.setCssAlerta("blanco");
				infoAlertas.setTextoAlerta("Revisar acción en bolsa del tratamiento");
			}		
			listInfoAlertas.add(infoAlertas);
			
			// D - (Validar datos)
			infoAlertas = new InfoAlertasBean();
			infoAlertas.setTituloAlerta("D - (Validar datos) ");
			if(frbean.getControlValidacionDatos()!=null && frbean.getControlValidacionDatos().equalsIgnoreCase(SPDConstants.CTRL_VALIDAR_NO))
			{
				infoAlertas.setCssAlerta("verde");
				infoAlertas.setTextoAlerta("Registro ok");
			}
			else if(frbean.getControlValidacionDatos()!=null && frbean.getControlValidacionDatos().equalsIgnoreCase(SPDConstants.CTRL_VALIDAR_ALERTA))
			{
				infoAlertas.setCssAlerta("naranja");
				infoAlertas.setTextoAlerta("Necesaria revisión de datos'");
			}
			else 
			{
				infoAlertas.setCssAlerta("blanco");
				infoAlertas.setTextoAlerta("No detectado");
			}		

			listInfoAlertas.add(infoAlertas);
			
			// P - Control de principio activo  
			infoAlertas = new InfoAlertasBean();
			infoAlertas.setTituloAlerta("P (Principio activo) ");
			if(frbean.getControlPrincipioActivo()!=null && frbean.getControlPrincipioActivo().equalsIgnoreCase(SPDConstants.CTRL_PRINCIPIO_ACTIVO_NO_ALERTA))
			{
				infoAlertas.setCssAlerta("verde");
				infoAlertas.setTextoAlerta("Registro ok");
				
			}
			else if(frbean.getControlPrincipioActivo()!=null && frbean.getControlPrincipioActivo().equalsIgnoreCase(SPDConstants.CTRL_PRINCIPIO_ACTIVO_ALERTA))
			{
				infoAlertas.setCssAlerta("amarillo");
				infoAlertas.setTextoAlerta("El principio activo de este tratamiento está marcado para CONTROL EXTRA  '" + frbean.getSpdNomGtVm()+"'");
			}
			else 
			{
				infoAlertas.setCssAlerta("blanco");
				infoAlertas.setTextoAlerta("No detectado");
			}
			listInfoAlertas.add(infoAlertas);



		    
			// S - Control de medicamento sustituible  
			infoAlertas = new InfoAlertasBean();
			infoAlertas.setTituloAlerta("S - Control de medicamento sustituible   ");
			if(frbean.getControlNoSustituible()!=null && frbean.getControlNoSustituible().equalsIgnoreCase(SPDConstants.CTRL_SUSTITUIBLE_NOALERTA))
			{
				infoAlertas.setCssAlerta("verde");
				infoAlertas.setTextoAlerta("Registro ok  ");
			}
			else if(frbean.getControlNoSustituible()!=null && frbean.getControlNoSustituible().equalsIgnoreCase(SPDConstants.CTRL_SUSTITUIBLE_ALERTA))
			{
				infoAlertas.setCssAlerta("rojo");
				infoAlertas.setTextoAlerta("Control medicamento NO sustituible  ");
			}
			else 
			{
				infoAlertas.setCssAlerta("blanco");
				infoAlertas.setTextoAlerta("No detectado");
			}
			listInfoAlertas.add(infoAlertas);

			

				
			// G - Control de GTVMP iguales  
			infoAlertas = new InfoAlertasBean();
			infoAlertas.setTituloAlerta("G - Control de GTVMP iguales  ");
			 if(frbean.getControlDiferentesGtvmp()!=null && frbean.getControlDiferentesGtvmp().equalsIgnoreCase(SPDConstants.CTRL_DIFERENTE_GTVMP_OK))
			{
				infoAlertas.setCssAlerta("verde");
				infoAlertas.setTextoAlerta("GTVMP ok ");
			}
			else if(frbean.getControlDiferentesGtvmp()!=null && frbean.getControlDiferentesGtvmp().equalsIgnoreCase(SPDConstants.CTRL_DIFERENTE_GTVMP_ALERTA))
			{
				infoAlertas.setCssAlerta("rojo");
				infoAlertas.setTextoAlerta(" El medicamento SPD tiene diferente GTVMP  que el de la residencia ");
			}
			else 
			{
				infoAlertas.setCssAlerta("blanco");
				infoAlertas.setTextoAlerta("No detectado");
			}

				listInfoAlertas.add(infoAlertas);

				// V - Control de GTVM ÚNICOS (para detectar tratamientos con el mismo GTVM) 
				infoAlertas = new InfoAlertasBean();
				infoAlertas.setTituloAlerta("V - Control de principio activo repetido");
				 if(frbean.getControlUnicoGtvm()!=null && frbean.getControlUnicoGtvm().equalsIgnoreCase(SPDConstants.CTRL_UNICO_GTVM_OK))
				{
					infoAlertas.setCssAlerta("verde");
					infoAlertas.setTextoAlerta("GTVM ok ");
				}
				else if(frbean.getControlUnicoGtvm()!=null && frbean.getControlUnicoGtvm().equalsIgnoreCase(SPDConstants.CTRL_UNICO_GTVM_ALERTA))
				{
					infoAlertas.setCssAlerta("rojo");
					infoAlertas.setTextoAlerta(" El residente tiene asignado más de un medicamento con este mismo principio activo ");
				}
				else 
				{
					infoAlertas.setCssAlerta("blanco");
					infoAlertas.setTextoAlerta("No detectado");
				}

				listInfoAlertas.add(infoAlertas);
				
				formulari.setListaInfoAlertas(listInfoAlertas);
			}

			List<String> errors = new ArrayList<String>();
			formulari.setErrors(errors);
			
			return mapping.findForward("infoAlertas");
		}
	

	public ActionForward exportExcel(ActionMapping mapping, ActionForm form,
	        HttpServletRequest request, HttpServletResponse response)
	        throws Exception {

			FicheroResiForm formulari =  (FicheroResiForm) form;
			List errors = new ArrayList();
			formulari.setErrors(errors);
		
			ActionForward actionForward = mapping.findForward("exportExcel");
			
			if(formulari.getACTIONTODO()!=null && !formulari.getACTIONTODO().equals("EXCEL"))
			{
				return list(mapping, form, request, response);
			}
	   		//Volvemos a poner list porque se quedaría exportExcel en cualquier acción posterior
			formulari.setACTIONTODO("list");
			
			//recuperamos la cabecera del listado
			FicheroResiBean cab =  FicheroResiDetalleHelper.getCabeceraFicheroResi(getIdUsuario(), formulari.getIdDivisionResidencia(), formulari.getIdProceso(), false, true);
			formulari.setListaTomasCabecera(FicheroResiDetalleHelper.getTomasCabeceraYHora(cab));	

		//	inicializamos para que no haya datos de otros módulos
			ExcelFicheroResiDetallePlantUnifLite excelCreator = new ExcelFicheroResiDetallePlantUnifLite();
			List results = dao.getGestFicheroResiBolsa(getIdUsuario(), formulari.getOidFicheroResiDetalle(), formulari, 0, 100000, "", false,  " g.resiCIP, g.resiMedicamento ", true, false );
			
			List resultsCab = new ArrayList<FicheroResiBean>();
			resultsCab.add(cab);
			resultsCab.addAll(results);
		
			String fechaDesde = HelperSPD.obtenerFechaDesde(cab.getIdProceso());  
			String fechaHasta = HelperSPD.obtenerFechaHasta(cab.getIdProceso()); 
	    	
			//HelperSPD.reordenarMatriz(cab);
		//	formulari.setListaTomasCabecera(FicheroResiDetalleHelper.getTomasCabeceraYHora(cab));	
			 // Lista para definir el orden deseado de las columnas
			// Lista para definir el orden deseado de las columnas
	        List<String> ordenColumnas = Arrays.asList("resiToma1", "resiToma2", "resiToma3", "resiToma41", "resiToma5", "resiToma7", "resiToma8", "resiToma6"); // Utilizando Arrays.asList()

	        // Llamar a la función para reorganizar las columnas
	        List<FicheroResiBean> resultadosReorganizados = HelperSPD.reordenarMatriz(resultsCab, ordenColumnas);

	        // Imprimir los resultados reorganizados
	        for (FicheroResiBean fila : resultadosReorganizados) {
	            System.out.println(fila.toString());
	        }
	        
			HSSFWorkbook workbook = excelCreator.createWorkbook(formulari, resultadosReorganizados);
        
	        String nombreFichero ="OK_"+cab.getIdDivisionResidencia().replace("general_",  "")+"_"+fechaDesde+"_" + fechaHasta+"_"+"1.xls";
	        
	    	
	        response.setHeader("Content-Disposition", "attachment; filename=/"+nombreFichero);
	        ServletOutputStream out = response.getOutputStream();
	        workbook.write(out);
	        out.flush();
	        out.close();
	        
			try{
				SpdLogAPI.addLog(getIdUsuario(), "",  formulari.getIdDivisionResidencia(), formulari.getIdProceso()
						, SpdLogAPI.A_TRATAMIENTO, SpdLogAPI.B_EXPORTACION, ".", "SpdLog.tratamiento.exportacion.info", nombreFichero  );//variables
			}catch(Exception e){}	//Exportada a Excel, nombre fichero @@.
			
			
			return actionForward;
	}
	

	
    }

