package lopicost.spd.struts.action;

import lopicost.config.logger.Logger;
import lopicost.spd.persistence.FicheroResiCabeceraDAO;
import lopicost.spd.robot.bean.rd.ProduccionPaciente;
import lopicost.spd.robot.helper.InformeProdHelper;
import lopicost.spd.struts.bean.FicheroResiBean;
import lopicost.spd.struts.form.InformeProdSpdForm;

import org.apache.struts.action.ActionForward;
import javax.servlet.http.HttpServletResponse;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;


public class InformeProdSpdAction extends GenericAction
{
    public ActionForward globalProd(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final InformeProdSpdForm f= (InformeProdSpdForm)form;
    	FicheroResiBean cab = FicheroResiCabeceraDAO.getFicheroResiCabeceraByOid(getIdUsuario(), f.getOidFicheroResiCabecera());
    	f.setCabecera(cab);
        InformeProdHelper helper = new InformeProdHelper();
        List<ProduccionPaciente> producciones =  helper.findByIdResidenciaCarga(getIdUsuario(), cab);
       
        f.setProducciones(producciones);
        return mapping.findForward("globalProd");
    }
  
    public ActionForward detalleProd(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final InformeProdSpdForm f= (InformeProdSpdForm)form;
        
        
        return mapping.findForward("detalleProd");
    }
    
    public void log(final String message, final int level) {
        Logger.log("SpdLogger", message, level);
    }
}