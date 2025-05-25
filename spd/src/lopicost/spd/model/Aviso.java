package lopicost.spd.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Aviso implements Serializable
{
	private int oidAviso;
	private Date fechaInsert;
	private Date fechaUpdate;
	private String texto;
	private String fechaInicio;
	private String fechaFin;
	private String activo;
	private String idFarmacia;
	private String usuarioCreador;
	private String usuarioUpdate;
	private int orden;
	private String tipo;
	
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");


     private Date parseDate(String dateStr) {
        try {
            return dateStr != null && !dateStr.isEmpty() ? DATE_FORMAT.parse(dateStr) : null;
        } catch (Exception e) {
            return null; // Manejar la excepción adecuadamente si el formato es incorrecto
        }
    }
   
	 // Convertir de String (dd/MM/yyyy) a Date para la base de datos
    public Date getFechaInicioDate() {
        return parseDate(fechaInicio);
    }

    public Date getFechaFinDate() {
        return parseDate(fechaFin);
    }
    
 // Getter adicional para mostrar la fecha formateada
    public String getFechaInsertFormateada() {
        if (fechaInsert == null) return "";
        return DATE_FORMAT.format(fechaInsert);
    }
    public String getFechaUpdateFormateada() {
        if (fechaUpdate == null) return "";
        return DATE_FORMAT.format(fechaUpdate);
    }
    
	public Date getFechaUpdate()			{		return fechaUpdate;		}	public void setFechaUpdate(Date fechaUpdate)	{		this.fechaUpdate = fechaUpdate;	}
	public int getOidAviso() 				{		return oidAviso;		}	public void setOidAviso(int oidAviso) 			{		this.oidAviso = oidAviso;		}
	public Date getFechaInsert() 			{		return fechaInsert;		}	public void setFechaInsert(Date fechaInsert)	{		this.fechaInsert = fechaInsert;	}
	public String getTexto() 				{		return texto;			}	public void setTexto(String texto) 				{		this.texto = texto;				}
	public String getFechaInicio() 			{		return fechaInicio;		}	public void setFechaInicio(String fechaInicio) 	{		this.fechaInicio = fechaInicio;	}
	public String getFechaFin() 			{		return fechaFin;		}	public void setFechaFin(String fechaFin) 		{		this.fechaFin = fechaFin;		}
	public String getActivo() 				{		return activo;			}	public void setActivo(String activo) 			{		this.activo = activo;			}
	public String getIdFarmacia() 			{		return idFarmacia;		}	public void setIdFarmacia(String idFarmacia) 	{		this.idFarmacia = idFarmacia;	}
	public String getUsuarioCreador() 		{		return usuarioCreador;	}	public void setUsuarioCreador(String creador) 	{		this.usuarioCreador = creador;	}
	public int getOrden() 					{		return orden;			}	public void setOrden(int orden) 				{		this.orden = orden;				}
	public String getTipo() 				{		return tipo;			}	public void setTipo(String tipo) 				{		this.tipo = tipo;				}
	public String getUsuarioUpdate() 		{		return usuarioUpdate;	}	public void setUsuarioUpdate(String user)		{		this.usuarioUpdate = user;		}
	

   
}