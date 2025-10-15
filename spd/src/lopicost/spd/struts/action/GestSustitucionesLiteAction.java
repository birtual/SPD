package lopicost.spd.struts.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import lopicost.spd.controller.SpdLogAPI;
import lopicost.spd.helper.FicheroResiDetalleHelper;
import lopicost.spd.model.BdConsejo;
import lopicost.spd.model.DivisionResidencia;
import lopicost.spd.model.GestSustitucionesLite;
import lopicost.spd.persistence.BdConsejoDAO;
import lopicost.spd.persistence.DivisionResidenciaDAO;

import lopicost.spd.persistence.GestSustitucionesLiteDAO;
import lopicost.spd.struts.bean.FicheroResiBean;
import lopicost.spd.struts.form.GestSustitucionesLiteForm;
import lopicost.spd.utils.DataUtil;
import lopicost.spd.utils.HelperSPD;
import lopicost.spd.utils.SPDConstants;
public class GestSustitucionesLiteAction extends GestSustitucionesAction  {

	GestSustitucionesLiteDAO dao= new  GestSustitucionesLiteDAO();
	
	

	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	
		
		List<GestSustitucionesLite> resultList= new ArrayList<GestSustitucionesLite>();
		GestSustitucionesLiteForm formulari =  (GestSustitucionesLiteForm) form;

		//si llega residencia la recogemos	
		DivisionResidencia dr = DivisionResidenciaDAO.getDivisionResidenciaByOid(getIdUsuario(), formulari.getOidDivisionResidenciaFiltro());
		formulari.setIdDivisionResidencia(dr!=null?dr.getIdDivisionResidencia():"0");

		
		//inicializamos para que no haya datos de otros m�dulos
		
		int getCountSustitucionesLite = dao.getCountSustitucionesLite(getIdUsuario(), formulari);
		int currpage = actualizaCurrentPage(formulari, getCountSustitucionesLite);
		formulari.setOidGestSustitucionesLite(0);
		formulari.setListaSustituciones(dao.getSustitucionesListadoLite(getIdUsuario(), formulari, currpage*SPDConstants.PAGE_ROWS,(currpage+1)*SPDConstants.PAGE_ROWS, false));
	//	Lista a = GestSustXConjHomogDAO.get
	//	gestSustitucionesForm.setSustXConjHomog();
		
		//formulari.setBdConsejoResi(BdConsejoDAO.getBdConsejobyCN());
		
	//	gestSustitucionesForm.setOidDivisionResidenciaFiltro(gestSustitucionesForm.getOidDivisionResidenciaFiltro());
	//	gestSustitucionesForm.setNombreCortoFiltro(gestSustitucionesForm.getNombreCortoFiltro());
		
		formulari.setListaDivisionResidencia(DivisionResidenciaDAO.getListaDivisionResidencias(getIdUsuario()));
		formulari.setListaMedicamentoResi(dao.getMedicamentoResiLite(getIdUsuario()));
		formulari.setListaNombreCortoOK(dao.getNombresCortosOK());
		formulari.setListaGtVm(dao.getGtvmLite());
		formulari.setListaGtVmp(dao.getGtvmpLite(formulari.getFiltroGtVm()));
		//formulari.setListaGtVmp(BdConsejoDAO.getListaGtVmp());
		
	//	formulari.setListaPresentacion(dao.getPresentacion());
	//	formulari.setListaTiposAccion(dao.getListaTiposAccion());
	//	formulari.setListaFormasFarmaceuticas(dao.getFormasFarmaceuticas());
		
			
	//	gestSustitucionesForm.setACTIONTODO(gestSustitucionesForm.getACTIONTODO());
	//	request.setAttribute("resultList", dao.getTodas());
	//	request.setAttribute("gestSustitucionesForm", gestSustitucionesForm);
		
		List errors = new ArrayList();
		formulari.setErrors(errors);

			return mapping.findForward("list");
	}
	
	public ActionForward nuevo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
			//inicializamos para que no haya datos de otros m�dulos
			GestSustitucionesLiteForm f =  (GestSustitucionesLiteForm) form;
			DivisionResidencia dr = DivisionResidenciaDAO.getDivisionResidenciaByOid(getIdUsuario(), f.getOidDivisionResidenciaFiltro());
			f.setListaTiposAccion(GestSustitucionesLiteDAO.getListaTiposAccion());
			f.setListaDivisionResidencia(DivisionResidenciaDAO.getListaDivisionResidencias(getIdUsuario()));
			f.setIdDivisionResidencia(dr!=null?dr.getIdDivisionResidencia():"0");
			System.out.println(HelperSPD.dameFechaHora() + " nuevo --> ACTIONTODO " + f.getACTIONTODO());		

			List errors = new ArrayList();
			boolean result=false;
			System.out.println("formulari.getACTIONTODO() main "  +f.getACTIONTODO());
			GestSustitucionesLite sustitucionLite = new GestSustitucionesLite();
			

			
			if(f.getACTIONTODO()!=null && f.getACTIONTODO().equals("NUEVO_EXPRESS"))
			{
				sustitucionLite.setIdDivisionResidencia(f.getIdDivisionResidencia());
				sustitucionLite.setSpdCn("");
				sustitucionLite.setResiCn(f.getFiltroCn()!=null&&!f.getFiltroCn().equals("")?f.getFiltroCn():f.getResiCn()!=null&&!f.getResiCn().equals("")?f.getResiCn():"");
				sustitucionLite.setOidDivisionResidencia(new Float(f.getOidDivisionResidencia()).intValue());
				sustitucionLite.setOidGestSustitucionesLite(0);
				sustitucionLite.setSpdNombreBolsa("");
				sustitucionLite.setResiMedicamento(f.getFiltroMedicamentoResi()!=null&&!f.getFiltroMedicamentoResi().equals("")?f.getFiltroMedicamentoResi():"");
				sustitucionLite.setExcepciones("");
				sustitucionLite.setAux1("");
				sustitucionLite.setAux2("");
				System.out.println(HelperSPD.dameFechaHora() + " nuevo --> NUEVO_EXPRESS f.getFiltroCn() - f.getResiCn()  " + f.getFiltroCn() +" - " + f.getResiCn());		
				System.out.println(HelperSPD.dameFechaHora() + " nuevo --> NUEVO_EXPRESS f.getFiltroMedicamentoResi()  " + f.getFiltroMedicamentoResi());		

				//sustituciones sugeridas de otras resis
				//f.setListaSustituciones(dao.getSustitucionesListadoLite( getIdUsuario(), -1, -1, f.getResiCn(), f.getFiltroGtVm(), f.getFiltroGtVmp(), f.getCampoGoogle(), 1, 20, false));
				f.setListaSustituciones(dao.getSustitucionesListadoLiteExpress( getIdUsuario(), f.getResiCn(), f.getResiMedicamento()));

				
				
			}
			else if(f.getACTIONTODO()!=null && f.getACTIONTODO().equals("NUEVO"))
			{
				sustitucionLite.setIdDivisionResidencia(f.getIdDivisionResidencia());
				sustitucionLite.setSpdCn("");
				sustitucionLite.setResiCn(f.getFiltroCn()!=null&&!f.getFiltroCn().equals("")?f.getFiltroCn():f.getResiCn()!=null&&!f.getResiCn().equals("")?f.getResiCn():"");
				sustitucionLite.setOidDivisionResidencia(new Float(f.getOidDivisionResidencia()).intValue());
				sustitucionLite.setOidGestSustitucionesLite(0);
				sustitucionLite.setSpdNombreBolsa("");
				sustitucionLite.setResiMedicamento(f.getFiltroMedicamentoResi()!=null&&!f.getFiltroMedicamentoResi().equals("")?f.getFiltroMedicamentoResi():"");
				sustitucionLite.setExcepciones("");
				sustitucionLite.setAux1("");
				sustitucionLite.setAux2("");
				System.out.println(HelperSPD.dameFechaHora() + " nuevo --> NUEVO f.getFiltroCn() - f.getResiCn()  " + f.getFiltroCn() +" - " + f.getResiCn());		
				System.out.println(HelperSPD.dameFechaHora() + " nuevo --> NUEVO f.getFiltroMedicamentoResi()  " + f.getFiltroMedicamentoResi());		

				//sustituciones sugeridas de otras resis
				//f.setListaSustituciones(dao.getSustitucionesListadoLite( getIdUsuario(), -1, -1, f.getResiCn(), f.getFiltroGtVm(), f.getFiltroGtVmp(), f.getCampoGoogle(), 1, 20, false));
				f.setListaSustituciones(dao.getSustitucionesListadoLite( getIdUsuario(), -1, -1, f.getResiCn(), f.getFiltroGtVm(), f.getFiltroGtVmp(), f.getCampoGoogle(), 1, 20, false));

				
				
			}
			else if(f.getACTIONTODO()!=null && f.getACTIONTODO().equals("NUEVO_OK"))
			{
				boolean sustCreada =GestSustitucionesLiteDAO.nuevoSustLite(f);
				System.out.println(HelperSPD.dameFechaHora() + " nuevo --> NUEVO_OK - sustCreada" + sustCreada);		

				if(sustCreada)
				{
					errors.add( "Registro creado correctamente ");
					//INICIO creación de log en BBDD
					try{
						SpdLogAPI.addLog(getIdUsuario(), "",  f.getIdDivisionResidencia(), f.getIdProceso(), SpdLogAPI.A_SUSTITUCION, SpdLogAPI.B_CREACION, SpdLogAPI.C_DATOSGENERALES, "SpdLog.sustitucion.creacion.datosGenerales", 
								new String[]{
									" | "+f.getResiCn()+" "+ f.getResiMedicamento() + " | ", 
									" | "+ f.getSpdCn() +" "+ f.getSpdNombreBolsa() +" "+ f.getIdTipoAccion() + " | "
									} );
					}catch(Exception e){}	//Se actualiza la previsión del proceso.
					//FIN creación de log en BBDD
					
				}
				else errors.add( "No se ha podido crear el registro");

				list( mapping,  form,  request,  response);
				f.setErrors(errors);
				return mapping.findForward("list");
				
			}
			return mapping.findForward("nuevo");
	}
	
	public ActionForward editar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		GestSustitucionesLiteForm f =  (GestSustitucionesLiteForm) form;
		GestSustitucionesLite sustitucionLite = GestSustitucionesLiteDAO.getSustitucionLiteByOid(getIdUsuario(), f);
		// INICIO Creación automática de la sustitución
		System.out.println(HelperSPD.dameFechaHora() + " editar --> ACTIONTODO " + f.getACTIONTODO());		
		System.out.println(HelperSPD.dameFechaHora() + " editar --> INICIO Creación automática de la sustitución ??" + sustitucionLite!=null?" SI":"NO");		
		if(sustitucionLite==null)
		{
			System.out.println(HelperSPD.dameFechaHora() + " editar --> INICIO Creación automática de la sustitución ");		
			System.out.println(HelperSPD.dameFechaHora() + " editar --> crearNuevaaPartirFichero " + request.getParameter("oidFicheroResiDetalle"));		

			sustitucionLite = crearNuevaaPartirFichero(request.getParameter("oidFicheroResiDetalle"));

		}
		String logAntes = sustitucionLite.getSpdCn() + " " + sustitucionLite.getResiMedicamento() + "  " + sustitucionLite.getSpdCn() + "  " + sustitucionLite.getSpdNombreBolsa() + "  " + sustitucionLite.getSpdFormaMedicacion()+ "  " + sustitucionLite.getSpdAccionBolsa() +" ";
		System.out.println(HelperSPD.dameFechaHora() + " editar --> logAntes " + logAntes);		

		//FIN Creación automática de la sustitución
		f.setListaTiposAccion(GestSustitucionesLiteDAO.getListaTiposAccion());
		f.setFiltroGtVm(f.getFiltroGtVm());
		
		List errors = new ArrayList();
		boolean result=false;
		if(f.getACTIONTODO()!=null && f.getACTIONTODO().equals("EDITA_OK"))
		{
			
			if(f.getSpdCn()!=null && !f.getSpdCn().equals("")) 
			{
				BdConsejo bdc = BdConsejoDAO.getBdConsejobyCN(f.getSpdCn());
				System.out.println(HelperSPD.dameFechaHora() + " editar --> f.getSpdCn(): " +f.getSpdCn() );		
				System.out.println(HelperSPD.dameFechaHora() + " editar --> BdConsejo.getBdConsejobyCN: " +bdc!=null?bdc.getCnConsejo():"_" );
			
				sustitucionLite.setSpdCn(f.getSpdCn());
				if(bdc!=null){
					sustitucionLite.setSpdFormaMedicacion(bdc.getNombreFormaFarmaceutica());
				}
			}
			if(f.getSpdNombreBolsa()!=null && !f.getSpdNombreBolsa().equals(""))
				sustitucionLite.setSpdNombreBolsa(f.getSpdNombreBolsa());
			sustitucionLite.setSpdAccionBolsa(f.getIdTipoAccion());
		    sustitucionLite.setExcepciones(f.getExcepciones());
			sustitucionLite.setAux1(f.getAux1());
			sustitucionLite.setAux2(f.getAux2());
			System.out.println(HelperSPD.dameFechaHora() + " editar --> sustitucionLite: "  + sustitucionLite.getSpdCn() + " - "+sustitucionLite.getSpdNombreBolsa() + " " );

			result=GestSustitucionesLiteDAO.editaSustLite(getIdUsuario(), sustitucionLite);
			
			if(result)
			{
				errors.add( "Registro editado correctamente ");
				f.setOidGestSustitucionesLite(0);
				f.setSustitucionLite(null);
				String logDespues = sustitucionLite.getSpdCn() + " " + sustitucionLite.getResiMedicamento() + "  " + sustitucionLite.getSpdCn() + "  " + sustitucionLite.getSpdNombreBolsa() + "  " + sustitucionLite.getSpdFormaMedicacion()+ "  " + sustitucionLite.getSpdAccionBolsa() +" ";
				
				//INICIO creación de log en BBDD
					try{
						SpdLogAPI.addLog(getIdUsuario(), "",  sustitucionLite.getIdDivisionResidencia(), null, SpdLogAPI.A_SUSTITUCION, SpdLogAPI.B_EDICION, SpdLogAPI.C_DATOSGENERALES, "SpdLog.sustitucion.edicion.datosGenerales", 
								new String[]{new String().valueOf(sustitucionLite.getOidGestSustitucionesLite()), logAntes,  logDespues	} );
					}catch(Exception e){}	//Se actualiza la previsión del proceso.
					//FIN creación de log en BBDD

			
				
				
				
			}
			else errors.add( "No se ha podido editar el registro");

			list( mapping,  form,  request,  response);
			f.setErrors(errors);
			return mapping.findForward("list");
			
		}
		f.setSustitucionLite(sustitucionLite);

		return mapping.findForward("editar");
	}

	private GestSustitucionesLite crearNuevaaPartirFichero(String oidFicheroResiDet) throws Exception {
		FicheroResiBean fbean = null;
		GestSustitucionesLite sustitucionLite = null;
		int oidFicheroResiDetalle;
		try{
			oidFicheroResiDetalle = new Integer(oidFicheroResiDet).intValue();
		}
		catch(Exception e){
			oidFicheroResiDetalle=-1;
		}
		//recuperamos el oidFicheroResiDetalle por si existe para replicarlo como sustitucionLite
		if(oidFicheroResiDetalle>-1)
			fbean = FicheroResiDetalleHelper.getFicheroResiDetalleByIdOid(getIdUsuario(), oidFicheroResiDetalle);

		if(fbean!=null)
		{
			sustitucionLite=new GestSustitucionesLite();
			sustitucionLite.setIdDivisionResidencia(fbean.getIdDivisionResidencia());
			sustitucionLite.setOidDivisionResidencia(fbean.getOidDivisionResidencia());
			sustitucionLite.setResiCn(fbean.getResiCn());
			sustitucionLite.setResiMedicamento(fbean.getResiMedicamento());
			sustitucionLite.setSpdCn(fbean.getSpdCnFinal());
			sustitucionLite.setSpdNombreMedicamento(fbean.getSpdNombreMedicamento());
			sustitucionLite.setSpdNombreBolsa(fbean.getSpdNombreBolsa());
			sustitucionLite.setSpdFormaMedicacion(fbean.getSpdFormaMedicacion());
			sustitucionLite.setSpdAccionBolsa(fbean.getSpdAccionBolsa());
			
			boolean sustCreada =GestSustitucionesLiteDAO.nuevoSustLite(sustitucionLite);
			
			if(sustCreada)
			{
				//INICIO creación de log en BBDD
				try{
					SpdLogAPI.addLog(getIdUsuario(), "",  sustitucionLite.getIdDivisionResidencia(), null, SpdLogAPI.A_SUSTITUCION, SpdLogAPI.B_CREACION, SpdLogAPI.C_DATOSGENERALES, "SpdLog.sustitucion.creacion.porFichero", 
							new String[]{new String().valueOf(sustitucionLite.getOidGestSustitucionesLite()), 
								" | "+sustitucionLite.getResiCn()+" "+ sustitucionLite.getResiMedicamento() + " | ", 
								" | "+ sustitucionLite.getSpdCn() +" "+ sustitucionLite.getSpdNombreBolsa() +" "+ sustitucionLite.getSpdNombreBolsa() +" "+ sustitucionLite.getSpdAccionBolsa() + " | "
								} );
				}catch(Exception e){}	//Se actualiza la previsión del proceso.
				//FIN creación de log en BBDD
				
			}
		

			
			
		}

		return sustitucionLite;
	}

	public ActionForward editarExpres(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		GestSustitucionesLiteForm f =  (GestSustitucionesLiteForm) form;
		GestSustitucionesLite sustitucionLite = null;
		
		//En el Express ha de ser un CN numérico para que no recupere un conjunto de CN por nombre y pueda dar error de cruzados como en Santa Creu
		if(f.getResiCn()!=null && !f.getResiCn().equals("") && DataUtil.isNumeroGreatherThanZero(f.getResiCn()))
			sustitucionLite = GestSustitucionesLiteDAO.getSustitucionLiteByCN(getIdUsuario(), f);
		System.out.println(HelperSPD.dameFechaHora() + " editarExpres --> ACTIONTODO: " +f.getACTIONTODO()!=null?f.getACTIONTODO():"_" );		

		if(sustitucionLite ==null) 
		{
			f.setACTIONTODO("NUEVO_EXPRESS");
			return nuevo(mapping, form, request, response);
		}
	
		f.setListaTiposAccion(GestSustitucionesLiteDAO.getListaTiposAccion());
		//f.setFiltroGtVm(f.getFiltroGtVm());
		
		List errors = new ArrayList();
		boolean result=false;
		if(f.getACTIONTODO()!=null && f.getACTIONTODO().equals("EDITA_OK"))
		{
			if(f.getSpdCn()!=null && !f.getSpdCn().equals("")) 
			{
				BdConsejo bdc = BdConsejoDAO.getBdConsejobyCN(f.getSpdCn());
				System.out.println(HelperSPD.dameFechaHora() + " editarExpres --> f.getSpdCn(): " +f.getSpdCn() );		
				System.out.println(HelperSPD.dameFechaHora() + " editarExpres --> BdConsejo.getBdConsejobyCN: " +(bdc!=null?bdc.getCnConsejo():"_") );

				sustitucionLite.setSpdCn(f.getSpdCn());
				if(bdc!=null){
					sustitucionLite.setSpdFormaMedicacion(bdc.getNombreFormaFarmaceutica());
				}
			}
			if(f.getSpdNombreBolsa()!=null && !f.getSpdNombreBolsa().equals(""))
				sustitucionLite.setSpdNombreBolsa(f.getSpdNombreBolsa());
			sustitucionLite.setSpdAccionBolsa(f.getIdTipoAccion());
		    sustitucionLite.setExcepciones(f.getExcepciones());
			sustitucionLite.setAux1(f.getAux1());
			sustitucionLite.setAux2(f.getAux2());
			System.out.println(HelperSPD.dameFechaHora() + " editarExpres --> sustitucionLite: "  + sustitucionLite.getSpdCn() + " - "+sustitucionLite.getSpdNombreBolsa() + " " );

			result=GestSustitucionesLiteDAO.editaSustLite(getIdUsuario(), sustitucionLite);
			
			if(result)
			{
				errors.add( "Registro editado correctamente ");
				f.setOidGestSustitucionesLite(0);
				f.setSustitucionLite(null);
				//INICIO creación de log en BBDD
				try{
					SpdLogAPI.addLog(getIdUsuario(), "",  sustitucionLite.getIdDivisionResidencia(), f.getIdProceso(), SpdLogAPI.A_SUSTITUCION, SpdLogAPI.B_EDICIONEXPRESS, SpdLogAPI.C_DATOSGENERALES, "SpdLog.sustitucion.creacion.express", 
							new String[]{new String().valueOf(sustitucionLite.getOidGestSustitucionesLite()), 
								" | "+sustitucionLite.getResiCn()+" "+ sustitucionLite.getResiMedicamento() + " | ", 
								" | "+ sustitucionLite.getSpdCn() +" "+ sustitucionLite.getSpdNombreBolsa() +" "+ sustitucionLite.getSpdNombreBolsa() +" "+ sustitucionLite.getSpdAccionBolsa() + " | "
								} );
				}catch(Exception e){}	//Se actualiza la previsión del proceso.
				//FIN creación de log en BBDD

			}
			else errors.add( "No se ha podido editar el registro");

			list( mapping,  form,  request,  response);
			f.setErrors(errors);
			return mapping.findForward("list");
			
		}
		f.setSustitucionLite(sustitucionLite);

		return mapping.findForward("editarExpres");
	}
	
	
	/*
	public ActionForward duplicar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			GestSustitucionesLiteForm f =  (GestSustitucionesLiteForm) form;
			GestSustitucionesLite sustitucionLite = GestSustitucionesLiteDAO.getSustitucionLiteByOid(f);

			DivisionResidencia dr = DivisionResidenciaDAO.getDivisionResidenciaByOid(f.getOidDivisionResidenciaFiltro());
			
			List errors = new ArrayList();
			boolean result=false;
			
			GestSustitucionesLite duplicada = new GestSustitucionesLite();
			duplicada.setIdDivisionResidencia(dr.getIdDivisionResidencia());
			duplicada.setSpdAccionBolsa(sustitucionLite.getSpdAccionBolsa());
			duplicada.setExcepciones(sustitucionLite.getExcepciones());
			duplicada.setAux1(sustitucionLite.getAux1());
			duplicada.setAux2(sustitucionLite.getAux2());
			result=GestSustitucionesLiteDAO.nuevoSustLite(duplicada);
			
			if(result)
			{
				errors.add( "Registro creado correctamente ");
				f.setOidGestSustitucionesLite(0);
				f.setSustitucionLite(null);
			}
			else errors.add( "No se ha podido crear el registro");

			list( mapping,  form,  request,  response);
			f.setErrors(errors);
			return mapping.findForward("list");
		}
	*/
	
	public ActionForward duplicar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		GestSustitucionesLiteForm f =  (GestSustitucionesLiteForm) form;
		DivisionResidencia dr = DivisionResidenciaDAO.getDivisionResidenciaByOid(getIdUsuario(), f.getOidDivisionResidenciaFiltro());
		f.setListaTiposAccion(GestSustitucionesLiteDAO.getListaTiposAccion());
		f.setListaDivisionResidencia(DivisionResidenciaDAO.getListaDivisionResidencias(getIdUsuario()));
		List errors = new ArrayList();
		boolean result=false;
		GestSustitucionesLite sustitucionLite = GestSustitucionesLiteDAO.getSustitucionLiteByOid(getIdUsuario(), f.getOidGestSustitucionesLite());
		
		System.out.println("formulari.getACTIONTODO() main "  +f.getACTIONTODO());
		
	
		
		GestSustitucionesLite duplicada = sustitucionLite.clone();
		
		//duplicada.setIdDivisionResidencia(dr!=null?dr.getIdDivisionResidencia():null);
			
		if(f.getACTIONTODO()!=null && f.getACTIONTODO().equals("DUPLICA_OK") && duplicada.getIdDivisionResidencia()!=null)
		{
			duplicada.setIdDivisionResidencia(dr.getIdDivisionResidencia());
			duplicada.setResiCn(f.getResiCn());
			duplicada.setResiMedicamento(f.getResiMedicamento());
			duplicada.setSpdAccionBolsa(f.getIdTipoAccion());
			duplicada.setAux1(f.getAux1()!=null?f.getAux1():"");
			duplicada.setAux2(f.getAux2()!=null?f.getAux2():"");
			
			
			boolean sustCreada=GestSustitucionesLiteDAO.nuevoSustLite(duplicada);
			if(sustCreada)
			{
				errors.add( "Registro creado correctamente ");
				//INICIO creación de log en BBDD
				try{
					SpdLogAPI.addLog(getIdUsuario(), "",  sustitucionLite.getIdDivisionResidencia(), f.getIdProceso(), SpdLogAPI.A_SUSTITUCION, SpdLogAPI.B_CREACION, SpdLogAPI.C_DATOSGENERALES, "SpdLog.sustitucion.creacion.duplicar", 
							new String[]{new String().valueOf(sustitucionLite.getOidGestSustitucionesLite()), 
								" | "+f.getResiCn()+" "+ f.getResiMedicamento() + " | ", 
								" | "+ sustitucionLite.getSpdCn() +" "+ sustitucionLite.getSpdNombreBolsa() +" "+ sustitucionLite.getSpdNombreBolsa() +" "+ sustitucionLite.getSpdAccionBolsa() + " | "
								} );
				}catch(Exception e){}	//Se actualiza la previsión del proceso.
				//FIN creación de log en BBDD

				
				
			}
			else errors.add( "No se ha podido crear el registro");

			list( mapping,  form,  request,  response);
			f.setErrors(errors);
			return mapping.findForward("list");
			
		}
			f.setSustitucionLite(sustitucionLite);

		return mapping.findForward("duplicar");
	}

	
	public ActionForward borrar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		List errors = new ArrayList();

		GestSustitucionesLiteForm formulari =  (GestSustitucionesLiteForm) form;
		GestSustitucionesLite sustitucionLite = GestSustitucionesLiteDAO.getSustitucionLiteByOid(getIdUsuario(), formulari.getOidGestSustitucionesLite());
		formulari.setSustitucionLite(sustitucionLite);

		
		boolean result=false;
		if(formulari.getACTIONTODO()!=null && formulari.getACTIONTODO().equals("CONFIRMADO_OK"))
		{
			result=GestSustitucionesLiteDAO.borraSustLiteByOid(getIdUsuario(), sustitucionLite.getOidGestSustitucionesLite());
			if(result)
			{
			//	errors.add(SPDConstants.MSG_LEVEL_INFO, new ActionMessage("Registro borrado correctamente Info"));
				errors.add( "Registro borrado correctamente ");
				//INICIO creación de log en BBDD
				try{
					SpdLogAPI.addLog(getIdUsuario(), "",  sustitucionLite.getIdDivisionResidencia(), formulari.getIdProceso(), SpdLogAPI.A_SUSTITUCION, SpdLogAPI.B_BORRADO, SpdLogAPI.C_DATOSGENERALES, "SpdLog.sustitucion.borrado.datos", 
							new String[]{new String().valueOf(sustitucionLite.getOidGestSustitucionesLite()), 
								" | "+sustitucionLite.getResiCn()+" "+ sustitucionLite.getResiMedicamento() + " | ", 
								" | "+ sustitucionLite.getSpdCn() +" "+ sustitucionLite.getSpdNombreBolsa() +" "+ sustitucionLite.getSpdNombreBolsa() +" "+ sustitucionLite.getSpdAccionBolsa() + " | "
								} );
				}catch(Exception e){}	//Se actualiza la previsión del proceso.
				//FIN creación de log en BBDD

				
				formulari.setOidGestSustitucionesLite(0);
				formulari.setSustitucionLite(null);
			}
			else errors.add( "Error en el borrado del registro");
			list( mapping,  form,  request,  response);
			formulari.setErrors(errors);
			return mapping.findForward("list");
		}
			return mapping.findForward("borrar");
	}






}
	