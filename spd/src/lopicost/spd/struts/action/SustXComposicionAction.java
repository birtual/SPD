package lopicost.spd.struts.action;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import lopicost.spd.iospd.exportdata.process.ExcelSustXComposicion;
import lopicost.spd.model.BdConsejo;
import lopicost.spd.model.SustXComposicion;

import lopicost.spd.persistence.BdConsejoDAO;
import lopicost.spd.persistence.RobotDAO;
import lopicost.spd.persistence.SustXComposicionDAO;
import lopicost.spd.persistence.SustXGtDAO;
import lopicost.spd.struts.form.SustXComposicionForm;
import lopicost.spd.utils.DataUtil;
import lopicost.spd.utils.SPDConstants;
public class SustXComposicionAction extends GenericAction  {

	SustXComposicionDAO dao= new  SustXComposicionDAO();
	SustXGtDAO daoGt= new SustXGtDAO();
	   
	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
	
		SustXComposicionForm formulari =  (SustXComposicionForm) form;
	//	inicializamos para que no haya datos de otros módulos
		formulari.setOidSustXComposicion(0);
		formulari.setCodGtVmp("");
		formulari.setNombreLab(SustXComposicionDAO.getNombreLaboratorio(formulari.getFiltroCodiLaboratorio()));
		formulari.setListaRobots(RobotDAO.getListaRobots());
		formulari.setFiltroCodigoMedicamento(formulari.getCnOk());
		
		int getCountSustXComposicion= dao.getCountSustXComposicion(formulari);
		int currpage = actualizaCurrentPage(formulari, getCountSustXComposicion);
		//formulari.setListaSustXComposicion(dao.getSustXComposicion(formulari, currpage*SPDConstants.PAGE_ROWS,(currpage+1)*SPDConstants.PAGE_ROWS));
		formulari.setListaSustXComposicion(dao.getSustXComposicion(formulari, currpage*SPDConstants.PAGE_ROWS,(currpage+1)*SPDConstants.PAGE_ROWS));
		formulari.setListaGtVm(BdConsejoDAO.getListaGtVmDeConsejo(formulari.getFiltroCodiLaboratorio(), formulari.isFiltroCheckedLabsSoloAsignados(), formulari.isFiltroCheckedComposicionSinLabs()));
		formulari.setListaGtVmp(BdConsejoDAO.getListaGtVmp(formulari.getFiltroCodiLaboratorio(),  formulari.getFiltroCodGtVm(), formulari.getFiltroNomGtVm(), formulari.isFiltroCheckedLabsSoloAsignados(), formulari.isFiltroCheckedComposicionSinLabs()));
		formulari.setListaGtVmpp(BdConsejoDAO.getListaGtVmpp(formulari.getFiltroCodiLaboratorio(), formulari.getFiltroCodGtVm(), formulari.getFiltroNomGtVm(),formulari.getFiltroCodGtVmp(), formulari.getFiltroNomGtVmp(), formulari.isFiltroCheckedLabsSoloAsignados(), formulari.isFiltroCheckedComposicionSinLabs()));
		formulari.setListaLabs(BdConsejoDAO.getLabsBdConsejo(null, null, 0,10000, formulari.getFiltroCodGtVm(), formulari.getFiltroNomGtVm(), formulari.getFiltroCodGtVmp(), formulari.getFiltroNomGtVmp(), formulari.getFiltroCodGtVmpp(), formulari.getFiltroNomGtVmpp())) ;

		formulari.setNombreConsejo(formulari.getNombreConsejo());
		List errors = new ArrayList();
		formulari.setErrors(errors);
		if(formulari.getACTIONTODO()!=null && formulari.getACTIONTODO().equals("EXCEL"))
		{
			return mapping.findForward("exportExcel");
		}
		return mapping.findForward("list");
	}
	
	
	public ActionForward editar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SustXComposicionForm formulari =  (SustXComposicionForm) form;
		SustXComposicion sust = SustXComposicionDAO.getSustXComposicionByOid(formulari);
		formulari.setSustXComposicion(sust);
			
		List errors = new ArrayList();
		boolean result=false;
		if(formulari.getACTIONTODO()!=null && formulari.getACTIONTODO().equals("EDITA_OK"))
		{
			BdConsejo bdConsejo=BdConsejoDAO.getByCN(formulari.getCn6()); 
			sust.setCn6(bdConsejo.getCnConsejo());
			sust.setCn7((String.valueOf(DataUtil.getCN7(new Integer(bdConsejo.getCnConsejo()).intValue()))));
			sust.setNombreMedicamento(bdConsejo.getNombreConsejo());
			sust.setCodGtVm(bdConsejo.getCodGtVm());
			sust.setNomGtVm(bdConsejo.getNomGtVm());
			sust.setCodGtVmp(bdConsejo.getCodGtVmp());
			sust.setNomGtVmp(bdConsejo.getNomGtVmp());
			sust.setCodGtVmpp(bdConsejo.getCodGtVmpp());
			sust.setNomGtVmpp(bdConsejo.getNomGtVmpp());
			sust.setCodiLab(bdConsejo.getCodigoLaboratorio());
			sust.setNombreLab(bdConsejo.getNombreLaboratorio());
					
			sust.setComentarios(formulari.getComentarios());
			sust.setSustituible(formulari.getSustituible());
			try {
				sust.setRentabilidad(formulari.getRentabilidad()+"");
			}catch(Exception e){}
			try {
				sust.setPonderacion(formulari.getPonderacion()+"");
			}catch(Exception e){}
			formulari.setSustXComposicion(sust);
			
			result=SustXComposicionDAO.edita(sust);
			if(result)
			{
				errors.add( "Registro editado correctamente ");
			}
			else errors.add( "No se ha podido editar el registro");

			list( mapping,  form,  request,  response);
			formulari.setErrors(errors);
			return mapping.findForward("list");
		}
			
		return mapping.findForward("editar");
	}

	
	public ActionForward borrar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		List errors = new ArrayList();
		SustXComposicionForm formulari =  (SustXComposicionForm) form;
		formulari.setSustXComposicion(SustXComposicionDAO.getSustXComposicionByOid(formulari));
		boolean result=false;
		if(formulari.getACTIONTODO()!=null && formulari.getACTIONTODO().equals("CONFIRMADO_OK"))
		{
			result=SustXComposicionDAO.borra(formulari);
			if(result)
			{
			//	errors.add(SPDConstants.MSG_LEVEL_INFO, new ActionMessage("Registro borrado correctamente Info"));
				errors.add( "Registro borrado correctamente ");
			}
			else errors.add( "Error en el borrado del registro");
			list( mapping,  form,  request,  response);
			formulari.setErrors(errors);
			return mapping.findForward("list");
		}
			return mapping.findForward("borrar");
	}


	
	public ActionForward nuevo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//inicializamos para que no haya datos de otros módulos
		SustXComposicionForm formulari =  (SustXComposicionForm) form;
		formulari.setListaGtVm(BdConsejoDAO.getListaGtVmDeConsejo());
		formulari.setListaPresentacion(BdConsejoDAO.getListaPresentacion());
		formulari.setListaGtVmpp(BdConsejoDAO.getListaGtVmpp(formulari.getFiltroCodGtVm(), formulari.getFiltroNomGtVm(), formulari.getFiltroCodGtVmp(), formulari.getFiltroNomGtVmp()));
		formulari.setListaLabs(BdConsejoDAO.getLabsBdConsejo(null, null, 0,10000)) ;

		List errors = new ArrayList();
		boolean result=false;
		System.out.println("SustXComposicionForm.getACTIONTODO() main "  +formulari.getACTIONTODO());
		if(formulari.getACTIONTODO()!=null && formulari.getACTIONTODO().equals("NUEVO"))
		{
			formulari.setComentarios("");
			formulari.setOidSustXComposicion(0);
			formulari.setPonderacion(0);
			formulari.setRentabilidad(0);
			formulari.setSustituible("NO");
		}
		else if(formulari.getACTIONTODO()!=null && formulari.getACTIONTODO().equals("NUEVO_OK"))
		{
			result=SustXComposicionDAO.nuevo(formulari);
			if(result)
			{
				errors.add( "Registro creado correctamente ");
			}
			else errors.add( "No se ha podido crear el registro");
			
			list( mapping,  form,  request,  response);
			formulari.setErrors(errors);
			return mapping.findForward("list");
		}
		return mapping.findForward("nuevo");
	}
	
	
	public ActionForward nuevoAsignacionMasiva(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	
		List<SustXComposicionAction> resultList= new ArrayList<SustXComposicionAction>();
		SustXComposicionForm formulari =  (SustXComposicionForm) form;
		//inicializamos para que no haya datos de otros módulos
		
		formulari.setOidSustXComposicion(0);
		formulari.setListaSustXComposicion(dao.getSustXComposicion(formulari, 0, 100000));
		formulari.setNombreLab(SustXComposicionDAO.getNombreLaboratorio(formulari.getFiltroCodiLaboratorio()));

		List errors = new ArrayList();
		formulari.setErrors(errors);
		if(formulari.getFiltroCodiLaboratorio()==null || formulari.getFiltroCodiLaboratorio().equals(""))
		{
			errors.add( "Falta indicar el laboratorio a asignar");
			return mapping.findForward("list");
		}
			boolean result=false;
		if(errors.isEmpty() && formulari.getACTIONTODO()!=null && formulari.getACTIONTODO().equals("CONFIRMADO_OK"))
		{
			result=SustXComposicionDAO.nuevoAsignacionMasiva(formulari);
			if(result)
			{
			//	errors.add(SPDConstants.MSG_LEVEL_INFO, new ActionMessage("Registro borrado correctamente Info"));
				errors.add( "Registros creados correctamente ");
			}
			else errors.add( "Error en la creación masiva de registros");
			list( mapping,  form,  request,  response);
			formulari.setErrors(errors);
			return mapping.findForward("list");
		}
		return mapping.findForward("nuevoAsignacionMasiva");
	}

		public ActionForward borradoMasivo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	
		List<SustXComposicionAction> resultList= new ArrayList<SustXComposicionAction>();
		SustXComposicionForm formulari =  (SustXComposicionForm) form;
		//inicializamos para que no haya datos de otros módulos
		
		formulari.setFiltroLabsSoloAsignados(true);
		formulari.setOidSustXComposicion(0);
		formulari.setListaSustXComposicion(dao.getSustXComposicion(formulari, 0, 100000));
		formulari.setNombreLab(SustXComposicionDAO.getNombreLaboratorio(formulari.getFiltroCodiLaboratorio()));
		List errors = new ArrayList();
		formulari.setErrors(errors);
		if(formulari.getFiltroCodiLaboratorio()==null || formulari.getFiltroCodiLaboratorio().equals(""))
		{
			errors.add( "Falta indicar el laboratorio a desasignar");	
			return mapping.findForward("list");
		}
		//formulari.setListaConjuntosHomogeneos(BdConsejoDAO.getListaConjuntosHomogeneos(formulari.getFiltroListaConjuntosHomogeneos(), formulari.getFiltroListaPrincipioActivo(), formulari.getFiltroCodGtVmp()));

		boolean result=false;
		if(errors.isEmpty() && formulari.getACTIONTODO()!=null && formulari.getACTIONTODO().equals("CONFIRMADO_OK"))
		{
			result=SustXComposicionDAO.desAsignacionMasiva(formulari);
			if(result)
			{
				errors.add( "Registros borrados correctamente ");
			}
			else errors.add( "Error en el borrado masivo de registros");
			list( mapping,  form,  request,  response);
			formulari.setErrors(errors);
			return mapping.findForward("list");
		}
		return mapping.findForward("borradoMasivo");
	}


	private int actualizaCurrentPage(SustXComposicionForm aForm, int numberObjects) {
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

	    public ActionForward exportExcel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
	    	List errors = new ArrayList();
	    	SustXComposicionForm formulari =  (SustXComposicionForm) form;
	    //	inicializamos para que no haya datos de otros módulos
	    	formulari.setOidSustXComposicion(0);
	    	ExcelSustXComposicion excelCreator = new ExcelSustXComposicion();
	    		 
	    	HSSFWorkbook workbook = excelCreator.createWorkbook(formulari, dao.getSustXComposicion(formulari,0,100000));
		    response.setHeader("Content-Disposition", "attachment; filename=c:/UserDetails.xls");
		    ServletOutputStream out = response.getOutputStream();
		    workbook.write(out);
		    out.flush();
		    out.close();

		    formulari.setErrors(errors);
	
	    	return mapping.findForward("exportExcel");
	    }
	
	
}
	