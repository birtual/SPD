package lopicost.spd.struts.action;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

import lopicost.spd.struts.action.GenericAction;
import lopicost.spd.model.DivisionResidencia;
import lopicost.spd.model.GestSustituciones;
import lopicost.spd.persistence.DivisionResidenciaDAO;
import lopicost.spd.persistence.SustXComposicionDAO;
import lopicost.spd.persistence.GestSustitucionesDAO;
import lopicost.spd.struts.form.GestSustitucionesForm;
import lopicost.spd.utils.SPDConstants;
public class GestSustitucionesAction extends GenericAction  {

	GestSustitucionesDAO dao= new  GestSustitucionesDAO();
	
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	
		
		List<GestSustituciones> resultList= new ArrayList<GestSustituciones>();
		GestSustitucionesForm gestSustitucionesForm =  (GestSustitucionesForm) form;
		//inicializamos para que no haya datos de otros módulos
		gestSustitucionesForm.setOidGestSustituciones(0);
		gestSustitucionesForm.setOidGestSustitucionesXResi(0);
		
		int getCountSustituciones = dao.getCountSustituciones(getIdUsuario(), gestSustitucionesForm);
		int currpage = actualizaCurrentPage(gestSustitucionesForm, getCountSustituciones);
		gestSustitucionesForm.setListaSustituciones(dao.getSustitucionesListado(getIdUsuario(), gestSustitucionesForm, currpage*SPDConstants.PAGE_ROWS,(currpage+1)*SPDConstants.PAGE_ROWS, true));
	//	Lista a = GestSustXConjHomogDAO.get
	//	gestSustitucionesForm.setSustXConjHomog();
		
		
	//	gestSustitucionesForm.setOidDivisionResidenciaFiltro(gestSustitucionesForm.getOidDivisionResidenciaFiltro());
	//	gestSustitucionesForm.setNombreCortoFiltro(gestSustitucionesForm.getNombreCortoFiltro());
		
		gestSustitucionesForm.setListaDivisionResidencia(DivisionResidenciaDAO.getListaDivisionResidencias(getIdUsuario()));
		gestSustitucionesForm.setListaMedicamentoResi(dao.getMedicamentoResi());
		gestSustitucionesForm.setListaNombreCortoOK(dao.getNombresCortosOK());
		gestSustitucionesForm.setListaGtVm(dao.getGtVm());
		gestSustitucionesForm.setListaPresentacion(dao.getPresentacion());
		gestSustitucionesForm.setListaTiposAccion(dao.getListaTiposAccion());
		gestSustitucionesForm.setListaFormasFarmaceuticas(dao.getFormasFarmaceuticas());
		
			
		gestSustitucionesForm.setFiltroExisteBdConsejo(gestSustitucionesForm.getFiltroExisteBdConsejo());
		
	//	gestSustitucionesForm.setACTIONTODO(gestSustitucionesForm.getACTIONTODO());
	//	request.setAttribute("resultList", dao.getTodas());
	//	request.setAttribute("gestSustitucionesForm", gestSustitucionesForm);
		
		List errors = new ArrayList();
		gestSustitucionesForm.setErrors(errors);

			return mapping.findForward("list");
	}
	
	
	protected int actualizaCurrentPage(GestSustitucionesForm aForm, int numberObjects) {
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


	public ActionForward editar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		GestSustitucionesForm gestSustitucionesForm =  (GestSustitucionesForm) form;
		gestSustitucionesForm.setSustitucion(GestSustitucionesDAO.getSustitucionByOid(getIdUsuario(), gestSustitucionesForm.getOidGestSustituciones()));
		
		//gestSustitucionesForm.setListaDivisionResidencia(DivisionResidenciaDAO.getListaDivisionResidencias());
		gestSustitucionesForm.setListaDivisionResidencia(DivisionResidenciaDAO.getListaDivisionResidenciasSinSustXResi(getIdUsuario(), gestSustitucionesForm.getOidGestSustituciones()));
		
		gestSustitucionesForm.setListaTiposAccion(GestSustitucionesDAO.getListaTiposAccion());
		//gestSustitucionesForm.setListaFormasFarmaceuticas(GestSustitucionesDAO.getFormasFarmaceuticas());
		
		
		List errors = new ArrayList();
		boolean result=false;
		if(gestSustitucionesForm.getACTIONTODO()!=null && gestSustitucionesForm.getACTIONTODO().equals("EDITA_OK"))
		{
			result=GestSustitucionesDAO.edita(getIdUsuario(), gestSustitucionesForm);
			if(result)
			{
				errors.add( "Registro editado correctamente ");
			}
			else errors.add( "No se ha podido editar el registro");

			list( mapping,  form,  request,  response);
			gestSustitucionesForm.setErrors(errors);
			return mapping.findForward("list");
			
		}

		return mapping.findForward("editar");
	}

	
	public ActionForward editarSustXResi(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		GestSustitucionesForm gestSustitucionesForm =  (GestSustitucionesForm) form;
		GestSustituciones gestSust = GestSustitucionesDAO.getSustitucionXResiByOid(getIdUsuario(), gestSustitucionesForm.getOidGestSustitucionesXResi());
		gestSustitucionesForm.setSustitucion(gestSust);
		gestSustitucionesForm.setSustitucionXResi(gestSust!=null && gestSust.getListaSustitucionesXResi()!=null && gestSust.getListaSustitucionesXResi().size()>0?gestSust.getListaSustitucionesXResi().get(0):null);
		

		gestSustitucionesForm.setIdDivisionResidencia(gestSust.getIdDivisionResidencia());
		gestSustitucionesForm.setListaTiposAccion(GestSustitucionesDAO.getListaTiposAccion());
//		gestSustitucionesForm.setListaFormasFarmaceuticas(GestSustitucionesDAO.getFormasFarmaceuticas());
		
		
		List errors = new ArrayList();
		boolean result=false;
		if(gestSustitucionesForm.getACTIONTODO()!=null && gestSustitucionesForm.getACTIONTODO().equals("EDITA_OK"))
		{
			result=GestSustitucionesDAO.editaSustXResi(getIdUsuario(), gestSustitucionesForm);
			if(result)
			{
				errors.add( "Registro editado correctamente ");
			}
			else errors.add( "No se ha podido editar el registro");

			list( mapping,  form,  request,  response);
			gestSustitucionesForm.setErrors(errors);
			return mapping.findForward("list");
			
		}

			
		return mapping.findForward("editarSustXResi");
	}

	
	public ActionForward borrarSustXResi(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		List errors = new ArrayList();

		GestSustitucionesForm gestSustitucionesForm =  (GestSustitucionesForm) form;
		GestSustituciones gestSust = GestSustitucionesDAO.getSustitucionXResiByOid(getIdUsuario(), gestSustitucionesForm.getOidGestSustitucionesXResi());
		gestSustitucionesForm.setSustitucion(gestSust);
		gestSustitucionesForm.setSustitucionXResi(gestSust!=null && gestSust.getListaSustitucionesXResi()!=null && gestSust.getListaSustitucionesXResi().size()>0?gestSust.getListaSustitucionesXResi().get(0):null);
		
		
		boolean result=false;
		if(gestSustitucionesForm.getACTIONTODO()!=null && gestSustitucionesForm.getACTIONTODO().equals("CONFIRMADO_OK"))
		{
			result=GestSustitucionesDAO.borraSustXResi(getIdUsuario(), gestSustitucionesForm);
			if(result)
			{
			//	errors.add(SPDConstants.MSG_LEVEL_INFO, new ActionMessage("Registro borrado correctamente Info"));
				errors.add( "Registro borrado correctamente ");
			}
			else errors.add( "Error en el borrado del registro");
			list( mapping,  form,  request,  response);
			gestSustitucionesForm.setErrors(errors);
			return mapping.findForward("list");
		}
			return mapping.findForward("borrarSustXResi");
	}


	
	public ActionForward borrar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		List errors = new ArrayList();

		GestSustitucionesForm gestSustitucionesForm =  (GestSustitucionesForm) form;
		gestSustitucionesForm.setSustitucion(GestSustitucionesDAO.getSustitucionByOid(getIdUsuario(), gestSustitucionesForm.getOidGestSustituciones()));
		boolean result=false;
		if(gestSustitucionesForm.getACTIONTODO()!=null && gestSustitucionesForm.getACTIONTODO().equals("CONFIRMADO_OK"))
		{
			result=GestSustitucionesDAO.borra(gestSustitucionesForm);
			if(result)
			{
			//	errors.add(SPDConstants.MSG_LEVEL_INFO, new ActionMessage("Registro borrado correctamente Info"));
				errors.add( "Registro borrado correctamente ");
			}
			else errors.add( "Error en el borrado del registro");
			list( mapping,  form,  request,  response);
			gestSustitucionesForm.setErrors(errors);
			return mapping.findForward("list");
		}
			return mapping.findForward("borrar");
	}

	
	public ActionForward nuevo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//inicializamos para que no haya datos de otros módulos
			GestSustitucionesForm f =  (GestSustitucionesForm) form;
			f.setListaTiposAccion(GestSustitucionesDAO.getListaTiposAccion());
			//gestSustitucionesForm.setListaFormasFarmaceuticas(GestSustitucionesDAO.getFormasFarmaceuticas());
			
			List<DivisionResidencia> listaDivisionResidencias= new ArrayList<DivisionResidencia>(); 
			
			//si viene marcada la resi, creamos directamente una sustitución para esa resi
			if(f.getOidDivisionResidenciaFiltro()>0 )
			{
				
				listaDivisionResidencias.add(DivisionResidenciaDAO.getDivisionResidenciaByOid(getIdUsuario(), f.getOidDivisionResidenciaFiltro()));
			}
			else 
				listaDivisionResidencias.addAll(DivisionResidenciaDAO.getListaDivisionResidencias(getIdUsuario()));
			
			f.setListaDivisionResidencia(listaDivisionResidencias);
			f.setListaNombreCortoOK(dao.getNombresCortosOK());

			
			List errors = new ArrayList();
			boolean result=false;
			System.out.println("gestSustitucionesForm.getACTIONTODO() main "  +f.getACTIONTODO());
			 if(f.getACTIONTODO()!=null && f.getACTIONTODO().equals("NUEVO"))
			{
				f.setCnOk("");
				f.setCnResi(f.getFiltroNombreCorto()!=null&&!f.getFiltroNombreCorto().equals("")?f.getFiltroNombreCorto():"");
				f.setIdDivisionResidencia("");
				f.setOidGestSustituciones(0);
				f.setNombreCorto("");
				f.setMedicamentoResi(f.getFiltroMedicamentoResi()!=null&&!f.getFiltroMedicamentoResi().equals("")?f.getFiltroMedicamentoResi():"");
				f.setNombreConsejo("");
				f.setComentario("");
			}
			else if(f.getACTIONTODO()!=null && f.getACTIONTODO().equals("NUEVO_OK"))
			{
				boolean sustCreada =GestSustitucionesDAO.nuevo(f);
				if(sustCreada)
				{
					errors.add( "Registro creado correctamente ");
				}
				else errors.add( "No se ha podido crear el registro");

				list( mapping,  form,  request,  response);
				f.setErrors(errors);
				return mapping.findForward("list");
				
			}
			return mapping.findForward("nuevo");
			
	}

	public ActionForward nuevoSustXResi(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
			//inicializamos para que no haya datos de otros módulos
			GestSustitucionesForm gestSustitucionesForm =  (GestSustitucionesForm) form;
			
			gestSustitucionesForm.setListaTiposAccion(GestSustitucionesDAO.getListaTiposAccion());
			gestSustitucionesForm.setListaFormasFarmaceuticas(GestSustitucionesDAO.getFormasFarmaceuticas());
			gestSustitucionesForm.setListaDivisionResidencia(DivisionResidenciaDAO.getListaDivisionResidencias(getIdUsuario()));
			gestSustitucionesForm.setListaNombreCortoOK(dao.getNombresCortosOK());

			
			List errors = new ArrayList();
			boolean result=false;
			System.out.println("gestSustitucionesForm.getACTIONTODO() main "  +gestSustitucionesForm.getACTIONTODO());
			 if(gestSustitucionesForm.getACTIONTODO()!=null && gestSustitucionesForm.getACTIONTODO().equals("NUEVO"))
			{
				gestSustitucionesForm.setCnOkSustXResi("");
				gestSustitucionesForm.setCnResi("");
				gestSustitucionesForm.setMedicamentoResi("");
				gestSustitucionesForm.setIdDivisionResidencia("");
				gestSustitucionesForm.setOidGestSustituciones(0);
				gestSustitucionesForm.setNombreCortoSustXResi("");
				gestSustitucionesForm.setNombreConsejo("");
				gestSustitucionesForm.setComentarioSustXResi("");
			}
			else if(gestSustitucionesForm.getACTIONTODO()!=null && gestSustitucionesForm.getACTIONTODO().equals("NUEVO_OK"))
			{

				boolean ok =false;
				GestSustituciones sust = GestSustitucionesDAO.getSustitucionByOid(getIdUsuario(), gestSustitucionesForm.getOidGestSustituciones());
				if(sust==null || gestSustitucionesForm.getOidGestSustituciones()<1) 
				{
					//miramos si la encontramos por codigo de resi
					List<GestSustituciones> lSust = GestSustitucionesDAO.getSustitucionesListado(getIdUsuario(), gestSustitucionesForm, 0, 1000, true);
					if(lSust!=null && lSust.size()>0)
						sust =  (GestSustituciones) lSust.get(0);				
					//	sust=GestSustitucionesDAO.nuevo(gestSustitucionesForm);
					
					ok=GestSustitucionesDAO.nuevo(gestSustitucionesForm);
					
				}
					
				
				//sust = GestSustitucionesDAO.getSustitucionByOid( GestSustitucionesDAO.getUltimoOid(gestSustitucionesForm));

				
				gestSustitucionesForm.setSustitucion(sust);
				
				result=GestSustitucionesDAO.nuevoSustXResi(gestSustitucionesForm);
				if(result)
				{
					errors.add( "Registro creado correctamente ");
				}
				else errors.add( "No se ha podido crear el registro");

				list( mapping,  form,  request,  response);
				gestSustitucionesForm.setErrors(errors);
				return mapping.findForward("list");
				
			}
			return mapping.findForward("list");
			
	}



}
	