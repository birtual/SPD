package lopicost.spd.struts.action;


import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import lopicost.config.logger.Logger;
import lopicost.spd.helper.FicheroResiDetalleHelper;
import lopicost.spd.persistence.DivisionResidenciaDAO;
import lopicost.spd.persistence.PacienteDAO;
import lopicost.spd.struts.bean.CamposPantallaBean;
import lopicost.spd.struts.bean.FicheroResiBean;
import lopicost.spd.struts.bean.PacienteBean;
import lopicost.spd.struts.form.PacientesForm;
import lopicost.spd.struts.helper.PacientesHelper;
import lopicost.spd.utils.HelperSPD;
import lopicost.spd.utils.SPDConstants;

public class PacientesAction extends GenericAction  {

	private final String cLOGGERHEADER = "PacientesAction: ";
	private final String cLOGGERHEADER_ERROR = cLOGGERHEADER + "ERROR: ";

	
	PacienteDAO dao= new  PacienteDAO();
	//private static final Logger logger = Logger.getLogger(PacientesAction.class);
	   
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	
		log(cLOGGERHEADER + ": inicio listado",Logger.DEBUG);
		PacientesForm formulari =  (PacientesForm) form;
		formulari.setOidPaciente("");
		//paginación
		int currpage = actualizaCurrentPage(formulari, dao.getCountBdPacientes(getIdUsuario(), formulari));
		formulari.setListaDivisionResidencias(DivisionResidenciaDAO.getListaDivisionResidencias(getIdUsuario()));
//		formulari.setListaEstatusResidente(PacienteDAO.getEstatusResidente(getIdUsuario()));
		formulari.setListaEstatusResidente(PacientesHelper.getEstatusResidente());
		formulari.setListaEstadosResidente(PacienteDAO.getEstadosResidente(getIdUsuario()));
		formulari.setListaPacientesBean(dao.getPacientes(getIdUsuario(), formulari, currpage*SPDConstants.PAGE_ROWS,(currpage+1)*SPDConstants.PAGE_ROWS, null));
	
		return mapping.findForward("list");
	}
	
	public ActionForward nuevo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		PacientesForm f =  (PacientesForm) form;
		List errors = new ArrayList();
		f.setErrors(errors);
		
		if(f.getACTIONTODO()!=null && f.getACTIONTODO().equals("NUEVO_CIP"))
		{
			return mapping.findForward("nuevoCIP");
		}
		else if(f.getACTIONTODO()!=null && f.getACTIONTODO().equals("NUEVO"))
		{
			f.setListaDivisionResidencias(DivisionResidenciaDAO.getListaDivisionResidencias(getIdUsuario()));
			//f.setListaEstatusResidente(PacienteDAO.getEstatusResidente(getIdUsuario()));
			f.setListaEstatusResidente(PacientesHelper.getEstatusResidente());
			f.setListaEstadosResidente(PacienteDAO.getEstadosResidente(getIdUsuario()));

			String nuevoCIP = f.getCIP();
			if(PacientesHelper.existeCIP(nuevoCIP))
			{
				errors.add( "El CIP " + nuevoCIP + " ya existe");
				f.setACTIONTODO("NUEVO_CIP");
				return mapping.findForward("nuevoCIP");
			}
				return mapping.findForward("nuevo");
		}
		else if(f.getACTIONTODO()!=null && f.getACTIONTODO().equals("NUEVO_OK"))
		{
			boolean result=PacientesHelper.nuevo(getIdUsuario(), f);
			if(result)
			{
				errors.add( "Alta creada correctamente ");
				PacienteBean p  = PacientesHelper.getPacientePorCIP(f.getCIP());
				PacientesHelper.aplicaControles(getIdUsuario(), p);
			}
			else errors.add( "No se ha podido crear el registro");

			list( mapping,  form,  request,  response);
			f.setErrors(errors);
			return mapping.findForward("list");
		}
				
		return mapping.findForward("nuevo");
	}
	
	public ActionForward cambioCIP(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		PacientesForm f =  (PacientesForm) form;
		List errors = new ArrayList();
		PacienteBean pac = null;
		pac=PacienteDAO.getPacientePorOID(getIdUsuario(), f.getOidPaciente());
		f.setPacienteBean(pac);

		f.setErrors(errors);
		
		//if(f.getACTIONTODO()!=null && f.getACTIONTODO().equals("CAMBIOCIP"))
		if ("CAMBIOCIP".equals(f.getACTIONTODO()))	
		{
			return mapping.findForward("cambioCIP");

		}
		//else if(f.getACTIONTODO()!=null && f.getACTIONTODO().equals("CAMBIOCIP_OK"))
		else if ("CAMBIOCIP_OK".equals(f.getACTIONTODO()))	
		{
			String anteriorCIP = pac.getCIP();
			String nuevoCIP = f.getCIP();

			//si son iguales lanzamos mensaje y volvemos
			if (nuevoCIP!=null && nuevoCIP.equals(anteriorCIP))
			{
				errors.add( "No se realiza cambio, el CIP es el mismo");
				f.setACTIONTODO("CAMBIOCIP");
				return mapping.findForward("cambioCIP");
			}
			//miramos si ya existe el CIP nuevo
			PacienteBean pc = PacientesHelper.getPacientePorCIP(nuevoCIP);
			//si existe lanzamos mensaje y volvemos
			if(pc!=null)
			{
				errors.add( "El CIP " + nuevoCIP + " ya existe");
				f.setACTIONTODO("CAMBIOCIP");
				return mapping.findForward("cambioCIP");
			}
			boolean result=PacientesHelper.cambioCIP(getIdUsuario(), pac, f);
			if(result)
			{
				errors.add( "CIP cambiado correctamente ");
			}
			else errors.add( "No se ha podido cambiar el CIP");

			list( mapping,  form,  request,  response);
			f.setErrors(errors);
			return mapping.findForward("list");
		}
				
		return mapping.findForward("cambioCIP");
	}
	
	
	public ActionForward editar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		PacientesForm f =  (PacientesForm) form;
		PacienteBean pac = null;
		pac=PacienteDAO.getPacientePorOID(getIdUsuario(), f.getOidPaciente());
		f.setPacienteBean(pac);

		List errors = new ArrayList();
		//f.setListaEstatusResidente(PacienteDAO.getEstatusResidente(getIdUsuario()));
		f.setListaEstatusResidente(PacientesHelper.getEstatusResidente());
		f.setListaDivisionResidencias(DivisionResidenciaDAO.getListaDivisionResidencias(getIdUsuario()));
		if(f.getACTIONTODO()!=null && f.getACTIONTODO().equals("EDITAR"))
		{
			f.setIdDivisionResidencia(pac.getIdDivisionResidencia()); //para que muestre la resi del paciente en el formulario
		}
		if(f.getACTIONTODO()!=null && f.getACTIONTODO().equals("EDITAR_OK"))
		{
			
			boolean result=PacientesHelper.actualizaDatos(getIdUsuario(), pac, f);
			if(result)
			{
				errors.add( "Editado correctamente ");
				PacienteBean p  = PacientesHelper.getPacientePorCIP(pac.getCIP()); //actualizamos el objeto para tratarlo
				PacientesHelper.aplicaControles(getIdUsuario(), p);
			}
			else errors.add( "No se ha podido editar el registro");

			list( mapping,  form,  request,  response);
			f.setErrors(errors);
			return mapping.findForward("list");
		}
		 
		 
		return mapping.findForward("editar");
	}
	
	
	public ActionForward detalle(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		PacientesForm formulari =  (PacientesForm) form;
		formulari.setPacienteBean(PacienteDAO.getPacientePorOID(getIdUsuario(), formulari.getOidPaciente()));
		
		return mapping.findForward("detalle");
	}
	
	public ActionForward detallBolquers(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		PacientesForm formulari =  (PacientesForm) form;
		PacienteBean pac = PacientesHelper.getPacientePorOID(getIdUsuario(),  formulari.getOidPaciente());
		formulari.setPacienteBean(pac);
		formulari.setListaBeans(PacientesHelper.getBolquersPaciente(getIdUsuario(), formulari.getOidPaciente()));

 		return mapping.findForward("detallBolquers");
	}
	
	public ActionForward detalleDiscrepancias(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		PacientesForm formulari =  (PacientesForm) form;
		PacienteBean pac = PacientesHelper.getPacientePorOID(getIdUsuario(),  formulari.getOidPaciente());
		formulari.setPacienteBean(pac);
		formulari.setListaBeans(PacientesHelper.getDiscrepanciasPorCIP(getIdUsuario(), pac, formulari.getDiasCalculo()));
		try
		{
			formulari.setOidPaciente(String.valueOf(pac.getOidPaciente()));
		}catch(Exception e){
			
		}
 		return mapping.findForward("detalleDiscrepancias");
	}
	
	public ActionForward detalleTratamientoRct(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		PacientesForm formulari =  (PacientesForm) form;
		PacienteBean pac = PacientesHelper.getPacientePorOID(getIdUsuario(),  formulari.getOidPaciente());
		formulari.setPacienteBean(pac);
		//formulari.setNombreApellidos(pac.getNombreApellidos()!=null?pac.getNombreApellidos():null);
		//formulari.setCIP(pac.getCIP()!=null?pac.getCIP():null);
		formulari.setListaBeans(PacientesHelper.getTratamientoRctPorCIP(getIdUsuario(), pac));
		try
		{
			formulari.setOidPaciente(String.valueOf(pac.getOidPaciente()));
		}catch(Exception e){
			
		}
 		return mapping.findForward("detalleTratamientoRct");
	}
	
	public ActionForward detalleTratamientoSPD(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		PacientesForm formulari =  (PacientesForm) form;
		PacienteBean pac = PacientesHelper.getPacientePorOID(getIdUsuario(),  formulari.getOidPaciente());
		formulari.setPacienteBean(pac);
		formulari.setListaProcesosCargados(PacientesHelper.getListTratamientosSPDPorCIP(getIdUsuario(), pac, false));
		try
		{
			formulari.setOidPaciente(String.valueOf(pac.getOidPaciente()));
		}catch(Exception e){
			
		}
		String idProceso=formulari.getIdProceso();
		if(idProceso==null || idProceso.contentEquals(""))
		{
			if(formulari.getListaProcesosCargados()!=null && formulari.getListaProcesosCargados().size()>0)
			{
				//por defecto cargamos la última
				FicheroResiBean fic = formulari.getListaProcesosCargados().get(0);
				idProceso=fic.getIdProceso();

				Iterator<FicheroResiBean> _it = formulari.getListaProcesosCargados().iterator();
				boolean trobat=false;
				while(_it.hasNext() && !trobat)
				{
					fic = (FicheroResiBean) _it.next();
					if(fic!=null && fic.getOidFicheroResiCabecera()==formulari.getOidFicheroResiCabecera())
					{
						trobat=true;
						idProceso=fic.getIdProceso();
					}
				}

			}
		}
		formulari.setIdProceso(idProceso);
		formulari.setListaBeans(PacientesHelper.getDetalleTratamientosSPDPorCIP(getIdUsuario(), pac, formulari.getIdProceso(), false));

		
		FicheroResiBean cab =  FicheroResiDetalleHelper.getCabeceraFicheroResi(getIdUsuario(), pac.getIdDivisionResidencia(), formulari.getIdProceso(), false);
		CamposPantallaBean camposPantallaBean = new CamposPantallaBean();
		HelperSPD.gestionVisibilidadCampos(camposPantallaBean, cab);
		formulari.setCamposPantallaBean(camposPantallaBean);
		formulari.setListaTomasCabecera(FicheroResiDetalleHelper.getTomasCabeceraYHora(cab));	
		
 		return mapping.findForward("detalleTratamientosSPD");
	}
	
	/*
	public ActionForward listadoProceso(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
	
		PacientesForm formulari =  (PacientesForm) form;
		//paginación

		int currpage = actualizaCurrentPage(formulari, dao.getCountBdPacientesProceso(getIdUsuario(), formulari));
		formulari.setListaProcesosCargados(FicheroResiCabeceraDAO.getProcesosCargados(getIdUsuario(), formulari.getOidDivisionResidencia()));
		formulari.setListaDivisionResidenciasCargadas(FicheroResiCabeceraDAO.getListaDivisionResidenciasCargadas(getIdUsuario()));
		formulari.setListaEstadosCabecera(FicheroResiCabeceraDAO.getEstados(getIdUsuario(), formulari.getOidDivisionResidencia(), formulari.getFiltroProceso()));
		formulari.setListaPacientesBean(dao.getPacientesProceso(getIdUsuario(), formulari, currpage*SPDConstants.PAGE_ROWS,(currpage+1)*SPDConstants.PAGE_ROWS, null));
		
		return mapping.findForward("listadoProceso");
	}
	*/
	public ActionForward producirSPDResidente(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		PacientesForm formulari =  (PacientesForm) form;

        String path = mapping.findForward("producirSPDResidente").getPath();
        path += "?oid=" + formulari.getOidFicheroResiCabecera();
        
        return new ActionForward(path, true);
	}
	

	/**
	 * método de ayuda a la paginación
	 * @param aForm
	 * @param numberObjects
	 * @return
	 */
	private int actualizaCurrentPage(PacientesForm aForm, int numberObjects) {
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


	public void log (String message, int level)
	{
		Logger.log("SPDLogger",message,level);	
	}
	
	
	
	
}
	