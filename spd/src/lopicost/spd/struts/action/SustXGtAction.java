package lopicost.spd.struts.action;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import lopicost.spd.model.Nivel3;
import lopicost.spd.persistence.RobotDAO;
import lopicost.spd.persistence.SustXGtDAO;
import lopicost.spd.struts.form.SustXGtForm;
import lopicost.spd.struts.helper.SustXGtHelper;
import lopicost.spd.utils.SPDConstants;
public class SustXGtAction extends GenericAction  {

	SustXGtDAO dao= new  SustXGtDAO();
	   
	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
	
		SustXGtForm formulari =  (SustXGtForm) form;
	//	inicializamos para que no haya datos de otros módulos
		formulari.setOidSustXComposicion(0);
		formulari.setListaRobots(RobotDAO.getListaRobots());
		
		//int getCountSustXComposicion= dao.getCountSustXComposicion(formulari);
		//int currpage = actualizaCurrentPage(formulari, getCountSustXComposicion);
		//formulari.setListaGtVm(BdConsejoDAO.getAutoGtVm());
		//formulari.setListaGtVmp(BdConsejoDAO.getListaGtVmp());
		//formulari.setListaGtVmpp(BdConsejoDAO.getListaGtVmpp());
		//formulari.setListaRobots(RobotDAO.getListaRobots());
		//formulari.setListaBeans(dao.getSustXGtvmp(formulari, currpage*SPDConstants.PAGE_ROWS,(currpage+1)*SPDConstants.PAGE_ROWS));
		//formulari.setListaBeans(dao.getDesdeNivel(formulari, currpage*SPDConstants.PAGE_ROWS,(currpage+1)*SPDConstants.PAGE_ROWS));
		formulari.setListaBeans(dao.getDesdeNivel(formulari, 0, 1000000));

		//formulari.setListaGtVmp(dao.getSustXGtvmp(formulari, currpage*SPDConstants.PAGE_ROWS,(currpage+1)*SPDConstants.PAGE_ROWS));
		List errors = new ArrayList();
		formulari.setErrors(errors);

		return mapping.findForward("list");
	}
	
	/**
	 * Creación de un GTVM (principio activo)
	 * Se crean todos los hijos (GTVMP) y nietos (GTVMPP) que se deriven
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward nuevoDesdeNivel1(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		SustXGtForm formulari =  (SustXGtForm) form;
		formulari.setNivel1(SustXGtDAO.getNivel1ById(formulari.getFiltroCodGtVm()));
		
		List errors = new ArrayList();
		boolean result=false;
		System.out.println("SustXGtAction.getACTIONTODO() main "  +formulari.getACTIONTODO());
		if(formulari.getACTIONTODO()!=null && formulari.getACTIONTODO().equals("NUEVO_OK"))
		{
			result=SustXGtHelper.gestionarNivel(getIdUsuario(), formulari);
			if(result)
			{
				errors.add( "Registros gestionados correctamente ");
			}
			else errors.add( "No se ha podido gestionar el registro");
			
			formulari.setErrors(errors);
			list( mapping,  form,  request,  response);
			return mapping.findForward("list");
		}
		return mapping.findForward("nuevo1");
	}
	
	
	
	public ActionForward nuevoDesdeNivel2(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		//inicializamos para que no haya datos de otros módulos
		SustXGtForm formulari =  (SustXGtForm) form;
		//formulari.setListaGtVm(BdConsejoDAO.getListaGtVmDeConsejo());
		//formulari.setListaPresentacion(BdConsejoDAO.getListaPresentacion());
		//formulari.setListaGtVmpp(BdConsejoDAO.getListaGtVmpp(formulari.getFiltroCodGtVm(), formulari.getFiltroNomGtVm(), formulari.getFiltroCodGtVmp(), formulari.getFiltroNomGtVmp()));
		//formulari.setListaLabs(BdConsejoDAO.getLabsBdConsejo(null, null, 0,10000)) ;
		formulari.setNivel2(SustXGtDAO.getNivel2ById(formulari.getFiltroCodGtVmp()));
		
		List errors = new ArrayList();
		boolean result=false;
		System.out.println("SustXGtAction.getACTIONTODO() main "  +formulari.getACTIONTODO());
		if(formulari.getACTIONTODO()!=null && formulari.getACTIONTODO().equals("NUEVO"))
		{
			formulari.setComentarios("");
			formulari.setRentabilidad(0);
			formulari.setSustituible("NO");
		}
		else if(formulari.getACTIONTODO()!=null && formulari.getACTIONTODO().equals("NUEVO_OK"))
		{
			result=SustXGtHelper.gestionarNivel(getIdUsuario(), formulari);
			if(result)
			{
				errors.add( "Registro creado correctamente ");
			}
			else errors.add( "No se ha podido crear el registro");
			
			formulari.setErrors(errors);
			//formulari.setFiltroCodGtVmp("");  //dejamos lo que había filtrado en el listado
			list( mapping,  form,  request,  response);
			return mapping.findForward("list");
		}
		return mapping.findForward("nuevo2");
	}
	
	
	public ActionForward nuevoDesdeNivel3(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		//inicializamos para que no haya datos de otros módulos
		SustXGtForm formulari =  (SustXGtForm) form;
		Nivel3 nivel3 = SustXGtDAO.getNivel3ById(formulari.getFiltroCodGtVmpp());
		formulari.setNivel3(nivel3);
		formulari.setNivel2(nivel3.getNivel2());
		formulari.setNivel1(nivel3.getNivel1());
		List errors = new ArrayList();
		boolean result=false;
		System.out.println("SustXGtAction.nuevoDesdeNivel3 "  +formulari.getACTIONTODO());
		if(formulari.getACTIONTODO()!=null && formulari.getACTIONTODO().equals("NUEVO"))
		{
			formulari.setComentarios("");
			formulari.setRentabilidad(0);
			formulari.setSustituible("NO");
		}
		else if(formulari.getACTIONTODO()!=null && formulari.getACTIONTODO().equals("NUEVO_OK"))
		{
			result=SustXGtHelper.gestionarNivel(getIdUsuario(), formulari);
			if(result)
			{
				errors.add( "Registro creado correctamente ");
			}
			else errors.add( "No se ha podido crear el registro");
			
			formulari.setErrors(errors);
			//formulari.setFiltroCodGtVmp("");  //dejamos lo que había filtrado en el listado
			list( mapping,  form,  request,  response);
			return mapping.findForward("list");
		}
		return mapping.findForward("nuevo3");
	}
	
	

	private int actualizaCurrentPage(SustXGtForm aForm, int numberObjects) {
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
	