package lopicost.spd.struts.action;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import lopicost.spd.model.SustXGtvmp;
import lopicost.spd.persistence.BdConsejoDAO;
import lopicost.spd.persistence.RobotDAO;
import lopicost.spd.persistence.SustXComposicionDAO;
import lopicost.spd.persistence.SustXGtDAO;
import lopicost.spd.struts.form.SustXComposicionForm;
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
		formulari.setListaRobots(RobotDAO.getListaRobots());
		//formulari.setListaBeans(dao.getSustXGtvmp(formulari, currpage*SPDConstants.PAGE_ROWS,(currpage+1)*SPDConstants.PAGE_ROWS));
		formulari.setListaBeans(dao.getSustXGtvmp(formulari, 1, 1000000));

		//formulari.setListaGtVmp(dao.getSustXGtvmp(formulari, currpage*SPDConstants.PAGE_ROWS,(currpage+1)*SPDConstants.PAGE_ROWS));
		List errors = new ArrayList();
		formulari.setErrors(errors);

		return mapping.findForward("list");
	}
	
	public ActionForward nuevo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//inicializamos para que no haya datos de otros módulos
		SustXGtForm formulari =  (SustXGtForm) form;
		//formulari.setListaGtVm(BdConsejoDAO.getListaGtVmDeConsejo());
		//formulari.setListaPresentacion(BdConsejoDAO.getListaPresentacion());
		//formulari.setListaGtVmpp(BdConsejoDAO.getListaGtVmpp(formulari.getFiltroCodGtVm(), formulari.getFiltroNomGtVm(), formulari.getFiltroCodGtVmp(), formulari.getFiltroNomGtVmp()));
		formulari.setListaLabs(BdConsejoDAO.getLabsBdConsejo(null, null, 0,10000)) ;
		formulari.setSustXGtPadre(SustXGtDAO.getPadreByOid(formulari.getOidSustXComposicion()));
		
		List errors = new ArrayList();
		boolean result=false;
		System.out.println("SustXGtAction.getACTIONTODO() main "  +formulari.getACTIONTODO());
		if(formulari.getACTIONTODO()!=null && formulari.getACTIONTODO().equals("NUEVO"))
		{
			formulari.setComentarios("");
			//formulari.setOidSustXComposicion(0);
			//formulari.setPonderacion(0);
			formulari.setRentabilidad(0);
			formulari.setSustituible("NO");
		}
		else if(formulari.getACTIONTODO()!=null && formulari.getACTIONTODO().equals("NUEVO_OK"))
		{
			result=SustXGtHelper.nuevoGtVmp(getIdUsuario(), formulari);
			//result=SustXGtDAO.nuevo(formulari);
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
	