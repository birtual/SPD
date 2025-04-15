package lopicost.spd.struts.action;


import java.util.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import lopicost.config.logger.Logger;
import lopicost.spd.controller.SpdLogAPI;

import lopicost.spd.persistence.*;
import lopicost.spd.struts.form.FicheroResiForm;
import lopicost.spd.utils.SPDConstants;
public class FicheroResiCabeceraLiteHstAction extends GenericAction  {

	FicheroResiCabeceraDAO dao= new  FicheroResiCabeceraDAO();
	FicheroResiDetalleDAO daoDetalle= new  FicheroResiDetalleDAO();
	
	   
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		FicheroResiForm formulari =  (FicheroResiForm) form;
		
		//inicializamos para que no haya datos de otros módulos al venir de un borrado por ejemplo
		formulari.setOidFicheroResiCabecera(0);
		formulari.setListaFicheroResiCabeceraBean(dao.getListaDivisionResidenciasCargadas(getIdUsuario(), true));
		//paginación
		int currpage = actualizaCurrentPage(formulari, dao.getCountGestFicheroResi(getIdUsuario(), formulari, true));
		formulari.setListaDivisionResidenciasCargadas(dao.getListaDivisionResidenciasCargadas(getIdUsuario(), true));
	
		formulari.setListaFicheroResiCabeceraBean(dao.getGestFicheroResi(getIdUsuario(), formulari, currpage*SPDConstants.PAGE_ROWS,(currpage+1)*SPDConstants.PAGE_ROWS, null, false, true));
	
		//INICIO creación de log en BBDD
		try{
			SpdLogAPI.addLog(getIdUsuario(), "",  "", "",  SpdLogAPI.A_TRATAMIENTO, SpdLogAPI.B_ACCESO, SpdLogAPI.C_LISTADO_HIST, "SpdLog.tratamiento.consulta.acceso_listado_hist", getIdUsuario());
		}catch(Exception e){}	 //Consulta del listado de tratamientos 
		//FIN creación de log en BBDD
		
		return mapping.findForward("list");
	}
	

	public ActionForward detalle(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		FicheroResiForm formulari =  (FicheroResiForm) form;
		//creación de log en BBDD
		try{
			SpdLogAPI.addLog(getIdUsuario(), "",  formulari.getIdDivisionResidencia(), formulari.getIdProceso(), SpdLogAPI.A_PRODUCCION, SpdLogAPI.B_CONSULTA, SpdLogAPI.C_DETALLE, "SpdLog.produccion.consulta.detalle", formulari.getIdProceso() );
		}catch(Exception e){}	//SpdLog.produccion.consulta.detalle
		
		return mapping.findForward("detalle");
	}
	


	

	public void log (String message, int level)
	{
		Logger.log("SgaLogger",message,level);	
	}
	

	

	/**
	 * método de ayuda a la paginación
	 * @param aForm
	 * @param numberObjects
	 * @return
	 */
	private int actualizaCurrentPage(FicheroResiForm aForm, int numberObjects) {
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
	