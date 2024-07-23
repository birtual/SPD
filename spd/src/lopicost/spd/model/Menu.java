package lopicost.spd.model;

import java.io.Serializable;

public class Menu implements Serializable
{
    private String idPerfil;
    private Enlace enlace;
    private boolean activo;
    private int orden;
    
    public String getIdPerfil() {
        return this.idPerfil;
    }
    
    public void setIdPerfil(final String idPerfil) {
        this.idPerfil = idPerfil;
    }
    
    public Enlace getEnlace() {
        return this.enlace;
    }
    
    public void setEnlace(final Enlace enlace) {
        this.enlace = enlace;
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
}