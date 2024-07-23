package lopicost.spd.struts.bean;

public class PerfilesBean
{
    String idPerfil;
    String nombrePerfil;
    String descripcionPerfil;
    
    public PerfilesBean() {
        this.idPerfil = "";
        this.nombrePerfil = "";
        this.descripcionPerfil = "";
    }
    
    public String getIdPerfil() {
        return this.idPerfil;
    }
    
    public void setIdPerfil(final String idPerfil) {
        this.idPerfil = idPerfil;
    }
    
    public String getNombrePerfil() {
        return this.nombrePerfil;
    }
    
    public void setNombrePerfil(final String nombrePerfil) {
        this.nombrePerfil = nombrePerfil;
    }
    
    public String getDescripcionPerfil() {
        return this.descripcionPerfil;
    }
    
    public void setDescripcionPerfil(final String descripcionPerfil) {
        this.descripcionPerfil = descripcionPerfil;
    }
}