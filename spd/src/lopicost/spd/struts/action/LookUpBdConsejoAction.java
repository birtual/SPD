package lopicost.spd.struts.action;

import org.apache.struts.action.*;
import org.apache.struts.actions.DispatchAction;
import com.google.gson.Gson;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.SQLException;
import java.util.*;

import lopicost.spd.model.BdConsejo;
import lopicost.spd.persistence.*;
import lopicost.spd.struts.form.BdConsejoForm;

import lopicost.spd.utils.SPDConstants;

import lopicost.config.logger.Logger;


public class LookUpBdConsejoAction extends DispatchAction  
{
	private final String cLOGGERHEADER = "bdConsejoLookUpAction: ";
	private final String cLOGGERHEADER_ERROR = cLOGGERHEADER + "ERROR: bdConsejoLookUpAction";
	private List listaBdConsejo = null;
	BdConsejoDAO dao= new BdConsejoDAO();

	public ActionForward init(ActionMapping mapping,ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		ActionForward actionForward = mapping.findForward("init");

		BdConsejoForm f =  (BdConsejoForm) form;
	//	formulari.setListaPresentacion(BdConsejoDAO.getListaPresentacion());
		//	bdConsejoForm.setListaGtVm(BdConsejoDAO.getListaGtVmDeConsejo(bdConsejoForm.getFiltroCodiLaboratorio(), false, false));
		f.setListaGtVmp(BdConsejoDAO.getListaGtVmp( f.getFiltroCodiLaboratorio(), null, f.getFiltroNomGtVm(), false, false));
		f.setListaGtVmpp(BdConsejoDAO.getListaGtVmpp(f.getFiltroCodiLaboratorio(), null, f.getFiltroNomGtVm(), f.getFiltroCodGtVmp(), null, false, false));
	//	formulari.setListaLabs(BdConsejoDAO.getLabsBdConsejo(formulari.getFiltroCodiLaboratorio(), formulari.getFiltroNombreLaboratorio(), 0,10000)) ;
		f.setListaLabs(BdConsejoDAO.getLabsBdConsejo(null, null, 0,10000, null, f.getFiltroNomGtVm(), f.getFiltroCodGtVmp(), null, f.getFiltroCodGtVmpp(), null )) ;
		//utilizado para mostrar los gtvmp de un CN
		if(f.getMode().equals("showGtvmpCn"))
		{
			String resiCn = request.getParameter("resiCn");
			BdConsejo bdConsejoResi = BdConsejoDAO.getBdConsejobyCN(resiCn);
			String Gtvmp = bdConsejoResi!=null && bdConsejoResi.getCodGtVmp()!=null ? bdConsejoResi.getCodGtVmp():"";
			f.setFiltroCodGtVmp(Gtvmp);
		}
		boolean hayFiltros = ( (f.getCnConsejo()!=null && !f.getCnConsejo().isEmpty() ) || (f.getNombreCortoOK()!=null && !f.getNombreCortoOK().isEmpty() ) ||  
				(f.getFiltroCodGtVm()!=null && !f.getFiltroCodGtVm().isEmpty() ) || (f.getFiltroNomGtVm()!=null && !f.getFiltroNomGtVm().isEmpty() ) || 
				(f.getFiltroCodGtVmp()!=null && !f.getFiltroCodGtVmp().isEmpty() ) || (f.getFiltroNomGtVmp()!=null && !f.getFiltroNomGtVmp().isEmpty() ) || 
				(f.getFiltroCodGtVmpp()!=null && !f.getFiltroCodGtVmpp().isEmpty() ) || (f.getFiltroNomGtVmpp()!=null && !f.getFiltroNomGtVmpp().isEmpty() ) );
				
		int getCountBdConsejo = 0;
		int currpage = 0;
		List<BdConsejo> listaBdConsejo =  new ArrayList();
		if(hayFiltros)
		{
			getCountBdConsejo=BdConsejoDAO.getCountBdConsejo(f);
			currpage=actualizaCurrentPage(f, getCountBdConsejo);
			//listaBdConsejo = dao.getBdConsejo(f, currpage*SPDConstants.PAGE_ROWS,(currpage+1)*SPDConstants.PAGE_ROWS); 
			listaBdConsejo = dao.getBdConsejo(f, currpage*SPDConstants.PAGE_ROWS,(currpage+1)*SPDConstants.PAGE_ROWS); 
		}
		f.setListaBdConsejo(listaBdConsejo);
		f.setFieldName1(request.getParameter("fieldName1"));
		return mapping.findForward("init");
	}

	public ActionForward initLabs(ActionMapping mapping,ActionForm form,HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, ClassNotFoundException, SQLException 
	{
		ActionForward actionForward = mapping.findForward("init");
		BdConsejoForm bdConsejoForm =  (BdConsejoForm) form;
		int getCountLabsBdConsejo = BdConsejoDAO.getCountLabsBdConsejo(bdConsejoForm.getCodiLab(), bdConsejoForm.getNombreLab());
		int currpage = actualizaCurrentPage(bdConsejoForm, getCountLabsBdConsejo);
		bdConsejoForm.setListaLabsBdConsejo(dao.getLabsBdConsejo(bdConsejoForm.getCodiLab(), bdConsejoForm.getNombreLab(), currpage*SPDConstants.PAGE_ROWS,(currpage+1)*SPDConstants.PAGE_ROWS));
		bdConsejoForm.setFieldName1(request.getParameter("fieldName1"));
		return mapping.findForward("initLabs");
	}

	
	private int actualizaCurrentPage(BdConsejoForm aForm, int numberObjects) {
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