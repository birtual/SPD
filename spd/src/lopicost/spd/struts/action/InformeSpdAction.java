package lopicost.spd.struts.action;

import lopicost.config.logger.Logger;
import lopicost.spd.persistence.FicheroResiCabeceraDAO;
import lopicost.spd.robot.bean.rd.ProduccionPaciente;
import lopicost.spd.robot.helper.InformeHelper;
import lopicost.spd.struts.bean.FicheroResiBean;
import lopicost.spd.struts.form.InformeSpdForm;

import org.apache.struts.action.ActionForward;
import javax.servlet.http.HttpServletResponse;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;


public class InformeSpdAction extends GenericAction
{
	/*
    public ActionForward base(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response, boolean recetas, boolean prevaleceReceta) throws Exception {
        final InformeSpdForm f= (InformeSpdForm)form;
    	FicheroResiBean cab = FicheroResiCabeceraDAO.getFicheroResiCabeceraByOid(getIdUsuario(), f.getOidFicheroResiCabecera());
    	f.setCabecera(cab);
        InformeHelper helper = new InformeHelper();
        List<ProduccionPaciente> producciones =  helper.findByIdResidenciaCarga(getIdUsuario(), cab, recetas, prevaleceReceta);
       
        f.setProducciones(producciones);
        return mapping.findForward("global");
    }
    */
	private ActionForward baseLite(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, String forwardName, boolean recetas, boolean prevaleceReceta) throws Exception {
	    final InformeSpdForm f = (InformeSpdForm) form;
	    FicheroResiBean cab = FicheroResiCabeceraDAO.getFicheroResiCabeceraByOid(getIdUsuario(), f.getOidFicheroResiCabecera());
	    f.setCabecera(cab);

	    InformeHelper helper = new InformeHelper();
	    List<ProduccionPaciente> producciones = helper.findByIdResidenciaCarga(getIdUsuario(), cab, recetas, prevaleceReceta);
	    f.setProducciones(producciones);

	    return mapping.findForward(forwardName);
	}

	public ActionForward etiquetas(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
	    return baseLite(mapping, form, request, "etiquetas", false, false);
	}
	public ActionForward etiquetasR(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
	    return baseLite(mapping, form, request, "etiquetasR", false, true);
	}
	public ActionForward globalLite(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
	    return baseLite(mapping, form, request, "globalLite", true, false);
	}
	public ActionForward globalLiteAll(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
	    return baseLite(mapping, form, request, "globalLiteAll", true, false);
	}
	public ActionForward global(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
	    return baseLite(mapping, form, request, "global", true, false);
	}
	
	/*
    public ActionForward global(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final InformeSpdForm f= (InformeSpdForm)form;
    	FicheroResiBean cab = FicheroResiCabeceraDAO.getFicheroResiCabeceraByOid(getIdUsuario(), f.getOidFicheroResiCabecera());
    	f.setCabecera(cab);
        InformeHelper helper = new InformeHelper();
        List<ProduccionPaciente> producciones =  helper.findByIdResidenciaCarga(getIdUsuario(), cab);
       
        f.setProducciones(producciones);
        return mapping.findForward("global");
    }
  
    public ActionForward globalLite(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final InformeSpdForm f= (InformeSpdForm)form;
    	FicheroResiBean cab = FicheroResiCabeceraDAO.getFicheroResiCabeceraByOid(getIdUsuario(), f.getOidFicheroResiCabecera());
    	f.setCabecera(cab);
        InformeHelper helper = new InformeHelper();
        List<ProduccionPaciente> producciones =  helper.findByIdResidenciaCarga(getIdUsuario(), cab);
        f.setProducciones(producciones);
        return mapping.findForward("globalLite");
    }
  */
    public ActionForward detalle(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final InformeSpdForm f= (InformeSpdForm)form;
    	FicheroResiBean cab = FicheroResiCabeceraDAO.getFicheroResiCabeceraByOid(getIdUsuario(), f.getOidFicheroResiCabecera());
    	f.setCabecera(cab);
        InformeHelper helper = new InformeHelper();
        List<ProduccionPaciente> producciones =  helper.findByIdResidenciaCarga(getIdUsuario(), cab, false, false);
       
        f.setProducciones(producciones);
        return mapping.findForward("detalle");
    }
    
    public void log(final String message, final int level) {
        Logger.log("SpdLogger", message, level);
    }
}