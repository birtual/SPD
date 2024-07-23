package lopicost.spd.struts.action;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import lopicost.spd.struts.action.GenericAction;

import lopicost.spd.persistence.DivisionResidenciaDAO;

import lopicost.spd.persistence.SpdLogDao;

import lopicost.spd.struts.form.SpdLogForm;
import lopicost.spd.utils.SPDConstants;
public class SpdLogAction extends GenericAction  {

	SpdLogDao dao= new  SpdLogDao();
	
	

	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	
		
		List<SpdLogAction> resultList= new ArrayList<SpdLogAction>();
		SpdLogForm formulario =  (SpdLogForm) form;
	
		int getCount = dao.getCount(getIdUsuario(), formulario);
		int currpage = actualizaCurrentPage(formulario, getCount);
		int rows = SPDConstants.PAGE_ROWS *2;
		formulario.setListaDivisionResidencia(DivisionResidenciaDAO.getListaDivisionResidencias(getIdUsuario()));
		formulario.setListaProcesos(dao.getApartados());
		formulario.setListaApartados(dao.getApartados());
		formulario.setListaAcciones(dao.getAcciones());
		formulario.setListaSubAcciones(dao.getSubAcciones());
		int inicio = currpage*rows;
		int fin = (currpage+1)*rows;
		formulario.setLista(dao.getListado(getIdUsuario(), formulario, inicio, fin, false));
		
		List errors = new ArrayList();
		formulario.setErrors(errors);

		return mapping.findForward("list");
	}
	
	
	protected int actualizaCurrentPage(SpdLogForm aForm, int numberObjects) {
	{
		int numpages= aForm.getNumpages();
		int currpage= aForm.getCurrpage();
		int rows = SPDConstants.PAGE_ROWS*2;

			if (numberObjects% rows != 0)
				numberObjects= numberObjects / rows + 1;
			else
				numberObjects = numberObjects/ rows;	
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
	