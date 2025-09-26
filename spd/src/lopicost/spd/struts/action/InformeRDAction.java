package lopicost.spd.struts.action;

import lopicost.config.logger.Logger;
import lopicost.spd.persistence.FicheroResiCabeceraDAO;

import lopicost.spd.helper.InformeRDHelper;
import lopicost.spd.model.spd.ProduccionPaciente;
import lopicost.spd.struts.bean.FicheroResiBean;
import lopicost.spd.struts.form.InformeRDForm;

import org.apache.struts.action.ActionForward;
import javax.servlet.http.HttpServletResponse;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;


public class InformeRDAction extends GenericAction
{
	
	private ActionForward baseLite(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, String forwardName, boolean recetas, boolean buscarDispensacion) throws Exception {
	    final InformeRDForm f = (InformeRDForm) form;
	    FicheroResiBean cab = FicheroResiCabeceraDAO.getFicheroResiCabeceraByOid(getIdUsuario(), f.getOidFicheroResiCabecera());
	    f.setCabecera(cab);

	    InformeRDHelper helper = new InformeRDHelper();
	    List<ProduccionPaciente> producciones = helper.findByIdResidenciaCarga(getIdUsuario(), cab, recetas, buscarDispensacion);
	    f.setProducciones(producciones);

	    return mapping.findForward(forwardName);
	}

	public ActionForward spdTest(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
	    return baseLite(mapping, form, request, "spdTest", true, true);
	}
	
	public ActionForward spd(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
	    return baseLite(mapping, form, request, "spd", false, false);
	}
	public ActionForward spdR(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
	    return baseLite(mapping, form, request, "spdR", false, true);
	}
	public ActionForward spdBloques(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
	    return baseLite(mapping, form, request, "spdBloques", true, false);
	}
	public ActionForward spdBloquesYTomas(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
	    return baseLite(mapping, form, request, "spdBloquesYTomas", true, false);
	}
	public ActionForward spdDetalleBolsas(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
	    return baseLite(mapping, form, request, "spdDetalleBolsas", true, false);
	}

	
	
    public ActionForward detalle(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final InformeRDForm f= (InformeRDForm)form;
    	FicheroResiBean cab = FicheroResiCabeceraDAO.getFicheroResiCabeceraByOid(getIdUsuario(), f.getOidFicheroResiCabecera());
    	f.setCabecera(cab);
        InformeRDHelper helper = new InformeRDHelper();
        List<ProduccionPaciente> producciones =  helper.findByIdResidenciaCarga(getIdUsuario(), cab, false, false);
       
        f.setProducciones(producciones);
        return mapping.findForward("detalle");
    }
    
    public void log(final String message, final int level) {
        Logger.log("SpdLogger", message, level);
    }
}