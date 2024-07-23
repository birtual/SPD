package lopicost.spd.struts.form;

import java.util.List;

public class MenuForm extends GenericForm
{
    private List listaMenu;
    private List listaPerfiles;
    private String idPerfil;
    private String idEnlace;
    private boolean activo;
    private int orden;
    
    public List getListaMenu() {
        return this.listaMenu;
    }
    
    public void setListaMenu(final List listaMenu) {
        this.listaMenu = listaMenu;
    }
    
    public String getIdPerfil() {
        return this.idPerfil;
    }
    
    public void setIdPerfil(final String idPerfil) {
        this.idPerfil = idPerfil;
    }
    
    public String getIdEnlace() {
        return this.idEnlace;
    }
    
    public void setIdEnlace(final String idEnlace) {
        this.idEnlace = idEnlace;
    }
    
    public boolean isActivo() {
        return this.activo;
    }
    
    public void setActivo(final boolean activo) {
        this.activo = activo;
    }
    
    public int getOrden() {
        return this.orden;
    }
    
    public void setOrden(final int orden) {
        this.orden = orden;
    }
    
    public List getListaPerfiles() {
        return this.listaPerfiles;
    }
    
    public void setListaPerfiles(final List listaPerfiles) {
        this.listaPerfiles = listaPerfiles;
    }
}