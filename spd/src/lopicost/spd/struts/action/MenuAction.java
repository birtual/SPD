package lopicost.spd.struts.action;

import lopicost.config.logger.Logger;
import lopicost.spd.model.Enlace;
import lopicost.spd.model.GestSustitucionesLite;
import lopicost.spd.model.Menu;
import lopicost.spd.persistence.EnlaceDAO;
import lopicost.spd.persistence.GestSustitucionesLiteDAO;

import java.util.ArrayList;
import java.util.List;
import lopicost.spd.persistence.MenuDAO;
import lopicost.spd.persistence.UsuarioDAO;
import lopicost.spd.struts.form.GestSustitucionesLiteForm;
import lopicost.spd.struts.form.MenuForm;
import org.apache.struts.action.ActionForward;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class MenuAction extends DispatchAction
{
    public ActionForward list(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final MenuForm menuForm = (MenuForm)form;
        final List perfiles = UsuarioDAO.getIdPerfiles();
        menuForm.setListaPerfiles(perfiles);
        final String idPerfil = menuForm.getIdPerfil();
        if (idPerfil != null && !idPerfil.equals("")) {
            menuForm.setListaMenu(MenuDAO.findByIdProfile(idPerfil));
        }
        return mapping.findForward("list");
    }
    
    public ActionForward borrar(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final MenuForm menuForm = (MenuForm)form;
        final String idEnlace = menuForm.getIdEnlace();
        final String idPerfil = menuForm.getIdPerfil();
        final boolean borrado = MenuDAO.borrar(idPerfil, idEnlace);
        return this.list(mapping, form, request, response);
    }
    
    public ActionForward nuevo(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final MenuForm menuForm = (MenuForm)form;
        final String idEnlace = menuForm.getIdEnlace();
        final String idPerfil = menuForm.getIdPerfil();
        final Enlace enlace = EnlaceDAO.findById(idEnlace);
        final boolean nuevo = MenuDAO.nuevo(idPerfil, enlace);
        return this.list(mapping, form, request, response);
    }
    public ActionForward subir(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final MenuForm menuForm = (MenuForm)form;
        final String idEnlace = menuForm.getIdEnlace();
        final String idPerfil = menuForm.getIdPerfil();
        final Menu menu = MenuDAO.findById(idPerfil, idEnlace);
        int posicion=menu.getOrden();
        MenuDAO.cambiarPosicion(idPerfil, idEnlace, posicion-1);
        
       // MenuDAO.intercambiarPosicion(idPerfil, idEnlace, posicion-1, 1);
		return this.list(mapping, form, request, response);
    }
    public ActionForward bajar(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final MenuForm menuForm = (MenuForm)form;
        final String idEnlace = menuForm.getIdEnlace();
        final String idPerfil = menuForm.getIdPerfil();
        final Menu menu = MenuDAO.findById(idPerfil, idEnlace);
        int posicion=menu.getOrden();
       // MenuDAO.intercambiarPosicion(idPerfil, idEnlace, posicion+1, -1);
        MenuDAO.cambiarPosicion(idPerfil, idEnlace, posicion+1);
		return this.list(mapping, form, request, response);
    }

	
	
	
    
    public void log(final String message, final int level) {
        Logger.log("SpdLogger", message, level);
    }
}