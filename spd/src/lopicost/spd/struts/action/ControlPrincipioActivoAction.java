package lopicost.spd.struts.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import lopicost.spd.struts.action.GenericAction;
import lopicost.spd.struts.bean.FicheroResiBean;
import lopicost.spd.persistence.DivisionResidenciaDAO;
import lopicost.spd.persistence.FarmaciaDAO;
import lopicost.spd.controller.SpdLogAPI;
import lopicost.spd.model.BdConsejo;
import lopicost.spd.model.ControlPrincipioActivo;
import lopicost.spd.model.DivisionResidencia;
import lopicost.spd.model.Farmacia;
import lopicost.spd.persistence.BdConsejoDAO;
import lopicost.spd.persistence.ControlPrincipioActivoDAO;
import lopicost.spd.struts.form.ControlPrincipioActivoForm;

import lopicost.spd.utils.SPDConstants;
public class ControlPrincipioActivoAction extends GenericAction  {

	static String className="ControlPrincipioActivoAction";


	
	ControlPrincipioActivoDAO dao= new  ControlPrincipioActivoDAO();
	
	
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	
		
		List<ControlPrincipioActivo> resultList= new ArrayList<ControlPrincipioActivo>();
		ControlPrincipioActivoForm formulario =  (ControlPrincipioActivoForm) form;
		
		/*int getCount = dao.getCount(getIdUsuario(), formulario);
		int currpage = actualizaCurrentPage(formulario, getCount);*/
		formulario.setListaFarmacias(FarmaciaDAO.getFarmaciasPorUser(getIdUsuario()));
		formulario.setListaControlPrincipioActivo(dao.getListado(getIdUsuario(), formulario.getIdFarmacia(), null, formulario.getOidDivisionResidenciaFiltro()));
		formulario.setListaDivisionResidencia(DivisionResidenciaDAO.getListaDivisionResidencias(getIdUsuario()));
	
		List errors = new ArrayList();
		formulario.setErrors(errors);

		return mapping.findForward("list");
	}
	
	public ActionForward nuevo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
			//inicializamos para que no haya datos de otros módulos
			ControlPrincipioActivoForm formulario =  (ControlPrincipioActivoForm) form;
			formulario.setListaDivisionResidencia(DivisionResidenciaDAO.getListaDivisionResidencias(getIdUsuario()));

			List errors = new ArrayList();
			boolean result=false;
			System.out.println(className + " nuevo ");
			ControlPrincipioActivo controlPActivo = new ControlPrincipioActivo();

			if(formulario.getACTIONTODO()!=null && formulario.getACTIONTODO().equals("NUEVO"))
			{
				formulario.setListaFarmacias(FarmaciaDAO.getFarmaciasPorUser(getIdUsuario()));
				formulario.setListaGtVm(BdConsejoDAO.getListaGtVmDeConsejo());
				formulario.setListaDivisionResidencia(DivisionResidenciaDAO.getListaDivisionResidencias(getIdUsuario()));
			}
			else if(formulario.getACTIONTODO()!=null && formulario.getACTIONTODO().equals("NUEVO_OK"))
			{
				String idFarmacia =formulario.getIdFarmacia();
				DivisionResidencia div = DivisionResidenciaDAO.getDivisionResidenciaByOid(getIdUsuario(), formulario.getOidDivisionResidencia());
				List listaBdC = BdConsejoDAO.getBdConsejoByFilters(null, null, null, 0, 1, formulario.getCodGtVm(), null, null, null, null, null);
				BdConsejo bdC = null;
				if(listaBdC!=null && listaBdC.size()>0) 
					bdC = (BdConsejo) listaBdC.get(0);
				//BdConsejoDAO.getListaGtVmDeConsejo()
				
				boolean existe = (dao.contar(getIdUsuario(),  idFarmacia, bdC.getNomGtVm(), (div.getIdDivisionResidencia()!=null?div.getIdDivisionResidencia():"")))>0;
				if(!existe &&  bdC!=null && idFarmacia!=null && !idFarmacia.equals(""))
				{
					result =dao.nuevo(getIdUsuario(), idFarmacia, div,  bdC);
				}
				if(result)
				{
					errors.add( "Registro creado correctamente ");
					formulario.setCodGtVm("");
					//dejamos log
					try{
						SpdLogAPI.addLog(getIdUsuario(), idFarmacia,  div.getIdDivisionResidencia(), "", SpdLogAPI.A_GESTION_FARMACIA, SpdLogAPI.B_CREACION, SpdLogAPI.C_CONTROL_PRINCIPIO_ACTIVO, "SpdLog.gestionFarmacia.creacion_controlPActivo"
								,  bdC.getNomGtVm() );
					}catch(Exception e){}	//Se añade un control extra para el principio activo @@.  
					

				}
				else errors.add( "No se ha podido crear el registro");

				list( mapping,  form,  request,  response);
				formulario.setErrors(errors);
				return mapping.findForward("list");
			}
			return mapping.findForward("nuevo");
	}
	
	
	public ActionForward borrar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
			List errors = new ArrayList();
	
			ControlPrincipioActivoForm formulario =  (ControlPrincipioActivoForm) form;
			List listCpa = (List) dao.getListado(getIdUsuario(), formulario.getIdFarmacia(), formulario.getCodGtVm(), formulario.getOidDivisionResidenciaFiltro());
			formulario.setListaControlPrincipioActivo(listCpa);
			
			boolean result=false;
			if(formulario.getACTIONTODO()!=null && formulario.getACTIONTODO().equals("CONFIRMADO_OK"))
			{
				
				Iterator<ControlPrincipioActivo> it = listCpa.iterator();
				while (it!=null && it.hasNext())
				{
					ControlPrincipioActivo cpa = (ControlPrincipioActivo) it.next();
					result=dao.borrar(cpa);
				if(result)
				{
				//	errors.add(SPDConstants.MSG_LEVEL_INFO, new ActionMessage("Registro borrado correctamente Info"));
					//dejamos log
					try{
						SpdLogAPI.addLog(getIdUsuario(), cpa.getFarmacia().getIdFarmacia(), (cpa.getDivisionResidencia()!=null?cpa.getDivisionResidencia().getIdDivisionResidencia():""), "", 
								SpdLogAPI.A_GESTION_FARMACIA, SpdLogAPI.B_BORRADO, SpdLogAPI.C_CONTROL_PRINCIPIO_ACTIVO, "SpdLog.gestionFarmacia.borrado.controlPActivo", cpa.getBdConsejo().getNomGtVm() );
					}catch(Exception e){}	//Se elimina el control extra para el principio activo @@.  

					errors.add( "Registro borrado correctamente ");
					formulario.setIdDivisionResidencia("");
					formulario.setIdFarmacia("");
					formulario.setCodGtVm("");
				}
				else errors.add( "Error en el borrado del registro");
				}
				list( mapping,  form,  request,  response);
				formulario.setErrors(errors);
				return mapping.findForward("list");
			}
			return mapping.findForward("borrar");
	}

	/*
	
	public ActionForward editar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		GestSustitucionesLiteForm f =  (GestSustitucionesLiteForm) form;
		GestSustitucionesLite sustitucionLite = GestSustitucionesLiteDAO.getSustitucionLiteByOid(f);
		f.setListaTiposAccion(GestSustitucionesLiteDAO.getListaTiposAccion());
		
		List errors = new ArrayList();
		boolean result=false;
		if(f.getACTIONTODO()!=null && f.getACTIONTODO().equals("EDITA_OK"))
		{
			sustitucionLite.setSpdAccionBolsa(f.getIdTipoAccion());
		    sustitucionLite.setExcepciones(f.getExcepciones());
			sustitucionLite.setAux1(f.getAux1());
			sustitucionLite.setAux2(f.getAux2());
			result=GestSustitucionesLiteDAO.editaSustLite(sustitucionLite);
			
			if(result)
			{
				errors.add( "Registro editado correctamente ");
				f.setOidGestSustitucionesLite(0);
				f.setSustitucionLite(null);
			}
			else errors.add( "No se ha podido editar el registro");

			list( mapping,  form,  request,  response);
			f.setErrors(errors);
			return mapping.findForward("list");
			
		}
		f.setSustitucionLite(sustitucionLite);

		return mapping.findForward("editar");
	}

	

	*/
	protected int actualizaCurrentPage(ControlPrincipioActivoForm aForm, int numberObjects) {
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
	