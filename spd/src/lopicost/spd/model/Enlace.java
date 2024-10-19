package lopicost.spd.model;

import org.apache.commons.lang.builder.HashCodeBuilder;
import java.io.Serializable;

public class Enlace implements Serializable
{
    private String idEnlace;
  //  private String aliasEnlace;
    private String idApartado;
    private String nombreApartado;
    private String aliasApartado;
    private String nombreEnlace;
    private String preEnlace;
    private String linkEnlace;
    private String paramsEnlace;
    private String descripcion;
    private boolean activo;
    private boolean nuevaVentana;
    private Usuario usuario;
    private int orden;
    private int nivel;

	public int hashCode() 			{   return new HashCodeBuilder().append((Object)this.getIdEnlace()).toHashCode();    }

	
    public String getIdEnlace() 	{   return this.idEnlace;    	}   public void setIdEnlace(final String idEnlace) 			{   this.idEnlace = idEnlace;    		}    
   // public String getAliasEnlace() 	{   return this.aliasEnlace;    }   public void setAliasEnlace(final String aliasEnlace) 	{   this.aliasEnlace = aliasEnlace;  	}    
    public String getAliasApartado(){   return this.aliasApartado;  }   public void setAliasApartado(final String aliasApartado){   this.aliasApartado = aliasApartado; }    
    public String getIdApartado() 	{   return this.idApartado;    	}   public void setIdApartado(final String idApartado) 		{   this.idApartado = idApartado;    	}    
    public String getNombreEnlace() {   return this.nombreEnlace;   }   public void setNombreEnlace(final String nombreEnlace) 	{   this.nombreEnlace = nombreEnlace;	}    
    public String getPreEnlace() 	{   return this.preEnlace;    	}   public void setPreEnlace(final String preEnlace) 		{   this.preEnlace = preEnlace;    		}    
    public String getLinkEnlace() 	{   return this.linkEnlace;  	}   public void setLinkEnlace(final String linkEnlace) 		{   this.linkEnlace = linkEnlace;    	}    
    public String getParamsEnlace() {   return this.paramsEnlace;	}   public void setParamsEnlace(final String paramsEnlace)	{   this.paramsEnlace = paramsEnlace;   }    
    public String getDescripcion() 	{   return this.descripcion;    }   public void setDescripcion(final String descripcion) 	{   this.descripcion = descripcion;    	}    
    public boolean isActivo() 		{   return this.activo;    		}   public void setActivo(final boolean activo) 			{   this.activo = activo;    			}    
    public int getOrden() 			{   return this.orden;    		}	public void setOrden(final int orden) 					{   this.orden = orden;    				}
    public Usuario getUsuario() 	{   return this.usuario;    	}   public void setUsuario(final Usuario usuario) 			{   this.usuario = usuario;    			}
    public boolean isNuevaVentana() {	return nuevaVentana;		}	public void setNuevaVentana(boolean nuevaVentana) 		{	this.nuevaVentana = nuevaVentana;	}
	public int getNivel() 			{	return nivel;				}	public void setNivel(int nivel) 						{	this.nivel = nivel;					}
	
    public String getNombreApartado() {return nombreApartado;	}	public void setNombreApartado(String nombreApartado) {		this.nombreApartado = nombreApartado;	}

}