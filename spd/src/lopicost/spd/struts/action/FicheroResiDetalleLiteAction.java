package lopicost.spd.struts.action;


import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import lopicost.config.logger.Logger;
import lopicost.spd.controller.ControlSPD;
import lopicost.spd.controller.SpdLogAPI;
import lopicost.spd.helper.FicheroResiDetalleHelper;
import lopicost.spd.iospd.IOSpdApi;
import lopicost.spd.iospd.exportdata.process.*;
import lopicost.spd.model.DivisionResidencia;
import lopicost.spd.persistence.*;
import lopicost.spd.security.helper.PermisosHelper;
import lopicost.spd.struts.bean.*;
import lopicost.spd.struts.form.FicheroResiForm;
import lopicost.spd.struts.helper.PacientesHelper;
import lopicost.spd.utils.*;


public class FicheroResiDetalleLiteAction extends GenericAction  {

	private static  TreeMap pouchRegistro =new TreeMap();
	private static  TreeMap pouchLinesRegistro =new TreeMap();
	static TreeMap Doses_TreeMap =new TreeMap();
	
	FicheroResiDetalleDAO dao= new  FicheroResiDetalleDAO();
	
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

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
		

		//20201016 - Aparcamos la paginación de momento en esta pantalla
		//int getCountGestFicheroResiBolsa = dao.getCountGestFicheroResiBolsa(formulari);
		//int currpage = actualizaCurrentPage(formulari, getCountGestFicheroResiBolsa);
		//formulari.setListaFicheroResiDetalleBean(dao.getListaDivisionResidenciasCargadas());
		//Collections.sort(listaProcesosCargados);
		//formulari.setListaEstados(dao.getEstados());
		//formulari.setListaProcesosCargados(dao.getProcesosCargados(getIdUsuario(), formulari.getFiltroDivisionResidenciasCargadas()));
	
		//recuperamos la cabecera del listado
		FicheroResiBean cab =  FicheroResiDetalleHelper.getCabeceraFicheroResi(getIdUsuario(), dr.getIdDivisionResidencia(), formulari.getIdProceso(), false);
		formulari.setListaTomasCabecera(FicheroResiDetalleHelper.getTomasCabeceraYHora(cab));	
		

		//formulari.setTodaLaListaGestFicheroResiBolsaBean(FicheroResiDetalleDAO.getGestFicheroResiBolsa(formulari, 0,100000, null, true));
		//20201016 - Aparcamos la paginación de momento, el sistema está preparado si se necesita activar.
		//formulari.setListaGestFicheroResiBolsaBean(FicheroResiDetalleDAO.getGestFicheroResiBolsa(formulari, currpage*SPDConstants.PAGE_ROWS,(currpage+1)*SPDConstants.PAGE_ROWS, null, false));
		//formulari.setListaFicheroResiDetalleBean(FicheroResiDetalleDAO.getGestFicheroResiBolsa(-1, formulari,  0,100000, null, false, null, false, true));
		String ordenacion="mensajesAlerta desc, mensajesResidencia desc, g.mensajesInfo desc, g.resiCIP, g.SpdNombreBolsa, g.resiInicioTratamiento ";
		if(formulari.getCampoOrder()!=null && !formulari.getCampoOrder().equalsIgnoreCase(""))
			ordenacion=formulari.getCampoOrder();
		
		boolean excluirNoPintar=false;
		if(formulari.getExcluirNoPintar()!=null && formulari.getExcluirNoPintar().equalsIgnoreCase("SI"))
			excluirNoPintar=true;
		
		formulari.setListaFicheroResiDetalleBean(FicheroResiDetalleDAO.getGestFicheroResiBolsa(getIdUsuario(), -1, formulari,  0,100000, null, false, ordenacion, true, excluirNoPintar));
		
		CamposPantallaBean camposPantallaBean = new CamposPantallaBean();
		HelperSPD.gestionVisibilidadCampos(camposPantallaBean, cab);
		formulari.setCamposPantallaBean(camposPantallaBean);
		FicheroResiDetalleHelper.rellenaListados(formulari);
		
		//INICIO creación de log en BBDD  //no lo ponemos porque se sobrecarga al refrescar cada accion de validación o confirmación
		//try{
		//			SpdLogAPI.addLog(getIdUsuario(), "",  formulari.getIdDivisionResidencia(), formulari.getIdProceso(),  SpdLogAPI.A_TRATAMIENTO, SpdLogAPI.B_CONSULTA, SpdLogAPI.C_LISTADO, "SpdLog.tratamiento.consulta.listado", formulari.getIdProceso());
		//}catch(Exception e){}	 //Consulta del listado de tratamientos de la producción
		//FIN creación de log en BBDD
		FicheroResiBean cabeceraGeneral=FicheroResiCabeceraDAO.getFicheroResiCabeceraByOid(getIdUsuario(), cab.getOidFicheroResiCabecera());
		formulari.setFree1(cabeceraGeneral.getFree1());
		FicheroResiDetalleHelper.checkEstadoCabecera(getIdUsuario(), cab, cabeceraGeneral);
		return mapping.findForward("listarTipoVista");
	
	}
	

	
	
	public ActionForward editar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		FicheroResiForm formulari =  (FicheroResiForm) form;
		List errors = new ArrayList();
		formulari.setErrors(errors);
		ActionForward actionForward = mapping.findForward("editar");

		//recuperamos el detalle
		//FicheroResiBean  frb = FicheroResiDetalleHelper.getFicheroResiDetalleByIdOid(formulari, getIdUsuario());
		
		FicheroResiBean  frb = FicheroResiDetalleDAO.getFicheroResiDetalleByIdOid(getIdUsuario(), formulari.getOidFicheroResiDetalle());
		try
		{
			PacienteBean pac = PacientesHelper.getPacientePorCIP(frb.getResiCIP());
			if(pac!=null && pac.getCIP()!=null) formulari.setOidPaciente(String. valueOf(pac.getOidPaciente()));
		}
		catch(Exception e){}
		//Cabecera con los datos resumen 
		FicheroResiBean  topCabecera = FicheroResiCabeceraDAO.getFicheroResiCabecera(getIdUsuario(), formulari);
		//de momento, hasta que lo cambie a trabajo por objetos
		frb.setPorcentajeCIPS(topCabecera.getPorcentajeCIPS());
		
		//recuperamos la cabecera del listado
		FicheroResiBean cabListado  =  FicheroResiDetalleHelper.getCabeceraFicheroResi(getIdUsuario(), formulari.getIdDivisionResidencia(), formulari.getIdProceso(), false);
		formulari.setListaTomasCabecera(FicheroResiDetalleHelper.getTomasCabeceraYHora(cabListado));	
		
		
		formulari.setFicheroResiDetalleBean(frb);
		
		List<String> listaResiPeriodo = new ArrayList();
		formulari.setListaResiPeriodo(listaResiPeriodo);
		listaResiPeriodo.add(SPDConstants.SPD_PERIODO_DIARIO);
		listaResiPeriodo.add(SPDConstants.SPD_PERIODO_SEMANAL);
		listaResiPeriodo.add(SPDConstants.SPD_PERIODO_QUINCENAL);
		listaResiPeriodo.add(SPDConstants.SPD_PERIODO_MENSUAL);
		listaResiPeriodo.add(SPDConstants.SPD_PERIODO_SEMESTRAL);
		listaResiPeriodo.add(SPDConstants.SPD_PERIODO_ANUAL);
		listaResiPeriodo.add(SPDConstants.SPD_PERIODO_ESPECIAL);
		listaResiPeriodo.add(SPDConstants.SPD_PERIODO_DIAS_SEMANA_CONCRETOS);		
		
		List<String> listaSpdAccionBolsa = new ArrayList();
		formulari.setListaSpdAccionBolsa(listaSpdAccionBolsa);
		listaSpdAccionBolsa.add(SPDConstants.SPD_ACCIONBOLSA_NO_PINTAR);
		listaSpdAccionBolsa.add(SPDConstants.SPD_ACCIONBOLSA_SOLO_INFO);
		listaSpdAccionBolsa.add(SPDConstants.SPD_ACCIONBOLSA_PASTILLERO);
		listaSpdAccionBolsa.add(SPDConstants.SPD_ACCIONBOLSA_SI_PRECISA);
		
		boolean result=false;

		
		if(formulari.getACTIONTODO()!=null && formulari.getACTIONTODO().equals("EDITA_OK"))
		{
			validarDatosFormulario(formulari);
			
			//if(frb.getIdEstado()!=null && !frb.getIdEstado().equals(SPDConstants.REGISTRO_CREADO_AUTOMATICAMENTE) && !frb.getIdEstado().equals(SPDConstants.REGISTRO_CREADO_AUTOM_SECUENCIA_GUIDE))
			{
				if(formulari.getErrors().size()==0)
				{
					//frb.setValidar(SPDConstants.REGISTRO_VALIDAR);	//al reiniciar la sustitución forzamos una confirmación posterior
					//frb.setConfirmar("");

					
			  	    HelperSPD.actualizarBeanConFormulario(frb, formulari);
			  	    String cambios =frb.getFree3(); 
			  	    
			  	    if(cambios!=null && !cambios.equals(""))
			  	    {
			  	    	//20240915 En estudio, para limpiar las seqGuide al editar
						HelperSPD.borrarTratamientosSecuencialesPrevios(getIdUsuario(), frb);

						HelperSPD.controlAlertasRegistro(frb);
				      	HelperSPD.changeSintrom(frb);
				      	HelperSPD.detectarPeriodoEdicion(getIdUsuario(), frb);
			    		HelperSPD.detectarDiasMarcados(frb);
				      		
			    		
						if(HelperSPD.checkTrazodona(frb) && frb.getIdEstado()!=null && !frb.getIdEstado().equals(SPDConstants.REGISTRO_CREADO_AUTOMATICAMENTE) )
						{
			  	      		HelperSPD.changeTrazodona(getIdUsuario(), frb);
						}

				  	    HelperSPD.chequeoRevisionConfirmacion(frb);
				  	    HelperSPD.detectarTipoEnvioHeliumLite(frb);
				  	    
						if((frb.getResiCIP()==null || frb.getResiCIP().equals("")) && frb.getResiNombrePaciente()!=null && !frb.getResiNombrePaciente().equals("")) //en caso que no exista CIP  ponemos el nombre
						{
							FicheroResiDetalleHelper.actualizaCIP(getIdUsuario(), frb);
						}
						
						//HelperSPD.borrarTratamientosSecuencialesPrevios(getIdUsuario(), frb);
						//HelperSPD.desdoblarTratamientosSecuenciales(getIdUsuario(), frb, null);
						HelperSPD.desdoblarTratamientosSecuenciales(getIdUsuario(), frb, DateUtilities.getDate(frb.getResiInicioTratamiento(), "dd/MM/yyyy"));

						/**
						 * 20241015 - Este punto es importante, aunque haya redundancia del método de nuevo, hay que volver a llamarlo para que trate los ttos que se desdoblan y que son periódicos. (Aegerus)
						 */
				      	HelperSPD.detectarPeriodoEdicion(getIdUsuario(), frb);
				  	    
						
						frb.setValidar(SPDConstants.REGISTRO_VALIDAR);	//al reiniciar la sustitución forzamos una confirmación posterior
						ControlSPD.aplicarControles(getIdUsuario(), frb);
						frb.setIdTratamientoCIP(HelperSPD.getID(frb));	//actualizamos el id del tratamiento
						result=FicheroResiDetalleDAO.edita(getIdUsuario(), frb);

						if(result)
						{
							//INICIO creación de log en BBDD
							try{
								SpdLogAPI.addLog(getIdUsuario(), frb.getResiCIP(),  frb.getIdDivisionResidencia(), formulari.getIdProceso(), SpdLogAPI.A_TRATAMIENTO, SpdLogAPI.B_EDICION, SpdLogAPI.C_DETALLE, "SpdLog.tratamiento.edicion.detalle", frb.getFree3());
							}catch(Exception e){}	// Cambios--> @@.
							//FIN creación de log en BBDD
							
							errors.add( "Registro editado correctamente ");
						}
						else errors.add( "No se ha podido editar el registro");

			  	    } 
					
					list( mapping,  form,  request,  response);
					actionForward= mapping.findForward("listarTipoVista");

				}
				else
				{
					formulari.setACTIONTODO("EDITA");
					
				}
				
			}
			formulari.setErrors(errors);
		}
		
		CamposPantallaBean camposPantallaBean = new CamposPantallaBean();
		HelperSPD.gestionVisibilidadCampos(camposPantallaBean, frb);
		formulari.setCamposPantallaBean(camposPantallaBean);
		
		return actionForward;
	}


	
	public ActionForward actualizarPrevision(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		boolean resultTotal=true;
		List errors = new ArrayList();

			
			
		FicheroResiForm formulari =  (FicheroResiForm) form;
		List<FicheroResiBean> filas=FicheroResiDetalleDAO.getGestFicheroResiBolsa( getIdUsuario(), -1, formulari,  0,100000, null, false, "g.resiCIP, g.SpdNombreBolsa, g.resiInicioTratamiento ", true, false);  
		//List filas = FicheroResiDetalleDAO.getGestFicheroResiBolsa(getIdUsuario(), -1, formulari, 0, 100000, null, true, null, true, false);
		boolean result=false;
		if(errors.isEmpty() && formulari.getACTIONTODO()!=null && formulari.getACTIONTODO().equals("CONFIRMADO_OK"))
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
					errors.add( "No se ha podido actualizar la fila " + fila.getRow() + " - " + fila.getResiCIP() + " - " + fila.getSpdCnFinal());
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
			
				//INICIO creación de log en BBDD
				try{
					SpdLogAPI.addLog(getIdUsuario(), "",  formulari.getIdDivisionResidencia(), formulari.getIdProceso(), SpdLogAPI.A_TRATAMIENTO, SpdLogAPI.B_CREACION, SpdLogAPI.C_PREVISION, "SpdLog.tratamiento.creacion.prevision", formulari.getIdProceso());
				}catch(Exception e){}	//Se actualiza la previsión del proceso.
				//FIN creación de log en BBDD

			}
			else
			
			if (!result){
				 errors.add( "Error en la actualización de previsión de consumo");
				 log("[FicheroResiCabeceraAction_BORRABLE] - confirmar () - actualizando previsión de la fila: "+formulari.getIdDivisionResidencia() +" - " + formulari.getIdProceso(),Logger.ERROR);
				throw new Exception("Error en la actualización de previsión de consumo");					
			}
			
			
			list( mapping,  form,  request,  response);
			formulari.setErrors(errors);
			
		}
		return mapping.findForward("listarTipoVista");
	}

		
	public ActionForward validar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		FicheroResiForm formulari =  (FicheroResiForm) form;
		List errors = new ArrayList();
		boolean result = false;
		FicheroResiBean  frbean = FicheroResiDetalleDAO.getFicheroResiDetalleByIdOid(getIdUsuario(), formulari.getOidFicheroResiDetalle());
		boolean  validable = ControlSPD.registroValidable(frbean);

		if(validable)
		{
			frbean.setValidar(SPDConstants.REGISTRO_VALIDADO);
			//si no hay que confirmar y ya está validado quitamos el alert de control de datos.
			if(frbean.getConfirmar()!=null 
					&& !frbean.getConfirmar().equalsIgnoreCase(SPDConstants.REGISTRO_CONFIRMAR) 
					&& frbean.getControlValidacionDatos()!=null 
					&& frbean.getControlValidacionDatos().equalsIgnoreCase(SPDConstants.CTRL_VALIDAR_ALERTA)
					)
			{
				frbean.setControlValidacionDatos(SPDConstants.CTRL_VALIDAR_NO);
			}
			
			result=FicheroResiDetalleDAO.edita(getIdUsuario(), frbean);

			if(validable && result)
			{
				//INICIO eación de log en BBDD
				try{
					SpdLogAPI.addLog(getIdUsuario(),  frbean.getResiCIP(),  frbean.getIdDivisionResidencia(), frbean.getIdProceso()
							, SpdLogAPI.A_TRATAMIENTO, SpdLogAPI.B_VALIDACION, ".", "SpdLog.tratamiento.validar",  HelperSPD.tratamientoLog(frbean) );//variables
				}catch(Exception e){}	//Validación del tratamiento @@
				//FIN de log en BBDD
				errors.add( "Registro validado correctamente");
				//volvemos a mostrar listado
				formulari.setOidFicheroResiDetalle(0);

					
			}
			else errors.add( "No se ha podido validar el registro");

			list( mapping,  form,  request,  response);
			formulari.setErrors(errors);
		}
		
		return mapping.findForward("listarTipoVista");
	}
	

	public ActionForward confirmacionMasiva(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		FicheroResiForm formulari =  (FicheroResiForm) form;
		FicheroResiBean cabeceraGeneral=FicheroResiCabeceraDAO.getFicheroResiCabeceraByOid(getIdUsuario(),formulari.getOidFicheroResiCabecera());
		
		//actualizamos campos que lanzan el botón para que no se valide de nuevo en caso de entrar por segunda vez 
		formulari.setFree1("");
		formulari.setFree3(SPDConstants.SPD_PROCESO_3_POR_VALIDACION_MASIVA); 
		FicheroResiCabeceraDAO.editarCabecera(getIdUsuario(), cabeceraGeneral, formulari);
		
		
		boolean  confirmable = PermisosHelper.confirmacion(getIdUsuario(), formulari.getIdDivisionResidencia());
		List errors = new ArrayList();
		formulari.setSeleccionValidar(SPDConstants.REGISTRO_VALIDAR);
		List listaBeans = FicheroResiDetalleDAO.getGestFicheroResiBolsa(getIdUsuario(), -1, formulari,  0,100000, null, false, "", true, false);
		Iterator _it = listaBeans.iterator();
		while(_it.hasNext())
		{
			boolean  validadoAutomatico = false; 
			FicheroResiBean  frbean = (FicheroResiBean)_it.next();
			boolean  registroConfirmable = ControlSPD.registroConfirmableAutomatico(frbean);
			boolean result=false;

  			//if(frbean.getValidar().equalsIgnoreCase(SPDConstants.REGISTRO_VALIDAR))
  			if(registroConfirmable)
  			{
  				frbean.setIncidencia("NO");
	  			frbean.setConfirmar(SPDConstants.REGISTRO_CONFIRMADO);
  				frbean.setValidar(SPDConstants.REGISTRO_VALIDADO);
				//si no hay que confirmar y ya está validado quitamos el alert de control de datos.
  				frbean.setControlValidacionDatos(SPDConstants.CTRL_VALIDAR_NO);
  				validadoAutomatico=true;
	  			result=FicheroResiDetalleDAO.edita(getIdUsuario(), frbean);
  			}
  			
  			if(confirmable && result)
			{
				//creación de log en BBDD
				try{
					SpdLogAPI.addLog(getIdUsuario(),  frbean.getResiCIP(),  frbean.getIdDivisionResidencia(), frbean.getIdProceso(), SpdLogAPI.A_TRATAMIENTO, SpdLogAPI.B_CONFIRMACION_MASIVA, "."
							, "SpdLog.tratamiento.confirmadoMasivo", HelperSPD.tratamientoLog(frbean) );
				}catch(Exception e){}	//Confirmación del tratamiento @@.
				
				if(validadoAutomatico)
				{
					//creación de log en BBDD
					try{
						SpdLogAPI.addLog(getIdUsuario(),  frbean.getResiCIP(),  frbean.getIdDivisionResidencia(), frbean.getIdProceso(), SpdLogAPI.A_TRATAMIENTO, SpdLogAPI.B_VALIDACION, "."
								, "SpdLog.tratamiento.validadoPorConfirmacionMasiva", "." );
					}catch(Exception e){}	//Confirmación del tratamiento @@.
					
				}
			}

		}
        String oidDivisionResidencia = "someValue"; // Obtener el valor necesario
        String url = "/FicheroResiCabeceraLite.do?parameter=list";

        // Crear un ActionForward dinámico
        return new ActionForward(url, true);
		//return mapping.findForward("listarTipoVista");
	}
	
	public ActionForward confirmar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		FicheroResiForm formulari =  (FicheroResiForm) form;
		List errors = new ArrayList();
		boolean result=false;
		
		if(formulari.getACTIONTODO()!=null && formulari.getACTIONTODO().equals(SPDConstants.REGISTRO_CONFIRMAR))
		{
			boolean  confirmable = PermisosHelper.confirmacion(getIdUsuario(), formulari.getIdDivisionResidencia());
			boolean  validadoAutomatico = false; 
			if(!confirmable)
				errors.add( "Permisos insuficientes para para realizar confirmación del tratamiento");
			else{
				FicheroResiBean  frbean = FicheroResiDetalleDAO.getFicheroResiDetalleByIdOid(getIdUsuario(), formulari.getOidFicheroResiDetalle());
				boolean  registroConfirmable = ControlSPD.registroConfirmable(frbean);

	  			//if(frbean.getValidar().equalsIgnoreCase(SPDConstants.REGISTRO_VALIDAR))
	  			if(registroConfirmable)
	  			{
		  			frbean.setConfirmar(SPDConstants.REGISTRO_CONFIRMADO);
	  				frbean.setValidar(SPDConstants.REGISTRO_VALIDADO);
					//si no hay que confirmar y ya está validado quitamos el alert de control de datos.
	  				frbean.setControlValidacionDatos(SPDConstants.CTRL_VALIDAR_NO);
	  				validadoAutomatico=true;
		  			result=FicheroResiDetalleDAO.edita(getIdUsuario(), frbean);
	  			}
	  				
	  			
	  			if(confirmable && result)
				{
					//creación de log en BBDD
					try{
						SpdLogAPI.addLog(getIdUsuario(),  frbean.getResiCIP(),  frbean.getIdDivisionResidencia(), frbean.getIdProceso(), SpdLogAPI.A_TRATAMIENTO, SpdLogAPI.B_CONFIRMACION, "."
								, "SpdLog.tratamiento.confirmar", HelperSPD.tratamientoLog(frbean) );
					}catch(Exception e){}	//Confirmación del tratamiento @@.
					
					if(validadoAutomatico)
					{
						//creación de log en BBDD
						try{
							SpdLogAPI.addLog(getIdUsuario(),  frbean.getResiCIP(),  frbean.getIdDivisionResidencia(), frbean.getIdProceso(), SpdLogAPI.A_TRATAMIENTO, SpdLogAPI.B_VALIDACION, "."
									, "SpdLog.tratamiento.validadoPorConfirmacion", "." );
						}catch(Exception e){}	//Confirmación del tratamiento @@.
						
					}

				
					
					errors.add( "Registro confirmado correctamente ");
					//volvemos a mostrar listado
					formulari.setOidFicheroResiDetalle(0);
				}
				else errors.add( "No se ha podido confirmar el registro");

			}
			
			list( mapping,  form,  request,  response);
			formulari.setErrors(errors);
			}
		
		return mapping.findForward("listarTipoVista");
	}

	
	
	
	
	/**
	 * Método que actualiza los datos del detalle, cruzándolo con los datos de gestión.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward refrescar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		FicheroResiForm formulari =  (FicheroResiForm) form;
		List errors = new ArrayList();
	
		boolean result=false;
		System.out.println( "FicheroResiDetalleLiteAction refrescar: INICIO "  + new Date() );


		if(formulari.getACTIONTODO()!=null && formulari.getACTIONTODO().equals("REFRESCAR"))
		{
			
			FicheroResiBean  frbean = FicheroResiDetalleDAO.getFicheroResiDetalleByIdOid(getIdUsuario(), formulari.getOidFicheroResiDetalle());

			//para registros que no se han creado automáticamente, sino podrían volver a actualizarse de inicio, lo que supondría incoherencias 
			if(frbean.getIdEstado()!=null && !frbean.getIdEstado().equals(SPDConstants.REGISTRO_CREADO_AUTOMATICAMENTE) && !frbean.getIdEstado().equals(SPDConstants.REGISTRO_CREADO_AUTOM_SECUENCIA_GUIDE))
			{
				//if(frbean!=null && (frbean.getSpdCnFinal()==null ||  frbean.getSpdCnFinal().equals("")))
				GestSustitucionesLiteDAO.buscaSustitucionLite(getIdUsuario(), frbean);
			}
					
				//TODO para que refresque sin dar  más clicks pero habría que hacer que solo hubiera un update
		
				HelperSPD.controlAlertasRegistro(frbean);
		      	HelperSPD.detectarPeriodoEdicion(getIdUsuario(), frbean);
		      	HelperSPD.chequeoRevisionConfirmacion(frbean);
		  	    HelperSPD.detectarTipoEnvioHeliumLite(frbean);
				if((frbean.getResiCIP()==null || frbean.getResiCIP().equals("")) && frbean.getResiNombrePaciente()!=null && !frbean.getResiNombrePaciente().equals("")) //en caso que no exista CIP  ponemos el nombre
				{
					FicheroResiDetalleHelper.actualizaCIP(getIdUsuario(), frbean);
				}    
		  	      
				
				//HelperSPD.borrarTratamientosSecuencialesPrevios(getIdUsuario(), frbean);
				//HelperSPD.desdoblarTratamientosSecuenciales(getIdUsuario(), frbean, null);
				HelperSPD.desdoblarTratamientosSecuenciales(getIdUsuario(), frbean, DateUtilities.getDate(frbean.getResiInicioTratamiento(), "dd/MM/yyyy"));
				
				
				/**
				 * 20241015 - Este punto es importante, aunque haya redundancia del método de nuevo, hay que volver a llamarlo para que trate los ttos que se desdoblan y que son periódicos. (Aegerus)
				 */
		      	HelperSPD.detectarPeriodoEdicion(getIdUsuario(), frbean);
		  	    
	
		  	    
				
				//HelperSPD.controlGtvmpCnResiCnSpd(frbean);
							
				CamposPantallaBean camposPantallaBean = new CamposPantallaBean();
				HelperSPD.gestionVisibilidadCampos(camposPantallaBean, frbean);
				formulari.setCamposPantallaBean(camposPantallaBean);
				if(frbean.getSpdAccionBolsa().equalsIgnoreCase(SPDConstants.SPD_ACCIONBOLSA_SOLO_INFO) || frbean.getSpdAccionBolsa().equalsIgnoreCase(SPDConstants.SPD_ACCIONBOLSA_PASTILLERO))
				{
					frbean.setValidar(SPDConstants.REGISTRO_VALIDAR);	//al reiniciar la sustitución forzamos una confirmación posterior
					//frbean.setConfirmar("");
					frbean.setConfirmar(SPDConstants.REGISTRO_CONFIRMAR);	//al reiniciar la sustitución forzamos una confirmación posterior
				}
				
				ControlSPD.aplicarControles(getIdUsuario(), frbean);
				
				result=FicheroResiDetalleDAO.edita(getIdUsuario(), frbean);
				
				if(result)
				{
					errors.add( "Registro refrescado correctamente ");
					//volvemos a mostrar listado
					formulari.setOidFicheroResiDetalle(0);
					//INICIO eación de log en BBDD
					try{
						SpdLogAPI.addLog(getIdUsuario(),  frbean.getResiCIP(),  frbean.getIdDivisionResidencia(), frbean.getIdProceso()
								, SpdLogAPI.A_TRATAMIENTO, SpdLogAPI.B_EDICION, ".", "SpdLog.tratamiento.refrescar",  HelperSPD.tratamientoLog(frbean) );//variables
					}catch(Exception e){}	//Se inicializa el tratamiento @@.
					//FIN de log en BBDD
					
					
					
				}
				else errors.add( "No se ha podido refrescar el registro");

				

	
			list( mapping,  form,  request,  response);
			formulari.setErrors(errors);
			}
		
		System.out.println( "FicheroResiDetalleLiteAction refrescar: FIN "  + new Date() );

		
		return mapping.findForward("listarTipoVista");
	}

	
	public ActionForward infoAlertas(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		FicheroResiForm formulari =  (FicheroResiForm) form;
		FicheroResiBean  frbean = FicheroResiDetalleDAO.getFicheroResiDetalleByIdOid(getIdUsuario(), formulari.getOidFicheroResiDetalle());
		
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

	

	
	public ActionForward exportFilasConInfo(ActionMapping mapping, ActionForm form,
	        HttpServletRequest request, HttpServletResponse response)
	        throws Exception {

			FicheroResiForm formulari =  (FicheroResiForm) form;
			List errors = new ArrayList();
			formulari.setErrors(errors);
		
			ActionForward actionForward = mapping.findForward("exportFilasConInfo");
			
			if(formulari.getACTIONTODO()!=null && !formulari.getACTIONTODO().equals("EXCEL"))
			{
				return list(mapping, form, request, response);
			}
	   		//Volvemos a poner list porque se quedaría exportExcel en cualquier acción posterior
			formulari.setACTIONTODO("list");
			

		//	inicializamos para que no haya datos de otros módulos
			ExcelFilasConInfo excelCreator = new ExcelFilasConInfo();
			HSSFWorkbook workbook = excelCreator.createWorkbook(formulari, dao.getFilasConMensajesInfo(getIdUsuario(), formulari.getIdProceso()));
			String nombreFichero = formulari.getIdProceso().replace("general_",  "")+"_/exportFilasConInfo.xls";
	        response.setHeader("Content-Disposition", "attachment; filename="+nombreFichero);
	        ServletOutputStream out = response.getOutputStream();
	        workbook.write(out);
	        out.flush();
	        out.close();
	        
			//INICIO eación de log en BBDD
			try{
				SpdLogAPI.addLog(getIdUsuario(), "",  formulari.getIdDivisionResidencia(), formulari.getIdProceso()
						, SpdLogAPI.A_TRATAMIENTO, SpdLogAPI.B_EXPORTACION, ".", "SpdLog.tratamiento.exportacion.info", nombreFichero  );//variables
			}catch(Exception e){}	//Exportada a Excel, nombre fichero @@.
			//FIN de log en BBDD
			
			
			return actionForward;
	}
	
	public ActionForward exportFilasSinSust(ActionMapping mapping, ActionForm form,
	        HttpServletRequest request, HttpServletResponse response)
	        throws Exception {

			FicheroResiForm formulari =  (FicheroResiForm) form;
			List errors = new ArrayList();
			formulari.setErrors(errors);
		
			ActionForward actionForward = mapping.findForward("exportSustPendientes");
			
			if(formulari.getACTIONTODO()!=null && !formulari.getACTIONTODO().equals("EXCEL"))
			{
				return list(mapping, form, request, response);
			}
	   		//Volvemos a poner list porque se quedaría exportExcel en cualquier acción posterior
			formulari.setACTIONTODO("list");
			
			String nombreFichero = formulari.getIdProceso().replace("general_",  "")+"_/exportSustPendientes.xls";

		//	inicializamos para que no haya datos de otros módulos
			ExcelSustitucionesPendientes  excelCreator = new ExcelSustitucionesPendientes();
			HSSFWorkbook workbook = excelCreator.createWorkbook(formulari, dao.getSustitucionesPendientes(formulari.getIdDivisionResidencia(), formulari.getIdProceso()));
		
	        response.setHeader("Content-Disposition", "attachment; filename=/"+nombreFichero);
	        ServletOutputStream out = response.getOutputStream();
	        workbook.write(out);
	        out.flush();
	        out.close();
	        
			//INICIO eación de log en BBDD
			try{
				SpdLogAPI.addLog(getIdUsuario(), "",  formulari.getIdDivisionResidencia(), formulari.getIdProceso()
						, SpdLogAPI.A_TRATAMIENTO, SpdLogAPI.B_EXPORTACION, ".", "SpdLog.tratamiento.exportacion.info",  new String[]{ formulari.getIdProceso(), nombreFichero}  );//variables
			}catch(Exception e){}	// Exportada a Excel, nombre fichero @@.
			//FIN de log en BBDD

			
			
			return actionForward;
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
			FicheroResiBean cab =  FicheroResiDetalleHelper.getCabeceraFicheroResi(getIdUsuario(), formulari.getIdDivisionResidencia(), formulari.getIdProceso(), false);
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
 
	/*
	public void generarFicherosHelium(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		FicheroResiForm formulari =  (FicheroResiForm) form;
		FiliaDM careHome = HeliumHelper.generarFicherosHelium(getIdUsuario(), formulari);
		
		List<String> errors = new ArrayList<String>();
		formulari.setErrors(errors);

		HeliumHelper.generaFichero(careHome,  response);
 	}
*/
	
	private void validarDatosFormulario(FicheroResiForm formulari) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		// Realiza la validación de la fecha aquí
		String fechaInicio=formulari.getResiInicioTratamiento();
		String fechaFin=formulari.getResiFinTratamiento();
		
		//validación de fechas
		if (fechaInicio!= null && !fechaInicio.trim().isEmpty() && !DateUtilities.isDateValid(fechaInicio, "dd/MM/yyyy")) 
               	formulari.getErrors().add("Fecha inicio tratamiento -  Formato incorrecto");
		
		if (fechaFin!= null && !fechaFin.trim().isEmpty() && !DateUtilities.isDateValid(fechaFin, "dd/MM/yyyy")) 
	           formulari.getErrors().add("Fecha fin tratamiento -  Formato incorrecto");
		        
		//validación de tomas
		 // Realiza la validación de los campos
		BigDecimal number ;
        for (int i = 1; i <= 24; i++) {
            String fieldName = "ResiToma" + i;
            String fieldValue = formulari.getClass().getMethod("get" + fieldName).invoke(formulari)!=null?formulari.getClass().getMethod("get" + fieldName).invoke(formulari).toString():"";


            // Verificar si el campo está vacío o no es un número válido
            if (fieldValue != null && !fieldValue.equalsIgnoreCase("null") && !fieldValue.trim().isEmpty()) {
            	
            	try {
            		// number = Double.parseDouble(fieldValue);
            		// Intenta parsear el valor como un número
            		fieldValue = fieldValue.replace(',', '.');
            		
                     number = new BigDecimal(fieldValue);
                     if (number.scale() > 2) {
                         throw new NumberFormatException();  } 
                         
                } catch (NumberFormatException e) {
                    // El valor no es un número válido
                	formulari.getErrors().add("Revisar número de la " + i + "ª toma " );
                }
   
            } 
        }


    }


}
	